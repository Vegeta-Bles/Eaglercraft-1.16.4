import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;

public class ww {
   private static final DynamicCommandExceptionType a = new DynamicCommandExceptionType(var0 -> new of("commands.enchant.failed.entity", var0));
   private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(var0 -> new of("commands.enchant.failed.itemless", var0));
   private static final DynamicCommandExceptionType c = new DynamicCommandExceptionType(var0 -> new of("commands.enchant.failed.incompatible", var0));
   private static final Dynamic2CommandExceptionType d = new Dynamic2CommandExceptionType((var0, var1) -> new of("commands.enchant.failed.level", var0, var1));
   private static final SimpleCommandExceptionType e = new SimpleCommandExceptionType(new of("commands.enchant.failed"));

   public static void a(CommandDispatcher<db> var0) {
      _snowman.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("enchant").requires(var0x -> var0x.c(2)))
            .then(
               dc.a("targets", dk.b())
                  .then(
                     ((RequiredArgumentBuilder)dc.a("enchantment", dn.a())
                           .executes(var0x -> a((db)var0x.getSource(), dk.b(var0x, "targets"), dn.a(var0x, "enchantment"), 1)))
                        .then(
                           dc.a("level", IntegerArgumentType.integer(0))
                              .executes(
                                 var0x -> a(
                                       (db)var0x.getSource(),
                                       dk.b(var0x, "targets"),
                                       dn.a(var0x, "enchantment"),
                                       IntegerArgumentType.getInteger(var0x, "level")
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int a(db var0, Collection<? extends aqa> var1, bps var2, int var3) throws CommandSyntaxException {
      if (_snowman > _snowman.a()) {
         throw d.create(_snowman, _snowman.a());
      } else {
         int _snowman = 0;

         for (aqa _snowmanx : _snowman) {
            if (_snowmanx instanceof aqm) {
               aqm _snowmanxx = (aqm)_snowmanx;
               bmb _snowmanxxx = _snowmanxx.dD();
               if (!_snowmanxxx.a()) {
                  if (_snowman.a(_snowmanxxx) && bpu.a(bpu.a(_snowmanxxx).keySet(), _snowman)) {
                     _snowmanxxx.a(_snowman, _snowman);
                     _snowman++;
                  } else if (_snowman.size() == 1) {
                     throw c.create(_snowmanxxx.b().h(_snowmanxxx).getString());
                  }
               } else if (_snowman.size() == 1) {
                  throw b.create(_snowmanxx.R().getString());
               }
            } else if (_snowman.size() == 1) {
               throw a.create(_snowmanx.R().getString());
            }
         }

         if (_snowman == 0) {
            throw e.create();
         } else {
            if (_snowman.size() == 1) {
               _snowman.a(new of("commands.enchant.success.single", _snowman.d(_snowman), _snowman.iterator().next().d()), true);
            } else {
               _snowman.a(new of("commands.enchant.success.multiple", _snowman.d(_snowman), _snowman.size()), true);
            }

            return _snowman;
         }
      }
   }
}
