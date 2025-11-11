import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;

public class buk extends bud {
   private static final gc[] c = new gc[]{gc.e, gc.f, gc.d};
   public static final cfb a = bxm.aq;
   public static final cfg b = cex.au;

   public buk(ceg.c var1) {
      super(_snowman);
      this.j(this.n.b().a(b, Integer.valueOf(0)).a(a, gc.c));
   }

   @Override
   public boolean a(ceh var1) {
      return true;
   }

   @Override
   public int a(ceh var1, brx var2, fx var3) {
      return _snowman.c(b);
   }

   @Override
   public void a(brx var1, bfw var2, fx var3, ceh var4, @Nullable ccj var5, bmb var6) {
      super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      if (!_snowman.v && _snowman instanceof ccg) {
         ccg _snowman = (ccg)_snowman;
         if (bpu.a(bpw.u, _snowman) == 0) {
            _snowman.a(_snowman, _snowman, ccg.b.c);
            _snowman.c(_snowman, this);
            this.b(_snowman, _snowman);
         }

         ac.K.a((aah)_snowman, _snowman.b(), _snowman, _snowman.j());
      }
   }

   private void b(brx var1, fx var2) {
      List<baa> _snowman = _snowman.a(baa.class, new dci(_snowman).c(8.0, 6.0, 8.0));
      if (!_snowman.isEmpty()) {
         List<bfw> _snowmanx = _snowman.a(bfw.class, new dci(_snowman).c(8.0, 6.0, 8.0));
         int _snowmanxx = _snowmanx.size();

         for (baa _snowmanxxx : _snowman) {
            if (_snowmanxxx.A() == null) {
               _snowmanxxx.h(_snowmanx.get(_snowman.t.nextInt(_snowmanxx)));
            }
         }
      }
   }

   public static void a(brx var0, fx var1) {
      a(_snowman, _snowman, new bmb(bmd.rq, 3));
   }

