import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;

public class cia extends cig<cmk> {
   public cia(Codec<cmk> var1, int var2) {
      super(_snowman, _snowman);
   }

   public boolean a(Random var1, int var2, int var3, cmk var4) {
      return _snowman.nextFloat() <= _snowman.c;
   }

   public boolean a(cfw var1, Function<fx, bsv> var2, Random var3, int var4, int var5, int var6, int var7, int var8, BitSet var9, cmk var10) {
      int _snowman = (this.d() * 2 - 1) * 16;
      int _snowmanx = _snowman.nextInt(_snowman.nextInt(_snowman.nextInt(this.a()) + 1) + 1);

      for (int _snowmanxx = 0; _snowmanxx < _snowmanx; _snowmanxx++) {
         double _snowmanxxx = (double)(_snowman * 16 + _snowman.nextInt(16));
         double _snowmanxxxx = (double)this.b(_snowman);
         double _snowmanxxxxx = (double)(_snowman * 16 + _snowman.nextInt(16));
         int _snowmanxxxxxx = 1;
         if (_snowman.nextInt(4) == 0) {
            double _snowmanxxxxxxx = 0.5;
            float _snowmanxxxxxxxx = 1.0F + _snowman.nextFloat() * 6.0F;
            this.a(_snowman, _snowman, _snowman.nextLong(), _snowman, _snowman, _snowman, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxxxx, 0.5, _snowman);
            _snowmanxxxxxx += _snowman.nextInt(4);
         }

         for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < _snowmanxxxxxx; _snowmanxxxxxxx++) {
            float _snowmanxxxxxxxx = _snowman.nextFloat() * (float) (Math.PI * 2);
            float _snowmanxxxxxxxxx = (_snowman.nextFloat() - 0.5F) / 4.0F;
            float _snowmanxxxxxxxxxx = this.a(_snowman);
            int _snowmanxxxxxxxxxxx = _snowman - _snowman.nextInt(_snowman / 4);
            int _snowmanxxxxxxxxxxxx = 0;
            this.a(_snowman, _snowman, _snowman.nextLong(), _snowman, _snowman, _snowman, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, 0, _snowmanxxxxxxxxxxx, this.b(), _snowman);
         }
      }

      return true;
   }

   protected int a() {
      return 15;
   }

   protected float a(Random var1) {
      float _snowman = _snowman.nextFloat() * 2.0F + _snowman.nextFloat();
      if (_snowman.nextInt(10) == 0) {
         _snowman *= _snowman.nextFloat() * _snowman.nextFloat() * 3.0F + 1.0F;
      }

      return _snowman;
   }

   protected double b() {
      return 1.0;
   }

   protected int b(Random var1) {
      return _snowman.nextInt(_snowman.nextInt(120) + 8);
   }

   protected void a(
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
      double var15,
      BitSet var17
   ) {
      double _snowman = 1.5 + (double)(afm.a((float) (Math.PI / 2)) * _snowman);
      double _snowmanx = _snowman * _snowman;
      this.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman + 1.0, _snowman, _snowman, _snowman, _snowmanx, _snowman);
   }

   protected void a(
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
      int _snowmanx = _snowman.nextInt(_snowman / 2) + _snowman / 4;
      boolean _snowmanxx = _snowman.nextInt(6) == 0;
      float _snowmanxxx = 0.0F;
      float _snowmanxxxx = 0.0F;

      for (int _snowmanxxxxx = _snowman; _snowmanxxxxx < _snowman; _snowmanxxxxx++) {
         double _snowmanxxxxxx = 1.5 + (double)(afm.a((float) Math.PI * (float)_snowmanxxxxx / (float)_snowman) * _snowman);
         double _snowmanxxxxxxx = _snowmanxxxxxx * _snowman;
         float _snowmanxxxxxxxx = afm.b(_snowman);
         _snowman += (double)(afm.b(_snowman) * _snowmanxxxxxxxx);
         _snowman += (double)afm.a(_snowman);
         _snowman += (double)(afm.a(_snowman) * _snowmanxxxxxxxx);
         _snowman *= _snowmanxx ? 0.92F : 0.7F;
         _snowman += _snowmanxxxx * 0.1F;
         _snowman += _snowmanxxx * 0.1F;
         _snowmanxxxx *= 0.9F;
         _snowmanxxx *= 0.75F;
         _snowmanxxxx += (_snowman.nextFloat() - _snowman.nextFloat()) * _snowman.nextFloat() * 2.0F;
         _snowmanxxx += (_snowman.nextFloat() - _snowman.nextFloat()) * _snowman.nextFloat() * 4.0F;
         if (_snowmanxxxxx == _snowmanx && _snowman > 1.0F) {
            this.a(_snowman, _snowman, _snowman.nextLong(), _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman.nextFloat() * 0.5F + 0.5F, _snowman - (float) (Math.PI / 2), _snowman / 3.0F, _snowmanxxxxx, _snowman, 1.0, _snowman);
            this.a(_snowman, _snowman, _snowman.nextLong(), _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman.nextFloat() * 0.5F + 0.5F, _snowman + (float) (Math.PI / 2), _snowman / 3.0F, _snowmanxxxxx, _snowman, 1.0, _snowman);
            return;
         }

         if (_snowman.nextInt(4) != 0) {
            if (!this.a(_snowman, _snowman, _snowman, _snowman, _snowmanxxxxx, _snowman, _snowman)) {
               return;
            }

            this.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowmanxxxxxx, _snowmanxxxxxxx, _snowman);
         }
      }
   }

   @Override
   protected boolean a(double var1, double var3, double var5, int var7) {
      return _snowman <= -0.7 || _snowman * _snowman + _snowman * _snowman + _snowman * _snowman >= 1.0;
   }
}
