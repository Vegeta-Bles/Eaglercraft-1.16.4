package net.minecraft.client.options;

import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.client.tutorial.TutorialStep;
import net.minecraft.client.util.InputUtil;
import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.network.packet.c2s.play.ClientSettingsC2SPacket;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Arm;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.Util;
import net.minecraft.world.Difficulty;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameOptions {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Gson GSON = new Gson();
   private static final TypeToken<List<String>> STRING_LIST_TYPE = new TypeToken<List<String>>() {
   };
   private static final Splitter COLON_SPLITTER = Splitter.on(':').limit(2);
   public double mouseSensitivity = 0.5;
   public int viewDistance = -1;
   public float entityDistanceScaling = 1.0F;
   public int maxFps = 120;
   public CloudRenderMode cloudRenderMode = CloudRenderMode.FANCY;
   public GraphicsMode graphicsMode = GraphicsMode.FANCY;
   public AoMode ao = AoMode.MAX;
   public List<String> resourcePacks = Lists.newArrayList();
   public List<String> incompatibleResourcePacks = Lists.newArrayList();
   public ChatVisibility chatVisibility = ChatVisibility.FULL;
   public double chatOpacity = 1.0;
   public double chatLineSpacing = 0.0;
   public double textBackgroundOpacity = 0.5;
   @Nullable
   public String fullscreenResolution;
   public boolean hideServerAddress;
   public boolean advancedItemTooltips;
   public boolean pauseOnLostFocus = true;
   private final Set<PlayerModelPart> enabledPlayerModelParts = Sets.newHashSet(PlayerModelPart.values());
   public Arm mainArm = Arm.RIGHT;
   public int overrideWidth;
   public int overrideHeight;
   public boolean heldItemTooltips = true;
   public double chatScale = 1.0;
   public double chatWidth = 1.0;
   public double chatHeightUnfocused = 0.44366196F;
   public double chatHeightFocused = 1.0;
   public double chatDelay = 0.0;
   public int mipmapLevels = 4;
   private final Map<SoundCategory, Float> soundVolumeLevels = Maps.newEnumMap(SoundCategory.class);
   public boolean useNativeTransport = true;
   public AttackIndicator attackIndicator = AttackIndicator.CROSSHAIR;
   public TutorialStep tutorialStep = TutorialStep.MOVEMENT;
   public boolean joinedFirstServer = false;
   public int biomeBlendRadius = 2;
   public double mouseWheelSensitivity = 1.0;
   public boolean rawMouseInput = true;
   public int glDebugVerbosity = 1;
   public boolean autoJump = true;
   public boolean autoSuggestions = true;
   public boolean chatColors = true;
   public boolean chatLinks = true;
   public boolean chatLinksPrompt = true;
   public boolean enableVsync = true;
   public boolean entityShadows = true;
   public boolean forceUnicodeFont;
   public boolean invertYMouse;
   public boolean discreteMouseScroll;
   public boolean realmsNotifications = true;
   public boolean reducedDebugInfo;
   public boolean snooperEnabled = true;
   public boolean showSubtitles;
   public boolean backgroundForChatOnly = true;
   public boolean touchscreen;
   public boolean fullscreen;
   public boolean bobView = true;
   public boolean sneakToggled;
   public boolean sprintToggled;
   public boolean skipMultiplayerWarning;
   public boolean field_26926 = true;
   public final KeyBinding keyForward = new KeyBinding("key.forward", 87, "key.categories.movement");
   public final KeyBinding keyLeft = new KeyBinding("key.left", 65, "key.categories.movement");
   public final KeyBinding keyBack = new KeyBinding("key.back", 83, "key.categories.movement");
   public final KeyBinding keyRight = new KeyBinding("key.right", 68, "key.categories.movement");
   public final KeyBinding keyJump = new KeyBinding("key.jump", 32, "key.categories.movement");
   public final KeyBinding keySneak = new StickyKeyBinding("key.sneak", 340, "key.categories.movement", () -> this.sneakToggled);
   public final KeyBinding keySprint = new StickyKeyBinding("key.sprint", 341, "key.categories.movement", () -> this.sprintToggled);
   public final KeyBinding keyInventory = new KeyBinding("key.inventory", 69, "key.categories.inventory");
   public final KeyBinding keySwapHands = new KeyBinding("key.swapOffhand", 70, "key.categories.inventory");
   public final KeyBinding keyDrop = new KeyBinding("key.drop", 81, "key.categories.inventory");
   public final KeyBinding keyUse = new KeyBinding("key.use", InputUtil.Type.MOUSE, 1, "key.categories.gameplay");
   public final KeyBinding keyAttack = new KeyBinding("key.attack", InputUtil.Type.MOUSE, 0, "key.categories.gameplay");
   public final KeyBinding keyPickItem = new KeyBinding("key.pickItem", InputUtil.Type.MOUSE, 2, "key.categories.gameplay");
   public final KeyBinding keyChat = new KeyBinding("key.chat", 84, "key.categories.multiplayer");
   public final KeyBinding keyPlayerList = new KeyBinding("key.playerlist", 258, "key.categories.multiplayer");
   public final KeyBinding keyCommand = new KeyBinding("key.command", 47, "key.categories.multiplayer");
   public final KeyBinding keySocialInteractions = new KeyBinding("key.socialInteractions", 80, "key.categories.multiplayer");
   public final KeyBinding keyScreenshot = new KeyBinding("key.screenshot", 291, "key.categories.misc");
   public final KeyBinding keyTogglePerspective = new KeyBinding("key.togglePerspective", 294, "key.categories.misc");
   public final KeyBinding keySmoothCamera = new KeyBinding("key.smoothCamera", InputUtil.UNKNOWN_KEY.getCode(), "key.categories.misc");
   public final KeyBinding keyFullscreen = new KeyBinding("key.fullscreen", 300, "key.categories.misc");
   public final KeyBinding keySpectatorOutlines = new KeyBinding("key.spectatorOutlines", InputUtil.UNKNOWN_KEY.getCode(), "key.categories.misc");
   public final KeyBinding keyAdvancements = new KeyBinding("key.advancements", 76, "key.categories.misc");
   public final KeyBinding[] keysHotbar = new KeyBinding[]{
      new KeyBinding("key.hotbar.1", 49, "key.categories.inventory"),
      new KeyBinding("key.hotbar.2", 50, "key.categories.inventory"),
      new KeyBinding("key.hotbar.3", 51, "key.categories.inventory"),
      new KeyBinding("key.hotbar.4", 52, "key.categories.inventory"),
      new KeyBinding("key.hotbar.5", 53, "key.categories.inventory"),
      new KeyBinding("key.hotbar.6", 54, "key.categories.inventory"),
      new KeyBinding("key.hotbar.7", 55, "key.categories.inventory"),
      new KeyBinding("key.hotbar.8", 56, "key.categories.inventory"),
      new KeyBinding("key.hotbar.9", 57, "key.categories.inventory")
   };
   public final KeyBinding keySaveToolbarActivator = new KeyBinding("key.saveToolbarActivator", 67, "key.categories.creative");
   public final KeyBinding keyLoadToolbarActivator = new KeyBinding("key.loadToolbarActivator", 88, "key.categories.creative");
   public final KeyBinding[] keysAll = (KeyBinding[])ArrayUtils.addAll(
      new KeyBinding[]{
         this.keyAttack,
         this.keyUse,
         this.keyForward,
         this.keyLeft,
         this.keyBack,
         this.keyRight,
         this.keyJump,
         this.keySneak,
         this.keySprint,
         this.keyDrop,
         this.keyInventory,
         this.keyChat,
         this.keyPlayerList,
         this.keyPickItem,
         this.keyCommand,
         this.keySocialInteractions,
         this.keyScreenshot,
         this.keyTogglePerspective,
         this.keySmoothCamera,
         this.keyFullscreen,
         this.keySpectatorOutlines,
         this.keySwapHands,
         this.keySaveToolbarActivator,
         this.keyLoadToolbarActivator,
         this.keyAdvancements
      },
      this.keysHotbar
   );
   protected MinecraftClient client;
   private final File optionsFile;
   public Difficulty difficulty = Difficulty.NORMAL;
   public boolean hudHidden;
   private Perspective perspective = Perspective.FIRST_PERSON;
   public boolean debugEnabled;
   public boolean debugProfilerEnabled;
   public boolean debugTpsEnabled;
   public String lastServer = "";
   public boolean smoothCameraEnabled;
   public double fov = 70.0;
   public float distortionEffectScale = 1.0F;
   public float fovEffectScale = 1.0F;
   public double gamma;
   public int guiScale;
   public ParticlesMode particles = ParticlesMode.ALL;
   public NarratorMode narrator = NarratorMode.OFF;
   public String language = "en_us";
   public boolean syncChunkWrites;

   public GameOptions(MinecraftClient client, File optionsFile) {
      this.client = client;
      this.optionsFile = new File(optionsFile, "options.txt");
      if (client.is64Bit() && Runtime.getRuntime().maxMemory() >= 1000000000L) {
         Option.RENDER_DISTANCE.setMax(32.0F);
      } else {
         Option.RENDER_DISTANCE.setMax(16.0F);
      }

      this.viewDistance = client.is64Bit() ? 12 : 8;
      this.syncChunkWrites = Util.getOperatingSystem() == Util.OperatingSystem.WINDOWS;
      this.load();
   }

   public float getTextBackgroundOpacity(float fallback) {
      return this.backgroundForChatOnly ? fallback : (float)this.textBackgroundOpacity;
   }

   public int getTextBackgroundColor(float fallbackOpacity) {
      return (int)(this.getTextBackgroundOpacity(fallbackOpacity) * 255.0F) << 24 & 0xFF000000;
   }

   public int getTextBackgroundColor(int fallbackColor) {
      return this.backgroundForChatOnly ? fallbackColor : (int)(this.textBackgroundOpacity * 255.0) << 24 & 0xFF000000;
   }

   public void setKeyCode(KeyBinding key, InputUtil.Key code) {
      key.setBoundKey(code);
      this.write();
   }

   public void load() {
      try {
         if (!this.optionsFile.exists()) {
            return;
         }

         this.soundVolumeLevels.clear();
         CompoundTag _snowman = new CompoundTag();

         try (BufferedReader _snowmanx = Files.newReader(this.optionsFile, Charsets.UTF_8)) {
            _snowmanx.lines().forEach(_snowmanxx -> {
               try {
                  Iterator<String> _snowmanxxx = COLON_SPLITTER.split(_snowmanxx).iterator();
                  _snowman.putString(_snowmanxxx.next(), _snowmanxxx.next());
               } catch (Exception var3) {
                  LOGGER.warn("Skipping bad option: {}", _snowmanxx);
               }
            });
         }

         CompoundTag _snowmanx = this.update(_snowman);
         if (!_snowmanx.contains("graphicsMode") && _snowmanx.contains("fancyGraphics")) {
            if ("true".equals(_snowmanx.getString("fancyGraphics"))) {
               this.graphicsMode = GraphicsMode.FANCY;
            } else {
               this.graphicsMode = GraphicsMode.FAST;
            }
         }

         for (String _snowmanxx : _snowmanx.getKeys()) {
            String _snowmanxxx = _snowmanx.getString(_snowmanxx);

            try {
               if ("autoJump".equals(_snowmanxx)) {
                  Option.AUTO_JUMP.set(this, _snowmanxxx);
               }

               if ("autoSuggestions".equals(_snowmanxx)) {
                  Option.AUTO_SUGGESTIONS.set(this, _snowmanxxx);
               }

               if ("chatColors".equals(_snowmanxx)) {
                  Option.CHAT_COLOR.set(this, _snowmanxxx);
               }

               if ("chatLinks".equals(_snowmanxx)) {
                  Option.CHAT_LINKS.set(this, _snowmanxxx);
               }

               if ("chatLinksPrompt".equals(_snowmanxx)) {
                  Option.CHAT_LINKS_PROMPT.set(this, _snowmanxxx);
               }

               if ("enableVsync".equals(_snowmanxx)) {
                  Option.VSYNC.set(this, _snowmanxxx);
               }

               if ("entityShadows".equals(_snowmanxx)) {
                  Option.ENTITY_SHADOWS.set(this, _snowmanxxx);
               }

               if ("forceUnicodeFont".equals(_snowmanxx)) {
                  Option.FORCE_UNICODE_FONT.set(this, _snowmanxxx);
               }

               if ("discrete_mouse_scroll".equals(_snowmanxx)) {
                  Option.DISCRETE_MOUSE_SCROLL.set(this, _snowmanxxx);
               }

               if ("invertYMouse".equals(_snowmanxx)) {
                  Option.INVERT_MOUSE.set(this, _snowmanxxx);
               }

               if ("realmsNotifications".equals(_snowmanxx)) {
                  Option.REALMS_NOTIFICATIONS.set(this, _snowmanxxx);
               }

               if ("reducedDebugInfo".equals(_snowmanxx)) {
                  Option.REDUCED_DEBUG_INFO.set(this, _snowmanxxx);
               }

               if ("showSubtitles".equals(_snowmanxx)) {
                  Option.SUBTITLES.set(this, _snowmanxxx);
               }

               if ("snooperEnabled".equals(_snowmanxx)) {
                  Option.SNOOPER.set(this, _snowmanxxx);
               }

               if ("touchscreen".equals(_snowmanxx)) {
                  Option.TOUCHSCREEN.set(this, _snowmanxxx);
               }

               if ("fullscreen".equals(_snowmanxx)) {
                  Option.FULLSCREEN.set(this, _snowmanxxx);
               }

               if ("bobView".equals(_snowmanxx)) {
                  Option.VIEW_BOBBING.set(this, _snowmanxxx);
               }

               if ("toggleCrouch".equals(_snowmanxx)) {
                  this.sneakToggled = "true".equals(_snowmanxxx);
               }

               if ("toggleSprint".equals(_snowmanxx)) {
                  this.sprintToggled = "true".equals(_snowmanxxx);
               }

               if ("mouseSensitivity".equals(_snowmanxx)) {
                  this.mouseSensitivity = (double)parseFloat(_snowmanxxx);
               }

               if ("fov".equals(_snowmanxx)) {
                  this.fov = (double)(parseFloat(_snowmanxxx) * 40.0F + 70.0F);
               }

               if ("screenEffectScale".equals(_snowmanxx)) {
                  this.distortionEffectScale = parseFloat(_snowmanxxx);
               }

               if ("fovEffectScale".equals(_snowmanxx)) {
                  this.fovEffectScale = parseFloat(_snowmanxxx);
               }

               if ("gamma".equals(_snowmanxx)) {
                  this.gamma = (double)parseFloat(_snowmanxxx);
               }

               if ("renderDistance".equals(_snowmanxx)) {
                  this.viewDistance = Integer.parseInt(_snowmanxxx);
               }

               if ("entityDistanceScaling".equals(_snowmanxx)) {
                  this.entityDistanceScaling = Float.parseFloat(_snowmanxxx);
               }

               if ("guiScale".equals(_snowmanxx)) {
                  this.guiScale = Integer.parseInt(_snowmanxxx);
               }

               if ("particles".equals(_snowmanxx)) {
                  this.particles = ParticlesMode.byId(Integer.parseInt(_snowmanxxx));
               }

               if ("maxFps".equals(_snowmanxx)) {
                  this.maxFps = Integer.parseInt(_snowmanxxx);
                  if (this.client.getWindow() != null) {
                     this.client.getWindow().setFramerateLimit(this.maxFps);
                  }
               }

               if ("difficulty".equals(_snowmanxx)) {
                  this.difficulty = Difficulty.byOrdinal(Integer.parseInt(_snowmanxxx));
               }

               if ("graphicsMode".equals(_snowmanxx)) {
                  this.graphicsMode = GraphicsMode.byId(Integer.parseInt(_snowmanxxx));
               }

               if ("tutorialStep".equals(_snowmanxx)) {
                  this.tutorialStep = TutorialStep.byName(_snowmanxxx);
               }

               if ("ao".equals(_snowmanxx)) {
                  if ("true".equals(_snowmanxxx)) {
                     this.ao = AoMode.MAX;
                  } else if ("false".equals(_snowmanxxx)) {
                     this.ao = AoMode.OFF;
                  } else {
                     this.ao = AoMode.byId(Integer.parseInt(_snowmanxxx));
                  }
               }

               if ("renderClouds".equals(_snowmanxx)) {
                  if ("true".equals(_snowmanxxx)) {
                     this.cloudRenderMode = CloudRenderMode.FANCY;
                  } else if ("false".equals(_snowmanxxx)) {
                     this.cloudRenderMode = CloudRenderMode.OFF;
                  } else if ("fast".equals(_snowmanxxx)) {
                     this.cloudRenderMode = CloudRenderMode.FAST;
                  }
               }

               if ("attackIndicator".equals(_snowmanxx)) {
                  this.attackIndicator = AttackIndicator.byId(Integer.parseInt(_snowmanxxx));
               }

               if ("resourcePacks".equals(_snowmanxx)) {
                  this.resourcePacks = JsonHelper.deserialize(GSON, _snowmanxxx, STRING_LIST_TYPE);
                  if (this.resourcePacks == null) {
                     this.resourcePacks = Lists.newArrayList();
                  }
               }

               if ("incompatibleResourcePacks".equals(_snowmanxx)) {
                  this.incompatibleResourcePacks = JsonHelper.deserialize(GSON, _snowmanxxx, STRING_LIST_TYPE);
                  if (this.incompatibleResourcePacks == null) {
                     this.incompatibleResourcePacks = Lists.newArrayList();
                  }
               }

               if ("lastServer".equals(_snowmanxx)) {
                  this.lastServer = _snowmanxxx;
               }

               if ("lang".equals(_snowmanxx)) {
                  this.language = _snowmanxxx;
               }

               if ("chatVisibility".equals(_snowmanxx)) {
                  this.chatVisibility = ChatVisibility.byId(Integer.parseInt(_snowmanxxx));
               }

               if ("chatOpacity".equals(_snowmanxx)) {
                  this.chatOpacity = (double)parseFloat(_snowmanxxx);
               }

               if ("chatLineSpacing".equals(_snowmanxx)) {
                  this.chatLineSpacing = (double)parseFloat(_snowmanxxx);
               }

               if ("textBackgroundOpacity".equals(_snowmanxx)) {
                  this.textBackgroundOpacity = (double)parseFloat(_snowmanxxx);
               }

               if ("backgroundForChatOnly".equals(_snowmanxx)) {
                  this.backgroundForChatOnly = "true".equals(_snowmanxxx);
               }

               if ("fullscreenResolution".equals(_snowmanxx)) {
                  this.fullscreenResolution = _snowmanxxx;
               }

               if ("hideServerAddress".equals(_snowmanxx)) {
                  this.hideServerAddress = "true".equals(_snowmanxxx);
               }

               if ("advancedItemTooltips".equals(_snowmanxx)) {
                  this.advancedItemTooltips = "true".equals(_snowmanxxx);
               }

               if ("pauseOnLostFocus".equals(_snowmanxx)) {
                  this.pauseOnLostFocus = "true".equals(_snowmanxxx);
               }

               if ("overrideHeight".equals(_snowmanxx)) {
                  this.overrideHeight = Integer.parseInt(_snowmanxxx);
               }

               if ("overrideWidth".equals(_snowmanxx)) {
                  this.overrideWidth = Integer.parseInt(_snowmanxxx);
               }

               if ("heldItemTooltips".equals(_snowmanxx)) {
                  this.heldItemTooltips = "true".equals(_snowmanxxx);
               }

               if ("chatHeightFocused".equals(_snowmanxx)) {
                  this.chatHeightFocused = (double)parseFloat(_snowmanxxx);
               }

               if ("chatDelay".equals(_snowmanxx)) {
                  this.chatDelay = (double)parseFloat(_snowmanxxx);
               }

               if ("chatHeightUnfocused".equals(_snowmanxx)) {
                  this.chatHeightUnfocused = (double)parseFloat(_snowmanxxx);
               }

               if ("chatScale".equals(_snowmanxx)) {
                  this.chatScale = (double)parseFloat(_snowmanxxx);
               }

               if ("chatWidth".equals(_snowmanxx)) {
                  this.chatWidth = (double)parseFloat(_snowmanxxx);
               }

               if ("mipmapLevels".equals(_snowmanxx)) {
                  this.mipmapLevels = Integer.parseInt(_snowmanxxx);
               }

               if ("useNativeTransport".equals(_snowmanxx)) {
                  this.useNativeTransport = "true".equals(_snowmanxxx);
               }

               if ("mainHand".equals(_snowmanxx)) {
                  this.mainArm = "left".equals(_snowmanxxx) ? Arm.LEFT : Arm.RIGHT;
               }

               if ("narrator".equals(_snowmanxx)) {
                  this.narrator = NarratorMode.byId(Integer.parseInt(_snowmanxxx));
               }

               if ("biomeBlendRadius".equals(_snowmanxx)) {
                  this.biomeBlendRadius = Integer.parseInt(_snowmanxxx);
               }

               if ("mouseWheelSensitivity".equals(_snowmanxx)) {
                  this.mouseWheelSensitivity = (double)parseFloat(_snowmanxxx);
               }

               if ("rawMouseInput".equals(_snowmanxx)) {
                  this.rawMouseInput = "true".equals(_snowmanxxx);
               }

               if ("glDebugVerbosity".equals(_snowmanxx)) {
                  this.glDebugVerbosity = Integer.parseInt(_snowmanxxx);
               }

               if ("skipMultiplayerWarning".equals(_snowmanxx)) {
                  this.skipMultiplayerWarning = "true".equals(_snowmanxxx);
               }

               if ("hideMatchedNames".equals(_snowmanxx)) {
                  this.field_26926 = "true".equals(_snowmanxxx);
               }

               if ("joinedFirstServer".equals(_snowmanxx)) {
                  this.joinedFirstServer = "true".equals(_snowmanxxx);
               }

               if ("syncChunkWrites".equals(_snowmanxx)) {
                  this.syncChunkWrites = "true".equals(_snowmanxxx);
               }

               for (KeyBinding _snowmanxxxx : this.keysAll) {
                  if (_snowmanxx.equals("key_" + _snowmanxxxx.getTranslationKey())) {
                     _snowmanxxxx.setBoundKey(InputUtil.fromTranslationKey(_snowmanxxx));
                  }
               }

               for (SoundCategory _snowmanxxxxx : SoundCategory.values()) {
                  if (_snowmanxx.equals("soundCategory_" + _snowmanxxxxx.getName())) {
                     this.soundVolumeLevels.put(_snowmanxxxxx, parseFloat(_snowmanxxx));
                  }
               }

               for (PlayerModelPart _snowmanxxxxxx : PlayerModelPart.values()) {
                  if (_snowmanxx.equals("modelPart_" + _snowmanxxxxxx.getName())) {
                     this.setPlayerModelPart(_snowmanxxxxxx, "true".equals(_snowmanxxx));
                  }
               }
            } catch (Exception var19) {
               LOGGER.warn("Skipping bad option: {}:{}", _snowmanxx, _snowmanxxx);
            }
         }

         KeyBinding.updateKeysByCode();
      } catch (Exception var20) {
         LOGGER.error("Failed to load options", var20);
      }
   }

   private CompoundTag update(CompoundTag tag) {
      int _snowman = 0;

      try {
         _snowman = Integer.parseInt(tag.getString("version"));
      } catch (RuntimeException var4) {
      }

      return NbtHelper.update(this.client.getDataFixer(), DataFixTypes.OPTIONS, tag, _snowman);
   }

   private static float parseFloat(String _snowman) {
      if ("true".equals(_snowman)) {
         return 1.0F;
      } else {
         return "false".equals(_snowman) ? 0.0F : Float.parseFloat(_snowman);
      }
   }

   public void write() {
      try (PrintWriter _snowman = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.optionsFile), StandardCharsets.UTF_8))) {
         _snowman.println("version:" + SharedConstants.getGameVersion().getWorldVersion());
         _snowman.println("autoJump:" + Option.AUTO_JUMP.get(this));
         _snowman.println("autoSuggestions:" + Option.AUTO_SUGGESTIONS.get(this));
         _snowman.println("chatColors:" + Option.CHAT_COLOR.get(this));
         _snowman.println("chatLinks:" + Option.CHAT_LINKS.get(this));
         _snowman.println("chatLinksPrompt:" + Option.CHAT_LINKS_PROMPT.get(this));
         _snowman.println("enableVsync:" + Option.VSYNC.get(this));
         _snowman.println("entityShadows:" + Option.ENTITY_SHADOWS.get(this));
         _snowman.println("forceUnicodeFont:" + Option.FORCE_UNICODE_FONT.get(this));
         _snowman.println("discrete_mouse_scroll:" + Option.DISCRETE_MOUSE_SCROLL.get(this));
         _snowman.println("invertYMouse:" + Option.INVERT_MOUSE.get(this));
         _snowman.println("realmsNotifications:" + Option.REALMS_NOTIFICATIONS.get(this));
         _snowman.println("reducedDebugInfo:" + Option.REDUCED_DEBUG_INFO.get(this));
         _snowman.println("snooperEnabled:" + Option.SNOOPER.get(this));
         _snowman.println("showSubtitles:" + Option.SUBTITLES.get(this));
         _snowman.println("touchscreen:" + Option.TOUCHSCREEN.get(this));
         _snowman.println("fullscreen:" + Option.FULLSCREEN.get(this));
         _snowman.println("bobView:" + Option.VIEW_BOBBING.get(this));
         _snowman.println("toggleCrouch:" + this.sneakToggled);
         _snowman.println("toggleSprint:" + this.sprintToggled);
         _snowman.println("mouseSensitivity:" + this.mouseSensitivity);
         _snowman.println("fov:" + (this.fov - 70.0) / 40.0);
         _snowman.println("screenEffectScale:" + this.distortionEffectScale);
         _snowman.println("fovEffectScale:" + this.fovEffectScale);
         _snowman.println("gamma:" + this.gamma);
         _snowman.println("renderDistance:" + this.viewDistance);
         _snowman.println("entityDistanceScaling:" + this.entityDistanceScaling);
         _snowman.println("guiScale:" + this.guiScale);
         _snowman.println("particles:" + this.particles.getId());
         _snowman.println("maxFps:" + this.maxFps);
         _snowman.println("difficulty:" + this.difficulty.getId());
         _snowman.println("graphicsMode:" + this.graphicsMode.getId());
         _snowman.println("ao:" + this.ao.getId());
         _snowman.println("biomeBlendRadius:" + this.biomeBlendRadius);
         switch (this.cloudRenderMode) {
            case FANCY:
               _snowman.println("renderClouds:true");
               break;
            case FAST:
               _snowman.println("renderClouds:fast");
               break;
            case OFF:
               _snowman.println("renderClouds:false");
         }

         _snowman.println("resourcePacks:" + GSON.toJson(this.resourcePacks));
         _snowman.println("incompatibleResourcePacks:" + GSON.toJson(this.incompatibleResourcePacks));
         _snowman.println("lastServer:" + this.lastServer);
         _snowman.println("lang:" + this.language);
         _snowman.println("chatVisibility:" + this.chatVisibility.getId());
         _snowman.println("chatOpacity:" + this.chatOpacity);
         _snowman.println("chatLineSpacing:" + this.chatLineSpacing);
         _snowman.println("textBackgroundOpacity:" + this.textBackgroundOpacity);
         _snowman.println("backgroundForChatOnly:" + this.backgroundForChatOnly);
         if (this.client.getWindow().getVideoMode().isPresent()) {
            _snowman.println("fullscreenResolution:" + this.client.getWindow().getVideoMode().get().asString());
         }

         _snowman.println("hideServerAddress:" + this.hideServerAddress);
         _snowman.println("advancedItemTooltips:" + this.advancedItemTooltips);
         _snowman.println("pauseOnLostFocus:" + this.pauseOnLostFocus);
         _snowman.println("overrideWidth:" + this.overrideWidth);
         _snowman.println("overrideHeight:" + this.overrideHeight);
         _snowman.println("heldItemTooltips:" + this.heldItemTooltips);
         _snowman.println("chatHeightFocused:" + this.chatHeightFocused);
         _snowman.println("chatDelay: " + this.chatDelay);
         _snowman.println("chatHeightUnfocused:" + this.chatHeightUnfocused);
         _snowman.println("chatScale:" + this.chatScale);
         _snowman.println("chatWidth:" + this.chatWidth);
         _snowman.println("mipmapLevels:" + this.mipmapLevels);
         _snowman.println("useNativeTransport:" + this.useNativeTransport);
         _snowman.println("mainHand:" + (this.mainArm == Arm.LEFT ? "left" : "right"));
         _snowman.println("attackIndicator:" + this.attackIndicator.getId());
         _snowman.println("narrator:" + this.narrator.getId());
         _snowman.println("tutorialStep:" + this.tutorialStep.getName());
         _snowman.println("mouseWheelSensitivity:" + this.mouseWheelSensitivity);
         _snowman.println("rawMouseInput:" + Option.RAW_MOUSE_INPUT.get(this));
         _snowman.println("glDebugVerbosity:" + this.glDebugVerbosity);
         _snowman.println("skipMultiplayerWarning:" + this.skipMultiplayerWarning);
         _snowman.println("hideMatchedNames:" + this.field_26926);
         _snowman.println("joinedFirstServer:" + this.joinedFirstServer);
         _snowman.println("syncChunkWrites:" + this.syncChunkWrites);

         for (KeyBinding _snowmanx : this.keysAll) {
            _snowman.println("key_" + _snowmanx.getTranslationKey() + ":" + _snowmanx.getBoundKeyTranslationKey());
         }

         for (SoundCategory _snowmanx : SoundCategory.values()) {
            _snowman.println("soundCategory_" + _snowmanx.getName() + ":" + this.getSoundVolume(_snowmanx));
         }

         for (PlayerModelPart _snowmanx : PlayerModelPart.values()) {
            _snowman.println("modelPart_" + _snowmanx.getName() + ":" + this.enabledPlayerModelParts.contains(_snowmanx));
         }
      } catch (Exception var17) {
         LOGGER.error("Failed to save options", var17);
      }

      this.onPlayerModelPartChange();
   }

   public float getSoundVolume(SoundCategory category) {
      return this.soundVolumeLevels.containsKey(category) ? this.soundVolumeLevels.get(category) : 1.0F;
   }

   public void setSoundVolume(SoundCategory category, float volume) {
      this.soundVolumeLevels.put(category, volume);
      this.client.getSoundManager().updateSoundVolume(category, volume);
   }

   public void onPlayerModelPartChange() {
      if (this.client.player != null) {
         int _snowman = 0;

         for (PlayerModelPart _snowmanx : this.enabledPlayerModelParts) {
            _snowman |= _snowmanx.getBitFlag();
         }

         this.client
            .player
            .networkHandler
            .sendPacket(new ClientSettingsC2SPacket(this.language, this.viewDistance, this.chatVisibility, this.chatColors, _snowman, this.mainArm));
      }
   }

   public Set<PlayerModelPart> getEnabledPlayerModelParts() {
      return ImmutableSet.copyOf(this.enabledPlayerModelParts);
   }

   public void setPlayerModelPart(PlayerModelPart part, boolean enabled) {
      if (enabled) {
         this.enabledPlayerModelParts.add(part);
      } else {
         this.enabledPlayerModelParts.remove(part);
      }

      this.onPlayerModelPartChange();
   }

   public void togglePlayerModelPart(PlayerModelPart part) {
      if (this.getEnabledPlayerModelParts().contains(part)) {
         this.enabledPlayerModelParts.remove(part);
      } else {
         this.enabledPlayerModelParts.add(part);
      }

      this.onPlayerModelPartChange();
   }

   public CloudRenderMode getCloudRenderMode() {
      return this.viewDistance >= 4 ? this.cloudRenderMode : CloudRenderMode.OFF;
   }

   public boolean shouldUseNativeTransport() {
      return this.useNativeTransport;
   }

   public void addResourcePackProfilesToManager(ResourcePackManager manager) {
      Set<String> _snowman = Sets.newLinkedHashSet();
      Iterator<String> _snowmanx = this.resourcePacks.iterator();

      while (_snowmanx.hasNext()) {
         String _snowmanxx = _snowmanx.next();
         ResourcePackProfile _snowmanxxx = manager.getProfile(_snowmanxx);
         if (_snowmanxxx == null && !_snowmanxx.startsWith("file/")) {
            _snowmanxxx = manager.getProfile("file/" + _snowmanxx);
         }

         if (_snowmanxxx == null) {
            LOGGER.warn("Removed resource pack {} from options because it doesn't seem to exist anymore", _snowmanxx);
            _snowmanx.remove();
         } else if (!_snowmanxxx.getCompatibility().isCompatible() && !this.incompatibleResourcePacks.contains(_snowmanxx)) {
            LOGGER.warn("Removed resource pack {} from options because it is no longer compatible", _snowmanxx);
            _snowmanx.remove();
         } else if (_snowmanxxx.getCompatibility().isCompatible() && this.incompatibleResourcePacks.contains(_snowmanxx)) {
            LOGGER.info("Removed resource pack {} from incompatibility list because it's now compatible", _snowmanxx);
            this.incompatibleResourcePacks.remove(_snowmanxx);
         } else {
            _snowman.add(_snowmanxxx.getName());
         }
      }

      manager.setEnabledProfiles(_snowman);
   }

   public Perspective getPerspective() {
      return this.perspective;
   }

   public void setPerspective(Perspective perspective) {
      this.perspective = perspective;
   }
}
