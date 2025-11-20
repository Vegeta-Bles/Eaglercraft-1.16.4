/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Splitter
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  com.mojang.authlib.GameProfile
 *  com.mojang.authlib.GameProfileRepository
 *  com.mojang.authlib.minecraft.MinecraftSessionService
 *  com.mojang.datafixers.DataFixer
 *  io.netty.buffer.ByteBuf
 *  io.netty.buffer.ByteBufOutputStream
 *  io.netty.buffer.Unpooled
 *  it.unimi.dsi.fastutil.longs.LongIterator
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.Validate
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.server;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.datafixers.DataFixer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import it.unimi.dsi.fastutil.longs.LongIterator;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.net.Proxy;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.security.KeyPair;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import net.minecraft.SharedConstants;
import net.minecraft.block.Block;
import net.minecraft.command.DataCommandStorage;
import net.minecraft.entity.boss.BossBarManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.loot.LootManager;
import net.minecraft.loot.condition.LootConditionManager;
import net.minecraft.network.NetworkEncryptionUtils;
import net.minecraft.network.encryption.NetworkEncryptionException;
import net.minecraft.network.packet.s2c.play.DifficultyS2CPacket;
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.resource.DataPackSettings;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.resource.ServerResourceManager;
import net.minecraft.scoreboard.ScoreboardState;
import net.minecraft.scoreboard.ScoreboardSynchronizer;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.server.OperatorEntry;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.ServerAdvancementLoader;
import net.minecraft.server.ServerMetadata;
import net.minecraft.server.ServerNetworkIo;
import net.minecraft.server.ServerTask;
import net.minecraft.server.Whitelist;
import net.minecraft.server.WorldGenerationProgressListener;
import net.minecraft.server.WorldGenerationProgressListenerFactory;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.filter.TextStream;
import net.minecraft.server.function.CommandFunctionManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.SpawnLocating;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructureManager;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.TagManager;
import net.minecraft.test.TestManager;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.MetricsData;
import net.minecraft.util.ProgressListener;
import net.minecraft.util.TickDurationMonitor;
import net.minecraft.util.Unit;
import net.minecraft.util.UserCache;
import net.minecraft.util.Util;
import net.minecraft.util.WorldSavePath;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.profiler.DummyProfiler;
import net.minecraft.util.profiler.ProfileResult;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.profiler.TickTimeTracker;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.util.snooper.Snooper;
import net.minecraft.util.snooper.SnooperListener;
import net.minecraft.util.thread.ReentrantThreadExecutor;
import net.minecraft.village.ZombieSiegeManager;
import net.minecraft.world.Difficulty;
import net.minecraft.world.ForcedChunkState;
import net.minecraft.world.GameMode;
import net.minecraft.world.GameRules;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.SaveProperties;
import net.minecraft.world.WanderingTraderManager;
import net.minecraft.world.World;
import net.minecraft.world.WorldProperties;
import net.minecraft.world.WorldSaveHandler;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.border.WorldBorderListener;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.CatSpawner;
import net.minecraft.world.gen.GeneratorOptions;
import net.minecraft.world.gen.PhantomSpawner;
import net.minecraft.world.gen.PillagerSpawner;
import net.minecraft.world.gen.Spawner;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.level.LevelInfo;
import net.minecraft.world.level.ServerWorldProperties;
import net.minecraft.world.level.UnmodifiableLevelProperties;
import net.minecraft.world.level.storage.LevelStorage;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class MinecraftServer
extends ReentrantThreadExecutor<ServerTask>
implements SnooperListener,
CommandOutput,
AutoCloseable {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final File USER_CACHE_FILE = new File("usercache.json");
    public static final LevelInfo DEMO_LEVEL_INFO = new LevelInfo("Demo World", GameMode.SURVIVAL, false, Difficulty.NORMAL, false, new GameRules(), DataPackSettings.SAFE_MODE);
    protected final LevelStorage.Session session;
    protected final WorldSaveHandler saveHandler;
    private final Snooper snooper = new Snooper("server", this, Util.getMeasuringTimeMs());
    private final List<Runnable> serverGuiTickables = Lists.newArrayList();
    private final TickTimeTracker tickTimeTracker = new TickTimeTracker(Util.nanoTimeSupplier, this::getTicks);
    private Profiler profiler = DummyProfiler.INSTANCE;
    private final ServerNetworkIo networkIo;
    private final WorldGenerationProgressListenerFactory worldGenerationProgressListenerFactory;
    private final ServerMetadata metadata = new ServerMetadata();
    private final Random random = new Random();
    private final DataFixer dataFixer;
    private String serverIp;
    private int serverPort = -1;
    protected final DynamicRegistryManager.Impl registryManager;
    private final Map<RegistryKey<World>, ServerWorld> worlds = Maps.newLinkedHashMap();
    private PlayerManager playerManager;
    private volatile boolean running = true;
    private boolean stopped;
    private int ticks;
    protected final Proxy proxy;
    private boolean onlineMode;
    private boolean preventProxyConnections;
    private boolean pvpEnabled;
    private boolean flightEnabled;
    @Nullable
    private String motd;
    private int worldHeight;
    private int playerIdleTimeout;
    public final long[] lastTickLengths = new long[100];
    @Nullable
    private KeyPair keyPair;
    @Nullable
    private String userName;
    private boolean demo;
    private String resourcePackUrl = "";
    private String resourcePackHash = "";
    private volatile boolean loading;
    private long lastTimeReference;
    private boolean profilerStartQueued;
    private boolean forceGameMode;
    private final MinecraftSessionService sessionService;
    private final GameProfileRepository gameProfileRepo;
    private final UserCache userCache;
    private long lastPlayerSampleUpdate;
    private final Thread serverThread;
    private long timeReference = Util.getMeasuringTimeMs();
    private long field_19248;
    private boolean waitingForNextTick;
    private boolean iconFilePresent;
    private final ResourcePackManager dataPackManager;
    private final ServerScoreboard scoreboard = new ServerScoreboard(this);
    @Nullable
    private DataCommandStorage dataCommandStorage;
    private final BossBarManager bossBarManager = new BossBarManager();
    private final CommandFunctionManager commandFunctionManager;
    private final MetricsData metricsData = new MetricsData();
    private boolean enforceWhitelist;
    private float tickTime;
    private final Executor workerExecutor;
    @Nullable
    private String serverId;
    private ServerResourceManager serverResourceManager;
    private final StructureManager structureManager;
    protected final SaveProperties saveProperties;

    public static <S extends MinecraftServer> S startServer(Function<Thread, S> serverFactory) {
        AtomicReference<MinecraftServer> atomicReference = new AtomicReference<MinecraftServer>();
        Thread _snowman2 = new Thread(() -> ((MinecraftServer)atomicReference.get()).runServer(), "Server thread");
        _snowman2.setUncaughtExceptionHandler((thread, throwable) -> LOGGER.error((Object)throwable));
        MinecraftServer _snowman3 = (MinecraftServer)serverFactory.apply(_snowman2);
        atomicReference.set(_snowman3);
        _snowman2.start();
        return (S)_snowman3;
    }

    public MinecraftServer(Thread thread, DynamicRegistryManager.Impl impl, LevelStorage.Session session, SaveProperties saveProperties, ResourcePackManager resourcePackManager, Proxy proxy, DataFixer dataFixer, ServerResourceManager serverResourceManager, MinecraftSessionService minecraftSessionService, GameProfileRepository gameProfileRepository, UserCache userCache, WorldGenerationProgressListenerFactory worldGenerationProgressListenerFactory) {
        super("Server");
        this.registryManager = impl;
        this.saveProperties = saveProperties;
        this.proxy = proxy;
        this.dataPackManager = resourcePackManager;
        this.serverResourceManager = serverResourceManager;
        this.sessionService = minecraftSessionService;
        this.gameProfileRepo = gameProfileRepository;
        this.userCache = userCache;
        this.networkIo = new ServerNetworkIo(this);
        this.worldGenerationProgressListenerFactory = worldGenerationProgressListenerFactory;
        this.session = session;
        this.saveHandler = session.createSaveHandler();
        this.dataFixer = dataFixer;
        this.commandFunctionManager = new CommandFunctionManager(this, serverResourceManager.getFunctionLoader());
        this.structureManager = new StructureManager(serverResourceManager.getResourceManager(), session, dataFixer);
        this.serverThread = thread;
        this.workerExecutor = Util.getMainWorkerExecutor();
    }

    private void initScoreboard(PersistentStateManager persistentStateManager) {
        ScoreboardState scoreboardState = persistentStateManager.getOrCreate(ScoreboardState::new, "scoreboard");
        scoreboardState.setScoreboard(this.getScoreboard());
        this.getScoreboard().addUpdateListener(new ScoreboardSynchronizer(scoreboardState));
    }

    protected abstract boolean setupServer() throws IOException;

    public static void convertLevel(LevelStorage.Session session) {
        if (session.needsConversion()) {
            LOGGER.info("Converting map!");
            session.convert(new ProgressListener(){
                private long lastProgressUpdate = Util.getMeasuringTimeMs();

                @Override
                public void method_15412(Text text) {
                }

                @Override
                public void method_15413(Text text) {
                }

                @Override
                public void progressStagePercentage(int n) {
                    if (Util.getMeasuringTimeMs() - this.lastProgressUpdate >= 1000L) {
                        this.lastProgressUpdate = Util.getMeasuringTimeMs();
                        LOGGER.info("Converting... {}%", (Object)n);
                    }
                }

                @Override
                public void setDone() {
                }

                @Override
                public void method_15414(Text text) {
                }
            });
        }
    }

    protected void loadWorld() {
        this.loadWorldResourcePack();
        this.saveProperties.addServerBrand(this.getServerModName(), this.getModdedStatusMessage().isPresent());
        WorldGenerationProgressListener worldGenerationProgressListener = this.worldGenerationProgressListenerFactory.create(11);
        this.createWorlds(worldGenerationProgressListener);
        this.method_27731();
        this.prepareStartRegion(worldGenerationProgressListener);
    }

    protected void method_27731() {
    }

    protected void createWorlds(WorldGenerationProgressListener worldGenerationProgressListener) {
        ChunkGenerator _snowman9;
        ServerWorldProperties serverWorldProperties = this.saveProperties.getMainWorldProperties();
        GeneratorOptions _snowman2 = this.saveProperties.getGeneratorOptions();
        boolean _snowman3 = _snowman2.isDebugWorld();
        long _snowman4 = _snowman2.getSeed();
        long _snowman5 = BiomeAccess.hashSeed(_snowman4);
        ImmutableList _snowman6 = ImmutableList.of((Object)new PhantomSpawner(), (Object)new PillagerSpawner(), (Object)new CatSpawner(), (Object)new ZombieSiegeManager(), (Object)new WanderingTraderManager(serverWorldProperties));
        SimpleRegistry<DimensionOptions> _snowman7 = _snowman2.getDimensions();
        DimensionOptions _snowman8 = _snowman7.get(DimensionOptions.OVERWORLD);
        if (_snowman8 == null) {
            DimensionType dimensionType = this.registryManager.getDimensionTypes().getOrThrow(DimensionType.OVERWORLD_REGISTRY_KEY);
            _snowman9 = GeneratorOptions.createOverworldGenerator(this.registryManager.get(Registry.BIOME_KEY), this.registryManager.get(Registry.NOISE_SETTINGS_WORLDGEN), new Random().nextLong());
        } else {
            dimensionType = _snowman8.getDimensionType();
            _snowman9 = _snowman8.getChunkGenerator();
        }
        ServerWorld serverWorld = new ServerWorld(this, this.workerExecutor, this.session, serverWorldProperties, World.OVERWORLD, dimensionType, worldGenerationProgressListener, _snowman9, _snowman3, _snowman5, (List<Spawner>)_snowman6, true);
        this.worlds.put(World.OVERWORLD, serverWorld);
        PersistentStateManager _snowman10 = serverWorld.getPersistentStateManager();
        this.initScoreboard(_snowman10);
        this.dataCommandStorage = new DataCommandStorage(_snowman10);
        WorldBorder _snowman11 = serverWorld.getWorldBorder();
        _snowman11.load(serverWorldProperties.getWorldBorder());
        if (!serverWorldProperties.isInitialized()) {
            try {
                MinecraftServer.setupSpawn(serverWorld, serverWorldProperties, _snowman2.hasBonusChest(), _snowman3, true);
                serverWorldProperties.setInitialized(true);
                if (_snowman3) {
                    this.setToDebugWorldProperties(this.saveProperties);
                }
            }
            catch (Throwable throwable) {
                CrashReport crashReport = CrashReport.create(throwable, "Exception initializing level");
                try {
                    serverWorld.addDetailsToCrashReport(crashReport);
                }
                catch (Throwable throwable2) {
                    // empty catch block
                }
                throw new CrashException(crashReport);
            }
            serverWorldProperties.setInitialized(true);
        }
        this.getPlayerManager().setMainWorld(serverWorld);
        if (this.saveProperties.getCustomBossEvents() != null) {
            this.getBossBarManager().fromTag(this.saveProperties.getCustomBossEvents());
        }
        for (Map.Entry<RegistryKey<DimensionOptions>, DimensionOptions> entry : _snowman7.getEntries()) {
            RegistryKey<DimensionOptions> registryKey = entry.getKey();
            if (registryKey == DimensionOptions.OVERWORLD) continue;
            RegistryKey<World> _snowman12 = RegistryKey.of(Registry.DIMENSION, registryKey.getValue());
            DimensionType _snowman13 = entry.getValue().getDimensionType();
            ChunkGenerator _snowman14 = entry.getValue().getChunkGenerator();
            UnmodifiableLevelProperties _snowman15 = new UnmodifiableLevelProperties(this.saveProperties, serverWorldProperties);
            ServerWorld _snowman16 = new ServerWorld(this, this.workerExecutor, this.session, _snowman15, _snowman12, _snowman13, worldGenerationProgressListener, _snowman14, _snowman3, _snowman5, (List<Spawner>)ImmutableList.of(), false);
            _snowman11.addListener(new WorldBorderListener.WorldBorderSyncer(_snowman16.getWorldBorder()));
            this.worlds.put(_snowman12, _snowman16);
        }
    }

    private static void setupSpawn(ServerWorld world, ServerWorldProperties serverWorldProperties2, boolean bonusChest, boolean debugWorld, boolean bl) {
        ServerWorldProperties serverWorldProperties2;
        ChunkGenerator chunkGenerator = world.getChunkManager().getChunkGenerator();
        if (!bl) {
            serverWorldProperties2.setSpawnPos(BlockPos.ORIGIN.up(chunkGenerator.getSpawnHeight()), 0.0f);
            return;
        }
        if (debugWorld) {
            serverWorldProperties2.setSpawnPos(BlockPos.ORIGIN.up(), 0.0f);
            return;
        }
        BiomeSource _snowman2 = chunkGenerator.getBiomeSource();
        Random _snowman3 = new Random(world.getSeed());
        BlockPos _snowman4 = _snowman2.locateBiome(0, world.getSeaLevel(), 0, 256, biome -> biome.getSpawnSettings().isPlayerSpawnFriendly(), _snowman3);
        ChunkPos chunkPos = _snowman = _snowman4 == null ? new ChunkPos(0, 0) : new ChunkPos(_snowman4);
        if (_snowman4 == null) {
            LOGGER.warn("Unable to find spawn biome");
        }
        boolean _snowman5 = false;
        for (Block block : BlockTags.VALID_SPAWN.values()) {
            if (!_snowman2.getTopMaterials().contains(block.getDefaultState())) continue;
            _snowman5 = true;
            break;
        }
        serverWorldProperties2.setSpawnPos(_snowman.getStartPos().add(8, chunkGenerator.getSpawnHeight(), 8), 0.0f);
        int _snowman6 = 0;
        int _snowman7 = 0;
        int _snowman8 = 0;
        int _snowman9 = -1;
        int _snowman10 = 32;
        for (int i = 0; i < 1024; ++i) {
            if (_snowman6 > -16 && _snowman6 <= 16 && _snowman7 > -16 && _snowman7 <= 16 && (_snowman = SpawnLocating.findServerSpawnPoint(world, new ChunkPos(_snowman.x + _snowman6, _snowman.z + _snowman7), _snowman5)) != null) {
                serverWorldProperties2.setSpawnPos(_snowman, 0.0f);
                break;
            }
            if (_snowman6 == _snowman7 || _snowman6 < 0 && _snowman6 == -_snowman7 || _snowman6 > 0 && _snowman6 == 1 - _snowman7) {
                _snowman = _snowman8;
                _snowman8 = -_snowman9;
                _snowman9 = _snowman;
            }
            _snowman6 += _snowman8;
            _snowman7 += _snowman9;
        }
        if (bonusChest) {
            ConfiguredFeature<?, ?> configuredFeature = ConfiguredFeatures.BONUS_CHEST;
            configuredFeature.generate(world, chunkGenerator, world.random, new BlockPos(serverWorldProperties2.getSpawnX(), serverWorldProperties2.getSpawnY(), serverWorldProperties2.getSpawnZ()));
        }
    }

    private void setToDebugWorldProperties(SaveProperties properties) {
        properties.setDifficulty(Difficulty.PEACEFUL);
        properties.setDifficultyLocked(true);
        ServerWorldProperties serverWorldProperties = properties.getMainWorldProperties();
        serverWorldProperties.setRaining(false);
        serverWorldProperties.setThundering(false);
        serverWorldProperties.setClearWeatherTime(1000000000);
        serverWorldProperties.setTimeOfDay(6000L);
        serverWorldProperties.setGameMode(GameMode.SPECTATOR);
    }

    private void prepareStartRegion(WorldGenerationProgressListener worldGenerationProgressListener) {
        ServerWorld serverWorld = this.getOverworld();
        LOGGER.info("Preparing start region for dimension {}", (Object)serverWorld.getRegistryKey().getValue());
        BlockPos _snowman2 = serverWorld.getSpawnPos();
        worldGenerationProgressListener.start(new ChunkPos(_snowman2));
        ServerChunkManager _snowman3 = serverWorld.getChunkManager();
        _snowman3.getLightingProvider().setTaskBatchSize(500);
        this.timeReference = Util.getMeasuringTimeMs();
        _snowman3.addTicket(ChunkTicketType.START, new ChunkPos(_snowman2), 11, Unit.INSTANCE);
        while (_snowman3.getTotalChunksLoadedCount() != 441) {
            this.timeReference = Util.getMeasuringTimeMs() + 10L;
            this.method_16208();
        }
        this.timeReference = Util.getMeasuringTimeMs() + 10L;
        this.method_16208();
        for (ServerWorld serverWorld2 : this.worlds.values()) {
            ForcedChunkState forcedChunkState = serverWorld2.getPersistentStateManager().get(ForcedChunkState::new, "chunks");
            if (forcedChunkState == null) continue;
            LongIterator _snowman4 = forcedChunkState.getChunks().iterator();
            while (_snowman4.hasNext()) {
                long l = _snowman4.nextLong();
                ChunkPos _snowman5 = new ChunkPos(l);
                serverWorld2.getChunkManager().setChunkForced(_snowman5, true);
            }
        }
        this.timeReference = Util.getMeasuringTimeMs() + 10L;
        this.method_16208();
        worldGenerationProgressListener.stop();
        _snowman3.getLightingProvider().setTaskBatchSize(5);
        this.updateMobSpawnOptions();
    }

    protected void loadWorldResourcePack() {
        File file = this.session.getDirectory(WorldSavePath.RESOURCES_ZIP).toFile();
        if (file.isFile()) {
            String string = this.session.getDirectoryName();
            try {
                this.setResourcePack("level://" + URLEncoder.encode(string, StandardCharsets.UTF_8.toString()) + "/" + "resources.zip", "");
            }
            catch (UnsupportedEncodingException _snowman2) {
                LOGGER.warn("Something went wrong url encoding {}", (Object)string);
            }
        }
    }

    public GameMode getDefaultGameMode() {
        return this.saveProperties.getGameMode();
    }

    public boolean isHardcore() {
        return this.saveProperties.isHardcore();
    }

    public abstract int getOpPermissionLevel();

    public abstract int getFunctionPermissionLevel();

    public abstract boolean shouldBroadcastRconToOps();

    public boolean save(boolean suppressLogs, boolean bl, boolean bl2) {
        boolean bl3 = false;
        for (ServerWorld serverWorld : this.getWorlds()) {
            if (!suppressLogs) {
                LOGGER.info("Saving chunks for level '{}'/{}", (Object)serverWorld, (Object)serverWorld.getRegistryKey().getValue());
            }
            serverWorld.save(null, bl, serverWorld.savingDisabled && !bl2);
            bl3 = true;
        }
        ServerWorld serverWorld = this.getOverworld();
        ServerWorldProperties serverWorldProperties = this.saveProperties.getMainWorldProperties();
        serverWorldProperties.setWorldBorder(serverWorld.getWorldBorder().write());
        this.saveProperties.setCustomBossEvents(this.getBossBarManager().toTag());
        this.session.backupLevelDataFile(this.registryManager, this.saveProperties, this.getPlayerManager().getUserData());
        return bl3;
    }

    @Override
    public void close() {
        this.shutdown();
    }

    protected void shutdown() {
        LOGGER.info("Stopping server");
        if (this.getNetworkIo() != null) {
            this.getNetworkIo().stop();
        }
        if (this.playerManager != null) {
            LOGGER.info("Saving players");
            this.playerManager.saveAllPlayerData();
            this.playerManager.disconnectAllPlayers();
        }
        LOGGER.info("Saving worlds");
        for (ServerWorld serverWorld : this.getWorlds()) {
            if (serverWorld == null) continue;
            serverWorld.savingDisabled = false;
        }
        this.save(false, true, false);
        for (ServerWorld serverWorld : this.getWorlds()) {
            if (serverWorld == null) continue;
            try {
                serverWorld.close();
            }
            catch (IOException iOException) {
                LOGGER.error("Exception closing the level", (Throwable)iOException);
            }
        }
        if (this.snooper.isActive()) {
            this.snooper.cancel();
        }
        this.serverResourceManager.close();
        try {
            this.session.close();
        }
        catch (IOException iOException) {
            LOGGER.error("Failed to unlock level {}", (Object)this.session.getDirectoryName(), (Object)iOException);
        }
    }

    public String getServerIp() {
        return this.serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public boolean isRunning() {
        return this.running;
    }

    public void stop(boolean bl) {
        this.running = false;
        if (bl) {
            try {
                this.serverThread.join();
            }
            catch (InterruptedException interruptedException) {
                LOGGER.error("Error while shutting down", (Throwable)interruptedException);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void runServer() {
        try {
            if (this.setupServer()) {
                this.timeReference = Util.getMeasuringTimeMs();
                this.metadata.setDescription(new LiteralText(this.motd));
                this.metadata.setVersion(new ServerMetadata.Version(SharedConstants.getGameVersion().getName(), SharedConstants.getGameVersion().getProtocolVersion()));
                this.setFavicon(this.metadata);
                while (this.running) {
                    long l = Util.getMeasuringTimeMs() - this.timeReference;
                    if (l > 2000L && this.timeReference - this.lastTimeReference >= 15000L) {
                        _snowman = l / 50L;
                        LOGGER.warn("Can't keep up! Is the server overloaded? Running {}ms or {} ticks behind", (Object)l, (Object)_snowman);
                        this.timeReference += _snowman * 50L;
                        this.lastTimeReference = this.timeReference;
                    }
                    this.timeReference += 50L;
                    TickDurationMonitor _snowman2 = TickDurationMonitor.create("Server");
                    this.startMonitor(_snowman2);
                    this.profiler.startTick();
                    this.profiler.push("tick");
                    this.tick(this::shouldKeepTicking);
                    this.profiler.swap("nextTickWait");
                    this.waitingForNextTick = true;
                    this.field_19248 = Math.max(Util.getMeasuringTimeMs() + 50L, this.timeReference);
                    this.method_16208();
                    this.profiler.pop();
                    this.profiler.endTick();
                    this.endMonitor(_snowman2);
                    this.loading = true;
                }
            } else {
                this.setCrashReport(null);
            }
        }
        catch (Throwable throwable) {
            LOGGER.error("Encountered an unexpected exception", throwable);
            CrashReport crashReport = throwable instanceof CrashException ? this.populateCrashReport(((CrashException)throwable).getReport()) : this.populateCrashReport(new CrashReport("Exception in server tick loop", throwable));
            File _snowman3 = new File(new File(this.getRunDirectory(), "crash-reports"), "crash-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + "-server.txt");
            if (crashReport.writeToFile(_snowman3)) {
                LOGGER.error("This crash report has been saved to: {}", (Object)_snowman3.getAbsolutePath());
            } else {
                LOGGER.error("We were unable to save this crash report to disk.");
            }
            this.setCrashReport(crashReport);
        }
        finally {
            try {
                this.stopped = true;
                this.shutdown();
            }
            catch (Throwable _snowman4) {
                LOGGER.error("Exception stopping the server", _snowman4);
            }
            finally {
                this.exit();
            }
        }
    }

    private boolean shouldKeepTicking() {
        return this.hasRunningTasks() || Util.getMeasuringTimeMs() < (this.waitingForNextTick ? this.field_19248 : this.timeReference);
    }

    protected void method_16208() {
        this.runTasks();
        this.runTasks(() -> !this.shouldKeepTicking());
    }

    @Override
    protected ServerTask createTask(Runnable runnable) {
        return new ServerTask(this.ticks, runnable);
    }

    @Override
    protected boolean canExecute(ServerTask serverTask) {
        return serverTask.getCreationTicks() + 3 < this.ticks || this.shouldKeepTicking();
    }

    @Override
    public boolean runTask() {
        boolean bl;
        this.waitingForNextTick = bl = this.method_20415();
        return bl;
    }

    private boolean method_20415() {
        if (super.runTask()) {
            return true;
        }
        if (this.shouldKeepTicking()) {
            for (ServerWorld serverWorld : this.getWorlds()) {
                if (!serverWorld.getChunkManager().executeQueuedTasks()) continue;
                return true;
            }
        }
        return false;
    }

    @Override
    protected void executeTask(ServerTask serverTask) {
        this.getProfiler().visit("runTask");
        super.executeTask(serverTask);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void setFavicon(ServerMetadata metadata) {
        File file = this.getFile("server-icon.png");
        if (!file.exists()) {
            file = this.session.getIconFile();
        }
        if (file.isFile()) {
            ByteBuf byteBuf = Unpooled.buffer();
            try {
                BufferedImage bufferedImage = ImageIO.read(file);
                Validate.validState((bufferedImage.getWidth() == 64 ? 1 : 0) != 0, (String)"Must be 64 pixels wide", (Object[])new Object[0]);
                Validate.validState((bufferedImage.getHeight() == 64 ? 1 : 0) != 0, (String)"Must be 64 pixels high", (Object[])new Object[0]);
                ImageIO.write((RenderedImage)bufferedImage, "PNG", (OutputStream)new ByteBufOutputStream(byteBuf));
                ByteBuffer _snowman2 = Base64.getEncoder().encode(byteBuf.nioBuffer());
                metadata.setFavicon("data:image/png;base64," + StandardCharsets.UTF_8.decode(_snowman2));
            }
            catch (Exception exception) {
                LOGGER.error("Couldn't load server icon", (Throwable)exception);
            }
            finally {
                byteBuf.release();
            }
        }
    }

    public boolean hasIconFile() {
        this.iconFilePresent = this.iconFilePresent || this.getIconFile().isFile();
        return this.iconFilePresent;
    }

    public File getIconFile() {
        return this.session.getIconFile();
    }

    public File getRunDirectory() {
        return new File(".");
    }

    protected void setCrashReport(CrashReport report) {
    }

    protected void exit() {
    }

    protected void tick(BooleanSupplier shouldKeepTicking) {
        long l = Util.getMeasuringTimeNano();
        ++this.ticks;
        this.tickWorlds(shouldKeepTicking);
        if (l - this.lastPlayerSampleUpdate >= 5000000000L) {
            this.lastPlayerSampleUpdate = l;
            this.metadata.setPlayers(new ServerMetadata.Players(this.getMaxPlayerCount(), this.getCurrentPlayerCount()));
            GameProfile[] gameProfileArray = new GameProfile[Math.min(this.getCurrentPlayerCount(), 12)];
            int _snowman2 = MathHelper.nextInt(this.random, 0, this.getCurrentPlayerCount() - gameProfileArray.length);
            for (int i = 0; i < gameProfileArray.length; ++i) {
                gameProfileArray[i] = this.playerManager.getPlayerList().get(_snowman2 + i).getGameProfile();
            }
            Collections.shuffle(Arrays.asList(gameProfileArray));
            this.metadata.getPlayers().setSample(gameProfileArray);
        }
        if (this.ticks % 6000 == 0) {
            LOGGER.debug("Autosave started");
            this.profiler.push("save");
            this.playerManager.saveAllPlayerData();
            this.save(true, false, false);
            this.profiler.pop();
            LOGGER.debug("Autosave finished");
        }
        this.profiler.push("snooper");
        if (!this.snooper.isActive() && this.ticks > 100) {
            this.snooper.method_5482();
        }
        if (this.ticks % 6000 == 0) {
            this.snooper.update();
        }
        this.profiler.pop();
        this.profiler.push("tallying");
        long l2 = Util.getMeasuringTimeNano() - l;
        this.lastTickLengths[this.ticks % 100] = l2;
        _snowman = l2;
        this.tickTime = this.tickTime * 0.8f + (float)_snowman / 1000000.0f * 0.19999999f;
        _snowman = Util.getMeasuringTimeNano();
        this.metricsData.pushSample(_snowman - l);
        this.profiler.pop();
    }

    protected void tickWorlds(BooleanSupplier shouldKeepTicking) {
        this.profiler.push("commandFunctions");
        this.getCommandFunctionManager().tick();
        this.profiler.swap("levels");
        for (ServerWorld serverWorld : this.getWorlds()) {
            this.profiler.push(() -> serverWorld + " " + serverWorld.getRegistryKey().getValue());
            if (this.ticks % 20 == 0) {
                this.profiler.push("timeSync");
                this.playerManager.sendToDimension(new WorldTimeUpdateS2CPacket(serverWorld.getTime(), serverWorld.getTimeOfDay(), serverWorld.getGameRules().getBoolean(GameRules.DO_DAYLIGHT_CYCLE)), serverWorld.getRegistryKey());
                this.profiler.pop();
            }
            this.profiler.push("tick");
            try {
                serverWorld.tick(shouldKeepTicking);
            }
            catch (Throwable throwable) {
                CrashReport crashReport = CrashReport.create(throwable, "Exception ticking world");
                serverWorld.addDetailsToCrashReport(crashReport);
                throw new CrashException(crashReport);
            }
            this.profiler.pop();
            this.profiler.pop();
        }
        this.profiler.swap("connection");
        this.getNetworkIo().tick();
        this.profiler.swap("players");
        this.playerManager.updatePlayerLatency();
        if (SharedConstants.isDevelopment) {
            TestManager.INSTANCE.tick();
        }
        this.profiler.swap("server gui refresh");
        for (int i = 0; i < this.serverGuiTickables.size(); ++i) {
            this.serverGuiTickables.get(i).run();
        }
        this.profiler.pop();
    }

    public boolean isNetherAllowed() {
        return true;
    }

    public void addServerGuiTickable(Runnable tickable) {
        this.serverGuiTickables.add(tickable);
    }

    protected void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public boolean isStopping() {
        return !this.serverThread.isAlive();
    }

    public File getFile(String path) {
        return new File(this.getRunDirectory(), path);
    }

    public final ServerWorld getOverworld() {
        return this.worlds.get(World.OVERWORLD);
    }

    @Nullable
    public ServerWorld getWorld(RegistryKey<World> key) {
        return this.worlds.get(key);
    }

    public Set<RegistryKey<World>> getWorldRegistryKeys() {
        return this.worlds.keySet();
    }

    public Iterable<ServerWorld> getWorlds() {
        return this.worlds.values();
    }

    public String getVersion() {
        return SharedConstants.getGameVersion().getName();
    }

    public int getCurrentPlayerCount() {
        return this.playerManager.getCurrentPlayerCount();
    }

    public int getMaxPlayerCount() {
        return this.playerManager.getMaxPlayerCount();
    }

    public String[] getPlayerNames() {
        return this.playerManager.getPlayerNames();
    }

    public String getServerModName() {
        return "vanilla";
    }

    public CrashReport populateCrashReport(CrashReport report) {
        if (this.playerManager != null) {
            report.getSystemDetailsSection().add("Player Count", () -> this.playerManager.getCurrentPlayerCount() + " / " + this.playerManager.getMaxPlayerCount() + "; " + this.playerManager.getPlayerList());
        }
        report.getSystemDetailsSection().add("Data Packs", () -> {
            StringBuilder stringBuilder = new StringBuilder();
            for (ResourcePackProfile resourcePackProfile : this.dataPackManager.getEnabledProfiles()) {
                if (stringBuilder.length() > 0) {
                    stringBuilder.append(", ");
                }
                stringBuilder.append(resourcePackProfile.getName());
                if (resourcePackProfile.getCompatibility().isCompatible()) continue;
                stringBuilder.append(" (incompatible)");
            }
            return stringBuilder.toString();
        });
        if (this.serverId != null) {
            report.getSystemDetailsSection().add("Server Id", () -> this.serverId);
        }
        return report;
    }

    public abstract Optional<String> getModdedStatusMessage();

    @Override
    public void sendSystemMessage(Text message, UUID senderUuid) {
        LOGGER.info(message.getString());
    }

    public KeyPair getKeyPair() {
        return this.keyPair;
    }

    public int getServerPort() {
        return this.serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setServerName(String serverName) {
        this.userName = serverName;
    }

    public boolean isSinglePlayer() {
        return this.userName != null;
    }

    protected void method_31400() {
        LOGGER.info("Generating keypair");
        try {
            this.keyPair = NetworkEncryptionUtils.generateServerKeyPair();
        }
        catch (NetworkEncryptionException networkEncryptionException) {
            throw new IllegalStateException("Failed to generate key pair", networkEncryptionException);
        }
    }

    public void setDifficulty(Difficulty difficulty, boolean forceUpdate) {
        if (!forceUpdate && this.saveProperties.isDifficultyLocked()) {
            return;
        }
        this.saveProperties.setDifficulty(this.saveProperties.isHardcore() ? Difficulty.HARD : difficulty);
        this.updateMobSpawnOptions();
        this.getPlayerManager().getPlayerList().forEach(this::sendDifficulty);
    }

    public int adjustTrackingDistance(int initialDistance) {
        return initialDistance;
    }

    private void updateMobSpawnOptions() {
        for (ServerWorld serverWorld : this.getWorlds()) {
            serverWorld.setMobSpawnOptions(this.isMonsterSpawningEnabled(), this.shouldSpawnAnimals());
        }
    }

    public void setDifficultyLocked(boolean locked) {
        this.saveProperties.setDifficultyLocked(locked);
        this.getPlayerManager().getPlayerList().forEach(this::sendDifficulty);
    }

    private void sendDifficulty(ServerPlayerEntity player) {
        WorldProperties worldProperties = player.getServerWorld().getLevelProperties();
        player.networkHandler.sendPacket(new DifficultyS2CPacket(worldProperties.getDifficulty(), worldProperties.isDifficultyLocked()));
    }

    protected boolean isMonsterSpawningEnabled() {
        return this.saveProperties.getDifficulty() != Difficulty.PEACEFUL;
    }

    public boolean isDemo() {
        return this.demo;
    }

    public void setDemo(boolean demo) {
        this.demo = demo;
    }

    public String getResourcePackUrl() {
        return this.resourcePackUrl;
    }

    public String getResourcePackHash() {
        return this.resourcePackHash;
    }

    public void setResourcePack(String url, String hash) {
        this.resourcePackUrl = url;
        this.resourcePackHash = hash;
    }

    @Override
    public void addSnooperInfo(Snooper snooper) {
        snooper.addInfo("whitelist_enabled", false);
        snooper.addInfo("whitelist_count", 0);
        if (this.playerManager != null) {
            snooper.addInfo("players_current", this.getCurrentPlayerCount());
            snooper.addInfo("players_max", this.getMaxPlayerCount());
            snooper.addInfo("players_seen", this.saveHandler.getSavedPlayerIds().length);
        }
        snooper.addInfo("uses_auth", this.onlineMode);
        snooper.addInfo("gui_state", this.hasGui() ? "enabled" : "disabled");
        snooper.addInfo("run_time", (Util.getMeasuringTimeMs() - snooper.getStartTime()) / 60L * 1000L);
        snooper.addInfo("avg_tick_ms", (int)(MathHelper.average(this.lastTickLengths) * 1.0E-6));
        int n = 0;
        for (ServerWorld serverWorld : this.getWorlds()) {
            if (serverWorld == null) continue;
            snooper.addInfo("world[" + n + "][dimension]", serverWorld.getRegistryKey().getValue());
            snooper.addInfo("world[" + n + "][mode]", (Object)this.saveProperties.getGameMode());
            snooper.addInfo("world[" + n + "][difficulty]", (Object)serverWorld.getDifficulty());
            snooper.addInfo("world[" + n + "][hardcore]", this.saveProperties.isHardcore());
            snooper.addInfo("world[" + n + "][height]", this.worldHeight);
            snooper.addInfo("world[" + n + "][chunks_loaded]", serverWorld.getChunkManager().getLoadedChunkCount());
            ++n;
        }
        snooper.addInfo("worlds", n);
    }

    public abstract boolean isDedicated();

    public abstract int getRateLimit();

    public boolean isOnlineMode() {
        return this.onlineMode;
    }

    public void setOnlineMode(boolean onlineMode) {
        this.onlineMode = onlineMode;
    }

    public boolean shouldPreventProxyConnections() {
        return this.preventProxyConnections;
    }

    public void setPreventProxyConnections(boolean preventProxyConnections) {
        this.preventProxyConnections = preventProxyConnections;
    }

    public boolean shouldSpawnAnimals() {
        return true;
    }

    public boolean shouldSpawnNpcs() {
        return true;
    }

    public abstract boolean isUsingNativeTransport();

    public boolean isPvpEnabled() {
        return this.pvpEnabled;
    }

    public void setPvpEnabled(boolean pvpEnabled) {
        this.pvpEnabled = pvpEnabled;
    }

    public boolean isFlightEnabled() {
        return this.flightEnabled;
    }

    public void setFlightEnabled(boolean flightEnabled) {
        this.flightEnabled = flightEnabled;
    }

    public abstract boolean areCommandBlocksEnabled();

    public String getServerMotd() {
        return this.motd;
    }

    public void setMotd(String motd) {
        this.motd = motd;
    }

    public int getWorldHeight() {
        return this.worldHeight;
    }

    public void setWorldHeight(int worldHeight) {
        this.worldHeight = worldHeight;
    }

    public boolean isStopped() {
        return this.stopped;
    }

    public PlayerManager getPlayerManager() {
        return this.playerManager;
    }

    public void setPlayerManager(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    public abstract boolean isRemote();

    public void setDefaultGameMode(GameMode gameMode) {
        this.saveProperties.setGameMode(gameMode);
    }

    @Nullable
    public ServerNetworkIo getNetworkIo() {
        return this.networkIo;
    }

    public boolean isLoading() {
        return this.loading;
    }

    public boolean hasGui() {
        return false;
    }

    public abstract boolean openToLan(GameMode var1, boolean var2, int var3);

    public int getTicks() {
        return this.ticks;
    }

    public Snooper getSnooper() {
        return this.snooper;
    }

    public int getSpawnProtectionRadius() {
        return 16;
    }

    public boolean isSpawnProtected(ServerWorld world, BlockPos pos, PlayerEntity player) {
        return false;
    }

    public void setForceGameMode(boolean forceGameMode) {
        this.forceGameMode = forceGameMode;
    }

    public boolean shouldForceGameMode() {
        return this.forceGameMode;
    }

    public boolean acceptsStatusQuery() {
        return true;
    }

    public int getPlayerIdleTimeout() {
        return this.playerIdleTimeout;
    }

    public void setPlayerIdleTimeout(int playerIdleTimeout) {
        this.playerIdleTimeout = playerIdleTimeout;
    }

    public MinecraftSessionService getSessionService() {
        return this.sessionService;
    }

    public GameProfileRepository getGameProfileRepo() {
        return this.gameProfileRepo;
    }

    public UserCache getUserCache() {
        return this.userCache;
    }

    public ServerMetadata getServerMetadata() {
        return this.metadata;
    }

    public void forcePlayerSampleUpdate() {
        this.lastPlayerSampleUpdate = 0L;
    }

    public int getMaxWorldBorderRadius() {
        return 29999984;
    }

    @Override
    public boolean shouldExecuteAsync() {
        return super.shouldExecuteAsync() && !this.isStopped();
    }

    @Override
    public Thread getThread() {
        return this.serverThread;
    }

    public int getNetworkCompressionThreshold() {
        return 256;
    }

    public long getServerStartTime() {
        return this.timeReference;
    }

    public DataFixer getDataFixer() {
        return this.dataFixer;
    }

    public int getSpawnRadius(@Nullable ServerWorld world) {
        if (world != null) {
            return world.getGameRules().getInt(GameRules.SPAWN_RADIUS);
        }
        return 10;
    }

    public ServerAdvancementLoader getAdvancementLoader() {
        return this.serverResourceManager.getServerAdvancementLoader();
    }

    public CommandFunctionManager getCommandFunctionManager() {
        return this.commandFunctionManager;
    }

    public CompletableFuture<Void> reloadResources(Collection<String> datapacks) {
        CompletionStage completionStage = ((CompletableFuture)CompletableFuture.supplyAsync(() -> (ImmutableList)datapacks.stream().map(this.dataPackManager::getProfile).filter(Objects::nonNull).map(ResourcePackProfile::createResourcePack).collect(ImmutableList.toImmutableList()), this).thenCompose(immutableList -> ServerResourceManager.reload((List<ResourcePack>)immutableList, this.isDedicated() ? CommandManager.RegistrationEnvironment.DEDICATED : CommandManager.RegistrationEnvironment.INTEGRATED, this.getFunctionPermissionLevel(), this.workerExecutor, this))).thenAcceptAsync(serverResourceManager -> {
            this.serverResourceManager.close();
            this.serverResourceManager = serverResourceManager;
            this.dataPackManager.setEnabledProfiles(datapacks);
            this.saveProperties.updateLevelInfo(MinecraftServer.method_29735(this.dataPackManager));
            serverResourceManager.loadRegistryTags();
            this.getPlayerManager().saveAllPlayerData();
            this.getPlayerManager().onDataPacksReloaded();
            this.commandFunctionManager.method_29461(this.serverResourceManager.getFunctionLoader());
            this.structureManager.method_29300(this.serverResourceManager.getResourceManager());
        }, (Executor)this);
        if (this.isOnThread()) {
            this.runTasks(((CompletableFuture)completionStage)::isDone);
        }
        return completionStage;
    }

    public static DataPackSettings loadDataPacks(ResourcePackManager resourcePackManager, DataPackSettings dataPackSettings, boolean safeMode) {
        resourcePackManager.scanPacks();
        if (safeMode) {
            resourcePackManager.setEnabledProfiles(Collections.singleton("vanilla"));
            return new DataPackSettings((List<String>)ImmutableList.of((Object)"vanilla"), (List<String>)ImmutableList.of());
        }
        LinkedHashSet linkedHashSet = Sets.newLinkedHashSet();
        for (String string : dataPackSettings.getEnabled()) {
            if (resourcePackManager.hasProfile(string)) {
                linkedHashSet.add(string);
                continue;
            }
            LOGGER.warn("Missing data pack {}", (Object)string);
        }
        for (ResourcePackProfile resourcePackProfile : resourcePackManager.getProfiles()) {
            String string = resourcePackProfile.getName();
            if (dataPackSettings.getDisabled().contains(string) || linkedHashSet.contains(string)) continue;
            LOGGER.info("Found new data pack {}, loading it automatically", (Object)string);
            linkedHashSet.add(string);
        }
        if (linkedHashSet.isEmpty()) {
            LOGGER.info("No datapacks selected, forcing vanilla");
            linkedHashSet.add("vanilla");
        }
        resourcePackManager.setEnabledProfiles(linkedHashSet);
        return MinecraftServer.method_29735(resourcePackManager);
    }

    private static DataPackSettings method_29735(ResourcePackManager resourcePackManager) {
        Collection<String> collection = resourcePackManager.getEnabledNames();
        ImmutableList _snowman2 = ImmutableList.copyOf(collection);
        List _snowman3 = (List)resourcePackManager.getNames().stream().filter(string -> !collection.contains(string)).collect(ImmutableList.toImmutableList());
        return new DataPackSettings((List<String>)_snowman2, _snowman3);
    }

    public void kickNonWhitelistedPlayers(ServerCommandSource source) {
        if (!this.isEnforceWhitelist()) {
            return;
        }
        PlayerManager playerManager = source.getMinecraftServer().getPlayerManager();
        Whitelist _snowman2 = playerManager.getWhitelist();
        ArrayList _snowman3 = Lists.newArrayList(playerManager.getPlayerList());
        for (ServerPlayerEntity serverPlayerEntity : _snowman3) {
            if (_snowman2.isAllowed(serverPlayerEntity.getGameProfile())) continue;
            serverPlayerEntity.networkHandler.disconnect(new TranslatableText("multiplayer.disconnect.not_whitelisted"));
        }
    }

    public ResourcePackManager getDataPackManager() {
        return this.dataPackManager;
    }

    public CommandManager getCommandManager() {
        return this.serverResourceManager.getCommandManager();
    }

    public ServerCommandSource getCommandSource() {
        ServerWorld serverWorld = this.getOverworld();
        return new ServerCommandSource(this, serverWorld == null ? Vec3d.ZERO : Vec3d.of(serverWorld.getSpawnPos()), Vec2f.ZERO, serverWorld, 4, "Server", new LiteralText("Server"), this, null);
    }

    @Override
    public boolean shouldReceiveFeedback() {
        return true;
    }

    @Override
    public boolean shouldTrackOutput() {
        return true;
    }

    public RecipeManager getRecipeManager() {
        return this.serverResourceManager.getRecipeManager();
    }

    public TagManager getTagManager() {
        return this.serverResourceManager.getRegistryTagManager();
    }

    public ServerScoreboard getScoreboard() {
        return this.scoreboard;
    }

    public DataCommandStorage getDataCommandStorage() {
        if (this.dataCommandStorage == null) {
            throw new NullPointerException("Called before server init");
        }
        return this.dataCommandStorage;
    }

    public LootManager getLootManager() {
        return this.serverResourceManager.getLootManager();
    }

    public LootConditionManager getPredicateManager() {
        return this.serverResourceManager.getLootConditionManager();
    }

    public GameRules getGameRules() {
        return this.getOverworld().getGameRules();
    }

    public BossBarManager getBossBarManager() {
        return this.bossBarManager;
    }

    public boolean isEnforceWhitelist() {
        return this.enforceWhitelist;
    }

    public void setEnforceWhitelist(boolean whitelistEnabled) {
        this.enforceWhitelist = whitelistEnabled;
    }

    public float getTickTime() {
        return this.tickTime;
    }

    public int getPermissionLevel(GameProfile profile) {
        if (this.getPlayerManager().isOperator(profile)) {
            OperatorEntry operatorEntry = (OperatorEntry)this.getPlayerManager().getOpList().get(profile);
            if (operatorEntry != null) {
                return operatorEntry.getPermissionLevel();
            }
            if (this.isHost(profile)) {
                return 4;
            }
            if (this.isSinglePlayer()) {
                return this.getPlayerManager().areCheatsAllowed() ? 4 : 0;
            }
            return this.getOpPermissionLevel();
        }
        return 0;
    }

    public MetricsData getMetricsData() {
        return this.metricsData;
    }

    public Profiler getProfiler() {
        return this.profiler;
    }

    public abstract boolean isHost(GameProfile var1);

    public void dump(Path path) throws IOException {
        Path path2 = path.resolve("levels");
        for (Map.Entry<RegistryKey<World>, ServerWorld> entry : this.worlds.entrySet()) {
            Identifier identifier = entry.getKey().getValue();
            Path _snowman2 = path2.resolve(identifier.getNamespace()).resolve(identifier.getPath());
            Files.createDirectories(_snowman2, new FileAttribute[0]);
            entry.getValue().dump(_snowman2);
        }
        this.dumpGamerules(path.resolve("gamerules.txt"));
        this.dumpClasspath(path.resolve("classpath.txt"));
        this.dumpExampleCrash(path.resolve("example_crash.txt"));
        this.dumpStats(path.resolve("stats.txt"));
        this.dumpThreads(path.resolve("threads.txt"));
    }

    private void dumpStats(Path path) throws IOException {
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, new OpenOption[0]);){
            bufferedWriter.write(String.format("pending_tasks: %d\n", this.getTaskCount()));
            bufferedWriter.write(String.format("average_tick_time: %f\n", Float.valueOf(this.getTickTime())));
            bufferedWriter.write(String.format("tick_times: %s\n", Arrays.toString(this.lastTickLengths)));
            bufferedWriter.write(String.format("queue: %s\n", Util.getMainWorkerExecutor()));
        }
    }

    private void dumpExampleCrash(Path path) throws IOException {
        CrashReport crashReport = new CrashReport("Server dump", new Exception("dummy"));
        this.populateCrashReport(crashReport);
        try (BufferedWriter _snowman2 = Files.newBufferedWriter(path, new OpenOption[0]);){
            _snowman2.write(crashReport.asString());
        }
    }

    private void dumpGamerules(Path path) throws IOException {
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, new OpenOption[0]);){
            final ArrayList arrayList = Lists.newArrayList();
            final GameRules _snowman2 = this.getGameRules();
            GameRules.accept(new GameRules.Visitor(){

                @Override
                public <T extends GameRules.Rule<T>> void visit(GameRules.Key<T> key, GameRules.Type<T> type) {
                    arrayList.add(String.format("%s=%s\n", key.getName(), ((GameRules.Rule)_snowman2.get(key)).toString()));
                }
            });
            for (String string : arrayList) {
                bufferedWriter.write(string);
            }
        }
    }

    private void dumpClasspath(Path path) throws IOException {
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, new OpenOption[0]);){
            String string = System.getProperty("java.class.path");
            _snowman = System.getProperty("path.separator");
            for (String string2 : Splitter.on((String)_snowman).split((CharSequence)string)) {
                bufferedWriter.write(string2);
                bufferedWriter.write("\n");
            }
        }
    }

    private void dumpThreads(Path path) throws IOException {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] _snowman2 = threadMXBean.dumpAllThreads(true, true);
        Arrays.sort(_snowman2, Comparator.comparing(ThreadInfo::getThreadName));
        try (BufferedWriter _snowman3 = Files.newBufferedWriter(path, new OpenOption[0]);){
            for (ThreadInfo threadInfo : _snowman2) {
                _snowman3.write(threadInfo.toString());
                ((Writer)_snowman3).write(10);
            }
        }
    }

    private void startMonitor(@Nullable TickDurationMonitor monitor) {
        if (this.profilerStartQueued) {
            this.profilerStartQueued = false;
            this.tickTimeTracker.enable();
        }
        this.profiler = TickDurationMonitor.tickProfiler(this.tickTimeTracker.getProfiler(), monitor);
    }

    private void endMonitor(@Nullable TickDurationMonitor monitor) {
        if (monitor != null) {
            monitor.endTick();
        }
        this.profiler = this.tickTimeTracker.getProfiler();
    }

    public boolean isDebugRunning() {
        return this.tickTimeTracker.isActive();
    }

    public void enableProfiler() {
        this.profilerStartQueued = true;
    }

    public ProfileResult stopDebug() {
        ProfileResult profileResult = this.tickTimeTracker.getResult();
        this.tickTimeTracker.disable();
        return profileResult;
    }

    public Path getSavePath(WorldSavePath worldSavePath) {
        return this.session.getDirectory(worldSavePath);
    }

    public boolean syncChunkWrites() {
        return true;
    }

    public StructureManager getStructureManager() {
        return this.structureManager;
    }

    public SaveProperties getSaveProperties() {
        return this.saveProperties;
    }

    public DynamicRegistryManager getRegistryManager() {
        return this.registryManager;
    }

    @Nullable
    public TextStream createFilterer(ServerPlayerEntity player) {
        return null;
    }

    @Override
    public /* synthetic */ void executeTask(Runnable task) {
        this.executeTask((ServerTask)task);
    }

    @Override
    public /* synthetic */ boolean canExecute(Runnable task) {
        return this.canExecute((ServerTask)task);
    }

    @Override
    public /* synthetic */ Runnable createTask(Runnable runnable) {
        return this.createTask(runnable);
    }
}

