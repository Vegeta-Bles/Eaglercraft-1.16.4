import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class dqc extends dqe<dqc.b> {
   private static final vk B = new vk("textures/gui/container/creative_inventory/tabs.png");
   private static final apa C = new apa(45);
   private static final nr D = new of("inventory.binSlot");
   private static int E = bks.b.a();
   private float F;
   private boolean G;
   private dlq H;
   @Nullable
   private List<bjr> I;
   @Nullable
   private bjr J;
   private dqb K;
   private boolean L;
   private boolean M;
   private final Map<vk, ael<blx>> N = Maps.newTreeMap();

   public dqc(bfw var1) {
      super(new dqc.b(_snowman), _snowman.bm, oe.d);
      _snowman.bp = this.t;
      this.n = true;
      this.c = 136;
      this.b = 195;
   }

   @Override
   public void d() {
      if (!this.i.q.g()) {
         this.i.a(new dql(this.i.s));
      } else if (this.H != null) {
         this.H.a();
      }
   }

   @Override
   protected void a(@Nullable bjr var1, int var2, int var3, bik var4) {
      if (this.a(_snowman)) {
         this.H.l();
         this.H.n(0);
      }

      boolean _snowman = _snowman == bik.b;
      _snowman = _snowman == -999 && _snowman == bik.a ? bik.e : _snowman;
      if (_snowman == null && E != bks.n.a() && _snowman != bik.f) {
         bfv _snowmanx = this.i.s.bm;
         if (!_snowmanx.m().a() && this.M) {
            if (_snowman == 0) {
               this.i.s.a(_snowmanx.m(), true);
               this.i.q.a(_snowmanx.m());
               _snowmanx.g(bmb.b);
            }

            if (_snowman == 1) {
               bmb _snowmanxx = _snowmanx.m().a(1);
               this.i.s.a(_snowmanxx, true);
               this.i.q.a(_snowmanxx);
            }
         }
      } else {
         if (_snowman != null && !_snowman.a(this.i.s)) {
            return;
         }

         if (_snowman == this.J && _snowman) {
            for (int _snowmanx = 0; _snowmanx < this.i.s.bo.b().size(); _snowmanx++) {
               this.i.q.a(bmb.b, _snowmanx);
            }
         } else if (E == bks.n.a()) {
            if (_snowman == this.J) {
               this.i.s.bm.g(bmb.b);
            } else if (_snowman == bik.e && _snowman != null && _snowman.f()) {
               bmb _snowmanx = _snowman.a(_snowman == 0 ? 1 : _snowman.e().c());
               bmb _snowmanxx = _snowman.e();
               this.i.s.a(_snowmanx, true);
               this.i.q.a(_snowmanx);
               this.i.q.a(_snowmanxx, ((dqc.c)_snowman).a.d);
            } else if (_snowman == bik.e && !this.i.s.bm.m().a()) {
               this.i.s.a(this.i.s.bm.m(), true);
               this.i.q.a(this.i.s.bm.m());
               this.i.s.bm.g(bmb.b);
            } else {
               this.i.s.bo.a(_snowman == null ? _snowman : ((dqc.c)_snowman).a.d, _snowman, _snowman, this.i.s);
               this.i.s.bo.c();
            }
         } else if (_snowman != bik.f && _snowman.c == C) {
            bfv _snowmanx = this.i.s.bm;
            bmb _snowmanxx = _snowmanx.m();
            bmb _snowmanxxx = _snowman.e();
            if (_snowman == bik.c) {
               if (!_snowmanxxx.a()) {
                  bmb _snowmanxxxx = _snowmanxxx.i();
                  _snowmanxxxx.e(_snowmanxxxx.c());
                  this.i.s.bm.a(_snowman, _snowmanxxxx);
                  this.i.s.bo.c();
               }

               return;
            }

            if (_snowman == bik.d) {
               if (_snowmanx.m().a() && _snowman.f()) {
                  bmb _snowmanxxxx = _snowman.e().i();
                  _snowmanxxxx.e(_snowmanxxxx.c());
                  _snowmanx.g(_snowmanxxxx);
               }

               return;
            }

            if (_snowman == bik.e) {
               if (!_snowmanxxx.a()) {
                  bmb _snowmanxxxx = _snowmanxxx.i();
                  _snowmanxxxx.e(_snowman == 0 ? 1 : _snowmanxxxx.c());
                  this.i.s.a(_snowmanxxxx, true);
                  this.i.q.a(_snowmanxxxx);
               }

               return;
            }

            if (!_snowmanxx.a() && !_snowmanxxx.a() && _snowmanxx.a(_snowmanxxx) && bmb.a(_snowmanxx, _snowmanxxx)) {
               if (_snowman == 0) {
                  if (_snowman) {
                     _snowmanxx.e(_snowmanxx.c());
                  } else if (_snowmanxx.E() < _snowmanxx.c()) {
                     _snowmanxx.f(1);
                  }
               } else {
                  _snowmanxx.g(1);
               }
            } else if (!_snowmanxxx.a() && _snowmanxx.a()) {
               _snowmanx.g(_snowmanxxx.i());
               _snowmanxx = _snowmanx.m();
               if (_snowman) {
                  _snowmanxx.e(_snowmanxx.c());
               }
            } else if (_snowman == 0) {
               _snowmanx.g(bmb.b);
            } else {
               _snowmanx.m().g(1);
            }
         } else if (this.t != null) {
            bmb _snowmanxxxx = _snowman == null ? bmb.b : this.t.a(_snowman.d).e();
            this.t.a(_snowman == null ? _snowman : _snowman.d, _snowman, _snowman, this.i.s);
            if (bic.c(_snowman) == 2) {
               for (int _snowmanxxxxx = 0; _snowmanxxxxx < 9; _snowmanxxxxx++) {
                  this.i.q.a(this.t.a(45 + _snowmanxxxxx).e(), 36 + _snowmanxxxxx);
               }
            } else if (_snowman != null) {
               bmb _snowmanxxxxx = this.t.a(_snowman.d).e();
               this.i.q.a(_snowmanxxxxx, _snowman.d - this.t.a.size() + 9 + 36);
               int _snowmanxxxxxx = 45 + _snowman;
               if (_snowman == bik.c) {
                  this.i.q.a(_snowmanxxxx, _snowmanxxxxxx - this.t.a.size() + 9 + 36);
               } else if (_snowman == bik.e && !_snowmanxxxx.a()) {
                  bmb _snowmanxxxxxxx = _snowmanxxxx.i();
                  _snowmanxxxxxxx.e(_snowman == 0 ? 1 : _snowmanxxxxxxx.c());
                  this.i.s.a(_snowmanxxxxxxx, true);
                  this.i.q.a(_snowmanxxxxxxx);
               }

               this.i.s.bo.c();
            }
         }
      }
   }

   private boolean a(@Nullable bjr var1) {
      return _snowman != null && _snowman.c == C;
   }

   @Override
   protected void i() {
      int _snowman = this.w;
      super.i();
      if (this.H != null && this.w != _snowman) {
         this.H.p(this.w + 82);
      }
   }

   @Override
   protected void b() {
      if (this.i.q.g()) {
         super.b();
         this.i.m.a(true);
         this.H = new dlq(this.o, this.w + 82, this.x + 6, 80, 9, new of("itemGroup.search"));
         this.H.k(50);
         this.H.f(false);
         this.H.i(false);
         this.H.l(16777215);
         this.e.add(this.H);
         int _snowman = E;
         E = -1;
         this.a(bks.a[_snowman]);
         this.i.s.bo.b(this.K);
         this.K = new dqb(this.i);
         this.i.s.bo.a(this.K);
      } else {
         this.i.a(new dql(this.i.s));
      }
   }

   @Override
   public void a(djz var1, int var2, int var3) {
      String _snowman = this.H.b();
      this.b(_snowman, _snowman, _snowman);
      this.H.a(_snowman);
      if (!this.H.b().isEmpty()) {
         this.m();
      }
   }

   @Override
   public void e() {
      super.e();
      if (this.i.s != null && this.i.s.bm != null) {
         this.i.s.bo.b(this.K);
      }

      this.i.m.a(false);
   }

   @Override
   public boolean a(char var1, int var2) {
      if (this.L) {
         return false;
      } else if (E != bks.g.a()) {
         return false;
      } else {
         String _snowman = this.H.b();
         if (this.H.a(_snowman, _snowman)) {
            if (!Objects.equals(_snowman, this.H.b())) {
               this.m();
            }

            return true;
         } else {
            return false;
         }
      }
   }

   @Override
   public boolean a(int var1, int var2, int var3) {
      this.L = false;
      if (E != bks.g.a()) {
         if (this.i.k.as.a(_snowman, _snowman)) {
            this.L = true;
            this.a(bks.g);
            return true;
         } else {
            return super.a(_snowman, _snowman, _snowman);
         }
      } else {
         boolean _snowman = !this.a(this.v) || this.v.f();
         boolean _snowmanx = deo.a(_snowman, _snowman).e().isPresent();
         if (_snowman && _snowmanx && this.b(_snowman, _snowman)) {
            this.L = true;
            return true;
         } else {
            String _snowmanxx = this.H.b();
            if (this.H.a(_snowman, _snowman, _snowman)) {
               if (!Objects.equals(_snowmanxx, this.H.b())) {
                  this.m();
               }

               return true;
            } else {
               return this.H.j() && this.H.p() && _snowman != 256 ? true : super.a(_snowman, _snowman, _snowman);
            }
         }
      }
   }

   @Override
   public boolean b(int var1, int var2, int var3) {
      this.L = false;
      return super.b(_snowman, _snowman, _snowman);
   }

   private void m() {
      this.t.c.clear();
      this.N.clear();
      String _snowman = this.H.b();
      if (_snowman.isEmpty()) {
         for (blx _snowmanx : gm.T) {
            _snowmanx.a(bks.g, this.t.c);
         }
      } else {
         enc<bmb> _snowmanx;
         if (_snowman.startsWith("#")) {
            _snowman = _snowman.substring(1);
            _snowmanx = this.i.a(enb.b);
            this.b(_snowman);
         } else {
            _snowmanx = this.i.a(enb.a);
         }

         this.t.c.addAll(_snowmanx.a(_snowman.toLowerCase(Locale.ROOT)));
      }

      this.F = 0.0F;
      this.t.a(0.0F);
   }

   private void b(String var1) {
      int _snowman = _snowman.indexOf(58);
      Predicate<vk> _snowmanx;
      if (_snowman == -1) {
         _snowmanx = var1x -> var1x.a().contains(_snowman);
      } else {
         String _snowmanxx = _snowman.substring(0, _snowman).trim();
         String _snowmanxxx = _snowman.substring(_snowman + 1).trim();
         _snowmanx = var2x -> var2x.b().contains(_snowman) && var2x.a().contains(_snowman);
      }

      aem<blx> _snowmanxx = aeg.a();
      _snowmanxx.b().stream().filter(_snowmanx).forEach(var2x -> {
         ael var10000 = this.N.put(var2x, _snowman.a(var2x));
      });
   }

   @Override
   protected void b(dfm var1, int var2, int var3) {
      bks _snowman = bks.a[E];
      if (_snowman.g()) {
         RenderSystem.disableBlend();
         this.o.b(_snowman, _snowman.c(), 8.0F, 6.0F, 4210752);
      }
   }

   @Override
   public boolean a(double var1, double var3, int var5) {
      if (_snowman == 0) {
         double _snowman = _snowman - (double)this.w;
         double _snowmanx = _snowman - (double)this.x;

         for (bks _snowmanxx : bks.a) {
            if (this.a(_snowmanxx, _snowman, _snowmanx)) {
               return true;
            }
         }

         if (E != bks.n.a() && this.a(_snowman, _snowman)) {
            this.G = this.n();
            return true;
         }
      }

      return super.a(_snowman, _snowman, _snowman);
   }

   @Override
   public boolean c(double var1, double var3, int var5) {
      if (_snowman == 0) {
         double _snowman = _snowman - (double)this.w;
         double _snowmanx = _snowman - (double)this.x;
         this.G = false;

         for (bks _snowmanxx : bks.a) {
            if (this.a(_snowmanxx, _snowman, _snowmanx)) {
               this.a(_snowmanxx);
               return true;
            }
         }
      }

      return super.c(_snowman, _snowman, _snowman);
   }

   private boolean n() {
      return E != bks.n.a() && bks.a[E].i() && this.t.e();
   }

   private void a(bks var1) {
      int _snowman = E;
      E = _snowman.a();
      this.y.clear();
      this.t.c.clear();
      if (_snowman == bks.m) {
         djv _snowmanx = this.i.aq();

         for (int _snowmanxx = 0; _snowmanxx < 9; _snowmanxx++) {
            dzo _snowmanxxx = _snowmanx.a(_snowmanxx);
            if (_snowmanxxx.isEmpty()) {
               for (int _snowmanxxxx = 0; _snowmanxxxx < 9; _snowmanxxxx++) {
                  if (_snowmanxxxx == _snowmanxx) {
                     bmb _snowmanxxxxx = new bmb(bmd.mb);
                     _snowmanxxxxx.a("CustomCreativeLock");
                     nr _snowmanxxxxxx = this.i.k.aC[_snowmanxx].j();
                     nr _snowmanxxxxxxx = this.i.k.aD.j();
                     _snowmanxxxxx.a(new of("inventory.hotbarInfo", _snowmanxxxxxxx, _snowmanxxxxxx));
                     this.t.c.add(_snowmanxxxxx);
                  } else {
                     this.t.c.add(bmb.b);
                  }
               }
            } else {
               this.t.c.addAll(_snowmanxxx);
            }
         }
      } else if (_snowman != bks.g) {
         _snowman.a(this.t.c);
      }

      if (_snowman == bks.n) {
         bic _snowmanx = this.i.s.bo;
         if (this.I == null) {
            this.I = ImmutableList.copyOf(this.t.a);
         }

         this.t.a.clear();

         for (int _snowmanxxx = 0; _snowmanxxx < _snowmanx.a.size(); _snowmanxxx++) {
            int _snowmanxxxxx;
            int _snowmanxxxxxx;
            if (_snowmanxxx >= 5 && _snowmanxxx < 9) {
               int _snowmanxxxxxxx = _snowmanxxx - 5;
               int _snowmanxxxxxxxx = _snowmanxxxxxxx / 2;
               int _snowmanxxxxxxxxx = _snowmanxxxxxxx % 2;
               _snowmanxxxxx = 54 + _snowmanxxxxxxxx * 54;
               _snowmanxxxxxx = 6 + _snowmanxxxxxxxxx * 27;
            } else if (_snowmanxxx >= 0 && _snowmanxxx < 5) {
               _snowmanxxxxx = -2000;
               _snowmanxxxxxx = -2000;
            } else if (_snowmanxxx == 45) {
               _snowmanxxxxx = 35;
               _snowmanxxxxxx = 20;
            } else {
               int _snowmanxxxxxxx = _snowmanxxx - 9;
               int _snowmanxxxxxxxx = _snowmanxxxxxxx % 9;
               int _snowmanxxxxxxxxx = _snowmanxxxxxxx / 9;
               _snowmanxxxxx = 9 + _snowmanxxxxxxxx * 18;
               if (_snowmanxxx >= 36) {
                  _snowmanxxxxxx = 112;
               } else {
                  _snowmanxxxxxx = 54 + _snowmanxxxxxxxxx * 18;
               }
            }

            bjr _snowmanxxxxxxx = new dqc.c(_snowmanx.a.get(_snowmanxxx), _snowmanxxx, _snowmanxxxxx, _snowmanxxxxxx);
            this.t.a.add(_snowmanxxxxxxx);
         }

         this.J = new bjr(C, 0, 173, 112);
         this.t.a.add(this.J);
      } else if (_snowman == bks.n.a()) {
         this.t.a.clear();
         this.t.a.addAll(this.I);
         this.I = null;
      }

      if (this.H != null) {
         if (_snowman == bks.g) {
            this.H.i(true);
            this.H.h(false);
            this.H.e(true);
            if (_snowman != _snowman.a()) {
               this.H.a("");
            }

            this.m();
         } else {
            this.H.i(false);
            this.H.h(true);
            this.H.e(false);
            this.H.a("");
         }
      }

      this.F = 0.0F;
      this.t.a(0.0F);
   }

   @Override
   public boolean a(double var1, double var3, double var5) {
      if (!this.n()) {
         return false;
      } else {
         int _snowman = (this.t.c.size() + 9 - 1) / 9 - 5;
         this.F = (float)((double)this.F - _snowman / (double)_snowman);
         this.F = afm.a(this.F, 0.0F, 1.0F);
         this.t.a(this.F);
         return true;
      }
   }

   @Override
   protected boolean a(double var1, double var3, int var5, int var6, int var7) {
      boolean _snowman = _snowman < (double)_snowman || _snowman < (double)_snowman || _snowman >= (double)(_snowman + this.b) || _snowman >= (double)(_snowman + this.c);
      this.M = _snowman && !this.a(bks.a[E], _snowman, _snowman);
      return this.M;
   }

   protected boolean a(double var1, double var3) {
      int _snowman = this.w;
      int _snowmanx = this.x;
      int _snowmanxx = _snowman + 175;
      int _snowmanxxx = _snowmanx + 18;
      int _snowmanxxxx = _snowmanxx + 14;
      int _snowmanxxxxx = _snowmanxxx + 112;
      return _snowman >= (double)_snowmanxx && _snowman >= (double)_snowmanxxx && _snowman < (double)_snowmanxxxx && _snowman < (double)_snowmanxxxxx;
   }

   @Override
   public boolean a(double var1, double var3, int var5, double var6, double var8) {
      if (this.G) {
         int _snowman = this.x + 18;
         int _snowmanx = _snowman + 112;
         this.F = ((float)_snowman - (float)_snowman - 7.5F) / ((float)(_snowmanx - _snowman) - 15.0F);
         this.F = afm.a(this.F, 0.0F, 1.0F);
         this.t.a(this.F);
         return true;
      } else {
         return super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
      super.a(_snowman, _snowman, _snowman, _snowman);

      for (bks _snowman : bks.a) {
         if (this.a(_snowman, _snowman, _snowman, _snowman)) {
            break;
         }
      }

      if (this.J != null && E == bks.n.a() && this.a(this.J.e, this.J.f, 16, 16, (double)_snowman, (double)_snowman)) {
         this.b(_snowman, D, _snowman, _snowman);
      }

      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.a(_snowman, _snowman, _snowman);
   }

   @Override
   protected void a(dfm var1, bmb var2, int var3, int var4) {
      if (E == bks.g.a()) {
         List<nr> _snowman = _snowman.a(this.i.s, this.i.k.p ? bnl.a.b : bnl.a.a);
         List<nr> _snowmanx = Lists.newArrayList(_snowman);
         blx _snowmanxx = _snowman.b();
         bks _snowmanxxx = _snowmanxx.q();
         if (_snowmanxxx == null && _snowmanxx == bmd.pq) {
            Map<bps, Integer> _snowmanxxxx = bpu.a(_snowman);
            if (_snowmanxxxx.size() == 1) {
               bps _snowmanxxxxx = _snowmanxxxx.keySet().iterator().next();

               for (bks _snowmanxxxxxx : bks.a) {
                  if (_snowmanxxxxxx.a(_snowmanxxxxx.b)) {
                     _snowmanxxx = _snowmanxxxxxx;
                     break;
                  }
               }
            }
         }

         this.N.forEach((var2x, var3x) -> {
            if (var3x.a(_snowman)) {
               _snowman.add(1, new oe("#" + var2x).a(k.f));
            }
         });
         if (_snowmanxxx != null) {
            _snowmanx.add(1, _snowmanxxx.c().e().a(k.j));
         }

         this.b(_snowman, _snowmanx, _snowman, _snowman);
      } else {
         super.a(_snowman, _snowman, _snowman, _snowman);
      }
   }

   @Override
   protected void a(dfm var1, float var2, int var3, int var4) {
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      bks _snowman = bks.a[E];

      for (bks _snowmanx : bks.a) {
         this.i.M().a(B);
         if (_snowmanx.a() != E) {
            this.a(_snowman, _snowmanx);
         }
      }

      this.i.M().a(new vk("textures/gui/container/creative_inventory/tab_" + _snowman.f()));
      this.b(_snowman, this.w, this.x, 0, 0, this.b, this.c);
      this.H.a(_snowman, _snowman, _snowman, _snowman);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      int _snowmanxx = this.w + 175;
      int _snowmanxxx = this.x + 18;
      int _snowmanxxxx = _snowmanxxx + 112;
      this.i.M().a(B);
      if (_snowman.i()) {
         this.b(_snowman, _snowmanxx, _snowmanxxx + (int)((float)(_snowmanxxxx - _snowmanxxx - 17) * this.F), 232 + (this.n() ? 0 : 12), 0, 12, 15);
      }

      this.a(_snowman, _snowman);
      if (_snowman == bks.n) {
         dql.a(this.w + 88, this.x + 45, 20, (float)(this.w + 88 - _snowman), (float)(this.x + 45 - 30 - _snowman), this.i.s);
      }
   }

   protected boolean a(bks var1, double var2, double var4) {
      int _snowman = _snowman.k();
      int _snowmanx = 28 * _snowman;
      int _snowmanxx = 0;
      if (_snowman.m()) {
         _snowmanx = this.b - 28 * (6 - _snowman) + 2;
      } else if (_snowman > 0) {
         _snowmanx += _snowman;
      }

      if (_snowman.l()) {
         _snowmanxx -= 32;
      } else {
         _snowmanxx += this.c;
      }

      return _snowman >= (double)_snowmanx && _snowman <= (double)(_snowmanx + 28) && _snowman >= (double)_snowmanxx && _snowman <= (double)(_snowmanxx + 32);
   }

   protected boolean a(dfm var1, bks var2, int var3, int var4) {
      int _snowman = _snowman.k();
      int _snowmanx = 28 * _snowman;
      int _snowmanxx = 0;
      if (_snowman.m()) {
         _snowmanx = this.b - 28 * (6 - _snowman) + 2;
      } else if (_snowman > 0) {
         _snowmanx += _snowman;
      }

      if (_snowman.l()) {
         _snowmanxx -= 32;
      } else {
         _snowmanxx += this.c;
      }

      if (this.a(_snowmanx + 3, _snowmanxx + 3, 23, 27, (double)_snowman, (double)_snowman)) {
         this.b(_snowman, _snowman.c(), _snowman, _snowman);
         return true;
      } else {
         return false;
      }
   }

   protected void a(dfm var1, bks var2) {
      boolean _snowman = _snowman.a() == E;
      boolean _snowmanx = _snowman.l();
      int _snowmanxx = _snowman.k();
      int _snowmanxxx = _snowmanxx * 28;
      int _snowmanxxxx = 0;
      int _snowmanxxxxx = this.w + 28 * _snowmanxx;
      int _snowmanxxxxxx = this.x;
      int _snowmanxxxxxxx = 32;
      if (_snowman) {
         _snowmanxxxx += 32;
      }

      if (_snowman.m()) {
         _snowmanxxxxx = this.w + this.b - 28 * (6 - _snowmanxx);
      } else if (_snowmanxx > 0) {
         _snowmanxxxxx += _snowmanxx;
      }

      if (_snowmanx) {
         _snowmanxxxxxx -= 28;
      } else {
         _snowmanxxxx += 64;
         _snowmanxxxxxx += this.c - 4;
      }

      this.b(_snowman, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxx, _snowmanxxxx, 28, 32);
      this.j.b = 100.0F;
      _snowmanxxxxx += 6;
      _snowmanxxxxxx += 8 + (_snowmanx ? 1 : -1);
      RenderSystem.enableRescaleNormal();
      bmb _snowmanxxxxxxxx = _snowman.d();
      this.j.b(_snowmanxxxxxxxx, _snowmanxxxxx, _snowmanxxxxxx);
      this.j.a(this.o, _snowmanxxxxxxxx, _snowmanxxxxx, _snowmanxxxxxx);
      this.j.b = 0.0F;
   }

   public int k() {
      return E;
   }

   public static void a(djz var0, int var1, boolean var2, boolean var3) {
      dzm _snowman = _snowman.s;
      djv _snowmanx = _snowman.aq();
      dzo _snowmanxx = _snowmanx.a(_snowman);
      if (_snowman) {
         for (int _snowmanxxx = 0; _snowmanxxx < bfv.g(); _snowmanxxx++) {
            bmb _snowmanxxxx = ((bmb)_snowmanxx.get(_snowmanxxx)).i();
            _snowman.bm.a(_snowmanxxx, _snowmanxxxx);
            _snowman.q.a(_snowmanxxxx, 36 + _snowmanxxx);
         }

         _snowman.bo.c();
      } else if (_snowman) {
         for (int _snowmanxxx = 0; _snowmanxxx < bfv.g(); _snowmanxxx++) {
            _snowmanxx.set(_snowmanxxx, _snowman.bm.a(_snowmanxxx).i());
         }

         nr _snowmanxxx = _snowman.k.aC[_snowman].j();
         nr _snowmanxxxx = _snowman.k.aE.j();
         _snowman.j.a(new of("inventory.hotbarSaved", _snowmanxxxx, _snowmanxxx), false);
         _snowmanx.a();
      }
   }

   static class a extends bjr {
      public a(aon var1, int var2, int var3, int var4) {
         super(_snowman, _snowman, _snowman, _snowman);
      }

      @Override
      public boolean a(bfw var1) {
         return super.a(_snowman) && this.f() ? this.e().b("CustomCreativeLock") == null : !this.f();
      }
   }

   public static class b extends bic {
      public final gj<bmb> c = gj.a();

      public b(bfw var1) {
         super(null, 0);
         bfv _snowman = _snowman.bm;

         for (int _snowmanx = 0; _snowmanx < 5; _snowmanx++) {
            for (int _snowmanxx = 0; _snowmanxx < 9; _snowmanxx++) {
               this.a(new dqc.a(dqc.C, _snowmanx * 9 + _snowmanxx, 9 + _snowmanxx * 18, 18 + _snowmanx * 18));
            }
         }

         for (int _snowmanx = 0; _snowmanx < 9; _snowmanx++) {
            this.a(new bjr(_snowman, _snowmanx, 9 + _snowmanx * 18, 112));
         }

         this.a(0.0F);
      }

      @Override
      public boolean a(bfw var1) {
         return true;
      }

      public void a(float var1) {
         int _snowman = (this.c.size() + 9 - 1) / 9 - 5;
         int _snowmanx = (int)((double)(_snowman * (float)_snowman) + 0.5);
         if (_snowmanx < 0) {
            _snowmanx = 0;
         }

         for (int _snowmanxx = 0; _snowmanxx < 5; _snowmanxx++) {
            for (int _snowmanxxx = 0; _snowmanxxx < 9; _snowmanxxx++) {
               int _snowmanxxxx = _snowmanxxx + (_snowmanxx + _snowmanx) * 9;
               if (_snowmanxxxx >= 0 && _snowmanxxxx < this.c.size()) {
                  dqc.C.a(_snowmanxxx + _snowmanxx * 9, this.c.get(_snowmanxxxx));
               } else {
                  dqc.C.a(_snowmanxxx + _snowmanxx * 9, bmb.b);
               }
            }
         }
      }

      public boolean e() {
         return this.c.size() > 45;
      }

      @Override
      public bmb b(bfw var1, int var2) {
         if (_snowman >= this.a.size() - 9 && _snowman < this.a.size()) {
            bjr _snowman = this.a.get(_snowman);
            if (_snowman != null && _snowman.f()) {
               _snowman.d(bmb.b);
            }
         }

         return bmb.b;
      }

      @Override
      public boolean a(bmb var1, bjr var2) {
         return _snowman.c != dqc.C;
      }

      @Override
      public boolean b(bjr var1) {
         return _snowman.c != dqc.C;
      }
   }

   static class c extends bjr {
      private final bjr a;

      public c(bjr var1, int var2, int var3, int var4) {
         super(_snowman.c, _snowman, _snowman, _snowman);
         this.a = _snowman;
      }

      @Override
      public bmb a(bfw var1, bmb var2) {
         return this.a.a(_snowman, _snowman);
      }

      @Override
      public boolean a(bmb var1) {
         return this.a.a(_snowman);
      }

      @Override
      public bmb e() {
         return this.a.e();
      }

      @Override
      public boolean f() {
         return this.a.f();
      }

      @Override
      public void d(bmb var1) {
         this.a.d(_snowman);
      }

      @Override
      public void d() {
         this.a.d();
      }

      @Override
      public int a() {
         return this.a.a();
      }

      @Override
      public int b(bmb var1) {
         return this.a.b(_snowman);
      }

      @Nullable
      @Override
      public Pair<vk, vk> c() {
         return this.a.c();
      }

      @Override
      public bmb a(int var1) {
         return this.a.a(_snowman);
      }

      @Override
      public boolean b() {
         return this.a.b();
      }

      @Override
      public boolean a(bfw var1) {
         return this.a.a(_snowman);
      }
   }
}
