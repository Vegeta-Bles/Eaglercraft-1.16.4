import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import java.util.Collection;

public class xg {
   public static void a(CommandDispatcher<db> var0) {
      _snowman.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("kick").requires(var0x -> var0x.c(3)))
            .then(
               ((RequiredArgumentBuilder)dc.a("targets", dk.d())
                     .executes(var0x -> a((db)var0x.getSource(), dk.f(var0x, "targets"), new of("multiplayer.disconnect.kicked"))))
                  .then(dc.a("reason", dp.a()).executes(var0x -> a((db)var0x.getSource(), dk.f(var0x, "targets"), dp.a(var0x, "reason"))))
            )
      );
   }

   private static int a(db var0, Collection<aah> var1, nr var2) {
      for (aah _snowman : _snowman) {
         _snowman.b.b(_snowman);
         _snowman.a(new of("commands.kick.success", _snowman.d(), _snowman), true);
      }

      return _snowman.size();
   }
}
