import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;

public class xs {
   private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new of("commands.publish.failed"));
   private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(var0 -> new of("commands.publish.alreadyPublished", var0));

   public static void a(CommandDispatcher<db> var0) {
      _snowman.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("publish").requires(var0x -> var0x.c(4)))
               .executes(var0x -> a((db)var0x.getSource(), aff.a())))
            .then(
               dc.a("port", IntegerArgumentType.integer(0, 65535)).executes(var0x -> a((db)var0x.getSource(), IntegerArgumentType.getInteger(var0x, "port")))
            )
      );
   }

   private static int a(db var0, int var1) throws CommandSyntaxException {
      if (_snowman.j().n()) {
         throw b.create(_snowman.j().M());
      } else if (!_snowman.j().a(_snowman.j().s(), false, _snowman)) {
         throw a.create();
      } else {
         _snowman.a(new of("commands.publish.success", _snowman), true);
         return _snowman;
      }
   }
}
