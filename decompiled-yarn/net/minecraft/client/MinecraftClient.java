package net.minecraft.client;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Queues;
import com.google.common.collect.UnmodifiableIterator;
import com.google.gson.JsonElement;
import com.mojang.authlib.AuthenticationService;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.minecraft.OfflineSocialInteractions;
import com.mojang.authlib.minecraft.SocialInteractionsService;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.blaze3d.platform.GlDebugInfo;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.util.Function4;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.Lifecycle;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.Proxy;
import java.net.SocketAddress;
import java.nio.ByteOrder;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.Bootstrap;
import net.minecraft.SharedConstants;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.SkullBlockEntity;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.font.FontManager;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gui.WorldGenerationProgressTracker;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.screen.BackupPromptScreen;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.gui.screen.CreditsScreen;
import net.minecraft.client.gui.screen.DatapackFailureScreen;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.LevelLoadingScreen;
import net.minecraft.client.gui.screen.OutOfMemoryScreen;
import net.minecraft.client.gui.screen.Overlay;
import net.minecraft.client.gui.screen.ProgressScreen;
import net.minecraft.client.gui.screen.SaveLevelScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.screen.SleepingChatScreen;
import net.minecraft.client.gui.screen.SplashScreen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.advancement.AdvancementsScreen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.multiplayer.SocialInteractionsScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeResultCollection;
import net.minecraft.client.gui.screen.world.EditWorldScreen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.network.ClientLoginNetworkHandler;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.network.SocialInteractionsManager;
import net.minecraft.client.options.AoMode;
import net.minecraft.client.options.ChatVisibility;
import net.minecraft.client.options.CloudRenderMode;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.GraphicsMode;
import net.minecraft.client.options.HotbarStorage;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.options.Option;
import net.minecraft.client.options.Perspective;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferBuilderStorage;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.BlockModels;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.debug.DebugRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.resource.ClientBuiltinResourcePackProvider;
import net.minecraft.client.resource.FoliageColormapResourceSupplier;
import net.minecraft.client.resource.Format3ResourcePack;
import net.minecraft.client.resource.Format4ResourcePack;
import net.minecraft.client.resource.GrassColormapResourceSupplier;
import net.minecraft.client.resource.SplashTextResourceSupplier;
import net.minecraft.client.resource.VideoWarningManager;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.resource.language.LanguageManager;
import net.minecraft.client.search.IdentifierSearchableContainer;
import net.minecraft.client.search.SearchManager;
import net.minecraft.client.search.SearchableContainer;
import net.minecraft.client.search.TextSearchableContainer;
import net.minecraft.client.sound.MusicTracker;
import net.minecraft.client.sound.MusicType;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.texture.PaintingManager;
import net.minecraft.client.texture.PlayerSkinProvider;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.StatusEffectSpriteManager;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.client.toast.TutorialToast;
import net.minecraft.client.tutorial.TutorialManager;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.client.util.Session;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.WindowProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.datafixer.Schemas;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.decoration.LeashKnotEntity;
import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SkullItem;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.NetworkState;
import net.minecraft.network.packet.c2s.handshake.HandshakeC2SPacket;
import net.minecraft.network.packet.c2s.login.LoginHelloC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.resource.DataPackSettings;
import net.minecraft.resource.FileResourcePackProvider;
import net.minecraft.resource.ReloadableResourceManager;
import net.minecraft.resource.ReloadableResourceManagerImpl;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.resource.ResourcePackSource;
import net.minecraft.resource.ResourceType;
import net.minecraft.resource.ServerResourceManager;
import net.minecraft.resource.VanillaDataPackProvider;
import net.minecraft.resource.metadata.PackResourceMetadata;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.QueueingWorldGenerationProgressListener;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.sound.MusicSound;
import net.minecraft.tag.ItemTags;
import net.minecraft.text.KeybindText;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.MetricsData;
import net.minecraft.util.TickDurationMonitor;
import net.minecraft.util.Unit;
import net.minecraft.util.UserCache;
import net.minecraft.util.Util;
import net.minecraft.util.WorldSavePath;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.util.dynamic.RegistryOps;
import net.minecraft.util.dynamic.RegistryReadingOps;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.profiler.DummyProfiler;
import net.minecraft.util.profiler.ProfileResult;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.profiler.ProfilerTiming;
import net.minecraft.util.profiler.TickTimeTracker;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.snooper.Snooper;
import net.minecraft.util.snooper.SnooperListener;
import net.minecraft.util.thread.ReentrantThreadExecutor;
import net.minecraft.world.SaveProperties;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GeneratorOptions;
import net.minecraft.world.level.LevelInfo;
import net.minecraft.world.level.LevelProperties;
import net.minecraft.world.level.storage.LevelStorage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MinecraftClient extends ReentrantThreadExecutor<Runnable> implements SnooperListener, WindowEventHandler {
   private static MinecraftClient instance;
   private static final Logger LOGGER = LogManager.getLogger();
   public static final boolean IS_SYSTEM_MAC = Util.getOperatingSystem() == Util.OperatingSystem.OSX;
   public static final Identifier DEFAULT_FONT_ID = new Identifier("default");
   public static final Identifier UNICODE_FONT_ID = new Identifier("uniform");
   public static final Identifier ALT_TEXT_RENDERER_ID = new Identifier("alt");
   private static final CompletableFuture<Unit> COMPLETED_UNIT_FUTURE = CompletableFuture.completedFuture(Unit.INSTANCE);
   private static final Text field_26841 = new TranslatableText("multiplayer.socialInteractions.not_available");
   private final File resourcePackDir;
   private final PropertyMap sessionPropertyMap;
   private final TextureManager textureManager;
   private final DataFixer dataFixer;
   private final WindowProvider windowProvider;
   private final Window window;
   private final RenderTickCounter renderTickCounter = new RenderTickCounter(20.0F, 0L);
   private final Snooper snooper = new Snooper("client", this, Util.getMeasuringTimeMs());
   private final BufferBuilderStorage bufferBuilders;
   public final WorldRenderer worldRenderer;
   private final EntityRenderDispatcher entityRenderDispatcher;
   private final ItemRenderer itemRenderer;
   private final HeldItemRenderer heldItemRenderer;
   public final ParticleManager particleManager;
   private final SearchManager searchManager = new SearchManager();
   private final Session session;
   public final TextRenderer textRenderer;
   public final GameRenderer gameRenderer;
   public final DebugRenderer debugRenderer;
   private final AtomicReference<WorldGenerationProgressTracker> worldGenProgressTracker = new AtomicReference<>();
   public final InGameHud inGameHud;
   public final GameOptions options;
   private final HotbarStorage creativeHotbarStorage;
   public final Mouse mouse;
   public final Keyboard keyboard;
   public final File runDirectory;
   private final String gameVersion;
   private final String versionType;
   private final Proxy netProxy;
   private final LevelStorage levelStorage;
   public final MetricsData metricsData = new MetricsData();
   private final boolean is64Bit;
   private final boolean isDemo;
   private final boolean multiplayerEnabled;
   private final boolean onlineChatEnabled;
   private final ReloadableResourceManager resourceManager;
   private final ClientBuiltinResourcePackProvider builtinPackProvider;
   private final ResourcePackManager resourcePackManager;
   private final LanguageManager languageManager;
   private final BlockColors blockColors;
   private final ItemColors itemColors;
   private final Framebuffer framebuffer;
   private final SoundManager soundManager;
   private final MusicTracker musicTracker;
   private final FontManager fontManager;
   private final SplashTextResourceSupplier splashTextLoader;
   private final VideoWarningManager videoWarningManager;
   private final MinecraftSessionService sessionService;
   private final SocialInteractionsService field_26902;
   private final PlayerSkinProvider skinProvider;
   private final BakedModelManager bakedModelManager;
   private final BlockRenderManager blockRenderManager;
   private final PaintingManager paintingManager;
   private final StatusEffectSpriteManager statusEffectSpriteManager;
   private final ToastManager toastManager;
   private final MinecraftClientGame game = new MinecraftClientGame(this);
   private final TutorialManager tutorialManager;
   private final SocialInteractionsManager socialInteractionsManager;
   public static byte[] memoryReservedForCrash = new byte[10485760];
   @Nullable
   public ClientPlayerInteractionManager interactionManager;
   @Nullable
   public ClientWorld world;
   @Nullable
   public ClientPlayerEntity player;
   @Nullable
   private IntegratedServer server;
   @Nullable
   private ServerInfo currentServerEntry;
   @Nullable
   private ClientConnection connection;
   private boolean integratedServerRunning;
   @Nullable
   public Entity cameraEntity;
   @Nullable
   public Entity targetedEntity;
   @Nullable
   public HitResult crosshairTarget;
   private int itemUseCooldown;
   protected int attackCooldown;
   private boolean paused;
   private float pausedTickDelta;
   private long lastMetricsSampleTime = Util.getMeasuringTimeNano();
   private long nextDebugInfoUpdateTime;
   private int fpsCounter;
   public boolean skipGameRender;
   @Nullable
   public Screen currentScreen;
   @Nullable
   public Overlay overlay;
   private boolean connectedToRealms;
   private Thread thread;
   private volatile boolean running = true;
   @Nullable
   private CrashReport crashReport;
   private static int currentFps;
   public String fpsDebugString = "";
   public boolean debugChunkInfo;
   public boolean debugChunkOcclusion;
   public boolean chunkCullingEnabled = true;
   private boolean windowFocused;
   private final Queue<Runnable> renderTaskQueue = Queues.newConcurrentLinkedQueue();
   @Nullable
   private CompletableFuture<Void> resourceReloadFuture;
   @Nullable
   private TutorialToast field_26843;
   private Profiler profiler = DummyProfiler.INSTANCE;
   private int trackingTick;
   private final TickTimeTracker tickTimeTracker = new TickTimeTracker(Util.nanoTimeSupplier, () -> this.trackingTick);
   @Nullable
   private ProfileResult tickProfilerResult;
   private String openProfilerSection = "root";

   public MinecraftClient(RunArgs args) {
      super("Client");
      instance = this;
      this.runDirectory = args.directories.runDir;
      File _snowman = args.directories.assetDir;
      this.resourcePackDir = args.directories.resourcePackDir;
      this.gameVersion = args.game.version;
      this.versionType = args.game.versionType;
      this.sessionPropertyMap = args.network.profileProperties;
      this.builtinPackProvider = new ClientBuiltinResourcePackProvider(
         new File(this.runDirectory, "server-resource-packs"), args.directories.getResourceIndex()
      );
      this.resourcePackManager = new ResourcePackManager(
         MinecraftClient::createResourcePackProfile,
         this.builtinPackProvider,
         new FileResourcePackProvider(this.resourcePackDir, ResourcePackSource.field_25347)
      );
      this.netProxy = args.network.netProxy;
      YggdrasilAuthenticationService _snowmanx = new YggdrasilAuthenticationService(this.netProxy);
      this.sessionService = _snowmanx.createMinecraftSessionService();
      this.field_26902 = this.method_31382(_snowmanx, args);
      this.session = args.network.session;
      LOGGER.info("Setting user: {}", this.session.getUsername());
      LOGGER.debug("(Session ID is {})", this.session.getSessionId());
      this.isDemo = args.game.demo;
      this.multiplayerEnabled = !args.game.multiplayerDisabled;
      this.onlineChatEnabled = !args.game.onlineChatDisabled;
      this.is64Bit = checkIs64Bit();
      this.server = null;
      String _snowmanxx;
      int _snowmanxxx;
      if (this.isMultiplayerEnabled() && args.autoConnect.serverAddress != null) {
         _snowmanxx = args.autoConnect.serverAddress;
         _snowmanxxx = args.autoConnect.serverPort;
      } else {
         _snowmanxx = null;
         _snowmanxxx = 0;
      }

      KeybindText.setTranslator(KeyBinding::getLocalizedName);
      this.dataFixer = Schemas.getFixer();
      this.toastManager = new ToastManager(this);
      this.tutorialManager = new TutorialManager(this);
      this.thread = Thread.currentThread();
      this.options = new GameOptions(this, this.runDirectory);
      this.creativeHotbarStorage = new HotbarStorage(this.runDirectory, this.dataFixer);
      LOGGER.info("Backend library: {}", RenderSystem.getBackendDescription());
      WindowSettings _snowmanxxxx;
      if (this.options.overrideHeight > 0 && this.options.overrideWidth > 0) {
         _snowmanxxxx = new WindowSettings(
            this.options.overrideWidth,
            this.options.overrideHeight,
            args.windowSettings.fullscreenWidth,
            args.windowSettings.fullscreenHeight,
            args.windowSettings.fullscreen
         );
      } else {
         _snowmanxxxx = args.windowSettings;
      }

      Util.nanoTimeSupplier = RenderSystem.initBackendSystem();
      this.windowProvider = new WindowProvider(this);
      this.window = this.windowProvider.createWindow(_snowmanxxxx, this.options.fullscreenResolution, this.getWindowTitle());
      this.onWindowFocusChanged(true);

      try {
         InputStream _snowmanxxxxx = this.getResourcePackDownloader().getPack().open(ResourceType.CLIENT_RESOURCES, new Identifier("icons/icon_16x16.png"));
         InputStream _snowmanxxxxxx = this.getResourcePackDownloader().getPack().open(ResourceType.CLIENT_RESOURCES, new Identifier("icons/icon_32x32.png"));
         this.window.setIcon(_snowmanxxxxx, _snowmanxxxxxx);
      } catch (IOException var9) {
         LOGGER.error("Couldn't set icon", var9);
      }

      this.window.setFramerateLimit(this.options.maxFps);
      this.mouse = new Mouse(this);
      this.mouse.setup(this.window.getHandle());
      this.keyboard = new Keyboard(this);
      this.keyboard.setup(this.window.getHandle());
      RenderSystem.initRenderer(this.options.glDebugVerbosity, false);
      this.framebuffer = new Framebuffer(this.window.getFramebufferWidth(), this.window.getFramebufferHeight(), true, IS_SYSTEM_MAC);
      this.framebuffer.setClearColor(0.0F, 0.0F, 0.0F, 0.0F);
      this.resourceManager = new ReloadableResourceManagerImpl(ResourceType.CLIENT_RESOURCES);
      this.resourcePackManager.scanPacks();
      this.options.addResourcePackProfilesToManager(this.resourcePackManager);
      this.languageManager = new LanguageManager(this.options.language);
      this.resourceManager.registerListener(this.languageManager);
      this.textureManager = new TextureManager(this.resourceManager);
      this.resourceManager.registerListener(this.textureManager);
      this.skinProvider = new PlayerSkinProvider(this.textureManager, new File(_snowman, "skins"), this.sessionService);
      this.levelStorage = new LevelStorage(this.runDirectory.toPath().resolve("saves"), this.runDirectory.toPath().resolve("backups"), this.dataFixer);
      this.soundManager = new SoundManager(this.resourceManager, this.options);
      this.resourceManager.registerListener(this.soundManager);
      this.splashTextLoader = new SplashTextResourceSupplier(this.session);
      this.resourceManager.registerListener(this.splashTextLoader);
      this.musicTracker = new MusicTracker(this);
      this.fontManager = new FontManager(this.textureManager);
      this.textRenderer = this.fontManager.createTextRenderer();
      this.resourceManager.registerListener(this.fontManager.getResourceReloadListener());
      this.initFont(this.forcesUnicodeFont());
      this.resourceManager.registerListener(new GrassColormapResourceSupplier());
      this.resourceManager.registerListener(new FoliageColormapResourceSupplier());
      this.window.setPhase("Startup");
      RenderSystem.setupDefaultState(0, 0, this.window.getFramebufferWidth(), this.window.getFramebufferHeight());
      this.window.setPhase("Post startup");
      this.blockColors = BlockColors.create();
      this.itemColors = ItemColors.create(this.blockColors);
      this.bakedModelManager = new BakedModelManager(this.textureManager, this.blockColors, this.options.mipmapLevels);
      this.resourceManager.registerListener(this.bakedModelManager);
      this.itemRenderer = new ItemRenderer(this.textureManager, this.bakedModelManager, this.itemColors);
      this.entityRenderDispatcher = new EntityRenderDispatcher(this.textureManager, this.itemRenderer, this.resourceManager, this.textRenderer, this.options);
      this.heldItemRenderer = new HeldItemRenderer(this);
      this.resourceManager.registerListener(this.itemRenderer);
      this.bufferBuilders = new BufferBuilderStorage();
      this.gameRenderer = new GameRenderer(this, this.resourceManager, this.bufferBuilders);
      this.resourceManager.registerListener(this.gameRenderer);
      this.socialInteractionsManager = new SocialInteractionsManager(this, this.field_26902);
      this.blockRenderManager = new BlockRenderManager(this.bakedModelManager.getBlockModels(), this.blockColors);
      this.resourceManager.registerListener(this.blockRenderManager);
      this.worldRenderer = new WorldRenderer(this, this.bufferBuilders);
      this.resourceManager.registerListener(this.worldRenderer);
      this.initializeSearchableContainers();
      this.resourceManager.registerListener(this.searchManager);
      this.particleManager = new ParticleManager(this.world, this.textureManager);
      this.resourceManager.registerListener(this.particleManager);
      this.paintingManager = new PaintingManager(this.textureManager);
      this.resourceManager.registerListener(this.paintingManager);
      this.statusEffectSpriteManager = new StatusEffectSpriteManager(this.textureManager);
      this.resourceManager.registerListener(this.statusEffectSpriteManager);
      this.videoWarningManager = new VideoWarningManager();
      this.resourceManager.registerListener(this.videoWarningManager);
      this.inGameHud = new InGameHud(this);
      this.debugRenderer = new DebugRenderer(this);
      RenderSystem.setErrorCallback(this::handleGlErrorByDisableVsync);
      if (this.options.fullscreen && !this.window.isFullscreen()) {
         this.window.toggleFullscreen();
         this.options.fullscreen = this.window.isFullscreen();
      }

      this.window.setVsync(this.options.enableVsync);
      this.window.setRawMouseMotion(this.options.rawMouseInput);
      this.window.logOnGlError();
      this.onResolutionChanged();
      if (_snowmanxx != null) {
         this.openScreen(new ConnectScreen(new TitleScreen(), this, _snowmanxx, _snowmanxxx));
      } else {
         this.openScreen(new TitleScreen(true));
      }

      SplashScreen.init(this);
      List<ResourcePack> _snowmanxxxxx = this.resourcePackManager.createResourcePacks();
      this.setOverlay(
         new SplashScreen(
            this,
            this.resourceManager.beginMonitoredReload(Util.getMainWorkerExecutor(), this, COMPLETED_UNIT_FUTURE, _snowmanxxxxx),
            _snowmanxxxxxx -> Util.ifPresentOrElse(_snowmanxxxxxx, this::handleResourceReloadException, () -> {
                  if (SharedConstants.isDevelopment) {
                     this.checkGameData();
                  }
               }),
            false
         )
      );
   }

   public void updateWindowTitle() {
      this.window.setTitle(this.getWindowTitle());
   }

   private String getWindowTitle() {
      StringBuilder _snowman = new StringBuilder("Minecraft");
      if (this.isModded()) {
         _snowman.append("*");
      }

      _snowman.append(" ");
      _snowman.append(SharedConstants.getGameVersion().getName());
      ClientPlayNetworkHandler _snowmanx = this.getNetworkHandler();
      if (_snowmanx != null && _snowmanx.getConnection().isOpen()) {
         _snowman.append(" - ");
         if (this.server != null && !this.server.isRemote()) {
            _snowman.append(I18n.translate("title.singleplayer"));
         } else if (this.isConnectedToRealms()) {
            _snowman.append(I18n.translate("title.multiplayer.realms"));
         } else if (this.server == null && (this.currentServerEntry == null || !this.currentServerEntry.isLocal())) {
            _snowman.append(I18n.translate("title.multiplayer.other"));
         } else {
            _snowman.append(I18n.translate("title.multiplayer.lan"));
         }
      }

      return _snowman.toString();
   }

   private SocialInteractionsService method_31382(YggdrasilAuthenticationService _snowman, RunArgs _snowman) {
      try {
         return _snowman.createSocialInteractionsService(_snowman.network.session.getAccessToken());
      } catch (AuthenticationException var4) {
         LOGGER.error("Failed to verify authentication", var4);
         return new OfflineSocialInteractions();
      }
   }

   public boolean isModded() {
      return !"vanilla".equals(ClientBrandRetriever.getClientModName()) || MinecraftClient.class.getSigners() == null;
   }

   private void handleResourceReloadException(Throwable _snowman) {
      if (this.resourcePackManager.getEnabledNames().size() > 1) {
         Text _snowmanx;
         if (_snowman instanceof ReloadableResourceManagerImpl.PackAdditionFailedException) {
            _snowmanx = new LiteralText(((ReloadableResourceManagerImpl.PackAdditionFailedException)_snowman).getPack().getName());
         } else {
            _snowmanx = null;
         }

         this.method_31186(_snowman, _snowmanx);
      } else {
         Util.throwUnchecked(_snowman);
      }
   }

   public void method_31186(Throwable _snowman, @Nullable Text _snowman) {
      LOGGER.info("Caught error loading resourcepacks, removing all selected resourcepacks", _snowman);
      this.resourcePackManager.setEnabledProfiles(Collections.emptyList());
      this.options.resourcePacks.clear();
      this.options.incompatibleResourcePacks.clear();
      this.options.write();
      this.reloadResources().thenRun(() -> {
         ToastManager _snowmanx = this.getToastManager();
         SystemToast.show(_snowmanx, SystemToast.Type.PACK_LOAD_FAILURE, new TranslatableText("resourcePack.load_fail"), _snowman);
      });
   }

   public void run() {
      this.thread = Thread.currentThread();

      try {
         boolean _snowman = false;

         while (this.running) {
            if (this.crashReport != null) {
               printCrashReport(this.crashReport);
               return;
            }

            try {
               TickDurationMonitor _snowmanx = TickDurationMonitor.create("Renderer");
               boolean _snowmanxx = this.shouldMonitorTickDuration();
               this.startMonitor(_snowmanxx, _snowmanx);
               this.profiler.startTick();
               this.render(!_snowman);
               this.profiler.endTick();
               this.endMonitor(_snowmanxx, _snowmanx);
            } catch (OutOfMemoryError var4) {
               if (_snowman) {
                  throw var4;
               }

               this.cleanUpAfterCrash();
               this.openScreen(new OutOfMemoryScreen());
               System.gc();
               LOGGER.fatal("Out of memory", var4);
               _snowman = true;
            }
         }
      } catch (CrashException var5) {
         this.addDetailsToCrashReport(var5.getReport());
         this.cleanUpAfterCrash();
         LOGGER.fatal("Reported exception thrown!", var5);
         printCrashReport(var5.getReport());
      } catch (Throwable var6) {
         CrashReport _snowman = this.addDetailsToCrashReport(new CrashReport("Unexpected error", var6));
         LOGGER.fatal("Unreported exception thrown!", var6);
         this.cleanUpAfterCrash();
         printCrashReport(_snowman);
      }
   }

   void initFont(boolean forcesUnicode) {
      this.fontManager.setIdOverrides(forcesUnicode ? ImmutableMap.of(DEFAULT_FONT_ID, UNICODE_FONT_ID) : ImmutableMap.of());
   }

   private void initializeSearchableContainers() {
      TextSearchableContainer<ItemStack> _snowman = new TextSearchableContainer<>(
         _snowmanx -> _snowmanx.getTooltip(null, TooltipContext.Default.NORMAL).stream().map(_snowmanxx -> Formatting.strip(_snowmanxx.getString()).trim()).filter(_snowmanxx -> !_snowmanxx.isEmpty()),
         _snowmanx -> Stream.of(Registry.ITEM.getId(_snowmanx.getItem()))
      );
      IdentifierSearchableContainer<ItemStack> _snowmanx = new IdentifierSearchableContainer<>(_snowmanxx -> ItemTags.getTagGroup().getTagsFor(_snowmanxx.getItem()).stream());
      DefaultedList<ItemStack> _snowmanxx = DefaultedList.of();

      for (Item _snowmanxxx : Registry.ITEM) {
         _snowmanxxx.appendStacks(ItemGroup.SEARCH, _snowmanxx);
      }

      _snowmanxx.forEach(_snowmanxxx -> {
         _snowman.add(_snowmanxxx);
         _snowman.add(_snowmanxxx);
      });
      TextSearchableContainer<RecipeResultCollection> _snowmanxxx = new TextSearchableContainer<>(
         _snowmanxxxx -> _snowmanxxxx.getAllRecipes()
               .stream()
               .flatMap(_snowmanxxxxx -> _snowmanxxxxx.getOutput().getTooltip(null, TooltipContext.Default.NORMAL).stream())
               .map(_snowmanxxxxx -> Formatting.strip(_snowmanxxxxx.getString()).trim())
               .filter(_snowmanxxxxx -> !_snowmanxxxxx.isEmpty()),
         _snowmanxxxx -> _snowmanxxxx.getAllRecipes().stream().map(_snowmanxxxxx -> Registry.ITEM.getId(_snowmanxxxxx.getOutput().getItem()))
      );
      this.searchManager.put(SearchManager.ITEM_TOOLTIP, _snowman);
      this.searchManager.put(SearchManager.ITEM_TAG, _snowmanx);
      this.searchManager.put(SearchManager.RECIPE_OUTPUT, _snowmanxxx);
   }

   private void handleGlErrorByDisableVsync(int error, long description) {
      this.options.enableVsync = false;
      this.options.write();
   }

   private static boolean checkIs64Bit() {
      String[] _snowman = new String[]{"sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch"};

      for (String _snowmanx : _snowman) {
         String _snowmanxx = System.getProperty(_snowmanx);
         if (_snowmanxx != null && _snowmanxx.contains("64")) {
            return true;
         }
      }

      return false;
   }

   public Framebuffer getFramebuffer() {
      return this.framebuffer;
   }

   public String getGameVersion() {
      return this.gameVersion;
   }

   public String getVersionType() {
      return this.versionType;
   }

   public void setCrashReport(CrashReport report) {
      this.crashReport = report;
   }

   public static void printCrashReport(CrashReport report) {
      File _snowman = new File(getInstance().runDirectory, "crash-reports");
      File _snowmanx = new File(_snowman, "crash-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + "-client.txt");
      Bootstrap.println(report.asString());
      if (report.getFile() != null) {
         Bootstrap.println("#@!@# Game crashed! Crash report saved to: #@!@# " + report.getFile());
         System.exit(-1);
      } else if (report.writeToFile(_snowmanx)) {
         Bootstrap.println("#@!@# Game crashed! Crash report saved to: #@!@# " + _snowmanx.getAbsolutePath());
         System.exit(-1);
      } else {
         Bootstrap.println("#@?@# Game crashed! Crash report could not be saved. #@?@#");
         System.exit(-2);
      }
   }

   public boolean forcesUnicodeFont() {
      return this.options.forceUnicodeFont;
   }

   public CompletableFuture<Void> reloadResources() {
      if (this.resourceReloadFuture != null) {
         return this.resourceReloadFuture;
      } else {
         CompletableFuture<Void> _snowman = new CompletableFuture<>();
         if (this.overlay instanceof SplashScreen) {
            this.resourceReloadFuture = _snowman;
            return _snowman;
         } else {
            this.resourcePackManager.scanPacks();
            List<ResourcePack> _snowmanx = this.resourcePackManager.createResourcePacks();
            this.setOverlay(
               new SplashScreen(
                  this,
                  this.resourceManager.beginMonitoredReload(Util.getMainWorkerExecutor(), this, COMPLETED_UNIT_FUTURE, _snowmanx),
                  _snowmanxx -> Util.ifPresentOrElse(_snowmanxx, this::handleResourceReloadException, () -> {
                        this.worldRenderer.reload();
                        _snowman.complete(null);
                     }),
                  true
               )
            );
            return _snowman;
         }
      }
   }

   private void checkGameData() {
      boolean _snowman = false;
      BlockModels _snowmanx = this.getBlockRenderManager().getModels();
      BakedModel _snowmanxx = _snowmanx.getModelManager().getMissingModel();

      for (Block _snowmanxxx : Registry.BLOCK) {
         UnmodifiableIterator var6 = _snowmanxxx.getStateManager().getStates().iterator();

         while (var6.hasNext()) {
            BlockState _snowmanxxxx = (BlockState)var6.next();
            if (_snowmanxxxx.getRenderType() == BlockRenderType.MODEL) {
               BakedModel _snowmanxxxxx = _snowmanx.getModel(_snowmanxxxx);
               if (_snowmanxxxxx == _snowmanxx) {
                  LOGGER.debug("Missing model for: {}", _snowmanxxxx);
                  _snowman = true;
               }
            }
         }
      }

      Sprite _snowmanxxx = _snowmanxx.getSprite();

      for (Block _snowmanxxxx : Registry.BLOCK) {
         UnmodifiableIterator var18 = _snowmanxxxx.getStateManager().getStates().iterator();

         while (var18.hasNext()) {
            BlockState _snowmanxxxxx = (BlockState)var18.next();
            Sprite _snowmanxxxxxx = _snowmanx.getSprite(_snowmanxxxxx);
            if (!_snowmanxxxxx.isAir() && _snowmanxxxxxx == _snowmanxxx) {
               LOGGER.debug("Missing particle icon for: {}", _snowmanxxxxx);
               _snowman = true;
            }
         }
      }

      DefaultedList<ItemStack> _snowmanxxxx = DefaultedList.of();

      for (Item _snowmanxxxxx : Registry.ITEM) {
         _snowmanxxxx.clear();
         _snowmanxxxxx.appendStacks(ItemGroup.SEARCH, _snowmanxxxx);

         for (ItemStack _snowmanxxxxxx : _snowmanxxxx) {
            String _snowmanxxxxxxx = _snowmanxxxxxx.getTranslationKey();
            String _snowmanxxxxxxxx = new TranslatableText(_snowmanxxxxxxx).getString();
            if (_snowmanxxxxxxxx.toLowerCase(Locale.ROOT).equals(_snowmanxxxxx.getTranslationKey())) {
               LOGGER.debug("Missing translation for: {} {} {}", _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxx.getItem());
            }
         }
      }

      _snowman |= HandledScreens.validateScreens();
      if (_snowman) {
         throw new IllegalStateException("Your game data is foobar, fix the errors above!");
      }
   }

   public LevelStorage getLevelStorage() {
      return this.levelStorage;
   }

   private void openChatScreen(String text) {
      if (this.isInSingleplayer() || this.isOnlineChatEnabled()) {
         this.openScreen(new ChatScreen(text));
      } else if (this.player != null) {
         this.player.sendSystemMessage(new TranslatableText("chat.cannotSend").formatted(Formatting.RED), Util.NIL_UUID);
      }
   }

   public void openScreen(@Nullable Screen screen) {
      if (this.currentScreen != null) {
         this.currentScreen.removed();
      }

      if (screen == null && this.world == null) {
         screen = new TitleScreen();
      } else if (screen == null && this.player.isDead()) {
         if (this.player.showsDeathScreen()) {
            screen = new DeathScreen(null, this.world.getLevelProperties().isHardcore());
         } else {
            this.player.requestRespawn();
         }
      }

      if (screen instanceof TitleScreen || screen instanceof MultiplayerScreen) {
         this.options.debugEnabled = false;
         this.inGameHud.getChatHud().clear(true);
      }

      this.currentScreen = screen;
      if (screen != null) {
         this.mouse.unlockCursor();
         KeyBinding.unpressAll();
         screen.init(this, this.window.getScaledWidth(), this.window.getScaledHeight());
         this.skipGameRender = false;
         NarratorManager.INSTANCE.narrate(screen.getNarrationMessage());
      } else {
         this.soundManager.resumeAll();
         this.mouse.lockCursor();
      }

      this.updateWindowTitle();
   }

   public void setOverlay(@Nullable Overlay overlay) {
      this.overlay = overlay;
   }

   public void stop() {
      try {
         LOGGER.info("Stopping!");

         try {
            NarratorManager.INSTANCE.destroy();
         } catch (Throwable var7) {
         }

         try {
            if (this.world != null) {
               this.world.disconnect();
            }

            this.disconnect();
         } catch (Throwable var6) {
         }

         if (this.currentScreen != null) {
            this.currentScreen.removed();
         }

         this.close();
      } finally {
         Util.nanoTimeSupplier = System::nanoTime;
         if (this.crashReport == null) {
            System.exit(0);
         }
      }
   }

   @Override
   public void close() {
      try {
         this.bakedModelManager.close();
         this.fontManager.close();
         this.gameRenderer.close();
         this.worldRenderer.close();
         this.soundManager.close();
         this.resourcePackManager.close();
         this.particleManager.clearAtlas();
         this.statusEffectSpriteManager.close();
         this.paintingManager.close();
         this.textureManager.close();
         this.resourceManager.close();
         Util.shutdownExecutors();
      } catch (Throwable var5) {
         LOGGER.error("Shutdown failure!", var5);
         throw var5;
      } finally {
         this.windowProvider.close();
         this.window.close();
      }
   }

   private void render(boolean tick) {
      this.window.setPhase("Pre render");
      long _snowman = Util.getMeasuringTimeNano();
      if (this.window.shouldClose()) {
         this.scheduleStop();
      }

      if (this.resourceReloadFuture != null && !(this.overlay instanceof SplashScreen)) {
         CompletableFuture<Void> _snowmanx = this.resourceReloadFuture;
         this.resourceReloadFuture = null;
         this.reloadResources().thenRun(() -> _snowman.complete(null));
      }

      Runnable _snowmanx;
      while ((_snowmanx = this.renderTaskQueue.poll()) != null) {
         _snowmanx.run();
      }

      if (tick) {
         int _snowmanxx = this.renderTickCounter.beginRenderTick(Util.getMeasuringTimeMs());
         this.profiler.push("scheduledExecutables");
         this.runTasks();
         this.profiler.pop();
         this.profiler.push("tick");

         for (int _snowmanxxx = 0; _snowmanxxx < Math.min(10, _snowmanxx); _snowmanxxx++) {
            this.profiler.visit("clientTick");
            this.tick();
         }

         this.profiler.pop();
      }

      this.mouse.updateMouse();
      this.window.setPhase("Render");
      this.profiler.push("sound");
      this.soundManager.updateListenerPosition(this.gameRenderer.getCamera());
      this.profiler.pop();
      this.profiler.push("render");
      RenderSystem.pushMatrix();
      RenderSystem.clear(16640, IS_SYSTEM_MAC);
      this.framebuffer.beginWrite(true);
      BackgroundRenderer.method_23792();
      this.profiler.push("display");
      RenderSystem.enableTexture();
      RenderSystem.enableCull();
      this.profiler.pop();
      if (!this.skipGameRender) {
         this.profiler.swap("gameRenderer");
         this.gameRenderer.render(this.paused ? this.pausedTickDelta : this.renderTickCounter.tickDelta, _snowman, tick);
         this.profiler.swap("toasts");
         this.toastManager.draw(new MatrixStack());
         this.profiler.pop();
      }

      if (this.tickProfilerResult != null) {
         this.profiler.push("fpsPie");
         this.drawProfilerResults(new MatrixStack(), this.tickProfilerResult);
         this.profiler.pop();
      }

      this.profiler.push("blit");
      this.framebuffer.endWrite();
      RenderSystem.popMatrix();
      RenderSystem.pushMatrix();
      this.framebuffer.draw(this.window.getFramebufferWidth(), this.window.getFramebufferHeight());
      RenderSystem.popMatrix();
      this.profiler.swap("updateDisplay");
      this.window.swapBuffers();
      int _snowmanxx = this.getFramerateLimit();
      if ((double)_snowmanxx < Option.FRAMERATE_LIMIT.getMax()) {
         RenderSystem.limitDisplayFPS(_snowmanxx);
      }

      this.profiler.swap("yield");
      Thread.yield();
      this.profiler.pop();
      this.window.setPhase("Post render");
      this.fpsCounter++;
      boolean _snowmanxxx = this.isIntegratedServerRunning()
         && (this.currentScreen != null && this.currentScreen.isPauseScreen() || this.overlay != null && this.overlay.pausesGame())
         && !this.server.isRemote();
      if (this.paused != _snowmanxxx) {
         if (this.paused) {
            this.pausedTickDelta = this.renderTickCounter.tickDelta;
         } else {
            this.renderTickCounter.tickDelta = this.pausedTickDelta;
         }

         this.paused = _snowmanxxx;
      }

      long _snowmanxxxx = Util.getMeasuringTimeNano();
      this.metricsData.pushSample(_snowmanxxxx - this.lastMetricsSampleTime);
      this.lastMetricsSampleTime = _snowmanxxxx;
      this.profiler.push("fpsUpdate");

      while (Util.getMeasuringTimeMs() >= this.nextDebugInfoUpdateTime + 1000L) {
         currentFps = this.fpsCounter;
         this.fpsDebugString = String.format(
            "%d fps T: %s%s%s%s B: %d",
            currentFps,
            (double)this.options.maxFps == Option.FRAMERATE_LIMIT.getMax() ? "inf" : this.options.maxFps,
            this.options.enableVsync ? " vsync" : "",
            this.options.graphicsMode.toString(),
            this.options.cloudRenderMode == CloudRenderMode.OFF
               ? ""
               : (this.options.cloudRenderMode == CloudRenderMode.FAST ? " fast-clouds" : " fancy-clouds"),
            this.options.biomeBlendRadius
         );
         this.nextDebugInfoUpdateTime += 1000L;
         this.fpsCounter = 0;
         this.snooper.update();
         if (!this.snooper.isActive()) {
            this.snooper.method_5482();
         }
      }

      this.profiler.pop();
   }

   private boolean shouldMonitorTickDuration() {
      return this.options.debugEnabled && this.options.debugProfilerEnabled && !this.options.hudHidden;
   }

   private void startMonitor(boolean active, @Nullable TickDurationMonitor monitor) {
      if (active) {
         if (!this.tickTimeTracker.isActive()) {
            this.trackingTick = 0;
            this.tickTimeTracker.enable();
         }

         this.trackingTick++;
      } else {
         this.tickTimeTracker.disable();
      }

      this.profiler = TickDurationMonitor.tickProfiler(this.tickTimeTracker.getProfiler(), monitor);
   }

   private void endMonitor(boolean active, @Nullable TickDurationMonitor monitor) {
      if (monitor != null) {
         monitor.endTick();
      }

      if (active) {
         this.tickProfilerResult = this.tickTimeTracker.getResult();
      } else {
         this.tickProfilerResult = null;
      }

      this.profiler = this.tickTimeTracker.getProfiler();
   }

   @Override
   public void onResolutionChanged() {
      int _snowman = this.window.calculateScaleFactor(this.options.guiScale, this.forcesUnicodeFont());
      this.window.setScaleFactor((double)_snowman);
      if (this.currentScreen != null) {
         this.currentScreen.resize(this, this.window.getScaledWidth(), this.window.getScaledHeight());
      }

      Framebuffer _snowmanx = this.getFramebuffer();
      _snowmanx.resize(this.window.getFramebufferWidth(), this.window.getFramebufferHeight(), IS_SYSTEM_MAC);
      this.gameRenderer.onResized(this.window.getFramebufferWidth(), this.window.getFramebufferHeight());
      this.mouse.onResolutionChanged();
   }

   @Override
   public void onCursorEnterChanged() {
      this.mouse.method_30134();
   }

   private int getFramerateLimit() {
      return this.world != null || this.currentScreen == null && this.overlay == null ? this.window.getFramerateLimit() : 60;
   }

   public void cleanUpAfterCrash() {
      try {
         memoryReservedForCrash = new byte[0];
         this.worldRenderer.method_3267();
      } catch (Throwable var3) {
      }

      try {
         System.gc();
         if (this.integratedServerRunning && this.server != null) {
            this.server.stop(true);
         }

         this.disconnect(new SaveLevelScreen(new TranslatableText("menu.savingLevel")));
      } catch (Throwable var2) {
      }

      System.gc();
   }

   void handleProfilerKeyPress(int digit) {
      if (this.tickProfilerResult != null) {
         List<ProfilerTiming> _snowman = this.tickProfilerResult.getTimings(this.openProfilerSection);
         if (!_snowman.isEmpty()) {
            ProfilerTiming _snowmanx = _snowman.remove(0);
            if (digit == 0) {
               if (!_snowmanx.name.isEmpty()) {
                  int _snowmanxx = this.openProfilerSection.lastIndexOf(30);
                  if (_snowmanxx >= 0) {
                     this.openProfilerSection = this.openProfilerSection.substring(0, _snowmanxx);
                  }
               }
            } else {
               digit--;
               if (digit < _snowman.size() && !"unspecified".equals(_snowman.get(digit).name)) {
                  if (!this.openProfilerSection.isEmpty()) {
                     this.openProfilerSection = this.openProfilerSection + '\u001e';
                  }

                  this.openProfilerSection = this.openProfilerSection + _snowman.get(digit).name;
               }
            }
         }
      }
   }

   private void drawProfilerResults(MatrixStack matrices, ProfileResult profileResult) {
      List<ProfilerTiming> _snowman = profileResult.getTimings(this.openProfilerSection);
      ProfilerTiming _snowmanx = _snowman.remove(0);
      RenderSystem.clear(256, IS_SYSTEM_MAC);
      RenderSystem.matrixMode(5889);
      RenderSystem.loadIdentity();
      RenderSystem.ortho(0.0, (double)this.window.getFramebufferWidth(), (double)this.window.getFramebufferHeight(), 0.0, 1000.0, 3000.0);
      RenderSystem.matrixMode(5888);
      RenderSystem.loadIdentity();
      RenderSystem.translatef(0.0F, 0.0F, -2000.0F);
      RenderSystem.lineWidth(1.0F);
      RenderSystem.disableTexture();
      Tessellator _snowmanxx = Tessellator.getInstance();
      BufferBuilder _snowmanxxx = _snowmanxx.getBuffer();
      int _snowmanxxxx = 160;
      int _snowmanxxxxx = this.window.getFramebufferWidth() - 160 - 10;
      int _snowmanxxxxxx = this.window.getFramebufferHeight() - 320;
      RenderSystem.enableBlend();
      _snowmanxxx.begin(7, VertexFormats.POSITION_COLOR);
      _snowmanxxx.vertex((double)((float)_snowmanxxxxx - 176.0F), (double)((float)_snowmanxxxxxx - 96.0F - 16.0F), 0.0).color(200, 0, 0, 0).next();
      _snowmanxxx.vertex((double)((float)_snowmanxxxxx - 176.0F), (double)(_snowmanxxxxxx + 320), 0.0).color(200, 0, 0, 0).next();
      _snowmanxxx.vertex((double)((float)_snowmanxxxxx + 176.0F), (double)(_snowmanxxxxxx + 320), 0.0).color(200, 0, 0, 0).next();
      _snowmanxxx.vertex((double)((float)_snowmanxxxxx + 176.0F), (double)((float)_snowmanxxxxxx - 96.0F - 16.0F), 0.0).color(200, 0, 0, 0).next();
      _snowmanxx.draw();
      RenderSystem.disableBlend();
      double _snowmanxxxxxxx = 0.0;

      for (ProfilerTiming _snowmanxxxxxxxx : _snowman) {
         int _snowmanxxxxxxxxx = MathHelper.floor(_snowmanxxxxxxxx.parentSectionUsagePercentage / 4.0) + 1;
         _snowmanxxx.begin(6, VertexFormats.POSITION_COLOR);
         int _snowmanxxxxxxxxxx = _snowmanxxxxxxxx.getColor();
         int _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxx >> 16 & 0xFF;
         int _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxx >> 8 & 0xFF;
         int _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxx & 0xFF;
         _snowmanxxx.vertex((double)_snowmanxxxxx, (double)_snowmanxxxxxx, 0.0).color(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, 255).next();

         for (int _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxx; _snowmanxxxxxxxxxxxxxx >= 0; _snowmanxxxxxxxxxxxxxx--) {
            float _snowmanxxxxxxxxxxxxxxx = (float)(
               (_snowmanxxxxxxx + _snowmanxxxxxxxx.parentSectionUsagePercentage * (double)_snowmanxxxxxxxxxxxxxx / (double)_snowmanxxxxxxxxx) * (float) (Math.PI * 2) / 100.0
            );
            float _snowmanxxxxxxxxxxxxxxxx = MathHelper.sin(_snowmanxxxxxxxxxxxxxxx) * 160.0F;
            float _snowmanxxxxxxxxxxxxxxxxx = MathHelper.cos(_snowmanxxxxxxxxxxxxxxx) * 160.0F * 0.5F;
            _snowmanxxx.vertex((double)((float)_snowmanxxxxx + _snowmanxxxxxxxxxxxxxxxx), (double)((float)_snowmanxxxxxx - _snowmanxxxxxxxxxxxxxxxxx), 0.0)
               .color(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, 255)
               .next();
         }

         _snowmanxx.draw();
         _snowmanxxx.begin(5, VertexFormats.POSITION_COLOR);

         for (int _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxx; _snowmanxxxxxxxxxxxxxx >= 0; _snowmanxxxxxxxxxxxxxx--) {
            float _snowmanxxxxxxxxxxxxxxx = (float)(
               (_snowmanxxxxxxx + _snowmanxxxxxxxx.parentSectionUsagePercentage * (double)_snowmanxxxxxxxxxxxxxx / (double)_snowmanxxxxxxxxx) * (float) (Math.PI * 2) / 100.0
            );
            float _snowmanxxxxxxxxxxxxxxxx = MathHelper.sin(_snowmanxxxxxxxxxxxxxxx) * 160.0F;
            float _snowmanxxxxxxxxxxxxxxxxx = MathHelper.cos(_snowmanxxxxxxxxxxxxxxx) * 160.0F * 0.5F;
            if (!(_snowmanxxxxxxxxxxxxxxxxx > 0.0F)) {
               _snowmanxxx.vertex((double)((float)_snowmanxxxxx + _snowmanxxxxxxxxxxxxxxxx), (double)((float)_snowmanxxxxxx - _snowmanxxxxxxxxxxxxxxxxx), 0.0)
                  .color(_snowmanxxxxxxxxxxx >> 1, _snowmanxxxxxxxxxxxx >> 1, _snowmanxxxxxxxxxxxxx >> 1, 255)
                  .next();
               _snowmanxxx.vertex((double)((float)_snowmanxxxxx + _snowmanxxxxxxxxxxxxxxxx), (double)((float)_snowmanxxxxxx - _snowmanxxxxxxxxxxxxxxxxx + 10.0F), 0.0)
                  .color(_snowmanxxxxxxxxxxx >> 1, _snowmanxxxxxxxxxxxx >> 1, _snowmanxxxxxxxxxxxxx >> 1, 255)
                  .next();
            }
         }

         _snowmanxx.draw();
         _snowmanxxxxxxx += _snowmanxxxxxxxx.parentSectionUsagePercentage;
      }

      DecimalFormat _snowmanxxxxxxxx = new DecimalFormat("##0.00");
      _snowmanxxxxxxxx.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
      RenderSystem.enableTexture();
      String _snowmanxxxxxxxxx = ProfileResult.getHumanReadableName(_snowmanx.name);
      String _snowmanxxxxxxxxxx = "";
      if (!"unspecified".equals(_snowmanxxxxxxxxx)) {
         _snowmanxxxxxxxxxx = _snowmanxxxxxxxxxx + "[0] ";
      }

      if (_snowmanxxxxxxxxx.isEmpty()) {
         _snowmanxxxxxxxxxx = _snowmanxxxxxxxxxx + "ROOT ";
      } else {
         _snowmanxxxxxxxxxx = _snowmanxxxxxxxxxx + _snowmanxxxxxxxxx + ' ';
      }

      int _snowmanxxxxxxxxxxx = 16777215;
      this.textRenderer.drawWithShadow(matrices, _snowmanxxxxxxxxxx, (float)(_snowmanxxxxx - 160), (float)(_snowmanxxxxxx - 80 - 16), 16777215);
      _snowmanxxxxxxxxxx = _snowmanxxxxxxxx.format(_snowmanx.totalUsagePercentage) + "%";
      this.textRenderer
         .drawWithShadow(matrices, _snowmanxxxxxxxxxx, (float)(_snowmanxxxxx + 160 - this.textRenderer.getWidth(_snowmanxxxxxxxxxx)), (float)(_snowmanxxxxxx - 80 - 16), 16777215);

      for (int _snowmanxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxx < _snowman.size(); _snowmanxxxxxxxxxxxx++) {
         ProfilerTiming _snowmanxxxxxxxxxxxxx = _snowman.get(_snowmanxxxxxxxxxxxx);
         StringBuilder _snowmanxxxxxxxxxxxxxxx = new StringBuilder();
         if ("unspecified".equals(_snowmanxxxxxxxxxxxxx.name)) {
            _snowmanxxxxxxxxxxxxxxx.append("[?] ");
         } else {
            _snowmanxxxxxxxxxxxxxxx.append("[").append(_snowmanxxxxxxxxxxxx + 1).append("] ");
         }

         String _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx.append(_snowmanxxxxxxxxxxxxx.name).toString();
         this.textRenderer
            .drawWithShadow(matrices, _snowmanxxxxxxxxxxxxxxxx, (float)(_snowmanxxxxx - 160), (float)(_snowmanxxxxxx + 80 + _snowmanxxxxxxxxxxxx * 8 + 20), _snowmanxxxxxxxxxxxxx.getColor());
         _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxx.format(_snowmanxxxxxxxxxxxxx.parentSectionUsagePercentage) + "%";
         this.textRenderer
            .drawWithShadow(
               matrices,
               _snowmanxxxxxxxxxxxxxxxx,
               (float)(_snowmanxxxxx + 160 - 50 - this.textRenderer.getWidth(_snowmanxxxxxxxxxxxxxxxx)),
               (float)(_snowmanxxxxxx + 80 + _snowmanxxxxxxxxxxxx * 8 + 20),
               _snowmanxxxxxxxxxxxxx.getColor()
            );
         _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxx.format(_snowmanxxxxxxxxxxxxx.totalUsagePercentage) + "%";
         this.textRenderer
            .drawWithShadow(
               matrices,
               _snowmanxxxxxxxxxxxxxxxx,
               (float)(_snowmanxxxxx + 160 - this.textRenderer.getWidth(_snowmanxxxxxxxxxxxxxxxx)),
               (float)(_snowmanxxxxxx + 80 + _snowmanxxxxxxxxxxxx * 8 + 20),
               _snowmanxxxxxxxxxxxxx.getColor()
            );
      }
   }

   public void scheduleStop() {
      this.running = false;
   }

   public boolean isRunning() {
      return this.running;
   }

   public void openPauseMenu(boolean pause) {
      if (this.currentScreen == null) {
         boolean _snowman = this.isIntegratedServerRunning() && !this.server.isRemote();
         if (_snowman) {
            this.openScreen(new GameMenuScreen(!pause));
            this.soundManager.pauseAll();
         } else {
            this.openScreen(new GameMenuScreen(true));
         }
      }
   }

   private void handleBlockBreaking(boolean _snowman) {
      if (!_snowman) {
         this.attackCooldown = 0;
      }

      if (this.attackCooldown <= 0 && !this.player.isUsingItem()) {
         if (_snowman && this.crosshairTarget != null && this.crosshairTarget.getType() == HitResult.Type.BLOCK) {
            BlockHitResult _snowmanx = (BlockHitResult)this.crosshairTarget;
            BlockPos _snowmanxx = _snowmanx.getBlockPos();
            if (!this.world.getBlockState(_snowmanxx).isAir()) {
               Direction _snowmanxxx = _snowmanx.getSide();
               if (this.interactionManager.updateBlockBreakingProgress(_snowmanxx, _snowmanxxx)) {
                  this.particleManager.addBlockBreakingParticles(_snowmanxx, _snowmanxxx);
                  this.player.swingHand(Hand.MAIN_HAND);
               }
            }
         } else {
            this.interactionManager.cancelBlockBreaking();
         }
      }
   }

   private void doAttack() {
      if (this.attackCooldown <= 0) {
         if (this.crosshairTarget == null) {
            LOGGER.error("Null returned as 'hitResult', this shouldn't happen!");
            if (this.interactionManager.hasLimitedAttackSpeed()) {
               this.attackCooldown = 10;
            }
         } else if (!this.player.isRiding()) {
            switch (this.crosshairTarget.getType()) {
               case ENTITY:
                  this.interactionManager.attackEntity(this.player, ((EntityHitResult)this.crosshairTarget).getEntity());
                  break;
               case BLOCK:
                  BlockHitResult _snowman = (BlockHitResult)this.crosshairTarget;
                  BlockPos _snowmanx = _snowman.getBlockPos();
                  if (!this.world.getBlockState(_snowmanx).isAir()) {
                     this.interactionManager.attackBlock(_snowmanx, _snowman.getSide());
                     break;
                  }
               case MISS:
                  if (this.interactionManager.hasLimitedAttackSpeed()) {
                     this.attackCooldown = 10;
                  }

                  this.player.resetLastAttackedTicks();
            }

            this.player.swingHand(Hand.MAIN_HAND);
         }
      }
   }

   private void doItemUse() {
      if (!this.interactionManager.isBreakingBlock()) {
         this.itemUseCooldown = 4;
         if (!this.player.isRiding()) {
            if (this.crosshairTarget == null) {
               LOGGER.warn("Null returned as 'hitResult', this shouldn't happen!");
            }

            for (Hand _snowman : Hand.values()) {
               ItemStack _snowmanx = this.player.getStackInHand(_snowman);
               if (this.crosshairTarget != null) {
                  switch (this.crosshairTarget.getType()) {
                     case ENTITY:
                        EntityHitResult _snowmanxx = (EntityHitResult)this.crosshairTarget;
                        Entity _snowmanxxx = _snowmanxx.getEntity();
                        ActionResult _snowmanxxxx = this.interactionManager.interactEntityAtLocation(this.player, _snowmanxxx, _snowmanxx, _snowman);
                        if (!_snowmanxxxx.isAccepted()) {
                           _snowmanxxxx = this.interactionManager.interactEntity(this.player, _snowmanxxx, _snowman);
                        }

                        if (_snowmanxxxx.isAccepted()) {
                           if (_snowmanxxxx.shouldSwingHand()) {
                              this.player.swingHand(_snowman);
                           }

                           return;
                        }
                        break;
                     case BLOCK:
                        BlockHitResult _snowmanxxxxx = (BlockHitResult)this.crosshairTarget;
                        int _snowmanxxxxxx = _snowmanx.getCount();
                        ActionResult _snowmanxxxxxxx = this.interactionManager.interactBlock(this.player, this.world, _snowman, _snowmanxxxxx);
                        if (_snowmanxxxxxxx.isAccepted()) {
                           if (_snowmanxxxxxxx.shouldSwingHand()) {
                              this.player.swingHand(_snowman);
                              if (!_snowmanx.isEmpty() && (_snowmanx.getCount() != _snowmanxxxxxx || this.interactionManager.hasCreativeInventory())) {
                                 this.gameRenderer.firstPersonRenderer.resetEquipProgress(_snowman);
                              }
                           }

                           return;
                        }

                        if (_snowmanxxxxxxx == ActionResult.FAIL) {
                           return;
                        }
                  }
               }

               if (!_snowmanx.isEmpty()) {
                  ActionResult _snowmanxxxxxxxx = this.interactionManager.interactItem(this.player, this.world, _snowman);
                  if (_snowmanxxxxxxxx.isAccepted()) {
                     if (_snowmanxxxxxxxx.shouldSwingHand()) {
                        this.player.swingHand(_snowman);
                     }

                     this.gameRenderer.firstPersonRenderer.resetEquipProgress(_snowman);
                     return;
                  }
               }
            }
         }
      }
   }

   public MusicTracker getMusicTracker() {
      return this.musicTracker;
   }

   public void tick() {
      if (this.itemUseCooldown > 0) {
         this.itemUseCooldown--;
      }

      this.profiler.push("gui");
      if (!this.paused) {
         this.inGameHud.tick();
      }

      this.profiler.pop();
      this.gameRenderer.updateTargetedEntity(1.0F);
      this.tutorialManager.tick(this.world, this.crosshairTarget);
      this.profiler.push("gameMode");
      if (!this.paused && this.world != null) {
         this.interactionManager.tick();
      }

      this.profiler.swap("textures");
      if (this.world != null) {
         this.textureManager.tick();
      }

      if (this.currentScreen == null && this.player != null) {
         if (this.player.isDead() && !(this.currentScreen instanceof DeathScreen)) {
            this.openScreen(null);
         } else if (this.player.isSleeping() && this.world != null) {
            this.openScreen(new SleepingChatScreen());
         }
      } else if (this.currentScreen != null && this.currentScreen instanceof SleepingChatScreen && !this.player.isSleeping()) {
         this.openScreen(null);
      }

      if (this.currentScreen != null) {
         this.attackCooldown = 10000;
      }

      if (this.currentScreen != null) {
         Screen.wrapScreenError(() -> this.currentScreen.tick(), "Ticking screen", this.currentScreen.getClass().getCanonicalName());
      }

      if (!this.options.debugEnabled) {
         this.inGameHud.resetDebugHudChunk();
      }

      if (this.overlay == null && (this.currentScreen == null || this.currentScreen.passEvents)) {
         this.profiler.swap("Keybindings");
         this.handleInputEvents();
         if (this.attackCooldown > 0) {
            this.attackCooldown--;
         }
      }

      if (this.world != null) {
         this.profiler.swap("gameRenderer");
         if (!this.paused) {
            this.gameRenderer.tick();
         }

         this.profiler.swap("levelRenderer");
         if (!this.paused) {
            this.worldRenderer.tick();
         }

         this.profiler.swap("level");
         if (!this.paused) {
            if (this.world.getLightningTicksLeft() > 0) {
               this.world.setLightningTicksLeft(this.world.getLightningTicksLeft() - 1);
            }

            this.world.tickEntities();
         }
      } else if (this.gameRenderer.getShader() != null) {
         this.gameRenderer.disableShader();
      }

      if (!this.paused) {
         this.musicTracker.tick();
      }

      this.soundManager.tick(this.paused);
      if (this.world != null) {
         if (!this.paused) {
            if (!this.options.joinedFirstServer && this.method_31321()) {
               Text _snowman = new TranslatableText("tutorial.socialInteractions.title");
               Text _snowmanx = new TranslatableText("tutorial.socialInteractions.description", TutorialManager.getKeybindName("socialInteractions"));
               this.field_26843 = new TutorialToast(TutorialToast.Type.SOCIAL_INTERACTIONS, _snowman, _snowmanx, true);
               this.tutorialManager.method_31365(this.field_26843, 160);
               this.options.joinedFirstServer = true;
               this.options.write();
            }

            this.tutorialManager.tick();

            try {
               this.world.tick(() -> true);
            } catch (Throwable var4) {
               CrashReport _snowman = CrashReport.create(var4, "Exception in world tick");
               if (this.world == null) {
                  CrashReportSection _snowmanx = _snowman.addElement("Affected level");
                  _snowmanx.add("Problem", "Level is null!");
               } else {
                  this.world.addDetailsToCrashReport(_snowman);
               }

               throw new CrashException(_snowman);
            }
         }

         this.profiler.swap("animateTick");
         if (!this.paused && this.world != null) {
            this.world
               .doRandomBlockDisplayTicks(MathHelper.floor(this.player.getX()), MathHelper.floor(this.player.getY()), MathHelper.floor(this.player.getZ()));
         }

         this.profiler.swap("particles");
         if (!this.paused) {
            this.particleManager.tick();
         }
      } else if (this.connection != null) {
         this.profiler.swap("pendingConnection");
         this.connection.tick();
      }

      this.profiler.swap("keyboard");
      this.keyboard.pollDebugCrash();
      this.profiler.pop();
   }

   private boolean method_31321() {
      return !this.integratedServerRunning || this.server != null && this.server.isRemote();
   }

   private void handleInputEvents() {
      while (this.options.keyTogglePerspective.wasPressed()) {
         Perspective _snowman = this.options.getPerspective();
         this.options.setPerspective(this.options.getPerspective().next());
         if (_snowman.isFirstPerson() != this.options.getPerspective().isFirstPerson()) {
            this.gameRenderer.onCameraEntitySet(this.options.getPerspective().isFirstPerson() ? this.getCameraEntity() : null);
         }

         this.worldRenderer.scheduleTerrainUpdate();
      }

      while (this.options.keySmoothCamera.wasPressed()) {
         this.options.smoothCameraEnabled = !this.options.smoothCameraEnabled;
      }

      for (int _snowman = 0; _snowman < 9; _snowman++) {
         boolean _snowmanx = this.options.keySaveToolbarActivator.isPressed();
         boolean _snowmanxx = this.options.keyLoadToolbarActivator.isPressed();
         if (this.options.keysHotbar[_snowman].wasPressed()) {
            if (this.player.isSpectator()) {
               this.inGameHud.getSpectatorHud().selectSlot(_snowman);
            } else if (!this.player.isCreative() || this.currentScreen != null || !_snowmanxx && !_snowmanx) {
               this.player.inventory.selectedSlot = _snowman;
            } else {
               CreativeInventoryScreen.onHotbarKeyPress(this, _snowman, _snowmanxx, _snowmanx);
            }
         }
      }

      while (this.options.keySocialInteractions.wasPressed()) {
         if (!this.method_31321()) {
            this.player.sendMessage(field_26841, true);
            NarratorManager.INSTANCE.narrate(field_26841.getString());
         } else {
            if (this.field_26843 != null) {
               this.tutorialManager.method_31364(this.field_26843);
               this.field_26843 = null;
            }

            this.openScreen(new SocialInteractionsScreen());
         }
      }

      while (this.options.keyInventory.wasPressed()) {
         if (this.interactionManager.hasRidingInventory()) {
            this.player.openRidingInventory();
         } else {
            this.tutorialManager.onInventoryOpened();
            this.openScreen(new InventoryScreen(this.player));
         }
      }

      while (this.options.keyAdvancements.wasPressed()) {
         this.openScreen(new AdvancementsScreen(this.player.networkHandler.getAdvancementHandler()));
      }

      while (this.options.keySwapHands.wasPressed()) {
         if (!this.player.isSpectator()) {
            this.getNetworkHandler()
               .sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.SWAP_ITEM_WITH_OFFHAND, BlockPos.ORIGIN, Direction.DOWN));
         }
      }

      while (this.options.keyDrop.wasPressed()) {
         if (!this.player.isSpectator() && this.player.dropSelectedItem(Screen.hasControlDown())) {
            this.player.swingHand(Hand.MAIN_HAND);
         }
      }

      boolean _snowmanx = this.options.chatVisibility != ChatVisibility.HIDDEN;
      if (_snowmanx) {
         while (this.options.keyChat.wasPressed()) {
            this.openChatScreen("");
         }

         if (this.currentScreen == null && this.overlay == null && this.options.keyCommand.wasPressed()) {
            this.openChatScreen("/");
         }
      }

      if (this.player.isUsingItem()) {
         if (!this.options.keyUse.isPressed()) {
            this.interactionManager.stopUsingItem(this.player);
         }

         while (this.options.keyAttack.wasPressed()) {
         }

         while (this.options.keyUse.wasPressed()) {
         }

         while (this.options.keyPickItem.wasPressed()) {
         }
      } else {
         while (this.options.keyAttack.wasPressed()) {
            this.doAttack();
         }

         while (this.options.keyUse.wasPressed()) {
            this.doItemUse();
         }

         while (this.options.keyPickItem.wasPressed()) {
            this.doItemPick();
         }
      }

      if (this.options.keyUse.isPressed() && this.itemUseCooldown == 0 && !this.player.isUsingItem()) {
         this.doItemUse();
      }

      this.handleBlockBreaking(this.currentScreen == null && this.options.keyAttack.isPressed() && this.mouse.isCursorLocked());
   }

   public static DataPackSettings method_29598(LevelStorage.Session _snowman) {
      MinecraftServer.convertLevel(_snowman);
      DataPackSettings _snowmanx = _snowman.getDataPackSettings();
      if (_snowmanx == null) {
         throw new IllegalStateException("Failed to load data pack config");
      } else {
         return _snowmanx;
      }
   }

   public static SaveProperties createSaveProperties(
      LevelStorage.Session session, DynamicRegistryManager.Impl registryTracker, ResourceManager resourceManager, DataPackSettings _snowman
   ) {
      RegistryOps<Tag> _snowmanx = RegistryOps.of(NbtOps.INSTANCE, resourceManager, registryTracker);
      SaveProperties _snowmanxx = session.readLevelProperties(_snowmanx, _snowman);
      if (_snowmanxx == null) {
         throw new IllegalStateException("Failed to load world");
      } else {
         return _snowmanxx;
      }
   }

   public void startIntegratedServer(String worldName) {
      this.startIntegratedServer(
         worldName,
         DynamicRegistryManager.create(),
         MinecraftClient::method_29598,
         MinecraftClient::createSaveProperties,
         false,
         MinecraftClient.WorldLoadAction.BACKUP
      );
   }

   public void method_29607(String worldName, LevelInfo levelInfo, DynamicRegistryManager.Impl registryTracker, GeneratorOptions generatorOptions) {
      this.startIntegratedServer(
         worldName,
         registryTracker,
         _snowmanx -> levelInfo.getDataPackSettings(),
         (_snowmanxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx) -> {
            RegistryReadingOps<JsonElement> _snowmanxxxxxxx = RegistryReadingOps.of(JsonOps.INSTANCE, registryTracker);
            RegistryOps<JsonElement> _snowmanx = RegistryOps.of(JsonOps.INSTANCE, _snowmanxxxxx, registryTracker);
            DataResult<GeneratorOptions> _snowmanxx = GeneratorOptions.CODEC
               .encodeStart(_snowmanxxxxxxx, generatorOptions)
               .setLifecycle(Lifecycle.stable())
               .flatMap(_snowmanxxxxxxxx -> GeneratorOptions.CODEC.parse(_snowman, _snowmanxxxxxxxx));
            GeneratorOptions _snowmanxxxxxxx = _snowmanxx.resultOrPartial(Util.method_29188("Error reading worldgen settings after loading data packs: ", LOGGER::error))
               .orElse(generatorOptions);
            return new LevelProperties(levelInfo, _snowmanxxxxxxx, _snowmanxx.lifecycle());
         },
         false,
         MinecraftClient.WorldLoadAction.CREATE
      );
   }

   private void startIntegratedServer(
      String worldName,
      DynamicRegistryManager.Impl registryTracker,
      Function<LevelStorage.Session, DataPackSettings> _snowman,
      Function4<LevelStorage.Session, DynamicRegistryManager.Impl, ResourceManager, DataPackSettings, SaveProperties> _snowman,
      boolean safeMode,
      MinecraftClient.WorldLoadAction _snowman
   ) {
      LevelStorage.Session _snowmanxxx;
      try {
         _snowmanxxx = this.levelStorage.createSession(worldName);
      } catch (IOException var21) {
         LOGGER.warn("Failed to read level {} data", worldName, var21);
         SystemToast.addWorldAccessFailureToast(this, worldName);
         this.openScreen(null);
         return;
      }

      MinecraftClient.IntegratedResourceManager _snowmanxxxx;
      try {
         _snowmanxxxx = this.method_29604(registryTracker, _snowman, _snowman, safeMode, _snowmanxxx);
      } catch (Exception var20) {
         LOGGER.warn("Failed to load datapacks, can't proceed with server load", var20);
         this.openScreen(new DatapackFailureScreen(() -> this.startIntegratedServer(worldName, registryTracker, _snowman, _snowman, true, _snowman)));

         try {
            _snowmanxxx.close();
         } catch (IOException var16) {
            LOGGER.warn("Failed to unlock access to level {}", worldName, var16);
         }

         return;
      }

      SaveProperties _snowmanxxxxx = _snowmanxxxx.getSaveProperties();
      boolean _snowmanxxxxxx = _snowmanxxxxx.getGeneratorOptions().isLegacyCustomizedType();
      boolean _snowmanxxxxxxx = _snowmanxxxxx.getLifecycle() != Lifecycle.stable();
      if (_snowman == MinecraftClient.WorldLoadAction.NONE || !_snowmanxxxxxx && !_snowmanxxxxxxx) {
         this.disconnect();
         this.worldGenProgressTracker.set(null);

         try {
            _snowmanxxx.backupLevelDataFile(registryTracker, _snowmanxxxxx);
            _snowmanxxxx.getServerResourceManager().loadRegistryTags();
            YggdrasilAuthenticationService _snowmanxxxxxxxx = new YggdrasilAuthenticationService(this.netProxy);
            MinecraftSessionService _snowmanxxxxxxxxx = _snowmanxxxxxxxx.createMinecraftSessionService();
            GameProfileRepository _snowmanxxxxxxxxxx = _snowmanxxxxxxxx.createProfileRepository();
            UserCache _snowmanxxxxxxxxxxx = new UserCache(_snowmanxxxxxxxxxx, new File(this.runDirectory, MinecraftServer.USER_CACHE_FILE.getName()));
            SkullBlockEntity.setUserCache(_snowmanxxxxxxxxxxx);
            SkullBlockEntity.setSessionService(_snowmanxxxxxxxxx);
            UserCache.setUseRemote(false);
            this.server = MinecraftServer.startServer(
               serverThread -> new IntegratedServer(
                     serverThread, this, registryTracker, _snowman, _snowman.getResourcePackManager(), _snowman.getServerResourceManager(), _snowman, _snowman, _snowman, _snowman, _snowmanxxxxxxxxxxxx -> {
                        WorldGenerationProgressTracker _snowmanxxxxxxxxxxxxx = new WorldGenerationProgressTracker(_snowmanxxxxxxxxxxxx + 0);
                        _snowmanxxxxxxxxxxxxx.start();
                        this.worldGenProgressTracker.set(_snowmanxxxxxxxxxxxxx);
                        return new QueueingWorldGenerationProgressListener(_snowmanxxxxxxxxxxxxx, this.renderTaskQueue::add);
                     }
                  )
            );
            this.integratedServerRunning = true;
         } catch (Throwable var19) {
            CrashReport _snowmanxxxxxxxxxxxx = CrashReport.create(var19, "Starting integrated server");
            CrashReportSection _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx.addElement("Starting integrated server");
            _snowmanxxxxxxxxxxxxx.add("Level ID", worldName);
            _snowmanxxxxxxxxxxxxx.add("Level Name", _snowmanxxxxx.getLevelName());
            throw new CrashException(_snowmanxxxxxxxxxxxx);
         }

         while (this.worldGenProgressTracker.get() == null) {
            Thread.yield();
         }

         LevelLoadingScreen _snowmanxxxxxxxx = new LevelLoadingScreen(this.worldGenProgressTracker.get());
         this.openScreen(_snowmanxxxxxxxx);
         this.profiler.push("waitForServer");

         while (!this.server.isLoading()) {
            _snowmanxxxxxxxx.tick();
            this.render(false);

            try {
               Thread.sleep(16L);
            } catch (InterruptedException var18) {
            }

            if (this.crashReport != null) {
               printCrashReport(this.crashReport);
               return;
            }
         }

         this.profiler.pop();
         SocketAddress _snowmanxxxxxxxxx = this.server.getNetworkIo().bindLocal();
         ClientConnection _snowmanxxxxxxxxxx = ClientConnection.connectLocal(_snowmanxxxxxxxxx);
         _snowmanxxxxxxxxxx.setPacketListener(new ClientLoginNetworkHandler(_snowmanxxxxxxxxxx, this, null, _snowmanxxxxxxxxxxx -> {
         }));
         _snowmanxxxxxxxxxx.send(new HandshakeC2SPacket(_snowmanxxxxxxxxx.toString(), 0, NetworkState.LOGIN));
         _snowmanxxxxxxxxxx.send(new LoginHelloC2SPacket(this.getSession().getProfile()));
         this.connection = _snowmanxxxxxxxxxx;
      } else {
         this.method_29601(
            _snowman, worldName, _snowmanxxxxxx, () -> this.startIntegratedServer(worldName, registryTracker, _snowman, _snowman, safeMode, MinecraftClient.WorldLoadAction.NONE)
         );
         _snowmanxxxx.close();

         try {
            _snowmanxxx.close();
         } catch (IOException var17) {
            LOGGER.warn("Failed to unlock access to level {}", worldName, var17);
         }
      }
   }

   private void method_29601(MinecraftClient.WorldLoadAction _snowman, String _snowman, boolean _snowman, Runnable _snowman) {
      if (_snowman == MinecraftClient.WorldLoadAction.BACKUP) {
         Text _snowmanxxxx;
         Text _snowmanxxxxx;
         if (_snowman) {
            _snowmanxxxx = new TranslatableText("selectWorld.backupQuestion.customized");
            _snowmanxxxxx = new TranslatableText("selectWorld.backupWarning.customized");
         } else {
            _snowmanxxxx = new TranslatableText("selectWorld.backupQuestion.experimental");
            _snowmanxxxxx = new TranslatableText("selectWorld.backupWarning.experimental");
         }

         this.openScreen(new BackupPromptScreen(null, (_snowmanxxxxxx, _snowmanxxxxxxx) -> {
            if (_snowmanxxxxxx) {
               EditWorldScreen.method_29784(this.levelStorage, _snowman);
            }

            _snowman.run();
         }, _snowmanxxxx, _snowmanxxxxx, false));
      } else {
         this.openScreen(
            new ConfirmScreen(
               _snowmanxxxxx -> {
                  if (_snowmanxxxxx) {
                     _snowman.run();
                  } else {
                     this.openScreen(null);

                     try (LevelStorage.Session _snowmanxxx = this.levelStorage.createSession(_snowman)) {
                        _snowmanxxx.deleteSessionLock();
                     } catch (IOException var17) {
                        SystemToast.addWorldDeleteFailureToast(this, _snowman);
                        LOGGER.error("Failed to delete world {}", _snowman, var17);
                     }
                  }
               },
               new TranslatableText("selectWorld.backupQuestion.experimental"),
               new TranslatableText("selectWorld.backupWarning.experimental"),
               ScreenTexts.PROCEED,
               ScreenTexts.CANCEL
            )
         );
      }
   }

   public MinecraftClient.IntegratedResourceManager method_29604(
      DynamicRegistryManager.Impl _snowman,
      Function<LevelStorage.Session, DataPackSettings> _snowman,
      Function4<LevelStorage.Session, DynamicRegistryManager.Impl, ResourceManager, DataPackSettings, SaveProperties> _snowman,
      boolean _snowman,
      LevelStorage.Session _snowman
   ) throws InterruptedException, ExecutionException {
      DataPackSettings _snowmanxxxxx = _snowman.apply(_snowman);
      ResourcePackManager _snowmanxxxxxx = new ResourcePackManager(
         new VanillaDataPackProvider(), new FileResourcePackProvider(_snowman.getDirectory(WorldSavePath.DATAPACKS).toFile(), ResourcePackSource.PACK_SOURCE_WORLD)
      );

      try {
         DataPackSettings _snowmanxxxxxxx = MinecraftServer.loadDataPacks(_snowmanxxxxxx, _snowmanxxxxx, _snowman);
         CompletableFuture<ServerResourceManager> _snowmanxxxxxxxx = ServerResourceManager.reload(
            _snowmanxxxxxx.createResourcePacks(), CommandManager.RegistrationEnvironment.INTEGRATED, 2, Util.getMainWorkerExecutor(), this
         );
         this.runTasks(_snowmanxxxxxxxx::isDone);
         ServerResourceManager _snowmanxxxxxxxxx = _snowmanxxxxxxxx.get();
         SaveProperties _snowmanxxxxxxxxxx = (SaveProperties)_snowman.apply(_snowman, _snowman, _snowmanxxxxxxxxx.getResourceManager(), _snowmanxxxxxxx);
         return new MinecraftClient.IntegratedResourceManager(_snowmanxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx);
      } catch (ExecutionException | InterruptedException var12) {
         _snowmanxxxxxx.close();
         throw var12;
      }
   }

   public void joinWorld(ClientWorld world) {
      ProgressScreen _snowman = new ProgressScreen();
      _snowman.method_15412(new TranslatableText("connect.joining"));
      this.reset(_snowman);
      this.world = world;
      this.setWorld(world);
      if (!this.integratedServerRunning) {
         AuthenticationService _snowmanx = new YggdrasilAuthenticationService(this.netProxy);
         MinecraftSessionService _snowmanxx = _snowmanx.createMinecraftSessionService();
         GameProfileRepository _snowmanxxx = _snowmanx.createProfileRepository();
         UserCache _snowmanxxxx = new UserCache(_snowmanxxx, new File(this.runDirectory, MinecraftServer.USER_CACHE_FILE.getName()));
         SkullBlockEntity.setUserCache(_snowmanxxxx);
         SkullBlockEntity.setSessionService(_snowmanxx);
         UserCache.setUseRemote(false);
      }
   }

   public void disconnect() {
      this.disconnect(new ProgressScreen());
   }

   public void disconnect(Screen screen) {
      ClientPlayNetworkHandler _snowman = this.getNetworkHandler();
      if (_snowman != null) {
         this.cancelTasks();
         _snowman.clearWorld();
      }

      IntegratedServer _snowmanx = this.server;
      this.server = null;
      this.gameRenderer.reset();
      this.interactionManager = null;
      NarratorManager.INSTANCE.clear();
      this.reset(screen);
      if (this.world != null) {
         if (_snowmanx != null) {
            this.profiler.push("waitForServer");

            while (!_snowmanx.isStopping()) {
               this.render(false);
            }

            this.profiler.pop();
         }

         this.builtinPackProvider.clear();
         this.inGameHud.clear();
         this.currentServerEntry = null;
         this.integratedServerRunning = false;
         this.game.onLeaveGameSession();
      }

      this.world = null;
      this.setWorld(null);
      this.player = null;
   }

   private void reset(Screen screen) {
      this.profiler.push("forcedTick");
      this.soundManager.stopAll();
      this.cameraEntity = null;
      this.connection = null;
      this.openScreen(screen);
      this.render(false);
      this.profiler.pop();
   }

   public void method_29970(Screen _snowman) {
      this.profiler.push("forcedTick");
      this.openScreen(_snowman);
      this.render(false);
      this.profiler.pop();
   }

   private void setWorld(@Nullable ClientWorld world) {
      this.worldRenderer.setWorld(world);
      this.particleManager.setWorld(world);
      BlockEntityRenderDispatcher.INSTANCE.setWorld(world);
      this.updateWindowTitle();
   }

   public boolean isMultiplayerEnabled() {
      return this.multiplayerEnabled && this.field_26902.serversAllowed();
   }

   public boolean shouldBlockMessages(UUID sender) {
      return this.isOnlineChatEnabled()
         ? this.socialInteractionsManager.method_31391(sender)
         : (this.player == null || !sender.equals(this.player.getUuid())) && !sender.equals(Util.NIL_UUID);
   }

   public boolean isOnlineChatEnabled() {
      return this.onlineChatEnabled && this.field_26902.chatAllowed();
   }

   public final boolean isDemo() {
      return this.isDemo;
   }

   @Nullable
   public ClientPlayNetworkHandler getNetworkHandler() {
      return this.player == null ? null : this.player.networkHandler;
   }

   public static boolean isHudEnabled() {
      return !instance.options.hudHidden;
   }

   public static boolean isFancyGraphicsOrBetter() {
      return instance.options.graphicsMode.getId() >= GraphicsMode.FANCY.getId();
   }

   public static boolean isFabulousGraphicsOrBetter() {
      return instance.options.graphicsMode.getId() >= GraphicsMode.FABULOUS.getId();
   }

   public static boolean isAmbientOcclusionEnabled() {
      return instance.options.ao != AoMode.OFF;
   }

   private void doItemPick() {
      if (this.crosshairTarget != null && this.crosshairTarget.getType() != HitResult.Type.MISS) {
         boolean _snowman = this.player.abilities.creativeMode;
         BlockEntity _snowmanx = null;
         HitResult.Type _snowmanxx = this.crosshairTarget.getType();
         ItemStack _snowmanxxx;
         if (_snowmanxx == HitResult.Type.BLOCK) {
            BlockPos _snowmanxxxx = ((BlockHitResult)this.crosshairTarget).getBlockPos();
            BlockState _snowmanxxxxx = this.world.getBlockState(_snowmanxxxx);
            Block _snowmanxxxxxx = _snowmanxxxxx.getBlock();
            if (_snowmanxxxxx.isAir()) {
               return;
            }

            _snowmanxxx = _snowmanxxxxxx.getPickStack(this.world, _snowmanxxxx, _snowmanxxxxx);
            if (_snowmanxxx.isEmpty()) {
               return;
            }

            if (_snowman && Screen.hasControlDown() && _snowmanxxxxxx.hasBlockEntity()) {
               _snowmanx = this.world.getBlockEntity(_snowmanxxxx);
            }
         } else {
            if (_snowmanxx != HitResult.Type.ENTITY || !_snowman) {
               return;
            }

            Entity _snowmanxxxxxxx = ((EntityHitResult)this.crosshairTarget).getEntity();
            if (_snowmanxxxxxxx instanceof PaintingEntity) {
               _snowmanxxx = new ItemStack(Items.PAINTING);
            } else if (_snowmanxxxxxxx instanceof LeashKnotEntity) {
               _snowmanxxx = new ItemStack(Items.LEAD);
            } else if (_snowmanxxxxxxx instanceof ItemFrameEntity) {
               ItemFrameEntity _snowmanxxxxxxxx = (ItemFrameEntity)_snowmanxxxxxxx;
               ItemStack _snowmanxxxxxxxxx = _snowmanxxxxxxxx.getHeldItemStack();
               if (_snowmanxxxxxxxxx.isEmpty()) {
                  _snowmanxxx = new ItemStack(Items.ITEM_FRAME);
               } else {
                  _snowmanxxx = _snowmanxxxxxxxxx.copy();
               }
            } else if (_snowmanxxxxxxx instanceof AbstractMinecartEntity) {
               AbstractMinecartEntity _snowmanxxxxxxxx = (AbstractMinecartEntity)_snowmanxxxxxxx;
               Item _snowmanxxxxxxxxx;
               switch (_snowmanxxxxxxxx.getMinecartType()) {
                  case FURNACE:
                     _snowmanxxxxxxxxx = Items.FURNACE_MINECART;
                     break;
                  case CHEST:
                     _snowmanxxxxxxxxx = Items.CHEST_MINECART;
                     break;
                  case TNT:
                     _snowmanxxxxxxxxx = Items.TNT_MINECART;
                     break;
                  case HOPPER:
                     _snowmanxxxxxxxxx = Items.HOPPER_MINECART;
                     break;
                  case COMMAND_BLOCK:
                     _snowmanxxxxxxxxx = Items.COMMAND_BLOCK_MINECART;
                     break;
                  default:
                     _snowmanxxxxxxxxx = Items.MINECART;
               }

               _snowmanxxx = new ItemStack(_snowmanxxxxxxxxx);
            } else if (_snowmanxxxxxxx instanceof BoatEntity) {
               _snowmanxxx = new ItemStack(((BoatEntity)_snowmanxxxxxxx).asItem());
            } else if (_snowmanxxxxxxx instanceof ArmorStandEntity) {
               _snowmanxxx = new ItemStack(Items.ARMOR_STAND);
            } else if (_snowmanxxxxxxx instanceof EndCrystalEntity) {
               _snowmanxxx = new ItemStack(Items.END_CRYSTAL);
            } else {
               SpawnEggItem _snowmanxxxxxxxx = SpawnEggItem.forEntity(_snowmanxxxxxxx.getType());
               if (_snowmanxxxxxxxx == null) {
                  return;
               }

               _snowmanxxx = new ItemStack(_snowmanxxxxxxxx);
            }
         }

         if (_snowmanxxx.isEmpty()) {
            String _snowmanxxxxxxx = "";
            if (_snowmanxx == HitResult.Type.BLOCK) {
               _snowmanxxxxxxx = Registry.BLOCK.getId(this.world.getBlockState(((BlockHitResult)this.crosshairTarget).getBlockPos()).getBlock()).toString();
            } else if (_snowmanxx == HitResult.Type.ENTITY) {
               _snowmanxxxxxxx = Registry.ENTITY_TYPE.getId(((EntityHitResult)this.crosshairTarget).getEntity().getType()).toString();
            }

            LOGGER.warn("Picking on: [{}] {} gave null item", _snowmanxx, _snowmanxxxxxxx);
         } else {
            PlayerInventory _snowmanxxxxxxx = this.player.inventory;
            if (_snowmanx != null) {
               this.addBlockEntityNbt(_snowmanxxx, _snowmanx);
            }

            int _snowmanxxxxxxxx = _snowmanxxxxxxx.getSlotWithStack(_snowmanxxx);
            if (_snowman) {
               _snowmanxxxxxxx.addPickBlock(_snowmanxxx);
               this.interactionManager.clickCreativeStack(this.player.getStackInHand(Hand.MAIN_HAND), 36 + _snowmanxxxxxxx.selectedSlot);
            } else if (_snowmanxxxxxxxx != -1) {
               if (PlayerInventory.isValidHotbarIndex(_snowmanxxxxxxxx)) {
                  _snowmanxxxxxxx.selectedSlot = _snowmanxxxxxxxx;
               } else {
                  this.interactionManager.pickFromInventory(_snowmanxxxxxxxx);
               }
            }
         }
      }
   }

   private ItemStack addBlockEntityNbt(ItemStack stack, BlockEntity blockEntity) {
      CompoundTag _snowman = blockEntity.toTag(new CompoundTag());
      if (stack.getItem() instanceof SkullItem && _snowman.contains("SkullOwner")) {
         CompoundTag _snowmanx = _snowman.getCompound("SkullOwner");
         stack.getOrCreateTag().put("SkullOwner", _snowmanx);
         return stack;
      } else {
         stack.putSubTag("BlockEntityTag", _snowman);
         CompoundTag _snowmanx = new CompoundTag();
         ListTag _snowmanxx = new ListTag();
         _snowmanxx.add(StringTag.of("\"(+NBT)\""));
         _snowmanx.put("Lore", _snowmanxx);
         stack.putSubTag("display", _snowmanx);
         return stack;
      }
   }

   public CrashReport addDetailsToCrashReport(CrashReport report) {
      addSystemDetailsToCrashReport(this.languageManager, this.gameVersion, this.options, report);
      if (this.world != null) {
         this.world.addDetailsToCrashReport(report);
      }

      return report;
   }

   public static void addSystemDetailsToCrashReport(
      @Nullable LanguageManager languageManager, String version, @Nullable GameOptions options, CrashReport report
   ) {
      CrashReportSection _snowman = report.getSystemDetailsSection();
      _snowman.add("Launched Version", () -> version);
      _snowman.add("Backend library", RenderSystem::getBackendDescription);
      _snowman.add("Backend API", RenderSystem::getApiDescription);
      _snowman.add("GL Caps", RenderSystem::getCapsString);
      _snowman.add("Using VBOs", () -> "Yes");
      _snowman.add(
         "Is Modded",
         () -> {
            String _snowmanx = ClientBrandRetriever.getClientModName();
            if (!"vanilla".equals(_snowmanx)) {
               return "Definitely; Client brand changed to '" + _snowmanx + "'";
            } else {
               return MinecraftClient.class.getSigners() == null
                  ? "Very likely; Jar signature invalidated"
                  : "Probably not. Jar signature remains and client brand is untouched.";
            }
         }
      );
      _snowman.add("Type", "Client (map_client.txt)");
      if (options != null) {
         if (instance != null) {
            String _snowmanx = instance.getVideoWarningManager().method_30920();
            if (_snowmanx != null) {
               _snowman.add("GPU Warnings", _snowmanx);
            }
         }

         _snowman.add("Graphics mode", options.graphicsMode);
         _snowman.add("Resource Packs", () -> {
            StringBuilder _snowmanx = new StringBuilder();

            for (String _snowmanx : options.resourcePacks) {
               if (_snowmanx.length() > 0) {
                  _snowmanx.append(", ");
               }

               _snowmanx.append(_snowmanx);
               if (options.incompatibleResourcePacks.contains(_snowmanx)) {
                  _snowmanx.append(" (incompatible)");
               }
            }

            return _snowmanx.toString();
         });
      }

      if (languageManager != null) {
         _snowman.add("Current Language", () -> languageManager.getLanguage().toString());
      }

      _snowman.add("CPU", GlDebugInfo::getCpuInfo);
   }

   public static MinecraftClient getInstance() {
      return instance;
   }

   public CompletableFuture<Void> reloadResourcesConcurrently() {
      return this.submit(this::reloadResources).thenCompose(_snowman -> (CompletionStage<Void>)_snowman);
   }

   @Override
   public void addSnooperInfo(Snooper snooper) {
      snooper.addInfo("fps", currentFps);
      snooper.addInfo("vsync_enabled", this.options.enableVsync);
      snooper.addInfo("display_frequency", this.window.getRefreshRate());
      snooper.addInfo("display_type", this.window.isFullscreen() ? "fullscreen" : "windowed");
      snooper.addInfo("run_time", (Util.getMeasuringTimeMs() - snooper.getStartTime()) / 60L * 1000L);
      snooper.addInfo("current_action", this.getCurrentAction());
      snooper.addInfo("language", this.options.language == null ? "en_us" : this.options.language);
      String _snowman = ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN ? "little" : "big";
      snooper.addInfo("endianness", _snowman);
      snooper.addInfo("subtitles", this.options.showSubtitles);
      snooper.addInfo("touch", this.options.touchscreen ? "touch" : "mouse");
      int _snowmanx = 0;

      for (ResourcePackProfile _snowmanxx : this.resourcePackManager.getEnabledProfiles()) {
         if (!_snowmanxx.isAlwaysEnabled() && !_snowmanxx.isPinned()) {
            snooper.addInfo("resource_pack[" + _snowmanx++ + "]", _snowmanxx.getName());
         }
      }

      snooper.addInfo("resource_packs", _snowmanx);
      if (this.server != null) {
         snooper.addInfo("snooper_partner", this.server.getSnooper().getToken());
      }
   }

   private String getCurrentAction() {
      if (this.server != null) {
         return this.server.isRemote() ? "hosting_lan" : "singleplayer";
      } else if (this.currentServerEntry != null) {
         return this.currentServerEntry.isLocal() ? "playing_lan" : "multiplayer";
      } else {
         return "out_of_game";
      }
   }

   public void setCurrentServerEntry(@Nullable ServerInfo _snowman) {
      this.currentServerEntry = _snowman;
   }

   @Nullable
   public ServerInfo getCurrentServerEntry() {
      return this.currentServerEntry;
   }

   public boolean isInSingleplayer() {
      return this.integratedServerRunning;
   }

   public boolean isIntegratedServerRunning() {
      return this.integratedServerRunning && this.server != null;
   }

   @Nullable
   public IntegratedServer getServer() {
      return this.server;
   }

   public Snooper getSnooper() {
      return this.snooper;
   }

   public Session getSession() {
      return this.session;
   }

   public PropertyMap getSessionProperties() {
      if (this.sessionPropertyMap.isEmpty()) {
         GameProfile _snowman = this.getSessionService().fillProfileProperties(this.session.getProfile(), false);
         this.sessionPropertyMap.putAll(_snowman.getProperties());
      }

      return this.sessionPropertyMap;
   }

   public Proxy getNetworkProxy() {
      return this.netProxy;
   }

   public TextureManager getTextureManager() {
      return this.textureManager;
   }

   public ResourceManager getResourceManager() {
      return this.resourceManager;
   }

   public ResourcePackManager getResourcePackManager() {
      return this.resourcePackManager;
   }

   public ClientBuiltinResourcePackProvider getResourcePackDownloader() {
      return this.builtinPackProvider;
   }

   public File getResourcePackDir() {
      return this.resourcePackDir;
   }

   public LanguageManager getLanguageManager() {
      return this.languageManager;
   }

   public Function<Identifier, Sprite> getSpriteAtlas(Identifier id) {
      return this.bakedModelManager.method_24153(id)::getSprite;
   }

   public boolean is64Bit() {
      return this.is64Bit;
   }

   public boolean isPaused() {
      return this.paused;
   }

   public VideoWarningManager getVideoWarningManager() {
      return this.videoWarningManager;
   }

   public SoundManager getSoundManager() {
      return this.soundManager;
   }

   public MusicSound getMusicType() {
      if (this.currentScreen instanceof CreditsScreen) {
         return MusicType.CREDITS;
      } else if (this.player != null) {
         if (this.player.world.getRegistryKey() == World.END) {
            return this.inGameHud.getBossBarHud().shouldPlayDragonMusic() ? MusicType.DRAGON : MusicType.END;
         } else {
            Biome.Category _snowman = this.player.world.getBiome(this.player.getBlockPos()).getCategory();
            if (!this.musicTracker.isPlayingType(MusicType.UNDERWATER)
               && (!this.player.isSubmergedInWater() || _snowman != Biome.Category.OCEAN && _snowman != Biome.Category.RIVER)) {
               return this.player.world.getRegistryKey() != World.NETHER && this.player.abilities.creativeMode && this.player.abilities.allowFlying
                  ? MusicType.CREATIVE
                  : this.world.getBiomeAccess().method_27344(this.player.getBlockPos()).getMusic().orElse(MusicType.GAME);
            } else {
               return MusicType.UNDERWATER;
            }
         }
      } else {
         return MusicType.MENU;
      }
   }

   public MinecraftSessionService getSessionService() {
      return this.sessionService;
   }

   public PlayerSkinProvider getSkinProvider() {
      return this.skinProvider;
   }

   @Nullable
   public Entity getCameraEntity() {
      return this.cameraEntity;
   }

   public void setCameraEntity(Entity entity) {
      this.cameraEntity = entity;
      this.gameRenderer.onCameraEntitySet(entity);
   }

   public boolean hasOutline(Entity entity) {
      return entity.isGlowing()
         || this.player != null && this.player.isSpectator() && this.options.keySpectatorOutlines.isPressed() && entity.getType() == EntityType.PLAYER;
   }

   @Override
   protected Thread getThread() {
      return this.thread;
   }

   @Override
   protected Runnable createTask(Runnable runnable) {
      return runnable;
   }

   @Override
   protected boolean canExecute(Runnable task) {
      return true;
   }

   public BlockRenderManager getBlockRenderManager() {
      return this.blockRenderManager;
   }

   public EntityRenderDispatcher getEntityRenderDispatcher() {
      return this.entityRenderDispatcher;
   }

   public ItemRenderer getItemRenderer() {
      return this.itemRenderer;
   }

   public HeldItemRenderer getHeldItemRenderer() {
      return this.heldItemRenderer;
   }

   public <T> SearchableContainer<T> getSearchableContainer(SearchManager.Key<T> key) {
      return this.searchManager.get(key);
   }

   public MetricsData getMetricsData() {
      return this.metricsData;
   }

   public boolean isConnectedToRealms() {
      return this.connectedToRealms;
   }

   public void setConnectedToRealms(boolean connectedToRealms) {
      this.connectedToRealms = connectedToRealms;
   }

   public DataFixer getDataFixer() {
      return this.dataFixer;
   }

   public float getTickDelta() {
      return this.renderTickCounter.tickDelta;
   }

   public float getLastFrameDuration() {
      return this.renderTickCounter.lastFrameDuration;
   }

   public BlockColors getBlockColors() {
      return this.blockColors;
   }

   public boolean hasReducedDebugInfo() {
      return this.player != null && this.player.getReducedDebugInfo() || this.options.reducedDebugInfo;
   }

   public ToastManager getToastManager() {
      return this.toastManager;
   }

   public TutorialManager getTutorialManager() {
      return this.tutorialManager;
   }

   public boolean isWindowFocused() {
      return this.windowFocused;
   }

   public HotbarStorage getCreativeHotbarStorage() {
      return this.creativeHotbarStorage;
   }

   public BakedModelManager getBakedModelManager() {
      return this.bakedModelManager;
   }

   public PaintingManager getPaintingManager() {
      return this.paintingManager;
   }

   public StatusEffectSpriteManager getStatusEffectSpriteManager() {
      return this.statusEffectSpriteManager;
   }

   @Override
   public void onWindowFocusChanged(boolean focused) {
      this.windowFocused = focused;
   }

   public Profiler getProfiler() {
      return this.profiler;
   }

   public MinecraftClientGame getGame() {
      return this.game;
   }

   public SplashTextResourceSupplier getSplashTextLoader() {
      return this.splashTextLoader;
   }

   @Nullable
   public Overlay getOverlay() {
      return this.overlay;
   }

   public SocialInteractionsManager getSocialInteractionsManager() {
      return this.socialInteractionsManager;
   }

   public boolean shouldRenderAsync() {
      return false;
   }

   public Window getWindow() {
      return this.window;
   }

   public BufferBuilderStorage getBufferBuilders() {
      return this.bufferBuilders;
   }

   private static ResourcePackProfile createResourcePackProfile(
      String name,
      boolean alwaysEnabled,
      Supplier<ResourcePack> packFactory,
      ResourcePack pack,
      PackResourceMetadata metadata,
      ResourcePackProfile.InsertionPosition insertionPosition,
      ResourcePackSource source
   ) {
      int _snowman = metadata.getPackFormat();
      Supplier<ResourcePack> _snowmanx = packFactory;
      if (_snowman <= 3) {
         _snowmanx = createV3ResourcePackFactory(packFactory);
      }

      if (_snowman <= 4) {
         _snowmanx = createV4ResourcePackFactory(_snowmanx);
      }

      return new ResourcePackProfile(name, alwaysEnabled, _snowmanx, pack, metadata, insertionPosition, source);
   }

   private static Supplier<ResourcePack> createV3ResourcePackFactory(Supplier<ResourcePack> packFactory) {
      return () -> new Format3ResourcePack(packFactory.get(), Format3ResourcePack.NEW_TO_OLD_MAP);
   }

   private static Supplier<ResourcePack> createV4ResourcePackFactory(Supplier<ResourcePack> packFactory) {
      return () -> new Format4ResourcePack(packFactory.get());
   }

   public void resetMipmapLevels(int mipmapLevels) {
      this.bakedModelManager.resetMipmapLevels(mipmapLevels);
   }

   public static final class IntegratedResourceManager implements AutoCloseable {
      private final ResourcePackManager resourcePackManager;
      private final ServerResourceManager serverResourceManager;
      private final SaveProperties saveProperties;

      private IntegratedResourceManager(ResourcePackManager resourcePackManager, ServerResourceManager serverResourceManager, SaveProperties saveProperties) {
         this.resourcePackManager = resourcePackManager;
         this.serverResourceManager = serverResourceManager;
         this.saveProperties = saveProperties;
      }

      public ResourcePackManager getResourcePackManager() {
         return this.resourcePackManager;
      }

      public ServerResourceManager getServerResourceManager() {
         return this.serverResourceManager;
      }

      public SaveProperties getSaveProperties() {
         return this.saveProperties;
      }

      @Override
      public void close() {
         this.resourcePackManager.close();
         this.serverResourceManager.close();
      }
   }

   static enum WorldLoadAction {
      NONE,
      CREATE,
      BACKUP;

      private WorldLoadAction() {
      }
   }
}
