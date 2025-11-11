public class ccn extends cdd implements cdc, cdm {
   private gj<bmb> i = gj.a(27, bmb.b);
   protected float a;
   protected float b;
   protected int c;
   private int j;

   protected ccn(cck<?> var1) {
      super(_snowman);
   }

   public ccn() {
      this(cck.b);
   }

   @Override
   public int Z_() {
      return 27;
   }

   @Override
   protected nr g() {
      return new of("container.chest");
   }

   @Override
   public void a(ceh var1, md var2) {
      super.a(_snowman, _snowman);
      this.i = gj.a(this.Z_(), bmb.b);
      if (!this.b(_snowman)) {
         aoo.b(_snowman, this.i);
      }
   }

   @Override
   public md a(md var1) {
      super.a(_snowman);
      if (!this.c(_snowman)) {
         aoo.a(_snowman, this.i);
      }

      return _snowman;
   }

   @Override
   public void aj_() {
      int _snowman = this.e.u();
      int _snowmanx = this.e.v();
      int _snowmanxx = this.e.w();
      this.j++;
      this.c = a(this.d, this, this.j, _snowman, _snowmanx, _snowmanxx, this.c);
      this.b = this.a;
      float _snowmanxxx = 0.1F;
      if (this.c > 0 && this.a == 0.0F) {
         this.a(adq.bG);
      }

      if (this.c == 0 && this.a > 0.0F || this.c > 0 && this.a < 1.0F) {
         float _snowmanxxxx = this.a;
         if (this.c > 0) {
            this.a += 0.1F;
         } else {
            this.a -= 0.1F;
         }

         if (this.a > 1.0F) {
            this.a = 1.0F;
         }

         float _snowmanxxxxx = 0.5F;
         if (this.a < 0.5F && _snowmanxxxx >= 0.5F) {
            this.a(adq.bE);
         }

         if (this.a < 0.0F) {
            this.a = 0.0F;
         }
      }
   }

   public static int a(brx var0, ccd var1, int var2, int var3, int var4, int var5, int var6) {
      if (!_snowman.v && _snowman != 0 && (_snowman + _snowman + _snowman + _snowman) % 200 == 0) {
         _snowman = a(_snowman, _snowman, _snowman, _snowman, _snowman);
      }

      return _snowman;
   }

   public static int a(brx var0, ccd var1, int var2, int var3, int var4) {
      int _snowman = 0;
      float _snowmanx = 5.0F;

      for (bfw _snowmanxx : _snowman.a(
         bfw.class,
         new dci(
            (double)((float)_snowman - 5.0F),
            (double)((float)_snowman - 5.0F),
            (double)((float)_snowman - 5.0F),
            (double)((float)(_snowman + 1) + 5.0F),
            (double)((float)(_snowman + 1) + 5.0F),
            (double)((float)(_snowman + 1) + 5.0F)
         )
      )) {
         if (_snowmanxx.bp instanceof bij) {
            aon _snowmanxxx = ((bij)_snowmanxx.bp).e();
            if (_snowmanxxx == _snowman || _snowmanxxx instanceof aom && ((aom)_snowmanxxx).a(_snowman)) {
               _snowman++;
            }
         }
      }

      return _snowman;
   }

   private void a(adp var1) {
      cez _snowman = this.p().c(bve.c);
      if (_snowman != cez.b) {
         double _snowmanx = (double)this.e.u() + 0.5;
         double _snowmanxx = (double)this.e.v() + 0.5;
         double _snowmanxxx = (double)this.e.w() + 0.5;
         if (_snowman == cez.c) {
            gc _snowmanxxxx = bve.h(this.p());
            _snowmanx += (double)_snowmanxxxx.i() * 0.5;
            _snowmanxxx += (double)_snowmanxxxx.k() * 0.5;
         }

         this.d.a(null, _snowmanx, _snowmanxx, _snowmanxxx, _snowman, adr.e, 0.5F, this.d.t.nextFloat() * 0.1F + 0.9F);
      }
   }

   @Override
   public boolean a_(int var1, int var2) {
      if (_snowman == 1) {
         this.c = _snowman;
         return true;
      } else {
         return super.a_(_snowman, _snowman);
      }
   }

   @Override
   public void c_(bfw var1) {
      if (!_snowman.a_()) {
         if (this.c < 0) {
            this.c = 0;
         }

         this.c++;
         this.h();
      }
   }

   @Override
   public void b_(bfw var1) {
      if (!_snowman.a_()) {
         this.c--;
         this.h();
      }
   }

   protected void h() {
      buo _snowman = this.p().b();
      if (_snowman instanceof bve) {
         this.d.a(this.e, _snowman, 1, this.c);
         this.d.b(this.e, _snowman);
      }
   }

   @Override
   protected gj<bmb> f() {
      return this.i;
   }

   @Override
   protected void a(gj<bmb> var1) {
      this.i = _snowman;
   }

   @Override
   public float a(float var1) {
      return afm.g(_snowman, this.b, this.a);
   }

   public static int a(brc var0, fx var1) {
      ceh _snowman = _snowman.d_(_snowman);
      if (_snowman.b().q()) {
         ccj _snowmanx = _snowman.c(_snowman);
         if (_snowmanx instanceof ccn) {
            return ((ccn)_snowmanx).c;
         }
      }

      return 0;
   }

   public static void a(ccn var0, ccn var1) {
      gj<bmb> _snowman = _snowman.f();
      _snowman.a(_snowman.f());
      _snowman.a(_snowman);
   }

   @Override
   protected bic a(int var1, bfv var2) {
      return bij.a(_snowman, _snowman, this);
   }
}
