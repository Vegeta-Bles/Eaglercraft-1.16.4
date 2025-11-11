import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Supplier;
import javax.annotation.Nullable;

public class bve extends btn<ccn> implements bzu {
   public static final cfb b = bxm.aq;
   public static final cfe<cez> c = cex.aF;
   public static final cey d = cex.C;
   protected static final ddh e = buo.a(1.0, 0.0, 0.0, 15.0, 14.0, 15.0);
   protected static final ddh f = buo.a(1.0, 0.0, 1.0, 15.0, 14.0, 16.0);
   protected static final ddh g = buo.a(0.0, 0.0, 1.0, 15.0, 14.0, 15.0);
   protected static final ddh h = buo.a(1.0, 0.0, 1.0, 16.0, 14.0, 15.0);
   protected static final ddh i = buo.a(1.0, 0.0, 1.0, 15.0, 14.0, 15.0);
   private static final bwc.b<ccn, Optional<aon>> j = new bwc.b<ccn, Optional<aon>>() {
      public Optional<aon> a(ccn var1, ccn var2) {
         return Optional.of(new aom(_snowman, _snowman));
      }

      public Optional<aon> a(ccn var1) {
         return Optional.of(_snowman);
      }

      public Optional<aon> a() {
         return Optional.empty();
      }
   };
   private static final bwc.b<ccn, Optional<aox>> k = new bwc.b<ccn, Optional<aox>>() {
      public Optional<aox> a(final ccn var1, final ccn var2) {
         final aon _snowman = new aom(_snowman, _snowman);
         return Optional.of(new aox() {
            @Nullable
            @Override
            public bic createMenu(int var1x, bfv var2x, bfw var3x) {
               if (_snowman.e(_snowman) && _snowman.e(_snowman)) {
                  _snowman.d(_snowman.e);
                  _snowman.d(_snowman.e);
                  return bij.b(_snowman, _snowman, _snowman);
               } else {
                  return null;
               }
            }

            @Override
            public nr d() {
               if (_snowman.S()) {
                  return _snowman.d();
               } else {
                  return (nr)(_snowman.S() ? _snowman.d() : new of("container.chestDouble"));
               }
            }
         });
      }

      public Optional<aox> a(ccn var1) {
         return Optional.of(_snowman);
      }

      public Optional<aox> a() {
         return Optional.empty();
      }
   };

   protected bve(ceg.c var1, Supplier<cck<? extends ccn>> var2) {
      super(_snowman, _snowman);
      this.j(this.n.b().a(b, gc.c).a(c, cez.a).a(d, Boolean.valueOf(false)));
   }

   public static bwc.a g(ceh var0) {
      cez _snowman = _snowman.c(c);
      if (_snowman == cez.a) {
         return bwc.a.a;
      } else {
         return _snowman == cez.c ? bwc.a.b : bwc.a.c;
      }
   }

   @Override
   public bzh b(ceh var1) {
      return bzh.b;
   }

   @Override
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      if (_snowman.c(d)) {
         _snowman.I().a(_snowman, cuy.c, cuy.c.a(_snowman));
      }

      if (_snowman.a(this) && _snowman.n().d()) {
         cez _snowman = _snowman.c(c);
         if (_snowman.c(c) == cez.a && _snowman != cez.a && _snowman.c(b) == _snowman.c(b) && h(_snowman) == _snowman.f()) {
            return _snowman.a(c, _snowman.b());
         }
      } else if (h(_snowman) == _snowman) {
         return _snowman.a(c, cez.a);
      }

