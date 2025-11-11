import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import java.util.Collection;

public class xh {
   public static void a(CommandDispatcher<db> var0) {
      _snowman.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("kill").requires(var0x -> var0x.c(2)))
               .executes(var0x -> a((db)var0x.getSource(), ImmutableList.of(((db)var0x.getSource()).g()))))
            .then(dc.a("targets", dk.b()).executes(var0x -> a((db)var0x.getSource(), dk.b(var0x, "targets"))))
      );
   }

   private static int a(db var0, Collection<? extends aqa> var1) {
      for (aqa _snowman : _snowman) {
         _snowman.aa();
      }

      if (_snowman.size() == 1) {
         _snowman.a(new of("commands.kill.success.single", _snowman.iterator().next().d()), true);
      } else {
         _snowman.a(new of("commands.kill.success.multiple", _snowman.size()), true);
      }

      return _snowman.size();
   }
}
