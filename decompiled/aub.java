import com.google.common.collect.ImmutableMap;
import java.util.Optional;

public class aub extends arv<aqu> {
   private final ayd<gf> b;
   private final int c;
   private final int d;
   private final float e;
   private long f;

   public aub(ayd<gf> var1, float var2, int var3, int var4) {
      super(ImmutableMap.of(ayd.m, aye.c, _snowman, aye.a));
      this.b = _snowman;
      this.e = _snowman;
      this.c = _snowman;
      this.d = _snowman;
   }

   protected boolean a(aag var1, aqu var2) {
      Optional<gf> _snowman = _snowman.cJ().c(this.b);
      return _snowman.isPresent() && _snowman.Y() == _snowman.get().a() && _snowman.get().b().a(_snowman.cA(), (double)this.d);
   }

   protected void a(aag var1, aqu var2, long var3) {
      if (_snowman > this.f) {
         arf<?> _snowman = _snowman.cJ();
         Optional<gf> _snowmanx = _snowman.c(this.b);
         _snowmanx.ifPresent(var2x -> _snowman.a(ayd.m, new ayf(var2x.b(), this.e, this.c)));
         this.f = _snowman + 80L;
      }
   }
}