      return super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      if (_snowman.c(c) == cez.a) {
         return i;
      } else {
         switch (h(_snowman)) {
            case c:
            default:
               return e;
            case d:
               return f;
            case e:
               return g;
            case f:
               return h;
         }
      }
   }

   public static gc h(ceh var0) {
      gc _snowman = _snowman.c(b);
      return _snowman.c(c) == cez.b ? _snowman.g() : _snowman.h();
   }

   @Override
   public ceh a(bny var1) {
      cez _snowman = cez.a;
      gc _snowmanx = _snowman.f().f();
      cux _snowmanxx = _snowman.p().b(_snowman.a());
      boolean _snowmanxxx = _snowman.g();
      gc _snowmanxxxx = _snowman.j();
      if (_snowmanxxxx.n().d() && _snowmanxxx) {
         gc _snowmanxxxxx = this.a(_snowman, _snowmanxxxx.f());
         if (_snowmanxxxxx != null && _snowmanxxxxx.n() != _snowmanxxxx.n()) {
            _snowmanx = _snowmanxxxxx;
            _snowman = _snowmanxxxxx.h() == _snowmanxxxx.f() ? cez.c : cez.b;
         }
      }

      if (_snowman == cez.a && !_snowmanxxx) {
         if (_snowmanx == this.a(_snowman, _snowmanx.g())) {
            _snowman = cez.b;
         } else if (_snowmanx == this.a(_snowman, _snowmanx.h())) {
            _snowman = cez.c;
         }
      }

      return this.n().a(b, _snowmanx).a(c, _snowman).a(d, Boolean.valueOf(_snowmanxx.a() == cuy.c));
   }

   @Override
   public cux d(ceh var1) {
      return _snowman.c(d) ? cuy.c.a(false) : super.d(_snowman);
   }

   @Nullable
   private gc a(bny var1, gc var2) {
      ceh _snowman = _snowman.p().d_(_snowman.a().a(_snowman));
      return _snowman.a(this) && _snowman.c(c) == cez.a ? _snowman.c(b) : null;
   }

   @Override
   public void a(brx var1, fx var2, ceh var3, aqm var4, bmb var5) {
      if (_snowman.t()) {
         ccj _snowman = _snowman.c(_snowman);
         if (_snowman instanceof ccn) {
            ((ccn)_snowman).a(_snowman.r());
         }
      }
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, ceh var4, boolean var5) {
      if (!_snowman.a(_snowman.b())) {
         ccj _snowman = _snowman.c(_snowman);
         if (_snowman instanceof aon) {
            aoq.a(_snowman, _snowman, (aon)_snowman);
            _snowman.c(_snowman, this);
         }

         super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }

   @Override
   public aou a(ceh var1, brx var2, fx var3, bfw var4, aot var5, dcj var6) {
      if (_snowman.v) {
         return aou.a;
      } else {
         aox _snowman = this.b(_snowman, _snowman, _snowman);
         if (_snowman != null) {
            _snowman.a(_snowman);
            _snowman.b(this.c());
            bet.a(_snowman, true);
         }

         return aou.b;
      }
   }

   protected adx<vk> c() {
      return aea.i.b(aea.an);
   }

   @Nullable
   public static aon a(bve var0, ceh var1, brx var2, fx var3, boolean var4) {
      return _snowman.a(_snowman, _snowman, _snowman, _snowman).apply(j).orElse(null);
   }

   @Override
   public bwc.c<? extends ccn> a(ceh var1, brx var2, fx var3, boolean var4) {
      BiPredicate<bry, fx> _snowman;
      if (_snowman) {
         _snowman = (var0, var1x) -> false;
      } else {
         _snowman = bve::a;
      }

      return bwc.a(this.a.get(), bve::g, bve::h, b, _snowman, _snowman, _snowman, _snowman);
   }

   @Nullable
   @Override
   public aox b(ceh var1, brx var2, fx var3) {
      return this.a(_snowman, _snowman, _snowman, false).apply(k).orElse(null);
   }

   public static bwc.b<ccn, Float2FloatFunction> a(final cdc var0) {
      return new bwc.b<ccn, Float2FloatFunction>() {
         public Float2FloatFunction a(ccn var1, ccn var2) {
            return var2x -> Math.max(_snowman.a(var2x), _snowman.a(var2x));
         }

         public Float2FloatFunction a(ccn var1) {
            return _snowman::a;
         }

         public Float2FloatFunction a() {
            return _snowman::a;
         }
      };
   }

   @Override
   public ccj a(brc var1) {
      return new ccn();
   }

   public static boolean a(bry var0, fx var1) {
      return a((brc)_snowman, _snowman) || b(_snowman, _snowman);
   }

   private static boolean a(brc var0, fx var1) {
      fx _snowman = _snowman.b();
      return _snowman.d_(_snowman).g(_snowman, _snowman);
   }

   private static boolean b(bry var0, fx var1) {
      List<bab> _snowman = _snowman.a(bab.class, new dci((double)_snowman.u(), (double)(_snowman.v() + 1), (double)_snowman.w(), (double)(_snowman.u() + 1), (double)(_snowman.v() + 2), (double)(_snowman.w() + 1)));
      if (!_snowman.isEmpty()) {
         for (bab _snowmanx : _snowman) {
            if (_snowmanx.eM()) {
               return true;
            }
         }
      }

      return false;
   }

   @Override
   public boolean a(ceh var1) {
      return true;
   }

   @Override
   public int a(ceh var1, brx var2, fx var3) {
      return bic.b(a(this, _snowman, _snowman, _snowman, false));
   }

   @Override
   public ceh a(ceh var1, bzm var2) {
      return _snowman.a(b, _snowman.a(_snowman.c(b)));
   }

   @Override
   public ceh a(ceh var1, byg var2) {
      return _snowman.a(_snowman.a(_snowman.c(b)));
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(b, c, d);
   }

   @Override
   public boolean a(ceh var1, brc var2, fx var3, cxe var4) {
      return false;
   }
}
