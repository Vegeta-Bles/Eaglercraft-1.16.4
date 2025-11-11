import java.util.Random;
import javax.annotation.Nullable;

public class bze extends buo {
   public static final cey a = bzf.a;

   public bze(ceg.c var1) {
      super(_snowman);
      this.j(this.n().a(a, Boolean.valueOf(false)));
   }

   @Nullable
   @Override
   public ceh a(bny var1) {
      return this.n().a(a, Boolean.valueOf(_snowman.p().r(_snowman.a())));
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, buo var4, fx var5, boolean var6) {
      if (!_snowman.v) {
         boolean _snowman = _snowman.c(a);
         if (_snowman != _snowman.r(_snowman)) {
            if (_snowman) {
               _snowman.J().a(_snowman, this, 4);
            } else {
               _snowman.a(_snowman, _snowman.a(a), 2);
            }
         }
      }
   }

   @Override
   public void a(ceh var1, aag var2, fx var3, Random var4) {
      if (_snowman.c(a) && !_snowman.r(_snowman)) {
         _snowman.a(_snowman, _snowman.a(a), 2);
      }
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(a);
   }
}
