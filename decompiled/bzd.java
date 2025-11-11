import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.collect.UnmodifiableIterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;

public class bzd extends buo {
   public static final cfe<cfl> a = cex.X;
   public static final cfe<cfl> b = cex.W;
   public static final cfe<cfl> c = cex.Y;
   public static final cfe<cfl> d = cex.Z;
   public static final cfg e = cex.az;
   public static final Map<gc, cfe<cfl>> f = Maps.newEnumMap(ImmutableMap.of(gc.c, a, gc.f, b, gc.d, c, gc.e, d));
   private static final ddh g = buo.a(3.0, 0.0, 3.0, 13.0, 1.0, 13.0);
   private static final Map<gc, ddh> h = Maps.newEnumMap(
      ImmutableMap.of(
         gc.c,
         buo.a(3.0, 0.0, 0.0, 13.0, 1.0, 13.0),
         gc.d,
         buo.a(3.0, 0.0, 3.0, 13.0, 1.0, 16.0),
         gc.f,
         buo.a(3.0, 0.0, 3.0, 16.0, 1.0, 13.0),
         gc.e,
         buo.a(0.0, 0.0, 3.0, 13.0, 1.0, 13.0)
      )
   );
   private static final Map<gc, ddh> i = Maps.newEnumMap(
      ImmutableMap.of(
         gc.c,
         dde.a(h.get(gc.c), buo.a(3.0, 0.0, 0.0, 13.0, 16.0, 1.0)),
         gc.d,
         dde.a(h.get(gc.d), buo.a(3.0, 0.0, 15.0, 13.0, 16.0, 16.0)),
         gc.f,
         dde.a(h.get(gc.f), buo.a(15.0, 0.0, 3.0, 16.0, 16.0, 13.0)),
         gc.e,
         dde.a(h.get(gc.e), buo.a(0.0, 0.0, 3.0, 1.0, 16.0, 13.0))
      )
   );
   private final Map<ceh, ddh> j = Maps.newHashMap();
   private static final g[] k = new g[16];
   private final ceh o;
   private boolean p = true;

   public bzd(ceg.c var1) {
      super(_snowman);
      this.j(this.n.b().a(a, cfl.c).a(b, cfl.c).a(c, cfl.c).a(d, cfl.c).a(e, Integer.valueOf(0)));
      this.o = this.n().a(a, cfl.b).a(b, cfl.b).a(c, cfl.b).a(d, cfl.b);
      UnmodifiableIterator var2 = this.m().a().iterator();

      while (var2.hasNext()) {
         ceh _snowman = (ceh)var2.next();
         if (_snowman.c(e) == 0) {
            this.j.put(_snowman, this.l(_snowman));
         }
      }
   }

   private ddh l(ceh var1) {
      ddh _snowman = g;

      for (gc _snowmanx : gc.c.a) {
         cfl _snowmanxx = _snowman.c(f.get(_snowmanx));
         if (_snowmanxx == cfl.b) {
            _snowman = dde.a(_snowman, h.get(_snowmanx));
         } else if (_snowmanxx == cfl.a) {
            _snowman = dde.a(_snowman, i.get(_snowmanx));
         }
      }

      return _snowman;
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      return this.j.get(_snowman.a(e, Integer.valueOf(0)));
   }

   @Override
   public ceh a(bny var1) {
      return this.a(_snowman.p(), this.o, _snowman.a());
   }

   private ceh a(brc var1, ceh var2, fx var3) {
      boolean _snowman = n(_snowman);
      _snowman = this.b(_snowman, this.n().a(e, _snowman.c(e)), _snowman);
      if (_snowman && n(_snowman)) {
         return _snowman;
      } else {
         boolean _snowmanx = _snowman.c(a).b();
         boolean _snowmanxx = _snowman.c(c).b();
         boolean _snowmanxxx = _snowman.c(b).b();
         boolean _snowmanxxxx = _snowman.c(d).b();
         boolean _snowmanxxxxx = !_snowmanx && !_snowmanxx;
         boolean _snowmanxxxxxx = !_snowmanxxx && !_snowmanxxxx;
         if (!_snowmanxxxx && _snowmanxxxxx) {
            _snowman = _snowman.a(d, cfl.b);
         }

         if (!_snowmanxxx && _snowmanxxxxx) {
            _snowman = _snowman.a(b, cfl.b);
         }

         if (!_snowmanx && _snowmanxxxxxx) {
            _snowman = _snowman.a(a, cfl.b);
         }

         if (!_snowmanxx && _snowmanxxxxxx) {
            _snowman = _snowman.a(c, cfl.b);
         }

         return _snowman;
      }
   }

