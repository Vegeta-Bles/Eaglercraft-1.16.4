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
import java.io.File;
import java.io.IOException;
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
import java.nio.file.Path;
import java.security.KeyPair;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
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
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.resource.ServerResourceManager;
import net.minecraft.scoreboard.ScoreboardState;
import net.minecraft.scoreboard.ScoreboardSynchronizer;
import net.minecraft.scoreboard.ServerScoreboard;
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

public abstract class MinecraftServer extends ReentrantThreadExecutor<ServerTask> implements SnooperListener, CommandOutput, AutoCloseable {
   private static final Logger LOGGER = LogManager.getLogger();
   public static final File USER_CACHE_FILE = new File("usercache.json");
   public static final LevelInfo DEMO_LEVEL_INFO = new LevelInfo(
      "Demo World", GameMode.SURVIVAL, false, Difficulty.NORMAL, false, new GameRules(), DataPackSettings.SAFE_MODE
   );
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
      AtomicReference<S> _snowman = new AtomicReference<>();
      Thread _snowmanx = new Thread(() -> _snowman.get().runServer(), "Server thread");
      _snowmanx.setUncaughtExceptionHandler((_snowmanxx, _snowmanxxx) -> LOGGER.error(_snowmanxxx));
      S _snowmanxx = (S)serverFactory.apply(_snowmanx);
      _snowman.set(_snowmanxx);
      _snowmanx.start();
      return _snowmanxx;
   }

   public MinecraftServer(
      Thread _snowman,
      DynamicRegistryManager.Impl _snowman,
      LevelStorage.Session _snowman,
      SaveProperties _snowman,
      ResourcePackManager _snowman,
      Proxy _snowman,
      DataFixer _snowman,
      ServerResourceManager _snowman,
      MinecraftSessionService _snowman,
      GameProfileRepository _snowman,
      UserCache _snowman,
      WorldGenerationProgressListenerFactory _snowman
   ) {
      super("Server");
      this.registryManager = _snowman;
      this.saveProperties = _snowman;
      this.proxy = _snowman;
      this.dataPackManager = _snowman;
      this.serverResourceManager = _snowman;
      this.sessionService = _snowman;
      this.gameProfileRepo = _snowman;
      this.userCache = _snowman;
      this.networkIo = new ServerNetworkIo(this);
      this.worldGenerationProgressListenerFactory = _snowman;
      this.session = _snowman;
      this.saveHandler = _snowman.createSaveHandler();
      this.dataFixer = _snowman;
      this.commandFunctionManager = new CommandFunctionManager(this, _snowman.getFunctionLoader());
      this.structureManager = new StructureManager(_snowman.getResourceManager(), _snowman, _snowman);
      this.serverThread = _snowman;
      this.workerExecutor = Util.getMainWorkerExecutor();
   }

   private void initScoreboard(PersistentStateManager persistentStateManager) {
      ScoreboardState _snowman = persistentStateManager.getOrCreate(ScoreboardState::new, "scoreboard");
      _snowman.setScoreboard(this.getScoreboard());
      this.getScoreboard().addUpdateListener(new ScoreboardSynchronizer(_snowman));
   }

   protected abstract boolean setupServer() throws IOException;

   public static void convertLevel(LevelStorage.Session _snowman) {
      if (_snowman.needsConversion()) {
         LOGGER.info("Converting map!");
         _snowman.convert(new ProgressListener() {
            private long lastProgressUpdate = Util.getMeasuringTimeMs();

            @Override
            public void method_15412(Text _snowman) {
            }

            @Override
            public void method_15413(Text _snowman) {
            }

            @Override
            public void progressStagePercentage(int _snowman) {
               if (Util.getMeasuringTimeMs() - this.lastProgressUpdate >= 1000L) {
                  this.lastProgressUpdate = Util.getMeasuringTimeMs();
                  MinecraftServer.LOGGER.info("Converting... {}%", _snowman);
               }
            }

            @Override
            public void setDone() {
            }

            @Override
            public void method_15414(Text _snowman) {
            }
         });
      }
   }

   protected void loadWorld() {
      this.loadWorldResourcePack();
      this.saveProperties.addServerBrand(this.getServerModName(), this.getModdedStatusMessage().isPresent());
      WorldGenerationProgressListener _snowman = this.worldGenerationProgressListenerFactory.create(11);
      this.createWorlds(_snowman);
      this.method_27731();
      this.prepareStartRegion(_snowman);
   }

   protected void method_27731() {
   }

   protected void createWorlds(WorldGenerationProgressListener _snowman) {
      ServerWorldProperties _snowmanx = this.saveProperties.getMainWorldProperties();
      GeneratorOptions _snowmanxx = this.saveProperties.getGeneratorOptions();
      boolean _snowmanxxx = _snowmanxx.isDebugWorld();
      long _snowmanxxxx = _snowmanxx.getSeed();
      long _snowmanxxxxx = BiomeAccess.hashSeed(_snowmanxxxx);
      List<Spawner> _snowmanxxxxxx = ImmutableList.of(
         new PhantomSpawner(), new PillagerSpawner(), new CatSpawner(), new ZombieSiegeManager(), new WanderingTraderManager(_snowmanx)
      );
      SimpleRegistry<DimensionOptions> _snowmanxxxxxxx = _snowmanxx.getDimensions();
      DimensionOptions _snowmanxxxxxxxx = _snowmanxxxxxxx.get(DimensionOptions.OVERWORLD);
      ChunkGenerator _snowmanxxxxxxxxx;
      DimensionType _snowmanxxxxxxxxxx;
      if (_snowmanxxxxxxxx == null) {
         _snowmanxxxxxxxxxx = this.registryManager.getDimensionTypes().getOrThrow(DimensionType.OVERWORLD_REGISTRY_KEY);
         _snowmanxxxxxxxxx = GeneratorOptions.createOverworldGenerator(
            this.registryManager.get(Registry.BIOME_KEY), this.registryManager.get(Registry.NOISE_SETTINGS_WORLDGEN), new Random().nextLong()
         );
      } else {
         _snowmanxxxxxxxxxx = _snowmanxxxxxxxx.getDimensionType();
         _snowmanxxxxxxxxx = _snowmanxxxxxxxx.getChunkGenerator();
      }

      ServerWorld _snowmanxxxxxxxxxxx = new ServerWorld(
         this, this.workerExecutor, this.session, _snowmanx, World.OVERWORLD, _snowmanxxxxxxxxxx, _snowman, _snowmanxxxxxxxxx, _snowmanxxx, _snowmanxxxxx, _snowmanxxxxxx, true
      );
      this.worlds.put(World.OVERWORLD, _snowmanxxxxxxxxxxx);
      PersistentStateManager _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.getPersistentStateManager();
      this.initScoreboard(_snowmanxxxxxxxxxxxx);
      this.dataCommandStorage = new DataCommandStorage(_snowmanxxxxxxxxxxxx);
      WorldBorder _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.getWorldBorder();
      _snowmanxxxxxxxxxxxxx.load(_snowmanx.getWorldBorder());
      if (!_snowmanx.isInitialized()) {
         try {
            setupSpawn(_snowmanxxxxxxxxxxx, _snowmanx, _snowmanxx.hasBonusChest(), _snowmanxxx, true);
            _snowmanx.setInitialized(true);
            if (_snowmanxxx) {
               this.setToDebugWorldProperties(this.saveProperties);
            }
         } catch (Throwable var26) {
            CrashReport _snowmanxxxxxxxxxxxxxx = CrashReport.create(var26, "Exception initializing level");

            try {
               _snowmanxxxxxxxxxxx.addDetailsToCrashReport(_snowmanxxxxxxxxxxxxxx);
            } catch (Throwable var25) {
            }

            throw new CrashException(_snowmanxxxxxxxxxxxxxx);
         }

         _snowmanx.setInitialized(true);
      }

      this.getPlayerManager().setMainWorld(_snowmanxxxxxxxxxxx);
      if (this.saveProperties.getCustomBossEvents() != null) {
         this.getBossBarManager().fromTag(this.saveProperties.getCustomBossEvents());
      }

      for (Entry<RegistryKey<DimensionOptions>, DimensionOptions> _snowmanxxxxxxxxxxxxxx : _snowmanxxxxxxx.getEntries()) {
         RegistryKey<DimensionOptions> _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx.getKey();
         if (_snowmanxxxxxxxxxxxxxxx != DimensionOptions.OVERWORLD) {
            RegistryKey<World> _snowmanxxxxxxxxxxxxxxxx = RegistryKey.of(Registry.DIMENSION, _snowmanxxxxxxxxxxxxxxx.getValue());
            DimensionType _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx.getValue().getDimensionType();
            ChunkGenerator _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx.getValue().getChunkGenerator();
            UnmodifiableLevelProperties _snowmanxxxxxxxxxxxxxxxxxxx = new UnmodifiableLevelProperties(this.saveProperties, _snowmanx);
            ServerWorld _snowmanxxxxxxxxxxxxxxxxxxxx = new ServerWorld(
               this,
               this.workerExecutor,
               this.session,
               _snowmanxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxx,
               _snowman,
               _snowmanxxxxxxxxxxxxxxxxxx,
               _snowmanxxx,
               _snowmanxxxxx,
               ImmutableList.of(),
               false
            );
            _snowmanxxxxxxxxxxxxx.addListener(new WorldBorderListener.WorldBorderSyncer(_snowmanxxxxxxxxxxxxxxxxxxxx.getWorldBorder()));
            this.worlds.put(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxx);
         }
      }
   }

   private static void setupSpawn(ServerWorld world, ServerWorldProperties _snowman, boolean bonusChest, boolean debugWorld, boolean _snowman) {
      ChunkGenerator _snowmanxx = world.getChunkManager().getChunkGenerator();
      if (!_snowman) {
         _snowman.setSpawnPos(BlockPos.ORIGIN.up(_snowmanxx.getSpawnHeight()), 0.0F);
      } else if (debugWorld) {
         _snowman.setSpawnPos(BlockPos.ORIGIN.up(), 0.0F);
      } else {
         BiomeSource _snowmanxxx = _snowmanxx.getBiomeSource();
         Random _snowmanxxxx = new Random(world.getSeed());
         BlockPos _snowmanxxxxx = _snowmanxxx.locateBiome(0, world.getSeaLevel(), 0, 256, _snowmanxxxxxx -> _snowmanxxxxxx.getSpawnSettings().isPlayerSpawnFriendly(), _snowmanxxxx);
         ChunkPos _snowmanxxxxxx = _snowmanxxxxx == null ? new ChunkPos(0, 0) : new ChunkPos(_snowmanxxxxx);
         if (_snowmanxxxxx == null) {
            LOGGER.warn("Unable to find spawn biome");
         }

         boolean _snowmanxxxxxxx = false;

         for (Block _snowmanxxxxxxxx : BlockTags.VALID_SPAWN.values()) {
            if (_snowmanxxx.getTopMaterials().contains(_snowmanxxxxxxxx.getDefaultState())) {
               _snowmanxxxxxxx = true;
               break;
            }
         }

         _snowman.setSpawnPos(_snowmanxxxxxx.getStartPos().add(8, _snowmanxx.getSpawnHeight(), 8), 0.0F);
         int _snowmanxxxxxxxxx = 0;
         int _snowmanxxxxxxxxxx = 0;
         int _snowmanxxxxxxxxxxx = 0;
         int _snowmanxxxxxxxxxxxx = -1;
         int _snowmanxxxxxxxxxxxxx = 32;

         for (int _snowmanxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxx < 1024; _snowmanxxxxxxxxxxxxxx++) {
            if (_snowmanxxxxxxxxx > -16 && _snowmanxxxxxxxxx <= 16 && _snowmanxxxxxxxxxx > -16 && _snowmanxxxxxxxxxx <= 16) {
               BlockPos _snowmanxxxxxxxxxxxxxxx = SpawnLocating.findServerSpawnPoint(world, new ChunkPos(_snowmanxxxxxx.x + _snowmanxxxxxxxxx, _snowmanxxxxxx.z + _snowmanxxxxxxxxxx), _snowmanxxxxxxx);
               if (_snowmanxxxxxxxxxxxxxxx != null) {
                  _snowman.setSpawnPos(_snowmanxxxxxxxxxxxxxxx, 0.0F);
                  break;
               }
            }

            if (_snowmanxxxxxxxxx == _snowmanxxxxxxxxxx || _snowmanxxxxxxxxx < 0 && _snowmanxxxxxxxxx == -_snowmanxxxxxxxxxx || _snowmanxxxxxxxxx > 0 && _snowmanxxxxxxxxx == 1 - _snowmanxxxxxxxxxx) {
               int _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx;
               _snowmanxxxxxxxxxxx = -_snowmanxxxxxxxxxxxx;
               _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx;
            }

            _snowmanxxxxxxxxx += _snowmanxxxxxxxxxxx;
            _snowmanxxxxxxxxxx += _snowmanxxxxxxxxxxxx;
         }

         if (bonusChest) {
            ConfiguredFeature<?, ?> _snowmanxxxxxxxxxxxxxx = ConfiguredFeatures.BONUS_CHEST;
            _snowmanxxxxxxxxxxxxxx.generate(world, _snowmanxx, world.random, new BlockPos(_snowman.getSpawnX(), _snowman.getSpawnY(), _snowman.getSpawnZ()));
         }
      }
   }

   private void setToDebugWorldProperties(SaveProperties properties) {
      properties.setDifficulty(Difficulty.PEACEFUL);
      properties.setDifficultyLocked(true);
      ServerWorldProperties _snowman = properties.getMainWorldProperties();
      _snowman.setRaining(false);
      _snowman.setThundering(false);
      _snowman.setClearWeatherTime(1000000000);
      _snowman.setTimeOfDay(6000L);
      _snowman.setGameMode(GameMode.SPECTATOR);
   }

   private void prepareStartRegion(WorldGenerationProgressListener _snowman) {
      ServerWorld _snowmanx = this.getOverworld();
      LOGGER.info("Preparing start region for dimension {}", _snowmanx.getRegistryKey().getValue());
      BlockPos _snowmanxx = _snowmanx.getSpawnPos();
      _snowman.start(new ChunkPos(_snowmanxx));
      ServerChunkManager _snowmanxxx = _snowmanx.getChunkManager();
      _snowmanxxx.getLightingProvider().setTaskBatchSize(500);
      this.timeReference = Util.getMeasuringTimeMs();
      _snowmanxxx.addTicket(ChunkTicketType.START, new ChunkPos(_snowmanxx), 11, Unit.INSTANCE);

      while (_snowmanxxx.getTotalChunksLoadedCount() != 441) {
         this.timeReference = Util.getMeasuringTimeMs() + 10L;
         this.method_16208();
      }

      this.timeReference = Util.getMeasuringTimeMs() + 10L;
      this.method_16208();

      for (ServerWorld _snowmanxxxx : this.worlds.values()) {
         ForcedChunkState _snowmanxxxxx = _snowmanxxxx.getPersistentStateManager().get(ForcedChunkState::new, "chunks");
         if (_snowmanxxxxx != null) {
            LongIterator _snowmanxxxxxx = _snowmanxxxxx.getChunks().iterator();

            while (_snowmanxxxxxx.hasNext()) {
               long _snowmanxxxxxxx = _snowmanxxxxxx.nextLong();
               ChunkPos _snowmanxxxxxxxx = new ChunkPos(_snowmanxxxxxxx);
               _snowmanxxxx.getChunkManager().setChunkForced(_snowmanxxxxxxxx, true);
            }
         }
      }

      this.timeReference = Util.getMeasuringTimeMs() + 10L;
      this.method_16208();
      _snowman.stop();
      _snowmanxxx.getLightingProvider().setTaskBatchSize(5);
      this.updateMobSpawnOptions();
   }

   protected void loadWorldResourcePack() {
      File _snowman = this.session.getDirectory(WorldSavePath.RESOURCES_ZIP).toFile();
      if (_snowman.isFile()) {
         String _snowmanx = this.session.getDirectoryName();

         try {
            this.setResourcePack("level://" + URLEncoder.encode(_snowmanx, StandardCharsets.UTF_8.toString()) + "/" + "resources.zip", "");
         } catch (UnsupportedEncodingException var4) {
            LOGGER.warn("Something went wrong url encoding {}", _snowmanx);
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

   public boolean save(boolean suppressLogs, boolean _snowman, boolean _snowman) {
      boolean _snowmanxx = false;

      for (ServerWorld _snowmanxxx : this.getWorlds()) {
         if (!suppressLogs) {
            LOGGER.info("Saving chunks for level '{}'/{}", _snowmanxxx, _snowmanxxx.getRegistryKey().getValue());
         }

         _snowmanxxx.save(null, _snowman, _snowmanxxx.savingDisabled && !_snowman);
         _snowmanxx = true;
      }

      ServerWorld _snowmanxxx = this.getOverworld();
      ServerWorldProperties _snowmanxxxx = this.saveProperties.getMainWorldProperties();
      _snowmanxxxx.setWorldBorder(_snowmanxxx.getWorldBorder().write());
      this.saveProperties.setCustomBossEvents(this.getBossBarManager().toTag());
      this.session.backupLevelDataFile(this.registryManager, this.saveProperties, this.getPlayerManager().getUserData());
      return _snowmanxx;
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

      for (ServerWorld _snowman : this.getWorlds()) {
         if (_snowman != null) {
            _snowman.savingDisabled = false;
         }
      }

      this.save(false, true, false);

      for (ServerWorld _snowmanx : this.getWorlds()) {
         if (_snowmanx != null) {
            try {
               _snowmanx.close();
            } catch (IOException var5) {
               LOGGER.error("Exception closing the level", var5);
            }
         }
      }

      if (this.snooper.isActive()) {
         this.snooper.cancel();
      }

      this.serverResourceManager.close();

      try {
         this.session.close();
      } catch (IOException var4) {
         LOGGER.error("Failed to unlock level {}", this.session.getDirectoryName(), var4);
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

   public void stop(boolean _snowman) {
      this.running = false;
      if (_snowman) {
         try {
            this.serverThread.join();
         } catch (InterruptedException var3) {
            LOGGER.error("Error while shutting down", var3);
         }
      }
   }

   protected void runServer() {
      try {
         if (this.setupServer()) {
            this.timeReference = Util.getMeasuringTimeMs();
            this.metadata.setDescription(new LiteralText(this.motd));
            this.metadata
               .setVersion(new ServerMetadata.Version(SharedConstants.getGameVersion().getName(), SharedConstants.getGameVersion().getProtocolVersion()));
            this.setFavicon(this.metadata);

            while (this.running) {
               long _snowman = Util.getMeasuringTimeMs() - this.timeReference;
               if (_snowman > 2000L && this.timeReference - this.lastTimeReference >= 15000L) {
                  long _snowmanx = _snowman / 50L;
                  LOGGER.warn("Can't keep up! Is the server overloaded? Running {}ms or {} ticks behind", _snowman, _snowmanx);
                  this.timeReference += _snowmanx * 50L;
                  this.lastTimeReference = this.timeReference;
               }

               this.timeReference += 50L;
               TickDurationMonitor _snowmanx = TickDurationMonitor.create("Server");
               this.startMonitor(_snowmanx);
               this.profiler.startTick();
               this.profiler.push("tick");
               this.tick(this::shouldKeepTicking);
               this.profiler.swap("nextTickWait");
               this.waitingForNextTick = true;
               this.field_19248 = Math.max(Util.getMeasuringTimeMs() + 50L, this.timeReference);
               this.method_16208();
               this.profiler.pop();
               this.profiler.endTick();
               this.endMonitor(_snowmanx);
               this.loading = true;
            }
         } else {
            this.setCrashReport(null);
         }
      } catch (Throwable var44) {
         LOGGER.error("Encountered an unexpected exception", var44);
         CrashReport _snowman;
         if (var44 instanceof CrashException) {
            _snowman = this.populateCrashReport(((CrashException)var44).getReport());
         } else {
            _snowman = this.populateCrashReport(new CrashReport("Exception in server tick loop", var44));
         }

         File _snowmanx = new File(
            new File(this.getRunDirectory(), "crash-reports"), "crash-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + "-server.txt"
         );
         if (_snowman.writeToFile(_snowmanx)) {
            LOGGER.error("This crash report has been saved to: {}", _snowmanx.getAbsolutePath());
         } else {
            LOGGER.error("We were unable to save this crash report to disk.");
         }

         this.setCrashReport(_snowman);
      } finally {
         try {
            this.stopped = true;
            this.shutdown();
         } catch (Throwable var42) {
            LOGGER.error("Exception stopping the server", var42);
         } finally {
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

   protected ServerTask createTask(Runnable _snowman) {
      return new ServerTask(this.ticks, _snowman);
   }

   protected boolean canExecute(ServerTask _snowman) {
      return _snowman.getCreationTicks() + 3 < this.ticks || this.shouldKeepTicking();
   }

   @Override
   public boolean runTask() {
      boolean _snowman = this.method_20415();
      this.waitingForNextTick = _snowman;
      return _snowman;
   }

   private boolean method_20415() {
      if (super.runTask()) {
         return true;
      } else {
         if (this.shouldKeepTicking()) {
            for (ServerWorld _snowman : this.getWorlds()) {
               if (_snowman.getChunkManager().executeQueuedTasks()) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   protected void executeTask(ServerTask _snowman) {
      this.getProfiler().visit("runTask");
      super.executeTask(_snowman);
   }

   private void setFavicon(ServerMetadata metadata) {
      File _snowman = this.getFile("server-icon.png");
      if (!_snowman.exists()) {
         _snowman = this.session.getIconFile();
      }

      if (_snowman.isFile()) {
         ByteBuf _snowmanx = Unpooled.buffer();

         try {
            BufferedImage _snowmanxx = ImageIO.read(_snowman);
            Validate.validState(_snowmanxx.getWidth() == 64, "Must be 64 pixels wide", new Object[0]);
            Validate.validState(_snowmanxx.getHeight() == 64, "Must be 64 pixels high", new Object[0]);
            ImageIO.write(_snowmanxx, "PNG", new ByteBufOutputStream(_snowmanx));
            ByteBuffer _snowmanxxx = Base64.getEncoder().encode(_snowmanx.nioBuffer());
            metadata.setFavicon("data:image/png;base64," + StandardCharsets.UTF_8.decode(_snowmanxxx));
         } catch (Exception var9) {
            LOGGER.error("Couldn't load server icon", var9);
         } finally {
            _snowmanx.release();
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
      long _snowman = Util.getMeasuringTimeNano();
      this.ticks++;
      this.tickWorlds(shouldKeepTicking);
      if (_snowman - this.lastPlayerSampleUpdate >= 5000000000L) {
         this.lastPlayerSampleUpdate = _snowman;
         this.metadata.setPlayers(new ServerMetadata.Players(this.getMaxPlayerCount(), this.getCurrentPlayerCount()));
         GameProfile[] _snowmanx = new GameProfile[Math.min(this.getCurrentPlayerCount(), 12)];
         int _snowmanxx = MathHelper.nextInt(this.random, 0, this.getCurrentPlayerCount() - _snowmanx.length);

         for (int _snowmanxxx = 0; _snowmanxxx < _snowmanx.length; _snowmanxxx++) {
            _snowmanx[_snowmanxxx] = this.playerManager.getPlayerList().get(_snowmanxx + _snowmanxxx).getGameProfile();
         }

         Collections.shuffle(Arrays.asList(_snowmanx));
         this.metadata.getPlayers().setSample(_snowmanx);
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
      long _snowmanx = this.lastTickLengths[this.ticks % 100] = Util.getMeasuringTimeNano() - _snowman;
      this.tickTime = this.tickTime * 0.8F + (float)_snowmanx / 1000000.0F * 0.19999999F;
      long _snowmanxx = Util.getMeasuringTimeNano();
      this.metricsData.pushSample(_snowmanxx - _snowman);
      this.profiler.pop();
   }

   protected void tickWorlds(BooleanSupplier shouldKeepTicking) {
      this.profiler.push("commandFunctions");
      this.getCommandFunctionManager().tick();
      this.profiler.swap("levels");

      for (ServerWorld _snowman : this.getWorlds()) {
         this.profiler.push(() -> _snowman + " " + _snowman.getRegistryKey().getValue());
         if (this.ticks % 20 == 0) {
            this.profiler.push("timeSync");
            this.playerManager
               .sendToDimension(
                  new WorldTimeUpdateS2CPacket(_snowman.getTime(), _snowman.getTimeOfDay(), _snowman.getGameRules().getBoolean(GameRules.DO_DAYLIGHT_CYCLE)), _snowman.getRegistryKey()
               );
            this.profiler.pop();
         }

         this.profiler.push("tick");

         try {
            _snowman.tick(shouldKeepTicking);
         } catch (Throwable var6) {
            CrashReport _snowmanx = CrashReport.create(var6, "Exception ticking world");
            _snowman.addDetailsToCrashReport(_snowmanx);
            throw new CrashException(_snowmanx);
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

      for (int _snowman = 0; _snowman < this.serverGuiTickables.size(); _snowman++) {
         this.serverGuiTickables.get(_snowman).run();
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
         report.getSystemDetailsSection()
            .add(
               "Player Count",
               () -> this.playerManager.getCurrentPlayerCount() + " / " + this.playerManager.getMaxPlayerCount() + "; " + this.playerManager.getPlayerList()
            );
      }

      report.getSystemDetailsSection().add("Data Packs", () -> {
         StringBuilder _snowman = new StringBuilder();

         for (ResourcePackProfile _snowmanx : this.dataPackManager.getEnabledProfiles()) {
            if (_snowman.length() > 0) {
               _snowman.append(", ");
            }

            _snowman.append(_snowmanx.getName());
            if (!_snowmanx.getCompatibility().isCompatible()) {
               _snowman.append(" (incompatible)");
            }
         }

         return _snowman.toString();
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
      } catch (NetworkEncryptionException var2) {
         throw new IllegalStateException("Failed to generate key pair", var2);
      }
   }

   public void setDifficulty(Difficulty _snowman, boolean forceUpdate) {
      if (forceUpdate || !this.saveProperties.isDifficultyLocked()) {
         this.saveProperties.setDifficulty(this.saveProperties.isHardcore() ? Difficulty.HARD : _snowman);
         this.updateMobSpawnOptions();
         this.getPlayerManager().getPlayerList().forEach(this::sendDifficulty);
      }
   }

   public int adjustTrackingDistance(int initialDistance) {
      return initialDistance;
   }

   private void updateMobSpawnOptions() {
      for (ServerWorld _snowman : this.getWorlds()) {
         _snowman.setMobSpawnOptions(this.isMonsterSpawningEnabled(), this.shouldSpawnAnimals());
      }
   }

   public void setDifficultyLocked(boolean locked) {
      this.saveProperties.setDifficultyLocked(locked);
      this.getPlayerManager().getPlayerList().forEach(this::sendDifficulty);
   }

   private void sendDifficulty(ServerPlayerEntity player) {
      WorldProperties _snowman = player.getServerWorld().getLevelProperties();
      player.networkHandler.sendPacket(new DifficultyS2CPacket(_snowman.getDifficulty(), _snowman.isDifficultyLocked()));
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
      int _snowman = 0;

      for (ServerWorld _snowmanx : this.getWorlds()) {
         if (_snowmanx != null) {
            snooper.addInfo("world[" + _snowman + "][dimension]", _snowmanx.getRegistryKey().getValue());
            snooper.addInfo("world[" + _snowman + "][mode]", this.saveProperties.getGameMode());
            snooper.addInfo("world[" + _snowman + "][difficulty]", _snowmanx.getDifficulty());
            snooper.addInfo("world[" + _snowman + "][hardcore]", this.saveProperties.isHardcore());
            snooper.addInfo("world[" + _snowman + "][height]", this.worldHeight);
            snooper.addInfo("world[" + _snowman + "][chunks_loaded]", _snowmanx.getChunkManager().getLoadedChunkCount());
            _snowman++;
         }
      }

      snooper.addInfo("worlds", _snowman);
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

   public abstract boolean openToLan(GameMode gameMode, boolean cheatsAllowed, int port);

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
      return world != null ? world.getGameRules().getInt(GameRules.SPAWN_RADIUS) : 10;
   }

   public ServerAdvancementLoader getAdvancementLoader() {
      return this.serverResourceManager.getServerAdvancementLoader();
   }

   public CommandFunctionManager getCommandFunctionManager() {
      return this.commandFunctionManager;
   }

   public CompletableFuture<Void> reloadResources(Collection<String> datapacks) {
      CompletableFuture<Void> _snowman = CompletableFuture.<ImmutableList>supplyAsync(
            () -> datapacks.stream()
                  .map(this.dataPackManager::getProfile)
                  .filter(Objects::nonNull)
                  .map(ResourcePackProfile::createResourcePack)
                  .collect(ImmutableList.toImmutableList()),
            this
         )
         .thenCompose(
            _snowmanx -> ServerResourceManager.reload(
                  _snowmanx,
                  this.isDedicated() ? CommandManager.RegistrationEnvironment.DEDICATED : CommandManager.RegistrationEnvironment.INTEGRATED,
                  this.getFunctionPermissionLevel(),
                  this.workerExecutor,
                  this
               )
         )
         .thenAcceptAsync(_snowmanx -> {
            this.serverResourceManager.close();
            this.serverResourceManager = _snowmanx;
            this.dataPackManager.setEnabledProfiles(datapacks);
            this.saveProperties.updateLevelInfo(method_29735(this.dataPackManager));
            _snowmanx.loadRegistryTags();
            this.getPlayerManager().saveAllPlayerData();
            this.getPlayerManager().onDataPacksReloaded();
            this.commandFunctionManager.method_29461(this.serverResourceManager.getFunctionLoader());
            this.structureManager.method_29300(this.serverResourceManager.getResourceManager());
         }, this);
      if (this.isOnThread()) {
         this.runTasks(_snowman::isDone);
      }

      return _snowman;
   }

   public static DataPackSettings loadDataPacks(ResourcePackManager resourcePackManager, DataPackSettings _snowman, boolean safeMode) {
      resourcePackManager.scanPacks();
      if (safeMode) {
         resourcePackManager.setEnabledProfiles(Collections.singleton("vanilla"));
         return new DataPackSettings(ImmutableList.of("vanilla"), ImmutableList.of());
      } else {
         Set<String> _snowmanx = Sets.newLinkedHashSet();

         for (String _snowmanxx : _snowman.getEnabled()) {
            if (resourcePackManager.hasProfile(_snowmanxx)) {
               _snowmanx.add(_snowmanxx);
            } else {
               LOGGER.warn("Missing data pack {}", _snowmanxx);
            }
         }

         for (ResourcePackProfile _snowmanxxx : resourcePackManager.getProfiles()) {
            String _snowmanxxxx = _snowmanxxx.getName();
            if (!_snowman.getDisabled().contains(_snowmanxxxx) && !_snowmanx.contains(_snowmanxxxx)) {
               LOGGER.info("Found new data pack {}, loading it automatically", _snowmanxxxx);
               _snowmanx.add(_snowmanxxxx);
            }
         }

         if (_snowmanx.isEmpty()) {
            LOGGER.info("No datapacks selected, forcing vanilla");
            _snowmanx.add("vanilla");
         }

         resourcePackManager.setEnabledProfiles(_snowmanx);
         return method_29735(resourcePackManager);
      }
   }

   private static DataPackSettings method_29735(ResourcePackManager _snowman) {
      Collection<String> _snowmanx = _snowman.getEnabledNames();
      List<String> _snowmanxx = ImmutableList.copyOf(_snowmanx);
      List<String> _snowmanxxx = _snowman.getNames().stream().filter(_snowmanxxxx -> !_snowman.contains(_snowmanxxxx)).collect(ImmutableList.toImmutableList());
      return new DataPackSettings(_snowmanxx, _snowmanxxx);
   }

   public void kickNonWhitelistedPlayers(ServerCommandSource source) {
      if (this.isEnforceWhitelist()) {
         PlayerManager _snowman = source.getMinecraftServer().getPlayerManager();
         Whitelist _snowmanx = _snowman.getWhitelist();

         for (ServerPlayerEntity _snowmanxx : Lists.newArrayList(_snowman.getPlayerList())) {
            if (!_snowmanx.isAllowed(_snowmanxx.getGameProfile())) {
               _snowmanxx.networkHandler.disconnect(new TranslatableText("multiplayer.disconnect.not_whitelisted"));
            }
         }
      }
   }

   public ResourcePackManager getDataPackManager() {
      return this.dataPackManager;
   }

   public CommandManager getCommandManager() {
      return this.serverResourceManager.getCommandManager();
   }

   public ServerCommandSource getCommandSource() {
      ServerWorld _snowman = this.getOverworld();
      return new ServerCommandSource(
         this, _snowman == null ? Vec3d.ZERO : Vec3d.of(_snowman.getSpawnPos()), Vec2f.ZERO, _snowman, 4, "Server", new LiteralText("Server"), this, null
      );
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
      } else {
         return this.dataCommandStorage;
      }
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
         OperatorEntry _snowman = this.getPlayerManager().getOpList().get(profile);
         if (_snowman != null) {
            return _snowman.getPermissionLevel();
         } else if (this.isHost(profile)) {
            return 4;
         } else if (this.isSinglePlayer()) {
            return this.getPlayerManager().areCheatsAllowed() ? 4 : 0;
         } else {
            return this.getOpPermissionLevel();
         }
      } else {
         return 0;
      }
   }

   public MetricsData getMetricsData() {
      return this.metricsData;
   }

   public Profiler getProfiler() {
      return this.profiler;
   }

   public abstract boolean isHost(GameProfile profile);

   public void dump(Path path) throws IOException {
      Path _snowman = path.resolve("levels");

      for (Entry<RegistryKey<World>, ServerWorld> _snowmanx : this.worlds.entrySet()) {
         Identifier _snowmanxx = _snowmanx.getKey().getValue();
         Path _snowmanxxx = _snowman.resolve(_snowmanxx.getNamespace()).resolve(_snowmanxx.getPath());
         Files.createDirectories(_snowmanxxx);
         _snowmanx.getValue().dump(_snowmanxxx);
      }

      this.dumpGamerules(path.resolve("gamerules.txt"));
      this.dumpClasspath(path.resolve("classpath.txt"));
      this.dumpExampleCrash(path.resolve("example_crash.txt"));
      this.dumpStats(path.resolve("stats.txt"));
      this.dumpThreads(path.resolve("threads.txt"));
   }

   private void dumpStats(Path _snowman) throws IOException {
      try (Writer _snowmanx = Files.newBufferedWriter(_snowman)) {
         _snowmanx.write(String.format("pending_tasks: %d\n", this.getTaskCount()));
         _snowmanx.write(String.format("average_tick_time: %f\n", this.getTickTime()));
         _snowmanx.write(String.format("tick_times: %s\n", Arrays.toString(this.lastTickLengths)));
         _snowmanx.write(String.format("queue: %s\n", Util.getMainWorkerExecutor()));
      }
   }

   private void dumpExampleCrash(Path _snowman) throws IOException {
      CrashReport _snowmanx = new CrashReport("Server dump", new Exception("dummy"));
      this.populateCrashReport(_snowmanx);

      try (Writer _snowmanxx = Files.newBufferedWriter(_snowman)) {
         _snowmanxx.write(_snowmanx.asString());
      }
   }

   private void dumpGamerules(Path _snowman) throws IOException {
      try (Writer _snowmanx = Files.newBufferedWriter(_snowman)) {
         final List<String> _snowmanxx = Lists.newArrayList();
         final GameRules _snowmanxxx = this.getGameRules();
         GameRules.accept(new GameRules.Visitor() {
            @Override
            public <T extends GameRules.Rule<T>> void visit(GameRules.Key<T> key, GameRules.Type<T> type) {
               _snowman.add(String.format("%s=%s\n", key.getName(), _snowman.<T>get(key).toString()));
            }
         });

         for (String _snowmanxxxx : _snowmanxx) {
            _snowmanx.write(_snowmanxxxx);
         }
      }
   }

   private void dumpClasspath(Path _snowman) throws IOException {
      try (Writer _snowmanx = Files.newBufferedWriter(_snowman)) {
         String _snowmanxx = System.getProperty("java.class.path");
         String _snowmanxxx = System.getProperty("path.separator");

         for (String _snowmanxxxx : Splitter.on(_snowmanxxx).split(_snowmanxx)) {
            _snowmanx.write(_snowmanxxxx);
            _snowmanx.write("\n");
         }
      }
   }

   private void dumpThreads(Path _snowman) throws IOException {
      ThreadMXBean _snowmanx = ManagementFactory.getThreadMXBean();
      ThreadInfo[] _snowmanxx = _snowmanx.dumpAllThreads(true, true);
      Arrays.sort(_snowmanxx, Comparator.comparing(ThreadInfo::getThreadName));

      try (Writer _snowmanxxx = Files.newBufferedWriter(_snowman)) {
         for (ThreadInfo _snowmanxxxx : _snowmanxx) {
            _snowmanxxx.write(_snowmanxxxx.toString());
            _snowmanxxx.write(10);
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
      ProfileResult _snowman = this.tickTimeTracker.getResult();
      this.tickTimeTracker.disable();
      return _snowman;
   }

   public Path getSavePath(WorldSavePath _snowman) {
      return this.session.getDirectory(_snowman);
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
}
