import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.UnmodifiableIterator;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

public abstract class bhl extends aqa {
   private static final us<Integer> b = uv.a(bhl.class, uu.b);
   private static final us<Integer> c = uv.a(bhl.class, uu.b);
   private static final us<Float> d = uv.a(bhl.class, uu.c);
   private static final us<Integer> e = uv.a(bhl.class, uu.b);
   private static final us<Integer> f = uv.a(bhl.class, uu.b);
   private static final us<Boolean> g = uv.a(bhl.class, uu.i);
   private static final ImmutableMap<aqx, ImmutableList<Integer>> ag = ImmutableMap.of(
      aqx.a, ImmutableList.of(0, 1, -1), aqx.f, ImmutableList.of(0, 1, -1), aqx.d, ImmutableList.of(0, 1)
   );
   private boolean ah;
   private static final Map<cfk, Pair<gr, gr>> ai = x.a(Maps.newEnumMap(cfk.class), var0 -> {
      gr _snowman = gc.e.p();
      gr _snowmanx = gc.f.p();
      gr _snowmanxx = gc.c.p();
      gr _snowmanxxx = gc.d.p();
      gr _snowmanxxxx = _snowman.n();
      gr _snowmanxxxxx = _snowmanx.n();
      gr _snowmanxxxxxx = _snowmanxx.n();
      gr _snowmanxxxxxxx = _snowmanxxx.n();
      var0.put(cfk.a, Pair.of(_snowmanxx, _snowmanxxx));
      var0.put(cfk.b, Pair.of(_snowman, _snowmanx));
      var0.put(cfk.c, Pair.of(_snowmanxxxx, _snowmanx));
      var0.put(cfk.d, Pair.of(_snowman, _snowmanxxxxx));
      var0.put(cfk.e, Pair.of(_snowmanxx, _snowmanxxxxxxx));
      var0.put(cfk.f, Pair.of(_snowmanxxxxxx, _snowmanxxx));
      var0.put(cfk.g, Pair.of(_snowmanxxx, _snowmanx));
      var0.put(cfk.h, Pair.of(_snowmanxxx, _snowman));
      var0.put(cfk.i, Pair.of(_snowmanxx, _snowman));
      var0.put(cfk.j, Pair.of(_snowmanxx, _snowmanx));
   });
   private int aj;
   private double ak;
   private double al;
   private double am;
   private double an;
   private double ao;
   private double ap;
   private double aq;
   private double ar;

   protected bhl(aqe<?> var1, brx var2) {
      super(_snowman, _snowman);
      this.i = true;
   }

   protected bhl(aqe<?> var1, brx var2, double var3, double var5, double var7) {
      this(_snowman, _snowman);
      this.d(_snowman, _snowman, _snowman);
      this.f(dcn.a);
      this.m = _snowman;
      this.n = _snowman;
      this.o = _snowman;
   }

   public static bhl a(brx var0, double var1, double var3, double var5, bhl.a var7) {
      if (_snowman == bhl.a.b) {
         return new bhq(_snowman, _snowman, _snowman, _snowman);
      } else if (_snowman == bhl.a.c) {
         return new bhs(_snowman, _snowman, _snowman, _snowman);
      } else if (_snowman == bhl.a.d) {
         return new bhv(_snowman, _snowman, _snowman, _snowman);
      } else if (_snowman == bhl.a.e) {
         return new bhu(_snowman, _snowman, _snowman, _snowman);
      } else if (_snowman == bhl.a.f) {
         return new bht(_snowman, _snowman, _snowman, _snowman);
      } else {
         return (bhl)(_snowman == bhl.a.g ? new bhr(_snowman, _snowman, _snowman, _snowman) : new bhp(_snowman, _snowman, _snowman, _snowman));
      }
   }

   @Override
   protected boolean aC() {
      return false;
   }

   @Override
   protected void e() {
      this.R.a(b, 0);
      this.R.a(c, 1);
      this.R.a(d, 0.0F);
      this.R.a(e, buo.i(bup.a.n()));
      this.R.a(f, 6);
      this.R.a(g, false);
   }

   @Override
   public boolean j(aqa var1) {
      return bhn.a(this, _snowman);
   }

   @Override
   public boolean aU() {
      return true;
   }

   @Override
   protected dcn a(gc.a var1, i.a var2) {
      return aqm.h(super.a(_snowman, _snowman));
   }

   @Override
   public double bc() {
      return 0.0;
   }

