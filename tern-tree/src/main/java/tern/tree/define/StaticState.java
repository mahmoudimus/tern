package tern.tree.define;

import tern.core.scope.Scope;
import tern.core.type.Type;

public class StaticState extends StaticBlock {
   
   private final StaticConstantCollector collector;
   
   public StaticState() {
      this.collector = new StaticConstantCollector();
   }

   @Override
   protected void compile(Scope scope) throws Exception { 
      Type type = scope.getType();
      collector.collect(type);
   }
}