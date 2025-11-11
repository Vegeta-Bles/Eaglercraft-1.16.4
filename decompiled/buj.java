import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import org.apache.commons.lang3.ArrayUtils;

public class buj extends bxm implements bwm {
   public static final cfe<cev> a = cex.aE;
   public static final cey b = cex.t;
   protected static final ddh c = buo.a(0.0, 3.0, 0.0, 16.0, 9.0, 16.0);
   protected static final ddh d = buo.a(0.0, 0.0, 0.0, 3.0, 3.0, 3.0);
   protected static final ddh e = buo.a(0.0, 0.0, 13.0, 3.0, 3.0, 16.0);
   protected static final ddh f = buo.a(13.0, 0.0, 0.0, 16.0, 3.0, 3.0);
   protected static final ddh g = buo.a(13.0, 0.0, 13.0, 16.0, 3.0, 16.0);
   protected static final ddh h = dde.a(c, d, f);
   protected static final ddh i = dde.a(c, e, g);
   protected static final ddh j = dde.a(c, d, e);
   protected static final ddh k = dde.a(c, f, g);
   private final bkx o;

   public buj(bkx var1, ceg.c var2) {
      super(_snowman);
      this.o = _snowman;
      this.j(this.n.b().a(a, cev.b).a(b, Boolean.valueOf(false)));
   }

   @Nullable
   public static gc a(brc var0, fx var1) {
      ceh _snowman = _snowman.d_(_snowman);
      return _snowman.b() instanceof buj ? _snowman.c(aq) : null;
   }

   @Override
   public aou a(ceh var1, brx var2, fx var3, bfw var4, aot var5, dcj var6) {
      if (_snowman.v) {
         return aou.b;
      } else {
         if (_snowman.c(a) != cev.a) {
            _snowman = _snowman.a(_snowman.c(aq));
            _snowman = _snowman.d_(_snowman);
            if (!_snowman.a(this)) {
               return aou.b;
            }
         }

         if (!a(_snowman)) {
            _snowman.a(_snowman, false);
            fx _snowman = _snowman.a(_snowman.c(aq).f());
            if (_snowman.d_(_snowman).a(this)) {
               _snowman.a(_snowman, false);
            }

            _snowman.a(null, apk.a(), null, (double)_snowman.u() + 0.5, (double)_snowman.v() + 0.5, (double)_snowman.w() + 0.5, 5.0F, true, brp.a.c);
            return aou.a;
         } else if (_snowman.c(b)) {
            if (!this.a(_snowman, _snowman)) {
               _snowman.a(new of("block.minecraft.bed.occupied"), true);
            }

            return aou.a;
         } else {
            _snowman.a(_snowman).ifLeft(var1x -> {
               if (var1x != null) {
                  _snowman.a(var1x.a(), true);
               }
            });
            return aou.a;
         }
      }
   }

   public static boolean a(brx var0) {
      return _snowman.k().h();
   }

   private boolean a(brx var1, fx var2) {
      List<bfj> _snowman = _snowman.a(bfj.class, new dci(_snowman), aqm::em);
      if (_snowman.isEmpty()) {
         return false;
      } else {
         _snowman.get(0).en();
         return true;
      }
   }

   @Override
   public void a(brx var1, fx var2, aqa var3, float var4) {
      super.a(_snowman, _snowman, _snowman, _snowman * 0.5F);
   }

   @Override
   public void a(brc var1, aqa var2) {
      if (_snowman.bw()) {
         super.a(_snowman, _snowman);
      } else {
         this.a(_snowman);
      }
   }

   private void a(aqa var1) {
      dcn _snowman = _snowman.cC();
      if (_snowman.c < 0.0) {
         double _snowmanx = _snowman instanceof aqm ? 1.0 : 0.8;
         _snowman.n(_snowman.b, -_snowman.c * 0.66F * _snowmanx, _snowman.d);
      }
   }

   @Override
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      if (_snowman == a(_snowman.c(a), _snowman.c(aq))) {
         return _snowman.a(this) && _snowman.c(a) != _snowman.c(a) ? _snowman.a(b, _snowman.c(b)) : bup.a.n();
      } else {
         return super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }

   private static gc a(cev var0, gc var1) {
      return _snowman == cev.b ? _snowman : _snowman.f();
   }

   @Override
   public void a(brx var1, fx var2, ceh var3, bfw var4) {
      if (!_snowman.v && _snowman.b_()) {
         cev _snowman = _snowman.c(a);
         if (_snowman == cev.b) {
            fx _snowmanx = _snowman.a(a(_snowman, _snowman.c(aq)));
            ceh _snowmanxx = _snowman.d_(_snowmanx);
            if (_snowmanxx.b() == this && _snowmanxx.c(a) == cev.a) {
               _snowman.a(_snowmanx, bup.a.n(), 35);
               _snowman.a(_snowman, 2001, _snowmanx, buo.i(_snowmanxx));
            }
         }
      }

      super.a(_snowman, _snowman, _snowman, _snowman);
   }

   @Nullable
   @Override
   public ceh a(bny var1) {
      gc _snowman = _snowman.f();
      fx _snowmanx = _snowman.a();
      fx _snowmanxx = _snowmanx.a(_snowman);
      return _snowman.p().d_(_snowmanxx).a(_snowman) ? this.n().a(aq, _snowman) : null;
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      gc _snowman = g(_snowman).f();
      switch (_snowman) {
         case c:
            return h;
         case d:
            return i;
         case e:
            return j;
         default:
            return k;
      }
   }

