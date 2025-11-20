package net.minecraft.server;

import com.google.common.collect.ImmutableSet;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Lifecycle;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.net.Proxy;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.BooleanSupplier;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import net.minecraft.Bootstrap;
import net.minecraft.datafixer.Schemas;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resource.DataPackSettings;
import net.minecraft.resource.FileResourcePackProvider;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.resource.ResourcePackSource;
import net.minecraft.resource.ServerResourceManager;
import net.minecraft.resource.VanillaDataPackProvider;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.dedicated.EulaReader;
import net.minecraft.server.dedicated.MinecraftDedicatedServer;
import net.minecraft.server.dedicated.ServerPropertiesHandler;
import net.minecraft.server.dedicated.ServerPropertiesLoader;
import net.minecraft.text.Text;
import net.minecraft.util.UserCache;
import net.minecraft.util.Util;
import net.minecraft.util.WorldSavePath;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.dynamic.RegistryOps;
import net.minecraft.util.logging.UncaughtExceptionLogger;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.GameRules;
import net.minecraft.world.SaveProperties;
import net.minecraft.world.World;
import net.minecraft.world.gen.GeneratorOptions;
import net.minecraft.world.level.LevelInfo;
import net.minecraft.world.level.LevelProperties;
import net.minecraft.world.level.storage.LevelStorage;
import net.minecraft.world.updater.WorldUpdater;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
   private static final Logger LOGGER = LogManager.getLogger();

   public Main() {
   }

   public static void main(String[] args) {
      OptionParser _snowman = new OptionParser();
      OptionSpec<Void> _snowmanx = _snowman.accepts("nogui");
      OptionSpec<Void> _snowmanxx = _snowman.accepts("initSettings", "Initializes 'server.properties' and 'eula.txt', then quits");
      OptionSpec<Void> _snowmanxxx = _snowman.accepts("demo");
      OptionSpec<Void> _snowmanxxxx = _snowman.accepts("bonusChest");
      OptionSpec<Void> _snowmanxxxxx = _snowman.accepts("forceUpgrade");
      OptionSpec<Void> _snowmanxxxxxx = _snowman.accepts("eraseCache");
      OptionSpec<Void> _snowmanxxxxxxx = _snowman.accepts("safeMode", "Loads level with vanilla datapack only");
      OptionSpec<Void> _snowmanxxxxxxxx = _snowman.accepts("help").forHelp();
      OptionSpec<String> _snowmanxxxxxxxxx = _snowman.accepts("singleplayer").withRequiredArg();
      OptionSpec<String> _snowmanxxxxxxxxxx = _snowman.accepts("universe").withRequiredArg().defaultsTo(".", new String[0]);
      OptionSpec<String> _snowmanxxxxxxxxxxx = _snowman.accepts("world").withRequiredArg();
      OptionSpec<Integer> _snowmanxxxxxxxxxxxx = _snowman.accepts("port").withRequiredArg().ofType(Integer.class).defaultsTo(-1, new Integer[0]);
      OptionSpec<String> _snowmanxxxxxxxxxxxxx = _snowman.accepts("serverId").withRequiredArg();
      OptionSpec<String> _snowmanxxxxxxxxxxxxxx = _snowman.nonOptions();

      try {
         OptionSet _snowmanxxxxxxxxxxxxxxx = _snowman.parse(args);
         if (_snowmanxxxxxxxxxxxxxxx.has(_snowmanxxxxxxxx)) {
            _snowman.printHelpOn(System.err);
            return;
         }

         CrashReport.initCrashReport();
         Bootstrap.initialize();
         Bootstrap.logMissing();
         Util.startTimerHack();
         DynamicRegistryManager.Impl _snowmanxxxxxxxxxxxxxxxx = DynamicRegistryManager.create();
         Path _snowmanxxxxxxxxxxxxxxxxx = Paths.get("server.properties");
         ServerPropertiesLoader _snowmanxxxxxxxxxxxxxxxxxx = new ServerPropertiesLoader(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx);
         _snowmanxxxxxxxxxxxxxxxxxx.store();
         Path _snowmanxxxxxxxxxxxxxxxxxxx = Paths.get("eula.txt");
         EulaReader _snowmanxxxxxxxxxxxxxxxxxxxx = new EulaReader(_snowmanxxxxxxxxxxxxxxxxxxx);
         if (_snowmanxxxxxxxxxxxxxxx.has(_snowmanxx)) {
            LOGGER.info("Initialized '{}' and '{}'", _snowmanxxxxxxxxxxxxxxxxx.toAbsolutePath(), _snowmanxxxxxxxxxxxxxxxxxxx.toAbsolutePath());
            return;
         }

         if (!_snowmanxxxxxxxxxxxxxxxxxxxx.isEulaAgreedTo()) {
            LOGGER.info("You need to agree to the EULA in order to run the server. Go to eula.txt for more info.");
            return;
         }

         File _snowmanxxxxxxxxxxxxxxxxxxxxx = new File((String)_snowmanxxxxxxxxxxxxxxx.valueOf(_snowmanxxxxxxxxxx));
         YggdrasilAuthenticationService _snowmanxxxxxxxxxxxxxxxxxxxxxx = new YggdrasilAuthenticationService(Proxy.NO_PROXY);
         MinecraftSessionService _snowmanxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxx.createMinecraftSessionService();
         GameProfileRepository _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxx.createProfileRepository();
         UserCache _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = new UserCache(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxx, new File(_snowmanxxxxxxxxxxxxxxxxxxxxx, MinecraftServer.USER_CACHE_FILE.getName())
         );
         String _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = (String)Optional.ofNullable(_snowmanxxxxxxxxxxxxxxx.valueOf(_snowmanxxxxxxxxxxx))
            .orElse(_snowmanxxxxxxxxxxxxxxxxxx.getPropertiesHandler().levelName);
         LevelStorage _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = LevelStorage.create(_snowmanxxxxxxxxxxxxxxxxxxxxx.toPath());
         LevelStorage.Session _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx.createSession(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx);
         MinecraftServer.convertLevel(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx);
         DataPackSettings _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getDataPackSettings();
         boolean _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx.has(_snowmanxxxxxxx);
         if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
            LOGGER.warn("Safe mode active, only vanilla datapack will be loaded");
         }

         ResourcePackManager _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = new ResourcePackManager(
            new VanillaDataPackProvider(),
            new FileResourcePackProvider(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getDirectory(WorldSavePath.DATAPACKS).toFile(), ResourcePackSource.PACK_SOURCE_WORLD)
         );
         DataPackSettings _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = MinecraftServer.loadDataPacks(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == null ? DataPackSettings.SAFE_MODE : _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
         );
         CompletableFuture<ServerResourceManager> _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ServerResourceManager.reload(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.createResourcePacks(),
            CommandManager.RegistrationEnvironment.DEDICATED,
            _snowmanxxxxxxxxxxxxxxxxxx.getPropertiesHandler().functionPermissionLevel,
            Util.getMainWorkerExecutor(),
            Runnable::run
         );

         ServerResourceManager _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
         try {
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.get();
         } catch (Exception var41) {
            LOGGER.warn(
               "Failed to load datapacks, can't proceed with server load. You can either fix your datapacks or reset to vanilla with --safeMode", var41
            );
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.close();
            return;
         }

         _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.loadRegistryTags();
         RegistryOps<Tag> _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = RegistryOps.of(
            NbtOps.INSTANCE, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getResourceManager(), _snowmanxxxxxxxxxxxxxxxx
         );
         SaveProperties _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx.readLevelProperties(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
         );
         if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == null) {
            LevelInfo _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
            GeneratorOptions _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
            if (_snowmanxxxxxxxxxxxxxxx.has(_snowmanxxx)) {
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = MinecraftServer.DEMO_LEVEL_INFO;
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = GeneratorOptions.method_31112(_snowmanxxxxxxxxxxxxxxxx);
            } else {
               ServerPropertiesHandler _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxx.getPropertiesHandler();
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = new LevelInfo(
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.levelName,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.gameMode,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.hardcore,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.difficulty,
                  false,
                  new GameRules(),
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
               );
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx.has(_snowmanxxxx)
                  ? _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.generatorOptions.withBonusChest()
                  : _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.generatorOptions;
            }

            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = new LevelProperties(
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, Lifecycle.stable()
            );
         }

         if (_snowmanxxxxxxxxxxxxxxx.has(_snowmanxxxxx)) {
            forceUpgradeWorld(
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               Schemas.getFixer(),
               _snowmanxxxxxxxxxxxxxxx.has(_snowmanxxxxxx),
               () -> true,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getGeneratorOptions().getWorlds()
            );
         }

         _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx.backupLevelDataFile(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx);
         SaveProperties _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
         final MinecraftDedicatedServer _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = MinecraftServer.startServer(
            serverThread -> {
               MinecraftDedicatedServer _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = new MinecraftDedicatedServer(
                  serverThread, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, Schemas.getFixer(), _snowman, _snowman, _snowman, WorldGenerationProgressLogger::new
               );
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setServerName((String)_snowman.valueOf(_snowman));
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setServerPort((Integer)_snowman.valueOf(_snowman));
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setDemo(_snowman.has(_snowman));
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setServerId((String)_snowman.valueOf(_snowman));
               boolean _snowmanx = !_snowman.has(_snowman) && !_snowman.valuesOf(_snowman).contains("nogui");
               if (_snowmanx && !GraphicsEnvironment.isHeadless()) {
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.createGui();
               }

               return _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
            }
         );
         Thread _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = new Thread("Server Shutdown Thread") {
            @Override
            public void run() {
               _snowman.stop(true);
            }
         };
         _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setUncaughtExceptionHandler(new UncaughtExceptionLogger(LOGGER));
         Runtime.getRuntime().addShutdownHook(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx);
      } catch (Exception var42) {
         LOGGER.fatal("Failed to start the minecraft server", var42);
      }
   }

   private static void forceUpgradeWorld(
      LevelStorage.Session session, DataFixer dataFixer, boolean eraseCache, BooleanSupplier _snowman, ImmutableSet<RegistryKey<World>> worlds
   ) {
      LOGGER.info("Forcing world upgrade!");
      WorldUpdater _snowmanx = new WorldUpdater(session, dataFixer, worlds, eraseCache);
      Text _snowmanxx = null;

      while (!_snowmanx.isDone()) {
         Text _snowmanxxx = _snowmanx.getStatus();
         if (_snowmanxx != _snowmanxxx) {
            _snowmanxx = _snowmanxxx;
            LOGGER.info(_snowmanx.getStatus().getString());
         }

         int _snowmanxxxx = _snowmanx.getTotalChunkCount();
         if (_snowmanxxxx > 0) {
            int _snowmanxxxxx = _snowmanx.getUpgradedChunkCount() + _snowmanx.getSkippedChunkCount();
            LOGGER.info("{}% completed ({} / {} chunks)...", MathHelper.floor((float)_snowmanxxxxx / (float)_snowmanxxxx * 100.0F), _snowmanxxxxx, _snowmanxxxx);
         }

         if (!_snowman.getAsBoolean()) {
            _snowmanx.cancel();
         } else {
            try {
               Thread.sleep(1000L);
            } catch (InterruptedException var10) {
            }
         }
      }
   }
}
