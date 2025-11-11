import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;

public class wl {
   private static final DynamicCommandExceptionType a = new DynamicCommandExceptionType(var0 -> new of("clear.failed.single", var0));
   private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(var0 -> new of("clear.failed.multiple", var0));

   public static void a(CommandDispatcher<db> var0) {
      _snowman.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("clear").requires(var0x -> var0x.c(2)))
               .executes(var0x -> a((db)var0x.getSource(), Collections.singleton(((db)var0x.getSource()).h()), var0xx -> true, -1)))
            .then(
               ((RequiredArgumentBuilder)dc.a("targets", dk.d()).executes(var0x -> a((db)var0x.getSource(), dk.f(var0x, "targets"), var0xx -> true, -1)))
                  .then(
                     ((RequiredArgumentBuilder)dc.a("item", ez.a())
                           .executes(var0x -> a((db)var0x.getSource(), dk.f(var0x, "targets"), ez.a(var0x, "item"), -1)))
                        .then(
                           dc.a("maxCount", IntegerArgumentType.integer(0))
                              .executes(
                                 var0x -> a(
                                       (db)var0x.getSource(), dk.f(var0x, "targets"), ez.a(var0x, "item"), IntegerArgumentType.getInteger(var0x, "maxCount")
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int a(db var0, Collection<aah> var1, Predicate<bmb> var2, int var3) throws CommandSyntaxException {
      int _snowman = 0;

      for (aah _snowmanx : _snowman) {
         _snowman += _snowmanx.bm.a(_snowman, _snowman, _snowmanx.bo.j());
         _snowmanx.bp.c();
         _snowmanx.bo.a(_snowmanx.bm);
         _snowmanx.n();
      }

      if (_snowman == 0) {
         if (_snowman.size() == 1) {
            throw a.create(_snowman.iterator().next().R());
         } else {
            throw b.create(_snowman.size());
         }
      } else {
         if (_snowman == 0) {
            if (_snowman.size() == 1) {
               _snowman.a(new of("commands.clear.test.single", _snowman, _snowman.iterator().next().d()), true);
            } else {
               _snowman.a(new of("commands.clear.test.multiple", _snowman, _snowman.size()), true);
            }
         } else if (_snowman.size() == 1) {
            _snowman.a(new of("commands.clear.success.single", _snowman, _snowman.iterator().next().d()), true);
         } else {
            _snowman.a(new of("commands.clear.success.multiple", _snowman, _snowman.size()), true);
         }

         return _snowman;
      }
   }
}
