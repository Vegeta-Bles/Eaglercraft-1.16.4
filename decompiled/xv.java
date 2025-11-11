import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import java.util.Collection;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class xv {
   private static final Logger a = LogManager.getLogger();

   public static void a(Collection<String> var0, db var1) {
      _snowman.j().a(_snowman).exceptionally(var1x -> {
         a.warn("Failed to execute reload", var1x);
         _snowman.a(new of("commands.reload.failure"));
         return null;
      });
   }

   private static Collection<String> a(abw var0, cyn var1, Collection<String> var2) {
      _snowman.a();
      Collection<String> _snowman = Lists.newArrayList(_snowman);
      Collection<String> _snowmanx = _snowman.D().b();

      for (String _snowmanxx : _snowman.b()) {
         if (!_snowmanx.contains(_snowmanxx) && !_snowman.contains(_snowmanxx)) {
            _snowman.add(_snowmanxx);
         }
      }

      return _snowman;
   }

   public static void a(CommandDispatcher<db> var0) {
      _snowman.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("reload").requires(var0x -> var0x.c(2))).executes(var0x -> {
         db _snowman = (db)var0x.getSource();
         MinecraftServer _snowmanx = _snowman.j();
         abw _snowmanxx = _snowmanx.aC();
         cyn _snowmanxxx = _snowmanx.aX();
         Collection<String> _snowmanxxxx = _snowmanxx.d();
         Collection<String> _snowmanxxxxx = a(_snowmanxx, _snowmanxxx, _snowmanxxxx);
         _snowman.a(new of("commands.reload.success"), true);
         a(_snowmanxxxxx, _snowman);
         return 0;
      }));
   }
}