   @Override
   public aou a(ceh var1, brx var2, fx var3, bfw var4, aot var5, dcj var6) {
      bmb _snowman = _snowman.b(_snowman);
      int _snowmanx = _snowman.c(b);
      boolean _snowmanxx = false;
      if (_snowmanx >= 5) {
         if (_snowman.b() == bmd.ng) {
            _snowman.a(_snowman, _snowman.cD(), _snowman.cE(), _snowman.cH(), adq.aH, adr.g, 1.0F, 1.0F);
            a(_snowman, _snowman);
            _snowman.a(1, _snowman, var1x -> var1x.d(_snowman));
            _snowmanxx = true;
         } else if (_snowman.b() == bmd.nw) {
            _snowman.g(1);
            _snowman.a(_snowman, _snowman.cD(), _snowman.cE(), _snowman.cH(), adq.bb, adr.g, 1.0F, 1.0F);
            if (_snowman.a()) {
               _snowman.a(_snowman, new bmb(bmd.rt));
            } else if (!_snowman.bm.e(new bmb(bmd.rt))) {
               _snowman.a(new bmb(bmd.rt), false);
            }

            _snowmanxx = true;
         }
      }

      if (_snowmanxx) {
         if (!buy.a(_snowman, _snowman)) {
            if (this.d(_snowman, _snowman)) {
               this.b(_snowman, _snowman);
            }

            this.a(_snowman, _snowman, _snowman, _snowman, ccg.b.c);
         } else {
            this.a(_snowman, _snowman, _snowman);
         }

         return aou.a(_snowman.v);
      } else {
         return super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }

   private boolean d(brx var1, fx var2) {
      ccj _snowman = _snowman.c(_snowman);
      if (_snowman instanceof ccg) {
         ccg _snowmanx = (ccg)_snowman;
         return !_snowmanx.f();
      } else {
         return false;
      }
   }

   public void a(brx var1, ceh var2, fx var3, @Nullable bfw var4, ccg.b var5) {
      this.a(_snowman, _snowman, _snowman);
      ccj _snowman = _snowman.c(_snowman);
      if (_snowman instanceof ccg) {
         ccg _snowmanx = (ccg)_snowman;
         _snowmanx.a(_snowman, _snowman, _snowman);
      }
   }

   public void a(brx var1, ceh var2, fx var3) {
      _snowman.a(_snowman, _snowman.a(b, Integer.valueOf(0)), 3);
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, Random var4) {
      if (_snowman.c(b) >= 5) {
         for (int _snowman = 0; _snowman < _snowman.nextInt(1) + 1; _snowman++) {
            this.a(_snowman, _snowman, _snowman);
         }
      }
   }

   private void a(brx var1, fx var2, ceh var3) {
      if (_snowman.m().c() && !(_snowman.t.nextFloat() < 0.3F)) {
         ddh _snowman = _snowman.k(_snowman, _snowman);
         double _snowmanx = _snowman.c(gc.a.b);
         if (_snowmanx >= 1.0 && !_snowman.a(aed.W)) {
            double _snowmanxx = _snowman.b(gc.a.b);
            if (_snowmanxx > 0.0) {
               this.a(_snowman, _snowman, _snowman, (double)_snowman.v() + _snowmanxx - 0.05);
            } else {
               fx _snowmanxxx = _snowman.c();
               ceh _snowmanxxxx = _snowman.d_(_snowmanxxx);
               ddh _snowmanxxxxx = _snowmanxxxx.k(_snowman, _snowmanxxx);
               double _snowmanxxxxxx = _snowmanxxxxx.c(gc.a.b);
               if ((_snowmanxxxxxx < 1.0 || !_snowmanxxxx.r(_snowman, _snowmanxxx)) && _snowmanxxxx.m().c()) {
                  this.a(_snowman, _snowman, _snowman, (double)_snowman.v() - 0.05);
               }
            }
         }
      }
   }

   private void a(brx var1, fx var2, ddh var3, double var4) {
      this.a(_snowman, (double)_snowman.u() + _snowman.b(gc.a.a), (double)_snowman.u() + _snowman.c(gc.a.a), (double)_snowman.w() + _snowman.b(gc.a.c), (double)_snowman.w() + _snowman.c(gc.a.c), _snowman);
   }

   private void a(brx var1, double var2, double var4, double var6, double var8, double var10) {
      _snowman.a(hh.ai, afm.d(_snowman.t.nextDouble(), _snowman, _snowman), _snowman, afm.d(_snowman.t.nextDouble(), _snowman, _snowman), 0.0, 0.0, 0.0);
   }

   @Override
   public ceh a(bny var1) {
      return this.n().a(a, _snowman.f().f());
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(b, a);
   }

   @Override
   public bzh b(ceh var1) {
      return bzh.c;
   }

   @Nullable
   @Override
   public ccj a(brc var1) {
      return new ccg();
   }

   @Override
   public void a(brx var1, fx var2, ceh var3, bfw var4) {
      if (!_snowman.v && _snowman.b_() && _snowman.V().b(brt.f)) {
         ccj _snowman = _snowman.c(_snowman);
         if (_snowman instanceof ccg) {
            ccg _snowmanx = (ccg)_snowman;
            bmb _snowmanxx = new bmb(this);
            int _snowmanxxx = _snowman.c(b);
            boolean _snowmanxxxx = !_snowmanx.f();
            if (!_snowmanxxxx && _snowmanxxx == 0) {
               return;
            }

            if (_snowmanxxxx) {
               md _snowmanxxxxx = new md();
               _snowmanxxxxx.a("Bees", _snowmanx.m());
               _snowmanxx.a("BlockEntityTag", _snowmanxxxxx);
            }

            md _snowmanxxxxx = new md();
            _snowmanxxxxx.b("honey_level", _snowmanxxx);
            _snowmanxx.a("BlockStateTag", _snowmanxxxxx);
            bcv _snowmanxxxxxx = new bcv(_snowman, (double)_snowman.u(), (double)_snowman.v(), (double)_snowman.w(), _snowmanxx);
            _snowmanxxxxxx.m();
            _snowman.c(_snowmanxxxxxx);
         }
      }

      super.a(_snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public List<bmb> a(ceh var1, cyv.a var2) {
      aqa _snowman = _snowman.b(dbc.a);
      if (_snowman instanceof bcw || _snowman instanceof bdc || _snowman instanceof bgz || _snowman instanceof bcl || _snowman instanceof bhv) {
         ccj _snowmanx = _snowman.b(dbc.h);
         if (_snowmanx instanceof ccg) {
            ccg _snowmanxx = (ccg)_snowmanx;
            _snowmanxx.a(null, _snowman, ccg.b.c);
         }
      }

      return super.a(_snowman, _snowman);
   }

   @Override
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      if (_snowman.d_(_snowman).b() instanceof bws) {
         ccj _snowman = _snowman.c(_snowman);
         if (_snowman instanceof ccg) {
            ccg _snowmanx = (ccg)_snowman;
            _snowmanx.a(null, _snowman, ccg.b.c);
         }
      }

      return super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public static gc a(Random var0) {
      return x.a(c, _snowman);
   }
}
