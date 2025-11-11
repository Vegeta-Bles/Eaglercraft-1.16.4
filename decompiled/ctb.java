import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class ctb {
   private final List<ctb.a> a = Lists.newArrayList();
   private final List<ctb.d> b = Lists.newArrayList();
   private fx c = fx.b;
   private String d = "?";

   public ctb() {
   }

   public fx a() {
      return this.c;
   }

   public void a(String var1) {
      this.d = _snowman;
   }

   public String b() {
      return this.d;
   }

   public void a(brx var1, fx var2, fx var3, boolean var4, @Nullable buo var5) {
      if (_snowman.u() >= 1 && _snowman.v() >= 1 && _snowman.w() >= 1) {
         fx _snowman = _snowman.a(_snowman).b(-1, -1, -1);
         List<ctb.c> _snowmanx = Lists.newArrayList();
         List<ctb.c> _snowmanxx = Lists.newArrayList();
         List<ctb.c> _snowmanxxx = Lists.newArrayList();
         fx _snowmanxxxx = new fx(Math.min(_snowman.u(), _snowman.u()), Math.min(_snowman.v(), _snowman.v()), Math.min(_snowman.w(), _snowman.w()));
         fx _snowmanxxxxx = new fx(Math.max(_snowman.u(), _snowman.u()), Math.max(_snowman.v(), _snowman.v()), Math.max(_snowman.w(), _snowman.w()));
         this.c = _snowman;

         for (fx _snowmanxxxxxx : fx.a(_snowmanxxxx, _snowmanxxxxx)) {
            fx _snowmanxxxxxxx = _snowmanxxxxxx.b(_snowmanxxxx);
            ceh _snowmanxxxxxxxx = _snowman.d_(_snowmanxxxxxx);
            if (_snowman == null || _snowman != _snowmanxxxxxxxx.b()) {
               ccj _snowmanxxxxxxxxx = _snowman.c(_snowmanxxxxxx);
               ctb.c _snowmanxxxxxxxxxx;
               if (_snowmanxxxxxxxxx != null) {
                  md _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxx.a(new md());
                  _snowmanxxxxxxxxxxx.r("x");
                  _snowmanxxxxxxxxxxx.r("y");
                  _snowmanxxxxxxxxxxx.r("z");
                  _snowmanxxxxxxxxxx = new ctb.c(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxxxx.g());
               } else {
                  _snowmanxxxxxxxxxx = new ctb.c(_snowmanxxxxxxx, _snowmanxxxxxxxx, null);
               }

               a(_snowmanxxxxxxxxxx, _snowmanx, _snowmanxx, _snowmanxxx);
            }
         }

         List<ctb.c> _snowmanxxxxxxx = a(_snowmanx, _snowmanxx, _snowmanxxx);
         this.a.clear();
         this.a.add(new ctb.a(_snowmanxxxxxxx));
         if (_snowman) {
            this.a(_snowman, _snowmanxxxx, _snowmanxxxxx.b(1, 1, 1));
         } else {
            this.b.clear();
         }
      }
   }

   private static void a(ctb.c var0, List<ctb.c> var1, List<ctb.c> var2, List<ctb.c> var3) {
      if (_snowman.c != null) {
         _snowman.add(_snowman);
      } else if (!_snowman.b.b().o() && _snowman.b.r(brl.a, fx.b)) {
         _snowman.add(_snowman);
      } else {
         _snowman.add(_snowman);
      }
   }

   private static List<ctb.c> a(List<ctb.c> var0, List<ctb.c> var1, List<ctb.c> var2) {
      Comparator<ctb.c> _snowman = Comparator.<ctb.c>comparingInt(var0x -> var0x.a.v()).thenComparingInt(var0x -> var0x.a.u()).thenComparingInt(var0x -> var0x.a.w());
      _snowman.sort(_snowman);
      _snowman.sort(_snowman);
      _snowman.sort(_snowman);
      List<ctb.c> _snowmanx = Lists.newArrayList();
      _snowmanx.addAll(_snowman);
      _snowmanx.addAll(_snowman);
      _snowmanx.addAll(_snowman);
      return _snowmanx;
   }

   private void a(brx var1, fx var2, fx var3) {
      List<aqa> _snowman = _snowman.a(aqa.class, new dci(_snowman, _snowman), var0 -> !(var0 instanceof bfw));
      this.b.clear();

      for (aqa _snowmanx : _snowman) {
         dcn _snowmanxx = new dcn(_snowmanx.cD() - (double)_snowman.u(), _snowmanx.cE() - (double)_snowman.v(), _snowmanx.cH() - (double)_snowman.w());
         md _snowmanxxx = new md();
         _snowmanx.d(_snowmanxxx);
         fx _snowmanxxxx;
         if (_snowmanx instanceof bcs) {
            _snowmanxxxx = ((bcs)_snowmanx).n().b(_snowman);
         } else {
            _snowmanxxxx = new fx(_snowmanxx);
         }

         this.b.add(new ctb.d(_snowmanxx, _snowmanxxxx, _snowmanxxx.g()));
      }
   }

   public List<ctb.c> a(fx var1, csx var2, buo var3) {
      return this.a(_snowman, _snowman, _snowman, true);
   }

   public List<ctb.c> a(fx var1, csx var2, buo var3, boolean var4) {
      List<ctb.c> _snowman = Lists.newArrayList();
      cra _snowmanx = _snowman.h();
      if (this.a.isEmpty()) {
         return Collections.emptyList();
      } else {
         for (ctb.c _snowmanxx : _snowman.a(this.a, _snowman).a(_snowman)) {
            fx _snowmanxxx = _snowman ? a(_snowman, _snowmanxx.a).a(_snowman) : _snowmanxx.a;
            if (_snowmanx == null || _snowmanx.b(_snowmanxxx)) {
               _snowman.add(new ctb.c(_snowmanxxx, _snowmanxx.b.a(_snowman.d()), _snowmanxx.c));
            }
         }

         return _snowman;
      }
   }

   public fx a(csx var1, fx var2, csx var3, fx var4) {
      fx _snowman = a(_snowman, _snowman);
      fx _snowmanx = a(_snowman, _snowman);
      return _snowman.b(_snowmanx);
   }

   public static fx a(csx var0, fx var1) {
      return a(_snowman, _snowman.c(), _snowman.d(), _snowman.e());
   }

   public void a(bsk var1, fx var2, csx var3, Random var4) {
      _snowman.k();
      this.b(_snowman, _snowman, _snowman, _snowman);
   }

   public void b(bsk var1, fx var2, csx var3, Random var4) {
      this.a(_snowman, _snowman, _snowman, _snowman, _snowman, 2);
   }

   public boolean a(bsk var1, fx var2, fx var3, csx var4, Random var5, int var6) {
      if (this.a.isEmpty()) {
         return false;
      } else {
         List<ctb.c> _snowman = _snowman.a(this.a, _snowman).a();
         if ((!_snowman.isEmpty() || !_snowman.g() && !this.b.isEmpty()) && this.c.u() >= 1 && this.c.v() >= 1 && this.c.w() >= 1) {
            cra _snowmanx = _snowman.h();
            List<fx> _snowmanxx = Lists.newArrayListWithCapacity(_snowman.l() ? _snowman.size() : 0);
            List<Pair<fx, md>> _snowmanxxx = Lists.newArrayListWithCapacity(_snowman.size());
            int _snowmanxxxx = Integer.MAX_VALUE;
            int _snowmanxxxxx = Integer.MAX_VALUE;
            int _snowmanxxxxxx = Integer.MAX_VALUE;
            int _snowmanxxxxxxx = Integer.MIN_VALUE;
            int _snowmanxxxxxxxx = Integer.MIN_VALUE;
            int _snowmanxxxxxxxxx = Integer.MIN_VALUE;

            for (ctb.c _snowmanxxxxxxxxxx : a(_snowman, _snowman, _snowman, _snowman, _snowman)) {
               fx _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxx.a;
               if (_snowmanx == null || _snowmanx.b(_snowmanxxxxxxxxxxx)) {
                  cux _snowmanxxxxxxxxxxxx = _snowman.l() ? _snowman.b(_snowmanxxxxxxxxxxx) : null;
                  ceh _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxx.b.a(_snowman.c()).a(_snowman.d());
                  if (_snowmanxxxxxxxxxx.c != null) {
                     ccj _snowmanxxxxxxxxxxxxxx = _snowman.c(_snowmanxxxxxxxxxxx);
                     aol.a(_snowmanxxxxxxxxxxxxxx);
                     _snowman.a(_snowmanxxxxxxxxxxx, bup.go.n(), 20);
                  }

                  if (_snowman.a(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, _snowman)) {
                     _snowmanxxxx = Math.min(_snowmanxxxx, _snowmanxxxxxxxxxxx.u());
                     _snowmanxxxxx = Math.min(_snowmanxxxxx, _snowmanxxxxxxxxxxx.v());
                     _snowmanxxxxxx = Math.min(_snowmanxxxxxx, _snowmanxxxxxxxxxxx.w());
                     _snowmanxxxxxxx = Math.max(_snowmanxxxxxxx, _snowmanxxxxxxxxxxx.u());
                     _snowmanxxxxxxxx = Math.max(_snowmanxxxxxxxx, _snowmanxxxxxxxxxxx.v());
                     _snowmanxxxxxxxxx = Math.max(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxxx.w());
                     _snowmanxxx.add(Pair.of(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxx.c));
                     if (_snowmanxxxxxxxxxx.c != null) {
                        ccj _snowmanxxxxxxxxxxxxxx = _snowman.c(_snowmanxxxxxxxxxxx);
                        if (_snowmanxxxxxxxxxxxxxx != null) {
                           _snowmanxxxxxxxxxx.c.b("x", _snowmanxxxxxxxxxxx.u());
                           _snowmanxxxxxxxxxx.c.b("y", _snowmanxxxxxxxxxxx.v());
                           _snowmanxxxxxxxxxx.c.b("z", _snowmanxxxxxxxxxxx.w());
                           if (_snowmanxxxxxxxxxxxxxx instanceof cdd) {
                              _snowmanxxxxxxxxxx.c.a("LootTableSeed", _snowman.nextLong());
                           }

                           _snowmanxxxxxxxxxxxxxx.a(_snowmanxxxxxxxxxx.b, _snowmanxxxxxxxxxx.c);
                           _snowmanxxxxxxxxxxxxxx.a(_snowman.c());
                           _snowmanxxxxxxxxxxxxxx.a(_snowman.d());
                        }
                     }

                     if (_snowmanxxxxxxxxxxxx != null && _snowmanxxxxxxxxxxxxx.b() instanceof byc) {
                        ((byc)_snowmanxxxxxxxxxxxxx.b()).a(_snowman, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxx);
                        if (!_snowmanxxxxxxxxxxxx.b()) {
                           _snowmanxx.add(_snowmanxxxxxxxxxxx);
                        }
                     }
                  }
               }
            }

            boolean _snowmanxxxxxxxxxxx = true;
            gc[] _snowmanxxxxxxxxxxxxxx = new gc[]{gc.b, gc.c, gc.f, gc.d, gc.e};

            while (_snowmanxxxxxxxxxxx && !_snowmanxx.isEmpty()) {
               _snowmanxxxxxxxxxxx = false;
               Iterator<fx> _snowmanxxxxxxxxxxxxxxx = _snowmanxx.iterator();

               while (_snowmanxxxxxxxxxxxxxxx.hasNext()) {
                  fx _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx.next();
                  fx _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx;
                  cux _snowmanxxxxxxxxxxxxxxxxxx = _snowman.b(_snowmanxxxxxxxxxxxxxxxx);

                  for (int _snowmanxxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxx.length && !_snowmanxxxxxxxxxxxxxxxxxx.b(); _snowmanxxxxxxxxxxxxxxxxxxx++) {
                     fx _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx.a(_snowmanxxxxxxxxxxxxxx[_snowmanxxxxxxxxxxxxxxxxxxx]);
                     cux _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowman.b(_snowmanxxxxxxxxxxxxxxxxxxxx);
                     if (_snowmanxxxxxxxxxxxxxxxxxxxxx.a(_snowman, _snowmanxxxxxxxxxxxxxxxxxxxx) > _snowmanxxxxxxxxxxxxxxxxxx.a(_snowman, _snowmanxxxxxxxxxxxxxxxxx)
                        || _snowmanxxxxxxxxxxxxxxxxxxxxx.b() && !_snowmanxxxxxxxxxxxxxxxxxx.b()) {
                        _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxx;
                        _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxx;
                     }
                  }

                  if (_snowmanxxxxxxxxxxxxxxxxxx.b()) {
                     ceh _snowmanxxxxxxxxxxxxxxxxxxxx = _snowman.d_(_snowmanxxxxxxxxxxxxxxxx);
                     buo _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxx.b();
                     if (_snowmanxxxxxxxxxxxxxxxxxxxxx instanceof byc) {
                        ((byc)_snowmanxxxxxxxxxxxxxxxxxxxxx).a(_snowman, _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx);
                        _snowmanxxxxxxxxxxx = true;
                        _snowmanxxxxxxxxxxxxxxx.remove();
                     }
                  }
               }
            }

            if (_snowmanxxxx <= _snowmanxxxxxxx) {
               if (!_snowman.i()) {
                  dcw _snowmanxxxxxxxxxxxxxxx = new dcq(_snowmanxxxxxxx - _snowmanxxxx + 1, _snowmanxxxxxxxx - _snowmanxxxxx + 1, _snowmanxxxxxxxxx - _snowmanxxxxxx + 1);
                  int _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxx;
                  int _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxx;
                  int _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxx;

                  for (Pair<fx, md> _snowmanxxxxxxxxxxxxxxxxxxxx : _snowmanxxx) {
                     fx _snowmanxxxxxxxxxxxxxxxxxxxxx = (fx)_snowmanxxxxxxxxxxxxxxxxxxxx.getFirst();
                     _snowmanxxxxxxxxxxxxxxx.a(
                        _snowmanxxxxxxxxxxxxxxxxxxxxx.u() - _snowmanxxxxxxxxxxxxxxxx,
                        _snowmanxxxxxxxxxxxxxxxxxxxxx.v() - _snowmanxxxxxxxxxxxxxxxxx,
                        _snowmanxxxxxxxxxxxxxxxxxxxxx.w() - _snowmanxxxxxxxxxxxxxxxxxx,
                        true,
                        true
                     );
                  }

                  a(_snowman, _snowman, _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx);
               }

               for (Pair<fx, md> _snowmanxxxxxxxxxxxxxxx : _snowmanxxx) {
                  fx _snowmanxxxxxxxxxxxxxxxx = (fx)_snowmanxxxxxxxxxxxxxxx.getFirst();
                  if (!_snowman.i()) {
                     ceh _snowmanxxxxxxxxxxxxxxxxx = _snowman.d_(_snowmanxxxxxxxxxxxxxxxx);
                     ceh _snowmanxxxxxxxxxxxxxxxxxx = buo.b(_snowmanxxxxxxxxxxxxxxxxx, (bry)_snowman, _snowmanxxxxxxxxxxxxxxxx);
                     if (_snowmanxxxxxxxxxxxxxxxxx != _snowmanxxxxxxxxxxxxxxxxxx) {
                        _snowman.a(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx, _snowman & -2 | 16);
                     }

                     _snowman.a(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx.b());
                  }

                  if (_snowmanxxxxxxxxxxxxxxx.getSecond() != null) {
                     ccj _snowmanxxxxxxxxxxxxxxxxx = _snowman.c(_snowmanxxxxxxxxxxxxxxxx);
                     if (_snowmanxxxxxxxxxxxxxxxxx != null) {
                        _snowmanxxxxxxxxxxxxxxxxx.X_();
                     }
                  }
               }
            }

            if (!_snowman.g()) {
               this.a(_snowman, _snowman, _snowman.c(), _snowman.d(), _snowman.e(), _snowmanx, _snowman.m());
            }

            return true;
         } else {
            return false;
         }
      }
   }

   public static void a(bry var0, int var1, dcw var2, int var3, int var4, int var5) {
      _snowman.a((var5x, var6, var7, var8) -> {
         fx _snowman = new fx(_snowman + var6, _snowman + var7, _snowman + var8);
         fx _snowmanx = _snowman.a(var5x);
         ceh _snowmanxx = _snowman.d_(_snowman);
         ceh _snowmanxxx = _snowman.d_(_snowmanx);
         ceh _snowmanxxxx = _snowmanxx.a(var5x, _snowmanxxx, _snowman, _snowman, _snowmanx);
         if (_snowmanxx != _snowmanxxxx) {
            _snowman.a(_snowman, _snowmanxxxx, _snowman & -2);
         }

         ceh _snowmanxxxxx = _snowmanxxx.a(var5x.f(), _snowmanxxxx, _snowman, _snowmanx, _snowman);
         if (_snowmanxxx != _snowmanxxxxx) {
            _snowman.a(_snowmanx, _snowmanxxxxx, _snowman & -2);
         }
      });
   }

   public static List<ctb.c> a(bry var0, fx var1, fx var2, csx var3, List<ctb.c> var4) {
      List<ctb.c> _snowman = Lists.newArrayList();

      for (ctb.c _snowmanx : _snowman) {
         fx _snowmanxx = a(_snowman, _snowmanx.a).a(_snowman);
         ctb.c _snowmanxxx = new ctb.c(_snowmanxx, _snowmanx.b, _snowmanx.c != null ? _snowmanx.c.g() : null);
         Iterator<csy> _snowmanxxxx = _snowman.j().iterator();

         while (_snowmanxxx != null && _snowmanxxxx.hasNext()) {
            _snowmanxxx = _snowmanxxxx.next().a(_snowman, _snowman, _snowman, _snowmanx, _snowmanxxx, _snowman);
         }

         if (_snowmanxxx != null) {
            _snowman.add(_snowmanxxx);
         }
      }

      return _snowman;
   }

   private void a(bsk var1, fx var2, byg var3, bzm var4, fx var5, @Nullable cra var6, boolean var7) {
      for (ctb.d _snowman : this.b) {
         fx _snowmanx = a(_snowman.b, _snowman, _snowman, _snowman).a(_snowman);
         if (_snowman == null || _snowman.b(_snowmanx)) {
            md _snowmanxx = _snowman.c.g();
            dcn _snowmanxxx = a(_snowman.a, _snowman, _snowman, _snowman);
            dcn _snowmanxxxx = _snowmanxxx.b((double)_snowman.u(), (double)_snowman.v(), (double)_snowman.w());
            mj _snowmanxxxxx = new mj();
            _snowmanxxxxx.add(me.a(_snowmanxxxx.b));
            _snowmanxxxxx.add(me.a(_snowmanxxxx.c));
            _snowmanxxxxx.add(me.a(_snowmanxxxx.d));
            _snowmanxx.a("Pos", _snowmanxxxxx);
            _snowmanxx.r("UUID");
            a(_snowman, _snowmanxx).ifPresent(var6x -> {
               float _snowmanxxxxxx = var6x.a(_snowman);
               _snowmanxxxxxx += var6x.p - var6x.a(_snowman);
               var6x.b(_snowman.b, _snowman.c, _snowman.d, _snowmanxxxxxx, var6x.q);
               if (_snowman && var6x instanceof aqn) {
                  ((aqn)var6x).a(_snowman, _snowman.d(new fx(_snowman)), aqp.d, null, _snowman);
               }

               _snowman.l(var6x);
            });
         }
      }
   }

   private static Optional<aqa> a(bsk var0, md var1) {
      try {
         return aqe.a(_snowman, _snowman.E());
      } catch (Exception var3) {
         return Optional.empty();
      }
   }

   public fx a(bzm var1) {
      switch (_snowman) {
         case d:
         case b:
            return new fx(this.c.w(), this.c.v(), this.c.u());
         default:
            return this.c;
      }
   }

   public static fx a(fx var0, byg var1, bzm var2, fx var3) {
      int _snowman = _snowman.u();
      int _snowmanx = _snowman.v();
      int _snowmanxx = _snowman.w();
      boolean _snowmanxxx = true;
      switch (_snowman) {
         case b:
            _snowmanxx = -_snowmanxx;
            break;
         case c:
            _snowman = -_snowman;
            break;
         default:
            _snowmanxxx = false;
      }

      int _snowman = _snowman.u();
      int _snowmanx = _snowman.w();
      switch (_snowman) {
         case d:
            return new fx(_snowman - _snowmanx + _snowmanxx, _snowmanx, _snowman + _snowmanx - _snowman);
         case b:
            return new fx(_snowman + _snowmanx - _snowmanxx, _snowmanx, _snowmanx - _snowman + _snowman);
         case c:
            return new fx(_snowman + _snowman - _snowman, _snowmanx, _snowmanx + _snowmanx - _snowmanxx);
         default:
            return _snowmanxxx ? new fx(_snowman, _snowmanx, _snowmanxx) : _snowman;
      }
   }

   public static dcn a(dcn var0, byg var1, bzm var2, fx var3) {
      double _snowman = _snowman.b;
      double _snowmanx = _snowman.c;
      double _snowmanxx = _snowman.d;
      boolean _snowmanxxx = true;
      switch (_snowman) {
         case b:
            _snowmanxx = 1.0 - _snowmanxx;
            break;
         case c:
            _snowman = 1.0 - _snowman;
            break;
         default:
            _snowmanxxx = false;
      }

      int _snowman = _snowman.u();
      int _snowmanx = _snowman.w();
      switch (_snowman) {
         case d:
            return new dcn((double)(_snowman - _snowmanx) + _snowmanxx, _snowmanx, (double)(_snowman + _snowmanx + 1) - _snowman);
         case b:
            return new dcn((double)(_snowman + _snowmanx + 1) - _snowmanxx, _snowmanx, (double)(_snowmanx - _snowman) + _snowman);
         case c:
            return new dcn((double)(_snowman + _snowman + 1) - _snowman, _snowmanx, (double)(_snowmanx + _snowmanx + 1) - _snowmanxx);
         default:
            return _snowmanxxx ? new dcn(_snowman, _snowmanx, _snowmanxx) : _snowman;
      }
   }

   public fx a(fx var1, byg var2, bzm var3) {
      return a(_snowman, _snowman, _snowman, this.a().u(), this.a().w());
   }

   public static fx a(fx var0, byg var1, bzm var2, int var3, int var4) {
      _snowman--;
      _snowman--;
      int _snowman = _snowman == byg.c ? _snowman : 0;
      int _snowmanx = _snowman == byg.b ? _snowman : 0;
      fx _snowmanxx = _snowman;
      switch (_snowman) {
         case d:
            _snowmanxx = _snowman.b(_snowmanx, 0, _snowman - _snowman);
            break;
         case b:
            _snowmanxx = _snowman.b(_snowman - _snowmanx, 0, _snowman);
            break;
         case c:
            _snowmanxx = _snowman.b(_snowman - _snowman, 0, _snowman - _snowmanx);
            break;
         case a:
            _snowmanxx = _snowman.b(_snowman, 0, _snowmanx);
      }

      return _snowmanxx;
   }

   public cra b(csx var1, fx var2) {
      return this.a(_snowman, _snowman.d(), _snowman.e(), _snowman.c());
   }

   public cra a(fx var1, bzm var2, fx var3, byg var4) {
      fx _snowman = this.a(_snowman);
      int _snowmanx = _snowman.u();
      int _snowmanxx = _snowman.w();
      int _snowmanxxx = _snowman.u() - 1;
      int _snowmanxxxx = _snowman.v() - 1;
      int _snowmanxxxxx = _snowman.w() - 1;
      cra _snowmanxxxxxx = new cra(0, 0, 0, 0, 0, 0);
      switch (_snowman) {
         case d:
            _snowmanxxxxxx = new cra(_snowmanx - _snowmanxx, 0, _snowmanx + _snowmanxx - _snowmanxxxxx, _snowmanx - _snowmanxx + _snowmanxxx, _snowmanxxxx, _snowmanx + _snowmanxx);
            break;
         case b:
            _snowmanxxxxxx = new cra(_snowmanx + _snowmanxx - _snowmanxxx, 0, _snowmanxx - _snowmanx, _snowmanx + _snowmanxx, _snowmanxxxx, _snowmanxx - _snowmanx + _snowmanxxxxx);
            break;
         case c:
            _snowmanxxxxxx = new cra(_snowmanx + _snowmanx - _snowmanxxx, 0, _snowmanxx + _snowmanxx - _snowmanxxxxx, _snowmanx + _snowmanx, _snowmanxxxx, _snowmanxx + _snowmanxx);
            break;
         case a:
            _snowmanxxxxxx = new cra(0, 0, 0, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx);
      }

      switch (_snowman) {
         case b:
            this.a(_snowman, _snowmanxxxxx, _snowmanxxx, _snowmanxxxxxx, gc.c, gc.d);
            break;
         case c:
            this.a(_snowman, _snowmanxxx, _snowmanxxxxx, _snowmanxxxxxx, gc.e, gc.f);
         case a:
      }

      _snowmanxxxxxx.a(_snowman.u(), _snowman.v(), _snowman.w());
      return _snowmanxxxxxx;
   }

   private void a(bzm var1, int var2, int var3, cra var4, gc var5, gc var6) {
      fx _snowman = fx.b;
      if (_snowman == bzm.b || _snowman == bzm.d) {
         _snowman = _snowman.a(_snowman.a(_snowman), _snowman);
      } else if (_snowman == bzm.c) {
         _snowman = _snowman.a(_snowman, _snowman);
      } else {
         _snowman = _snowman.a(_snowman, _snowman);
      }

      _snowman.a(_snowman.u(), 0, _snowman.w());
   }

   public md a(md var1) {
      if (this.a.isEmpty()) {
         _snowman.a("blocks", new mj());
         _snowman.a("palette", new mj());
      } else {
         List<ctb.b> _snowman = Lists.newArrayList();
         ctb.b _snowmanx = new ctb.b();
         _snowman.add(_snowmanx);

         for (int _snowmanxx = 1; _snowmanxx < this.a.size(); _snowmanxx++) {
            _snowman.add(new ctb.b());
         }

         mj _snowmanxx = new mj();
         List<ctb.c> _snowmanxxx = this.a.get(0).a();

         for (int _snowmanxxxx = 0; _snowmanxxxx < _snowmanxxx.size(); _snowmanxxxx++) {
            ctb.c _snowmanxxxxx = _snowmanxxx.get(_snowmanxxxx);
            md _snowmanxxxxxx = new md();
            _snowmanxxxxxx.a("pos", this.a(_snowmanxxxxx.a.u(), _snowmanxxxxx.a.v(), _snowmanxxxxx.a.w()));
            int _snowmanxxxxxxx = _snowmanx.a(_snowmanxxxxx.b);
            _snowmanxxxxxx.b("state", _snowmanxxxxxxx);
            if (_snowmanxxxxx.c != null) {
               _snowmanxxxxxx.a("nbt", _snowmanxxxxx.c);
            }

            _snowmanxx.add(_snowmanxxxxxx);

            for (int _snowmanxxxxxxxx = 1; _snowmanxxxxxxxx < this.a.size(); _snowmanxxxxxxxx++) {
               ctb.b _snowmanxxxxxxxxx = _snowman.get(_snowmanxxxxxxxx);
               _snowmanxxxxxxxxx.a(this.a.get(_snowmanxxxxxxxx).a().get(_snowmanxxxx).b, _snowmanxxxxxxx);
            }
         }

         _snowman.a("blocks", _snowmanxx);
         if (_snowman.size() == 1) {
            mj _snowmanxxxx = new mj();

            for (ceh _snowmanxxxxx : _snowmanx) {
               _snowmanxxxx.add(mp.a(_snowmanxxxxx));
            }

            _snowman.a("palette", _snowmanxxxx);
         } else {
            mj _snowmanxxxx = new mj();

            for (ctb.b _snowmanxxxxx : _snowman) {
               mj _snowmanxxxxxx = new mj();

               for (ceh _snowmanxxxxxxx : _snowmanxxxxx) {
                  _snowmanxxxxxx.add(mp.a(_snowmanxxxxxxx));
               }

               _snowmanxxxx.add(_snowmanxxxxxx);
            }

            _snowman.a("palettes", _snowmanxxxx);
         }
      }

      mj _snowman = new mj();

      for (ctb.d _snowmanx : this.b) {
         md _snowmanxx = new md();
         _snowmanxx.a("pos", this.a(_snowmanx.a.b, _snowmanx.a.c, _snowmanx.a.d));
         _snowmanxx.a("blockPos", this.a(_snowmanx.b.u(), _snowmanx.b.v(), _snowmanx.b.w()));
         if (_snowmanx.c != null) {
            _snowmanxx.a("nbt", _snowmanx.c);
         }

         _snowman.add(_snowmanxx);
      }

      _snowman.a("entities", _snowman);
      _snowman.a("size", this.a(this.c.u(), this.c.v(), this.c.w()));
      _snowman.b("DataVersion", w.a().getWorldVersion());
      return _snowman;
   }

   public void b(md var1) {
      this.a.clear();
      this.b.clear();
      mj _snowman = _snowman.d("size", 3);
      this.c = new fx(_snowman.e(0), _snowman.e(1), _snowman.e(2));
      mj _snowmanx = _snowman.d("blocks", 10);
      if (_snowman.c("palettes", 9)) {
         mj _snowmanxx = _snowman.d("palettes", 9);

         for (int _snowmanxxx = 0; _snowmanxxx < _snowmanxx.size(); _snowmanxxx++) {
            this.a(_snowmanxx.b(_snowmanxxx), _snowmanx);
         }
      } else {
         this.a(_snowman.d("palette", 10), _snowmanx);
      }

      mj _snowmanxx = _snowman.d("entities", 10);

      for (int _snowmanxxx = 0; _snowmanxxx < _snowmanxx.size(); _snowmanxxx++) {
         md _snowmanxxxx = _snowmanxx.a(_snowmanxxx);
         mj _snowmanxxxxx = _snowmanxxxx.d("pos", 6);
         dcn _snowmanxxxxxx = new dcn(_snowmanxxxxx.h(0), _snowmanxxxxx.h(1), _snowmanxxxxx.h(2));
         mj _snowmanxxxxxxx = _snowmanxxxx.d("blockPos", 3);
         fx _snowmanxxxxxxxx = new fx(_snowmanxxxxxxx.e(0), _snowmanxxxxxxx.e(1), _snowmanxxxxxxx.e(2));
         if (_snowmanxxxx.e("nbt")) {
            md _snowmanxxxxxxxxx = _snowmanxxxx.p("nbt");
            this.b.add(new ctb.d(_snowmanxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx));
         }
      }
   }

   private void a(mj var1, mj var2) {
      ctb.b _snowman = new ctb.b();

      for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
         _snowman.a(mp.c(_snowman.a(_snowmanx)), _snowmanx);
      }

      List<ctb.c> _snowmanx = Lists.newArrayList();
      List<ctb.c> _snowmanxx = Lists.newArrayList();
      List<ctb.c> _snowmanxxx = Lists.newArrayList();

      for (int _snowmanxxxx = 0; _snowmanxxxx < _snowman.size(); _snowmanxxxx++) {
         md _snowmanxxxxx = _snowman.a(_snowmanxxxx);
         mj _snowmanxxxxxx = _snowmanxxxxx.d("pos", 3);
         fx _snowmanxxxxxxx = new fx(_snowmanxxxxxx.e(0), _snowmanxxxxxx.e(1), _snowmanxxxxxx.e(2));
         ceh _snowmanxxxxxxxx = _snowman.a(_snowmanxxxxx.h("state"));
         md _snowmanxxxxxxxxx;
         if (_snowmanxxxxx.e("nbt")) {
            _snowmanxxxxxxxxx = _snowmanxxxxx.p("nbt");
         } else {
            _snowmanxxxxxxxxx = null;
         }

         ctb.c _snowmanxxxxxxxxxx = new ctb.c(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx);
         a(_snowmanxxxxxxxxxx, _snowmanx, _snowmanxx, _snowmanxxx);
      }

      List<ctb.c> _snowmanxxxx = a(_snowmanx, _snowmanxx, _snowmanxxx);
      this.a.add(new ctb.a(_snowmanxxxx));
   }

   private mj a(int... var1) {
      mj _snowman = new mj();

      for (int _snowmanx : _snowman) {
         _snowman.add(mi.a(_snowmanx));
      }

      return _snowman;
   }

   private mj a(double... var1) {
      mj _snowman = new mj();

      for (double _snowmanx : _snowman) {
         _snowman.add(me.a(_snowmanx));
      }

      return _snowman;
   }

   public static final class a {
      private final List<ctb.c> a;
      private final Map<buo, List<ctb.c>> b = Maps.newHashMap();

      private a(List<ctb.c> var1) {
         this.a = _snowman;
      }

      public List<ctb.c> a() {
         return this.a;
      }

      public List<ctb.c> a(buo var1) {
         return this.b.computeIfAbsent(_snowman, var1x -> this.a.stream().filter(var1xx -> var1xx.b.a(var1x)).collect(Collectors.toList()));
      }
   }

   static class b implements Iterable<ceh> {
      public static final ceh a = bup.a.n();
      private final gh<ceh> b = new gh<>(16);
      private int c;

      private b() {
      }

      public int a(ceh var1) {
         int _snowman = this.b.a(_snowman);
         if (_snowman == -1) {
            _snowman = this.c++;
            this.b.a(_snowman, _snowman);
         }

         return _snowman;
      }

      @Nullable
      public ceh a(int var1) {
         ceh _snowman = this.b.a(_snowman);
         return _snowman == null ? a : _snowman;
      }

      @Override
      public Iterator<ceh> iterator() {
         return this.b.iterator();
      }

      public void a(ceh var1, int var2) {
         this.b.a(_snowman, _snowman);
      }
   }

   public static class c {
      public final fx a;
      public final ceh b;
      public final md c;

      public c(fx var1, ceh var2, @Nullable md var3) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
      }

      @Override
      public String toString() {
         return String.format("<StructureBlockInfo | %s | %s | %s>", this.a, this.b, this.c);
      }
   }

   public static class d {
      public final dcn a;
      public final fx b;
      public final md c;

      public d(dcn var1, fx var2, md var3) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
      }
   }
}
