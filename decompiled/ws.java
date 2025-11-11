import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.MinecraftServer;

public class ws {
   public static void a(CommandDispatcher<db> var0) {
      LiteralArgumentBuilder<db> _snowman = (LiteralArgumentBuilder<db>)dc.a("defaultgamemode").requires(var0x -> var0x.c(2));

      for (bru _snowmanx : bru.values()) {
         if (_snowmanx != bru.a) {
            _snowman.then(dc.a(_snowmanx.b()).executes(var1x -> a((db)var1x.getSource(), _snowman)));
         }
      }

      _snowman.register(_snowman);
   }

   private static int a(db var0, bru var1) {
      int _snowman = 0;
      MinecraftServer _snowmanx = _snowman.j();
      _snowmanx.a(_snowman);
      if (_snowmanx.al()) {
         for (aah _snowmanxx : _snowmanx.ae().s()) {
            if (_snowmanxx.d.b() != _snowman) {
               _snowmanxx.a(_snowman);
               _snowman++;
            }
         }
      }

      _snowman.a(new of("commands.defaultgamemode.success", _snowman.c()), true);
      return _snowman;
   }
}
