import com.google.common.collect.ImmutableMap;
import java.util.Optional;

public class ask extends arv<bfj> {
   final float b;

   public ask(float var1) {
      super(ImmutableMap.of(ayd.d, aye.a), 1200);
      this.b = _snowman;
   }

   protected boolean a(aag var1, bfj var2) {
      return _snowman.cJ().f().map(var0 -> var0 == bhf.b || var0 == bhf.c || var0 == bhf.d).orElse(true);
   }

   protected boolean a(aag var1, bfj var2, long var3) {
      return _snowman.cJ().a(ayd.d);
   }

   protected void b(aag var1, bfj var2, long var3) {
      arw.a(_snowman, _snowman.cJ().c(ayd.d).get().b(), this.b, 1);
   }

   protected void c(aag var1, bfj var2, long var3) {
      Optional<gf> _snowman = _snowman.cJ().c(ayd.d);
      _snowman.ifPresent(var1x -> {
         fx _snowmanx = var1x.b();
         aag _snowmanx = _snowman.l().a(var1x.a());
         if (_snowmanx != null) {
            azo _snowmanxx = _snowmanx.y();
            if (_snowmanxx.a(_snowmanx, var0x -> true)) {
               _snowmanxx.b(_snowmanx);
            }

            rz.c(_snowman, _snowmanx);
         }
      });
      _snowman.cJ().b(ayd.d);
   }
}
