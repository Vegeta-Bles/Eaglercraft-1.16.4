import com.google.common.base.MoreObjects;

public class eac {
   private static final eao a = eao.p(new vk("textures/map/map_background.png"));
   private static final eao b = eao.p(new vk("textures/map/map_background_checkerboard.png"));
   private final djz c;
   private bmb d = bmb.b;
   private bmb e = bmb.b;
   private float f;
   private float g;
   private float h;
   private float i;
   private final eet j;
   private final efo k;

   public eac(djz var1) {
      this.c = _snowman;
      this.j = _snowman.ac();
      this.k = _snowman.ad();
   }

   public void a(aqm var1, bmb var2, ebm.b var3, boolean var4, dfm var5, eag var6, int var7) {
      if (!_snowman.a()) {
         this.k.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman.l, _snowman, ejw.a);
      }
   }

   private float a(float var1) {
      float _snowman = 1.0F - _snowman / 45.0F + 0.1F;
      _snowman = afm.a(_snowman, 0.0F, 1.0F);
      return -afm.b(_snowman * (float) Math.PI) * 0.5F + 0.5F;
   }

   private void a(dfm var1, eag var2, int var3, aqi var4) {
      this.c.M().a(this.c.s.o());
      ejk _snowman = (ejk)this.j.<dzj>a(this.c.s);
      _snowman.a();
      float _snowmanx = _snowman == aqi.b ? 1.0F : -1.0F;
      _snowman.a(g.d.a(92.0F));
      _snowman.a(g.b.a(45.0F));
      _snowman.a(g.f.a(_snowmanx * -41.0F));
      _snowman.a((double)(_snowmanx * 0.3F), -1.1F, 0.45F);
      if (_snowman == aqi.b) {
         _snowman.a(_snowman, _snowman, _snowman, this.c.s);
      } else {
         _snowman.b(_snowman, _snowman, _snowman, this.c.s);
      }

      _snowman.b();
   }

   private void a(dfm var1, eag var2, int var3, float var4, aqi var5, float var6, bmb var7) {
      float _snowman = _snowman == aqi.b ? 1.0F : -1.0F;
      _snowman.a((double)(_snowman * 0.125F), -0.125, 0.0);
      if (!this.c.s.bF()) {
         _snowman.a();
         _snowman.a(g.f.a(_snowman * 10.0F));
         this.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
         _snowman.b();
      }

      _snowman.a();
      _snowman.a((double)(_snowman * 0.51F), (double)(-0.08F + _snowman * -1.2F), -0.75);
      float _snowmanx = afm.c(_snowman);
      float _snowmanxx = afm.a(_snowmanx * (float) Math.PI);
      float _snowmanxxx = -0.5F * _snowmanxx;
      float _snowmanxxxx = 0.4F * afm.a(_snowmanx * (float) (Math.PI * 2));
      float _snowmanxxxxx = -0.3F * afm.a(_snowman * (float) Math.PI);
      _snowman.a((double)(_snowman * _snowmanxxx), (double)(_snowmanxxxx - 0.3F * _snowmanxx), (double)_snowmanxxxxx);
      _snowman.a(g.b.a(_snowmanxx * -45.0F));
      _snowman.a(g.d.a(_snowman * _snowmanxx * -30.0F));
      this.a(_snowman, _snowman, _snowman, _snowman);
      _snowman.b();
   }

   private void a(dfm var1, eag var2, int var3, float var4, float var5, float var6) {
      float _snowman = afm.c(_snowman);
      float _snowmanx = -0.2F * afm.a(_snowman * (float) Math.PI);
      float _snowmanxx = -0.4F * afm.a(_snowman * (float) Math.PI);
      _snowman.a(0.0, (double)(-_snowmanx / 2.0F), (double)_snowmanxx);
      float _snowmanxxx = this.a(_snowman);
      _snowman.a(0.0, (double)(0.04F + _snowman * -1.2F + _snowmanxxx * -0.5F), -0.72F);
      _snowman.a(g.b.a(_snowmanxxx * -85.0F));
      if (!this.c.s.bF()) {
         _snowman.a();
         _snowman.a(g.d.a(90.0F));
         this.a(_snowman, _snowman, _snowman, aqi.b);
         this.a(_snowman, _snowman, _snowman, aqi.a);
         _snowman.b();
      }

      float _snowmanxxxx = afm.a(_snowman * (float) Math.PI);
      _snowman.a(g.b.a(_snowmanxxxx * 20.0F));
      _snowman.a(2.0F, 2.0F, 2.0F);
      this.a(_snowman, _snowman, _snowman, this.d);
   }

   private void a(dfm var1, eag var2, int var3, bmb var4) {
      _snowman.a(g.d.a(180.0F));
      _snowman.a(g.f.a(180.0F));
      _snowman.a(0.38F, 0.38F, 0.38F);
      _snowman.a(-0.5, -0.5, 0.0);
      _snowman.a(0.0078125F, 0.0078125F, 0.0078125F);
      cxx _snowman = bmh.b(_snowman, this.c.r);
      dfq _snowmanx = _snowman.getBuffer(_snowman == null ? a : b);
      b _snowmanxx = _snowman.c().a();
      _snowmanx.a(_snowmanxx, -7.0F, 135.0F, 0.0F).a(255, 255, 255, 255).a(0.0F, 1.0F).a(_snowman).d();
      _snowmanx.a(_snowmanxx, 135.0F, 135.0F, 0.0F).a(255, 255, 255, 255).a(1.0F, 1.0F).a(_snowman).d();
      _snowmanx.a(_snowmanxx, 135.0F, -7.0F, 0.0F).a(255, 255, 255, 255).a(1.0F, 0.0F).a(_snowman).d();
      _snowmanx.a(_snowmanxx, -7.0F, -7.0F, 0.0F).a(255, 255, 255, 255).a(0.0F, 0.0F).a(_snowman).d();
      if (_snowman != null) {
         this.c.h.h().a(_snowman, _snowman, _snowman, false, _snowman);
      }
   }

   private void a(dfm var1, eag var2, int var3, float var4, float var5, aqi var6) {
      boolean _snowman = _snowman != aqi.a;
      float _snowmanx = _snowman ? 1.0F : -1.0F;
      float _snowmanxx = afm.c(_snowman);
      float _snowmanxxx = -0.3F * afm.a(_snowmanxx * (float) Math.PI);
      float _snowmanxxxx = 0.4F * afm.a(_snowmanxx * (float) (Math.PI * 2));
      float _snowmanxxxxx = -0.4F * afm.a(_snowman * (float) Math.PI);
      _snowman.a((double)(_snowmanx * (_snowmanxxx + 0.64000005F)), (double)(_snowmanxxxx + -0.6F + _snowman * -0.6F), (double)(_snowmanxxxxx + -0.71999997F));
      _snowman.a(g.d.a(_snowmanx * 45.0F));
      float _snowmanxxxxxx = afm.a(_snowman * _snowman * (float) Math.PI);
      float _snowmanxxxxxxx = afm.a(_snowmanxx * (float) Math.PI);
      _snowman.a(g.d.a(_snowmanx * _snowmanxxxxxxx * 70.0F));
      _snowman.a(g.f.a(_snowmanx * _snowmanxxxxxx * -20.0F));
      dzj _snowmanxxxxxxxx = this.c.s;
      this.c.M().a(_snowmanxxxxxxxx.o());
      _snowman.a((double)(_snowmanx * -1.0F), 3.6F, 3.5);
      _snowman.a(g.f.a(_snowmanx * 120.0F));
      _snowman.a(g.b.a(200.0F));
      _snowman.a(g.d.a(_snowmanx * -135.0F));
      _snowman.a((double)(_snowmanx * 5.6F), 0.0, 0.0);
      ejk _snowmanxxxxxxxxx = (ejk)this.j.<dzj>a(_snowmanxxxxxxxx);
      if (_snowman) {
         _snowmanxxxxxxxxx.a(_snowman, _snowman, _snowman, _snowmanxxxxxxxx);
      } else {
         _snowmanxxxxxxxxx.b(_snowman, _snowman, _snowman, _snowmanxxxxxxxx);
      }
   }

   private void a(dfm var1, float var2, aqi var3, bmb var4) {
      float _snowman = (float)this.c.s.dZ() - _snowman + 1.0F;
      float _snowmanx = _snowman / (float)_snowman.k();
      if (_snowmanx < 0.8F) {
         float _snowmanxx = afm.e(afm.b(_snowman / 4.0F * (float) Math.PI) * 0.1F);
         _snowman.a(0.0, (double)_snowmanxx, 0.0);
      }

      float _snowmanxx = 1.0F - (float)Math.pow((double)_snowmanx, 27.0);
      int _snowmanxxx = _snowman == aqi.b ? 1 : -1;
      _snowman.a((double)(_snowmanxx * 0.6F * (float)_snowmanxxx), (double)(_snowmanxx * -0.5F), (double)(_snowmanxx * 0.0F));
      _snowman.a(g.d.a((float)_snowmanxxx * _snowmanxx * 90.0F));
      _snowman.a(g.b.a(_snowmanxx * 10.0F));
      _snowman.a(g.f.a((float)_snowmanxxx * _snowmanxx * 30.0F));
   }

   private void a(dfm var1, aqi var2, float var3) {
      int _snowman = _snowman == aqi.b ? 1 : -1;
      float _snowmanx = afm.a(_snowman * _snowman * (float) Math.PI);
      _snowman.a(g.d.a((float)_snowman * (45.0F + _snowmanx * -20.0F)));
      float _snowmanxx = afm.a(afm.c(_snowman) * (float) Math.PI);
      _snowman.a(g.f.a((float)_snowman * _snowmanxx * -20.0F));
      _snowman.a(g.b.a(_snowmanxx * -80.0F));
      _snowman.a(g.d.a((float)_snowman * -45.0F));
   }

   private void b(dfm var1, aqi var2, float var3) {
      int _snowman = _snowman == aqi.b ? 1 : -1;
      _snowman.a((double)((float)_snowman * 0.56F), (double)(-0.52F + _snowman * -0.6F), -0.72F);
   }

   public void a(float var1, dfm var2, eag.a var3, dzm var4, int var5) {
      float _snowman = _snowman.r(_snowman);
      aot _snowmanx = (aot)MoreObjects.firstNonNull(_snowman.aj, aot.a);
      float _snowmanxx = afm.g(_snowman, _snowman.s, _snowman.q);
      boolean _snowmanxxx = true;
      boolean _snowmanxxxx = true;
      if (_snowman.dW()) {
         bmb _snowmanxxxxx = _snowman.dY();
         if (_snowmanxxxxx.b() == bmd.kc || _snowmanxxxxx.b() == bmd.qQ) {
            _snowmanxxx = _snowman.dX() == aot.a;
            _snowmanxxxx = !_snowmanxxx;
         }

         aot _snowmanxxxxxx = _snowman.dX();
         if (_snowmanxxxxxx == aot.a) {
            bmb _snowmanxxxxxxx = _snowman.dE();
            if (_snowmanxxxxxxx.b() == bmd.qQ && bkt.d(_snowmanxxxxxxx)) {
               _snowmanxxxx = false;
            }
         }
      } else {
         bmb _snowmanxxxxxx = _snowman.dD();
         bmb _snowmanxxxxxxx = _snowman.dE();
         if (_snowmanxxxxxx.b() == bmd.qQ && bkt.d(_snowmanxxxxxx)) {
            _snowmanxxxx = !_snowmanxxx;
         }

         if (_snowmanxxxxxxx.b() == bmd.qQ && bkt.d(_snowmanxxxxxxx)) {
            _snowmanxxx = !_snowmanxxxxxx.a();
            _snowmanxxxx = !_snowmanxxx;
         }
      }

      float _snowmanxxxxxxxx = afm.g(_snowman, _snowman.bO, _snowman.bM);
      float _snowmanxxxxxxxxx = afm.g(_snowman, _snowman.bN, _snowman.bL);
      _snowman.a(g.b.a((_snowman.g(_snowman) - _snowmanxxxxxxxx) * 0.1F));
      _snowman.a(g.d.a((_snowman.h(_snowman) - _snowmanxxxxxxxxx) * 0.1F));
      if (_snowmanxxx) {
         float _snowmanxxxxxxxxxx = _snowmanx == aot.a ? _snowman : 0.0F;
         float _snowmanxxxxxxxxxxx = 1.0F - afm.g(_snowman, this.g, this.f);
         this.a(_snowman, _snowman, _snowmanxx, aot.a, _snowmanxxxxxxxxxx, this.d, _snowmanxxxxxxxxxxx, _snowman, _snowman, _snowman);
      }

      if (_snowmanxxxx) {
         float _snowmanxxxxxxxxxx = _snowmanx == aot.b ? _snowman : 0.0F;
         float _snowmanxxxxxxxxxxx = 1.0F - afm.g(_snowman, this.i, this.h);
         this.a(_snowman, _snowman, _snowmanxx, aot.b, _snowmanxxxxxxxxxx, this.e, _snowmanxxxxxxxxxxx, _snowman, _snowman, _snowman);
      }

      _snowman.a();
   }

   private void a(dzj var1, float var2, float var3, aot var4, float var5, bmb var6, float var7, dfm var8, eag var9, int var10) {
      boolean _snowman = _snowman == aot.a;
      aqi _snowmanx = _snowman ? _snowman.dV() : _snowman.dV().a();
      _snowman.a();
      if (_snowman.a()) {
         if (_snowman && !_snowman.bF()) {
            this.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowmanx);
         }
      } else if (_snowman.b() == bmd.nf) {
         if (_snowman && this.e.a()) {
            this.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
         } else {
            this.a(_snowman, _snowman, _snowman, _snowman, _snowmanx, _snowman, _snowman);
         }
      } else if (_snowman.b() == bmd.qQ) {
         boolean _snowmanxx = bkt.d(_snowman);
         boolean _snowmanxxx = _snowmanx == aqi.b;
         int _snowmanxxxx = _snowmanxxx ? 1 : -1;
         if (_snowman.dW() && _snowman.dZ() > 0 && _snowman.dX() == _snowman) {
            this.b(_snowman, _snowmanx, _snowman);
            _snowman.a((double)((float)_snowmanxxxx * -0.4785682F), -0.094387F, 0.05731531F);
            _snowman.a(g.b.a(-11.935F));
            _snowman.a(g.d.a((float)_snowmanxxxx * 65.3F));
            _snowman.a(g.f.a((float)_snowmanxxxx * -9.785F));
            float _snowmanxxxxx = (float)_snowman.k() - ((float)this.c.s.dZ() - _snowman + 1.0F);
            float _snowmanxxxxxx = _snowmanxxxxx / (float)bkt.g(_snowman);
            if (_snowmanxxxxxx > 1.0F) {
               _snowmanxxxxxx = 1.0F;
            }

            if (_snowmanxxxxxx > 0.1F) {
               float _snowmanxxxxxxx = afm.a((_snowmanxxxxx - 0.1F) * 1.3F);
               float _snowmanxxxxxxxx = _snowmanxxxxxx - 0.1F;
               float _snowmanxxxxxxxxx = _snowmanxxxxxxx * _snowmanxxxxxxxx;
               _snowman.a((double)(_snowmanxxxxxxxxx * 0.0F), (double)(_snowmanxxxxxxxxx * 0.004F), (double)(_snowmanxxxxxxxxx * 0.0F));
            }

            _snowman.a((double)(_snowmanxxxxxx * 0.0F), (double)(_snowmanxxxxxx * 0.0F), (double)(_snowmanxxxxxx * 0.04F));
            _snowman.a(1.0F, 1.0F, 1.0F + _snowmanxxxxxx * 0.2F);
            _snowman.a(g.c.a((float)_snowmanxxxx * 45.0F));
         } else {
            float _snowmanxxxxxxx = -0.4F * afm.a(afm.c(_snowman) * (float) Math.PI);
            float _snowmanxxxxxxxx = 0.2F * afm.a(afm.c(_snowman) * (float) (Math.PI * 2));
            float _snowmanxxxxxxxxx = -0.2F * afm.a(_snowman * (float) Math.PI);
            _snowman.a((double)((float)_snowmanxxxx * _snowmanxxxxxxx), (double)_snowmanxxxxxxxx, (double)_snowmanxxxxxxxxx);
            this.b(_snowman, _snowmanx, _snowman);
            this.a(_snowman, _snowmanx, _snowman);
            if (_snowmanxx && _snowman < 0.001F) {
               _snowman.a((double)((float)_snowmanxxxx * -0.641864F), 0.0, 0.0);
               _snowman.a(g.d.a((float)_snowmanxxxx * 10.0F));
            }
         }

         this.a(_snowman, _snowman, _snowmanxxx ? ebm.b.e : ebm.b.d, !_snowmanxxx, _snowman, _snowman, _snowman);
      } else {
         boolean _snowmanxx = _snowmanx == aqi.b;
         if (_snowman.dW() && _snowman.dZ() > 0 && _snowman.dX() == _snowman) {
            int _snowmanxxx = _snowmanxx ? 1 : -1;
            switch (_snowman.l()) {
               case a:
                  this.b(_snowman, _snowmanx, _snowman);
                  break;
               case b:
               case c:
                  this.a(_snowman, _snowman, _snowmanx, _snowman);
                  this.b(_snowman, _snowmanx, _snowman);
                  break;
               case d:
                  this.b(_snowman, _snowmanx, _snowman);
                  break;
               case e:
                  this.b(_snowman, _snowmanx, _snowman);
                  _snowman.a((double)((float)_snowmanxxx * -0.2785682F), 0.18344387F, 0.15731531F);
                  _snowman.a(g.b.a(-13.935F));
                  _snowman.a(g.d.a((float)_snowmanxxx * 35.3F));
                  _snowman.a(g.f.a((float)_snowmanxxx * -9.785F));
                  float _snowmanxxxxxxxx = (float)_snowman.k() - ((float)this.c.s.dZ() - _snowman + 1.0F);
                  float _snowmanxxxxxxxxx = _snowmanxxxxxxxx / 20.0F;
                  _snowmanxxxxxxxxx = (_snowmanxxxxxxxxx * _snowmanxxxxxxxxx + _snowmanxxxxxxxxx * 2.0F) / 3.0F;
                  if (_snowmanxxxxxxxxx > 1.0F) {
                     _snowmanxxxxxxxxx = 1.0F;
                  }

                  if (_snowmanxxxxxxxxx > 0.1F) {
                     float _snowmanxxxxxxxxxx = afm.a((_snowmanxxxxxxxx - 0.1F) * 1.3F);
                     float _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxx - 0.1F;
                     float _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxx * _snowmanxxxxxxxxxxx;
                     _snowman.a((double)(_snowmanxxxxxxxxxxxx * 0.0F), (double)(_snowmanxxxxxxxxxxxx * 0.004F), (double)(_snowmanxxxxxxxxxxxx * 0.0F));
                  }

                  _snowman.a((double)(_snowmanxxxxxxxxx * 0.0F), (double)(_snowmanxxxxxxxxx * 0.0F), (double)(_snowmanxxxxxxxxx * 0.04F));
                  _snowman.a(1.0F, 1.0F, 1.0F + _snowmanxxxxxxxxx * 0.2F);
                  _snowman.a(g.c.a((float)_snowmanxxx * 45.0F));
                  break;
               case f:
                  this.b(_snowman, _snowmanx, _snowman);
                  _snowman.a((double)((float)_snowmanxxx * -0.5F), 0.7F, 0.1F);
                  _snowman.a(g.b.a(-55.0F));
                  _snowman.a(g.d.a((float)_snowmanxxx * 35.3F));
                  _snowman.a(g.f.a((float)_snowmanxxx * -9.785F));
                  float _snowmanxxxx = (float)_snowman.k() - ((float)this.c.s.dZ() - _snowman + 1.0F);
                  float _snowmanxxxxxxx = _snowmanxxxx / 10.0F;
                  if (_snowmanxxxxxxx > 1.0F) {
                     _snowmanxxxxxxx = 1.0F;
                  }

                  if (_snowmanxxxxxxx > 0.1F) {
                     float _snowmanxxxxxxxx = afm.a((_snowmanxxxx - 0.1F) * 1.3F);
                     float _snowmanxxxxxxxxx = _snowmanxxxxxxx - 0.1F;
                     float _snowmanxxxxxxxxxx = _snowmanxxxxxxxx * _snowmanxxxxxxxxx;
                     _snowman.a((double)(_snowmanxxxxxxxxxx * 0.0F), (double)(_snowmanxxxxxxxxxx * 0.004F), (double)(_snowmanxxxxxxxxxx * 0.0F));
                  }

                  _snowman.a(0.0, 0.0, (double)(_snowmanxxxxxxx * 0.2F));
                  _snowman.a(1.0F, 1.0F, 1.0F + _snowmanxxxxxxx * 0.2F);
                  _snowman.a(g.c.a((float)_snowmanxxx * 45.0F));
            }
         } else if (_snowman.dR()) {
            this.b(_snowman, _snowmanx, _snowman);
            int _snowmanxxx = _snowmanxx ? 1 : -1;
            _snowman.a((double)((float)_snowmanxxx * -0.4F), 0.8F, 0.3F);
            _snowman.a(g.d.a((float)_snowmanxxx * 65.0F));
            _snowman.a(g.f.a((float)_snowmanxxx * -85.0F));
         } else {
            float _snowmanxxx = -0.4F * afm.a(afm.c(_snowman) * (float) Math.PI);
            float _snowmanxxxxxxxxxx = 0.2F * afm.a(afm.c(_snowman) * (float) (Math.PI * 2));
            float _snowmanxxxxxxxxxxx = -0.2F * afm.a(_snowman * (float) Math.PI);
            int _snowmanxxxxxxxxxxxx = _snowmanxx ? 1 : -1;
            _snowman.a((double)((float)_snowmanxxxxxxxxxxxx * _snowmanxxx), (double)_snowmanxxxxxxxxxx, (double)_snowmanxxxxxxxxxxx);
            this.b(_snowman, _snowmanx, _snowman);
            this.a(_snowman, _snowmanx, _snowman);
         }

         this.a(_snowman, _snowman, _snowmanxx ? ebm.b.e : ebm.b.d, !_snowmanxx, _snowman, _snowman, _snowman);
      }

      _snowman.b();
   }

   public void a() {
      this.g = this.f;
      this.i = this.h;
      dzm _snowman = this.c.s;
      bmb _snowmanx = _snowman.dD();
      bmb _snowmanxx = _snowman.dE();
      if (bmb.b(this.d, _snowmanx)) {
         this.d = _snowmanx;
      }

      if (bmb.b(this.e, _snowmanxx)) {
         this.e = _snowmanxx;
      }

      if (_snowman.L()) {
         this.f = afm.a(this.f - 0.4F, 0.0F, 1.0F);
         this.h = afm.a(this.h - 0.4F, 0.0F, 1.0F);
      } else {
         float _snowmanxxx = _snowman.u(1.0F);
         this.f = this.f + afm.a((this.d == _snowmanx ? _snowmanxxx * _snowmanxxx * _snowmanxxx : 0.0F) - this.f, -0.4F, 0.4F);
         this.h = this.h + afm.a((float)(this.e == _snowmanxx ? 1 : 0) - this.h, -0.4F, 0.4F);
      }

      if (this.f < 0.1F) {
         this.d = _snowmanx;
      }

      if (this.h < 0.1F) {
         this.e = _snowmanxx;
      }
   }

   public void a(aot var1) {
      if (_snowman == aot.a) {
         this.f = 0.0F;
      } else {
         this.h = 0.0F;
      }
   }
}
