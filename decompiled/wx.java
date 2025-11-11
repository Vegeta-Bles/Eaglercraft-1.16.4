import com.google.common.collect.Lists;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ResultConsumer;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.OptionalInt;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.IntFunction;

public class wx {
   private static final Dynamic2CommandExceptionType a = new Dynamic2CommandExceptionType((var0, var1) -> new of("commands.execute.blocks.toobig", var0, var1));
   private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new of("commands.execute.conditional.fail"));
   private static final DynamicCommandExceptionType c = new DynamicCommandExceptionType(var0 -> new of("commands.execute.conditional.fail_count", var0));
   private static final BinaryOperator<ResultConsumer<db>> d = (var0, var1) -> (var2, var3, var4) -> {
         var0.onCommandComplete(var2, var3, var4);
         var1.onCommandComplete(var2, var3, var4);
      };
   private static final SuggestionProvider<db> e = (var0, var1) -> {
      cza _snowman = ((db)var0.getSource()).j().aK();
      return dd.a(_snowman.a(), var1);
   };

   public static void a(CommandDispatcher<db> var0) {
      LiteralCommandNode<db> _snowman = _snowman.register((LiteralArgumentBuilder)dc.a("execute").requires(var0x -> var0x.c(2)));
      _snowman.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a(
                                                   "execute"
                                                )
                                                .requires(var0x -> var0x.c(2)))
                                             .then(dc.a("run").redirect(_snowman.getRoot())))
                                          .then(a(_snowman, dc.a("if_"), true)))
                                       .then(a(_snowman, dc.a("unless"), false)))
                                    .then(dc.a("as").then(dc.a("targets", dk.b()).fork(_snowman, var0x -> {
                                       List<db> _snowmanx = Lists.newArrayList();

                                       for (aqa _snowmanx : dk.c(var0x, "targets")) {
                                          _snowmanx.add(((db)var0x.getSource()).a(_snowmanx));
                                       }

                                       return _snowmanx;
                                    }))))
                                 .then(dc.a("at").then(dc.a("targets", dk.b()).fork(_snowman, var0x -> {
                                    List<db> _snowmanx = Lists.newArrayList();

                                    for (aqa _snowmanx : dk.c(var0x, "targets")) {
                                       _snowmanx.add(((db)var0x.getSource()).a((aag)_snowmanx.l).a(_snowmanx.cA()).a(_snowmanx.bi()));
                                    }

                                    return _snowmanx;
                                 }))))
                              .then(((LiteralArgumentBuilder)dc.a("store").then(a(_snowman, dc.a("result"), true))).then(a(_snowman, dc.a("success"), false))))
                           .then(
                              ((LiteralArgumentBuilder)dc.a("positioned")
                                    .then(dc.a("pos", er.a()).redirect(_snowman, var0x -> ((db)var0x.getSource()).a(er.a(var0x, "pos")).a(dj.a.a))))
                                 .then(dc.a("as").then(dc.a("targets", dk.b()).fork(_snowman, var0x -> {
                                    List<db> _snowmanx = Lists.newArrayList();

                                    for (aqa _snowmanx : dk.c(var0x, "targets")) {
                                       _snowmanx.add(((db)var0x.getSource()).a(_snowmanx.cA()));
                                    }

                                    return _snowmanx;
                                 })))
                           ))
                        .then(
                           ((LiteralArgumentBuilder)dc.a("rotated")
                                 .then(dc.a("rot", eo.a()).redirect(_snowman, var0x -> ((db)var0x.getSource()).a(eo.a(var0x, "rot").b((db)var0x.getSource())))))
                              .then(dc.a("as").then(dc.a("targets", dk.b()).fork(_snowman, var0x -> {
                                 List<db> _snowmanx = Lists.newArrayList();

                                 for (aqa _snowmanx : dk.c(var0x, "targets")) {
                                    _snowmanx.add(((db)var0x.getSource()).a(_snowmanx.bi()));
                                 }

                                 return _snowmanx;
                              })))
                        ))
                     .then(
                        ((LiteralArgumentBuilder)dc.a("facing").then(dc.a("entity").then(dc.a("targets", dk.b()).then(dc.a("anchor", dj.a()).fork(_snowman, var0x -> {
                           List<db> _snowmanx = Lists.newArrayList();
                           dj.a _snowmanx = dj.a(var0x, "anchor");

                           for (aqa _snowmanxx : dk.c(var0x, "targets")) {
                              _snowmanx.add(((db)var0x.getSource()).a(_snowmanxx, _snowmanx));
                           }

                           return _snowmanx;
                        }))))).then(dc.a("pos", er.a()).redirect(_snowman, var0x -> ((db)var0x.getSource()).b(er.a(var0x, "pos"))))
                     ))
                  .then(
                     dc.a("align")
                        .then(dc.a("axes", ep.a()).redirect(_snowman, var0x -> ((db)var0x.getSource()).a(((db)var0x.getSource()).d().a(ep.a(var0x, "axes")))))
                  ))
               .then(dc.a("anchored").then(dc.a("anchor", dj.a()).redirect(_snowman, var0x -> ((db)var0x.getSource()).a(dj.a(var0x, "anchor"))))))
            .then(dc.a("in").then(dc.a("dimension", di.a()).redirect(_snowman, var0x -> ((db)var0x.getSource()).a(di.a(var0x, "dimension")))))
      );
   }

   private static ArgumentBuilder<db, ?> a(LiteralCommandNode<db> var0, LiteralArgumentBuilder<db> var1, boolean var2) {
      _snowman.then(
         dc.a("score")
            .then(
               dc.a("targets", dz.b())
                  .suggests(dz.a)
                  .then(dc.a("objective", dt.a()).redirect(_snowman, var1x -> a((db)var1x.getSource(), dz.c(var1x, "targets"), dt.a(var1x, "objective"), _snowman)))
            )
      );
      _snowman.then(
         dc.a("bossbar")
            .then(
               ((RequiredArgumentBuilder)dc.a("id", dy.a())
                     .suggests(wk.a)
                     .then(dc.a("value").redirect(_snowman, var1x -> a((db)var1x.getSource(), wk.a(var1x), true, _snowman))))
                  .then(dc.a("max").redirect(_snowman, var1x -> a((db)var1x.getSource(), wk.a(var1x), false, _snowman)))
            )
      );

      for (za.c _snowman : za.b) {
         _snowman.a(
            _snowman,
            var3 -> var3.then(
                  ((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)dc.a(
                                       "path", dr.a()
                                    )
                                    .then(
                                       dc.a("int")
                                          .then(
                                             dc.a("scale", DoubleArgumentType.doubleArg())
                                                .redirect(
                                                   _snowman,
                                                   var2x -> a(
                                                         (db)var2x.getSource(),
                                                         _snowman.a(var2x),
                                                         dr.a(var2x, "path"),
                                                         var1x -> mi.a((int)((double)var1x * DoubleArgumentType.getDouble(var2x, "scale"))),
                                                         _snowman
                                                      )
                                                )
                                          )
                                    ))
                                 .then(
                                    dc.a("float")
                                       .then(
                                          dc.a("scale", DoubleArgumentType.doubleArg())
                                             .redirect(
                                                _snowman,
                                                var2x -> a(
                                                      (db)var2x.getSource(),
                                                      _snowman.a(var2x),
                                                      dr.a(var2x, "path"),
                                                      var1x -> mg.a((float)((double)var1x * DoubleArgumentType.getDouble(var2x, "scale"))),
                                                      _snowman
                                                   )
                                             )
                                       )
                                 ))
                              .then(
                                 dc.a("short")
                                    .then(
                                       dc.a("scale", DoubleArgumentType.doubleArg())
                                          .redirect(
                                             _snowman,
                                             var2x -> a(
                                                   (db)var2x.getSource(),
                                                   _snowman.a(var2x),
                                                   dr.a(var2x, "path"),
                                                   var1x -> mr.a((short)((int)((double)var1x * DoubleArgumentType.getDouble(var2x, "scale")))),
                                                   _snowman
                                                )
                                          )
                                    )
                              ))
                           .then(
                              dc.a("long")
                                 .then(
                                    dc.a("scale", DoubleArgumentType.doubleArg())
                                       .redirect(
                                          _snowman,
                                          var2x -> a(
                                                (db)var2x.getSource(),
                                                _snowman.a(var2x),
                                                dr.a(var2x, "path"),
                                                var1x -> ml.a((long)((double)var1x * DoubleArgumentType.getDouble(var2x, "scale"))),
                                                _snowman
                                             )
                                       )
                                 )
                           ))
                        .then(
                           dc.a("double")
                              .then(
                                 dc.a("scale", DoubleArgumentType.doubleArg())
                                    .redirect(
                                       _snowman,
                                       var2x -> a(
                                             (db)var2x.getSource(),
                                             _snowman.a(var2x),
                                             dr.a(var2x, "path"),
                                             var1x -> me.a((double)var1x * DoubleArgumentType.getDouble(var2x, "scale")),
                                             _snowman
                                          )
                                    )
                              )
                        ))
                     .then(
                        dc.a("byte")
                           .then(
                              dc.a("scale", DoubleArgumentType.doubleArg())
                                 .redirect(
                                    _snowman,
                                    var2x -> a(
                                          (db)var2x.getSource(),
                                          _snowman.a(var2x),
                                          dr.a(var2x, "path"),
                                          var1x -> mb.a((byte)((int)((double)var1x * DoubleArgumentType.getDouble(var2x, "scale")))),
                                          _snowman
                                       )
                                 )
                           )
                     )
               )
         );
      }

      return _snowman;
   }

   private static db a(db var0, Collection<String> var1, ddk var2, boolean var3) {
      ddn _snowman = _snowman.j().aH();
      return _snowman.a((var4x, var5, var6) -> {
         for (String _snowmanx : _snowman) {
            ddm _snowmanx = _snowman.c(_snowmanx, _snowman);
            int _snowmanxx = _snowman ? var6 : (var5 ? 1 : 0);
            _snowmanx.c(_snowmanxx);
         }
      }, d);
   }

   private static db a(db var0, wc var1, boolean var2, boolean var3) {
      return _snowman.a((var3x, var4, var5) -> {
         int _snowman = _snowman ? var5 : (var4 ? 1 : 0);
         if (_snowman) {
            _snowman.a(_snowman);
         } else {
            _snowman.b(_snowman);
         }
      }, d);
   }

   private static db a(db var0, yz var1, dr.h var2, IntFunction<mt> var3, boolean var4) {
      return _snowman.a((var4x, var5, var6) -> {
         try {
            md _snowman = _snowman.a();
            int _snowmanx = _snowman ? var6 : (var5 ? 1 : 0);
            _snowman.b(_snowman, () -> _snowman.apply(_snowman));
            _snowman.a(_snowman);
         } catch (CommandSyntaxException var9) {
         }
      }, d);
   }

   private static ArgumentBuilder<db, ?> a(CommandNode<db> var0, LiteralArgumentBuilder<db> var1, boolean var2) {
      ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)_snowman.then(
                     dc.a("block")
                        .then(
                           dc.a("pos", ek.a())
                              .then(
                                 a(
                                    _snowman,
                                    dc.a("block", eg.a()),
                                    _snowman,
                                    var0x -> eg.a(var0x, "block").test(new cel(((db)var0x.getSource()).e(), ek.a(var0x, "pos"), true))
                                 )
                              )
                        )
                  ))
                  .then(
                     dc.a("score")
                        .then(
                           dc.a("target", dz.a())
                              .suggests(dz.a)
                              .then(
                                 ((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)dc.a(
                                                      "targetObjective", dt.a()
                                                   )
                                                   .then(
                                                      dc.a("=")
                                                         .then(
                                                            dc.a("source", dz.a())
                                                               .suggests(dz.a)
                                                               .then(a(_snowman, dc.a("sourceObjective", dt.a()), _snowman, var0x -> a(var0x, Integer::equals)))
                                                         )
                                                   ))
                                                .then(
                                                   dc.a("<")
                                                      .then(
                                                         dc.a("source", dz.a())
                                                            .suggests(dz.a)
                                                            .then(
                                                               a(
                                                                  _snowman,
                                                                  dc.a("sourceObjective", dt.a()),
                                                                  _snowman,
                                                                  var0x -> a(var0x, (BiPredicate<Integer, Integer>)((var0xx, var1x) -> var0xx < var1x))
                                                               )
                                                            )
                                                      )
                                                ))
                                             .then(
                                                dc.a("<=")
                                                   .then(
                                                      dc.a("source", dz.a())
                                                         .suggests(dz.a)
                                                         .then(
                                                            a(
                                                               _snowman,
                                                               dc.a("sourceObjective", dt.a()),
                                                               _snowman,
                                                               var0x -> a(var0x, (BiPredicate<Integer, Integer>)((var0xx, var1x) -> var0xx <= var1x))
                                                            )
                                                         )
                                                   )
                                             ))
                                          .then(
                                             dc.a(">")
                                                .then(
                                                   dc.a("source", dz.a())
                                                      .suggests(dz.a)
                                                      .then(
                                                         a(
                                                            _snowman,
                                                            dc.a("sourceObjective", dt.a()),
                                                            _snowman,
                                                            var0x -> a(var0x, (BiPredicate<Integer, Integer>)((var0xx, var1x) -> var0xx > var1x))
                                                         )
                                                      )
                                                )
                                          ))
                                       .then(
                                          dc.a(">=")
                                             .then(
                                                dc.a("source", dz.a())
                                                   .suggests(dz.a)
                                                   .then(
                                                      a(
                                                         _snowman,
                                                         dc.a("sourceObjective", dt.a()),
                                                         _snowman,
                                                         var0x -> a(var0x, (BiPredicate<Integer, Integer>)((var0xx, var1x) -> var0xx >= var1x))
                                                      )
                                                   )
                                             )
                                       ))
                                    .then(dc.a("matches").then(a(_snowman, dc.a("range", dx.a()), _snowman, var0x -> a(var0x, dx.b.a(var0x, "range")))))
                              )
                        )
                  ))
               .then(
                  dc.a("blocks")
                     .then(
                        dc.a("start", ek.a())
                           .then(
                              dc.a("end", ek.a())
                                 .then(
                                    ((RequiredArgumentBuilder)dc.a("destination", ek.a()).then(a(_snowman, dc.a("all"), _snowman, false)))
                                       .then(a(_snowman, dc.a("masked"), _snowman, true))
                                 )
                           )
                     )
               ))
            .then(
               dc.a("entity")
                  .then(
                     ((RequiredArgumentBuilder)dc.a("entities", dk.b()).fork(_snowman, var1x -> a(var1x, _snowman, !dk.c(var1x, "entities").isEmpty())))
                        .executes(a(_snowman, (wx.a)(var0x -> dk.c(var0x, "entities").size())))
                  )
            ))
         .then(dc.a("predicate").then(a(_snowman, dc.a("predicate", dy.a()).suggests(e), _snowman, var0x -> a((db)var0x.getSource(), dy.c(var0x, "predicate")))));

      for (za.c _snowman : za.c) {
         _snowman.then(
            _snowman.a(
               dc.a("data"),
               var3 -> var3.then(
                     ((RequiredArgumentBuilder)dc.a("path", dr.a()).fork(_snowman, var2x -> a(var2x, _snowman, a(_snowman.a(var2x), dr.a(var2x, "path")) > 0)))
                        .executes(a(_snowman, (wx.a)(var1x -> a(_snowman.a(var1x), dr.a(var1x, "path")))))
                  )
            )
         );
      }

      return _snowman;
   }

   private static Command<db> a(boolean var0, wx.a var1) {
      return _snowman ? var1x -> {
         int _snowman = _snowman.test(var1x);
         if (_snowman > 0) {
            ((db)var1x.getSource()).a(new of("commands.execute.conditional.pass_count", _snowman), false);
            return _snowman;
         } else {
            throw b.create();
         }
      } : var1x -> {
         int _snowman = _snowman.test(var1x);
         if (_snowman == 0) {
            ((db)var1x.getSource()).a(new of("commands.execute.conditional.pass"), false);
            return 1;
         } else {
            throw c.create(_snowman);
         }
      };
   }

   private static int a(yz var0, dr.h var1) throws CommandSyntaxException {
      return _snowman.b(_snowman.a());
   }

   private static boolean a(CommandContext<db> var0, BiPredicate<Integer, Integer> var1) throws CommandSyntaxException {
      String _snowman = dz.a(_snowman, "target");
      ddk _snowmanx = dt.a(_snowman, "targetObjective");
      String _snowmanxx = dz.a(_snowman, "source");
      ddk _snowmanxxx = dt.a(_snowman, "sourceObjective");
      ddn _snowmanxxxx = ((db)_snowman.getSource()).j().aH();
      if (_snowmanxxxx.b(_snowman, _snowmanx) && _snowmanxxxx.b(_snowmanxx, _snowmanxxx)) {
         ddm _snowmanxxxxx = _snowmanxxxx.c(_snowman, _snowmanx);
         ddm _snowmanxxxxxx = _snowmanxxxx.c(_snowmanxx, _snowmanxxx);
         return _snowman.test(_snowmanxxxxx.b(), _snowmanxxxxxx.b());
      } else {
         return false;
      }
   }

   private static boolean a(CommandContext<db> var0, bz.d var1) throws CommandSyntaxException {
      String _snowman = dz.a(_snowman, "target");
      ddk _snowmanx = dt.a(_snowman, "targetObjective");
      ddn _snowmanxx = ((db)_snowman.getSource()).j().aH();
      return !_snowmanxx.b(_snowman, _snowmanx) ? false : _snowman.d(_snowmanxx.c(_snowman, _snowmanx).b());
   }

   private static boolean a(db var0, dbo var1) {
      aag _snowman = _snowman.e();
      cyv.a _snowmanx = new cyv.a(_snowman).a(dbc.f, _snowman.d()).b(dbc.a, _snowman.f());
      return _snowman.test(_snowmanx.a(dbb.c));
   }

   private static Collection<db> a(CommandContext<db> var0, boolean var1, boolean var2) {
      return (Collection<db>)(_snowman == _snowman ? Collections.singleton((db)_snowman.getSource()) : Collections.emptyList());
   }

   private static ArgumentBuilder<db, ?> a(CommandNode<db> var0, ArgumentBuilder<db, ?> var1, boolean var2, wx.b var3) {
      return _snowman.fork(_snowman, var2x -> a(var2x, _snowman, _snowman.test(var2x))).executes(var2x -> {
         if (_snowman == _snowman.test(var2x)) {
            ((db)var2x.getSource()).a(new of("commands.execute.conditional.pass"), false);
            return 1;
         } else {
            throw b.create();
         }
      });
   }

   private static ArgumentBuilder<db, ?> a(CommandNode<db> var0, ArgumentBuilder<db, ?> var1, boolean var2, boolean var3) {
      return _snowman.fork(_snowman, var2x -> a(var2x, _snowman, c(var2x, _snowman).isPresent())).executes(_snowman ? var1x -> a(var1x, _snowman) : var1x -> b(var1x, _snowman));
   }

   private static int a(CommandContext<db> var0, boolean var1) throws CommandSyntaxException {
      OptionalInt _snowman = c(_snowman, _snowman);
      if (_snowman.isPresent()) {
         ((db)_snowman.getSource()).a(new of("commands.execute.conditional.pass_count", _snowman.getAsInt()), false);
         return _snowman.getAsInt();
      } else {
         throw b.create();
      }
   }

   private static int b(CommandContext<db> var0, boolean var1) throws CommandSyntaxException {
      OptionalInt _snowman = c(_snowman, _snowman);
      if (_snowman.isPresent()) {
         throw c.create(_snowman.getAsInt());
      } else {
         ((db)_snowman.getSource()).a(new of("commands.execute.conditional.pass"), false);
         return 1;
      }
   }

   private static OptionalInt c(CommandContext<db> var0, boolean var1) throws CommandSyntaxException {
      return a(((db)_snowman.getSource()).e(), ek.a(_snowman, "start"), ek.a(_snowman, "end"), ek.a(_snowman, "destination"), _snowman);
   }

   private static OptionalInt a(aag var0, fx var1, fx var2, fx var3, boolean var4) throws CommandSyntaxException {
      cra _snowman = new cra(_snowman, _snowman);
      cra _snowmanx = new cra(_snowman, _snowman.a(_snowman.c()));
      fx _snowmanxx = new fx(_snowmanx.a - _snowman.a, _snowmanx.b - _snowman.b, _snowmanx.c - _snowman.c);
      int _snowmanxxx = _snowman.d() * _snowman.e() * _snowman.f();
      if (_snowmanxxx > 32768) {
         throw a.create(32768, _snowmanxxx);
      } else {
         int _snowmanxxxx = 0;

         for (int _snowmanxxxxx = _snowman.c; _snowmanxxxxx <= _snowman.f; _snowmanxxxxx++) {
            for (int _snowmanxxxxxx = _snowman.b; _snowmanxxxxxx <= _snowman.e; _snowmanxxxxxx++) {
               for (int _snowmanxxxxxxx = _snowman.a; _snowmanxxxxxxx <= _snowman.d; _snowmanxxxxxxx++) {
                  fx _snowmanxxxxxxxx = new fx(_snowmanxxxxxxx, _snowmanxxxxxx, _snowmanxxxxx);
                  fx _snowmanxxxxxxxxx = _snowmanxxxxxxxx.a(_snowmanxx);
                  ceh _snowmanxxxxxxxxxx = _snowman.d_(_snowmanxxxxxxxx);
                  if (!_snowman || !_snowmanxxxxxxxxxx.a(bup.a)) {
                     if (_snowmanxxxxxxxxxx != _snowman.d_(_snowmanxxxxxxxxx)) {
                        return OptionalInt.empty();
                     }

                     ccj _snowmanxxxxxxxxxxx = _snowman.c(_snowmanxxxxxxxx);
                     ccj _snowmanxxxxxxxxxxxx = _snowman.c(_snowmanxxxxxxxxx);
                     if (_snowmanxxxxxxxxxxx != null) {
                        if (_snowmanxxxxxxxxxxxx == null) {
                           return OptionalInt.empty();
                        }

                        md _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.a(new md());
                        _snowmanxxxxxxxxxxxxx.r("x");
                        _snowmanxxxxxxxxxxxxx.r("y");
                        _snowmanxxxxxxxxxxxxx.r("z");
                        md _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx.a(new md());
                        _snowmanxxxxxxxxxxxxxx.r("x");
                        _snowmanxxxxxxxxxxxxxx.r("y");
                        _snowmanxxxxxxxxxxxxxx.r("z");
                        if (!_snowmanxxxxxxxxxxxxx.equals(_snowmanxxxxxxxxxxxxxx)) {
                           return OptionalInt.empty();
                        }
                     }

                     _snowmanxxxx++;
                  }
               }
            }
         }

         return OptionalInt.of(_snowmanxxxx);
      }
   }

   @FunctionalInterface
   interface a {
      int test(CommandContext<db> var1) throws CommandSyntaxException;
   }

   @FunctionalInterface
   interface b {
      boolean test(CommandContext<db> var1) throws CommandSyntaxException;
   }
}
