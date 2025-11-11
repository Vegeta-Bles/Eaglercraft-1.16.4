import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;

public abstract class bic {
   private final gj<bmb> c = gj.a();
   public final List<bjr> a = Lists.newArrayList();
   private final List<biq> d = Lists.newArrayList();
   @Nullable
   private final bje<?> e;
   public final int b;
   private short f;
   private int g = -1;
   private int h;
   private final Set<bjr> i = Sets.newHashSet();
   private final List<bin> j = Lists.newArrayList();
   private final Set<bfw> k = Sets.newHashSet();

   protected bic(@Nullable bje<?> var1, int var2) {
      this.e = _snowman;
      this.b = _snowman;
   }

   protected static boolean a(bim var0, bfw var1, buo var2) {
      return _snowman.a((var2x, var3) -> !var2x.d_(var3).a(_snowman) ? false : _snowman.h((double)var3.u() + 0.5, (double)var3.v() + 0.5, (double)var3.w() + 0.5) <= 64.0, true);
   }

   public bje<?> a() {
      if (this.e == null) {
         throw new UnsupportedOperationException("Unable to construct this menu by type");
      } else {
         return this.e;
      }
   }

   protected static void a(aon var0, int var1) {
      int _snowman = _snowman.Z_();
      if (_snowman < _snowman) {
         throw new IllegalArgumentException("Container size " + _snowman + " is smaller than expected " + _snowman);
      }
   }

   protected static void a(bil var0, int var1) {
      int _snowman = _snowman.a();
      if (_snowman < _snowman) {
         throw new IllegalArgumentException("Container data count " + _snowman + " is smaller than expected " + _snowman);
      }
   }

   protected bjr a(bjr var1) {
      _snowman.d = this.a.size();
      this.a.add(_snowman);
      this.c.add(bmb.b);
      return _snowman;
   }

   protected biq a(biq var1) {
      this.d.add(_snowman);
      return _snowman;
   }

   protected void a(bil var1) {
      for (int _snowman = 0; _snowman < _snowman.a(); _snowman++) {
         this.a(biq.a(_snowman, _snowman));
      }
   }

   public void a(bin var1) {
      if (!this.j.contains(_snowman)) {
         this.j.add(_snowman);
         _snowman.a(this, this.b());
         this.c();
      }
   }

   public void b(bin var1) {
      this.j.remove(_snowman);
   }

   public gj<bmb> b() {
      gj<bmb> _snowman = gj.a();

      for (int _snowmanx = 0; _snowmanx < this.a.size(); _snowmanx++) {
         _snowman.add(this.a.get(_snowmanx).e());
      }

      return _snowman;
   }

   public void c() {
      for (int _snowman = 0; _snowman < this.a.size(); _snowman++) {
         bmb _snowmanx = this.a.get(_snowman).e();
         bmb _snowmanxx = this.c.get(_snowman);
         if (!bmb.b(_snowmanxx, _snowmanx)) {
            bmb _snowmanxxx = _snowmanx.i();
            this.c.set(_snowman, _snowmanxxx);

            for (bin _snowmanxxxx : this.j) {
               _snowmanxxxx.a(this, _snowman, _snowmanxxx);
            }
         }
      }

      for (int _snowmanx = 0; _snowmanx < this.d.size(); _snowmanx++) {
         biq _snowmanxx = this.d.get(_snowmanx);
         if (_snowmanxx.c()) {
            for (bin _snowmanxxx : this.j) {
               _snowmanxxx.a(this, _snowmanx, _snowmanxx.b());
            }
         }
      }
   }

   public boolean a(bfw var1, int var2) {
      return false;
   }

   public bjr a(int var1) {
      return this.a.get(_snowman);
   }

   public bmb b(bfw var1, int var2) {
      bjr _snowman = this.a.get(_snowman);
      return _snowman != null ? _snowman.e() : bmb.b;
   }

