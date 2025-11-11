import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.util.List;
import java.util.Set;

public class aei<T> implements ael<T> {
   private final ImmutableList<T> b;
   private final Set<T> c;
   @VisibleForTesting
   protected final Class<?> a;

   protected aei(Set<T> var1, Class<?> var2) {
      this.a = _snowman;
      this.c = _snowman;
      this.b = ImmutableList.copyOf(_snowman);
   }

   public static <T> aei<T> a() {
      return new aei<>(ImmutableSet.of(), Void.class);
   }

   public static <T> aei<T> a(Set<T> var0) {
      return new aei<>(_snowman, c(_snowman));
   }

   @Override
   public boolean a(T var1) {
      return this.a.isInstance(_snowman) && this.c.contains(_snowman);
   }

   @Override
   public List<T> b() {
      return this.b;
   }

   private static <T> Class<?> c(Set<T> var0) {
      if (_snowman.isEmpty()) {
         return Void.class;
      } else {
         Class<?> _snowman = null;

         for (T _snowmanx : _snowman) {
            if (_snowman == null) {
               _snowman = _snowmanx.getClass();
            } else {
               _snowman = a(_snowman, _snowmanx.getClass());
            }
         }

         return _snowman;
      }
   }

   private static Class<?> a(Class<?> var0, Class<?> var1) {
      while (!_snowman.isAssignableFrom(_snowman)) {
         _snowman = _snowman.getSuperclass();
      }

      return _snowman;
   }
}
