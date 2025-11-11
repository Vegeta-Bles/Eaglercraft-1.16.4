import java.util.Random;

public final class ctz {
   private final byte[] d;
   public final double a;
   public final double b;
   public final double c;

   public ctz(Random var1) {
      this.a = _snowman.nextDouble() * 256.0;
      this.b = _snowman.nextDouble() * 256.0;
      this.c = _snowman.nextDouble() * 256.0;
      this.d = new byte[256];

      for (int _snowman = 0; _snowman < 256; _snowman++) {
         this.d[_snowman] = (byte)_snowman;
      }

      for (int _snowman = 0; _snowman < 256; _snowman++) {
         int _snowmanx = _snowman.nextInt(256 - _snowman);
         byte _snowmanxx = this.d[_snowman];
         this.d[_snowman] = this.d[_snowman + _snowmanx];
         this.d[_snowman + _snowmanx] = _snowmanxx;
      }
   }

   public double a(double var1, double var3, double var5, double var7, double var9) {
      double _snowman = _snowman + this.a;
      double _snowmanx = _snowman + this.b;
      double _snowmanxx = _snowman + this.c;
      int _snowmanxxx = afm.c(_snowman);
      int _snowmanxxxx = afm.c(_snowmanx);
      int _snowmanxxxxx = afm.c(_snowmanxx);
      double _snowmanxxxxxx = _snowman - (double)_snowmanxxx;
      double _snowmanxxxxxxx = _snowmanx - (double)_snowmanxxxx;
      double _snowmanxxxxxxxx = _snowmanxx - (double)_snowmanxxxxx;
      double _snowmanxxxxxxxxx = afm.j(_snowmanxxxxxx);
      double _snowmanxxxxxxxxxx = afm.j(_snowmanxxxxxxx);
      double _snowmanxxxxxxxxxxx = afm.j(_snowmanxxxxxxxx);
      double _snowmanxxxxxxxxxxxx;
      if (_snowman != 0.0) {
         double _snowmanxxxxxxxxxxxxx = Math.min(_snowman, _snowmanxxxxxxx);
         _snowmanxxxxxxxxxxxx = (double)afm.c(_snowmanxxxxxxxxxxxxx / _snowman) * _snowman;
      } else {
         _snowmanxxxxxxxxxxxx = 0.0;
      }

      return this.a(_snowmanxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx - _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx);
   }

   private static double a(int var0, double var1, double var3, double var5) {
      int _snowman = _snowman & 15;
      return cud.a(cud.a[_snowman], _snowman, _snowman, _snowman);
   }

   private int a(int var1) {
      return this.d[_snowman & 0xFF] & 0xFF;
   }

   public double a(int var1, int var2, int var3, double var4, double var6, double var8, double var10, double var12, double var14) {
      int _snowman = this.a(_snowman) + _snowman;
      int _snowmanx = this.a(_snowman) + _snowman;
      int _snowmanxx = this.a(_snowman + 1) + _snowman;
      int _snowmanxxx = this.a(_snowman + 1) + _snowman;
      int _snowmanxxxx = this.a(_snowmanxxx) + _snowman;
      int _snowmanxxxxx = this.a(_snowmanxxx + 1) + _snowman;
      double _snowmanxxxxxx = a(this.a(_snowmanx), _snowman, _snowman, _snowman);
      double _snowmanxxxxxxx = a(this.a(_snowmanxxxx), _snowman - 1.0, _snowman, _snowman);
      double _snowmanxxxxxxxx = a(this.a(_snowmanxx), _snowman, _snowman - 1.0, _snowman);
      double _snowmanxxxxxxxxx = a(this.a(_snowmanxxxxx), _snowman - 1.0, _snowman - 1.0, _snowman);
      double _snowmanxxxxxxxxxx = a(this.a(_snowmanx + 1), _snowman, _snowman, _snowman - 1.0);
      double _snowmanxxxxxxxxxxx = a(this.a(_snowmanxxxx + 1), _snowman - 1.0, _snowman, _snowman - 1.0);
      double _snowmanxxxxxxxxxxxx = a(this.a(_snowmanxx + 1), _snowman, _snowman - 1.0, _snowman - 1.0);
      double _snowmanxxxxxxxxxxxxx = a(this.a(_snowmanxxxxx + 1), _snowman - 1.0, _snowman - 1.0, _snowman - 1.0);
      return afm.a(_snowman, _snowman, _snowman, _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx);
   }
}
