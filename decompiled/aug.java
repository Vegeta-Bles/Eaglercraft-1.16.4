import com.google.common.collect.ImmutableMap;
import java.util.Optional;

public class aug extends arv<bfj> {
   private long b;
   private long c;
   private int d;
   private Optional<fx> e = Optional.empty();

   public aug() {
      super(ImmutableMap.of(ayd.n, aye.b, ayd.m, aye.b));
   }

   protected boolean a(aag var1, bfj var2) {
      if (_snowman.K % 10 == 0 && (this.c == 0L || this.c + 160L <= (long)_snowman.K)) {
         if (_snowman.eU().a(bmd.mK) <= 0) {
            return false;
         } else {
            this.e = this.b(_snowman, _snowman);
            return this.e.isPresent();
         }
      } else {
         return false;
      }
   }

   protected boolean a(aag var1, bfj var2, long var3) {
      return this.d < 80 && this.e.isPresent();
   }

   private Optional<fx> b(aag var1, bfj var2) {
      fx.a _snowman = new fx.a();
      Optional<fx> _snowmanx = Optional.empty();
      int _snowmanxx = 0;

      for (int _snowmanxxx = -1; _snowmanxxx <= 1; _snowmanxxx++) {
         for (int _snowmanxxxx = -1; _snowmanxxxx <= 1; _snowmanxxxx++) {
            for (int _snowmanxxxxx = -1; _snowmanxxxxx <= 1; _snowmanxxxxx++) {
               _snowman.a(_snowman.cB(), _snowmanxxx, _snowmanxxxx, _snowmanxxxxx);
               if (this.a(_snowman, _snowman)) {
                  if (_snowman.t.nextInt(++_snowmanxx) == 0) {
                     _snowmanx = Optional.of(_snowman.h());
                  }
               }
            }
         }
      }

      return _snowmanx;
   }

   private boolean a(fx var1, aag var2) {
      ceh _snowman = _snowman.d_(_snowman);
      buo _snowmanx = _snowman.b();
      return _snowmanx instanceof bvs && !((bvs)_snowmanx).h(_snowman);
   }

   protected void b(aag var1, bfj var2, long var3) {
      this.a(_snowman);
      _snowman.a(aqf.a, new bmb(bmd.mK));
      this.b = _snowman;
      this.d = 0;
   }

   private void a(bfj var1) {
      this.e.ifPresent(var1x -> {
         arx _snowman = new arx(var1x);
         _snowman.cJ().a(ayd.n, _snowman);
         _snowman.cJ().a(ayd.m, new ayf(_snowman, 0.5F, 1));
      });
   }

   protected void c(aag var1, bfj var2, long var3) {
      _snowman.a(aqf.a, bmb.b);
      this.c = (long)_snowman.K;
   }

   protected void d(aag var1, bfj var2, long var3) {
      fx _snowman = this.e.get();
      if (_snowman >= this.b && _snowman.a(_snowman.cA(), 1.0)) {
         bmb _snowmanx = bmb.b;
         apa _snowmanxx = _snowman.eU();
         int _snowmanxxx = _snowmanxx.Z_();

         for (int _snowmanxxxx = 0; _snowmanxxxx < _snowmanxxx; _snowmanxxxx++) {
            bmb _snowmanxxxxx = _snowmanxx.a(_snowmanxxxx);
            if (_snowmanxxxxx.b() == bmd.mK) {
               _snowmanx = _snowmanxxxxx;
               break;
            }
         }

         if (!_snowmanx.a() && bkj.a(_snowmanx, _snowman, _snowman)) {
            _snowman.c(2005, _snowman, 0);
            this.e = this.b(_snowman, _snowman);
            this.a(_snowman);
            this.b = _snowman + 40L;
         }

         this.d++;
      }
   }
}
