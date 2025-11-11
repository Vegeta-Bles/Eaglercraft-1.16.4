import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class cbi extends buo {
   public static final cey a = bys.e;
   public static final cey b = bys.a;
   public static final cey c = bys.b;
   public static final cey d = bys.c;
   public static final cey e = bys.d;
   public static final Map<gc, cey> f = bys.g.entrySet().stream().filter(var0 -> var0.getKey() != gc.a).collect(x.a());
   private static final ddh g = buo.a(0.0, 15.0, 0.0, 16.0, 16.0, 16.0);
   private static final ddh h = buo.a(0.0, 0.0, 0.0, 1.0, 16.0, 16.0);
   private static final ddh i = buo.a(15.0, 0.0, 0.0, 16.0, 16.0, 16.0);
   private static final ddh j = buo.a(0.0, 0.0, 0.0, 16.0, 16.0, 1.0);
   private static final ddh k = buo.a(0.0, 0.0, 15.0, 16.0, 16.0, 16.0);
   private final Map<ceh, ddh> o;

   public cbi(ceg.c var1) {
      super(_snowman);
      this.j(
         this.n
            .b()
            .a(a, Boolean.valueOf(false))
            .a(b, Boolean.valueOf(false))
            .a(c, Boolean.valueOf(false))
            .a(d, Boolean.valueOf(false))
            .a(e, Boolean.valueOf(false))
      );
      this.o = ImmutableMap.copyOf(this.n.a().stream().collect(Collectors.toMap(Function.identity(), cbi::h)));
   }

   private static ddh h(ceh var0) {
      ddh _snowman = dde.a();
      if (_snowman.c(a)) {
         _snowman = g;
      }

      if (_snowman.c(b)) {
         _snowman = dde.a(_snowman, j);
      }

      if (_snowman.c(d)) {
         _snowman = dde.a(_snowman, k);
      }

      if (_snowman.c(c)) {
         _snowman = dde.a(_snowman, i);
      }

      if (_snowman.c(e)) {
         _snowman = dde.a(_snowman, h);
      }

      return _snowman;
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      return this.o.get(_snowman);
   }

   @Override
   public boolean a(ceh var1, brz var2, fx var3) {
      return this.l(this.g(_snowman, _snowman, _snowman));
   }

   private boolean l(ceh var1) {
      return this.m(_snowman) > 0;
   }

   private int m(ceh var1) {
      int _snowman = 0;

      for (cey _snowmanx : f.values()) {
         if (_snowman.c(_snowmanx)) {
            _snowman++;
         }
      }

      return _snowman;
   }

   private boolean b(brc var1, fx var2, gc var3) {
      if (_snowman == gc.a) {
         return false;
      } else {
         fx _snowman = _snowman.a(_snowman);
         if (a(_snowman, _snowman, _snowman)) {
            return true;
         } else if (_snowman.n() == gc.a.b) {
            return false;
         } else {
            cey _snowmanx = f.get(_snowman);
            ceh _snowmanxx = _snowman.d_(_snowman.b());
            return _snowmanxx.a(this) && _snowmanxx.c(_snowmanx);
         }
      }
   }

   public static boolean a(brc var0, fx var1, gc var2) {
      ceh _snowman = _snowman.d_(_snowman);
      return buo.a(_snowman.k(_snowman, _snowman), _snowman.f());
   }

   private ceh g(ceh var1, brc var2, fx var3) {
      fx _snowman = _snowman.b();
      if (_snowman.c(a)) {
         _snowman = _snowman.a(a, Boolean.valueOf(a(_snowman, _snowman, gc.a)));
      }

      ceh _snowmanx = null;

      for (gc _snowmanxx : gc.c.a) {
         cey _snowmanxxx = a(_snowmanxx);
         if (_snowman.c(_snowmanxxx)) {
            boolean _snowmanxxxx = this.b(_snowman, _snowman, _snowmanxx);
            if (!_snowmanxxxx) {
               if (_snowmanx == null) {
                  _snowmanx = _snowman.d_(_snowman);
               }

               _snowmanxxxx = _snowmanx.a(this) && _snowmanx.c(_snowmanxxx);
            }

            _snowman = _snowman.a(_snowmanxxx, Boolean.valueOf(_snowmanxxxx));
         }
      }

      return _snowman;
   }

   @Override
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      if (_snowman == gc.a) {
         return super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      } else {
         ceh _snowman = this.g(_snowman, _snowman, _snowman);
         return !this.l(_snowman) ? bup.a.n() : _snowman;
      }
   }

   @Override
   public void b(ceh var1, aag var2, fx var3, Random var4) {
      if (_snowman.t.nextInt(4) == 0) {
         gc _snowman = gc.a(_snowman);
         fx _snowmanx = _snowman.b();
         if (_snowman.n().d() && !_snowman.c(a(_snowman))) {
            if (this.a(_snowman, _snowman)) {
               fx _snowmanxx = _snowman.a(_snowman);
               ceh _snowmanxxx = _snowman.d_(_snowmanxx);
               if (_snowmanxxx.g()) {
                  gc _snowmanxxxx = _snowman.g();
                  gc _snowmanxxxxx = _snowman.h();
                  boolean _snowmanxxxxxx = _snowman.c(a(_snowmanxxxx));
                  boolean _snowmanxxxxxxx = _snowman.c(a(_snowmanxxxxx));
                  fx _snowmanxxxxxxxx = _snowmanxx.a(_snowmanxxxx);
                  fx _snowmanxxxxxxxxx = _snowmanxx.a(_snowmanxxxxx);
                  if (_snowmanxxxxxx && a(_snowman, _snowmanxxxxxxxx, _snowmanxxxx)) {
                     _snowman.a(_snowmanxx, this.n().a(a(_snowmanxxxx), Boolean.valueOf(true)), 2);
                  } else if (_snowmanxxxxxxx && a(_snowman, _snowmanxxxxxxxxx, _snowmanxxxxx)) {
                     _snowman.a(_snowmanxx, this.n().a(a(_snowmanxxxxx), Boolean.valueOf(true)), 2);
                  } else {
                     gc _snowmanxxxxxxxxxx = _snowman.f();
                     if (_snowmanxxxxxx && _snowman.w(_snowmanxxxxxxxx) && a(_snowman, _snowman.a(_snowmanxxxx), _snowmanxxxxxxxxxx)) {
                        _snowman.a(_snowmanxxxxxxxx, this.n().a(a(_snowmanxxxxxxxxxx), Boolean.valueOf(true)), 2);
                     } else if (_snowmanxxxxxxx && _snowman.w(_snowmanxxxxxxxxx) && a(_snowman, _snowman.a(_snowmanxxxxx), _snowmanxxxxxxxxxx)) {
                        _snowman.a(_snowmanxxxxxxxxx, this.n().a(a(_snowmanxxxxxxxxxx), Boolean.valueOf(true)), 2);
                     } else if ((double)_snowman.t.nextFloat() < 0.05 && a(_snowman, _snowmanxx.b(), gc.b)) {
                        _snowman.a(_snowmanxx, this.n().a(a, Boolean.valueOf(true)), 2);
                     }
                  }
               } else if (a(_snowman, _snowmanxx, _snowman)) {
                  _snowman.a(_snowman, _snowman.a(a(_snowman), Boolean.valueOf(true)), 2);
               }
            }
         } else {
            if (_snowman == gc.b && _snowman.v() < 255) {
               if (this.b(_snowman, _snowman, _snowman)) {
                  _snowman.a(_snowman, _snowman.a(a, Boolean.valueOf(true)), 2);
                  return;
               }

               if (_snowman.w(_snowmanx)) {
                  if (!this.a(_snowman, _snowman)) {
                     return;
                  }

                  ceh _snowmanxx = _snowman;

                  for (gc _snowmanxxx : gc.c.a) {
                     if (_snowman.nextBoolean() || !a(_snowman, _snowmanx.a(_snowmanxxx), gc.b)) {
                        _snowmanxx = _snowmanxx.a(a(_snowmanxxx), Boolean.valueOf(false));
                     }
                  }

                  if (this.n(_snowmanxx)) {
                     _snowman.a(_snowmanx, _snowmanxx, 2);
                  }

                  return;
               }
            }

            if (_snowman.v() > 0) {
               fx _snowmanxx = _snowman.c();
               ceh _snowmanxxxx = _snowman.d_(_snowmanxx);
               if (_snowmanxxxx.g() || _snowmanxxxx.a(this)) {
                  ceh _snowmanxxxxx = _snowmanxxxx.g() ? this.n() : _snowmanxxxx;
                  ceh _snowmanxxxxxx = this.a(_snowman, _snowmanxxxxx, _snowman);
                  if (_snowmanxxxxx != _snowmanxxxxxx && this.n(_snowmanxxxxxx)) {
                     _snowman.a(_snowmanxx, _snowmanxxxxxx, 2);
                  }
               }
            }
         }
      }
   }

   private ceh a(ceh var1, ceh var2, Random var3) {
      for (gc _snowman : gc.c.a) {
         if (_snowman.nextBoolean()) {
            cey _snowmanx = a(_snowman);
            if (_snowman.c(_snowmanx)) {
               _snowman = _snowman.a(_snowmanx, Boolean.valueOf(true));
            }
         }
      }

      return _snowman;
   }

   private boolean n(ceh var1) {
      return _snowman.c(b) || _snowman.c(c) || _snowman.c(d) || _snowman.c(e);
   }

   private boolean a(brc var1, fx var2) {
      int _snowman = 4;
      Iterable<fx> _snowmanx = fx.b(_snowman.u() - 4, _snowman.v() - 1, _snowman.w() - 4, _snowman.u() + 4, _snowman.v() + 1, _snowman.w() + 4);
      int _snowmanxx = 5;

      for (fx _snowmanxxx : _snowmanx) {
         if (_snowman.d_(_snowmanxxx).a(this)) {
            if (--_snowmanxx <= 0) {
               return false;
            }
         }
      }

      return true;
   }

   @Override
   public boolean a(ceh var1, bny var2) {
      ceh _snowman = _snowman.p().d_(_snowman.a());
      return _snowman.a(this) ? this.m(_snowman) < f.size() : super.a(_snowman, _snowman);
   }

   @Nullable
   @Override
   public ceh a(bny var1) {
      ceh _snowman = _snowman.p().d_(_snowman.a());
      boolean _snowmanx = _snowman.a(this);
      ceh _snowmanxx = _snowmanx ? _snowman : this.n();

      for (gc _snowmanxxx : _snowman.e()) {
         if (_snowmanxxx != gc.a) {
            cey _snowmanxxxx = a(_snowmanxxx);
            boolean _snowmanxxxxx = _snowmanx && _snowman.c(_snowmanxxxx);
            if (!_snowmanxxxxx && this.b(_snowman.p(), _snowman.a(), _snowmanxxx)) {
               return _snowmanxx.a(_snowmanxxxx, Boolean.valueOf(true));
            }
         }
      }

      return _snowmanx ? _snowmanxx : null;
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(a, b, c, d, e);
   }

   @Override
   public ceh a(ceh var1, bzm var2) {
      switch (_snowman) {
         case c:
            return _snowman.a(b, _snowman.c(d)).a(c, _snowman.c(e)).a(d, _snowman.c(b)).a(e, _snowman.c(c));
         case d:
            return _snowman.a(b, _snowman.c(c)).a(c, _snowman.c(d)).a(d, _snowman.c(e)).a(e, _snowman.c(b));
         case b:
            return _snowman.a(b, _snowman.c(e)).a(c, _snowman.c(b)).a(d, _snowman.c(c)).a(e, _snowman.c(d));
         default:
            return _snowman;
      }
   }

   @Override
   public ceh a(ceh var1, byg var2) {
      switch (_snowman) {
         case b:
            return _snowman.a(b, _snowman.c(d)).a(d, _snowman.c(b));
         case c:
            return _snowman.a(c, _snowman.c(e)).a(e, _snowman.c(c));
         default:
            return super.a(_snowman, _snowman);
      }
   }

   public static cey a(gc var0) {
      return f.get(_snowman);
   }
}
