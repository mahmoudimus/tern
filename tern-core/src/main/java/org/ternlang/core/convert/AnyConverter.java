package org.ternlang.core.convert;

import static org.ternlang.core.convert.Score.SIMILAR;

import org.ternlang.core.type.Type;
import org.ternlang.core.convert.proxy.ProxyWrapper;

public class AnyConverter extends ConstraintConverter {
   
   private final ProxyWrapper wrapper;
   
   public AnyConverter(ProxyWrapper wrapper) {
      this.wrapper = wrapper;
   }
   
   @Override
   public Score score(Type type) throws Exception {
      return SIMILAR;
   }
   
   @Override
   public Score score(Object value) throws Exception {
      return SIMILAR;
   }
 
   @Override
   public Object assign(Object object) {
      return object;
   }
   
   @Override
   public Object convert(Object object) {
      return wrapper.toProxy(object);
   }
}