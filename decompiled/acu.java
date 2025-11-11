import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import com.mojang.serialization.Dynamic;
import io.netty.buffer.Unpooled;
import java.io.File;
import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class acu {
   public static final File b = new File("banned-players.json");
   public static final File c = new File("banned-ips.json");
   public static final File d = new File("ops.json");
   public static final File e = new File("whitelist.json");
   private static final Logger a = LogManager.getLogger();
   private static final SimpleDateFormat g = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
   private final MinecraftServer h;
   private final List<aah> i = Lists.newArrayList();
   private final Map<UUID, aah> j = Maps.newHashMap();
   private final acz k = new acz(b);
   private final acr l = new acr(c);
   private final acv m = new acv(d);
   private final adb n = new adb(e);
   private final Map<UUID, adw> o = Maps.newHashMap();
   private final Map<UUID, vt> p = Maps.newHashMap();
   private final cyk q;
   private boolean r;
   private final gn.b s;
   protected final int f;
   private int t;
   private bru u;
   private boolean v;
   private int w;

   public acu(MinecraftServer var1, gn.b var2, cyk var3, int var4) {
      this.h = _snowman;
      this.s = _snowman;
      this.f = _snowman;
      this.q = _snowman;
   }

   public void a(nd var1, aah var2) {
      GameProfile _snowman = _snowman.eA();
      acq _snowmanx = this.h.ar();
      GameProfile _snowmanxx = _snowmanx.a(_snowman.getId());
      String _snowmanxxx = _snowmanxx == null ? _snowman.getName() : _snowmanxx.getName();
      _snowmanx.a(_snowman);
      md _snowmanxxxx = this.a(_snowman);
      vj<brx> _snowmanxxxxx = _snowmanxxxx != null ? chd.a(new Dynamic(mo.a, _snowmanxxxx.c("Dimension"))).resultOrPartial(a::error).orElse(brx.g) : brx.g;
      aag _snowmanxxxxxx = this.h.a(_snowmanxxxxx);
      aag _snowmanxxxxxxx;
      if (_snowmanxxxxxx == null) {
         a.warn("Unknown respawn dimension {}, defaulting to overworld", _snowmanxxxxx);
         _snowmanxxxxxxx = this.h.E();
      } else {
         _snowmanxxxxxxx = _snowmanxxxxxx;
      }

      _snowman.a_(_snowmanxxxxxxx);
      _snowman.d.a((aag)_snowman.l);
      String _snowmanxxxxxxxx = "local";
      if (_snowman.c() != null) {
         _snowmanxxxxxxxx = _snowman.c().toString();
      }

      a.info("{}[{}] logged in with entity id {} at ({}, {}, {})", _snowman.R().getString(), _snowmanxxxxxxxx, _snowman.Y(), _snowman.cD(), _snowman.cE(), _snowman.cH());
      cyd _snowmanxxxxxxxxx = _snowmanxxxxxxx.h();
      this.a(_snowman, null, _snowmanxxxxxxx);
      aay _snowmanxxxxxxxxxx = new aay(this.h, _snowman, _snowman);
      brt _snowmanxxxxxxxxxxx = _snowmanxxxxxxx.V();
      boolean _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.b(brt.z);
      boolean _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.b(brt.o);
      _snowmanxxxxxxxxxx.a(
         new px(
            _snowman.Y(),
            _snowman.d.b(),
            _snowman.d.c(),
            bsx.a(_snowmanxxxxxxx.C()),
            _snowmanxxxxxxxxx.n(),
            this.h.F(),
            this.s,
            _snowmanxxxxxxx.k(),
            _snowmanxxxxxxx.Y(),
            this.n(),
            this.t,
            _snowmanxxxxxxxxxxxxx,
            !_snowmanxxxxxxxxxxxx,
            _snowmanxxxxxxx.ab(),
            _snowmanxxxxxxx.B()
         )
      );
      _snowmanxxxxxxxxxx.a(new pk(pk.a, new nf(Unpooled.buffer()).a(this.c().getServerModName())));
      _snowmanxxxxxxxxxx.a(new pa(_snowmanxxxxxxxxx.s(), _snowmanxxxxxxxxx.t()));
      _snowmanxxxxxxxxxx.a(new qg(_snowman.bC));
      _snowmanxxxxxxxxxx.a(new qv(_snowman.bm.d));
      _snowmanxxxxxxxxxx.a(new rw(this.h.aF().b()));
      _snowmanxxxxxxxxxx.a(new rx(this.h.aG()));
      this.d(_snowman);
      _snowman.A().c();
      _snowman.B().a(_snowman);
      this.a(_snowmanxxxxxxx.o_(), _snowman);
      this.h.at();
      nx _snowmanxxxxxxxxxxxxxx;
      if (_snowman.eA().getName().equalsIgnoreCase(_snowmanxxx)) {
         _snowmanxxxxxxxxxxxxxx = new of("multiplayer.player.joined", _snowman.d());
      } else {
         _snowmanxxxxxxxxxxxxxx = new of("multiplayer.player.joined.renamed", _snowman.d(), _snowmanxxx);
      }

      this.a(_snowmanxxxxxxxxxxxxxx.a(k.o), no.b, x.b);
      _snowmanxxxxxxxxxx.a(_snowman.cD(), _snowman.cE(), _snowman.cH(), _snowman.p, _snowman.q);
      this.i.add(_snowman);
      this.j.put(_snowman.bS(), _snowman);
      this.a(new qi(qi.a.a, _snowman));

      for (int _snowmanxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxx < this.i.size(); _snowmanxxxxxxxxxxxxxxx++) {
         _snowman.b.a(new qi(qi.a.a, this.i.get(_snowmanxxxxxxxxxxxxxxx)));
      }

      _snowmanxxxxxxx.c(_snowman);
      this.h.aM().a(_snowman);
      this.a(_snowman, _snowmanxxxxxxx);
      if (!this.h.S().isEmpty()) {
         _snowman.a(this.h.S(), this.h.T());
      }

      for (apu _snowmanxxxxxxxxxxxxxxx : _snowman.dh()) {
         _snowmanxxxxxxxxxx.a(new rv(_snowman.Y(), _snowmanxxxxxxxxxxxxxxx));
      }

      if (_snowmanxxxx != null && _snowmanxxxx.c("RootVehicle", 10)) {
         md _snowmanxxxxxxxxxxxxxxx = _snowmanxxxx.p("RootVehicle");
         aqa _snowmanxxxxxxxxxxxxxxxx = aqe.a(_snowmanxxxxxxxxxxxxxxx.p("Entity"), _snowmanxxxxxxx, var1x -> !_snowman.d(var1x) ? null : var1x);
         if (_snowmanxxxxxxxxxxxxxxxx != null) {
            UUID _snowmanxxxxxxxxxxxxxxxxx;
            if (_snowmanxxxxxxxxxxxxxxx.b("Attach")) {
               _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx.a("Attach");
            } else {
               _snowmanxxxxxxxxxxxxxxxxx = null;
            }

            if (_snowmanxxxxxxxxxxxxxxxx.bS().equals(_snowmanxxxxxxxxxxxxxxxxx)) {
               _snowman.a(_snowmanxxxxxxxxxxxxxxxx, true);
            } else {
               for (aqa _snowmanxxxxxxxxxxxxxxxxxx : _snowmanxxxxxxxxxxxxxxxx.co()) {
                  if (_snowmanxxxxxxxxxxxxxxxxxx.bS().equals(_snowmanxxxxxxxxxxxxxxxxx)) {
                     _snowman.a(_snowmanxxxxxxxxxxxxxxxxxx, true);
                     break;
                  }
               }
            }

            if (!_snowman.br()) {
               a.warn("Couldn't reattach entity to player");
               _snowmanxxxxxxx.i(_snowmanxxxxxxxxxxxxxxxx);

               for (aqa _snowmanxxxxxxxxxxxxxxxxxxx : _snowmanxxxxxxxxxxxxxxxx.co()) {
                  _snowmanxxxxxxx.i(_snowmanxxxxxxxxxxxxxxxxxxx);
               }
            }
         }
      }

      _snowman.f();
   }

   protected void a(wa var1, aah var2) {
      Set<ddk> _snowman = Sets.newHashSet();

      for (ddl _snowmanx : _snowman.g()) {
         _snowman.b.a(new ri(_snowmanx, 0));
      }

      for (int _snowmanx = 0; _snowmanx < 19; _snowmanx++) {
         ddk _snowmanxx = _snowman.a(_snowmanx);
         if (_snowmanxx != null && !_snowman.contains(_snowmanxx)) {
            for (oj<?> _snowmanxxx : _snowman.d(_snowmanxx)) {
               _snowman.b.a(_snowmanxxx);
            }

            _snowman.add(_snowmanxx);
         }
      }
   }

   public void a(aag var1) {
      _snowman.f().a(new cfs() {
         @Override
         public void a(cfu var1, double var2) {
            acu.this.a(new qt(_snowman, qt.a.a));
         }

         @Override
         public void a(cfu var1, double var2, double var4, long var6) {
            acu.this.a(new qt(_snowman, qt.a.b));
         }

         @Override
         public void a(cfu var1, double var2, double var4) {
            acu.this.a(new qt(_snowman, qt.a.c));
         }

         @Override
         public void a(cfu var1, int var2) {
            acu.this.a(new qt(_snowman, qt.a.e));
         }

         @Override
         public void b(cfu var1, int var2) {
            acu.this.a(new qt(_snowman, qt.a.f));
         }

         @Override
         public void b(cfu var1, double var2) {
         }

         @Override
         public void c(cfu var1, double var2) {
         }
      });
   }

   @Nullable
   public md a(aah var1) {
      md _snowman = this.h.aX().y();
      md _snowmanx;
      if (_snowman.R().getString().equals(this.h.N()) && _snowman != null) {
         _snowmanx = _snowman;
         _snowman.f(_snowman);
         a.debug("loading single player");
      } else {
         _snowmanx = this.q.b(_snowman);
      }

      return _snowmanx;
   }

   protected void b(aah var1) {
      this.q.a(_snowman);
      adw _snowman = this.o.get(_snowman.bS());
      if (_snowman != null) {
         _snowman.a();
      }

      vt _snowmanx = this.p.get(_snowman.bS());
      if (_snowmanx != null) {
         _snowmanx.b();
      }
   }

   public void c(aah var1) {
      aag _snowman = _snowman.u();
      _snowman.a(aea.j);
      this.b(_snowman);
      if (_snowman.br()) {
         aqa _snowmanx = _snowman.cr();
         if (_snowmanx.cq()) {
            a.debug("Removing player mount");
            _snowman.l();
            _snowman.i(_snowmanx);
            _snowmanx.y = true;

            for (aqa _snowmanxx : _snowmanx.co()) {
               _snowman.i(_snowmanxx);
               _snowmanxx.y = true;
            }

            _snowman.d(_snowman.V, _snowman.X).s();
         }
      }

      _snowman.V();
      _snowman.e(_snowman);
      _snowman.J().a();
      this.i.remove(_snowman);
      this.h.aM().b(_snowman);
      UUID _snowmanx = _snowman.bS();
      aah _snowmanxx = this.j.get(_snowmanx);
      if (_snowmanxx == _snowman) {
         this.j.remove(_snowmanx);
         this.o.remove(_snowmanx);
         this.p.remove(_snowmanx);
      }

      this.a(new qi(qi.a.e, _snowman));
   }

   @Nullable
   public nr a(SocketAddress var1, GameProfile var2) {
      if (this.k.a(_snowman)) {
         ada _snowman = this.k.b(_snowman);
         nx _snowmanx = new of("multiplayer.disconnect.banned.reason", _snowman.d());
         if (_snowman.c() != null) {
            _snowmanx.a(new of("multiplayer.disconnect.banned.expiration", g.format(_snowman.c())));
         }

         return _snowmanx;
      } else if (!this.e(_snowman)) {
         return new of("multiplayer.disconnect.not_whitelisted");
      } else if (this.l.a(_snowman)) {
         acs _snowman = this.l.b(_snowman);
         nx _snowmanx = new of("multiplayer.disconnect.banned_ip.reason", _snowman.d());
         if (_snowman.c() != null) {
            _snowmanx.a(new of("multiplayer.disconnect.banned_ip.expiration", g.format(_snowman.c())));
         }

         return _snowmanx;
      } else {
         return this.i.size() >= this.f && !this.f(_snowman) ? new of("multiplayer.disconnect.server_full") : null;
      }
   }

   public aah g(GameProfile var1) {
      UUID _snowman = bfw.a(_snowman);
      List<aah> _snowmanx = Lists.newArrayList();

      for (int _snowmanxx = 0; _snowmanxx < this.i.size(); _snowmanxx++) {
         aah _snowmanxxx = this.i.get(_snowmanxx);
         if (_snowmanxxx.bS().equals(_snowman)) {
            _snowmanx.add(_snowmanxxx);
         }
      }

      aah _snowmanxxx = this.j.get(_snowman.getId());
      if (_snowmanxxx != null && !_snowmanx.contains(_snowmanxxx)) {
         _snowmanx.add(_snowmanxxx);
      }

      for (aah _snowmanxxxx : _snowmanx) {
         _snowmanxxxx.b.b(new of("multiplayer.disconnect.duplicate_login"));
      }

      aag _snowmanxxxx = this.h.E();
      aai _snowmanxxxxx;
      if (this.h.R()) {
         _snowmanxxxxx = new zx(_snowmanxxxx);
      } else {
         _snowmanxxxxx = new aai(_snowmanxxxx);
      }

      return new aah(this.h, _snowmanxxxx, _snowman, _snowmanxxxxx);
   }

   public aah a(aah var1, boolean var2) {
      this.i.remove(_snowman);
      _snowman.u().e(_snowman);
      fx _snowman = _snowman.K();
      float _snowmanx = _snowman.L();
      boolean _snowmanxx = _snowman.N();
      aag _snowmanxxx = this.h.a(_snowman.M());
      Optional<dcn> _snowmanxxxx;
      if (_snowmanxxx != null && _snowman != null) {
         _snowmanxxxx = bfw.a(_snowmanxxx, _snowman, _snowmanx, _snowmanxx, _snowman);
      } else {
         _snowmanxxxx = Optional.empty();
      }

      aag _snowmanxxxxx = _snowmanxxx != null && _snowmanxxxx.isPresent() ? _snowmanxxx : this.h.E();
      aai _snowmanxxxxxx;
      if (this.h.R()) {
         _snowmanxxxxxx = new zx(_snowmanxxxxx);
      } else {
         _snowmanxxxxxx = new aai(_snowmanxxxxx);
      }

      aah _snowmanxxxxxxx = new aah(this.h, _snowmanxxxxx, _snowman.eA(), _snowmanxxxxxx);
      _snowmanxxxxxxx.b = _snowman.b;
      _snowmanxxxxxxx.a(_snowman, _snowman);
      _snowmanxxxxxxx.e(_snowman.Y());
      _snowmanxxxxxxx.a(_snowman.dV());

      for (String _snowmanxxxxxxxx : _snowman.Z()) {
         _snowmanxxxxxxx.a(_snowmanxxxxxxxx);
      }

      this.a(_snowmanxxxxxxx, _snowman, _snowmanxxxxx);
      boolean _snowmanxxxxxxxx = false;
      if (_snowmanxxxx.isPresent()) {
         ceh _snowmanxxxxxxxxx = _snowmanxxxxx.d_(_snowman);
         boolean _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx.a(bup.nj);
         dcn _snowmanxxxxxxxxxxx = _snowmanxxxx.get();
         float _snowmanxxxxxxxxxxxx;
         if (!_snowmanxxxxxxxxx.a(aed.L) && !_snowmanxxxxxxxxxx) {
            _snowmanxxxxxxxxxxxx = _snowmanx;
         } else {
            dcn _snowmanxxxxxxxxxxxxx = dcn.c(_snowman).d(_snowmanxxxxxxxxxxx).d();
            _snowmanxxxxxxxxxxxx = (float)afm.g(afm.d(_snowmanxxxxxxxxxxxxx.d, _snowmanxxxxxxxxxxxxx.b) * 180.0F / (float)Math.PI - 90.0);
         }

         _snowmanxxxxxxx.b(_snowmanxxxxxxxxxxx.b, _snowmanxxxxxxxxxxx.c, _snowmanxxxxxxxxxxx.d, _snowmanxxxxxxxxxxxx, 0.0F);
         _snowmanxxxxxxx.a(_snowmanxxxxx.Y(), _snowman, _snowmanx, _snowmanxx, false);
         _snowmanxxxxxxxx = !_snowman && _snowmanxxxxxxxxxx;
      } else if (_snowman != null) {
         _snowmanxxxxxxx.b.a(new pq(pq.a, 0.0F));
      }

      while (!_snowmanxxxxx.k(_snowmanxxxxxxx) && _snowmanxxxxxxx.cE() < 256.0) {
         _snowmanxxxxxxx.d(_snowmanxxxxxxx.cD(), _snowmanxxxxxxx.cE() + 1.0, _snowmanxxxxxxx.cH());
      }

      cyd _snowmanxxxxxxxxx = _snowmanxxxxxxx.l.h();
      _snowmanxxxxxxx.b.a(new qp(_snowmanxxxxxxx.l.k(), _snowmanxxxxxxx.l.Y(), bsx.a(_snowmanxxxxxxx.u().C()), _snowmanxxxxxxx.d.b(), _snowmanxxxxxxx.d.c(), _snowmanxxxxxxx.u().ab(), _snowmanxxxxxxx.u().B(), _snowman));
      _snowmanxxxxxxx.b.a(_snowmanxxxxxxx.cD(), _snowmanxxxxxxx.cE(), _snowmanxxxxxxx.cH(), _snowmanxxxxxxx.p, _snowmanxxxxxxx.q);
      _snowmanxxxxxxx.b.a(new qy(_snowmanxxxxx.u(), _snowmanxxxxx.v()));
      _snowmanxxxxxxx.b.a(new pa(_snowmanxxxxxxxxx.s(), _snowmanxxxxxxxxx.t()));
      _snowmanxxxxxxx.b.a(new re(_snowmanxxxxxxx.bF, _snowmanxxxxxxx.bE, _snowmanxxxxxxx.bD));
      this.a(_snowmanxxxxxxx, _snowmanxxxxx);
      this.d(_snowmanxxxxxxx);
      _snowmanxxxxx.d(_snowmanxxxxxxx);
      this.i.add(_snowmanxxxxxxx);
      this.j.put(_snowmanxxxxxxx.bS(), _snowmanxxxxxxx);
      _snowmanxxxxxxx.f();
      _snowmanxxxxxxx.c(_snowmanxxxxxxx.dk());
      if (_snowmanxxxxxxxx) {
         _snowmanxxxxxxx.b.a(new rn(adq.mz, adr.e, (double)_snowman.u(), (double)_snowman.v(), (double)_snowman.w(), 1.0F, 1.0F));
      }

      return _snowmanxxxxxxx;
   }

   public void d(aah var1) {
      GameProfile _snowman = _snowman.eA();
      int _snowmanx = this.h.b(_snowman);
      this.a(_snowman, _snowmanx);
   }

   public void d() {
      if (++this.w > 600) {
         this.a(new qi(qi.a.c, this.i));
         this.w = 0;
      }
   }

   public void a(oj<?> var1) {
      for (int _snowman = 0; _snowman < this.i.size(); _snowman++) {
         this.i.get(_snowman).b.a(_snowman);
      }
   }

   public void a(oj<?> var1, vj<brx> var2) {
      for (int _snowman = 0; _snowman < this.i.size(); _snowman++) {
         aah _snowmanx = this.i.get(_snowman);
         if (_snowmanx.l.Y() == _snowman) {
            _snowmanx.b.a(_snowman);
         }
      }
   }

   public void a(bfw var1, nr var2) {
      ddp _snowman = _snowman.bG();
      if (_snowman != null) {
         for (String _snowmanx : _snowman.g()) {
            aah _snowmanxx = this.a(_snowmanx);
            if (_snowmanxx != null && _snowmanxx != _snowman) {
               _snowmanxx.a(_snowman, _snowman.bS());
            }
         }
      }
   }

   public void b(bfw var1, nr var2) {
      ddp _snowman = _snowman.bG();
      if (_snowman == null) {
         this.a(_snowman, no.b, _snowman.bS());
      } else {
         for (int _snowmanx = 0; _snowmanx < this.i.size(); _snowmanx++) {
            aah _snowmanxx = this.i.get(_snowmanx);
            if (_snowmanxx.bG() != _snowman) {
               _snowmanxx.a(_snowman, _snowman.bS());
            }
         }
      }
   }

   public String[] e() {
      String[] _snowman = new String[this.i.size()];

      for (int _snowmanx = 0; _snowmanx < this.i.size(); _snowmanx++) {
         _snowman[_snowmanx] = this.i.get(_snowmanx).eA().getName();
      }

      return _snowman;
   }

   public acz f() {
      return this.k;
   }

   public acr g() {
      return this.l;
   }

   public void a(GameProfile var1) {
      this.m.a(new acw(_snowman, this.h.g(), this.m.b(_snowman)));
      aah _snowman = this.a(_snowman.getId());
      if (_snowman != null) {
         this.d(_snowman);
      }
   }

   public void b(GameProfile var1) {
      this.m.c(_snowman);
      aah _snowman = this.a(_snowman.getId());
      if (_snowman != null) {
         this.d(_snowman);
      }
   }

   private void a(aah var1, int var2) {
      if (_snowman.b != null) {
         byte _snowman;
         if (_snowman <= 0) {
            _snowman = 24;
         } else if (_snowman >= 4) {
            _snowman = 28;
         } else {
            _snowman = (byte)(24 + _snowman);
         }

         _snowman.b.a(new pn(_snowman, _snowman));
      }

      this.h.aD().a(_snowman);
   }

   public boolean e(GameProfile var1) {
      return !this.r || this.m.d(_snowman) || this.n.d(_snowman);
   }

   public boolean h(GameProfile var1) {
      return this.m.d(_snowman) || this.h.a(_snowman) && this.h.aX().o() || this.v;
   }

   @Nullable
   public aah a(String var1) {
      for (aah _snowman : this.i) {
         if (_snowman.eA().getName().equalsIgnoreCase(_snowman)) {
            return _snowman;
         }
      }

      return null;
   }

   public void a(@Nullable bfw var1, double var2, double var4, double var6, double var8, vj<brx> var10, oj<?> var11) {
      for (int _snowman = 0; _snowman < this.i.size(); _snowman++) {
         aah _snowmanx = this.i.get(_snowman);
         if (_snowmanx != _snowman && _snowmanx.l.Y() == _snowman) {
            double _snowmanxx = _snowman - _snowmanx.cD();
            double _snowmanxxx = _snowman - _snowmanx.cE();
            double _snowmanxxxx = _snowman - _snowmanx.cH();
            if (_snowmanxx * _snowmanxx + _snowmanxxx * _snowmanxxx + _snowmanxxxx * _snowmanxxxx < _snowman * _snowman) {
               _snowmanx.b.a(_snowman);
            }
         }
      }
   }

   public void h() {
      for (int _snowman = 0; _snowman < this.i.size(); _snowman++) {
         this.b(this.i.get(_snowman));
      }
   }

   public adb i() {
      return this.n;
   }

   public String[] j() {
      return this.n.a();
   }

   public acv k() {
      return this.m;
   }

   public String[] l() {
      return this.m.a();
   }

   public void a() {
   }

   public void a(aah var1, aag var2) {
      cfu _snowman = this.h.E().f();
      _snowman.b.a(new qt(_snowman, qt.a.d));
      _snowman.b.a(new rk(_snowman.T(), _snowman.U(), _snowman.V().b(brt.j)));
      _snowman.b.a(new qy(_snowman.u(), _snowman.v()));
      if (_snowman.X()) {
         _snowman.b.a(new pq(pq.b, 0.0F));
         _snowman.b.a(new pq(pq.h, _snowman.d(1.0F)));
         _snowman.b.a(new pq(pq.i, _snowman.b(1.0F)));
      }
   }

   public void e(aah var1) {
      _snowman.a(_snowman.bo);
      _snowman.r();
      _snowman.b.a(new qv(_snowman.bm.d));
   }

   public int m() {
      return this.i.size();
   }

   public int n() {
      return this.f;
   }

   public boolean o() {
      return this.r;
   }

   public void a(boolean var1) {
      this.r = _snowman;
   }

   public List<aah> b(String var1) {
      List<aah> _snowman = Lists.newArrayList();

      for (aah _snowmanx : this.i) {
         if (_snowmanx.v().equals(_snowman)) {
            _snowman.add(_snowmanx);
         }
      }

      return _snowman;
   }

   public int p() {
      return this.t;
   }

   public MinecraftServer c() {
      return this.h;
   }

   public md q() {
      return null;
   }

   public void a(bru var1) {
      this.u = _snowman;
   }

   private void a(aah var1, @Nullable aah var2, aag var3) {
      if (_snowman != null) {
         _snowman.d.a(_snowman.d.b(), _snowman.d.c());
      } else if (this.u != null) {
         _snowman.d.a(this.u, bru.a);
      }

      _snowman.d.b(_snowman.l().aX().m());
   }

   public void b(boolean var1) {
      this.v = _snowman;
   }

   public void r() {
      for (int _snowman = 0; _snowman < this.i.size(); _snowman++) {
         this.i.get(_snowman).b.b(new of("multiplayer.disconnect.server_shutdown"));
      }
   }

   public void a(nr var1, no var2, UUID var3) {
      this.h.a(_snowman, _snowman);
      this.a(new pb(_snowman, _snowman, _snowman));
   }

   public adw a(bfw var1) {
      UUID _snowman = _snowman.bS();
      adw _snowmanx = _snowman == null ? null : this.o.get(_snowman);
      if (_snowmanx == null) {
         File _snowmanxx = this.h.a(cye.b).toFile();
         File _snowmanxxx = new File(_snowmanxx, _snowman + ".json");
         if (!_snowmanxxx.exists()) {
            File _snowmanxxxx = new File(_snowmanxx, _snowman.R().getString() + ".json");
            if (_snowmanxxxx.exists() && _snowmanxxxx.isFile()) {
               _snowmanxxxx.renameTo(_snowmanxxx);
            }
         }

         _snowmanx = new adw(this.h, _snowmanxxx);
         this.o.put(_snowman, _snowmanx);
      }

      return _snowmanx;
   }

   public vt f(aah var1) {
      UUID _snowman = _snowman.bS();
      vt _snowmanx = this.p.get(_snowman);
      if (_snowmanx == null) {
         File _snowmanxx = this.h.a(cye.a).toFile();
         File _snowmanxxx = new File(_snowmanxx, _snowman + ".json");
         _snowmanx = new vt(this.h.az(), this, this.h.aA(), _snowmanxxx, _snowman);
         this.p.put(_snowman, _snowmanx);
      }

      _snowmanx.a(_snowman);
      return _snowmanx;
   }

   public void a(int var1) {
      this.t = _snowman;
      this.a(new qx(_snowman));

      for (aag _snowman : this.h.G()) {
         if (_snowman != null) {
            _snowman.i().a(_snowman);
         }
      }
   }

   public List<aah> s() {
      return this.i;
   }

   @Nullable
   public aah a(UUID var1) {
      return this.j.get(_snowman);
   }

   public boolean f(GameProfile var1) {
      return false;
   }

   public void t() {
      for (vt _snowman : this.p.values()) {
         _snowman.a(this.h.aA());
      }

      this.a(new rx(this.h.aG()));
      rw _snowman = new rw(this.h.aF().b());

      for (aah _snowmanx : this.i) {
         _snowmanx.b.a(_snowman);
         _snowmanx.B().a(_snowmanx);
      }
   }

   public boolean u() {
      return this.v;
   }
}
