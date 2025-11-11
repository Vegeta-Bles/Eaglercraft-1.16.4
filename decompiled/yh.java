import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;

public class yh {
   public static void a(CommandDispatcher<db> var0) {
      _snowman.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("setworldspawn").requires(var0x -> var0x.c(2)))
               .executes(var0x -> a((db)var0x.getSource(), new fx(((db)var0x.getSource()).d()), 0.0F)))
            .then(
               ((RequiredArgumentBuilder)dc.a("pos", ek.a()).executes(var0x -> a((db)var0x.getSource(), ek.b(var0x, "pos"), 0.0F)))
                  .then(dc.a("angle", de.a()).executes(var0x -> a((db)var0x.getSource(), ek.b(var0x, "pos"), de.a(var0x, "angle"))))
            )
      );
   }

   private static int a(db var0, fx var1, float var2) {
      _snowman.e().a(_snowman, _snowman);
      _snowman.a(new of("commands.setworldspawn.success", _snowman.u(), _snowman.v(), _snowman.w(), _snowman), true);
      return 1;
   }
}