   public static gc g(ceh var0) {
      gc _snowman = _snowman.c(aq);
      return _snowman.c(a) == cev.a ? _snowman.f() : _snowman;
   }

   public static bwc.a h(ceh var0) {
      cev _snowman = _snowman.c(a);
      return _snowman == cev.a ? bwc.a.b : bwc.a.c;
   }

   private static boolean b(brc var0, fx var1) {
      return _snowman.d_(_snowman.c()).b() instanceof buj;
   }

   public static Optional<dcn> a(aqe<?> var0, brg var1, fx var2, float var3) {
      gc _snowman = _snowman.d_(_snowman).c(aq);
      gc _snowmanx = _snowman.g();
      gc _snowmanxx = _snowmanx.a(_snowman) ? _snowmanx.f() : _snowmanx;
      if (b(_snowman, _snowman)) {
         return a(_snowman, _snowman, _snowman, _snowman, _snowmanxx);
      } else {
         int[][] _snowmanxxx = a(_snowman, _snowmanxx);
         Optional<dcn> _snowmanxxxx = a(_snowman, _snowman, _snowman, _snowmanxxx, true);
         return _snowmanxxxx.isPresent() ? _snowmanxxxx : a(_snowman, _snowman, _snowman, _snowmanxxx, false);
      }
   }

   private static Optional<dcn> a(aqe<?> var0, brg var1, fx var2, gc var3, gc var4) {
      int[][] _snowman = b(_snowman, _snowman);
      Optional<dcn> _snowmanx = a(_snowman, _snowman, _snowman, _snowman, true);
      if (_snowmanx.isPresent()) {
         return _snowmanx;
      } else {
         fx _snowmanxx = _snowman.c();
         Optional<dcn> _snowmanxxx = a(_snowman, _snowman, _snowmanxx, _snowman, true);
         if (_snowmanxxx.isPresent()) {
            return _snowmanxxx;
         } else {
            int[][] _snowmanxxxx = a(_snowman);
            Optional<dcn> _snowmanxxxxx = a(_snowman, _snowman, _snowman, _snowmanxxxx, true);
            if (_snowmanxxxxx.isPresent()) {
               return _snowmanxxxxx;
            } else {
               Optional<dcn> _snowmanxxxxxx = a(_snowman, _snowman, _snowman, _snowman, false);
               if (_snowmanxxxxxx.isPresent()) {
                  return _snowmanxxxxxx;
               } else {
                  Optional<dcn> _snowmanxxxxxxx = a(_snowman, _snowman, _snowmanxx, _snowman, false);
                  return _snowmanxxxxxxx.isPresent() ? _snowmanxxxxxxx : a(_snowman, _snowman, _snowman, _snowmanxxxx, false);
               }
            }
         }
      }
   }

   private static Optional<dcn> a(aqe<?> var0, brg var1, fx var2, int[][] var3, boolean var4) {
      fx.a _snowman = new fx.a();

      for (int[] _snowmanx : _snowman) {
         _snowman.d(_snowman.u() + _snowmanx[0], _snowman.v(), _snowman.w() + _snowmanx[1]);
         dcn _snowmanxx = bho.a(_snowman, _snowman, _snowman, _snowman);
         if (_snowmanxx != null) {
            return Optional.of(_snowmanxx);
         }
      }

      return Optional.empty();
   }

   @Override
   public cvc f(ceh var1) {
      return cvc.b;
   }

   @Override
   public bzh b(ceh var1) {
      return bzh.b;
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(aq, a, b);
   }

   @Override
   public ccj a(brc var1) {
      return new ccf(this.o);
   }

   @Override
   public void a(brx var1, fx var2, ceh var3, @Nullable aqm var4, bmb var5) {
      super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
      if (!_snowman.v) {
         fx _snowman = _snowman.a(_snowman.c(aq));
         _snowman.a(_snowman, _snowman.a(a, cev.a), 3);
         _snowman.a(_snowman, bup.a);
         _snowman.a(_snowman, _snowman, 3);
      }
   }

   public bkx c() {
      return this.o;
   }

   @Override
   public long a(ceh var1, fx var2) {
      fx _snowman = _snowman.a(_snowman.c(aq), _snowman.c(a) == cev.a ? 0 : 1);
      return afm.c(_snowman.u(), _snowman.v(), _snowman.w());
   }

   @Override
   public boolean a(ceh var1, brc var2, fx var3, cxe var4) {
      return false;
   }

   private static int[][] a(gc var0, gc var1) {
      return (int[][])ArrayUtils.addAll(b(_snowman, _snowman), a(_snowman));
   }

   private static int[][] b(gc var0, gc var1) {
      return new int[][]{
         {_snowman.i(), _snowman.k()},
         {_snowman.i() - _snowman.i(), _snowman.k() - _snowman.k()},
         {_snowman.i() - _snowman.i() * 2, _snowman.k() - _snowman.k() * 2},
         {-_snowman.i() * 2, -_snowman.k() * 2},
         {-_snowman.i() - _snowman.i() * 2, -_snowman.k() - _snowman.k() * 2},
         {-_snowman.i() - _snowman.i(), -_snowman.k() - _snowman.k()},
         {-_snowman.i(), -_snowman.k()},
         {-_snowman.i() + _snowman.i(), -_snowman.k() + _snowman.k()},
         {_snowman.i(), _snowman.k()},
         {_snowman.i() + _snowman.i(), _snowman.k() + _snowman.k()}
      };
   }

   private static int[][] a(gc var0) {
      return new int[][]{{0, 0}, {-_snowman.i(), -_snowman.k()}};
   }
}
