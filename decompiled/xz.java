import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;

public class xz {
   private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new of("commands.save.alreadyOn"));

   public static void a(CommandDispatcher<db> var0) {
      _snowman.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("save-on").requires(var0x -> var0x.c(4))).executes(var0x -> {
         db _snowman = (db)var0x.getSource();
         boolean _snowmanx = false;

         for (aag _snowmanxx : _snowman.j().G()) {
            if (_snowmanxx != null && _snowmanxx.c) {
               _snowmanxx.c = false;
               _snowmanx = true;
            }
         }

         if (!_snowmanx) {
            throw a.create();
         } else {
            _snowman.a(new of("commands.save.enabled"), true);
            return 1;
         }
      }));
   }
}
