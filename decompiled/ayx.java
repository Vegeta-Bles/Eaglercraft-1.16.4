import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ayx extends azb<aqm> {
   public ayx() {
   }

   @Override
   public Set<ayd<?>> a() {
      return ImmutableSet.of(ayd.h, ayd.g, ayd.K, ayd.W, ayd.ae, ayd.U, new ayd[]{ayd.V, ayd.Y, ayd.X, ayd.ac, ayd.ad, ayd.ag});
   }

   @Override
   protected void a(aag var1, aqm var2) {
      arf<?> _snowman = _snowman.cJ();
      _snowman.a(ayd.ag, c(_snowman, _snowman));
      Optional<aqn> _snowmanx = Optional.empty();
      Optional<bem> _snowmanxx = Optional.empty();
      Optional<bem> _snowmanxxx = Optional.empty();
      Optional<bes> _snowmanxxxx = Optional.empty();
      Optional<aqm> _snowmanxxxxx = Optional.empty();
      Optional<bfw> _snowmanxxxxxx = Optional.empty();
      Optional<bfw> _snowmanxxxxxxx = Optional.empty();
      int _snowmanxxxxxxxx = 0;
      List<ber> _snowmanxxxxxxxxx = Lists.newArrayList();
      List<ber> _snowmanxxxxxxxxxx = Lists.newArrayList();

      for (aqm _snowmanxxxxxxxxxxx : _snowman.c(ayd.h).orElse(ImmutableList.of())) {
         if (_snowmanxxxxxxxxxxx instanceof bem) {
            bem _snowmanxxxxxxxxxxxx = (bem)_snowmanxxxxxxxxxxx;
            if (_snowmanxxxxxxxxxxxx.w_() && !_snowmanxxx.isPresent()) {
               _snowmanxxx = Optional.of(_snowmanxxxxxxxxxxxx);
            } else if (_snowmanxxxxxxxxxxxx.eL()) {
               _snowmanxxxxxxxx++;
               if (!_snowmanxx.isPresent() && _snowmanxxxxxxxxxxxx.eO()) {
                  _snowmanxx = Optional.of(_snowmanxxxxxxxxxxxx);
               }
            }
         } else if (_snowmanxxxxxxxxxxx instanceof bev) {
            _snowmanxxxxxxxxx.add((bev)_snowmanxxxxxxxxxxx);
         } else if (_snowmanxxxxxxxxxxx instanceof bes) {
            bes _snowmanxxxxxxxxxxxx = (bes)_snowmanxxxxxxxxxxx;
            if (_snowmanxxxxxxxxxxxx.w_() && !_snowmanxxxx.isPresent()) {
               _snowmanxxxx = Optional.of(_snowmanxxxxxxxxxxxx);
            } else if (_snowmanxxxxxxxxxxxx.eM()) {
               _snowmanxxxxxxxxx.add(_snowmanxxxxxxxxxxxx);
            }
         } else if (_snowmanxxxxxxxxxxx instanceof bfw) {
            bfw _snowmanxxxxxxxxxxxx = (bfw)_snowmanxxxxxxxxxxx;
            if (!_snowmanxxxxxx.isPresent() && aqd.f.test(_snowmanxxxxxxxxxxx) && !bet.a(_snowmanxxxxxxxxxxxx)) {
               _snowmanxxxxxx = Optional.of(_snowmanxxxxxxxxxxxx);
            }

            if (!_snowmanxxxxxxx.isPresent() && !_snowmanxxxxxxxxxxxx.a_() && bet.b(_snowmanxxxxxxxxxxxx)) {
               _snowmanxxxxxxx = Optional.of(_snowmanxxxxxxxxxxxx);
            }
         } else if (_snowmanx.isPresent() || !(_snowmanxxxxxxxxxxx instanceof beh) && !(_snowmanxxxxxxxxxxx instanceof bcl)) {
            if (!_snowmanxxxxx.isPresent() && bet.a(_snowmanxxxxxxxxxxx.X())) {
               _snowmanxxxxx = Optional.of(_snowmanxxxxxxxxxxx);
            }
         } else {
            _snowmanx = Optional.of((aqn)_snowmanxxxxxxxxxxx);
         }
      }

      for (aqm _snowmanxxxxxxxxxxxxx : _snowman.c(ayd.g).orElse(ImmutableList.of())) {
         if (_snowmanxxxxxxxxxxxxx instanceof ber && ((ber)_snowmanxxxxxxxxxxxxx).eM()) {
            _snowmanxxxxxxxxxx.add((ber)_snowmanxxxxxxxxxxxxx);
         }
      }

      _snowman.a(ayd.K, _snowmanx);
      _snowman.a(ayd.U, _snowmanxx);
      _snowman.a(ayd.V, _snowmanxxx);
      _snowman.a(ayd.ab, _snowmanxxxxx);
      _snowman.a(ayd.W, _snowmanxxxxxx);
      _snowman.a(ayd.ae, _snowmanxxxxxxx);
      _snowman.a(ayd.X, _snowmanxxxxxxxxxx);
      _snowman.a(ayd.Y, _snowmanxxxxxxxxx);
      _snowman.a(ayd.ac, _snowmanxxxxxxxxx.size());
      _snowman.a(ayd.ad, _snowmanxxxxxxxx);
   }

   private static Optional<fx> c(aag var0, aqm var1) {
      return fx.a(_snowman.cB(), 8, 4, var1x -> a(_snowman, var1x));
   }

   private static boolean a(aag var0, fx var1) {
      ceh _snowman = _snowman.d_(_snowman);
      boolean _snowmanx = _snowman.a(aed.P);
      return _snowmanx && _snowman.a(bup.mf) ? buy.g(_snowman) : _snowmanx;
   }
}
