import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public interface brc {
   @Nullable
   ccj c(fx var1);

   ceh d_(fx var1);

   cux b(fx var1);

   default int g(fx var1) {
      return this.d_(_snowman).f();
   }

   default int K() {
      return 15;
   }

   default int L() {
      return 256;
   }

   default Stream<ceh> a(dci var1) {
      return fx.a(_snowman).map(this::d_);
   }

   default dcj a(brf var1) {
      return a(_snowman, (var1x, var2) -> {
         ceh _snowman = this.d_(var2);
         cux _snowmanx = this.b(var2);
         dcn _snowmanxx = var1x.b();
         dcn _snowmanxxx = var1x.a();
         ddh _snowmanxxxx = var1x.a(_snowman, this, var2);
         dcj _snowmanxxxxx = this.a(_snowmanxx, _snowmanxxx, var2, _snowmanxxxx, _snowman);
         ddh _snowmanxxxxxx = var1x.a(_snowmanx, this, var2);
         dcj _snowmanxxxxxxx = _snowmanxxxxxx.a(_snowmanxx, _snowmanxxx, var2);
         double _snowmanxxxxxxxx = _snowmanxxxxx == null ? Double.MAX_VALUE : var1x.b().g(_snowmanxxxxx.e());
         double _snowmanxxxxxxxxx = _snowmanxxxxxxx == null ? Double.MAX_VALUE : var1x.b().g(_snowmanxxxxxxx.e());
         return _snowmanxxxxxxxx <= _snowmanxxxxxxxxx ? _snowmanxxxxx : _snowmanxxxxxxx;
      }, var0 -> {
         dcn _snowman = var0.b().d(var0.a());
         return dcj.a(var0.a(), gc.a(_snowman.b, _snowman.c, _snowman.d), new fx(var0.a()));
      });
   }

   @Nullable
   default dcj a(dcn var1, dcn var2, fx var3, ddh var4, ceh var5) {
      dcj _snowman = _snowman.a(_snowman, _snowman, _snowman);
      if (_snowman != null) {
         dcj _snowmanx = _snowman.m(this, _snowman).a(_snowman, _snowman, _snowman);
         if (_snowmanx != null && _snowmanx.e().d(_snowman).g() < _snowman.e().d(_snowman).g()) {
            return _snowman.a(_snowmanx.b());
         }
      }

      return _snowman;
   }

   default double a(ddh var1, Supplier<ddh> var2) {
      if (!_snowman.b()) {
         return _snowman.c(gc.a.b);
      } else {
         double _snowman = _snowman.get().c(gc.a.b);
         return _snowman >= 1.0 ? _snowman - 1.0 : Double.NEGATIVE_INFINITY;
      }
   }

   default double h(fx var1) {
      return this.a(this.d_(_snowman).k(this, _snowman), () -> {
         fx _snowman = _snowman.c();
         return this.d_(_snowman).k(this, _snowman);
      });
   }

   static <T> T a(brf var0, BiFunction<brf, fx, T> var1, Function<brf, T> var2) {
      dcn _snowman = _snowman.b();
      dcn _snowmanx = _snowman.a();
      if (_snowman.equals(_snowmanx)) {
         return _snowman.apply(_snowman);
      } else {
         double _snowmanxx = afm.d(-1.0E-7, _snowmanx.b, _snowman.b);
         double _snowmanxxx = afm.d(-1.0E-7, _snowmanx.c, _snowman.c);
         double _snowmanxxxx = afm.d(-1.0E-7, _snowmanx.d, _snowman.d);
         double _snowmanxxxxx = afm.d(-1.0E-7, _snowman.b, _snowmanx.b);
         double _snowmanxxxxxx = afm.d(-1.0E-7, _snowman.c, _snowmanx.c);
         double _snowmanxxxxxxx = afm.d(-1.0E-7, _snowman.d, _snowmanx.d);
         int _snowmanxxxxxxxx = afm.c(_snowmanxxxxx);
         int _snowmanxxxxxxxxx = afm.c(_snowmanxxxxxx);
         int _snowmanxxxxxxxxxx = afm.c(_snowmanxxxxxxx);
         fx.a _snowmanxxxxxxxxxxx = new fx.a(_snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx);
         T _snowmanxxxxxxxxxxxx = _snowman.apply(_snowman, _snowmanxxxxxxxxxxx);
         if (_snowmanxxxxxxxxxxxx != null) {
            return _snowmanxxxxxxxxxxxx;
         } else {
            double _snowmanxxxxxxxxxxxxx = _snowmanxx - _snowmanxxxxx;
            double _snowmanxxxxxxxxxxxxxx = _snowmanxxx - _snowmanxxxxxx;
            double _snowmanxxxxxxxxxxxxxxx = _snowmanxxxx - _snowmanxxxxxxx;
            int _snowmanxxxxxxxxxxxxxxxx = afm.k(_snowmanxxxxxxxxxxxxx);
            int _snowmanxxxxxxxxxxxxxxxxx = afm.k(_snowmanxxxxxxxxxxxxxx);
            int _snowmanxxxxxxxxxxxxxxxxxx = afm.k(_snowmanxxxxxxxxxxxxxxx);
            double _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx == 0 ? Double.MAX_VALUE : (double)_snowmanxxxxxxxxxxxxxxxx / _snowmanxxxxxxxxxxxxx;
            double _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx == 0 ? Double.MAX_VALUE : (double)_snowmanxxxxxxxxxxxxxxxxx / _snowmanxxxxxxxxxxxxxx;
            double _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxx == 0 ? Double.MAX_VALUE : (double)_snowmanxxxxxxxxxxxxxxxxxx / _snowmanxxxxxxxxxxxxxxx;
            double _snowmanxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxx * (_snowmanxxxxxxxxxxxxxxxx > 0 ? 1.0 - afm.h(_snowmanxxxxx) : afm.h(_snowmanxxxxx));
            double _snowmanxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxx * (_snowmanxxxxxxxxxxxxxxxxx > 0 ? 1.0 - afm.h(_snowmanxxxxxx) : afm.h(_snowmanxxxxxx));
            double _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxx * (_snowmanxxxxxxxxxxxxxxxxxx > 0 ? 1.0 - afm.h(_snowmanxxxxxxx) : afm.h(_snowmanxxxxxxx));

            while (_snowmanxxxxxxxxxxxxxxxxxxxxxx <= 1.0 || _snowmanxxxxxxxxxxxxxxxxxxxxxxx <= 1.0 || _snowmanxxxxxxxxxxxxxxxxxxxxxxxx <= 1.0) {
               if (_snowmanxxxxxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxxxxxxxxx) {
                  if (_snowmanxxxxxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxxxxxxxxxx) {
                     _snowmanxxxxxxxx += _snowmanxxxxxxxxxxxxxxxx;
                     _snowmanxxxxxxxxxxxxxxxxxxxxxx += _snowmanxxxxxxxxxxxxxxxxxxx;
                  } else {
                     _snowmanxxxxxxxxxx += _snowmanxxxxxxxxxxxxxxxxxx;
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxx += _snowmanxxxxxxxxxxxxxxxxxxxxx;
                  }
               } else if (_snowmanxxxxxxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxxxxxxxxxx) {
                  _snowmanxxxxxxxxx += _snowmanxxxxxxxxxxxxxxxxx;
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxx += _snowmanxxxxxxxxxxxxxxxxxxxx;
               } else {
                  _snowmanxxxxxxxxxx += _snowmanxxxxxxxxxxxxxxxxxx;
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxx += _snowmanxxxxxxxxxxxxxxxxxxxxx;
               }

               T _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.apply(_snowman, _snowmanxxxxxxxxxxx.d(_snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx));
               if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx != null) {
                  return _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx;
               }
            }

            return _snowman.apply(_snowman);
         }
      }
   }
}
