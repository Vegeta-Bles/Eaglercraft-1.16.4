import com.google.common.base.Joiner;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import it.unimi.dsi.fastutil.longs.LongSet;

public class xa {
   private static final Dynamic2CommandExceptionType a = new Dynamic2CommandExceptionType((var0, var1) -> new of("commands.forceload.toobig", var0, var1));
   private static final Dynamic2CommandExceptionType b = new Dynamic2CommandExceptionType(
      (var0, var1) -> new of("commands.forceload.query.failure", var0, var1)
   );
   private static final SimpleCommandExceptionType c = new SimpleCommandExceptionType(new of("commands.forceload.added.failure"));
   private static final SimpleCommandExceptionType d = new SimpleCommandExceptionType(new of("commands.forceload.removed.failure"));

   public static void a(CommandDispatcher<db> var0) {
      _snowman.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("forceload").requires(var0x -> var0x.c(2)))
                  .then(
                     dc.a("add")
                        .then(
                           ((RequiredArgumentBuilder)dc.a("from", el.a())
                                 .executes(var0x -> a((db)var0x.getSource(), el.a(var0x, "from"), el.a(var0x, "from"), true)))
                              .then(dc.a("to", el.a()).executes(var0x -> a((db)var0x.getSource(), el.a(var0x, "from"), el.a(var0x, "to"), true)))
                        )
                  ))
               .then(
                  ((LiteralArgumentBuilder)dc.a("remove")
                        .then(
                           ((RequiredArgumentBuilder)dc.a("from", el.a())
                                 .executes(var0x -> a((db)var0x.getSource(), el.a(var0x, "from"), el.a(var0x, "from"), false)))
                              .then(dc.a("to", el.a()).executes(var0x -> a((db)var0x.getSource(), el.a(var0x, "from"), el.a(var0x, "to"), false)))
                        ))
                     .then(dc.a("all").executes(var0x -> b((db)var0x.getSource())))
               ))
            .then(
               ((LiteralArgumentBuilder)dc.a("query").executes(var0x -> a((db)var0x.getSource())))
                  .then(dc.a("pos", el.a()).executes(var0x -> a((db)var0x.getSource(), el.a(var0x, "pos"))))
            )
      );
   }

   private static int a(db var0, zw var1) throws CommandSyntaxException {
      brd _snowman = new brd(_snowman.a >> 4, _snowman.b >> 4);
      aag _snowmanx = _snowman.e();
      vj<brx> _snowmanxx = _snowmanx.Y();
      boolean _snowmanxxx = _snowmanx.w().contains(_snowman.a());
      if (_snowmanxxx) {
         _snowman.a(new of("commands.forceload.query.success", _snowman, _snowmanxx.a()), false);
         return 1;
      } else {
         throw b.create(_snowman, _snowmanxx.a());
      }
   }

   private static int a(db var0) {
      aag _snowman = _snowman.e();
      vj<brx> _snowmanx = _snowman.Y();
      LongSet _snowmanxx = _snowman.w();
      int _snowmanxxx = _snowmanxx.size();
      if (_snowmanxxx > 0) {
         String _snowmanxxxx = Joiner.on(", ").join(_snowmanxx.stream().sorted().map(brd::new).map(brd::toString).iterator());
         if (_snowmanxxx == 1) {
            _snowman.a(new of("commands.forceload.list.single", _snowmanx.a(), _snowmanxxxx), false);
         } else {
            _snowman.a(new of("commands.forceload.list.multiple", _snowmanxxx, _snowmanx.a(), _snowmanxxxx), false);
         }
      } else {
         _snowman.a(new of("commands.forceload.added.none", _snowmanx.a()));
      }

      return _snowmanxxx;
   }

   private static int b(db var0) {
      aag _snowman = _snowman.e();
      vj<brx> _snowmanx = _snowman.Y();
      LongSet _snowmanxx = _snowman.w();
      _snowmanxx.forEach(var1x -> _snowman.a(brd.a(var1x), brd.b(var1x), false));
      _snowman.a(new of("commands.forceload.removed.all", _snowmanx.a()), true);
      return 0;
   }

   private static int a(db var0, zw var1, zw var2, boolean var3) throws CommandSyntaxException {
      int _snowman = Math.min(_snowman.a, _snowman.a);
      int _snowmanx = Math.min(_snowman.b, _snowman.b);
      int _snowmanxx = Math.max(_snowman.a, _snowman.a);
      int _snowmanxxx = Math.max(_snowman.b, _snowman.b);
      if (_snowman >= -30000000 && _snowmanx >= -30000000 && _snowmanxx < 30000000 && _snowmanxxx < 30000000) {
         int _snowmanxxxx = _snowman >> 4;
         int _snowmanxxxxx = _snowmanx >> 4;
         int _snowmanxxxxxx = _snowmanxx >> 4;
         int _snowmanxxxxxxx = _snowmanxxx >> 4;
         long _snowmanxxxxxxxx = ((long)(_snowmanxxxxxx - _snowmanxxxx) + 1L) * ((long)(_snowmanxxxxxxx - _snowmanxxxxx) + 1L);
         if (_snowmanxxxxxxxx > 256L) {
            throw a.create(256, _snowmanxxxxxxxx);
         } else {
            aag _snowmanxxxxxxxxx = _snowman.e();
            vj<brx> _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx.Y();
            brd _snowmanxxxxxxxxxxx = null;
            int _snowmanxxxxxxxxxxxx = 0;

            for (int _snowmanxxxxxxxxxxxxx = _snowmanxxxx; _snowmanxxxxxxxxxxxxx <= _snowmanxxxxxx; _snowmanxxxxxxxxxxxxx++) {
               for (int _snowmanxxxxxxxxxxxxxx = _snowmanxxxxx; _snowmanxxxxxxxxxxxxxx <= _snowmanxxxxxxx; _snowmanxxxxxxxxxxxxxx++) {
                  boolean _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxx.a(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowman);
                  if (_snowmanxxxxxxxxxxxxxxx) {
                     _snowmanxxxxxxxxxxxx++;
                     if (_snowmanxxxxxxxxxxx == null) {
                        _snowmanxxxxxxxxxxx = new brd(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx);
                     }
                  }
               }
            }

            if (_snowmanxxxxxxxxxxxx == 0) {
               throw (_snowman ? c : d).create();
            } else {
               if (_snowmanxxxxxxxxxxxx == 1) {
                  _snowman.a(new of("commands.forceload." + (_snowman ? "added" : "removed") + ".single", _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxx.a()), true);
               } else {
                  brd _snowmanxxxxxxxxxxxxx = new brd(_snowmanxxxx, _snowmanxxxxx);
                  brd _snowmanxxxxxxxxxxxxxxx = new brd(_snowmanxxxxxx, _snowmanxxxxxxx);
                  _snowman.a(
                     new of("commands.forceload." + (_snowman ? "added" : "removed") + ".multiple", _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxx.a(), _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx),
                     true
                  );
               }

               return _snowmanxxxxxxxxxxxx;
            }
         }
      } else {
         throw ek.b.create();
      }
   }
}
