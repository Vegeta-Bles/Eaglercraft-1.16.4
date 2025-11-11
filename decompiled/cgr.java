import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.EnumSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class cgr {
   private static final Logger b = LogManager.getLogger();
   public static final cgr a = new cgr();
   private static final gd[] c = gd.values();
   private final EnumSet<gd> d = EnumSet.noneOf(gd.class);
   private final int[][] e = new int[16][];
   private static final Map<buo, cgr.a> f = new IdentityHashMap<>();
   private static final Set<cgr.a> g = Sets.newHashSet();

   private cgr() {
   }

   public cgr(md var1) {
      this();
      if (_snowman.c("Indices", 10)) {
         md _snowman = _snowman.p("Indices");

         for (int _snowmanx = 0; _snowmanx < this.e.length; _snowmanx++) {
            String _snowmanxx = String.valueOf(_snowmanx);
            if (_snowman.c(_snowmanxx, 11)) {
               this.e[_snowmanx] = _snowman.n(_snowmanxx);
            }
         }
      }

      int _snowman = _snowman.h("Sides");

      for (gd _snowmanxx : gd.values()) {
         if ((_snowman & 1 << _snowmanxx.ordinal()) != 0) {
            this.d.add(_snowmanxx);
         }
      }
   }

   public void a(cgh var1) {
      this.b(_snowman);

      for (gd _snowman : c) {
         a(_snowman, _snowman);
      }

      brx _snowman = _snowman.x();
      g.forEach(var1x -> var1x.a(_snowman));
   }

   private static void a(cgh var0, gd var1) {
      brx _snowman = _snowman.x();
      if (_snowman.p().d.remove(_snowman)) {
         Set<gc> _snowmanx = _snowman.a();
         int _snowmanxx = 0;
         int _snowmanxxx = 15;
         boolean _snowmanxxxx = _snowmanx.contains(gc.f);
         boolean _snowmanxxxxx = _snowmanx.contains(gc.e);
         boolean _snowmanxxxxxx = _snowmanx.contains(gc.d);
         boolean _snowmanxxxxxxx = _snowmanx.contains(gc.c);
         boolean _snowmanxxxxxxxx = _snowmanx.size() == 1;
         brd _snowmanxxxxxxxxx = _snowman.g();
         int _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx.d() + (!_snowmanxxxxxxxx || !_snowmanxxxxxxx && !_snowmanxxxxxx ? (_snowmanxxxxx ? 0 : 15) : 1);
         int _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxx.d() + (!_snowmanxxxxxxxx || !_snowmanxxxxxxx && !_snowmanxxxxxx ? (_snowmanxxxxx ? 0 : 15) : 14);
         int _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxx.e() + (!_snowmanxxxxxxxx || !_snowmanxxxx && !_snowmanxxxxx ? (_snowmanxxxxxxx ? 0 : 15) : 1);
         int _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxx.e() + (!_snowmanxxxxxxxx || !_snowmanxxxx && !_snowmanxxxxx ? (_snowmanxxxxxxx ? 0 : 15) : 14);
         gc[] _snowmanxxxxxxxxxxxxxx = gc.values();
         fx.a _snowmanxxxxxxxxxxxxxxx = new fx.a();

         for (fx _snowmanxxxxxxxxxxxxxxxx : fx.b(_snowmanxxxxxxxxxx, 0, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowman.L() - 1, _snowmanxxxxxxxxxxxxx)) {
            ceh _snowmanxxxxxxxxxxxxxxxxx = _snowman.d_(_snowmanxxxxxxxxxxxxxxxx);
            ceh _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx;

            for (gc _snowmanxxxxxxxxxxxxxxxxxxx : _snowmanxxxxxxxxxxxxxx) {
               _snowmanxxxxxxxxxxxxxxx.a(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxx);
               _snowmanxxxxxxxxxxxxxxxxxx = a(_snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxx, _snowman, _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx);
            }

            buo.a(_snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx, _snowman, _snowmanxxxxxxxxxxxxxxxx, 18);
         }
      }
   }

   private static ceh a(ceh var0, gc var1, bry var2, fx var3, fx var4) {
      return f.getOrDefault(_snowman.b(), cgr.b.b).a(_snowman, _snowman, _snowman.d_(_snowman), _snowman, _snowman, _snowman);
   }

   private void b(cgh var1) {
      fx.a _snowman = new fx.a();
      fx.a _snowmanx = new fx.a();
      brd _snowmanxx = _snowman.g();
      bry _snowmanxxx = _snowman.x();

      for (int _snowmanxxxx = 0; _snowmanxxxx < 16; _snowmanxxxx++) {
         cgi _snowmanxxxxx = _snowman.d()[_snowmanxxxx];
         int[] _snowmanxxxxxx = this.e[_snowmanxxxx];
         this.e[_snowmanxxxx] = null;
         if (_snowmanxxxxx != null && _snowmanxxxxxx != null && _snowmanxxxxxx.length > 0) {
            gc[] _snowmanxxxxxxx = gc.values();
            cgo<ceh> _snowmanxxxxxxxx = _snowmanxxxxx.i();

            for (int _snowmanxxxxxxxxx : _snowmanxxxxxx) {
               int _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx & 15;
               int _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxx >> 8 & 15;
               int _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxx >> 4 & 15;
               _snowman.d(_snowmanxx.d() + _snowmanxxxxxxxxxx, (_snowmanxxxx << 4) + _snowmanxxxxxxxxxxx, _snowmanxx.e() + _snowmanxxxxxxxxxxxx);
               ceh _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxx.a(_snowmanxxxxxxxxx);
               ceh _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx;

               for (gc _snowmanxxxxxxxxxxxxxxx : _snowmanxxxxxxx) {
                  _snowmanx.a(_snowman, _snowmanxxxxxxxxxxxxxxx);
                  if (_snowman.u() >> 4 == _snowmanxx.b && _snowman.w() >> 4 == _snowmanxx.c) {
                     _snowmanxxxxxxxxxxxxxx = a(_snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, _snowmanxxx, _snowman, _snowmanx);
                  }
               }

               buo.a(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxx, _snowman, 18);
            }
         }
      }

      for (int _snowmanxxxxx = 0; _snowmanxxxxx < this.e.length; _snowmanxxxxx++) {
         if (this.e[_snowmanxxxxx] != null) {
            b.warn("Discarding update data for section {} for chunk ({} {})", _snowmanxxxxx, _snowmanxx.b, _snowmanxx.c);
         }

         this.e[_snowmanxxxxx] = null;
      }
   }

   public boolean a() {
      for (int[] _snowman : this.e) {
         if (_snowman != null) {
            return false;
         }
      }

      return this.d.isEmpty();
   }

   public md b() {
      md _snowman = new md();
      md _snowmanx = new md();

      for (int _snowmanxx = 0; _snowmanxx < this.e.length; _snowmanxx++) {
         String _snowmanxxx = String.valueOf(_snowmanxx);
         if (this.e[_snowmanxx] != null && this.e[_snowmanxx].length != 0) {
            _snowmanx.a(_snowmanxxx, this.e[_snowmanxx]);
         }
      }

      if (!_snowmanx.isEmpty()) {
         _snowman.a("Indices", _snowmanx);
      }

      int _snowmanxxx = 0;

      for (gd _snowmanxxxx : this.d) {
         _snowmanxxx |= 1 << _snowmanxxxx.ordinal();
      }

      _snowman.a("Sides", (byte)_snowmanxxx);
      return _snowman;
   }

   public interface a {
      ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6);

      default void a(bry var1) {
      }
   }

   static enum b implements cgr.a {
      a(
         bup.iO,
         bup.cT,
         bup.jM,
         bup.jN,
         bup.jO,
         bup.jP,
         bup.jQ,
         bup.jR,
         bup.jS,
         bup.jT,
         bup.jU,
         bup.jV,
         bup.jW,
         bup.jX,
         bup.jY,
         bup.jZ,
         bup.ka,
         bup.kb,
         bup.fo,
         bup.fp,
         bup.fq,
         bup.ef,
         bup.E,
         bup.C,
         bup.D,
         bup.bZ,
         bup.ca,
         bup.cb,
         bup.cc,
         bup.cd,
         bup.ce,
         bup.cj,
         bup.ck,
         bup.cl,
         bup.cm,
         bup.cn,
         bup.co
      ) {
         @Override
         public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
            return _snowman;
         }
      },
      b {
         @Override
         public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
            return _snowman.a(_snowman, _snowman.d_(_snowman), _snowman, _snowman, _snowman);
         }
      },
      c(bup.bR, bup.fr) {
         @Override
         public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
            if (_snowman.a(_snowman.b()) && _snowman.n().d() && _snowman.c(bve.c) == cez.a && _snowman.c(bve.c) == cez.a) {
               gc _snowman = _snowman.c(bve.b);
               if (_snowman.n() != _snowman.n() && _snowman == _snowman.c(bve.b)) {
                  cez _snowmanx = _snowman == _snowman.g() ? cez.b : cez.c;
                  _snowman.a(_snowman, _snowman.a(bve.c, _snowmanx.b()), 18);
                  if (_snowman == gc.c || _snowman == gc.f) {
                     ccj _snowmanxx = _snowman.c(_snowman);
                     ccj _snowmanxxx = _snowman.c(_snowman);
                     if (_snowmanxx instanceof ccn && _snowmanxxx instanceof ccn) {
                        ccn.a((ccn)_snowmanxx, (ccn)_snowmanxxx);
                     }
                  }

                  return _snowman.a(bve.c, _snowmanx);
               }
            }

            return _snowman;
         }
      },
      d(true, bup.al, bup.aj, bup.am, bup.ak, bup.ah, bup.ai) {
         private final ThreadLocal<List<ObjectSet<fx>>> g = ThreadLocal.withInitial(() -> Lists.newArrayListWithCapacity(7));

         @Override
         public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
            ceh _snowman = _snowman.a(_snowman, _snowman.d_(_snowman), _snowman, _snowman, _snowman);
            if (_snowman != _snowman) {
               int _snowmanx = _snowman.c(cex.an);
               List<ObjectSet<fx>> _snowmanxx = this.g.get();
               if (_snowmanxx.isEmpty()) {
                  for (int _snowmanxxx = 0; _snowmanxxx < 7; _snowmanxxx++) {
                     _snowmanxx.add(new ObjectOpenHashSet());
                  }
               }

               _snowmanxx.get(_snowmanx).add(_snowman.h());
            }

            return _snowman;
         }

         @Override
         public void a(bry var1) {
            fx.a _snowman = new fx.a();
            List<ObjectSet<fx>> _snowmanx = this.g.get();

            for (int _snowmanxx = 2; _snowmanxx < _snowmanx.size(); _snowmanxx++) {
               int _snowmanxxx = _snowmanxx - 1;
               ObjectSet<fx> _snowmanxxxx = _snowmanx.get(_snowmanxxx);
               ObjectSet<fx> _snowmanxxxxx = _snowmanx.get(_snowmanxx);
               ObjectIterator var8 = _snowmanxxxx.iterator();

               while (var8.hasNext()) {
                  fx _snowmanxxxxxx = (fx)var8.next();
                  ceh _snowmanxxxxxxx = _snowman.d_(_snowmanxxxxxx);
                  if (_snowmanxxxxxxx.c(cex.an) >= _snowmanxxx) {
                     _snowman.a(_snowmanxxxxxx, _snowmanxxxxxxx.a(cex.an, Integer.valueOf(_snowmanxxx)), 18);
                     if (_snowmanxx != 7) {
                        for (gc _snowmanxxxxxxxx : f) {
                           _snowman.a(_snowmanxxxxxx, _snowmanxxxxxxxx);
                           ceh _snowmanxxxxxxxxx = _snowman.d_(_snowman);
                           if (_snowmanxxxxxxxxx.b(cex.an) && _snowmanxxxxxxx.c(cex.an) > _snowmanxx) {
                              _snowmanxxxxx.add(_snowman.h());
                           }
                        }
                     }
                  }
               }
            }

            _snowmanx.clear();
         }
      },
      e(bup.dO, bup.dN) {
         @Override
         public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
            if (_snowman.c(cam.a) == 7) {
               can _snowman = ((cam)_snowman.b()).d();
               if (_snowman.a(_snowman)) {
                  return _snowman.d().n().a(bxm.aq, _snowman);
               }
            }

            return _snowman;
         }
      };

      public static final gc[] f = gc.values();

      private b(buo... var3) {
         this(false, _snowman);
      }

      private b(boolean var3, buo... var4) {
         for (buo _snowman : _snowman) {
            cgr.f.put(_snowman, this);
         }

         if (_snowman) {
            cgr.g.add(this);
         }
      }
   }
}
