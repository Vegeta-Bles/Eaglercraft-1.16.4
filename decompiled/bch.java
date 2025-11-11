import java.lang.reflect.Constructor;
import java.util.Arrays;

public class bch<T extends bcb> {
   private static bch<?>[] l = new bch[0];
   public static final bch<bbx> a = a(bbx.class, "HoldingPattern");
   public static final bch<bcf> b = a(bcf.class, "StrafePlayer");
   public static final bch<bbz> c = a(bbz.class, "LandingApproach");
   public static final bch<bca> d = a(bca.class, "Landing");
   public static final bch<bcg> e = a(bcg.class, "Takeoff");
   public static final bch<bcd> f = a(bcd.class, "SittingFlaming");
   public static final bch<bce> g = a(bce.class, "SittingScanning");
   public static final bch<bcc> h = a(bcc.class, "SittingAttacking");
   public static final bch<bbv> i = a(bbv.class, "ChargingPlayer");
   public static final bch<bbw> j = a(bbw.class, "Dying");
   public static final bch<bby> k = a(bby.class, "Hover");
   private final Class<? extends bcb> m;
   private final int n;
   private final String o;

   private bch(int var1, Class<? extends bcb> var2, String var3) {
      this.n = _snowman;
      this.m = _snowman;
      this.o = _snowman;
   }

   public bcb a(bbr var1) {
      try {
         Constructor<? extends bcb> _snowman = this.a();
         return _snowman.newInstance(_snowman);
      } catch (Exception var3) {
         throw new Error(var3);
      }
   }

   protected Constructor<? extends bcb> a() throws NoSuchMethodException {
      return this.m.getConstructor(bbr.class);
   }

   public int b() {
      return this.n;
   }

   @Override
   public String toString() {
      return this.o + " (#" + this.n + ")";
   }

   public static bch<?> a(int var0) {
      return _snowman >= 0 && _snowman < l.length ? l[_snowman] : a;
   }

   public static int c() {
      return l.length;
   }

   private static <T extends bcb> bch<T> a(Class<T> var0, String var1) {
      bch<T> _snowman = new bch<>(l.length, _snowman, _snowman);
      l = Arrays.copyOf(l, l.length + 1);
      l[_snowman.b()] = _snowman;
      return _snowman;
   }
}
