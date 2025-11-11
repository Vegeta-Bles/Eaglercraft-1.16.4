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
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class djz extends aof<Runnable> implements apd, dfa {
   private static djz F;
   private static final Logger G = LogManager.getLogger();
   public static final boolean a = x.i() == x.b.d;
   public static final vk b = new vk("default");
   public static final vk c = new vk("uniform");
   public static final vk d = new vk("alt");
   private static final CompletableFuture<afx> H = CompletableFuture.completedFuture(afx.a);
   private static final nr I = new of("multiplayer.socialInteractions.not_available");
   private final File J;
   private final PropertyMap K;
   private final ekd L;
   private final DataFixer M;
   private final eau N;
   private final dez O;
   private final dkk P = new dkk(20.0F, 0L);
   private final apc Q = new apc("client", this, x.b());
   private final eam R;
   public final eae e;
   private final eet S;
   private final efo T;
   private final eac U;
   public final dyi f;
   private final enb V = new enb();
   private final dkm W;
   public final dku g;
   public final dzz h;
   public final edh i;
   private final AtomicReference<aat> X = new AtomicReference<>();
   public final dkv j;
   public final dkd k;
   private final djv Y;
   public final dka l;
   public final djx m;
   public final File n;
   private final String Z;
   private final String aa;
   private final Proxy ab;
   private final cyg ac;
   public final afc o = new afc();
   private final boolean ad;
   private final boolean ae;
   private final boolean af;
   private final boolean ag;
   private final acf ah;
   private final ekh ai;
   private final abw aj;
   private final ekz ak;
   private final dko al;
   private final dks am;
   private final deg an;
   private final enu ao;
   private final enp ap;
   private final dmv aq;
   private final ekt ar;
   private final eaa as;
   private final MinecraftSessionService at;
   private final SocialInteractionsService au;
   private final eks av;
   private final elt aw;
   private final eax ax;
   private final ekr ay;
   private final ekp az;
   private final dmr aA;
   private final djs aB = new djs(this);
   private final eoe aC;
   private final dsa aD;
   public static byte[] p = new byte[10485760];
   @Nullable
   public dww q;
   @Nullable
   public dwt r;
   @Nullable
   public dzm s;
   @Nullable
   private eng aE;
   @Nullable
   private dwz aF;
   @Nullable
   private nd aG;
   private boolean aH;
   @Nullable
   public aqa t;
   @Nullable
   public aqa u;
   @Nullable
   public dcl v;
   private int aI;
   protected int w;
   private boolean aJ;
   private float aK;
   private long aL = x.c();
   private long aM;
   private int aN;
   public boolean x;
   @Nullable
   public dot y;
   @Nullable
   public don z;
   private boolean aO;
   private Thread aP;
   private volatile boolean aQ = true;
   @Nullable
   private l aR;
   private static int aS;
   public String A = "";
   public boolean C;
   public boolean D;
   public boolean E = true;
   private boolean aT;
   private final Queue<Runnable> aU = Queues.newConcurrentLinkedQueue();
   @Nullable
   private CompletableFuture<Void> aV;
   @Nullable
   private dms aW;
   private anw aX = ant.a;
   private int aY;
   private final anq aZ = new anq(x.a, () -> this.aY);
   @Nullable
   private anv ba;
   private String bb = "root";

   public djz(dsz var1) {
      super("Client");
      F = this;
      this.n = _snowman.c.a;
      File _snowman = _snowman.c.c;
      this.J = _snowman.c.b;
      this.Z = _snowman.d.b;
      this.aa = _snowman.d.c;
      this.K = _snowman.a.c;
      this.ai = new ekh(new File(this.n, "server-resource-packs"), _snowman.c.a());
      this.aj = new abw(djz::a, this.ai, new abt(this.J, abx.a));
      this.ab = _snowman.a.d;
      YggdrasilAuthenticationService _snowmanx = new YggdrasilAuthenticationService(this.ab);
      this.at = _snowmanx.createMinecraftSessionService();
      this.au = this.a(_snowmanx, _snowman);
      this.W = _snowman.a.a;
      G.info("Setting user: {}", this.W.c());
      G.debug("(Session ID is {})", this.W.a());
      this.ae = _snowman.d.a;
      this.af = !_snowman.d.d;
      this.ag = !_snowman.d.e;
      this.ad = aH();
      this.aE = null;
      String _snowmanxx;
      int _snowmanxxx;
      if (this.s() && _snowman.e.a != null) {
         _snowmanxx = _snowman.e.a;
         _snowmanxxx = _snowman.e.b;
      } else {
         _snowmanxx = null;
         _snowmanxxx = 0;
      }

      nw.a(djw::a);
      this.M = agb.a();
      this.aA = new dmr(this);
      this.aC = new eoe(this);
      this.aP = Thread.currentThread();
      this.k = new dkd(this, this.n);
      this.Y = new djv(this.n, this.M);
      G.info("Backend library: {}", RenderSystem.getBackendDescription());
      dej _snowmanxxxx;
      if (this.k.t > 0 && this.k.s > 0) {
         _snowmanxxxx = new dej(this.k.s, this.k.t, _snowman.b.c, _snowman.b.d, _snowman.b.e);
      } else {
         _snowmanxxxx = _snowman.b;
      }

      x.a = RenderSystem.initBackendSystem();
      this.N = new eau(this);
      this.O = this.N.a(_snowmanxxxx, this.k.n, this.aF());
      this.a(true);

      try {
         InputStream _snowmanxxxxx = this.P().a().a(abk.a, new vk("icons/icon_16x16.png"));
         InputStream _snowmanxxxxxx = this.P().a().a(abk.a, new vk("icons/icon_32x32.png"));
         this.O.a(_snowmanxxxxx, _snowmanxxxxxx);
      } catch (IOException var9) {
         G.error("Couldn't set icon", var9);
      }

      this.O.a(this.k.d);
      this.l = new dka(this);
      this.l.a(this.O.i());
      this.m = new djx(this);
      this.m.a(this.O.i());
      RenderSystem.initRenderer(this.k.I, false);
      this.an = new deg(this.O.k(), this.O.l(), true, a);
      this.an.a(0.0F, 0.0F, 0.0F, 0.0F);
      this.ah = new acm(abk.a);
      this.aj.a();
      this.k.a(this.aj);
      this.ak = new ekz(this.k.aV);
      this.ah.a(this.ak);
      this.L = new ekd(this.ah);
      this.ah.a(this.L);
      this.av = new eks(this.L, new File(_snowman, "skins"), this.at);
      this.ac = new cyg(this.n.toPath().resolve("saves"), this.n.toPath().resolve("backups"), this.M);
      this.ao = new enu(this.ah, this.k);
      this.ah.a(this.ao);
      this.ar = new ekt(this.W);
      this.ah.a(this.ar);
      this.ap = new enp(this);
      this.aq = new dmv(this.L);
      this.g = this.aq.a();
      this.ah.a(this.aq.b());
      this.b(this.i());
      this.ah.a(new ekm());
      this.ah.a(new ekl());
      this.O.a("Startup");
      RenderSystem.setupDefaultState(0, 0, this.O.k(), this.O.l());
      this.O.a("Post startup");
      this.al = dko.a();
      this.am = dks.a(this.al);
      this.aw = new elt(this.L, this.al, this.k.A);
      this.ah.a(this.aw);
      this.T = new efo(this.L, this.aw, this.am);
      this.S = new eet(this.L, this.T, this.ah, this.g, this.k);
      this.U = new eac(this);
      this.ah.a(this.T);
      this.R = new eam();
      this.h = new dzz(this, this.ah, this.R);
      this.ah.a(this.h);
      this.aD = new dsa(this, this.au);
      this.ax = new eax(this.aw.b(), this.al);
      this.ah.a(this.ax);
      this.e = new eae(this, this.R);
      this.ah.a(this.e);
      this.aG();
      this.ah.a(this.V);
      this.f = new dyi(this.r, this.L);
      this.ah.a(this.f);
      this.ay = new ekr(this.L);
      this.ah.a(this.ay);
      this.az = new ekp(this.L);
      this.ah.a(this.az);
      this.as = new eaa();
      this.ah.a(this.as);
      this.j = new dkv(this);
      this.i = new edh(this);
      RenderSystem.setErrorCallback(this::a);
      if (this.k.Z && !this.O.j()) {
         this.O.h();
         this.k.Z = this.O.j();
      }

      this.O.a(this.k.O);
      this.O.b(this.k.H);
      this.O.c();
      this.a();
      if (_snowmanxx != null) {
         this.a(new dnt(new doy(), this, _snowmanxx, _snowmanxxx));
      } else {
         this.a(new doy(true));
      }

      doh.a(this);
      List<abj> _snowmanxxxxx = this.aj.f();
      this.a(new doh(this, this.ah.a(x.f(), this, H, _snowmanxxxxx), var1x -> x.a(var1x, this::a, () -> {
            if (w.d) {
               this.aI();
            }
         }), false));
   }

   public void c() {
      this.O.b(this.aF());
   }

   private String aF() {
      StringBuilder _snowman = new StringBuilder("Minecraft");
      if (this.d()) {
         _snowman.append("*");
      }

      _snowman.append(" ");
      _snowman.append(w.a().getName());
      dwu _snowmanx = this.w();
      if (_snowmanx != null && _snowmanx.a().h()) {
         _snowman.append(" - ");
         if (this.aE != null && !this.aE.n()) {
            _snowman.append(ekx.a("title.singleplayer"));
         } else if (this.ah()) {
            _snowman.append(ekx.a("title.multiplayer.realms"));
         } else if (this.aE == null && (this.aF == null || !this.aF.d())) {
            _snowman.append(ekx.a("title.multiplayer.other"));
         } else {
            _snowman.append(ekx.a("title.multiplayer.lan"));
         }
      }

      return _snowman.toString();
   }

   private SocialInteractionsService a(YggdrasilAuthenticationService var1, dsz var2) {
      try {
         return _snowman.createSocialInteractionsService(_snowman.a.a.d());
      } catch (AuthenticationException var4) {
         G.error("Failed to verify authentication", var4);
         return new OfflineSocialInteractions();
      }
   }

   public boolean d() {
      return !"vanilla".equals(ClientBrandRetriever.getClientModName()) || djz.class.getSigners() == null;
   }

   private void a(Throwable var1) {
      if (this.aj.d().size() > 1) {
         nr _snowman;
         if (_snowman instanceof acm.b) {
            _snowman = new oe(((acm.b)_snowman).a().a());
         } else {
            _snowman = null;
         }

         this.a(_snowman, _snowman);
      } else {
         x.b(_snowman);
      }
   }

   public void a(Throwable var1, @Nullable nr var2) {
      G.info("Caught error loading resourcepacks, removing all selected resourcepacks", _snowman);
      this.aj.a(Collections.emptyList());
      this.k.h.clear();
      this.k.i.clear();
      this.k.b();
      this.j().thenRun(() -> {
         dmr _snowman = this.an();
         dmp.b(_snowman, dmp.a.e, new of("resourcePack.load_fail"), _snowman);
      });
   }

   public void e() {
      this.aP = Thread.currentThread();

      try {
         boolean _snowman = false;

         while (this.aQ) {
            if (this.aR != null) {
               b(this.aR);
               return;
            }

            try {
               anz _snowmanx = anz.a("Renderer");
               boolean _snowmanxx = this.aJ();
               this.a(_snowmanxx, _snowmanx);
               this.aX.a();
               this.e(!_snowman);
               this.aX.b();
               this.b(_snowmanxx, _snowmanx);
            } catch (OutOfMemoryError var4) {
               if (_snowman) {
                  throw var4;
               }

               this.m();
               this.a(new dom());
               System.gc();
               G.fatal("Out of memory", var4);
               _snowman = true;
            }
         }
      } catch (u var5) {
         this.c(var5.a());
         this.m();
         G.fatal("Reported exception thrown!", var5);
         b(var5.a());
      } catch (Throwable var6) {
         l _snowman = this.c(new l("Unexpected error", var6));
         G.fatal("Unreported exception thrown!", var6);
         this.m();
         b(_snowman);
      }
   }

   void b(boolean var1) {
      this.aq.a(_snowman ? ImmutableMap.of(b, c) : ImmutableMap.of());
   }

   private void aG() {
      ena<bmb> _snowman = new ena<>(
         var0 -> var0.a(null, bnl.a.a).stream().map(var0x -> k.a(var0x.getString()).trim()).filter(var0x -> !var0x.isEmpty()),
         var0 -> Stream.of(gm.T.b(var0.b()))
      );
      emz<bmb> _snowmanx = new emz<>(var0 -> aeg.a().a(var0.b()).stream());
      gj<bmb> _snowmanxx = gj.a();

      for (blx _snowmanxxx : gm.T) {
         _snowmanxxx.a(bks.g, _snowmanxx);
      }

      _snowmanxx.forEach(var2x -> {
         _snowman.a(var2x);
         _snowman.a(var2x);
      });
      ena<drt> _snowmanxxx = new ena<>(
         var0 -> var0.d()
               .stream()
               .flatMap(var0x -> var0x.c().a(null, bnl.a.a).stream())
               .map(var0x -> k.a(var0x.getString()).trim())
               .filter(var0x -> !var0x.isEmpty()),
         var0 -> var0.d().stream().map(var0x -> gm.T.b(var0x.c().b()))
      );
      this.V.a(enb.a, _snowman);
      this.V.a(enb.b, _snowmanx);
      this.V.a(enb.c, _snowmanxxx);
   }

   private void a(int var1, long var2) {
      this.k.O = false;
      this.k.b();
   }

   private static boolean aH() {
      String[] _snowman = new String[]{"sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch"};

      for (String _snowmanx : _snowman) {
         String _snowmanxx = System.getProperty(_snowmanx);
         if (_snowmanxx != null && _snowmanxx.contains("64")) {
            return true;
         }
      }

      return false;
   }

   public deg f() {
      return this.an;
   }

   public String g() {
      return this.Z;
   }

   public String h() {
      return this.aa;
   }

   public void a(l var1) {
      this.aR = _snowman;
   }

   public static void b(l var0) {
      File _snowman = new File(C().n, "crash-reports");
      File _snowmanx = new File(_snowman, "crash-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + "-client.txt");
      vm.a(_snowman.e());
      if (_snowman.f() != null) {
         vm.a("#@!@# Game crashed! Crash report saved to: #@!@# " + _snowman.f());
         System.exit(-1);
      } else if (_snowman.a(_snowmanx)) {
         vm.a("#@!@# Game crashed! Crash report saved to: #@!@# " + _snowmanx.getAbsolutePath());
         System.exit(-1);
      } else {
         vm.a("#@?@# Game crashed! Crash report could not be saved. #@?@#");
         System.exit(-2);
      }
   }

   public boolean i() {
      return this.k.Q;
   }

   public CompletableFuture<Void> j() {
      if (this.aV != null) {
         return this.aV;
      } else {
         CompletableFuture<Void> _snowman = new CompletableFuture<>();
         if (this.z instanceof doh) {
            this.aV = _snowman;
            return _snowman;
         } else {
            this.aj.a();
            List<abj> _snowmanx = this.aj.f();
            this.a(new doh(this, this.ah.a(x.f(), this, H, _snowmanx), var2x -> x.a(var2x, this::a, () -> {
                  this.e.e();
                  _snowman.complete(null);
               }), true));
            return _snowman;
         }
      }
   }

   private void aI() {
      boolean _snowman = false;
      eaw _snowmanx = this.ab().a();
      elo _snowmanxx = _snowmanx.a().a();

      for (buo _snowmanxxx : gm.Q) {
         UnmodifiableIterator var6 = _snowmanxxx.m().a().iterator();

         while (var6.hasNext()) {
            ceh _snowmanxxxx = (ceh)var6.next();
            if (_snowmanxxxx.h() == bzh.c) {
               elo _snowmanxxxxx = _snowmanx.b(_snowmanxxxx);
               if (_snowmanxxxxx == _snowmanxx) {
                  G.debug("Missing model for: {}", _snowmanxxxx);
                  _snowman = true;
               }
            }
         }
      }

      ekc _snowmanxxx = _snowmanxx.e();

      for (buo _snowmanxxxx : gm.Q) {
         UnmodifiableIterator var18 = _snowmanxxxx.m().a().iterator();

         while (var18.hasNext()) {
            ceh _snowmanxxxxx = (ceh)var18.next();
            ekc _snowmanxxxxxx = _snowmanx.a(_snowmanxxxxx);
            if (!_snowmanxxxxx.g() && _snowmanxxxxxx == _snowmanxxx) {
               G.debug("Missing particle icon for: {}", _snowmanxxxxx);
               _snowman = true;
            }
         }
      }

      gj<bmb> _snowmanxxxx = gj.a();

      for (blx _snowmanxxxxx : gm.T) {
         _snowmanxxxx.clear();
         _snowmanxxxxx.a(bks.g, _snowmanxxxx);

         for (bmb _snowmanxxxxxx : _snowmanxxxx) {
            String _snowmanxxxxxxx = _snowmanxxxxxx.j();
            String _snowmanxxxxxxxx = new of(_snowmanxxxxxxx).getString();
            if (_snowmanxxxxxxxx.toLowerCase(Locale.ROOT).equals(_snowmanxxxxx.a())) {
               G.debug("Missing translation for: {} {} {}", _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxx.b());
            }
         }
      }

      _snowman |= doi.a();
      if (_snowman) {
         throw new IllegalStateException("Your game data is foobar, fix the errors above!");
      }
   }

   public cyg k() {
      return this.ac;
   }

   private void b(String var1) {
      if (this.F() || this.u()) {
         this.a(new dnq(_snowman));
      } else if (this.s != null) {
         this.s.a(new of("chat.cannotSend").a(k.m), x.b);
      }
   }

   public void a(@Nullable dot var1) {
      if (this.y != null) {
         this.y.e();
      }

      if (_snowman == null && this.r == null) {
         _snowman = new doy();
      } else if (_snowman == null && this.s.dl()) {
         if (this.s.G()) {
            _snowman = new dnx(null, this.r.w().n());
         } else {
            this.s.ey();
         }
      }

      if (_snowman instanceof doy || _snowman instanceof drc) {
         this.k.aJ = false;
         this.j.c().a(true);
      }

      this.y = _snowman;
      if (_snowman != null) {
         this.l.j();
         djw.b();
         _snowman.b(this, this.O.o(), this.O.p());
         this.x = false;
         dkz.b.a(_snowman.ax_());
      } else {
         this.ao.f();
         this.l.i();
      }

      this.c();
   }

   public void a(@Nullable don var1) {
      this.z = _snowman;
   }

   public void l() {
      try {
         G.info("Stopping!");

         try {
            dkz.b.c();
         } catch (Throwable var7) {
         }

         try {
            if (this.r != null) {
               this.r.S();
            }

            this.r();
         } catch (Throwable var6) {
         }

         if (this.y != null) {
            this.y.e();
         }

         this.close();
      } finally {
         x.a = System::nanoTime;
         if (this.aR == null) {
            System.exit(0);
         }
      }
   }

   @Override
   public void close() {
      try {
         this.aw.close();
         this.aq.close();
         this.h.close();
         this.e.close();
         this.ao.e();
         this.aj.close();
         this.f.a();
         this.az.close();
         this.ay.close();
         this.L.close();
         this.ah.close();
         x.h();
      } catch (Throwable var5) {
         G.error("Shutdown failure!", var5);
         throw var5;
      } finally {
         this.N.close();
         this.O.close();
      }
   }

   private void e(boolean var1) {
      this.O.a("Pre render");
      long _snowman = x.c();
      if (this.O.b()) {
         this.n();
      }

      if (this.aV != null && !(this.z instanceof doh)) {
         CompletableFuture<Void> _snowmanx = this.aV;
         this.aV = null;
         this.j().thenRun(() -> _snowman.complete(null));
      }

      Runnable _snowmanx;
      while ((_snowmanx = this.aU.poll()) != null) {
         _snowmanx.run();
      }

      if (_snowman) {
         int _snowmanxx = this.P.a(x.b());
         this.aX.a("scheduledExecutables");
         this.bl();
         this.aX.c();
         this.aX.a("tick");

         for (int _snowmanxxx = 0; _snowmanxxx < Math.min(10, _snowmanxx); _snowmanxxx++) {
            this.aX.c("clientTick");
            this.q();
         }

         this.aX.c();
      }

      this.l.a();
      this.O.a("Render");
      this.aX.a("sound");
      this.ao.a(this.h.k());
      this.aX.c();
      this.aX.a("render");
      RenderSystem.pushMatrix();
      RenderSystem.clear(16640, a);
      this.an.a(true);
      dzy.a();
      this.aX.a("display");
      RenderSystem.enableTexture();
      RenderSystem.enableCull();
      this.aX.c();
      if (!this.x) {
         this.aX.b("gameRenderer");
         this.h.a(this.aJ ? this.aK : this.P.a, _snowman, _snowman);
         this.aX.b("toasts");
         this.aA.a(new dfm());
         this.aX.c();
      }

      if (this.ba != null) {
         this.aX.a("fpsPie");
         this.a(new dfm(), this.ba);
         this.aX.c();
      }

      this.aX.a("blit");
      this.an.e();
      RenderSystem.popMatrix();
      RenderSystem.pushMatrix();
      this.an.a(this.O.k(), this.O.l());
      RenderSystem.popMatrix();
      this.aX.b("updateDisplay");
      this.O.e();
      int _snowmanxx = this.aK();
      if ((double)_snowmanxx < dkc.l.d()) {
         RenderSystem.limitDisplayFPS(_snowmanxx);
      }

      this.aX.b("yield");
      Thread.yield();
      this.aX.c();
      this.O.a("Post render");
      this.aN++;
      boolean _snowmanxxx = this.G() && (this.y != null && this.y.ay_() || this.z != null && this.z.a()) && !this.aE.n();
      if (this.aJ != _snowmanxxx) {
         if (this.aJ) {
            this.aK = this.P.a;
         } else {
            this.P.a = this.aK;
         }

         this.aJ = _snowmanxxx;
      }

      long _snowmanxxxx = x.c();
      this.o.a(_snowmanxxxx - this.aL);
      this.aL = _snowmanxxxx;
      this.aX.a("fpsUpdate");

      while (x.b() >= this.aM + 1000L) {
         aS = this.aN;
         this.A = String.format(
            "%d fps T: %s%s%s%s B: %d",
            aS,
            (double)this.k.d == dkc.l.d() ? "inf" : this.k.d,
            this.k.O ? " vsync" : "",
            this.k.f.toString(),
            this.k.e == djn.a ? "" : (this.k.e == djn.b ? " fast-clouds" : " fancy-clouds"),
            this.k.F
         );
         this.aM += 1000L;
         this.aN = 0;
         this.Q.b();
         if (!this.Q.d()) {
            this.Q.a();
         }
      }

      this.aX.c();
   }

   private boolean aJ() {
      return this.k.aJ && this.k.aK && !this.k.aI;
   }

   private void a(boolean var1, @Nullable anz var2) {
      if (_snowman) {
         if (!this.aZ.a()) {
            this.aY = 0;
            this.aZ.c();
         }

         this.aY++;
      } else {
         this.aZ.b();
      }

      this.aX = anz.a(this.aZ.d(), _snowman);
   }

   private void b(boolean var1, @Nullable anz var2) {
      if (_snowman != null) {
         _snowman.b();
      }

      if (_snowman) {
         this.ba = this.aZ.e();
      } else {
         this.ba = null;
      }

      this.aX = this.aZ.d();
   }

   @Override
   public void a() {
      int _snowman = this.O.a(this.k.aS, this.i());
      this.O.a((double)_snowman);
      if (this.y != null) {
         this.y.a(this, this.O.o(), this.O.p());
      }

      deg _snowmanx = this.f();
      _snowmanx.a(this.O.k(), this.O.l(), a);
      this.h.a(this.O.k(), this.O.l());
      this.l.g();
   }

   @Override
   public void b() {
      this.l.k();
   }

   private int aK() {
      return this.r != null || this.y == null && this.z == null ? this.O.d() : 60;
   }

   public void m() {
      try {
         p = new byte[0];
         this.e.m();
      } catch (Throwable var3) {
      }

      try {
         System.gc();
         if (this.aH && this.aE != null) {
            this.aE.a(true);
         }

         this.b(new dod(new of("menu.savingLevel")));
      } catch (Throwable var2) {
      }

      System.gc();
   }

   void a(int var1) {
      if (this.ba != null) {
         List<any> _snowman = this.ba.a(this.bb);
         if (!_snowman.isEmpty()) {
            any _snowmanx = _snowman.remove(0);
            if (_snowman == 0) {
               if (!_snowmanx.d.isEmpty()) {
                  int _snowmanxx = this.bb.lastIndexOf(30);
                  if (_snowmanxx >= 0) {
                     this.bb = this.bb.substring(0, _snowmanxx);
                  }
               }
            } else {
               _snowman--;
               if (_snowman < _snowman.size() && !"unspecified".equals(_snowman.get(_snowman).d)) {
                  if (!this.bb.isEmpty()) {
                     this.bb = this.bb + '\u001e';
                  }

                  this.bb = this.bb + _snowman.get(_snowman).d;
               }
            }
         }
      }
   }

   private void a(dfm var1, anv var2) {
      List<any> _snowman = _snowman.a(this.bb);
      any _snowmanx = _snowman.remove(0);
      RenderSystem.clear(256, a);
      RenderSystem.matrixMode(5889);
      RenderSystem.loadIdentity();
      RenderSystem.ortho(0.0, (double)this.O.k(), (double)this.O.l(), 0.0, 1000.0, 3000.0);
      RenderSystem.matrixMode(5888);
      RenderSystem.loadIdentity();
      RenderSystem.translatef(0.0F, 0.0F, -2000.0F);
      RenderSystem.lineWidth(1.0F);
      RenderSystem.disableTexture();
      dfo _snowmanxx = dfo.a();
      dfh _snowmanxxx = _snowmanxx.c();
      int _snowmanxxxx = 160;
      int _snowmanxxxxx = this.O.k() - 160 - 10;
      int _snowmanxxxxxx = this.O.l() - 320;
      RenderSystem.enableBlend();
      _snowmanxxx.a(7, dfk.l);
      _snowmanxxx.a((double)((float)_snowmanxxxxx - 176.0F), (double)((float)_snowmanxxxxxx - 96.0F - 16.0F), 0.0).a(200, 0, 0, 0).d();
      _snowmanxxx.a((double)((float)_snowmanxxxxx - 176.0F), (double)(_snowmanxxxxxx + 320), 0.0).a(200, 0, 0, 0).d();
      _snowmanxxx.a((double)((float)_snowmanxxxxx + 176.0F), (double)(_snowmanxxxxxx + 320), 0.0).a(200, 0, 0, 0).d();
      _snowmanxxx.a((double)((float)_snowmanxxxxx + 176.0F), (double)((float)_snowmanxxxxxx - 96.0F - 16.0F), 0.0).a(200, 0, 0, 0).d();
      _snowmanxx.b();
      RenderSystem.disableBlend();
      double _snowmanxxxxxxx = 0.0;

      for (any _snowmanxxxxxxxx : _snowman) {
         int _snowmanxxxxxxxxx = afm.c(_snowmanxxxxxxxx.a / 4.0) + 1;
         _snowmanxxx.a(6, dfk.l);
         int _snowmanxxxxxxxxxx = _snowmanxxxxxxxx.a();
         int _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxx >> 16 & 0xFF;
         int _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxx >> 8 & 0xFF;
         int _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxx & 0xFF;
         _snowmanxxx.a((double)_snowmanxxxxx, (double)_snowmanxxxxxx, 0.0).a(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, 255).d();

         for (int _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxx; _snowmanxxxxxxxxxxxxxx >= 0; _snowmanxxxxxxxxxxxxxx--) {
            float _snowmanxxxxxxxxxxxxxxx = (float)((_snowmanxxxxxxx + _snowmanxxxxxxxx.a * (double)_snowmanxxxxxxxxxxxxxx / (double)_snowmanxxxxxxxxx) * (float) (Math.PI * 2) / 100.0);
            float _snowmanxxxxxxxxxxxxxxxx = afm.a(_snowmanxxxxxxxxxxxxxxx) * 160.0F;
            float _snowmanxxxxxxxxxxxxxxxxx = afm.b(_snowmanxxxxxxxxxxxxxxx) * 160.0F * 0.5F;
            _snowmanxxx.a((double)((float)_snowmanxxxxx + _snowmanxxxxxxxxxxxxxxxx), (double)((float)_snowmanxxxxxx - _snowmanxxxxxxxxxxxxxxxxx), 0.0)
               .a(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, 255)
               .d();
         }

         _snowmanxx.b();
         _snowmanxxx.a(5, dfk.l);

         for (int _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxx; _snowmanxxxxxxxxxxxxxx >= 0; _snowmanxxxxxxxxxxxxxx--) {
            float _snowmanxxxxxxxxxxxxxxx = (float)((_snowmanxxxxxxx + _snowmanxxxxxxxx.a * (double)_snowmanxxxxxxxxxxxxxx / (double)_snowmanxxxxxxxxx) * (float) (Math.PI * 2) / 100.0);
            float _snowmanxxxxxxxxxxxxxxxx = afm.a(_snowmanxxxxxxxxxxxxxxx) * 160.0F;
            float _snowmanxxxxxxxxxxxxxxxxx = afm.b(_snowmanxxxxxxxxxxxxxxx) * 160.0F * 0.5F;
            if (!(_snowmanxxxxxxxxxxxxxxxxx > 0.0F)) {
               _snowmanxxx.a((double)((float)_snowmanxxxxx + _snowmanxxxxxxxxxxxxxxxx), (double)((float)_snowmanxxxxxx - _snowmanxxxxxxxxxxxxxxxxx), 0.0)
                  .a(_snowmanxxxxxxxxxxx >> 1, _snowmanxxxxxxxxxxxx >> 1, _snowmanxxxxxxxxxxxxx >> 1, 255)
                  .d();
               _snowmanxxx.a((double)((float)_snowmanxxxxx + _snowmanxxxxxxxxxxxxxxxx), (double)((float)_snowmanxxxxxx - _snowmanxxxxxxxxxxxxxxxxx + 10.0F), 0.0)
                  .a(_snowmanxxxxxxxxxxx >> 1, _snowmanxxxxxxxxxxxx >> 1, _snowmanxxxxxxxxxxxxx >> 1, 255)
                  .d();
            }
         }

         _snowmanxx.b();
         _snowmanxxxxxxx += _snowmanxxxxxxxx.a;
      }

      DecimalFormat _snowmanxxxxxxxx = new DecimalFormat("##0.00");
      _snowmanxxxxxxxx.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
      RenderSystem.enableTexture();
      String _snowmanxxxxxxxxx = anv.b(_snowmanx.d);
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
      this.g.a(_snowman, _snowmanxxxxxxxxxx, (float)(_snowmanxxxxx - 160), (float)(_snowmanxxxxxx - 80 - 16), 16777215);
      _snowmanxxxxxxxxxx = _snowmanxxxxxxxx.format(_snowmanx.b) + "%";
      this.g.a(_snowman, _snowmanxxxxxxxxxx, (float)(_snowmanxxxxx + 160 - this.g.b(_snowmanxxxxxxxxxx)), (float)(_snowmanxxxxxx - 80 - 16), 16777215);

      for (int _snowmanxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxx < _snowman.size(); _snowmanxxxxxxxxxxxx++) {
         any _snowmanxxxxxxxxxxxxx = _snowman.get(_snowmanxxxxxxxxxxxx);
         StringBuilder _snowmanxxxxxxxxxxxxxxx = new StringBuilder();
         if ("unspecified".equals(_snowmanxxxxxxxxxxxxx.d)) {
            _snowmanxxxxxxxxxxxxxxx.append("[?] ");
         } else {
            _snowmanxxxxxxxxxxxxxxx.append("[").append(_snowmanxxxxxxxxxxxx + 1).append("] ");
         }

         String _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx.append(_snowmanxxxxxxxxxxxxx.d).toString();
         this.g.a(_snowman, _snowmanxxxxxxxxxxxxxxxx, (float)(_snowmanxxxxx - 160), (float)(_snowmanxxxxxx + 80 + _snowmanxxxxxxxxxxxx * 8 + 20), _snowmanxxxxxxxxxxxxx.a());
         _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxx.format(_snowmanxxxxxxxxxxxxx.a) + "%";
         this.g
            .a(
               _snowman,
               _snowmanxxxxxxxxxxxxxxxx,
               (float)(_snowmanxxxxx + 160 - 50 - this.g.b(_snowmanxxxxxxxxxxxxxxxx)),
               (float)(_snowmanxxxxxx + 80 + _snowmanxxxxxxxxxxxx * 8 + 20),
               _snowmanxxxxxxxxxxxxx.a()
            );
         _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxx.format(_snowmanxxxxxxxxxxxxx.b) + "%";
         this.g
            .a(_snowman, _snowmanxxxxxxxxxxxxxxxx, (float)(_snowmanxxxxx + 160 - this.g.b(_snowmanxxxxxxxxxxxxxxxx)), (float)(_snowmanxxxxxx + 80 + _snowmanxxxxxxxxxxxx * 8 + 20), _snowmanxxxxxxxxxxxxx.a());
      }
   }

   public void n() {
      this.aQ = false;
   }

   public boolean o() {
      return this.aQ;
   }

   public void c(boolean var1) {
      if (this.y == null) {
         boolean _snowman = this.G() && !this.aE.n();
         if (_snowman) {
            this.a(new doo(!_snowman));
            this.ao.b();
         } else {
            this.a(new doo(true));
         }
      }
   }

   private void f(boolean var1) {
      if (!_snowman) {
         this.w = 0;
      }

      if (this.w <= 0 && !this.s.dW()) {
         if (_snowman && this.v != null && this.v.c() == dcl.a.b) {
            dcj _snowman = (dcj)this.v;
            fx _snowmanx = _snowman.a();
            if (!this.r.d_(_snowmanx).g()) {
               gc _snowmanxx = _snowman.b();
               if (this.q.b(_snowmanx, _snowmanxx)) {
                  this.f.a(_snowmanx, _snowmanxx);
                  this.s.a(aot.a);
               }
            }
         } else {
            this.q.b();
         }
      }
   }

   private void aL() {
      if (this.w <= 0) {
         if (this.v == null) {
            G.error("Null returned as 'hitResult', this shouldn't happen!");
            if (this.q.f()) {
               this.w = 10;
            }
         } else if (!this.s.L()) {
            switch (this.v.c()) {
               case c:
                  this.q.a(this.s, ((dck)this.v).a());
                  break;
               case b:
                  dcj _snowman = (dcj)this.v;
                  fx _snowmanx = _snowman.a();
                  if (!this.r.d_(_snowmanx).g()) {
                     this.q.a(_snowmanx, _snowman.b());
                     break;
                  }
               case a:
                  if (this.q.f()) {
                     this.w = 10;
                  }

                  this.s.eS();
            }

            this.s.a(aot.a);
         }
      }
   }

   private void aM() {
      if (!this.q.m()) {
         this.aI = 4;
         if (!this.s.L()) {
            if (this.v == null) {
               G.warn("Null returned as 'hitResult', this shouldn't happen!");
            }

            for (aot _snowman : aot.values()) {
               bmb _snowmanx = this.s.b(_snowman);
               if (this.v != null) {
                  switch (this.v.c()) {
                     case c:
                        dck _snowmanxx = (dck)this.v;
                        aqa _snowmanxxx = _snowmanxx.a();
                        aou _snowmanxxxx = this.q.a(this.s, _snowmanxxx, _snowmanxx, _snowman);
                        if (!_snowmanxxxx.a()) {
                           _snowmanxxxx = this.q.a(this.s, _snowmanxxx, _snowman);
                        }

                        if (_snowmanxxxx.a()) {
                           if (_snowmanxxxx.b()) {
                              this.s.a(_snowman);
                           }

                           return;
                        }
                        break;
                     case b:
                        dcj _snowmanxxxxx = (dcj)this.v;
                        int _snowmanxxxxxx = _snowmanx.E();
                        aou _snowmanxxxxxxx = this.q.a(this.s, this.r, _snowman, _snowmanxxxxx);
                        if (_snowmanxxxxxxx.a()) {
                           if (_snowmanxxxxxxx.b()) {
                              this.s.a(_snowman);
                              if (!_snowmanx.a() && (_snowmanx.E() != _snowmanxxxxxx || this.q.g())) {
                                 this.h.a.a(_snowman);
                              }
                           }

                           return;
                        }

                        if (_snowmanxxxxxxx == aou.d) {
                           return;
                        }
                  }
               }

               if (!_snowmanx.a()) {
                  aou _snowmanxxxxxxxx = this.q.a(this.s, this.r, _snowman);
                  if (_snowmanxxxxxxxx.a()) {
                     if (_snowmanxxxxxxxx.b()) {
                        this.s.a(_snowman);
                     }

                     this.h.a.a(_snowman);
                     return;
                  }
               }
            }
         }
      }
   }

   public enp p() {
      return this.ap;
   }

   public void q() {
      if (this.aI > 0) {
         this.aI--;
      }

      this.aX.a("gui");
      if (!this.aJ) {
         this.j.b();
      }

      this.aX.c();
      this.h.a(1.0F);
      this.aC.a(this.r, this.v);
      this.aX.a("gameMode");
      if (!this.aJ && this.r != null) {
         this.q.d();
      }

      this.aX.b("textures");
      if (this.r != null) {
         this.L.e();
      }

      if (this.y == null && this.s != null) {
         if (this.s.dl() && !(this.y instanceof dnx)) {
            this.a(null);
         } else if (this.s.em() && this.r != null) {
            this.a(new doe());
         }
      } else if (this.y != null && this.y instanceof doe && !this.s.em()) {
         this.a(null);
      }

      if (this.y != null) {
         this.w = 10000;
      }

      if (this.y != null) {
         dot.a(() -> this.y.d(), "Ticking screen", this.y.getClass().getCanonicalName());
      }

      if (!this.k.aJ) {
         this.j.j();
      }

      if (this.z == null && (this.y == null || this.y.n)) {
         this.aX.b("Keybindings");
         this.aO();
         if (this.w > 0) {
            this.w--;
         }
      }

      if (this.r != null) {
         this.aX.b("gameRenderer");
         if (!this.aJ) {
            this.h.e();
         }

         this.aX.b("levelRenderer");
         if (!this.aJ) {
            this.e.l();
         }

         this.aX.b("level");
         if (!this.aJ) {
            if (this.r.s() > 0) {
               this.r.c(this.r.s() - 1);
            }

            this.r.g();
         }
      } else if (this.h.f() != null) {
         this.h.a();
      }

      if (!this.aJ) {
         this.ap.a();
      }

      this.ao.a(this.aJ);
      if (this.r != null) {
         if (!this.aJ) {
            if (!this.k.E && this.aN()) {
               nr _snowman = new of("tutorial.socialInteractions.title");
               nr _snowmanx = new of("tutorial.socialInteractions.description", eoe.a("socialInteractions"));
               this.aW = new dms(dms.a.f, _snowman, _snowmanx, true);
               this.aC.a(this.aW, 160);
               this.k.E = true;
               this.k.b();
            }

            this.aC.d();

            try {
               this.r.a(() -> true);
            } catch (Throwable var4) {
               l _snowman = l.a(var4, "Exception in world tick");
               if (this.r == null) {
                  m _snowmanx = _snowman.a("Affected level");
                  _snowmanx.a("Problem", "Level is null!");
               } else {
                  this.r.a(_snowman);
               }

               throw new u(_snowman);
            }
         }

         this.aX.b("animateTick");
         if (!this.aJ && this.r != null) {
            this.r.c(afm.c(this.s.cD()), afm.c(this.s.cE()), afm.c(this.s.cH()));
         }

         this.aX.b("particles");
         if (!this.aJ) {
            this.f.b();
         }
      } else if (this.aG != null) {
         this.aX.b("pendingConnection");
         this.aG.a();
      }

      this.aX.b("keyboard");
      this.m.b();
      this.aX.c();
   }

   private boolean aN() {
      return !this.aH || this.aE != null && this.aE.n();
   }

   private void aO() {
      while (this.k.ax.f()) {
         djl _snowman = this.k.g();
         this.k.a(this.k.g().c());
         if (_snowman.a() != this.k.g().a()) {
            this.h.a(this.k.g().a() ? this.aa() : null);
         }

         this.e.o();
      }

      while (this.k.ay.f()) {
         this.k.aN = !this.k.aN;
      }

      for (int _snowman = 0; _snowman < 9; _snowman++) {
         boolean _snowmanx = this.k.aD.d();
         boolean _snowmanxx = this.k.aE.d();
         if (this.k.aC[_snowman].f()) {
            if (this.s.a_()) {
               this.j.f().a(_snowman);
            } else if (!this.s.b_() || this.y != null || !_snowmanxx && !_snowmanx) {
               this.s.bm.d = _snowman;
            } else {
               dqc.a(this, _snowman, _snowmanxx, _snowmanx);
            }
         }
      }

      while (this.k.av.f()) {
         if (!this.aN()) {
            this.s.a(I, true);
            dkz.b.a(I.getString());
         } else {
            if (this.aW != null) {
               this.aC.a(this.aW);
               this.aW = null;
            }

            this.a(new dsc());
         }
      }

      while (this.k.am.f()) {
         if (this.q.i()) {
            this.s.A();
         } else {
            this.aC.a();
            this.a(new dql(this.s));
         }
      }

      while (this.k.aB.f()) {
         this.a(new dpi(this.s.e.h()));
      }

      while (this.k.an.f()) {
         if (!this.s.a_()) {
            this.w().a(new sz(sz.a.g, fx.b, gc.a));
         }
      }

      while (this.k.ao.f()) {
         if (!this.s.a_() && this.s.a(dot.x())) {
            this.s.a(aot.a);
         }
      }

      boolean _snowmanx = this.k.j != bfu.c;
      if (_snowmanx) {
         while (this.k.as.f()) {
            this.b("");
         }

         if (this.y == null && this.z == null && this.k.au.f()) {
            this.b("/");
         }
      }

      if (this.s.dW()) {
         if (!this.k.ap.d()) {
            this.q.b(this.s);
         }

         while (this.k.aq.f()) {
         }

         while (this.k.ap.f()) {
         }

         while (this.k.ar.f()) {
         }
      } else {
         while (this.k.aq.f()) {
            this.aL();
         }

         while (this.k.ap.f()) {
            this.aM();
         }

         while (this.k.ar.f()) {
            this.aP();
         }
      }

      if (this.k.ap.d() && this.aI == 0 && !this.s.dW()) {
         this.aM();
      }

      this.f(this.y == null && this.k.aq.d() && this.l.h());
   }

   public static brk a(cyg.a var0) {
      MinecraftServer.a(_snowman);
      brk _snowman = _snowman.e();
      if (_snowman == null) {
         throw new IllegalStateException("Failed to load data pack config");
      } else {
         return _snowman;
      }
   }

   public static cyn a(cyg.a var0, gn.b var1, ach var2, brk var3) {
      vh<mt> _snowman = vh.a(mo.a, _snowman, _snowman);
      cyn _snowmanx = _snowman.a(_snowman, _snowman);
      if (_snowmanx == null) {
         throw new IllegalStateException("Failed to load world");
      } else {
         return _snowmanx;
      }
   }

   public void a(String var1) {
      this.a(_snowman, gn.b(), djz::a, djz::a, false, djz.a.c);
   }

   public void a(String var1, bsa var2, gn.b var3, chw var4) {
      this.a(_snowman, _snowman, var1x -> _snowman.g(), (var3x, var4x, var5, var6) -> {
         vi<JsonElement> _snowman = vi.a(JsonOps.INSTANCE, _snowman);
         vh<JsonElement> _snowmanx = vh.a(JsonOps.INSTANCE, var5, _snowman);
         DataResult<chw> _snowmanxx = chw.a.encodeStart(_snowman, _snowman).setLifecycle(Lifecycle.stable()).flatMap(var1x -> chw.a.parse(_snowman, var1x));
         chw _snowmanxxx = _snowmanxx.resultOrPartial(x.a("Error reading worldgen settings after loading data packs: ", G::error)).orElse(_snowman);
         return new cyl(_snowman, _snowmanxxx, _snowmanxx.lifecycle());
      }, false, djz.a.b);
   }

   private void a(String var1, gn.b var2, Function<cyg.a, brk> var3, Function4<cyg.a, gn.b, ach, brk, cyn> var4, boolean var5, djz.a var6) {
      cyg.a _snowman;
      try {
         _snowman = this.ac.c(_snowman);
      } catch (IOException var21) {
         G.warn("Failed to read level {} data", _snowman, var21);
         dmp.a(this, _snowman);
         this.a(null);
         return;
      }

      djz.b _snowmanx;
      try {
         _snowmanx = this.a(_snowman, _snowman, _snowman, _snowman, _snowman);
      } catch (Exception var20) {
         G.warn("Failed to load datapacks, can't proceed with server load", var20);
         this.a(new dnw(() -> this.a(_snowman, _snowman, _snowman, _snowman, true, _snowman)));

         try {
            _snowman.close();
         } catch (IOException var16) {
            G.warn("Failed to unlock access to level {}", _snowman, var16);
         }

         return;
      }

      cyn _snowmanxx = _snowmanx.c();
      boolean _snowmanxxx = _snowmanxx.A().i();
      boolean _snowmanxxxx = _snowmanxx.B() != Lifecycle.stable();
      if (_snowman == djz.a.a || !_snowmanxxx && !_snowmanxxxx) {
         this.r();
         this.X.set(null);

         try {
            _snowman.a(_snowman, _snowmanxx);
            _snowmanx.b().i();
            YggdrasilAuthenticationService _snowmanxxxxx = new YggdrasilAuthenticationService(this.ab);
            MinecraftSessionService _snowmanxxxxxx = _snowmanxxxxx.createMinecraftSessionService();
            GameProfileRepository _snowmanxxxxxxx = _snowmanxxxxx.createProfileRepository();
            acq _snowmanxxxxxxxx = new acq(_snowmanxxxxxxx, new File(this.n, MinecraftServer.b.getName()));
            cdg.a(_snowmanxxxxxxxx);
            cdg.a(_snowmanxxxxxx);
            acq.a(false);
            this.aE = MinecraftServer.a((Function<Thread, eng>)(var8x -> new eng(var8x, this, _snowman, _snowman, _snowman.a(), _snowman.b(), _snowman, _snowman, _snowman, _snowman, var1x -> {
                  aat _snowmanxxxxxxxxx = new aat(var1x + 0);
                  _snowmanxxxxxxxxx.a();
                  this.X.set(_snowmanxxxxxxxxx);
                  return new aas(_snowmanxxxxxxxxx, this.aU::add);
               })));
            this.aH = true;
         } catch (Throwable var19) {
            l _snowmanxxxxxxxxx = l.a(var19, "Starting integrated server");
            m _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx.a("Starting integrated server");
            _snowmanxxxxxxxxxx.a("Level ID", _snowman);
            _snowmanxxxxxxxxxx.a("Level Name", _snowmanxx.g());
            throw new u(_snowmanxxxxxxxxx);
         }

         while (this.X.get() == null) {
            Thread.yield();
         }

         dog _snowmanxxxxx = new dog(this.X.get());
         this.a(_snowmanxxxxx);
         this.aX.a("waitForServer");

         while (!this.aE.ag()) {
            _snowmanxxxxx.d();
            this.e(false);

            try {
               Thread.sleep(16L);
            } catch (InterruptedException var18) {
            }

            if (this.aR != null) {
               b(this.aR);
               return;
            }
         }

         this.aX.c();
         SocketAddress _snowmanxxxxxx = this.aE.af().a();
         nd _snowmanxxxxxxx = nd.a(_snowmanxxxxxx);
         _snowmanxxxxxxx.a(new dws(_snowmanxxxxxxx, this, null, var0 -> {
         }));
         _snowmanxxxxxxx.a(new tv(_snowmanxxxxxx.toString(), 0, ne.d));
         _snowmanxxxxxxx.a(new ug(this.J().e()));
         this.aG = _snowmanxxxxxxx;
      } else {
         this.a(_snowman, _snowman, _snowmanxxx, () -> this.a(_snowman, _snowman, _snowman, _snowman, _snowman, djz.a.a));
         _snowmanx.close();

         try {
            _snowman.close();
         } catch (IOException var17) {
            G.warn("Failed to unlock access to level {}", _snowman, var17);
         }
      }
   }

   private void a(djz.a var1, String var2, boolean var3, Runnable var4) {
      if (_snowman == djz.a.c) {
         nr _snowman;
         nr _snowmanx;
         if (_snowman) {
            _snowman = new of("selectWorld.backupQuestion.customized");
            _snowmanx = new of("selectWorld.backupWarning.customized");
         } else {
            _snowman = new of("selectWorld.backupQuestion.experimental");
            _snowmanx = new of("selectWorld.backupWarning.experimental");
         }

         this.a(new dno(null, (var3x, var4x) -> {
            if (var3x) {
               dsh.a(this.ac, _snowman);
            }

            _snowman.run();
         }, _snowman, _snowmanx, false));
      } else {
         this.a(new dns(var3x -> {
            if (var3x) {
               _snowman.run();
            } else {
               this.a(null);

               try (cyg.a _snowman = this.ac.c(_snowman)) {
                  _snowman.g();
               } catch (IOException var17) {
                  dmp.b(this, _snowman);
                  G.error("Failed to delete world {}", _snowman, var17);
               }
            }
         }, new of("selectWorld.backupQuestion.experimental"), new of("selectWorld.backupWarning.experimental"), nq.g, nq.d));
      }
   }

   public djz.b a(gn.b var1, Function<cyg.a, brk> var2, Function4<cyg.a, gn.b, ach, brk, cyn> var3, boolean var4, cyg.a var5) throws InterruptedException, ExecutionException {
      brk _snowman = _snowman.apply(_snowman);
      abw _snowmanx = new abw(new abz(), new abt(_snowman.a(cye.g).toFile(), abx.c));

      try {
         brk _snowmanxx = MinecraftServer.a(_snowmanx, _snowman, _snowman);
         CompletableFuture<vz> _snowmanxxx = vz.a(_snowmanx.f(), dc.a.c, 2, x.f(), this);
         this.c(_snowmanxxx::isDone);
         vz _snowmanxxxx = _snowmanxxx.get();
         cyn _snowmanxxxxx = (cyn)_snowman.apply(_snowman, _snowman, _snowmanxxxx.h(), _snowmanxx);
         return new djz.b(_snowmanx, _snowmanxxxx, _snowmanxxxxx);
      } catch (ExecutionException | InterruptedException var12) {
         _snowmanx.close();
         throw var12;
      }
   }

   public void a(dwt var1) {
      dor _snowman = new dor();
      _snowman.a(new of("connect.joining"));
      this.d(_snowman);
      this.r = _snowman;
      this.b(_snowman);
      if (!this.aH) {
         AuthenticationService _snowmanx = new YggdrasilAuthenticationService(this.ab);
         MinecraftSessionService _snowmanxx = _snowmanx.createMinecraftSessionService();
         GameProfileRepository _snowmanxxx = _snowmanx.createProfileRepository();
         acq _snowmanxxxx = new acq(_snowmanxxx, new File(this.n, MinecraftServer.b.getName()));
         cdg.a(_snowmanxxxx);
         cdg.a(_snowmanxx);
         acq.a(false);
      }
   }

   public void r() {
      this.b(new dor());
   }

   public void b(dot var1) {
      dwu _snowman = this.w();
      if (_snowman != null) {
         this.bk();
         _snowman.c();
      }

      eng _snowmanx = this.aE;
      this.aE = null;
      this.h.g();
      this.q = null;
      dkz.b.b();
      this.d(_snowman);
      if (this.r != null) {
         if (_snowmanx != null) {
            this.aX.a("waitForServer");

            while (!_snowmanx.D()) {
               this.e(false);
            }

            this.aX.c();
         }

         this.ai.b();
         this.j.h();
         this.aF = null;
         this.aH = false;
         this.aB.b();
      }

      this.r = null;
      this.b(null);
      this.s = null;
   }

   private void d(dot var1) {
      this.aX.a("forcedTick");
      this.ao.d();
      this.t = null;
      this.aG = null;
      this.a(_snowman);
      this.e(false);
      this.aX.c();
   }

   public void c(dot var1) {
      this.aX.a("forcedTick");
      this.a(_snowman);
      this.e(false);
      this.aX.c();
   }

   private void b(@Nullable dwt var1) {
      this.e.a(_snowman);
      this.f.a(_snowman);
      ecd.a.a(_snowman);
      this.c();
   }

   public boolean s() {
      return this.af && this.au.serversAllowed();
   }

   public boolean a(UUID var1) {
      return this.u() ? this.aD.c(_snowman) : (this.s == null || !_snowman.equals(this.s.bS())) && !_snowman.equals(x.b);
   }

   public boolean u() {
      return this.ag && this.au.chatAllowed();
   }

   public final boolean v() {
      return this.ae;
   }

   @Nullable
   public dwu w() {
      return this.s == null ? null : this.s.e;
   }

   public static boolean x() {
      return !F.k.aI;
   }

   public static boolean z() {
      return F.k.f.a() >= djt.b.a();
   }

   public static boolean A() {
      return F.k.f.a() >= djt.c.a();
   }

   public static boolean B() {
      return F.k.g != djh.a;
   }

   private void aP() {
      if (this.v != null && this.v.c() != dcl.a.a) {
         boolean _snowman = this.s.bC.d;
         ccj _snowmanx = null;
         dcl.a _snowmanxx = this.v.c();
         bmb _snowmanxxx;
         if (_snowmanxx == dcl.a.b) {
            fx _snowmanxxxx = ((dcj)this.v).a();
            ceh _snowmanxxxxx = this.r.d_(_snowmanxxxx);
            buo _snowmanxxxxxx = _snowmanxxxxx.b();
            if (_snowmanxxxxx.g()) {
               return;
            }

            _snowmanxxx = _snowmanxxxxxx.a((brc)this.r, _snowmanxxxx, _snowmanxxxxx);
            if (_snowmanxxx.a()) {
               return;
            }

            if (_snowman && dot.x() && _snowmanxxxxxx.q()) {
               _snowmanx = this.r.c(_snowmanxxxx);
            }
         } else {
            if (_snowmanxx != dcl.a.c || !_snowman) {
               return;
            }

            aqa _snowmanxxxxxxx = ((dck)this.v).a();
            if (_snowmanxxxxxxx instanceof bcs) {
               _snowmanxxx = new bmb(bmd.lz);
            } else if (_snowmanxxxxxxx instanceof bcq) {
               _snowmanxxx = new bmb(bmd.pH);
            } else if (_snowmanxxxxxxx instanceof bcp) {
               bcp _snowmanxxxxxxxx = (bcp)_snowmanxxxxxxx;
               bmb _snowmanxxxxxxxxx = _snowmanxxxxxxxx.o();
               if (_snowmanxxxxxxxxx.a()) {
                  _snowmanxxx = new bmb(bmd.oW);
               } else {
                  _snowmanxxx = _snowmanxxxxxxxxx.i();
               }
            } else if (_snowmanxxxxxxx instanceof bhl) {
               bhl _snowmanxxxxxxxx = (bhl)_snowmanxxxxxxx;
               blx _snowmanxxxxxxxxx;
               switch (_snowmanxxxxxxxx.o()) {
                  case c:
                     _snowmanxxxxxxxxx = bmd.mf;
                     break;
                  case b:
                     _snowmanxxxxxxxxx = bmd.me;
                     break;
                  case d:
                     _snowmanxxxxxxxxx = bmd.pt;
                     break;
                  case f:
                     _snowmanxxxxxxxxx = bmd.pu;
                     break;
                  case g:
                     _snowmanxxxxxxxxx = bmd.pJ;
                     break;
                  default:
                     _snowmanxxxxxxxxx = bmd.lN;
               }

               _snowmanxxx = new bmb(_snowmanxxxxxxxxx);
            } else if (_snowmanxxxxxxx instanceof bhn) {
               _snowmanxxx = new bmb(((bhn)_snowmanxxxxxxx).g());
            } else if (_snowmanxxxxxxx instanceof bcn) {
               _snowmanxxx = new bmb(bmd.pC);
            } else if (_snowmanxxxxxxx instanceof bbq) {
               _snowmanxxx = new bmb(bmd.qc);
            } else {
               bna _snowmanxxxxxxxx = bna.a(_snowmanxxxxxxx.X());
               if (_snowmanxxxxxxxx == null) {
                  return;
               }

               _snowmanxxx = new bmb(_snowmanxxxxxxxx);
            }
         }

         if (_snowmanxxx.a()) {
            String _snowmanxxxxxxx = "";
            if (_snowmanxx == dcl.a.b) {
               _snowmanxxxxxxx = gm.Q.b(this.r.d_(((dcj)this.v).a()).b()).toString();
            } else if (_snowmanxx == dcl.a.c) {
               _snowmanxxxxxxx = gm.S.b(((dck)this.v).a().X()).toString();
            }

            G.warn("Picking on: [{}] {} gave null item", _snowmanxx, _snowmanxxxxxxx);
         } else {
            bfv _snowmanxxxxxxx = this.s.bm;
            if (_snowmanx != null) {
               this.a(_snowmanxxx, _snowmanx);
            }

            int _snowmanxxxxxxxx = _snowmanxxxxxxx.b(_snowmanxxx);
            if (_snowman) {
               _snowmanxxxxxxx.a(_snowmanxxx);
               this.q.a(this.s.b(aot.a), 36 + _snowmanxxxxxxx.d);
            } else if (_snowmanxxxxxxxx != -1) {
               if (bfv.d(_snowmanxxxxxxxx)) {
                  _snowmanxxxxxxx.d = _snowmanxxxxxxxx;
               } else {
                  this.q.a(_snowmanxxxxxxxx);
               }
            }
         }
      }
   }

   private bmb a(bmb var1, ccj var2) {
      md _snowman = _snowman.a(new md());
      if (_snowman.b() instanceof bmm && _snowman.e("SkullOwner")) {
         md _snowmanx = _snowman.p("SkullOwner");
         _snowman.p().a("SkullOwner", _snowmanx);
         return _snowman;
      } else {
         _snowman.a("BlockEntityTag", _snowman);
         md _snowmanx = new md();
         mj _snowmanxx = new mj();
         _snowmanxx.add(ms.a("\"(+NBT)\""));
         _snowmanx.a("Lore", _snowmanxx);
         _snowman.a("display", _snowmanx);
         return _snowman;
      }
   }

   public l c(l var1) {
      a(this.ak, this.Z, this.k, _snowman);
      if (this.r != null) {
         this.r.a(_snowman);
      }

      return _snowman;
   }

   public static void a(@Nullable ekz var0, String var1, @Nullable dkd var2, l var3) {
      m _snowman = _snowman.g();
      _snowman.a("Launched Version", () -> _snowman);
      _snowman.a("Backend library", RenderSystem::getBackendDescription);
      _snowman.a("Backend API", RenderSystem::getApiDescription);
      _snowman.a("GL Caps", RenderSystem::getCapsString);
      _snowman.a("Using VBOs", () -> "Yes");
      _snowman.a(
         "Is Modded",
         () -> {
            String _snowmanx = ClientBrandRetriever.getClientModName();
            if (!"vanilla".equals(_snowmanx)) {
               return "Definitely; Client brand changed to '" + _snowmanx + "'";
            } else {
               return djz.class.getSigners() == null
                  ? "Very likely; Jar signature invalidated"
                  : "Probably not. Jar signature remains and client brand is untouched.";
            }
         }
      );
      _snowman.a("Type", "Client (map_client.txt)");
      if (_snowman != null) {
         if (F != null) {
            String _snowmanx = F.V().m();
            if (_snowmanx != null) {
               _snowman.a("GPU Warnings", _snowmanx);
            }
         }

         _snowman.a("Graphics mode", _snowman.f);
         _snowman.a("Resource Packs", () -> {
            StringBuilder _snowmanx = new StringBuilder();

            for (String _snowmanx : _snowman.h) {
               if (_snowmanx.length() > 0) {
                  _snowmanx.append(", ");
               }

               _snowmanx.append(_snowmanx);
               if (_snowman.i.contains(_snowmanx)) {
                  _snowmanx.append(" (incompatible)");
               }
            }

            return _snowmanx.toString();
         });
      }

      if (_snowman != null) {
         _snowman.a("Current Language", () -> _snowman.b().toString());
      }

      _snowman.a("CPU", den::b);
   }

   public static djz C() {
      return F;
   }

   public CompletableFuture<Void> D() {
      return this.a(this::j).thenCompose(var0 -> (CompletionStage<Void>)var0);
   }

   @Override
   public void a(apc var1) {
      _snowman.a("fps", aS);
      _snowman.a("vsync_enabled", this.k.O);
      _snowman.a("display_frequency", this.O.a());
      _snowman.a("display_type", this.O.j() ? "fullscreen" : "windowed");
      _snowman.a("run_time", (x.b() - _snowman.g()) / 60L * 1000L);
      _snowman.a("current_action", this.aQ());
      _snowman.a("language", this.k.aV == null ? "en_us" : this.k.aV);
      String _snowman = ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN ? "little" : "big";
      _snowman.a("endianness", _snowman);
      _snowman.a("subtitles", this.k.W);
      _snowman.a("touch", this.k.Y ? "touch" : "mouse");
      int _snowmanx = 0;

      for (abu _snowmanxx : this.aj.e()) {
         if (!_snowmanxx.f() && !_snowmanxx.g()) {
            _snowman.a("resource_pack[" + _snowmanx++ + "]", _snowmanxx.e());
         }
      }

      _snowman.a("resource_packs", _snowmanx);
      if (this.aE != null) {
         _snowman.a("snooper_partner", this.aE.aj().f());
      }
   }

   private String aQ() {
      if (this.aE != null) {
         return this.aE.n() ? "hosting_lan" : "singleplayer";
      } else if (this.aF != null) {
         return this.aF.d() ? "playing_lan" : "multiplayer";
      } else {
         return "out_of_game";
      }
   }

   public void a(@Nullable dwz var1) {
      this.aF = _snowman;
   }

   @Nullable
   public dwz E() {
      return this.aF;
   }

   public boolean F() {
      return this.aH;
   }

   public boolean G() {
      return this.aH && this.aE != null;
   }

   @Nullable
   public eng H() {
      return this.aE;
   }

   public apc I() {
      return this.Q;
   }

   public dkm J() {
      return this.W;
   }

   public PropertyMap K() {
      if (this.K.isEmpty()) {
         GameProfile _snowman = this.Y().fillProfileProperties(this.W.e(), false);
         this.K.putAll(_snowman.getProperties());
      }

      return this.K;
   }

   public Proxy L() {
      return this.ab;
   }

   public ekd M() {
      return this.L;
   }

   public ach N() {
      return this.ah;
   }

   public abw O() {
      return this.aj;
   }

   public ekh P() {
      return this.ai;
   }

   public File Q() {
      return this.J;
   }

   public ekz R() {
      return this.ak;
   }

   public Function<vk, ekc> a(vk var1) {
      return this.aw.a(_snowman)::a;
   }

   public boolean S() {
      return this.ad;
   }

   public boolean T() {
      return this.aJ;
   }

   public eaa V() {
      return this.as;
   }

   public enu W() {
      return this.ao;
   }

   public adn X() {
      if (this.y instanceof dpa) {
         return ado.c;
      } else if (this.s != null) {
         if (this.s.l.Y() == brx.i) {
            return this.j.i().c() ? ado.d : ado.e;
         } else {
            bsv.b _snowman = this.s.l.v(this.s.cB()).t();
            if (!this.ap.b(ado.f) && (!this.s.aI() || _snowman != bsv.b.l && _snowman != bsv.b.n)) {
               return this.s.l.Y() != brx.h && this.s.bC.d && this.s.bC.c ? ado.b : this.r.d().b(this.s.cB()).s().orElse(ado.g);
            } else {
               return ado.f;
            }
         }
      } else {
         return ado.a;
      }
   }

   public MinecraftSessionService Y() {
      return this.at;
   }

   public eks Z() {
      return this.av;
   }

   @Nullable
   public aqa aa() {
      return this.t;
   }

   public void a(aqa var1) {
      this.t = _snowman;
      this.h.a(_snowman);
   }

   public boolean b(aqa var1) {
      return _snowman.bE() || this.s != null && this.s.a_() && this.k.aA.d() && _snowman.X() == aqe.bc;
   }

   @Override
   protected Thread aw() {
      return this.aP;
   }

   @Override
   protected Runnable e(Runnable var1) {
      return _snowman;
   }

   @Override
   protected boolean d(Runnable var1) {
      return true;
   }

   public eax ab() {
      return this.ax;
   }

   public eet ac() {
      return this.S;
   }

   public efo ad() {
      return this.T;
   }

   public eac ae() {
      return this.U;
   }

   public <T> emy<T> a(enb.a<T> var1) {
      return this.V.a(_snowman);
   }

   public afc ag() {
      return this.o;
   }

   public boolean ah() {
      return this.aO;
   }

   public void d(boolean var1) {
      this.aO = _snowman;
   }

   public DataFixer ai() {
      return this.M;
   }

   public float aj() {
      return this.P.a;
   }

   public float ak() {
      return this.P.b;
   }

   public dko al() {
      return this.al;
   }

   public boolean am() {
      return this.s != null && this.s.eO() || this.k.U;
   }

   public dmr an() {
      return this.aA;
   }

   public eoe ao() {
      return this.aC;
   }

   public boolean ap() {
      return this.aT;
   }

   public djv aq() {
      return this.Y;
   }

   public elt ar() {
      return this.aw;
   }

   public ekr as() {
      return this.ay;
   }

   public ekp at() {
      return this.az;
   }

   @Override
   public void a(boolean var1) {
      this.aT = _snowman;
   }

   public anw au() {
      return this.aX;
   }

   public djs ax() {
      return this.aB;
   }

   public ekt az() {
      return this.ar;
   }

   @Nullable
   public don aA() {
      return this.z;
   }

   public dsa aB() {
      return this.aD;
   }

   public boolean aC() {
      return false;
   }

   public dez aD() {
      return this.O;
   }

   public eam aE() {
      return this.R;
   }

   private static abu a(String var0, boolean var1, Supplier<abj> var2, abj var3, abo var4, abu.b var5, abx var6) {
      int _snowman = _snowman.b();
      Supplier<abj> _snowmanx = _snowman;
      if (_snowman <= 3) {
         _snowmanx = b(_snowman);
      }

      if (_snowman <= 4) {
         _snowmanx = c(_snowmanx);
      }

      return new abu(_snowman, _snowman, _snowmanx, _snowman, _snowman, _snowman, _snowman);
   }

   private static Supplier<abj> b(Supplier<abj> var0) {
      return () -> new ekn(_snowman.get(), ekn.a);
   }

   private static Supplier<abj> c(Supplier<abj> var0) {
      return () -> new ekq(_snowman.get());
   }

   public void b(int var1) {
      this.aw.a(_snowman);
   }

   static enum a {
      a,
      b,
      c;

      private a() {
      }
   }

   public static final class b implements AutoCloseable {
      private final abw a;
      private final vz b;
      private final cyn c;

      private b(abw var1, vz var2, cyn var3) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
      }

      public abw a() {
         return this.a;
      }

      public vz b() {
         return this.b;
      }

      public cyn c() {
         return this.c;
      }

      @Override
      public void close() {
         this.a.close();
         this.b.close();
      }
   }
}
