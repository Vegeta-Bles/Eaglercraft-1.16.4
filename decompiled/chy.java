import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;

public class chy extends cig<cmk> {
   private final float[] m = new float[1024];

   public chy(Codec<cmk> var1) {
      super(_snowman, 256);
   }

   public boolean a(Random var1, int var2, int var3, cmk var4) {
      return _snowman.nextFloat() <= _snowman.c;
   }

   public boolean a(cfw var1, Function<fx, bsv> var2, Random var3, int var4, int var5, int var6, int var7, int var8, BitSet var9, cmk var10) {
      int _snowman = (this.d() * 2 - 1) * 16;
      double _snowmanx = (double)(_snowman * 16 + _snowman.nextInt(16));
      double _snowmanxx = (double)(_snowman.nextInt(_snowman.nextInt(40) + 8) + 20);
      double _snowmanxxx = (double)(_snowman * 16 + _snowman.nextInt(16));
      float _snowmanxxxx = _snowman.nextFloat() * (float) (Math.PI * 2);
      float _snowmanxxxxx = (_snowman.nextFloat() - 0.5F) * 2.0F / 8.0F;
      double _snowmanxxxxxx = 3.0;
      float _snowmanxxxxxxx = (_snowman.nextFloat() * 2.0F + _snowman.nextFloat()) * 2.0F;
      int _snowmanxxxxxxxx = _snowman - _snowman.nextInt(_snowman / 4);
      int _snowmanxxxxxxxxx = 0;
      this.a(_snowman, _snowman, _snowman.nextLong(), _snowman, _snowman, _snowman, _snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxxxxx, _snowmanxxxx, _snowmanxxxxx, 0, _snowmanxxxxxxxx, 3.0, _snowman);
      return true;
   }

   private void a(
      cfw var1,
      Function<fx, bsv> var2,
      long var3,
      int var5,
      int var6,
      int var7,
      double var8,
      double var10,
      double var12,
      float var14,
      float var15,
      float var16,
      int var17,
      int var18,
      double var19,
      BitSet var21
   ) {
      Random _snowman = new Random(_snowman);
      float _snowmanx = 1.0F;

      for (int _snowmanxx = 0; _snowmanxx < 256; _snowmanxx++) {
         if (_snowmanxx == 0 || _snowman.nextInt(3) == 0) {
            _snowmanx = 1.0F + _snowman.nextFloat() * _snowman.nextFloat();
         }

         this.m[_snowmanxx] = _snowmanx * _snowmanx;
      }

      float _snowmanxx = 0.0F;
      float _snowmanxxx = 0.0F;

      for (int _snowmanxxxx = _snowman; _snowmanxxxx < _snowman; _snowmanxxxx++) {
         double _snowmanxxxxx = 1.5 + (double)(afm.a((float)_snowmanxxxx * (float) Math.PI / (float)_snowman) * _snowman);
         double _snowmanxxxxxx = _snowmanxxxxx * _snowman;
         _snowmanxxxxx *= (double)_snowman.nextFloat() * 0.25 + 0.75;
         _snowmanxxxxxx *= (double)_snowman.nextFloat() * 0.25 + 0.75;
         float _snowmanxxxxxxx = afm.b(_snowman);
         float _snowmanxxxxxxxx = afm.a(_snowman);
         _snowman += (double)(afm.b(_snowman) * _snowmanxxxxxxx);
         _snowman += (double)_snowmanxxxxxxxx;
         _snowman += (double)(afm.a(_snowman) * _snowmanxxxxxxx);
         _snowman *= 0.7F;
         _snowman += _snowmanxxx * 0.05F;
         _snowman += _snowmanxx * 0.05F;
         _snowmanxxx *= 0.8F;
         _snowmanxx *= 0.5F;
         _snowmanxxx += (_snowman.nextFloat() - _snowman.nextFloat()) * _snowman.nextFloat() * 2.0F;
         _snowmanxx += (_snowman.nextFloat() - _snowman.nextFloat()) * _snowman.nextFloat() * 4.0F;
         if (_snowman.nextInt(4) != 0) {
            if (!this.a(_snowman, _snowman, _snowman, _snowman, _snowmanxxxx, _snowman, _snowman)) {
               return;
            }

            this.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowmanxxxxx, _snowmanxxxxxx, _snowman);
         }
      }
   }

   @Override
   protected boolean a(double var1, double var3, double var5, int var7) {
      return (_snowman * _snowman + _snowman * _snowman) * (double)this.m[_snowman - 1] + _snowman * _snowman / 6.0 >= 1.0;
   }
}
