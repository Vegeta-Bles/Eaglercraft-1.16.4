import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.world.level.ColorResolver;

public interface brz extends bra, brg, bsx.a {
   @Nullable
   cfw a(int var1, int var2, cga var3, boolean var4);

   @Deprecated
   boolean b(int var1, int var2);

   int a(chn.a var1, int var2, int var3);

   int c();

   bsx d();

   default bsv v(fx var1) {
      return this.d().a(_snowman);
   }

   default Stream<ceh> c(dci var1) {
      int _snowman = afm.c(_snowman.a);
      int _snowmanx = afm.c(_snowman.d);
      int _snowmanxx = afm.c(_snowman.b);
      int _snowmanxxx = afm.c(_snowman.e);
      int _snowmanxxxx = afm.c(_snowman.c);
      int _snowmanxxxxx = afm.c(_snowman.f);
      return this.a(_snowman, _snowmanxx, _snowmanxxxx, _snowmanx, _snowmanxxx, _snowmanxxxxx) ? this.a(_snowman) : Stream.empty();
   }

   @Override
   default int a(fx var1, ColorResolver var2) {
      return _snowman.getColor(this.v(_snowman), (double)_snowman.u(), (double)_snowman.w());
   }

   @Override
   default bsv b(int var1, int var2, int var3) {
      cfw _snowman = this.a(_snowman >> 2, _snowman >> 2, cga.d, false);
      return _snowman != null && _snowman.i() != null ? _snowman.i().b(_snowman, _snowman, _snowman) : this.a(_snowman, _snowman, _snowman);
   }

   bsv a(int var1, int var2, int var3);

   boolean s_();

   @Deprecated
   int t_();

   chd k();

   default fx a(chn.a var1, fx var2) {
      return new fx(_snowman.u(), this.a(_snowman, _snowman.u(), _snowman.w()), _snowman.w());
   }

   default boolean w(fx var1) {
      return this.d_(_snowman).g();
   }

   default boolean x(fx var1) {
      if (_snowman.v() >= this.t_()) {
         return this.e(_snowman);
      } else {
         fx _snowman = new fx(_snowman.u(), this.t_(), _snowman.w());
         if (!this.e(_snowman)) {
            return false;
         } else {
            for (fx var4 = _snowman.c(); var4.v() > _snowman.v(); var4 = var4.c()) {
               ceh _snowmanx = this.d_(var4);
               if (_snowmanx.b(this, var4) > 0 && !_snowmanx.c().a()) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   @Deprecated
   default float y(fx var1) {
      return this.k().a(this.B(_snowman));
   }

   default int c(fx var1, gc var2) {
      return this.d_(_snowman).c(this, _snowman, _snowman);
   }

   default cfw z(fx var1) {
      return this.a(_snowman.u() >> 4, _snowman.w() >> 4);
   }

   default cfw a(int var1, int var2) {
      return this.a(_snowman, _snowman, cga.m, true);
   }

   default cfw a(int var1, int var2, cga var3) {
      return this.a(_snowman, _snowman, _snowman, true);
   }

   @Nullable
   @Override
   default brc c(int var1, int var2) {
      return this.a(_snowman, _snowman, cga.a, false);
   }

   default boolean A(fx var1) {
      return this.b(_snowman).a(aef.b);
   }

   default boolean d(dci var1) {
      int _snowman = afm.c(_snowman.a);
      int _snowmanx = afm.f(_snowman.d);
      int _snowmanxx = afm.c(_snowman.b);
      int _snowmanxxx = afm.f(_snowman.e);
      int _snowmanxxxx = afm.c(_snowman.c);
      int _snowmanxxxxx = afm.f(_snowman.f);
      fx.a _snowmanxxxxxx = new fx.a();

      for (int _snowmanxxxxxxx = _snowman; _snowmanxxxxxxx < _snowmanx; _snowmanxxxxxxx++) {
         for (int _snowmanxxxxxxxx = _snowmanxx; _snowmanxxxxxxxx < _snowmanxxx; _snowmanxxxxxxxx++) {
            for (int _snowmanxxxxxxxxx = _snowmanxxxx; _snowmanxxxxxxxxx < _snowmanxxxxx; _snowmanxxxxxxxxx++) {
               ceh _snowmanxxxxxxxxxx = this.d_(_snowmanxxxxxx.d(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx));
               if (!_snowmanxxxxxxxxxx.m().c()) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   default int B(fx var1) {
      return this.c(_snowman, this.c());
   }

   default int c(fx var1, int var2) {
      return _snowman.u() >= -30000000 && _snowman.w() >= -30000000 && _snowman.u() < 30000000 && _snowman.w() < 30000000 ? this.b(_snowman, _snowman) : 15;
   }

   @Deprecated
   default boolean C(fx var1) {
      return this.b(_snowman.u() >> 4, _snowman.w() >> 4);
   }

   @Deprecated
   default boolean a(fx var1, fx var2) {
      return this.a(_snowman.u(), _snowman.v(), _snowman.w(), _snowman.u(), _snowman.v(), _snowman.w());
   }

   @Deprecated
   default boolean a(int var1, int var2, int var3, int var4, int var5, int var6) {
      if (_snowman >= 0 && _snowman < 256) {
         _snowman >>= 4;
         _snowman >>= 4;
         _snowman >>= 4;
         _snowman >>= 4;

         for (int _snowman = _snowman; _snowman <= _snowman; _snowman++) {
            for (int _snowmanx = _snowman; _snowmanx <= _snowman; _snowmanx++) {
               if (!this.b(_snowman, _snowmanx)) {
                  return false;
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }
}
