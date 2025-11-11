import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class yu {
   private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new of("commands.trigger.failed.unprimed"));
   private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new of("commands.trigger.failed.invalid"));

   public static void a(CommandDispatcher<db> var0) {
      _snowman.register(
         (LiteralArgumentBuilder)dc.a("trigger")
            .then(
               ((RequiredArgumentBuilder)((RequiredArgumentBuilder)dc.a("objective", dt.a())
                        .suggests((var0x, var1) -> a((db)var0x.getSource(), var1))
                        .executes(var0x -> a((db)var0x.getSource(), a(((db)var0x.getSource()).h(), dt.a(var0x, "objective")))))
                     .then(
                        dc.a("add")
                           .then(
                              dc.a("value", IntegerArgumentType.integer())
                                 .executes(
                                    var0x -> a(
                                          (db)var0x.getSource(),
                                          a(((db)var0x.getSource()).h(), dt.a(var0x, "objective")),
                                          IntegerArgumentType.getInteger(var0x, "value")
                                       )
                                 )
                           )
                     ))
                  .then(
                     dc.a("set")
                        .then(
                           dc.a("value", IntegerArgumentType.integer())
                              .executes(
                                 var0x -> b(
                                       (db)var0x.getSource(),
                                       a(((db)var0x.getSource()).h(), dt.a(var0x, "objective")),
                                       IntegerArgumentType.getInteger(var0x, "value")
                                    )
                              )
                        )
                  )
            )
      );
   }

   public static CompletableFuture<Suggestions> a(db var0, SuggestionsBuilder var1) {
      aqa _snowman = _snowman.f();
      List<String> _snowmanx = Lists.newArrayList();
      if (_snowman != null) {
         ddn _snowmanxx = _snowman.j().aH();
         String _snowmanxxx = _snowman.bU();

         for (ddk _snowmanxxxx : _snowmanxx.c()) {
            if (_snowmanxxxx.c() == ddq.c && _snowmanxx.b(_snowmanxxx, _snowmanxxxx)) {
               ddm _snowmanxxxxx = _snowmanxx.c(_snowmanxxx, _snowmanxxxx);
               if (!_snowmanxxxxx.g()) {
                  _snowmanx.add(_snowmanxxxx.b());
               }
            }
         }
      }

      return dd.b(_snowmanx, _snowman);
   }

   private static int a(db var0, ddm var1, int var2) {
      _snowman.a(_snowman);
      _snowman.a(new of("commands.trigger.add.success", _snowman.d().e(), _snowman), true);
      return _snowman.b();
   }

   private static int b(db var0, ddm var1, int var2) {
      _snowman.c(_snowman);
      _snowman.a(new of("commands.trigger.set.success", _snowman.d().e(), _snowman), true);
      return _snowman;
   }

   private static int a(db var0, ddm var1) {
      _snowman.a(1);
      _snowman.a(new of("commands.trigger.simple.success", _snowman.d().e()), true);
      return _snowman.b();
   }

   private static ddm a(aah var0, ddk var1) throws CommandSyntaxException {
      if (_snowman.c() != ddq.c) {
         throw b.create();
      } else {
         ddn _snowman = _snowman.eN();
         String _snowmanx = _snowman.bU();
         if (!_snowman.b(_snowmanx, _snowman)) {
            throw a.create();
         } else {
            ddm _snowmanxx = _snowman.c(_snowmanx, _snowman);
            if (_snowmanxx.g()) {
               throw a.create();
            } else {
               _snowmanxx.a(true);
               return _snowmanxx;
            }
         }
      }
   }
}
