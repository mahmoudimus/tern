package org.ternlang.tree.construct;

import java.util.LinkedHashSet;
import java.util.Set;

import org.ternlang.core.Compilation;
import org.ternlang.core.Context;
import org.ternlang.core.Evaluation;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.convert.proxy.ProxyWrapper;
import org.ternlang.core.module.Module;
import org.ternlang.core.module.Path;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.trace.Trace;
import org.ternlang.core.trace.TraceEvaluation;
import org.ternlang.core.trace.TraceInterceptor;
import org.ternlang.core.variable.Value;
import org.ternlang.parse.StringToken;

public class ConstructSet implements Compilation {
   
   private final Evaluation construct;
  
   public ConstructSet(StringToken token) {
      this(null, token);
   }
   
   public ConstructSet(ElementData elements) {
      this(elements, null);
   }
   
   public ConstructSet(ElementData elements, StringToken token) {
      this.construct = new CompileResult(elements);
   }
   
   @Override
   public Evaluation compile(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = Trace.getConstruct(module, path, line);
      
      return new TraceEvaluation(interceptor, construct, trace);
   }
   
   private static class CompileResult extends Evaluation {

      private final ElementData elements;
      
      public CompileResult(ElementData elements) {
         this.elements = elements;
      }   
      
      @Override
      public void define(Scope scope) throws Exception { 
         if(elements != null) {
            elements.define(scope);      
         }   
      }
      
      @Override
      public Constraint compile(Scope scope, Constraint left) throws Exception {
         if(elements != null) {
            elements.compile(scope);      
         }  
         return Constraint.SET;
      } 
      
      @Override
      public Value evaluate(Scope scope, Value left) throws Exception { 
         Set result = new LinkedHashSet();
         
         if(elements != null) {
            Value value = elements.evaluate(scope);
            Iterable iterable = value.getValue();
            Module module = scope.getModule();
            Context context = module.getContext();
            ProxyWrapper wrapper = context.getWrapper();

            for(Object element : iterable) {
               Object proxy = wrapper.toProxy(element);
               result.add(proxy);
            }
         }   
         return Value.getTransient(result);
      }
   }
}