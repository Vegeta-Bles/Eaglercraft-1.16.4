import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;

public class wo {
   private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new of("commands.deop.failed"));

   public static void a(CommandDispatcher<db> var0) {
      _snowman.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("deop").requires(var0x -> var0x.c(3)))
            .then(
               dc.a("targets", dm.a())
                  .suggests((var0x, var1) -> dd.a(((db)var0x.getSource()).j().ae().l(), var1))
                  .executes(var0x -> a((db)var0x.getSource(), dm.a(var0x, "targets")))
            )
      );
   }

   private static int a(db var0, Collection<GameProfile> var1) throws CommandSyntaxException {
      acu _snowman = _snowman.j().ae();
      int _snowmanx = 0;

      for (GameProfile _snowmanxx : _snowman) {
         if (_snowman.h(_snowmanxx)) {
            _snowman.b(_snowmanxx);
            _snowmanx++;
            _snowman.a(new of("commands.deop.success", _snowman.iterator().next().getName()), true);
         }
      }

      if (_snowmanx == 0) {
         throw a.create();
      } else {
         _snowman.j().a(_snowman);
         return _snowmanx;
      }
   }
}
