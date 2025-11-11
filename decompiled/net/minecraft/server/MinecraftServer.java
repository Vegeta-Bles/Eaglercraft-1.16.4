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
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class MinecraftServer extends aof<wb> implements apd, da, AutoCloseable {
   private static final Logger j = LogManager.getLogger();
   public static final File b = new File("usercache.json");
   public static final bsa c = new bsa("Demo World", bru.b, false, aor.c, false, new brt(), brk.a);
   protected final cyg.a d;
   protected final cyk e;
   private final apc k = new apc("server", this, x.b());
   private final List<Runnable> l = Lists.newArrayList();
   private final anq m = new anq(x.a, this::ai);
   private anw n = ant.a;
   private final aax o;
   private final aaq p;
   private final un q = new un();
   private final Random r = new Random();
   private final DataFixer s;
   private String t;
   private int u = -1;
   protected final gn.b f;
   private final Map<vj<brx>, aag> v = Maps.newLinkedHashMap();
   private acu w;
   private volatile boolean x = true;
   private boolean y;
   private int z;
   protected final Proxy g;
   private boolean A;
   private boolean B;
   private boolean C;
   private boolean D;
   @Nullable
   private String E;
   private int F;
   private int G;
   public final long[] h = new long[100];
   @Nullable
   private KeyPair H;
   @Nullable
   private String I;
   private boolean J;
   private String K = "";
   private String L = "";
   private volatile boolean M;
   private long N;
   private boolean O;
   private boolean P;
   private final MinecraftSessionService Q;
   private final GameProfileRepository R;
   private final acq S;
   private long T;
   private final Thread U;
   private long V = x.b();
   private long W;
   private boolean X;
   private boolean Y;
   private final abw Z;
   private final wa aa = new wa(this);
   @Nullable
   private cya ab;
   private final wd ac = new wd();
   private final vx ad;
   private final afc ae = new afc();
   private boolean af;
   private float ag;
   private final Executor ah;
   @Nullable
   private String ai;
   private vz aj;
   private final csw ak;
   protected final cyn i;

   public static <S extends MinecraftServer> S a(Function<Thread, S> var0) {
      AtomicReference<S> _snowman = new AtomicReference<>();
      Thread _snowmanx = new Thread(() -> _snowman.get().w(), "Server thread");
      _snowmanx.setUncaughtExceptionHandler((var0x, var1x) -> j.error(var1x));
      S _snowmanxx = (S)_snowman.apply(_snowmanx);
      _snowman.set(_snowmanxx);
      _snowmanx.start();
      return _snowmanxx;
   }

   public MinecraftServer(
      Thread var1,
      gn.b var2,
      cyg.a var3,
      cyn var4,
      abw var5,
      Proxy var6,
      DataFixer var7,
      vz var8,
      MinecraftSessionService var9,
      GameProfileRepository var10,
      acq var11,
      aaq var12
   ) {
      super("Server");
      this.f = _snowman;
      this.i = _snowman;
      this.g = _snowman;
      this.Z = _snowman;
      this.aj = _snowman;
      this.Q = _snowman;
      this.R = _snowman;
      this.S = _snowman;
      this.o = new aax(this);
      this.p = _snowman;
      this.d = _snowman;
      this.e = _snowman.b();
      this.s = _snowman;
      this.ad = new vx(this, _snowman.a());
      this.ak = new csw(_snowman.h(), _snowman, _snowman);
      this.U = _snowman;
      this.ah = x.f();
   }

   private void a(cyc var1) {
      ddo _snowman = _snowman.a(ddo::new, "scoreboard");
      _snowman.a(this.aH());
      this.aH().a(new cxr(_snowman));
   }

   protected abstract boolean d() throws IOException;

   public static void a(cyg.a var0) {
      if (_snowman.c()) {
         j.info("Converting map!");
         _snowman.a(new afn() {
            private long a = x.b();

            @Override
            public void a(nr var1) {
            }

            @Override
            public void b(nr var1) {
            }

            @Override
            public void a(int var1) {
               if (x.b() - this.a >= 1000L) {
                  this.a = x.b();
                  MinecraftServer.j.info("Converting... {}%", _snowman);
               }
            }

            @Override
            public void a() {
            }

            @Override
            public void c(nr var1) {
            }
         });
      }
   }

   protected void l_() {
      this.r();
      this.i.a(this.getServerModName(), this.o().isPresent());
      aap _snowman = this.p.create(11);
      this.a(_snowman);
      this.q();
      this.b(_snowman);
   }

   protected void q() {
   }

   protected void a(aap var1) {
      cym _snowman = this.i.H();
      chw _snowmanx = this.i.A();
      boolean _snowmanxx = _snowmanx.g();
      long _snowmanxxx = _snowmanx.a();
      long _snowmanxxxx = bsx.a(_snowmanxxx);
      List<brj> _snowmanxxxxx = ImmutableList.of(new chu(), new cht(), new bff(), new azm(), new bfq(_snowman));
      gi<che> _snowmanxxxxxx = _snowmanx.d();
      che _snowmanxxxxxxx = _snowmanxxxxxx.a(che.b);
      cfy _snowmanxxxxxxxx;
      chd _snowmanxxxxxxxxx;
      if (_snowmanxxxxxxx == null) {
         _snowmanxxxxxxxxx = this.f.a().d(chd.f);
         _snowmanxxxxxxxx = chw.a(this.f.b(gm.ay), this.f.b(gm.ar), new Random().nextLong());
      } else {
         _snowmanxxxxxxxxx = _snowmanxxxxxxx.b();
         _snowmanxxxxxxxx = _snowmanxxxxxxx.c();
      }

      aag _snowmanxxxxxxxxxx = new aag(this, this.ah, this.d, _snowman, brx.g, _snowmanxxxxxxxxx, _snowman, _snowmanxxxxxxxx, _snowmanxx, _snowmanxxxx, _snowmanxxxxx, true);
      this.v.put(brx.g, _snowmanxxxxxxxxxx);
      cyc _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxx.s();
      this.a(_snowmanxxxxxxxxxxx);
      this.ab = new cya(_snowmanxxxxxxxxxxx);
      cfu _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxx.f();
      _snowmanxxxxxxxxxxxx.a(_snowman.r());
      if (!_snowman.p()) {
         try {
            a(_snowmanxxxxxxxxxx, _snowman, _snowmanx.c(), _snowmanxx, true);
            _snowman.c(true);
            if (_snowmanxx) {
               this.a(this.i);
            }
         } catch (Throwable var26) {
            l _snowmanxxxxxxxxxxxxx = l.a(var26, "Exception initializing level");

            try {
               _snowmanxxxxxxxxxx.a(_snowmanxxxxxxxxxxxxx);
            } catch (Throwable var25) {
            }

            throw new u(_snowmanxxxxxxxxxxxxx);
         }

         _snowman.c(true);
      }

      this.ae().a(_snowmanxxxxxxxxxx);
      if (this.i.E() != null) {
         this.aM().a(this.i.E());
      }

      for (Entry<vj<che>, che> _snowmanxxxxxxxxxxxxx : _snowmanxxxxxx.d()) {
         vj<che> _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx.getKey();
         if (_snowmanxxxxxxxxxxxxxx != che.b) {
            vj<brx> _snowmanxxxxxxxxxxxxxxx = vj.a(gm.L, _snowmanxxxxxxxxxxxxxx.a());
            chd _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx.getValue().b();
            cfy _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx.getValue().c();
            cyb _snowmanxxxxxxxxxxxxxxxxxx = new cyb(this.i, _snowman);
            aag _snowmanxxxxxxxxxxxxxxxxxxx = new aag(
               this, this.ah, this.d, _snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx, _snowman, _snowmanxxxxxxxxxxxxxxxxx, _snowmanxx, _snowmanxxxx, ImmutableList.of(), false
            );
            _snowmanxxxxxxxxxxxx.a(new cfs.a(_snowmanxxxxxxxxxxxxxxxxxxx.f()));
            this.v.put(_snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxx);
         }
      }
   }

   private static void a(aag var0, cym var1, boolean var2, boolean var3, boolean var4) {
      cfy _snowman = _snowman.i().g();
      if (!_snowman) {
         _snowman.a(fx.b.b(_snowman.c()), 0.0F);
      } else if (_snowman) {
         _snowman.a(fx.b.b(), 0.0F);
      } else {
         bsy _snowmanx = _snowman.d();
         Random _snowmanxx = new Random(_snowman.C());
         fx _snowmanxxx = _snowmanx.a(0, _snowman.t_(), 0, 256, var0x -> var0x.b().b(), _snowmanxx);
         brd _snowmanxxxx = _snowmanxxx == null ? new brd(0, 0) : new brd(_snowmanxxx);
         if (_snowmanxxx == null) {
            j.warn("Unable to find spawn biome");
         }

         boolean _snowmanxxxxx = false;

         for (buo _snowmanxxxxxx : aed.V.b()) {
            if (_snowmanx.c().contains(_snowmanxxxxxx.n())) {
               _snowmanxxxxx = true;
               break;
            }
         }

         _snowman.a(_snowmanxxxx.l().b(8, _snowman.c(), 8), 0.0F);
         int _snowmanxxxxxxx = 0;
         int _snowmanxxxxxxxx = 0;
         int _snowmanxxxxxxxxx = 0;
         int _snowmanxxxxxxxxxx = -1;
         int _snowmanxxxxxxxxxxx = 32;

         for (int _snowmanxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxx < 1024; _snowmanxxxxxxxxxxxx++) {
            if (_snowmanxxxxxxx > -16 && _snowmanxxxxxxx <= 16 && _snowmanxxxxxxxx > -16 && _snowmanxxxxxxxx <= 16) {
               fx _snowmanxxxxxxxxxxxxx = aab.a(_snowman, new brd(_snowmanxxxx.b + _snowmanxxxxxxx, _snowmanxxxx.c + _snowmanxxxxxxxx), _snowmanxxxxx);
               if (_snowmanxxxxxxxxxxxxx != null) {
                  _snowman.a(_snowmanxxxxxxxxxxxxx, 0.0F);
                  break;
               }
            }

            if (_snowmanxxxxxxx == _snowmanxxxxxxxx || _snowmanxxxxxxx < 0 && _snowmanxxxxxxx == -_snowmanxxxxxxxx || _snowmanxxxxxxx > 0 && _snowmanxxxxxxx == 1 - _snowmanxxxxxxxx) {
               int _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxx;
               _snowmanxxxxxxxxx = -_snowmanxxxxxxxxxx;
               _snowmanxxxxxxxxxx = _snowmanxxxxxxxxxxxxx;
            }

            _snowmanxxxxxxx += _snowmanxxxxxxxxx;
            _snowmanxxxxxxxx += _snowmanxxxxxxxxxx;
         }

         if (_snowman) {
            civ<?, ?> _snowmanxxxxxxxxxxxx = kh.U;
            _snowmanxxxxxxxxxxxx.a(_snowman, _snowman, _snowman.t, new fx(_snowman.a(), _snowman.b(), _snowman.c()));
         }
      }
   }

   private void a(cyn var1) {
      _snowman.a(aor.a);
      _snowman.d(true);
      cym _snowman = _snowman.H();
      _snowman.b(false);
      _snowman.a(false);
      _snowman.a(1000000000);
      _snowman.b(6000L);
      _snowman.a(bru.e);
   }

   private void b(aap var1) {
      aag _snowman = this.E();
      j.info("Preparing start region for dimension {}", _snowman.Y().a());
      fx _snowmanx = _snowman.u();
      _snowman.a(new brd(_snowmanx));
      aae _snowmanxx = _snowman.i();
      _snowmanxx.a().a(500);
      this.V = x.b();
      _snowmanxx.a(aal.a, new brd(_snowmanx), 11, afx.a);

      while (_snowmanxx.b() != 441) {
         this.V = x.b() + 10L;
         this.x();
      }

      this.V = x.b() + 10L;
      this.x();

      for (aag _snowmanxxx : this.v.values()) {
         brs _snowmanxxxx = _snowmanxxx.s().b(brs::new, "chunks");
         if (_snowmanxxxx != null) {
            LongIterator _snowmanxxxxx = _snowmanxxxx.a().iterator();

            while (_snowmanxxxxx.hasNext()) {
               long _snowmanxxxxxx = _snowmanxxxxx.nextLong();
               brd _snowmanxxxxxxx = new brd(_snowmanxxxxxx);
               _snowmanxxx.i().a(_snowmanxxxxxxx, true);
            }
         }
      }

      this.V = x.b() + 10L;
      this.x();
      _snowman.b();
      _snowmanxx.a().a(5);
      this.bc();
   }

   protected void r() {
      File _snowman = this.d.a(cye.h).toFile();
      if (_snowman.isFile()) {
         String _snowmanx = this.d.a();

         try {
            this.a("level://" + URLEncoder.encode(_snowmanx, StandardCharsets.UTF_8.toString()) + "/" + "resources.zip", "");
         } catch (UnsupportedEncodingException var4) {
            j.warn("Something went wrong url encoding {}", _snowmanx);
         }
      }
   }

   public bru s() {
      return this.i.m();
   }

   public boolean f() {
      return this.i.n();
   }

   public abstract int g();

   public abstract int h();

   public abstract boolean i();

   public boolean a(boolean var1, boolean var2, boolean var3) {
      boolean _snowman = false;

      for (aag _snowmanx : this.G()) {
         if (!_snowman) {
            j.info("Saving chunks for level '{}'/{}", _snowmanx, _snowmanx.Y().a());
         }

         _snowmanx.a(null, _snowman, _snowmanx.c && !_snowman);
         _snowman = true;
      }

      aag _snowmanx = this.E();
      cym _snowmanxx = this.i.H();
      _snowmanxx.a(_snowmanx.f().t());
      this.i.b(this.aM().c());
      this.d.a(this.f, this.i, this.ae().q());
      return _snowman;
   }

   @Override
   public void close() {
      this.t();
   }

   protected void t() {
      j.info("Stopping server");
      if (this.af() != null) {
         this.af().b();
      }

      if (this.w != null) {
         j.info("Saving players");
         this.w.h();
         this.w.r();
      }

      j.info("Saving worlds");

      for (aag _snowman : this.G()) {
         if (_snowman != null) {
            _snowman.c = false;
         }
      }

      this.a(false, true, false);

      for (aag _snowmanx : this.G()) {
         if (_snowmanx != null) {
            try {
               _snowmanx.close();
            } catch (IOException var5) {
               j.error("Exception closing the level", var5);
            }
         }
      }

      if (this.k.d()) {
         this.k.e();
      }

      this.aj.close();

      try {
         this.d.close();
      } catch (IOException var4) {
         j.error("Failed to unlock level {}", this.d.a(), var4);
      }
   }

   public String u() {
      return this.t;
   }

   public void a_(String var1) {
      this.t = _snowman;
   }

   public boolean v() {
      return this.x;
   }

   public void a(boolean var1) {
      this.x = false;
      if (_snowman) {
         try {
            this.U.join();
         } catch (InterruptedException var3) {
            j.error("Error while shutting down", var3);
         }
      }
   }

   protected void w() {
      try {
         if (this.d()) {
            this.V = x.b();
            this.q.a(new oe(this.E));
            this.q.a(new un.c(w.a().getName(), w.a().getProtocolVersion()));
            this.a(this.q);

            while (this.x) {
               long _snowman = x.b() - this.V;
               if (_snowman > 2000L && this.V - this.N >= 15000L) {
                  long _snowmanx = _snowman / 50L;
                  j.warn("Can't keep up! Is the server overloaded? Running {}ms or {} ticks behind", _snowman, _snowmanx);
                  this.V += _snowmanx * 50L;
                  this.N = this.V;
               }

               this.V += 50L;
               anz _snowmanx = anz.a("Server");
               this.a(_snowmanx);
               this.n.a();
               this.n.a("tick");
               this.a(this::ba);
               this.n.b("nextTickWait");
               this.X = true;
               this.W = Math.max(x.b() + 50L, this.V);
               this.x();
               this.n.c();
               this.n.b();
               this.b(_snowmanx);
               this.M = true;
            }
         } else {
            this.a(null);
         }
      } catch (Throwable var44) {
         j.error("Encountered an unexpected exception", var44);
         l _snowman;
         if (var44 instanceof u) {
            _snowman = this.b(((u)var44).a());
         } else {
            _snowman = this.b(new l("Exception in server tick loop", var44));
         }

         File _snowmanx = new File(new File(this.B(), "crash-reports"), "crash-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + "-server.txt");
         if (_snowman.a(_snowmanx)) {
            j.error("This crash report has been saved to: {}", _snowmanx.getAbsolutePath());
         } else {
            j.error("We were unable to save this crash report to disk.");
         }

         this.a(_snowman);
      } finally {
         try {
            this.y = true;
            this.t();
         } catch (Throwable var42) {
            j.error("Exception stopping the server", var42);
         } finally {
            this.e();
         }
      }
   }

   private boolean ba() {
      return this.bn() || x.b() < (this.X ? this.W : this.V);
   }

   protected void x() {
      this.bl();
      this.c(() -> !this.ba());
   }

   protected wb a(Runnable var1) {
      return new wb(this.z, _snowman);
   }

   protected boolean a(wb var1) {
      return _snowman.a() + 3 < this.z || this.ba();
   }

   @Override
   public boolean y() {
      boolean _snowman = this.bb();
      this.X = _snowman;
      return _snowman;
   }

   private boolean bb() {
      if (super.y()) {
         return true;
      } else {
         if (this.ba()) {
            for (aag _snowman : this.G()) {
               if (_snowman.i().d()) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   protected void b(wb var1) {
      this.aQ().c("runTask");
      super.c(_snowman);
   }

   private void a(un var1) {
      File _snowman = this.c("server-icon.png");
      if (!_snowman.exists()) {
         _snowman = this.d.f();
      }

      if (_snowman.isFile()) {
         ByteBuf _snowmanx = Unpooled.buffer();

         try {
            BufferedImage _snowmanxx = ImageIO.read(_snowman);
            Validate.validState(_snowmanxx.getWidth() == 64, "Must be 64 pixels wide", new Object[0]);
            Validate.validState(_snowmanxx.getHeight() == 64, "Must be 64 pixels high", new Object[0]);
            ImageIO.write(_snowmanxx, "PNG", new ByteBufOutputStream(_snowmanx));
            ByteBuffer _snowmanxxx = Base64.getEncoder().encode(_snowmanx.nioBuffer());
            _snowman.a("data:image/png;base64," + StandardCharsets.UTF_8.decode(_snowmanxxx));
         } catch (Exception var9) {
            j.error("Couldn't load server icon", var9);
         } finally {
            _snowmanx.release();
         }
      }
   }

   public boolean z() {
      this.Y = this.Y || this.A().isFile();
      return this.Y;
   }

   public File A() {
      return this.d.f();
   }

   public File B() {
      return new File(".");
   }

   protected void a(l var1) {
   }

   protected void e() {
   }

   protected void a(BooleanSupplier var1) {
      long _snowman = x.c();
      this.z++;
      this.b(_snowman);
      if (_snowman - this.T >= 5000000000L) {
         this.T = _snowman;
         this.q.a(new un.a(this.J(), this.I()));
         GameProfile[] _snowmanx = new GameProfile[Math.min(this.I(), 12)];
         int _snowmanxx = afm.a(this.r, 0, this.I() - _snowmanx.length);

         for (int _snowmanxxx = 0; _snowmanxxx < _snowmanx.length; _snowmanxxx++) {
            _snowmanx[_snowmanxxx] = this.w.s().get(_snowmanxx + _snowmanxxx).eA();
         }

         Collections.shuffle(Arrays.asList(_snowmanx));
         this.q.b().a(_snowmanx);
      }

      if (this.z % 6000 == 0) {
         j.debug("Autosave started");
         this.n.a("save");
         this.w.h();
         this.a(true, false, false);
         this.n.c();
         j.debug("Autosave finished");
      }

      this.n.a("snooper");
      if (!this.k.d() && this.z > 100) {
         this.k.a();
      }

      if (this.z % 6000 == 0) {
         this.k.b();
      }

      this.n.c();
      this.n.a("tallying");
      long _snowmanx = this.h[this.z % 100] = x.c() - _snowman;
      this.ag = this.ag * 0.8F + (float)_snowmanx / 1000000.0F * 0.19999999F;
      long _snowmanxx = x.c();
      this.ae.a(_snowmanxx - _snowman);
      this.n.c();
   }

   protected void b(BooleanSupplier var1) {
      this.n.a("commandFunctions");
      this.aB().d();
      this.n.b("levels");

      for (aag _snowman : this.G()) {
         this.n.a(() -> _snowman + " " + _snowman.Y().a());
         if (this.z % 20 == 0) {
            this.n.a("timeSync");
            this.w.a(new rk(_snowman.T(), _snowman.U(), _snowman.V().b(brt.j)), _snowman.Y());
            this.n.c();
         }

         this.n.a("tick");

         try {
            _snowman.a(_snowman);
         } catch (Throwable var6) {
            l _snowmanx = l.a(var6, "Exception ticking world");
            _snowman.a(_snowmanx);
            throw new u(_snowmanx);
         }

         this.n.c();
         this.n.c();
      }

      this.n.b("connection");
      this.af().c();
      this.n.b("players");
      this.w.d();
      if (w.d) {
         ll.a.b();
      }

      this.n.b("server gui refresh");

      for (int _snowman = 0; _snowman < this.l.size(); _snowman++) {
         this.l.get(_snowman).run();
      }

      this.n.c();
   }

   public boolean C() {
      return true;
   }

   public void b(Runnable var1) {
      this.l.add(_snowman);
   }

   protected void b(String var1) {
      this.ai = _snowman;
   }

   public boolean D() {
      return !this.U.isAlive();
   }

   public File c(String var1) {
      return new File(this.B(), _snowman);
   }

   public final aag E() {
      return this.v.get(brx.g);
   }

   @Nullable
   public aag a(vj<brx> var1) {
      return this.v.get(_snowman);
   }

   public Set<vj<brx>> F() {
      return this.v.keySet();
   }

   public Iterable<aag> G() {
      return this.v.values();
   }

   public String H() {
      return w.a().getName();
   }

   public int I() {
      return this.w.m();
   }

   public int J() {
      return this.w.n();
   }

   public String[] K() {
      return this.w.e();
   }

   public String getServerModName() {
      return "vanilla";
   }

   public l b(l var1) {
      if (this.w != null) {
         _snowman.g().a("Player Count", () -> this.w.m() + " / " + this.w.n() + "; " + this.w.s());
      }

      _snowman.g().a("Data Packs", () -> {
         StringBuilder _snowman = new StringBuilder();

         for (abu _snowmanx : this.Z.e()) {
            if (_snowman.length() > 0) {
               _snowman.append(", ");
            }

            _snowman.append(_snowmanx.e());
            if (!_snowmanx.c().a()) {
               _snowman.append(" (incompatible)");
            }
         }

         return _snowman.toString();
      });
      if (this.ai != null) {
         _snowman.g().a("Server Id", () -> this.ai);
      }

      return _snowman;
   }

   public abstract Optional<String> o();

   @Override
   public void a(nr var1, UUID var2) {
      j.info(_snowman.getString());
   }

   public KeyPair L() {
      return this.H;
   }

   public int M() {
      return this.u;
   }

   public void a(int var1) {
      this.u = _snowman;
   }

   public String N() {
      return this.I;
   }

   public void d(String var1) {
      this.I = _snowman;
   }

   public boolean O() {
      return this.I != null;
   }

   protected void P() {
      j.info("Generating keypair");

      try {
         this.H = aeu.b();
      } catch (aev var2) {
         throw new IllegalStateException("Failed to generate key pair", var2);
      }
   }

   public void a(aor var1, boolean var2) {
      if (_snowman || !this.i.t()) {
         this.i.a(this.i.n() ? aor.d : _snowman);
         this.bc();
         this.ae().s().forEach(this::b);
      }
   }

   public int b(int var1) {
      return _snowman;
   }

   private void bc() {
      for (aag _snowman : this.G()) {
         _snowman.b(this.Q(), this.X());
      }
   }

   public void b(boolean var1) {
      this.i.d(_snowman);
      this.ae().s().forEach(this::b);
   }

   private void b(aah var1) {
      cyd _snowman = _snowman.u().h();
      _snowman.b.a(new pa(_snowman.s(), _snowman.t()));
   }

   protected boolean Q() {
      return this.i.s() != aor.a;
   }

   public boolean R() {
      return this.J;
   }

   public void c(boolean var1) {
      this.J = _snowman;
   }

   public String S() {
      return this.K;
   }

   public String T() {
      return this.L;
   }

   public void a(String var1, String var2) {
      this.K = _snowman;
      this.L = _snowman;
   }

   @Override
   public void a(apc var1) {
      _snowman.a("whitelist_enabled", false);
      _snowman.a("whitelist_count", 0);
      if (this.w != null) {
         _snowman.a("players_current", this.I());
         _snowman.a("players_max", this.J());
         _snowman.a("players_seen", this.e.a().length);
      }

      _snowman.a("uses_auth", this.A);
      _snowman.a("gui_state", this.ah() ? "enabled" : "disabled");
      _snowman.a("run_time", (x.b() - _snowman.g()) / 60L * 1000L);
      _snowman.a("avg_tick_ms", (int)(afm.a(this.h) * 1.0E-6));
      int _snowman = 0;

      for (aag _snowmanx : this.G()) {
         if (_snowmanx != null) {
            _snowman.a("world[" + _snowman + "][dimension]", _snowmanx.Y().a());
            _snowman.a("world[" + _snowman + "][mode]", this.i.m());
            _snowman.a("world[" + _snowman + "][difficulty]", _snowmanx.ad());
            _snowman.a("world[" + _snowman + "][hardcore]", this.i.n());
            _snowman.a("world[" + _snowman + "][height]", this.F);
            _snowman.a("world[" + _snowman + "][chunks_loaded]", _snowmanx.i().h());
            _snowman++;
         }
      }

      _snowman.a("worlds", _snowman);
   }

   public abstract boolean j();

   public abstract int k();

   public boolean V() {
      return this.A;
   }

   public void d(boolean var1) {
      this.A = _snowman;
   }

   public boolean W() {
      return this.B;
   }

   public void e(boolean var1) {
      this.B = _snowman;
   }

   public boolean X() {
      return true;
   }

   public boolean Y() {
      return true;
   }

   public abstract boolean l();

   public boolean Z() {
      return this.C;
   }

   public void f(boolean var1) {
      this.C = _snowman;
   }

   public boolean aa() {
      return this.D;
   }

   public void g(boolean var1) {
      this.D = _snowman;
   }

   public abstract boolean m();

   public String ab() {
      return this.E;
   }

   public void e(String var1) {
      this.E = _snowman;
   }

   public int ac() {
      return this.F;
   }

   public void c(int var1) {
      this.F = _snowman;
   }

   public boolean ad() {
      return this.y;
   }

   public acu ae() {
      return this.w;
   }

   public void a(acu var1) {
      this.w = _snowman;
   }

   public abstract boolean n();

   public void a(bru var1) {
      this.i.a(_snowman);
   }

   @Nullable
   public aax af() {
      return this.o;
   }

   public boolean ag() {
      return this.M;
   }

   public boolean ah() {
      return false;
   }

   public abstract boolean a(bru var1, boolean var2, int var3);

   public int ai() {
      return this.z;
   }

   public apc aj() {
      return this.k;
   }

   public int ak() {
      return 16;
   }

   public boolean a(aag var1, fx var2, bfw var3) {
      return false;
   }

   public void h(boolean var1) {
      this.P = _snowman;
   }

   public boolean al() {
      return this.P;
   }

   public boolean am() {
      return true;
   }

   public int ao() {
      return this.G;
   }

   public void d(int var1) {
      this.G = _snowman;
   }

   public MinecraftSessionService ap() {
      return this.Q;
   }

   public GameProfileRepository aq() {
      return this.R;
   }

   public acq ar() {
      return this.S;
   }

   public un as() {
      return this.q;
   }

   public void at() {
      this.T = 0L;
   }

   public int au() {
      return 29999984;
   }

   @Override
   public boolean av() {
      return super.av() && !this.ad();
   }

   @Override
   public Thread aw() {
      return this.U;
   }

   public int ax() {
      return 256;
   }

   public long ay() {
      return this.V;
   }

   public DataFixer az() {
      return this.s;
   }

   public int a(@Nullable aag var1) {
      return _snowman != null ? _snowman.V().c(brt.q) : 10;
   }

   public vv aA() {
      return this.aj.g();
   }

   public vx aB() {
      return this.ad;
   }

   public CompletableFuture<Void> a(Collection<String> var1) {
      CompletableFuture<Void> _snowman = CompletableFuture.<ImmutableList>supplyAsync(
            () -> _snowman.stream().map(this.Z::a).filter(Objects::nonNull).map(abu::d).collect(ImmutableList.toImmutableList()), this
         )
         .thenCompose(var1x -> vz.a(var1x, this.j() ? dc.a.b : dc.a.c, this.h(), this.ah, this))
         .thenAcceptAsync(var2x -> {
            this.aj.close();
            this.aj = var2x;
            this.Z.a(_snowman);
            this.i.a(a(this.Z));
            var2x.i();
            this.ae().h();
            this.ae().t();
            this.ad.a(this.aj.a());
            this.ak.a(this.aj.h());
         }, this);
      if (this.bh()) {
         this.c(_snowman::isDone);
      }

      return _snowman;
   }

   public static brk a(abw var0, brk var1, boolean var2) {
      _snowman.a();
      if (_snowman) {
         _snowman.a(Collections.singleton("vanilla"));
         return new brk(ImmutableList.of("vanilla"), ImmutableList.of());
      } else {
         Set<String> _snowman = Sets.newLinkedHashSet();

         for (String _snowmanx : _snowman.a()) {
            if (_snowman.b(_snowmanx)) {
               _snowman.add(_snowmanx);
            } else {
               j.warn("Missing data pack {}", _snowmanx);
            }
         }

         for (abu _snowmanxx : _snowman.c()) {
            String _snowmanxxx = _snowmanxx.e();
            if (!_snowman.b().contains(_snowmanxxx) && !_snowman.contains(_snowmanxxx)) {
               j.info("Found new data pack {}, loading it automatically", _snowmanxxx);
               _snowman.add(_snowmanxxx);
            }
         }

         if (_snowman.isEmpty()) {
            j.info("No datapacks selected, forcing vanilla");
            _snowman.add("vanilla");
         }

         _snowman.a(_snowman);
         return a(_snowman);
      }
   }

   private static brk a(abw var0) {
      Collection<String> _snowman = _snowman.d();
      List<String> _snowmanx = ImmutableList.copyOf(_snowman);
      List<String> _snowmanxx = _snowman.b().stream().filter(var1x -> !_snowman.contains(var1x)).collect(ImmutableList.toImmutableList());
      return new brk(_snowmanx, _snowmanxx);
   }

   public void a(db var1) {
      if (this.aN()) {
         acu _snowman = _snowman.j().ae();
         adb _snowmanx = _snowman.i();

         for (aah _snowmanxx : Lists.newArrayList(_snowman.s())) {
            if (!_snowmanx.a(_snowmanxx.eA())) {
               _snowmanxx.b.b(new of("multiplayer.disconnect.not_whitelisted"));
            }
         }
      }
   }

   public abw aC() {
      return this.Z;
   }

   public dc aD() {
      return this.aj.f();
   }

   public db aE() {
      aag _snowman = this.E();
      return new db(this, _snowman == null ? dcn.a : dcn.b(_snowman.u()), dcm.a, _snowman, 4, "Server", new oe("Server"), this, null);
   }

   @Override
   public boolean a() {
      return true;
   }

   @Override
   public boolean b() {
      return true;
   }

   public bor aF() {
      return this.aj.e();
   }

   public aen aG() {
      return this.aj.d();
   }

   public wa aH() {
      return this.aa;
   }

   public cya aI() {
      if (this.ab == null) {
         throw new NullPointerException("Called before server init");
      } else {
         return this.ab;
      }
   }

   public cyz aJ() {
      return this.aj.c();
   }

   public cza aK() {
      return this.aj.b();
   }

   public brt aL() {
      return this.E().V();
   }

   public wd aM() {
      return this.ac;
   }

   public boolean aN() {
      return this.af;
   }

   public void i(boolean var1) {
      this.af = _snowman;
   }

   public float aO() {
      return this.ag;
   }

   public int b(GameProfile var1) {
      if (this.ae().h(_snowman)) {
         acw _snowman = this.ae().k().b(_snowman);
         if (_snowman != null) {
            return _snowman.a();
         } else if (this.a(_snowman)) {
            return 4;
         } else if (this.O()) {
            return this.ae().u() ? 4 : 0;
         } else {
            return this.g();
         }
      } else {
         return 0;
      }
   }

   public afc aP() {
      return this.ae;
   }

   public anw aQ() {
      return this.n;
   }

   public abstract boolean a(GameProfile var1);

   public void a(Path var1) throws IOException {
      Path _snowman = _snowman.resolve("levels");

      for (Entry<vj<brx>, aag> _snowmanx : this.v.entrySet()) {
         vk _snowmanxx = _snowmanx.getKey().a();
         Path _snowmanxxx = _snowman.resolve(_snowmanxx.b()).resolve(_snowmanxx.a());
         Files.createDirectories(_snowmanxxx);
         _snowmanx.getValue().a(_snowmanxxx);
      }

      this.d(_snowman.resolve("gamerules.txt"));
      this.e(_snowman.resolve("classpath.txt"));
      this.c(_snowman.resolve("example_crash.txt"));
      this.b(_snowman.resolve("stats.txt"));
      this.f(_snowman.resolve("threads.txt"));
   }

   private void b(Path var1) throws IOException {
      try (Writer _snowman = Files.newBufferedWriter(_snowman)) {
         _snowman.write(String.format("pending_tasks: %d\n", this.bi()));
         _snowman.write(String.format("average_tick_time: %f\n", this.aO()));
         _snowman.write(String.format("tick_times: %s\n", Arrays.toString(this.h)));
         _snowman.write(String.format("queue: %s\n", x.f()));
      }
   }

   private void c(Path var1) throws IOException {
      l _snowman = new l("Server dump", new Exception("dummy"));
      this.b(_snowman);

      try (Writer _snowmanx = Files.newBufferedWriter(_snowman)) {
         _snowmanx.write(_snowman.e());
      }
   }

   private void d(Path var1) throws IOException {
      try (Writer _snowman = Files.newBufferedWriter(_snowman)) {
         final List<String> _snowmanx = Lists.newArrayList();
         final brt _snowmanxx = this.aL();
         brt.a(new brt.c() {
            @Override
            public <T extends brt.g<T>> void a(brt.e<T> var1, brt.f<T> var2) {
               _snowman.add(String.format("%s=%s\n", _snowman.a(), _snowman.<T>a(_snowman).toString()));
            }
         });

         for (String _snowmanxxx : _snowmanx) {
            _snowman.write(_snowmanxxx);
         }
      }
   }

   private void e(Path var1) throws IOException {
      try (Writer _snowman = Files.newBufferedWriter(_snowman)) {
         String _snowmanx = System.getProperty("java.class.path");
         String _snowmanxx = System.getProperty("path.separator");

         for (String _snowmanxxx : Splitter.on(_snowmanxx).split(_snowmanx)) {
            _snowman.write(_snowmanxxx);
            _snowman.write("\n");
         }
      }
   }

   private void f(Path var1) throws IOException {
      ThreadMXBean _snowman = ManagementFactory.getThreadMXBean();
      ThreadInfo[] _snowmanx = _snowman.dumpAllThreads(true, true);
      Arrays.sort(_snowmanx, Comparator.comparing(ThreadInfo::getThreadName));

      try (Writer _snowmanxx = Files.newBufferedWriter(_snowman)) {
         for (ThreadInfo _snowmanxxx : _snowmanx) {
            _snowmanxx.write(_snowmanxxx.toString());
            _snowmanxx.write(10);
         }
      }
   }

   private void a(@Nullable anz var1) {
      if (this.O) {
         this.O = false;
         this.m.c();
      }

      this.n = anz.a(this.m.d(), _snowman);
   }

   private void b(@Nullable anz var1) {
      if (_snowman != null) {
         _snowman.b();
      }

      this.n = this.m.d();
   }

   public boolean aS() {
      return this.m.a();
   }

   public void aT() {
      this.O = true;
   }

   public anv aU() {
      anv _snowman = this.m.e();
      this.m.b();
      return _snowman;
   }

   public Path a(cye var1) {
      return this.d.a(_snowman);
   }

   public boolean aV() {
      return true;
   }

   public csw aW() {
      return this.ak;
   }

   public cyn aX() {
      return this.i;
   }

   public gn aY() {
      return this.f;
   }

   @Nullable
   public abc a(aah var1) {
      return null;
   }
}
