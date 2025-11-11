import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

public class ys {
   public static void a(CommandDispatcher<db> var0) {
      _snowman.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("time").requires(var0x -> var0x.c(2)))
                  .then(
                     ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("set")
                                    .then(dc.a("day").executes(var0x -> a((db)var0x.getSource(), 1000))))
                                 .then(dc.a("noon").executes(var0x -> a((db)var0x.getSource(), 6000))))
                              .then(dc.a("night").executes(var0x -> a((db)var0x.getSource(), 13000))))
                           .then(dc.a("midnight").executes(var0x -> a((db)var0x.getSource(), 18000))))
                        .then(dc.a("time", ed.a()).executes(var0x -> a((db)var0x.getSource(), IntegerArgumentType.getInteger(var0x, "time"))))
                  ))
               .then(dc.a("add").then(dc.a("time", ed.a()).executes(var0x -> b((db)var0x.getSource(), IntegerArgumentType.getInteger(var0x, "time"))))))
            .then(
               ((LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("query")
                        .then(dc.a("daytime").executes(var0x -> c((db)var0x.getSource(), a(((db)var0x.getSource()).e())))))
                     .then(dc.a("gametime").executes(var0x -> c((db)var0x.getSource(), (int)(((db)var0x.getSource()).e().T() % 2147483647L)))))
                  .then(dc.a("day").executes(var0x -> c((db)var0x.getSource(), (int)(((db)var0x.getSource()).e().U() / 24000L % 2147483647L))))
            )
      );
   }

   private static int a(aag var0) {
      return (int)(_snowman.U() % 24000L);
   }

   private static int c(db var0, int var1) {
      _snowman.a(new of("commands.time.query", _snowman), false);
      return _snowman;
   }

   public static int a(db var0, int var1) {
      for (aag _snowman : _snowman.j().G()) {
         _snowman.a((long)_snowman);
      }

      _snowman.a(new of("commands.time.set", _snowman), true);
      return a(_snowman.e());
   }

   public static int b(db var0, int var1) {
      for (aag _snowman : _snowman.j().G()) {
         _snowman.a(_snowman.U() + (long)_snowman);
      }

      int _snowman = a(_snowman.e());
      _snowman.a(new of("commands.time.set", _snowman), true);
      return _snowman;
   }
}
