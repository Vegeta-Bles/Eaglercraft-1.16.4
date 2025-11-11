import com.google.common.collect.ImmutableList;
import java.util.function.Function;

public class dum<T extends aqm> extends dtf<T> implements dth, dui {
   public dwn f;
   public dwn g;
   public dwn h;
   public dwn i;
   public dwn j;
   public dwn k;
   public dwn l;
   public dum.a m = dum.a.a;
   public dum.a n = dum.a.a;
   public boolean o;
   public float p;

   public dum(float var1) {
      this(eao::d, _snowman, 0.0F, 64, 32);
   }

   protected dum(float var1, float var2, int var3, int var4) {
      this(eao::d, _snowman, _snowman, _snowman, _snowman);
   }

   public dum(Function<vk, eao> var1, float var2, float var3, int var4, int var5) {
      super(_snowman, true, 16.0F, 0.0F, 2.0F, 2.0F, 24.0F);
      this.r = _snowman;
      this.s = _snowman;
      this.f = new dwn(this, 0, 0);
      this.f.a(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, _snowman);
      this.f.a(0.0F, 0.0F + _snowman, 0.0F);
      this.g = new dwn(this, 32, 0);
      this.g.a(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, _snowman + 0.5F);
      this.g.a(0.0F, 0.0F + _snowman, 0.0F);
      this.h = new dwn(this, 16, 16);
      this.h.a(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, _snowman);
      this.h.a(0.0F, 0.0F + _snowman, 0.0F);
      this.i = new dwn(this, 40, 16);
      this.i.a(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, _snowman);
      this.i.a(-5.0F, 2.0F + _snowman, 0.0F);
      this.j = new dwn(this, 40, 16);
      this.j.g = true;
      this.j.a(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, _snowman);
      this.j.a(5.0F, 2.0F + _snowman, 0.0F);
      this.k = new dwn(this, 0, 16);
      this.k.a(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, _snowman);
      this.k.a(-1.9F, 12.0F + _snowman, 0.0F);
      this.l = new dwn(this, 0, 16);
      this.l.g = true;
      this.l.a(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, _snowman);
      this.l.a(1.9F, 12.0F + _snowman, 0.0F);
   }

   @Override
   protected Iterable<dwn> a() {
      return ImmutableList.of(this.f);
   }

   @Override
   protected Iterable<dwn> b() {
      return ImmutableList.of(this.h, this.i, this.j, this.k, this.l, this.g);
   }

   public void a(T var1, float var2, float var3, float var4) {
      this.p = _snowman.a(_snowman);
      super.a(_snowman, _snowman, _snowman, _snowman);
   }

