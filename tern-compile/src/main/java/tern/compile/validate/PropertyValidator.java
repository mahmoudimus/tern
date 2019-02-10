package tern.compile.validate;

import tern.core.convert.ConstraintMatcher;
import tern.core.property.Property;
import tern.core.type.Type;

public class PropertyValidator {
   
   private final ConstraintMatcher matcher;
   
   public PropertyValidator(ConstraintMatcher matcher) {
      this.matcher = matcher;
   }
   
   public void validate(Property property) throws Exception {
      Type source = property.getSource();

      if(source == null) {
         throw new ValidateException("Property '" + property + "' does not have a type");
      }
   }   
}