import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class wn {
   private static final DynamicCommandExceptionType a = new DynamicCommandExceptionType(var0 -> new of("commands.datapack.unknown", var0));
   private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(var0 -> new of("commands.datapack.enable.failed", var0));
   private static final DynamicCommandExceptionType c = new DynamicCommandExceptionType(var0 -> new of("commands.datapack.disable.failed", var0));
   private static final SuggestionProvider<db> d = (var0, var1) -> dd.b(
         ((db)var0.getSource()).j().aC().d().stream().map(StringArgumentType::escapeIfRequired), var1
      );
   private static final SuggestionProvider<db> e = (var0, var1) -> {
      abw _snowman = ((db)var0.getSource()).j().aC();
      Collection<String> _snowmanx = _snowman.d();
      return dd.b(_snowman.b().stream().filter(var1x -> !_snowman.contains(var1x)).map(StringArgumentType::escapeIfRequired), var1);
   };

   public static void a(CommandDispatcher<db> var0) {
      _snowman.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("datapack").requires(var0x -> var0x.c(2)))
                  .then(
                     dc.a("enable")
                        .then(
                           ((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)dc.a(
                                             "name", StringArgumentType.string()
                                          )
                                          .suggests(e)
                                          .executes(
                                             var0x -> a(
                                                   (db)var0x.getSource(),
                                                   a(var0x, "name", true),
                                                   (var0xx, var1) -> var1.h().a(var0xx, var1, var0xxx -> var0xxx, false)
                                                )
                                          ))
                                       .then(
                                          dc.a("after")
                                             .then(
                                                dc.a("existing", StringArgumentType.string())
                                                   .suggests(d)
                                                   .executes(
                                                      var0x -> a(
                                                            (db)var0x.getSource(),
                                                            a(var0x, "name", true),
                                                            (var1, var2) -> var1.add(var1.indexOf(a(var0x, "existing", false)) + 1, var2)
                                                         )
                                                   )
                                             )
                                       ))
                                    .then(
                                       dc.a("before")
                                          .then(
                                             dc.a("existing", StringArgumentType.string())
                                                .suggests(d)
                                                .executes(
                                                   var0x -> a(
                                                         (db)var0x.getSource(),
                                                         a(var0x, "name", true),
                                                         (var1, var2) -> var1.add(var1.indexOf(a(var0x, "existing", false)), var2)
                                                      )
                                                )
                                          )
                                    ))
                                 .then(dc.a("last").executes(var0x -> a((db)var0x.getSource(), a(var0x, "name", true), List::add))))
                              .then(dc.a("first").executes(var0x -> a((db)var0x.getSource(), a(var0x, "name", true), (var0xx, var1) -> var0xx.add(0, var1))))
                        )
                  ))
               .then(
                  dc.a("disable")
                     .then(dc.a("name", StringArgumentType.string()).suggests(d).executes(var0x -> a((db)var0x.getSource(), a(var0x, "name", false))))
               ))
            .then(
               ((LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("list").executes(var0x -> a((db)var0x.getSource())))
                     .then(dc.a("available").executes(var0x -> b((db)var0x.getSource()))))
                  .then(dc.a("enabled").executes(var0x -> c((db)var0x.getSource())))
            )
      );
   }

   private static int a(db var0, abu var1, wn.a var2) throws CommandSyntaxException {
      abw _snowman = _snowman.j().aC();
      List<abu> _snowmanx = Lists.newArrayList(_snowman.e());
      _snowman.apply(_snowmanx, _snowman);
      _snowman.a(new of("commands.datapack.modify.enable", _snowman.a(true)), true);
      xv.a(_snowmanx.stream().map(abu::e).collect(Collectors.toList()), _snowman);
      return _snowmanx.size();
   }

   private static int a(db var0, abu var1) {
      abw _snowman = _snowman.j().aC();
      List<abu> _snowmanx = Lists.newArrayList(_snowman.e());
      _snowmanx.remove(_snowman);
      _snowman.a(new of("commands.datapack.modify.disable", _snowman.a(true)), true);
      xv.a(_snowmanx.stream().map(abu::e).collect(Collectors.toList()), _snowman);
      return _snowmanx.size();
   }

   private static int a(db var0) {
      return c(_snowman) + b(_snowman);
   }

   private static int b(db var0) {
      abw _snowman = _snowman.j().aC();
      _snowman.a();
      Collection<? extends abu> _snowmanx = _snowman.e();
      Collection<? extends abu> _snowmanxx = _snowman.c();
      List<abu> _snowmanxxx = _snowmanxx.stream().filter(var1x -> !_snowman.contains(var1x)).collect(Collectors.toList());
      if (_snowmanxxx.isEmpty()) {
         _snowman.a(new of("commands.datapack.list.available.none"), false);
      } else {
         _snowman.a(new of("commands.datapack.list.available.success", _snowmanxxx.size(), ns.b(_snowmanxxx, var0x -> var0x.a(false))), false);
      }

      return _snowmanxxx.size();
   }

   private static int c(db var0) {
      abw _snowman = _snowman.j().aC();
      _snowman.a();
      Collection<? extends abu> _snowmanx = _snowman.e();
      if (_snowmanx.isEmpty()) {
         _snowman.a(new of("commands.datapack.list.enabled.none"), false);
      } else {
         _snowman.a(new of("commands.datapack.list.enabled.success", _snowmanx.size(), ns.b(_snowmanx, var0x -> var0x.a(true))), false);
      }

      return _snowmanx.size();
   }

   private static abu a(CommandContext<db> var0, String var1, boolean var2) throws CommandSyntaxException {
      String _snowman = StringArgumentType.getString(_snowman, _snowman);
      abw _snowmanx = ((db)_snowman.getSource()).j().aC();
      abu _snowmanxx = _snowmanx.a(_snowman);
      if (_snowmanxx == null) {
         throw a.create(_snowman);
      } else {
         boolean _snowmanxxx = _snowmanx.e().contains(_snowmanxx);
         if (_snowman && _snowmanxxx) {
            throw b.create(_snowman);
         } else if (!_snowman && !_snowmanxxx) {
            throw c.create(_snowman);
         } else {
            return _snowmanxx;
         }
      }
   }

   interface a {
      void apply(List<abu> var1, abu var2) throws CommandSyntaxException;
   }
}
