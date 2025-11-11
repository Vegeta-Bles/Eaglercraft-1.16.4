import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import javax.annotation.Nullable;

public class wu {
   private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new of("commands.effect.give.failed"));
   private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new of("commands.effect.clear.everything.failed"));
   private static final SimpleCommandExceptionType c = new SimpleCommandExceptionType(new of("commands.effect.clear.specific.failed"));

   public static void a(CommandDispatcher<db> var0) {
      _snowman.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("effect").requires(var0x -> var0x.c(2)))
               .then(
                  ((LiteralArgumentBuilder)dc.a("clear").executes(var0x -> a((db)var0x.getSource(), ImmutableList.of(((db)var0x.getSource()).g()))))
                     .then(
                        ((RequiredArgumentBuilder)dc.a("targets", dk.b()).executes(var0x -> a((db)var0x.getSource(), dk.b(var0x, "targets"))))
                           .then(dc.a("effect", dq.a()).executes(var0x -> a((db)var0x.getSource(), dk.b(var0x, "targets"), dq.a(var0x, "effect"))))
                     )
               ))
            .then(
               dc.a("give")
                  .then(
                     dc.a("targets", dk.b())
                        .then(
                           ((RequiredArgumentBuilder)dc.a("effect", dq.a())
                                 .executes(var0x -> a((db)var0x.getSource(), dk.b(var0x, "targets"), dq.a(var0x, "effect"), null, 0, true)))
                              .then(
                                 ((RequiredArgumentBuilder)dc.a("seconds", IntegerArgumentType.integer(1, 1000000))
                                       .executes(
                                          var0x -> a(
                                                (db)var0x.getSource(),
                                                dk.b(var0x, "targets"),
                                                dq.a(var0x, "effect"),
                                                IntegerArgumentType.getInteger(var0x, "seconds"),
                                                0,
                                                true
                                             )
                                       ))
                                    .then(
                                       ((RequiredArgumentBuilder)dc.a("amplifier", IntegerArgumentType.integer(0, 255))
                                             .executes(
                                                var0x -> a(
                                                      (db)var0x.getSource(),
                                                      dk.b(var0x, "targets"),
                                                      dq.a(var0x, "effect"),
                                                      IntegerArgumentType.getInteger(var0x, "seconds"),
                                                      IntegerArgumentType.getInteger(var0x, "amplifier"),
                                                      true
                                                   )
                                             ))
                                          .then(
                                             dc.a("hideParticles", BoolArgumentType.bool())
                                                .executes(
                                                   var0x -> a(
                                                         (db)var0x.getSource(),
                                                         dk.b(var0x, "targets"),
                                                         dq.a(var0x, "effect"),
                                                         IntegerArgumentType.getInteger(var0x, "seconds"),
                                                         IntegerArgumentType.getInteger(var0x, "amplifier"),
                                                         !BoolArgumentType.getBool(var0x, "hideParticles")
                                                      )
                                                )
                                          )
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int a(db var0, Collection<? extends aqa> var1, aps var2, @Nullable Integer var3, int var4, boolean var5) throws CommandSyntaxException {
      int _snowman = 0;
      int _snowmanx;
      if (_snowman != null) {
         if (_snowman.a()) {
            _snowmanx = _snowman;
         } else {
            _snowmanx = _snowman * 20;
         }
      } else if (_snowman.a()) {
         _snowmanx = 1;
      } else {
         _snowmanx = 600;
      }

      for (aqa _snowmanxx : _snowman) {
         if (_snowmanxx instanceof aqm) {
            apu _snowmanxxx = new apu(_snowman, _snowmanx, _snowman, false, _snowman);
            if (((aqm)_snowmanxx).c(_snowmanxxx)) {
               _snowman++;
            }
         }
      }

      if (_snowman == 0) {
         throw a.create();
      } else {
         if (_snowman.size() == 1) {
            _snowman.a(new of("commands.effect.give.success.single", _snowman.d(), _snowman.iterator().next().d(), _snowmanx / 20), true);
         } else {
            _snowman.a(new of("commands.effect.give.success.multiple", _snowman.d(), _snowman.size(), _snowmanx / 20), true);
         }

         return _snowman;
      }
   }

   private static int a(db var0, Collection<? extends aqa> var1) throws CommandSyntaxException {
      int _snowman = 0;

      for (aqa _snowmanx : _snowman) {
         if (_snowmanx instanceof aqm && ((aqm)_snowmanx).dg()) {
            _snowman++;
         }
      }

      if (_snowman == 0) {
         throw b.create();
      } else {
         if (_snowman.size() == 1) {
            _snowman.a(new of("commands.effect.clear.everything.success.single", _snowman.iterator().next().d()), true);
         } else {
            _snowman.a(new of("commands.effect.clear.everything.success.multiple", _snowman.size()), true);
         }

         return _snowman;
      }
   }

   private static int a(db var0, Collection<? extends aqa> var1, aps var2) throws CommandSyntaxException {
      int _snowman = 0;

      for (aqa _snowmanx : _snowman) {
         if (_snowmanx instanceof aqm && ((aqm)_snowmanx).d(_snowman)) {
            _snowman++;
         }
      }

      if (_snowman == 0) {
         throw c.create();
      } else {
         if (_snowman.size() == 1) {
            _snowman.a(new of("commands.effect.clear.specific.success.single", _snowman.d(), _snowman.iterator().next().d()), true);
         } else {
            _snowman.a(new of("commands.effect.clear.specific.success.multiple", _snowman.d(), _snowman.size()), true);
         }

         return _snowman;
      }
   }
}
