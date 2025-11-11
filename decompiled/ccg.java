import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

public class ccg extends ccj implements cdm {
   private final List<ccg.a> a = Lists.newArrayList();
   @Nullable
   private fx b = null;

   public ccg() {
      super(cck.G);
   }

   @Override
   public void X_() {
      if (this.d()) {
         this.a(null, this.d.d_(this.o()), ccg.b.c);
      }

      super.X_();
   }

   public boolean d() {
      if (this.d == null) {
         return false;
      } else {
         for (fx _snowman : fx.a(this.e.b(-1, -1, -1), this.e.b(1, 1, 1))) {
            if (this.d.d_(_snowman).b() instanceof bws) {
               return true;
            }
         }

         return false;
      }
   }

   public boolean f() {
      return this.a.isEmpty();
   }

   public boolean h() {
      return this.a.size() == 3;
   }

   public void a(@Nullable bfw var1, ceh var2, ccg.b var3) {
      List<aqa> _snowman = this.a(_snowman, _snowman);
      if (_snowman != null) {
         for (aqa _snowmanx : _snowman) {
            if (_snowmanx instanceof baa) {
               baa _snowmanxx = (baa)_snowmanx;
               if (_snowman.cA().g(_snowmanx.cA()) <= 16.0) {
                  if (!this.k()) {
                     _snowmanxx.h(_snowman);
                  } else {
                     _snowmanxx.t(400);
                  }
               }
            }
         }
      }
   }

   private List<aqa> a(ceh var1, ccg.b var2) {
      List<aqa> _snowman = Lists.newArrayList();
      this.a.removeIf(var4 -> this.a(_snowman, var4, _snowman, _snowman));
      return _snowman;
   }

   public void a(aqa var1, boolean var2) {
      this.a(_snowman, _snowman, 0);
   }

   public int j() {
      return this.a.size();
   }

   public static int a(ceh var0) {
      return _snowman.c(buk.b);
   }

   public boolean k() {
      return buy.a(this.d, this.o());
   }

   protected void l() {
      rz.a(this);
   }

   public void a(aqa var1, boolean var2, int var3) {
      if (this.a.size() < 3) {
         _snowman.l();
         _snowman.be();
         md _snowman = new md();
         _snowman.d(_snowman);
         this.a.add(new ccg.a(_snowman, _snowman, _snowman ? 2400 : 600));
         if (this.d != null) {
            if (_snowman instanceof baa) {
               baa _snowmanx = (baa)_snowman;
               if (_snowmanx.eL() && (!this.x() || this.d.t.nextBoolean())) {
                  this.b = _snowmanx.eK();
               }
            }

            fx _snowmanx = this.o();
            this.d.a(null, (double)_snowmanx.u(), (double)_snowmanx.v(), (double)_snowmanx.w(), adq.aF, adr.e, 1.0F, 1.0F);
         }

         _snowman.ad();
      }
   }

   private boolean a(ceh var1, ccg.a var2, @Nullable List<aqa> var3, ccg.b var4) {
      if ((this.d.N() || this.d.X()) && _snowman != ccg.b.c) {
         return false;
      } else {
         fx _snowman = this.o();
         md _snowmanx = _snowman.a;
         _snowmanx.r("Passengers");
         _snowmanx.r("Leash");
         _snowmanx.r("UUID");
         gc _snowmanxx = _snowman.c(buk.a);
         fx _snowmanxxx = _snowman.a(_snowmanxx);
         boolean _snowmanxxxx = !this.d.d_(_snowmanxxx).k(this.d, _snowmanxxx).b();
         if (_snowmanxxxx && _snowman != ccg.b.c) {
            return false;
         } else {
            aqa _snowmanxxxxx = aqe.a(_snowmanx, this.d, var0 -> var0);
            if (_snowmanxxxxx != null) {
               if (!_snowmanxxxxx.X().a(aee.d)) {
                  return false;
               } else {
                  if (_snowmanxxxxx instanceof baa) {
                     baa _snowmanxxxxxx = (baa)_snowmanxxxxx;
                     if (this.x() && !_snowmanxxxxxx.eL() && this.d.t.nextFloat() < 0.9F) {
                        _snowmanxxxxxx.g(this.b);
                     }

                     if (_snowman == ccg.b.a) {
                        _snowmanxxxxxx.fb();
                        if (_snowman.b().a(aed.aj)) {
                           int _snowmanxxxxxxx = a(_snowman);
                           if (_snowmanxxxxxxx < 5) {
                              int _snowmanxxxxxxxx = this.d.t.nextInt(100) == 0 ? 2 : 1;
                              if (_snowmanxxxxxxx + _snowmanxxxxxxxx > 5) {
                                 _snowmanxxxxxxxx--;
                              }

                              this.d.a(this.o(), _snowman.a(buk.b, Integer.valueOf(_snowmanxxxxxxx + _snowmanxxxxxxxx)));
                           }
                        }
                     }

                     this.a(_snowman.b, _snowmanxxxxxx);
                     if (_snowman != null) {
                        _snowman.add(_snowmanxxxxxx);
                     }

                     float _snowmanxxxxxxx = _snowmanxxxxx.cy();
                     double _snowmanxxxxxxxx = _snowmanxxxx ? 0.0 : 0.55 + (double)(_snowmanxxxxxxx / 2.0F);
                     double _snowmanxxxxxxxxx = (double)_snowman.u() + 0.5 + _snowmanxxxxxxxx * (double)_snowmanxx.i();
                     double _snowmanxxxxxxxxxx = (double)_snowman.v() + 0.5 - (double)(_snowmanxxxxx.cz() / 2.0F);
                     double _snowmanxxxxxxxxxxx = (double)_snowman.w() + 0.5 + _snowmanxxxxxxxx * (double)_snowmanxx.k();
                     _snowmanxxxxx.b(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxx.p, _snowmanxxxxx.q);
                  }

                  this.d.a(null, _snowman, adq.aG, adr.e, 1.0F, 1.0F);
                  return this.d.c(_snowmanxxxxx);
               }
            } else {
               return false;
            }
         }
      }
   }

