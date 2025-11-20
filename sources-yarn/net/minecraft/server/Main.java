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
      OptionParser optionParser = new OptionParser();
      OptionSpec<Void> optionSpec = optionParser.accepts("nogui");
      OptionSpec<Void> optionSpec2 = optionParser.accepts("initSettings", "Initializes 'server.properties' and 'eula.txt', then quits");
      OptionSpec<Void> optionSpec3 = optionParser.accepts("demo");
      OptionSpec<Void> optionSpec4 = optionParser.accepts("bonusChest");
      OptionSpec<Void> optionSpec5 = optionParser.accepts("forceUpgrade");
      OptionSpec<Void> optionSpec6 = optionParser.accepts("eraseCache");
      OptionSpec<Void> optionSpec7 = optionParser.accepts("safeMode", "Loads level with vanilla datapack only");
      OptionSpec<Void> optionSpec8 = optionParser.accepts("help").forHelp();
      OptionSpec<String> optionSpec9 = optionParser.accepts("singleplayer").withRequiredArg();
      OptionSpec<String> optionSpec10 = optionParser.accepts("universe").withRequiredArg().defaultsTo(".", new String[0]);
      OptionSpec<String> optionSpec11 = optionParser.accepts("world").withRequiredArg();
      OptionSpec<Integer> optionSpec12 = optionParser.accepts("port").withRequiredArg().ofType(Integer.class).defaultsTo(-1, new Integer[0]);
      OptionSpec<String> optionSpec13 = optionParser.accepts("serverId").withRequiredArg();
      OptionSpec<String> optionSpec14 = optionParser.nonOptions();

      try {
         OptionSet optionSet = optionParser.parse(args);
         if (optionSet.has(optionSpec8)) {
            optionParser.printHelpOn(System.err);
            return;
         }

         CrashReport.initCrashReport();
         Bootstrap.initialize();
         Bootstrap.logMissing();
         Util.startTimerHack();
         DynamicRegistryManager.Impl lv = DynamicRegistryManager.create();
         Path path = Paths.get("server.properties");
         ServerPropertiesLoader lv2 = new ServerPropertiesLoader(lv, path);
         lv2.store();
         Path path2 = Paths.get("eula.txt");
         EulaReader lv3 = new EulaReader(path2);
         if (optionSet.has(optionSpec2)) {
            LOGGER.info("Initialized '{}' and '{}'", path.toAbsolutePath(), path2.toAbsolutePath());
            return;
         }

         if (!lv3.isEulaAgreedTo()) {
            LOGGER.info("You need to agree to the EULA in order to run the server. Go to eula.txt for more info.");
            return;
         }

         File file = new File((String)optionSet.valueOf(optionSpec10));
         YggdrasilAuthenticationService yggdrasilAuthenticationService = new YggdrasilAuthenticationService(Proxy.NO_PROXY);
         MinecraftSessionService minecraftSessionService = yggdrasilAuthenticationService.createMinecraftSessionService();
         GameProfileRepository gameProfileRepository = yggdrasilAuthenticationService.createProfileRepository();
         UserCache lv4 = new UserCache(gameProfileRepository, new File(file, MinecraftServer.USER_CACHE_FILE.getName()));
         String string = (String)Optional.ofNullable(optionSet.valueOf(optionSpec11)).orElse(lv2.getPropertiesHandler().levelName);
         LevelStorage lv5 = LevelStorage.create(file.toPath());
         LevelStorage.Session lv6 = lv5.createSession(string);
         MinecraftServer.convertLevel(lv6);
         DataPackSettings lv7 = lv6.getDataPackSettings();
         boolean bl = optionSet.has(optionSpec7);
         if (bl) {
            LOGGER.warn("Safe mode active, only vanilla datapack will be loaded");
         }

         ResourcePackManager lv8 = new ResourcePackManager(
            new VanillaDataPackProvider(),
            new FileResourcePackProvider(lv6.getDirectory(WorldSavePath.DATAPACKS).toFile(), ResourcePackSource.PACK_SOURCE_WORLD)
         );
         DataPackSettings lv9 = MinecraftServer.loadDataPacks(lv8, lv7 == null ? DataPackSettings.SAFE_MODE : lv7, bl);
         CompletableFuture<ServerResourceManager> completableFuture = ServerResourceManager.reload(
            lv8.createResourcePacks(),
            CommandManager.RegistrationEnvironment.DEDICATED,
            lv2.getPropertiesHandler().functionPermissionLevel,
            Util.getMainWorkerExecutor(),
            Runnable::run
         );

         ServerResourceManager lv10;
         try {
            lv10 = completableFuture.get();
         } catch (Exception var41) {
            LOGGER.warn(
               "Failed to load datapacks, can't proceed with server load. You can either fix your datapacks or reset to vanilla with --safeMode", var41
            );
            lv8.close();
            return;
         }

         lv10.loadRegistryTags();
         RegistryOps<Tag> lv12 = RegistryOps.of(NbtOps.INSTANCE, lv10.getResourceManager(), lv);
         SaveProperties lv13 = lv6.readLevelProperties(lv12, lv9);
         if (lv13 == null) {
            LevelInfo lv14;
            GeneratorOptions lv15;
            if (optionSet.has(optionSpec3)) {
               lv14 = MinecraftServer.DEMO_LEVEL_INFO;
               lv15 = GeneratorOptions.method_31112(lv);
            } else {
               ServerPropertiesHandler lv16 = lv2.getPropertiesHandler();
               lv14 = new LevelInfo(lv16.levelName, lv16.gameMode, lv16.hardcore, lv16.difficulty, false, new GameRules(), lv9);
               lv15 = optionSet.has(optionSpec4) ? lv16.generatorOptions.withBonusChest() : lv16.generatorOptions;
            }

            lv13 = new LevelProperties(lv14, lv15, Lifecycle.stable());
         }

         if (optionSet.has(optionSpec5)) {
            forceUpgradeWorld(lv6, Schemas.getFixer(), optionSet.has(optionSpec6), () -> true, lv13.getGeneratorOptions().getWorlds());
         }

         lv6.backupLevelDataFile(lv, lv13);
         SaveProperties lv19 = lv13;
         final MinecraftDedicatedServer lv20 = MinecraftServer.startServer(
            serverThread -> {
               MinecraftDedicatedServer lvx = new MinecraftDedicatedServer(
                  serverThread,
                  lv,
                  lv6,
                  lv8,
                  lv10,
                  lv19,
                  lv2,
                  Schemas.getFixer(),
                  minecraftSessionService,
                  gameProfileRepository,
                  lv4,
                  WorldGenerationProgressLogger::new
               );
               lvx.setServerName((String)optionSet.valueOf(optionSpec9));
               lvx.setServerPort((Integer)optionSet.valueOf(optionSpec12));
               lvx.setDemo(optionSet.has(optionSpec3));
               lvx.setServerId((String)optionSet.valueOf(optionSpec13));
               boolean blx = !optionSet.has(optionSpec) && !optionSet.valuesOf(optionSpec14).contains("nogui");
               if (blx && !GraphicsEnvironment.isHeadless()) {
                  lvx.createGui();
               }

               return lvx;
            }
         );
         Thread thread = new Thread("Server Shutdown Thread") {
            @Override
            public void run() {
               lv20.stop(true);
            }
         };
         thread.setUncaughtExceptionHandler(new UncaughtExceptionLogger(LOGGER));
         Runtime.getRuntime().addShutdownHook(thread);
      } catch (Exception var42) {
         LOGGER.fatal("Failed to start the minecraft server", var42);
      }
   }

   private static void forceUpgradeWorld(
      LevelStorage.Session session, DataFixer dataFixer, boolean eraseCache, BooleanSupplier booleanSupplier, ImmutableSet<RegistryKey<World>> worlds
   ) {
      LOGGER.info("Forcing world upgrade!");
      WorldUpdater lv = new WorldUpdater(session, dataFixer, worlds, eraseCache);
      Text lv2 = null;

      while (!lv.isDone()) {
         Text lv3 = lv.getStatus();
         if (lv2 != lv3) {
            lv2 = lv3;
            LOGGER.info(lv.getStatus().getString());
         }

         int i = lv.getTotalChunkCount();
         if (i > 0) {
            int j = lv.getUpgradedChunkCount() + lv.getSkippedChunkCount();
            LOGGER.info("{}% completed ({} / {} chunks)...", MathHelper.floor((float)j / (float)i * 100.0F), j, i);
         }

         if (!booleanSupplier.getAsBoolean()) {
            lv.cancel();
         } else {
            try {
               Thread.sleep(1000L);
            } catch (InterruptedException var10) {
            }
         }
      }
   }
}
