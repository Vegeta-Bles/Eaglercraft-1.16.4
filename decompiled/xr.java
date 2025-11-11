import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;

public class xr {
   private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new of("commands.playsound.failed"));

   public static void a(CommandDispatcher<db> var0) {
      RequiredArgumentBuilder<db, vk> _snowman = dc.a("sound", dy.a()).suggests(fm.c);

      for (adr _snowmanx : adr.values()) {
         _snowman.then(a(_snowmanx));
      }

      _snowman.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("playsound").requires(var0x -> var0x.c(2))).then(_snowman));
   }

   private static LiteralArgumentBuilder<db> a(adr var0) {
      return (LiteralArgumentBuilder<db>)dc.a(_snowman.a())
         .then(
            ((RequiredArgumentBuilder)dc.a("targets", dk.d())
                  .executes(var1 -> a((db)var1.getSource(), dk.f(var1, "targets"), dy.e(var1, "sound"), _snowman, ((db)var1.getSource()).d(), 1.0F, 1.0F, 0.0F)))
               .then(
                  ((RequiredArgumentBuilder)dc.a("pos", er.a())
                        .executes(var1 -> a((db)var1.getSource(), dk.f(var1, "targets"), dy.e(var1, "sound"), _snowman, er.a(var1, "pos"), 1.0F, 1.0F, 0.0F)))
                     .then(
                        ((RequiredArgumentBuilder)dc.a("volume", FloatArgumentType.floatArg(0.0F))
                              .executes(
                                 var1 -> a(
                                       (db)var1.getSource(),
                                       dk.f(var1, "targets"),
                                       dy.e(var1, "sound"),
                                       _snowman,
                                       er.a(var1, "pos"),
                                       (Float)var1.getArgument("volume", Float.class),
                                       1.0F,
                                       0.0F
                                    )
                              ))
                           .then(
                              ((RequiredArgumentBuilder)dc.a("pitch", FloatArgumentType.floatArg(0.0F, 2.0F))
                                    .executes(
                                       var1 -> a(
                                             (db)var1.getSource(),
                                             dk.f(var1, "targets"),
                                             dy.e(var1, "sound"),
                                             _snowman,
                                             er.a(var1, "pos"),
                                             (Float)var1.getArgument("volume", Float.class),
                                             (Float)var1.getArgument("pitch", Float.class),
                                             0.0F
                                          )
                                    ))
                                 .then(
                                    dc.a("minVolume", FloatArgumentType.floatArg(0.0F, 1.0F))
                                       .executes(
                                          var1 -> a(
                                                (db)var1.getSource(),
                                                dk.f(var1, "targets"),
                                                dy.e(var1, "sound"),
                                                _snowman,
                                                er.a(var1, "pos"),
                                                (Float)var1.getArgument("volume", Float.class),
                                                (Float)var1.getArgument("pitch", Float.class),
                                                (Float)var1.getArgument("minVolume", Float.class)
                                             )
                                       )
                                 )
                           )
                     )
               )
         );
   }

   private static int a(db var0, Collection<aah> var1, vk var2, adr var3, dcn var4, float var5, float var6, float var7) throws CommandSyntaxException {
      double _snowman = Math.pow(_snowman > 1.0F ? (double)(_snowman * 16.0F) : 16.0, 2.0);
      int _snowmanx = 0;

      for (aah _snowmanxx : _snowman) {
         double _snowmanxxx = _snowman.b - _snowmanxx.cD();
         double _snowmanxxxx = _snowman.c - _snowmanxx.cE();
         double _snowmanxxxxx = _snowman.d - _snowmanxx.cH();
         double _snowmanxxxxxx = _snowmanxxx * _snowmanxxx + _snowmanxxxx * _snowmanxxxx + _snowmanxxxxx * _snowmanxxxxx;
         dcn _snowmanxxxxxxx = _snowman;
         float _snowmanxxxxxxxx = _snowman;
         if (_snowmanxxxxxx > _snowman) {
            if (_snowman <= 0.0F) {
               continue;
            }

            double _snowmanxxxxxxxxx = (double)afm.a(_snowmanxxxxxx);
            _snowmanxxxxxxx = new dcn(_snowmanxx.cD() + _snowmanxxx / _snowmanxxxxxxxxx * 2.0, _snowmanxx.cE() + _snowmanxxxx / _snowmanxxxxxxxxx * 2.0, _snowmanxx.cH() + _snowmanxxxxx / _snowmanxxxxxxxxx * 2.0);
            _snowmanxxxxxxxx = _snowman;
         }

         _snowmanxx.b.a(new pl(_snowman, _snowman, _snowmanxxxxxxx, _snowmanxxxxxxxx, _snowman));
         _snowmanx++;
      }

      if (_snowmanx == 0) {
         throw a.create();
      } else {
         if (_snowman.size() == 1) {
            _snowman.a(new of("commands.playsound.success.single", _snowman, _snowman.iterator().next().d()), true);
         } else {
            _snowman.a(new of("commands.playsound.success.multiple", _snowman, _snowman.size()), true);
         }

         return _snowmanx;
      }
   }
}
