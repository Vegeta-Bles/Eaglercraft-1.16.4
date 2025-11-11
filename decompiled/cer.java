import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class cer implements Predicate<ceh> {
   public static final Predicate<ceh> a = var0 -> true;
   private final cei<buo, ceh> b;
   private final Map<cfj<?>, Predicate<Object>> c = Maps.newHashMap();

   private cer(cei<buo, ceh> var1) {
      this.b = _snowman;
   }

   public static cer a(buo var0) {
      return new cer(_snowman.m());
   }

   public boolean a(@Nullable ceh var1) {
      if (_snowman != null && _snowman.b().equals(this.b.c())) {
         if (this.c.isEmpty()) {
            return true;
         } else {
            for (Entry<cfj<?>, Predicate<Object>> _snowman : this.c.entrySet()) {
               if (!this.a(_snowman, _snowman.getKey(), _snowman.getValue())) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   protected <T extends Comparable<T>> boolean a(ceh var1, cfj<T> var2, Predicate<Object> var3) {
      T _snowman = _snowman.c(_snowman);
      return _snowman.test(_snowman);
   }

   public <V extends Comparable<V>> cer a(cfj<V> var1, Predicate<Object> var2) {
      if (!this.b.d().contains(_snowman)) {
         throw new IllegalArgumentException(this.b + " cannot support property " + _snowman);
      } else {
         this.c.put(_snowman, _snowman);
         return this;
      }
   }
}
