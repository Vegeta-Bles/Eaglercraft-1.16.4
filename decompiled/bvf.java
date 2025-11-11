import java.util.Random;
import javax.annotation.Nullable;

public class bvf extends buo {
   public static final cfg a = cex.ah;
   private final bvg b;

   protected bvf(bvg var1, ceg.c var2) {
      super(_snowman);
      this.b = _snowman;
      this.j(this.n.b().a(a, Integer.valueOf(0)));
   }

   @Override
   public void a(ceh var1, aag var2, fx var3, Random var4) {
      if (!_snowman.a(_snowman, _snowman)) {
         _snowman.b(_snowman, true);
      }
   }

   @Override
   public boolean a_(ceh var1) {
      return _snowman.c(a) < 5;
   }

   @Override
   public void b(ceh var1, aag var2, fx var3, Random var4) {
      fx _snowman = _snowman.b();
      if (_snowman.w(_snowman) && _snowman.v() < 256) {
         int _snowmanx = _snowman.c(a);
         if (_snowmanx < 5) {
            boolean _snowmanxx = false;
            boolean _snowmanxxx = false;
            ceh _snowmanxxxx = _snowman.d_(_snowman.c());
            buo _snowmanxxxxx = _snowmanxxxx.b();
            if (_snowmanxxxxx == bup.ee) {
               _snowmanxx = true;
            } else if (_snowmanxxxxx == this.b) {
               int _snowmanxxxxxx = 1;

               for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < 4; _snowmanxxxxxxx++) {
                  buo _snowmanxxxxxxxx = _snowman.d_(_snowman.c(_snowmanxxxxxx + 1)).b();
                  if (_snowmanxxxxxxxx != this.b) {
                     if (_snowmanxxxxxxxx == bup.ee) {
                        _snowmanxxx = true;
                     }
                     break;
                  }

                  _snowmanxxxxxx++;
               }

               if (_snowmanxxxxxx < 2 || _snowmanxxxxxx <= _snowman.nextInt(_snowmanxxx ? 5 : 4)) {
                  _snowmanxx = true;
               }
            } else if (_snowmanxxxx.g()) {
               _snowmanxx = true;
            }

            if (_snowmanxx && b(_snowman, _snowman, null) && _snowman.w(_snowman.b(2))) {
               _snowman.a(_snowman, this.b.a(_snowman, _snowman), 2);
               this.a(_snowman, _snowman, _snowmanx);
            } else if (_snowmanx < 4) {
               int _snowmanxxxxxx = _snowman.nextInt(4);
               if (_snowmanxxx) {
                  _snowmanxxxxxx++;
               }

               boolean _snowmanxxxxxxx = false;

               for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < _snowmanxxxxxx; _snowmanxxxxxxxx++) {
                  gc _snowmanxxxxxxxxx = gc.c.a.a(_snowman);
                  fx _snowmanxxxxxxxxxx = _snowman.a(_snowmanxxxxxxxxx);
                  if (_snowman.w(_snowmanxxxxxxxxxx) && _snowman.w(_snowmanxxxxxxxxxx.c()) && b(_snowman, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxx.f())) {
                     this.a(_snowman, _snowmanxxxxxxxxxx, _snowmanx + 1);
                     _snowmanxxxxxxx = true;
                  }
               }

               if (_snowmanxxxxxxx) {
                  _snowman.a(_snowman, this.b.a(_snowman, _snowman), 2);
               } else {
                  this.a(_snowman, _snowman);
               }
            } else {
               this.a(_snowman, _snowman);
            }
         }
      }
   }

   private void a(brx var1, fx var2, int var3) {
      _snowman.a(_snowman, this.n().a(a, Integer.valueOf(_snowman)), 2);
      _snowman.c(1033, _snowman, 0);
   }

   private void a(brx var1, fx var2) {
      _snowman.a(_snowman, this.n().a(a, Integer.valueOf(5)), 2);
      _snowman.c(1034, _snowman, 0);
   }

   private static boolean b(brz var0, fx var1, @Nullable gc var2) {
      for (gc _snowman : gc.c.a) {
         if (_snowman != _snowman && !_snowman.w(_snowman.a(_snowman))) {
            return false;
         }
      }

      return true;
   }

   @Override
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      if (_snowman != gc.b && !_snowman.a(_snowman, _snowman)) {
         _snowman.J().a(_snowman, this, 1);
      }

      return super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public boolean a(ceh var1, brz var2, fx var3) {
      ceh _snowman = _snowman.d_(_snowman.c());
      if (_snowman.b() != this.b && !_snowman.a(bup.ee)) {
         if (!_snowman.g()) {
            return false;
         } else {
            boolean _snowmanx = false;

            for (gc _snowmanxx : gc.c.a) {
               ceh _snowmanxxx = _snowman.d_(_snowman.a(_snowmanxx));
               if (_snowmanxxx.a(this.b)) {
                  if (_snowmanx) {
                     return false;
                  }

                  _snowmanx = true;
               } else if (!_snowmanxxx.g()) {
                  return false;
               }
            }

            return _snowmanx;
         }
      } else {
         return true;
      }
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(a);
   }

   public static void a(bry var0, fx var1, Random var2, int var3) {
      _snowman.a(_snowman, ((bvg)bup.ix).a(_snowman, _snowman), 2);
      a(_snowman, _snowman, _snowman, _snowman, _snowman, 0);
   }

   private static void a(bry var0, fx var1, Random var2, fx var3, int var4, int var5) {
      bvg _snowman = (bvg)bup.ix;
      int _snowmanx = _snowman.nextInt(4) + 1;
      if (_snowman == 0) {
         _snowmanx++;
      }

      for (int _snowmanxx = 0; _snowmanxx < _snowmanx; _snowmanxx++) {
         fx _snowmanxxx = _snowman.b(_snowmanxx + 1);
         if (!b(_snowman, _snowmanxxx, null)) {
            return;
         }

         _snowman.a(_snowmanxxx, _snowman.a(_snowman, _snowmanxxx), 2);
         _snowman.a(_snowmanxxx.c(), _snowman.a(_snowman, _snowmanxxx.c()), 2);
      }

      boolean _snowmanxx = false;
      if (_snowman < 4) {
         int _snowmanxxx = _snowman.nextInt(4);
         if (_snowman == 0) {
            _snowmanxxx++;
         }

         for (int _snowmanxxxx = 0; _snowmanxxxx < _snowmanxxx; _snowmanxxxx++) {
            gc _snowmanxxxxx = gc.c.a.a(_snowman);
            fx _snowmanxxxxxx = _snowman.b(_snowmanx).a(_snowmanxxxxx);
            if (Math.abs(_snowmanxxxxxx.u() - _snowman.u()) < _snowman && Math.abs(_snowmanxxxxxx.w() - _snowman.w()) < _snowman && _snowman.w(_snowmanxxxxxx) && _snowman.w(_snowmanxxxxxx.c()) && b(_snowman, _snowmanxxxxxx, _snowmanxxxxx.f())) {
               _snowmanxx = true;
               _snowman.a(_snowmanxxxxxx, _snowman.a(_snowman, _snowmanxxxxxx), 2);
               _snowman.a(_snowmanxxxxxx.a(_snowmanxxxxx.f()), _snowman.a(_snowman, _snowmanxxxxxx.a(_snowmanxxxxx.f())), 2);
               a(_snowman, _snowmanxxxxxx, _snowman, _snowman, _snowman, _snowman + 1);
            }
         }
      }

      if (!_snowmanxx) {
         _snowman.a(_snowman.b(_snowmanx), bup.iy.n().a(a, Integer.valueOf(5)), 2);
      }
   }

   @Override
   public void a(brx var1, ceh var2, dcj var3, bgm var4) {
      if (_snowman.X().a(aee.f)) {
         fx _snowman = _snowman.a();
         _snowman.a(_snowman, true, _snowman);
      }
   }
}