   private void a(int var1, baa var2) {
      int _snowman = _snowman.i();
      if (_snowman < 0) {
         _snowman.c_(Math.min(0, _snowman + _snowman));
      } else if (_snowman > 0) {
         _snowman.c_(Math.max(0, _snowman - _snowman));
      }

      _snowman.s(Math.max(0, _snowman.eQ() - _snowman));
      _snowman.eO();
   }

   private boolean x() {
      return this.b != null;
   }

   private void y() {
      Iterator<ccg.a> _snowman = this.a.iterator();
      ceh _snowmanx = this.p();

      while (_snowman.hasNext()) {
         ccg.a _snowmanxx = _snowman.next();
         if (_snowmanxx.b > _snowmanxx.c) {
            ccg.b _snowmanxxx = _snowmanxx.a.q("HasNectar") ? ccg.b.a : ccg.b.b;
            if (this.a(_snowmanx, _snowmanxx, null, _snowmanxxx)) {
               _snowman.remove();
            }
         }

         _snowmanxx.b++;
      }
   }

   @Override
   public void aj_() {
      if (!this.d.v) {
         this.y();
         fx _snowman = this.o();
         if (this.a.size() > 0 && this.d.u_().nextDouble() < 0.005) {
            double _snowmanx = (double)_snowman.u() + 0.5;
            double _snowmanxx = (double)_snowman.v();
            double _snowmanxxx = (double)_snowman.w() + 0.5;
            this.d.a(null, _snowmanx, _snowmanxx, _snowmanxxx, adq.aI, adr.e, 1.0F, 1.0F);
         }

         this.l();
      }
   }

   @Override
   public void a(ceh var1, md var2) {
      super.a(_snowman, _snowman);
      this.a.clear();
      mj _snowman = _snowman.d("Bees", 10);

      for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
         md _snowmanxx = _snowman.a(_snowmanx);
         ccg.a _snowmanxxx = new ccg.a(_snowmanxx.p("EntityData"), _snowmanxx.h("TicksInHive"), _snowmanxx.h("MinOccupationTicks"));
         this.a.add(_snowmanxxx);
      }

      this.b = null;
      if (_snowman.e("FlowerPos")) {
         this.b = mp.b(_snowman.p("FlowerPos"));
      }
   }

   @Override
   public md a(md var1) {
      super.a(_snowman);
      _snowman.a("Bees", this.m());
      if (this.x()) {
         _snowman.a("FlowerPos", mp.a(this.b));
      }

      return _snowman;
   }

   public mj m() {
      mj _snowman = new mj();

      for (ccg.a _snowmanx : this.a) {
         _snowmanx.a.r("UUID");
         md _snowmanxx = new md();
         _snowmanxx.a("EntityData", _snowmanx.a);
         _snowmanxx.b("TicksInHive", _snowmanx.b);
         _snowmanxx.b("MinOccupationTicks", _snowmanx.c);
         _snowman.add(_snowmanxx);
      }

      return _snowman;
   }

   static class a {
      private final md a;
      private int b;
      private final int c;

      private a(md var1, int var2, int var3) {
         _snowman.r("UUID");
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
      }
   }

   public static enum b {
      a,
      b,
      c;

      private b() {
      }
   }
}
