import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class cow extends cpb {
   public static final Codec<cow> a = RecordCodecBuilder.create(var0 -> a(var0).apply(var0, cow::new));

   public cow(int var1, int var2, int var3) {
      super(_snowman, _snowman, _snowman);
   }

   @Override
   protected cpc<?> a() {
      return cpc.f;
   }

   @Override
   public List<cnl.b> a(bsb var1, Random var2, int var3, fx var4, Set<fx> var5, cra var6, cmz var7) {
      int _snowman = 5;
      int _snowmanx = _snowman + 2;
      int _snowmanxx = afm.c((double)_snowmanx * 0.618);
      if (!_snowman.e) {
         a(_snowman, _snowman.c());
      }

      double _snowmanxxx = 1.0;
      int _snowmanxxxx = Math.min(1, afm.c(1.382 + Math.pow(1.0 * (double)_snowmanx / 13.0, 2.0)));
      int _snowmanxxxxx = _snowman.v() + _snowmanxx;
      int _snowmanxxxxxx = _snowmanx - 5;
      List<cow.a> _snowmanxxxxxxx = Lists.newArrayList();
      _snowmanxxxxxxx.add(new cow.a(_snowman.b(_snowmanxxxxxx), _snowmanxxxxx));

      for (; _snowmanxxxxxx >= 0; _snowmanxxxxxx--) {
         float _snowmanxxxxxxxx = this.b(_snowmanx, _snowmanxxxxxx);
         if (!(_snowmanxxxxxxxx < 0.0F)) {
            for (int _snowmanxxxxxxxxx = 0; _snowmanxxxxxxxxx < _snowmanxxxx; _snowmanxxxxxxxxx++) {
               double _snowmanxxxxxxxxxx = 1.0;
               double _snowmanxxxxxxxxxxx = 1.0 * (double)_snowmanxxxxxxxx * ((double)_snowman.nextFloat() + 0.328);
               double _snowmanxxxxxxxxxxxx = (double)(_snowman.nextFloat() * 2.0F) * Math.PI;
               double _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx * Math.sin(_snowmanxxxxxxxxxxxx) + 0.5;
               double _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx * Math.cos(_snowmanxxxxxxxxxxxx) + 0.5;
               fx _snowmanxxxxxxxxxxxxxxx = _snowman.a(_snowmanxxxxxxxxxxxxx, (double)(_snowmanxxxxxx - 1), _snowmanxxxxxxxxxxxxxx);
               fx _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx.b(5);
               if (this.a(_snowman, _snowman, _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx, false, _snowman, _snowman, _snowman)) {
                  int _snowmanxxxxxxxxxxxxxxxxx = _snowman.u() - _snowmanxxxxxxxxxxxxxxx.u();
                  int _snowmanxxxxxxxxxxxxxxxxxx = _snowman.w() - _snowmanxxxxxxxxxxxxxxx.w();
                  double _snowmanxxxxxxxxxxxxxxxxxxx = (double)_snowmanxxxxxxxxxxxxxxx.v()
                     - Math.sqrt((double)(_snowmanxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxx)) * 0.381;
                  int _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxx > (double)_snowmanxxxxx ? _snowmanxxxxx : (int)_snowmanxxxxxxxxxxxxxxxxxxx;
                  fx _snowmanxxxxxxxxxxxxxxxxxxxxx = new fx(_snowman.u(), _snowmanxxxxxxxxxxxxxxxxxxxx, _snowman.w());
                  if (this.a(_snowman, _snowman, _snowmanxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, false, _snowman, _snowman, _snowman)) {
                     _snowmanxxxxxxx.add(new cow.a(_snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxx.v()));
                  }
               }
            }
         }
      }

      this.a(_snowman, _snowman, _snowman, _snowman.b(_snowmanxx), true, _snowman, _snowman, _snowman);
      this.a(_snowman, _snowman, _snowmanx, _snowman, _snowmanxxxxxxx, _snowman, _snowman, _snowman);
      List<cnl.b> _snowmanxxxxxxxx = Lists.newArrayList();

      for (cow.a _snowmanxxxxxxxxxx : _snowmanxxxxxxx) {
         if (this.a(_snowmanx, _snowmanxxxxxxxxxx.a() - _snowman.v())) {
            _snowmanxxxxxxxx.add(_snowmanxxxxxxxxxx.a);
         }
      }

      return _snowmanxxxxxxxx;
   }

   private boolean a(bsb var1, Random var2, fx var3, fx var4, boolean var5, Set<fx> var6, cra var7, cmz var8) {
      if (!_snowman && Objects.equals(_snowman, _snowman)) {
         return true;
      } else {
         fx _snowman = _snowman.b(-_snowman.u(), -_snowman.v(), -_snowman.w());
         int _snowmanx = this.a(_snowman);
         float _snowmanxx = (float)_snowman.u() / (float)_snowmanx;
         float _snowmanxxx = (float)_snowman.v() / (float)_snowmanx;
         float _snowmanxxxx = (float)_snowman.w() / (float)_snowmanx;

         for (int _snowmanxxxxx = 0; _snowmanxxxxx <= _snowmanx; _snowmanxxxxx++) {
            fx _snowmanxxxxxx = _snowman.a((double)(0.5F + (float)_snowmanxxxxx * _snowmanxx), (double)(0.5F + (float)_snowmanxxxxx * _snowmanxxx), (double)(0.5F + (float)_snowmanxxxxx * _snowmanxxxx));
            if (_snowman) {
               a(_snowman, _snowmanxxxxxx, _snowman.b.a(_snowman, _snowmanxxxxxx).a(bzl.e, this.a(_snowman, _snowmanxxxxxx)), _snowman);
               _snowman.add(_snowmanxxxxxx.h());
            } else if (!cld.c(_snowman, _snowmanxxxxxx)) {
               return false;
            }
         }

         return true;
      }
   }

   private int a(fx var1) {
      int _snowman = afm.a(_snowman.u());
      int _snowmanx = afm.a(_snowman.v());
      int _snowmanxx = afm.a(_snowman.w());
      return Math.max(_snowman, Math.max(_snowmanx, _snowmanxx));
   }

   private gc.a a(fx var1, fx var2) {
      gc.a _snowman = gc.a.b;
      int _snowmanx = Math.abs(_snowman.u() - _snowman.u());
      int _snowmanxx = Math.abs(_snowman.w() - _snowman.w());
      int _snowmanxxx = Math.max(_snowmanx, _snowmanxx);
      if (_snowmanxxx > 0) {
         if (_snowmanx == _snowmanxxx) {
            _snowman = gc.a.a;
         } else {
            _snowman = gc.a.c;
         }
      }

      return _snowman;
   }

   private boolean a(int var1, int var2) {
      return (double)_snowman >= (double)_snowman * 0.2;
   }

   private void a(bsb var1, Random var2, int var3, fx var4, List<cow.a> var5, Set<fx> var6, cra var7, cmz var8) {
      for (cow.a _snowman : _snowman) {
         int _snowmanx = _snowman.a();
         fx _snowmanxx = new fx(_snowman.u(), _snowmanx, _snowman.w());
         if (!_snowmanxx.equals(_snowman.a.a()) && this.a(_snowman, _snowmanx - _snowman.v())) {
            this.a(_snowman, _snowman, _snowmanxx, _snowman.a.a(), true, _snowman, _snowman, _snowman);
         }
      }
   }

   private float b(int var1, int var2) {
      if ((float)_snowman < (float)_snowman * 0.3F) {
         return -1.0F;
      } else {
         float _snowman = (float)_snowman / 2.0F;
         float _snowmanx = _snowman - (float)_snowman;
         float _snowmanxx = afm.c(_snowman * _snowman - _snowmanx * _snowmanx);
         if (_snowmanx == 0.0F) {
            _snowmanxx = _snowman;
         } else if (Math.abs(_snowmanx) >= _snowman) {
            return 0.0F;
         }

         return _snowmanxx * 0.5F;
      }
   }

   static class a {
      private final cnl.b a;
      private final int b;

      public a(fx var1, int var2) {
         this.a = new cnl.b(_snowman, 0, false);
         this.b = _snowman;
      }

      public int a() {
         return this.b;
      }
   }
}
