import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

public class yk {
   public static void a(CommandDispatcher<db> var0) {
      _snowman.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("stop").requires(var0x -> var0x.c(4))).executes(var0x -> {
         ((db)var0x.getSource()).a(new of("commands.stop.stopping"), true);
         ((db)var0x.getSource()).j().a(false);
         return 1;
      }));
   }
}
