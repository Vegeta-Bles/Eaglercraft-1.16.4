import java.util.Random;

public class bvs extends buu implements buq {
   public static final cfg b = cex.ai;
   private static final ddh[] a = new ddh[]{
      buo.a(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
      buo.a(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
      buo.a(0.0, 0.0, 0.0, 16.0, 6.0, 16.0),
      buo.a(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),
      buo.a(0.0, 0.0, 0.0, 16.0, 10.0, 16.0),
      buo.a(0.0, 0.0, 0.0, 16.0, 12.0, 16.0),
      buo.a(0.0, 0.0, 0.0, 16.0, 14.0, 16.0),
      buo.a(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)
   };

   protected bvs(ceg.c var1) {
      super(_snowman);
      this.j(this.n.b().a(this.c(), Integer.valueOf(0)));
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      return a[_snowman.c(this.c())];
   }

   @Override
   protected boolean c(ceh var1, brc var2, fx var3) {
      return _snowman.a(bup.bX);
   }

   public cfg c() {
      return b;
   }

   public int d() {
      return 7;
   }

   protected int g(ceh var1) {
      return _snowman.c(this.c());
   }

   public ceh b(int var1) {
      return this.n().a(this.c(), Integer.valueOf(_snowman));
   }

   public boolean h(ceh var1) {
      return _snowman.c(this.c()) >= this.d();
   }

   @Override
   public boolean a_(ceh var1) {
      return !this.h(_snowman);
   }

   @Override
   public void b(ceh var1, aag var2, fx var3, Random var4) {
      if (_snowman.b(_snowman, 0) >= 9) {
         int _snowman = this.g(_snowman);
         if (_snowman < this.d()) {
            float _snowmanx = a(this, _snowman, _snowman);
            if (_snowman.nextInt((int)(25.0F / _snowmanx) + 1) == 0) {
               _snowman.a(_snowman, this.b(_snowman + 1), 2);
            }
         }
      }
   }

   public void a(brx var1, fx var2, ceh var3) {
      int _snowman = this.g(_snowman) + this.a(_snowman);
      int _snowmanx = this.d();
      if (_snowman > _snowmanx) {
         _snowman = _snowmanx;
      }

      _snowman.a(_snowman, this.b(_snowman), 2);
   }

   protected int a(brx var1) {
      return afm.a(_snowman.t, 2, 5);
   }

   protected static float a(buo var0, brc var1, fx var2) {
      float _snowman = 1.0F;
      fx _snowmanx = _snowman.c();

      for (int _snowmanxx = -1; _snowmanxx <= 1; _snowmanxx++) {
         for (int _snowmanxxx = -1; _snowmanxxx <= 1; _snowmanxxx++) {
            float _snowmanxxxx = 0.0F;
            ceh _snowmanxxxxx = _snowman.d_(_snowmanx.b(_snowmanxx, 0, _snowmanxxx));
            if (_snowmanxxxxx.a(bup.bX)) {
               _snowmanxxxx = 1.0F;
               if (_snowmanxxxxx.c(bwp.a) > 0) {
                  _snowmanxxxx = 3.0F;
               }
            }

            if (_snowmanxx != 0 || _snowmanxxx != 0) {
               _snowmanxxxx /= 4.0F;
            }

            _snowman += _snowmanxxxx;
         }
      }

      fx _snowmanxx = _snowman.d();
      fx _snowmanxxx = _snowman.e();
      fx _snowmanxxxxxx = _snowman.f();
      fx _snowmanxxxxxxx = _snowman.g();
      boolean _snowmanxxxxxxxx = _snowman == _snowman.d_(_snowmanxxxxxx).b() || _snowman == _snowman.d_(_snowmanxxxxxxx).b();
      boolean _snowmanxxxxxxxxx = _snowman == _snowman.d_(_snowmanxx).b() || _snowman == _snowman.d_(_snowmanxxx).b();
      if (_snowmanxxxxxxxx && _snowmanxxxxxxxxx) {
         _snowman /= 2.0F;
      } else {
         boolean _snowmanxxxxxxxxxx = _snowman == _snowman.d_(_snowmanxxxxxx.d()).b() || _snowman == _snowman.d_(_snowmanxxxxxxx.d()).b() || _snowman == _snowman.d_(_snowmanxxxxxxx.e()).b() || _snowman == _snowman.d_(_snowmanxxxxxx.e()).b();
         if (_snowmanxxxxxxxxxx) {
            _snowman /= 2.0F;
         }
      }

      return _snowman;
   }

   @Override
   public boolean a(ceh var1, brz var2, fx var3) {
      return (_snowman.b(_snowman, 0) >= 8 || _snowman.e(_snowman)) && super.a(_snowman, _snowman, _snowman);
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, aqa var4) {
      if (_snowman instanceof bdv && _snowman.V().b(brt.b)) {
         _snowman.a(_snowman, true, _snowman);
      }

      super.a(_snowman, _snowman, _snowman, _snowman);
   }

   protected brw e() {
      return bmd.kV;
   }

   @Override
   public bmb a(brc var1, fx var2, ceh var3) {
      return new bmb(this.e());
   }

   @Override
   public boolean a(brc var1, fx var2, ceh var3, boolean var4) {
      return !this.h(_snowman);
   }

   @Override
   public boolean a(brx var1, Random var2, fx var3, ceh var4) {
      return true;
   }

   @Override
   public void a(aag var1, Random var2, fx var3, ceh var4) {
      this.a((brx)_snowman, _snowman, _snowman);
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(b);
   }
}
