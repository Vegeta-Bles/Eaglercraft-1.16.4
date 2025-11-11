import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.regex.Matcher;

public class xp {
   private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new of("commands.pardonip.invalid"));
   private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new of("commands.pardonip.failed"));

   public static void a(CommandDispatcher<db> var0) {
      _snowman.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("pardon-ip").requires(var0x -> var0x.c(3)))
            .then(
               dc.a("target", StringArgumentType.word())
                  .suggests((var0x, var1) -> dd.a(((db)var0x.getSource()).j().ae().g().a(), var1))
                  .executes(var0x -> a((db)var0x.getSource(), StringArgumentType.getString(var0x, "target")))
            )
      );
   }

   private static int a(db var0, String var1) throws CommandSyntaxException {
      Matcher _snowman = wh.a.matcher(_snowman);
      if (!_snowman.matches()) {
         throw a.create();
      } else {
         acr _snowmanx = _snowman.j().ae().g();
         if (!_snowmanx.a(_snowman)) {
            throw b.create();
         } else {
            _snowmanx.c(_snowman);
            _snowman.a(new of("commands.pardonip.success", _snowman), true);
            return 1;
         }
      }
   }
}
