package tern.core;

import static tern.core.ModifierType.ABSTRACT;
import static tern.core.ModifierType.CONSTANT;
import static tern.core.ModifierType.OVERRIDE;
import static tern.core.ModifierType.PRIVATE;
import static tern.core.ModifierType.PUBLIC;
import static tern.core.ModifierType.STATIC;
import static tern.core.ModifierType.VARIABLE;

import tern.core.error.InternalStateException;
import tern.core.function.Function;
import tern.core.property.Property;

public class ModifierValidator {
   
   private static final int CONSTANT_VARIABLE = CONSTANT.mask | VARIABLE.mask;
   private static final int OVERRIDE_STATIC = OVERRIDE.mask | STATIC.mask;
   private static final int ABSTRACT_STATIC = ABSTRACT.mask | STATIC.mask;
   private static final int PUBLIC_PRIVATE = PUBLIC.mask | PRIVATE.mask;
   
   public ModifierValidator() {
      super();
   }
   
   public void validate(Entity source, Property property, int modifiers) {
      if((PUBLIC_PRIVATE & modifiers) == PUBLIC_PRIVATE) {
         throw new InternalStateException("Property '" + source + '.' + property + "' is both public and private");
      }
      if((CONSTANT_VARIABLE & modifiers) == CONSTANT_VARIABLE) {
         throw new InternalStateException("Property '" + source + '.' + property + "' is both variable and constant");
      }
      if((OVERRIDE.mask & modifiers) == OVERRIDE.mask) {
         throw new InternalStateException("Property '" + source + '.' + property + "' is declared as override");
      }
      if((ABSTRACT.mask & modifiers) == ABSTRACT.mask) {
         throw new InternalStateException("Property '" + source + '.' + property + "' is declared as abstract");
      }
   }
   
   public void validate(Entity source, Function function, int modifiers) {
      int type = source.getModifiers();
      
      if((PUBLIC_PRIVATE & modifiers) == PUBLIC_PRIVATE) {
         throw new InternalStateException("Function '" + function + "' is both public and private");
      }
      if((OVERRIDE_STATIC & modifiers) == OVERRIDE_STATIC) {
         throw new InternalStateException("Function '" + function + "' is both static and override");
      }
      if((ABSTRACT_STATIC & modifiers) == ABSTRACT_STATIC) {
         throw new InternalStateException("Function '" + function + "' is both static and abstract");
      }
      if((CONSTANT.mask & modifiers) == CONSTANT.mask) {
         throw new InternalStateException("Function '" + function + "' is declared as constant");
      }
      if((VARIABLE.mask & modifiers) == VARIABLE.mask) {
         throw new InternalStateException("Function '" + function + "' is declared as variable");
      }
      if((ABSTRACT.mask & modifiers) == ABSTRACT.mask && (ABSTRACT.mask & type) != ABSTRACT.mask) {
         throw new InternalStateException("Function '" + function + "' is abstract but '" + source + "' is not");
      }
   }
}