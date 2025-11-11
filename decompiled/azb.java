import java.util.Random;
import java.util.Set;

public abstract class azb<E extends aqm> {
   private static final Random a = new Random();
   private static final azg b = new azg().a(16.0).b().d();
   private static final azg c = new azg().a(16.0).b().d().e();
   private final int d;
   private long e;

   public azb(int var1) {
      this.d = _snowman;
      this.e = (long)a.nextInt(_snowman);
   }

   public azb() {
      this(20);
   }

   public final void b(aag var1, E var2) {
      if (--this.e <= 0L) {
         this.e = (long)this.d;
         this.a(_snowman, _snowman);
      }
   }

   protected abstract void a(aag var1, E var2);

   public abstract Set<ayd<?>> a();

   protected static boolean a(aqm var0, aqm var1) {
      return _snowman.cJ().b(ayd.o, _snowman) ? c.a(_snowman, _snowman) : b.a(_snowman, _snowman);
   }
}
