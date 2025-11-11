import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.Collection;
import java.util.UUID;
import java.util.function.Consumer;

public class xm {
   public static void a(CommandDispatcher<db> var0) {
      LiteralCommandNode<db> _snowman = _snowman.register(
         (LiteralArgumentBuilder)dc.a("msg")
            .then(
               dc.a("targets", dk.d())
                  .then(dc.a("message", dp.a()).executes(var0x -> a((db)var0x.getSource(), dk.f(var0x, "targets"), dp.a(var0x, "message"))))
            )
      );
      _snowman.register((LiteralArgumentBuilder)dc.a("tell").redirect(_snowman));
      _snowman.register((LiteralArgumentBuilder)dc.a("w").redirect(_snowman));
   }

   private static int a(db var0, Collection<aah> var1, nr var2) {
      UUID _snowman = _snowman.f() == null ? x.b : _snowman.f().bS();
      aqa _snowmanx = _snowman.f();
      Consumer<nr> _snowmanxx;
      if (_snowmanx instanceof aah) {
         aah _snowmanxxx = (aah)_snowmanx;
         _snowmanxx = var2x -> _snowman.a(new of("commands.message.display.outgoing", var2x, _snowman).a(new k[]{k.h, k.u}), _snowman.bS());
      } else {
         _snowmanxx = var2x -> _snowman.a(new of("commands.message.display.outgoing", var2x, _snowman).a(new k[]{k.h, k.u}), false);
      }

      for (aah _snowmanxxx : _snowman) {
         _snowmanxx.accept(_snowmanxxx.d());
         _snowmanxxx.a(new of("commands.message.display.incoming", _snowman.b(), _snowman).a(new k[]{k.h, k.u}), _snowman);
      }

      return _snowman.size();
   }
}
