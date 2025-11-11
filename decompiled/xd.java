import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;

public class xd {
   public static void a(CommandDispatcher<db> var0) {
      final LiteralArgumentBuilder<db> _snowman = (LiteralArgumentBuilder<db>)dc.a("gamerule").requires(var0x -> var0x.c(2));
      brt.a(
         new brt.c() {
            @Override
            public <T extends brt.g<T>> void a(brt.e<T> var1x, brt.f<T> var2) {
               _snowman.then(
                  ((LiteralArgumentBuilder)dc.a(_snowman.a()).executes(var1xx -> xd.b((db)var1xx.getSource(), _snowman)))
                     .then(_snowman.a("value").executes(var1xx -> xd.b(var1xx, _snowman)))
               );
            }
         }
      );
      _snowman.register(_snowman);
   }

   private static <T extends brt.g<T>> int b(CommandContext<db> var0, brt.e<T> var1) {
      db _snowman = (db)_snowman.getSource();
      T _snowmanx = _snowman.j().aL().a(_snowman);
      _snowmanx.b(_snowman, "value");
      _snowman.a(new of("commands.gamerule.set", _snowman.a(), _snowmanx.toString()), true);
      return _snowmanx.c();
   }

   private static <T extends brt.g<T>> int b(db var0, brt.e<T> var1) {
      T _snowman = _snowman.j().aL().a(_snowman);
      _snowman.a(new of("commands.gamerule.query", _snowman.a(), _snowman.toString()), false);
      return _snowman.c();
   }
}
