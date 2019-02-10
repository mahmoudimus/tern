package tern.core.scope;

import tern.core.error.InternalStateException;
import tern.core.module.Module;
import tern.core.scope.index.ArrayTable;
import tern.core.scope.index.ScopeIndex;
import tern.core.scope.index.ScopeTable;
import tern.core.scope.index.StackIndex;
import tern.core.type.Type;
import tern.core.variable.Value;

public class CompoundScope implements Scope {
   
   private final ScopeIndex index;
   private final ScopeTable table;
   private final ScopeState state;
   private final Scope outer;
   
   public CompoundScope(Scope inner, Scope outer) {
      this.index = new StackIndex(inner);
      this.state = new MapState(inner);
      this.table = new ArrayTable();
      this.outer = outer;
   } 
  
   @Override
   public Scope getStack() {
      throw new InternalStateException("Stack already created");
   }  
   
   @Override
   public Scope getScope() {
      return outer;
   }
   
   @Override
   public Value getThis() {
      return outer.getThis();
   }
   
   @Override
   public Type getHandle() {
      return outer.getType();
   }
   
   @Override
   public Type getType() {
      return outer.getType();
   }
  
   @Override
   public Module getModule() {
      return outer.getModule();
   } 
   
   @Override
   public ScopeIndex getIndex(){
      return index;
   }
   
   @Override
   public ScopeTable getTable(){
      return table;
   }
   
   @Override
   public ScopeState getState() {
      return state;
   }
   
   @Override
   public String toString() {
      return String.valueOf(state);
   }
}