   public void a(T var1, float var2, float var3, float var4, float var5, float var6) {
      boolean _snowman = _snowman.eg() > 4;
      boolean _snowmanx = _snowman.bC();
      this.f.e = _snowman * (float) (Math.PI / 180.0);
      if (_snowman) {
         this.f.d = (float) (-Math.PI / 4);
      } else if (this.p > 0.0F) {
         if (_snowmanx) {
            this.f.d = this.a(this.p, this.f.d, (float) (-Math.PI / 4));
         } else {
            this.f.d = this.a(this.p, this.f.d, _snowman * (float) (Math.PI / 180.0));
         }
      } else {
         this.f.d = _snowman * (float) (Math.PI / 180.0);
      }

      this.h.e = 0.0F;
      this.i.c = 0.0F;
      this.i.a = -5.0F;
      this.j.c = 0.0F;
      this.j.a = 5.0F;
      float _snowmanxx = 1.0F;
      if (_snowman) {
         _snowmanxx = (float)_snowman.cC().g();
         _snowmanxx /= 0.2F;
         _snowmanxx *= _snowmanxx * _snowmanxx;
      }

      if (_snowmanxx < 1.0F) {
         _snowmanxx = 1.0F;
      }

      this.i.d = afm.b(_snowman * 0.6662F + (float) Math.PI) * 2.0F * _snowman * 0.5F / _snowmanxx;
      this.j.d = afm.b(_snowman * 0.6662F) * 2.0F * _snowman * 0.5F / _snowmanxx;
      this.i.f = 0.0F;
      this.j.f = 0.0F;
      this.k.d = afm.b(_snowman * 0.6662F) * 1.4F * _snowman / _snowmanxx;
      this.l.d = afm.b(_snowman * 0.6662F + (float) Math.PI) * 1.4F * _snowman / _snowmanxx;
      this.k.e = 0.0F;
      this.l.e = 0.0F;
      this.k.f = 0.0F;
      this.l.f = 0.0F;
      if (this.d) {
         this.i.d += (float) (-Math.PI / 5);
         this.j.d += (float) (-Math.PI / 5);
         this.k.d = -1.4137167F;
         this.k.e = (float) (Math.PI / 10);
         this.k.f = 0.07853982F;
         this.l.d = -1.4137167F;
         this.l.e = (float) (-Math.PI / 10);
         this.l.f = -0.07853982F;
      }

      this.i.e = 0.0F;
      this.j.e = 0.0F;
      boolean _snowmanxxx = _snowman.dV() == aqi.b;
      boolean _snowmanxxxx = _snowmanxxx ? this.m.a() : this.n.a();
      if (_snowmanxxx != _snowmanxxxx) {
         this.c(_snowman);
         this.b(_snowman);
      } else {
         this.b(_snowman);
         this.c(_snowman);
      }

      this.a(_snowman, _snowman);
      if (this.o) {
         this.h.d = 0.5F;
         this.i.d += 0.4F;
         this.j.d += 0.4F;
         this.k.c = 4.0F;
         this.l.c = 4.0F;
         this.k.b = 12.2F;
         this.l.b = 12.2F;
         this.f.b = 4.2F;
         this.h.b = 3.2F;
         this.j.b = 5.2F;
         this.i.b = 5.2F;
      } else {
         this.h.d = 0.0F;
         this.k.c = 0.1F;
         this.l.c = 0.1F;
         this.k.b = 12.0F;
         this.l.b = 12.0F;
         this.f.b = 0.0F;
         this.h.b = 0.0F;
         this.j.b = 2.0F;
         this.i.b = 2.0F;
      }

      dtg.a(this.i, this.j, _snowman);
      if (this.p > 0.0F) {
         float _snowmanxxxxx = _snowman % 26.0F;
         aqi _snowmanxxxxxx = this.a(_snowman);
         float _snowmanxxxxxxx = _snowmanxxxxxx == aqi.b && this.c > 0.0F ? 0.0F : this.p;
         float _snowmanxxxxxxxx = _snowmanxxxxxx == aqi.a && this.c > 0.0F ? 0.0F : this.p;
         if (_snowmanxxxxx < 14.0F) {
            this.j.d = this.a(_snowmanxxxxxxxx, this.j.d, 0.0F);
            this.i.d = afm.g(_snowmanxxxxxxx, this.i.d, 0.0F);
            this.j.e = this.a(_snowmanxxxxxxxx, this.j.e, (float) Math.PI);
            this.i.e = afm.g(_snowmanxxxxxxx, this.i.e, (float) Math.PI);
            this.j.f = this.a(_snowmanxxxxxxxx, this.j.f, (float) Math.PI + 1.8707964F * this.a(_snowmanxxxxx) / this.a(14.0F));
            this.i.f = afm.g(_snowmanxxxxxxx, this.i.f, (float) Math.PI - 1.8707964F * this.a(_snowmanxxxxx) / this.a(14.0F));
         } else if (_snowmanxxxxx >= 14.0F && _snowmanxxxxx < 22.0F) {
            float _snowmanxxxxxxxxx = (_snowmanxxxxx - 14.0F) / 8.0F;
            this.j.d = this.a(_snowmanxxxxxxxx, this.j.d, (float) (Math.PI / 2) * _snowmanxxxxxxxxx);
            this.i.d = afm.g(_snowmanxxxxxxx, this.i.d, (float) (Math.PI / 2) * _snowmanxxxxxxxxx);
            this.j.e = this.a(_snowmanxxxxxxxx, this.j.e, (float) Math.PI);
            this.i.e = afm.g(_snowmanxxxxxxx, this.i.e, (float) Math.PI);
            this.j.f = this.a(_snowmanxxxxxxxx, this.j.f, 5.012389F - 1.8707964F * _snowmanxxxxxxxxx);
            this.i.f = afm.g(_snowmanxxxxxxx, this.i.f, 1.2707963F + 1.8707964F * _snowmanxxxxxxxxx);
         } else if (_snowmanxxxxx >= 22.0F && _snowmanxxxxx < 26.0F) {
            float _snowmanxxxxxxxxx = (_snowmanxxxxx - 22.0F) / 4.0F;
            this.j.d = this.a(_snowmanxxxxxxxx, this.j.d, (float) (Math.PI / 2) - (float) (Math.PI / 2) * _snowmanxxxxxxxxx);
            this.i.d = afm.g(_snowmanxxxxxxx, this.i.d, (float) (Math.PI / 2) - (float) (Math.PI / 2) * _snowmanxxxxxxxxx);
            this.j.e = this.a(_snowmanxxxxxxxx, this.j.e, (float) Math.PI);
            this.i.e = afm.g(_snowmanxxxxxxx, this.i.e, (float) Math.PI);
            this.j.f = this.a(_snowmanxxxxxxxx, this.j.f, (float) Math.PI);
            this.i.f = afm.g(_snowmanxxxxxxx, this.i.f, (float) Math.PI);
         }

         float _snowmanxxxxxxxxx = 0.3F;
         float _snowmanxxxxxxxxxx = 0.33333334F;
         this.l.d = afm.g(this.p, this.l.d, 0.3F * afm.b(_snowman * 0.33333334F + (float) Math.PI));
         this.k.d = afm.g(this.p, this.k.d, 0.3F * afm.b(_snowman * 0.33333334F));
      }

      this.g.a(this.f);
   }