   public bmb a(int var1, int var2, bik var3, bfw var4) {
      try {
         return this.b(_snowman, _snowman, _snowman, _snowman);
      } catch (Exception var8) {
         l _snowman = l.a(var8, "Container click");
         m _snowmanx = _snowman.a("Click info");
         _snowmanx.a("Menu Type", () -> this.e != null ? gm.ac.b(this.e).toString() : "<no type>");
         _snowmanx.a("Menu Class", () -> this.getClass().getCanonicalName());
         _snowmanx.a("Slot Count", this.a.size());
         _snowmanx.a("Slot", _snowman);
         _snowmanx.a("Button", _snowman);
         _snowmanx.a("Type", _snowman);
         throw new u(_snowman);
      }
   }

   private bmb b(int var1, int var2, bik var3, bfw var4) {
      bmb _snowman = bmb.b;
      bfv _snowmanx = _snowman.bm;
      if (_snowman == bik.f) {
         int _snowmanxx = this.h;
         this.h = c(_snowman);
         if ((_snowmanxx != 1 || this.h != 2) && _snowmanxx != this.h) {
            this.d();
         } else if (_snowmanx.m().a()) {
            this.d();
         } else if (this.h == 0) {
            this.g = b(_snowman);
            if (a(this.g, _snowman)) {
               this.h = 1;
               this.i.clear();
            } else {
               this.d();
            }
         } else if (this.h == 1) {
            bjr _snowmanxxx = this.a.get(_snowman);
            bmb _snowmanxxxx = _snowmanx.m();
            if (_snowmanxxx != null && a(_snowmanxxx, _snowmanxxxx, true) && _snowmanxxx.a(_snowmanxxxx) && (this.g == 2 || _snowmanxxxx.E() > this.i.size()) && this.b(_snowmanxxx)) {
               this.i.add(_snowmanxxx);
            }
         } else if (this.h == 2) {
            if (!this.i.isEmpty()) {
               bmb _snowmanxxx = _snowmanx.m().i();
               int _snowmanxxxx = _snowmanx.m().E();

               for (bjr _snowmanxxxxx : this.i) {
                  bmb _snowmanxxxxxx = _snowmanx.m();
                  if (_snowmanxxxxx != null && a(_snowmanxxxxx, _snowmanxxxxxx, true) && _snowmanxxxxx.a(_snowmanxxxxxx) && (this.g == 2 || _snowmanxxxxxx.E() >= this.i.size()) && this.b(_snowmanxxxxx)) {
                     bmb _snowmanxxxxxxx = _snowmanxxx.i();
                     int _snowmanxxxxxxxx = _snowmanxxxxx.f() ? _snowmanxxxxx.e().E() : 0;
                     a(this.i, this.g, _snowmanxxxxxxx, _snowmanxxxxxxxx);
                     int _snowmanxxxxxxxxx = Math.min(_snowmanxxxxxxx.c(), _snowmanxxxxx.b(_snowmanxxxxxxx));
                     if (_snowmanxxxxxxx.E() > _snowmanxxxxxxxxx) {
                        _snowmanxxxxxxx.e(_snowmanxxxxxxxxx);
                     }

                     _snowmanxxxx -= _snowmanxxxxxxx.E() - _snowmanxxxxxxxx;
                     _snowmanxxxxx.d(_snowmanxxxxxxx);
                  }
               }

               _snowmanxxx.e(_snowmanxxxx);
               _snowmanx.g(_snowmanxxx);
            }

            this.d();
         } else {
            this.d();
         }
      } else if (this.h != 0) {
         this.d();
      } else if ((_snowman == bik.a || _snowman == bik.b) && (_snowman == 0 || _snowman == 1)) {
         if (_snowman == -999) {
            if (!_snowmanx.m().a()) {
               if (_snowman == 0) {
                  _snowman.a(_snowmanx.m(), true);
                  _snowmanx.g(bmb.b);
               }

               if (_snowman == 1) {
                  _snowman.a(_snowmanx.m().a(1), true);
               }
            }
         } else if (_snowman == bik.b) {
            if (_snowman < 0) {
               return bmb.b;
            }

            bjr _snowmanxx = this.a.get(_snowman);
            if (_snowmanxx == null || !_snowmanxx.a(_snowman)) {
               return bmb.b;
            }

            for (bmb _snowmanxxx = this.b(_snowman, _snowman); !_snowmanxxx.a() && bmb.c(_snowmanxx.e(), _snowmanxxx); _snowmanxxx = this.b(_snowman, _snowman)) {
               _snowman = _snowmanxxx.i();
            }
         } else {
            if (_snowman < 0) {
               return bmb.b;
            }

            bjr _snowmanxx = this.a.get(_snowman);
            if (_snowmanxx != null) {
               bmb _snowmanxxx = _snowmanxx.e();
               bmb _snowmanxxxx = _snowmanx.m();
               if (!_snowmanxxx.a()) {
                  _snowman = _snowmanxxx.i();
               }

               if (_snowmanxxx.a()) {
                  if (!_snowmanxxxx.a() && _snowmanxx.a(_snowmanxxxx)) {
                     int _snowmanxxxxxx = _snowman == 0 ? _snowmanxxxx.E() : 1;
                     if (_snowmanxxxxxx > _snowmanxx.b(_snowmanxxxx)) {
                        _snowmanxxxxxx = _snowmanxx.b(_snowmanxxxx);
                     }

                     _snowmanxx.d(_snowmanxxxx.a(_snowmanxxxxxx));
                  }
               } else if (_snowmanxx.a(_snowman)) {
                  if (_snowmanxxxx.a()) {
                     if (_snowmanxxx.a()) {
                        _snowmanxx.d(bmb.b);
                        _snowmanx.g(bmb.b);
                     } else {
                        int _snowmanxxxxxx = _snowman == 0 ? _snowmanxxx.E() : (_snowmanxxx.E() + 1) / 2;
                        _snowmanx.g(_snowmanxx.a(_snowmanxxxxxx));
                        if (_snowmanxxx.a()) {
                           _snowmanxx.d(bmb.b);
                        }

                        _snowmanxx.a(_snowman, _snowmanx.m());
                     }
                  } else if (_snowmanxx.a(_snowmanxxxx)) {
                     if (a(_snowmanxxx, _snowmanxxxx)) {
                        int _snowmanxxxxxx = _snowman == 0 ? _snowmanxxxx.E() : 1;
                        if (_snowmanxxxxxx > _snowmanxx.b(_snowmanxxxx) - _snowmanxxx.E()) {
                           _snowmanxxxxxx = _snowmanxx.b(_snowmanxxxx) - _snowmanxxx.E();
                        }

                        if (_snowmanxxxxxx > _snowmanxxxx.c() - _snowmanxxx.E()) {
                           _snowmanxxxxxx = _snowmanxxxx.c() - _snowmanxxx.E();
                        }

                        _snowmanxxxx.g(_snowmanxxxxxx);
                        _snowmanxxx.f(_snowmanxxxxxx);
                     } else if (_snowmanxxxx.E() <= _snowmanxx.b(_snowmanxxxx)) {
                        _snowmanxx.d(_snowmanxxxx);
                        _snowmanx.g(_snowmanxxx);
                     }
                  } else if (_snowmanxxxx.c() > 1 && a(_snowmanxxx, _snowmanxxxx) && !_snowmanxxx.a()) {
                     int _snowmanxxxxxxx = _snowmanxxx.E();
                     if (_snowmanxxxxxxx + _snowmanxxxx.E() <= _snowmanxxxx.c()) {
                        _snowmanxxxx.f(_snowmanxxxxxxx);
                        _snowmanxxx = _snowmanxx.a(_snowmanxxxxxxx);
                        if (_snowmanxxx.a()) {
                           _snowmanxx.d(bmb.b);
                        }

                        _snowmanxx.a(_snowman, _snowmanx.m());
                     }
                  }
               }

               _snowmanxx.d();
            }
         }
      } else if (_snowman == bik.c) {
         bjr _snowmanxx = this.a.get(_snowman);
         bmb _snowmanxxxxxxx = _snowmanx.a(_snowman);
         bmb _snowmanxxxxxxxx = _snowmanxx.e();
         if (!_snowmanxxxxxxx.a() || !_snowmanxxxxxxxx.a()) {
            if (_snowmanxxxxxxx.a()) {
               if (_snowmanxx.a(_snowman)) {
                  _snowmanx.a(_snowman, _snowmanxxxxxxxx);
                  _snowmanxx.b(_snowmanxxxxxxxx.E());
                  _snowmanxx.d(bmb.b);
                  _snowmanxx.a(_snowman, _snowmanxxxxxxxx);
               }
            } else if (_snowmanxxxxxxxx.a()) {
               if (_snowmanxx.a(_snowmanxxxxxxx)) {
                  int _snowmanxxxxxxxxx = _snowmanxx.b(_snowmanxxxxxxx);
                  if (_snowmanxxxxxxx.E() > _snowmanxxxxxxxxx) {
                     _snowmanxx.d(_snowmanxxxxxxx.a(_snowmanxxxxxxxxx));
                  } else {
                     _snowmanxx.d(_snowmanxxxxxxx);
                     _snowmanx.a(_snowman, bmb.b);
                  }
               }
            } else if (_snowmanxx.a(_snowman) && _snowmanxx.a(_snowmanxxxxxxx)) {
               int _snowmanxxxxxxxxx = _snowmanxx.b(_snowmanxxxxxxx);
               if (_snowmanxxxxxxx.E() > _snowmanxxxxxxxxx) {
                  _snowmanxx.d(_snowmanxxxxxxx.a(_snowmanxxxxxxxxx));
                  _snowmanxx.a(_snowman, _snowmanxxxxxxxx);
                  if (!_snowmanx.e(_snowmanxxxxxxxx)) {
                     _snowman.a(_snowmanxxxxxxxx, true);
                  }
               } else {
                  _snowmanxx.d(_snowmanxxxxxxx);
                  _snowmanx.a(_snowman, _snowmanxxxxxxxx);
                  _snowmanxx.a(_snowman, _snowmanxxxxxxxx);
               }
            }
         }
      } else if (_snowman == bik.d && _snowman.bC.d && _snowmanx.m().a() && _snowman >= 0) {
         bjr _snowmanxx = this.a.get(_snowman);
         if (_snowmanxx != null && _snowmanxx.f()) {
            bmb _snowmanxxxxxxx = _snowmanxx.e().i();
            _snowmanxxxxxxx.e(_snowmanxxxxxxx.c());
            _snowmanx.g(_snowmanxxxxxxx);
         }
      } else if (_snowman == bik.e && _snowmanx.m().a() && _snowman >= 0) {
         bjr _snowmanxx = this.a.get(_snowman);
         if (_snowmanxx != null && _snowmanxx.f() && _snowmanxx.a(_snowman)) {
            bmb _snowmanxxxxxxx = _snowmanxx.a(_snowman == 0 ? 1 : _snowmanxx.e().E());
            _snowmanxx.a(_snowman, _snowmanxxxxxxx);
            _snowman.a(_snowmanxxxxxxx, true);
         }
      } else if (_snowman == bik.g && _snowman >= 0) {
         bjr _snowmanxx = this.a.get(_snowman);
         bmb _snowmanxxxxxxx = _snowmanx.m();
         if (!_snowmanxxxxxxx.a() && (_snowmanxx == null || !_snowmanxx.f() || !_snowmanxx.a(_snowman))) {
            int _snowmanxxxxxxxx = _snowman == 0 ? 0 : this.a.size() - 1;
            int _snowmanxxxxxxxxx = _snowman == 0 ? 1 : -1;

            for (int _snowmanxxxxxxxxxx = 0; _snowmanxxxxxxxxxx < 2; _snowmanxxxxxxxxxx++) {
               for (int _snowmanxxxxxxxxxxx = _snowmanxxxxxxxx; _snowmanxxxxxxxxxxx >= 0 && _snowmanxxxxxxxxxxx < this.a.size() && _snowmanxxxxxxx.E() < _snowmanxxxxxxx.c(); _snowmanxxxxxxxxxxx += _snowmanxxxxxxxxx) {
                  bjr _snowmanxxxxxxxxxxxx = this.a.get(_snowmanxxxxxxxxxxx);
                  if (_snowmanxxxxxxxxxxxx.f() && a(_snowmanxxxxxxxxxxxx, _snowmanxxxxxxx, true) && _snowmanxxxxxxxxxxxx.a(_snowman) && this.a(_snowmanxxxxxxx, _snowmanxxxxxxxxxxxx)) {
                     bmb _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx.e();
                     if (_snowmanxxxxxxxxxx != 0 || _snowmanxxxxxxxxxxxxx.E() != _snowmanxxxxxxxxxxxxx.c()) {
                        int _snowmanxxxxxxxxxxxxxx = Math.min(_snowmanxxxxxxx.c() - _snowmanxxxxxxx.E(), _snowmanxxxxxxxxxxxxx.E());
                        bmb _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx.a(_snowmanxxxxxxxxxxxxxx);
                        _snowmanxxxxxxx.f(_snowmanxxxxxxxxxxxxxx);
                        if (_snowmanxxxxxxxxxxxxxxx.a()) {
                           _snowmanxxxxxxxxxxxx.d(bmb.b);
                        }

                        _snowmanxxxxxxxxxxxx.a(_snowman, _snowmanxxxxxxxxxxxxxxx);
                     }
                  }
               }
            }
         }

         this.c();
      }

      return _snowman;
   }

