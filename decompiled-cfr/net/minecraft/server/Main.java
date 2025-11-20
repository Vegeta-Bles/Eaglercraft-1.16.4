/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  com.mojang.authlib.GameProfileRepository
 *  com.mojang.authlib.minecraft.MinecraftSessionService
 *  com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService
 *  com.mojang.datafixers.DataFixer
 *  com.mojang.serialization.Lifecycle
 *  joptsimple.AbstractOptionSpec
 *  joptsimple.ArgumentAcceptingOptionSpec
 *  joptsimple.NonOptionArgumentSpec
 *  joptsimple.OptionParser
 *  joptsimple.OptionSet
 *  joptsimple.OptionSpec
 *  joptsimple.OptionSpecBuilder
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.server;

import com.google.common.collect.ImmutableSet;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Lifecycle;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.OutputStream;
import java.net.Proxy;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.BooleanSupplier;
import joptsimple.AbstractOptionSpec;
import joptsimple.ArgumentAcceptingOptionSpec;
import joptsimple.NonOptionArgumentSpec;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import joptsimple.OptionSpecBuilder;
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
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressLogger;
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

    public static void main(String[] args) {
        OptionParser optionParser = new OptionParser();
        OptionSpecBuilder _snowman2 = optionParser.accepts("nogui");
        OptionSpecBuilder _snowman3 = optionParser.accepts("initSettings", "Initializes 'server.properties' and 'eula.txt', then quits");
        OptionSpecBuilder _snowman4 = optionParser.accepts("demo");
        OptionSpecBuilder _snowman5 = optionParser.accepts("bonusChest");
        OptionSpecBuilder _snowman6 = optionParser.accepts("forceUpgrade");
        OptionSpecBuilder _snowman7 = optionParser.accepts("eraseCache");
        OptionSpecBuilder _snowman8 = optionParser.accepts("safeMode", "Loads level with vanilla datapack only");
        AbstractOptionSpec _snowman9 = optionParser.accepts("help").forHelp();
        ArgumentAcceptingOptionSpec _snowman10 = optionParser.accepts("singleplayer").withRequiredArg();
        ArgumentAcceptingOptionSpec _snowman11 = optionParser.accepts("universe").withRequiredArg().defaultsTo((Object)".", (Object[])new String[0]);
        ArgumentAcceptingOptionSpec _snowman12 = optionParser.accepts("world").withRequiredArg();
        ArgumentAcceptingOptionSpec _snowman13 = optionParser.accepts("port").withRequiredArg().ofType(Integer.class).defaultsTo((Object)-1, (Object[])new Integer[0]);
        ArgumentAcceptingOptionSpec _snowman14 = optionParser.accepts("serverId").withRequiredArg();
        NonOptionArgumentSpec _snowman15 = optionParser.nonOptions();
        try {
            Object _snowman37;
            Object _snowman35;
            OptionSet optionSet = optionParser.parse(args);
            if (optionSet.has((OptionSpec)_snowman9)) {
                optionParser.printHelpOn((OutputStream)System.err);
                return;
            }
            CrashReport.initCrashReport();
            Bootstrap.initialize();
            Bootstrap.logMissing();
            Util.startTimerHack();
            DynamicRegistryManager.Impl _snowman16 = DynamicRegistryManager.create();
            Path _snowman17 = Paths.get("server.properties", new String[0]);
            ServerPropertiesLoader _snowman18 = new ServerPropertiesLoader(_snowman16, _snowman17);
            _snowman18.store();
            Path _snowman19 = Paths.get("eula.txt", new String[0]);
            EulaReader _snowman20 = new EulaReader(_snowman19);
            if (optionSet.has((OptionSpec)_snowman3)) {
                LOGGER.info("Initialized '{}' and '{}'", (Object)_snowman17.toAbsolutePath(), (Object)_snowman19.toAbsolutePath());
                return;
            }
            if (!_snowman20.isEulaAgreedTo()) {
                LOGGER.info("You need to agree to the EULA in order to run the server. Go to eula.txt for more info.");
                return;
            }
            File _snowman21 = new File((String)optionSet.valueOf((OptionSpec)_snowman11));
            YggdrasilAuthenticationService _snowman22 = new YggdrasilAuthenticationService(Proxy.NO_PROXY);
            MinecraftSessionService _snowman23 = _snowman22.createMinecraftSessionService();
            GameProfileRepository _snowman24 = _snowman22.createProfileRepository();
            UserCache _snowman25 = new UserCache(_snowman24, new File(_snowman21, MinecraftServer.USER_CACHE_FILE.getName()));
            String _snowman26 = (String)Optional.ofNullable(optionSet.valueOf((OptionSpec)_snowman12)).orElse(_snowman18.getPropertiesHandler().levelName);
            LevelStorage _snowman27 = LevelStorage.create(_snowman21.toPath());
            LevelStorage.Session _snowman28 = _snowman27.createSession(_snowman26);
            MinecraftServer.convertLevel(_snowman28);
            DataPackSettings _snowman29 = _snowman28.getDataPackSettings();
            boolean _snowman30 = optionSet.has((OptionSpec)_snowman8);
            if (_snowman30) {
                LOGGER.warn("Safe mode active, only vanilla datapack will be loaded");
            }
            ResourcePackManager _snowman31 = new ResourcePackManager(new VanillaDataPackProvider(), new FileResourcePackProvider(_snowman28.getDirectory(WorldSavePath.DATAPACKS).toFile(), ResourcePackSource.PACK_SOURCE_WORLD));
            DataPackSettings _snowman32 = MinecraftServer.loadDataPacks(_snowman31, _snowman29 == null ? DataPackSettings.SAFE_MODE : _snowman29, _snowman30);
            CompletableFuture<ServerResourceManager> _snowman33 = ServerResourceManager.reload(_snowman31.createResourcePacks(), CommandManager.RegistrationEnvironment.DEDICATED, _snowman18.getPropertiesHandler().functionPermissionLevel, Util.getMainWorkerExecutor(), Runnable::run);
            try {
                ServerResourceManager serverResourceManager = _snowman33.get();
            }
            catch (Exception exception) {
                LOGGER.warn("Failed to load datapacks, can't proceed with server load. You can either fix your datapacks or reset to vanilla with --safeMode", (Throwable)exception);
                _snowman31.close();
                return;
            }
            serverResourceManager.loadRegistryTags();
            RegistryOps<Tag> registryOps = RegistryOps.of(NbtOps.INSTANCE, serverResourceManager.getResourceManager(), _snowman16);
            SaveProperties _snowman34 = _snowman28.readLevelProperties(registryOps, _snowman32);
            if (_snowman34 == null) {
                if (optionSet.has((OptionSpec)_snowman4)) {
                    _snowman35 = MinecraftServer.DEMO_LEVEL_INFO;
                    _snowman36 = GeneratorOptions.method_31112(_snowman16);
                } else {
                    _snowman37 = _snowman18.getPropertiesHandler();
                    _snowman35 = new LevelInfo(((ServerPropertiesHandler)_snowman37).levelName, ((ServerPropertiesHandler)_snowman37).gameMode, ((ServerPropertiesHandler)_snowman37).hardcore, ((ServerPropertiesHandler)_snowman37).difficulty, false, new GameRules(), _snowman32);
                    _snowman36 = optionSet.has((OptionSpec)_snowman5) ? ((ServerPropertiesHandler)_snowman37).generatorOptions.withBonusChest() : ((ServerPropertiesHandler)_snowman37).generatorOptions;
                }
                _snowman34 = new LevelProperties((LevelInfo)_snowman35, (GeneratorOptions)_snowman36, Lifecycle.stable());
            }
            if (optionSet.has((OptionSpec)_snowman6)) {
                Main.forceUpgradeWorld(_snowman28, Schemas.getFixer(), optionSet.has((OptionSpec)_snowman7), () -> true, _snowman34.getGeneratorOptions().getWorlds());
            }
            _snowman28.backupLevelDataFile(_snowman16, _snowman34);
            _snowman35 = _snowman34;
            Object _snowman36 = MinecraftServer.startServer(arg_0 -> Main.method_29734(_snowman16, _snowman28, _snowman31, serverResourceManager, (SaveProperties)_snowman35, _snowman18, _snowman23, _snowman24, _snowman25, optionSet, (OptionSpec)_snowman10, (OptionSpec)_snowman13, (OptionSpec)_snowman4, (OptionSpec)_snowman14, (OptionSpec)_snowman2, (OptionSpec)_snowman15, arg_0));
            _snowman37 = new Thread("Server Shutdown Thread", (MinecraftDedicatedServer)_snowman36){
                final /* synthetic */ MinecraftDedicatedServer field_4611;
                {
                    this.field_4611 = minecraftDedicatedServer;
                    super(string);
                }

                @Override
                public void run() {
                    this.field_4611.stop(true);
                }
            };
            ((Thread)_snowman37).setUncaughtExceptionHandler(new UncaughtExceptionLogger(LOGGER));
            Runtime.getRuntime().addShutdownHook((Thread)_snowman37);
        }
        catch (Exception exception) {
            LOGGER.fatal("Failed to start the minecraft server", (Throwable)exception);
        }
    }

    private static void forceUpgradeWorld(LevelStorage.Session session, DataFixer dataFixer, boolean eraseCache, BooleanSupplier booleanSupplier2, ImmutableSet<RegistryKey<World>> worlds) {
        LOGGER.info("Forcing world upgrade!");
        WorldUpdater worldUpdater = new WorldUpdater(session, dataFixer, worlds, eraseCache);
        Text _snowman2 = null;
        while (!worldUpdater.isDone()) {
            BooleanSupplier booleanSupplier2;
            Text text = worldUpdater.getStatus();
            if (_snowman2 != text) {
                _snowman2 = text;
                LOGGER.info(worldUpdater.getStatus().getString());
            }
            if ((_snowman = worldUpdater.getTotalChunkCount()) > 0) {
                int n = worldUpdater.getUpgradedChunkCount() + worldUpdater.getSkippedChunkCount();
                LOGGER.info("{}% completed ({} / {} chunks)...", (Object)MathHelper.floor((float)n / (float)_snowman * 100.0f), (Object)n, (Object)_snowman);
            }
            if (!booleanSupplier2.getAsBoolean()) {
                worldUpdater.cancel();
                continue;
            }
            try {
                Thread.sleep(1000L);
            }
            catch (InterruptedException interruptedException) {}
        }
    }

    private static /* synthetic */ MinecraftDedicatedServer method_29734(DynamicRegistryManager.Impl registryTracker, LevelStorage.Session session, ResourcePackManager resourcePackManager, ServerResourceManager serverResourceManager, SaveProperties saveProperties, ServerPropertiesLoader propertiesLoader, MinecraftSessionService sessionService, GameProfileRepository profileRepository, UserCache userCache, OptionSet optionSet, OptionSpec serverName, OptionSpec serverPort, OptionSpec demo, OptionSpec serverId, OptionSpec noGui, OptionSpec nonOptions, Thread serverThread) {
        MinecraftDedicatedServer minecraftDedicatedServer = new MinecraftDedicatedServer(serverThread, registryTracker, session, resourcePackManager, serverResourceManager, saveProperties, propertiesLoader, Schemas.getFixer(), sessionService, profileRepository, userCache, WorldGenerationProgressLogger::new);
        minecraftDedicatedServer.setServerName((String)optionSet.valueOf(serverName));
        minecraftDedicatedServer.setServerPort((Integer)optionSet.valueOf(serverPort));
        minecraftDedicatedServer.setDemo(optionSet.has(demo));
        minecraftDedicatedServer.setServerId((String)optionSet.valueOf(serverId));
        boolean bl = _snowman = !optionSet.has(noGui) && !optionSet.valuesOf(nonOptions).contains("nogui");
        if (_snowman && !GraphicsEnvironment.isHeadless()) {
            minecraftDedicatedServer.createGui();
        }
        return minecraftDedicatedServer;
    }
}

