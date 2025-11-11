import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Deque;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class wm {
   private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new of("commands.clone.overlap"));
   private static final Dynamic2CommandExceptionType c = new Dynamic2CommandExceptionType((var0, var1) -> new of("commands.clone.toobig", var0, var1));
   private static final SimpleCommandExceptionType d = new SimpleCommandExceptionType(new of("commands.clone.failed"));
   public static final Predicate<cel> a = var0 -> !var0.a().g();

   public static void a(CommandDispatcher<db> var0) {
      _snowman.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("clone").requires(var0x -> var0x.c(2)))
            .then(
               dc.a("begin", ek.a())
                  .then(
                     dc.a("end", ek.a())
                        .then(
                           ((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)dc.a("destination", ek.a())
                                       .executes(
                                          var0x -> a(
                                                (db)var0x.getSource(),
                                                ek.a(var0x, "begin"),
                                                ek.a(var0x, "end"),
                                                ek.a(var0x, "destination"),
                                                var0xx -> true,
                                                wm.b.c
                                             )
                                       ))
                                    .then(
                                       ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("replace")
                                                   .executes(
                                                      var0x -> a(
                                                            (db)var0x.getSource(),
                                                            ek.a(var0x, "begin"),
                                                            ek.a(var0x, "end"),
                                                            ek.a(var0x, "destination"),
                                                            var0xx -> true,
                                                            wm.b.c
                                                         )
                                                   ))
                                                .then(
                                                   dc.a("force")
                                                      .executes(
                                                         var0x -> a(
                                                               (db)var0x.getSource(),
                                                               ek.a(var0x, "begin"),
                                                               ek.a(var0x, "end"),
                                                               ek.a(var0x, "destination"),
                                                               var0xx -> true,
                                                               wm.b.a
                                                            )
                                                      )
                                                ))
                                             .then(
                                                dc.a("move")
                                                   .executes(
                                                      var0x -> a(
                                                            (db)var0x.getSource(),
                                                            ek.a(var0x, "begin"),
                                                            ek.a(var0x, "end"),
                                                            ek.a(var0x, "destination"),
                                                            var0xx -> true,
                                                            wm.b.b
                                                         )
                                                   )
                                             ))
                                          .then(
                                             dc.a("normal")
                                                .executes(
                                                   var0x -> a(
                                                         (db)var0x.getSource(),
                                                         ek.a(var0x, "begin"),
                                                         ek.a(var0x, "end"),
                                                         ek.a(var0x, "destination"),
                                                         var0xx -> true,
                                                         wm.b.c
                                                      )
                                                )
                                          )
                                    ))
                                 .then(
                                    ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("masked")
                                                .executes(
                                                   var0x -> a(
                                                         (db)var0x.getSource(), ek.a(var0x, "begin"), ek.a(var0x, "end"), ek.a(var0x, "destination"), a, wm.b.c
                                                      )
                                                ))
                                             .then(
                                                dc.a("force")
                                                   .executes(
                                                      var0x -> a(
                                                            (db)var0x.getSource(),
                                                            ek.a(var0x, "begin"),
                                                            ek.a(var0x, "end"),
                                                            ek.a(var0x, "destination"),
                                                            a,
                                                            wm.b.a
                                                         )
                                                   )
                                             ))
                                          .then(
                                             dc.a("move")
                                                .executes(
                                                   var0x -> a(
                                                         (db)var0x.getSource(), ek.a(var0x, "begin"), ek.a(var0x, "end"), ek.a(var0x, "destination"), a, wm.b.b
                                                      )
                                                )
                                          ))
                                       .then(
                                          dc.a("normal")
                                             .executes(
                                                var0x -> a(
                                                      (db)var0x.getSource(), ek.a(var0x, "begin"), ek.a(var0x, "end"), ek.a(var0x, "destination"), a, wm.b.c
                                                   )
                                             )
                                       )
                                 ))
                              .then(
                                 dc.a("filtered")
                                    .then(
                                       ((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)dc.a("filter", eg.a())
                                                   .executes(
                                                      var0x -> a(
                                                            (db)var0x.getSource(),
                                                            ek.a(var0x, "begin"),
                                                            ek.a(var0x, "end"),
                                                            ek.a(var0x, "destination"),
                                                            eg.a(var0x, "filter"),
                                                            wm.b.c
                                                         )
                                                   ))
                                                .then(
                                                   dc.a("force")
                                                      .executes(
                                                         var0x -> a(
                                                               (db)var0x.getSource(),
                                                               ek.a(var0x, "begin"),
                                                               ek.a(var0x, "end"),
                                                               ek.a(var0x, "destination"),
                                                               eg.a(var0x, "filter"),
                                                               wm.b.a
                                                            )
                                                      )
                                                ))
                                             .then(
                                                dc.a("move")
                                                   .executes(
                                                      var0x -> a(
                                                            (db)var0x.getSource(),
                                                            ek.a(var0x, "begin"),
                                                            ek.a(var0x, "end"),
                                                            ek.a(var0x, "destination"),
                                                            eg.a(var0x, "filter"),
                                                            wm.b.b
                                                         )
                                                   )
                                             ))
                                          .then(
                                             dc.a("normal")
                                                .executes(
                                                   var0x -> a(
                                                         (db)var0x.getSource(),
                                                         ek.a(var0x, "begin"),
                                                         ek.a(var0x, "end"),
                                                         ek.a(var0x, "destination"),
                                                         eg.a(var0x, "filter"),
                                                         wm.b.c
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

   private static int a(db var0, fx var1, fx var2, fx var3, Predicate<cel> var4, wm.b var5) throws CommandSyntaxException {
      cra _snowman = new cra(_snowman, _snowman);
      fx _snowmanx = _snowman.a(_snowman.c());
      cra _snowmanxx = new cra(_snowman, _snowmanx);
      if (!_snowman.a() && _snowmanxx.b(_snowman)) {
         throw b.create();
      } else {
         int _snowmanxxx = _snowman.d() * _snowman.e() * _snowman.f();
         if (_snowmanxxx > 32768) {
            throw c.create(32768, _snowmanxxx);
         } else {
            aag _snowmanxxxx = _snowman.e();
            if (_snowmanxxxx.a(_snowman, _snowman) && _snowmanxxxx.a(_snowman, _snowmanx)) {
               List<wm.a> _snowmanxxxxx = Lists.newArrayList();
               List<wm.a> _snowmanxxxxxx = Lists.newArrayList();
               List<wm.a> _snowmanxxxxxxx = Lists.newArrayList();
               Deque<fx> _snowmanxxxxxxxx = Lists.newLinkedList();
               fx _snowmanxxxxxxxxx = new fx(_snowmanxx.a - _snowman.a, _snowmanxx.b - _snowman.b, _snowmanxx.c - _snowman.c);

               for (int _snowmanxxxxxxxxxx = _snowman.c; _snowmanxxxxxxxxxx <= _snowman.f; _snowmanxxxxxxxxxx++) {
                  for (int _snowmanxxxxxxxxxxx = _snowman.b; _snowmanxxxxxxxxxxx <= _snowman.e; _snowmanxxxxxxxxxxx++) {
                     for (int _snowmanxxxxxxxxxxxx = _snowman.a; _snowmanxxxxxxxxxxxx <= _snowman.d; _snowmanxxxxxxxxxxxx++) {
                        fx _snowmanxxxxxxxxxxxxx = new fx(_snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxx);
                        fx _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx.a(_snowmanxxxxxxxxx);
                        cel _snowmanxxxxxxxxxxxxxxx = new cel(_snowmanxxxx, _snowmanxxxxxxxxxxxxx, false);
                        ceh _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx.a();
                        if (_snowman.test(_snowmanxxxxxxxxxxxxxxx)) {
                           ccj _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxx.c(_snowmanxxxxxxxxxxxxx);
                           if (_snowmanxxxxxxxxxxxxxxxxx != null) {
                              md _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx.a(new md());
                              _snowmanxxxxxx.add(new wm.a(_snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx));
                              _snowmanxxxxxxxx.addLast(_snowmanxxxxxxxxxxxxx);
                           } else if (!_snowmanxxxxxxxxxxxxxxxx.i(_snowmanxxxx, _snowmanxxxxxxxxxxxxx) && !_snowmanxxxxxxxxxxxxxxxx.r(_snowmanxxxx, _snowmanxxxxxxxxxxxxx)) {
                              _snowmanxxxxxxx.add(new wm.a(_snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx, null));
                              _snowmanxxxxxxxx.addFirst(_snowmanxxxxxxxxxxxxx);
                           } else {
                              _snowmanxxxxx.add(new wm.a(_snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx, null));
                              _snowmanxxxxxxxx.addLast(_snowmanxxxxxxxxxxxxx);
                           }
                        }
                     }
                  }
               }

               if (_snowman == wm.b.b) {
                  for (fx _snowmanxxxxxxxxxx : _snowmanxxxxxxxx) {
                     ccj _snowmanxxxxxxxxxxx = _snowmanxxxx.c(_snowmanxxxxxxxxxx);
                     aol.a(_snowmanxxxxxxxxxxx);
                     _snowmanxxxx.a(_snowmanxxxxxxxxxx, bup.go.n(), 2);
                  }

                  for (fx _snowmanxxxxxxxxxx : _snowmanxxxxxxxx) {
                     _snowmanxxxx.a(_snowmanxxxxxxxxxx, bup.a.n(), 3);
                  }
               }

               List<wm.a> _snowmanxxxxxxxxxx = Lists.newArrayList();
               _snowmanxxxxxxxxxx.addAll(_snowmanxxxxx);
               _snowmanxxxxxxxxxx.addAll(_snowmanxxxxxx);
               _snowmanxxxxxxxxxx.addAll(_snowmanxxxxxxx);
               List<wm.a> _snowmanxxxxxxxxxxx = Lists.reverse(_snowmanxxxxxxxxxx);

               for (wm.a _snowmanxxxxxxxxxxxxx : _snowmanxxxxxxxxxxx) {
                  ccj _snowmanxxxxxxxxxxxxxx = _snowmanxxxx.c(_snowmanxxxxxxxxxxxxx.a);
                  aol.a(_snowmanxxxxxxxxxxxxxx);
                  _snowmanxxxx.a(_snowmanxxxxxxxxxxxxx.a, bup.go.n(), 2);
               }

               int _snowmanxxxxxxxxxxxxx = 0;

               for (wm.a _snowmanxxxxxxxxxxxxxx : _snowmanxxxxxxxxxx) {
                  if (_snowmanxxxx.a(_snowmanxxxxxxxxxxxxxx.a, _snowmanxxxxxxxxxxxxxx.b, 2)) {
                     _snowmanxxxxxxxxxxxxx++;
                  }
               }

               for (wm.a _snowmanxxxxxxxxxxxxxxx : _snowmanxxxxxx) {
                  ccj _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxx.c(_snowmanxxxxxxxxxxxxxxx.a);
                  if (_snowmanxxxxxxxxxxxxxxx.c != null && _snowmanxxxxxxxxxxxxxxxx != null) {
                     _snowmanxxxxxxxxxxxxxxx.c.b("x", _snowmanxxxxxxxxxxxxxxx.a.u());
                     _snowmanxxxxxxxxxxxxxxx.c.b("y", _snowmanxxxxxxxxxxxxxxx.a.v());
                     _snowmanxxxxxxxxxxxxxxx.c.b("z", _snowmanxxxxxxxxxxxxxxx.a.w());
                     _snowmanxxxxxxxxxxxxxxxx.a(_snowmanxxxxxxxxxxxxxxx.b, _snowmanxxxxxxxxxxxxxxx.c);
                     _snowmanxxxxxxxxxxxxxxxx.X_();
                  }

                  _snowmanxxxx.a(_snowmanxxxxxxxxxxxxxxx.a, _snowmanxxxxxxxxxxxxxxx.b, 2);
               }

               for (wm.a _snowmanxxxxxxxxxxxxxxx : _snowmanxxxxxxxxxxx) {
                  _snowmanxxxx.a(_snowmanxxxxxxxxxxxxxxx.a, _snowmanxxxxxxxxxxxxxxx.b.b());
               }

               _snowmanxxxx.j().a(_snowman, _snowmanxxxxxxxxx);
               if (_snowmanxxxxxxxxxxxxx == 0) {
                  throw d.create();
               } else {
                  _snowman.a(new of("commands.clone.success", _snowmanxxxxxxxxxxxxx), true);
                  return _snowmanxxxxxxxxxxxxx;
               }
            } else {
               throw ek.a.create();
            }
         }
      }
   }

   static class a {
      public final fx a;
      public final ceh b;
      @Nullable
      public final md c;

      public a(fx var1, ceh var2, @Nullable md var3) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
      }
   }

   static enum b {
      a(true),
      b(true),
      c(false);

      private final boolean d;

      private b(boolean var3) {
         this.d = _snowman;
      }

      public boolean a() {
         return this.d;
      }
   }
}