   public static boolean a(bmb var0, bmb var1) {
      return _snowman.b() == _snowman.b() && bmb.a(_snowman, _snowman);
   }

   public boolean a(bmb var1, bjr var2) {
      return true;
   }

   public void b(bfw var1) {
      bfv _snowman = _snowman.bm;
      if (!_snowman.m().a()) {
         _snowman.a(_snowman.m(), false);
         _snowman.g(bmb.b);
      }
   }

   protected void a(bfw var1, brx var2, aon var3) {
      if (!_snowman.aX() || _snowman instanceof aah && ((aah)_snowman).q()) {
         for (int _snowman = 0; _snowman < _snowman.Z_(); _snowman++) {
            _snowman.a(_snowman.b(_snowman), false);
         }
      } else {
         for (int _snowman = 0; _snowman < _snowman.Z_(); _snowman++) {
            _snowman.bm.a(_snowman, _snowman.b(_snowman));
         }
      }
   }

   public void a(aon var1) {
      this.c();
   }

   public void a(int var1, bmb var2) {
      this.a(_snowman).d(_snowman);
   }

   public void a(List<bmb> var1) {
      for (int _snowman = 0; _snowman < _snowman.size(); _snowman++) {
         this.a(_snowman).d(_snowman.get(_snowman));
      }
   }

