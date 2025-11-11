import javax.annotation.Nullable;

public class eat {
   protected final eae a;
   protected final brx b;
   protected int c;
   protected int d;
   protected int e;
   public ecu.c[] f;

   public eat(ecu var1, brx var2, int var3, eae var4) {
      this.a = _snowman;
      this.b = _snowman;
      this.a(_snowman);
      this.a(_snowman);
   }

   protected void a(ecu var1) {
      int _snowman = this.d * this.c * this.e;
      this.f = new ecu.c[_snowman];

      for (int _snowmanx = 0; _snowmanx < this.d; _snowmanx++) {
         for (int _snowmanxx = 0; _snowmanxx < this.c; _snowmanxx++) {
            for (int _snowmanxxx = 0; _snowmanxxx < this.e; _snowmanxxx++) {
               int _snowmanxxxx = this.a(_snowmanx, _snowmanxx, _snowmanxxx);
               this.f[_snowmanxxxx] = _snowman.new c();
               this.f[_snowmanxxxx].a(_snowmanx * 16, _snowmanxx * 16, _snowmanxxx * 16);
            }
         }
      }
   }

   public void a() {
      for (ecu.c _snowman : this.f) {
         _snowman.d();
      }
   }

   private int a(int var1, int var2, int var3) {
      return (_snowman * this.c + _snowman) * this.d + _snowman;
   }

   protected void a(int var1) {
      int _snowman = _snowman * 2 + 1;
      this.d = _snowman;
      this.c = 16;
      this.e = _snowman;
   }

   public void a(double var1, double var3) {
      int _snowman = afm.c(_snowman);
      int _snowmanx = afm.c(_snowman);

      for (int _snowmanxx = 0; _snowmanxx < this.d; _snowmanxx++) {
         int _snowmanxxx = this.d * 16;
         int _snowmanxxxx = _snowman - 8 - _snowmanxxx / 2;
         int _snowmanxxxxx = _snowmanxxxx + Math.floorMod(_snowmanxx * 16 - _snowmanxxxx, _snowmanxxx);

         for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < this.e; _snowmanxxxxxx++) {
            int _snowmanxxxxxxx = this.e * 16;
            int _snowmanxxxxxxxx = _snowmanx - 8 - _snowmanxxxxxxx / 2;
            int _snowmanxxxxxxxxx = _snowmanxxxxxxxx + Math.floorMod(_snowmanxxxxxx * 16 - _snowmanxxxxxxxx, _snowmanxxxxxxx);

            for (int _snowmanxxxxxxxxxx = 0; _snowmanxxxxxxxxxx < this.c; _snowmanxxxxxxxxxx++) {
               int _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxx * 16;
               ecu.c _snowmanxxxxxxxxxxxx = this.f[this.a(_snowmanxx, _snowmanxxxxxxxxxx, _snowmanxxxxxx)];
               _snowmanxxxxxxxxxxxx.a(_snowmanxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxx);
            }
         }
      }
   }

   public void a(int var1, int var2, int var3, boolean var4) {
      int _snowman = Math.floorMod(_snowman, this.d);
      int _snowmanx = Math.floorMod(_snowman, this.c);
      int _snowmanxx = Math.floorMod(_snowman, this.e);
      ecu.c _snowmanxxx = this.f[this.a(_snowman, _snowmanx, _snowmanxx)];
      _snowmanxxx.a(_snowman);
   }

   @Nullable
   protected ecu.c a(fx var1) {
      int _snowman = afm.a(_snowman.u(), 16);
      int _snowmanx = afm.a(_snowman.v(), 16);
      int _snowmanxx = afm.a(_snowman.w(), 16);
      if (_snowmanx >= 0 && _snowmanx < this.c) {
         _snowman = afm.b(_snowman, this.d);
         _snowmanxx = afm.b(_snowmanxx, this.e);
         return this.f[this.a(_snowman, _snowmanx, _snowmanxx)];
      } else {
         return null;
      }
   }
}
