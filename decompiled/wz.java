import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class wz {
   private static final Dynamic2CommandExceptionType a = new Dynamic2CommandExceptionType((var0, var1) -> new of("commands.fill.toobig", var0, var1));
   private static final ef b = new ef(bup.a.n(), Collections.emptySet(), null);
   private static final SimpleCommandExceptionType c = new SimpleCommandExceptionType(new of("commands.fill.failed"));

   public static void a(CommandDispatcher<db> var0) {
      _snowman.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("fill").requires(var0x -> var0x.c(2)))
            .then(
               dc.a("from", ek.a())
                  .then(
                     dc.a("to", ek.a())
                        .then(
                           ((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)dc.a(
                                                "block", eh.a()
                                             )
                                             .executes(
                                                var0x -> a(
                                                      (db)var0x.getSource(),
                                                      new cra(ek.a(var0x, "from"), ek.a(var0x, "to")),
                                                      eh.a(var0x, "block"),
                                                      wz.a.a,
                                                      null
                                                   )
                                             ))
                                          .then(
                                             ((LiteralArgumentBuilder)dc.a("replace")
                                                   .executes(
                                                      var0x -> a(
                                                            (db)var0x.getSource(),
                                                            new cra(ek.a(var0x, "from"), ek.a(var0x, "to")),
                                                            eh.a(var0x, "block"),
                                                            wz.a.a,
                                                            null
                                                         )
                                                   ))
                                                .then(
                                                   dc.a("filter", eg.a())
                                                      .executes(
                                                         var0x -> a(
                                                               (db)var0x.getSource(),
                                                               new cra(ek.a(var0x, "from"), ek.a(var0x, "to")),
                                                               eh.a(var0x, "block"),
                                                               wz.a.a,
                                                               eg.a(var0x, "filter")
                                                            )
                                                      )
                                                )
                                          ))
                                       .then(
                                          dc.a("keep")
                                             .executes(
                                                var0x -> a(
                                                      (db)var0x.getSource(),
                                                      new cra(ek.a(var0x, "from"), ek.a(var0x, "to")),
                                                      eh.a(var0x, "block"),
                                                      wz.a.a,
                                                      var0xx -> var0xx.c().w(var0xx.d())
                                                   )
                                             )
                                       ))
                                    .then(
                                       dc.a("outline")
                                          .executes(
                                             var0x -> a(
                                                   (db)var0x.getSource(), new cra(ek.a(var0x, "from"), ek.a(var0x, "to")), eh.a(var0x, "block"), wz.a.b, null
                                                )
                                          )
                                    ))
                                 .then(
                                    dc.a("hollow")
                                       .executes(
                                          var0x -> a((db)var0x.getSource(), new cra(ek.a(var0x, "from"), ek.a(var0x, "to")), eh.a(var0x, "block"), wz.a.c, null)
                                       )
                                 ))
                              .then(
                                 dc.a("destroy")
                                    .executes(
                                       var0x -> a((db)var0x.getSource(), new cra(ek.a(var0x, "from"), ek.a(var0x, "to")), eh.a(var0x, "block"), wz.a.d, null)
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int a(db var0, cra var1, ef var2, wz.a var3, @Nullable Predicate<cel> var4) throws CommandSyntaxException {
      int _snowman = _snowman.d() * _snowman.e() * _snowman.f();
      if (_snowman > 32768) {
         throw a.create(32768, _snowman);
      } else {
         List<fx> _snowmanx = Lists.newArrayList();
         aag _snowmanxx = _snowman.e();
         int _snowmanxxx = 0;

         for (fx _snowmanxxxx : fx.b(_snowman.a, _snowman.b, _snowman.c, _snowman.d, _snowman.e, _snowman.f)) {
            if (_snowman == null || _snowman.test(new cel(_snowmanxx, _snowmanxxxx, true))) {
               ef _snowmanxxxxx = _snowman.e.filter(_snowman, _snowmanxxxx, _snowman, _snowmanxx);
               if (_snowmanxxxxx != null) {
                  ccj _snowmanxxxxxx = _snowmanxx.c(_snowmanxxxx);
                  aol.a(_snowmanxxxxxx);
                  if (_snowmanxxxxx.a(_snowmanxx, _snowmanxxxx, 2)) {
                     _snowmanx.add(_snowmanxxxx.h());
                     _snowmanxxx++;
                  }
               }
            }
         }

         for (fx _snowmanxxxxx : _snowmanx) {
            buo _snowmanxxxxxx = _snowmanxx.d_(_snowmanxxxxx).b();
            _snowmanxx.a(_snowmanxxxxx, _snowmanxxxxxx);
         }

         if (_snowmanxxx == 0) {
            throw c.create();
         } else {
            _snowman.a(new of("commands.fill.success", _snowmanxxx), true);
            return _snowmanxxx;
         }
      }
   }

   static enum a {
      a((var0, var1, var2, var3) -> var2),
      b(
         (var0, var1, var2, var3) -> var1.u() != var0.a
                  && var1.u() != var0.d
                  && var1.v() != var0.b
                  && var1.v() != var0.e
                  && var1.w() != var0.c
                  && var1.w() != var0.f
               ? null
               : var2
      ),
      c(
         (var0, var1, var2, var3) -> var1.u() != var0.a
                  && var1.u() != var0.d
                  && var1.v() != var0.b
                  && var1.v() != var0.e
                  && var1.w() != var0.c
                  && var1.w() != var0.f
               ? wz.b
               : var2
      ),
      d((var0, var1, var2, var3) -> {
         var3.b(var1, true);
         return var2;
      });

      public final ye.a e;

      private a(ye.a var3) {
         this.e = _snowman;
      }
   }
}
