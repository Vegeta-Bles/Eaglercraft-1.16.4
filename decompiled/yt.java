import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Collection;
import java.util.Locale;

public class yt {
   public static void a(CommandDispatcher<db> var0) {
      _snowman.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("title").requires(var0x -> var0x.c(2)))
            .then(
               ((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)dc.a(
                                    "targets", dk.d()
                                 )
                                 .then(dc.a("clear").executes(var0x -> a((db)var0x.getSource(), dk.f(var0x, "targets")))))
                              .then(dc.a("reset").executes(var0x -> b((db)var0x.getSource(), dk.f(var0x, "targets")))))
                           .then(
                              dc.a("title")
                                 .then(dc.a("title", dg.a()).executes(var0x -> a((db)var0x.getSource(), dk.f(var0x, "targets"), dg.a(var0x, "title"), rl.a.a)))
                           ))
                        .then(
                           dc.a("subtitle")
                              .then(dc.a("title", dg.a()).executes(var0x -> a((db)var0x.getSource(), dk.f(var0x, "targets"), dg.a(var0x, "title"), rl.a.b)))
                        ))
                     .then(
                        dc.a("actionbar")
                           .then(dc.a("title", dg.a()).executes(var0x -> a((db)var0x.getSource(), dk.f(var0x, "targets"), dg.a(var0x, "title"), rl.a.c)))
                     ))
                  .then(
                     dc.a("times")
                        .then(
                           dc.a("fadeIn", IntegerArgumentType.integer(0))
                              .then(
                                 dc.a("stay", IntegerArgumentType.integer(0))
                                    .then(
                                       dc.a("fadeOut", IntegerArgumentType.integer(0))
                                          .executes(
                                             var0x -> a(
                                                   (db)var0x.getSource(),
                                                   dk.f(var0x, "targets"),
                                                   IntegerArgumentType.getInteger(var0x, "fadeIn"),
                                                   IntegerArgumentType.getInteger(var0x, "stay"),
                                                   IntegerArgumentType.getInteger(var0x, "fadeOut")
                                                )
                                          )
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int a(db var0, Collection<aah> var1) {
      rl _snowman = new rl(rl.a.e, null);

      for (aah _snowmanx : _snowman) {
         _snowmanx.b.a(_snowman);
      }

      if (_snowman.size() == 1) {
         _snowman.a(new of("commands.title.cleared.single", _snowman.iterator().next().d()), true);
      } else {
         _snowman.a(new of("commands.title.cleared.multiple", _snowman.size()), true);
      }

      return _snowman.size();
   }

   private static int b(db var0, Collection<aah> var1) {
      rl _snowman = new rl(rl.a.f, null);

      for (aah _snowmanx : _snowman) {
         _snowmanx.b.a(_snowman);
      }

      if (_snowman.size() == 1) {
         _snowman.a(new of("commands.title.reset.single", _snowman.iterator().next().d()), true);
      } else {
         _snowman.a(new of("commands.title.reset.multiple", _snowman.size()), true);
      }

      return _snowman.size();
   }

   private static int a(db var0, Collection<aah> var1, nr var2, rl.a var3) throws CommandSyntaxException {
      for (aah _snowman : _snowman) {
         _snowman.b.a(new rl(_snowman, ns.a(_snowman, _snowman, _snowman, 0)));
      }

      if (_snowman.size() == 1) {
         _snowman.a(new of("commands.title.show." + _snowman.name().toLowerCase(Locale.ROOT) + ".single", _snowman.iterator().next().d()), true);
      } else {
         _snowman.a(new of("commands.title.show." + _snowman.name().toLowerCase(Locale.ROOT) + ".multiple", _snowman.size()), true);
      }

      return _snowman.size();
   }

   private static int a(db var0, Collection<aah> var1, int var2, int var3, int var4) {
      rl _snowman = new rl(_snowman, _snowman, _snowman);

      for (aah _snowmanx : _snowman) {
         _snowmanx.b.a(_snowman);
      }

      if (_snowman.size() == 1) {
         _snowman.a(new of("commands.title.times.single", _snowman.iterator().next().d()), true);
      } else {
         _snowman.a(new of("commands.title.times.multiple", _snowman.size()), true);
      }

      return _snowman.size();
   }
}
