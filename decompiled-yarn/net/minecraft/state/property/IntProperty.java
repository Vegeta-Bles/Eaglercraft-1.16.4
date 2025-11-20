package net.minecraft.state.property;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public class IntProperty extends Property<Integer> {
   private final ImmutableSet<Integer> values;

   protected IntProperty(String name, int min, int max) {
      super(name, Integer.class);
      if (min < 0) {
         throw new IllegalArgumentException("Min value of " + name + " must be 0 or greater");
      } else if (max <= min) {
         throw new IllegalArgumentException("Max value of " + name + " must be greater than min (" + min + ")");
      } else {
         Set<Integer> _snowman = Sets.newHashSet();

         for (int _snowmanx = min; _snowmanx <= max; _snowmanx++) {
            _snowman.add(_snowmanx);
         }

         this.values = ImmutableSet.copyOf(_snowman);
      }
   }

   @Override
   public Collection<Integer> getValues() {
      return this.values;
   }

   @Override
   public boolean equals(Object _snowman) {
      if (this == _snowman) {
         return true;
      } else if (_snowman instanceof IntProperty && super.equals(_snowman)) {
         IntProperty _snowmanx = (IntProperty)_snowman;
         return this.values.equals(_snowmanx.values);
      } else {
         return false;
      }
   }

   @Override
   public int computeHashCode() {
      return 31 * super.computeHashCode() + this.values.hashCode();
   }

   public static IntProperty of(String name, int min, int max) {
      return new IntProperty(name, min, max);
   }

   @Override
   public Optional<Integer> parse(String name) {
      try {
         Integer _snowman = Integer.valueOf(name);
         return this.values.contains(_snowman) ? Optional.of(_snowman) : Optional.empty();
      } catch (NumberFormatException var3) {
         return Optional.empty();
      }
   }

   public String name(Integer _snowman) {
      return _snowman.toString();
   }
}
