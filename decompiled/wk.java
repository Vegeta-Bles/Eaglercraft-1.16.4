import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.Collection;
import java.util.Collections;

public class wk {
   private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(var0 -> new of("commands.bossbar.create.failed", var0));
   private static final DynamicCommandExceptionType c = new DynamicCommandExceptionType(var0 -> new of("commands.bossbar.unknown", var0));
   private static final SimpleCommandExceptionType d = new SimpleCommandExceptionType(new of("commands.bossbar.set.players.unchanged"));
   private static final SimpleCommandExceptionType e = new SimpleCommandExceptionType(new of("commands.bossbar.set.name.unchanged"));
   private static final SimpleCommandExceptionType f = new SimpleCommandExceptionType(new of("commands.bossbar.set.color.unchanged"));
   private static final SimpleCommandExceptionType g = new SimpleCommandExceptionType(new of("commands.bossbar.set.style.unchanged"));
   private static final SimpleCommandExceptionType h = new SimpleCommandExceptionType(new of("commands.bossbar.set.value.unchanged"));
   private static final SimpleCommandExceptionType i = new SimpleCommandExceptionType(new of("commands.bossbar.set.max.unchanged"));
   private static final SimpleCommandExceptionType j = new SimpleCommandExceptionType(new of("commands.bossbar.set.visibility.unchanged.hidden"));
   private static final SimpleCommandExceptionType k = new SimpleCommandExceptionType(new of("commands.bossbar.set.visibility.unchanged.visible"));
   public static final SuggestionProvider<db> a = (var0, var1) -> dd.a(((db)var0.getSource()).j().aM().a(), var1);