   public void a(int var1, int var2) {
      this.d.get(_snowman).a(_snowman);
   }

   public short a(bfv var1) {
      this.f++;
      return this.f;
   }

   public boolean c(bfw var1) {
      return !this.k.contains(_snowman);
   }

   public void a(bfw var1, boolean var2) {
      if (_snowman) {
         this.k.remove(_snowman);
      } else {
         this.k.add(_snowman);
      }
   }

   public abstract boolean a(bfw var1);

   protected boolean a(bmb var1, int var2, int var3, boolean var4) {
      boolean _snowman = false;
      int _snowmanx = _snowman;
      if (_snowman) {
         _snowmanx = _snowman - 1;
      }

      if (_snowman.d()) {
         while (!_snowman.a() && (_snowman ? _snowmanx >= _snowman : _snowmanx < _snowman)) {
            bjr _snowmanxx = this.a.get(_snowmanx);
            bmb _snowmanxxx = _snowmanxx.e();
            if (!_snowmanxxx.a() && a(_snowman, _snowmanxxx)) {
               int _snowmanxxxx = _snowmanxxx.E() + _snowman.E();
               if (_snowmanxxxx <= _snowman.c()) {
                  _snowman.e(0);
                  _snowmanxxx.e(_snowmanxxxx);
                  _snowmanxx.d();
                  _snowman = true;
               } else if (_snowmanxxx.E() < _snowman.c()) {
                  _snowman.g(_snowman.c() - _snowmanxxx.E());
                  _snowmanxxx.e(_snowman.c());
                  _snowmanxx.d();
                  _snowman = true;
               }
            }

            if (_snowman) {
               _snowmanx--;
            } else {
               _snowmanx++;
            }
         }
      }

      if (!_snowman.a()) {
         if (_snowman) {
            _snowmanx = _snowman - 1;
         } else {
            _snowmanx = _snowman;
         }

         while (_snowman ? _snowmanx >= _snowman : _snowmanx < _snowman) {
            bjr _snowmanxxxx = this.a.get(_snowmanx);
            bmb _snowmanxxxxx = _snowmanxxxx.e();
            if (_snowmanxxxxx.a() && _snowmanxxxx.a(_snowman)) {
               if (_snowman.E() > _snowmanxxxx.a()) {
                  _snowmanxxxx.d(_snowman.a(_snowmanxxxx.a()));
               } else {
                  _snowmanxxxx.d(_snowman.a(_snowman.E()));
               }

               _snowmanxxxx.d();
               _snowman = true;
               break;
            }

            if (_snowman) {
               _snowmanx--;
            } else {
               _snowmanx++;
            }
         }
      }

      return _snowman;
   }