   @Override
   public dcn b(aqm var1) {
      gc _snowman = this.ca();
      if (_snowman.n() == gc.a.b) {
         return super.b(_snowman);
      } else {
         int[][] _snowmanx = bho.a(_snowman);
         fx _snowmanxx = this.cB();
         fx.a _snowmanxxx = new fx.a();
         ImmutableList<aqx> _snowmanxxxx = _snowman.ej();
         UnmodifiableIterator var7 = _snowmanxxxx.iterator();

         while (var7.hasNext()) {
            aqx _snowmanxxxxx = (aqx)var7.next();
            aqb _snowmanxxxxxx = _snowman.a(_snowmanxxxxx);
            float _snowmanxxxxxxx = Math.min(_snowmanxxxxxx.a, 1.0F) / 2.0F;
            UnmodifiableIterator var11 = ((ImmutableList)ag.get(_snowmanxxxxx)).iterator();

            while (var11.hasNext()) {
               int _snowmanxxxxxxxx = (Integer)var11.next();

               for (int[] _snowmanxxxxxxxxx : _snowmanx) {
                  _snowmanxxx.d(_snowmanxx.u() + _snowmanxxxxxxxxx[0], _snowmanxx.v() + _snowmanxxxxxxxx, _snowmanxx.w() + _snowmanxxxxxxxxx[1]);
                  double _snowmanxxxxxxxxxx = this.l.a(bho.a(this.l, _snowmanxxx), () -> bho.a(this.l, _snowman.c()));
                  if (bho.a(_snowmanxxxxxxxxxx)) {
                     dci _snowmanxxxxxxxxxxx = new dci((double)(-_snowmanxxxxxxx), 0.0, (double)(-_snowmanxxxxxxx), (double)_snowmanxxxxxxx, (double)_snowmanxxxxxx.b, (double)_snowmanxxxxxxx);
                     dcn _snowmanxxxxxxxxxxxx = dcn.a(_snowmanxxx, _snowmanxxxxxxxxxx);
                     if (bho.a(this.l, _snowman, _snowmanxxxxxxxxxxx.c(_snowmanxxxxxxxxxxxx))) {
                        _snowman.b(_snowmanxxxxx);
                        return _snowmanxxxxxxxxxxxx;
                     }
                  }
               }
            }
         }

         double _snowmanxxxxx = this.cc().e;
         _snowmanxxx.c((double)_snowmanxx.u(), _snowmanxxxxx, (double)_snowmanxx.w());
         UnmodifiableIterator var22 = _snowmanxxxx.iterator();

         while (var22.hasNext()) {
            aqx _snowmanxxxxxx = (aqx)var22.next();
            double _snowmanxxxxxxx = (double)_snowman.a(_snowmanxxxxxx).b;
            int _snowmanxxxxxxxx = afm.f(_snowmanxxxxx - (double)_snowmanxxx.v() + _snowmanxxxxxxx);
            double _snowmanxxxxxxxxxx = bho.a(_snowmanxxx, _snowmanxxxxxxxx, var1x -> this.l.d_(var1x).k(this.l, var1x));
            if (_snowmanxxxxx + _snowmanxxxxxxx <= _snowmanxxxxxxxxxx) {
               _snowman.b(_snowmanxxxxxx);
               break;
            }
         }

         return super.b(_snowman);
      }
   }

   @Override
   public boolean a(apk var1, float var2) {
      if (this.l.v || this.y) {
         return true;
      } else if (this.b(_snowman)) {
         return false;
      } else {
         this.d(-this.n());
         this.c(10);
         this.aS();
         this.a(this.k() + _snowman * 10.0F);
         boolean _snowman = _snowman.k() instanceof bfw && ((bfw)_snowman.k()).bC.d;
         if (_snowman || this.k() > 40.0F) {
            this.be();
            if (_snowman && !this.S()) {
               this.ad();
            } else {
               this.a(_snowman);
            }
         }

         return true;
      }
   }

   @Override
   protected float ar() {
      ceh _snowman = this.l.d_(this.cB());
      return _snowman.a(aed.H) ? 1.0F : super.ar();
   }

   public void a(apk var1) {
      this.ad();
      if (this.l.V().b(brt.g)) {
         bmb _snowman = new bmb(bmd.lN);
         if (this.S()) {
            _snowman.a(this.T());
         }

         this.a(_snowman);
      }
   }

   @Override
   public void bm() {
      this.d(-this.n());
      this.c(10);
      this.a(this.k() + this.k() * 10.0F);
   }

   @Override
   public boolean aT() {
      return !this.y;
   }

