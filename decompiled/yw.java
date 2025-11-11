import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;

public class yw {
   private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new of("commands.whitelist.alreadyOn"));
   private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new of("commands.whitelist.alreadyOff"));
   private static final SimpleCommandExceptionType c = new SimpleCommandExceptionType(new of("commands.whitelist.add.failed"));
   private static final SimpleCommandExceptionType d = new SimpleCommandExceptionType(new of("commands.whitelist.remove.failed"));

   public static void a(CommandDispatcher<db> var0) {
      _snowman.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a(
                                 "whitelist"
                              )
                              .requires(var0x -> var0x.c(3)))
                           .then(dc.a("on").executes(var0x -> b((db)var0x.getSource()))))
                        .then(dc.a("off").executes(var0x -> c((db)var0x.getSource()))))
                     .then(dc.a("list").executes(var0x -> d((db)var0x.getSource()))))
                  .then(dc.a("add").then(dc.a("targets", dm.a()).suggests((var0x, var1) -> {
                     acu _snowman = ((db)var0x.getSource()).j().ae();
                     return dd.b(_snowman.s().stream().filter(var1x -> !_snowman.i().a(var1x.eA())).map(var0xx -> var0xx.eA().getName()), var1);
                  }).executes(var0x -> a((db)var0x.getSource(), dm.a(var0x, "targets"))))))
               .then(
                  dc.a("remove")
                     .then(
                        dc.a("targets", dm.a())
                           .suggests((var0x, var1) -> dd.a(((db)var0x.getSource()).j().ae().j(), var1))
                           .executes(var0x -> b((db)var0x.getSource(), dm.a(var0x, "targets")))
                     )
               ))
            .then(dc.a("reload").executes(var0x -> a((db)var0x.getSource())))
      );
   }

   private static int a(db var0) {
      _snowman.j().ae().a();
      _snowman.a(new of("commands.whitelist.reloaded"), true);
      _snowman.j().a(_snowman);
      return 1;
   }

   private static int a(db var0, Collection<GameProfile> var1) throws CommandSyntaxException {
      adb _snowman = _snowman.j().ae().i();
      int _snowmanx = 0;

      for (GameProfile _snowmanxx : _snowman) {
         if (!_snowman.a(_snowmanxx)) {
            adc _snowmanxxx = new adc(_snowmanxx);
            _snowman.a(_snowmanxxx);
            _snowman.a(new of("commands.whitelist.add.success", ns.a(_snowmanxx)), true);
            _snowmanx++;
         }
      }

      if (_snowmanx == 0) {
         throw c.create();
      } else {
         return _snowmanx;
      }
   }

   private static int b(db var0, Collection<GameProfile> var1) throws CommandSyntaxException {
      adb _snowman = _snowman.j().ae().i();
      int _snowmanx = 0;

      for (GameProfile _snowmanxx : _snowman) {
         if (_snowman.a(_snowmanxx)) {
            adc _snowmanxxx = new adc(_snowmanxx);
            _snowman.b(_snowmanxxx);
            _snowman.a(new of("commands.whitelist.remove.success", ns.a(_snowmanxx)), true);
            _snowmanx++;
         }
      }

      if (_snowmanx == 0) {
         throw d.create();
      } else {
         _snowman.j().a(_snowman);
         return _snowmanx;
      }
   }

   private static int b(db var0) throws CommandSyntaxException {
      acu _snowman = _snowman.j().ae();
      if (_snowman.o()) {
         throw a.create();
      } else {
         _snowman.a(true);
         _snowman.a(new of("commands.whitelist.enabled"), true);
         _snowman.j().a(_snowman);
         return 1;
      }
   }

   private static int c(db var0) throws CommandSyntaxException {
      acu _snowman = _snowman.j().ae();
      if (!_snowman.o()) {
         throw b.create();
      } else {
         _snowman.a(false);
         _snowman.a(new of("commands.whitelist.disabled"), true);
         return 1;
      }
   }

   private static int d(db var0) {
      String[] _snowman = _snowman.j().ae().j();
      if (_snowman.length == 0) {
         _snowman.a(new of("commands.whitelist.none"), false);
      } else {
         _snowman.a(new of("commands.whitelist.list", _snowman.length, String.join(", ", _snowman)), false);
      }

      return _snowman.length;
   }
}
