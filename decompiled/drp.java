import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.annotation.Nullable;

public class drp extends dkw implements dmf, dmi, dru, uz<bon> {
   protected static final vk a = new vk("textures/gui/recipe_book.png");
   private static final nr i = new of("gui.recipebook.search_hint").a(k.u).a(k.h);
   private static final nr j = new of("gui.recipebook.toggleRecipes.craftable");
   private static final nr k = new of("gui.recipebook.toggleRecipes.all");
   private int l;
   private int m;
   private int n;
   protected final drn b = new drn();
   private final List<drr> o = Lists.newArrayList();
   private drr p;
   protected dma c;
   protected bjj<?> d;
   protected djz e;
   private dlq q;
   private String r = "";
   private djm s;
   private final drq t = new drq();
   private final bfy u = new bfy();
   private int v;
   private boolean w;

   public drp() {
   }

   public void a(int var1, int var2, djz var3, boolean var4, bjj<?> var5) {
      this.e = _snowman;
      this.m = _snowman;
      this.n = _snowman;
      this.d = _snowman;
      _snowman.s.bp = _snowman;
      this.s = _snowman.s.F();
      this.v = _snowman.s.bm.l();
      if (this.f()) {
         this.a(_snowman);
      }

      _snowman.m.a(true);
   }

   public void a(boolean var1) {
      this.l = _snowman ? 0 : 86;
      int _snowman = (this.m - 147) / 2 - this.l;
      int _snowmanx = (this.n - 166) / 2;
      this.u.a();
      this.e.s.bm.a(this.u);
      this.d.a(this.u);
      String _snowmanxx = this.q != null ? this.q.b() : "";
      this.q = new dlq(this.e.g, _snowman + 25, _snowmanx + 14, 80, 9 + 5, new of("itemGroup.search"));
      this.q.k(50);
      this.q.f(false);
      this.q.i(true);
      this.q.l(16777215);
      this.q.a(_snowmanxx);
      this.t.a(this.e, _snowman, _snowmanx);
      this.t.a(this);
      this.c = new dma(_snowman + 110, _snowmanx + 12, 26, 16, this.s.a(this.d));
      this.a();
      this.o.clear();

      for (dkg _snowmanxxx : dkg.a(this.d.m())) {
         this.o.add(new drr(_snowmanxxx));
      }

      if (this.p != null) {
         this.p = this.o.stream().filter(var1x -> var1x.b().equals(this.p.b())).findFirst().orElse(null);
      }

      if (this.p == null) {
         this.p = this.o.get(0);
      }

      this.p.e(true);
      this.d(false);
      this.b();
   }

   @Override
   public boolean c_(boolean var1) {
      return false;
   }

   protected void a() {
      this.c.a(152, 41, 28, 18, a);
   }

   public void d() {
      this.q = null;
      this.p = null;
      this.e.m.a(false);
   }

   public int a(boolean var1, int var2, int var3) {
      int _snowman;
      if (this.f() && !_snowman) {
         _snowman = 177 + (_snowman - _snowman - 200) / 2;
      } else {
         _snowman = (_snowman - _snowman) / 2;
      }

      return _snowman;
   }

   public void e() {
      this.c(!this.f());
   }

   public boolean f() {
      return this.s.a(this.d.m());
   }

   protected void c(boolean var1) {
      this.s.a(this.d.m(), _snowman);
      if (!_snowman) {
         this.t.c();
      }

      this.i();
   }

   public void a(@Nullable bjr var1) {
      if (_snowman != null && _snowman.d < this.d.i()) {
         this.b.a();
         if (this.f()) {
            this.j();
         }
      }
   }

   private void d(boolean var1) {
      List<drt> _snowman = this.s.a(this.p.b());
      _snowman.forEach(var1x -> var1x.a(this.u, this.d.g(), this.d.h(), this.s));
      List<drt> _snowmanx = Lists.newArrayList(_snowman);
      _snowmanx.removeIf(var0 -> !var0.a());
      _snowmanx.removeIf(var0 -> !var0.c());
      String _snowmanxx = this.q.b();
      if (!_snowmanxx.isEmpty()) {
         ObjectSet<drt> _snowmanxxx = new ObjectLinkedOpenHashSet(this.e.a(enb.c).a(_snowmanxx.toLowerCase(Locale.ROOT)));
         _snowmanx.removeIf(var1x -> !_snowman.contains(var1x));
      }

      if (this.s.a(this.d)) {
         _snowmanx.removeIf(var0 -> !var0.b());
      }

      this.t.a(_snowmanx, _snowman);
   }