   public static int b(int var0) {
      return _snowman >> 2 & 3;
   }

   public static int c(int var0) {
      return _snowman & 3;
   }

   public static int b(int var0, int var1) {
      return _snowman & 3 | (_snowman & 3) << 2;
   }

   public static boolean a(int var0, bfw var1) {
      if (_snowman == 0) {
         return true;
      } else {
         return _snowman == 1 ? true : _snowman == 2 && _snowman.bC.d;
      }
   }

   protected void d() {
      this.h = 0;
      this.i.clear();
   }

   public static boolean a(@Nullable bjr var0, bmb var1, boolean var2) {
      boolean _snowman = _snowman == null || !_snowman.f();
      return !_snowman && _snowman.a(_snowman.e()) && bmb.a(_snowman.e(), _snowman) ? _snowman.e().E() + (_snowman ? 0 : _snowman.E()) <= _snowman.c() : _snowman;
   }

   public static void a(Set<bjr> var0, int var1, bmb var2, int var3) {
      switch (_snowman) {
         case 0:
            _snowman.e(afm.d((float)_snowman.E() / (float)_snowman.size()));
            break;
         case 1:
            _snowman.e(1);
            break;
         case 2:
            _snowman.e(_snowman.b().i());
      }

      _snowman.f(_snowman);
   }

   public boolean b(bjr var1) {
      return true;
   }

   public static int a(@Nullable ccj var0) {
      return _snowman instanceof aon ? b((aon)_snowman) : 0;
   }

   public static int b(@Nullable aon var0) {
      if (_snowman == null) {
         return 0;
      } else {
         int _snowman = 0;
         float _snowmanx = 0.0F;

         for (int _snowmanxx = 0; _snowmanxx < _snowman.Z_(); _snowmanxx++) {
            bmb _snowmanxxx = _snowman.a(_snowmanxx);
            if (!_snowmanxxx.a()) {
               _snowmanx += (float)_snowmanxxx.E() / (float)Math.min(_snowman.V_(), _snowmanxxx.c());
               _snowman++;
            }
         }

         _snowmanx /= (float)_snowman.Z_();
         return afm.d(_snowmanx * 14.0F) + (_snowman > 0 ? 1 : 0);
      }
   }
}
