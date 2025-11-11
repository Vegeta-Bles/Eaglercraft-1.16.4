import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import javax.annotation.Nullable;

public class yq {
   private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new of("commands.teleport.invalidPosition"));

   public static void a(CommandDispatcher<db> var0) {
      LiteralCommandNode<db> _snowman = _snowman.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("teleport").requires(var0x -> var0x.c(2)))
                  .then(
                     ((RequiredArgumentBuilder)dc.a("targets", dk.b())
                           .then(
                              ((RequiredArgumentBuilder)((RequiredArgumentBuilder)dc.a("location", er.a())
                                       .executes(
                                          var0x -> a(
                                                (db)var0x.getSource(), dk.b(var0x, "targets"), ((db)var0x.getSource()).e(), er.b(var0x, "location"), null, null
                                             )
                                       ))
                                    .then(
                                       dc.a("rotation", eo.a())
                                          .executes(
                                             var0x -> a(
                                                   (db)var0x.getSource(),
                                                   dk.b(var0x, "targets"),
                                                   ((db)var0x.getSource()).e(),
                                                   er.b(var0x, "location"),
                                                   eo.a(var0x, "rotation"),
                                                   null
                                                )
                                          )
                                    ))
                                 .then(
                                    ((LiteralArgumentBuilder)dc.a("facing")
                                          .then(
                                             dc.a("entity")
                                                .then(
                                                   ((RequiredArgumentBuilder)dc.a("facingEntity", dk.a())
                                                         .executes(
                                                            var0x -> a(
                                                                  (db)var0x.getSource(),
                                                                  dk.b(var0x, "targets"),
                                                                  ((db)var0x.getSource()).e(),
                                                                  er.b(var0x, "location"),
                                                                  null,
                                                                  new yq.a(dk.a(var0x, "facingEntity"), dj.a.a)
                                                               )
                                                         ))
                                                      .then(
                                                         dc.a("facingAnchor", dj.a())
                                                            .executes(
                                                               var0x -> a(
                                                                     (db)var0x.getSource(),
                                                                     dk.b(var0x, "targets"),
                                                                     ((db)var0x.getSource()).e(),
                                                                     er.b(var0x, "location"),
                                                                     null,
                                                                     new yq.a(dk.a(var0x, "facingEntity"), dj.a(var0x, "facingAnchor"))
                                                                  )
                                                            )
                                                      )
                                                )
                                          ))
                                       .then(
                                          dc.a("facingLocation", er.a())
                                             .executes(
                                                var0x -> a(
                                                      (db)var0x.getSource(),
                                                      dk.b(var0x, "targets"),
                                                      ((db)var0x.getSource()).e(),
                                                      er.b(var0x, "location"),
                                                      null,
                                                      new yq.a(er.a(var0x, "facingLocation"))
                                                   )
                                             )
                                       )
                                 )
                           ))
                        .then(dc.a("destination", dk.a()).executes(var0x -> a((db)var0x.getSource(), dk.b(var0x, "targets"), dk.a(var0x, "destination"))))
                  ))
               .then(
                  dc.a("location", er.a())
                     .executes(
                        var0x -> a(
                              (db)var0x.getSource(),
                              Collections.singleton(((db)var0x.getSource()).g()),
                              ((db)var0x.getSource()).e(),
                              er.b(var0x, "location"),
                              et.d(),
                              null
                           )
                     )
               ))
            .then(
               dc.a("destination", dk.a())
                  .executes(var0x -> a((db)var0x.getSource(), Collections.singleton(((db)var0x.getSource()).g()), dk.a(var0x, "destination")))
            )
      );
      _snowman.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("tp").requires(var0x -> var0x.c(2))).redirect(_snowman));
   }

   private static int a(db var0, Collection<? extends aqa> var1, aqa var2) throws CommandSyntaxException {
      for (aqa _snowman : _snowman) {
         a(_snowman, _snowman, (aag)_snowman.l, _snowman.cD(), _snowman.cE(), _snowman.cH(), EnumSet.noneOf(qk.a.class), _snowman.p, _snowman.q, null);
      }

      if (_snowman.size() == 1) {
         _snowman.a(new of("commands.teleport.success.entity.single", _snowman.iterator().next().d(), _snowman.d()), true);
      } else {
         _snowman.a(new of("commands.teleport.success.entity.multiple", _snowman.size(), _snowman.d()), true);
      }

      return _snowman.size();
   }

   private static int a(db var0, Collection<? extends aqa> var1, aag var2, em var3, @Nullable em var4, @Nullable yq.a var5) throws CommandSyntaxException {
      dcn _snowman = _snowman.a(_snowman);
      dcm _snowmanx = _snowman == null ? null : _snowman.b(_snowman);
      Set<qk.a> _snowmanxx = EnumSet.noneOf(qk.a.class);
      if (_snowman.a()) {
         _snowmanxx.add(qk.a.a);
      }

      if (_snowman.b()) {
         _snowmanxx.add(qk.a.b);
      }

      if (_snowman.c()) {
         _snowmanxx.add(qk.a.c);
      }

      if (_snowman == null) {
         _snowmanxx.add(qk.a.e);
         _snowmanxx.add(qk.a.d);
      } else {
         if (_snowman.a()) {
            _snowmanxx.add(qk.a.e);
         }

         if (_snowman.b()) {
            _snowmanxx.add(qk.a.d);
         }
      }

      for (aqa _snowmanxxx : _snowman) {
         if (_snowman == null) {
            a(_snowman, _snowmanxxx, _snowman, _snowman.b, _snowman.c, _snowman.d, _snowmanxx, _snowmanxxx.p, _snowmanxxx.q, _snowman);
         } else {
            a(_snowman, _snowmanxxx, _snowman, _snowman.b, _snowman.c, _snowman.d, _snowmanxx, _snowmanx.j, _snowmanx.i, _snowman);
         }
      }

      if (_snowman.size() == 1) {
         _snowman.a(new of("commands.teleport.success.location.single", _snowman.iterator().next().d(), _snowman.b, _snowman.c, _snowman.d), true);
      } else {
         _snowman.a(new of("commands.teleport.success.location.multiple", _snowman.size(), _snowman.b, _snowman.c, _snowman.d), true);
      }

      return _snowman.size();
   }

   private static void a(db var0, aqa var1, aag var2, double var3, double var5, double var7, Set<qk.a> var9, float var10, float var11, @Nullable yq.a var12) throws CommandSyntaxException {
      fx _snowman = new fx(_snowman, _snowman, _snowman);
      if (!brx.l(_snowman)) {
         throw a.create();
      } else {
         if (_snowman instanceof aah) {
            brd _snowmanx = new brd(new fx(_snowman, _snowman, _snowman));
            _snowman.i().a(aal.g, _snowmanx, 1, _snowman.Y());
            _snowman.l();
            if (((aah)_snowman).em()) {
               ((aah)_snowman).a(true, true);
            }

            if (_snowman == _snowman.l) {
               ((aah)_snowman).b.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
            } else {
               ((aah)_snowman).a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
            }

            _snowman.m(_snowman);
         } else {
            float _snowmanxx = afm.g(_snowman);
            float _snowmanxxx = afm.g(_snowman);
            _snowmanxxx = afm.a(_snowmanxxx, -90.0F, 90.0F);
            if (_snowman == _snowman.l) {
               _snowman.b(_snowman, _snowman, _snowman, _snowmanxx, _snowmanxxx);
               _snowman.m(_snowmanxx);
            } else {
               _snowman.V();
               aqa _snowmanxxxx = _snowman;
               _snowman = _snowman.X().a(_snowman);
               if (_snowman == null) {
                  return;
               }

               _snowman.v(_snowmanxxxx);
               _snowman.b(_snowman, _snowman, _snowman, _snowmanxx, _snowmanxxx);
               _snowman.m(_snowmanxx);
               _snowman.e(_snowman);
               _snowmanxxxx.y = true;
            }
         }

         if (_snowman != null) {
            _snowman.a(_snowman, _snowman);
         }

         if (!(_snowman instanceof aqm) || !((aqm)_snowman).ef()) {
            _snowman.f(_snowman.cC().d(1.0, 0.0, 1.0));
            _snowman.c(true);
         }

         if (_snowman instanceof aqu) {
            ((aqu)_snowman).x().o();
         }
      }
   }

   static class a {
      private final dcn a;
      private final aqa b;
      private final dj.a c;

      public a(aqa var1, dj.a var2) {
         this.b = _snowman;
         this.c = _snowman;
         this.a = _snowman.a(_snowman);
      }

      public a(dcn var1) {
         this.b = null;
         this.a = _snowman;
         this.c = null;
      }

      public void a(db var1, aqa var2) {
         if (this.b != null) {
            if (_snowman instanceof aah) {
               ((aah)_snowman).a(_snowman.k(), this.b, this.c);
            } else {
               _snowman.a(_snowman.k(), this.a);
            }
         } else {
            _snowman.a(_snowman.k(), this.a);
         }
      }
   }
}
