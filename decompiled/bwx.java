import java.util.Random;
import java.util.function.Supplier;

public class bwx extends buu implements buq {
   protected static final ddh a = buo.a(4.0, 0.0, 4.0, 12.0, 9.0, 12.0);
   private final Supplier<civ<cjq, ?>> b;

   protected bwx(ceg.c var1, Supplier<civ<cjq, ?>> var2) {
      super(_snowman);
      this.b = _snowman;
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      return a;
   }

   @Override
   protected boolean c(ceh var1, brc var2, fx var3) {
      return _snowman.a(aed.ao) || _snowman.a(bup.dT) || _snowman.a(bup.cN) || super.c(_snowman, _snowman, _snowman);
   }

   @Override
   public boolean a(brc var1, fx var2, ceh var3, boolean var4) {
      buo _snowman = ((cjq)this.b.get().f).f.b();
      buo _snowmanx = _snowman.d_(_snowman.c()).b();
      return _snowmanx == _snowman;
   }

   @Override
   public boolean a(brx var1, Random var2, fx var3, ceh var4) {
      return (double)_snowman.nextFloat() < 0.4;
   }

   @Override
   public void a(aag var1, Random var2, fx var3, ceh var4) {
      this.b.get().a(_snowman, _snowman.i().g(), _snowman, _snowman);
   }
}
