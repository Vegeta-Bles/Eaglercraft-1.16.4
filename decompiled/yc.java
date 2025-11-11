import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;

public class yc {
   private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new of("commands.scoreboard.objectives.add.duplicate"));
   private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new of("commands.scoreboard.objectives.display.alreadyEmpty"));
   private static final SimpleCommandExceptionType c = new SimpleCommandExceptionType(new of("commands.scoreboard.objectives.display.alreadySet"));
   private static final SimpleCommandExceptionType d = new SimpleCommandExceptionType(new of("commands.scoreboard.players.enable.failed"));
   private static final SimpleCommandExceptionType e = new SimpleCommandExceptionType(new of("commands.scoreboard.players.enable.invalid"));
   private static final Dynamic2CommandExceptionType f = new Dynamic2CommandExceptionType(
      (var0, var1) -> new of("commands.scoreboard.players.get.null", var0, var1)
   );

   public static void a(CommandDispatcher<db> var0) {
      _snowman.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("scoreboard").requires(var0x -> var0x.c(2)))
               .then(
                  ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("objectives")
                                 .then(dc.a("list").executes(var0x -> b((db)var0x.getSource()))))
                              .then(
                                 dc.a("add")
                                    .then(
                                       dc.a("objective", StringArgumentType.word())
                                          .then(
                                             ((RequiredArgumentBuilder)dc.a("criteria", du.a())
                                                   .executes(
                                                      var0x -> a(
                                                            (db)var0x.getSource(),
                                                            StringArgumentType.getString(var0x, "objective"),
                                                            du.a(var0x, "criteria"),
                                                            new oe(StringArgumentType.getString(var0x, "objective"))
                                                         )
                                                   ))
                                                .then(
                                                   dc.a("displayName", dg.a())
                                                      .executes(
                                                         var0x -> a(
                                                               (db)var0x.getSource(),
                                                               StringArgumentType.getString(var0x, "objective"),
                                                               du.a(var0x, "criteria"),
                                                               dg.a(var0x, "displayName")
                                                            )
                                                      )
                                                )
                                          )
                                    )
                              ))
                           .then(
                              dc.a("modify")
                                 .then(
                                    ((RequiredArgumentBuilder)dc.a("objective", dt.a())
                                          .then(
                                             dc.a("displayname")
                                                .then(
                                                   dc.a("displayName", dg.a())
                                                      .executes(var0x -> a((db)var0x.getSource(), dt.a(var0x, "objective"), dg.a(var0x, "displayName")))
                                                )
                                          ))
                                       .then(a())
                                 )
                           ))
                        .then(dc.a("remove").then(dc.a("objective", dt.a()).executes(var0x -> a((db)var0x.getSource(), dt.a(var0x, "objective"))))))
                     .then(
                        dc.a("setdisplay")
                           .then(
                              ((RequiredArgumentBuilder)dc.a("slot", ea.a()).executes(var0x -> a((db)var0x.getSource(), ea.a(var0x, "slot"))))
                                 .then(dc.a("objective", dt.a()).executes(var0x -> a((db)var0x.getSource(), ea.a(var0x, "slot"), dt.a(var0x, "objective"))))
                           )
                     )
               ))
            .then(
               ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a(
                                          "players"
                                       )
                                       .then(
                                          ((LiteralArgumentBuilder)dc.a("list").executes(var0x -> a((db)var0x.getSource())))
                                             .then(dc.a("target", dz.a()).suggests(dz.a).executes(var0x -> a((db)var0x.getSource(), dz.a(var0x, "target"))))
                                       ))
                                    .then(
                                       dc.a("set")
                                          .then(
                                             dc.a("targets", dz.b())
                                                .suggests(dz.a)
                                                .then(
                                                   dc.a("objective", dt.a())
                                                      .then(
                                                         dc.a("score", IntegerArgumentType.integer())
                                                            .executes(
                                                               var0x -> a(
                                                                     (db)var0x.getSource(),
                                                                     dz.c(var0x, "targets"),
                                                                     dt.b(var0x, "objective"),
                                                                     IntegerArgumentType.getInteger(var0x, "score")
                                                                  )
                                                            )
                                                      )
                                                )
                                          )
                                    ))
                                 .then(
                                    dc.a("get")
                                       .then(
                                          dc.a("target", dz.a())
                                             .suggests(dz.a)
                                             .then(
                                                dc.a("objective", dt.a())
                                                   .executes(var0x -> a((db)var0x.getSource(), dz.a(var0x, "target"), dt.a(var0x, "objective")))
                                             )
                                       )
                                 ))
                              .then(
                                 dc.a("add")
                                    .then(
                                       dc.a("targets", dz.b())
                                          .suggests(dz.a)
                                          .then(
                                             dc.a("objective", dt.a())
                                                .then(
                                                   dc.a("score", IntegerArgumentType.integer(0))
                                                      .executes(
                                                         var0x -> b(
                                                               (db)var0x.getSource(),
                                                               dz.c(var0x, "targets"),
                                                               dt.b(var0x, "objective"),
                                                               IntegerArgumentType.getInteger(var0x, "score")
                                                            )
                                                      )
                                                )
                                          )
                                    )
                              ))
                           .then(
                              dc.a("remove")
                                 .then(
                                    dc.a("targets", dz.b())
                                       .suggests(dz.a)
                                       .then(
                                          dc.a("objective", dt.a())
                                             .then(
                                                dc.a("score", IntegerArgumentType.integer(0))
                                                   .executes(
                                                      var0x -> c(
                                                            (db)var0x.getSource(),
                                                            dz.c(var0x, "targets"),
                                                            dt.b(var0x, "objective"),
                                                            IntegerArgumentType.getInteger(var0x, "score")
                                                         )
                                                   )
                                             )
                                       )
                                 )
                           ))
                        .then(
                           dc.a("reset")
                              .then(
                                 ((RequiredArgumentBuilder)dc.a("targets", dz.b())
                                       .suggests(dz.a)
                                       .executes(var0x -> a((db)var0x.getSource(), dz.c(var0x, "targets"))))
                                    .then(
                                       dc.a("objective", dt.a()).executes(var0x -> b((db)var0x.getSource(), dz.c(var0x, "targets"), dt.a(var0x, "objective")))
                                    )
                              )
                        ))
                     .then(
                        dc.a("enable")
                           .then(
                              dc.a("targets", dz.b())
                                 .suggests(dz.a)
                                 .then(
                                    dc.a("objective", dt.a())
                                       .suggests((var0x, var1) -> a((db)var0x.getSource(), dz.c(var0x, "targets"), var1))
                                       .executes(var0x -> a((db)var0x.getSource(), dz.c(var0x, "targets"), dt.a(var0x, "objective")))
                                 )
                           )
                     ))
                  .then(
                     dc.a("operation")
                        .then(
                           dc.a("targets", dz.b())
                              .suggests(dz.a)
                              .then(
                                 dc.a("targetObjective", dt.a())
                                    .then(
                                       dc.a("operation", dv.a())
                                          .then(
                                             dc.a("source", dz.b())
                                                .suggests(dz.a)
                                                .then(
                                                   dc.a("sourceObjective", dt.a())
                                                      .executes(
                                                         var0x -> a(
                                                               (db)var0x.getSource(),
                                                               dz.c(var0x, "targets"),
                                                               dt.b(var0x, "targetObjective"),
                                                               dv.a(var0x, "operation"),
                                                               dz.c(var0x, "source"),
                                                               dt.a(var0x, "sourceObjective")
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

   private static LiteralArgumentBuilder<db> a() {
      LiteralArgumentBuilder<db> _snowman = dc.a("rendertype");

      for (ddq.a _snowmanx : ddq.a.values()) {
         _snowman.then(dc.a(_snowmanx.a()).executes(var1 -> a((db)var1.getSource(), dt.a(var1, "objective"), _snowman)));
      }

      return _snowman;
   }

   private static CompletableFuture<Suggestions> a(db var0, Collection<String> var1, SuggestionsBuilder var2) {
      List<String> _snowman = Lists.newArrayList();
      ddn _snowmanx = _snowman.j().aH();

      for (ddk _snowmanxx : _snowmanx.c()) {
         if (_snowmanxx.c() == ddq.c) {
            boolean _snowmanxxx = false;

            for (String _snowmanxxxx : _snowman) {
               if (!_snowmanx.b(_snowmanxxxx, _snowmanxx) || _snowmanx.c(_snowmanxxxx, _snowmanxx).g()) {
                  _snowmanxxx = true;
                  break;
               }
            }

            if (_snowmanxxx) {
               _snowman.add(_snowmanxx.b());
            }
         }
      }

      return dd.b(_snowman, _snowman);
   }

   private static int a(db var0, String var1, ddk var2) throws CommandSyntaxException {
      ddn _snowman = _snowman.j().aH();
      if (!_snowman.b(_snowman, _snowman)) {
         throw f.create(_snowman.b(), _snowman);
      } else {
         ddm _snowmanx = _snowman.c(_snowman, _snowman);
         _snowman.a(new of("commands.scoreboard.players.get.success", _snowman, _snowmanx.b(), _snowman.e()), false);
         return _snowmanx.b();
      }
   }

   private static int a(db var0, Collection<String> var1, ddk var2, dv.a var3, Collection<String> var4, ddk var5) throws CommandSyntaxException {
      ddn _snowman = _snowman.j().aH();
      int _snowmanx = 0;

      for (String _snowmanxx : _snowman) {
         ddm _snowmanxxx = _snowman.c(_snowmanxx, _snowman);

         for (String _snowmanxxxx : _snowman) {
            ddm _snowmanxxxxx = _snowman.c(_snowmanxxxx, _snowman);
            _snowman.apply(_snowmanxxx, _snowmanxxxxx);
         }

         _snowmanx += _snowmanxxx.b();
      }

      if (_snowman.size() == 1) {
         _snowman.a(new of("commands.scoreboard.players.operation.success.single", _snowman.e(), _snowman.iterator().next(), _snowmanx), true);
      } else {
         _snowman.a(new of("commands.scoreboard.players.operation.success.multiple", _snowman.e(), _snowman.size()), true);
      }

      return _snowmanx;
   }

   private static int a(db var0, Collection<String> var1, ddk var2) throws CommandSyntaxException {
      if (_snowman.c() != ddq.c) {
         throw e.create();
      } else {
         ddn _snowman = _snowman.j().aH();
         int _snowmanx = 0;

         for (String _snowmanxx : _snowman) {
            ddm _snowmanxxx = _snowman.c(_snowmanxx, _snowman);
            if (_snowmanxxx.g()) {
               _snowmanxxx.a(false);
               _snowmanx++;
            }
         }

         if (_snowmanx == 0) {
            throw d.create();
         } else {
            if (_snowman.size() == 1) {
               _snowman.a(new of("commands.scoreboard.players.enable.success.single", _snowman.e(), _snowman.iterator().next()), true);
            } else {
               _snowman.a(new of("commands.scoreboard.players.enable.success.multiple", _snowman.e(), _snowman.size()), true);
            }

            return _snowmanx;
         }
      }
   }

   private static int a(db var0, Collection<String> var1) {
      ddn _snowman = _snowman.j().aH();

      for (String _snowmanx : _snowman) {
         _snowman.d(_snowmanx, null);
      }

      if (_snowman.size() == 1) {
         _snowman.a(new of("commands.scoreboard.players.reset.all.single", _snowman.iterator().next()), true);
      } else {
         _snowman.a(new of("commands.scoreboard.players.reset.all.multiple", _snowman.size()), true);
      }

      return _snowman.size();
   }

   private static int b(db var0, Collection<String> var1, ddk var2) {
      ddn _snowman = _snowman.j().aH();

      for (String _snowmanx : _snowman) {
         _snowman.d(_snowmanx, _snowman);
      }

      if (_snowman.size() == 1) {
         _snowman.a(new of("commands.scoreboard.players.reset.specific.single", _snowman.e(), _snowman.iterator().next()), true);
      } else {
         _snowman.a(new of("commands.scoreboard.players.reset.specific.multiple", _snowman.e(), _snowman.size()), true);
      }

      return _snowman.size();
   }

   private static int a(db var0, Collection<String> var1, ddk var2, int var3) {
      ddn _snowman = _snowman.j().aH();

      for (String _snowmanx : _snowman) {
         ddm _snowmanxx = _snowman.c(_snowmanx, _snowman);
         _snowmanxx.c(_snowman);
      }

      if (_snowman.size() == 1) {
         _snowman.a(new of("commands.scoreboard.players.set.success.single", _snowman.e(), _snowman.iterator().next(), _snowman), true);
      } else {
         _snowman.a(new of("commands.scoreboard.players.set.success.multiple", _snowman.e(), _snowman.size(), _snowman), true);
      }

      return _snowman * _snowman.size();
   }

   private static int b(db var0, Collection<String> var1, ddk var2, int var3) {
      ddn _snowman = _snowman.j().aH();
      int _snowmanx = 0;

      for (String _snowmanxx : _snowman) {
         ddm _snowmanxxx = _snowman.c(_snowmanxx, _snowman);
         _snowmanxxx.c(_snowmanxxx.b() + _snowman);
         _snowmanx += _snowmanxxx.b();
      }

      if (_snowman.size() == 1) {
         _snowman.a(new of("commands.scoreboard.players.add.success.single", _snowman, _snowman.e(), _snowman.iterator().next(), _snowmanx), true);
      } else {
         _snowman.a(new of("commands.scoreboard.players.add.success.multiple", _snowman, _snowman.e(), _snowman.size()), true);
      }

      return _snowmanx;
   }

   private static int c(db var0, Collection<String> var1, ddk var2, int var3) {
      ddn _snowman = _snowman.j().aH();
      int _snowmanx = 0;

      for (String _snowmanxx : _snowman) {
         ddm _snowmanxxx = _snowman.c(_snowmanxx, _snowman);
         _snowmanxxx.c(_snowmanxxx.b() - _snowman);
         _snowmanx += _snowmanxxx.b();
      }

      if (_snowman.size() == 1) {
         _snowman.a(new of("commands.scoreboard.players.remove.success.single", _snowman, _snowman.e(), _snowman.iterator().next(), _snowmanx), true);
      } else {
         _snowman.a(new of("commands.scoreboard.players.remove.success.multiple", _snowman, _snowman.e(), _snowman.size()), true);
      }

      return _snowmanx;
   }

   private static int a(db var0) {
      Collection<String> _snowman = _snowman.j().aH().e();
      if (_snowman.isEmpty()) {
         _snowman.a(new of("commands.scoreboard.players.list.empty"), false);
      } else {
         _snowman.a(new of("commands.scoreboard.players.list.success", _snowman.size(), ns.a(_snowman)), false);
      }

      return _snowman.size();
   }

   private static int a(db var0, String var1) {
      Map<ddk, ddm> _snowman = _snowman.j().aH().e(_snowman);
      if (_snowman.isEmpty()) {
         _snowman.a(new of("commands.scoreboard.players.list.entity.empty", _snowman), false);
      } else {
         _snowman.a(new of("commands.scoreboard.players.list.entity.success", _snowman, _snowman.size()), false);

         for (Entry<ddk, ddm> _snowmanx : _snowman.entrySet()) {
            _snowman.a(new of("commands.scoreboard.players.list.entity.entry", _snowmanx.getKey().e(), _snowmanx.getValue().b()), false);
         }
      }

      return _snowman.size();
   }

   private static int a(db var0, int var1) throws CommandSyntaxException {
      ddn _snowman = _snowman.j().aH();
      if (_snowman.a(_snowman) == null) {
         throw b.create();
      } else {
         _snowman.a(_snowman, null);
         _snowman.a(new of("commands.scoreboard.objectives.display.cleared", ddn.h()[_snowman]), true);
         return 0;
      }
   }

   private static int a(db var0, int var1, ddk var2) throws CommandSyntaxException {
      ddn _snowman = _snowman.j().aH();
      if (_snowman.a(_snowman) == _snowman) {
         throw c.create();
      } else {
         _snowman.a(_snowman, _snowman);
         _snowman.a(new of("commands.scoreboard.objectives.display.set", ddn.h()[_snowman], _snowman.d()), true);
         return 0;
      }
   }

   private static int a(db var0, ddk var1, nr var2) {
      if (!_snowman.d().equals(_snowman)) {
         _snowman.a(_snowman);
         _snowman.a(new of("commands.scoreboard.objectives.modify.displayname", _snowman.b(), _snowman.e()), true);
      }

      return 0;
   }

   private static int a(db var0, ddk var1, ddq.a var2) {
      if (_snowman.f() != _snowman) {
         _snowman.a(_snowman);
         _snowman.a(new of("commands.scoreboard.objectives.modify.rendertype", _snowman.e()), true);
      }

      return 0;
   }

   private static int a(db var0, ddk var1) {
      ddn _snowman = _snowman.j().aH();
      _snowman.j(_snowman);
      _snowman.a(new of("commands.scoreboard.objectives.remove.success", _snowman.e()), true);
      return _snowman.c().size();
   }

   private static int a(db var0, String var1, ddq var2, nr var3) throws CommandSyntaxException {
      ddn _snowman = _snowman.j().aH();
      if (_snowman.d(_snowman) != null) {
         throw a.create();
      } else if (_snowman.length() > 16) {
         throw dt.a.create(16);
      } else {
         _snowman.a(_snowman, _snowman, _snowman, _snowman.e());
         ddk _snowmanx = _snowman.d(_snowman);
         _snowman.a(new of("commands.scoreboard.objectives.add.success", _snowmanx.e()), true);
         return _snowman.c().size();
      }
   }

   private static int b(db var0) {
      Collection<ddk> _snowman = _snowman.j().aH().c();
      if (_snowman.isEmpty()) {
         _snowman.a(new of("commands.scoreboard.objectives.list.empty"), false);
      } else {
         _snowman.a(new of("commands.scoreboard.objectives.list.success", _snowman.size(), ns.b(_snowman, ddk::e)), false);
      }

      return _snowman.size();
   }
}
