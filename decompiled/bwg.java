import java.util.Random;
import javax.annotation.Nullable;

public class bwg extends bud {
   protected static final ddh a = buo.a(0.0, 0.0, 0.0, 16.0, 12.0, 16.0);

   protected bwg(ceg.c var1) {
      super(_snowman);
   }

   @Override
   public boolean c_(ceh var1) {
      return true;
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      return a;
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, Random var4) {
      super.a(_snowman, _snowman, _snowman, _snowman);

      for (int _snowman = -2; _snowman <= 2; _snowman++) {
         for (int _snowmanx = -2; _snowmanx <= 2; _snowmanx++) {
            if (_snowman > -2 && _snowman < 2 && _snowmanx == -1) {
               _snowmanx = 2;
            }

            if (_snowman.nextInt(16) == 0) {
               for (int _snowmanxx = 0; _snowmanxx <= 1; _snowmanxx++) {
                  fx _snowmanxxx = _snowman.b(_snowman, _snowmanxx, _snowmanx);
                  if (_snowman.d_(_snowmanxxx).a(bup.bI)) {
                     if (!_snowman.w(_snowman.b(_snowman / 2, 0, _snowmanx / 2))) {
                        break;
                     }

                     _snowman.a(
                        hh.s,
                        (double)_snowman.u() + 0.5,
                        (double)_snowman.v() + 2.0,
                        (double)_snowman.w() + 0.5,
                        (double)((float)_snowman + _snowman.nextFloat()) - 0.5,
                        (double)((float)_snowmanxx - _snowman.nextFloat() - 1.0F),
                        (double)((float)_snowmanx + _snowman.nextFloat()) - 0.5
                     );
                  }
               }
            }
         }
      }
   }

   @Override
   public bzh b(ceh var1) {
      return bzh.c;
   }

   @Override
   public ccj a(brc var1) {
      return new ccu();
   }

   @Override
   public aou a(ceh var1, brx var2, fx var3, bfw var4, aot var5, dcj var6) {
      if (_snowman.v) {
         return aou.a;
      } else {
         _snowman.a(_snowman.b(_snowman, _snowman));
         return aou.b;
      }
   }

   @Nullable
   @Override
   public aox b(ceh var1, brx var2, fx var3) {
      ccj _snowman = _snowman.c(_snowman);
      if (_snowman instanceof ccu) {
         nr _snowmanx = ((aoy)_snowman).d();
         return new apb((var2x, var3x, var4x) -> new bis(var2x, var3x, bim.a(_snowman, _snowman)), _snowmanx);
      } else {
         return null;
      }
   }

   @Override
   public void a(brx var1, fx var2, ceh var3, aqm var4, bmb var5) {
      if (_snowman.t()) {
         ccj _snowman = _snowman.c(_snowman);
         if (_snowman instanceof ccu) {
            ((ccu)_snowman).a(_snowman.r());
         }
      }
   }

   @Override
   public boolean a(ceh var1, brc var2, fx var3, cxe var4) {
      return false;
   }
}
