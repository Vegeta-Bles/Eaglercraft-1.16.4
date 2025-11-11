import com.google.common.collect.ImmutableMap;
import java.util.Optional;

public class atr extends arv<bfj> {
   private final ayd<gf> b;
   private final float c;
   private final int d;
   private final int e;
   private final int f;

   public atr(ayd<gf> var1, float var2, int var3, int var4, int var5) {
      super(ImmutableMap.of(ayd.D, aye.c, ayd.m, aye.b, _snowman, aye.a));
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
   }

   private void a(bfj var1, long var2) {
      arf<?> _snowman = _snowman.cJ();
      _snowman.a(this.b);
      _snowman.b(this.b);
      _snowman.a(ayd.D, _snowman);
   }

   protected void a(aag var1, bfj var2, long var3) {
      arf<?> _snowman = _snowman.cJ();
      _snowman.c(this.b).ifPresent(var6 -> {
         if (this.a(_snowman, var6) || this.a(_snowman, _snowman)) {
            this.a(_snowman, _snowman);
         } else if (this.a(_snowman, var6)) {
            dcn _snowmanx = null;
            int _snowmanx = 0;

            for (int _snowmanxx = 1000; _snowmanx < 1000 && (_snowmanx == null || this.a(_snowman, gf.a(_snowman.Y(), new fx(_snowmanx)))); _snowmanx++) {
               _snowmanx = azj.b(_snowman, 15, 7, dcn.c(var6.b()));
            }

            if (_snowmanx == 1000) {
               this.a(_snowman, _snowman);
               return;
            }

            _snowman.a(ayd.m, new ayf(_snowmanx, this.c, this.d));
         } else if (!this.a(_snowman, _snowman, var6)) {
            _snowman.a(ayd.m, new ayf(var6.b(), this.c, this.d));
         }
      });
   }

   private boolean a(aag var1, bfj var2) {
      Optional<Long> _snowman = _snowman.cJ().c(ayd.D);
      return _snowman.isPresent() ? _snowman.T() - _snowman.get() > (long)this.f : false;
   }

   private boolean a(bfj var1, gf var2) {
      return _snowman.b().k(_snowman.cB()) > this.e;
   }

   private boolean a(aag var1, gf var2) {
      return _snowman.a() != _snowman.Y();
   }

   private boolean a(aag var1, bfj var2, gf var3) {
      return _snowman.a() == _snowman.Y() && _snowman.b().k(_snowman.cB()) <= this.d;
   }
}
