import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import java.util.List;
import java.util.function.Function;

public class xi {
   public static void a(CommandDispatcher<db> var0) {
      _snowman.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("list").executes(var0x -> a((db)var0x.getSource())))
            .then(dc.a("uuids").executes(var0x -> b((db)var0x.getSource())))
      );
   }

   private static int a(db var0) {
      return a(_snowman, bfw::d);
   }

   private static int b(db var0) {
      return a(_snowman, var0x -> new of("commands.list.nameAndId", var0x.R(), var0x.eA().getId()));
   }

   private static int a(db var0, Function<aah, nr> var1) {
      acu _snowman = _snowman.j().ae();
      List<aah> _snowmanx = _snowman.s();
      nr _snowmanxx = ns.b(_snowmanx, _snowman);
      _snowman.a(new of("commands.list.players", _snowmanx.size(), _snowman.n(), _snowmanxx), false);
      return _snowmanx.size();
   }
}
