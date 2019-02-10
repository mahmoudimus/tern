package tern.tree.define;

import tern.core.Evaluation;
import tern.core.constraint.Constraint;
import tern.core.error.InternalStateException;
import tern.core.scope.Scope;
import tern.core.scope.ScopeState;
import tern.core.type.TypeState;
import tern.core.variable.Value;
import tern.tree.DeclarationAllocator;
import tern.tree.ModifierChecker;
import tern.tree.ModifierData;

public class MemberFieldAssembler {
   
   private final ModifierChecker checker;

   public MemberFieldAssembler(ModifierData modifiers) {
      this.checker = new ModifierChecker(modifiers);
   }
   
   public TypeState assemble(MemberFieldData data) throws Exception {
      Evaluation declaration = create(data);
      
      if (checker.isStatic()) {
         return new StaticField(declaration);
      }
      return new InstanceField(declaration);
   }
   
   private Evaluation create(MemberFieldData data) throws Exception {
      int modifiers = checker.getModifiers();
      String name = data.getName();
      String alias = data.getAlias();
      Constraint constraint = data.getConstraint();
      Evaluation declare = data.getValue();
      
      return new Declaration(name, alias, constraint, declare, modifiers);
   }
   
   private static class Declaration extends Evaluation {
      
      private final DeclarationAllocator allocator;
      private final Constraint constraint;
      private final Evaluation declare;
      private final String alias; // private name
      private final String name;
      private final int modifiers;
      
      public Declaration(String name, String alias, Constraint constraint, Evaluation declare, int modifiers) {
         this.allocator = new MemberFieldAllocator(constraint, declare);
         this.constraint = constraint;
         this.modifiers = modifiers;
         this.declare = declare;
         this.alias = alias;
         this.name = name;
      }  

      @Override
      public void define(Scope scope) throws Exception {
         if(declare != null) {
            declare.define(scope);
         }
      }

      @Override
      public Constraint compile(Scope scope, Constraint left) throws Exception {
         Value value = allocator.compile(scope, name, modifiers);
         ScopeState state = scope.getState();
         
         try { 
            state.addValue(alias, value);
         }catch(Exception e) {
            throw new InternalStateException("Declaration of variable '" + name +"' failed", e);
         }  
         return constraint;
      }
      
      @Override
      public Value evaluate(Scope scope, Value left) throws Exception {
         Value value = allocator.allocate(scope, name, modifiers);
         ScopeState state = scope.getState();
         
         try { 
            state.addValue(alias, value);
         }catch(Exception e) {
            throw new InternalStateException("Declaration of variable '" + name +"' failed", e);
         }  
         return value;
      }
   }
}