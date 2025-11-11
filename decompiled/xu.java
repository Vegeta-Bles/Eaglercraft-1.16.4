import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import java.util.Collections;

public class xu {
   private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new of("commands.recipe.give.failed"));
   private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new of("commands.recipe.take.failed"));

   public static void a(CommandDispatcher<db> var0) {
      _snowman.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("recipe").requires(var0x -> var0x.c(2)))
               .then(
                  dc.a("give")
                     .then(
                        ((RequiredArgumentBuilder)dc.a("targets", dk.d())
                              .then(
                                 dc.a("recipe", dy.a())
                                    .suggests(fm.b)
                                    .executes(var0x -> a((db)var0x.getSource(), dk.f(var0x, "targets"), Collections.singleton(dy.b(var0x, "recipe"))))
                              ))
                           .then(dc.a("*").executes(var0x -> a((db)var0x.getSource(), dk.f(var0x, "targets"), ((db)var0x.getSource()).j().aF().b())))
                     )
               ))
            .then(
               dc.a("take")
                  .then(
                     ((RequiredArgumentBuilder)dc.a("targets", dk.d())
                           .then(
                              dc.a("recipe", dy.a())
                                 .suggests(fm.b)
                                 .executes(var0x -> b((db)var0x.getSource(), dk.f(var0x, "targets"), Collections.singleton(dy.b(var0x, "recipe"))))
                           ))
                        .then(dc.a("*").executes(var0x -> b((db)var0x.getSource(), dk.f(var0x, "targets"), ((db)var0x.getSource()).j().aF().b())))
                  )
            )
      );
   }

   private static int a(db var0, Collection<aah> var1, Collection<boq<?>> var2) throws CommandSyntaxException {
      int _snowman = 0;

      for (aah _snowmanx : _snowman) {
         _snowman += _snowmanx.a(_snowman);
      }

      if (_snowman == 0) {
         throw a.create();
      } else {
         if (_snowman.size() == 1) {
            _snowman.a(new of("commands.recipe.give.success.single", _snowman.size(), _snowman.iterator().next().d()), true);
         } else {
            _snowman.a(new of("commands.recipe.give.success.multiple", _snowman.size(), _snowman.size()), true);
         }

         return _snowman;
      }
   }

   private static int b(db var0, Collection<aah> var1, Collection<boq<?>> var2) throws CommandSyntaxException {
      int _snowman = 0;

      for (aah _snowmanx : _snowman) {
         _snowman += _snowmanx.b(_snowman);
      }

      if (_snowman == 0) {
         throw b.create();
      } else {
         if (_snowman.size() == 1) {
            _snowman.a(new of("commands.recipe.take.success.single", _snowman.size(), _snowman.iterator().next().d()), true);
         } else {
            _snowman.a(new of("commands.recipe.take.success.multiple", _snowman.size(), _snowman.size()), true);
         }

         return _snowman;
      }
   }
}
