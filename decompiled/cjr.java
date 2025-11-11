import com.mojang.serialization.Codec;
import java.util.Random;

public class cjr extends cjl<cjq> {
   public cjr(Codec<cjq> var1) {
      super(_snowman);
   }

   public boolean a(bsr var1, cfy var2, Random var3, fx var4, cjq var5) {
      buo _snowman = _snowman.f.b();
      fx _snowmanx = null;
      buo _snowmanxx = _snowman.d_(_snowman.c()).b();
      if (_snowmanxx == _snowman) {
         _snowmanx = _snowman;
      }

      if (_snowmanx == null) {
         return false;
      } else {
         int _snowmanxxx = afm.a(_snowman, 4, 13);
         if (_snowman.nextInt(12) == 0) {
            _snowmanxxx *= 2;
         }

         if (!_snowman.j) {
            int _snowmanxxxx = _snowman.e();
            if (_snowmanx.v() + _snowmanxxx + 1 >= _snowmanxxxx) {
               return false;
            }
         }

         boolean _snowmanxxxx = !_snowman.j && _snowman.nextFloat() < 0.06F;
         _snowman.a(_snowman, bup.a.n(), 4);
         this.a(_snowman, _snowman, _snowman, _snowmanx, _snowmanxxx, _snowmanxxxx);
         this.b(_snowman, _snowman, _snowman, _snowmanx, _snowmanxxx, _snowmanxxxx);
         return true;
      }
   }

   private static boolean a(bry var0, fx var1, boolean var2) {
      return _snowman.a(_snowman, var1x -> {
         cva _snowman = var1x.c();
         return var1x.c().e() || _snowman && _snowman == cva.e;
      });
   }

   private void a(bry var1, Random var2, cjq var3, fx var4, int var5, boolean var6) {
      fx.a _snowman = new fx.a();
      ceh _snowmanx = _snowman.g;
      int _snowmanxx = _snowman ? 1 : 0;

      for (int _snowmanxxx = -_snowmanxx; _snowmanxxx <= _snowmanxx; _snowmanxxx++) {
         for (int _snowmanxxxx = -_snowmanxx; _snowmanxxxx <= _snowmanxx; _snowmanxxxx++) {
            boolean _snowmanxxxxx = _snowman && afm.a(_snowmanxxx) == _snowmanxx && afm.a(_snowmanxxxx) == _snowmanxx;

            for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < _snowman; _snowmanxxxxxx++) {
               _snowman.a(_snowman, _snowmanxxx, _snowmanxxxxxx, _snowmanxxxx);
               if (a(_snowman, _snowman, true)) {
                  if (_snowman.j) {
                     if (!_snowman.d_(_snowman.c()).g()) {
                        _snowman.b(_snowman, true);
                     }

                     _snowman.a(_snowman, _snowmanx, 3);
                  } else if (_snowmanxxxxx) {
                     if (_snowman.nextFloat() < 0.1F) {
                        this.a(_snowman, _snowman, _snowmanx);
                     }
                  } else {
                     this.a(_snowman, _snowman, _snowmanx);
                  }
               }
            }
         }
      }
   }

   private void b(bry var1, Random var2, cjq var3, fx var4, int var5, boolean var6) {
      fx.a _snowman = new fx.a();
      boolean _snowmanx = _snowman.h.a(bup.iK);
      int _snowmanxx = Math.min(_snowman.nextInt(1 + _snowman / 3) + 5, _snowman);
      int _snowmanxxx = _snowman - _snowmanxx;

      for (int _snowmanxxxx = _snowmanxxx; _snowmanxxxx <= _snowman; _snowmanxxxx++) {
         int _snowmanxxxxx = _snowmanxxxx < _snowman - _snowman.nextInt(3) ? 2 : 1;
         if (_snowmanxx > 8 && _snowmanxxxx < _snowmanxxx + 4) {
            _snowmanxxxxx = 3;
         }

         if (_snowman) {
            _snowmanxxxxx++;
         }

         for (int _snowmanxxxxxx = -_snowmanxxxxx; _snowmanxxxxxx <= _snowmanxxxxx; _snowmanxxxxxx++) {
            for (int _snowmanxxxxxxx = -_snowmanxxxxx; _snowmanxxxxxxx <= _snowmanxxxxx; _snowmanxxxxxxx++) {
               boolean _snowmanxxxxxxxx = _snowmanxxxxxx == -_snowmanxxxxx || _snowmanxxxxxx == _snowmanxxxxx;
               boolean _snowmanxxxxxxxxx = _snowmanxxxxxxx == -_snowmanxxxxx || _snowmanxxxxxxx == _snowmanxxxxx;
               boolean _snowmanxxxxxxxxxx = !_snowmanxxxxxxxx && !_snowmanxxxxxxxxx && _snowmanxxxx != _snowman;
               boolean _snowmanxxxxxxxxxxx = _snowmanxxxxxxxx && _snowmanxxxxxxxxx;
               boolean _snowmanxxxxxxxxxxxx = _snowmanxxxx < _snowmanxxx + 3;
               _snowman.a(_snowman, _snowmanxxxxxx, _snowmanxxxx, _snowmanxxxxxxx);
               if (a(_snowman, _snowman, false)) {
                  if (_snowman.j && !_snowman.d_(_snowman.c()).g()) {
                     _snowman.b(_snowman, true);
                  }

                  if (_snowmanxxxxxxxxxxxx) {
                     if (!_snowmanxxxxxxxxxx) {
                        this.a(_snowman, _snowman, _snowman, _snowman.h, _snowmanx);
                     }
                  } else if (_snowmanxxxxxxxxxx) {
                     this.a(_snowman, _snowman, _snowman, _snowman, 0.1F, 0.2F, _snowmanx ? 0.1F : 0.0F);
                  } else if (_snowmanxxxxxxxxxxx) {
                     this.a(_snowman, _snowman, _snowman, _snowman, 0.01F, 0.7F, _snowmanx ? 0.083F : 0.0F);
                  } else {
                     this.a(_snowman, _snowman, _snowman, _snowman, 5.0E-4F, 0.98F, _snowmanx ? 0.07F : 0.0F);
                  }
               }
            }
         }
      }
   }

   private void a(bry var1, Random var2, cjq var3, fx.a var4, float var5, float var6, float var7) {
      if (_snowman.nextFloat() < _snowman) {
         this.a(_snowman, _snowman, _snowman.i);
      } else if (_snowman.nextFloat() < _snowman) {
         this.a(_snowman, _snowman, _snowman.h);
         if (_snowman.nextFloat() < _snowman) {
            a(_snowman, _snowman, _snowman);
         }
      }
   }

   private void a(bry var1, Random var2, fx var3, ceh var4, boolean var5) {
      if (_snowman.d_(_snowman.c()).a(_snowman.b())) {
         this.a(_snowman, _snowman, _snowman);
      } else if ((double)_snowman.nextFloat() < 0.15) {
         this.a(_snowman, _snowman, _snowman);
         if (_snowman && _snowman.nextInt(11) == 0) {
            a(_snowman, _snowman, _snowman);
         }
      }
   }

   private static void a(fx var0, bry var1, Random var2) {
      fx.a _snowman = _snowman.i().c(gc.a);
      if (_snowman.w(_snowman)) {
         int _snowmanx = afm.a(_snowman, 1, 5);
         if (_snowman.nextInt(7) == 0) {
            _snowmanx *= 2;
         }

         int _snowmanxx = 23;
         int _snowmanxxx = 25;
         cli.a(_snowman, _snowman, _snowman, _snowmanx, 23, 25);
      }
   }
}
