package org.ternlang.tree.math;

import org.ternlang.core.variable.Value;
import org.ternlang.parse.StringToken;

public enum NumericOperator {
   REPLACE("", 0) {
      @Override
      public Value operate(Value left, Value right) {
         return right;
      }
      @Override
      public Value operate(Value left, Value right, ValueCalculator calculator) {
         return calculator.replace(left, right);
      }        
   },
   COALESCE("??", 1){
      @Override
      public Value operate(Value left, Value right, ValueCalculator calculator) {
         return calculator.coalesce(left, right);
      }      
   },   
   POWER("**", 2){
      @Override
      public Value operate(Value left, Value right, ValueCalculator calculator) {
         return calculator.power(left, right);
      }      
   },   
   DIVIDE("/", 3){
      @Override
      public Value operate(Value left, Value right, ValueCalculator calculator) {
         return calculator.divide(left, right);
      }      
   },
   MULTIPLY("*", 3){
      @Override
      public Value operate(Value left, Value right, ValueCalculator calculator) {
         return calculator.multiply(left, right);
      }      
   }, 
   MODULUS("%", 3){
      @Override
      public Value operate(Value left, Value right, ValueCalculator calculator) {
         return calculator.modulus(left, right);
      }      
   },   
   PLUS("+", 4){
      @Override
      public Value operate(Value left, Value right, ValueCalculator calculator) {
         return calculator.add(left, right);
      }      
   }, 
   MINUS("-", 4){
      @Override
      public Value operate(Value left, Value right, ValueCalculator calculator) {
         return calculator.subtract(left, right);
      }      
   },
   SHIFT_RIGHT(">>", 5){
      @Override
      public Value operate(Value left, Value right, ValueCalculator calculator) {
         return calculator.shiftRight(left, right);
      }      
   }, 
   SHIFT_LEFT("<<", 5){
      @Override
      public Value operate(Value left, Value right, ValueCalculator calculator) {
         return calculator.shiftLeft(left, right);
      }      
   },  
   UNSIGNED_SHIFT_RIGHT(">>>", 5){
      @Override
      public Value operate(Value left, Value right, ValueCalculator calculator) {
         return calculator.unsignedShiftRight(left, right);
      }      
   },
   AND("&", 6){
      @Override
      public Value operate(Value left, Value right, ValueCalculator calculator) {
         return calculator.and(left, right);
      }      
   },  
   OR("|", 6){
      @Override
      public Value operate(Value left, Value right, ValueCalculator calculator) {
         return calculator.or(left, right);
      }      
   }, 
   XOR("^", 6){
      @Override
      public Value operate(Value left, Value right, ValueCalculator calculator) {
         return calculator.xor(left, right); 
      }      
   };
   
   public final String operator;
   public final int priority;
   
   private NumericOperator(String operator, int priority) {
      this.priority = -priority; // invert value to sort
      this.operator = operator;
   }   
   
   public Value operate(Value left, Value right) {
      NumericConverter converter = NumericConverter.resolveConverter(left, right);
      return operate(left, right, converter.calculator);
   }
   
   public abstract Value operate(Value left, Value right, ValueCalculator calculator);
   
   public static NumericOperator resolveOperator(StringToken token) {
      if(token != null) {
         String value = token.getValue();
         
         for(NumericOperator operator : VALUES) {
            if(operator.operator.equals(value)) {
               return operator;
            }
         }
      }
      return null;
   }
   
   private static final NumericOperator[] VALUES = {
      PLUS,
      MINUS,
      DIVIDE,
      MULTIPLY,
      MODULUS,
      AND,
      OR,
      XOR,
      SHIFT_LEFT,
      SHIFT_RIGHT,
      UNSIGNED_SHIFT_RIGHT,
      POWER,
      COALESCE,
      REPLACE
   };
}