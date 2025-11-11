import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.List;

public class yp {
   private static final ob a = ob.a.a(new nv(nv.a.a, new of("chat.type.team.hover"))).a(new np(np.a.d, "/teammsg "));
   private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new of("commands.teammsg.failed.noteam"));

   public static void a(CommandDispatcher<db> var0) {
      LiteralCommandNode<db> _snowman = _snowman.register(
         (LiteralArgumentBuilder)dc.a("teammsg").then(dc.a("message", dp.a()).executes(var0x -> a((db)var0x.getSource(), dp.a(var0x, "message"))))
      );
      _snowman.register((LiteralArgumentBuilder)dc.a("tm").redirect(_snowman));
   }

   private static int a(db var0, nr var1) throws CommandSyntaxException {
      aqa _snowman = _snowman.g();
      ddl _snowmanx = (ddl)_snowman.bG();
      if (_snowmanx == null) {
         throw b.create();
      } else {
         nr _snowmanxx = _snowmanx.d().c(a);
         List<aah> _snowmanxxx = _snowman.j().ae().s();

         for (aah _snowmanxxxx : _snowmanxxx) {
            if (_snowmanxxxx == _snowman) {
               _snowmanxxxx.a(new of("chat.type.team.sent", _snowmanxx, _snowman.b(), _snowman), _snowman.bS());
            } else if (_snowmanxxxx.bG() == _snowmanx) {
               _snowmanxxxx.a(new of("chat.type.team.text", _snowmanxx, _snowman.b(), _snowman), _snowman.bS());
            }
         }

         return _snowmanxxx.size();
      }
   }
}