   private static Pair<gr, gr> a(cfk var0) {
      return ai.get(_snowman);
   }

   @Override
   public gc ca() {
      return this.ah ? this.bZ().f().g() : this.bZ().g();
   }

   @Override
   public void j() {
      if (this.m() > 0) {
         this.c(this.m() - 1);
      }

      if (this.k() > 0.0F) {
         this.a(this.k() - 1.0F);
      }

      if (this.cE() < -64.0) {
         this.an();
      }

      this.bk();
      if (this.l.v) {
         if (this.aj > 0) {
            double _snowman = this.cD() + (this.ak - this.cD()) / (double)this.aj;
            double _snowmanx = this.cE() + (this.al - this.cE()) / (double)this.aj;
            double _snowmanxx = this.cH() + (this.am - this.cH()) / (double)this.aj;
            double _snowmanxxx = afm.g(this.an - (double)this.p);
            this.p = (float)((double)this.p + _snowmanxxx / (double)this.aj);
            this.q = (float)((double)this.q + (this.ao - (double)this.q) / (double)this.aj);
            this.aj--;
            this.d(_snowman, _snowmanx, _snowmanxx);
            this.a(this.p, this.q);
         } else {
            this.af();
            this.a(this.p, this.q);
         }
      } else {
         if (!this.aB()) {
            this.f(this.cC().b(0.0, -0.04, 0.0));
         }

         int _snowman = afm.c(this.cD());
         int _snowmanx = afm.c(this.cE());
         int _snowmanxx = afm.c(this.cH());
         if (this.l.d_(new fx(_snowman, _snowmanx - 1, _snowmanxx)).a(aed.H)) {
            _snowmanx--;
         }

         fx _snowmanxxx = new fx(_snowman, _snowmanx, _snowmanxx);
         ceh _snowmanxxxx = this.l.d_(_snowmanxxx);
         if (bug.g(_snowmanxxxx)) {
            this.c(_snowmanxxx, _snowmanxxxx);
            if (_snowmanxxxx.a(bup.fD)) {
               this.a(_snowman, _snowmanx, _snowmanxx, _snowmanxxxx.c(byx.d));
            }
         } else {
            this.h();
         }

         this.ay();
         this.q = 0.0F;
         double _snowmanxxxxx = this.m - this.cD();
         double _snowmanxxxxxx = this.o - this.cH();
         if (_snowmanxxxxx * _snowmanxxxxx + _snowmanxxxxxx * _snowmanxxxxxx > 0.001) {
            this.p = (float)(afm.d(_snowmanxxxxxx, _snowmanxxxxx) * 180.0 / Math.PI);
            if (this.ah) {
               this.p += 180.0F;
            }
         }

         double _snowmanxxxxxxx = (double)afm.g(this.p - this.r);
         if (_snowmanxxxxxxx < -170.0 || _snowmanxxxxxxx >= 170.0) {
            this.p += 180.0F;
            this.ah = !this.ah;
         }

         this.a(this.p, this.q);
         if (this.o() == bhl.a.a && c(this.cC()) > 0.01) {
            List<aqa> _snowmanxxxxxxxx = this.l.a(this, this.cc().c(0.2F, 0.0, 0.2F), aqd.a(this));
            if (!_snowmanxxxxxxxx.isEmpty()) {
               for (int _snowmanxxxxxxxxx = 0; _snowmanxxxxxxxxx < _snowmanxxxxxxxx.size(); _snowmanxxxxxxxxx++) {
                  aqa _snowmanxxxxxxxxxx = _snowmanxxxxxxxx.get(_snowmanxxxxxxxxx);
                  if (!(_snowmanxxxxxxxxxx instanceof bfw) && !(_snowmanxxxxxxxxxx instanceof bai) && !(_snowmanxxxxxxxxxx instanceof bhl) && !this.bs() && !_snowmanxxxxxxxxxx.br()) {
                     _snowmanxxxxxxxxxx.m(this);
                  } else {
                     _snowmanxxxxxxxxxx.i(this);
                  }
               }
            }
         } else {
            for (aqa _snowmanxxxxxxxx : this.l.a(this, this.cc().c(0.2F, 0.0, 0.2F))) {
               if (!this.w(_snowmanxxxxxxxx) && _snowmanxxxxxxxx.aU() && _snowmanxxxxxxxx instanceof bhl) {
                  _snowmanxxxxxxxx.i(this);
               }
            }
         }

         this.aK();
         if (this.aQ()) {
            this.ak();
            this.C *= 0.5F;
         }

         this.Q = false;
      }
   }

