package org.ternlang.tree.define;

import org.ternlang.core.Evaluation;
import org.ternlang.core.scope.Scope;

public class StaticField extends StaticBlock {
   
   private final Evaluation evaluation;
   
   public StaticField(Evaluation evaluation){
      this.evaluation = evaluation;
   }
   
   @Override
   protected void define(Scope scope) throws Exception {
      evaluation.define(scope);
   }
   
   @Override
   protected void compile(Scope scope) throws Exception {
      evaluation.compile(scope, null);
   }

   @Override
   protected void allocate(Scope scope) throws Exception {
      evaluation.evaluate(scope, null);
   }
}