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
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

@Environment(EnvType.CLIENT)
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
      File file = args.directories.assetDir;
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
      YggdrasilAuthenticationService yggdrasilAuthenticationService = new YggdrasilAuthenticationService(this.netProxy);
      this.sessionService = yggdrasilAuthenticationService.createMinecraftSessionService();
      this.field_26902 = this.method_31382(yggdrasilAuthenticationService, args);
      this.session = args.network.session;
      LOGGER.info("Setting user: {}", this.session.getUsername());
      LOGGER.debug("(Session ID is {})", this.session.getSessionId());
      this.isDemo = args.game.demo;
      this.multiplayerEnabled = !args.game.multiplayerDisabled;
      this.onlineChatEnabled = !args.game.onlineChatDisabled;
      this.is64Bit = checkIs64Bit();
      this.server = null;
      String string;
      int i;
      if (this.isMultiplayerEnabled() && args.autoConnect.serverAddress != null) {
         string = args.autoConnect.serverAddress;
         i = args.autoConnect.serverPort;
      } else {
         string = null;
         i = 0;
      }

      KeybindText.setTranslator(KeyBinding::getLocalizedName);
      this.dataFixer = Schemas.getFixer();
      this.toastManager = new ToastManager(this);
      this.tutorialManager = new TutorialManager(this);
      this.thread = Thread.currentThread();
      this.options = new GameOptions(this, this.runDirectory);
      this.creativeHotbarStorage = new HotbarStorage(this.runDirectory, this.dataFixer);
      LOGGER.info("Backend library: {}", RenderSystem.getBackendDescription());
      WindowSettings lv;
      if (this.options.overrideHeight > 0 && this.options.overrideWidth > 0) {
         lv = new WindowSettings(
            this.options.overrideWidth,
            this.options.overrideHeight,
            args.windowSettings.fullscreenWidth,
            args.windowSettings.fullscreenHeight,
            args.windowSettings.fullscreen
         );
      } else {
         lv = args.windowSettings;
      }

      Util.nanoTimeSupplier = RenderSystem.initBackendSystem();
      this.windowProvider = new WindowProvider(this);
      this.window = this.windowProvider.createWindow(lv, this.options.fullscreenResolution, this.getWindowTitle());
      this.onWindowFocusChanged(true);

      try {
         InputStream inputStream = this.getResourcePackDownloader().getPack().open(ResourceType.CLIENT_RESOURCES, new Identifier("icons/icon_16x16.png"));
         InputStream inputStream2 = this.getResourcePackDownloader().getPack().open(ResourceType.CLIENT_RESOURCES, new Identifier("icons/icon_32x32.png"));
         this.window.setIcon(inputStream, inputStream2);
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
      this.skinProvider = new PlayerSkinProvider(this.textureManager, new File(file, "skins"), this.sessionService);
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
      if (string != null) {
         this.openScreen(new ConnectScreen(new TitleScreen(), this, string, i));
      } else {
         this.openScreen(new TitleScreen(true));
      }

      SplashScreen.init(this);
      List<ResourcePack> list = this.resourcePackManager.createResourcePacks();
      this.setOverlay(
         new SplashScreen(
            this,
            this.resourceManager.beginMonitoredReload(Util.getMainWorkerExecutor(), this, COMPLETED_UNIT_FUTURE, list),
            optional -> Util.ifPresentOrElse(optional, this::handleResourceReloadException, () -> {
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
      StringBuilder stringBuilder = new StringBuilder("Minecraft");
      if (this.isModded()) {
         stringBuilder.append("*");
      }

      stringBuilder.append(" ");
      stringBuilder.append(SharedConstants.getGameVersion().getName());
      ClientPlayNetworkHandler lv = this.getNetworkHandler();
      if (lv != null && lv.getConnection().isOpen()) {
         stringBuilder.append(" - ");
         if (this.server != null && !this.server.isRemote()) {
            stringBuilder.append(I18n.translate("title.singleplayer"));
         } else if (this.isConnectedToRealms()) {
            stringBuilder.append(I18n.translate("title.multiplayer.realms"));
         } else if (this.server == null && (this.currentServerEntry == null || !this.currentServerEntry.isLocal())) {
            stringBuilder.append(I18n.translate("title.multiplayer.other"));
         } else {
            stringBuilder.append(I18n.translate("title.multiplayer.lan"));
         }
      }

      return stringBuilder.toString();
   }

   private SocialInteractionsService method_31382(YggdrasilAuthenticationService yggdrasilAuthenticationService, RunArgs arg) {
      try {
         return yggdrasilAuthenticationService.createSocialInteractionsService(arg.network.session.getAccessToken());
      } catch (AuthenticationException var4) {
         LOGGER.error("Failed to verify authentication", var4);
         return new OfflineSocialInteractions();
      }
   }

   public boolean isModded() {
      return !"vanilla".equals(ClientBrandRetriever.getClientModName()) || MinecraftClient.class.getSigners() == null;
   }

   private void handleResourceReloadException(Throwable throwable) {
      if (this.resourcePackManager.getEnabledNames().size() > 1) {
         Text lv;
         if (throwable instanceof ReloadableResourceManagerImpl.PackAdditionFailedException) {
            lv = new LiteralText(((ReloadableResourceManagerImpl.PackAdditionFailedException)throwable).getPack().getName());
         } else {
            lv = null;
         }

         this.method_31186(throwable, lv);
      } else {
         Util.throwUnchecked(throwable);
      }
   }

   public void method_31186(Throwable throwable, @Nullable Text arg) {
      LOGGER.info("Caught error loading resourcepacks, removing all selected resourcepacks", throwable);
      this.resourcePackManager.setEnabledProfiles(Collections.emptyList());
      this.options.resourcePacks.clear();
      this.options.incompatibleResourcePacks.clear();
      this.options.write();
      this.reloadResources().thenRun(() -> {
         ToastManager lv = this.getToastManager();
         SystemToast.show(lv, SystemToast.Type.PACK_LOAD_FAILURE, new TranslatableText("resourcePack.load_fail"), arg);
      });
   }

   public void run() {
      this.thread = Thread.currentThread();

      try {
         boolean bl = false;

         while (this.running) {
            if (this.crashReport != null) {
               printCrashReport(this.crashReport);
               return;
            }

            try {
               TickDurationMonitor lv = TickDurationMonitor.create("Renderer");
               boolean bl2 = this.shouldMonitorTickDuration();
               this.startMonitor(bl2, lv);
               this.profiler.startTick();
               this.render(!bl);
               this.profiler.endTick();
               this.endMonitor(bl2, lv);
            } catch (OutOfMemoryError var4) {
               if (bl) {
                  throw var4;
               }

               this.cleanUpAfterCrash();
               this.openScreen(new OutOfMemoryScreen());
               System.gc();
               LOGGER.fatal("Out of memory", var4);
               bl = true;
            }
         }
      } catch (CrashException var5) {
         this.addDetailsToCrashReport(var5.getReport());
         this.cleanUpAfterCrash();
         LOGGER.fatal("Reported exception thrown!", var5);
         printCrashReport(var5.getReport());
      } catch (Throwable var6) {
         CrashReport lv3 = this.addDetailsToCrashReport(new CrashReport("Unexpected error", var6));
         LOGGER.fatal("Unreported exception thrown!", var6);
         this.cleanUpAfterCrash();
         printCrashReport(lv3);
      }
   }

   void initFont(boolean forcesUnicode) {
      this.fontManager.setIdOverrides(forcesUnicode ? ImmutableMap.of(DEFAULT_FONT_ID, UNICODE_FONT_ID) : ImmutableMap.of());
   }

   private void initializeSearchableContainers() {
      TextSearchableContainer<ItemStack> lv = new TextSearchableContainer<>(
         arg -> arg.getTooltip(null, TooltipContext.Default.NORMAL)
               .stream()
               .map(argx -> Formatting.strip(argx.getString()).trim())
               .filter(string -> !string.isEmpty()),
         arg -> Stream.of(Registry.ITEM.getId(arg.getItem()))
      );
      IdentifierSearchableContainer<ItemStack> lv2 = new IdentifierSearchableContainer<>(arg -> ItemTags.getTagGroup().getTagsFor(arg.getItem()).stream());
      DefaultedList<ItemStack> lv3 = DefaultedList.of();

      for (Item lv4 : Registry.ITEM) {
         lv4.appendStacks(ItemGroup.SEARCH, lv3);
      }

      lv3.forEach(arg3 -> {
         lv.add(arg3);
         lv2.add(arg3);
      });
      TextSearchableContainer<RecipeResultCollection> lv5 = new TextSearchableContainer<>(
         arg -> arg.getAllRecipes()
               .stream()
               .flatMap(argx -> argx.getOutput().getTooltip(null, TooltipContext.Default.NORMAL).stream())
               .map(argx -> Formatting.strip(argx.getString()).trim())
               .filter(string -> !string.isEmpty()),
         arg -> arg.getAllRecipes().stream().map(argx -> Registry.ITEM.getId(argx.getOutput().getItem()))
      );
      this.searchManager.put(SearchManager.ITEM_TOOLTIP, lv);
      this.searchManager.put(SearchManager.ITEM_TAG, lv2);
      this.searchManager.put(SearchManager.RECIPE_OUTPUT, lv5);
   }

   private void handleGlErrorByDisableVsync(int error, long description) {
      this.options.enableVsync = false;
      this.options.write();
   }

   private static boolean checkIs64Bit() {
      String[] strings = new String[]{"sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch"};

      for (String string : strings) {
         String string2 = System.getProperty(string);
         if (string2 != null && string2.contains("64")) {
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
      File file = new File(getInstance().runDirectory, "crash-reports");
      File file2 = new File(file, "crash-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + "-client.txt");
      Bootstrap.println(report.asString());
      if (report.getFile() != null) {
         Bootstrap.println("#@!@# Game crashed! Crash report saved to: #@!@# " + report.getFile());
         System.exit(-1);
      } else if (report.writeToFile(file2)) {
         Bootstrap.println("#@!@# Game crashed! Crash report saved to: #@!@# " + file2.getAbsolutePath());
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
         CompletableFuture<Void> completableFuture = new CompletableFuture<>();
         if (this.overlay instanceof SplashScreen) {
            this.resourceReloadFuture = completableFuture;
            return completableFuture;
         } else {
            this.resourcePackManager.scanPacks();
            List<ResourcePack> list = this.resourcePackManager.createResourcePacks();
            this.setOverlay(
               new SplashScreen(
                  this,
                  this.resourceManager.beginMonitoredReload(Util.getMainWorkerExecutor(), this, COMPLETED_UNIT_FUTURE, list),
                  optional -> Util.ifPresentOrElse(optional, this::handleResourceReloadException, () -> {
                        this.worldRenderer.reload();
                        completableFuture.complete(null);
                     }),
                  true
               )
            );
            return completableFuture;
         }
      }
   }

   private void checkGameData() {
      boolean bl = false;
      BlockModels lv = this.getBlockRenderManager().getModels();
      BakedModel lv2 = lv.getModelManager().getMissingModel();

      for (Block lv3 : Registry.BLOCK) {
         UnmodifiableIterator lv7 = lv3.getStateManager().getStates().iterator();

         while (lv7.hasNext()) {
            BlockState lv4 = (BlockState)lv7.next();
            if (lv4.getRenderType() == BlockRenderType.MODEL) {
               BakedModel lv5 = lv.getModel(lv4);
               if (lv5 == lv2) {
                  LOGGER.debug("Missing model for: {}", lv4);
                  bl = true;
               }
            }
         }
      }

      Sprite lv6 = lv2.getSprite();

      for (Block lv7 : Registry.BLOCK) {
         UnmodifiableIterator var18 = lv7.getStateManager().getStates().iterator();

         while (var18.hasNext()) {
            BlockState lv8 = (BlockState)var18.next();
            Sprite lv9 = lv.getSprite(lv8);
            if (!lv8.isAir() && lv9 == lv6) {
               LOGGER.debug("Missing particle icon for: {}", lv8);
               bl = true;
            }
         }
      }

      DefaultedList<ItemStack> lv10 = DefaultedList.of();

      for (Item lv11 : Registry.ITEM) {
         lv10.clear();
         lv11.appendStacks(ItemGroup.SEARCH, lv10);

         for (ItemStack lv12 : lv10) {
            String string = lv12.getTranslationKey();
            String string2 = new TranslatableText(string).getString();
            if (string2.toLowerCase(Locale.ROOT).equals(lv11.getTranslationKey())) {
               LOGGER.debug("Missing translation for: {} {} {}", lv12, string, lv12.getItem());
            }
         }
      }

      bl |= HandledScreens.validateScreens();
      if (bl) {
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
      long l = Util.getMeasuringTimeNano();
      if (this.window.shouldClose()) {
         this.scheduleStop();
      }

      if (this.resourceReloadFuture != null && !(this.overlay instanceof SplashScreen)) {
         CompletableFuture<Void> completableFuture = this.resourceReloadFuture;
         this.resourceReloadFuture = null;
         this.reloadResources().thenRun(() -> completableFuture.complete(null));
      }

      Runnable runnable;
      while ((runnable = this.renderTaskQueue.poll()) != null) {
         runnable.run();
      }

      if (tick) {
         int i = this.renderTickCounter.beginRenderTick(Util.getMeasuringTimeMs());
         this.profiler.push("scheduledExecutables");
         this.runTasks();
         this.profiler.pop();
         this.profiler.push("tick");

         for (int j = 0; j < Math.min(10, i); j++) {
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
         this.gameRenderer.render(this.paused ? this.pausedTickDelta : this.renderTickCounter.tickDelta, l, tick);
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
      int k = this.getFramerateLimit();
      if ((double)k < Option.FRAMERATE_LIMIT.getMax()) {
         RenderSystem.limitDisplayFPS(k);
      }

      this.profiler.swap("yield");
      Thread.yield();
      this.profiler.pop();
      this.window.setPhase("Post render");
      this.fpsCounter++;
      boolean bl2 = this.isIntegratedServerRunning()
         && (this.currentScreen != null && this.currentScreen.isPauseScreen() || this.overlay != null && this.overlay.pausesGame())
         && !this.server.isRemote();
      if (this.paused != bl2) {
         if (this.paused) {
            this.pausedTickDelta = this.renderTickCounter.tickDelta;
         } else {
            this.renderTickCounter.tickDelta = this.pausedTickDelta;
         }

         this.paused = bl2;
      }

      long m = Util.getMeasuringTimeNano();
      this.metricsData.pushSample(m - this.lastMetricsSampleTime);
      this.lastMetricsSampleTime = m;
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
      int i = this.window.calculateScaleFactor(this.options.guiScale, this.forcesUnicodeFont());
      this.window.setScaleFactor((double)i);
      if (this.currentScreen != null) {
         this.currentScreen.resize(this, this.window.getScaledWidth(), this.window.getScaledHeight());
      }

      Framebuffer lv = this.getFramebuffer();
      lv.resize(this.window.getFramebufferWidth(), this.window.getFramebufferHeight(), IS_SYSTEM_MAC);
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
         List<ProfilerTiming> list = this.tickProfilerResult.getTimings(this.openProfilerSection);
         if (!list.isEmpty()) {
            ProfilerTiming lv = list.remove(0);
            if (digit == 0) {
               if (!lv.name.isEmpty()) {
                  int j = this.openProfilerSection.lastIndexOf(30);
                  if (j >= 0) {
                     this.openProfilerSection = this.openProfilerSection.substring(0, j);
                  }
               }
            } else {
               digit--;
               if (digit < list.size() && !"unspecified".equals(list.get(digit).name)) {
                  if (!this.openProfilerSection.isEmpty()) {
                     this.openProfilerSection = this.openProfilerSection + '\u001e';
                  }

                  this.openProfilerSection = this.openProfilerSection + list.get(digit).name;
               }
            }
         }
      }
   }

   private void drawProfilerResults(MatrixStack matrices, ProfileResult profileResult) {
      List<ProfilerTiming> list = profileResult.getTimings(this.openProfilerSection);
      ProfilerTiming lv = list.remove(0);
      RenderSystem.clear(256, IS_SYSTEM_MAC);
      RenderSystem.matrixMode(5889);
      RenderSystem.loadIdentity();
      RenderSystem.ortho(0.0, (double)this.window.getFramebufferWidth(), (double)this.window.getFramebufferHeight(), 0.0, 1000.0, 3000.0);
      RenderSystem.matrixMode(5888);
      RenderSystem.loadIdentity();
      RenderSystem.translatef(0.0F, 0.0F, -2000.0F);
      RenderSystem.lineWidth(1.0F);
      RenderSystem.disableTexture();
      Tessellator lv2 = Tessellator.getInstance();
      BufferBuilder lv3 = lv2.getBuffer();
      int i = 160;
      int j = this.window.getFramebufferWidth() - 160 - 10;
      int k = this.window.getFramebufferHeight() - 320;
      RenderSystem.enableBlend();
      lv3.begin(7, VertexFormats.POSITION_COLOR);
      lv3.vertex((double)((float)j - 176.0F), (double)((float)k - 96.0F - 16.0F), 0.0).color(200, 0, 0, 0).next();
      lv3.vertex((double)((float)j - 176.0F), (double)(k + 320), 0.0).color(200, 0, 0, 0).next();
      lv3.vertex((double)((float)j + 176.0F), (double)(k + 320), 0.0).color(200, 0, 0, 0).next();
      lv3.vertex((double)((float)j + 176.0F), (double)((float)k - 96.0F - 16.0F), 0.0).color(200, 0, 0, 0).next();
      lv2.draw();
      RenderSystem.disableBlend();
      double d = 0.0;

      for (ProfilerTiming lv4 : list) {
         int l = MathHelper.floor(lv4.parentSectionUsagePercentage / 4.0) + 1;
         lv3.begin(6, VertexFormats.POSITION_COLOR);
         int m = lv4.getColor();
         int n = m >> 16 & 0xFF;
         int o = m >> 8 & 0xFF;
         int p = m & 0xFF;
         lv3.vertex((double)j, (double)k, 0.0).color(n, o, p, 255).next();

         for (int q = l; q >= 0; q--) {
            float f = (float)((d + lv4.parentSectionUsagePercentage * (double)q / (double)l) * (float) (Math.PI * 2) / 100.0);
            float g = MathHelper.sin(f) * 160.0F;
            float h = MathHelper.cos(f) * 160.0F * 0.5F;
            lv3.vertex((double)((float)j + g), (double)((float)k - h), 0.0).color(n, o, p, 255).next();
         }

         lv2.draw();
         lv3.begin(5, VertexFormats.POSITION_COLOR);

         for (int r = l; r >= 0; r--) {
            float s = (float)((d + lv4.parentSectionUsagePercentage * (double)r / (double)l) * (float) (Math.PI * 2) / 100.0);
            float t = MathHelper.sin(s) * 160.0F;
            float u = MathHelper.cos(s) * 160.0F * 0.5F;
            if (!(u > 0.0F)) {
               lv3.vertex((double)((float)j + t), (double)((float)k - u), 0.0).color(n >> 1, o >> 1, p >> 1, 255).next();
               lv3.vertex((double)((float)j + t), (double)((float)k - u + 10.0F), 0.0).color(n >> 1, o >> 1, p >> 1, 255).next();
            }
         }

         lv2.draw();
         d += lv4.parentSectionUsagePercentage;
      }

      DecimalFormat decimalFormat = new DecimalFormat("##0.00");
      decimalFormat.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
      RenderSystem.enableTexture();
      String string = ProfileResult.getHumanReadableName(lv.name);
      String string2 = "";
      if (!"unspecified".equals(string)) {
         string2 = string2 + "[0] ";
      }

      if (string.isEmpty()) {
         string2 = string2 + "ROOT ";
      } else {
         string2 = string2 + string + ' ';
      }

      int v = 16777215;
      this.textRenderer.drawWithShadow(matrices, string2, (float)(j - 160), (float)(k - 80 - 16), 16777215);
      string2 = decimalFormat.format(lv.totalUsagePercentage) + "%";
      this.textRenderer.drawWithShadow(matrices, string2, (float)(j + 160 - this.textRenderer.getWidth(string2)), (float)(k - 80 - 16), 16777215);

      for (int w = 0; w < list.size(); w++) {
         ProfilerTiming lv5 = list.get(w);
         StringBuilder stringBuilder = new StringBuilder();
         if ("unspecified".equals(lv5.name)) {
            stringBuilder.append("[?] ");
         } else {
            stringBuilder.append("[").append(w + 1).append("] ");
         }

         String string3 = stringBuilder.append(lv5.name).toString();
         this.textRenderer.drawWithShadow(matrices, string3, (float)(j - 160), (float)(k + 80 + w * 8 + 20), lv5.getColor());
         string3 = decimalFormat.format(lv5.parentSectionUsagePercentage) + "%";
         this.textRenderer
            .drawWithShadow(matrices, string3, (float)(j + 160 - 50 - this.textRenderer.getWidth(string3)), (float)(k + 80 + w * 8 + 20), lv5.getColor());
         string3 = decimalFormat.format(lv5.totalUsagePercentage) + "%";
         this.textRenderer
            .drawWithShadow(matrices, string3, (float)(j + 160 - this.textRenderer.getWidth(string3)), (float)(k + 80 + w * 8 + 20), lv5.getColor());
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
         boolean bl2 = this.isIntegratedServerRunning() && !this.server.isRemote();
         if (bl2) {
            this.openScreen(new GameMenuScreen(!pause));
            this.soundManager.pauseAll();
         } else {
            this.openScreen(new GameMenuScreen(true));
         }
      }
   }

   private void handleBlockBreaking(boolean bl) {
      if (!bl) {
         this.attackCooldown = 0;
      }

      if (this.attackCooldown <= 0 && !this.player.isUsingItem()) {
         if (bl && this.crosshairTarget != null && this.crosshairTarget.getType() == HitResult.Type.BLOCK) {
            BlockHitResult lv = (BlockHitResult)this.crosshairTarget;
            BlockPos lv2 = lv.getBlockPos();
            if (!this.world.getBlockState(lv2).isAir()) {
               Direction lv3 = lv.getSide();
               if (this.interactionManager.updateBlockBreakingProgress(lv2, lv3)) {
                  this.particleManager.addBlockBreakingParticles(lv2, lv3);
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
                  BlockHitResult lv = (BlockHitResult)this.crosshairTarget;
                  BlockPos lv2 = lv.getBlockPos();
                  if (!this.world.getBlockState(lv2).isAir()) {
                     this.interactionManager.attackBlock(lv2, lv.getSide());
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

            for (Hand lv : Hand.values()) {
               ItemStack lv2 = this.player.getStackInHand(lv);
               if (this.crosshairTarget != null) {
                  switch (this.crosshairTarget.getType()) {
                     case ENTITY:
                        EntityHitResult lv3 = (EntityHitResult)this.crosshairTarget;
                        Entity lv4 = lv3.getEntity();
                        ActionResult lv5 = this.interactionManager.interactEntityAtLocation(this.player, lv4, lv3, lv);
                        if (!lv5.isAccepted()) {
                           lv5 = this.interactionManager.interactEntity(this.player, lv4, lv);
                        }

                        if (lv5.isAccepted()) {
                           if (lv5.shouldSwingHand()) {
                              this.player.swingHand(lv);
                           }

                           return;
                        }
                        break;
                     case BLOCK:
                        BlockHitResult lv6 = (BlockHitResult)this.crosshairTarget;
                        int i = lv2.getCount();
                        ActionResult lv7 = this.interactionManager.interactBlock(this.player, this.world, lv, lv6);
                        if (lv7.isAccepted()) {
                           if (lv7.shouldSwingHand()) {
                              this.player.swingHand(lv);
                              if (!lv2.isEmpty() && (lv2.getCount() != i || this.interactionManager.hasCreativeInventory())) {
                                 this.gameRenderer.firstPersonRenderer.resetEquipProgress(lv);
                              }
                           }

                           return;
                        }

                        if (lv7 == ActionResult.FAIL) {
                           return;
                        }
                  }
               }

               if (!lv2.isEmpty()) {
                  ActionResult lv8 = this.interactionManager.interactItem(this.player, this.world, lv);
                  if (lv8.isAccepted()) {
                     if (lv8.shouldSwingHand()) {
                        this.player.swingHand(lv);
                     }

                     this.gameRenderer.firstPersonRenderer.resetEquipProgress(lv);
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
               Text lv = new TranslatableText("tutorial.socialInteractions.title");
               Text lv2 = new TranslatableText("tutorial.socialInteractions.description", TutorialManager.getKeybindName("socialInteractions"));
               this.field_26843 = new TutorialToast(TutorialToast.Type.SOCIAL_INTERACTIONS, lv, lv2, true);
               this.tutorialManager.method_31365(this.field_26843, 160);
               this.options.joinedFirstServer = true;
               this.options.write();
            }

            this.tutorialManager.tick();

            try {
               this.world.tick(() -> true);
            } catch (Throwable var4) {
               CrashReport lv3 = CrashReport.create(var4, "Exception in world tick");
               if (this.world == null) {
                  CrashReportSection lv4 = lv3.addElement("Affected level");
                  lv4.add("Problem", "Level is null!");
               } else {
                  this.world.addDetailsToCrashReport(lv3);
               }

               throw new CrashException(lv3);
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
         Perspective lv = this.options.getPerspective();
         this.options.setPerspective(this.options.getPerspective().next());
         if (lv.isFirstPerson() != this.options.getPerspective().isFirstPerson()) {
            this.gameRenderer.onCameraEntitySet(this.options.getPerspective().isFirstPerson() ? this.getCameraEntity() : null);
         }

         this.worldRenderer.scheduleTerrainUpdate();
      }

      while (this.options.keySmoothCamera.wasPressed()) {
         this.options.smoothCameraEnabled = !this.options.smoothCameraEnabled;
      }

      for (int i = 0; i < 9; i++) {
         boolean bl = this.options.keySaveToolbarActivator.isPressed();
         boolean bl2 = this.options.keyLoadToolbarActivator.isPressed();
         if (this.options.keysHotbar[i].wasPressed()) {
            if (this.player.isSpectator()) {
               this.inGameHud.getSpectatorHud().selectSlot(i);
            } else if (!this.player.isCreative() || this.currentScreen != null || !bl2 && !bl) {
               this.player.inventory.selectedSlot = i;
            } else {
               CreativeInventoryScreen.onHotbarKeyPress(this, i, bl2, bl);
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

      boolean bl3 = this.options.chatVisibility != ChatVisibility.HIDDEN;
      if (bl3) {
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

   public static DataPackSettings method_29598(LevelStorage.Session arg) {
      MinecraftServer.convertLevel(arg);
      DataPackSettings lv = arg.getDataPackSettings();
      if (lv == null) {
         throw new IllegalStateException("Failed to load data pack config");
      } else {
         return lv;
      }
   }

   public static SaveProperties createSaveProperties(
      LevelStorage.Session session, DynamicRegistryManager.Impl registryTracker, ResourceManager resourceManager, DataPackSettings arg4
   ) {
      RegistryOps<Tag> lv = RegistryOps.of(NbtOps.INSTANCE, resourceManager, registryTracker);
      SaveProperties lv2 = session.readLevelProperties(lv, arg4);
      if (lv2 == null) {
         throw new IllegalStateException("Failed to load world");
      } else {
         return lv2;
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
         arg2 -> levelInfo.getDataPackSettings(),
         (arg4, arg5, arg6, arg7) -> {
            RegistryReadingOps<JsonElement> lv = RegistryReadingOps.of(JsonOps.INSTANCE, registryTracker);
            RegistryOps<JsonElement> lv2 = RegistryOps.of(JsonOps.INSTANCE, arg6, registryTracker);
            DataResult<GeneratorOptions> dataResult = GeneratorOptions.CODEC
               .encodeStart(lv, generatorOptions)
               .setLifecycle(Lifecycle.stable())
               .flatMap(jsonElement -> GeneratorOptions.CODEC.parse(lv2, jsonElement));
            GeneratorOptions lv3 = dataResult.resultOrPartial(Util.method_29188("Error reading worldgen settings after loading data packs: ", LOGGER::error))
               .orElse(generatorOptions);
            return new LevelProperties(levelInfo, lv3, dataResult.lifecycle());
         },
         false,
         MinecraftClient.WorldLoadAction.CREATE
      );
   }

   private void startIntegratedServer(
      String worldName,
      DynamicRegistryManager.Impl registryTracker,
      Function<LevelStorage.Session, DataPackSettings> function,
      Function4<LevelStorage.Session, DynamicRegistryManager.Impl, ResourceManager, DataPackSettings, SaveProperties> function4,
      boolean safeMode,
      MinecraftClient.WorldLoadAction arg2
   ) {
      LevelStorage.Session lv;
      try {
         lv = this.levelStorage.createSession(worldName);
      } catch (IOException var21) {
         LOGGER.warn("Failed to read level {} data", worldName, var21);
         SystemToast.addWorldAccessFailureToast(this, worldName);
         this.openScreen(null);
         return;
      }

      MinecraftClient.IntegratedResourceManager lv3;
      try {
         lv3 = this.method_29604(registryTracker, function, function4, safeMode, lv);
      } catch (Exception var20) {
         LOGGER.warn("Failed to load datapacks, can't proceed with server load", var20);
         this.openScreen(new DatapackFailureScreen(() -> this.startIntegratedServer(worldName, registryTracker, function, function4, true, arg2)));

         try {
            lv.close();
         } catch (IOException var16) {
            LOGGER.warn("Failed to unlock access to level {}", worldName, var16);
         }

         return;
      }

      SaveProperties lv5 = lv3.getSaveProperties();
      boolean bl2 = lv5.getGeneratorOptions().isLegacyCustomizedType();
      boolean bl3 = lv5.getLifecycle() != Lifecycle.stable();
      if (arg2 == MinecraftClient.WorldLoadAction.NONE || !bl2 && !bl3) {
         this.disconnect();
         this.worldGenProgressTracker.set(null);

         try {
            lv.backupLevelDataFile(registryTracker, lv5);
            lv3.getServerResourceManager().loadRegistryTags();
            YggdrasilAuthenticationService yggdrasilAuthenticationService = new YggdrasilAuthenticationService(this.netProxy);
            MinecraftSessionService minecraftSessionService = yggdrasilAuthenticationService.createMinecraftSessionService();
            GameProfileRepository gameProfileRepository = yggdrasilAuthenticationService.createProfileRepository();
            UserCache lv6 = new UserCache(gameProfileRepository, new File(this.runDirectory, MinecraftServer.USER_CACHE_FILE.getName()));
            SkullBlockEntity.setUserCache(lv6);
            SkullBlockEntity.setSessionService(minecraftSessionService);
            UserCache.setUseRemote(false);
            this.server = MinecraftServer.startServer(
               serverThread -> new IntegratedServer(
                     serverThread,
                     this,
                     registryTracker,
                     lv,
                     lv3.getResourcePackManager(),
                     lv3.getServerResourceManager(),
                     lv5,
                     minecraftSessionService,
                     gameProfileRepository,
                     lv6,
                     i -> {
                        WorldGenerationProgressTracker lvx = new WorldGenerationProgressTracker(i + 0);
                        lvx.start();
                        this.worldGenProgressTracker.set(lvx);
                        return new QueueingWorldGenerationProgressListener(lvx, this.renderTaskQueue::add);
                     }
                  )
            );
            this.integratedServerRunning = true;
         } catch (Throwable var19) {
            CrashReport lv7 = CrashReport.create(var19, "Starting integrated server");
            CrashReportSection lv8 = lv7.addElement("Starting integrated server");
            lv8.add("Level ID", worldName);
            lv8.add("Level Name", lv5.getLevelName());
            throw new CrashException(lv7);
         }

         while (this.worldGenProgressTracker.get() == null) {
            Thread.yield();
         }

         LevelLoadingScreen lv9 = new LevelLoadingScreen(this.worldGenProgressTracker.get());
         this.openScreen(lv9);
         this.profiler.push("waitForServer");

         while (!this.server.isLoading()) {
            lv9.tick();
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
         SocketAddress socketAddress = this.server.getNetworkIo().bindLocal();
         ClientConnection lv10 = ClientConnection.connectLocal(socketAddress);
         lv10.setPacketListener(new ClientLoginNetworkHandler(lv10, this, null, arg -> {
         }));
         lv10.send(new HandshakeC2SPacket(socketAddress.toString(), 0, NetworkState.LOGIN));
         lv10.send(new LoginHelloC2SPacket(this.getSession().getProfile()));
         this.connection = lv10;
      } else {
         this.method_29601(
            arg2,
            worldName,
            bl2,
            () -> this.startIntegratedServer(worldName, registryTracker, function, function4, safeMode, MinecraftClient.WorldLoadAction.NONE)
         );
         lv3.close();

         try {
            lv.close();
         } catch (IOException var17) {
            LOGGER.warn("Failed to unlock access to level {}", worldName, var17);
         }
      }
   }

   private void method_29601(MinecraftClient.WorldLoadAction arg, String string, boolean bl, Runnable runnable) {
      if (arg == MinecraftClient.WorldLoadAction.BACKUP) {
         Text lv;
         Text lv2;
         if (bl) {
            lv = new TranslatableText("selectWorld.backupQuestion.customized");
            lv2 = new TranslatableText("selectWorld.backupWarning.customized");
         } else {
            lv = new TranslatableText("selectWorld.backupQuestion.experimental");
            lv2 = new TranslatableText("selectWorld.backupWarning.experimental");
         }

         this.openScreen(new BackupPromptScreen(null, (blx, bl2) -> {
            if (blx) {
               EditWorldScreen.method_29784(this.levelStorage, string);
            }

            runnable.run();
         }, lv, lv2, false));
      } else {
         this.openScreen(
            new ConfirmScreen(
               blx -> {
                  if (blx) {
                     runnable.run();
                  } else {
                     this.openScreen(null);

                     try (LevelStorage.Session lv = this.levelStorage.createSession(string)) {
                        lv.deleteSessionLock();
                     } catch (IOException var17) {
                        SystemToast.addWorldDeleteFailureToast(this, string);
                        LOGGER.error("Failed to delete world {}", string, var17);
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
      DynamicRegistryManager.Impl arg,
      Function<LevelStorage.Session, DataPackSettings> function,
      Function4<LevelStorage.Session, DynamicRegistryManager.Impl, ResourceManager, DataPackSettings, SaveProperties> function4,
      boolean bl,
      LevelStorage.Session arg2
   ) throws InterruptedException, ExecutionException {
      DataPackSettings lv = function.apply(arg2);
      ResourcePackManager lv2 = new ResourcePackManager(
         new VanillaDataPackProvider(), new FileResourcePackProvider(arg2.getDirectory(WorldSavePath.DATAPACKS).toFile(), ResourcePackSource.PACK_SOURCE_WORLD)
      );

      try {
         DataPackSettings lv3 = MinecraftServer.loadDataPacks(lv2, lv, bl);
         CompletableFuture<ServerResourceManager> completableFuture = ServerResourceManager.reload(
            lv2.createResourcePacks(), CommandManager.RegistrationEnvironment.INTEGRATED, 2, Util.getMainWorkerExecutor(), this
         );
         this.runTasks(completableFuture::isDone);
         ServerResourceManager lv4 = completableFuture.get();
         SaveProperties lv5 = (SaveProperties)function4.apply(arg2, arg, lv4.getResourceManager(), lv3);
         return new MinecraftClient.IntegratedResourceManager(lv2, lv4, lv5);
      } catch (ExecutionException | InterruptedException var12) {
         lv2.close();
         throw var12;
      }
   }

   public void joinWorld(ClientWorld world) {
      ProgressScreen lv = new ProgressScreen();
      lv.method_15412(new TranslatableText("connect.joining"));
      this.reset(lv);
      this.world = world;
      this.setWorld(world);
      if (!this.integratedServerRunning) {
         AuthenticationService authenticationService = new YggdrasilAuthenticationService(this.netProxy);
         MinecraftSessionService minecraftSessionService = authenticationService.createMinecraftSessionService();
         GameProfileRepository gameProfileRepository = authenticationService.createProfileRepository();
         UserCache lv2 = new UserCache(gameProfileRepository, new File(this.runDirectory, MinecraftServer.USER_CACHE_FILE.getName()));
         SkullBlockEntity.setUserCache(lv2);
         SkullBlockEntity.setSessionService(minecraftSessionService);
         UserCache.setUseRemote(false);
      }
   }

   public void disconnect() {
      this.disconnect(new ProgressScreen());
   }

   public void disconnect(Screen screen) {
      ClientPlayNetworkHandler lv = this.getNetworkHandler();
      if (lv != null) {
         this.cancelTasks();
         lv.clearWorld();
      }

      IntegratedServer lv2 = this.server;
      this.server = null;
      this.gameRenderer.reset();
      this.interactionManager = null;
      NarratorManager.INSTANCE.clear();
      this.reset(screen);
      if (this.world != null) {
         if (lv2 != null) {
            this.profiler.push("waitForServer");

            while (!lv2.isStopping()) {
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

   public void method_29970(Screen arg) {
      this.profiler.push("forcedTick");
      this.openScreen(arg);
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
         boolean bl = this.player.abilities.creativeMode;
         BlockEntity lv = null;
         HitResult.Type lv2 = this.crosshairTarget.getType();
         ItemStack lv6;
         if (lv2 == HitResult.Type.BLOCK) {
            BlockPos lv3 = ((BlockHitResult)this.crosshairTarget).getBlockPos();
            BlockState lv4 = this.world.getBlockState(lv3);
            Block lv5 = lv4.getBlock();
            if (lv4.isAir()) {
               return;
            }

            lv6 = lv5.getPickStack(this.world, lv3, lv4);
            if (lv6.isEmpty()) {
               return;
            }

            if (bl && Screen.hasControlDown() && lv5.hasBlockEntity()) {
               lv = this.world.getBlockEntity(lv3);
            }
         } else {
            if (lv2 != HitResult.Type.ENTITY || !bl) {
               return;
            }

            Entity lv7 = ((EntityHitResult)this.crosshairTarget).getEntity();
            if (lv7 instanceof PaintingEntity) {
               lv6 = new ItemStack(Items.PAINTING);
            } else if (lv7 instanceof LeashKnotEntity) {
               lv6 = new ItemStack(Items.LEAD);
            } else if (lv7 instanceof ItemFrameEntity) {
               ItemFrameEntity lv10 = (ItemFrameEntity)lv7;
               ItemStack lv11 = lv10.getHeldItemStack();
               if (lv11.isEmpty()) {
                  lv6 = new ItemStack(Items.ITEM_FRAME);
               } else {
                  lv6 = lv11.copy();
               }
            } else if (lv7 instanceof AbstractMinecartEntity) {
               AbstractMinecartEntity lv14 = (AbstractMinecartEntity)lv7;
               Item lv15;
               switch (lv14.getMinecartType()) {
                  case FURNACE:
                     lv15 = Items.FURNACE_MINECART;
                     break;
                  case CHEST:
                     lv15 = Items.CHEST_MINECART;
                     break;
                  case TNT:
                     lv15 = Items.TNT_MINECART;
                     break;
                  case HOPPER:
                     lv15 = Items.HOPPER_MINECART;
                     break;
                  case COMMAND_BLOCK:
                     lv15 = Items.COMMAND_BLOCK_MINECART;
                     break;
                  default:
                     lv15 = Items.MINECART;
               }

               lv6 = new ItemStack(lv15);
            } else if (lv7 instanceof BoatEntity) {
               lv6 = new ItemStack(((BoatEntity)lv7).asItem());
            } else if (lv7 instanceof ArmorStandEntity) {
               lv6 = new ItemStack(Items.ARMOR_STAND);
            } else if (lv7 instanceof EndCrystalEntity) {
               lv6 = new ItemStack(Items.END_CRYSTAL);
            } else {
               SpawnEggItem lv25 = SpawnEggItem.forEntity(lv7.getType());
               if (lv25 == null) {
                  return;
               }

               lv6 = new ItemStack(lv25);
            }
         }

         if (lv6.isEmpty()) {
            String string = "";
            if (lv2 == HitResult.Type.BLOCK) {
               string = Registry.BLOCK.getId(this.world.getBlockState(((BlockHitResult)this.crosshairTarget).getBlockPos()).getBlock()).toString();
            } else if (lv2 == HitResult.Type.ENTITY) {
               string = Registry.ENTITY_TYPE.getId(((EntityHitResult)this.crosshairTarget).getEntity().getType()).toString();
            }

            LOGGER.warn("Picking on: [{}] {} gave null item", lv2, string);
         } else {
            PlayerInventory lv28 = this.player.inventory;
            if (lv != null) {
               this.addBlockEntityNbt(lv6, lv);
            }

            int i = lv28.getSlotWithStack(lv6);
            if (bl) {
               lv28.addPickBlock(lv6);
               this.interactionManager.clickCreativeStack(this.player.getStackInHand(Hand.MAIN_HAND), 36 + lv28.selectedSlot);
            } else if (i != -1) {
               if (PlayerInventory.isValidHotbarIndex(i)) {
                  lv28.selectedSlot = i;
               } else {
                  this.interactionManager.pickFromInventory(i);
               }
            }
         }
      }
   }

   private ItemStack addBlockEntityNbt(ItemStack stack, BlockEntity blockEntity) {
      CompoundTag lv = blockEntity.toTag(new CompoundTag());
      if (stack.getItem() instanceof SkullItem && lv.contains("SkullOwner")) {
         CompoundTag lv2 = lv.getCompound("SkullOwner");
         stack.getOrCreateTag().put("SkullOwner", lv2);
         return stack;
      } else {
         stack.putSubTag("BlockEntityTag", lv);
         CompoundTag lv3 = new CompoundTag();
         ListTag lv4 = new ListTag();
         lv4.add(StringTag.of("\"(+NBT)\""));
         lv3.put("Lore", lv4);
         stack.putSubTag("display", lv3);
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
      CrashReportSection lv = report.getSystemDetailsSection();
      lv.add("Launched Version", () -> version);
      lv.add("Backend library", RenderSystem::getBackendDescription);
      lv.add("Backend API", RenderSystem::getApiDescription);
      lv.add("GL Caps", RenderSystem::getCapsString);
      lv.add("Using VBOs", () -> "Yes");
      lv.add(
         "Is Modded",
         () -> {
            String string = ClientBrandRetriever.getClientModName();
            if (!"vanilla".equals(string)) {
               return "Definitely; Client brand changed to '" + string + "'";
            } else {
               return MinecraftClient.class.getSigners() == null
                  ? "Very likely; Jar signature invalidated"
                  : "Probably not. Jar signature remains and client brand is untouched.";
            }
         }
      );
      lv.add("Type", "Client (map_client.txt)");
      if (options != null) {
         if (instance != null) {
            String string2 = instance.getVideoWarningManager().method_30920();
            if (string2 != null) {
               lv.add("GPU Warnings", string2);
            }
         }

         lv.add("Graphics mode", options.graphicsMode);
         lv.add("Resource Packs", () -> {
            StringBuilder stringBuilder = new StringBuilder();

            for (String string : options.resourcePacks) {
               if (stringBuilder.length() > 0) {
                  stringBuilder.append(", ");
               }

               stringBuilder.append(string);
               if (options.incompatibleResourcePacks.contains(string)) {
                  stringBuilder.append(" (incompatible)");
               }
            }

            return stringBuilder.toString();
         });
      }

      if (languageManager != null) {
         lv.add("Current Language", () -> languageManager.getLanguage().toString());
      }

      lv.add("CPU", GlDebugInfo::getCpuInfo);
   }

   public static MinecraftClient getInstance() {
      return instance;
   }

   public CompletableFuture<Void> reloadResourcesConcurrently() {
      return this.submit(this::reloadResources).thenCompose(completableFuture -> (CompletionStage<Void>)completableFuture);
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
      String string = ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN ? "little" : "big";
      snooper.addInfo("endianness", string);
      snooper.addInfo("subtitles", this.options.showSubtitles);
      snooper.addInfo("touch", this.options.touchscreen ? "touch" : "mouse");
      int i = 0;

      for (ResourcePackProfile lv : this.resourcePackManager.getEnabledProfiles()) {
         if (!lv.isAlwaysEnabled() && !lv.isPinned()) {
            snooper.addInfo("resource_pack[" + i++ + "]", lv.getName());
         }
      }

      snooper.addInfo("resource_packs", i);
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

   public void setCurrentServerEntry(@Nullable ServerInfo arg) {
      this.currentServerEntry = arg;
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
         GameProfile gameProfile = this.getSessionService().fillProfileProperties(this.session.getProfile(), false);
         this.sessionPropertyMap.putAll(gameProfile.getProperties());
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
            Biome.Category lv = this.player.world.getBiome(this.player.getBlockPos()).getCategory();
            if (!this.musicTracker.isPlayingType(MusicType.UNDERWATER)
               && (!this.player.isSubmergedInWater() || lv != Biome.Category.OCEAN && lv != Biome.Category.RIVER)) {
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
      int i = metadata.getPackFormat();
      Supplier<ResourcePack> supplier2 = packFactory;
      if (i <= 3) {
         supplier2 = createV3ResourcePackFactory(packFactory);
      }

      if (i <= 4) {
         supplier2 = createV4ResourcePackFactory(supplier2);
      }

      return new ResourcePackProfile(name, alwaysEnabled, supplier2, pack, metadata, insertionPosition, source);
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

   @Environment(EnvType.CLIENT)
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

   @Environment(EnvType.CLIENT)
   static enum WorldLoadAction {
      NONE,
      CREATE,
      BACKUP;

      private WorldLoadAction() {
      }
   }
}
