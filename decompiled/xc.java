import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import java.util.Collection;
import java.util.Collections;

public class xc {
   public static void a(CommandDispatcher<db> var0) {
      LiteralArgumentBuilder<db> _snowman = (LiteralArgumentBuilder<db>)dc.a("gamemode").requires(var0x -> var0x.c(2));

      for (bru _snowmanx : bru.values()) {
         if (_snowmanx != bru.a) {
            _snowman.then(
               ((LiteralArgumentBuilder)dc.a(_snowmanx.b()).executes(var1x -> a(var1x, Collections.singleton(((db)var1x.getSource()).h()), _snowman)))
                  .then(dc.a("target", dk.d()).executes(var1x -> a(var1x, dk.f(var1x, "target"), _snowman)))
            );
         }
      }

      _snowman.register(_snowman);
   }

   private static void a(db var0, aah var1, bru var2) {
      nr _snowman = new of("gameMode." + _snowman.b());
      if (_snowman.f() == _snowman) {
         _snowman.a(new of("commands.gamemode.success.self", _snowman), true);
      } else {
         if (_snowman.e().V().b(brt.n)) {
            _snowman.a(new of("gameMode.changed", _snowman), x.b);
         }

         _snowman.a(new of("commands.gamemode.success.other", _snowman.d(), _snowman), true);
      }
   }

   private static int a(CommandContext<db> var0, Collection<aah> var1, bru var2) {
      int _snowman = 0;

      for (aah _snowmanx : _snowman) {
         if (_snowmanx.d.b() != _snowman) {
            _snowmanx.a(_snowman);
            a((db)_snowman.getSource(), _snowmanx, _snowman);
            _snowman++;
         }
      }

      return _snowman;
   }
}
