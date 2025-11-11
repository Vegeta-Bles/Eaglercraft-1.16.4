import com.mojang.serialization.Codec;
import java.util.Random;

public class cjv extends cjl<cls> {
   public cjv(Codec<cls> var1) {
      super(_snowman);
   }

   public boolean a(bsr var1, cfy var2, Random var3, fx var4, cls var5) {
      _snowman = new fx(_snowman.u(), _snowman.f(), _snowman.w());
      boolean _snowman = _snowman.nextDouble() > 0.7;
      ceh _snowmanx = _snowman.b;
      double _snowmanxx = _snowman.nextDouble() * 2.0 * Math.PI;
      int _snowmanxxx = 11 - _snowman.nextInt(5);
      int _snowmanxxxx = 3 + _snowman.nextInt(3);
      boolean _snowmanxxxxx = _snowman.nextDouble() > 0.7;
      int _snowmanxxxxxx = 11;
      int _snowmanxxxxxxx = _snowmanxxxxx ? _snowman.nextInt(6) + 6 : _snowman.nextInt(15) + 3;
      if (!_snowmanxxxxx && _snowman.nextDouble() > 0.9) {
         _snowmanxxxxxxx += _snowman.nextInt(19) + 7;
      }

      int _snowmanxxxxxxxx = Math.min(_snowmanxxxxxxx + _snowman.nextInt(11), 18);
      int _snowmanxxxxxxxxx = Math.min(_snowmanxxxxxxx + _snowman.nextInt(7) - _snowman.nextInt(5), 11);
      int _snowmanxxxxxxxxxx = _snowmanxxxxx ? _snowmanxxx : 11;

      for (int _snowmanxxxxxxxxxxx = -_snowmanxxxxxxxxxx; _snowmanxxxxxxxxxxx < _snowmanxxxxxxxxxx; _snowmanxxxxxxxxxxx++) {
         for (int _snowmanxxxxxxxxxxxx = -_snowmanxxxxxxxxxx; _snowmanxxxxxxxxxxxx < _snowmanxxxxxxxxxx; _snowmanxxxxxxxxxxxx++) {
            for (int _snowmanxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxx < _snowmanxxxxxxx; _snowmanxxxxxxxxxxxxx++) {
               int _snowmanxxxxxxxxxxxxxx = _snowmanxxxxx ? this.b(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxxx) : this.a(_snowman, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxxx);
               if (_snowmanxxxxx || _snowmanxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxx) {
                  this.a(_snowman, _snowman, _snowman, _snowmanxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxx, _snowmanxxxx, _snowmanxx, _snowman, _snowmanx);
               }
            }
         }
      }

      this.a(_snowman, _snowman, _snowmanxxxxxxxxx, _snowmanxxxxxxx, _snowmanxxxxx, _snowmanxxx);

      for (int _snowmanxxxxxxxxxxx = -_snowmanxxxxxxxxxx; _snowmanxxxxxxxxxxx < _snowmanxxxxxxxxxx; _snowmanxxxxxxxxxxx++) {
         for (int _snowmanxxxxxxxxxxxx = -_snowmanxxxxxxxxxx; _snowmanxxxxxxxxxxxx < _snowmanxxxxxxxxxx; _snowmanxxxxxxxxxxxx++) {
            for (int _snowmanxxxxxxxxxxxxxx = -1; _snowmanxxxxxxxxxxxxxx > -_snowmanxxxxxxxx; _snowmanxxxxxxxxxxxxxx--) {
               int _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxx
                  ? afm.f((float)_snowmanxxxxxxxxxx * (1.0F - (float)Math.pow((double)_snowmanxxxxxxxxxxxxxx, 2.0) / ((float)_snowmanxxxxxxxx * 8.0F)))
                  : _snowmanxxxxxxxxxx;
               int _snowmanxxxxxxxxxxxxxxxx = this.b(_snowman, -_snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx);
               if (_snowmanxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxx) {
                  this.a(_snowman, _snowman, _snowman, _snowmanxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxx, _snowmanxxxx, _snowmanxx, _snowman, _snowmanx);
               }
            }
         }
      }

      boolean _snowmanxxxxxxxxxxx = _snowmanxxxxx ? _snowman.nextDouble() > 0.1 : _snowman.nextDouble() > 0.7;
      if (_snowmanxxxxxxxxxxx) {
         this.a(_snowman, _snowman, _snowmanxxxxxxxxx, _snowmanxxxxxxx, _snowman, _snowmanxxxxx, _snowmanxxx, _snowmanxx, _snowmanxxxx);
      }

      return true;
   }

