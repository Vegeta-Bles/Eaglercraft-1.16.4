import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.AbstractList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public abstract class dlf<E extends dlf.a<E>> extends dmg implements dmf {
   protected final djz b;
   protected final int c;
   private final List<E> a = new dlf.c();
   protected int d;
   protected int e;
   protected int i;
   protected int j;
   protected int k;
   protected int l;
   protected boolean m = true;
   private double o;
   private boolean p = true;
   private boolean q;
   protected int n;
   private boolean r;
   private E s;
   private boolean t = true;
   private boolean u = true;

   public dlf(djz var1, int var2, int var3, int var4, int var5, int var6) {
      this.b = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.i = _snowman;
      this.j = _snowman;
      this.c = _snowman;
      this.l = 0;
      this.k = _snowman;
   }

   public void a(boolean var1) {
      this.p = _snowman;
   }

   protected void a(boolean var1, int var2) {
      this.q = _snowman;
      this.n = _snowman;
      if (!_snowman) {
         this.n = 0;
      }
   }

   public int d() {
      return 220;
   }

   @Nullable
   public E h() {
      return this.s;
   }

   public void a(@Nullable E var1) {
      this.s = _snowman;
   }

   public void b(boolean var1) {
      this.t = _snowman;
   }

   public void c(boolean var1) {
      this.u = _snowman;
   }

   @Nullable
   public E i() {
      return (E)super.aw_();
   }

   @Override
   public final List<E> au_() {
      return this.a;
   }

   protected final void k() {
      this.a.clear();
   }

   protected void a(Collection<E> var1) {
      this.a.clear();
      this.a.addAll(_snowman);
   }

   protected E e(int var1) {
      return this.au_().get(_snowman);
   }

   protected int b(E var1) {
      this.a.add(_snowman);
      return this.a.size() - 1;
   }

   protected int l() {
      return this.au_().size();
   }

   protected boolean f(int var1) {
      return Objects.equals(this.h(), this.au_().get(_snowman));
   }

   @Nullable
   protected final E a(double var1, double var3) {
      int _snowman = this.d() / 2;
      int _snowmanx = this.l + this.d / 2;
      int _snowmanxx = _snowmanx - _snowman;
      int _snowmanxxx = _snowmanx + _snowman;
      int _snowmanxxxx = afm.c(_snowman - (double)this.i) - this.n + (int)this.m() - 4;
      int _snowmanxxxxx = _snowmanxxxx / this.c;
      return _snowman < (double)this.e() && _snowman >= (double)_snowmanxx && _snowman <= (double)_snowmanxxx && _snowmanxxxxx >= 0 && _snowmanxxxx >= 0 && _snowmanxxxxx < this.l() ? this.au_().get(_snowmanxxxxx) : null;
   }

   public void a(int var1, int var2, int var3, int var4) {
      this.d = _snowman;
      this.e = _snowman;
      this.i = _snowman;
      this.j = _snowman;
      this.l = 0;
      this.k = _snowman;
   }

   public void g(int var1) {
      this.l = _snowman;
      this.k = _snowman + this.d;
   }

   protected int c() {
      return this.l() * this.c + this.n;
   }

   protected void a(int var1, int var2) {
   }

   protected void a(dfm var1, int var2, int var3, dfo var4) {
   }

   protected void a(dfm var1) {
   }

   protected void a(dfm var1, int var2, int var3) {
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
      int _snowman = this.e();
      int _snowmanx = _snowman + 6;
      dfo _snowmanxx = dfo.a();
      dfh _snowmanxxx = _snowmanxx.c();
      if (this.t) {
         this.b.M().a(dkw.f);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         float _snowmanxxxx = 32.0F;
         _snowmanxxx.a(7, dfk.p);
         _snowmanxxx.a((double)this.l, (double)this.j, 0.0).a((float)this.l / 32.0F, (float)(this.j + (int)this.m()) / 32.0F).a(32, 32, 32, 255).d();
         _snowmanxxx.a((double)this.k, (double)this.j, 0.0).a((float)this.k / 32.0F, (float)(this.j + (int)this.m()) / 32.0F).a(32, 32, 32, 255).d();
         _snowmanxxx.a((double)this.k, (double)this.i, 0.0).a((float)this.k / 32.0F, (float)(this.i + (int)this.m()) / 32.0F).a(32, 32, 32, 255).d();
         _snowmanxxx.a((double)this.l, (double)this.i, 0.0).a((float)this.l / 32.0F, (float)(this.i + (int)this.m()) / 32.0F).a(32, 32, 32, 255).d();
         _snowmanxx.b();
      }

      int _snowmanxxxx = this.q();
      int _snowmanxxxxx = this.i + 4 - (int)this.m();
      if (this.q) {
         this.a(_snowman, _snowmanxxxx, _snowmanxxxxx, _snowmanxx);
      }

      this.a(_snowman, _snowmanxxxx, _snowmanxxxxx, _snowman, _snowman, _snowman);
      if (this.u) {
         this.b.M().a(dkw.f);
         RenderSystem.enableDepthTest();
         RenderSystem.depthFunc(519);
         float _snowmanxxxxxx = 32.0F;
         int _snowmanxxxxxxx = -100;
         _snowmanxxx.a(7, dfk.p);
         _snowmanxxx.a((double)this.l, (double)this.i, -100.0).a(0.0F, (float)this.i / 32.0F).a(64, 64, 64, 255).d();
         _snowmanxxx.a((double)(this.l + this.d), (double)this.i, -100.0).a((float)this.d / 32.0F, (float)this.i / 32.0F).a(64, 64, 64, 255).d();
         _snowmanxxx.a((double)(this.l + this.d), 0.0, -100.0).a((float)this.d / 32.0F, 0.0F).a(64, 64, 64, 255).d();
         _snowmanxxx.a((double)this.l, 0.0, -100.0).a(0.0F, 0.0F).a(64, 64, 64, 255).d();
         _snowmanxxx.a((double)this.l, (double)this.e, -100.0).a(0.0F, (float)this.e / 32.0F).a(64, 64, 64, 255).d();
         _snowmanxxx.a((double)(this.l + this.d), (double)this.e, -100.0).a((float)this.d / 32.0F, (float)this.e / 32.0F).a(64, 64, 64, 255).d();
         _snowmanxxx.a((double)(this.l + this.d), (double)this.j, -100.0).a((float)this.d / 32.0F, (float)this.j / 32.0F).a(64, 64, 64, 255).d();
         _snowmanxxx.a((double)this.l, (double)this.j, -100.0).a(0.0F, (float)this.j / 32.0F).a(64, 64, 64, 255).d();
         _snowmanxx.b();
         RenderSystem.depthFunc(515);
         RenderSystem.disableDepthTest();
         RenderSystem.enableBlend();
         RenderSystem.blendFuncSeparate(dem.r.l, dem.j.j, dem.r.o, dem.j.e);
         RenderSystem.disableAlphaTest();
         RenderSystem.shadeModel(7425);
         RenderSystem.disableTexture();
         int _snowmanxxxxxxxx = 4;
         _snowmanxxx.a(7, dfk.p);
         _snowmanxxx.a((double)this.l, (double)(this.i + 4), 0.0).a(0.0F, 1.0F).a(0, 0, 0, 0).d();
         _snowmanxxx.a((double)this.k, (double)(this.i + 4), 0.0).a(1.0F, 1.0F).a(0, 0, 0, 0).d();
         _snowmanxxx.a((double)this.k, (double)this.i, 0.0).a(1.0F, 0.0F).a(0, 0, 0, 255).d();
         _snowmanxxx.a((double)this.l, (double)this.i, 0.0).a(0.0F, 0.0F).a(0, 0, 0, 255).d();
         _snowmanxxx.a((double)this.l, (double)this.j, 0.0).a(0.0F, 1.0F).a(0, 0, 0, 255).d();
         _snowmanxxx.a((double)this.k, (double)this.j, 0.0).a(1.0F, 1.0F).a(0, 0, 0, 255).d();
         _snowmanxxx.a((double)this.k, (double)(this.j - 4), 0.0).a(1.0F, 0.0F).a(0, 0, 0, 0).d();
         _snowmanxxx.a((double)this.l, (double)(this.j - 4), 0.0).a(0.0F, 0.0F).a(0, 0, 0, 0).d();
         _snowmanxx.b();
      }

      int _snowmanxxxxxx = this.n();
      if (_snowmanxxxxxx > 0) {
         RenderSystem.disableTexture();
         int _snowmanxxxxxxx = (int)((float)((this.j - this.i) * (this.j - this.i)) / (float)this.c());
         _snowmanxxxxxxx = afm.a(_snowmanxxxxxxx, 32, this.j - this.i - 8);
         int _snowmanxxxxxxxx = (int)this.m() * (this.j - this.i - _snowmanxxxxxxx) / _snowmanxxxxxx + this.i;
         if (_snowmanxxxxxxxx < this.i) {
            _snowmanxxxxxxxx = this.i;
         }

         _snowmanxxx.a(7, dfk.p);
         _snowmanxxx.a((double)_snowman, (double)this.j, 0.0).a(0.0F, 1.0F).a(0, 0, 0, 255).d();
         _snowmanxxx.a((double)_snowmanx, (double)this.j, 0.0).a(1.0F, 1.0F).a(0, 0, 0, 255).d();
         _snowmanxxx.a((double)_snowmanx, (double)this.i, 0.0).a(1.0F, 0.0F).a(0, 0, 0, 255).d();
         _snowmanxxx.a((double)_snowman, (double)this.i, 0.0).a(0.0F, 0.0F).a(0, 0, 0, 255).d();
         _snowmanxxx.a((double)_snowman, (double)(_snowmanxxxxxxxx + _snowmanxxxxxxx), 0.0).a(0.0F, 1.0F).a(128, 128, 128, 255).d();
         _snowmanxxx.a((double)_snowmanx, (double)(_snowmanxxxxxxxx + _snowmanxxxxxxx), 0.0).a(1.0F, 1.0F).a(128, 128, 128, 255).d();
         _snowmanxxx.a((double)_snowmanx, (double)_snowmanxxxxxxxx, 0.0).a(1.0F, 0.0F).a(128, 128, 128, 255).d();
         _snowmanxxx.a((double)_snowman, (double)_snowmanxxxxxxxx, 0.0).a(0.0F, 0.0F).a(128, 128, 128, 255).d();
         _snowmanxxx.a((double)_snowman, (double)(_snowmanxxxxxxxx + _snowmanxxxxxxx - 1), 0.0).a(0.0F, 1.0F).a(192, 192, 192, 255).d();
         _snowmanxxx.a((double)(_snowmanx - 1), (double)(_snowmanxxxxxxxx + _snowmanxxxxxxx - 1), 0.0).a(1.0F, 1.0F).a(192, 192, 192, 255).d();
         _snowmanxxx.a((double)(_snowmanx - 1), (double)_snowmanxxxxxxxx, 0.0).a(1.0F, 0.0F).a(192, 192, 192, 255).d();
         _snowmanxxx.a((double)_snowman, (double)_snowmanxxxxxxxx, 0.0).a(0.0F, 0.0F).a(192, 192, 192, 255).d();
         _snowmanxx.b();
      }

      this.a(_snowman, _snowman, _snowman);
      RenderSystem.enableTexture();
      RenderSystem.shadeModel(7424);
      RenderSystem.enableAlphaTest();
      RenderSystem.disableBlend();
   }

   protected void c(E var1) {
      this.a((double)(this.au_().indexOf(_snowman) * this.c + this.c / 2 - (this.j - this.i) / 2));
   }

   protected void d(E var1) {
      int _snowman = this.h(this.au_().indexOf(_snowman));
      int _snowmanx = _snowman - this.i - 4 - this.c;
      if (_snowmanx < 0) {
         this.a(_snowmanx);
      }

      int _snowmanxx = this.j - _snowman - this.c - this.c;
      if (_snowmanxx < 0) {
         this.a(-_snowmanxx);
      }
   }

   private void a(int var1) {
      this.a(this.m() + (double)_snowman);
   }

   public double m() {
      return this.o;
   }

   public void a(double var1) {
      this.o = afm.a(_snowman, 0.0, (double)this.n());
   }

   public int n() {
      return Math.max(0, this.c() - (this.j - this.i - 4));
   }

   protected void b(double var1, double var3, int var5) {
      this.r = _snowman == 0 && _snowman >= (double)this.e() && _snowman < (double)(this.e() + 6);
   }

   protected int e() {
      return this.d / 2 + 124;
   }

   @Override
   public boolean a(double var1, double var3, int var5) {
      this.b(_snowman, _snowman, _snowman);
      if (!this.b(_snowman, _snowman)) {
         return false;
      } else {
         E _snowman = this.a(_snowman, _snowman);
         if (_snowman != null) {
            if (_snowman.a(_snowman, _snowman, _snowman)) {
               this.a(_snowman);
               this.b_(true);
               return true;
            }
         } else if (_snowman == 0) {
            this.a((int)(_snowman - (double)(this.l + this.d / 2 - this.d() / 2)), (int)(_snowman - (double)this.i) + (int)this.m() - 4);
            return true;
         }

         return this.r;
      }
   }

   @Override
   public boolean c(double var1, double var3, int var5) {
      if (this.i() != null) {
         this.i().c(_snowman, _snowman, _snowman);
      }

      return false;
   }

   @Override
   public boolean a(double var1, double var3, int var5, double var6, double var8) {
      if (super.a(_snowman, _snowman, _snowman, _snowman, _snowman)) {
         return true;
      } else if (_snowman == 0 && this.r) {
         if (_snowman < (double)this.i) {
            this.a(0.0);
         } else if (_snowman > (double)this.j) {
            this.a((double)this.n());
         } else {
            double _snowman = (double)Math.max(1, this.n());
            int _snowmanx = this.j - this.i;
            int _snowmanxx = afm.a((int)((float)(_snowmanx * _snowmanx) / (float)this.c()), 32, _snowmanx - 8);
            double _snowmanxxx = Math.max(1.0, _snowman / (double)(_snowmanx - _snowmanxx));
            this.a(this.m() + _snowman * _snowmanxxx);
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean a(double var1, double var3, double var5) {
      this.a(this.m() - _snowman * (double)this.c / 2.0);
      return true;
   }

   @Override
   public boolean a(int var1, int var2, int var3) {
      if (super.a(_snowman, _snowman, _snowman)) {
         return true;
      } else if (_snowman == 264) {
         this.a(dlf.b.b);
         return true;
      } else if (_snowman == 265) {
         this.a(dlf.b.a);
         return true;
      } else {
         return false;
      }
   }

   protected void a(dlf.b var1) {
      this.a(_snowman, var0 -> true);
   }

   protected void p() {
      E _snowman = this.h();
      if (_snowman != null) {
         this.a(_snowman);
         this.d(_snowman);
      }
   }

   protected void a(dlf.b var1, Predicate<E> var2) {
      int _snowman = _snowman == dlf.b.a ? -1 : 1;
      if (!this.au_().isEmpty()) {
         int _snowmanx = this.au_().indexOf(this.h());

         while (true) {
            int _snowmanxx = afm.a(_snowmanx + _snowman, 0, this.l() - 1);
            if (_snowmanx == _snowmanxx) {
               break;
            }

            E _snowmanxxx = this.au_().get(_snowmanxx);
            if (_snowman.test(_snowmanxxx)) {
               this.a(_snowmanxxx);
               this.d(_snowmanxxx);
               break;
            }

            _snowmanx = _snowmanxx;
         }
      }
   }

   @Override
   public boolean b(double var1, double var3) {
      return _snowman >= (double)this.i && _snowman <= (double)this.j && _snowman >= (double)this.l && _snowman <= (double)this.k;
   }

   protected void a(dfm var1, int var2, int var3, int var4, int var5, float var6) {
      int _snowman = this.l();
      dfo _snowmanx = dfo.a();
      dfh _snowmanxx = _snowmanx.c();

      for (int _snowmanxxx = 0; _snowmanxxx < _snowman; _snowmanxxx++) {
         int _snowmanxxxx = this.h(_snowmanxxx);
         int _snowmanxxxxx = this.b(_snowmanxxx);
         if (_snowmanxxxxx >= this.i && _snowmanxxxx <= this.j) {
            int _snowmanxxxxxx = _snowman + _snowmanxxx * this.c + this.n;
            int _snowmanxxxxxxx = this.c - 4;
            E _snowmanxxxxxxxx = this.e(_snowmanxxx);
            int _snowmanxxxxxxxxx = this.d();
            if (this.p && this.f(_snowmanxxx)) {
               int _snowmanxxxxxxxxxx = this.l + this.d / 2 - _snowmanxxxxxxxxx / 2;
               int _snowmanxxxxxxxxxxx = this.l + this.d / 2 + _snowmanxxxxxxxxx / 2;
               RenderSystem.disableTexture();
               float _snowmanxxxxxxxxxxxx = this.b() ? 1.0F : 0.5F;
               RenderSystem.color4f(_snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, 1.0F);
               _snowmanxx.a(7, dfk.k);
               _snowmanxx.a((double)_snowmanxxxxxxxxxx, (double)(_snowmanxxxxxx + _snowmanxxxxxxx + 2), 0.0).d();
               _snowmanxx.a((double)_snowmanxxxxxxxxxxx, (double)(_snowmanxxxxxx + _snowmanxxxxxxx + 2), 0.0).d();
               _snowmanxx.a((double)_snowmanxxxxxxxxxxx, (double)(_snowmanxxxxxx - 2), 0.0).d();
               _snowmanxx.a((double)_snowmanxxxxxxxxxx, (double)(_snowmanxxxxxx - 2), 0.0).d();
               _snowmanx.b();
               RenderSystem.color4f(0.0F, 0.0F, 0.0F, 1.0F);
               _snowmanxx.a(7, dfk.k);
               _snowmanxx.a((double)(_snowmanxxxxxxxxxx + 1), (double)(_snowmanxxxxxx + _snowmanxxxxxxx + 1), 0.0).d();
               _snowmanxx.a((double)(_snowmanxxxxxxxxxxx - 1), (double)(_snowmanxxxxxx + _snowmanxxxxxxx + 1), 0.0).d();
               _snowmanxx.a((double)(_snowmanxxxxxxxxxxx - 1), (double)(_snowmanxxxxxx - 1), 0.0).d();
               _snowmanxx.a((double)(_snowmanxxxxxxxxxx + 1), (double)(_snowmanxxxxxx - 1), 0.0).d();
               _snowmanx.b();
               RenderSystem.enableTexture();
            }

            int _snowmanxxxxxxxxxx = this.q();
            _snowmanxxxxxxxx.a(
               _snowman,
               _snowmanxxx,
               _snowmanxxxx,
               _snowmanxxxxxxxxxx,
               _snowmanxxxxxxxxx,
               _snowmanxxxxxxx,
               _snowman,
               _snowman,
               this.b((double)_snowman, (double)_snowman) && Objects.equals(this.a((double)_snowman, (double)_snowman), _snowmanxxxxxxxx),
               _snowman
            );
         }
      }
   }

   public int q() {
      return this.l + this.d / 2 - this.d() / 2 + 2;
   }

   public int r() {
      return this.q() + this.d();
   }

   protected int h(int var1) {
      return this.i + 4 - (int)this.m() + _snowman * this.c + this.n;
   }

   private int b(int var1) {
      return this.h(_snowman) + this.c;
   }

   protected boolean b() {
      return false;
   }

   protected E i(int var1) {
      E _snowman = this.a.get(_snowman);
      return this.e(this.a.get(_snowman)) ? _snowman : null;
   }

   protected boolean e(E var1) {
      boolean _snowman = this.a.remove(_snowman);
      if (_snowman && _snowman == this.h()) {
         this.a(null);
      }

      return _snowman;
   }

   private void f(dlf.a<E> var1) {
      _snowman.a = this;
   }

   public abstract static class a<E extends dlf.a<E>> implements dmi {
      @Deprecated
      private dlf<E> a;

      public a() {
      }

      public abstract void a(dfm var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10);

      @Override
      public boolean b(double var1, double var3) {
         return Objects.equals(this.a.a(_snowman, _snowman), this);
      }
   }

   public static enum b {
      a,
      b;

      private b() {
      }
   }

   class c extends AbstractList<E> {
      private final List<E> b = Lists.newArrayList();

      private c() {
      }

      public E a(int var1) {
         return this.b.get(_snowman);
      }

      @Override
      public int size() {
         return this.b.size();
      }

      public E a(int var1, E var2) {
         E _snowman = this.b.set(_snowman, _snowman);
         dlf.this.f(_snowman);
         return _snowman;
      }

      public void b(int var1, E var2) {
         this.b.add(_snowman, _snowman);
         dlf.this.f(_snowman);
      }

      public E b(int var1) {
         return this.b.remove(_snowman);
      }
   }
}
