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
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dkd {
   private static final Logger aX = LogManager.getLogger();
   private static final Gson aY = new Gson();
   private static final TypeToken<List<String>> aZ = new TypeToken<List<String>>() {
   };
   private static final Splitter ba = Splitter.on(':').limit(2);
   public double a = 0.5;
   public int b = -1;
   public float c = 1.0F;
   public int d = 120;
   public djn e = djn.c;
   public djt f = djt.b;
   public djh g = djh.c;
   public List<String> h = Lists.newArrayList();
   public List<String> i = Lists.newArrayList();
   public bfu j = bfu.a;
   public double k = 1.0;
   public double l = 0.0;
   public double m = 0.5;
   @Nullable
   public String n;
   public boolean o;
   public boolean p;
   public boolean q = true;
   private final Set<bfx> bb = Sets.newHashSet(bfx.values());
   public aqi r = aqi.b;
   public int s;
   public int t;
   public boolean u = true;
   public double v = 1.0;
   public double w = 1.0;
   public double x = 0.44366196F;
   public double y = 1.0;
   public double z = 0.0;
   public int A = 4;
   private final Map<adr, Float> bc = Maps.newEnumMap(adr.class);
   public boolean B = true;
   public dji C = dji.b;
   public eog D = eog.a;
   public boolean E = false;
   public int F = 2;
   public double G = 1.0;
   public boolean H = true;
   public int I = 1;
   public boolean J = true;
   public boolean K = true;
   public boolean L = true;
   public boolean M = true;
   public boolean N = true;
   public boolean O = true;
   public boolean P = true;
   public boolean Q;
   public boolean R;
   public boolean S;
   public boolean T = true;
   public boolean U;
   public boolean V = true;
   public boolean W;
   public boolean X = true;
   public boolean Y;
   public boolean Z;
   public boolean aa = true;
   public boolean ab;
   public boolean ac;
   public boolean ad;
   public boolean ae = true;
   public final djw af = new djw("key.forward", 87, "key.categories.movement");
   public final djw ag = new djw("key.left", 65, "key.categories.movement");
   public final djw ah = new djw("key.back", 83, "key.categories.movement");
   public final djw ai = new djw("key.right", 68, "key.categories.movement");
   public final djw aj = new djw("key.jump", 32, "key.categories.movement");
   public final djw ak = new dkl("key.sneak", 340, "key.categories.movement", () -> this.ab);
   public final djw al = new dkl("key.sprint", 341, "key.categories.movement", () -> this.ac);
   public final djw am = new djw("key.inventory", 69, "key.categories.inventory");
   public final djw an = new djw("key.swapOffhand", 70, "key.categories.inventory");
   public final djw ao = new djw("key.drop", 81, "key.categories.inventory");
   public final djw ap = new djw("key.use", deo.b.c, 1, "key.categories.gameplay");
   public final djw aq = new djw("key.attack", deo.b.c, 0, "key.categories.gameplay");
   public final djw ar = new djw("key.pickItem", deo.b.c, 2, "key.categories.gameplay");
   public final djw as = new djw("key.chat", 84, "key.categories.multiplayer");
   public final djw at = new djw("key.playerlist", 258, "key.categories.multiplayer");
   public final djw au = new djw("key.command", 47, "key.categories.multiplayer");
   public final djw av = new djw("key.socialInteractions", 80, "key.categories.multiplayer");
   public final djw aw = new djw("key.screenshot", 291, "key.categories.misc");
   public final djw ax = new djw("key.togglePerspective", 294, "key.categories.misc");
   public final djw ay = new djw("key.smoothCamera", deo.a.b(), "key.categories.misc");
   public final djw az = new djw("key.fullscreen", 300, "key.categories.misc");
   public final djw aA = new djw("key.spectatorOutlines", deo.a.b(), "key.categories.misc");
   public final djw aB = new djw("key.advancements", 76, "key.categories.misc");
   public final djw[] aC = new djw[]{
      new djw("key.hotbar.1", 49, "key.categories.inventory"),
      new djw("key.hotbar.2", 50, "key.categories.inventory"),
      new djw("key.hotbar.3", 51, "key.categories.inventory"),
      new djw("key.hotbar.4", 52, "key.categories.inventory"),
      new djw("key.hotbar.5", 53, "key.categories.inventory"),
      new djw("key.hotbar.6", 54, "key.categories.inventory"),
      new djw("key.hotbar.7", 55, "key.categories.inventory"),
      new djw("key.hotbar.8", 56, "key.categories.inventory"),
      new djw("key.hotbar.9", 57, "key.categories.inventory")
   };
   public final djw aD = new djw("key.saveToolbarActivator", 67, "key.categories.creative");
   public final djw aE = new djw("key.loadToolbarActivator", 88, "key.categories.creative");
   public final djw[] aF = (djw[])ArrayUtils.addAll(
      new djw[]{
         this.aq,
         this.ap,
         this.af,
         this.ag,
         this.ah,
         this.ai,
         this.aj,
         this.ak,
         this.al,
         this.ao,
         this.am,
         this.as,
         this.at,
         this.ar,
         this.au,
         this.av,
         this.aw,
         this.ax,
         this.ay,
         this.az,
         this.aA,
         this.an,
         this.aD,
         this.aE,
         this.aB
      },
      this.aC
   );
   protected djz aG;
   private final File bd;
   public aor aH = aor.c;
   public boolean aI;
   private djl be = djl.a;
   public boolean aJ;
   public boolean aK;
   public boolean aL;
   public String aM = "";
   public boolean aN;
   public double aO = 70.0;
   public float aP = 1.0F;
   public float aQ = 1.0F;
   public double aR;
   public int aS;
   public dke aT = dke.a;
   public dkb aU = dkb.a;
   public String aV = "en_us";
   public boolean aW;

   public dkd(djz var1, File var2) {
      this.aG = _snowman;
      this.bd = new File(_snowman, "options.txt");
      if (_snowman.S() && Runtime.getRuntime().maxMemory() >= 1000000000L) {
         dkc.q.a(32.0F);
      } else {
         dkc.q.a(16.0F);
      }

      this.b = _snowman.S() ? 12 : 8;
      this.aW = x.i() == x.b.c;
      this.a();
   }

   public float a(float var1) {
      return this.X ? _snowman : (float)this.m;
   }

   public int b(float var1) {
      return (int)(this.a(_snowman) * 255.0F) << 24 & 0xFF000000;
   }

   public int a(int var1) {
      return this.X ? _snowman : (int)(this.m * 255.0) << 24 & 0xFF000000;
   }

   public void a(djw var1, deo.a var2) {
      _snowman.b(_snowman);
      this.b();
   }

   public void a() {
      try {
         if (!this.bd.exists()) {
            return;
         }

         this.bc.clear();
         md _snowman = new md();

         try (BufferedReader _snowmanx = Files.newReader(this.bd, Charsets.UTF_8)) {
            _snowmanx.lines().forEach(var1x -> {
               try {
                  Iterator<String> _snowmanxx = ba.split(var1x).iterator();
                  _snowman.a(_snowmanxx.next(), _snowmanxx.next());
               } catch (Exception var3) {
                  aX.warn("Skipping bad option: {}", var1x);
               }
            });
         }

         md _snowmanx = this.a(_snowman);
         if (!_snowmanx.e("graphicsMode") && _snowmanx.e("fancyGraphics")) {
            if ("true".equals(_snowmanx.l("fancyGraphics"))) {
               this.f = djt.b;
            } else {
               this.f = djt.a;
            }
         }

         for (String _snowmanxx : _snowmanx.d()) {
            String _snowmanxxx = _snowmanx.l(_snowmanxx);

            try {
               if ("autoJump".equals(_snowmanxx)) {
                  dkc.E.a(this, _snowmanxxx);
               }

               if ("autoSuggestions".equals(_snowmanxx)) {
                  dkc.F.a(this, _snowmanxxx);
               }

               if ("chatColors".equals(_snowmanxx)) {
                  dkc.H.a(this, _snowmanxxx);
               }

               if ("chatLinks".equals(_snowmanxx)) {
                  dkc.I.a(this, _snowmanxxx);
               }

               if ("chatLinksPrompt".equals(_snowmanxx)) {
                  dkc.J.a(this, _snowmanxxx);
               }

               if ("enableVsync".equals(_snowmanxx)) {
                  dkc.L.a(this, _snowmanxxx);
               }

               if ("entityShadows".equals(_snowmanxx)) {
                  dkc.M.a(this, _snowmanxxx);
               }

               if ("forceUnicodeFont".equals(_snowmanxx)) {
                  dkc.N.a(this, _snowmanxxx);
               }

               if ("discrete_mouse_scroll".equals(_snowmanxx)) {
                  dkc.K.a(this, _snowmanxxx);
               }

               if ("invertYMouse".equals(_snowmanxx)) {
                  dkc.O.a(this, _snowmanxxx);
               }

               if ("realmsNotifications".equals(_snowmanxx)) {
                  dkc.P.a(this, _snowmanxxx);
               }

               if ("reducedDebugInfo".equals(_snowmanxx)) {
                  dkc.Q.a(this, _snowmanxxx);
               }

               if ("showSubtitles".equals(_snowmanxx)) {
                  dkc.R.a(this, _snowmanxxx);
               }

               if ("snooperEnabled".equals(_snowmanxx)) {
                  dkc.S.a(this, _snowmanxxx);
               }

               if ("touchscreen".equals(_snowmanxx)) {
                  dkc.V.a(this, _snowmanxxx);
               }

               if ("fullscreen".equals(_snowmanxx)) {
                  dkc.W.a(this, _snowmanxxx);
               }

               if ("bobView".equals(_snowmanxx)) {
                  dkc.X.a(this, _snowmanxxx);
               }

               if ("toggleCrouch".equals(_snowmanxx)) {
                  this.ab = "true".equals(_snowmanxxx);
               }

               if ("toggleSprint".equals(_snowmanxx)) {
                  this.ac = "true".equals(_snowmanxxx);
               }

               if ("mouseSensitivity".equals(_snowmanxx)) {
                  this.a = (double)a(_snowmanxxx);
               }

               if ("fov".equals(_snowmanxx)) {
                  this.aO = (double)(a(_snowmanxxx) * 40.0F + 70.0F);
               }

               if ("screenEffectScale".equals(_snowmanxx)) {
                  this.aP = a(_snowmanxxx);
               }

               if ("fovEffectScale".equals(_snowmanxx)) {
                  this.aQ = a(_snowmanxxx);
               }

               if ("gamma".equals(_snowmanxx)) {
                  this.aR = (double)a(_snowmanxxx);
               }

               if ("renderDistance".equals(_snowmanxx)) {
                  this.b = Integer.parseInt(_snowmanxxx);
               }

               if ("entityDistanceScaling".equals(_snowmanxx)) {
                  this.c = Float.parseFloat(_snowmanxxx);
               }

               if ("guiScale".equals(_snowmanxx)) {
                  this.aS = Integer.parseInt(_snowmanxxx);
               }

               if ("particles".equals(_snowmanxx)) {
                  this.aT = dke.a(Integer.parseInt(_snowmanxxx));
               }

               if ("maxFps".equals(_snowmanxx)) {
                  this.d = Integer.parseInt(_snowmanxxx);
                  if (this.aG.aD() != null) {
                     this.aG.aD().a(this.d);
                  }
               }

               if ("difficulty".equals(_snowmanxx)) {
                  this.aH = aor.a(Integer.parseInt(_snowmanxxx));
               }

               if ("graphicsMode".equals(_snowmanxx)) {
                  this.f = djt.a(Integer.parseInt(_snowmanxxx));
               }

               if ("tutorialStep".equals(_snowmanxx)) {
                  this.D = eog.a(_snowmanxxx);
               }

               if ("ao".equals(_snowmanxx)) {
                  if ("true".equals(_snowmanxxx)) {
                     this.g = djh.c;
                  } else if ("false".equals(_snowmanxxx)) {
                     this.g = djh.a;
                  } else {
                     this.g = djh.a(Integer.parseInt(_snowmanxxx));
                  }
               }

               if ("renderClouds".equals(_snowmanxx)) {
                  if ("true".equals(_snowmanxxx)) {
                     this.e = djn.c;
                  } else if ("false".equals(_snowmanxxx)) {
                     this.e = djn.a;
                  } else if ("fast".equals(_snowmanxxx)) {
                     this.e = djn.b;
                  }
               }

               if ("attackIndicator".equals(_snowmanxx)) {
                  this.C = dji.a(Integer.parseInt(_snowmanxxx));
               }

               if ("resourcePacks".equals(_snowmanxx)) {
                  this.h = afd.a(aY, _snowmanxxx, aZ);
                  if (this.h == null) {
                     this.h = Lists.newArrayList();
                  }
               }

               if ("incompatibleResourcePacks".equals(_snowmanxx)) {
                  this.i = afd.a(aY, _snowmanxxx, aZ);
                  if (this.i == null) {
                     this.i = Lists.newArrayList();
                  }
               }

               if ("lastServer".equals(_snowmanxx)) {
                  this.aM = _snowmanxxx;
               }

               if ("lang".equals(_snowmanxx)) {
                  this.aV = _snowmanxxx;
               }

               if ("chatVisibility".equals(_snowmanxx)) {
                  this.j = bfu.a(Integer.parseInt(_snowmanxxx));
               }

               if ("chatOpacity".equals(_snowmanxx)) {
                  this.k = (double)a(_snowmanxxx);
               }

               if ("chatLineSpacing".equals(_snowmanxx)) {
                  this.l = (double)a(_snowmanxxx);
               }

               if ("textBackgroundOpacity".equals(_snowmanxx)) {
                  this.m = (double)a(_snowmanxxx);
               }

               if ("backgroundForChatOnly".equals(_snowmanxx)) {
                  this.X = "true".equals(_snowmanxxx);
               }

               if ("fullscreenResolution".equals(_snowmanxx)) {
                  this.n = _snowmanxxx;
               }

               if ("hideServerAddress".equals(_snowmanxx)) {
                  this.o = "true".equals(_snowmanxxx);
               }

               if ("advancedItemTooltips".equals(_snowmanxx)) {
                  this.p = "true".equals(_snowmanxxx);
               }

               if ("pauseOnLostFocus".equals(_snowmanxx)) {
                  this.q = "true".equals(_snowmanxxx);
               }

               if ("overrideHeight".equals(_snowmanxx)) {
                  this.t = Integer.parseInt(_snowmanxxx);
               }

               if ("overrideWidth".equals(_snowmanxx)) {
                  this.s = Integer.parseInt(_snowmanxxx);
               }

               if ("heldItemTooltips".equals(_snowmanxx)) {
                  this.u = "true".equals(_snowmanxxx);
               }

               if ("chatHeightFocused".equals(_snowmanxx)) {
                  this.y = (double)a(_snowmanxxx);
               }

               if ("chatDelay".equals(_snowmanxx)) {
                  this.z = (double)a(_snowmanxxx);
               }

               if ("chatHeightUnfocused".equals(_snowmanxx)) {
                  this.x = (double)a(_snowmanxxx);
               }

               if ("chatScale".equals(_snowmanxx)) {
                  this.v = (double)a(_snowmanxxx);
               }

               if ("chatWidth".equals(_snowmanxx)) {
                  this.w = (double)a(_snowmanxxx);
               }

               if ("mipmapLevels".equals(_snowmanxx)) {
                  this.A = Integer.parseInt(_snowmanxxx);
               }

               if ("useNativeTransport".equals(_snowmanxx)) {
                  this.B = "true".equals(_snowmanxxx);
               }

               if ("mainHand".equals(_snowmanxx)) {
                  this.r = "left".equals(_snowmanxxx) ? aqi.a : aqi.b;
               }

               if ("narrator".equals(_snowmanxx)) {
                  this.aU = dkb.a(Integer.parseInt(_snowmanxxx));
               }

               if ("biomeBlendRadius".equals(_snowmanxx)) {
                  this.F = Integer.parseInt(_snowmanxxx);
               }

               if ("mouseWheelSensitivity".equals(_snowmanxx)) {
                  this.G = (double)a(_snowmanxxx);
               }

               if ("rawMouseInput".equals(_snowmanxx)) {
                  this.H = "true".equals(_snowmanxxx);
               }

               if ("glDebugVerbosity".equals(_snowmanxx)) {
                  this.I = Integer.parseInt(_snowmanxxx);
               }

               if ("skipMultiplayerWarning".equals(_snowmanxx)) {
                  this.ad = "true".equals(_snowmanxxx);
               }

               if ("hideMatchedNames".equals(_snowmanxx)) {
                  this.ae = "true".equals(_snowmanxxx);
               }

               if ("joinedFirstServer".equals(_snowmanxx)) {
                  this.E = "true".equals(_snowmanxxx);
               }

               if ("syncChunkWrites".equals(_snowmanxx)) {
                  this.aW = "true".equals(_snowmanxxx);
               }

               for (djw _snowmanxxxx : this.aF) {
                  if (_snowmanxx.equals("key_" + _snowmanxxxx.g())) {
                     _snowmanxxxx.b(deo.a(_snowmanxxx));
                  }
               }

               for (adr _snowmanxxxxx : adr.values()) {
                  if (_snowmanxx.equals("soundCategory_" + _snowmanxxxxx.a())) {
                     this.bc.put(_snowmanxxxxx, a(_snowmanxxx));
                  }
               }

               for (bfx _snowmanxxxxxx : bfx.values()) {
                  if (_snowmanxx.equals("modelPart_" + _snowmanxxxxxx.c())) {
                     this.a(_snowmanxxxxxx, "true".equals(_snowmanxxx));
                  }
               }
            } catch (Exception var19) {
               aX.warn("Skipping bad option: {}:{}", _snowmanxx, _snowmanxxx);
            }
         }

         djw.c();
      } catch (Exception var20) {
         aX.error("Failed to load options", var20);
      }
   }

   private md a(md var1) {
      int _snowman = 0;

      try {
         _snowman = Integer.parseInt(_snowman.l("version"));
      } catch (RuntimeException var4) {
      }

      return mp.a(this.aG.ai(), aga.e, _snowman, _snowman);
   }

   private static float a(String var0) {
      if ("true".equals(_snowman)) {
         return 1.0F;
      } else {
         return "false".equals(_snowman) ? 0.0F : Float.parseFloat(_snowman);
      }
   }

   public void b() {
      try (PrintWriter _snowman = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.bd), StandardCharsets.UTF_8))) {
         _snowman.println("version:" + w.a().getWorldVersion());
         _snowman.println("autoJump:" + dkc.E.b(this));
         _snowman.println("autoSuggestions:" + dkc.F.b(this));
         _snowman.println("chatColors:" + dkc.H.b(this));
         _snowman.println("chatLinks:" + dkc.I.b(this));
         _snowman.println("chatLinksPrompt:" + dkc.J.b(this));
         _snowman.println("enableVsync:" + dkc.L.b(this));
         _snowman.println("entityShadows:" + dkc.M.b(this));
         _snowman.println("forceUnicodeFont:" + dkc.N.b(this));
         _snowman.println("discrete_mouse_scroll:" + dkc.K.b(this));
         _snowman.println("invertYMouse:" + dkc.O.b(this));
         _snowman.println("realmsNotifications:" + dkc.P.b(this));
         _snowman.println("reducedDebugInfo:" + dkc.Q.b(this));
         _snowman.println("snooperEnabled:" + dkc.S.b(this));
         _snowman.println("showSubtitles:" + dkc.R.b(this));
         _snowman.println("touchscreen:" + dkc.V.b(this));
         _snowman.println("fullscreen:" + dkc.W.b(this));
         _snowman.println("bobView:" + dkc.X.b(this));
         _snowman.println("toggleCrouch:" + this.ab);
         _snowman.println("toggleSprint:" + this.ac);
         _snowman.println("mouseSensitivity:" + this.a);
         _snowman.println("fov:" + (this.aO - 70.0) / 40.0);
         _snowman.println("screenEffectScale:" + this.aP);
         _snowman.println("fovEffectScale:" + this.aQ);
         _snowman.println("gamma:" + this.aR);
         _snowman.println("renderDistance:" + this.b);
         _snowman.println("entityDistanceScaling:" + this.c);
         _snowman.println("guiScale:" + this.aS);
         _snowman.println("particles:" + this.aT.b());
         _snowman.println("maxFps:" + this.d);
         _snowman.println("difficulty:" + this.aH.a());
         _snowman.println("graphicsMode:" + this.f.a());
         _snowman.println("ao:" + this.g.a());
         _snowman.println("biomeBlendRadius:" + this.F);
         switch (this.e) {
            case c:
               _snowman.println("renderClouds:true");
               break;
            case b:
               _snowman.println("renderClouds:fast");
               break;
            case a:
               _snowman.println("renderClouds:false");
         }

         _snowman.println("resourcePacks:" + aY.toJson(this.h));
         _snowman.println("incompatibleResourcePacks:" + aY.toJson(this.i));
         _snowman.println("lastServer:" + this.aM);
         _snowman.println("lang:" + this.aV);
         _snowman.println("chatVisibility:" + this.j.a());
         _snowman.println("chatOpacity:" + this.k);
         _snowman.println("chatLineSpacing:" + this.l);
         _snowman.println("textBackgroundOpacity:" + this.m);
         _snowman.println("backgroundForChatOnly:" + this.X);
         if (this.aG.aD().f().isPresent()) {
            _snowman.println("fullscreenResolution:" + this.aG.aD().f().get().g());
         }

         _snowman.println("hideServerAddress:" + this.o);
         _snowman.println("advancedItemTooltips:" + this.p);
         _snowman.println("pauseOnLostFocus:" + this.q);
         _snowman.println("overrideWidth:" + this.s);
         _snowman.println("overrideHeight:" + this.t);
         _snowman.println("heldItemTooltips:" + this.u);
         _snowman.println("chatHeightFocused:" + this.y);
         _snowman.println("chatDelay: " + this.z);
         _snowman.println("chatHeightUnfocused:" + this.x);
         _snowman.println("chatScale:" + this.v);
         _snowman.println("chatWidth:" + this.w);
         _snowman.println("mipmapLevels:" + this.A);
         _snowman.println("useNativeTransport:" + this.B);
         _snowman.println("mainHand:" + (this.r == aqi.a ? "left" : "right"));
         _snowman.println("attackIndicator:" + this.C.a());
         _snowman.println("narrator:" + this.aU.a());
         _snowman.println("tutorialStep:" + this.D.a());
         _snowman.println("mouseWheelSensitivity:" + this.G);
         _snowman.println("rawMouseInput:" + dkc.p.b(this));
         _snowman.println("glDebugVerbosity:" + this.I);
         _snowman.println("skipMultiplayerWarning:" + this.ad);
         _snowman.println("hideMatchedNames:" + this.ae);
         _snowman.println("joinedFirstServer:" + this.E);
         _snowman.println("syncChunkWrites:" + this.aW);

         for (djw _snowmanx : this.aF) {
            _snowman.println("key_" + _snowmanx.g() + ":" + _snowmanx.l());
         }

         for (adr _snowmanx : adr.values()) {
            _snowman.println("soundCategory_" + _snowmanx.a() + ":" + this.a(_snowmanx));
         }

         for (bfx _snowmanx : bfx.values()) {
            _snowman.println("modelPart_" + _snowmanx.c() + ":" + this.bb.contains(_snowmanx));
         }
      } catch (Exception var17) {
         aX.error("Failed to save options", var17);
      }

      this.c();
   }

   public float a(adr var1) {
      return this.bc.containsKey(_snowman) ? this.bc.get(_snowman) : 1.0F;
   }

   public void a(adr var1, float var2) {
      this.bc.put(_snowman, _snowman);
      this.aG.W().a(_snowman, _snowman);
   }

   public void c() {
      if (this.aG.s != null) {
         int _snowman = 0;

         for (bfx _snowmanx : this.bb) {
            _snowman |= _snowmanx.a();
         }

         this.aG.s.e.a(new sg(this.aV, this.b, this.j, this.L, _snowman, this.r));
      }
   }

   public Set<bfx> d() {
      return ImmutableSet.copyOf(this.bb);
   }

   public void a(bfx var1, boolean var2) {
      if (_snowman) {
         this.bb.add(_snowman);
      } else {
         this.bb.remove(_snowman);
      }

      this.c();
   }

   public void a(bfx var1) {
      if (this.d().contains(_snowman)) {
         this.bb.remove(_snowman);
      } else {
         this.bb.add(_snowman);
      }

      this.c();
   }

   public djn e() {
      return this.b >= 4 ? this.e : djn.a;
   }

   public boolean f() {
      return this.B;
   }

   public void a(abw var1) {
      Set<String> _snowman = Sets.newLinkedHashSet();
      Iterator<String> _snowmanx = this.h.iterator();

      while (_snowmanx.hasNext()) {
         String _snowmanxx = _snowmanx.next();
         abu _snowmanxxx = _snowman.a(_snowmanxx);
         if (_snowmanxxx == null && !_snowmanxx.startsWith("file/")) {
            _snowmanxxx = _snowman.a("file/" + _snowmanxx);
         }

         if (_snowmanxxx == null) {
            aX.warn("Removed resource pack {} from options because it doesn't seem to exist anymore", _snowmanxx);
            _snowmanx.remove();
         } else if (!_snowmanxxx.c().a() && !this.i.contains(_snowmanxx)) {
            aX.warn("Removed resource pack {} from options because it is no longer compatible", _snowmanxx);
            _snowmanx.remove();
         } else if (_snowmanxxx.c().a() && this.i.contains(_snowmanxx)) {
            aX.info("Removed resource pack {} from incompatibility list because it's now compatible", _snowmanxx);
            this.i.remove(_snowmanxx);
         } else {
            _snowman.add(_snowmanxxx.e());
         }
      }

      _snowman.a(_snowman);
   }

   public djl g() {
      return this.be;
   }

   public void a(djl var1) {
      this.be = _snowman;
   }
}
