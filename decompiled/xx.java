import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.server.MinecraftServer;

public class xx {
   private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new of("commands.save.failed"));

   public static void a(CommandDispatcher<db> var0) {
      _snowman.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("save-all").requires(var0x -> var0x.c(4)))
               .executes(var0x -> a((db)var0x.getSource(), false)))
            .then(dc.a("flush").executes(var0x -> a((db)var0x.getSource(), true)))
      );
   }

   private static int a(db var0, boolean var1) throws CommandSyntaxException {
      _snowman.a(new of("commands.save.saving"), false);
      MinecraftServer _snowman = _snowman.j();
      _snowman.ae().h();
      boolean _snowmanx = _snowman.a(true, _snowman, true);
      if (!_snowmanx) {
         throw a.create();
      } else {
         _snowman.a(new of("commands.save.success"), true);
         return 1;
      }
   }
}
