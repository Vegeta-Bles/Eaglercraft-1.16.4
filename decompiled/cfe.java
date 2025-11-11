import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class cfe<T extends Enum<T> & afs> extends cfj<T> {
   private final ImmutableSet<T> a;
   private final Map<String, T> b = Maps.newHashMap();

   protected cfe(String var1, Class<T> var2, Collection<T> var3) {
      super(_snowman, _snowman);
      this.a = ImmutableSet.copyOf(_snowman);

      for (T _snowman : _snowman) {
         String _snowmanx = _snowman.a();
         if (this.b.containsKey(_snowmanx)) {
            throw new IllegalArgumentException("Multiple values have the same name '" + _snowmanx + "'");
         }

         this.b.put(_snowmanx, _snowman);
      }
   }

   @Override
   public Collection<T> a() {
      return this.a;
   }

   @Override
   public Optional<T> b(String var1) {
      return Optional.ofNullable(this.b.get(_snowman));
   }

   public String a(T var1) {
      return _snowman.a();
   }

   @Override
   public boolean equals(Object var1) {
      if (this == _snowman) {
         return true;
      } else if (_snowman instanceof cfe && super.equals(_snowman)) {
         cfe<?> _snowman = (cfe<?>)_snowman;
         return this.a.equals(_snowman.a) && this.b.equals(_snowman.b);
      } else {
         return false;
      }
   }

   @Override
   public int b() {
      int _snowman = super.b();
      _snowman = 31 * _snowman + this.a.hashCode();
      return 31 * _snowman + this.b.hashCode();
   }

   public static <T extends Enum<T> & afs> cfe<T> a(String var0, Class<T> var1) {
      return a(_snowman, _snowman, Predicates.alwaysTrue());
   }

   public static <T extends Enum<T> & afs> cfe<T> a(String var0, Class<T> var1, Predicate<T> var2) {
      return a(_snowman, _snowman, Arrays.<T>stream(_snowman.getEnumConstants()).filter(_snowman).collect(Collectors.toList()));
   }

   public static <T extends Enum<T> & afs> cfe<T> a(String var0, Class<T> var1, T... var2) {
      return a(_snowman, _snowman, Lists.newArrayList(_snowman));
   }

   public static <T extends Enum<T> & afs> cfe<T> a(String var0, Class<T> var1, Collection<T> var2) {
      return new cfe<>(_snowman, _snowman, _snowman);
   }
}
