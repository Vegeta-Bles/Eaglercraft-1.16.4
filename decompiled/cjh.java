import com.mojang.serialization.Codec;
import java.util.Random;

public class cjh extends cla<cmh> {
   public cjh(Codec<cmh> var1) {
      super(_snowman);
   }

   @Override
   protected boolean b() {
      return false;
   }

   protected boolean a(cfy var1, bsy var2, long var3, chx var5, int var6, int var7, bsv var8, brd var9, cmh var10) {
      return b(_snowman, _snowman, _snowman) >= 60;
   }

   @Override
   public cla.a<cmh> a() {
      return cjh.a::new;
   }

   private static int b(int var0, int var1, cfy var2) {
      Random _snowman = new Random((long)(_snowman + _snowman * 10387313));
      bzm _snowmanx = bzm.a(_snowman);
      int _snowmanxx = 5;
      int _snowmanxxx = 5;
      if (_snowmanx == bzm.b) {
         _snowmanxx = -5;
      } else if (_snowmanx == bzm.c) {
         _snowmanxx = -5;
         _snowmanxxx = -5;
      } else if (_snowmanx == bzm.d) {
         _snowmanxxx = -5;
      }

      int _snowmanxxxx = (_snowman << 4) + 7;
      int _snowmanxxxxx = (_snowman << 4) + 7;
      int _snowmanxxxxxx = _snowman.c(_snowmanxxxx, _snowmanxxxxx, chn.a.a);
      int _snowmanxxxxxxx = _snowman.c(_snowmanxxxx, _snowmanxxxxx + _snowmanxxx, chn.a.a);
      int _snowmanxxxxxxxx = _snowman.c(_snowmanxxxx + _snowmanxx, _snowmanxxxxx, chn.a.a);
      int _snowmanxxxxxxxxx = _snowman.c(_snowmanxxxx + _snowmanxx, _snowmanxxxxx + _snowmanxxx, chn.a.a);
      return Math.min(Math.min(_snowmanxxxxxx, _snowmanxxxxxxx), Math.min(_snowmanxxxxxxxx, _snowmanxxxxxxxxx));
   }

   public static class a extends crv<cmh> {
      public a(cla<cmh> var1, int var2, int var3, cra var4, int var5, long var6) {
         super(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      }

      public void a(gn var1, cfy var2, csw var3, int var4, int var5, bsv var6, cmh var7) {
         bzm _snowman = bzm.a(this.d);
         int _snowmanx = cjh.b(_snowman, _snowman, _snowman);
         if (_snowmanx >= 60) {
            fx _snowmanxx = new fx(_snowman * 16 + 8, _snowmanx, _snowman * 16 + 8);
            crd.a(_snowman, _snowmanxx, _snowman, this.b, this.d);
            this.b();
         }
      }
   }
}
