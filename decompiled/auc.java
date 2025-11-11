import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;

public class auc extends arv<bfj> {
   private final ayd<List<gf>> b;
   private final ayd<gf> c;
   private final float d;
   private final int e;
   private final int f;
   private long g;
   @Nullable
   private gf h;

   public auc(ayd<List<gf>> var1, float var2, int var3, int var4, ayd<gf> var5) {
      super(ImmutableMap.of(ayd.m, aye.c, _snowman, aye.a, _snowman, aye.a));
      this.b = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
      this.c = _snowman;
   }

   protected boolean a(aag var1, bfj var2) {
      Optional<List<gf>> _snowman = _snowman.cJ().c(this.b);
      Optional<gf> _snowmanx = _snowman.cJ().c(this.c);
      if (_snowman.isPresent() && _snowmanx.isPresent()) {
         List<gf> _snowmanxx = _snowman.get();
         if (!_snowmanxx.isEmpty()) {
            this.h = _snowmanxx.get(_snowman.u_().nextInt(_snowmanxx.size()));
            return this.h != null && _snowman.Y() == this.h.a() && _snowmanx.get().b().a(_snowman.cA(), (double)this.f);
         }
      }

      return false;
   }

   protected void a(aag var1, bfj var2, long var3) {
      if (_snowman > this.g && this.h != null) {
         _snowman.cJ().a(ayd.m, new ayf(this.h.b(), this.d, this.e));
         this.g = _snowman + 100L;
      }
   }
}
