import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import net.minecraft.server.MinecraftServer;

public class yb {
   private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new of("commands.schedule.same_tick"));
   private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(var0 -> new of("commands.schedule.cleared.failure", var0));
   private static final SuggestionProvider<db> c = (var0, var1) -> dd.b(((db)var0.getSource()).j().aX().H().u().a(), var1);

   public static void a(CommandDispatcher<db> var0) {
      _snowman.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("schedule").requires(var0x -> var0x.c(2)))
               .then(
                  dc.a("function")
                     .then(
                        dc.a("function", ev.a())
                           .suggests(xb.a)
                           .then(
                              ((RequiredArgumentBuilder)((RequiredArgumentBuilder)dc.a("time", ed.a())
                                       .executes(
                                          var0x -> a((db)var0x.getSource(), ev.b(var0x, "function"), IntegerArgumentType.getInteger(var0x, "time"), true)
                                       ))
                                    .then(
                                       dc.a("append")
                                          .executes(
                                             var0x -> a((db)var0x.getSource(), ev.b(var0x, "function"), IntegerArgumentType.getInteger(var0x, "time"), false)
                                          )
                                    ))
                                 .then(
                                    dc.a("replace")
                                       .executes(
                                          var0x -> a((db)var0x.getSource(), ev.b(var0x, "function"), IntegerArgumentType.getInteger(var0x, "time"), true)
                                       )
                                 )
                           )
                     )
               ))
            .then(
               dc.a("clear")
                  .then(
                     dc.a("function", StringArgumentType.greedyString())
                        .suggests(c)
                        .executes(var0x -> a((db)var0x.getSource(), StringArgumentType.getString(var0x, "function")))
                  )
            )
      );
   }

   private static int a(db var0, Pair<vk, Either<cy, ael<cy>>> var1, int var2, boolean var3) throws CommandSyntaxException {
      if (_snowman == 0) {
         throw a.create();
      } else {
         long _snowman = _snowman.e().T() + (long)_snowman;
         vk _snowmanx = (vk)_snowman.getFirst();
         dcf<MinecraftServer> _snowmanxx = _snowman.j().aX().H().u();
         ((Either)_snowman.getSecond()).ifLeft(var7x -> {
            String _snowmanxxx = _snowman.toString();
            if (_snowman) {
               _snowman.a(_snowmanxxx);
            }

            _snowman.a(_snowmanxxx, _snowman, new dcb(_snowman));
            _snowman.a(new of("commands.schedule.created.function", _snowman, _snowman, _snowman), true);
         }).ifRight(var7x -> {
            String _snowmanxxx = "#" + _snowman.toString();
            if (_snowman) {
               _snowman.a(_snowmanxxx);
            }

            _snowman.a(_snowmanxxx, _snowman, new dcc(_snowman));
            _snowman.a(new of("commands.schedule.created.tag", _snowman, _snowman, _snowman), true);
         });
         return (int)Math.floorMod(_snowman, 2147483647L);
      }
   }

   private static int a(db var0, String var1) throws CommandSyntaxException {
      int _snowman = _snowman.j().aX().H().u().a(_snowman);
      if (_snowman == 0) {
         throw b.create(_snowman);
      } else {
         _snowman.a(new of("commands.schedule.cleared.success", _snowman, _snowman), true);
         return _snowman;
      }
   }
}