   private void b() {
      int _snowman = (this.m - 147) / 2 - this.l - 30;
      int _snowmanx = (this.n - 166) / 2 + 3;
      int _snowmanxx = 27;
      int _snowmanxxx = 0;

      for (drr _snowmanxxxx : this.o) {
         dkg _snowmanxxxxx = _snowmanxxxx.b();
         if (_snowmanxxxxx == dkg.a || _snowmanxxxxx == dkg.f) {
            _snowmanxxxx.p = true;
            _snowmanxxxx.a(_snowman, _snowmanx + 27 * _snowmanxxx++);
         } else if (_snowmanxxxx.a(this.s)) {
            _snowmanxxxx.a(_snowman, _snowmanx + 27 * _snowmanxxx++);
            _snowmanxxxx.a(this.e);
         }
      }
   }

   public void g() {
      if (this.f()) {
         if (this.v != this.e.s.bm.l()) {
            this.j();
            this.v = this.e.s.bm.l();
         }

         this.q.a();
      }
   }

   private void j() {
      this.u.a();
      this.e.s.bm.a(this.u);
      this.d.a(this.u);
      this.d(false);
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      if (this.f()) {
         RenderSystem.pushMatrix();
         RenderSystem.translatef(0.0F, 0.0F, 100.0F);
         this.e.M().a(a);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         int _snowman = (this.m - 147) / 2 - this.l;
         int _snowmanx = (this.n - 166) / 2;
         this.b(_snowman, _snowman, _snowmanx, 1, 1, 147, 166);
         if (!this.q.j() && this.q.b().isEmpty()) {
            b(_snowman, this.e.g, i, _snowman + 25, _snowmanx + 14, -1);
         } else {
            this.q.a(_snowman, _snowman, _snowman, _snowman);
         }

         for (drr _snowmanxx : this.o) {
            _snowmanxx.a(_snowman, _snowman, _snowman, _snowman);
         }

         this.c.a(_snowman, _snowman, _snowman, _snowman);
         this.t.a(_snowman, _snowman, _snowmanx, _snowman, _snowman, _snowman);
         RenderSystem.popMatrix();
      }
   }

