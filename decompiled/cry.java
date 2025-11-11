import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;

public class cry {
   public static void a(csw var0, fx var1, bzm var2, List<cry.i> var3, Random var4) {
      cry.c _snowman = new cry.c(_snowman);
      cry.d _snowmanx = new cry.d(_snowman, _snowman);
      _snowmanx.a(_snowman, _snowman, _snowman, _snowman);
   }

   static class a extends cry.b {
      private a() {
      }

      @Override
      public String a(Random var1) {
         return "1x1_a" + (_snowman.nextInt(5) + 1);
      }

      @Override
      public String b(Random var1) {
         return "1x1_as" + (_snowman.nextInt(4) + 1);
      }

      @Override
      public String a(Random var1, boolean var2) {
         return "1x2_a" + (_snowman.nextInt(9) + 1);
      }

      @Override
      public String b(Random var1, boolean var2) {
         return "1x2_b" + (_snowman.nextInt(5) + 1);
      }

      @Override
      public String c(Random var1) {
         return "1x2_s" + (_snowman.nextInt(2) + 1);
      }

      @Override
      public String d(Random var1) {
         return "2x2_a" + (_snowman.nextInt(4) + 1);
      }

      @Override
      public String e(Random var1) {
         return "2x2_s1";
      }
   }

   abstract static class b {
      private b() {
      }

      public abstract String a(Random var1);

      public abstract String b(Random var1);

      public abstract String a(Random var1, boolean var2);

      public abstract String b(Random var1, boolean var2);

      public abstract String c(Random var1);

      public abstract String d(Random var1);

      public abstract String e(Random var1);
   }

   static class c {
      private final Random a;
      private final cry.g b;
      private final cry.g c;
      private final cry.g[] d;
      private final int e;
      private final int f;

      public c(Random var1) {
         this.a = _snowman;
         int _snowman = 11;
         this.e = 7;
         this.f = 4;
         this.b = new cry.g(11, 11, 5);
         this.b.a(this.e, this.f, this.e + 1, this.f + 1, 3);
         this.b.a(this.e - 1, this.f, this.e - 1, this.f + 1, 2);
         this.b.a(this.e + 2, this.f - 2, this.e + 3, this.f + 3, 5);
         this.b.a(this.e + 1, this.f - 2, this.e + 1, this.f - 1, 1);
         this.b.a(this.e + 1, this.f + 2, this.e + 1, this.f + 3, 1);
         this.b.a(this.e - 1, this.f - 1, 1);
         this.b.a(this.e - 1, this.f + 2, 1);
         this.b.a(0, 0, 11, 1, 5);
         this.b.a(0, 9, 11, 11, 5);
         this.a(this.b, this.e, this.f - 2, gc.e, 6);
         this.a(this.b, this.e, this.f + 3, gc.e, 6);
         this.a(this.b, this.e - 2, this.f - 1, gc.e, 3);
         this.a(this.b, this.e - 2, this.f + 2, gc.e, 3);

         while (this.a(this.b)) {
         }

         this.d = new cry.g[3];
         this.d[0] = new cry.g(11, 11, 5);
         this.d[1] = new cry.g(11, 11, 5);
         this.d[2] = new cry.g(11, 11, 5);
         this.a(this.b, this.d[0]);
         this.a(this.b, this.d[1]);
         this.d[0].a(this.e + 1, this.f, this.e + 1, this.f + 1, 8388608);
         this.d[1].a(this.e + 1, this.f, this.e + 1, this.f + 1, 8388608);
         this.c = new cry.g(this.b.b, this.b.c, 5);
         this.b();
         this.a(this.c, this.d[2]);
      }

      public static boolean a(cry.g var0, int var1, int var2) {
         int _snowman = _snowman.a(_snowman, _snowman);
         return _snowman == 1 || _snowman == 2 || _snowman == 3 || _snowman == 4;
      }

      public boolean a(cry.g var1, int var2, int var3, int var4, int var5) {
         return (this.d[_snowman].a(_snowman, _snowman) & 65535) == _snowman;
      }

      @Nullable
      public gc b(cry.g var1, int var2, int var3, int var4, int var5) {
         for (gc _snowman : gc.c.a) {
            if (this.a(_snowman, _snowman + _snowman.i(), _snowman + _snowman.k(), _snowman, _snowman)) {
               return _snowman;
            }
         }

         return null;
      }

      private void a(cry.g var1, int var2, int var3, gc var4, int var5) {
         if (_snowman > 0) {
            _snowman.a(_snowman, _snowman, 1);
            _snowman.a(_snowman + _snowman.i(), _snowman + _snowman.k(), 0, 1);

            for (int _snowman = 0; _snowman < 8; _snowman++) {
               gc _snowmanx = gc.b(this.a.nextInt(4));
               if (_snowmanx != _snowman.f() && (_snowmanx != gc.f || !this.a.nextBoolean())) {
                  int _snowmanxx = _snowman + _snowman.i();
                  int _snowmanxxx = _snowman + _snowman.k();
                  if (_snowman.a(_snowmanxx + _snowmanx.i(), _snowmanxxx + _snowmanx.k()) == 0 && _snowman.a(_snowmanxx + _snowmanx.i() * 2, _snowmanxxx + _snowmanx.k() * 2) == 0) {
                     this.a(_snowman, _snowman + _snowman.i() + _snowmanx.i(), _snowman + _snowman.k() + _snowmanx.k(), _snowmanx, _snowman - 1);
                     break;
                  }
               }
            }

            gc _snowmanx = _snowman.g();
            gc _snowmanxx = _snowman.h();
            _snowman.a(_snowman + _snowmanx.i(), _snowman + _snowmanx.k(), 0, 2);
            _snowman.a(_snowman + _snowmanxx.i(), _snowman + _snowmanxx.k(), 0, 2);
            _snowman.a(_snowman + _snowman.i() + _snowmanx.i(), _snowman + _snowman.k() + _snowmanx.k(), 0, 2);
            _snowman.a(_snowman + _snowman.i() + _snowmanxx.i(), _snowman + _snowman.k() + _snowmanxx.k(), 0, 2);
            _snowman.a(_snowman + _snowman.i() * 2, _snowman + _snowman.k() * 2, 0, 2);
            _snowman.a(_snowman + _snowmanx.i() * 2, _snowman + _snowmanx.k() * 2, 0, 2);
            _snowman.a(_snowman + _snowmanxx.i() * 2, _snowman + _snowmanxx.k() * 2, 0, 2);
         }
      }

