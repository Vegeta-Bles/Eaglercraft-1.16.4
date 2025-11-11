import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

public class yr {
   public static void a(CommandDispatcher<db> var0) {
      _snowman.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("tellraw").requires(var0x -> var0x.c(2)))
            .then(dc.a("targets", dk.d()).then(dc.a("message", dg.a()).executes(var0x -> {
               int _snowman = 0;

               for (aah _snowmanx : dk.f(var0x, "targets")) {
                  _snowmanx.a(ns.a((db)var0x.getSource(), dg.a(var0x, "message"), _snowmanx, 0), x.b);
                  _snowman++;
               }

               return _snowman;
            })))
      );
   }
}
