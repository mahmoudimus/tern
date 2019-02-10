package tern.core.variable.bind;

import tern.core.constraint.Constraint;
import tern.core.convert.proxy.ProxyWrapper;
import tern.core.error.ErrorHandler;
import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.core.variable.Value;
import tern.core.variable.index.VariableIndexer;
import tern.core.variable.index.VariablePointer;

public class VariableBinder {

   private final VariableIndexer resolver;
   private final ErrorHandler handler;
   private final ProxyWrapper wrapper;
   private final String name;
   
   public VariableBinder(ErrorHandler handler, ProxyWrapper wrapper, String name) {
      this.resolver = new VariableIndexer(wrapper, name);
      this.wrapper = wrapper;
      this.handler = handler;
      this.name = name;
   }
   
   public Constraint compile(Scope scope) throws Exception {
      VariablePointer pointer = resolver.index(scope);
      Constraint value = pointer.getConstraint(scope, null);
      
      if(value == null) {
         handler.failCompileReference(scope, name);
      }
      return value;
   }
   
   public Constraint compile(Scope scope, Constraint left) throws Exception {
      VariablePointer pointer = resolver.index(scope, left);
      Constraint value = pointer.getConstraint(scope, left);
      Type type = left.getType(scope);
      
      if(value == null) {
         handler.failCompileReference(scope, type, name);
      }
      return value;
   }
   
   public Value bind(Scope scope) throws Exception {
      VariablePointer pointer = resolver.index(scope);
      Value value = pointer.getValue(scope, null);
      
      if(value == null) {
         handler.failRuntimeReference(scope, name);
      }
      return value;
   }
   
   public Value bind(Scope scope, Value left) throws Exception {
      Object original = left.getValue();
      Object object = wrapper.fromProxy(original); // what about double wrapping?
      VariablePointer pointer = resolver.index(scope, object);
      Value value = pointer.getValue(scope, object);
      
      if(value == null) {
         handler.failRuntimeReference(scope, object, name);
      }
      return value;
   }
}