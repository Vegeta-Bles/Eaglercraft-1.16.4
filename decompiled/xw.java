import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import java.util.List;

public class xw {
   public static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new of("commands.replaceitem.block.failed"));
   public static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(var0 -> new of("commands.replaceitem.slot.inapplicable", var0));
   public static final Dynamic2CommandExceptionType c = new Dynamic2CommandExceptionType(
      (var0, var1) -> new of("commands.replaceitem.entity.failed", var0, var1)
   );

   public static void a(CommandDispatcher<db> var0) {
      _snowman.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("replaceitem").requires(var0x -> var0x.c(2)))
               .then(
                  dc.a("block")
                     .then(
                        dc.a("pos", ek.a())
                           .then(
                              dc.a("slot", eb.a())
                                 .then(
                                    ((RequiredArgumentBuilder)dc.a("item", ew.a())
                                          .executes(var0x -> a((db)var0x.getSource(), ek.a(var0x, "pos"), eb.a(var0x, "slot"), ew.a(var0x, "item").a(1, false))))
                                       .then(
                                          dc.a("count", IntegerArgumentType.integer(1, 64))
                                             .executes(
                                                var0x -> a(
                                                      (db)var0x.getSource(),
                                                      ek.a(var0x, "pos"),
                                                      eb.a(var0x, "slot"),
                                                      ew.a(var0x, "item").a(IntegerArgumentType.getInteger(var0x, "count"), true)
                                                   )
                                             )
                                       )
                                 )
                           )
                     )
               ))
            .then(
               dc.a("entity")
                  .then(
                     dc.a("targets", dk.b())
                        .then(
                           dc.a("slot", eb.a())
                              .then(
                                 ((RequiredArgumentBuilder)dc.a("item", ew.a())
                                       .executes(
                                          var0x -> a((db)var0x.getSource(), dk.b(var0x, "targets"), eb.a(var0x, "slot"), ew.a(var0x, "item").a(1, false))
                                       ))
                                    .then(
                                       dc.a("count", IntegerArgumentType.integer(1, 64))
                                          .executes(
                                             var0x -> a(
                                                   (db)var0x.getSource(),
                                                   dk.b(var0x, "targets"),
                                                   eb.a(var0x, "slot"),
                                                   ew.a(var0x, "item").a(IntegerArgumentType.getInteger(var0x, "count"), true)
                                                )
                                          )
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int a(db var0, fx var1, int var2, bmb var3) throws CommandSyntaxException {
      ccj _snowman = _snowman.e().c(_snowman);
      if (!(_snowman instanceof aon)) {
         throw a.create();
      } else {
         aon _snowmanx = (aon)_snowman;
         if (_snowman >= 0 && _snowman < _snowmanx.Z_()) {
            _snowmanx.a(_snowman, _snowman);
            _snowman.a(new of("commands.replaceitem.block.success", _snowman.u(), _snowman.v(), _snowman.w(), _snowman.C()), true);
            return 1;
         } else {
            throw b.create(_snowman);
         }
      }
   }

   private static int a(db var0, Collection<? extends aqa> var1, int var2, bmb var3) throws CommandSyntaxException {
      List<aqa> _snowman = Lists.newArrayListWithCapacity(_snowman.size());

      for (aqa _snowmanx : _snowman) {
         if (_snowmanx instanceof aah) {
            ((aah)_snowmanx).bo.c();
         }

         if (_snowmanx.a_(_snowman, _snowman.i())) {
            _snowman.add(_snowmanx);
            if (_snowmanx instanceof aah) {
               ((aah)_snowmanx).bo.c();
            }
         }
      }

      if (_snowman.isEmpty()) {
         throw c.create(_snowman.C(), _snowman);
      } else {
         if (_snowman.size() == 1) {
            _snowman.a(new of("commands.replaceitem.entity.success.single", _snowman.iterator().next().d(), _snowman.C()), true);
         } else {
            _snowman.a(new of("commands.replaceitem.entity.success.multiple", _snowman.size(), _snowman.C()), true);
         }

         return _snowman.size();
      }
   }
}
