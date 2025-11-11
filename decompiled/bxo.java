import java.util.Random;
import javax.annotation.Nullable;

public class bxo extends bxi {
   public bxo(ceg.c var1) {
      super(_snowman);
   }

   @Override
   public void a(brx var1, bfw var2, fx var3, ceh var4, @Nullable ccj var5, bmb var6) {
      super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      if (bpu.a(bpw.u, _snowman) == 0) {
         if (_snowman.k().d()) {
            _snowman.a(_snowman, false);
            return;
         }

         cva _snowman = _snowman.d_(_snowman.c()).c();
         if (_snowman.c() || _snowman.a()) {
            _snowman.a(_snowman, bup.A.n());
         }
      }
   }

   @Override
   public void b(ceh var1, aag var2, fx var3, Random var4) {
      if (_snowman.a(bsf.b, _snowman) > 11 - _snowman.b(_snowman, _snowman)) {
         this.d(_snowman, _snowman, _snowman);
      }
   }

   protected void d(ceh var1, brx var2, fx var3) {
      if (_snowman.k().d()) {
         _snowman.a(_snowman, false);
      } else {
         _snowman.a(_snowman, bup.A.n());
         _snowman.a(_snowman, bup.A, _snowman);
      }
   }

   @Override
   public cvc f(ceh var1) {
      return cvc.a;
   }
}
