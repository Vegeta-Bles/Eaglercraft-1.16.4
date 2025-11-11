import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import net.minecraft.server.MinecraftServer;

public class wt {
   private static final DynamicCommandExceptionType a = new DynamicCommandExceptionType(var0 -> new of("commands.difficulty.failure", var0));

   public static void a(CommandDispatcher<db> var0) {
      LiteralArgumentBuilder<db> _snowman = dc.a("difficulty");

      for (aor _snowmanx : aor.values()) {
         _snowman.then(dc.a(_snowmanx.c()).executes(var1x -> a((db)var1x.getSource(), _snowman)));
      }

      _snowman.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)_snowman.requires(var0x -> var0x.c(2))).executes(var0x -> {
         aor _snowmanx = ((db)var0x.getSource()).e().ad();
         ((db)var0x.getSource()).a(new of("commands.difficulty.query", _snowmanx.b()), false);
         return _snowmanx.a();
      }));
   }

   public static int a(db var0, aor var1) throws CommandSyntaxException {
      MinecraftServer _snowman = _snowman.j();
      if (_snowman.aX().s() == _snowman) {
         throw a.create(_snowman.c());
      } else {
         _snowman.a(_snowman, true);
         _snowman.a(new of("commands.difficulty.success", _snowman.b()), true);
         return 0;
      }
   }
}