   public static void a(CommandDispatcher<db> var0) {
      _snowman.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a(
                              "bossbar"
                           )
                           .requires(var0x -> var0x.c(2)))
                        .then(
                           dc.a("add")
                              .then(
                                 dc.a("id", dy.a())
                                    .then(dc.a("name", dg.a()).executes(var0x -> a((db)var0x.getSource(), dy.e(var0x, "id"), dg.a(var0x, "name"))))
                              )
                        ))
                     .then(dc.a("remove").then(dc.a("id", dy.a()).suggests(a).executes(var0x -> e((db)var0x.getSource(), a(var0x))))))
                  .then(dc.a("list").executes(var0x -> a((db)var0x.getSource()))))
               .then(
                  dc.a("set")
                     .then(
                        ((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)dc.a(
                                                "id", dy.a()
                                             )
                                             .suggests(a)
                                             .then(
                                                dc.a("name")
                                                   .then(dc.a("name", dg.a()).executes(var0x -> a((db)var0x.getSource(), a(var0x), dg.a(var0x, "name"))))
                                             ))
                                          .then(
                                             ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a(
                                                                     "color"
                                                                  )
                                                                  .then(dc.a("pink").executes(var0x -> a((db)var0x.getSource(), a(var0x), aok.a.a))))
                                                               .then(dc.a("blue").executes(var0x -> a((db)var0x.getSource(), a(var0x), aok.a.b))))
                                                            .then(dc.a("red").executes(var0x -> a((db)var0x.getSource(), a(var0x), aok.a.c))))
                                                         .then(dc.a("green").executes(var0x -> a((db)var0x.getSource(), a(var0x), aok.a.d))))
                                                      .then(dc.a("yellow").executes(var0x -> a((db)var0x.getSource(), a(var0x), aok.a.e))))
                                                   .then(dc.a("purple").executes(var0x -> a((db)var0x.getSource(), a(var0x), aok.a.f))))
                                                .then(dc.a("white").executes(var0x -> a((db)var0x.getSource(), a(var0x), aok.a.g)))
                                          ))
                                       .then(
                                          ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("style")
                                                         .then(dc.a("progress").executes(var0x -> a((db)var0x.getSource(), a(var0x), aok.b.a))))
                                                      .then(dc.a("notched_6").executes(var0x -> a((db)var0x.getSource(), a(var0x), aok.b.b))))
                                                   .then(dc.a("notched_10").executes(var0x -> a((db)var0x.getSource(), a(var0x), aok.b.c))))
                                                .then(dc.a("notched_12").executes(var0x -> a((db)var0x.getSource(), a(var0x), aok.b.d))))
                                             .then(dc.a("notched_20").executes(var0x -> a((db)var0x.getSource(), a(var0x), aok.b.e)))
                                       ))
                                    .then(
                                       dc.a("value")
                                          .then(
                                             dc.a("value", IntegerArgumentType.integer(0))
                                                .executes(var0x -> a((db)var0x.getSource(), a(var0x), IntegerArgumentType.getInteger(var0x, "value")))
                                          )
                                    ))
                                 .then(
                                    dc.a("max")
                                       .then(
                                          dc.a("max", IntegerArgumentType.integer(1))
                                             .executes(var0x -> b((db)var0x.getSource(), a(var0x), IntegerArgumentType.getInteger(var0x, "max")))
                                       )
                                 ))
                              .then(
                                 dc.a("visible")
                                    .then(
                                       dc.a("visible", BoolArgumentType.bool())
                                          .executes(var0x -> a((db)var0x.getSource(), a(var0x), BoolArgumentType.getBool(var0x, "visible")))
                                    )
                              ))
                           .then(
                              ((LiteralArgumentBuilder)dc.a("players").executes(var0x -> a((db)var0x.getSource(), a(var0x), Collections.emptyList())))
                                 .then(dc.a("targets", dk.d()).executes(var0x -> a((db)var0x.getSource(), a(var0x), dk.d(var0x, "targets"))))
                           )
                     )
               ))
            .then(
               dc.a("get")
                  .then(
                     ((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)dc.a("id", dy.a())
                                 .suggests(a)
                                 .then(dc.a("value").executes(var0x -> a((db)var0x.getSource(), a(var0x)))))
                              .then(dc.a("max").executes(var0x -> b((db)var0x.getSource(), a(var0x)))))
                           .then(dc.a("visible").executes(var0x -> c((db)var0x.getSource(), a(var0x)))))
                        .then(dc.a("players").executes(var0x -> d((db)var0x.getSource(), a(var0x))))
                  )
            )
      );
   }

   private static int a(db var0, wc var1) {
      _snowman.a(new of("commands.bossbar.get.value", _snowman.e(), _snowman.c()), true);
      return _snowman.c();
   }

   private static int b(db var0, wc var1) {
      _snowman.a(new of("commands.bossbar.get.max", _snowman.e(), _snowman.d()), true);
      return _snowman.d();
   }

   private static int c(db var0, wc var1) {
      if (_snowman.g()) {
         _snowman.a(new of("commands.bossbar.get.visible.visible", _snowman.e()), true);
         return 1;
      } else {
         _snowman.a(new of("commands.bossbar.get.visible.hidden", _snowman.e()), true);
         return 0;
      }
   }

   private static int d(db var0, wc var1) {
      if (_snowman.h().isEmpty()) {
         _snowman.a(new of("commands.bossbar.get.players.none", _snowman.e()), true);
      } else {
         _snowman.a(new of("commands.bossbar.get.players.some", _snowman.e(), _snowman.h().size(), ns.b(_snowman.h(), bfw::d)), true);
      }

      return _snowman.h().size();
   }

   private static int a(db var0, wc var1, boolean var2) throws CommandSyntaxException {
      if (_snowman.g() == _snowman) {
         if (_snowman) {
            throw k.create();
         } else {
            throw j.create();
         }
      } else {
         _snowman.d(_snowman);
         if (_snowman) {
            _snowman.a(new of("commands.bossbar.set.visible.success.visible", _snowman.e()), true);
         } else {
            _snowman.a(new of("commands.bossbar.set.visible.success.hidden", _snowman.e()), true);
         }

         return 0;
      }
   }

   private static int a(db var0, wc var1, int var2) throws CommandSyntaxException {
      if (_snowman.c() == _snowman) {
         throw h.create();
      } else {
         _snowman.a(_snowman);
         _snowman.a(new of("commands.bossbar.set.value.success", _snowman.e(), _snowman), true);
         return _snowman;
      }
   }

   private static int b(db var0, wc var1, int var2) throws CommandSyntaxException {
      if (_snowman.d() == _snowman) {
         throw i.create();
      } else {
         _snowman.b(_snowman);
         _snowman.a(new of("commands.bossbar.set.max.success", _snowman.e(), _snowman), true);
         return _snowman;
      }
   }

   private static int a(db var0, wc var1, aok.a var2) throws CommandSyntaxException {
      if (_snowman.l().equals(_snowman)) {
         throw f.create();
      } else {
         _snowman.a(_snowman);
         _snowman.a(new of("commands.bossbar.set.color.success", _snowman.e()), true);
         return 0;
      }
   }

   private static int a(db var0, wc var1, aok.b var2) throws CommandSyntaxException {
      if (_snowman.m().equals(_snowman)) {
         throw g.create();
      } else {
         _snowman.a(_snowman);
         _snowman.a(new of("commands.bossbar.set.style.success", _snowman.e()), true);
         return 0;
      }
   }

   private static int a(db var0, wc var1, nr var2) throws CommandSyntaxException {
      nr _snowman = ns.a(_snowman, _snowman, null, 0);
      if (_snowman.j().equals(_snowman)) {
         throw e.create();
      } else {
         _snowman.a(_snowman);
         _snowman.a(new of("commands.bossbar.set.name.success", _snowman.e()), true);
         return 0;
      }
   }

   private static int a(db var0, wc var1, Collection<aah> var2) throws CommandSyntaxException {
      boolean _snowman = _snowman.a(_snowman);
      if (!_snowman) {
         throw d.create();
      } else {
         if (_snowman.h().isEmpty()) {
            _snowman.a(new of("commands.bossbar.set.players.success.none", _snowman.e()), true);
         } else {
            _snowman.a(new of("commands.bossbar.set.players.success.some", _snowman.e(), _snowman.size(), ns.b(_snowman, bfw::d)), true);
         }

         return _snowman.h().size();
      }
   }

   private static int a(db var0) {
      Collection<wc> _snowman = _snowman.j().aM().b();
      if (_snowman.isEmpty()) {
         _snowman.a(new of("commands.bossbar.list.bars.none"), false);
      } else {
         _snowman.a(new of("commands.bossbar.list.bars.some", _snowman.size(), ns.b(_snowman, wc::e)), false);
      }

      return _snowman.size();
   }

   private static int a(db var0, vk var1, nr var2) throws CommandSyntaxException {
      wd _snowman = _snowman.j().aM();
      if (_snowman.a(_snowman) != null) {
         throw b.create(_snowman.toString());
      } else {
         wc _snowmanx = _snowman.a(_snowman, ns.a(_snowman, _snowman, null, 0));
         _snowman.a(new of("commands.bossbar.create.success", _snowmanx.e()), true);
         return _snowman.b().size();
      }
   }

   private static int e(db var0, wc var1) {
      wd _snowman = _snowman.j().aM();
      _snowman.b();
      _snowman.a(_snowman);
      _snowman.a(new of("commands.bossbar.remove.success", _snowman.e()), true);
      return _snowman.b().size();
   }

   public static wc a(CommandContext<db> var0) throws CommandSyntaxException {
      vk _snowman = dy.e(_snowman, "id");
      wc _snowmanx = ((db)_snowman.getSource()).j().aM().a(_snowman);
      if (_snowmanx == null) {
         throw c.create(_snowman.toString());
      } else {
         return _snowmanx;
      }
   }
}
