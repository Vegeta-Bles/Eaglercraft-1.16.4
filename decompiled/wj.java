import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import javax.annotation.Nullable;

public class wj {
   private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new of("commands.ban.failed"));

   public static void a(CommandDispatcher<db> var0) {
      _snowman.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("ban").requires(var0x -> var0x.c(3)))
            .then(
               ((RequiredArgumentBuilder)dc.a("targets", dm.a()).executes(var0x -> a((db)var0x.getSource(), dm.a(var0x, "targets"), null)))
                  .then(dc.a("reason", dp.a()).executes(var0x -> a((db)var0x.getSource(), dm.a(var0x, "targets"), dp.a(var0x, "reason"))))
            )
      );
   }

   private static int a(db var0, Collection<GameProfile> var1, @Nullable nr var2) throws CommandSyntaxException {
      acz _snowman = _snowman.j().ae().f();
      int _snowmanx = 0;

      for (GameProfile _snowmanxx : _snowman) {
         if (!_snowman.a(_snowmanxx)) {
            ada _snowmanxxx = new ada(_snowmanxx, null, _snowman.c(), null, _snowman == null ? null : _snowman.getString());
            _snowman.a(_snowmanxxx);
            _snowmanx++;
            _snowman.a(new of("commands.ban.success", ns.a(_snowmanxx), _snowmanxxx.d()), true);
            aah _snowmanxxxx = _snowman.j().ae().a(_snowmanxx.getId());
            if (_snowmanxxxx != null) {
               _snowmanxxxx.b.b(new of("multiplayer.disconnect.banned"));
            }
         }
      }

      if (_snowmanx == 0) {
         throw a.create();
      } else {
         return _snowmanx;
      }
   }
}
