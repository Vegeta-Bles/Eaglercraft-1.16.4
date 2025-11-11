import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import java.util.Collection;
import javax.annotation.Nullable;

public class yl {
   public static void a(CommandDispatcher<db> var0) {
      RequiredArgumentBuilder<db, fc> _snowman = (RequiredArgumentBuilder<db, fc>)((RequiredArgumentBuilder)dc.a("targets", dk.d())
            .executes(var0x -> a((db)var0x.getSource(), dk.f(var0x, "targets"), null, null)))
         .then(
            dc.a("*")
               .then(dc.a("sound", dy.a()).suggests(fm.c).executes(var0x -> a((db)var0x.getSource(), dk.f(var0x, "targets"), null, dy.e(var0x, "sound"))))
         );

      for (adr _snowmanx : adr.values()) {
         _snowman.then(
            ((LiteralArgumentBuilder)dc.a(_snowmanx.a()).executes(var1x -> a((db)var1x.getSource(), dk.f(var1x, "targets"), _snowman, null)))
               .then(dc.a("sound", dy.a()).suggests(fm.c).executes(var1x -> a((db)var1x.getSource(), dk.f(var1x, "targets"), _snowman, dy.e(var1x, "sound"))))
         );
      }

      _snowman.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("stopsound").requires(var0x -> var0x.c(2))).then(_snowman));
   }

   private static int a(db var0, Collection<aah> var1, @Nullable adr var2, @Nullable vk var3) {
      ro _snowman = new ro(_snowman, _snowman);

      for (aah _snowmanx : _snowman) {
         _snowmanx.b.a(_snowman);
      }

      if (_snowman != null) {
         if (_snowman != null) {
            _snowman.a(new of("commands.stopsound.success.source.sound", _snowman, _snowman.a()), true);
         } else {
            _snowman.a(new of("commands.stopsound.success.source.any", _snowman.a()), true);
         }
      } else if (_snowman != null) {
         _snowman.a(new of("commands.stopsound.success.sourceless.sound", _snowman), true);
      } else {
         _snowman.a(new of("commands.stopsound.success.sourceless.any"), true);
      }

      return _snowman.size();
   }
}