   private ceh b(brc var1, ceh var2, fx var3) {
      boolean _snowman = !_snowman.d_(_snowman.b()).g(_snowman, _snowman);

      for (gc _snowmanx : gc.c.a) {
         if (!_snowman.c(f.get(_snowmanx)).b()) {
            cfl _snowmanxx = this.a(_snowman, _snowman, _snowmanx, _snowman);
            _snowman = _snowman.a(f.get(_snowmanx), _snowmanxx);
         }
      }

      return _snowman;
   }

   @Override
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      if (_snowman == gc.a) {
         return _snowman;
      } else if (_snowman == gc.b) {
         return this.a(_snowman, _snowman, _snowman);
      } else {
         cfl _snowman = this.a(_snowman, _snowman, _snowman);
         return _snowman.b() == _snowman.c(f.get(_snowman)).b() && !m(_snowman) ? _snowman.a(f.get(_snowman), _snowman) : this.a(_snowman, this.o.a(e, _snowman.c(e)).a(f.get(_snowman), _snowman), _snowman);
      }
   }

   private static boolean m(ceh var0) {
      return _snowman.c(a).b() && _snowman.c(c).b() && _snowman.c(b).b() && _snowman.c(d).b();
   }

   private static boolean n(ceh var0) {
      return !_snowman.c(a).b() && !_snowman.c(c).b() && !_snowman.c(b).b() && !_snowman.c(d).b();
   }

   @Override
   public void a(ceh var1, bry var2, fx var3, int var4, int var5) {
      fx.a _snowman = new fx.a();

      for (gc _snowmanx : gc.c.a) {
         cfl _snowmanxx = _snowman.c(f.get(_snowmanx));
         if (_snowmanxx != cfl.c && !_snowman.d_(_snowman.a(_snowman, _snowmanx)).a(this)) {
            _snowman.c(gc.a);
            ceh _snowmanxxx = _snowman.d_(_snowman);
            if (!_snowmanxxx.a(bup.iO)) {
               fx _snowmanxxxx = _snowman.a(_snowmanx.f());
               ceh _snowmanxxxxx = _snowmanxxx.a(_snowmanx.f(), _snowman.d_(_snowmanxxxx), _snowman, _snowman, _snowmanxxxx);
               a(_snowmanxxx, _snowmanxxxxx, _snowman, _snowman, _snowman, _snowman);
            }

            _snowman.a(_snowman, _snowmanx).c(gc.b);
            ceh _snowmanxxxx = _snowman.d_(_snowman);
            if (!_snowmanxxxx.a(bup.iO)) {
               fx _snowmanxxxxx = _snowman.a(_snowmanx.f());
               ceh _snowmanxxxxxx = _snowmanxxxx.a(_snowmanx.f(), _snowman.d_(_snowmanxxxxx), _snowman, _snowman, _snowmanxxxxx);
               a(_snowmanxxxx, _snowmanxxxxxx, _snowman, _snowman, _snowman, _snowman);
            }
         }
      }
   }

   private cfl a(brc var1, fx var2, gc var3) {
      return this.a(_snowman, _snowman, _snowman, !_snowman.d_(_snowman.b()).g(_snowman, _snowman));
   }

   private cfl a(brc var1, fx var2, gc var3, boolean var4) {
      fx _snowman = _snowman.a(_snowman);
      ceh _snowmanx = _snowman.d_(_snowman);
      if (_snowman) {
         boolean _snowmanxx = this.b(_snowman, _snowman, _snowmanx);
         if (_snowmanxx && h(_snowman.d_(_snowman.b()))) {
            if (_snowmanx.d(_snowman, _snowman, _snowman.f())) {
               return cfl.a;
            }

            return cfl.b;
         }
      }

      return !a(_snowmanx, _snowman) && (_snowmanx.g(_snowman, _snowman) || !h(_snowman.d_(_snowman.c()))) ? cfl.c : cfl.b;
   }

   @Override
   public boolean a(ceh var1, brz var2, fx var3) {
      fx _snowman = _snowman.c();
      ceh _snowmanx = _snowman.d_(_snowman);
      return this.b(_snowman, _snowman, _snowmanx);
   }

   private boolean b(brc var1, fx var2, ceh var3) {
      return _snowman.d(_snowman, _snowman, gc.b) || _snowman.a(bup.fy);
   }

   private void a(brx var1, fx var2, ceh var3) {
      int _snowman = this.a(_snowman, _snowman);
      if (_snowman.c(e) != _snowman) {
         if (_snowman.d_(_snowman) == _snowman) {
            _snowman.a(_snowman, _snowman.a(e, Integer.valueOf(_snowman)), 2);
         }

         Set<fx> _snowmanx = Sets.newHashSet();
         _snowmanx.add(_snowman);

         for (gc _snowmanxx : gc.values()) {
            _snowmanx.add(_snowman.a(_snowmanxx));
         }

         for (fx _snowmanxx : _snowmanx) {
            _snowman.b(_snowmanxx, this);
         }
      }
   }

   private int a(brx var1, fx var2) {
      this.p = false;
      int _snowman = _snowman.s(_snowman);
      this.p = true;
      int _snowmanx = 0;
      if (_snowman < 15) {
         for (gc _snowmanxx : gc.c.a) {
            fx _snowmanxxx = _snowman.a(_snowmanxx);
            ceh _snowmanxxxx = _snowman.d_(_snowmanxxx);
            _snowmanx = Math.max(_snowmanx, this.o(_snowmanxxxx));
            fx _snowmanxxxxx = _snowman.b();
            if (_snowmanxxxx.g(_snowman, _snowmanxxx) && !_snowman.d_(_snowmanxxxxx).g(_snowman, _snowmanxxxxx)) {
               _snowmanx = Math.max(_snowmanx, this.o(_snowman.d_(_snowmanxxx.b())));
            } else if (!_snowmanxxxx.g(_snowman, _snowmanxxx)) {
               _snowmanx = Math.max(_snowmanx, this.o(_snowman.d_(_snowmanxxx.c())));
            }
         }
      }

      return Math.max(_snowman, _snowmanx - 1);
   }

   private int o(ceh var1) {
      return _snowman.a(this) ? _snowman.c(e) : 0;
   }

   private void b(brx var1, fx var2) {
      if (_snowman.d_(_snowman).a(this)) {
         _snowman.b(_snowman, this);

         for (gc _snowman : gc.values()) {
            _snowman.b(_snowman.a(_snowman), this);
         }
      }
   }

   @Override
   public void b(ceh var1, brx var2, fx var3, ceh var4, boolean var5) {
      if (!_snowman.a(_snowman.b()) && !_snowman.v) {
         this.a(_snowman, _snowman, _snowman);

         for (gc _snowman : gc.c.b) {
            _snowman.b(_snowman.a(_snowman), this);
         }

         this.d(_snowman, _snowman);
      }
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, ceh var4, boolean var5) {
      if (!_snowman && !_snowman.a(_snowman.b())) {
         super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
         if (!_snowman.v) {
            for (gc _snowman : gc.values()) {
               _snowman.b(_snowman.a(_snowman), this);
            }

            this.a(_snowman, _snowman, _snowman);
            this.d(_snowman, _snowman);
         }
      }
   }

   private void d(brx var1, fx var2) {
      for (gc _snowman : gc.c.a) {
         this.b(_snowman, _snowman.a(_snowman));
      }

      for (gc _snowman : gc.c.a) {
         fx _snowmanx = _snowman.a(_snowman);
         if (_snowman.d_(_snowmanx).g(_snowman, _snowmanx)) {
            this.b(_snowman, _snowmanx.b());
         } else {
            this.b(_snowman, _snowmanx.c());
         }
      }
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, buo var4, fx var5, boolean var6) {
      if (!_snowman.v) {
         if (_snowman.a((brz)_snowman, _snowman)) {
            this.a(_snowman, _snowman, _snowman);
         } else {
            c(_snowman, _snowman, _snowman);
            _snowman.a(_snowman, false);
         }
      }
   }

   @Override
   public int b(ceh var1, brc var2, fx var3, gc var4) {
      return !this.p ? 0 : _snowman.b(_snowman, _snowman, _snowman);
   }

   @Override
   public int a(ceh var1, brc var2, fx var3, gc var4) {
      if (this.p && _snowman != gc.a) {
         int _snowman = _snowman.c(e);
         if (_snowman == 0) {
            return 0;
         } else {
            return _snowman != gc.b && !this.a(_snowman, _snowman, _snowman).c(f.get(_snowman.f())).b() ? 0 : _snowman;
         }
      } else {
         return 0;
      }
   }

   protected static boolean h(ceh var0) {
      return a(_snowman, null);
   }

   protected static boolean a(ceh var0, @Nullable gc var1) {
      if (_snowman.a(bup.bS)) {
         return true;
      } else if (_snowman.a(bup.cX)) {
         gc _snowman = _snowman.c(bzi.aq);
         return _snowman == _snowman || _snowman.f() == _snowman;
      } else {
         return _snowman.a(bup.iO) ? _snowman == _snowman.c(byq.a) : _snowman.i() && _snowman != null;
      }
   }

   @Override
   public boolean b_(ceh var1) {
      return this.p;
   }

   public static int b(int var0) {
      g _snowman = k[_snowman];
      return afm.e(_snowman.a(), _snowman.b(), _snowman.c());
   }

   private void a(brx var1, Random var2, fx var3, g var4, gc var5, gc var6, float var7, float var8) {
      float _snowman = _snowman - _snowman;
      if (!(_snowman.nextFloat() >= 0.2F * _snowman)) {
         float _snowmanx = 0.4375F;
         float _snowmanxx = _snowman + _snowman * _snowman.nextFloat();
         double _snowmanxxx = 0.5 + (double)(0.4375F * (float)_snowman.i()) + (double)(_snowmanxx * (float)_snowman.i());
         double _snowmanxxxx = 0.5 + (double)(0.4375F * (float)_snowman.j()) + (double)(_snowmanxx * (float)_snowman.j());
         double _snowmanxxxxx = 0.5 + (double)(0.4375F * (float)_snowman.k()) + (double)(_snowmanxx * (float)_snowman.k());
         _snowman.a(new hd(_snowman.a(), _snowman.b(), _snowman.c(), 1.0F), (double)_snowman.u() + _snowmanxxx, (double)_snowman.v() + _snowmanxxxx, (double)_snowman.w() + _snowmanxxxxx, 0.0, 0.0, 0.0);
      }
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, Random var4) {
      int _snowman = _snowman.c(e);
      if (_snowman != 0) {
         for (gc _snowmanx : gc.c.a) {
            cfl _snowmanxx = _snowman.c(f.get(_snowmanx));
            switch (_snowmanxx) {
               case a:
                  this.a(_snowman, _snowman, _snowman, k[_snowman], _snowmanx, gc.b, -0.5F, 0.5F);
               case b:
                  this.a(_snowman, _snowman, _snowman, k[_snowman], gc.a, _snowmanx, 0.0F, 0.5F);
                  break;
               case c:
               default:
                  this.a(_snowman, _snowman, _snowman, k[_snowman], gc.a, _snowmanx, 0.0F, 0.3F);
            }
         }
      }
   }

   @Override
   public ceh a(ceh var1, bzm var2) {
      switch (_snowman) {
         case c:
            return _snowman.a(a, _snowman.c(c)).a(b, _snowman.c(d)).a(c, _snowman.c(a)).a(d, _snowman.c(b));
         case d:
            return _snowman.a(a, _snowman.c(b)).a(b, _snowman.c(c)).a(c, _snowman.c(d)).a(d, _snowman.c(a));
         case b:
            return _snowman.a(a, _snowman.c(d)).a(b, _snowman.c(a)).a(c, _snowman.c(b)).a(d, _snowman.c(c));
         default:
            return _snowman;
      }
   }

   @Override
   public ceh a(ceh var1, byg var2) {
      switch (_snowman) {
         case b:
            return _snowman.a(a, _snowman.c(c)).a(c, _snowman.c(a));
         case c:
            return _snowman.a(b, _snowman.c(d)).a(d, _snowman.c(b));
         default:
            return super.a(_snowman, _snowman);
      }
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(a, b, c, d, e);
   }

   @Override
   public aou a(ceh var1, brx var2, fx var3, bfw var4, aot var5, dcj var6) {
      if (!_snowman.bC.e) {
         return aou.c;
      } else {
         if (m(_snowman) || n(_snowman)) {
            ceh _snowman = m(_snowman) ? this.n() : this.o;
            _snowman = _snowman.a(e, _snowman.c(e));
            _snowman = this.a(_snowman, _snowman, _snowman);
            if (_snowman != _snowman) {
               _snowman.a(_snowman, _snowman, 3);
               this.a(_snowman, _snowman, _snowman, _snowman);
               return aou.a;
            }
         }

         return aou.c;
      }
   }

   private void a(brx var1, fx var2, ceh var3, ceh var4) {
      for (gc _snowman : gc.c.a) {
         fx _snowmanx = _snowman.a(_snowman);
         if (_snowman.c(f.get(_snowman)).b() != _snowman.c(f.get(_snowman)).b() && _snowman.d_(_snowmanx).g(_snowman, _snowmanx)) {
            _snowman.a(_snowmanx, _snowman.b(), _snowman.f());
         }
      }
   }

   static {
      for (int _snowman = 0; _snowman <= 15; _snowman++) {
         float _snowmanx = (float)_snowman / 15.0F;
         float _snowmanxx = _snowmanx * 0.6F + (_snowmanx > 0.0F ? 0.4F : 0.3F);
         float _snowmanxxx = afm.a(_snowmanx * _snowmanx * 0.7F - 0.5F, 0.0F, 1.0F);
         float _snowmanxxxx = afm.a(_snowmanx * _snowmanx * 0.6F - 0.7F, 0.0F, 1.0F);
         k[_snowman] = new g(_snowmanxx, _snowmanxxx, _snowmanxxxx);
      }
   }
}
