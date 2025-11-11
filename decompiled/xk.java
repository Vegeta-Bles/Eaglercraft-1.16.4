import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Map.Entry;

public class xk {
   private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new of("commands.locate.failed"));

   public static void a(CommandDispatcher<db> var0) {
      LiteralArgumentBuilder<db> _snowman = (LiteralArgumentBuilder<db>)dc.a("locate").requires(var0x -> var0x.c(2));

      for (Entry<String, cla<?>> _snowmanx : cla.a.entrySet()) {
         _snowman = (LiteralArgumentBuilder<db>)_snowman.then(dc.a(_snowmanx.getKey()).executes(var1x -> a((db)var1x.getSource(), _snowman.getValue())));
      }

      _snowman.register(_snowman);
   }

   private static int a(db var0, cla<?> var1) throws CommandSyntaxException {
      fx _snowman = new fx(_snowman.d());
      fx _snowmanx = _snowman.e().a(_snowman, _snowman, 100, false);
      if (_snowmanx == null) {
         throw a.create();
      } else {
         return a(_snowman, _snowman.i(), _snowman, _snowmanx, "commands.locate.success");
      }
   }

   public static int a(db var0, String var1, fx var2, fx var3, String var4) {
      int _snowman = afm.d(a(_snowman.u(), _snowman.w(), _snowman.u(), _snowman.w()));
      nr _snowmanx = ns.a((nr)(new of("chat.coordinates", _snowman.u(), "~", _snowman.w())))
         .a(var1x -> var1x.a(k.k).a(new np(np.a.d, "/tp @s " + _snowman.u() + " ~ " + _snowman.w())).a(new nv(nv.a.a, new of("chat.coordinates.tooltip"))));
      _snowman.a(new of(_snowman, _snowman, _snowmanx, _snowman), false);
      return _snowman;
   }

   private static float a(int var0, int var1, int var2, int var3) {
      int _snowman = _snowman - _snowman;
      int _snowmanx = _snowman - _snowman;
      return afm.c((float)(_snowman * _snowman + _snowmanx * _snowmanx));
   }
}
