import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import java.util.Iterator;
import java.util.Objects;
import javax.annotation.Nullable;

public class asp extends arv<aqm> {
   @Nullable
   private cxb b;
   private int c;

   public asp() {
      super(ImmutableMap.of(ayd.t, aye.a, ayd.v, aye.c));
   }

   @Override
   protected boolean a(aag var1, aqm var2) {
      cxd _snowman = _snowman.cJ().c(ayd.t).get();
      if (!_snowman.b() && !_snowman.c()) {
         if (!Objects.equals(this.b, _snowman.h())) {
            this.c = 20;
            return true;
         } else {
            if (this.c > 0) {
               this.c--;
            }

            return this.c == 0;
         }
      } else {
         return false;
      }
   }

   @Override
   protected void a(aag var1, aqm var2, long var3) {
      cxd _snowman = _snowman.cJ().c(ayd.t).get();
      this.b = _snowman.h();
      cxb _snowmanx = _snowman.i();
      cxb _snowmanxx = _snowman.h();
      fx _snowmanxxx = _snowmanx.a();
      ceh _snowmanxxxx = _snowman.d_(_snowmanxxx);
      if (_snowmanxxxx.a(aed.h)) {
         bwb _snowmanxxxxx = (bwb)_snowmanxxxx.b();
         if (!_snowmanxxxxx.h(_snowmanxxxx)) {
            _snowmanxxxxx.a(_snowman, _snowmanxxxx, _snowmanxxx, true);
         }

         this.c(_snowman, _snowman, _snowmanxxx);
      }

      fx _snowmanxxxxx = _snowmanxx.a();
      ceh _snowmanxxxxxx = _snowman.d_(_snowmanxxxxx);
      if (_snowmanxxxxxx.a(aed.h)) {
         bwb _snowmanxxxxxxx = (bwb)_snowmanxxxxxx.b();
         if (!_snowmanxxxxxxx.h(_snowmanxxxxxx)) {
            _snowmanxxxxxxx.a(_snowman, _snowmanxxxxxx, _snowmanxxxxx, true);
            this.c(_snowman, _snowman, _snowmanxxxxx);
         }
      }

      a(_snowman, _snowman, _snowmanx, _snowmanxx);
   }

   public static void a(aag var0, aqm var1, @Nullable cxb var2, @Nullable cxb var3) {
      arf<?> _snowman = _snowman.cJ();
      if (_snowman.a(ayd.v)) {
         Iterator<gf> _snowmanx = _snowman.c(ayd.v).get().iterator();

         while (_snowmanx.hasNext()) {
            gf _snowmanxx = _snowmanx.next();
            fx _snowmanxxx = _snowmanxx.b();
            if ((_snowman == null || !_snowman.a().equals(_snowmanxxx)) && (_snowman == null || !_snowman.a().equals(_snowmanxxx))) {
               if (a(_snowman, _snowman, _snowmanxx)) {
                  _snowmanx.remove();
               } else {
                  ceh _snowmanxxxx = _snowman.d_(_snowmanxxx);
                  if (!_snowmanxxxx.a(aed.h)) {
                     _snowmanx.remove();
                  } else {
                     bwb _snowmanxxxxx = (bwb)_snowmanxxxx.b();
                     if (!_snowmanxxxxx.h(_snowmanxxxx)) {
                        _snowmanx.remove();
                     } else if (a(_snowman, _snowman, _snowmanxxx)) {
                        _snowmanx.remove();
                     } else {
                        _snowmanxxxxx.a(_snowman, _snowmanxxxx, _snowmanxxx, false);
                        _snowmanx.remove();
                     }
                  }
               }
            }
         }
      }
   }

   private static boolean a(aag var0, aqm var1, fx var2) {
      arf<?> _snowman = _snowman.cJ();
      return !_snowman.a(ayd.g)
         ? false
         : _snowman.c(ayd.g).get().stream().filter(var1x -> var1x.X() == _snowman.X()).filter(var1x -> _snowman.a(var1x.cA(), 2.0)).anyMatch(var2x -> b(_snowman, var2x, _snowman));
   }

   private static boolean b(aag var0, aqm var1, fx var2) {
      if (!_snowman.cJ().a(ayd.t)) {
         return false;
      } else {
         cxd _snowman = _snowman.cJ().c(ayd.t).get();
         if (_snowman.c()) {
            return false;
         } else {
            cxb _snowmanx = _snowman.i();
            if (_snowmanx == null) {
               return false;
            } else {
               cxb _snowmanxx = _snowman.h();
               return _snowman.equals(_snowmanx.a()) || _snowman.equals(_snowmanxx.a());
            }
         }
      }
   }

   private static boolean a(aag var0, aqm var1, gf var2) {
      return _snowman.a() != _snowman.Y() || !_snowman.b().a(_snowman.cA(), 2.0);
   }

   private void c(aag var1, aqm var2, fx var3) {
      arf<?> _snowman = _snowman.cJ();
      gf _snowmanx = gf.a(_snowman.Y(), _snowman);
      if (_snowman.c(ayd.v).isPresent()) {
         _snowman.c(ayd.v).get().add(_snowmanx);
      } else {
         _snowman.a(ayd.v, Sets.newHashSet(new gf[]{_snowmanx}));
      }
   }
}
