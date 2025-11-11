import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Collection;

public class xe {
   public static void a(CommandDispatcher<db> var0) {
      _snowman.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("give").requires(var0x -> var0x.c(2)))
            .then(
               dc.a("targets", dk.d())
                  .then(
                     ((RequiredArgumentBuilder)dc.a("item", ew.a()).executes(var0x -> a((db)var0x.getSource(), ew.a(var0x, "item"), dk.f(var0x, "targets"), 1)))
                        .then(
                           dc.a("count", IntegerArgumentType.integer(1))
                              .executes(
                                 var0x -> a((db)var0x.getSource(), ew.a(var0x, "item"), dk.f(var0x, "targets"), IntegerArgumentType.getInteger(var0x, "count"))
                              )
                        )
                  )
            )
      );
   }

   private static int a(db var0, ex var1, Collection<aah> var2, int var3) throws CommandSyntaxException {
      for (aah _snowman : _snowman) {
         int _snowmanx = _snowman;

         while (_snowmanx > 0) {
            int _snowmanxx = Math.min(_snowman.a().i(), _snowmanx);
            _snowmanx -= _snowmanxx;
            bmb _snowmanxxx = _snowman.a(_snowmanxx, false);
            boolean _snowmanxxxx = _snowman.bm.e(_snowmanxxx);
            if (_snowmanxxxx && _snowmanxxx.a()) {
               _snowmanxxx.e(1);
               bcv _snowmanxxxxx = _snowman.a(_snowmanxxx, false);
               if (_snowmanxxxxx != null) {
                  _snowmanxxxxx.s();
               }

               _snowman.l.a(null, _snowman.cD(), _snowman.cE(), _snowman.cH(), adq.gL, adr.h, 0.2F, ((_snowman.cY().nextFloat() - _snowman.cY().nextFloat()) * 0.7F + 1.0F) * 2.0F);
               _snowman.bo.c();
            } else {
               bcv _snowmanxxxxx = _snowman.a(_snowmanxxx, false);
               if (_snowmanxxxxx != null) {
                  _snowmanxxxxx.n();
                  _snowmanxxxxx.b(_snowman.bS());
               }
            }
         }
      }

      if (_snowman.size() == 1) {
         _snowman.a(new of("commands.give.success.single", _snowman, _snowman.a(_snowman, false).C(), _snowman.iterator().next().d()), true);
      } else {
         _snowman.a(new of("commands.give.success.single", _snowman, _snowman.a(_snowman, false).C(), _snowman.size()), true);
      }

      return _snowman.size();
   }
}
