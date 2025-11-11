import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;

public class xq {
   private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new of("commands.particle.failed"));

   public static void a(CommandDispatcher<db> var0) {
      _snowman.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("particle").requires(var0x -> var0x.c(2)))
            .then(
               ((RequiredArgumentBuilder)dc.a("name", dw.a())
                     .executes(
                        var0x -> a(
                              (db)var0x.getSource(),
                              dw.a(var0x, "name"),
                              ((db)var0x.getSource()).d(),
                              dcn.a,
                              0.0F,
                              0,
                              false,
                              ((db)var0x.getSource()).j().ae().s()
                           )
                     ))
                  .then(
                     ((RequiredArgumentBuilder)dc.a("pos", er.a())
                           .executes(
                              var0x -> a(
                                    (db)var0x.getSource(), dw.a(var0x, "name"), er.a(var0x, "pos"), dcn.a, 0.0F, 0, false, ((db)var0x.getSource()).j().ae().s()
                                 )
                           ))
                        .then(
                           dc.a("delta", er.a(false))
                              .then(
                                 dc.a("speed", FloatArgumentType.floatArg(0.0F))
                                    .then(
                                       ((RequiredArgumentBuilder)((RequiredArgumentBuilder)dc.a("count", IntegerArgumentType.integer(0))
                                                .executes(
                                                   var0x -> a(
                                                         (db)var0x.getSource(),
                                                         dw.a(var0x, "name"),
                                                         er.a(var0x, "pos"),
                                                         er.a(var0x, "delta"),
                                                         FloatArgumentType.getFloat(var0x, "speed"),
                                                         IntegerArgumentType.getInteger(var0x, "count"),
                                                         false,
                                                         ((db)var0x.getSource()).j().ae().s()
                                                      )
                                                ))
                                             .then(
                                                ((LiteralArgumentBuilder)dc.a("force")
                                                      .executes(
                                                         var0x -> a(
                                                               (db)var0x.getSource(),
                                                               dw.a(var0x, "name"),
                                                               er.a(var0x, "pos"),
                                                               er.a(var0x, "delta"),
                                                               FloatArgumentType.getFloat(var0x, "speed"),
                                                               IntegerArgumentType.getInteger(var0x, "count"),
                                                               true,
                                                               ((db)var0x.getSource()).j().ae().s()
                                                            )
                                                      ))
                                                   .then(
                                                      dc.a("viewers", dk.d())
                                                         .executes(
                                                            var0x -> a(
                                                                  (db)var0x.getSource(),
                                                                  dw.a(var0x, "name"),
                                                                  er.a(var0x, "pos"),
                                                                  er.a(var0x, "delta"),
                                                                  FloatArgumentType.getFloat(var0x, "speed"),
                                                                  IntegerArgumentType.getInteger(var0x, "count"),
                                                                  true,
                                                                  dk.f(var0x, "viewers")
                                                               )
                                                         )
                                                   )
                                             ))
                                          .then(
                                             ((LiteralArgumentBuilder)dc.a("normal")
                                                   .executes(
                                                      var0x -> a(
                                                            (db)var0x.getSource(),
                                                            dw.a(var0x, "name"),
                                                            er.a(var0x, "pos"),
                                                            er.a(var0x, "delta"),
                                                            FloatArgumentType.getFloat(var0x, "speed"),
                                                            IntegerArgumentType.getInteger(var0x, "count"),
                                                            false,
                                                            ((db)var0x.getSource()).j().ae().s()
                                                         )
                                                   ))
                                                .then(
                                                   dc.a("viewers", dk.d())
                                                      .executes(
                                                         var0x -> a(
                                                               (db)var0x.getSource(),
                                                               dw.a(var0x, "name"),
                                                               er.a(var0x, "pos"),
                                                               er.a(var0x, "delta"),
                                                               FloatArgumentType.getFloat(var0x, "speed"),
                                                               IntegerArgumentType.getInteger(var0x, "count"),
                                                               false,
                                                               dk.f(var0x, "viewers")
                                                            )
                                                      )
                                                )
                                          )
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int a(db var0, hf var1, dcn var2, dcn var3, float var4, int var5, boolean var6, Collection<aah> var7) throws CommandSyntaxException {
      int _snowman = 0;

      for (aah _snowmanx : _snowman) {
         if (_snowman.e().a(_snowmanx, _snowman, _snowman, _snowman.b, _snowman.c, _snowman.d, _snowman, _snowman.b, _snowman.c, _snowman.d, (double)_snowman)) {
            _snowman++;
         }
      }

      if (_snowman == 0) {
         throw a.create();
      } else {
         _snowman.a(new of("commands.particle.success", gm.V.b(_snowman.b()).toString()), true);
         return _snowman;
      }
   }
}
