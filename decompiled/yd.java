import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

public class yd {
   public static void a(CommandDispatcher<db> var0, boolean var1) {
      _snowman.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("seed").requires(var1x -> !_snowman || var1x.c(2)))
            .executes(
               var0x -> {
                  long _snowman = ((db)var0x.getSource()).e().C();
                  nr _snowmanx = ns.a(
                     (nr)new oe(String.valueOf(_snowman))
                        .a(var2 -> var2.a(k.k).a(new np(np.a.f, String.valueOf(_snowman))).a(new nv(nv.a.a, new of("chat.copy.click"))).a(String.valueOf(_snowman)))
                  );
                  ((db)var0x.getSource()).a(new of("commands.seed.success", _snowmanx), false);
                  return (int)_snowman;
               }
            )
      );
   }
}
