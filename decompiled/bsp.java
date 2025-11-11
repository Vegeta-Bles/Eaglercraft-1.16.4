import java.util.Comparator;

public class bsp<T> {
   private static long d;
   private final T e;
   public final fx a;
   public final long b;
   public final bsq c;
   private final long f;

   public bsp(fx var1, T var2) {
      this(_snowman, _snowman, 0L, bsq.d);
   }

   public bsp(fx var1, T var2, long var3, bsq var5) {
      this.f = d++;
      this.a = _snowman.h();
      this.e = _snowman;
      this.b = _snowman;
      this.c = _snowman;
   }

   @Override
   public boolean equals(Object var1) {
      if (!(_snowman instanceof bsp)) {
         return false;
      } else {
         bsp<?> _snowman = (bsp<?>)_snowman;
         return this.a.equals(_snowman.a) && this.e == _snowman.e;
      }
   }

   @Override
   public int hashCode() {
      return this.a.hashCode();
   }

   public static <T> Comparator<bsp<T>> a() {
      return Comparator.<bsp<T>>comparingLong(var0 -> var0.b).thenComparing(var0 -> var0.c).thenComparingLong(var0 -> var0.f);
   }

   @Override
   public String toString() {
      return this.e + ": " + this.a + ", " + this.b + ", " + this.c + ", " + this.f;
   }

   public T b() {
      return this.e;
   }
}
