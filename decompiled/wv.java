import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.MinecraftServer;

public class wv {
   public static void a(CommandDispatcher<db> var0) {
      _snowman.register((LiteralArgumentBuilder)dc.a("me").then(dc.a("action", StringArgumentType.greedyString()).executes(var0x -> {
         String _snowman = StringArgumentType.getString(var0x, "action");
         aqa _snowmanx = ((db)var0x.getSource()).f();
         MinecraftServer _snowmanxx = ((db)var0x.getSource()).j();
         if (_snowmanx != null) {
            if (_snowmanx instanceof aah) {
               abc _snowmanxxx = ((aah)_snowmanx).Q();
               if (_snowmanxxx != null) {
                  _snowmanxxx.a(_snowman).thenAcceptAsync(var3x -> var3x.ifPresent(var3xx -> _snowman.ae().a(a(var0x, var3xx), no.a, _snowman.bS())), _snowmanxx);
                  return 1;
               }
            }

            _snowmanxx.ae().a(a(var0x, _snowman), no.a, _snowmanx.bS());
         } else {
            _snowmanxx.ae().a(a(var0x, _snowman), no.b, x.b);
         }

         return 1;
      })));
   }

   private static nr a(CommandContext<db> var0, String var1) {
      return new of("chat.type.emote", ((db)_snowman.getSource()).b(), _snowman);
   }
}
