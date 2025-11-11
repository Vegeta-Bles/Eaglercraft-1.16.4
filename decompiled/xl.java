import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class xl {
   public static final SuggestionProvider<db> a = (var0, var1) -> {
      cyz _snowman = ((db)var0.getSource()).j().aJ();
      return dd.a(_snowman.a(), var1);
   };
   private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(var0 -> new of("commands.drop.no_held_items", var0));
   private static final DynamicCommandExceptionType c = new DynamicCommandExceptionType(var0 -> new of("commands.drop.no_loot_table", var0));

   public static void a(CommandDispatcher<db> var0) {
      _snowman.register(
         a(
            dc.a("loot").requires(var0x -> var0x.c(2)),
            (var0x, var1) -> var0x.then(
                     dc.a("fish")
                        .then(
                           dc.a("loot_table", dy.a())
                              .suggests(a)
                              .then(
                                 ((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)dc.a("pos", ek.a())
                                             .executes(var1x -> a(var1x, dy.e(var1x, "loot_table"), ek.a(var1x, "pos"), bmb.b, var1)))
                                          .then(
                                             dc.a("tool", ew.a())
                                                .executes(
                                                   var1x -> a(var1x, dy.e(var1x, "loot_table"), ek.a(var1x, "pos"), ew.a(var1x, "tool").a(1, false), var1)
                                                )
                                          ))
                                       .then(
                                          dc.a("mainhand")
                                             .executes(var1x -> a(var1x, dy.e(var1x, "loot_table"), ek.a(var1x, "pos"), a((db)var1x.getSource(), aqf.a), var1))
                                       ))
                                    .then(
                                       dc.a("offhand")
                                          .executes(var1x -> a(var1x, dy.e(var1x, "loot_table"), ek.a(var1x, "pos"), a((db)var1x.getSource(), aqf.b), var1))
                                    )
                              )
                        )
                  )
                  .then(dc.a("loot").then(dc.a("loot_table", dy.a()).suggests(a).executes(var1x -> a(var1x, dy.e(var1x, "loot_table"), var1))))
                  .then(dc.a("kill").then(dc.a("target", dk.a()).executes(var1x -> a(var1x, dk.a(var1x, "target"), var1))))
                  .then(
                     dc.a("mine")
                        .then(
                           ((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)dc.a("pos", ek.a())
                                       .executes(var1x -> a(var1x, ek.a(var1x, "pos"), bmb.b, var1)))
                                    .then(dc.a("tool", ew.a()).executes(var1x -> a(var1x, ek.a(var1x, "pos"), ew.a(var1x, "tool").a(1, false), var1))))
                                 .then(dc.a("mainhand").executes(var1x -> a(var1x, ek.a(var1x, "pos"), a((db)var1x.getSource(), aqf.a), var1))))
                              .then(dc.a("offhand").executes(var1x -> a(var1x, ek.a(var1x, "pos"), a((db)var1x.getSource(), aqf.b), var1)))
                        )
                  )
         )
      );
   }

   private static <T extends ArgumentBuilder<db, T>> T a(T var0, xl.c var1) {
      return (T)_snowman.then(
            ((LiteralArgumentBuilder)dc.a("replace")
                  .then(
                     dc.a("entity")
                        .then(
                           dc.a("entities", dk.b())
                              .then(
                                 _snowman.construct(
                                       dc.a("slot", eb.a()), (var0x, var1x, var2) -> a(dk.b(var0x, "entities"), eb.a(var0x, "slot"), var1x.size(), var1x, var2)
                                    )
                                    .then(
                                       _snowman.construct(
                                          dc.a("count", IntegerArgumentType.integer(0)),
                                          (var0x, var1x, var2) -> a(
                                                dk.b(var0x, "entities"), eb.a(var0x, "slot"), IntegerArgumentType.getInteger(var0x, "count"), var1x, var2
                                             )
                                       )
                                    )
                              )
                        )
                  ))
               .then(
                  dc.a("block")
                     .then(
                        dc.a("targetPos", ek.a())
                           .then(
                              _snowman.construct(
                                    dc.a("slot", eb.a()),
                                    (var0x, var1x, var2) -> a((db)var0x.getSource(), ek.a(var0x, "targetPos"), eb.a(var0x, "slot"), var1x.size(), var1x, var2)
                                 )
                                 .then(
                                    _snowman.construct(
                                       dc.a("count", IntegerArgumentType.integer(0)),
                                       (var0x, var1x, var2) -> a(
                                             (db)var0x.getSource(),
                                             ek.a(var0x, "targetPos"),
                                             IntegerArgumentType.getInteger(var0x, "slot"),
                                             IntegerArgumentType.getInteger(var0x, "count"),
                                             var1x,
                                             var2
                                          )
                                    )
                                 )
                           )
                     )
               )
         )
         .then(
            dc.a("insert")
               .then(_snowman.construct(dc.a("targetPos", ek.a()), (var0x, var1x, var2) -> a((db)var0x.getSource(), ek.a(var0x, "targetPos"), var1x, var2)))
         )
         .then(dc.a("give").then(_snowman.construct(dc.a("players", dk.d()), (var0x, var1x, var2) -> a(dk.f(var0x, "players"), var1x, var2))))
         .then(
            dc.a("spawn").then(_snowman.construct(dc.a("targetPos", er.a()), (var0x, var1x, var2) -> a((db)var0x.getSource(), er.a(var0x, "targetPos"), var1x, var2)))
         );
   }

   private static aon a(db var0, fx var1) throws CommandSyntaxException {
      ccj _snowman = _snowman.e().c(_snowman);
      if (!(_snowman instanceof aon)) {
         throw xw.a.create();
      } else {
         return (aon)_snowman;
      }
   }

   private static int a(db var0, fx var1, List<bmb> var2, xl.a var3) throws CommandSyntaxException {
      aon _snowman = a(_snowman, _snowman);
      List<bmb> _snowmanx = Lists.newArrayListWithCapacity(_snowman.size());

      for (bmb _snowmanxx : _snowman) {
         if (a(_snowman, _snowmanxx.i())) {
            _snowman.X_();
            _snowmanx.add(_snowmanxx);
         }
      }

      _snowman.accept(_snowmanx);
      return _snowmanx.size();
   }

   private static boolean a(aon var0, bmb var1) {
      boolean _snowman = false;

      for (int _snowmanx = 0; _snowmanx < _snowman.Z_() && !_snowman.a(); _snowmanx++) {
         bmb _snowmanxx = _snowman.a(_snowmanx);
         if (_snowman.b(_snowmanx, _snowman)) {
            if (_snowmanxx.a()) {
               _snowman.a(_snowmanx, _snowman);
               _snowman = true;
               break;
            }

            if (a(_snowmanxx, _snowman)) {
               int _snowmanxxx = _snowman.c() - _snowmanxx.E();
               int _snowmanxxxx = Math.min(_snowman.E(), _snowmanxxx);
               _snowman.g(_snowmanxxxx);
               _snowmanxx.f(_snowmanxxxx);
               _snowman = true;
            }
         }
      }

      return _snowman;
   }

   private static int a(db var0, fx var1, int var2, int var3, List<bmb> var4, xl.a var5) throws CommandSyntaxException {
      aon _snowman = a(_snowman, _snowman);
      int _snowmanx = _snowman.Z_();
      if (_snowman >= 0 && _snowman < _snowmanx) {
         List<bmb> _snowmanxx = Lists.newArrayListWithCapacity(_snowman.size());

         for (int _snowmanxxx = 0; _snowmanxxx < _snowman; _snowmanxxx++) {
            int _snowmanxxxx = _snowman + _snowmanxxx;
            bmb _snowmanxxxxx = _snowmanxxx < _snowman.size() ? _snowman.get(_snowmanxxx) : bmb.b;
            if (_snowman.b(_snowmanxxxx, _snowmanxxxxx)) {
               _snowman.a(_snowmanxxxx, _snowmanxxxxx);
               _snowmanxx.add(_snowmanxxxxx);
            }
         }

         _snowman.accept(_snowmanxx);
         return _snowmanxx.size();
      } else {
         throw xw.b.create(_snowman);
      }
   }

   private static boolean a(bmb var0, bmb var1) {
      return _snowman.b() == _snowman.b() && _snowman.g() == _snowman.g() && _snowman.E() <= _snowman.c() && Objects.equals(_snowman.o(), _snowman.o());
   }

   private static int a(Collection<aah> var0, List<bmb> var1, xl.a var2) throws CommandSyntaxException {
      List<bmb> _snowman = Lists.newArrayListWithCapacity(_snowman.size());

      for (bmb _snowmanx : _snowman) {
         for (aah _snowmanxx : _snowman) {
            if (_snowmanxx.bm.e(_snowmanx.i())) {
               _snowman.add(_snowmanx);
            }
         }
      }

      _snowman.accept(_snowman);
      return _snowman.size();
   }

   private static void a(aqa var0, List<bmb> var1, int var2, int var3, List<bmb> var4) {
      for (int _snowman = 0; _snowman < _snowman; _snowman++) {
         bmb _snowmanx = _snowman < _snowman.size() ? _snowman.get(_snowman) : bmb.b;
         if (_snowman.a_(_snowman + _snowman, _snowmanx.i())) {
            _snowman.add(_snowmanx);
         }
      }
   }

   private static int a(Collection<? extends aqa> var0, int var1, int var2, List<bmb> var3, xl.a var4) throws CommandSyntaxException {
      List<bmb> _snowman = Lists.newArrayListWithCapacity(_snowman.size());

      for (aqa _snowmanx : _snowman) {
         if (_snowmanx instanceof aah) {
            aah _snowmanxx = (aah)_snowmanx;
            _snowmanxx.bo.c();
            a(_snowmanx, _snowman, _snowman, _snowman, _snowman);
            _snowmanxx.bo.c();
         } else {
            a(_snowmanx, _snowman, _snowman, _snowman, _snowman);
         }
      }

      _snowman.accept(_snowman);
      return _snowman.size();
   }

   private static int a(db var0, dcn var1, List<bmb> var2, xl.a var3) throws CommandSyntaxException {
      aag _snowman = _snowman.e();
      _snowman.forEach(var2x -> {
         bcv _snowmanx = new bcv(_snowman, _snowman.b, _snowman.c, _snowman.d, var2x.i());
         _snowmanx.m();
         _snowman.c(_snowmanx);
      });
      _snowman.accept(_snowman);
      return _snowman.size();
   }

   private static void a(db var0, List<bmb> var1) {
      if (_snowman.size() == 1) {
         bmb _snowman = _snowman.get(0);
         _snowman.a(new of("commands.drop.success.single", _snowman.E(), _snowman.C()), false);
      } else {
         _snowman.a(new of("commands.drop.success.multiple", _snowman.size()), false);
      }
   }

   private static void a(db var0, List<bmb> var1, vk var2) {
      if (_snowman.size() == 1) {
         bmb _snowman = _snowman.get(0);
         _snowman.a(new of("commands.drop.success.single_with_table", _snowman.E(), _snowman.C(), _snowman), false);
      } else {
         _snowman.a(new of("commands.drop.success.multiple_with_table", _snowman.size(), _snowman), false);
      }
   }

   private static bmb a(db var0, aqf var1) throws CommandSyntaxException {
      aqa _snowman = _snowman.g();
      if (_snowman instanceof aqm) {
         return ((aqm)_snowman).b(_snowman);
      } else {
         throw b.create(_snowman.d());
      }
   }

   private static int a(CommandContext<db> var0, fx var1, bmb var2, xl.b var3) throws CommandSyntaxException {
      db _snowman = (db)_snowman.getSource();
      aag _snowmanx = _snowman.e();
      ceh _snowmanxx = _snowmanx.d_(_snowman);
      ccj _snowmanxxx = _snowmanx.c(_snowman);
      cyv.a _snowmanxxxx = new cyv.a(_snowmanx).a(dbc.f, dcn.a(_snowman)).a(dbc.g, _snowmanxx).b(dbc.h, _snowmanxxx).b(dbc.a, _snowman.f()).a(dbc.i, _snowman);
      List<bmb> _snowmanxxxxx = _snowmanxx.a(_snowmanxxxx);
      return _snowman.accept(_snowman, _snowmanxxxxx, var2x -> a(_snowman, var2x, _snowman.b().r()));
   }

   private static int a(CommandContext<db> var0, aqa var1, xl.b var2) throws CommandSyntaxException {
      if (!(_snowman instanceof aqm)) {
         throw c.create(_snowman.d());
      } else {
         vk _snowman = ((aqm)_snowman).dp();
         db _snowmanx = (db)_snowman.getSource();
         cyv.a _snowmanxx = new cyv.a(_snowmanx.e());
         aqa _snowmanxxx = _snowmanx.f();
         if (_snowmanxxx instanceof bfw) {
            _snowmanxx.a(dbc.b, (bfw)_snowmanxxx);
         }

         _snowmanxx.a(dbc.c, apk.o);
         _snowmanxx.b(dbc.e, _snowmanxxx);
         _snowmanxx.b(dbc.d, _snowmanxxx);
         _snowmanxx.a(dbc.a, _snowman);
         _snowmanxx.a(dbc.f, _snowmanx.d());
         cyy _snowmanxxxx = _snowmanx.j().aJ().a(_snowman);
         List<bmb> _snowmanxxxxx = _snowmanxxxx.a(_snowmanxx.a(dbb.f));
         return _snowman.accept(_snowman, _snowmanxxxxx, var2x -> a(_snowman, var2x, _snowman));
      }
   }

   private static int a(CommandContext<db> var0, vk var1, xl.b var2) throws CommandSyntaxException {
      db _snowman = (db)_snowman.getSource();
      cyv.a _snowmanx = new cyv.a(_snowman.e()).b(dbc.a, _snowman.f()).a(dbc.f, _snowman.d());
      return a(_snowman, _snowman, _snowmanx.a(dbb.b), _snowman);
   }

   private static int a(CommandContext<db> var0, vk var1, fx var2, bmb var3, xl.b var4) throws CommandSyntaxException {
      db _snowman = (db)_snowman.getSource();
      cyv _snowmanx = new cyv.a(_snowman.e()).a(dbc.f, dcn.a(_snowman)).a(dbc.i, _snowman).b(dbc.a, _snowman.f()).a(dbb.e);
      return a(_snowman, _snowman, _snowmanx, _snowman);
   }

   private static int a(CommandContext<db> var0, vk var1, cyv var2, xl.b var3) throws CommandSyntaxException {
      db _snowman = (db)_snowman.getSource();
      cyy _snowmanx = _snowman.j().aJ().a(_snowman);
      List<bmb> _snowmanxx = _snowmanx.a(_snowman);
      return _snowman.accept(_snowman, _snowmanxx, var1x -> a(_snowman, var1x));
   }

   @FunctionalInterface
   interface a {
      void accept(List<bmb> var1) throws CommandSyntaxException;
   }

   @FunctionalInterface
   interface b {
      int accept(CommandContext<db> var1, List<bmb> var2, xl.a var3) throws CommandSyntaxException;
   }

   @FunctionalInterface
   interface c {
      ArgumentBuilder<db, ?> construct(ArgumentBuilder<db, ?> var1, xl.b var2);
   }
}
