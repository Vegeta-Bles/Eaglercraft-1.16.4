import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;

public class xj {
   public static final DynamicCommandExceptionType a = new DynamicCommandExceptionType(var0 -> new of("commands.locatebiome.invalid", var0));
   private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(var0 -> new of("commands.locatebiome.notFound", var0));

   public static void a(CommandDispatcher<db> var0) {
      _snowman.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("locatebiome").requires(var0x -> var0x.c(2)))
            .then(dc.a("biome", dy.a()).suggests(fm.d).executes(var0x -> a((db)var0x.getSource(), (vk)var0x.getArgument("biome", vk.class))))
      );
   }

   private static int a(db var0, vk var1) throws CommandSyntaxException {
      bsv _snowman = _snowman.j().aY().b(gm.ay).b(_snowman).orElseThrow(() -> a.create(_snowman));
      fx _snowmanx = new fx(_snowman.d());
      fx _snowmanxx = _snowman.e().a(_snowman, _snowmanx, 6400, 8);
      String _snowmanxxx = _snowman.toString();
      if (_snowmanxx == null) {
         throw b.create(_snowmanxxx);
      } else {
         return xk.a(_snowman, _snowmanxxx, _snowmanx, _snowmanxx, "commands.locatebiome.success");
      }
   }
}
