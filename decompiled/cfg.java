import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public class cfg extends cfj<Integer> {
   private final ImmutableSet<Integer> a;

   protected cfg(String var1, int var2, int var3) {
      super(_snowman, Integer.class);
      if (_snowman < 0) {
         throw new IllegalArgumentException("Min value of " + _snowman + " must be 0 or greater");
      } else if (_snowman <= _snowman) {
         throw new IllegalArgumentException("Max value of " + _snowman + " must be greater than min (" + _snowman + ")");
      } else {
         Set<Integer> _snowman = Sets.newHashSet();

         for (int _snowmanx = _snowman; _snowmanx <= _snowman; _snowmanx++) {
            _snowman.add(_snowmanx);
         }

         this.a = ImmutableSet.copyOf(_snowman);
      }
   }

   @Override
   public Collection<Integer> a() {
      return this.a;
   }

   @Override
   public boolean equals(Object var1) {
      if (this == _snowman) {
         return true;
      } else if (_snowman instanceof cfg && super.equals(_snowman)) {
         cfg _snowman = (cfg)_snowman;
         return this.a.equals(_snowman.a);
      } else {
         return false;
      }
   }

   @Override
   public int b() {
      return 31 * super.b() + this.a.hashCode();
   }

   public static cfg a(String var0, int var1, int var2) {
      return new cfg(_snowman, _snowman, _snowman);
   }

   @Override
   public Optional<Integer> b(String var1) {
      try {
         Integer _snowman = Integer.valueOf(_snowman);
         return this.a.contains(_snowman) ? Optional.of(_snowman) : Optional.empty();
      } catch (NumberFormatException var3) {
         return Optional.empty();
      }
   }

   public String a(Integer var1) {
      return _snowman.toString();
   }
}