      private boolean a(cry.g var1) {
         boolean _snowman = false;

         for (int _snowmanx = 0; _snowmanx < _snowman.c; _snowmanx++) {
            for (int _snowmanxx = 0; _snowmanxx < _snowman.b; _snowmanxx++) {
               if (_snowman.a(_snowmanxx, _snowmanx) == 0) {
                  int _snowmanxxx = 0;
                  _snowmanxxx += a(_snowman, _snowmanxx + 1, _snowmanx) ? 1 : 0;
                  _snowmanxxx += a(_snowman, _snowmanxx - 1, _snowmanx) ? 1 : 0;
                  _snowmanxxx += a(_snowman, _snowmanxx, _snowmanx + 1) ? 1 : 0;
                  _snowmanxxx += a(_snowman, _snowmanxx, _snowmanx - 1) ? 1 : 0;
                  if (_snowmanxxx >= 3) {
                     _snowman.a(_snowmanxx, _snowmanx, 2);
                     _snowman = true;
                  } else if (_snowmanxxx == 2) {
                     int _snowmanxxxx = 0;
                     _snowmanxxxx += a(_snowman, _snowmanxx + 1, _snowmanx + 1) ? 1 : 0;
                     _snowmanxxxx += a(_snowman, _snowmanxx - 1, _snowmanx + 1) ? 1 : 0;
                     _snowmanxxxx += a(_snowman, _snowmanxx + 1, _snowmanx - 1) ? 1 : 0;
                     _snowmanxxxx += a(_snowman, _snowmanxx - 1, _snowmanx - 1) ? 1 : 0;
                     if (_snowmanxxxx <= 1) {
                        _snowman.a(_snowmanxx, _snowmanx, 2);
                        _snowman = true;
                     }
                  }
               }
            }
         }

         return _snowman;
      }

      private void b() {
         List<afv<Integer, Integer>> _snowman = Lists.newArrayList();
         cry.g _snowmanx = this.d[1];

         for (int _snowmanxx = 0; _snowmanxx < this.c.c; _snowmanxx++) {
            for (int _snowmanxxx = 0; _snowmanxxx < this.c.b; _snowmanxxx++) {
               int _snowmanxxxx = _snowmanx.a(_snowmanxxx, _snowmanxx);
               int _snowmanxxxxx = _snowmanxxxx & 983040;
               if (_snowmanxxxxx == 131072 && (_snowmanxxxx & 2097152) == 2097152) {
                  _snowman.add(new afv<>(_snowmanxxx, _snowmanxx));
               }
            }
         }

         if (_snowman.isEmpty()) {
            this.c.a(0, 0, this.c.b, this.c.c, 5);
         } else {
            afv<Integer, Integer> _snowmanxx = _snowman.get(this.a.nextInt(_snowman.size()));
            int _snowmanxxxx = _snowmanx.a(_snowmanxx.a(), _snowmanxx.b());
            _snowmanx.a(_snowmanxx.a(), _snowmanxx.b(), _snowmanxxxx | 4194304);
            gc _snowmanxxxxx = this.b(this.b, _snowmanxx.a(), _snowmanxx.b(), 1, _snowmanxxxx & 65535);
            int _snowmanxxxxxx = _snowmanxx.a() + _snowmanxxxxx.i();
            int _snowmanxxxxxxx = _snowmanxx.b() + _snowmanxxxxx.k();

            for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < this.c.c; _snowmanxxxxxxxx++) {
               for (int _snowmanxxxxxxxxx = 0; _snowmanxxxxxxxxx < this.c.b; _snowmanxxxxxxxxx++) {
                  if (!a(this.b, _snowmanxxxxxxxxx, _snowmanxxxxxxxx)) {
                     this.c.a(_snowmanxxxxxxxxx, _snowmanxxxxxxxx, 5);
                  } else if (_snowmanxxxxxxxxx == _snowmanxx.a() && _snowmanxxxxxxxx == _snowmanxx.b()) {
                     this.c.a(_snowmanxxxxxxxxx, _snowmanxxxxxxxx, 3);
                  } else if (_snowmanxxxxxxxxx == _snowmanxxxxxx && _snowmanxxxxxxxx == _snowmanxxxxxxx) {
                     this.c.a(_snowmanxxxxxxxxx, _snowmanxxxxxxxx, 3);
                     this.d[2].a(_snowmanxxxxxxxxx, _snowmanxxxxxxxx, 8388608);
                  }
               }
            }

            List<gc> _snowmanxxxxxxxx = Lists.newArrayList();

            for (gc _snowmanxxxxxxxxxx : gc.c.a) {
               if (this.c.a(_snowmanxxxxxx + _snowmanxxxxxxxxxx.i(), _snowmanxxxxxxx + _snowmanxxxxxxxxxx.k()) == 0) {
                  _snowmanxxxxxxxx.add(_snowmanxxxxxxxxxx);
               }
            }

            if (_snowmanxxxxxxxx.isEmpty()) {
               this.c.a(0, 0, this.c.b, this.c.c, 5);
               _snowmanx.a(_snowmanxx.a(), _snowmanxx.b(), _snowmanxxxx);
            } else {
               gc _snowmanxxxxxxxxxxx = _snowmanxxxxxxxx.get(this.a.nextInt(_snowmanxxxxxxxx.size()));
               this.a(this.c, _snowmanxxxxxx + _snowmanxxxxxxxxxxx.i(), _snowmanxxxxxxx + _snowmanxxxxxxxxxxx.k(), _snowmanxxxxxxxxxxx, 4);

               while (this.a(this.c)) {
               }
            }
         }
      }

