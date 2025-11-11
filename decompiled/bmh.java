import com.google.common.collect.Iterables;
import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import java.util.List;
import javax.annotation.Nullable;

public class bmh extends bkr {
   public bmh(blx.a var1) {
      super(_snowman);
   }

   public static bmb a(brx var0, int var1, int var2, byte var3, boolean var4, boolean var5) {
      bmb _snowman = new bmb(bmd.nf);
      a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman.Y());
      return _snowman;
   }

   @Nullable
   public static cxx a(bmb var0, brx var1) {
      return _snowman.a(a(d(_snowman)));
   }

   @Nullable
   public static cxx b(bmb var0, brx var1) {
      cxx _snowman = a(_snowman, _snowman);
      if (_snowman == null && _snowman instanceof aag) {
         _snowman = a(_snowman, _snowman, _snowman.h().a(), _snowman.h().c(), 3, false, false, _snowman.Y());
      }

      return _snowman;
   }

   public static int d(bmb var0) {
      md _snowman = _snowman.o();
      return _snowman != null && _snowman.c("map", 99) ? _snowman.h("map") : 0;
   }

   private static cxx a(bmb var0, brx var1, int var2, int var3, int var4, boolean var5, boolean var6, vj<brx> var7) {
      int _snowman = _snowman.t();
      cxx _snowmanx = new cxx(a(_snowman));
      _snowmanx.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      _snowman.a(_snowmanx);
      _snowman.p().b("map", _snowman);
      return _snowmanx;
   }

   public static String a(int var0) {
      return "map_" + _snowman;
   }

   public void a(brx var1, aqa var2, cxx var3) {
      if (_snowman.Y() == _snowman.c && _snowman instanceof bfw) {
         int _snowman = 1 << _snowman.f;
         int _snowmanx = _snowman.a;
         int _snowmanxx = _snowman.b;
         int _snowmanxxx = afm.c(_snowman.cD() - (double)_snowmanx) / _snowman + 64;
         int _snowmanxxxx = afm.c(_snowman.cH() - (double)_snowmanxx) / _snowman + 64;
         int _snowmanxxxxx = 128 / _snowman;
         if (_snowman.k().c()) {
            _snowmanxxxxx /= 2;
         }

         cxx.a _snowmanxxxxxx = _snowman.a((bfw)_snowman);
         _snowmanxxxxxx.b++;
         boolean _snowmanxxxxxxx = false;

         for (int _snowmanxxxxxxxx = _snowmanxxx - _snowmanxxxxx + 1; _snowmanxxxxxxxx < _snowmanxxx + _snowmanxxxxx; _snowmanxxxxxxxx++) {
            if ((_snowmanxxxxxxxx & 15) == (_snowmanxxxxxx.b & 15) || _snowmanxxxxxxx) {
               _snowmanxxxxxxx = false;
               double _snowmanxxxxxxxxx = 0.0;

               for (int _snowmanxxxxxxxxxx = _snowmanxxxx - _snowmanxxxxx - 1; _snowmanxxxxxxxxxx < _snowmanxxxx + _snowmanxxxxx; _snowmanxxxxxxxxxx++) {
                  if (_snowmanxxxxxxxx >= 0 && _snowmanxxxxxxxxxx >= -1 && _snowmanxxxxxxxx < 128 && _snowmanxxxxxxxxxx < 128) {
                     int _snowmanxxxxxxxxxxx = _snowmanxxxxxxxx - _snowmanxxx;
                     int _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxx - _snowmanxxxx;
                     boolean _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx * _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxxxxx * _snowmanxxxxxxxxxxxx > (_snowmanxxxxx - 2) * (_snowmanxxxxx - 2);
                     int _snowmanxxxxxxxxxxxxxx = (_snowmanx / _snowman + _snowmanxxxxxxxx - 64) * _snowman;
                     int _snowmanxxxxxxxxxxxxxxx = (_snowmanxx / _snowman + _snowmanxxxxxxxxxx - 64) * _snowman;
                     Multiset<cvb> _snowmanxxxxxxxxxxxxxxxx = LinkedHashMultiset.create();
                     cgh _snowmanxxxxxxxxxxxxxxxxx = _snowman.n(new fx(_snowmanxxxxxxxxxxxxxx, 0, _snowmanxxxxxxxxxxxxxxx));
                     if (!_snowmanxxxxxxxxxxxxxxxxx.t()) {
                        brd _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx.g();
                        int _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx & 15;
                        int _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx & 15;
                        int _snowmanxxxxxxxxxxxxxxxxxxxxx = 0;
                        double _snowmanxxxxxxxxxxxxxxxxxxxxxx = 0.0;
                        if (_snowman.k().c()) {
                           int _snowmanxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxx * 231871;
                           _snowmanxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxx * 31287121 + _snowmanxxxxxxxxxxxxxxxxxxxxxxx * 11;
                           if ((_snowmanxxxxxxxxxxxxxxxxxxxxxxx >> 20 & 1) == 0) {
                              _snowmanxxxxxxxxxxxxxxxx.add(bup.j.n().d(_snowman, fx.b), 10);
                           } else {
                              _snowmanxxxxxxxxxxxxxxxx.add(bup.b.n().d(_snowman, fx.b), 100);
                           }

                           _snowmanxxxxxxxxxxxxxxxxxxxxxx = 100.0;
                        } else {
                           fx.a _snowmanxxxxxxxxxxxxxxxxxxxxxxx = new fx.a();
                           fx.a _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = new fx.a();

                           for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx < _snowman; _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx++) {
                              for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx < _snowman; _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx++) {
                                 int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx.a(
                                       chn.a.b, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxx
                                    )
                                    + 1;
                                 ceh _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
                                 if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx <= 1) {
                                    _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = bup.z.n();
                                 } else {
                                    do {
                                       _snowmanxxxxxxxxxxxxxxxxxxxxxxx.d(
                                          _snowmanxxxxxxxxxxxxxxxxxx.d() + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxx,
                                          --_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                                          _snowmanxxxxxxxxxxxxxxxxxx.e() + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxx
                                       );
                                       _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx.d_(_snowmanxxxxxxxxxxxxxxxxxxxxxxx);
                                    } while (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx.d(_snowman, _snowmanxxxxxxxxxxxxxxxxxxxxxxx) == cvb.b && _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx > 0);

                                    if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx > 0 && !_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx.m().c()) {
                                       int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx - 1;
                                       _snowmanxxxxxxxxxxxxxxxxxxxxxxxx.g(_snowmanxxxxxxxxxxxxxxxxxxxxxxx);

                                       ceh _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
                                       do {
                                          _snowmanxxxxxxxxxxxxxxxxxxxxxxxx.p(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx--);
                                          _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx.d_(_snowmanxxxxxxxxxxxxxxxxxxxxxxxx);
                                          _snowmanxxxxxxxxxxxxxxxxxxxxx++;
                                       } while (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx > 0 && !_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.m().c());

                                       _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = this.a(_snowman, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxx);
                                    }
                                 }

                                 _snowman.a(
                                    _snowman,
                                    _snowmanxxxxxxxxxxxxxxxxxx.d() + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxx,
                                    _snowmanxxxxxxxxxxxxxxxxxx.e() + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxx
                                 );
                                 _snowmanxxxxxxxxxxxxxxxxxxxxxx += (double)_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx / (double)(_snowman * _snowman);
                                 _snowmanxxxxxxxxxxxxxxxx.add(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx.d(_snowman, _snowmanxxxxxxxxxxxxxxxxxxxxxxx));
                              }
                           }
                        }

                        _snowmanxxxxxxxxxxxxxxxxxxxxx /= _snowman * _snowman;
                        double _snowmanxxxxxxxxxxxxxxxxxxxxxxx = (_snowmanxxxxxxxxxxxxxxxxxxxxxx - _snowmanxxxxxxxxx) * 4.0 / (double)(_snowman + 4)
                           + ((double)(_snowmanxxxxxxxx + _snowmanxxxxxxxxxx & 1) - 0.5) * 0.4;
                        int _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = 1;
                        if (_snowmanxxxxxxxxxxxxxxxxxxxxxxx > 0.6) {
                           _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = 2;
                        }

                        if (_snowmanxxxxxxxxxxxxxxxxxxxxxxx < -0.6) {
                           _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = 0;
                        }

                        cvb _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = (cvb)Iterables.getFirst(Multisets.copyHighestCountFirst(_snowmanxxxxxxxxxxxxxxxx), cvb.b);
                        if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx == cvb.n) {
                           _snowmanxxxxxxxxxxxxxxxxxxxxxxx = (double)_snowmanxxxxxxxxxxxxxxxxxxxxx * 0.1 + (double)(_snowmanxxxxxxxx + _snowmanxxxxxxxxxx & 1) * 0.2;
                           _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = 1;
                           if (_snowmanxxxxxxxxxxxxxxxxxxxxxxx < 0.5) {
                              _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = 2;
                           }

                           if (_snowmanxxxxxxxxxxxxxxxxxxxxxxx > 0.9) {
                              _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = 0;
                           }
                        }

                        _snowmanxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxx;
                        if (_snowmanxxxxxxxxxx >= 0
                           && _snowmanxxxxxxxxxxx * _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxxxxx * _snowmanxxxxxxxxxxxx < _snowmanxxxxx * _snowmanxxxxx
                           && (!_snowmanxxxxxxxxxxxxx || (_snowmanxxxxxxxx + _snowmanxxxxxxxxxx & 1) != 0)) {
                           byte _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.g[_snowmanxxxxxxxx + _snowmanxxxxxxxxxx * 128];
                           byte _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = (byte)(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx.aj * 4 + _snowmanxxxxxxxxxxxxxxxxxxxxxxxx);
                           if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx != _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                              _snowman.g[_snowmanxxxxxxxx + _snowmanxxxxxxxxxx * 128] = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx;
                              _snowman.a(_snowmanxxxxxxxx, _snowmanxxxxxxxxxx);
                              _snowmanxxxxxxx = true;
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private ceh a(brx var1, ceh var2, fx var3) {
      cux _snowman = _snowman.m();
      return !_snowman.c() && !_snowman.d(_snowman, _snowman, gc.b) ? _snowman.g() : _snowman;
   }

   private static boolean a(bsv[] var0, int var1, int var2, int var3) {
      return _snowman[_snowman * _snowman + _snowman * _snowman * 128 * _snowman].h() >= 0.0F;
   }

   public static void a(aag var0, bmb var1) {
      cxx _snowman = b(_snowman, _snowman);
      if (_snowman != null) {
         if (_snowman.Y() == _snowman.c) {
            int _snowmanx = 1 << _snowman.f;
            int _snowmanxx = _snowman.a;
            int _snowmanxxx = _snowman.b;
            bsv[] _snowmanxxxx = new bsv[128 * _snowmanx * 128 * _snowmanx];

            for (int _snowmanxxxxx = 0; _snowmanxxxxx < 128 * _snowmanx; _snowmanxxxxx++) {
               for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < 128 * _snowmanx; _snowmanxxxxxx++) {
                  _snowmanxxxx[_snowmanxxxxx * 128 * _snowmanx + _snowmanxxxxxx] = _snowman.v(new fx((_snowmanxx / _snowmanx - 64) * _snowmanx + _snowmanxxxxxx, 0, (_snowmanxxx / _snowmanx - 64) * _snowmanx + _snowmanxxxxx));
               }
            }

            for (int _snowmanxxxxx = 0; _snowmanxxxxx < 128; _snowmanxxxxx++) {
               for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < 128; _snowmanxxxxxx++) {
                  if (_snowmanxxxxx > 0 && _snowmanxxxxxx > 0 && _snowmanxxxxx < 127 && _snowmanxxxxxx < 127) {
                     bsv _snowmanxxxxxxx = _snowmanxxxx[_snowmanxxxxx * _snowmanx + _snowmanxxxxxx * _snowmanx * 128 * _snowmanx];
                     int _snowmanxxxxxxxx = 8;
                     if (a(_snowmanxxxx, _snowmanx, _snowmanxxxxx - 1, _snowmanxxxxxx - 1)) {
                        _snowmanxxxxxxxx--;
                     }

                     if (a(_snowmanxxxx, _snowmanx, _snowmanxxxxx - 1, _snowmanxxxxxx + 1)) {
                        _snowmanxxxxxxxx--;
                     }

                     if (a(_snowmanxxxx, _snowmanx, _snowmanxxxxx - 1, _snowmanxxxxxx)) {
                        _snowmanxxxxxxxx--;
                     }

                     if (a(_snowmanxxxx, _snowmanx, _snowmanxxxxx + 1, _snowmanxxxxxx - 1)) {
                        _snowmanxxxxxxxx--;
                     }

                     if (a(_snowmanxxxx, _snowmanx, _snowmanxxxxx + 1, _snowmanxxxxxx + 1)) {
                        _snowmanxxxxxxxx--;
                     }

                     if (a(_snowmanxxxx, _snowmanx, _snowmanxxxxx + 1, _snowmanxxxxxx)) {
                        _snowmanxxxxxxxx--;
                     }

                     if (a(_snowmanxxxx, _snowmanx, _snowmanxxxxx, _snowmanxxxxxx - 1)) {
                        _snowmanxxxxxxxx--;
                     }

                     if (a(_snowmanxxxx, _snowmanx, _snowmanxxxxx, _snowmanxxxxxx + 1)) {
                        _snowmanxxxxxxxx--;
                     }

                     int _snowmanxxxxxxxxx = 3;
                     cvb _snowmanxxxxxxxxxx = cvb.b;
                     if (_snowmanxxxxxxx.h() < 0.0F) {
                        _snowmanxxxxxxxxxx = cvb.q;
                        if (_snowmanxxxxxxxx > 7 && _snowmanxxxxxx % 2 == 0) {
                           _snowmanxxxxxxxxx = (_snowmanxxxxx + (int)(afm.a((float)_snowmanxxxxxx + 0.0F) * 7.0F)) / 8 % 5;
                           if (_snowmanxxxxxxxxx == 3) {
                              _snowmanxxxxxxxxx = 1;
                           } else if (_snowmanxxxxxxxxx == 4) {
                              _snowmanxxxxxxxxx = 0;
                           }
                        } else if (_snowmanxxxxxxxx > 7) {
                           _snowmanxxxxxxxxxx = cvb.b;
                        } else if (_snowmanxxxxxxxx > 5) {
                           _snowmanxxxxxxxxx = 1;
                        } else if (_snowmanxxxxxxxx > 3) {
                           _snowmanxxxxxxxxx = 0;
                        } else if (_snowmanxxxxxxxx > 1) {
                           _snowmanxxxxxxxxx = 0;
                        }
                     } else if (_snowmanxxxxxxxx > 0) {
                        _snowmanxxxxxxxxxx = cvb.B;
                        if (_snowmanxxxxxxxx > 3) {
                           _snowmanxxxxxxxxx = 1;
                        } else {
                           _snowmanxxxxxxxxx = 3;
                        }
                     }

                     if (_snowmanxxxxxxxxxx != cvb.b) {
                        _snowman.g[_snowmanxxxxx + _snowmanxxxxxx * 128] = (byte)(_snowmanxxxxxxxxxx.aj * 4 + _snowmanxxxxxxxxx);
                        _snowman.a(_snowmanxxxxx, _snowmanxxxxxx);
                     }
                  }
               }
            }
         }
      }
   }

   @Override
   public void a(bmb var1, brx var2, aqa var3, int var4, boolean var5) {
      if (!_snowman.v) {
         cxx _snowman = b(_snowman, _snowman);
         if (_snowman != null) {
            if (_snowman instanceof bfw) {
               bfw _snowmanx = (bfw)_snowman;
               _snowman.a(_snowmanx, _snowman);
            }

            if (!_snowman.h && (_snowman || _snowman instanceof bfw && ((bfw)_snowman).dE() == _snowman)) {
               this.a(_snowman, _snowman, _snowman);
            }
         }
      }
   }

   @Nullable
   @Override
   public oj<?> a(bmb var1, brx var2, bfw var3) {
      return b(_snowman, _snowman).a(_snowman, _snowman, _snowman);
   }

   @Override
   public void b(bmb var1, brx var2, bfw var3) {
      md _snowman = _snowman.o();
      if (_snowman != null && _snowman.c("map_scale_direction", 99)) {
         a(_snowman, _snowman, _snowman.h("map_scale_direction"));
         _snowman.r("map_scale_direction");
      } else if (_snowman != null && _snowman.c("map_to_lock", 1) && _snowman.q("map_to_lock")) {
         a(_snowman, _snowman);
         _snowman.r("map_to_lock");
      }
   }

   protected static void a(bmb var0, brx var1, int var2) {
      cxx _snowman = b(_snowman, _snowman);
      if (_snowman != null) {
         a(_snowman, _snowman, _snowman.a, _snowman.b, afm.a(_snowman.f + _snowman, 0, 4), _snowman.d, _snowman.e, _snowman.c);
      }
   }

   public static void a(brx var0, bmb var1) {
      cxx _snowman = b(_snowman, _snowman);
      if (_snowman != null) {
         cxx _snowmanx = a(_snowman, _snowman, 0, 0, _snowman.f, _snowman.d, _snowman.e, _snowman.c);
         _snowmanx.a(_snowman);
      }
   }

   @Override
   public void a(bmb var1, @Nullable brx var2, List<nr> var3, bnl var4) {
      cxx _snowman = _snowman == null ? null : b(_snowman, _snowman);
      if (_snowman != null && _snowman.h) {
         _snowman.add(new of("filled_map.locked", d(_snowman)).a(k.h));
      }

      if (_snowman.a()) {
         if (_snowman != null) {
            _snowman.add(new of("filled_map.id", d(_snowman)).a(k.h));
            _snowman.add(new of("filled_map.scale", 1 << _snowman.f).a(k.h));
            _snowman.add(new of("filled_map.level", _snowman.f, 4).a(k.h));
         } else {
            _snowman.add(new of("filled_map.unknown").a(k.h));
         }
      }
   }

   public static int g(bmb var0) {
      md _snowman = _snowman.b("display");
      if (_snowman != null && _snowman.c("MapColor", 99)) {
         int _snowmanx = _snowman.h("MapColor");
         return 0xFF000000 | _snowmanx & 16777215;
      } else {
         return -12173266;
      }
   }

   @Override
   public aou a(boa var1) {
      ceh _snowman = _snowman.p().d_(_snowman.a());
      if (_snowman.a(aed.B)) {
         if (!_snowman.p().v) {
            cxx _snowmanx = b(_snowman.m(), _snowman.p());
            _snowmanx.a(_snowman.p(), _snowman.a());
         }

         return aou.a(_snowman.p().v);
      } else {
         return super.a(_snowman);
      }
   }
}
