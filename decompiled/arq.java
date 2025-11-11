import com.google.common.collect.ImmutableMap;
import java.util.Optional;

public class arq extends arv<azz> {
   private final aqe<? extends azz> b;
   private final float c;
   private long d;

   public arq(aqe<? extends azz> var1, float var2) {
      super(ImmutableMap.of(ayd.h, aye.a, ayd.r, aye.b, ayd.m, aye.c, ayd.n, aye.c), 325);
      this.b = _snowman;
      this.c = _snowman;
   }

   protected boolean a(aag var1, azz var2) {
      return _snowman.eS() && this.c(_snowman).isPresent();
   }

   protected void a(aag var1, azz var2, long var3) {
      azz _snowman = this.c(_snowman).get();
      _snowman.cJ().a(ayd.r, _snowman);
      _snowman.cJ().a(ayd.r, _snowman);
      arw.a(_snowman, _snowman, this.c);
      int _snowmanx = 275 + _snowman.cY().nextInt(50);
      this.d = _snowman + (long)_snowmanx;
   }

   protected boolean b(aag var1, azz var2, long var3) {
      if (!this.b(_snowman)) {
         return false;
      } else {
         azz _snowman = this.a(_snowman);
         return _snowman.aX() && _snowman.a(_snowman) && arw.a(_snowman.cJ(), _snowman) && _snowman <= this.d;
      }
   }

   protected void c(aag var1, azz var2, long var3) {
      azz _snowman = this.a(_snowman);
      arw.a(_snowman, _snowman, this.c);
      if (_snowman.a(_snowman, 3.0)) {
         if (_snowman >= this.d) {
            _snowman.a(_snowman, _snowman);
            _snowman.cJ().b(ayd.r);
            _snowman.cJ().b(ayd.r);
         }
      }
   }

   protected void d(aag var1, azz var2, long var3) {
      _snowman.cJ().b(ayd.r);
      _snowman.cJ().b(ayd.m);
      _snowman.cJ().b(ayd.n);
      this.d = 0L;
   }

   private azz a(azz var1) {
      return (azz)_snowman.cJ().c(ayd.r).get();
   }

   private boolean b(azz var1) {
      arf<?> _snowman = _snowman.cJ();
      return _snowman.a(ayd.r) && _snowman.c(ayd.r).get().X() == this.b;
   }

   private Optional<? extends azz> c(azz var1) {
      return _snowman.cJ().c(ayd.h).get().stream().filter(var1x -> var1x.X() == this.b).map(var0 -> (azz)var0).filter(_snowman::a).findFirst();
   }
}
