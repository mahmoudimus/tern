package org.ternlang.core.convert;

import junit.framework.TestCase;

import org.ternlang.core.MockType;
import org.ternlang.core.type.Type;
import org.ternlang.core.convert.BooleanConverter;
import org.ternlang.core.convert.Score;

public class BooleanConverterTest extends TestCase {

   public void testBoolean() throws Exception {
      Type type = new MockType(null, null, null, Boolean.class);
      BooleanConverter converter = new BooleanConverter(type);
      
      assertEquals(converter.score(true), Score.EXACT);
      assertEquals(converter.score(Boolean.TRUE), Score.EXACT);
      assertEquals(converter.score(Boolean.FALSE), Score.EXACT);
      assertEquals(converter.score("true"), Score.INVALID);
      assertEquals(converter.score("false"), Score.INVALID);
      assertEquals(converter.score("TRUE"), Score.INVALID);
      assertEquals(converter.score("FALSE"), Score.INVALID);
      assertEquals(converter.score("yes"), Score.INVALID);
      assertEquals(converter.score((Object)null), Score.POSSIBLE);
      
      assertEquals(converter.convert(true), Boolean.TRUE);
      assertEquals(converter.convert(false), Boolean.FALSE);
      assertEquals(converter.convert(Boolean.TRUE), Boolean.TRUE);
      assertEquals(converter.convert(Boolean.FALSE), Boolean.FALSE);
      assertEquals(converter.convert((Object)null), null);
   }
   
   public void testPrimitiveBoolean() throws Exception {
      Type type = new MockType(null, null, null, boolean.class);
      BooleanConverter converter = new BooleanConverter(type);
      
      assertEquals(converter.score(true), Score.EXACT);
      assertEquals(converter.score(Boolean.TRUE), Score.EXACT);
      assertEquals(converter.score(Boolean.FALSE), Score.EXACT);
      assertEquals(converter.score("true"), Score.INVALID);
      assertEquals(converter.score("false"), Score.INVALID);
      assertEquals(converter.score("TRUE"), Score.INVALID);
      assertEquals(converter.score("FALSE"), Score.INVALID);
      assertEquals(converter.score("yes"), Score.INVALID);
      assertEquals(converter.score((Object)null), Score.INVALID);
   }
}