   public void c(dfm var1, int var2, int var3, int var4, int var5) {
      if (this.f()) {
         this.t.a(_snowman, _snowman, _snowman);
         if (this.c.g()) {
            nr _snowman = this.k();
            if (this.e.y != null) {
               this.e.y.b(_snowman, _snowman, _snowman, _snowman);
            }
         }

         this.d(_snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }

   private nr k() {
      return this.c.a() ? this.c() : k;
   }

   protected nr c() {
      return j;
   }

   private void d(dfm var1, int var2, int var3, int var4, int var5) {
      bmb _snowman = null;

      for (int _snowmanx = 0; _snowmanx < this.b.b(); _snowmanx++) {
         drn.a _snowmanxx = this.b.a(_snowmanx);
         int _snowmanxxx = _snowmanxx.a() + _snowman;
         int _snowmanxxxx = _snowmanxx.b() + _snowman;
         if (_snowman >= _snowmanxxx && _snowman >= _snowmanxxxx && _snowman < _snowmanxxx + 16 && _snowman < _snowmanxxxx + 16) {
            _snowman = _snowmanxx.c();
         }
      }

      if (_snowman != null && this.e.y != null) {
         this.e.y.b(_snowman, this.e.y.a(_snowman), _snowman, _snowman);
      }
   }

   public void a(dfm var1, int var2, int var3, boolean var4, float var5) {
      this.b.a(_snowman, this.e, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public boolean a(double var1, double var3, int var5) {
      if (this.f() && !this.e.s.a_()) {
         if (this.t.a(_snowman, _snowman, _snowman, (this.m - 147) / 2 - this.l, (this.n - 166) / 2, 147, 166)) {
            boq<?> _snowman = this.t.a();
            drt _snowmanx = this.t.b();
            if (_snowman != null && _snowmanx != null) {
               if (!_snowmanx.a(_snowman) && this.b.c() == _snowman) {
                  return false;
               }

               this.b.a();
               this.e.q.a(this.e.s.bp.b, _snowman, dot.y());
               if (!this.n()) {
                  this.c(false);
               }
            }

            return true;
         } else if (this.q.a(_snowman, _snowman, _snowman)) {
            return true;
         } else if (this.c.a(_snowman, _snowman, _snowman)) {
            boolean _snowman = this.l();
            this.c.e(_snowman);
            this.i();
            this.d(false);
            return true;
         } else {
            for (drr _snowman : this.o) {
               if (_snowman.a(_snowman, _snowman, _snowman)) {
                  if (this.p != _snowman) {
                     this.p.e(false);
                     this.p = _snowman;
                     this.p.e(true);
                     this.d(true);
                  }

                  return true;
               }
            }

            return false;
         }
      } else {
         return false;
      }
   }

   private boolean l() {
      bjk _snowman = this.d.m();
      boolean _snowmanx = !this.s.b(_snowman);
      this.s.b(_snowman, _snowmanx);
      return _snowmanx;
   }

   public boolean a(double var1, double var3, int var5, int var6, int var7, int var8, int var9) {
      if (!this.f()) {
         return true;
      } else {
         boolean _snowman = _snowman < (double)_snowman || _snowman < (double)_snowman || _snowman >= (double)(_snowman + _snowman) || _snowman >= (double)(_snowman + _snowman);
         boolean _snowmanx = (double)(_snowman - 147) < _snowman && _snowman < (double)_snowman && (double)_snowman < _snowman && _snowman < (double)(_snowman + _snowman);
         return _snowman && !_snowmanx && !this.p.g();
      }
   }

   @Override
   public boolean a(int var1, int var2, int var3) {
      this.w = false;
      if (!this.f() || this.e.s.a_()) {
         return false;
      } else if (_snowman == 256 && !this.n()) {
         this.c(false);
         return true;
      } else if (this.q.a(_snowman, _snowman, _snowman)) {
         this.m();
         return true;
      } else if (this.q.j() && this.q.p() && _snowman != 256) {
         return true;
      } else if (this.e.k.as.a(_snowman, _snowman) && !this.q.j()) {
         this.w = true;
         this.q.e(true);
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean b(int var1, int var2, int var3) {
      this.w = false;
      return dmi.super.b(_snowman, _snowman, _snowman);
   }

   @Override
   public boolean a(char var1, int var2) {
      if (this.w) {
         return false;
      } else if (!this.f() || this.e.s.a_()) {
         return false;
      } else if (this.q.a(_snowman, _snowman)) {
         this.m();
         return true;
      } else {
         return dmi.super.a(_snowman, _snowman);
      }
   }

   @Override
   public boolean b(double var1, double var3) {
      return false;
   }

   private void m() {
      String _snowman = this.q.b().toLowerCase(Locale.ROOT);
      this.a(_snowman);
      if (!_snowman.equals(this.r)) {
         this.d(false);
         this.r = _snowman;
      }
   }

   private void a(String var1) {
      if ("excitedze".equals(_snowman)) {
         ekz _snowman = this.e.R();
         eky _snowmanx = _snowman.a("en_pt");
         if (_snowman.b().a(_snowmanx) == 0) {
            return;
         }

         _snowman.a(_snowmanx);
         this.e.k.aV = _snowmanx.getCode();
         this.e.j();
         this.e.k.b();
      }
   }

   private boolean n() {
      return this.l == 86;
   }

   public void h() {
      this.b();
      if (this.f()) {
         this.d(false);
      }
   }

   @Override
   public void a(List<boq<?>> var1) {
      for (boq<?> _snowman : _snowman) {
         this.e.s.a(_snowman);
      }
   }

   public void a(boq<?> var1, List<bjr> var2) {
      bmb _snowman = _snowman.c();
      this.b.a(_snowman);
      this.b.a(bon.a(_snowman), _snowman.get(0).e, _snowman.get(0).f);
      this.a(this.d.g(), this.d.h(), this.d.f(), _snowman, _snowman.a().iterator(), 0);
   }

   @Override
   public void a(Iterator<bon> var1, int var2, int var3, int var4, int var5) {
      bon _snowman = _snowman.next();
      if (!_snowman.d()) {
         bjr _snowmanx = this.d.a.get(_snowman);
         this.b.a(_snowman, _snowmanx.e, _snowmanx.f);
      }
   }

   protected void i() {
      if (this.e.w() != null) {
         bjk _snowman = this.d.m();
         boolean _snowmanx = this.s.a().a(_snowman);
         boolean _snowmanxx = this.s.a().b(_snowman);
         this.e.w().a(new tc(_snowman, _snowmanx, _snowmanxx));
      }
   }
}