   protected double g() {
      return 0.4;
   }

   public void a(int var1, int var2, int var3, boolean var4) {
   }

   protected void h() {
      double _snowman = this.g();
      dcn _snowmanx = this.cC();
      this.n(afm.a(_snowmanx.b, -_snowman, _snowman), _snowmanx.c, afm.a(_snowmanx.d, -_snowman, _snowman));
      if (this.t) {
         this.f(this.cC().a(0.5));
      }

      this.a(aqr.a, this.cC());
      if (!this.t) {
         this.f(this.cC().a(0.95));
      }
   }

   protected void c(fx var1, ceh var2) {
      this.C = 0.0F;
      double _snowman = this.cD();
      double _snowmanx = this.cE();
      double _snowmanxx = this.cH();
      dcn _snowmanxxx = this.p(_snowman, _snowmanx, _snowmanxx);
      _snowmanx = (double)_snowman.v();
      boolean _snowmanxxxx = false;
      boolean _snowmanxxxxx = false;
      bug _snowmanxxxxxx = (bug)_snowman.b();
      if (_snowmanxxxxxx == bup.aN) {
         _snowmanxxxx = _snowman.c(byx.d);
         _snowmanxxxxx = !_snowmanxxxx;
      }

      double _snowmanxxxxxxx = 0.0078125;
      dcn _snowmanxxxxxxxx = this.cC();
      cfk _snowmanxxxxxxxxx = _snowman.c(_snowmanxxxxxx.d());
      switch (_snowmanxxxxxxxxx) {
         case c:
            this.f(_snowmanxxxxxxxx.b(-0.0078125, 0.0, 0.0));
            _snowmanx++;
            break;
         case d:
            this.f(_snowmanxxxxxxxx.b(0.0078125, 0.0, 0.0));
            _snowmanx++;
            break;
         case e:
            this.f(_snowmanxxxxxxxx.b(0.0, 0.0, 0.0078125));
            _snowmanx++;
            break;
         case f:
            this.f(_snowmanxxxxxxxx.b(0.0, 0.0, -0.0078125));
            _snowmanx++;
      }

      _snowmanxxxxxxxx = this.cC();
      Pair<gr, gr> _snowmanxxxxxxx = a(_snowmanxxxxxxxxx);
      gr _snowmanxxxxxxxx = (gr)_snowmanxxxxxxx.getFirst();
      gr _snowmanxxxxxxxxx = (gr)_snowmanxxxxxxx.getSecond();
      double _snowmanxxxxxxxxxx = (double)(_snowmanxxxxxxxxx.u() - _snowmanxxxxxxxx.u());
      double _snowmanxxxxxxxxxxx = (double)(_snowmanxxxxxxxxx.w() - _snowmanxxxxxxxx.w());
      double _snowmanxxxxxxxxxxxx = Math.sqrt(_snowmanxxxxxxxxxx * _snowmanxxxxxxxxxx + _snowmanxxxxxxxxxxx * _snowmanxxxxxxxxxxx);
      double _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxx.b * _snowmanxxxxxxxxxx + _snowmanxxxxxxxx.d * _snowmanxxxxxxxxxxx;
      if (_snowmanxxxxxxxxxxxxx < 0.0) {
         _snowmanxxxxxxxxxx = -_snowmanxxxxxxxxxx;
         _snowmanxxxxxxxxxxx = -_snowmanxxxxxxxxxxx;
      }

      double _snowmanxxxxxxxxxxxxxx = Math.min(2.0, Math.sqrt(c(_snowmanxxxxxxxx)));
      _snowmanxxxxxxxx = new dcn(_snowmanxxxxxxxxxxxxxx * _snowmanxxxxxxxxxx / _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxx.c, _snowmanxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxx / _snowmanxxxxxxxxxxxx);
      this.f(_snowmanxxxxxxxx);
      aqa _snowmanxxxxxxxxxxxxxxx = this.cn().isEmpty() ? null : this.cn().get(0);
      if (_snowmanxxxxxxxxxxxxxxx instanceof bfw) {
         dcn _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx.cC();
         double _snowmanxxxxxxxxxxxxxxxxx = c(_snowmanxxxxxxxxxxxxxxxx);
         double _snowmanxxxxxxxxxxxxxxxxxx = c(this.cC());
         if (_snowmanxxxxxxxxxxxxxxxxx > 1.0E-4 && _snowmanxxxxxxxxxxxxxxxxxx < 0.01) {
            this.f(this.cC().b(_snowmanxxxxxxxxxxxxxxxx.b * 0.1, 0.0, _snowmanxxxxxxxxxxxxxxxx.d * 0.1));
            _snowmanxxxxx = false;
         }
      }

      if (_snowmanxxxxx) {
         double _snowmanxxxxxxxxxxxxxxxx = Math.sqrt(c(this.cC()));
         if (_snowmanxxxxxxxxxxxxxxxx < 0.03) {
            this.f(dcn.a);
         } else {
            this.f(this.cC().d(0.5, 0.0, 0.5));
         }
      }

      double _snowmanxxxxxxxxxxxxxxxx = (double)_snowman.u() + 0.5 + (double)_snowmanxxxxxxxx.u() * 0.5;
      double _snowmanxxxxxxxxxxxxxxxxx = (double)_snowman.w() + 0.5 + (double)_snowmanxxxxxxxx.w() * 0.5;
      double _snowmanxxxxxxxxxxxxxxxxxx = (double)_snowman.u() + 0.5 + (double)_snowmanxxxxxxxxx.u() * 0.5;
      double _snowmanxxxxxxxxxxxxxxxxxxx = (double)_snowman.w() + 0.5 + (double)_snowmanxxxxxxxxx.w() * 0.5;
      _snowmanxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxxxxx;
      _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxxxxxx;
      double _snowmanxxxxxxxxxxxxxxxxxxxx;
      if (_snowmanxxxxxxxxxx == 0.0) {
         _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxx - (double)_snowman.w();
      } else if (_snowmanxxxxxxxxxxx == 0.0) {
         _snowmanxxxxxxxxxxxxxxxxxxxx = _snowman - (double)_snowman.u();
      } else {
         double _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowman - _snowmanxxxxxxxxxxxxxxxx;
         double _snowmanxxxxxxxxxxxxxxxxxxxxxx = _snowmanxx - _snowmanxxxxxxxxxxxxxxxxx;
         _snowmanxxxxxxxxxxxxxxxxxxxx = (_snowmanxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxx) * 2.0;
      }

      _snowman = _snowmanxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxx;
      _snowmanxx = _snowmanxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxx;
      this.d(_snowman, _snowmanx, _snowmanxx);
      double _snowmanxxxxxxxxxxxxxxxxxxxxx = this.bs() ? 0.75 : 1.0;
      double _snowmanxxxxxxxxxxxxxxxxxxxxxx = this.g();
      _snowmanxxxxxxxx = this.cC();
      this.a(
         aqr.a,
         new dcn(
            afm.a(_snowmanxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxx.b, -_snowmanxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxx),
            0.0,
            afm.a(_snowmanxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxx.d, -_snowmanxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxx)
         )
      );
      if (_snowmanxxxxxxxx.v() != 0 && afm.c(this.cD()) - _snowman.u() == _snowmanxxxxxxxx.u() && afm.c(this.cH()) - _snowman.w() == _snowmanxxxxxxxx.w()) {
         this.d(this.cD(), this.cE() + (double)_snowmanxxxxxxxx.v(), this.cH());
      } else if (_snowmanxxxxxxxxx.v() != 0 && afm.c(this.cD()) - _snowman.u() == _snowmanxxxxxxxxx.u() && afm.c(this.cH()) - _snowman.w() == _snowmanxxxxxxxxx.w()) {
         this.d(this.cD(), this.cE() + (double)_snowmanxxxxxxxxx.v(), this.cH());
      }

      this.i();
      dcn _snowmanxxxxxxxxxxxxxxxxxxxxxxx = this.p(this.cD(), this.cE(), this.cH());
      if (_snowmanxxxxxxxxxxxxxxxxxxxxxxx != null && _snowmanxxx != null) {
         double _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = (_snowmanxxx.c - _snowmanxxxxxxxxxxxxxxxxxxxxxxx.c) * 0.05;
         dcn _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = this.cC();
         double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = Math.sqrt(c(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx));
         if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx > 0.0) {
            this.f(
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx.d(
                  (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxx) / _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  1.0,
                  (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxx) / _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx
               )
            );
         }

         this.d(this.cD(), _snowmanxxxxxxxxxxxxxxxxxxxxxxx.c, this.cH());
      }

