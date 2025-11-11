import java.util.Map;
import java.util.Map.Entry;

public abstract class arv<E extends aqm> {
   protected final Map<ayd<?>, aye> a;
   private arv.a b = arv.a.a;
   private long c;
   private final int d;
   private final int e;

   public arv(Map<ayd<?>, aye> var1) {
      this(_snowman, 60);
   }

   public arv(Map<ayd<?>, aye> var1, int var2) {
      this(_snowman, _snowman, _snowman);
   }

   public arv(Map<ayd<?>, aye> var1, int var2, int var3) {
      this.d = _snowman;
      this.e = _snowman;
      this.a = _snowman;
   }

   public arv.a a() {
      return this.b;
   }

   public final boolean e(aag var1, E var2, long var3) {
      if (this.a(_snowman) && this.a(_snowman, _snowman)) {
         this.b = arv.a.b;
         int _snowman = this.d + _snowman.u_().nextInt(this.e + 1 - this.d);
         this.c = _snowman + (long)_snowman;
         this.a(_snowman, _snowman, _snowman);
         return true;
      } else {
         return false;
      }
   }

   protected void a(aag var1, E var2, long var3) {
   }

   public final void f(aag var1, E var2, long var3) {
      if (!this.a(_snowman) && this.b(_snowman, _snowman, _snowman)) {
         this.d(_snowman, _snowman, _snowman);
      } else {
         this.g(_snowman, _snowman, _snowman);
      }
   }

   protected void d(aag var1, E var2, long var3) {
   }

   public final void g(aag var1, E var2, long var3) {
      this.b = arv.a.a;
      this.c(_snowman, _snowman, _snowman);
   }

   protected void c(aag var1, E var2, long var3) {
   }

   protected boolean b(aag var1, E var2, long var3) {
      return false;
   }

   protected boolean a(long var1) {
      return _snowman > this.c;
   }

   protected boolean a(aag var1, E var2) {
      return true;
   }

   @Override
   public String toString() {
      return this.getClass().getSimpleName();
   }

   private boolean a(E var1) {
      for (Entry<ayd<?>, aye> _snowman : this.a.entrySet()) {
         ayd<?> _snowmanx = _snowman.getKey();
         aye _snowmanxx = _snowman.getValue();
         if (!_snowman.cJ().a(_snowmanx, _snowmanxx)) {
            return false;
         }
      }

      return true;
   }

   public static enum a {
      a,
      b;

      private a() {
      }
   }
}
