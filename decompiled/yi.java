import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import javax.annotation.Nullable;

public class yi {
   private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new of("commands.spectate.self"));
   private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(var0 -> new of("commands.spectate.not_spectator", var0));

   public static void a(CommandDispatcher<db> var0) {
      _snowman.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("spectate").requires(var0x -> var0x.c(2)))
               .executes(var0x -> a((db)var0x.getSource(), null, ((db)var0x.getSource()).h())))
            .then(
               ((RequiredArgumentBuilder)dc.a("target", dk.a()).executes(var0x -> a((db)var0x.getSource(), dk.a(var0x, "target"), ((db)var0x.getSource()).h())))
                  .then(dc.a("player", dk.c()).executes(var0x -> a((db)var0x.getSource(), dk.a(var0x, "target"), dk.e(var0x, "player"))))
            )
      );
   }

   private static int a(db var0, @Nullable aqa var1, aah var2) throws CommandSyntaxException {
      if (_snowman == _snowman) {
         throw a.create();
      } else if (_snowman.d.b() != bru.e) {
         throw b.create(_snowman.d());
      } else {
         _snowman.e(_snowman);
         if (_snowman != null) {
            _snowman.a(new of("commands.spectate.success.started", _snowman.d()), false);
         } else {
            _snowman.a(new of("commands.spectate.success.stopped"), false);
         }

         return 1;
      }
   }
}
