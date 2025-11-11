import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import java.util.Collection;

public class wi {
   public static void a(CommandDispatcher<db> var0) {
      _snowman.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("banlist").requires(var0x -> var0x.c(3)))
                  .executes(var0x -> {
                     acu _snowman = ((db)var0x.getSource()).j().ae();
                     return a((db)var0x.getSource(), Lists.newArrayList(Iterables.concat(_snowman.f().d(), _snowman.g().d())));
                  }))
               .then(dc.a("ips").executes(var0x -> a((db)var0x.getSource(), ((db)var0x.getSource()).j().ae().g().d()))))
            .then(dc.a("players").executes(var0x -> a((db)var0x.getSource(), ((db)var0x.getSource()).j().ae().f().d())))
      );
   }

   private static int a(db var0, Collection<? extends acp<?>> var1) {
      if (_snowman.isEmpty()) {
         _snowman.a(new of("commands.banlist.none"), false);
      } else {
         _snowman.a(new of("commands.banlist.list", _snowman.size()), false);

         for (acp<?> _snowman : _snowman) {
            _snowman.a(new of("commands.banlist.entry", _snowman.e(), _snowman.b(), _snowman.d()), false);
         }
      }

      return _snowman.size();
   }
}
