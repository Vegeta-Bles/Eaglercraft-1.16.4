import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.Collection;

public class xb {
   public static final SuggestionProvider<db> a = (var0, var1) -> {
      vx _snowman = ((db)var0.getSource()).j().aB();
      dd.a(_snowman.g(), var1, "#");
      return dd.a(_snowman.f(), var1);
   };

   public static void a(CommandDispatcher<db> var0) {
      _snowman.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("function").requires(var0x -> var0x.c(2)))
            .then(dc.a("name", ev.a()).suggests(a).executes(var0x -> a((db)var0x.getSource(), ev.a(var0x, "name"))))
      );
   }

   private static int a(db var0, Collection<cy> var1) {
      int _snowman = 0;

      for (cy _snowmanx : _snowman) {
         _snowman += _snowman.j().aB().a(_snowmanx, _snowman.a().b(2));
      }

      if (_snowman.size() == 1) {
         _snowman.a(new of("commands.function.success.single", _snowman, _snowman.iterator().next().a()), true);
      } else {
         _snowman.a(new of("commands.function.success.multiple", _snowman, _snowman.size()), true);
      }

      return _snowman;
   }
}
