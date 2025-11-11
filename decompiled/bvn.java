import java.util.Random;
import javax.annotation.Nullable;

public class bvn extends buo {
   private final buo a;

   public bvn(buo var1, ceg.c var2) {
      super(_snowman);
      this.a = _snowman;
   }

   @Override
   public void a(ceh var1, aag var2, fx var3, Random var4) {
      if (!this.a(_snowman, _snowman)) {
         _snowman.a(_snowman, this.a.n(), 2);
      }
   }

   @Override
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      if (!this.a(_snowman, _snowman)) {
         _snowman.J().a(_snowman, this, 60 + _snowman.u_().nextInt(40));
      }

      return super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   protected boolean a(brc var1, fx var2) {
      for (gc _snowman : gc.values()) {
         cux _snowmanx = _snowman.b(_snowman.a(_snowman));
         if (_snowmanx.a(aef.b)) {
            return true;
         }
      }

      return false;
   }

   @Nullable
   @Override
   public ceh a(bny var1) {
      if (!this.a(_snowman.p(), _snowman.a())) {
         _snowman.p().J().a(_snowman.a(), this, 60 + _snowman.p().u_().nextInt(40));
      }

      return this.n();
   }
}
