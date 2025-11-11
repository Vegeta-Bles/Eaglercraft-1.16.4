import com.google.common.base.Joiner;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class cen {
   private static final Joiner a = Joiner.on(",");
   private final List<String[]> b = Lists.newArrayList();
   private final Map<Character, Predicate<cel>> c = Maps.newHashMap();
   private int d;
   private int e;

   private cen() {
      this.c.put(' ', Predicates.alwaysTrue());
   }

   public cen a(String... var1) {
      if (!ArrayUtils.isEmpty(_snowman) && !StringUtils.isEmpty(_snowman[0])) {
         if (this.b.isEmpty()) {
            this.d = _snowman.length;
            this.e = _snowman[0].length();
         }

         if (_snowman.length != this.d) {
            throw new IllegalArgumentException("Expected aisle with height of " + this.d + ", but was given one with a height of " + _snowman.length + ")");
         } else {
            for (String _snowman : _snowman) {
               if (_snowman.length() != this.e) {
                  throw new IllegalArgumentException(
                     "Not all rows in the given aisle are the correct width (expected " + this.e + ", found one with " + _snowman.length() + ")"
                  );
               }

               for (char _snowmanx : _snowman.toCharArray()) {
                  if (!this.c.containsKey(_snowmanx)) {
                     this.c.put(_snowmanx, null);
                  }
               }
            }

            this.b.add(_snowman);
            return this;
         }
      } else {
         throw new IllegalArgumentException("Empty pattern for aisle");
      }
   }

   public static cen a() {
      return new cen();
   }

   public cen a(char var1, Predicate<cel> var2) {
      this.c.put(_snowman, _snowman);
      return this;
   }

   public cem b() {
      return new cem(this.c());
   }

   private Predicate<cel>[][][] c() {
      this.d();
      Predicate<cel>[][][] _snowman = (Predicate<cel>[][][])Array.newInstance(Predicate.class, this.b.size(), this.d, this.e);

      for (int _snowmanx = 0; _snowmanx < this.b.size(); _snowmanx++) {
         for (int _snowmanxx = 0; _snowmanxx < this.d; _snowmanxx++) {
            for (int _snowmanxxx = 0; _snowmanxxx < this.e; _snowmanxxx++) {
               _snowman[_snowmanx][_snowmanxx][_snowmanxxx] = this.c.get(this.b.get(_snowmanx)[_snowmanxx].charAt(_snowmanxxx));
            }
         }
      }

      return _snowman;
   }

   private void d() {
      List<Character> _snowman = Lists.newArrayList();

      for (Entry<Character, Predicate<cel>> _snowmanx : this.c.entrySet()) {
         if (_snowmanx.getValue() == null) {
            _snowman.add(_snowmanx.getKey());
         }
      }

      if (!_snowman.isEmpty()) {
         throw new IllegalStateException("Predicates for character(s) " + a.join(_snowman) + " are missing");
      }
   }
}
