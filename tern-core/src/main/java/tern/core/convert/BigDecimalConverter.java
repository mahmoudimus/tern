package tern.core.convert;

import static tern.core.convert.Score.COMPATIBLE;
import static tern.core.convert.Score.EXACT;
import static tern.core.convert.Score.SIMILAR;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import tern.core.type.Type;

public class BigDecimalConverter extends NumberConverter {

   private static final Class[] BIG_DECIMAL_TYPES = {
      BigDecimal.class, 
      Double.class, 
      Float.class, 
      Long.class, 
      AtomicLong.class,
      Integer.class, 
      BigInteger.class, 
      AtomicInteger.class, 
      Short.class, 
      Byte.class,
      Number.class
   };
   
   private static final Score[] BIG_DECIMAL_SCORES = {
      EXACT,
      SIMILAR,
      SIMILAR,
      COMPATIBLE,
      COMPATIBLE,
      COMPATIBLE,
      COMPATIBLE,
      COMPATIBLE,
      COMPATIBLE,
      COMPATIBLE,
      COMPATIBLE
   };
   
   public BigDecimalConverter(Type type) {
      super(type, BIG_DECIMAL_TYPES, BIG_DECIMAL_SCORES);
   }
}