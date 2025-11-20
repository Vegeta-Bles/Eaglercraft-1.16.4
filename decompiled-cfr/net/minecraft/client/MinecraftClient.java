/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Multimap
 *  com.google.common.collect.Queues
 *  com.mojang.authlib.GameProfile
 *  com.mojang.authlib.GameProfileRepository
 *  com.mojang.authlib.exceptions.AuthenticationException
 *  com.mojang.authlib.minecraft.MinecraftSessionService
 *  com.mojang.authlib.minecraft.OfflineSocialInteractions
 *  com.mojang.authlib.minecraft.SocialInteractionsService
 *  com.mojang.authlib.properties.PropertyMap
 *  com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService
 *  com.mojang.datafixers.DataFixer
 *  com.mojang.datafixers.util.Function4
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.JsonOps
 *  com.mojang.serialization.Lifecycle
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Queues;
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
import com.mojang.serialization.DynamicOps;
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
import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.Bootstrap;
import net.minecraft.SharedConstants;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.SkullBlockEntity;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClientGame;
import net.minecraft.client.Mouse;
import net.minecraft.client.RunArgs;
import net.minecraft.client.WindowEventHandler;
import net.minecraft.client.WindowSettings;
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
import net.minecraft.item.ItemConvertible;
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

public class MinecraftClient
extends ReentrantThreadExecutor<Runnable>
implements SnooperListener,
WindowEventHandler {
    private static MinecraftClient instance;
    private static final Logger LOGGER;
    public static final boolean IS_SYSTEM_MAC;
    public static final Identifier DEFAULT_FONT_ID;
    public static final Identifier UNICODE_FONT_ID;
    public static final Identifier ALT_TEXT_RENDERER_ID;
    private static final CompletableFuture<Unit> COMPLETED_UNIT_FUTURE;
    private static final Text field_26841;
    private final File resourcePackDir;
    private final PropertyMap sessionPropertyMap;
    private final TextureManager textureManager;
    private final DataFixer dataFixer;
    private final WindowProvider windowProvider;
    private final Window window;
    private final RenderTickCounter renderTickCounter = new RenderTickCounter(20.0f, 0L);
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
    private final AtomicReference<WorldGenerationProgressTracker> worldGenProgressTracker = new AtomicReference();
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
    public static byte[] memoryReservedForCrash;
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
        Object object;
        int _snowman3;
        instance = this;
        this.runDirectory = args.directories.runDir;
        File file = args.directories.assetDir;
        this.resourcePackDir = args.directories.resourcePackDir;
        this.gameVersion = args.game.version;
        this.versionType = args.game.versionType;
        this.sessionPropertyMap = args.network.profileProperties;
        this.builtinPackProvider = new ClientBuiltinResourcePackProvider(new File(this.runDirectory, "server-resource-packs"), args.directories.getResourceIndex());
        this.resourcePackManager = new ResourcePackManager(MinecraftClient::createResourcePackProfile, this.builtinPackProvider, new FileResourcePackProvider(this.resourcePackDir, ResourcePackSource.field_25347));
        this.netProxy = args.network.netProxy;
        YggdrasilAuthenticationService _snowman2 = new YggdrasilAuthenticationService(this.netProxy);
        this.sessionService = _snowman2.createMinecraftSessionService();
        this.field_26902 = this.method_31382(_snowman2, args);
        this.session = args.network.session;
        LOGGER.info("Setting user: {}", (Object)this.session.getUsername());
        LOGGER.debug("(Session ID is {})", (Object)this.session.getSessionId());
        this.isDemo = args.game.demo;
        this.multiplayerEnabled = !args.game.multiplayerDisabled;
        this.onlineChatEnabled = !args.game.onlineChatDisabled;
        this.is64Bit = MinecraftClient.checkIs64Bit();
        this.server = null;
        if (this.isMultiplayerEnabled() && args.autoConnect.serverAddress != null) {
            String string = args.autoConnect.serverAddress;
            _snowman3 = args.autoConnect.serverPort;
        } else {
            string = null;
            _snowman3 = 0;
        }
        KeybindText.setTranslator(KeyBinding::getLocalizedName);
        this.dataFixer = Schemas.getFixer();
        this.toastManager = new ToastManager(this);
        this.tutorialManager = new TutorialManager(this);
        this.thread = Thread.currentThread();
        this.options = new GameOptions(this, this.runDirectory);
        this.creativeHotbarStorage = new HotbarStorage(this.runDirectory, this.dataFixer);
        LOGGER.info("Backend library: {}", (Object)RenderSystem.getBackendDescription());
        WindowSettings windowSettings = this.options.overrideHeight > 0 && this.options.overrideWidth > 0 ? new WindowSettings(this.options.overrideWidth, this.options.overrideHeight, args.windowSettings.fullscreenWidth, args.windowSettings.fullscreenHeight, args.windowSettings.fullscreen) : args.windowSettings;
        Util.nanoTimeSupplier = RenderSystem.initBackendSystem();
        this.windowProvider = new WindowProvider(this);
        this.window = this.windowProvider.createWindow(windowSettings, this.options.fullscreenResolution, this.getWindowTitle());
        this.onWindowFocusChanged(true);
        try {
            object = this.getResourcePackDownloader().getPack().open(ResourceType.CLIENT_RESOURCES, new Identifier("icons/icon_16x16.png"));
            InputStream _snowman4 = this.getResourcePackDownloader().getPack().open(ResourceType.CLIENT_RESOURCES, new Identifier("icons/icon_32x32.png"));
            this.window.setIcon((InputStream)object, _snowman4);
        }
        catch (IOException iOException) {
            LOGGER.error("Couldn't set icon", (Throwable)iOException);
        }
        this.window.setFramerateLimit(this.options.maxFps);
        this.mouse = new Mouse(this);
        this.mouse.setup(this.window.getHandle());
        this.keyboard = new Keyboard(this);
        this.keyboard.setup(this.window.getHandle());
        RenderSystem.initRenderer(this.options.glDebugVerbosity, false);
        this.framebuffer = new Framebuffer(this.window.getFramebufferWidth(), this.window.getFramebufferHeight(), true, IS_SYSTEM_MAC);
        this.framebuffer.setClearColor(0.0f, 0.0f, 0.0f, 0.0f);
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
            this.openScreen(new ConnectScreen(new TitleScreen(), this, string, _snowman3));
        } else {
            this.openScreen(new TitleScreen(true));
        }
        SplashScreen.init(this);
        object = this.resourcePackManager.createResourcePacks();
        this.setOverlay(new SplashScreen(this, this.resourceManager.beginMonitoredReload(Util.getMainWorkerExecutor(), this, COMPLETED_UNIT_FUTURE, (List<ResourcePack>)object), optional -> Util.ifPresentOrElse(optional, this::handleResourceReloadException, () -> {
            if (SharedConstants.isDevelopment) {
                this.checkGameData();
            }
        }), false));
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
        ClientPlayNetworkHandler _snowman2 = this.getNetworkHandler();
        if (_snowman2 != null && _snowman2.getConnection().isOpen()) {
            stringBuilder.append(" - ");
            if (this.server != null && !this.server.isRemote()) {
                stringBuilder.append(I18n.translate("title.singleplayer", new Object[0]));
            } else if (this.isConnectedToRealms()) {
                stringBuilder.append(I18n.translate("title.multiplayer.realms", new Object[0]));
            } else if (this.server != null || this.currentServerEntry != null && this.currentServerEntry.isLocal()) {
                stringBuilder.append(I18n.translate("title.multiplayer.lan", new Object[0]));
            } else {
                stringBuilder.append(I18n.translate("title.multiplayer.other", new Object[0]));
            }
        }
        return stringBuilder.toString();
    }

    private SocialInteractionsService method_31382(YggdrasilAuthenticationService yggdrasilAuthenticationService, RunArgs runArgs) {
        try {
            return yggdrasilAuthenticationService.createSocialInteractionsService(runArgs.network.session.getAccessToken());
        }
        catch (AuthenticationException authenticationException) {
            LOGGER.error("Failed to verify authentication", (Throwable)authenticationException);
            return new OfflineSocialInteractions();
        }
    }

    public boolean isModded() {
        return !"vanilla".equals(ClientBrandRetriever.getClientModName()) || MinecraftClient.class.getSigners() == null;
    }

    private void handleResourceReloadException(Throwable throwable2) {
        if (this.resourcePackManager.getEnabledNames().size() > 1) {
            LiteralText literalText = throwable2 instanceof ReloadableResourceManagerImpl.PackAdditionFailedException ? new LiteralText(((ReloadableResourceManagerImpl.PackAdditionFailedException)throwable2).getPack().getName()) : null;
            this.method_31186(throwable2, literalText);
        } else {
            Throwable throwable2;
            Util.throwUnchecked(throwable2);
        }
    }

    public void method_31186(Throwable throwable, @Nullable Text text) {
        LOGGER.info("Caught error loading resourcepacks, removing all selected resourcepacks", throwable);
        this.resourcePackManager.setEnabledProfiles(Collections.emptyList());
        this.options.resourcePacks.clear();
        this.options.incompatibleResourcePacks.clear();
        this.options.write();
        this.reloadResources().thenRun(() -> {
            ToastManager toastManager = this.getToastManager();
            SystemToast.show(toastManager, SystemToast.Type.PACK_LOAD_FAILURE, new TranslatableText("resourcePack.load_fail"), text);
        });
    }

    public void run() {
        this.thread = Thread.currentThread();
        try {
            boolean bl = false;
            while (this.running) {
                if (this.crashReport != null) {
                    MinecraftClient.printCrashReport(this.crashReport);
                    return;
                }
                try {
                    TickDurationMonitor tickDurationMonitor = TickDurationMonitor.create("Renderer");
                    boolean _snowman2 = this.shouldMonitorTickDuration();
                    this.startMonitor(_snowman2, tickDurationMonitor);
                    this.profiler.startTick();
                    this.render(!bl);
                    this.profiler.endTick();
                    this.endMonitor(_snowman2, tickDurationMonitor);
                }
                catch (OutOfMemoryError outOfMemoryError) {
                    if (bl) {
                        throw outOfMemoryError;
                    }
                    this.cleanUpAfterCrash();
                    this.openScreen(new OutOfMemoryScreen());
                    System.gc();
                    LOGGER.fatal("Out of memory", (Throwable)outOfMemoryError);
                    bl = true;
                }
            }
        }
        catch (CrashException crashException) {
            this.addDetailsToCrashReport(crashException.getReport());
            this.cleanUpAfterCrash();
            LOGGER.fatal("Reported exception thrown!", (Throwable)crashException);
            MinecraftClient.printCrashReport(crashException.getReport());
        }
        catch (Throwable throwable) {
            CrashReport crashReport = this.addDetailsToCrashReport(new CrashReport("Unexpected error", throwable));
            LOGGER.fatal("Unreported exception thrown!", throwable);
            this.cleanUpAfterCrash();
            MinecraftClient.printCrashReport(crashReport);
        }
    }

    void initFont(boolean forcesUnicode) {
        this.fontManager.setIdOverrides((Map<Identifier, Identifier>)(forcesUnicode ? ImmutableMap.of((Object)DEFAULT_FONT_ID, (Object)UNICODE_FONT_ID) : ImmutableMap.of()));
    }

    private void initializeSearchableContainers() {
        TextSearchableContainer<ItemStack> textSearchableContainer = new TextSearchableContainer<ItemStack>(itemStack -> itemStack.getTooltip(null, TooltipContext.Default.NORMAL).stream().map(text -> Formatting.strip(text.getString()).trim()).filter(string -> !string.isEmpty()), itemStack -> Stream.of(Registry.ITEM.getId(itemStack.getItem())));
        IdentifierSearchableContainer<ItemStack> _snowman2 = new IdentifierSearchableContainer<ItemStack>(itemStack -> ItemTags.getTagGroup().getTagsFor(itemStack.getItem()).stream());
        DefaultedList<ItemStack> _snowman3 = DefaultedList.of();
        for (Item item : Registry.ITEM) {
            item.appendStacks(ItemGroup.SEARCH, _snowman3);
        }
        _snowman3.forEach(itemStack -> {
            textSearchableContainer.add((ItemStack)itemStack);
            _snowman2.add((ItemStack)itemStack);
        });
        TextSearchableContainer<RecipeResultCollection> _snowman4 = new TextSearchableContainer<RecipeResultCollection>(recipeResultCollection -> recipeResultCollection.getAllRecipes().stream().flatMap(recipe -> recipe.getOutput().getTooltip(null, TooltipContext.Default.NORMAL).stream()).map(text -> Formatting.strip(text.getString()).trim()).filter(string -> !string.isEmpty()), recipeResultCollection -> recipeResultCollection.getAllRecipes().stream().map(recipe -> Registry.ITEM.getId(recipe.getOutput().getItem())));
        this.searchManager.put(SearchManager.ITEM_TOOLTIP, textSearchableContainer);
        this.searchManager.put(SearchManager.ITEM_TAG, _snowman2);
        this.searchManager.put(SearchManager.RECIPE_OUTPUT, _snowman4);
    }

    private void handleGlErrorByDisableVsync(int error, long description) {
        this.options.enableVsync = false;
        this.options.write();
    }

    private static boolean checkIs64Bit() {
        String[] stringArray;
        for (String _snowman2 : stringArray = new String[]{"sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch"}) {
            String string = System.getProperty(_snowman2);
            if (string == null || !string.contains("64")) continue;
            return true;
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
        File file = new File(MinecraftClient.getInstance().runDirectory, "crash-reports");
        _snowman = new File(file, "crash-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + "-client.txt");
        Bootstrap.println(report.asString());
        if (report.getFile() != null) {
            Bootstrap.println("#@!@# Game crashed! Crash report saved to: #@!@# " + report.getFile());
            System.exit(-1);
        } else if (report.writeToFile(_snowman)) {
            Bootstrap.println("#@!@# Game crashed! Crash report saved to: #@!@# " + _snowman.getAbsolutePath());
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
        }
        CompletableFuture<Void> completableFuture = new CompletableFuture<Void>();
        if (this.overlay instanceof SplashScreen) {
            this.resourceReloadFuture = completableFuture;
            return completableFuture;
        }
        this.resourcePackManager.scanPacks();
        List<ResourcePack> _snowman2 = this.resourcePackManager.createResourcePacks();
        this.setOverlay(new SplashScreen(this, this.resourceManager.beginMonitoredReload(Util.getMainWorkerExecutor(), this, COMPLETED_UNIT_FUTURE, _snowman2), optional -> Util.ifPresentOrElse(optional, this::handleResourceReloadException, () -> {
            this.worldRenderer.reload();
            completableFuture.complete(null);
        }), true));
        return completableFuture;
    }

    private void checkGameData() {
        boolean bl = false;
        BlockModels _snowman2 = this.getBlockRenderManager().getModels();
        BakedModel _snowman3 = _snowman2.getModelManager().getMissingModel();
        for (Block block : Registry.BLOCK) {
            for (Object object : block.getStateManager().getStates()) {
                if (((AbstractBlock.AbstractBlockState)object).getRenderType() != BlockRenderType.MODEL || (object = _snowman2.getModel((BlockState)object)) != _snowman3) continue;
                LOGGER.debug("Missing model for: {}", object);
                bl = true;
            }
        }
        Sprite sprite = _snowman3.getSprite();
        for (Block block : Registry.BLOCK) {
            for (Object object : block.getStateManager().getStates()) {
                object2 = _snowman2.getSprite((BlockState)object);
                if (((AbstractBlock.AbstractBlockState)object).isAir() || object2 != sprite) continue;
                LOGGER.debug("Missing particle icon for: {}", object);
                bl = true;
            }
        }
        DefaultedList<ItemStack> defaultedList = DefaultedList.of();
        for (Object object : Registry.ITEM) {
            defaultedList.clear();
            ((Item)object).appendStacks(ItemGroup.SEARCH, defaultedList);
            for (Object object2 : defaultedList) {
                String string = ((ItemStack)object2).getTranslationKey();
                _snowman = new TranslatableText(string).getString();
                if (!_snowman.toLowerCase(Locale.ROOT).equals(((Item)object).getTranslationKey())) continue;
                LOGGER.debug("Missing translation for: {} {} {}", object2, (Object)string, (Object)((ItemStack)object2).getItem());
            }
        }
        if (bl |= HandledScreens.validateScreens()) {
            throw new IllegalStateException("Your game data is foobar, fix the errors above!");
        }
    }

    public LevelStorage getLevelStorage() {
        return this.levelStorage;
    }

    private void openChatScreen(String text) {
        if (!this.isInSingleplayer() && !this.isOnlineChatEnabled()) {
            if (this.player != null) {
                this.player.sendSystemMessage(new TranslatableText("chat.cannotSend").formatted(Formatting.RED), Util.NIL_UUID);
            }
        } else {
            this.openScreen(new ChatScreen(text));
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
            }
            catch (Throwable throwable) {
                // empty catch block
            }
            try {
                if (this.world != null) {
                    this.world.disconnect();
                }
                this.disconnect();
            }
            catch (Throwable throwable) {
                // empty catch block
            }
            if (this.currentScreen != null) {
                this.currentScreen.removed();
            }
            this.close();
        }
        finally {
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
        }
        catch (Throwable throwable) {
            LOGGER.error("Shutdown failure!", throwable);
            throw throwable;
        }
        finally {
            this.windowProvider.close();
            this.window.close();
        }
    }

    private void render(boolean tick) {
        int n;
        Object object;
        this.window.setPhase("Pre render");
        long l = Util.getMeasuringTimeNano();
        if (this.window.shouldClose()) {
            this.scheduleStop();
        }
        if (this.resourceReloadFuture != null && !(this.overlay instanceof SplashScreen)) {
            object = this.resourceReloadFuture;
            this.resourceReloadFuture = null;
            this.reloadResources().thenRun(() -> MinecraftClient.method_18508((CompletableFuture)object));
        }
        while ((object = this.renderTaskQueue.poll()) != null) {
            object.run();
        }
        if (tick) {
            n = this.renderTickCounter.beginRenderTick(Util.getMeasuringTimeMs());
            this.profiler.push("scheduledExecutables");
            this.runTasks();
            this.profiler.pop();
            this.profiler.push("tick");
            for (_snowman = 0; _snowman < Math.min(10, n); ++_snowman) {
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
        n = this.getFramerateLimit();
        if ((double)n < Option.FRAMERATE_LIMIT.getMax()) {
            RenderSystem.limitDisplayFPS(n);
        }
        this.profiler.swap("yield");
        Thread.yield();
        this.profiler.pop();
        this.window.setPhase("Post render");
        ++this.fpsCounter;
        int n2 = _snowman = this.isIntegratedServerRunning() && (this.currentScreen != null && this.currentScreen.isPauseScreen() || this.overlay != null && this.overlay.pausesGame()) && !this.server.isRemote() ? 1 : 0;
        if (this.paused != _snowman) {
            if (this.paused) {
                this.pausedTickDelta = this.renderTickCounter.tickDelta;
            } else {
                this.renderTickCounter.tickDelta = this.pausedTickDelta;
            }
            this.paused = _snowman;
        }
        long _snowman2 = Util.getMeasuringTimeNano();
        this.metricsData.pushSample(_snowman2 - this.lastMetricsSampleTime);
        this.lastMetricsSampleTime = _snowman2;
        this.profiler.push("fpsUpdate");
        while (Util.getMeasuringTimeMs() >= this.nextDebugInfoUpdateTime + 1000L) {
            currentFps = this.fpsCounter;
            this.fpsDebugString = String.format("%d fps T: %s%s%s%s B: %d", currentFps, (double)this.options.maxFps == Option.FRAMERATE_LIMIT.getMax() ? "inf" : Integer.valueOf(this.options.maxFps), this.options.enableVsync ? " vsync" : "", this.options.graphicsMode.toString(), this.options.cloudRenderMode == CloudRenderMode.OFF ? "" : (this.options.cloudRenderMode == CloudRenderMode.FAST ? " fast-clouds" : " fancy-clouds"), this.options.biomeBlendRadius);
            this.nextDebugInfoUpdateTime += 1000L;
            this.fpsCounter = 0;
            this.snooper.update();
            if (this.snooper.isActive()) continue;
            this.snooper.method_5482();
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
            ++this.trackingTick;
        } else {
            this.tickTimeTracker.disable();
        }
        this.profiler = TickDurationMonitor.tickProfiler(this.tickTimeTracker.getProfiler(), monitor);
    }

    private void endMonitor(boolean active, @Nullable TickDurationMonitor monitor) {
        if (monitor != null) {
            monitor.endTick();
        }
        this.tickProfilerResult = active ? this.tickTimeTracker.getResult() : null;
        this.profiler = this.tickTimeTracker.getProfiler();
    }

    @Override
    public void onResolutionChanged() {
        int n = this.window.calculateScaleFactor(this.options.guiScale, this.forcesUnicodeFont());
        this.window.setScaleFactor(n);
        if (this.currentScreen != null) {
            this.currentScreen.resize(this, this.window.getScaledWidth(), this.window.getScaledHeight());
        }
        Framebuffer _snowman2 = this.getFramebuffer();
        _snowman2.resize(this.window.getFramebufferWidth(), this.window.getFramebufferHeight(), IS_SYSTEM_MAC);
        this.gameRenderer.onResized(this.window.getFramebufferWidth(), this.window.getFramebufferHeight());
        this.mouse.onResolutionChanged();
    }

    @Override
    public void onCursorEnterChanged() {
        this.mouse.method_30134();
    }

    private int getFramerateLimit() {
        if (this.world == null && (this.currentScreen != null || this.overlay != null)) {
            return 60;
        }
        return this.window.getFramerateLimit();
    }

    public void cleanUpAfterCrash() {
        try {
            memoryReservedForCrash = new byte[0];
            this.worldRenderer.method_3267();
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        try {
            System.gc();
            if (this.integratedServerRunning && this.server != null) {
                this.server.stop(true);
            }
            this.disconnect(new SaveLevelScreen(new TranslatableText("menu.savingLevel")));
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        System.gc();
    }

    void handleProfilerKeyPress(int digit) {
        if (this.tickProfilerResult == null) {
            return;
        }
        List<ProfilerTiming> list = this.tickProfilerResult.getTimings(this.openProfilerSection);
        if (list.isEmpty()) {
            return;
        }
        ProfilerTiming _snowman2 = list.remove(0);
        if (digit == 0) {
            if (!_snowman2.name.isEmpty() && (_snowman = this.openProfilerSection.lastIndexOf(30)) >= 0) {
                this.openProfilerSection = this.openProfilerSection.substring(0, _snowman);
            }
        } else if (--digit < list.size() && !"unspecified".equals(list.get((int)digit).name)) {
            if (!this.openProfilerSection.isEmpty()) {
                this.openProfilerSection = this.openProfilerSection + '\u001e';
            }
            this.openProfilerSection = this.openProfilerSection + list.get((int)digit).name;
        }
    }

    /*
     * WARNING - void declaration
     */
    private void drawProfilerResults(MatrixStack matrices, ProfileResult profileResult) {
        void var13_15;
        List<ProfilerTiming> list = profileResult.getTimings(this.openProfilerSection);
        ProfilerTiming _snowman2 = list.remove(0);
        RenderSystem.clear(256, IS_SYSTEM_MAC);
        RenderSystem.matrixMode(5889);
        RenderSystem.loadIdentity();
        RenderSystem.ortho(0.0, this.window.getFramebufferWidth(), this.window.getFramebufferHeight(), 0.0, 1000.0, 3000.0);
        RenderSystem.matrixMode(5888);
        RenderSystem.loadIdentity();
        RenderSystem.translatef(0.0f, 0.0f, -2000.0f);
        RenderSystem.lineWidth(1.0f);
        RenderSystem.disableTexture();
        Tessellator _snowman3 = Tessellator.getInstance();
        BufferBuilder _snowman4 = _snowman3.getBuffer();
        int _snowman5 = 160;
        int _snowman6 = this.window.getFramebufferWidth() - 160 - 10;
        int _snowman7 = this.window.getFramebufferHeight() - 320;
        RenderSystem.enableBlend();
        _snowman4.begin(7, VertexFormats.POSITION_COLOR);
        _snowman4.vertex((float)_snowman6 - 176.0f, (float)_snowman7 - 96.0f - 16.0f, 0.0).color(200, 0, 0, 0).next();
        _snowman4.vertex((float)_snowman6 - 176.0f, _snowman7 + 320, 0.0).color(200, 0, 0, 0).next();
        _snowman4.vertex((float)_snowman6 + 176.0f, _snowman7 + 320, 0.0).color(200, 0, 0, 0).next();
        _snowman4.vertex((float)_snowman6 + 176.0f, (float)_snowman7 - 96.0f - 16.0f, 0.0).color(200, 0, 0, 0).next();
        _snowman3.draw();
        RenderSystem.disableBlend();
        double _snowman8 = 0.0;
        for (ProfilerTiming profilerTiming : list) {
            int n = MathHelper.floor(profilerTiming.parentSectionUsagePercentage / 4.0) + 1;
            _snowman4.begin(6, VertexFormats.POSITION_COLOR);
            int _snowman11 = profilerTiming.getColor();
            int n2 = _snowman11 >> 16 & 0xFF;
            _snowman = _snowman11 >> 8 & 0xFF;
            _snowman = _snowman11 & 0xFF;
            _snowman4.vertex(_snowman6, _snowman7, 0.0).color(n2, _snowman, _snowman, 255).next();
            for (int i = n; i >= 0; --i) {
                float f = (float)((_snowman8 + profilerTiming.parentSectionUsagePercentage * (double)i / (double)n) * 6.2831854820251465 / 100.0);
                float f2 = MathHelper.sin(f) * 160.0f;
                _snowman = MathHelper.cos(f) * 160.0f * 0.5f;
                _snowman4.vertex((float)_snowman6 + f2, (float)_snowman7 - _snowman, 0.0).color(n2, _snowman, _snowman, 255).next();
            }
            _snowman3.draw();
            _snowman4.begin(5, VertexFormats.POSITION_COLOR);
            for (int i = n; i >= 0; --i) {
                float f = (float)((_snowman8 + profilerTiming.parentSectionUsagePercentage * (double)i / (double)n) * 6.2831854820251465 / 100.0);
                f2 = MathHelper.sin(f) * 160.0f;
                _snowman = MathHelper.cos(f) * 160.0f * 0.5f;
                if (_snowman > 0.0f) continue;
                _snowman4.vertex((float)_snowman6 + f2, (float)_snowman7 - _snowman, 0.0).color(n2 >> 1, _snowman >> 1, _snowman >> 1, 255).next();
                _snowman4.vertex((float)_snowman6 + f2, (float)_snowman7 - _snowman + 10.0f, 0.0).color(n2 >> 1, _snowman >> 1, _snowman >> 1, 255).next();
            }
            _snowman3.draw();
            _snowman8 += profilerTiming.parentSectionUsagePercentage;
        }
        DecimalFormat decimalFormat = new DecimalFormat("##0.00");
        decimalFormat.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
        RenderSystem.enableTexture();
        String string = ProfileResult.getHumanReadableName(_snowman2.name);
        String _snowman10 = "";
        if (!"unspecified".equals(string)) {
            _snowman10 = _snowman10 + "[0] ";
        }
        _snowman10 = string.isEmpty() ? _snowman10 + "ROOT " : _snowman10 + string + ' ';
        int n = 0xFFFFFF;
        this.textRenderer.drawWithShadow(matrices, _snowman10, (float)(_snowman6 - 160), (float)(_snowman7 - 80 - 16), 0xFFFFFF);
        _snowman10 = decimalFormat.format(_snowman2.totalUsagePercentage) + "%";
        this.textRenderer.drawWithShadow(matrices, _snowman10, (float)(_snowman6 + 160 - this.textRenderer.getWidth(_snowman10)), (float)(_snowman7 - 80 - 16), 0xFFFFFF);
        boolean bl = false;
        while (var13_15 < list.size()) {
            ProfilerTiming profilerTiming = list.get((int)var13_15);
            StringBuilder stringBuilder = new StringBuilder();
            if ("unspecified".equals(profilerTiming.name)) {
                stringBuilder.append("[?] ");
            } else {
                stringBuilder.append("[").append((int)(var13_15 + true)).append("] ");
            }
            String _snowman12 = stringBuilder.append(profilerTiming.name).toString();
            this.textRenderer.drawWithShadow(matrices, _snowman12, (float)(_snowman6 - 160), (float)(_snowman7 + 80 + var13_15 * 8 + 20), profilerTiming.getColor());
            _snowman12 = decimalFormat.format(profilerTiming.parentSectionUsagePercentage) + "%";
            this.textRenderer.drawWithShadow(matrices, _snowman12, (float)(_snowman6 + 160 - 50 - this.textRenderer.getWidth(_snowman12)), (float)(_snowman7 + 80 + var13_15 * 8 + 20), profilerTiming.getColor());
            _snowman12 = decimalFormat.format(profilerTiming.totalUsagePercentage) + "%";
            this.textRenderer.drawWithShadow(matrices, _snowman12, (float)(_snowman6 + 160 - this.textRenderer.getWidth(_snowman12)), (float)(_snowman7 + 80 + var13_15 * 8 + 20), profilerTiming.getColor());
            ++var13_15;
        }
    }

    public void scheduleStop() {
        this.running = false;
    }

    public boolean isRunning() {
        return this.running;
    }

    public void openPauseMenu(boolean pause) {
        boolean bl;
        if (this.currentScreen != null) {
            return;
        }
        boolean bl2 = bl = this.isIntegratedServerRunning() && !this.server.isRemote();
        if (bl) {
            this.openScreen(new GameMenuScreen(!pause));
            this.soundManager.pauseAll();
        } else {
            this.openScreen(new GameMenuScreen(true));
        }
    }

    private void handleBlockBreaking(boolean bl) {
        if (!bl) {
            this.attackCooldown = 0;
        }
        if (this.attackCooldown > 0 || this.player.isUsingItem()) {
            return;
        }
        if (bl && this.crosshairTarget != null && this.crosshairTarget.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockHitResult = (BlockHitResult)this.crosshairTarget;
            BlockPos _snowman2 = blockHitResult.getBlockPos();
            if (!this.world.getBlockState(_snowman2).isAir() && this.interactionManager.updateBlockBreakingProgress(_snowman2, _snowman = blockHitResult.getSide())) {
                this.particleManager.addBlockBreakingParticles(_snowman2, _snowman);
                this.player.swingHand(Hand.MAIN_HAND);
            }
            return;
        }
        this.interactionManager.cancelBlockBreaking();
    }

    private void doAttack() {
        if (this.attackCooldown > 0) {
            return;
        }
        if (this.crosshairTarget == null) {
            LOGGER.error("Null returned as 'hitResult', this shouldn't happen!");
            if (this.interactionManager.hasLimitedAttackSpeed()) {
                this.attackCooldown = 10;
            }
            return;
        }
        if (this.player.isRiding()) {
            return;
        }
        switch (this.crosshairTarget.getType()) {
            case ENTITY: {
                this.interactionManager.attackEntity(this.player, ((EntityHitResult)this.crosshairTarget).getEntity());
                break;
            }
            case BLOCK: {
                BlockHitResult blockHitResult = (BlockHitResult)this.crosshairTarget;
                BlockPos _snowman2 = blockHitResult.getBlockPos();
                if (!this.world.getBlockState(_snowman2).isAir()) {
                    this.interactionManager.attackBlock(_snowman2, blockHitResult.getSide());
                    break;
                }
            }
            case MISS: {
                if (this.interactionManager.hasLimitedAttackSpeed()) {
                    this.attackCooldown = 10;
                }
                this.player.resetLastAttackedTicks();
            }
        }
        this.player.swingHand(Hand.MAIN_HAND);
    }

    private void doItemUse() {
        if (this.interactionManager.isBreakingBlock()) {
            return;
        }
        this.itemUseCooldown = 4;
        if (this.player.isRiding()) {
            return;
        }
        if (this.crosshairTarget == null) {
            LOGGER.warn("Null returned as 'hitResult', this shouldn't happen!");
        }
        for (Hand hand : Hand.values()) {
            ItemStack itemStack = this.player.getStackInHand(hand);
            if (this.crosshairTarget != null) {
                switch (this.crosshairTarget.getType()) {
                    case ENTITY: {
                        Object object = (EntityHitResult)this.crosshairTarget;
                        Entity _snowman2 = ((EntityHitResult)object).getEntity();
                        ActionResult _snowman3 = this.interactionManager.interactEntityAtLocation(this.player, _snowman2, (EntityHitResult)object, hand);
                        if (!_snowman3.isAccepted()) {
                            _snowman3 = this.interactionManager.interactEntity(this.player, _snowman2, hand);
                        }
                        if (!_snowman3.isAccepted()) break;
                        if (_snowman3.shouldSwingHand()) {
                            this.player.swingHand(hand);
                        }
                        return;
                    }
                    case BLOCK: {
                        BlockHitResult _snowman4 = (BlockHitResult)this.crosshairTarget;
                        int _snowman5 = itemStack.getCount();
                        ActionResult _snowman6 = this.interactionManager.interactBlock(this.player, this.world, hand, _snowman4);
                        if (_snowman6.isAccepted()) {
                            if (_snowman6.shouldSwingHand()) {
                                this.player.swingHand(hand);
                                if (!itemStack.isEmpty() && (itemStack.getCount() != _snowman5 || this.interactionManager.hasCreativeInventory())) {
                                    this.gameRenderer.firstPersonRenderer.resetEquipProgress(hand);
                                }
                            }
                            return;
                        }
                        if (_snowman6 != ActionResult.FAIL) break;
                        return;
                    }
                }
            }
            if (itemStack.isEmpty() || !((ActionResult)((Object)(object = this.interactionManager.interactItem(this.player, this.world, hand)))).isAccepted()) continue;
            if (((ActionResult)((Object)object)).shouldSwingHand()) {
                this.player.swingHand(hand);
            }
            this.gameRenderer.firstPersonRenderer.resetEquipProgress(hand);
            return;
        }
    }

    public MusicTracker getMusicTracker() {
        return this.musicTracker;
    }

    public void tick() {
        if (this.itemUseCooldown > 0) {
            --this.itemUseCooldown;
        }
        this.profiler.push("gui");
        if (!this.paused) {
            this.inGameHud.tick();
        }
        this.profiler.pop();
        this.gameRenderer.updateTargetedEntity(1.0f);
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
                --this.attackCooldown;
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
                Object _snowman2;
                if (!this.options.joinedFirstServer && this.method_31321()) {
                    TranslatableText translatableText = new TranslatableText("tutorial.socialInteractions.title");
                    _snowman2 = new TranslatableText("tutorial.socialInteractions.description", TutorialManager.getKeybindName("socialInteractions"));
                    this.field_26843 = new TutorialToast(TutorialToast.Type.SOCIAL_INTERACTIONS, translatableText, (Text)_snowman2, true);
                    this.tutorialManager.method_31365(this.field_26843, 160);
                    this.options.joinedFirstServer = true;
                    this.options.write();
                }
                this.tutorialManager.tick();
                try {
                    this.world.tick(() -> true);
                }
                catch (Throwable throwable) {
                    _snowman2 = CrashReport.create(throwable, "Exception in world tick");
                    if (this.world == null) {
                        CrashReportSection crashReportSection = ((CrashReport)_snowman2).addElement("Affected level");
                        crashReportSection.add("Problem", "Level is null!");
                    } else {
                        this.world.addDetailsToCrashReport((CrashReport)_snowman2);
                    }
                    throw new CrashException((CrashReport)_snowman2);
                }
            }
            this.profiler.swap("animateTick");
            if (!this.paused && this.world != null) {
                this.world.doRandomBlockDisplayTicks(MathHelper.floor(this.player.getX()), MathHelper.floor(this.player.getY()), MathHelper.floor(this.player.getZ()));
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
        int n;
        while (this.options.keyTogglePerspective.wasPressed()) {
            Perspective perspective = this.options.getPerspective();
            this.options.setPerspective(this.options.getPerspective().next());
            if (perspective.isFirstPerson() != this.options.getPerspective().isFirstPerson()) {
                this.gameRenderer.onCameraEntitySet(this.options.getPerspective().isFirstPerson() ? this.getCameraEntity() : null);
            }
            this.worldRenderer.scheduleTerrainUpdate();
        }
        while (this.options.keySmoothCamera.wasPressed()) {
            this.options.smoothCameraEnabled = !this.options.smoothCameraEnabled;
        }
        for (n = 0; n < 9; ++n) {
            boolean bl = this.options.keySaveToolbarActivator.isPressed();
            _snowman = this.options.keyLoadToolbarActivator.isPressed();
            if (!this.options.keysHotbar[n].wasPressed()) continue;
            if (this.player.isSpectator()) {
                this.inGameHud.getSpectatorHud().selectSlot(n);
                continue;
            }
            if (this.player.isCreative() && this.currentScreen == null && (_snowman || bl)) {
                CreativeInventoryScreen.onHotbarKeyPress(this, n, _snowman, bl);
                continue;
            }
            this.player.inventory.selectedSlot = n;
        }
        while (this.options.keySocialInteractions.wasPressed()) {
            if (!this.method_31321()) {
                this.player.sendMessage(field_26841, true);
                NarratorManager.INSTANCE.narrate(field_26841.getString());
                continue;
            }
            if (this.field_26843 != null) {
                this.tutorialManager.method_31364(this.field_26843);
                this.field_26843 = null;
            }
            this.openScreen(new SocialInteractionsScreen());
        }
        while (this.options.keyInventory.wasPressed()) {
            if (this.interactionManager.hasRidingInventory()) {
                this.player.openRidingInventory();
                continue;
            }
            this.tutorialManager.onInventoryOpened();
            this.openScreen(new InventoryScreen(this.player));
        }
        while (this.options.keyAdvancements.wasPressed()) {
            this.openScreen(new AdvancementsScreen(this.player.networkHandler.getAdvancementHandler()));
        }
        while (this.options.keySwapHands.wasPressed()) {
            if (this.player.isSpectator()) continue;
            this.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.SWAP_ITEM_WITH_OFFHAND, BlockPos.ORIGIN, Direction.DOWN));
        }
        while (this.options.keyDrop.wasPressed()) {
            if (this.player.isSpectator() || !this.player.dropSelectedItem(Screen.hasControlDown())) continue;
            this.player.swingHand(Hand.MAIN_HAND);
        }
        int n2 = n = this.options.chatVisibility != ChatVisibility.HIDDEN ? 1 : 0;
        if (n != 0) {
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

    public static DataPackSettings method_29598(LevelStorage.Session session) {
        MinecraftServer.convertLevel(session);
        DataPackSettings dataPackSettings = session.getDataPackSettings();
        if (dataPackSettings == null) {
            throw new IllegalStateException("Failed to load data pack config");
        }
        return dataPackSettings;
    }

    public static SaveProperties createSaveProperties(LevelStorage.Session session, DynamicRegistryManager.Impl registryTracker, ResourceManager resourceManager, DataPackSettings dataPackSettings) {
        RegistryOps<Tag> registryOps = RegistryOps.of(NbtOps.INSTANCE, resourceManager, registryTracker);
        SaveProperties _snowman2 = session.readLevelProperties(registryOps, dataPackSettings);
        if (_snowman2 == null) {
            throw new IllegalStateException("Failed to load world");
        }
        return _snowman2;
    }

    public void startIntegratedServer(String worldName) {
        this.startIntegratedServer(worldName, DynamicRegistryManager.create(), MinecraftClient::method_29598, (Function4<LevelStorage.Session, DynamicRegistryManager.Impl, ResourceManager, DataPackSettings, SaveProperties>)((Function4)MinecraftClient::createSaveProperties), false, WorldLoadAction.BACKUP);
    }

    public void method_29607(String worldName, LevelInfo levelInfo, DynamicRegistryManager.Impl registryTracker, GeneratorOptions generatorOptions) {
        this.startIntegratedServer(worldName, registryTracker, session -> levelInfo.getDataPackSettings(), (Function4<LevelStorage.Session, DynamicRegistryManager.Impl, ResourceManager, DataPackSettings, SaveProperties>)((Function4)(session, impl2, resourceManager, dataPackSettings) -> {
            RegistryReadingOps registryReadingOps = RegistryReadingOps.of(JsonOps.INSTANCE, registryTracker);
            RegistryOps _snowman2 = RegistryOps.of(JsonOps.INSTANCE, resourceManager, registryTracker);
            DataResult _snowman3 = GeneratorOptions.CODEC.encodeStart(registryReadingOps, (Object)generatorOptions).setLifecycle(Lifecycle.stable()).flatMap(jsonElement -> GeneratorOptions.CODEC.parse((DynamicOps)_snowman2, jsonElement));
            GeneratorOptions _snowman4 = _snowman3.resultOrPartial(Util.method_29188("Error reading worldgen settings after loading data packs: ", arg_0 -> ((Logger)LOGGER).error(arg_0))).orElse(generatorOptions);
            return new LevelProperties(levelInfo, _snowman4, _snowman3.lifecycle());
        }), false, WorldLoadAction.CREATE);
    }

    private void startIntegratedServer(String worldName, DynamicRegistryManager.Impl registryTracker, Function<LevelStorage.Session, DataPackSettings> function, Function4<LevelStorage.Session, DynamicRegistryManager.Impl, ResourceManager, DataPackSettings, SaveProperties> function4, boolean safeMode, WorldLoadAction worldLoadAction) {
        Object _snowman6;
        Object _snowman5;
        Object _snowman4;
        IntegratedResourceManager integratedResourceManager;
        LevelStorage.Session session;
        try {
            session = this.levelStorage.createSession(worldName);
        }
        catch (IOException iOException) {
            LOGGER.warn("Failed to read level {} data", (Object)worldName, (Object)iOException);
            SystemToast.addWorldAccessFailureToast(this, worldName);
            this.openScreen(null);
            return;
        }
        try {
            integratedResourceManager = this.method_29604(registryTracker, function, function4, safeMode, session);
        }
        catch (Exception exception) {
            LOGGER.warn("Failed to load datapacks, can't proceed with server load", (Throwable)exception);
            this.openScreen(new DatapackFailureScreen(() -> this.startIntegratedServer(worldName, registryTracker, function, function4, true, worldLoadAction)));
            try {
                session.close();
            }
            catch (IOException iOException) {
                LOGGER.warn("Failed to unlock access to level {}", (Object)worldName, (Object)iOException);
            }
            return;
        }
        SaveProperties _snowman2 = integratedResourceManager.getSaveProperties();
        boolean _snowman3 = _snowman2.getGeneratorOptions().isLegacyCustomizedType();
        boolean bl = _snowman = _snowman2.getLifecycle() != Lifecycle.stable();
        if (worldLoadAction != WorldLoadAction.NONE && (_snowman3 || _snowman)) {
            this.method_29601(worldLoadAction, worldName, _snowman3, () -> this.startIntegratedServer(worldName, registryTracker, function, function4, safeMode, WorldLoadAction.NONE));
            integratedResourceManager.close();
            try {
                session.close();
            }
            catch (IOException iOException) {
                LOGGER.warn("Failed to unlock access to level {}", (Object)worldName, (Object)iOException);
            }
            return;
        }
        this.disconnect();
        this.worldGenProgressTracker.set(null);
        try {
            session.backupLevelDataFile(registryTracker, _snowman2);
            integratedResourceManager.getServerResourceManager().loadRegistryTags();
            _snowman4 = new YggdrasilAuthenticationService(this.netProxy);
            _snowman5 = _snowman4.createMinecraftSessionService();
            _snowman6 = _snowman4.createProfileRepository();
            UserCache _snowman7 = new UserCache((GameProfileRepository)_snowman6, new File(this.runDirectory, MinecraftServer.USER_CACHE_FILE.getName()));
            SkullBlockEntity.setUserCache(_snowman7);
            SkullBlockEntity.setSessionService((MinecraftSessionService)_snowman5);
            UserCache.setUseRemote(false);
            this.server = MinecraftServer.startServer(arg_0 -> this.method_29603(registryTracker, session, integratedResourceManager, _snowman2, (MinecraftSessionService)_snowman5, (GameProfileRepository)_snowman6, _snowman7, arg_0));
            this.integratedServerRunning = true;
        }
        catch (Throwable throwable) {
            CrashReport _snowman9 = CrashReport.create(throwable, "Starting integrated server");
            CrashReportSection _snowman8 = _snowman9.addElement("Starting integrated server");
            _snowman8.add("Level ID", worldName);
            _snowman8.add("Level Name", _snowman2.getLevelName());
            throw new CrashException(_snowman9);
        }
        while (this.worldGenProgressTracker.get() == null) {
            Thread.yield();
        }
        _snowman4 = new LevelLoadingScreen(this.worldGenProgressTracker.get());
        this.openScreen((Screen)_snowman4);
        this.profiler.push("waitForServer");
        while (!this.server.isLoading()) {
            ((Screen)_snowman4).tick();
            this.render(false);
            try {
                Thread.sleep(16L);
            }
            catch (InterruptedException _snowman9) {
                // empty catch block
            }
            if (this.crashReport == null) continue;
            MinecraftClient.printCrashReport(this.crashReport);
            return;
        }
        this.profiler.pop();
        _snowman5 = this.server.getNetworkIo().bindLocal();
        _snowman6 = ClientConnection.connectLocal((SocketAddress)_snowman5);
        ((ClientConnection)((Object)_snowman6)).setPacketListener(new ClientLoginNetworkHandler((ClientConnection)((Object)_snowman6), this, null, text -> {}));
        ((ClientConnection)((Object)_snowman6)).send(new HandshakeC2SPacket(_snowman5.toString(), 0, NetworkState.LOGIN));
        ((ClientConnection)((Object)_snowman6)).send(new LoginHelloC2SPacket(this.getSession().getProfile()));
        this.connection = _snowman6;
    }

    private void method_29601(WorldLoadAction worldLoadAction, String string2, boolean bl3, Runnable runnable2) {
        if (worldLoadAction == WorldLoadAction.BACKUP) {
            String string2;
            TranslatableText translatableText;
            if (bl3) {
                translatableText = new TranslatableText("selectWorld.backupQuestion.customized");
                _snowman = new TranslatableText("selectWorld.backupWarning.customized");
            } else {
                translatableText = new TranslatableText("selectWorld.backupQuestion.experimental");
                _snowman = new TranslatableText("selectWorld.backupWarning.experimental");
            }
            this.openScreen(new BackupPromptScreen(null, (bl, bl2) -> {
                if (bl) {
                    EditWorldScreen.method_29784(this.levelStorage, string2);
                }
                runnable2.run();
            }, translatableText, _snowman, false));
        } else {
            Runnable runnable2;
            this.openScreen(new ConfirmScreen(bl -> {
                if (bl) {
                    runnable2.run();
                } else {
                    this.openScreen(null);
                    try (LevelStorage.Session session = this.levelStorage.createSession(string2);){
                        session.deleteSessionLock();
                    }
                    catch (IOException iOException) {
                        SystemToast.addWorldDeleteFailureToast(this, string2);
                        LOGGER.error("Failed to delete world {}", (Object)string2, (Object)iOException);
                    }
                }
            }, new TranslatableText("selectWorld.backupQuestion.experimental"), new TranslatableText("selectWorld.backupWarning.experimental"), ScreenTexts.PROCEED, ScreenTexts.CANCEL));
        }
    }

    public IntegratedResourceManager method_29604(DynamicRegistryManager.Impl impl, Function<LevelStorage.Session, DataPackSettings> function, Function4<LevelStorage.Session, DynamicRegistryManager.Impl, ResourceManager, DataPackSettings, SaveProperties> function4, boolean bl, LevelStorage.Session session) throws InterruptedException, ExecutionException {
        DataPackSettings dataPackSettings = function.apply(session);
        ResourcePackManager _snowman2 = new ResourcePackManager(new VanillaDataPackProvider(), new FileResourcePackProvider(session.getDirectory(WorldSavePath.DATAPACKS).toFile(), ResourcePackSource.PACK_SOURCE_WORLD));
        try {
            _snowman = MinecraftServer.loadDataPacks(_snowman2, dataPackSettings, bl);
            CompletableFuture<ServerResourceManager> completableFuture = ServerResourceManager.reload(_snowman2.createResourcePacks(), CommandManager.RegistrationEnvironment.INTEGRATED, 2, Util.getMainWorkerExecutor(), this);
            this.runTasks(completableFuture::isDone);
            ServerResourceManager _snowman3 = completableFuture.get();
            SaveProperties _snowman4 = (SaveProperties)function4.apply((Object)session, (Object)impl, (Object)_snowman3.getResourceManager(), (Object)_snowman);
            return new IntegratedResourceManager(_snowman2, _snowman3, _snowman4);
        }
        catch (InterruptedException | ExecutionException exception) {
            _snowman2.close();
            throw exception;
        }
    }

    public void joinWorld(ClientWorld world) {
        ProgressScreen progressScreen = new ProgressScreen();
        progressScreen.method_15412(new TranslatableText("connect.joining"));
        this.reset(progressScreen);
        this.world = world;
        this.setWorld(world);
        if (!this.integratedServerRunning) {
            YggdrasilAuthenticationService yggdrasilAuthenticationService = new YggdrasilAuthenticationService(this.netProxy);
            MinecraftSessionService _snowman2 = yggdrasilAuthenticationService.createMinecraftSessionService();
            GameProfileRepository _snowman3 = yggdrasilAuthenticationService.createProfileRepository();
            UserCache _snowman4 = new UserCache(_snowman3, new File(this.runDirectory, MinecraftServer.USER_CACHE_FILE.getName()));
            SkullBlockEntity.setUserCache(_snowman4);
            SkullBlockEntity.setSessionService(_snowman2);
            UserCache.setUseRemote(false);
        }
    }

    public void disconnect() {
        this.disconnect(new ProgressScreen());
    }

    public void disconnect(Screen screen) {
        ClientPlayNetworkHandler clientPlayNetworkHandler = this.getNetworkHandler();
        if (clientPlayNetworkHandler != null) {
            this.cancelTasks();
            clientPlayNetworkHandler.clearWorld();
        }
        IntegratedServer _snowman2 = this.server;
        this.server = null;
        this.gameRenderer.reset();
        this.interactionManager = null;
        NarratorManager.INSTANCE.clear();
        this.reset(screen);
        if (this.world != null) {
            if (_snowman2 != null) {
                this.profiler.push("waitForServer");
                while (!_snowman2.isStopping()) {
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

    public void method_29970(Screen screen) {
        this.profiler.push("forcedTick");
        this.openScreen(screen);
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
        if (!this.isOnlineChatEnabled()) {
            return (this.player == null || !sender.equals(this.player.getUuid())) && !sender.equals(Util.NIL_UUID);
        }
        return this.socialInteractionsManager.method_31391(sender);
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
        return !MinecraftClient.instance.options.hudHidden;
    }

    public static boolean isFancyGraphicsOrBetter() {
        return MinecraftClient.instance.options.graphicsMode.getId() >= GraphicsMode.FANCY.getId();
    }

    public static boolean isFabulousGraphicsOrBetter() {
        return MinecraftClient.instance.options.graphicsMode.getId() >= GraphicsMode.FABULOUS.getId();
    }

    public static boolean isAmbientOcclusionEnabled() {
        return MinecraftClient.instance.options.ao != AoMode.OFF;
    }

    private void doItemPick() {
        ItemStack _snowman5;
        Object object;
        if (this.crosshairTarget == null || this.crosshairTarget.getType() == HitResult.Type.MISS) {
            return;
        }
        boolean bl = this.player.abilities.creativeMode;
        BlockEntity _snowman2 = null;
        HitResult.Type _snowman3 = this.crosshairTarget.getType();
        if (_snowman3 == HitResult.Type.BLOCK) {
            object = ((BlockHitResult)this.crosshairTarget).getBlockPos();
            _snowman = this.world.getBlockState((BlockPos)object);
            Block _snowman4 = ((AbstractBlock.AbstractBlockState)_snowman).getBlock();
            if (((AbstractBlock.AbstractBlockState)_snowman).isAir()) {
                return;
            }
            _snowman5 = _snowman4.getPickStack(this.world, (BlockPos)object, (BlockState)_snowman);
            if (_snowman5.isEmpty()) {
                return;
            }
            if (bl && Screen.hasControlDown() && _snowman4.hasBlockEntity()) {
                _snowman2 = this.world.getBlockEntity((BlockPos)object);
            }
        } else if (_snowman3 == HitResult.Type.ENTITY && bl) {
            object = ((EntityHitResult)this.crosshairTarget).getEntity();
            if (object instanceof PaintingEntity) {
                _snowman5 = new ItemStack(Items.PAINTING);
            } else if (object instanceof LeashKnotEntity) {
                _snowman5 = new ItemStack(Items.LEAD);
            } else if (object instanceof ItemFrameEntity) {
                _snowman = (ItemFrameEntity)object;
                ItemStack itemStack = ((ItemFrameEntity)_snowman).getHeldItemStack();
                _snowman5 = itemStack.isEmpty() ? new ItemStack(Items.ITEM_FRAME) : itemStack.copy();
            } else if (object instanceof AbstractMinecartEntity) {
                Item item;
                _snowman = (AbstractMinecartEntity)object;
                switch (((AbstractMinecartEntity)_snowman).getMinecartType()) {
                    case FURNACE: {
                        item = Items.FURNACE_MINECART;
                        break;
                    }
                    case CHEST: {
                        item = Items.CHEST_MINECART;
                        break;
                    }
                    case TNT: {
                        item = Items.TNT_MINECART;
                        break;
                    }
                    case HOPPER: {
                        item = Items.HOPPER_MINECART;
                        break;
                    }
                    case COMMAND_BLOCK: {
                        item = Items.COMMAND_BLOCK_MINECART;
                        break;
                    }
                    default: {
                        item = Items.MINECART;
                    }
                }
                _snowman5 = new ItemStack(item);
            } else if (object instanceof BoatEntity) {
                _snowman5 = new ItemStack(((BoatEntity)object).asItem());
            } else if (object instanceof ArmorStandEntity) {
                _snowman5 = new ItemStack(Items.ARMOR_STAND);
            } else if (object instanceof EndCrystalEntity) {
                _snowman5 = new ItemStack(Items.END_CRYSTAL);
            } else {
                _snowman = SpawnEggItem.forEntity(((Entity)object).getType());
                if (_snowman == null) {
                    return;
                }
                _snowman5 = new ItemStack((ItemConvertible)_snowman);
            }
        } else {
            return;
        }
        if (_snowman5.isEmpty()) {
            object = "";
            if (_snowman3 == HitResult.Type.BLOCK) {
                object = Registry.BLOCK.getId(this.world.getBlockState(((BlockHitResult)this.crosshairTarget).getBlockPos()).getBlock()).toString();
            } else if (_snowman3 == HitResult.Type.ENTITY) {
                object = Registry.ENTITY_TYPE.getId(((EntityHitResult)this.crosshairTarget).getEntity().getType()).toString();
            }
            LOGGER.warn("Picking on: [{}] {} gave null item", (Object)_snowman3, object);
            return;
        }
        object = this.player.inventory;
        if (_snowman2 != null) {
            this.addBlockEntityNbt(_snowman5, _snowman2);
        }
        int _snowman6 = ((PlayerInventory)object).getSlotWithStack(_snowman5);
        if (bl) {
            ((PlayerInventory)object).addPickBlock(_snowman5);
            this.interactionManager.clickCreativeStack(this.player.getStackInHand(Hand.MAIN_HAND), 36 + ((PlayerInventory)object).selectedSlot);
        } else if (_snowman6 != -1) {
            if (PlayerInventory.isValidHotbarIndex(_snowman6)) {
                ((PlayerInventory)object).selectedSlot = _snowman6;
            } else {
                this.interactionManager.pickFromInventory(_snowman6);
            }
        }
    }

    private ItemStack addBlockEntityNbt(ItemStack stack, BlockEntity blockEntity) {
        CompoundTag compoundTag = blockEntity.toTag(new CompoundTag());
        if (stack.getItem() instanceof SkullItem && compoundTag.contains("SkullOwner")) {
            _snowman = compoundTag.getCompound("SkullOwner");
            stack.getOrCreateTag().put("SkullOwner", _snowman);
            return stack;
        }
        stack.putSubTag("BlockEntityTag", compoundTag);
        _snowman = new CompoundTag();
        ListTag _snowman2 = new ListTag();
        _snowman2.add(StringTag.of("\"(+NBT)\""));
        _snowman.put("Lore", _snowman2);
        stack.putSubTag("display", _snowman);
        return stack;
    }

    public CrashReport addDetailsToCrashReport(CrashReport report) {
        MinecraftClient.addSystemDetailsToCrashReport(this.languageManager, this.gameVersion, this.options, report);
        if (this.world != null) {
            this.world.addDetailsToCrashReport(report);
        }
        return report;
    }

    public static void addSystemDetailsToCrashReport(@Nullable LanguageManager languageManager, String version, @Nullable GameOptions options, CrashReport report) {
        CrashReportSection crashReportSection = report.getSystemDetailsSection();
        crashReportSection.add("Launched Version", () -> version);
        crashReportSection.add("Backend library", RenderSystem::getBackendDescription);
        crashReportSection.add("Backend API", RenderSystem::getApiDescription);
        crashReportSection.add("GL Caps", RenderSystem::getCapsString);
        crashReportSection.add("Using VBOs", () -> "Yes");
        crashReportSection.add("Is Modded", () -> {
            String string = ClientBrandRetriever.getClientModName();
            if (!"vanilla".equals(string)) {
                return "Definitely; Client brand changed to '" + string + "'";
            }
            if (MinecraftClient.class.getSigners() == null) {
                return "Very likely; Jar signature invalidated";
            }
            return "Probably not. Jar signature remains and client brand is untouched.";
        });
        crashReportSection.add("Type", "Client (map_client.txt)");
        if (options != null) {
            if (instance != null && (_snowman = instance.getVideoWarningManager().method_30920()) != null) {
                crashReportSection.add("GPU Warnings", _snowman);
            }
            crashReportSection.add("Graphics mode", (Object)options.graphicsMode);
            crashReportSection.add("Resource Packs", () -> {
                StringBuilder stringBuilder = new StringBuilder();
                for (String string : gameOptions.resourcePacks) {
                    if (stringBuilder.length() > 0) {
                        stringBuilder.append(", ");
                    }
                    stringBuilder.append(string);
                    if (!gameOptions.incompatibleResourcePacks.contains(string)) continue;
                    stringBuilder.append(" (incompatible)");
                }
                return stringBuilder.toString();
            });
        }
        if (languageManager != null) {
            crashReportSection.add("Current Language", () -> languageManager.getLanguage().toString());
        }
        crashReportSection.add("CPU", GlDebugInfo::getCpuInfo);
    }

    public static MinecraftClient getInstance() {
        return instance;
    }

    public CompletableFuture<Void> reloadResourcesConcurrently() {
        return this.submit(this::reloadResources).thenCompose(completableFuture -> completableFuture);
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
        int _snowman2 = 0;
        for (ResourcePackProfile resourcePackProfile : this.resourcePackManager.getEnabledProfiles()) {
            if (resourcePackProfile.isAlwaysEnabled() || resourcePackProfile.isPinned()) continue;
            snooper.addInfo("resource_pack[" + _snowman2++ + "]", resourcePackProfile.getName());
        }
        snooper.addInfo("resource_packs", _snowman2);
        if (this.server != null) {
            snooper.addInfo("snooper_partner", this.server.getSnooper().getToken());
        }
    }

    private String getCurrentAction() {
        if (this.server != null) {
            if (this.server.isRemote()) {
                return "hosting_lan";
            }
            return "singleplayer";
        }
        if (this.currentServerEntry != null) {
            if (this.currentServerEntry.isLocal()) {
                return "playing_lan";
            }
            return "multiplayer";
        }
        return "out_of_game";
    }

    public void setCurrentServerEntry(@Nullable ServerInfo serverInfo) {
        this.currentServerEntry = serverInfo;
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
            this.sessionPropertyMap.putAll((Multimap)gameProfile.getProperties());
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
        }
        if (this.player != null) {
            if (this.player.world.getRegistryKey() == World.END) {
                if (this.inGameHud.getBossBarHud().shouldPlayDragonMusic()) {
                    return MusicType.DRAGON;
                }
                return MusicType.END;
            }
            Biome.Category category = this.player.world.getBiome(this.player.getBlockPos()).getCategory();
            if (this.musicTracker.isPlayingType(MusicType.UNDERWATER) || this.player.isSubmergedInWater() && (category == Biome.Category.OCEAN || category == Biome.Category.RIVER)) {
                return MusicType.UNDERWATER;
            }
            if (this.player.world.getRegistryKey() != World.NETHER && this.player.abilities.creativeMode && this.player.abilities.allowFlying) {
                return MusicType.CREATIVE;
            }
            return this.world.getBiomeAccess().method_27344(this.player.getBlockPos()).getMusic().orElse(MusicType.GAME);
        }
        return MusicType.MENU;
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
        return entity.isGlowing() || this.player != null && this.player.isSpectator() && this.options.keySpectatorOutlines.isPressed() && entity.getType() == EntityType.PLAYER;
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

    private static ResourcePackProfile createResourcePackProfile(String name, boolean alwaysEnabled, Supplier<ResourcePack> packFactory, ResourcePack pack, PackResourceMetadata metadata, ResourcePackProfile.InsertionPosition insertionPosition, ResourcePackSource source) {
        int n = metadata.getPackFormat();
        Supplier<ResourcePack> _snowman2 = packFactory;
        if (n <= 3) {
            _snowman2 = MinecraftClient.createV3ResourcePackFactory(_snowman2);
        }
        if (n <= 4) {
            _snowman2 = MinecraftClient.createV4ResourcePackFactory(_snowman2);
        }
        return new ResourcePackProfile(name, alwaysEnabled, _snowman2, pack, metadata, insertionPosition, source);
    }

    private static Supplier<ResourcePack> createV3ResourcePackFactory(Supplier<ResourcePack> packFactory) {
        return () -> new Format3ResourcePack((ResourcePack)packFactory.get(), Format3ResourcePack.NEW_TO_OLD_MAP);
    }

    private static Supplier<ResourcePack> createV4ResourcePackFactory(Supplier<ResourcePack> packFactory) {
        return () -> new Format4ResourcePack((ResourcePack)packFactory.get());
    }

    public void resetMipmapLevels(int mipmapLevels) {
        this.bakedModelManager.resetMipmapLevels(mipmapLevels);
    }

    private /* synthetic */ IntegratedServer method_29603(DynamicRegistryManager.Impl registryTracker, LevelStorage.Session session, IntegratedResourceManager integratedResourceManager, SaveProperties saveProperties, MinecraftSessionService sessionService, GameProfileRepository profileRepository, UserCache userCache, Thread serverThread) {
        return new IntegratedServer(serverThread, this, registryTracker, session, integratedResourceManager.getResourcePackManager(), integratedResourceManager.getServerResourceManager(), saveProperties, sessionService, profileRepository, userCache, n -> {
            WorldGenerationProgressTracker worldGenerationProgressTracker = new WorldGenerationProgressTracker(n + 0);
            worldGenerationProgressTracker.start();
            this.worldGenProgressTracker.set(worldGenerationProgressTracker);
            return new QueueingWorldGenerationProgressListener(worldGenerationProgressTracker, this.renderTaskQueue::add);
        });
    }

    private static /* synthetic */ void method_18508(CompletableFuture completableFuture) {
        completableFuture.complete(null);
    }

    static {
        LOGGER = LogManager.getLogger();
        IS_SYSTEM_MAC = Util.getOperatingSystem() == Util.OperatingSystem.OSX;
        DEFAULT_FONT_ID = new Identifier("default");
        UNICODE_FONT_ID = new Identifier("uniform");
        ALT_TEXT_RENDERER_ID = new Identifier("alt");
        COMPLETED_UNIT_FUTURE = CompletableFuture.completedFuture(Unit.INSTANCE);
        field_26841 = new TranslatableText("multiplayer.socialInteractions.not_available");
        memoryReservedForCrash = new byte[0xA00000];
    }

    public static final class IntegratedResourceManager
    implements AutoCloseable {
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

    }
}

