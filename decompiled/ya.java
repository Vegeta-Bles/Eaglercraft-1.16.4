import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

public class ya {
   public static void a(CommandDispatcher<db> var0) {
      _snowman.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("say").requires(var0x -> var0x.c(2))).then(dc.a("message", dp.a()).executes(var0x -> {
         nr _snowman = dp.a(var0x, "message");
         of _snowmanx = new of("chat.type.announcement", ((db)var0x.getSource()).b(), _snowman);
         aqa _snowmanxx = ((db)var0x.getSource()).f();
         if (_snowmanxx != null) {
            ((db)var0x.getSource()).j().ae().a(_snowmanx, no.a, _snowmanxx.bS());
         } else {
            ((db)var0x.getSource()).j().ae().a(_snowmanx, no.b, x.b);
         }

         return 1;
      })));
   }
}
