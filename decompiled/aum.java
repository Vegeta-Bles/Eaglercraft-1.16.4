import com.google.common.collect.ImmutableMap;
import java.util.Optional;

public class aum extends arv<bfj> {
   private long b;

   public aum() {
      super(ImmutableMap.of(ayd.r, aye.a, ayd.h, aye.a), 350, 350);
   }

   protected boolean a(aag var1, bfj var2) {
      return this.a(_snowman);
   }

   protected boolean a(aag var1, bfj var2, long var3) {
      return _snowman <= this.b && this.a(_snowman);
   }

   protected void b(aag var1, bfj var2, long var3) {
      apy _snowman = _snowman.cJ().c(ayd.r).get();
      arw.a(_snowman, _snowman, 0.5F);
      _snowman.a(_snowman, (byte)18);
      _snowman.a(_snowman, (byte)18);
      int _snowmanx = 275 + _snowman.cY().nextInt(50);
      this.b = _snowman + (long)_snowmanx;
   }

   protected void c(aag var1, bfj var2, long var3) {
      bfj _snowman = (bfj)_snowman.cJ().c(ayd.r).get();
      if (!(_snowman.h(_snowman) > 5.0)) {
         arw.a(_snowman, _snowman, 0.5F);
         if (_snowman >= this.b) {
            _snowman.ff();
            _snowman.ff();
            this.a(_snowman, _snowman, _snowman);
         } else if (_snowman.cY().nextInt(35) == 0) {
            _snowman.a(_snowman, (byte)12);
            _snowman.a(_snowman, (byte)12);
         }
      }
   }

   private void a(aag var1, bfj var2, bfj var3) {
      Optional<fx> _snowman = this.b(_snowman, _snowman);
      if (!_snowman.isPresent()) {
         _snowman.a(_snowman, (byte)13);
         _snowman.a(_snowman, (byte)13);
      } else {
         Optional<bfj> _snowmanx = this.b(_snowman, _snowman, _snowman);
         if (_snowmanx.isPresent()) {
            this.a(_snowman, _snowmanx.get(), _snowman.get());
         } else {
            _snowman.y().b(_snowman.get());
            rz.c(_snowman, _snowman.get());
         }
      }
   }

   protected void d(aag var1, bfj var2, long var3) {
      _snowman.cJ().b(ayd.r);
   }

   private boolean a(bfj var1) {
      arf<bfj> _snowman = _snowman.cJ();
      Optional<apy> _snowmanx = _snowman.c(ayd.r).filter(var0 -> var0.X() == aqe.aP);
      return !_snowmanx.isPresent() ? false : arw.a(_snowman, ayd.r, aqe.aP) && _snowman.f() && _snowmanx.get().f();
   }

   private Optional<fx> b(aag var1, bfj var2) {
      return _snowman.y().a(azr.r.c(), var2x -> this.a(_snowman, var2x), _snowman.cB(), 48);
   }

   private boolean a(bfj var1, fx var2) {
      cxd _snowman = _snowman.x().a(_snowman, azr.r.d());
      return _snowman != null && _snowman.j();
   }

   private Optional<bfj> b(aag var1, bfj var2, bfj var3) {
      bfj _snowman = _snowman.b(_snowman, _snowman);
      if (_snowman == null) {
         return Optional.empty();
      } else {
         _snowman.c_(6000);
         _snowman.c_(6000);
         _snowman.c_(-24000);
         _snowman.b(_snowman.cD(), _snowman.cE(), _snowman.cH(), 0.0F, 0.0F);
         _snowman.l(_snowman);
         _snowman.a(_snowman, (byte)12);
         return Optional.of(_snowman);
      }
   }

   private void a(aag var1, bfj var2, fx var3) {
      gf _snowman = gf.a(_snowman.Y(), _snowman);
      _snowman.cJ().a(ayd.b, _snowman);
   }
}
