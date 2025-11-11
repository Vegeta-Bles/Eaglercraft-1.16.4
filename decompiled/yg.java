import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import java.util.Collection;
import java.util.Collections;

public class yg {
   public static void a(CommandDispatcher<db> var0) {
      _snowman.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("spawnpoint").requires(var0x -> var0x.c(2)))
               .executes(var0x -> a((db)var0x.getSource(), Collections.singleton(((db)var0x.getSource()).h()), new fx(((db)var0x.getSource()).d()), 0.0F)))
            .then(
               ((RequiredArgumentBuilder)dc.a("targets", dk.d())
                     .executes(var0x -> a((db)var0x.getSource(), dk.f(var0x, "targets"), new fx(((db)var0x.getSource()).d()), 0.0F)))
                  .then(
                     ((RequiredArgumentBuilder)dc.a("pos", ek.a())
                           .executes(var0x -> a((db)var0x.getSource(), dk.f(var0x, "targets"), ek.b(var0x, "pos"), 0.0F)))
                        .then(
                           dc.a("angle", de.a()).executes(var0x -> a((db)var0x.getSource(), dk.f(var0x, "targets"), ek.b(var0x, "pos"), de.a(var0x, "angle")))
                        )
                  )
            )
      );
   }

   private static int a(db var0, Collection<aah> var1, fx var2, float var3) {
      vj<brx> _snowman = _snowman.e().Y();

      for (aah _snowmanx : _snowman) {
         _snowmanx.a(_snowman, _snowman, _snowman, true, false);
      }

      String _snowmanx = _snowman.a().toString();
      if (_snowman.size() == 1) {
         _snowman.a(new of("commands.spawnpoint.success.single", _snowman.u(), _snowman.v(), _snowman.w(), _snowman, _snowmanx, _snowman.iterator().next().d()), true);
      } else {
         _snowman.a(new of("commands.spawnpoint.success.multiple", _snowman.u(), _snowman.v(), _snowman.w(), _snowman, _snowmanx, _snowman.size()), true);
      }

      return _snowman.size();
   }
}
