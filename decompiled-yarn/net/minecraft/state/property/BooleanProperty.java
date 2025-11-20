package net.minecraft.state.property;

import com.google.common.collect.ImmutableSet;
import java.util.Collection;
import java.util.Optional;

public class BooleanProperty extends Property<Boolean> {
   private final ImmutableSet<Boolean> values = ImmutableSet.of(true, false);

   protected BooleanProperty(String name) {
      super(name, Boolean.class);
   }

   @Override
   public Collection<Boolean> getValues() {
      return this.values;
   }

   public static BooleanProperty of(String name) {
      return new BooleanProperty(name);
   }

   @Override
   public Optional<Boolean> parse(String name) {
      return !"true".equals(name) && !"false".equals(name) ? Optional.empty() : Optional.of(Boolean.valueOf(name));
   }

   public String name(Boolean _snowman) {
      return _snowman.toString();
   }

   @Override
   public boolean equals(Object _snowman) {
      if (this == _snowman) {
         return true;
      } else if (_snowman instanceof BooleanProperty && super.equals(_snowman)) {
         BooleanProperty _snowmanx = (BooleanProperty)_snowman;
         return this.values.equals(_snowmanx.values);
      } else {
         return false;
      }
   }

   @Override
   public int computeHashCode() {
      return 31 * super.computeHashCode() + this.values.hashCode();
   }
}
