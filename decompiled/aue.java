import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import java.util.stream.Collectors;

public class aue extends arv<bfj> {
   private Set<blx> b = ImmutableSet.of();

   public aue() {
      super(ImmutableMap.of(ayd.q, aye.a, ayd.h, aye.a));
   }

   protected boolean a(aag var1, bfj var2) {
      return arw.a(_snowman.cJ(), ayd.q, aqe.aP);
   }

   protected boolean a(aag var1, bfj var2, long var3) {
      return this.a(_snowman, _snowman);
   }

   protected void b(aag var1, bfj var2, long var3) {
      bfj _snowman = (bfj)_snowman.cJ().c(ayd.q).get();
      arw.a(_snowman, _snowman, 0.5F);
      this.b = a(_snowman, _snowman);
   }

   protected void c(aag var1, bfj var2, long var3) {
      bfj _snowman = (bfj)_snowman.cJ().c(ayd.q).get();
      if (!(_snowman.h(_snowman) > 5.0)) {
         arw.a(_snowman, _snowman, 0.5F);
         _snowman.a(_snowman, _snowman, _snowman);
         if (_snowman.fg() && (_snowman.eX().b() == bfm.f || _snowman.fh())) {
            a(_snowman, bfj.bp.keySet(), _snowman);
         }

         if (_snowman.eX().b() == bfm.f && _snowman.eU().a(bmd.kW) > bmd.kW.i() / 2) {
            a(_snowman, ImmutableSet.of(bmd.kW), _snowman);
         }

         if (!this.b.isEmpty() && _snowman.eU().a(this.b)) {
            a(_snowman, this.b, _snowman);
         }
      }
   }

   protected void d(aag var1, bfj var2, long var3) {
      _snowman.cJ().b(ayd.q);
   }

   private static Set<blx> a(bfj var0, bfj var1) {
      ImmutableSet<blx> _snowman = _snowman.eX().b().c();
      ImmutableSet<blx> _snowmanx = _snowman.eX().b().c();
      return _snowman.stream().filter(var1x -> !_snowman.contains(var1x)).collect(Collectors.toSet());
   }

   private static void a(bfj var0, Set<blx> var1, aqm var2) {
      apa _snowman = _snowman.eU();
      bmb _snowmanx = bmb.b;
      int _snowmanxx = 0;

      while (_snowmanxx < _snowman.Z_()) {
         bmb _snowmanxxx;
         blx _snowmanxxxx;
         int _snowmanxxxxx;
         label28: {
            _snowmanxxx = _snowman.a(_snowmanxx);
            if (!_snowmanxxx.a()) {
               _snowmanxxxx = _snowmanxxx.b();
               if (_snowman.contains(_snowmanxxxx)) {
                  if (_snowmanxxx.E() > _snowmanxxx.c() / 2) {
                     _snowmanxxxxx = _snowmanxxx.E() / 2;
                     break label28;
                  }

                  if (_snowmanxxx.E() > 24) {
                     _snowmanxxxxx = _snowmanxxx.E() - 24;
                     break label28;
                  }
               }
            }

            _snowmanxx++;
            continue;
         }

         _snowmanxxx.g(_snowmanxxxxx);
         _snowmanx = new bmb(_snowmanxxxx, _snowmanxxxxx);
         break;
      }

      if (!_snowmanx.a()) {
         arw.a(_snowman, _snowmanx, _snowman.cA());
      }
   }
}
