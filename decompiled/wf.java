import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.Collection;
import java.util.List;

public class wf {
   private static final SuggestionProvider<db> a = (var0, var1) -> {
      Collection<y> _snowman = ((db)var0.getSource()).j().aA().a();
      return dd.a(_snowman.stream().map(y::h), var1);
   };

   public static void a(CommandDispatcher<db> var0) {
      _snowman.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("advancement").requires(var0x -> var0x.c(2)))
               .then(
                  dc.a("grant")
                     .then(
                        ((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)dc.a("targets", dk.d())
                                       .then(
                                          dc.a("only")
                                             .then(
                                                ((RequiredArgumentBuilder)dc.a("advancement", dy.a())
                                                      .suggests(a)
                                                      .executes(
                                                         var0x -> a(
                                                               (db)var0x.getSource(), dk.f(var0x, "targets"), wf.a.a, a(dy.a(var0x, "advancement"), wf.b.a)
                                                            )
                                                      ))
                                                   .then(
                                                      dc.a("criterion", StringArgumentType.greedyString())
                                                         .suggests((var0x, var1) -> dd.b(dy.a(var0x, "advancement").f().keySet(), var1))
                                                         .executes(
                                                            var0x -> a(
                                                                  (db)var0x.getSource(),
                                                                  dk.f(var0x, "targets"),
                                                                  wf.a.a,
                                                                  dy.a(var0x, "advancement"),
                                                                  StringArgumentType.getString(var0x, "criterion")
                                                               )
                                                         )
                                                   )
                                             )
                                       ))
                                    .then(
                                       dc.a("from")
                                          .then(
                                             dc.a("advancement", dy.a())
                                                .suggests(a)
                                                .executes(
                                                   var0x -> a((db)var0x.getSource(), dk.f(var0x, "targets"), wf.a.a, a(dy.a(var0x, "advancement"), wf.b.c))
                                                )
                                          )
                                    ))
                                 .then(
                                    dc.a("until")
                                       .then(
                                          dc.a("advancement", dy.a())
                                             .suggests(a)
                                             .executes(var0x -> a((db)var0x.getSource(), dk.f(var0x, "targets"), wf.a.a, a(dy.a(var0x, "advancement"), wf.b.d)))
                                       )
                                 ))
                              .then(
                                 dc.a("through")
                                    .then(
                                       dc.a("advancement", dy.a())
                                          .suggests(a)
                                          .executes(var0x -> a((db)var0x.getSource(), dk.f(var0x, "targets"), wf.a.a, a(dy.a(var0x, "advancement"), wf.b.b)))
                                    )
                              ))
                           .then(
                              dc.a("everything")
                                 .executes(var0x -> a((db)var0x.getSource(), dk.f(var0x, "targets"), wf.a.a, ((db)var0x.getSource()).j().aA().a()))
                           )
                     )
               ))
            .then(
               dc.a("revoke")
                  .then(
                     ((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)dc.a("targets", dk.d())
                                    .then(
                                       dc.a("only")
                                          .then(
                                             ((RequiredArgumentBuilder)dc.a("advancement", dy.a())
                                                   .suggests(a)
                                                   .executes(
                                                      var0x -> a((db)var0x.getSource(), dk.f(var0x, "targets"), wf.a.b, a(dy.a(var0x, "advancement"), wf.b.a))
                                                   ))
                                                .then(
                                                   dc.a("criterion", StringArgumentType.greedyString())
                                                      .suggests((var0x, var1) -> dd.b(dy.a(var0x, "advancement").f().keySet(), var1))
                                                      .executes(
                                                         var0x -> a(
                                                               (db)var0x.getSource(),
                                                               dk.f(var0x, "targets"),
                                                               wf.a.b,
                                                               dy.a(var0x, "advancement"),
                                                               StringArgumentType.getString(var0x, "criterion")
                                                            )
                                                      )
                                                )
                                          )
                                    ))
                                 .then(
                                    dc.a("from")
                                       .then(
                                          dc.a("advancement", dy.a())
                                             .suggests(a)
                                             .executes(var0x -> a((db)var0x.getSource(), dk.f(var0x, "targets"), wf.a.b, a(dy.a(var0x, "advancement"), wf.b.c)))
                                       )
                                 ))
                              .then(
                                 dc.a("until")
                                    .then(
                                       dc.a("advancement", dy.a())
                                          .suggests(a)
                                          .executes(var0x -> a((db)var0x.getSource(), dk.f(var0x, "targets"), wf.a.b, a(dy.a(var0x, "advancement"), wf.b.d)))
                                    )
                              ))
                           .then(
                              dc.a("through")
                                 .then(
                                    dc.a("advancement", dy.a())
                                       .suggests(a)
                                       .executes(var0x -> a((db)var0x.getSource(), dk.f(var0x, "targets"), wf.a.b, a(dy.a(var0x, "advancement"), wf.b.b)))
                                 )
                           ))
                        .then(
                           dc.a("everything").executes(var0x -> a((db)var0x.getSource(), dk.f(var0x, "targets"), wf.a.b, ((db)var0x.getSource()).j().aA().a()))
                        )
                  )
            )
      );
   }

   private static int a(db var0, Collection<aah> var1, wf.a var2, Collection<y> var3) {
      int _snowman = 0;

      for (aah _snowmanx : _snowman) {
         _snowman += _snowman.a(_snowmanx, _snowman);
      }

      if (_snowman == 0) {
         if (_snowman.size() == 1) {
            if (_snowman.size() == 1) {
               throw new cz(new of(_snowman.a() + ".one.to.one.failure", _snowman.iterator().next().j(), _snowman.iterator().next().d()));
            } else {
               throw new cz(new of(_snowman.a() + ".one.to.many.failure", _snowman.iterator().next().j(), _snowman.size()));
            }
         } else if (_snowman.size() == 1) {
            throw new cz(new of(_snowman.a() + ".many.to.one.failure", _snowman.size(), _snowman.iterator().next().d()));
         } else {
            throw new cz(new of(_snowman.a() + ".many.to.many.failure", _snowman.size(), _snowman.size()));
         }
      } else {
         if (_snowman.size() == 1) {
            if (_snowman.size() == 1) {
               _snowman.a(new of(_snowman.a() + ".one.to.one.success", _snowman.iterator().next().j(), _snowman.iterator().next().d()), true);
            } else {
               _snowman.a(new of(_snowman.a() + ".one.to.many.success", _snowman.iterator().next().j(), _snowman.size()), true);
            }
         } else if (_snowman.size() == 1) {
            _snowman.a(new of(_snowman.a() + ".many.to.one.success", _snowman.size(), _snowman.iterator().next().d()), true);
         } else {
            _snowman.a(new of(_snowman.a() + ".many.to.many.success", _snowman.size(), _snowman.size()), true);
         }

         return _snowman;
      }
   }

   private static int a(db var0, Collection<aah> var1, wf.a var2, y var3, String var4) {
      int _snowman = 0;
      if (!_snowman.f().containsKey(_snowman)) {
         throw new cz(new of("commands.advancement.criterionNotFound", _snowman.j(), _snowman));
      } else {
         for (aah _snowmanx : _snowman) {
            if (_snowman.a(_snowmanx, _snowman, _snowman)) {
               _snowman++;
            }
         }

         if (_snowman == 0) {
            if (_snowman.size() == 1) {
               throw new cz(new of(_snowman.a() + ".criterion.to.one.failure", _snowman, _snowman.j(), _snowman.iterator().next().d()));
            } else {
               throw new cz(new of(_snowman.a() + ".criterion.to.many.failure", _snowman, _snowman.j(), _snowman.size()));
            }
         } else {
            if (_snowman.size() == 1) {
               _snowman.a(new of(_snowman.a() + ".criterion.to.one.success", _snowman, _snowman.j(), _snowman.iterator().next().d()), true);
            } else {
               _snowman.a(new of(_snowman.a() + ".criterion.to.many.success", _snowman, _snowman.j(), _snowman.size()), true);
            }

            return _snowman;
         }
      }
   }

   private static List<y> a(y var0, wf.b var1) {
      List<y> _snowman = Lists.newArrayList();
      if (_snowman.f) {
         for (y _snowmanx = _snowman.b(); _snowmanx != null; _snowmanx = _snowmanx.b()) {
            _snowman.add(_snowmanx);
         }
      }

      _snowman.add(_snowman);
      if (_snowman.g) {
         a(_snowman, _snowman);
      }

      return _snowman;
   }

   private static void a(y var0, List<y> var1) {
      for (y _snowman : _snowman.e()) {
         _snowman.add(_snowman);
         a(_snowman, _snowman);
      }
   }

   static enum a {
      a("grant") {
         @Override
         protected boolean a(aah var1, y var2) {
            aa _snowman = _snowman.J().b(_snowman);
            if (_snowman.a()) {
               return false;
            } else {
               for (String _snowmanx : _snowman.e()) {
                  _snowman.J().a(_snowman, _snowmanx);
               }

               return true;
            }
         }

         @Override
         protected boolean a(aah var1, y var2, String var3) {
            return _snowman.J().a(_snowman, _snowman);
         }
      },
      b("revoke") {
         @Override
         protected boolean a(aah var1, y var2) {
            aa _snowman = _snowman.J().b(_snowman);
            if (!_snowman.b()) {
               return false;
            } else {
               for (String _snowmanx : _snowman.f()) {
                  _snowman.J().b(_snowman, _snowmanx);
               }

               return true;
            }
         }

         @Override
         protected boolean a(aah var1, y var2, String var3) {
            return _snowman.J().b(_snowman, _snowman);
         }
      };

      private final String c;

      private a(String var3) {
         this.c = "commands.advancement." + _snowman;
      }

      public int a(aah var1, Iterable<y> var2) {
         int _snowman = 0;

         for (y _snowmanx : _snowman) {
            if (this.a(_snowman, _snowmanx)) {
               _snowman++;
            }
         }

         return _snowman;
      }

      protected abstract boolean a(aah var1, y var2);

      protected abstract boolean a(aah var1, y var2, String var3);

      protected String a() {
         return this.c;
      }
   }

   static enum b {
      a(false, false),
      b(true, true),
      c(false, true),
      d(true, false),
      e(true, true);

      private final boolean f;
      private final boolean g;

      private b(boolean var3, boolean var4) {
         this.f = _snowman;
         this.g = _snowman;
      }
   }
}
