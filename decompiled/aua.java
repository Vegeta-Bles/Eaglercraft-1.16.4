import com.google.common.collect.ImmutableMap;
import java.util.Optional;

public class aua extends arv<aqu> {
   private final ayd<gf> b;
   private long c;
   private final int d;
   private float e;

   public aua(ayd<gf> var1, float var2, int var3) {
      super(ImmutableMap.of(ayd.m, aye.c, _snowman, aye.a));
      this.b = _snowman;
      this.e = _snowman;
      this.d = _snowman;
   }

   protected boolean a(aag var1, aqu var2) {
      Optional<gf> _snowman = _snowman.cJ().c(this.b);
      return _snowman.isPresent() && _snowman.Y() == _snowman.get().a() && _snowman.get().b().a(_snowman.cA(), (double)this.d);
   }

   protected void a(aag var1, aqu var2, long var3) {
      if (_snowman > this.c) {
         Optional<dcn> _snowman = Optional.ofNullable(azj.b(_snowman, 8, 6));
         _snowman.cJ().a(ayd.m, _snowman.map(var1x -> new ayf(var1x, this.e, 1)));
         this.c = _snowman + 180L;
      }
   }
}
