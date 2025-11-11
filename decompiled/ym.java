import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;

public class ym {
   private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new of("commands.summon.failed"));
   private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new of("commands.summon.failed.uuid"));
   private static final SimpleCommandExceptionType c = new SimpleCommandExceptionType(new of("commands.summon.invalidPosition"));

   public static void a(CommandDispatcher<db> var0) {
      _snowman.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("summon").requires(var0x -> var0x.c(2)))
            .then(
               ((RequiredArgumentBuilder)dc.a("entity", dl.a())
                     .suggests(fm.e)
                     .executes(var0x -> a((db)var0x.getSource(), dl.a(var0x, "entity"), ((db)var0x.getSource()).d(), new md(), true)))
                  .then(
                     ((RequiredArgumentBuilder)dc.a("pos", er.a())
                           .executes(var0x -> a((db)var0x.getSource(), dl.a(var0x, "entity"), er.a(var0x, "pos"), new md(), true)))
                        .then(
                           dc.a("nbt", dh.a())
                              .executes(var0x -> a((db)var0x.getSource(), dl.a(var0x, "entity"), er.a(var0x, "pos"), dh.a(var0x, "nbt"), false))
                        )
                  )
            )
      );
   }

   private static int a(db var0, vk var1, dcn var2, md var3, boolean var4) throws CommandSyntaxException {
      fx _snowman = new fx(_snowman);
      if (!brx.l(_snowman)) {
         throw c.create();
      } else {
         md _snowmanx = _snowman.g();
         _snowmanx.a("id", _snowman.toString());
         aag _snowmanxx = _snowman.e();
         aqa _snowmanxxx = aqe.a(_snowmanx, _snowmanxx, var1x -> {
            var1x.b(_snowman.b, _snowman.c, _snowman.d, var1x.p, var1x.q);
            return var1x;
         });
         if (_snowmanxxx == null) {
            throw a.create();
         } else {
            if (_snowman && _snowmanxxx instanceof aqn) {
               ((aqn)_snowmanxxx).a(_snowman.e(), _snowman.e().d(_snowmanxxx.cB()), aqp.n, null, null);
            }

            if (!_snowmanxx.g(_snowmanxxx)) {
               throw b.create();
            } else {
               _snowman.a(new of("commands.summon.success", _snowmanxxx.d()), true);
               return 1;
            }
         }
      }
   }
}
