import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.Nullable;

public class adz<T> implements Iterable<adx<T>> {
   private final gm<T> a;
   private final Map<T, adx<T>> b = new IdentityHashMap<>();
   @Nullable
   private nr c;

   public adz(gm<T> var1) {
      this.a = _snowman;
   }

   public boolean a(T var1) {
      return this.b.containsKey(_snowman);
   }

   public adx<T> a(T var1, ady var2) {
      return this.b.computeIfAbsent(_snowman, var2x -> new adx<>(this, (T)var2x, _snowman));
   }

   public gm<T> a() {
      return this.a;
   }

   @Override
   public Iterator<adx<T>> iterator() {
      return this.b.values().iterator();
   }

   public adx<T> b(T var1) {
      return this.a(_snowman, ady.b);
   }

   public String c() {
      return "stat_type." + gm.ag.b(this).toString().replace(':', '.');
   }

   public nr d() {
      if (this.c == null) {
         this.c = new of(this.c());
      }

      return this.c;
   }
}