   private void b(T var1) {
      switch (this.n) {
         case a:
            this.i.e = 0.0F;
            break;
         case c:
            this.i.d = this.i.d * 0.5F - 0.9424779F;
            this.i.e = (float) (-Math.PI / 6);
            break;
         case b:
            this.i.d = this.i.d * 0.5F - (float) (Math.PI / 10);
            this.i.e = 0.0F;
            break;
         case e:
            this.i.d = this.i.d * 0.5F - (float) Math.PI;
            this.i.e = 0.0F;
            break;
         case d:
            this.i.e = -0.1F + this.f.e;
            this.j.e = 0.1F + this.f.e + 0.4F;
            this.i.d = (float) (-Math.PI / 2) + this.f.d;
            this.j.d = (float) (-Math.PI / 2) + this.f.d;
            break;
         case f:
            dtg.a(this.i, this.j, _snowman, true);
            break;
         case g:
            dtg.a(this.i, this.j, this.f, true);
      }
   }

   private void c(T var1) {
      switch (this.m) {
         case a:
            this.j.e = 0.0F;
            break;
         case c:
            this.j.d = this.j.d * 0.5F - 0.9424779F;
            this.j.e = (float) (Math.PI / 6);
            break;
         case b:
            this.j.d = this.j.d * 0.5F - (float) (Math.PI / 10);
            this.j.e = 0.0F;
            break;
         case e:
            this.j.d = this.j.d * 0.5F - (float) Math.PI;
            this.j.e = 0.0F;
            break;
         case d:
            this.i.e = -0.1F + this.f.e - 0.4F;
            this.j.e = 0.1F + this.f.e;
            this.i.d = (float) (-Math.PI / 2) + this.f.d;
            this.j.d = (float) (-Math.PI / 2) + this.f.d;
            break;
         case f:
            dtg.a(this.i, this.j, _snowman, false);
            break;
         case g:
            dtg.a(this.i, this.j, this.f, false);
      }
   }