      private void a(cry.g var1, cry.g var2) {
         List<afv<Integer, Integer>> _snowman = Lists.newArrayList();

         for (int _snowmanx = 0; _snowmanx < _snowman.c; _snowmanx++) {
            for (int _snowmanxx = 0; _snowmanxx < _snowman.b; _snowmanxx++) {
               if (_snowman.a(_snowmanxx, _snowmanx) == 2) {
                  _snowman.add(new afv<>(_snowmanxx, _snowmanx));
               }
            }
         }

         Collections.shuffle(_snowman, this.a);
         int _snowmanx = 10;

         for (afv<Integer, Integer> _snowmanxxx : _snowman) {
            int _snowmanxxxx = _snowmanxxx.a();
            int _snowmanxxxxx = _snowmanxxx.b();
            if (_snowman.a(_snowmanxxxx, _snowmanxxxxx) == 0) {
               int _snowmanxxxxxx = _snowmanxxxx;
               int _snowmanxxxxxxx = _snowmanxxxx;
               int _snowmanxxxxxxxx = _snowmanxxxxx;
               int _snowmanxxxxxxxxx = _snowmanxxxxx;
               int _snowmanxxxxxxxxxx = 65536;
               if (_snowman.a(_snowmanxxxx + 1, _snowmanxxxxx) == 0
                  && _snowman.a(_snowmanxxxx, _snowmanxxxxx + 1) == 0
                  && _snowman.a(_snowmanxxxx + 1, _snowmanxxxxx + 1) == 0
                  && _snowman.a(_snowmanxxxx + 1, _snowmanxxxxx) == 2
                  && _snowman.a(_snowmanxxxx, _snowmanxxxxx + 1) == 2
                  && _snowman.a(_snowmanxxxx + 1, _snowmanxxxxx + 1) == 2) {
                  _snowmanxxxxxxx = _snowmanxxxx + 1;
                  _snowmanxxxxxxxxx = _snowmanxxxxx + 1;
                  _snowmanxxxxxxxxxx = 262144;
               } else if (_snowman.a(_snowmanxxxx - 1, _snowmanxxxxx) == 0
                  && _snowman.a(_snowmanxxxx, _snowmanxxxxx + 1) == 0
                  && _snowman.a(_snowmanxxxx - 1, _snowmanxxxxx + 1) == 0
                  && _snowman.a(_snowmanxxxx - 1, _snowmanxxxxx) == 2
                  && _snowman.a(_snowmanxxxx, _snowmanxxxxx + 1) == 2
                  && _snowman.a(_snowmanxxxx - 1, _snowmanxxxxx + 1) == 2) {
                  _snowmanxxxxxx = _snowmanxxxx - 1;
                  _snowmanxxxxxxxxx = _snowmanxxxxx + 1;
                  _snowmanxxxxxxxxxx = 262144;
               } else if (_snowman.a(_snowmanxxxx - 1, _snowmanxxxxx) == 0
                  && _snowman.a(_snowmanxxxx, _snowmanxxxxx - 1) == 0
                  && _snowman.a(_snowmanxxxx - 1, _snowmanxxxxx - 1) == 0
                  && _snowman.a(_snowmanxxxx - 1, _snowmanxxxxx) == 2
                  && _snowman.a(_snowmanxxxx, _snowmanxxxxx - 1) == 2
                  && _snowman.a(_snowmanxxxx - 1, _snowmanxxxxx - 1) == 2) {
                  _snowmanxxxxxx = _snowmanxxxx - 1;
                  _snowmanxxxxxxxx = _snowmanxxxxx - 1;
                  _snowmanxxxxxxxxxx = 262144;
               } else if (_snowman.a(_snowmanxxxx + 1, _snowmanxxxxx) == 0 && _snowman.a(_snowmanxxxx + 1, _snowmanxxxxx) == 2) {
                  _snowmanxxxxxxx = _snowmanxxxx + 1;
                  _snowmanxxxxxxxxxx = 131072;
               } else if (_snowman.a(_snowmanxxxx, _snowmanxxxxx + 1) == 0 && _snowman.a(_snowmanxxxx, _snowmanxxxxx + 1) == 2) {
                  _snowmanxxxxxxxxx = _snowmanxxxxx + 1;
                  _snowmanxxxxxxxxxx = 131072;
               } else if (_snowman.a(_snowmanxxxx - 1, _snowmanxxxxx) == 0 && _snowman.a(_snowmanxxxx - 1, _snowmanxxxxx) == 2) {
                  _snowmanxxxxxx = _snowmanxxxx - 1;
                  _snowmanxxxxxxxxxx = 131072;
               } else if (_snowman.a(_snowmanxxxx, _snowmanxxxxx - 1) == 0 && _snowman.a(_snowmanxxxx, _snowmanxxxxx - 1) == 2) {
                  _snowmanxxxxxxxx = _snowmanxxxxx - 1;
                  _snowmanxxxxxxxxxx = 131072;
               }

               int _snowmanxxxxxxxxxxx = this.a.nextBoolean() ? _snowmanxxxxxx : _snowmanxxxxxxx;
               int _snowmanxxxxxxxxxxxx = this.a.nextBoolean() ? _snowmanxxxxxxxx : _snowmanxxxxxxxxx;
               int _snowmanxxxxxxxxxxxxx = 2097152;
               if (!_snowman.b(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, 1)) {
                  _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxxx == _snowmanxxxxxx ? _snowmanxxxxxxx : _snowmanxxxxxx;
                  _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx == _snowmanxxxxxxxx ? _snowmanxxxxxxxxx : _snowmanxxxxxxxx;
                  if (!_snowman.b(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, 1)) {
                     _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx == _snowmanxxxxxxxx ? _snowmanxxxxxxxxx : _snowmanxxxxxxxx;
                     if (!_snowman.b(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, 1)) {
                        _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxxx == _snowmanxxxxxx ? _snowmanxxxxxxx : _snowmanxxxxxx;
                        _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx == _snowmanxxxxxxxx ? _snowmanxxxxxxxxx : _snowmanxxxxxxxx;
                        if (!_snowman.b(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, 1)) {
                           _snowmanxxxxxxxxxxxxx = 0;
                           _snowmanxxxxxxxxxxx = _snowmanxxxxxx;
                           _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxx;
                        }
                     }
                  }
               }

               for (int _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxx; _snowmanxxxxxxxxxxxxxx <= _snowmanxxxxxxxxx; _snowmanxxxxxxxxxxxxxx++) {
                  for (int _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxx; _snowmanxxxxxxxxxxxxxxx <= _snowmanxxxxxxx; _snowmanxxxxxxxxxxxxxxx++) {
                     if (_snowmanxxxxxxxxxxxxxxx == _snowmanxxxxxxxxxxx && _snowmanxxxxxxxxxxxxxx == _snowmanxxxxxxxxxxxx) {
                        _snowman.a(_snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, 1048576 | _snowmanxxxxxxxxxxxxx | _snowmanxxxxxxxxxx | _snowmanx);
                     } else {
                        _snowman.a(_snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxx | _snowmanx);
                     }
                  }
               }

               _snowmanx++;
            }
         }
      }
   }

   static class d {
      private final csw a;
      private final Random b;
      private int c;
      private int d;

      public d(csw var1, Random var2) {
         this.a = _snowman;
         this.b = _snowman;
      }

      public void a(fx var1, bzm var2, List<cry.i> var3, cry.c var4) {
         cry.e _snowman = new cry.e();
         _snowman.b = _snowman;
         _snowman.a = _snowman;
         _snowman.c = "wall_flat";
         cry.e _snowmanx = new cry.e();
         this.a(_snowman, _snowman);
         _snowmanx.b = _snowman.b.b(8);
         _snowmanx.a = _snowman.a;
         _snowmanx.c = "wall_window";
         if (!_snowman.isEmpty()) {
         }

         cry.g _snowmanxx = _snowman.b;
         cry.g _snowmanxxx = _snowman.c;
         this.c = _snowman.e + 1;
         this.d = _snowman.f + 1;
         int _snowmanxxxx = _snowman.e + 1;
         int _snowmanxxxxx = _snowman.f;
         this.a(_snowman, _snowman, _snowmanxx, gc.d, this.c, this.d, _snowmanxxxx, _snowmanxxxxx);
         this.a(_snowman, _snowmanx, _snowmanxx, gc.d, this.c, this.d, _snowmanxxxx, _snowmanxxxxx);
         cry.e _snowmanxxxxxx = new cry.e();
         _snowmanxxxxxx.b = _snowman.b.b(19);
         _snowmanxxxxxx.a = _snowman.a;
         _snowmanxxxxxx.c = "wall_window";
         boolean _snowmanxxxxxxx = false;

         for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < _snowmanxxx.c && !_snowmanxxxxxxx; _snowmanxxxxxxxx++) {
            for (int _snowmanxxxxxxxxx = _snowmanxxx.b - 1; _snowmanxxxxxxxxx >= 0 && !_snowmanxxxxxxx; _snowmanxxxxxxxxx--) {
               if (cry.c.a(_snowmanxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxx)) {
                  _snowmanxxxxxx.b = _snowmanxxxxxx.b.a(_snowman.a(gc.d), 8 + (_snowmanxxxxxxxx - this.d) * 8);
                  _snowmanxxxxxx.b = _snowmanxxxxxx.b.a(_snowman.a(gc.f), (_snowmanxxxxxxxxx - this.c) * 8);
                  this.b(_snowman, _snowmanxxxxxx);
                  this.a(_snowman, _snowmanxxxxxx, _snowmanxxx, gc.d, _snowmanxxxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxx);
                  _snowmanxxxxxxx = true;
               }
            }
         }

         this.a(_snowman, _snowman.b(16), _snowman, _snowmanxx, _snowmanxxx);
         this.a(_snowman, _snowman.b(27), _snowman, _snowmanxxx, null);
         if (!_snowman.isEmpty()) {
         }

         cry.b[] _snowmanxxxxxxxx = new cry.b[]{new cry.a(), new cry.f(), new cry.h()};

         for (int _snowmanxxxxxxxxxx = 0; _snowmanxxxxxxxxxx < 3; _snowmanxxxxxxxxxx++) {
            fx _snowmanxxxxxxxxxxx = _snowman.b(8 * _snowmanxxxxxxxxxx + (_snowmanxxxxxxxxxx == 2 ? 3 : 0));
            cry.g _snowmanxxxxxxxxxxxx = _snowman.d[_snowmanxxxxxxxxxx];
            cry.g _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxx == 2 ? _snowmanxxx : _snowmanxx;
            String _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxx == 0 ? "carpet_south_1" : "carpet_south_2";
            String _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxx == 0 ? "carpet_west_1" : "carpet_west_2";

            for (int _snowmanxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxx.c; _snowmanxxxxxxxxxxxxxxxx++) {
               for (int _snowmanxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxx.b; _snowmanxxxxxxxxxxxxxxxxx++) {
                  if (_snowmanxxxxxxxxxxxxx.a(_snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx) == 1) {
                     fx _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.a(_snowman.a(gc.d), 8 + (_snowmanxxxxxxxxxxxxxxxx - this.d) * 8);
                     _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxx.a(_snowman.a(gc.f), (_snowmanxxxxxxxxxxxxxxxxx - this.c) * 8);
                     _snowman.add(new cry.i(this.a, "corridor_floor", _snowmanxxxxxxxxxxxxxxxxxx, _snowman));
                     if (_snowmanxxxxxxxxxxxxx.a(_snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx - 1) == 1
                        || (_snowmanxxxxxxxxxxxx.a(_snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx - 1) & 8388608) == 8388608) {
                        _snowman.add(new cry.i(this.a, "carpet_north", _snowmanxxxxxxxxxxxxxxxxxx.a(_snowman.a(gc.f), 1).b(), _snowman));
                     }

                     if (_snowmanxxxxxxxxxxxxx.a(_snowmanxxxxxxxxxxxxxxxxx + 1, _snowmanxxxxxxxxxxxxxxxx) == 1
                        || (_snowmanxxxxxxxxxxxx.a(_snowmanxxxxxxxxxxxxxxxxx + 1, _snowmanxxxxxxxxxxxxxxxx) & 8388608) == 8388608) {
                        _snowman.add(new cry.i(this.a, "carpet_east", _snowmanxxxxxxxxxxxxxxxxxx.a(_snowman.a(gc.d), 1).a(_snowman.a(gc.f), 5).b(), _snowman));
                     }

                     if (_snowmanxxxxxxxxxxxxx.a(_snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx + 1) == 1
                        || (_snowmanxxxxxxxxxxxx.a(_snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx + 1) & 8388608) == 8388608) {
                        _snowman.add(new cry.i(this.a, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx.a(_snowman.a(gc.d), 5).a(_snowman.a(gc.e), 1), _snowman));
                     }

                     if (_snowmanxxxxxxxxxxxxx.a(_snowmanxxxxxxxxxxxxxxxxx - 1, _snowmanxxxxxxxxxxxxxxxx) == 1
                        || (_snowmanxxxxxxxxxxxx.a(_snowmanxxxxxxxxxxxxxxxxx - 1, _snowmanxxxxxxxxxxxxxxxx) & 8388608) == 8388608) {
                        _snowman.add(new cry.i(this.a, _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx.a(_snowman.a(gc.e), 1).a(_snowman.a(gc.c), 1), _snowman));
                     }
                  }
               }
            }

            String _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxx == 0 ? "indoors_wall_1" : "indoors_wall_2";
            String _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxx == 0 ? "indoors_door_1" : "indoors_door_2";
            List<gc> _snowmanxxxxxxxxxxxxxxxxxxxx = Lists.newArrayList();

            for (int _snowmanxxxxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxx.c; _snowmanxxxxxxxxxxxxxxxxxxxxx++) {
               for (int _snowmanxxxxxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxx.b; _snowmanxxxxxxxxxxxxxxxxxxxxxx++) {
                  boolean _snowmanxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxx == 2 && _snowmanxxxxxxxxxxxxx.a(_snowmanxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxx) == 3;
                  if (_snowmanxxxxxxxxxxxxx.a(_snowmanxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxx) == 2 || _snowmanxxxxxxxxxxxxxxxxxxxxxxx) {
                     int _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx.a(_snowmanxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxx);
                     int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxx & 983040;
                     int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxx & 65535;
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxx && (_snowmanxxxxxxxxxxxxxxxxxxxxxxxx & 8388608) == 8388608;
                     _snowmanxxxxxxxxxxxxxxxxxxxx.clear();
                     if ((_snowmanxxxxxxxxxxxxxxxxxxxxxxxx & 2097152) == 2097152) {
                        for (gc _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx : gc.c.a) {
                           if (_snowmanxxxxxxxxxxxxx.a(
                                 _snowmanxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx.i(), _snowmanxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx.k()
                              )
                              == 1) {
                              _snowmanxxxxxxxxxxxxxxxxxxxx.add(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx);
                           }
                        }
                     }

                     gc _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = null;
                     if (!_snowmanxxxxxxxxxxxxxxxxxxxx.isEmpty()) {
                        _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxx.get(this.b.nextInt(_snowmanxxxxxxxxxxxxxxxxxxxx.size()));
                     } else if ((_snowmanxxxxxxxxxxxxxxxxxxxxxxxx & 1048576) == 1048576) {
                        _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = gc.b;
                     }

                     fx _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.a(_snowman.a(gc.d), 8 + (_snowmanxxxxxxxxxxxxxxxxxxxxx - this.d) * 8);
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.a(_snowman.a(gc.f), -1 + (_snowmanxxxxxxxxxxxxxxxxxxxxxx - this.c) * 8);
                     if (cry.c.a(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxx - 1, _snowmanxxxxxxxxxxxxxxxxxxxxx)
                        && !_snowman.a(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxx - 1, _snowmanxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx)) {
                        _snowman.add(
                           new cry.i(
                              this.a, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx == gc.e ? _snowmanxxxxxxxxxxxxxxxxxxx : _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowman
                           )
                        );
                     }

                     if (_snowmanxxxxxxxxxxxxx.a(_snowmanxxxxxxxxxxxxxxxxxxxxxx + 1, _snowmanxxxxxxxxxxxxxxxxxxxxx) == 1 && !_snowmanxxxxxxxxxxxxxxxxxxxxxxx) {
                        fx _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.a(_snowman.a(gc.f), 8);
                        _snowman.add(
                           new cry.i(
                              this.a, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx == gc.f ? _snowmanxxxxxxxxxxxxxxxxxxx : _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowman
                           )
                        );
                     }

                     if (cry.c.a(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxx + 1)
                        && !_snowman.a(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxx + 1, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx)) {
                        fx _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.a(_snowman.a(gc.d), 7);
                        _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.a(_snowman.a(gc.f), 7);
                        _snowman.add(
                           new cry.i(
                              this.a,
                              _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx == gc.d ? _snowmanxxxxxxxxxxxxxxxxxxx : _snowmanxxxxxxxxxxxxxxxx,
                              _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                              _snowman.a(bzm.b)
                           )
                        );
                     }

                     if (_snowmanxxxxxxxxxxxxx.a(_snowmanxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxx - 1) == 1 && !_snowmanxxxxxxxxxxxxxxxxxxxxxxx) {
                        fx _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.a(_snowman.a(gc.c), 1);
                        _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.a(_snowman.a(gc.f), 7);
                        _snowman.add(
                           new cry.i(
                              this.a,
                              _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx == gc.c ? _snowmanxxxxxxxxxxxxxxxxxxx : _snowmanxxxxxxxxxxxxxxxx,
                              _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                              _snowman.a(bzm.b)
                           )
                        );
                     }

                     if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx == 65536) {
                        this.a(_snowman, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowman, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxx[_snowmanxxxxxxxxxx]);
                     } else if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx == 131072 && _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx != null) {
                        gc _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.b(
                           _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx
                        );
                        boolean _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (_snowmanxxxxxxxxxxxxxxxxxxxxxxxx & 4194304) == 4194304;
                        this.a(
                           _snowman,
                           _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                           _snowman,
                           _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                           _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                           _snowmanxxxxxxxx[_snowmanxxxxxxxxxx],
                           _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                        );
                     } else if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx == 262144 && _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx != null && _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx != gc.b) {
                        gc _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx.g();
                        if (!_snowman.a(
                           _snowmanxxxxxxxxxxxxx,
                           _snowmanxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.i(),
                           _snowmanxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.k(),
                           _snowmanxxxxxxxxxx,
                           _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx
                        )) {
                           _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.f();
                        }

                        this.a(_snowman, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowman, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxx[_snowmanxxxxxxxxxx]);
                     } else if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx == 262144 && _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx == gc.b) {
                        this.a(_snowman, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowman, _snowmanxxxxxxxx[_snowmanxxxxxxxxxx]);
                     }
                  }
               }
            }
         }
      }

      private void a(List<cry.i> var1, cry.e var2, cry.g var3, gc var4, int var5, int var6, int var7, int var8) {
         int _snowman = _snowman;
         int _snowmanx = _snowman;
         gc _snowmanxx = _snowman;

         do {
            if (!cry.c.a(_snowman, _snowman + _snowman.i(), _snowmanx + _snowman.k())) {
               this.c(_snowman, _snowman);
               _snowman = _snowman.g();
               if (_snowman != _snowman || _snowmanx != _snowman || _snowmanxx != _snowman) {
                  this.b(_snowman, _snowman);
               }
            } else if (cry.c.a(_snowman, _snowman + _snowman.i(), _snowmanx + _snowman.k()) && cry.c.a(_snowman, _snowman + _snowman.i() + _snowman.h().i(), _snowmanx + _snowman.k() + _snowman.h().k())) {
               this.d(_snowman, _snowman);
               _snowman += _snowman.i();
               _snowmanx += _snowman.k();
               _snowman = _snowman.h();
            } else {
               _snowman += _snowman.i();
               _snowmanx += _snowman.k();
               if (_snowman != _snowman || _snowmanx != _snowman || _snowmanxx != _snowman) {
                  this.b(_snowman, _snowman);
               }
            }
         } while (_snowman != _snowman || _snowmanx != _snowman || _snowmanxx != _snowman);
      }

      private void a(List<cry.i> var1, fx var2, bzm var3, cry.g var4, @Nullable cry.g var5) {
         for (int _snowman = 0; _snowman < _snowman.c; _snowman++) {
            for (int _snowmanx = 0; _snowmanx < _snowman.b; _snowmanx++) {
               fx var8 = _snowman.a(_snowman.a(gc.d), 8 + (_snowman - this.d) * 8);
               var8 = var8.a(_snowman.a(gc.f), (_snowmanx - this.c) * 8);
               boolean _snowmanxx = _snowman != null && cry.c.a(_snowman, _snowmanx, _snowman);
               if (cry.c.a(_snowman, _snowmanx, _snowman) && !_snowmanxx) {
                  _snowman.add(new cry.i(this.a, "roof", var8.b(3), _snowman));
                  if (!cry.c.a(_snowman, _snowmanx + 1, _snowman)) {
                     fx _snowmanxxx = var8.a(_snowman.a(gc.f), 6);
                     _snowman.add(new cry.i(this.a, "roof_front", _snowmanxxx, _snowman));
                  }

                  if (!cry.c.a(_snowman, _snowmanx - 1, _snowman)) {
                     fx _snowmanxxx = var8.a(_snowman.a(gc.f), 0);
                     _snowmanxxx = _snowmanxxx.a(_snowman.a(gc.d), 7);
                     _snowman.add(new cry.i(this.a, "roof_front", _snowmanxxx, _snowman.a(bzm.c)));
                  }

                  if (!cry.c.a(_snowman, _snowmanx, _snowman - 1)) {
                     fx _snowmanxxx = var8.a(_snowman.a(gc.e), 1);
                     _snowman.add(new cry.i(this.a, "roof_front", _snowmanxxx, _snowman.a(bzm.d)));
                  }

                  if (!cry.c.a(_snowman, _snowmanx, _snowman + 1)) {
                     fx _snowmanxxx = var8.a(_snowman.a(gc.f), 6);
                     _snowmanxxx = _snowmanxxx.a(_snowman.a(gc.d), 6);
                     _snowman.add(new cry.i(this.a, "roof_front", _snowmanxxx, _snowman.a(bzm.b)));
                  }
               }
            }
         }

         if (_snowman != null) {
            for (int _snowman = 0; _snowman < _snowman.c; _snowman++) {
               for (int _snowmanxx = 0; _snowmanxx < _snowman.b; _snowmanxx++) {
                  fx var17 = _snowman.a(_snowman.a(gc.d), 8 + (_snowman - this.d) * 8);
                  var17 = var17.a(_snowman.a(gc.f), (_snowmanxx - this.c) * 8);
                  boolean _snowmanxxx = cry.c.a(_snowman, _snowmanxx, _snowman);
                  if (cry.c.a(_snowman, _snowmanxx, _snowman) && _snowmanxxx) {
                     if (!cry.c.a(_snowman, _snowmanxx + 1, _snowman)) {
                        fx _snowmanxxxx = var17.a(_snowman.a(gc.f), 7);
                        _snowman.add(new cry.i(this.a, "small_wall", _snowmanxxxx, _snowman));
                     }

                     if (!cry.c.a(_snowman, _snowmanxx - 1, _snowman)) {
                        fx _snowmanxxxx = var17.a(_snowman.a(gc.e), 1);
                        _snowmanxxxx = _snowmanxxxx.a(_snowman.a(gc.d), 6);
                        _snowman.add(new cry.i(this.a, "small_wall", _snowmanxxxx, _snowman.a(bzm.c)));
                     }

                     if (!cry.c.a(_snowman, _snowmanxx, _snowman - 1)) {
                        fx _snowmanxxxx = var17.a(_snowman.a(gc.e), 0);
                        _snowmanxxxx = _snowmanxxxx.a(_snowman.a(gc.c), 1);
                        _snowman.add(new cry.i(this.a, "small_wall", _snowmanxxxx, _snowman.a(bzm.d)));
                     }

                     if (!cry.c.a(_snowman, _snowmanxx, _snowman + 1)) {
                        fx _snowmanxxxx = var17.a(_snowman.a(gc.f), 6);
                        _snowmanxxxx = _snowmanxxxx.a(_snowman.a(gc.d), 7);
                        _snowman.add(new cry.i(this.a, "small_wall", _snowmanxxxx, _snowman.a(bzm.b)));
                     }

                     if (!cry.c.a(_snowman, _snowmanxx + 1, _snowman)) {
                        if (!cry.c.a(_snowman, _snowmanxx, _snowman - 1)) {
                           fx _snowmanxxxx = var17.a(_snowman.a(gc.f), 7);
                           _snowmanxxxx = _snowmanxxxx.a(_snowman.a(gc.c), 2);
                           _snowman.add(new cry.i(this.a, "small_wall_corner", _snowmanxxxx, _snowman));
                        }

                        if (!cry.c.a(_snowman, _snowmanxx, _snowman + 1)) {
                           fx _snowmanxxxx = var17.a(_snowman.a(gc.f), 8);
                           _snowmanxxxx = _snowmanxxxx.a(_snowman.a(gc.d), 7);
                           _snowman.add(new cry.i(this.a, "small_wall_corner", _snowmanxxxx, _snowman.a(bzm.b)));
                        }
                     }

                     if (!cry.c.a(_snowman, _snowmanxx - 1, _snowman)) {
                        if (!cry.c.a(_snowman, _snowmanxx, _snowman - 1)) {
                           fx _snowmanxxxx = var17.a(_snowman.a(gc.e), 2);
                           _snowmanxxxx = _snowmanxxxx.a(_snowman.a(gc.c), 1);
                           _snowman.add(new cry.i(this.a, "small_wall_corner", _snowmanxxxx, _snowman.a(bzm.d)));
                        }

                        if (!cry.c.a(_snowman, _snowmanxx, _snowman + 1)) {
                           fx _snowmanxxxx = var17.a(_snowman.a(gc.e), 1);
                           _snowmanxxxx = _snowmanxxxx.a(_snowman.a(gc.d), 8);
                           _snowman.add(new cry.i(this.a, "small_wall_corner", _snowmanxxxx, _snowman.a(bzm.c)));
                        }
                     }
                  }
               }
            }
         }

         for (int _snowman = 0; _snowman < _snowman.c; _snowman++) {
            for (int _snowmanxxx = 0; _snowmanxxx < _snowman.b; _snowmanxxx++) {
               fx var19 = _snowman.a(_snowman.a(gc.d), 8 + (_snowman - this.d) * 8);
               var19 = var19.a(_snowman.a(gc.f), (_snowmanxxx - this.c) * 8);
               boolean _snowmanxxxx = _snowman != null && cry.c.a(_snowman, _snowmanxxx, _snowman);
               if (cry.c.a(_snowman, _snowmanxxx, _snowman) && !_snowmanxxxx) {
                  if (!cry.c.a(_snowman, _snowmanxxx + 1, _snowman)) {
                     fx _snowmanxxxxx = var19.a(_snowman.a(gc.f), 6);
                     if (!cry.c.a(_snowman, _snowmanxxx, _snowman + 1)) {
                        fx _snowmanxxxxxx = _snowmanxxxxx.a(_snowman.a(gc.d), 6);
                        _snowman.add(new cry.i(this.a, "roof_corner", _snowmanxxxxxx, _snowman));
                     } else if (cry.c.a(_snowman, _snowmanxxx + 1, _snowman + 1)) {
                        fx _snowmanxxxxxx = _snowmanxxxxx.a(_snowman.a(gc.d), 5);
                        _snowman.add(new cry.i(this.a, "roof_inner_corner", _snowmanxxxxxx, _snowman));
                     }

                     if (!cry.c.a(_snowman, _snowmanxxx, _snowman - 1)) {
                        _snowman.add(new cry.i(this.a, "roof_corner", _snowmanxxxxx, _snowman.a(bzm.d)));
                     } else if (cry.c.a(_snowman, _snowmanxxx + 1, _snowman - 1)) {
                        fx _snowmanxxxxxx = var19.a(_snowman.a(gc.f), 9);
                        _snowmanxxxxxx = _snowmanxxxxxx.a(_snowman.a(gc.c), 2);
                        _snowman.add(new cry.i(this.a, "roof_inner_corner", _snowmanxxxxxx, _snowman.a(bzm.b)));
                     }
                  }

                  if (!cry.c.a(_snowman, _snowmanxxx - 1, _snowman)) {
                     fx _snowmanxxxxxx = var19.a(_snowman.a(gc.f), 0);
                     _snowmanxxxxxx = _snowmanxxxxxx.a(_snowman.a(gc.d), 0);
                     if (!cry.c.a(_snowman, _snowmanxxx, _snowman + 1)) {
                        fx _snowmanxxxxxxx = _snowmanxxxxxx.a(_snowman.a(gc.d), 6);
                        _snowman.add(new cry.i(this.a, "roof_corner", _snowmanxxxxxxx, _snowman.a(bzm.b)));
                     } else if (cry.c.a(_snowman, _snowmanxxx - 1, _snowman + 1)) {
                        fx _snowmanxxxxxxx = _snowmanxxxxxx.a(_snowman.a(gc.d), 8);
                        _snowmanxxxxxxx = _snowmanxxxxxxx.a(_snowman.a(gc.e), 3);
                        _snowman.add(new cry.i(this.a, "roof_inner_corner", _snowmanxxxxxxx, _snowman.a(bzm.d)));
                     }

                     if (!cry.c.a(_snowman, _snowmanxxx, _snowman - 1)) {
                        _snowman.add(new cry.i(this.a, "roof_corner", _snowmanxxxxxx, _snowman.a(bzm.c)));
                     } else if (cry.c.a(_snowman, _snowmanxxx - 1, _snowman - 1)) {
                        fx _snowmanxxxxxxx = _snowmanxxxxxx.a(_snowman.a(gc.d), 1);
                        _snowman.add(new cry.i(this.a, "roof_inner_corner", _snowmanxxxxxxx, _snowman.a(bzm.c)));
                     }
                  }
               }
            }
         }
      }

      private void a(List<cry.i> var1, cry.e var2) {
         gc _snowman = _snowman.a.a(gc.e);
         _snowman.add(new cry.i(this.a, "entrance", _snowman.b.a(_snowman, 9), _snowman.a));
         _snowman.b = _snowman.b.a(_snowman.a.a(gc.d), 16);
      }

      private void b(List<cry.i> var1, cry.e var2) {
         _snowman.add(new cry.i(this.a, _snowman.c, _snowman.b.a(_snowman.a.a(gc.f), 7), _snowman.a));
         _snowman.b = _snowman.b.a(_snowman.a.a(gc.d), 8);
      }

      private void c(List<cry.i> var1, cry.e var2) {
         _snowman.b = _snowman.b.a(_snowman.a.a(gc.d), -1);
         _snowman.add(new cry.i(this.a, "wall_corner", _snowman.b, _snowman.a));
         _snowman.b = _snowman.b.a(_snowman.a.a(gc.d), -7);
         _snowman.b = _snowman.b.a(_snowman.a.a(gc.e), -6);
         _snowman.a = _snowman.a.a(bzm.b);
      }

      private void d(List<cry.i> var1, cry.e var2) {
         _snowman.b = _snowman.b.a(_snowman.a.a(gc.d), 6);
         _snowman.b = _snowman.b.a(_snowman.a.a(gc.f), 8);
         _snowman.a = _snowman.a.a(bzm.d);
      }

      private void a(List<cry.i> var1, fx var2, bzm var3, gc var4, cry.b var5) {
         bzm _snowman = bzm.a;
         String _snowmanx = _snowman.a(this.b);
         if (_snowman != gc.f) {
            if (_snowman == gc.c) {
               _snowman = _snowman.a(bzm.d);
            } else if (_snowman == gc.e) {
               _snowman = _snowman.a(bzm.c);
            } else if (_snowman == gc.d) {
               _snowman = _snowman.a(bzm.b);
            } else {
               _snowmanx = _snowman.b(this.b);
            }
         }

         fx _snowmanxx = ctb.a(new fx(1, 0, 0), byg.a, _snowman, 7, 7);
         _snowman = _snowman.a(_snowman);
         _snowmanxx = _snowmanxx.a(_snowman);
         fx _snowmanxxx = _snowman.b(_snowmanxx.u(), 0, _snowmanxx.w());
         _snowman.add(new cry.i(this.a, _snowmanx, _snowmanxxx, _snowman));
      }

      private void a(List<cry.i> var1, fx var2, bzm var3, gc var4, gc var5, cry.b var6, boolean var7) {
         if (_snowman == gc.f && _snowman == gc.d) {
            fx _snowman = _snowman.a(_snowman.a(gc.f), 1);
            _snowman.add(new cry.i(this.a, _snowman.a(this.b, _snowman), _snowman, _snowman));
         } else if (_snowman == gc.f && _snowman == gc.c) {
            fx _snowman = _snowman.a(_snowman.a(gc.f), 1);
            _snowman = _snowman.a(_snowman.a(gc.d), 6);
            _snowman.add(new cry.i(this.a, _snowman.a(this.b, _snowman), _snowman, _snowman, byg.b));
         } else if (_snowman == gc.e && _snowman == gc.c) {
            fx _snowman = _snowman.a(_snowman.a(gc.f), 7);
            _snowman = _snowman.a(_snowman.a(gc.d), 6);
            _snowman.add(new cry.i(this.a, _snowman.a(this.b, _snowman), _snowman, _snowman.a(bzm.c)));
         } else if (_snowman == gc.e && _snowman == gc.d) {
            fx _snowman = _snowman.a(_snowman.a(gc.f), 7);
            _snowman.add(new cry.i(this.a, _snowman.a(this.b, _snowman), _snowman, _snowman, byg.c));
         } else if (_snowman == gc.d && _snowman == gc.f) {
            fx _snowman = _snowman.a(_snowman.a(gc.f), 1);
            _snowman.add(new cry.i(this.a, _snowman.a(this.b, _snowman), _snowman, _snowman.a(bzm.b), byg.b));
         } else if (_snowman == gc.d && _snowman == gc.e) {
            fx _snowman = _snowman.a(_snowman.a(gc.f), 7);
            _snowman.add(new cry.i(this.a, _snowman.a(this.b, _snowman), _snowman, _snowman.a(bzm.b)));
         } else if (_snowman == gc.c && _snowman == gc.e) {
            fx _snowman = _snowman.a(_snowman.a(gc.f), 7);
            _snowman = _snowman.a(_snowman.a(gc.d), 6);
            _snowman.add(new cry.i(this.a, _snowman.a(this.b, _snowman), _snowman, _snowman.a(bzm.b), byg.c));
         } else if (_snowman == gc.c && _snowman == gc.f) {
            fx _snowman = _snowman.a(_snowman.a(gc.f), 1);
            _snowman = _snowman.a(_snowman.a(gc.d), 6);
            _snowman.add(new cry.i(this.a, _snowman.a(this.b, _snowman), _snowman, _snowman.a(bzm.d)));
         } else if (_snowman == gc.d && _snowman == gc.c) {
            fx _snowman = _snowman.a(_snowman.a(gc.f), 1);
            _snowman = _snowman.a(_snowman.a(gc.c), 8);
            _snowman.add(new cry.i(this.a, _snowman.b(this.b, _snowman), _snowman, _snowman));
         } else if (_snowman == gc.c && _snowman == gc.d) {
            fx _snowman = _snowman.a(_snowman.a(gc.f), 7);
            _snowman = _snowman.a(_snowman.a(gc.d), 14);
            _snowman.add(new cry.i(this.a, _snowman.b(this.b, _snowman), _snowman, _snowman.a(bzm.c)));
         } else if (_snowman == gc.e && _snowman == gc.f) {
            fx _snowman = _snowman.a(_snowman.a(gc.f), 15);
            _snowman.add(new cry.i(this.a, _snowman.b(this.b, _snowman), _snowman, _snowman.a(bzm.b)));
         } else if (_snowman == gc.f && _snowman == gc.e) {
            fx _snowman = _snowman.a(_snowman.a(gc.e), 7);
            _snowman = _snowman.a(_snowman.a(gc.d), 6);
            _snowman.add(new cry.i(this.a, _snowman.b(this.b, _snowman), _snowman, _snowman.a(bzm.d)));
         } else if (_snowman == gc.b && _snowman == gc.f) {
            fx _snowman = _snowman.a(_snowman.a(gc.f), 15);
            _snowman.add(new cry.i(this.a, _snowman.c(this.b), _snowman, _snowman.a(bzm.b)));
         } else if (_snowman == gc.b && _snowman == gc.d) {
            fx _snowman = _snowman.a(_snowman.a(gc.f), 1);
            _snowman = _snowman.a(_snowman.a(gc.c), 0);
            _snowman.add(new cry.i(this.a, _snowman.c(this.b), _snowman, _snowman));
         }
      }

      private void a(List<cry.i> var1, fx var2, bzm var3, gc var4, gc var5, cry.b var6) {
         int _snowman = 0;
         int _snowmanx = 0;
         bzm _snowmanxx = _snowman;
         byg _snowmanxxx = byg.a;
         if (_snowman == gc.f && _snowman == gc.d) {
            _snowman = -7;
         } else if (_snowman == gc.f && _snowman == gc.c) {
            _snowman = -7;
            _snowmanx = 6;
            _snowmanxxx = byg.b;
         } else if (_snowman == gc.c && _snowman == gc.f) {
            _snowman = 1;
            _snowmanx = 14;
            _snowmanxx = _snowman.a(bzm.d);
         } else if (_snowman == gc.c && _snowman == gc.e) {
            _snowman = 7;
            _snowmanx = 14;
            _snowmanxx = _snowman.a(bzm.d);
            _snowmanxxx = byg.b;
         } else if (_snowman == gc.d && _snowman == gc.e) {
            _snowman = 7;
            _snowmanx = -8;
            _snowmanxx = _snowman.a(bzm.b);
         } else if (_snowman == gc.d && _snowman == gc.f) {
            _snowman = 1;
            _snowmanx = -8;
            _snowmanxx = _snowman.a(bzm.b);
            _snowmanxxx = byg.b;
         } else if (_snowman == gc.e && _snowman == gc.c) {
            _snowman = 15;
            _snowmanx = 6;
            _snowmanxx = _snowman.a(bzm.c);
         } else if (_snowman == gc.e && _snowman == gc.d) {
            _snowman = 15;
            _snowmanxxx = byg.c;
         }

         fx _snowmanxxxx = _snowman.a(_snowman.a(gc.f), _snowman);
         _snowmanxxxx = _snowmanxxxx.a(_snowman.a(gc.d), _snowmanx);
         _snowman.add(new cry.i(this.a, _snowman.d(this.b), _snowmanxxxx, _snowmanxx, _snowmanxxx));
      }

      private void a(List<cry.i> var1, fx var2, bzm var3, cry.b var4) {
         fx _snowman = _snowman.a(_snowman.a(gc.f), 1);
         _snowman.add(new cry.i(this.a, _snowman.e(this.b), _snowman, _snowman, byg.a));
      }
   }

   static class e {
      public bzm a;
      public fx b;
      public String c;

      private e() {
      }
   }

   static class f extends cry.b {
      private f() {
      }

      @Override
      public String a(Random var1) {
         return "1x1_b" + (_snowman.nextInt(4) + 1);
      }

      @Override
      public String b(Random var1) {
         return "1x1_as" + (_snowman.nextInt(4) + 1);
      }

      @Override
      public String a(Random var1, boolean var2) {
         return _snowman ? "1x2_c_stairs" : "1x2_c" + (_snowman.nextInt(4) + 1);
      }

      @Override
      public String b(Random var1, boolean var2) {
         return _snowman ? "1x2_d_stairs" : "1x2_d" + (_snowman.nextInt(5) + 1);
      }

      @Override
      public String c(Random var1) {
         return "1x2_se" + (_snowman.nextInt(1) + 1);
      }

      @Override
      public String d(Random var1) {
         return "2x2_b" + (_snowman.nextInt(5) + 1);
      }

      @Override
      public String e(Random var1) {
         return "2x2_s1";
      }
   }

   static class g {
      private final int[][] a;
      private final int b;
      private final int c;
      private final int d;

      public g(int var1, int var2, int var3) {
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
         this.a = new int[_snowman][_snowman];
      }

      public void a(int var1, int var2, int var3) {
         if (_snowman >= 0 && _snowman < this.b && _snowman >= 0 && _snowman < this.c) {
            this.a[_snowman][_snowman] = _snowman;
         }
      }

      public void a(int var1, int var2, int var3, int var4, int var5) {
         for (int _snowman = _snowman; _snowman <= _snowman; _snowman++) {
            for (int _snowmanx = _snowman; _snowmanx <= _snowman; _snowmanx++) {
               this.a(_snowmanx, _snowman, _snowman);
            }
         }
      }

      public int a(int var1, int var2) {
         return _snowman >= 0 && _snowman < this.b && _snowman >= 0 && _snowman < this.c ? this.a[_snowman][_snowman] : this.d;
      }

      public void a(int var1, int var2, int var3, int var4) {
         if (this.a(_snowman, _snowman) == _snowman) {
            this.a(_snowman, _snowman, _snowman);
         }
      }

      public boolean b(int var1, int var2, int var3) {
         return this.a(_snowman - 1, _snowman) == _snowman || this.a(_snowman + 1, _snowman) == _snowman || this.a(_snowman, _snowman + 1) == _snowman || this.a(_snowman, _snowman - 1) == _snowman;
      }
   }

   static class h extends cry.f {
      private h() {
      }
   }

   public static class i extends crx {
      private final String d;
      private final bzm e;
      private final byg f;

      public i(csw var1, String var2, fx var3, bzm var4) {
         this(_snowman, _snowman, _snowman, _snowman, byg.a);
      }

      public i(csw var1, String var2, fx var3, bzm var4, byg var5) {
         super(clb.Z, 0);
         this.d = _snowman;
         this.c = _snowman;
         this.e = _snowman;
         this.f = _snowman;
         this.a(_snowman);
      }

      public i(csw var1, md var2) {
         super(clb.Z, _snowman);
         this.d = _snowman.l("Template");
         this.e = bzm.valueOf(_snowman.l("Rot"));
         this.f = byg.valueOf(_snowman.l("Mi"));
         this.a(_snowman);
      }

      private void a(csw var1) {
         ctb _snowman = _snowman.a(new vk("woodland_mansion/" + this.d));
         csx _snowmanx = new csx().a(true).a(this.e).a(this.f).a(cse.b);
         this.a(_snowman, this.c, _snowmanx);
      }

      @Override
      protected void a(md var1) {
         super.a(_snowman);
         _snowman.a("Template", this.d);
         _snowman.a("Rot", this.b.d().name());
         _snowman.a("Mi", this.b.c().name());
      }

      @Override
      protected void a(String var1, fx var2, bsk var3, Random var4, cra var5) {
         if (_snowman.startsWith("Chest")) {
            bzm _snowman = this.b.d();
            ceh _snowmanx = bup.bR.n();
            if ("ChestWest".equals(_snowman)) {
               _snowmanx = _snowmanx.a(bve.b, _snowman.a(gc.e));
            } else if ("ChestEast".equals(_snowman)) {
               _snowmanx = _snowmanx.a(bve.b, _snowman.a(gc.f));
            } else if ("ChestSouth".equals(_snowman)) {
               _snowmanx = _snowmanx.a(bve.b, _snowman.a(gc.d));
            } else if ("ChestNorth".equals(_snowman)) {
               _snowmanx = _snowmanx.a(bve.b, _snowman.a(gc.c));
            }

            this.a(_snowman, _snowman, _snowman, _snowman, cyq.D, _snowmanx);
         } else {
            bcy _snowman;
            switch (_snowman) {
               case "Mage":
                  _snowman = aqe.w.a(_snowman.E());
                  break;
               case "Warrior":
                  _snowman = aqe.aQ.a(_snowman.E());
                  break;
               default:
                  return;
            }

            _snowman.es();
            _snowman.a(_snowman, 0.0F, 0.0F);
            _snowman.a(_snowman, _snowman.d(_snowman.cB()), aqp.d, null, null);
            _snowman.l(_snowman);
            _snowman.a(_snowman, bup.a.n(), 2);
         }
      }
   }
}
