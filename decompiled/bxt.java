import java.util.Random;
import javax.annotation.Nullable;

public class bxt extends bxh implements byc {
   protected static final ddh e = buo.a(0.0, 0.0, 0.0, 16.0, 9.0, 16.0);

   protected bxt(ceg.c var1) {
      super(_snowman, gc.b, e, true, 0.14);
   }

   @Override
   protected boolean h(ceh var1) {
      return _snowman.a(bup.A);
   }

   @Override
   protected buo d() {
      return bup.kd;
   }

   @Override
   protected boolean c(buo var1) {
      return _snowman != bup.iJ;
   }

   @Override
   public boolean a(brc var1, fx var2, ceh var3, cuw var4) {
      return false;
   }

   @Override
   public boolean a(bry var1, fx var2, ceh var3, cux var4) {
      return false;
   }

   @Override
   protected int a(Random var1) {
      return 1;
   }

   @Nullable
   @Override
   public ceh a(bny var1) {
      cux _snowman = _snowman.p().b(_snowman.a());
      return _snowman.a(aef.b) && _snowman.e() == 8 ? super.a(_snowman) : null;
   }

   @Override
   public cux d(ceh var1) {
      return cuy.c.a(false);
   }
}