   private void a(Random var1, bry var2, int var3, int var4, fx var5, boolean var6, int var7, double var8, int var10) {
      int _snowman = _snowman.nextBoolean() ? -1 : 1;
      int _snowmanx = _snowman.nextBoolean() ? -1 : 1;
      int _snowmanxx = _snowman.nextInt(Math.max(_snowman / 2 - 2, 1));
      if (_snowman.nextBoolean()) {
         _snowmanxx = _snowman / 2 + 1 - _snowman.nextInt(Math.max(_snowman - _snowman / 2 - 1, 1));
      }

      int _snowmanxxx = _snowman.nextInt(Math.max(_snowman / 2 - 2, 1));
      if (_snowman.nextBoolean()) {
         _snowmanxxx = _snowman / 2 + 1 - _snowman.nextInt(Math.max(_snowman - _snowman / 2 - 1, 1));
      }

      if (_snowman) {
         _snowmanxx = _snowmanxxx = _snowman.nextInt(Math.max(_snowman - 5, 1));
      }

      fx _snowmanxxxx = new fx(_snowman * _snowmanxx, 0, _snowmanx * _snowmanxxx);
      double _snowmanxxxxx = _snowman ? _snowman + (Math.PI / 2) : _snowman.nextDouble() * 2.0 * Math.PI;

      for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < _snowman - 3; _snowmanxxxxxx++) {
         int _snowmanxxxxxxx = this.a(_snowman, _snowmanxxxxxx, _snowman, _snowman);
         this.a(_snowmanxxxxxxx, _snowmanxxxxxx, _snowman, _snowman, false, _snowmanxxxxx, _snowmanxxxx, _snowman, _snowman);
      }

