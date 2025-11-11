import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.datafixers.DataFixer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Proxy;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class zg extends MinecraftServer implements vy {
   private static final Logger j = LogManager.getLogger();
   private static final Pattern k = Pattern.compile("^[a-fA-F0-9]{40}$");
   private final List<vo> l = Collections.synchronizedList(Lists.newArrayList());
   private adj m;
   private final adg n;
   private adl o;
   private final zi p;
   @Nullable
   private zm q;
   @Nullable
   private final abd r;

   public zg(
      Thread var1,
      gn.b var2,
      cyg.a var3,
      abw var4,
      vz var5,
      cyn var6,
      zi var7,
      DataFixer var8,
      MinecraftSessionService var9,
      GameProfileRepository var10,
      acq var11,
      aaq var12
   ) {
      super(_snowman, _snowman, _snowman, _snowman, _snowman, Proxy.NO_PROXY, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      this.p = _snowman;
      this.n = new adg(this);
      this.r = null;
   }

   @Override
   public boolean d() throws IOException {
      Thread _snowman = new Thread("Server console handler") {
         @Override
         public void run() {
            BufferedReader _snowman = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));

            String _snowmanx;
            try {
               while (!zg.this.ad() && zg.this.v() && (_snowmanx = _snowman.readLine()) != null) {
                  zg.this.a(_snowmanx, zg.this.aE());
               }
            } catch (IOException var4) {
               zg.j.error("Exception handling console input", var4);
            }
         }
      };
      _snowman.setDaemon(true);
      _snowman.setUncaughtExceptionHandler(new o(j));
      _snowman.start();
      j.info("Starting minecraft server version " + w.a().getName());
      if (Runtime.getRuntime().maxMemory() / 1024L / 1024L < 512L) {
         j.warn("To start the server with more ram, launch it as \"java -Xmx1024M -Xms1024M -jar minecraft_server.jar\"");
      }

      j.info("Loading properties");
      zh _snowmanx = this.p.a();
      if (this.O()) {
         this.a_("127.0.0.1");
      } else {
         this.d(_snowmanx.a);
         this.e(_snowmanx.b);
         this.a_(_snowmanx.c);
      }

      this.f(_snowmanx.f);
      this.g(_snowmanx.g);
      this.a(_snowmanx.h, this.ba());
      this.e(_snowmanx.i);
      this.h(_snowmanx.j);
      super.d(_snowmanx.U.get());
      this.i(_snowmanx.k);
      this.i.a(_snowmanx.m);
      j.info("Default game type: {}", _snowmanx.m);
      InetAddress _snowmanxx = null;
      if (!this.u().isEmpty()) {
         _snowmanxx = InetAddress.getByName(this.u());
      }

      if (this.M() < 0) {
         this.a(_snowmanx.o);
      }

      this.P();
      j.info("Starting Minecraft server on {}:{}", this.u().isEmpty() ? "*" : this.u(), this.M());

      try {
         this.af().a(_snowmanxx, this.M());
      } catch (IOException var10) {
         j.warn("**** FAILED TO BIND TO PORT!");
         j.warn("The exception was: {}", var10.toString());
         j.warn("Perhaps a server is already running on that port?");
         return false;
      }

      if (!this.V()) {
         j.warn("**** SERVER IS RUNNING IN OFFLINE/INSECURE MODE!");
         j.warn("The server will make no attempt to authenticate usernames. Beware.");
         j.warn(
            "While this makes the game possible to play without internet access, it also opens up the ability for hackers to connect with any username they choose."
         );
         j.warn("To change this, set \"online-mode\" to \"true\" in the server.properties file.");
      }

      if (this.be()) {
         this.ar().b();
      }

      if (!act.e(this)) {
         return false;
      } else {
         this.a(new zf(this, this.f, this.e));
         long _snowmanxxx = x.c();
         this.c(_snowmanx.p);
         cdg.a(this.ar());
         cdg.a(this.ap());
         acq.a(this.V());
         j.info("Preparing level \"{}\"", this.k_());
         this.l_();
         long _snowmanxxxx = x.c() - _snowmanxxx;
         String _snowmanxxxxx = String.format(Locale.ROOT, "%.3fs", (double)_snowmanxxxx / 1.0E9);
         j.info("Done ({})! For help, type \"help\"", _snowmanxxxxx);
         if (_snowmanx.q != null) {
            this.aL().a(brt.w).a(_snowmanx.q, this);
         }

         if (_snowmanx.r) {
            j.info("Starting GS4 status listener");
            this.m = adj.a(this);
         }

         if (_snowmanx.t) {
            j.info("Starting remote control listener");
            this.o = adl.a(this);
         }

         if (this.bf() > 0L) {
            Thread _snowmanxxxxxx = new Thread(new zj(this));
            _snowmanxxxxxx.setUncaughtExceptionHandler(new p(j));
            _snowmanxxxxxx.setName("Server Watchdog");
            _snowmanxxxxxx.setDaemon(true);
            _snowmanxxxxxx.start();
         }

         bmd.a.a(bks.g, gj.a());
         if (_snowmanx.Q) {
            ann.a(this);
         }

         return true;
      }
   }

   @Override
   public boolean X() {
      return this.g_().d && super.X();
   }

   @Override
   public boolean Q() {
      return this.p.a().A && super.Q();
   }

   @Override
   public boolean Y() {
      return this.p.a().e && super.Y();
   }

   public String ba() {
      zh _snowman = this.p.a();
      String _snowmanx;
      if (!_snowman.x.isEmpty()) {
         _snowmanx = _snowman.x;
         if (!Strings.isNullOrEmpty(_snowman.w)) {
            j.warn("resource-pack-hash is deprecated and found along side resource-pack-sha1. resource-pack-hash will be ignored.");
         }
      } else if (!Strings.isNullOrEmpty(_snowman.w)) {
         j.warn("resource-pack-hash is deprecated. Please use resource-pack-sha1 instead.");
         _snowmanx = _snowman.w;
      } else {
         _snowmanx = "";
      }

      if (!_snowmanx.isEmpty() && !k.matcher(_snowmanx).matches()) {
         j.warn("Invalid sha1 for ressource-pack-sha1");
      }

      if (!_snowman.h.isEmpty() && _snowmanx.isEmpty()) {
         j.warn("You specified a resource pack without providing a sha1 hash. Pack will be updated on the client only if_ you change the name of the pack.");
      }

      return _snowmanx;
   }

   @Override
   public zh g_() {
      return this.p.a();
   }

   @Override
   public void q() {
      this.a(this.g_().l, true);
   }

   @Override
   public boolean f() {
      return this.g_().y;
   }

   @Override
   public l b(l var1) {
      _snowman = super.b(_snowman);
      _snowman.g().a("Is Modded", () -> this.o().orElse("Unknown (can't tell)"));
      _snowman.g().a("Type", () -> "Dedicated Server (map_server.txt)");
      return _snowman;
   }

   @Override
   public Optional<String> o() {
      String _snowman = this.getServerModName();
      return !"vanilla".equals(_snowman) ? Optional.of("Definitely; Server brand changed to '" + _snowman + "'") : Optional.empty();
   }

   @Override
   public void e() {
      if (this.r != null) {
         this.r.close();
      }

      if (this.q != null) {
         this.q.b();
      }

      if (this.o != null) {
         this.o.b();
      }

      if (this.m != null) {
         this.m.b();
      }
   }

   @Override
   public void b(BooleanSupplier var1) {
      super.b(_snowman);
      this.bb();
   }

   @Override
   public boolean C() {
      return this.g_().z;
   }

   @Override
   public void a(apc var1) {
      _snowman.a("whitelist_enabled", this.bc().o());
      _snowman.a("whitelist_count", this.bc().j().length);
      super.a(_snowman);
   }

   public void a(String var1, db var2) {
      this.l.add(new vo(_snowman, _snowman));
   }

   public void bb() {
      while (!this.l.isEmpty()) {
         vo _snowman = this.l.remove(0);
         this.aD().a(_snowman.b, _snowman.a);
      }
   }

   @Override
   public boolean j() {
      return true;
   }

   @Override
   public int k() {
      return this.g_().I;
   }

   @Override
   public boolean l() {
      return this.g_().C;
   }

   public zf bc() {
      return (zf)super.ae();
   }

   @Override
   public boolean n() {
      return true;
   }

   @Override
   public String h_() {
      return this.u();
   }

   @Override
   public int p() {
      return this.M();
   }

   @Override
   public String i_() {
      return this.ab();
   }

   public void bd() {
      if (this.q == null) {
         this.q = zm.a(this);
      }
   }

   @Override
   public boolean ah() {
      return this.q != null;
   }

   @Override
   public boolean a(bru var1, boolean var2, int var3) {
      return false;
   }

   @Override
   public boolean m() {
      return this.g_().D;
   }

   @Override
   public int ak() {
      return this.g_().E;
   }

   @Override
   public boolean a(aag var1, fx var2, bfw var3) {
      if (_snowman.Y() != brx.g) {
         return false;
      } else if (this.bc().k().c()) {
         return false;
      } else if (this.bc().h(_snowman.eA())) {
         return false;
      } else if (this.ak() <= 0) {
         return false;
      } else {
         fx _snowman = _snowman.u();
         int _snowmanx = afm.a(_snowman.u() - _snowman.u());
         int _snowmanxx = afm.a(_snowman.w() - _snowman.w());
         int _snowmanxxx = Math.max(_snowmanx, _snowmanxx);
         return _snowmanxxx <= this.ak();
      }
   }

   @Override
   public boolean am() {
      return this.g_().R;
   }

   @Override
   public int g() {
      return this.g_().F;
   }

   @Override
   public int h() {
      return this.g_().G;
   }

   @Override
   public void d(int var1) {
      super.d(_snowman);
      this.p.a(var2 -> var2.U.a(this.aY(), _snowman));
   }

   @Override
   public boolean i() {
      return this.g_().M;
   }

   @Override
   public boolean R_() {
      return this.g_().N;
   }

   @Override
   public int au() {
      return this.g_().O;
   }

   @Override
   public int ax() {
      return this.g_().L;
   }

   protected boolean be() {
      boolean _snowman = false;

      for (int _snowmanx = 0; !_snowman && _snowmanx <= 2; _snowmanx++) {
         if (_snowmanx > 0) {
            j.warn("Encountered a problem while converting the user banlist, retrying in a few seconds");
            this.bo();
         }

         _snowman = act.a((MinecraftServer)this);
      }

      boolean _snowmanx = false;

      for (int var7 = 0; !_snowmanx && var7 <= 2; var7++) {
         if (var7 > 0) {
            j.warn("Encountered a problem while converting the ip banlist, retrying in a few seconds");
            this.bo();
         }

         _snowmanx = act.b(this);
      }

      boolean _snowmanxx = false;

      for (int var8 = 0; !_snowmanxx && var8 <= 2; var8++) {
         if (var8 > 0) {
            j.warn("Encountered a problem while converting the op list, retrying in a few seconds");
            this.bo();
         }

         _snowmanxx = act.c(this);
      }

      boolean _snowmanxxx = false;

      for (int var9 = 0; !_snowmanxxx && var9 <= 2; var9++) {
         if (var9 > 0) {
            j.warn("Encountered a problem while converting the whitelist, retrying in a few seconds");
            this.bo();
         }

         _snowmanxxx = act.d(this);
      }

      boolean _snowmanxxxx = false;

      for (int var10 = 0; !_snowmanxxxx && var10 <= 2; var10++) {
         if (var10 > 0) {
            j.warn("Encountered a problem while converting the player save files, retrying in a few seconds");
            this.bo();
         }

         _snowmanxxxx = act.a(this);
      }

      return _snowman || _snowmanx || _snowmanxx || _snowmanxxx || _snowmanxxxx;
   }

   private void bo() {
      try {
         Thread.sleep(5000L);
      } catch (InterruptedException var2) {
      }
   }

   public long bf() {
      return this.g_().H;
   }

   @Override
   public String j_() {
      return "";
   }

   @Override
   public String a(String var1) {
      this.n.d();
      this.g(() -> this.aD().a(this.n.f(), _snowman));
      return this.n.e();
   }

   public void j(boolean var1) {
      this.p.a(var2 -> var2.V.a(this.aY(), _snowman));
   }

   @Override
   public void t() {
      super.t();
      x.h();
   }

   @Override
   public boolean a(GameProfile var1) {
      return false;
   }

   @Override
   public int b(int var1) {
      return this.g_().S * _snowman / 100;
   }

   @Override
   public String k_() {
      return this.d.a();
   }

   @Override
   public boolean aV() {
      return this.p.a().P;
   }

   @Nullable
   @Override
   public abc a(aah var1) {
      return this.r != null ? this.r.a(_snowman.eA()) : null;
   }
}
