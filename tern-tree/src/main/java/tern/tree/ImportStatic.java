package tern.tree;

import static tern.core.result.Result.NORMAL;

import java.util.List;

import tern.core.Compilation;
import tern.core.Context;
import tern.core.Execution;
import tern.core.NameFormatter;
import tern.core.NoExecution;
import tern.core.Statement;
import tern.core.constraint.Constraint;
import tern.core.error.InternalStateException;
import tern.core.function.Function;
import tern.core.module.Module;
import tern.core.module.Path;
import tern.core.property.Property;
import tern.core.scope.Scope;
import tern.core.scope.ScopeState;
import tern.core.scope.index.Local;
import tern.core.trace.Trace;
import tern.core.trace.TraceInterceptor;
import tern.core.type.Type;

public class ImportStatic implements Compilation {   
   
   private final Qualifier qualifier;    
   
   public ImportStatic(Qualifier qualifier) {
      this.qualifier = qualifier;
   }

   @Override
   public Object compile(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = Trace.getImport(module, path, line);
      String location = qualifier.getLocation();
      String target = qualifier.getTarget();
      String name = qualifier.getName();
      
      return new CompileResult(interceptor, trace, location, target, name);
   }
   
   private static class CompileResult extends Statement {
      
      private final TraceInterceptor interceptor;
      private final StaticImportMatcher matcher;
      private final NameFormatter formatter;  
      private final Execution execution;    
      private final String location;
      private final String target;
      private final String prefix;
      private final Trace trace;
      
      public CompileResult(TraceInterceptor interceptor, Trace trace, String location, String target, String prefix) {
         this.execution = new NoExecution(NORMAL);
         this.matcher = new StaticImportMatcher();
         this.formatter = new NameFormatter();    
         this.interceptor = interceptor;
         this.location = location;
         this.target = target;
         this.prefix = prefix;
         this.trace = trace;
      }
      
      @Override
      public boolean define(Scope scope) throws Exception {
         try {
            Module module = scope.getModule();         
            String parent = formatter.formatFullName(location, target);
            Type type = module.getType(parent); // this is a type name
            
            if(type == null) {
               throw new InternalStateException("Could not import '" + parent + "'");
            }
            List<Function> list = module.getFunctions();
            List<Function> functions = matcher.matchFunctions(type, prefix);
            List<Property> properties = matcher.matchProperties(type, prefix);
            Scope outer = module.getScope(); // make sure to use module scope
            ScopeState state = outer.getState(); 
            
            for(Property property : properties) {
               String name = property.getName();
               Object value = property.getValue(null); // its static
               Constraint constraint = property.getConstraint();
               Local local = Local.getConstant(value, name, constraint);
   
               try {
                  state.addValue(name, local);
               }catch(Exception e) {
                  throw new InternalStateException("Import of static property '" + name +"' failed", e);
               }  
            }
            list.addAll(functions);     
         } catch(Exception cause) {
            interceptor.traceCompileError(scope, trace, cause);
         }
         return true;
      }
      
      @Override
      public Execution compile(Scope scope, Constraint returns) throws Exception {
         return execution;
      }
      
   }
}