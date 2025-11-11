import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;

public class wh {
   public static final Pattern a = Pattern.compile(
      "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$"
   );
   private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new of("commands.banip.invalid"));
   private static final SimpleCommandExceptionType c = new SimpleCommandExceptionType(new of("commands.banip.failed"));

   public static void a(CommandDispatcher<db> var0) {
      _snowman.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("ban-ip").requires(var0x -> var0x.c(3)))
            .then(
               ((RequiredArgumentBuilder)dc.a("target", StringArgumentType.word())
                     .executes(var0x -> a((db)var0x.getSource(), StringArgumentType.getString(var0x, "target"), null)))
                  .then(
                     dc.a("reason", dp.a()).executes(var0x -> a((db)var0x.getSource(), StringArgumentType.getString(var0x, "target"), dp.a(var0x, "reason")))
                  )
            )
      );
   }

   private static int a(db var0, String var1, @Nullable nr var2) throws CommandSyntaxException {
      Matcher _snowman = a.matcher(_snowman);
      if (_snowman.matches()) {
         return b(_snowman, _snowman, _snowman);
      } else {
         aah _snowmanx = _snowman.j().ae().a(_snowman);
         if (_snowmanx != null) {
            return b(_snowman, _snowmanx.v(), _snowman);
         } else {
            throw b.create();
         }
      }
   }

   private static int b(db var0, String var1, @Nullable nr var2) throws CommandSyntaxException {
      acr _snowman = _snowman.j().ae().g();
      if (_snowman.a(_snowman)) {
         throw c.create();
      } else {
         List<aah> _snowmanx = _snowman.j().ae().b(_snowman);
         acs _snowmanxx = new acs(_snowman, null, _snowman.c(), null, _snowman == null ? null : _snowman.getString());
         _snowman.a(_snowmanxx);
         _snowman.a(new of("commands.banip.success", _snowman, _snowmanxx.d()), true);
         if (!_snowmanx.isEmpty()) {
            _snowman.a(new of("commands.banip.info", _snowmanx.size(), fc.a(_snowmanx)), true);
         }

         for (aah _snowmanxxx : _snowmanx) {
            _snowmanxxx.b.b(new of("multiplayer.disconnect.ip_banned"));
         }

         return _snowmanx.size();
      }
   }
}