      for (int _snowmanxxxxxx = -1; _snowmanxxxxxx > -_snowman + _snowman.nextInt(5); _snowmanxxxxxx--) {
         int _snowmanxxxxxxx = this.b(_snowman, -_snowmanxxxxxx, _snowman, _snowman);
         this.a(_snowmanxxxxxxx, _snowmanxxxxxx, _snowman, _snowman, true, _snowmanxxxxx, _snowmanxxxx, _snowman, _snowman);
      }
   }

   private void a(int var1, int var2, fx var3, bry var4, boolean var5, double var6, fx var8, int var9, int var10) {
      int _snowman = _snowman + 1 + _snowman / 3;
      int _snowmanx = Math.min(_snowman - 3, 3) + _snowman / 2 - 1;

      for (int _snowmanxx = -_snowman; _snowmanxx < _snowman; _snowmanxx++) {
         for (int _snowmanxxx = -_snowman; _snowmanxxx < _snowman; _snowmanxxx++) {
            double _snowmanxxxx = this.a(_snowmanxx, _snowmanxxx, _snowman, _snowman, _snowmanx, _snowman);
            if (_snowmanxxxx < 0.0) {
               fx _snowmanxxxxx = _snowman.b(_snowmanxx, _snowman, _snowmanxxx);
               buo _snowmanxxxxxx = _snowman.d_(_snowmanxxxxx).b();
               if (this.c(_snowmanxxxxxx) || _snowmanxxxxxx == bup.cE) {
                  if (_snowman) {
                     this.a(_snowman, _snowmanxxxxx, bup.A.n());
                  } else {
                     this.a(_snowman, _snowmanxxxxx, bup.a.n());
                     this.a(_snowman, _snowmanxxxxx);
                  }
               }
            }
         }
      }
   }

   private void a(bry var1, fx var2) {
      if (_snowman.d_(_snowman.b()).a(bup.cC)) {
         this.a(_snowman, _snowman.b(), bup.a.n());
      }
   }

   private void a(
      bry var1,
      Random var2,
      fx var3,
      int var4,
      int var5,
      int var6,
      int var7,
      int var8,
      int var9,
      boolean var10,
      int var11,
      double var12,
      boolean var14,
      ceh var15
   ) {
      double _snowman = _snowman ? this.a(_snowman, _snowman, fx.b, _snowman, this.a(_snowman, _snowman, _snowman), _snowman) : this.a(_snowman, _snowman, fx.b, _snowman, _snowman);
      if (_snowman < 0.0) {
         fx _snowmanx = _snowman.b(_snowman, _snowman, _snowman);
         double _snowmanxx = _snowman ? -0.5 : (double)(-6 - _snowman.nextInt(3));
         if (_snowman > _snowmanxx && _snowman.nextDouble() > 0.9) {
            return;
         }

         this.a(_snowmanx, _snowman, _snowman, _snowman - _snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }

   private void a(fx var1, bry var2, Random var3, int var4, int var5, boolean var6, boolean var7, ceh var8) {
      ceh _snowman = _snowman.d_(_snowman);
      if (_snowman.c() == cva.a || _snowman.a(bup.cE) || _snowman.a(bup.cD) || _snowman.a(bup.A)) {
         boolean _snowmanx = !_snowman || _snowman.nextDouble() > 0.05;
         int _snowmanxx = _snowman ? 3 : 2;
         if (_snowman && !_snowman.a(bup.A) && (double)_snowman <= (double)_snowman.nextInt(Math.max(1, _snowman / _snowmanxx)) + (double)_snowman * 0.6 && _snowmanx) {
            this.a(_snowman, _snowman, bup.cE.n());
         } else {
            this.a(_snowman, _snowman, _snowman);
         }
      }
   }

   private int a(int var1, int var2, int var3) {
      int _snowman = _snowman;
      if (_snowman > 0 && _snowman - _snowman <= 3) {
         _snowman = _snowman - (4 - (_snowman - _snowman));
      }

      return _snowman;
   }

   private double a(int var1, int var2, fx var3, int var4, Random var5) {
      float _snowman = 10.0F * afm.a(_snowman.nextFloat(), 0.2F, 0.8F) / (float)_snowman;
      return (double)_snowman + Math.pow((double)(_snowman - _snowman.u()), 2.0) + Math.pow((double)(_snowman - _snowman.w()), 2.0) - Math.pow((double)_snowman, 2.0);
   }

   private double a(int var1, int var2, fx var3, int var4, int var5, double var6) {
      return Math.pow(((double)(_snowman - _snowman.u()) * Math.cos(_snowman) - (double)(_snowman - _snowman.w()) * Math.sin(_snowman)) / (double)_snowman, 2.0)
         + Math.pow(((double)(_snowman - _snowman.u()) * Math.sin(_snowman) + (double)(_snowman - _snowman.w()) * Math.cos(_snowman)) / (double)_snowman, 2.0)
         - 1.0;
   }

   private int a(Random var1, int var2, int var3, int var4) {
      float _snowman = 3.5F - _snowman.nextFloat();
      float _snowmanx = (1.0F - (float)Math.pow((double)_snowman, 2.0) / ((float)_snowman * _snowman)) * (float)_snowman;
      if (_snowman > 15 + _snowman.nextInt(5)) {
         int _snowmanxx = _snowman < 3 + _snowman.nextInt(6) ? _snowman / 2 : _snowman;
         _snowmanx = (1.0F - (float)_snowmanxx / ((float)_snowman * _snowman * 0.4F)) * (float)_snowman;
      }

      return afm.f(_snowmanx / 2.0F);
   }

   private int b(int var1, int var2, int var3) {
      float _snowman = 1.0F;
      float _snowmanx = (1.0F - (float)Math.pow((double)_snowman, 2.0) / ((float)_snowman * 1.0F)) * (float)_snowman;
      return afm.f(_snowmanx / 2.0F);
   }

   private int b(Random var1, int var2, int var3, int var4) {
      float _snowman = 1.0F + _snowman.nextFloat() / 2.0F;
      float _snowmanx = (1.0F - (float)_snowman / ((float)_snowman * _snowman)) * (float)_snowman;
      return afm.f(_snowmanx / 2.0F);
   }

   private boolean c(buo var1) {
      return _snowman == bup.gT || _snowman == bup.cE || _snowman == bup.kV;
   }

   private boolean a(brc var1, fx var2) {
      return _snowman.d_(_snowman.c()).c() == cva.a;
   }

   private void a(bry var1, fx var2, int var3, int var4, boolean var5, int var6) {
      int _snowman = _snowman ? _snowman : _snowman / 2;

      for (int _snowmanx = -_snowman; _snowmanx <= _snowman; _snowmanx++) {
         for (int _snowmanxx = -_snowman; _snowmanxx <= _snowman; _snowmanxx++) {
            for (int _snowmanxxx = 0; _snowmanxxx <= _snowman; _snowmanxxx++) {
               fx _snowmanxxxx = _snowman.b(_snowmanx, _snowmanxxx, _snowmanxx);
               buo _snowmanxxxxx = _snowman.d_(_snowmanxxxx).b();
               if (this.c(_snowmanxxxxx) || _snowmanxxxxx == bup.cC) {
                  if (this.a((brc)_snowman, _snowmanxxxx)) {
                     this.a(_snowman, _snowmanxxxx, bup.a.n());
                     this.a(_snowman, _snowmanxxxx.b(), bup.a.n());
                  } else if (this.c(_snowmanxxxxx)) {
                     buo[] _snowmanxxxxxx = new buo[]{_snowman.d_(_snowmanxxxx.f()).b(), _snowman.d_(_snowmanxxxx.g()).b(), _snowman.d_(_snowmanxxxx.d()).b(), _snowman.d_(_snowmanxxxx.e()).b()};
                     int _snowmanxxxxxxx = 0;

                     for (buo _snowmanxxxxxxxx : _snowmanxxxxxx) {
                        if (!this.c(_snowmanxxxxxxxx)) {
                           _snowmanxxxxxxx++;
                        }
                     }

                     if (_snowmanxxxxxxx >= 3) {
                        this.a(_snowman, _snowmanxxxx, bup.a.n());
                     }
                  }
               }
            }
         }
      }
   }
}
