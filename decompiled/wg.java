import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.Dynamic3CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.UUID;

public class wg {
   private static final SuggestionProvider<db> a = (var0, var1) -> dd.a(gm.af.c(), var1);
   private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(var0 -> new of("commands.attribute.failed.entity", var0));
   private static final Dynamic2CommandExceptionType c = new Dynamic2CommandExceptionType(
      (var0, var1) -> new of("commands.attribute.failed.no_attribute", var0, var1)
   );
   private static final Dynamic3CommandExceptionType d = new Dynamic3CommandExceptionType(
      (var0, var1, var2) -> new of("commands.attribute.failed.no_modifier", var1, var0, var2)
   );
   private static final Dynamic3CommandExceptionType e = new Dynamic3CommandExceptionType(
      (var0, var1, var2) -> new of("commands.attribute.failed.modifier_already_present", var2, var1, var0)
   );

   public static void a(CommandDispatcher<db> var0) {
      _snowman.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("attribute").requires(var0x -> var0x.c(2)))
            .then(
               dc.a("target", dk.a())
                  .then(
                     ((RequiredArgumentBuilder)((RequiredArgumentBuilder)dc.a("attribute", dy.a())
                              .suggests(a)
                              .then(
                                 ((LiteralArgumentBuilder)dc.a("get")
                                       .executes(var0x -> a((db)var0x.getSource(), dk.a(var0x, "target"), dy.d(var0x, "attribute"), 1.0)))
                                    .then(
                                       dc.a("scale", DoubleArgumentType.doubleArg())
                                          .executes(
                                             var0x -> a(
                                                   (db)var0x.getSource(),
                                                   dk.a(var0x, "target"),
                                                   dy.d(var0x, "attribute"),
                                                   DoubleArgumentType.getDouble(var0x, "scale")
                                                )
                                          )
                                    )
                              ))
                           .then(
                              ((LiteralArgumentBuilder)dc.a("base")
                                    .then(
                                       dc.a("set")
                                          .then(
                                             dc.a("value", DoubleArgumentType.doubleArg())
                                                .executes(
                                                   var0x -> c(
                                                         (db)var0x.getSource(),
                                                         dk.a(var0x, "target"),
                                                         dy.d(var0x, "attribute"),
                                                         DoubleArgumentType.getDouble(var0x, "value")
                                                      )
                                                )
                                          )
                                    ))
                                 .then(
                                    ((LiteralArgumentBuilder)dc.a("get")
                                          .executes(var0x -> b((db)var0x.getSource(), dk.a(var0x, "target"), dy.d(var0x, "attribute"), 1.0)))
                                       .then(
                                          dc.a("scale", DoubleArgumentType.doubleArg())
                                             .executes(
                                                var0x -> b(
                                                      (db)var0x.getSource(),
                                                      dk.a(var0x, "target"),
                                                      dy.d(var0x, "attribute"),
                                                      DoubleArgumentType.getDouble(var0x, "scale")
                                                   )
                                             )
                                       )
                                 )
                           ))
                        .then(
                           ((LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("modifier")
                                    .then(
                                       dc.a("add")
                                          .then(
                                             dc.a("uuid", ee.a())
                                                .then(
                                                   dc.a("name", StringArgumentType.string())
                                                      .then(
                                                         ((RequiredArgumentBuilder)((RequiredArgumentBuilder)dc.a("value", DoubleArgumentType.doubleArg())
                                                                  .then(
                                                                     dc.a("add")
                                                                        .executes(
                                                                           var0x -> a(
                                                                                 (db)var0x.getSource(),
                                                                                 dk.a(var0x, "target"),
                                                                                 dy.d(var0x, "attribute"),
                                                                                 ee.a(var0x, "uuid"),
                                                                                 StringArgumentType.getString(var0x, "name"),
                                                                                 DoubleArgumentType.getDouble(var0x, "value"),
                                                                                 arj.a.a
                                                                              )
                                                                        )
                                                                  ))
                                                               .then(
                                                                  dc.a("multiply")
                                                                     .executes(
                                                                        var0x -> a(
                                                                              (db)var0x.getSource(),
                                                                              dk.a(var0x, "target"),
                                                                              dy.d(var0x, "attribute"),
                                                                              ee.a(var0x, "uuid"),
                                                                              StringArgumentType.getString(var0x, "name"),
                                                                              DoubleArgumentType.getDouble(var0x, "value"),
                                                                              arj.a.c
                                                                           )
                                                                     )
                                                               ))
                                                            .then(
                                                               dc.a("multiply_base")
                                                                  .executes(
                                                                     var0x -> a(
                                                                           (db)var0x.getSource(),
                                                                           dk.a(var0x, "target"),
                                                                           dy.d(var0x, "attribute"),
                                                                           ee.a(var0x, "uuid"),
                                                                           StringArgumentType.getString(var0x, "name"),
                                                                           DoubleArgumentType.getDouble(var0x, "value"),
                                                                           arj.a.b
                                                                        )
                                                                  )
                                                            )
                                                      )
                                                )
                                          )
                                    ))
                                 .then(
                                    dc.a("remove")
                                       .then(
                                          dc.a("uuid", ee.a())
                                             .executes(var0x -> a((db)var0x.getSource(), dk.a(var0x, "target"), dy.d(var0x, "attribute"), ee.a(var0x, "uuid")))
                                       )
                                 ))
                              .then(
                                 dc.a("value")
                                    .then(
                                       dc.a("get")
                                          .then(
                                             ((RequiredArgumentBuilder)dc.a("uuid", ee.a())
                                                   .executes(
                                                      var0x -> a(
                                                            (db)var0x.getSource(), dk.a(var0x, "target"), dy.d(var0x, "attribute"), ee.a(var0x, "uuid"), 1.0
                                                         )
                                                   ))
                                                .then(
                                                   dc.a("scale", DoubleArgumentType.doubleArg())
                                                      .executes(
                                                         var0x -> a(
                                                               (db)var0x.getSource(),
                                                               dk.a(var0x, "target"),
                                                               dy.d(var0x, "attribute"),
                                                               ee.a(var0x, "uuid"),
                                                               DoubleArgumentType.getDouble(var0x, "scale")
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

   private static arh a(aqa var0, arg var1) throws CommandSyntaxException {
      arh _snowman = a(_snowman).dB().a(_snowman);
      if (_snowman == null) {
         throw c.create(_snowman.R(), new of(_snowman.c()));
      } else {
         return _snowman;
      }
   }

   private static aqm a(aqa var0) throws CommandSyntaxException {
      if (!(_snowman instanceof aqm)) {
         throw b.create(_snowman.R());
      } else {
         return (aqm)_snowman;
      }
   }

   private static aqm b(aqa var0, arg var1) throws CommandSyntaxException {
      aqm _snowman = a(_snowman);
      if (!_snowman.dB().b(_snowman)) {
         throw c.create(_snowman.R(), new of(_snowman.c()));
      } else {
         return _snowman;
      }
   }

   private static int a(db var0, aqa var1, arg var2, double var3) throws CommandSyntaxException {
      aqm _snowman = b(_snowman, _snowman);
      double _snowmanx = _snowman.b(_snowman);
      _snowman.a(new of("commands.attribute.value.get.success", new of(_snowman.c()), _snowman.R(), _snowmanx), false);
      return (int)(_snowmanx * _snowman);
   }

   private static int b(db var0, aqa var1, arg var2, double var3) throws CommandSyntaxException {
      aqm _snowman = b(_snowman, _snowman);
      double _snowmanx = _snowman.c(_snowman);
      _snowman.a(new of("commands.attribute.base_value.get.success", new of(_snowman.c()), _snowman.R(), _snowmanx), false);
      return (int)(_snowmanx * _snowman);
   }

   private static int a(db var0, aqa var1, arg var2, UUID var3, double var4) throws CommandSyntaxException {
      aqm _snowman = b(_snowman, _snowman);
      ari _snowmanx = _snowman.dB();
      if (!_snowmanx.a(_snowman, _snowman)) {
         throw d.create(_snowman.R(), new of(_snowman.c()), _snowman);
      } else {
         double _snowmanxx = _snowmanx.b(_snowman, _snowman);
         _snowman.a(new of("commands.attribute.modifier.value.get.success", _snowman, new of(_snowman.c()), _snowman.R(), _snowmanxx), false);
         return (int)(_snowmanxx * _snowman);
      }
   }

   private static int c(db var0, aqa var1, arg var2, double var3) throws CommandSyntaxException {
      a(_snowman, _snowman).a(_snowman);
      _snowman.a(new of("commands.attribute.base_value.set.success", new of(_snowman.c()), _snowman.R(), _snowman), false);
      return 1;
   }

   private static int a(db var0, aqa var1, arg var2, UUID var3, String var4, double var5, arj.a var7) throws CommandSyntaxException {
      arh _snowman = a(_snowman, _snowman);
      arj _snowmanx = new arj(_snowman, _snowman, _snowman, _snowman);
      if (_snowman.a(_snowmanx)) {
         throw e.create(_snowman.R(), new of(_snowman.c()), _snowman);
      } else {
         _snowman.c(_snowmanx);
         _snowman.a(new of("commands.attribute.modifier.add.success", _snowman, new of(_snowman.c()), _snowman.R()), false);
         return 1;
      }
   }

   private static int a(db var0, aqa var1, arg var2, UUID var3) throws CommandSyntaxException {
      arh _snowman = a(_snowman, _snowman);
      if (_snowman.c(_snowman)) {
         _snowman.a(new of("commands.attribute.modifier.remove.success", _snowman, new of(_snowman.c()), _snowman.R()), false);
         return 1;
      } else {
         throw d.create(_snowman.R(), new of(_snowman.c()), _snowman);
      }
   }
}
