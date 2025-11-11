import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.ToIntFunction;

public class wy {
   private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new of("commands.experience.set.points.invalid"));

   public static void a(CommandDispatcher<db> var0) {
      LiteralCommandNode<db> _snowman = _snowman.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("experience").requires(var0x -> var0x.c(2)))
                  .then(
                     dc.a("add")
                        .then(
                           dc.a("targets", dk.d())
                              .then(
                                 ((RequiredArgumentBuilder)((RequiredArgumentBuilder)dc.a("amount", IntegerArgumentType.integer())
                                          .executes(
                                             var0x -> a((db)var0x.getSource(), dk.f(var0x, "targets"), IntegerArgumentType.getInteger(var0x, "amount"), wy.a.a)
                                          ))
                                       .then(
                                          dc.a("points")
                                             .executes(
                                                var0x -> a(
                                                      (db)var0x.getSource(), dk.f(var0x, "targets"), IntegerArgumentType.getInteger(var0x, "amount"), wy.a.a
                                                   )
                                             )
                                       ))
                                    .then(
                                       dc.a("levels")
                                          .executes(
                                             var0x -> a((db)var0x.getSource(), dk.f(var0x, "targets"), IntegerArgumentType.getInteger(var0x, "amount"), wy.a.b)
                                          )
                                    )
                              )
                        )
                  ))
               .then(
                  dc.a("set")
                     .then(
                        dc.a("targets", dk.d())
                           .then(
                              ((RequiredArgumentBuilder)((RequiredArgumentBuilder)dc.a("amount", IntegerArgumentType.integer(0))
                                       .executes(
                                          var0x -> b((db)var0x.getSource(), dk.f(var0x, "targets"), IntegerArgumentType.getInteger(var0x, "amount"), wy.a.a)
                                       ))
                                    .then(
                                       dc.a("points")
                                          .executes(
                                             var0x -> b((db)var0x.getSource(), dk.f(var0x, "targets"), IntegerArgumentType.getInteger(var0x, "amount"), wy.a.a)
                                          )
                                    ))
                                 .then(
                                    dc.a("levels")
                                       .executes(
                                          var0x -> b((db)var0x.getSource(), dk.f(var0x, "targets"), IntegerArgumentType.getInteger(var0x, "amount"), wy.a.b)
                                       )
                                 )
                           )
                     )
               ))
            .then(
               dc.a("query")
                  .then(
                     ((RequiredArgumentBuilder)dc.a("targets", dk.c())
                           .then(dc.a("points").executes(var0x -> a((db)var0x.getSource(), dk.e(var0x, "targets"), wy.a.a))))
                        .then(dc.a("levels").executes(var0x -> a((db)var0x.getSource(), dk.e(var0x, "targets"), wy.a.b)))
                  )
            )
      );
      _snowman.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("xp").requires(var0x -> var0x.c(2))).redirect(_snowman));
   }

   private static int a(db var0, aah var1, wy.a var2) {
      int _snowman = _snowman.f.applyAsInt(_snowman);
      _snowman.a(new of("commands.experience.query." + _snowman.e, _snowman.d(), _snowman), false);
      return _snowman;
   }

   private static int a(db var0, Collection<? extends aah> var1, int var2, wy.a var3) {
      for (aah _snowman : _snowman) {
         _snowman.c.accept(_snowman, _snowman);
      }

      if (_snowman.size() == 1) {
         _snowman.a(new of("commands.experience.add." + _snowman.e + ".success.single", _snowman, _snowman.iterator().next().d()), true);
      } else {
         _snowman.a(new of("commands.experience.add." + _snowman.e + ".success.multiple", _snowman, _snowman.size()), true);
      }

      return _snowman.size();
   }

   private static int b(db var0, Collection<? extends aah> var1, int var2, wy.a var3) throws CommandSyntaxException {
      int _snowman = 0;

      for (aah _snowmanx : _snowman) {
         if (_snowman.d.test(_snowmanx, _snowman)) {
            _snowman++;
         }
      }

      if (_snowman == 0) {
         throw a.create();
      } else {
         if (_snowman.size() == 1) {
            _snowman.a(new of("commands.experience.set." + _snowman.e + ".success.single", _snowman, _snowman.iterator().next().d()), true);
         } else {
            _snowman.a(new of("commands.experience.set." + _snowman.e + ".success.multiple", _snowman, _snowman.size()), true);
         }

         return _snowman.size();
      }
   }

   static enum a {
      a("points", bfw::d, (var0, var1) -> {
         if (var1 >= var0.eH()) {
            return false;
         } else {
            var0.a(var1);
            return true;
         }
      }, var0 -> afm.d(var0.bF * (float)var0.eH())),
      b("levels", aah::c, (var0, var1) -> {
         var0.b(var1);
         return true;
      }, var0 -> var0.bD);

      public final BiConsumer<aah, Integer> c;
      public final BiPredicate<aah, Integer> d;
      public final String e;
      private final ToIntFunction<aah> f;

      private a(String var3, BiConsumer<aah, Integer> var4, BiPredicate<aah, Integer> var5, ToIntFunction<aah> var6) {
         this.c = _snowman;
         this.e = _snowman;
         this.d = _snowman;
         this.f = _snowman;
      }
   }
}
