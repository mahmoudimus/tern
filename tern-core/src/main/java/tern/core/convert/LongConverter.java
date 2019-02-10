package tern.core.convert;

import static tern.core.convert.Score.COMPATIBLE;
import static tern.core.convert.Score.EXACT;
import static tern.core.convert.Score.SIMILAR;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import tern.core.type.Type;

public class LongConverter extends NumberConverter{
   
   private static final Class[] LONG_TYPES = {
      Long.class, 
      Integer.class, 
      BigInteger.class, 
      AtomicInteger.class, 
      AtomicLong.class,
      Double.class, 
      Float.class, 
      BigDecimal.class, 
      Short.class, 
      Byte.class,
      Number.class
   };
   
   private static final Score[] LONG_SCORES = {
      EXACT,
      SIMILAR,
      SIMILAR,
      SIMILAR,
      SIMILAR,
      COMPATIBLE,
      COMPATIBLE,
      COMPATIBLE,
      COMPATIBLE,
      COMPATIBLE,
      COMPATIBLE
   };
   
   public LongConverter(Type type) {
      super(type, LONG_TYPES, LONG_SCORES);
   }
}