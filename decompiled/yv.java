import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

public class yv {
   public static void a(CommandDispatcher<db> var0) {
      _snowman.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("weather").requires(var0x -> var0x.c(2)))
                  .then(
                     ((LiteralArgumentBuilder)dc.a("clear").executes(var0x -> a((db)var0x.getSource(), 6000)))
                        .then(
                           dc.a("duration", IntegerArgumentType.integer(0, 1000000))
                              .executes(var0x -> a((db)var0x.getSource(), IntegerArgumentType.getInteger(var0x, "duration") * 20))
                        )
                  ))
               .then(
                  ((LiteralArgumentBuilder)dc.a("rain").executes(var0x -> b((db)var0x.getSource(), 6000)))
                     .then(
                        dc.a("duration", IntegerArgumentType.integer(0, 1000000))
                           .executes(var0x -> b((db)var0x.getSource(), IntegerArgumentType.getInteger(var0x, "duration") * 20))
                     )
               ))
            .then(
               ((LiteralArgumentBuilder)dc.a("thunder").executes(var0x -> c((db)var0x.getSource(), 6000)))
                  .then(
                     dc.a("duration", IntegerArgumentType.integer(0, 1000000))
                        .executes(var0x -> c((db)var0x.getSource(), IntegerArgumentType.getInteger(var0x, "duration") * 20))
                  )
            )
      );
   }

   private static int a(db var0, int var1) {
      _snowman.e().a(_snowman, 0, false, false);
      _snowman.a(new of("commands.weather.set.clear"), true);
      return _snowman;
   }

   private static int b(db var0, int var1) {
      _snowman.e().a(0, _snowman, true, false);
      _snowman.a(new of("commands.weather.set.rain"), true);
      return _snowman;
   }

   private static int c(db var0, int var1) {
      _snowman.e().a(0, _snowman, true, true);
      _snowman.a(new of("commands.weather.set.thunder"), true);
      return _snowman;
   }
}