   protected void a(T var1, float var2) {
      if (!(this.c <= 0.0F)) {
         aqi _snowman = this.a(_snowman);
         dwn _snowmanx = this.a(_snowman);
         float _snowmanxx = this.c;
         this.h.e = afm.a(afm.c(_snowmanxx) * (float) (Math.PI * 2)) * 0.2F;
         if (_snowman == aqi.a) {
            this.h.e *= -1.0F;
         }

         this.i.c = afm.a(this.h.e) * 5.0F;
         this.i.a = -afm.b(this.h.e) * 5.0F;
         this.j.c = -afm.a(this.h.e) * 5.0F;
         this.j.a = afm.b(this.h.e) * 5.0F;
         this.i.e = this.i.e + this.h.e;
         this.j.e = this.j.e + this.h.e;
         this.j.d = this.j.d + this.h.e;
         _snowmanxx = 1.0F - this.c;
         _snowmanxx *= _snowmanxx;
         _snowmanxx *= _snowmanxx;
         _snowmanxx = 1.0F - _snowmanxx;
         float _snowmanxxx = afm.a(_snowmanxx * (float) Math.PI);
         float _snowmanxxxx = afm.a(this.c * (float) Math.PI) * -(this.f.d - 0.7F) * 0.75F;
         _snowmanx.d = (float)((double)_snowmanx.d - ((double)_snowmanxxx * 1.2 + (double)_snowmanxxxx));
         _snowmanx.e = _snowmanx.e + this.h.e * 2.0F;
         _snowmanx.f = _snowmanx.f + afm.a(this.c * (float) Math.PI) * -0.4F;
      }
   }

   protected float a(float var1, float var2, float var3) {
      float _snowman = (_snowman - _snowman) % (float) (Math.PI * 2);
      if (_snowman < (float) -Math.PI) {
         _snowman += (float) (Math.PI * 2);
      }

      if (_snowman >= (float) Math.PI) {
         _snowman -= (float) (Math.PI * 2);
      }

      return _snowman + _snowman * _snowman;
   }

   private float a(float var1) {
      return -65.0F * _snowman + _snowman * _snowman;
   }

   public void a(dum<T> var1) {
      super.a(_snowman);
      _snowman.m = this.m;
      _snowman.n = this.n;
      _snowman.o = this.o;
      _snowman.f.a(this.f);
      _snowman.g.a(this.g);
      _snowman.h.a(this.h);
      _snowman.i.a(this.i);
      _snowman.j.a(this.j);
      _snowman.k.a(this.k);
      _snowman.l.a(this.l);
   }

   public void d_(boolean var1) {
      this.f.h = _snowman;
      this.g.h = _snowman;
      this.h.h = _snowman;
      this.i.h = _snowman;
      this.j.h = _snowman;
      this.k.h = _snowman;
      this.l.h = _snowman;
   }

   @Override
   public void a(aqi var1, dfm var2) {
      this.a(_snowman).a(_snowman);
   }

   protected dwn a(aqi var1) {
      return _snowman == aqi.a ? this.j : this.i;
   }

   @Override
   public dwn c() {
      return this.f;
   }

   protected aqi a(T var1) {
      aqi _snowman = _snowman.dV();
      return _snowman.aj == aot.a ? _snowman : _snowman.a();
   }

   public static enum a {
      a(false),
      b(false),
      c(false),
      d(true),
      e(false),
      f(true),
      g(true);

      private final boolean h;

      private a(boolean var3) {
         this.h = _snowman;
      }

      public boolean a() {
         return this.h;
      }
   }
}
