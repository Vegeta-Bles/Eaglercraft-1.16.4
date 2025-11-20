package net.minecraft.server.command;

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
import net.minecraft.SharedConstants;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.profiler.ProfileResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DebugCommand {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final SimpleCommandExceptionType NOT_RUNNING_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.debug.notRunning"));
   private static final SimpleCommandExceptionType ALREADY_RUNNING_EXCEPTION = new SimpleCommandExceptionType(
      new TranslatableText("commands.debug.alreadyRunning")
   );
   @Nullable
   private static final FileSystemProvider FILE_SYSTEM_PROVIDER = FileSystemProvider.installedProviders()
      .stream()
      .filter(_snowman -> _snowman.getScheme().equalsIgnoreCase("jar"))
      .findFirst()
      .orElse(null);

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("debug")
                     .requires(_snowman -> _snowman.hasPermissionLevel(3)))
                  .then(CommandManager.literal("start").executes(_snowman -> executeStart((ServerCommandSource)_snowman.getSource()))))
               .then(CommandManager.literal("stop").executes(_snowman -> executeStop((ServerCommandSource)_snowman.getSource()))))
            .then(CommandManager.literal("report").executes(_snowman -> createDebugReport((ServerCommandSource)_snowman.getSource())))
      );
   }

   private static int executeStart(ServerCommandSource source) throws CommandSyntaxException {
      MinecraftServer _snowman = source.getMinecraftServer();
      if (_snowman.isDebugRunning()) {
         throw ALREADY_RUNNING_EXCEPTION.create();
      } else {
         _snowman.enableProfiler();
         source.sendFeedback(new TranslatableText("commands.debug.started", "Started the debug profiler. Type '/debug stop' to stop it."), true);
         return 0;
      }
   }

   private static int executeStop(ServerCommandSource source) throws CommandSyntaxException {
      MinecraftServer _snowman = source.getMinecraftServer();
      if (!_snowman.isDebugRunning()) {
         throw NOT_RUNNING_EXCEPTION.create();
      } else {
         ProfileResult _snowmanx = _snowman.stopDebug();
         File _snowmanxx = new File(_snowman.getFile("debug"), "profile-results-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + ".txt");
         _snowmanx.save(_snowmanxx);
         float _snowmanxxx = (float)_snowmanx.getTimeSpan() / 1.0E9F;
         float _snowmanxxxx = (float)_snowmanx.getTickSpan() / _snowmanxxx;
         source.sendFeedback(
            new TranslatableText("commands.debug.stopped", String.format(Locale.ROOT, "%.2f", _snowmanxxx), _snowmanx.getTickSpan(), String.format("%.2f", _snowmanxxxx)), true
         );
         return MathHelper.floor(_snowmanxxxx);
      }
   }

   private static int createDebugReport(ServerCommandSource source) {
      MinecraftServer _snowman = source.getMinecraftServer();
      String _snowmanx = "debug-report-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date());

      try {
         Path _snowmanxx = _snowman.getFile("debug").toPath();
         Files.createDirectories(_snowmanxx);
         if (!SharedConstants.isDevelopment && FILE_SYSTEM_PROVIDER != null) {
            Path _snowmanxxx = _snowmanxx.resolve(_snowmanx + ".zip");

            try (FileSystem _snowmanxxxx = FILE_SYSTEM_PROVIDER.newFileSystem(_snowmanxxx, ImmutableMap.of("create", "true"))) {
               _snowman.dump(_snowmanxxxx.getPath("/"));
            }
         } else {
            Path _snowmanxxx = _snowmanxx.resolve(_snowmanx);
            _snowman.dump(_snowmanxxx);
         }

         source.sendFeedback(new TranslatableText("commands.debug.reportSaved", _snowmanx), false);
         return 1;
      } catch (IOException var18) {
         LOGGER.error("Failed to save debug dump", var18);
         source.sendError(new TranslatableText("commands.debug.reportFailed"));
         return 0;
      }
   }
}
