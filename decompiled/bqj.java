import java.util.Random;
import java.util.Map.Entry;

public class bqj extends bps {
   public bqj(bps.a var1, aqf... var2) {
      super(_snowman, bpt.d, _snowman);
   }

   @Override
   public int a(int var1) {
      return 10 + 20 * (_snowman - 1);
   }

   @Override
   public int b(int var1) {
      return super.a(_snowman) + 50;
   }

   @Override
   public int a() {
      return 3;
   }

   @Override
   public boolean a(bmb var1) {
      return _snowman.b() instanceof bjy ? true : super.a(_snowman);
   }

   @Override
   public void b(aqm var1, aqa var2, int var3) {
      Random _snowman = _snowman.cY();
      Entry<aqf, bmb> _snowmanx = bpu.b(bpw.h, _snowman);
      if (a(_snowman, _snowman)) {
         if (_snowman != null) {
            _snowman.a(apk.a((aqa)_snowman), (float)b(_snowman, _snowman));
         }

         if (_snowmanx != null) {
            _snowmanx.getValue().a(2, _snowman, var1x -> var1x.c(_snowman.getKey()));
         }
      }
   }

   public static boolean a(int var0, Random var1) {
      return _snowman <= 0 ? false : _snowman.nextFloat() < 0.15F * (float)_snowman;
   }

   public static int b(int var0, Random var1) {
      return _snowman > 10 ? _snowman - 10 : 1 + _snowman.nextInt(4);
   }
}
