import com.google.common.collect.ImmutableMap;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.spi.FileSystemProvider;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class wp {
   private static final Logger a = LogManager.getLogger();
   private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new of("commands.debug.notRunning"));
   private static final SimpleCommandExceptionType c = new SimpleCommandExceptionType(new of("commands.debug.alreadyRunning"));
   @Nullable
   private static final FileSystemProvider d = FileSystemProvider.installedProviders()
      .stream()
      .filter(var0 -> var0.getScheme().equalsIgnoreCase("jar"))
      .findFirst()
      .orElse(null);

   public static void a(CommandDispatcher<db> var0) {
      _snowman.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("debug").requires(var0x -> var0x.c(3)))
                  .then(dc.a("start").executes(var0x -> a((db)var0x.getSource()))))
               .then(dc.a("stop").executes(var0x -> b((db)var0x.getSource()))))
            .then(dc.a("report").executes(var0x -> c((db)var0x.getSource())))
      );
   }

   private static int a(db var0) throws CommandSyntaxException {
      MinecraftServer _snowman = _snowman.j();
      if (_snowman.aS()) {
         throw c.create();
      } else {
         _snowman.aT();
         _snowman.a(new of("commands.debug.started", "Started the debug profiler. Type '/debug stop' to stop it."), true);
         return 0;
      }
   }

   private static int b(db var0) throws CommandSyntaxException {
      MinecraftServer _snowman = _snowman.j();
      if (!_snowman.aS()) {
         throw b.create();
      } else {
         anv _snowmanx = _snowman.aU();
         File _snowmanxx = new File(_snowman.c("debug"), "profile-results-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + ".txt");
         _snowmanx.a(_snowmanxx);
         float _snowmanxxx = (float)_snowmanx.g() / 1.0E9F;
         float _snowmanxxxx = (float)_snowmanx.f() / _snowmanxxx;
         _snowman.a(new of("commands.debug.stopped", String.format(Locale.ROOT, "%.2f", _snowmanxxx), _snowmanx.f(), String.format("%.2f", _snowmanxxxx)), true);
         return afm.d(_snowmanxxxx);
      }
   }

   private static int c(db var0) {
      MinecraftServer _snowman = _snowman.j();
      String _snowmanx = "debug-report-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date());

      try {
         Path _snowmanxx = _snowman.c("debug").toPath();
         Files.createDirectories(_snowmanxx);
         if (!w.d && d != null) {
            Path _snowmanxxx = _snowmanxx.resolve(_snowmanx + ".zip");

            try (FileSystem _snowmanxxxx = d.newFileSystem(_snowmanxxx, ImmutableMap.of("create", "true"))) {
               _snowman.a(_snowmanxxxx.getPath("/"));
            }
         } else {
            Path _snowmanxxx = _snowmanxx.resolve(_snowmanx);
            _snowman.a(_snowmanxxx);
         }

         _snowman.a(new of("commands.debug.reportSaved", _snowmanx), false);
         return 1;
      } catch (IOException var18) {
         a.error("Failed to save debug dump", var18);
         _snowman.a(new of("commands.debug.reportFailed"));
         return 0;
      }
   }
}
