import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Locale;

public class yx {
   private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new of("commands.worldborder.center.failed"));
   private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new of("commands.worldborder.set.failed.nochange"));
   private static final SimpleCommandExceptionType c = new SimpleCommandExceptionType(new of("commands.worldborder.set.failed.small."));
   private static final SimpleCommandExceptionType d = new SimpleCommandExceptionType(new of("commands.worldborder.set.failed.big."));
   private static final SimpleCommandExceptionType e = new SimpleCommandExceptionType(new of("commands.worldborder.warning.time.failed"));
   private static final SimpleCommandExceptionType f = new SimpleCommandExceptionType(new of("commands.worldborder.warning.distance.failed"));
   private static final SimpleCommandExceptionType g = new SimpleCommandExceptionType(new of("commands.worldborder.damage.buffer.failed"));
   private static final SimpleCommandExceptionType h = new SimpleCommandExceptionType(new of("commands.worldborder.damage.amount.failed"));

   public static void a(CommandDispatcher<db> var0) {
      _snowman.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a(
                                 "worldborder"
                              )
                              .requires(var0x -> var0x.c(2)))
                           .then(
                              dc.a("add")
                                 .then(
                                    ((RequiredArgumentBuilder)dc.a("distance", FloatArgumentType.floatArg(-6.0E7F, 6.0E7F))
                                          .executes(
                                             var0x -> a(
                                                   (db)var0x.getSource(),
                                                   ((db)var0x.getSource()).e().f().i() + (double)FloatArgumentType.getFloat(var0x, "distance"),
                                                   0L
                                                )
                                          ))
                                       .then(
                                          dc.a("time", IntegerArgumentType.integer(0))
                                             .executes(
                                                var0x -> a(
                                                      (db)var0x.getSource(),
                                                      ((db)var0x.getSource()).e().f().i() + (double)FloatArgumentType.getFloat(var0x, "distance"),
                                                      ((db)var0x.getSource()).e().f().j() + (long)IntegerArgumentType.getInteger(var0x, "time") * 1000L
                                                   )
                                             )
                                       )
                                 )
                           ))
                        .then(
                           dc.a("set")
                              .then(
                                 ((RequiredArgumentBuilder)dc.a("distance", FloatArgumentType.floatArg(-6.0E7F, 6.0E7F))
                                       .executes(var0x -> a((db)var0x.getSource(), (double)FloatArgumentType.getFloat(var0x, "distance"), 0L)))
                                    .then(
                                       dc.a("time", IntegerArgumentType.integer(0))
                                          .executes(
                                             var0x -> a(
                                                   (db)var0x.getSource(),
                                                   (double)FloatArgumentType.getFloat(var0x, "distance"),
                                                   (long)IntegerArgumentType.getInteger(var0x, "time") * 1000L
                                                )
                                          )
                                    )
                              )
                        ))
                     .then(dc.a("center").then(dc.a("pos", eq.a()).executes(var0x -> a((db)var0x.getSource(), eq.a(var0x, "pos"))))))
                  .then(
                     ((LiteralArgumentBuilder)dc.a("damage")
                           .then(
                              dc.a("amount")
                                 .then(
                                    dc.a("damagePerBlock", FloatArgumentType.floatArg(0.0F))
                                       .executes(var0x -> b((db)var0x.getSource(), FloatArgumentType.getFloat(var0x, "damagePerBlock")))
                                 )
                           ))
                        .then(
                           dc.a("buffer")
                              .then(
                                 dc.a("distance", FloatArgumentType.floatArg(0.0F))
                                    .executes(var0x -> a((db)var0x.getSource(), FloatArgumentType.getFloat(var0x, "distance")))
                              )
                        )
                  ))
               .then(dc.a("get").executes(var0x -> a((db)var0x.getSource()))))
            .then(
               ((LiteralArgumentBuilder)dc.a("warning")
                     .then(
                        dc.a("distance")
                           .then(
                              dc.a("distance", IntegerArgumentType.integer(0))
                                 .executes(var0x -> b((db)var0x.getSource(), IntegerArgumentType.getInteger(var0x, "distance")))
                           )
                     ))
                  .then(
                     dc.a("time")
                        .then(
                           dc.a("time", IntegerArgumentType.integer(0))
                              .executes(var0x -> a((db)var0x.getSource(), IntegerArgumentType.getInteger(var0x, "time")))
                        )
                  )
            )
      );
   }

   private static int a(db var0, float var1) throws CommandSyntaxException {
      cfu _snowman = _snowman.e().f();
      if (_snowman.n() == (double)_snowman) {
         throw g.create();
      } else {
         _snowman.b((double)_snowman);
         _snowman.a(new of("commands.worldborder.damage.buffer.success", String.format(Locale.ROOT, "%.2f", _snowman)), true);
         return (int)_snowman;
      }
   }

   private static int b(db var0, float var1) throws CommandSyntaxException {
      cfu _snowman = _snowman.e().f();
      if (_snowman.o() == (double)_snowman) {
         throw h.create();
      } else {
         _snowman.c((double)_snowman);
         _snowman.a(new of("commands.worldborder.damage.amount.success", String.format(Locale.ROOT, "%.2f", _snowman)), true);
         return (int)_snowman;
      }
   }

   private static int a(db var0, int var1) throws CommandSyntaxException {
      cfu _snowman = _snowman.e().f();
      if (_snowman.q() == _snowman) {
         throw e.create();
      } else {
         _snowman.b(_snowman);
         _snowman.a(new of("commands.worldborder.warning.time.success", _snowman), true);
         return _snowman;
      }
   }

   private static int b(db var0, int var1) throws CommandSyntaxException {
      cfu _snowman = _snowman.e().f();
      if (_snowman.r() == _snowman) {
         throw f.create();
      } else {
         _snowman.c(_snowman);
         _snowman.a(new of("commands.worldborder.warning.distance.success", _snowman), true);
         return _snowman;
      }
   }

   private static int a(db var0) {
      double _snowman = _snowman.e().f().i();
      _snowman.a(new of("commands.worldborder.get", String.format(Locale.ROOT, "%.0f", _snowman)), false);
      return afm.c(_snowman + 0.5);
   }

   private static int a(db var0, dcm var1) throws CommandSyntaxException {
      cfu _snowman = _snowman.e().f();
      if (_snowman.a() == (double)_snowman.i && _snowman.b() == (double)_snowman.j) {
         throw a.create();
      } else {
         _snowman.c((double)_snowman.i, (double)_snowman.j);
         _snowman.a(new of("commands.worldborder.center.success", String.format(Locale.ROOT, "%.2f", _snowman.i), String.format("%.2f", _snowman.j)), true);
         return 0;
      }
   }

   private static int a(db var0, double var1, long var3) throws CommandSyntaxException {
      cfu _snowman = _snowman.e().f();
      double _snowmanx = _snowman.i();
      if (_snowmanx == _snowman) {
         throw b.create();
      } else if (_snowman < 1.0) {
         throw c.create();
      } else if (_snowman > 6.0E7) {
         throw d.create();
      } else {
         if (_snowman > 0L) {
            _snowman.a(_snowmanx, _snowman, _snowman);
            if (_snowman > _snowmanx) {
               _snowman.a(new of("commands.worldborder.set.grow", String.format(Locale.ROOT, "%.1f", _snowman), Long.toString(_snowman / 1000L)), true);
            } else {
               _snowman.a(new of("commands.worldborder.set.shrink", String.format(Locale.ROOT, "%.1f", _snowman), Long.toString(_snowman / 1000L)), true);
            }
         } else {
            _snowman.a(_snowman);
            _snowman.a(new of("commands.worldborder.set.immediate", String.format(Locale.ROOT, "%.1f", _snowman)), true);
         }

         return (int)(_snowman - _snowmanx);
      }
   }
}
