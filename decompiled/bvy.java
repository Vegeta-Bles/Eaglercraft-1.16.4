import java.util.Random;

public abstract class bvy extends bxm {
   protected static final ddh b = buo.a(0.0, 0.0, 0.0, 16.0, 2.0, 16.0);
   public static final cey c = cex.w;

   protected bvy(ceg.c var1) {
      super(_snowman);
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      return b;
   }

   @Override
   public boolean a(ceh var1, brz var2, fx var3) {
      return c(_snowman, _snowman.c());
   }

   @Override
   public void a(ceh var1, aag var2, fx var3, Random var4) {
      if (!this.a((brz)_snowman, _snowman, _snowman)) {
         boolean _snowman = _snowman.c(c);
         boolean _snowmanx = this.a((brx)_snowman, _snowman, _snowman);
         if (_snowman && !_snowmanx) {
            _snowman.a(_snowman, _snowman.a(c, Boolean.valueOf(false)), 2);
         } else if (!_snowman) {
            _snowman.a(_snowman, _snowman.a(c, Boolean.valueOf(true)), 2);
            if (!_snowmanx) {
               _snowman.j().a(_snowman, this, this.g(_snowman), bsq.b);
            }
         }
      }
   }

   @Override
   public int b(ceh var1, brc var2, fx var3, gc var4) {
      return _snowman.b(_snowman, _snowman, _snowman);
   }

   @Override
   public int a(ceh var1, brc var2, fx var3, gc var4) {
      if (!_snowman.c(c)) {
         return 0;
      } else {
         return _snowman.c(aq) == _snowman ? this.b(_snowman, _snowman, _snowman) : 0;
      }
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, buo var4, fx var5, boolean var6) {
      if (_snowman.a((brz)_snowman, _snowman)) {
         this.c(_snowman, _snowman, _snowman);
      } else {
         ccj _snowman = this.q() ? _snowman.c(_snowman) : null;
         a(_snowman, _snowman, _snowman, _snowman);
         _snowman.a(_snowman, false);

         for (gc _snowmanx : gc.values()) {
            _snowman.b(_snowman.a(_snowmanx), this);
         }
      }
   }

   protected void c(brx var1, fx var2, ceh var3) {
      if (!this.a((brz)_snowman, _snowman, _snowman)) {
         boolean _snowman = _snowman.c(c);
         boolean _snowmanx = this.a(_snowman, _snowman, _snowman);
         if (_snowman != _snowmanx && !_snowman.J().b(_snowman, this)) {
            bsq _snowmanxx = bsq.c;
            if (this.c((brc)_snowman, _snowman, _snowman)) {
               _snowmanxx = bsq.a;
            } else if (_snowman) {
               _snowmanxx = bsq.b;
            }

            _snowman.J().a(_snowman, this, this.g(_snowman), _snowmanxx);
         }
      }
   }

   public boolean a(brz var1, fx var2, ceh var3) {
      return false;
   }

   protected boolean a(brx var1, fx var2, ceh var3) {
      return this.b(_snowman, _snowman, _snowman) > 0;
   }

   protected int b(brx var1, fx var2, ceh var3) {
      gc _snowman = _snowman.c(aq);
      fx _snowmanx = _snowman.a(_snowman);
      int _snowmanxx = _snowman.b(_snowmanx, _snowman);
      if (_snowmanxx >= 15) {
         return _snowmanxx;
      } else {
         ceh _snowmanxxx = _snowman.d_(_snowmanx);
         return Math.max(_snowmanxx, _snowmanxxx.a(bup.bS) ? _snowmanxxx.c(bzd.e) : 0);
      }
   }

   protected int b(brz var1, fx var2, ceh var3) {
      gc _snowman = _snowman.c(aq);
      gc _snowmanx = _snowman.g();
      gc _snowmanxx = _snowman.h();
      return Math.max(this.b(_snowman, _snowman.a(_snowmanx), _snowmanx), this.b(_snowman, _snowman.a(_snowmanxx), _snowmanxx));
   }

   protected int b(brz var1, fx var2, gc var3) {
      ceh _snowman = _snowman.d_(_snowman);
      if (this.h(_snowman)) {
         if (_snowman.a(bup.fw)) {
            return 15;
         } else {
            return _snowman.a(bup.bS) ? _snowman.c(bzd.e) : _snowman.c(_snowman, _snowman);
         }
      } else {
         return 0;
      }
   }

   @Override
   public boolean b_(ceh var1) {
      return true;
   }

   @Override
   public ceh a(bny var1) {
      return this.n().a(aq, _snowman.f().f());
   }

   @Override
   public void a(brx var1, fx var2, ceh var3, aqm var4, bmb var5) {
      if (this.a(_snowman, _snowman, _snowman)) {
         _snowman.J().a(_snowman, this, 1);
      }
   }

   @Override
   public void b(ceh var1, brx var2, fx var3, ceh var4, boolean var5) {
      this.d(_snowman, _snowman, _snowman);
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, ceh var4, boolean var5) {
      if (!_snowman && !_snowman.a(_snowman.b())) {
         super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
         this.d(_snowman, _snowman, _snowman);
      }
   }

   protected void d(brx var1, fx var2, ceh var3) {
      gc _snowman = _snowman.c(aq);
      fx _snowmanx = _snowman.a(_snowman.f());
      _snowman.a(_snowmanx, this, _snowman);
      _snowman.a(_snowmanx, this, _snowman);
   }

   protected boolean h(ceh var1) {
      return _snowman.i();
   }

   protected int b(brc var1, fx var2, ceh var3) {
      return 15;
   }

   public static boolean l(ceh var0) {
      return _snowman.b() instanceof bvy;
   }

   public boolean c(brc var1, fx var2, ceh var3) {
      gc _snowman = _snowman.c(aq).f();
      ceh _snowmanx = _snowman.d_(_snowman.a(_snowman));
      return l(_snowmanx) && _snowmanx.c(aq) != _snowman;
   }

   protected abstract int g(ceh var1);
}
