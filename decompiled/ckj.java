import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.List;

public class ckj extends cjx {
   private static final List<btg.c> u = ImmutableList.of(new btg.c(aqe.ak, 1, 1, 1));

   public ckj(Codec<cmc> var1) {
      super(_snowman, 0, true, true);
   }

   @Override
   public List<btg.c> c() {
      return u;
   }

   protected boolean a(cfy var1, bsy var2, long var3, chx var5, int var6, int var7, bsv var8, brd var9, cmc var10) {
      int _snowman = _snowman >> 4;
      int _snowmanx = _snowman >> 4;
      _snowman.setSeed((long)(_snowman ^ _snowmanx << 4) ^ _snowman);
      _snowman.nextInt();
      return _snowman.nextInt(5) != 0 ? false : !this.a(_snowman, _snowman, _snowman, _snowman, _snowman);
   }

   private boolean a(cfy var1, long var2, chx var4, int var5, int var6) {
      cmy _snowman = _snowman.b().a(cla.q);
      if (_snowman == null) {
         return false;
      } else {
         for (int _snowmanx = _snowman - 10; _snowmanx <= _snowman + 10; _snowmanx++) {
            for (int _snowmanxx = _snowman - 10; _snowmanxx <= _snowman + 10; _snowmanxx++) {
               brd _snowmanxxx = cla.q.a(_snowman, _snowman, _snowman, _snowmanx, _snowmanxx);
               if (_snowmanx == _snowmanxxx.b && _snowmanxx == _snowmanxxx.c) {
                  return true;
               }
            }
         }

         return false;
      }
   }
}
