import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.server.MinecraftServer;

public class arr extends arv<bfj> {
   public arr() {
      super(ImmutableMap.of(ayd.d, aye.a));
   }

   protected boolean a(aag var1, bfj var2) {
      fx _snowman = _snowman.cJ().c(ayd.d).get().b();
      return _snowman.a(_snowman.cA(), 2.0) || _snowman.eZ();
   }

   protected void a(aag var1, bfj var2, long var3) {
      gf _snowman = _snowman.cJ().c(ayd.d).get();
      _snowman.cJ().b(ayd.d);
      _snowman.cJ().a(ayd.c, _snowman);
      _snowman.a(_snowman, (byte)14);
      if (_snowman.eX().b() == bfm.a) {
         MinecraftServer _snowmanx = _snowman.l();
         Optional.ofNullable(_snowmanx.a(_snowman.a()))
            .flatMap(var1x -> var1x.y().c(_snowman.b()))
            .flatMap(var0 -> gm.ai.g().filter(var1x -> var1x.b() == var0).findFirst())
            .ifPresent(var2x -> {
               _snowman.a(_snowman.eX().a(var2x));
               _snowman.c(_snowman);
            });
      }
   }
}