      int _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = afm.c(this.cD());
      int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = afm.c(this.cH());
      if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxx != _snowman.u() || _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx != _snowman.w()) {
         dcn _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = this.cC();
         double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = Math.sqrt(c(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx));
         this.n(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx * (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxxx - _snowman.u()),
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.c,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx * (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx - _snowman.w())
         );
      }

      if (_snowmanxxxx) {
         dcn _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = this.cC();
         double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = Math.sqrt(c(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx));
         if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx > 0.01) {
            double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0.06;
            this.f(
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.b(
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.b / _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx * 0.06, 0.0, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.d / _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx * 0.06
               )
            );
         } else {
            dcn _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = this.cC();
            double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx.b;
            double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx.d;
            if (_snowmanxxxxxxxxx == cfk.b) {
               if (this.a(_snowman.f())) {
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0.02;
               } else if (this.a(_snowman.g())) {
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = -0.02;
               }
            } else {
               if (_snowmanxxxxxxxxx != cfk.a) {
                  return;
               }

               if (this.a(_snowman.d())) {
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0.02;
               } else if (this.a(_snowman.e())) {
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = -0.02;
               }
            }

            this.n(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx.c, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx);
         }
      }
   }

   private boolean a(fx var1) {
      return this.l.d_(_snowman).g(this.l, _snowman);
   }

   protected void i() {
      double _snowman = this.bs() ? 0.997 : 0.96;
      this.f(this.cC().d(_snowman, 0.0, _snowman));
   }

   @Nullable
   public dcn a(double var1, double var3, double var5, double var7) {
      int _snowman = afm.c(_snowman);
      int _snowmanx = afm.c(_snowman);
      int _snowmanxx = afm.c(_snowman);
      if (this.l.d_(new fx(_snowman, _snowmanx - 1, _snowmanxx)).a(aed.H)) {
         _snowmanx--;
      }

      ceh _snowmanxxx = this.l.d_(new fx(_snowman, _snowmanx, _snowmanxx));
      if (bug.g(_snowmanxxx)) {
         cfk _snowmanxxxx = _snowmanxxx.c(((bug)_snowmanxxx.b()).d());
         _snowman = (double)_snowmanx;
         if (_snowmanxxxx.c()) {
            _snowman = (double)(_snowmanx + 1);
         }

         Pair<gr, gr> _snowmanxxxxx = a(_snowmanxxxx);
         gr _snowmanxxxxxx = (gr)_snowmanxxxxx.getFirst();
         gr _snowmanxxxxxxx = (gr)_snowmanxxxxx.getSecond();
         double _snowmanxxxxxxxx = (double)(_snowmanxxxxxxx.u() - _snowmanxxxxxx.u());
         double _snowmanxxxxxxxxx = (double)(_snowmanxxxxxxx.w() - _snowmanxxxxxx.w());
         double _snowmanxxxxxxxxxx = Math.sqrt(_snowmanxxxxxxxx * _snowmanxxxxxxxx + _snowmanxxxxxxxxx * _snowmanxxxxxxxxx);
         _snowmanxxxxxxxx /= _snowmanxxxxxxxxxx;
         _snowmanxxxxxxxxx /= _snowmanxxxxxxxxxx;
         _snowman += _snowmanxxxxxxxx * _snowman;
         _snowman += _snowmanxxxxxxxxx * _snowman;
         if (_snowmanxxxxxx.v() != 0 && afm.c(_snowman) - _snowman == _snowmanxxxxxx.u() && afm.c(_snowman) - _snowmanxx == _snowmanxxxxxx.w()) {
            _snowman += (double)_snowmanxxxxxx.v();
         } else if (_snowmanxxxxxxx.v() != 0 && afm.c(_snowman) - _snowman == _snowmanxxxxxxx.u() && afm.c(_snowman) - _snowmanxx == _snowmanxxxxxxx.w()) {
            _snowman += (double)_snowmanxxxxxxx.v();
         }

         return this.p(_snowman, _snowman, _snowman);
      } else {
         return null;
      }
   }

   @Nullable
   public dcn p(double var1, double var3, double var5) {
      int _snowman = afm.c(_snowman);
      int _snowmanx = afm.c(_snowman);
      int _snowmanxx = afm.c(_snowman);
      if (this.l.d_(new fx(_snowman, _snowmanx - 1, _snowmanxx)).a(aed.H)) {
         _snowmanx--;
      }

      ceh _snowmanxxx = this.l.d_(new fx(_snowman, _snowmanx, _snowmanxx));
      if (bug.g(_snowmanxxx)) {
         cfk _snowmanxxxx = _snowmanxxx.c(((bug)_snowmanxxx.b()).d());
         Pair<gr, gr> _snowmanxxxxx = a(_snowmanxxxx);
         gr _snowmanxxxxxx = (gr)_snowmanxxxxx.getFirst();
         gr _snowmanxxxxxxx = (gr)_snowmanxxxxx.getSecond();
         double _snowmanxxxxxxxx = (double)_snowman + 0.5 + (double)_snowmanxxxxxx.u() * 0.5;
         double _snowmanxxxxxxxxx = (double)_snowmanx + 0.0625 + (double)_snowmanxxxxxx.v() * 0.5;
         double _snowmanxxxxxxxxxx = (double)_snowmanxx + 0.5 + (double)_snowmanxxxxxx.w() * 0.5;
         double _snowmanxxxxxxxxxxx = (double)_snowman + 0.5 + (double)_snowmanxxxxxxx.u() * 0.5;
         double _snowmanxxxxxxxxxxxx = (double)_snowmanx + 0.0625 + (double)_snowmanxxxxxxx.v() * 0.5;
         double _snowmanxxxxxxxxxxxxx = (double)_snowmanxx + 0.5 + (double)_snowmanxxxxxxx.w() * 0.5;
         double _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx - _snowmanxxxxxxxx;
         double _snowmanxxxxxxxxxxxxxxx = (_snowmanxxxxxxxxxxxx - _snowmanxxxxxxxxx) * 2.0;
         double _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx - _snowmanxxxxxxxxxx;
         double _snowmanxxxxxxxxxxxxxxxxx;
         if (_snowmanxxxxxxxxxxxxxx == 0.0) {
            _snowmanxxxxxxxxxxxxxxxxx = _snowman - (double)_snowmanxx;
         } else if (_snowmanxxxxxxxxxxxxxxxx == 0.0) {
            _snowmanxxxxxxxxxxxxxxxxx = _snowman - (double)_snowman;
         } else {
            double _snowmanxxxxxxxxxxxxxxxxxx = _snowman - _snowmanxxxxxxxx;
            double _snowmanxxxxxxxxxxxxxxxxxxx = _snowman - _snowmanxxxxxxxxxx;
            _snowmanxxxxxxxxxxxxxxxxx = (_snowmanxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxx) * 2.0;
         }

         _snowman = _snowmanxxxxxxxx + _snowmanxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxx;
         _snowman = _snowmanxxxxxxxxx + _snowmanxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxx;
         _snowman = _snowmanxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxx;
         if (_snowmanxxxxxxxxxxxxxxx < 0.0) {
            _snowman++;
         } else if (_snowmanxxxxxxxxxxxxxxx > 0.0) {
            _snowman += 0.5;
         }

         return new dcn(_snowman, _snowman, _snowman);
      } else {
         return null;
      }
   }

   @Override
   public dci cd() {
      dci _snowman = this.cc();
      return this.t() ? _snowman.g((double)Math.abs(this.r()) / 16.0) : _snowman;
   }

   @Override
   protected void a(md var1) {
      if (_snowman.q("CustomDisplayTile")) {
         this.b(mp.c(_snowman.p("DisplayState")));
         this.l(_snowman.h("DisplayOffset"));
      }
   }

   @Override
   protected void b(md var1) {
      if (this.t()) {
         _snowman.a("CustomDisplayTile", true);
         _snowman.a("DisplayState", mp.a(this.p()));
         _snowman.b("DisplayOffset", this.r());
      }
   }

   @Override
   public void i(aqa var1) {
      if (!this.l.v) {
         if (!_snowman.H && !this.H) {
            if (!this.w(_snowman)) {
               double _snowman = _snowman.cD() - this.cD();
               double _snowmanx = _snowman.cH() - this.cH();
               double _snowmanxx = _snowman * _snowman + _snowmanx * _snowmanx;
               if (_snowmanxx >= 1.0E-4F) {
                  _snowmanxx = (double)afm.a(_snowmanxx);
                  _snowman /= _snowmanxx;
                  _snowmanx /= _snowmanxx;
                  double _snowmanxxx = 1.0 / _snowmanxx;
                  if (_snowmanxxx > 1.0) {
                     _snowmanxxx = 1.0;
                  }

                  _snowman *= _snowmanxxx;
                  _snowmanx *= _snowmanxxx;
                  _snowman *= 0.1F;
                  _snowmanx *= 0.1F;
                  _snowman *= (double)(1.0F - this.I);
                  _snowmanx *= (double)(1.0F - this.I);
                  _snowman *= 0.5;
                  _snowmanx *= 0.5;
                  if (_snowman instanceof bhl) {
                     double _snowmanxxxx = _snowman.cD() - this.cD();
                     double _snowmanxxxxx = _snowman.cH() - this.cH();
                     dcn _snowmanxxxxxx = new dcn(_snowmanxxxx, 0.0, _snowmanxxxxx).d();
                     dcn _snowmanxxxxxxx = new dcn((double)afm.b(this.p * (float) (Math.PI / 180.0)), 0.0, (double)afm.a(this.p * (float) (Math.PI / 180.0))).d();
                     double _snowmanxxxxxxxx = Math.abs(_snowmanxxxxxx.b(_snowmanxxxxxxx));
                     if (_snowmanxxxxxxxx < 0.8F) {
                        return;
                     }

                     dcn _snowmanxxxxxxxxx = this.cC();
                     dcn _snowmanxxxxxxxxxx = _snowman.cC();
                     if (((bhl)_snowman).o() == bhl.a.c && this.o() != bhl.a.c) {
                        this.f(_snowmanxxxxxxxxx.d(0.2, 1.0, 0.2));
                        this.i(_snowmanxxxxxxxxxx.b - _snowman, 0.0, _snowmanxxxxxxxxxx.d - _snowmanx);
                        _snowman.f(_snowmanxxxxxxxxxx.d(0.95, 1.0, 0.95));
                     } else if (((bhl)_snowman).o() != bhl.a.c && this.o() == bhl.a.c) {
                        _snowman.f(_snowmanxxxxxxxxxx.d(0.2, 1.0, 0.2));
                        _snowman.i(_snowmanxxxxxxxxx.b + _snowman, 0.0, _snowmanxxxxxxxxx.d + _snowmanx);
                        this.f(_snowmanxxxxxxxxx.d(0.95, 1.0, 0.95));
                     } else {
                        double _snowmanxxxxxxxxxxx = (_snowmanxxxxxxxxxx.b + _snowmanxxxxxxxxx.b) / 2.0;
                        double _snowmanxxxxxxxxxxxx = (_snowmanxxxxxxxxxx.d + _snowmanxxxxxxxxx.d) / 2.0;
                        this.f(_snowmanxxxxxxxxx.d(0.2, 1.0, 0.2));
                        this.i(_snowmanxxxxxxxxxxx - _snowman, 0.0, _snowmanxxxxxxxxxxxx - _snowmanx);
                        _snowman.f(_snowmanxxxxxxxxxx.d(0.2, 1.0, 0.2));
                        _snowman.i(_snowmanxxxxxxxxxxx + _snowman, 0.0, _snowmanxxxxxxxxxxxx + _snowmanx);
                     }
                  } else {
                     this.i(-_snowman, 0.0, -_snowmanx);
                     _snowman.i(_snowman / 4.0, 0.0, _snowmanx / 4.0);
                  }
               }
            }
         }
      }
   }

   @Override
   public void a(double var1, double var3, double var5, float var7, float var8, int var9, boolean var10) {
      this.ak = _snowman;
      this.al = _snowman;
      this.am = _snowman;
      this.an = (double)_snowman;
      this.ao = (double)_snowman;
      this.aj = _snowman + 2;
      this.n(this.ap, this.aq, this.ar);
   }

   @Override
   public void k(double var1, double var3, double var5) {
      this.ap = _snowman;
      this.aq = _snowman;
      this.ar = _snowman;
      this.n(this.ap, this.aq, this.ar);
   }

   public void a(float var1) {
      this.R.b(d, _snowman);
   }

   public float k() {
      return this.R.a(d);
   }

   public void c(int var1) {
      this.R.b(b, _snowman);
   }

   public int m() {
      return this.R.a(b);
   }

   public void d(int var1) {
      this.R.b(c, _snowman);
   }

   public int n() {
      return this.R.a(c);
   }

   public abstract bhl.a o();

   public ceh p() {
      return !this.t() ? this.q() : buo.a(this.ab().a(e));
   }

   public ceh q() {
      return bup.a.n();
   }

   public int r() {
      return !this.t() ? this.s() : this.ab().a(f);
   }

   public int s() {
      return 6;
   }

   public void b(ceh var1) {
      this.ab().b(e, buo.i(_snowman));
      this.a(true);
   }

   public void l(int var1) {
      this.ab().b(f, _snowman);
      this.a(true);
   }

   public boolean t() {
      return this.ab().a(g);
   }

   public void a(boolean var1) {
      this.ab().b(g, _snowman);
   }

   @Override
   public oj<?> P() {
      return new on(this);
   }

   public static enum a {
      a,
      b,
      c,
      d,
      e,
      f,
      g;

      private a() {
      }
   }
}
