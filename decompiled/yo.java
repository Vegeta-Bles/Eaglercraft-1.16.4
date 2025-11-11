import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import java.util.Collections;

public class yo {
   private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new of("commands.team.add.duplicate"));
   private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(var0 -> new of("commands.team.add.longName", var0));
   private static final SimpleCommandExceptionType c = new SimpleCommandExceptionType(new of("commands.team.empty.unchanged"));
   private static final SimpleCommandExceptionType d = new SimpleCommandExceptionType(new of("commands.team.option.name.unchanged"));
   private static final SimpleCommandExceptionType e = new SimpleCommandExceptionType(new of("commands.team.option.color.unchanged"));
   private static final SimpleCommandExceptionType f = new SimpleCommandExceptionType(new of("commands.team.option.friendlyfire.alreadyEnabled"));
   private static final SimpleCommandExceptionType g = new SimpleCommandExceptionType(new of("commands.team.option.friendlyfire.alreadyDisabled"));
   private static final SimpleCommandExceptionType h = new SimpleCommandExceptionType(new of("commands.team.option.seeFriendlyInvisibles.alreadyEnabled"));
   private static final SimpleCommandExceptionType i = new SimpleCommandExceptionType(new of("commands.team.option.seeFriendlyInvisibles.alreadyDisabled"));
   private static final SimpleCommandExceptionType j = new SimpleCommandExceptionType(new of("commands.team.option.nametagVisibility.unchanged"));
   private static final SimpleCommandExceptionType k = new SimpleCommandExceptionType(new of("commands.team.option.deathMessageVisibility.unchanged"));
   private static final SimpleCommandExceptionType l = new SimpleCommandExceptionType(new of("commands.team.option.collisionRule.unchanged"));

   public static void a(CommandDispatcher<db> var0) {
      _snowman.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a(
                                    "team"
                                 )
                                 .requires(var0x -> var0x.c(2)))
                              .then(
                                 ((LiteralArgumentBuilder)dc.a("list").executes(var0x -> a((db)var0x.getSource())))
                                    .then(dc.a("team", ec.a()).executes(var0x -> c((db)var0x.getSource(), ec.a(var0x, "team"))))
                              ))
                           .then(
                              dc.a("add")
                                 .then(
                                    ((RequiredArgumentBuilder)dc.a("team", StringArgumentType.word())
                                          .executes(var0x -> a((db)var0x.getSource(), StringArgumentType.getString(var0x, "team"))))
                                       .then(
                                          dc.a("displayName", dg.a())
                                             .executes(
                                                var0x -> a((db)var0x.getSource(), StringArgumentType.getString(var0x, "team"), dg.a(var0x, "displayName"))
                                             )
                                       )
                                 )
                           ))
                        .then(dc.a("remove").then(dc.a("team", ec.a()).executes(var0x -> b((db)var0x.getSource(), ec.a(var0x, "team"))))))
                     .then(dc.a("empty").then(dc.a("team", ec.a()).executes(var0x -> a((db)var0x.getSource(), ec.a(var0x, "team"))))))
                  .then(
                     dc.a("join")
                        .then(
                           ((RequiredArgumentBuilder)dc.a("team", ec.a())
                                 .executes(var0x -> a((db)var0x.getSource(), ec.a(var0x, "team"), Collections.singleton(((db)var0x.getSource()).g().bU()))))
                              .then(
                                 dc.a("members", dz.b())
                                    .suggests(dz.a)
                                    .executes(var0x -> a((db)var0x.getSource(), ec.a(var0x, "team"), dz.c(var0x, "members")))
                              )
                        )
                  ))
               .then(dc.a("leave").then(dc.a("members", dz.b()).suggests(dz.a).executes(var0x -> a((db)var0x.getSource(), dz.c(var0x, "members"))))))
            .then(
               dc.a("modify")
                  .then(
                     ((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)dc.a(
                                                   "team", ec.a()
                                                )
                                                .then(
                                                   dc.a("displayName")
                                                      .then(
                                                         dc.a("displayName", dg.a())
                                                            .executes(var0x -> a((db)var0x.getSource(), ec.a(var0x, "team"), dg.a(var0x, "displayName")))
                                                      )
                                                ))
                                             .then(
                                                dc.a("color")
                                                   .then(
                                                      dc.a("value", df.a())
                                                         .executes(var0x -> a((db)var0x.getSource(), ec.a(var0x, "team"), df.a(var0x, "value")))
                                                   )
                                             ))
                                          .then(
                                             dc.a("friendlyFire")
                                                .then(
                                                   dc.a("allowed", BoolArgumentType.bool())
                                                      .executes(
                                                         var0x -> b((db)var0x.getSource(), ec.a(var0x, "team"), BoolArgumentType.getBool(var0x, "allowed"))
                                                      )
                                                )
                                          ))
                                       .then(
                                          dc.a("seeFriendlyInvisibles")
                                             .then(
                                                dc.a("allowed", BoolArgumentType.bool())
                                                   .executes(var0x -> a((db)var0x.getSource(), ec.a(var0x, "team"), BoolArgumentType.getBool(var0x, "allowed")))
                                             )
                                       ))
                                    .then(
                                       ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("nametagVisibility")
                                                   .then(dc.a("never").executes(var0x -> a((db)var0x.getSource(), ec.a(var0x, "team"), ddp.b.b))))
                                                .then(dc.a("hideForOtherTeams").executes(var0x -> a((db)var0x.getSource(), ec.a(var0x, "team"), ddp.b.c))))
                                             .then(dc.a("hideForOwnTeam").executes(var0x -> a((db)var0x.getSource(), ec.a(var0x, "team"), ddp.b.d))))
                                          .then(dc.a("always").executes(var0x -> a((db)var0x.getSource(), ec.a(var0x, "team"), ddp.b.a)))
                                    ))
                                 .then(
                                    ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("deathMessageVisibility")
                                                .then(dc.a("never").executes(var0x -> b((db)var0x.getSource(), ec.a(var0x, "team"), ddp.b.b))))
                                             .then(dc.a("hideForOtherTeams").executes(var0x -> b((db)var0x.getSource(), ec.a(var0x, "team"), ddp.b.c))))
                                          .then(dc.a("hideForOwnTeam").executes(var0x -> b((db)var0x.getSource(), ec.a(var0x, "team"), ddp.b.d))))
                                       .then(dc.a("always").executes(var0x -> b((db)var0x.getSource(), ec.a(var0x, "team"), ddp.b.a)))
                                 ))
                              .then(
                                 ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("collisionRule")
                                             .then(dc.a("never").executes(var0x -> a((db)var0x.getSource(), ec.a(var0x, "team"), ddp.a.b))))
                                          .then(dc.a("pushOwnTeam").executes(var0x -> a((db)var0x.getSource(), ec.a(var0x, "team"), ddp.a.d))))
                                       .then(dc.a("pushOtherTeams").executes(var0x -> a((db)var0x.getSource(), ec.a(var0x, "team"), ddp.a.c))))
                                    .then(dc.a("always").executes(var0x -> a((db)var0x.getSource(), ec.a(var0x, "team"), ddp.a.a)))
                              ))
                           .then(
                              dc.a("prefix")
                                 .then(dc.a("prefix", dg.a()).executes(var0x -> b((db)var0x.getSource(), ec.a(var0x, "team"), dg.a(var0x, "prefix"))))
                           ))
                        .then(
                           dc.a("suffix").then(dc.a("suffix", dg.a()).executes(var0x -> c((db)var0x.getSource(), ec.a(var0x, "team"), dg.a(var0x, "suffix"))))
                        )
                  )
            )
      );
   }

   private static int a(db var0, Collection<String> var1) {
      ddn _snowman = _snowman.j().aH();

      for (String _snowmanx : _snowman) {
         _snowman.h(_snowmanx);
      }

      if (_snowman.size() == 1) {
         _snowman.a(new of("commands.team.leave.success.single", _snowman.iterator().next()), true);
      } else {
         _snowman.a(new of("commands.team.leave.success.multiple", _snowman.size()), true);
      }

      return _snowman.size();
   }

   private static int a(db var0, ddl var1, Collection<String> var2) {
      ddn _snowman = _snowman.j().aH();

      for (String _snowmanx : _snowman) {
         _snowman.a(_snowmanx, _snowman);
      }

      if (_snowman.size() == 1) {
         _snowman.a(new of("commands.team.join.success.single", _snowman.iterator().next(), _snowman.d()), true);
      } else {
         _snowman.a(new of("commands.team.join.success.multiple", _snowman.size(), _snowman.d()), true);
      }

      return _snowman.size();
   }

   private static int a(db var0, ddl var1, ddp.b var2) throws CommandSyntaxException {
      if (_snowman.j() == _snowman) {
         throw j.create();
      } else {
         _snowman.a(_snowman);
         _snowman.a(new of("commands.team.option.nametagVisibility.success", _snowman.d(), _snowman.b()), true);
         return 0;
      }
   }

   private static int b(db var0, ddl var1, ddp.b var2) throws CommandSyntaxException {
      if (_snowman.k() == _snowman) {
         throw k.create();
      } else {
         _snowman.b(_snowman);
         _snowman.a(new of("commands.team.option.deathMessageVisibility.success", _snowman.d(), _snowman.b()), true);
         return 0;
      }
   }

   private static int a(db var0, ddl var1, ddp.a var2) throws CommandSyntaxException {
      if (_snowman.l() == _snowman) {
         throw l.create();
      } else {
         _snowman.a(_snowman);
         _snowman.a(new of("commands.team.option.collisionRule.success", _snowman.d(), _snowman.b()), true);
         return 0;
      }
   }

   private static int a(db var0, ddl var1, boolean var2) throws CommandSyntaxException {
      if (_snowman.i() == _snowman) {
         if (_snowman) {
            throw h.create();
         } else {
            throw i.create();
         }
      } else {
         _snowman.b(_snowman);
         _snowman.a(new of("commands.team.option.seeFriendlyInvisibles." + (_snowman ? "enabled" : "disabled"), _snowman.d()), true);
         return 0;
      }
   }

   private static int b(db var0, ddl var1, boolean var2) throws CommandSyntaxException {
      if (_snowman.h() == _snowman) {
         if (_snowman) {
            throw f.create();
         } else {
            throw g.create();
         }
      } else {
         _snowman.a(_snowman);
         _snowman.a(new of("commands.team.option.friendlyfire." + (_snowman ? "enabled" : "disabled"), _snowman.d()), true);
         return 0;
      }
   }

   private static int a(db var0, ddl var1, nr var2) throws CommandSyntaxException {
      if (_snowman.c().equals(_snowman)) {
         throw d.create();
      } else {
         _snowman.a(_snowman);
         _snowman.a(new of("commands.team.option.name.success", _snowman.d()), true);
         return 0;
      }
   }

   private static int a(db var0, ddl var1, k var2) throws CommandSyntaxException {
      if (_snowman.n() == _snowman) {
         throw e.create();
      } else {
         _snowman.a(_snowman);
         _snowman.a(new of("commands.team.option.color.success", _snowman.d(), _snowman.f()), true);
         return 0;
      }
   }

   private static int a(db var0, ddl var1) throws CommandSyntaxException {
      ddn _snowman = _snowman.j().aH();
      Collection<String> _snowmanx = Lists.newArrayList(_snowman.g());
      if (_snowmanx.isEmpty()) {
         throw c.create();
      } else {
         for (String _snowmanxx : _snowmanx) {
            _snowman.b(_snowmanxx, _snowman);
         }

         _snowman.a(new of("commands.team.empty.success", _snowmanx.size(), _snowman.d()), true);
         return _snowmanx.size();
      }
   }

   private static int b(db var0, ddl var1) {
      ddn _snowman = _snowman.j().aH();
      _snowman.d(_snowman);
      _snowman.a(new of("commands.team.remove.success", _snowman.d()), true);
      return _snowman.g().size();
   }

   private static int a(db var0, String var1) throws CommandSyntaxException {
      return a(_snowman, _snowman, new oe(_snowman));
   }

   private static int a(db var0, String var1, nr var2) throws CommandSyntaxException {
      ddn _snowman = _snowman.j().aH();
      if (_snowman.f(_snowman) != null) {
         throw a.create();
      } else if (_snowman.length() > 16) {
         throw b.create(16);
      } else {
         ddl _snowmanx = _snowman.g(_snowman);
         _snowmanx.a(_snowman);
         _snowman.a(new of("commands.team.add.success", _snowmanx.d()), true);
         return _snowman.g().size();
      }
   }

   private static int c(db var0, ddl var1) {
      Collection<String> _snowman = _snowman.g();
      if (_snowman.isEmpty()) {
         _snowman.a(new of("commands.team.list.members.empty", _snowman.d()), false);
      } else {
         _snowman.a(new of("commands.team.list.members.success", _snowman.d(), _snowman.size(), ns.a(_snowman)), false);
      }

      return _snowman.size();
   }

   private static int a(db var0) {
      Collection<ddl> _snowman = _snowman.j().aH().g();
      if (_snowman.isEmpty()) {
         _snowman.a(new of("commands.team.list.teams.empty"), false);
      } else {
         _snowman.a(new of("commands.team.list.teams.success", _snowman.size(), ns.b(_snowman, ddl::d)), false);
      }

      return _snowman.size();
   }

   private static int b(db var0, ddl var1, nr var2) {
      _snowman.b(_snowman);
      _snowman.a(new of("commands.team.option.prefix.success", _snowman), false);
      return 1;
   }

   private static int c(db var0, ddl var1, nr var2) {
      _snowman.c(_snowman);
      _snowman.a(new of("commands.team.option.suffix.success", _snowman), false);
      return 1;
   }
}
