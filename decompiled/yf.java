import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

public class yf {
   public static void a(CommandDispatcher<db> var0) {
      _snowman.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("setidletimeout").requires(var0x -> var0x.c(3)))
            .then(dc.a("minutes", IntegerArgumentType.integer(0)).executes(var0x -> a((db)var0x.getSource(), IntegerArgumentType.getInteger(var0x, "minutes"))))
      );
   }

   private static int a(db var0, int var1) {
      _snowman.j().d(_snowman);
      _snowman.a(new of("commands.setidletimeout.success", _snowman), true);
      return _snowman;
   }
}
