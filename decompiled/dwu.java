import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import io.netty.buffer.Unpooled;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import net.minecraft.client.ClientBrandRetriever;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dwu implements om {
   private static final Logger a = LogManager.getLogger();
   private static final nr b = new of("disconnect.lost");
   private final nd c;
   private final GameProfile d;
   private final dot e;
   private djz f;
   private dwt g;
   private dwt.a h;
   private boolean i;
   private final Map<UUID, dwx> j = Maps.newHashMap();
   private final dwq k;
   private final dwv l;
   private aen m = aen.a;
   private final djq n = new djq(this);
   private int o = 3;
   private final Random p = new Random();
   private CommandDispatcher<dd> q = new CommandDispatcher();
   private final bor r = new bor();
   private final UUID s = UUID.randomUUID();
   private Set<vj<brx>> t;
   private gn u = gn.b();

   public dwu(djz var1, dot var2, nd var3, GameProfile var4) {
      this.f = _snowman;
      this.e = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.k = new dwq(_snowman);
      this.l = new dwv(this, _snowman);
   }

   public dwv b() {
      return this.l;
   }

   public void c() {
      this.g = null;
   }

   public bor d() {
      return this.r;
   }

   @Override
   public void a(px var1) {
      ol.a(_snowman, this, this.f);
      this.f.q = new dww(this.f, this);
      if (!this.c.d()) {
         aek.a();
      }

      ArrayList<vj<brx>> _snowman = Lists.newArrayList(_snowman.g());
      Collections.shuffle(_snowman);
      this.t = Sets.newLinkedHashSet(_snowman);
      this.u = _snowman.h();
      vj<brx> _snowmanx = _snowman.j();
      chd _snowmanxx = _snowman.i();
      this.o = _snowman.l();
      boolean _snowmanxxx = _snowman.o();
      boolean _snowmanxxxx = _snowman.p();
      dwt.a _snowmanxxxxx = new dwt.a(aor.c, _snowman.d(), _snowmanxxxx);
      this.h = _snowmanxxxxx;
      this.g = new dwt(this, _snowmanxxxxx, _snowmanx, _snowmanxx, this.o, this.f::au, this.f.e, _snowmanxxx, _snowman.c());
      this.f.a(this.g);
      if (this.f.s == null) {
         this.f.s = this.f.q.a(this.g, new aeb(), new djm());
         this.f.s.p = -180.0F;
         if (this.f.H() != null) {
            this.f.H().a(this.f.s.bS());
         }
      }

      this.f.i.a();
      this.f.s.ac();
      int _snowmanxxxxxx = _snowman.b();
      this.g.a(_snowmanxxxxxx, (dzj)this.f.s);
      this.f.s.f = new dzl(this.f.k);
      this.f.q.a(this.f.s);
      this.f.t = this.f.s;
      this.f.a(new dos());
      this.f.s.e(_snowmanxxxxxx);
      this.f.s.r(_snowman.m());
      this.f.s.b(_snowman.n());
      this.f.q.b(_snowman.e());
      this.f.q.a(_snowman.f());
      this.f.k.c();
      this.c.a(new sm(sm.a, new nf(Unpooled.buffer()).a(ClientBrandRetriever.getClientModName())));
      this.f.ax().a();
   }

   @Override
   public void a(on var1) {
      ol.a(_snowman, this, this.f);
      double _snowman = _snowman.d();
      double _snowmanx = _snowman.e();
      double _snowmanxx = _snowman.f();
      aqe<?> _snowmanxxx = _snowman.l();
      aqa _snowmanxxxx;
      if (_snowmanxxx == aqe.U) {
         _snowmanxxxx = new bhq(this.g, _snowman, _snowmanx, _snowmanxx);
      } else if (_snowmanxxx == aqe.W) {
         _snowmanxxxx = new bhs(this.g, _snowman, _snowmanx, _snowmanxx);
      } else if (_snowmanxxx == aqe.Z) {
         _snowmanxxxx = new bhv(this.g, _snowman, _snowmanx, _snowmanxx);
      } else if (_snowmanxxx == aqe.Y) {
         _snowmanxxxx = new bhu(this.g, _snowman, _snowmanx, _snowmanxx);
      } else if (_snowmanxxx == aqe.X) {
         _snowmanxxxx = new bht(this.g, _snowman, _snowmanx, _snowmanxx);
      } else if (_snowmanxxx == aqe.V) {
         _snowmanxxxx = new bhr(this.g, _snowman, _snowmanx, _snowmanxx);
      } else if (_snowmanxxx == aqe.T) {
         _snowmanxxxx = new bhp(this.g, _snowman, _snowmanx, _snowmanxx);
      } else if (_snowmanxxx == aqe.bd) {
         aqa _snowmanxxxxx = this.g.a(_snowman.m());
         if (_snowmanxxxxx instanceof bfw) {
            _snowmanxxxx = new bgi(this.g, (bfw)_snowmanxxxxx, _snowman, _snowmanx, _snowmanxx);
         } else {
            _snowmanxxxx = null;
         }
      } else if (_snowmanxxx == aqe.c) {
         _snowmanxxxx = new bgc(this.g, _snowman, _snowmanx, _snowmanxx);
         aqa _snowmanxxxxx = this.g.a(_snowman.m());
         if (_snowmanxxxxx != null) {
            ((bga)_snowmanxxxx).b(_snowmanxxxxx);
         }
      } else if (_snowmanxxx == aqe.aB) {
         _snowmanxxxx = new bgr(this.g, _snowman, _snowmanx, _snowmanxx);
         aqa _snowmanxxxxx = this.g.a(_snowman.m());
         if (_snowmanxxxxx != null) {
            ((bga)_snowmanxxxx).b(_snowmanxxxxx);
         }
      } else if (_snowmanxxx == aqe.aK) {
         _snowmanxxxx = new bgy(this.g, _snowman, _snowmanx, _snowmanxx);
         aqa _snowmanxxxxx = this.g.a(_snowman.m());
         if (_snowmanxxxxx != null) {
            ((bga)_snowmanxxxx).b(_snowmanxxxxx);
         }
      } else if (_snowmanxxx == aqe.aA) {
         _snowmanxxxx = new bgq(this.g, _snowman, _snowmanx, _snowmanxx);
      } else if (_snowmanxxx == aqe.R) {
         _snowmanxxxx = new bgl(this.g, _snowman, _snowmanx, _snowmanxx, _snowman.g(), _snowman.h(), _snowman.i());
      } else if (_snowmanxxx == aqe.M) {
         _snowmanxxxx = new bcp(this.g, new fx(_snowman, _snowmanx, _snowmanxx), gc.a(_snowman.m()));
      } else if (_snowmanxxx == aqe.O) {
         _snowmanxxxx = new bcq(this.g, new fx(_snowman, _snowmanx, _snowmanxx));
      } else if (_snowmanxxx == aqe.aH) {
         _snowmanxxxx = new bgv(this.g, _snowman, _snowmanx, _snowmanxx);
      } else if (_snowmanxxx == aqe.z) {
         _snowmanxxxx = new bgf(this.g, _snowman, _snowmanx, _snowmanxx);
      } else if (_snowmanxxx == aqe.B) {
         _snowmanxxxx = new bgh(this.g, _snowman, _snowmanx, _snowmanxx, bmb.b);
      } else if (_snowmanxxx == aqe.N) {
         _snowmanxxxx = new bgk(this.g, _snowman, _snowmanx, _snowmanxx, _snowman.g(), _snowman.h(), _snowman.i());
      } else if (_snowmanxxx == aqe.p) {
         _snowmanxxxx = new bgd(this.g, _snowman, _snowmanx, _snowmanxx, _snowman.g(), _snowman.h(), _snowman.i());
      } else if (_snowmanxxx == aqe.ay) {
         _snowmanxxxx = new bgp(this.g, _snowman, _snowmanx, _snowmanxx, _snowman.g(), _snowman.h(), _snowman.i());
      } else if (_snowmanxxx == aqe.aV) {
         _snowmanxxxx = new bgz(this.g, _snowman, _snowmanx, _snowmanxx, _snowman.g(), _snowman.h(), _snowman.i());
      } else if (_snowmanxxx == aqe.at) {
         _snowmanxxxx = new bgo(this.g, _snowman, _snowmanx, _snowmanxx, _snowman.g(), _snowman.h(), _snowman.i());
      } else if (_snowmanxxx == aqe.aG) {
         _snowmanxxxx = new bgu(this.g, _snowman, _snowmanx, _snowmanxx);
      } else if (_snowmanxxx == aqe.x) {
         _snowmanxxxx = new bge(this.g, _snowman, _snowmanx, _snowmanxx, 0.0F, 0, null);
      } else if (_snowmanxxx == aqe.aJ) {
         _snowmanxxxx = new bgx(this.g, _snowman, _snowmanx, _snowmanxx);
      } else if (_snowmanxxx == aqe.aI) {
         _snowmanxxxx = new bgw(this.g, _snowman, _snowmanx, _snowmanxx);
      } else if (_snowmanxxx == aqe.g) {
         _snowmanxxxx = new bhn(this.g, _snowman, _snowmanx, _snowmanxx);
      } else if (_snowmanxxx == aqe.am) {
         _snowmanxxxx = new bcw(this.g, _snowman, _snowmanx, _snowmanxx, null);
      } else if (_snowmanxxx == aqe.b) {
         _snowmanxxxx = new bcn(this.g, _snowman, _snowmanx, _snowmanxx);
      } else if (_snowmanxxx == aqe.s) {
         _snowmanxxxx = new bbq(this.g, _snowman, _snowmanx, _snowmanxx);
      } else if (_snowmanxxx == aqe.L) {
         _snowmanxxxx = new bcv(this.g, _snowman, _snowmanx, _snowmanxx);
      } else if (_snowmanxxx == aqe.A) {
         _snowmanxxxx = new bcu(this.g, _snowman, _snowmanx, _snowmanxx, buo.a(_snowman.m()));
      } else if (_snowmanxxx == aqe.a) {
         _snowmanxxxx = new apz(this.g, _snowman, _snowmanx, _snowmanxx);
      } else if (_snowmanxxx == aqe.P) {
         _snowmanxxxx = new aql(aqe.P, this.g);
      } else {
         _snowmanxxxx = null;
      }

      if (_snowmanxxxx != null) {
         int _snowmanxxxxx = _snowman.b();
         _snowmanxxxx.c(_snowman, _snowmanx, _snowmanxx);
         _snowmanxxxx.b(_snowman, _snowmanx, _snowmanxx);
         _snowmanxxxx.q = (float)(_snowman.j() * 360) / 256.0F;
         _snowmanxxxx.p = (float)(_snowman.k() * 360) / 256.0F;
         _snowmanxxxx.e(_snowmanxxxxx);
         _snowmanxxxx.a_(_snowman.c());
         this.g.a(_snowmanxxxxx, _snowmanxxxx);
         if (_snowmanxxxx instanceof bhl) {
            this.f.W().a((emt)(new emn((bhl)_snowmanxxxx)));
         }
      }
   }

   @Override
   public void a(oo var1) {
      ol.a(_snowman, this, this.f);
      double _snowman = _snowman.c();
      double _snowmanx = _snowman.d();
      double _snowmanxx = _snowman.e();
      aqa _snowmanxxx = new aqg(this.g, _snowman, _snowmanx, _snowmanxx, _snowman.f());
      _snowmanxxx.c(_snowman, _snowmanx, _snowmanxx);
      _snowmanxxx.p = 0.0F;
      _snowmanxxx.q = 0.0F;
      _snowmanxxx.e(_snowman.b());
      this.g.a(_snowman.b(), _snowmanxxx);
   }

   @Override
   public void a(oq var1) {
      ol.a(_snowman, this, this.f);
      bcs _snowman = new bcs(this.g, _snowman.d(), _snowman.e(), _snowman.f());
      _snowman.e(_snowman.b());
      _snowman.a_(_snowman.c());
      this.g.a(_snowman.b(), _snowman);
   }

   @Override
   public void a(rc var1) {
      ol.a(_snowman, this, this.f);
      aqa _snowman = this.g.a(_snowman.b());
      if (_snowman != null) {
         _snowman.k((double)_snowman.c() / 8000.0, (double)_snowman.d() / 8000.0, (double)_snowman.e() / 8000.0);
      }
   }

   @Override
   public void a(ra var1) {
      ol.a(_snowman, this, this.f);
      aqa _snowman = this.g.a(_snowman.c());
      if (_snowman != null && _snowman.b() != null) {
         _snowman.ab().a(_snowman.b());
      }
   }

   @Override
   public void a(or var1) {
      ol.a(_snowman, this, this.f);
      double _snowman = _snowman.d();
      double _snowmanx = _snowman.e();
      double _snowmanxx = _snowman.f();
      float _snowmanxxx = (float)(_snowman.g() * 360) / 256.0F;
      float _snowmanxxxx = (float)(_snowman.h() * 360) / 256.0F;
      int _snowmanxxxxx = _snowman.b();
      dzn _snowmanxxxxxx = new dzn(this.f.r, this.a(_snowman.c()).a());
      _snowmanxxxxxx.e(_snowmanxxxxx);
      _snowmanxxxxxx.g(_snowman, _snowmanx, _snowmanxx);
      _snowmanxxxxxx.c(_snowman, _snowmanx, _snowmanxx);
      _snowmanxxxxxx.a(_snowman, _snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx);
      this.g.a(_snowmanxxxxx, (dzj)_snowmanxxxxxx);
   }

   @Override
   public void a(rs var1) {
      ol.a(_snowman, this, this.f);
      aqa _snowman = this.g.a(_snowman.b());
      if (_snowman != null) {
         double _snowmanx = _snowman.c();
         double _snowmanxx = _snowman.d();
         double _snowmanxxx = _snowman.e();
         _snowman.c(_snowmanx, _snowmanxx, _snowmanxxx);
         if (!_snowman.cs()) {
            float _snowmanxxxx = (float)(_snowman.f() * 360) / 256.0F;
            float _snowmanxxxxx = (float)(_snowman.g() * 360) / 256.0F;
            _snowman.a(_snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, 3, true);
            _snowman.c(_snowman.h());
         }
      }
   }

   @Override
   public void a(qv var1) {
      ol.a(_snowman, this, this.f);
      if (bfv.d(_snowman.b())) {
         this.f.s.bm.d = _snowman.b();
      }
   }

   @Override
   public void a(qa var1) {
      ol.a(_snowman, this, this.f);
      aqa _snowman = _snowman.a(this.g);
      if (_snowman != null) {
         if (!_snowman.cs()) {
            if (_snowman.h()) {
               dcn _snowmanx = _snowman.a(_snowman.W());
               _snowman.a(_snowmanx);
               float _snowmanxx = _snowman.g() ? (float)(_snowman.e() * 360) / 256.0F : _snowman.p;
               float _snowmanxxx = _snowman.g() ? (float)(_snowman.f() * 360) / 256.0F : _snowman.q;
               _snowman.a(_snowmanx.a(), _snowmanx.b(), _snowmanx.c(), _snowmanxx, _snowmanxxx, 3, false);
            } else if (_snowman.g()) {
               float _snowmanx = (float)(_snowman.e() * 360) / 256.0F;
               float _snowmanxx = (float)(_snowman.f() * 360) / 256.0F;
               _snowman.a(_snowman.cD(), _snowman.cE(), _snowman.cH(), _snowmanx, _snowmanxx, 3, false);
            }

            _snowman.c(_snowman.i());
         }
      }
   }

   @Override
   public void a(qq var1) {
      ol.a(_snowman, this, this.f);
      aqa _snowman = _snowman.a(this.g);
      if (_snowman != null) {
         float _snowmanx = (float)(_snowman.b() * 360) / 256.0F;
         _snowman.a(_snowmanx, 3);
      }
   }

   @Override
   public void a(qm var1) {
      ol.a(_snowman, this, this.f);

      for (int _snowman = 0; _snowman < _snowman.b().length; _snowman++) {
         int _snowmanx = _snowman.b()[_snowman];
         this.g.d(_snowmanx);
      }
   }

   @Override
   public void a(qk var1) {
      ol.a(_snowman, this, this.f);
      bfw _snowman = this.f.s;
      dcn _snowmanx = _snowman.cC();
      boolean _snowmanxx = _snowman.h().contains(qk.a.a);
      boolean _snowmanxxx = _snowman.h().contains(qk.a.b);
      boolean _snowmanxxxx = _snowman.h().contains(qk.a.c);
      double _snowmanxxxxx;
      double _snowmanxxxxxx;
      if (_snowmanxx) {
         _snowmanxxxxx = _snowmanx.a();
         _snowmanxxxxxx = _snowman.cD() + _snowman.b();
         _snowman.D = _snowman.D + _snowman.b();
      } else {
         _snowmanxxxxx = 0.0;
         _snowmanxxxxxx = _snowman.b();
         _snowman.D = _snowmanxxxxxx;
      }

      double _snowmanxxxxxxx;
      double _snowmanxxxxxxxx;
      if (_snowmanxxx) {
         _snowmanxxxxxxx = _snowmanx.b();
         _snowmanxxxxxxxx = _snowman.cE() + _snowman.c();
         _snowman.E = _snowman.E + _snowman.c();
      } else {
         _snowmanxxxxxxx = 0.0;
         _snowmanxxxxxxxx = _snowman.c();
         _snowman.E = _snowmanxxxxxxxx;
      }

      double _snowmanxxxxxxxxx;
      double _snowmanxxxxxxxxxx;
      if (_snowmanxxxx) {
         _snowmanxxxxxxxxx = _snowmanx.c();
         _snowmanxxxxxxxxxx = _snowman.cH() + _snowman.d();
         _snowman.F = _snowman.F + _snowman.d();
      } else {
         _snowmanxxxxxxxxx = 0.0;
         _snowmanxxxxxxxxxx = _snowman.d();
         _snowman.F = _snowmanxxxxxxxxxx;
      }

      if (_snowman.K > 0 && _snowman.ct() != null) {
         _snowman.bf();
      }

      _snowman.o(_snowmanxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxxx);
      _snowman.m = _snowmanxxxxxx;
      _snowman.n = _snowmanxxxxxxxx;
      _snowman.o = _snowmanxxxxxxxxxx;
      _snowman.n(_snowmanxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxxx);
      float _snowmanxxxxxxxxxxx = _snowman.e();
      float _snowmanxxxxxxxxxxxx = _snowman.f();
      if (_snowman.h().contains(qk.a.e)) {
         _snowmanxxxxxxxxxxxx += _snowman.q;
      }

      if (_snowman.h().contains(qk.a.d)) {
         _snowmanxxxxxxxxxxx += _snowman.p;
      }

      _snowman.a(_snowmanxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx);
      this.c.a(new sb(_snowman.g()));
      this.c.a(new st.b(_snowman.cD(), _snowman.cE(), _snowman.cH(), _snowman.p, _snowman.q, false));
      if (!this.i) {
         this.i = true;
         this.f.a(null);
      }
   }

   @Override
   public void a(qr var1) {
      ol.a(_snowman, this, this.f);
      int _snowman = 19 | (_snowman.b() ? 128 : 0);
      _snowman.a((var2x, var3) -> this.g.a(var2x, var3, _snowman));
   }

   @Override
   public void a(pt var1) {
      ol.a(_snowman, this, this.f);
      int _snowman = _snowman.c();
      int _snowmanx = _snowman.d();
      cfx _snowmanxx = _snowman.i() == null ? null : new cfx(this.u.b(gm.ay), _snowman.i());
      cgh _snowmanxxx = this.g.n().a(_snowman, _snowmanx, _snowmanxx, _snowman.b(), _snowman.g(), _snowman.e(), _snowman.f());
      if (_snowmanxxx != null && _snowman.f()) {
         this.g.b(_snowmanxxx);
      }

      for (int _snowmanxxxx = 0; _snowmanxxxx < 16; _snowmanxxxx++) {
         this.g.d(_snowman, _snowmanxxxx, _snowmanx);
      }

      for (md _snowmanxxxx : _snowman.h()) {
         fx _snowmanxxxxx = new fx(_snowmanxxxx.h("x"), _snowmanxxxx.h("y"), _snowmanxxxx.h("z"));
         ccj _snowmanxxxxxx = this.g.c(_snowmanxxxxx);
         if (_snowmanxxxxxx != null) {
            _snowmanxxxxxx.a(this.g.d_(_snowmanxxxxx), _snowmanxxxx);
         }
      }
   }

   @Override
   public void a(pp var1) {
      ol.a(_snowman, this, this.f);
      int _snowman = _snowman.b();
      int _snowmanx = _snowman.c();
      dwr _snowmanxx = this.g.n();
      _snowmanxx.d(_snowman, _snowmanx);
      cuo _snowmanxxx = _snowmanxx.l();

      for (int _snowmanxxxx = 0; _snowmanxxxx < 16; _snowmanxxxx++) {
         this.g.d(_snowman, _snowmanxxxx, _snowmanx);
         _snowmanxxx.a(gp.a(_snowman, _snowmanxxxx, _snowmanx), true);
      }

      _snowmanxxx.a(new brd(_snowman, _snowmanx), false);
   }

   @Override
   public void a(oy var1) {
      ol.a(_snowman, this, this.f);
      this.g.b(_snowman.c(), _snowman.b());
   }

   @Override
   public void a(pm var1) {
      this.c.a(_snowman.b());
   }

   @Override
   public void a(nr var1) {
      this.f.r();
      if (this.e != null) {
         if (this.e instanceof eoo) {
            this.f.a(new eoi(this.e, b, _snowman));
         } else {
            this.f.a(new doa(this.e, b, _snowman));
         }
      } else {
         this.f.a(new doa(new drc(new doy()), b, _snowman));
      }
   }

   public void a(oj<?> var1) {
      this.c.a(_snowman);
   }

   @Override
   public void a(rr var1) {
      ol.a(_snowman, this, this.f);
      aqa _snowman = this.g.a(_snowman.b());
      aqm _snowmanx = (aqm)this.g.a(_snowman.c());
      if (_snowmanx == null) {
         _snowmanx = this.f.s;
      }

      if (_snowman != null) {
         if (_snowman instanceof aqg) {
            this.g.a(_snowman.cD(), _snowman.cE(), _snowman.cH(), adq.dW, adr.h, 0.1F, (this.p.nextFloat() - this.p.nextFloat()) * 0.35F + 0.9F, false);
         } else {
            this.g.a(_snowman.cD(), _snowman.cE(), _snowman.cH(), adq.gL, adr.h, 0.2F, (this.p.nextFloat() - this.p.nextFloat()) * 1.4F + 2.0F, false);
         }

         this.f.f.a(new dya(this.f.ac(), this.f.aE(), this.g, _snowman, _snowmanx));
         if (_snowman instanceof bcv) {
            bcv _snowmanxx = (bcv)_snowman;
            bmb _snowmanxxx = _snowmanxx.g();
            _snowmanxxx.g(_snowman.d());
            if (_snowmanxxx.a()) {
               this.g.d(_snowman.b());
            }
         } else {
            this.g.d(_snowman.b());
         }
      }
   }

   @Override
   public void a(pb var1) {
      ol.a(_snowman, this, this.f);
      this.f.j.a(_snowman.d(), _snowman.b(), _snowman.e());
   }

   @Override
   public void a(os var1) {
      ol.a(_snowman, this, this.f);
      aqa _snowman = this.g.a(_snowman.b());
      if (_snowman != null) {
         if (_snowman.c() == 0) {
            aqm _snowmanx = (aqm)_snowman;
            _snowmanx.a(aot.a);
         } else if (_snowman.c() == 3) {
            aqm _snowmanx = (aqm)_snowman;
            _snowmanx.a(aot.b);
         } else if (_snowman.c() == 1) {
            _snowman.bm();
         } else if (_snowman.c() == 2) {
            bfw _snowmanx = (bfw)_snowman;
            _snowmanx.a(false, false);
         } else if (_snowman.c() == 4) {
            this.f.f.a(_snowman, hh.g);
         } else if (_snowman.c() == 5) {
            this.f.f.a(_snowman, hh.r);
         }
      }
   }

   @Override
   public void a(op var1) {
      ol.a(_snowman, this, this.f);
      double _snowman = _snowman.e();
      double _snowmanx = _snowman.f();
      double _snowmanxx = _snowman.g();
      float _snowmanxxx = (float)(_snowman.k() * 360) / 256.0F;
      float _snowmanxxxx = (float)(_snowman.l() * 360) / 256.0F;
      aqm _snowmanxxxxx = (aqm)aqe.a(_snowman.d(), this.f.r);
      if (_snowmanxxxxx != null) {
         _snowmanxxxxx.c(_snowman, _snowmanx, _snowmanxx);
         _snowmanxxxxx.aA = (float)(_snowman.m() * 360) / 256.0F;
         _snowmanxxxxx.aC = (float)(_snowman.m() * 360) / 256.0F;
         if (_snowmanxxxxx instanceof bbr) {
            bbp[] _snowmanxxxxxx = ((bbr)_snowmanxxxxx).eJ();

            for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < _snowmanxxxxxx.length; _snowmanxxxxxxx++) {
               _snowmanxxxxxx[_snowmanxxxxxxx].e(_snowmanxxxxxxx + _snowman.b());
            }
         }

         _snowmanxxxxx.e(_snowman.b());
         _snowmanxxxxx.a_(_snowman.c());
         _snowmanxxxxx.a(_snowman, _snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx);
         _snowmanxxxxx.n((double)((float)_snowman.h() / 8000.0F), (double)((float)_snowman.i() / 8000.0F), (double)((float)_snowman.j() / 8000.0F));
         this.g.a(_snowman.b(), _snowmanxxxxx);
         if (_snowmanxxxxx instanceof baa) {
            boolean _snowmanxxxxxx = ((baa)_snowmanxxxxx).H_();
            emh _snowmanxxxxxxx;
            if (_snowmanxxxxxx) {
               _snowmanxxxxxxx = new emf((baa)_snowmanxxxxx);
            } else {
               _snowmanxxxxxxx = new emg((baa)_snowmanxxxxx);
            }

            this.f.W().a((emu)_snowmanxxxxxxx);
         }
      } else {
         a.warn("Skipping Entity with id {}", _snowman.d());
      }
   }

   @Override
   public void a(rk var1) {
      ol.a(_snowman, this, this.f);
      this.f.r.a(_snowman.b());
      this.f.r.b(_snowman.c());
   }

   @Override
   public void a(qy var1) {
      ol.a(_snowman, this, this.f);
      this.f.r.b(_snowman.b(), _snowman.c());
   }

   @Override
   public void a(rh var1) {
      ol.a(_snowman, this, this.f);
      aqa _snowman = this.g.a(_snowman.c());
      if (_snowman == null) {
         a.warn("Received passengers for unknown entity");
      } else {
         boolean _snowmanx = _snowman.y(this.f.s);
         _snowman.be();

         for (int _snowmanxx : _snowman.b()) {
            aqa _snowmanxxx = this.g.a(_snowmanxx);
            if (_snowmanxxx != null) {
               _snowmanxxx.a(_snowman, true);
               if (_snowmanxxx == this.f.s && !_snowmanx) {
                  this.f.j.a(new of("mount.onboard", this.f.k.ak.j()), false);
               }
            }
         }
      }
   }

   @Override
   public void a(rb var1) {
      ol.a(_snowman, this, this.f);
      aqa _snowman = this.g.a(_snowman.b());
      if (_snowman instanceof aqn) {
         ((aqn)_snowman).d(_snowman.c());
      }
   }

   private static bmb a(bfw var0) {
      for (aot _snowman : aot.values()) {
         bmb _snowmanx = _snowman.b(_snowman);
         if (_snowmanx.b() == bmd.qu) {
            return _snowmanx;
         }
      }

      return new bmb(bmd.qu);
   }

   @Override
   public void a(pn var1) {
      ol.a(_snowman, this, this.f);
      aqa _snowman = _snowman.a(this.g);
      if (_snowman != null) {
         if (_snowman.b() == 21) {
            this.f.W().a((emt)(new emm((bdm)_snowman)));
         } else if (_snowman.b() == 35) {
            int _snowmanx = 40;
            this.f.f.a(_snowman, hh.X, 30);
            this.g.a(_snowman.cD(), _snowman.cE(), _snowman.cH(), adq.pc, _snowman.cu(), 1.0F, 1.0F, false);
            if (_snowman == this.f.s) {
               this.f.h.a(a(this.f.s));
            }
         } else {
            _snowman.a(_snowman.b());
         }
      }
   }

   @Override
   public void a(rf var1) {
      ol.a(_snowman, this, this.f);
      this.f.s.v(_snowman.b());
      this.f.s.eI().a(_snowman.c());
      this.f.s.eI().b(_snowman.d());
   }

   @Override
   public void a(re var1) {
      ol.a(_snowman, this, this.f);
      this.f.s.a(_snowman.b(), _snowman.c(), _snowman.d());
   }

   @Override
   public void a(qp var1) {
      ol.a(_snowman, this, this.f);
      vj<brx> _snowman = _snowman.c();
      chd _snowmanx = _snowman.b();
      dzm _snowmanxx = this.f.s;
      int _snowmanxxx = _snowmanxx.Y();
      this.i = false;
      if (_snowman != _snowmanxx.l.Y()) {
         ddn _snowmanxxxx = this.g.G();
         boolean _snowmanxxxxx = _snowman.g();
         boolean _snowmanxxxxxx = _snowman.h();
         dwt.a _snowmanxxxxxxx = new dwt.a(this.h.s(), this.h.n(), _snowmanxxxxxx);
         this.h = _snowmanxxxxxxx;
         this.g = new dwt(this, _snowmanxxxxxxx, _snowman, _snowmanx, this.o, this.f::au, this.f.e, _snowmanxxxxx, _snowman.d());
         this.g.a(_snowmanxxxx);
         this.f.a(this.g);
         this.f.a(new dos());
      }

      this.g.m();
      String _snowmanxxxx = _snowmanxx.B();
      this.f.t = null;
      dzm _snowmanxxxxx = this.f.q.a(this.g, _snowmanxx.D(), _snowmanxx.F(), _snowmanxx.bu(), _snowmanxx.bA());
      _snowmanxxxxx.e(_snowmanxxx);
      this.f.s = _snowmanxxxxx;
      if (_snowman != _snowmanxx.l.Y()) {
         this.f.p().b();
      }

      this.f.t = _snowmanxxxxx;
      _snowmanxxxxx.ab().a(_snowmanxx.ab().c());
      if (_snowman.i()) {
         _snowmanxxxxx.dB().a(_snowmanxx.dB());
      }

      _snowmanxxxxx.ac();
      _snowmanxxxxx.g(_snowmanxxxx);
      this.g.a(_snowmanxxx, (dzj)_snowmanxxxxx);
      _snowmanxxxxx.p = -180.0F;
      _snowmanxxxxx.f = new dzl(this.f.k);
      this.f.q.a(_snowmanxxxxx);
      _snowmanxxxxx.r(_snowmanxx.eO());
      _snowmanxxxxx.b(_snowmanxx.G());
      if (this.f.y instanceof dnx) {
         this.f.a(null);
      }

      this.f.q.b(_snowman.e());
      this.f.q.a(_snowman.f());
   }

   @Override
   public void a(po var1) {
      ol.a(_snowman, this, this.f);
      brp _snowman = new brp(this.f.r, null, _snowman.e(), _snowman.f(), _snowman.g(), _snowman.h(), _snowman.i());
      _snowman.a(true);
      this.f.s.f(this.f.s.cC().b((double)_snowman.b(), (double)_snowman.c(), (double)_snowman.d()));
   }

   @Override
   public void a(pr var1) {
      ol.a(_snowman, this, this.f);
      aqa _snowman = this.g.a(_snowman.d());
      if (_snowman instanceof bbb) {
         dzm _snowmanx = this.f.s;
         bbb _snowmanxx = (bbb)_snowman;
         apa _snowmanxxx = new apa(_snowman.c());
         biy _snowmanxxxx = new biy(_snowman.b(), _snowmanx.bm, _snowmanxxx, _snowmanxx);
         _snowmanx.bp = _snowmanxxxx;
         this.f.a(new dqk(_snowmanxxxx, _snowmanx.bm, _snowmanxx));
      }
   }

   @Override
   public void a(qd var1) {
      ol.a(_snowman, this, this.f);
      doi.a(_snowman.c(), this.f, _snowman.b(), _snowman.d());
   }

   @Override
   public void a(pi var1) {
      ol.a(_snowman, this, this.f);
      bfw _snowman = this.f.s;
      bmb _snowmanx = _snowman.d();
      int _snowmanxx = _snowman.c();
      this.f.ao().a(_snowmanx);
      if (_snowman.b() == -1) {
         if (!(this.f.y instanceof dqc)) {
            _snowman.bm.g(_snowmanx);
         }
      } else if (_snowman.b() == -2) {
         _snowman.bm.a(_snowmanxx, _snowmanx);
      } else {
         boolean _snowmanxxx = false;
         if (this.f.y instanceof dqc) {
            dqc _snowmanxxxx = (dqc)this.f.y;
            _snowmanxxx = _snowmanxxxx.k() != bks.n.a();
         }

         if (_snowman.b() == 0 && _snowman.c() >= 36 && _snowmanxx < 45) {
            if (!_snowmanx.a()) {
               bmb _snowmanxxxx = _snowman.bo.a(_snowmanxx).e();
               if (_snowmanxxxx.a() || _snowmanxxxx.E() < _snowmanx.E()) {
                  _snowmanx.d(5);
               }
            }

            _snowman.bo.a(_snowmanxx, _snowmanx);
         } else if (_snowman.b() == _snowman.bp.b && (_snowman.b() != 0 || !_snowmanxxx)) {
            _snowman.bp.a(_snowmanxx, _snowmanx);
         }
      }
   }

   @Override
   public void a(pe var1) {
      ol.a(_snowman, this, this.f);
      bic _snowman = null;
      bfw _snowmanx = this.f.s;
      if (_snowman.b() == 0) {
         _snowman = _snowmanx.bo;
      } else if (_snowman.b() == _snowmanx.bp.b) {
         _snowman = _snowmanx.bp;
      }

      if (_snowman != null && !_snowman.d()) {
         this.a(new si(_snowman.b(), _snowman.c(), true));
      }
   }

   @Override
   public void a(pg var1) {
      ol.a(_snowman, this, this.f);
      bfw _snowman = this.f.s;
      if (_snowman.b() == 0) {
         _snowman.bo.a(_snowman.c());
      } else if (_snowman.b() == _snowman.bp.b) {
         _snowman.bp.a(_snowman.c());
      }
   }

   @Override
   public void a(qe var1) {
      ol.a(_snowman, this, this.f);
      ccj _snowman = this.g.c(_snowman.b());
      if (!(_snowman instanceof cdf)) {
         _snowman = new cdf();
         _snowman.a(this.g, _snowman.b());
      }

      this.f.s.a((cdf)_snowman);
   }

   @Override
   public void a(ow var1) {
      ol.a(_snowman, this, this.f);
      fx _snowman = _snowman.b();
      ccj _snowmanx = this.f.r.c(_snowman);
      int _snowmanxx = _snowman.c();
      boolean _snowmanxxx = _snowmanxx == 2 && _snowmanx instanceof cco;
      if (_snowmanxx == 1 && _snowmanx instanceof cdi
         || _snowmanxxx
         || _snowmanxx == 3 && _snowmanx instanceof cce
         || _snowmanxx == 4 && _snowmanx instanceof cdg
         || _snowmanxx == 6 && _snowmanx instanceof cca
         || _snowmanxx == 7 && _snowmanx instanceof cdj
         || _snowmanxx == 8 && _snowmanx instanceof cdk
         || _snowmanxx == 9 && _snowmanx instanceof cdf
         || _snowmanxx == 11 && _snowmanx instanceof ccf
         || _snowmanxx == 5 && _snowmanx instanceof ccq
         || _snowmanxx == 12 && _snowmanx instanceof ccz
         || _snowmanxx == 13 && _snowmanx instanceof ccm
         || _snowmanxx == 14 && _snowmanx instanceof ccg) {
         _snowmanx.a(this.f.r.d_(_snowman), _snowman.d());
      }

      if (_snowmanxxx && this.f.y instanceof dpy) {
         ((dpy)this.f.y).m();
      }
   }

   @Override
   public void a(ph var1) {
      ol.a(_snowman, this, this.f);
      bfw _snowman = this.f.s;
      if (_snowman.bp != null && _snowman.bp.b == _snowman.b()) {
         _snowman.bp.a(_snowman.c(), _snowman.d());
      }
   }

   @Override
   public void a(rd var1) {
      ol.a(_snowman, this, this.f);
      aqa _snowman = this.g.a(_snowman.b());
      if (_snowman != null) {
         _snowman.c().forEach(var1x -> _snowman.a((aqf)var1x.getFirst(), (bmb)var1x.getSecond()));
      }
   }

   @Override
   public void a(pf var1) {
      ol.a(_snowman, this, this.f);
      this.f.s.x();
   }

   @Override
   public void a(ox var1) {
      ol.a(_snowman, this, this.f);
      this.f.r.a(_snowman.b(), _snowman.e(), _snowman.c(), _snowman.d());
   }

   @Override
   public void a(ov var1) {
      ol.a(_snowman, this, this.f);
      this.f.r.a(_snowman.b(), _snowman.c(), _snowman.d());
   }

   @Override
   public void a(pq var1) {
      ol.a(_snowman, this, this.f);
      bfw _snowman = this.f.s;
      pq.a _snowmanx = _snowman.b();
      float _snowmanxx = _snowman.c();
      int _snowmanxxx = afm.d(_snowmanxx + 0.5F);
      if (_snowmanx == pq.a) {
         _snowman.a(new of("block.minecraft.spawn.not_valid"), false);
      } else if (_snowmanx == pq.b) {
         this.g.w().b(true);
         this.g.e(0.0F);
      } else if (_snowmanx == pq.c) {
         this.g.w().b(false);
         this.g.e(1.0F);
      } else if (_snowmanx == pq.d) {
         this.f.q.b(bru.a(_snowmanxxx));
      } else if (_snowmanx == pq.e) {
         if (_snowmanxxx == 0) {
            this.f.s.e.a(new sf(sf.a.a));
            this.f.a(new dos());
         } else if (_snowmanxxx == 1) {
            this.f.a(new dpa(true, () -> this.f.s.e.a(new sf(sf.a.a))));
         }
      } else if (_snowmanx == pq.f) {
         dkd _snowmanxxxx = this.f.k;
         if (_snowmanxx == 0.0F) {
            this.f.a(new dny());
         } else if (_snowmanxx == 101.0F) {
            this.f.j.c().a(new of("demo.help.movement", _snowmanxxxx.af.j(), _snowmanxxxx.ag.j(), _snowmanxxxx.ah.j(), _snowmanxxxx.ai.j()));
         } else if (_snowmanxx == 102.0F) {
            this.f.j.c().a(new of("demo.help.jump", _snowmanxxxx.aj.j()));
         } else if (_snowmanxx == 103.0F) {
            this.f.j.c().a(new of("demo.help.inventory", _snowmanxxxx.am.j()));
         } else if (_snowmanxx == 104.0F) {
            this.f.j.c().a(new of("demo.day.6", _snowmanxxxx.aw.j()));
         }
      } else if (_snowmanx == pq.g) {
         this.g.a(_snowman, _snowman.cD(), _snowman.cG(), _snowman.cH(), adq.X, adr.h, 0.18F, 0.45F);
      } else if (_snowmanx == pq.h) {
         this.g.e(_snowmanxx);
      } else if (_snowmanx == pq.i) {
         this.g.c(_snowmanxx);
      } else if (_snowmanx == pq.j) {
         this.g.a(_snowman, _snowman.cD(), _snowman.cE(), _snowman.cH(), adq.lW, adr.g, 1.0F, 1.0F);
      } else if (_snowmanx == pq.k) {
         this.g.a(hh.q, _snowman.cD(), _snowman.cE(), _snowman.cH(), 0.0, 0.0, 0.0);
         if (_snowmanxxx == 1) {
            this.g.a(_snowman, _snowman.cD(), _snowman.cE(), _snowman.cH(), adq.df, adr.f, 1.0F, 1.0F);
         }
      } else if (_snowmanx == pq.l) {
         this.f.s.b(_snowmanxx == 0.0F);
      }
   }

   @Override
   public void a(py var1) {
      ol.a(_snowman, this, this.f);
      dkx _snowman = this.f.h.h();
      String _snowmanx = bmh.a(_snowman.b());
      cxx _snowmanxx = this.f.r.a(_snowmanx);
      if (_snowmanxx == null) {
         _snowmanxx = new cxx(_snowmanx);
         if (_snowman.a(_snowmanx) != null) {
            cxx _snowmanxxx = _snowman.a(_snowman.a(_snowmanx));
            if (_snowmanxxx != null) {
               _snowmanxx = _snowmanxxx;
            }
         }

         this.f.r.a(_snowmanxx);
      }

      _snowman.a(_snowmanxx);
      _snowman.a(_snowmanxx);
   }

   @Override
   public void a(pu var1) {
      ol.a(_snowman, this, this.f);
      if (_snowman.b()) {
         this.f.r.b(_snowman.c(), _snowman.e(), _snowman.d());
      } else {
         this.f.r.c(_snowman.c(), _snowman.e(), _snowman.d());
      }
   }

   @Override
   public void a(rt var1) {
      ol.a(_snowman, this, this.f);
      this.k.a(_snowman);
   }

   @Override
   public void a(qs var1) {
      ol.a(_snowman, this, this.f);
      vk _snowman = _snowman.b();
      if (_snowman == null) {
         this.k.a(null, false);
      } else {
         y _snowmanx = this.k.a().a(_snowman);
         this.k.a(_snowmanx, false);
      }
   }

   @Override
   public void a(pd var1) {
      ol.a(_snowman, this, this.f);
      this.q = new CommandDispatcher(_snowman.b());
   }

   @Override
   public void a(ro var1) {
      ol.a(_snowman, this, this.f);
      this.f.W().a(_snowman.b(), _snowman.c());
   }

   @Override
   public void a(pc var1) {
      ol.a(_snowman, this, this.f);
      this.l.a(_snowman.b(), _snowman.c());
   }

   @Override
   public void a(rw var1) {
      ol.a(_snowman, this, this.f);
      this.r.a(_snowman.b());
      emy<drt> _snowman = this.f.a(enb.c);
      _snowman.a();
      djm _snowmanx = this.f.s.F();
      _snowmanx.a(this.r.b());
      _snowmanx.b().forEach(_snowman::a);
      _snowman.b();
   }

   @Override
   public void a(qj var1) {
      ol.a(_snowman, this, this.f);
      dcn _snowman = _snowman.a(this.g);
      if (_snowman != null) {
         this.f.s.a(_snowman.b(), _snowman);
      }
   }

   @Override
   public void a(rq var1) {
      ol.a(_snowman, this, this.f);
      if (!this.n.a(_snowman.b(), _snowman.c())) {
         a.debug("Got unhandled response to tag query {}", _snowman.b());
      }
   }

   @Override
   public void a(ot var1) {
      ol.a(_snowman, this, this.f);

      for (Entry<adx<?>, Integer> _snowman : _snowman.b().entrySet()) {
         adx<?> _snowmanx = _snowman.getKey();
         int _snowmanxx = _snowman.getValue();
         this.f.s.D().a(this.f.s, _snowmanx, _snowmanxx);
      }

      if (this.f.y instanceof dpc) {
         ((dpc)this.f.y).k();
      }
   }

   @Override
   public void a(ql var1) {
      ol.a(_snowman, this, this.f);
      djm _snowman = this.f.s.F();
      _snowman.a(_snowman.d());
      ql.a _snowmanx = _snowman.e();
      switch (_snowmanx) {
         case c:
            for (vk _snowmanxx : _snowman.b()) {
               this.r.a(_snowmanxx).ifPresent(_snowman::c);
            }
            break;
         case a:
            for (vk _snowmanxx : _snowman.b()) {
               this.r.a(_snowmanxx).ifPresent(_snowman::a);
            }

            for (vk _snowmanxx : _snowman.c()) {
               this.r.a(_snowmanxx).ifPresent(_snowman::f);
            }
            break;
         case b:
            for (vk _snowmanxx : _snowman.b()) {
               this.r.a(_snowmanxx).ifPresent(var2x -> {
                  _snowman.a((boq<?>)var2x);
                  _snowman.f((boq<?>)var2x);
                  dmo.a(this.f.an(), (boq<?>)var2x);
               });
            }
      }

      _snowman.b().forEach(var1x -> var1x.a(_snowman));
      if (this.f.y instanceof drv) {
         ((drv)this.f.y).az_();
      }
   }

   @Override
   public void a(rv var1) {
      ol.a(_snowman, this, this.f);
      aqa _snowman = this.g.a(_snowman.c());
      if (_snowman instanceof aqm) {
         aps _snowmanx = aps.a(_snowman.d());
         if (_snowmanx != null) {
            apu _snowmanxx = new apu(_snowmanx, _snowman.f(), _snowman.e(), _snowman.h(), _snowman.g(), _snowman.i());
            _snowmanxx.b(_snowman.b());
            ((aqm)_snowman).e(_snowmanxx);
         }
      }
   }

   @Override
   public void a(rx var1) {
      ol.a(_snowman, this, this.f);
      aen _snowman = _snowman.b();
      Multimap<vk, vk> _snowmanx = aek.b(_snowman);
      if (!_snowmanx.isEmpty()) {
         a.warn("Incomplete server tags, disconnecting. Missing: {}", _snowmanx);
         this.c.a(new of("multiplayer.disconnect.missing_tags"));
      } else {
         this.m = _snowman;
         if (!this.c.d()) {
            _snowman.e();
         }

         this.f.a(enb.b).b();
      }
   }

   @Override
   public void a(qh var1) {
      ol.a(_snowman, this, this.f);
      if (_snowman.a == qh.a.c) {
         aqa _snowman = this.g.a(_snowman.b);
         if (_snowman == this.f.s) {
            if (this.f.s.G()) {
               this.f.a(new dnx(_snowman.e, this.g.w().n()));
            } else {
               this.f.s.ey();
            }
         }
      }
   }

   @Override
   public void a(pa var1) {
      ol.a(_snowman, this, this.f);
      this.h.a(_snowman.c());
      this.h.a(_snowman.b());
   }

   @Override
   public void a(qu var1) {
      ol.a(_snowman, this, this.f);
      aqa _snowman = _snowman.a(this.g);
      if (_snowman != null) {
         this.f.a(_snowman);
      }
   }

   @Override
   public void a(qt var1) {
      ol.a(_snowman, this, this.f);
      _snowman.a(this.g.f());
   }

   @Override
   public void a(rl var1) {
      ol.a(_snowman, this, this.f);
      rl.a _snowman = _snowman.b();
      nr _snowmanx = null;
      nr _snowmanxx = null;
      nr _snowmanxxx = _snowman.c() != null ? _snowman.c() : oe.d;
      switch (_snowman) {
         case a:
            _snowmanx = _snowmanxxx;
            break;
         case b:
            _snowmanxx = _snowmanxxx;
            break;
         case c:
            this.f.j.a(_snowmanxxx, false);
            return;
         case f:
            this.f.j.a(null, null, -1, -1, -1);
            this.f.j.a();
            return;
      }

      this.f.j.a(_snowmanx, _snowmanxx, _snowman.d(), _snowman.e(), _snowman.f());
   }

   @Override
   public void a(rp var1) {
      this.f.j.g().b(_snowman.b().getString().isEmpty() ? null : _snowman.b());
      this.f.j.g().a(_snowman.c().getString().isEmpty() ? null : _snowman.c());
   }

   @Override
   public void a(qn var1) {
      ol.a(_snowman, this, this.f);
      aqa _snowman = _snowman.a(this.g);
      if (_snowman instanceof aqm) {
         ((aqm)_snowman).c(_snowman.b());
      }
   }

   @Override
   public void a(qi var1) {
      ol.a(_snowman, this, this.f);

      for (qi.b _snowman : _snowman.b()) {
         if (_snowman.c() == qi.a.e) {
            this.f.aB().f(_snowman.a().getId());
            this.j.remove(_snowman.a().getId());
         } else {
            dwx _snowmanx = this.j.get(_snowman.a().getId());
            if (_snowman.c() == qi.a.a) {
               _snowmanx = new dwx(_snowman);
               this.j.put(_snowmanx.a().getId(), _snowmanx);
               this.f.aB().a(_snowmanx);
            }

            if (_snowmanx != null) {
               switch (_snowman.c()) {
                  case a:
                     _snowmanx.a(_snowman.c());
                     _snowmanx.a(_snowman.b());
                     _snowmanx.a(_snowman.d());
                     break;
                  case b:
                     _snowmanx.a(_snowman.c());
                     break;
                  case c:
                     _snowmanx.a(_snowman.b());
                     break;
                  case d:
                     _snowmanx.a(_snowman.d());
               }
            }
         }
      }
   }

   @Override
   public void a(ps var1) {
      this.a(new sr(_snowman.b()));
   }

   @Override
   public void a(qg var1) {
      ol.a(_snowman, this, this.f);
      bfw _snowman = this.f.s;
      _snowman.bC.b = _snowman.c();
      _snowman.bC.d = _snowman.e();
      _snowman.bC.a = _snowman.b();
      _snowman.bC.c = _snowman.d();
      _snowman.bC.a(_snowman.f());
      _snowman.bC.b(_snowman.g());
   }

   @Override
   public void a(rn var1) {
      ol.a(_snowman, this, this.f);
      this.f.r.a(this.f.s, _snowman.d(), _snowman.e(), _snowman.f(), _snowman.b(), _snowman.c(), _snowman.g(), _snowman.h());
   }

   @Override
   public void a(rm var1) {
      ol.a(_snowman, this, this.f);
      aqa _snowman = this.g.a(_snowman.d());
      if (_snowman != null) {
         this.f.r.a(this.f.s, _snowman, _snowman.b(), _snowman.c(), _snowman.e(), _snowman.f());
      }
   }

   @Override
   public void a(pl var1) {
      ol.a(_snowman, this, this.f);
      this.f.W().a(new emp(_snowman.b(), _snowman.c(), _snowman.g(), _snowman.h(), false, 0, emt.a.b, _snowman.d(), _snowman.e(), _snowman.f(), false));
   }

   @Override
   public void a(qo var1) {
      String _snowman = _snowman.b();
      String _snowmanx = _snowman.c();
      if (this.b(_snowman)) {
         if (_snowman.startsWith("level://")) {
            try {
               String _snowmanxx = URLDecoder.decode(_snowman.substring("level://".length()), StandardCharsets.UTF_8.toString());
               File _snowmanxxx = new File(this.f.n, "saves");
               File _snowmanxxxx = new File(_snowmanxxx, _snowmanxx);
               if (_snowmanxxxx.isFile()) {
                  this.a(tf.a.d);
                  CompletableFuture<?> _snowmanxxxxx = this.f.P().a(_snowmanxxxx, abx.c);
                  this.a(_snowmanxxxxx);
                  return;
               }
            } catch (UnsupportedEncodingException var8) {
            }

            this.a(tf.a.c);
         } else {
            dwz _snowmanxx = this.f.E();
            if (_snowmanxx != null && _snowmanxx.b() == dwz.a.a) {
               this.a(tf.a.d);
               this.a(this.f.P().a(_snowman, _snowmanx));
            } else if (_snowmanxx != null && _snowmanxx.b() != dwz.a.c) {
               this.a(tf.a.b);
            } else {
               this.f.execute(() -> this.f.a(new dns(var3x -> {
                     this.f = djz.C();
                     dwz _snowmanxxx = this.f.E();
                     if (var3x) {
                        if (_snowmanxxx != null) {
                           _snowmanxxx.a(dwz.a.a);
                        }

                        this.a(tf.a.d);
                        this.a(this.f.P().a(_snowman, _snowman));
                     } else {
                        if (_snowmanxxx != null) {
                           _snowmanxxx.a(dwz.a.b);
                        }

                        this.a(tf.a.b);
                     }

                     dxa.c(_snowmanxxx);
                     this.f.a(null);
                  }, new of("multiplayer.texturePrompt.line1"), new of("multiplayer.texturePrompt.line2"))));
            }
         }
      }
   }

   private boolean b(String var1) {
      try {
         URI _snowman = new URI(_snowman);
         String _snowmanx = _snowman.getScheme();
         boolean _snowmanxx = "level".equals(_snowmanx);
         if (!"http".equals(_snowmanx) && !"https".equals(_snowmanx) && !_snowmanxx) {
            throw new URISyntaxException(_snowman, "Wrong protocol");
         } else if (!_snowmanxx || !_snowman.contains("..") && _snowman.endsWith("/resources.zip")) {
            return true;
         } else {
            throw new URISyntaxException(_snowman, "Invalid levelstorage resourcepack path");
         }
      } catch (URISyntaxException var5) {
         this.a(tf.a.c);
         return false;
      }
   }

   private void a(CompletableFuture<?> var1) {
      _snowman.thenRun(() -> this.a(tf.a.a)).exceptionally(var1x -> {
         this.a(tf.a.c);
         return null;
      });
   }

   private void a(tf.a var1) {
      this.c.a(new tf(_snowman));
   }

   @Override
   public void a(oz var1) {
      ol.a(_snowman, this, this.f);
      this.f.j.i().a(_snowman);
   }

   @Override
   public void a(pj var1) {
      ol.a(_snowman, this, this.f);
      if (_snowman.c() == 0) {
         this.f.s.eT().b(_snowman.b());
      } else {
         this.f.s.eT().a(_snowman.b(), _snowman.c());
      }
   }

   @Override
   public void a(qb var1) {
      ol.a(_snowman, this, this.f);
      aqa _snowman = this.f.s.cr();
      if (_snowman != this.f.s && _snowman.cs()) {
         _snowman.a(_snowman.b(), _snowman.c(), _snowman.d(), _snowman.e(), _snowman.f());
         this.c.a(new su(_snowman));
      }
   }

   @Override
   public void a(qc var1) {
      ol.a(_snowman, this, this.f);
      bmb _snowman = this.f.s.b(_snowman.b());
      if (_snowman.b() == bmd.oU) {
         this.f.a(new dpv(new dpv.c(_snowman)));
      }
   }

   @Override
   public void a(pk var1) {
      ol.a(_snowman, this, this.f);
      vk _snowman = _snowman.b();
      nf _snowmanx = null;

      try {
         _snowmanx = _snowman.c();
         if (pk.a.equals(_snowman)) {
            this.f.s.g(_snowmanx.e(32767));
         } else if (pk.b.equals(_snowman)) {
            int _snowmanxx = _snowmanx.readInt();
            float _snowmanxxx = _snowmanx.readFloat();
            cxd _snowmanxxxx = cxd.b(_snowmanx);
            this.f.i.a.a(_snowmanxx, _snowmanxxxx, _snowmanxxx);
         } else if (pk.c.equals(_snowman)) {
            long _snowmanxx = _snowmanx.j();
            fx _snowmanxxx = _snowmanx.e();
            ((edm)this.f.i.f).a(_snowmanxx, _snowmanxxx);
         } else if (pk.d.equals(_snowman)) {
            fx _snowmanxx = _snowmanx.e();
            int _snowmanxxx = _snowmanx.readInt();
            List<fx> _snowmanxxxx = Lists.newArrayList();
            List<Float> _snowmanxxxxx = Lists.newArrayList();

            for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < _snowmanxxx; _snowmanxxxxxx++) {
               _snowmanxxxx.add(_snowmanx.e());
               _snowmanxxxxx.add(_snowmanx.readFloat());
            }

            this.f.i.g.a(_snowmanxx, _snowmanxxxx, _snowmanxxxxx);
         } else if (pk.e.equals(_snowman)) {
            chd _snowmanxx = this.u.a().a(_snowmanx.p());
            cra _snowmanxxx = new cra(_snowmanx.readInt(), _snowmanx.readInt(), _snowmanx.readInt(), _snowmanx.readInt(), _snowmanx.readInt(), _snowmanx.readInt());
            int _snowmanxxxx = _snowmanx.readInt();
            List<cra> _snowmanxxxxx = Lists.newArrayList();
            List<Boolean> _snowmanxxxxxx = Lists.newArrayList();

            for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < _snowmanxxxx; _snowmanxxxxxxx++) {
               _snowmanxxxxx.add(new cra(_snowmanx.readInt(), _snowmanx.readInt(), _snowmanx.readInt(), _snowmanx.readInt(), _snowmanx.readInt(), _snowmanx.readInt()));
               _snowmanxxxxxx.add(_snowmanx.readBoolean());
            }

            this.f.i.h.a(_snowmanxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxx);
         } else if (pk.f.equals(_snowman)) {
            ((edt)this.f.i.j).a(_snowmanx.e(), _snowmanx.readFloat(), _snowmanx.readFloat(), _snowmanx.readFloat(), _snowmanx.readFloat(), _snowmanx.readFloat());
         } else if (pk.j.equals(_snowman)) {
            int _snowmanxx = _snowmanx.readInt();

            for (int _snowmanxxx = 0; _snowmanxxx < _snowmanxx; _snowmanxxx++) {
               this.f.i.n.a(_snowmanx.g());
            }

            int _snowmanxxx = _snowmanx.readInt();

            for (int _snowmanxxxx = 0; _snowmanxxxx < _snowmanxxx; _snowmanxxxx++) {
               this.f.i.n.b(_snowmanx.g());
            }
         } else if (pk.h.equals(_snowman)) {
            fx _snowmanxx = _snowmanx.e();
            String _snowmanxxx = _snowmanx.o();
            int _snowmanxxxx = _snowmanx.readInt();
            edc.b _snowmanxxxxx = new edc.b(_snowmanxx, _snowmanxxx, _snowmanxxxx);
            this.f.i.m.a(_snowmanxxxxx);
         } else if (pk.i.equals(_snowman)) {
            fx _snowmanxx = _snowmanx.e();
            this.f.i.m.a(_snowmanxx);
         } else if (pk.g.equals(_snowman)) {
            fx _snowmanxx = _snowmanx.e();
            int _snowmanxxx = _snowmanx.readInt();
            this.f.i.m.a(_snowmanxx, _snowmanxxx);
         } else if (pk.k.equals(_snowman)) {
            fx _snowmanxx = _snowmanx.e();
            int _snowmanxxx = _snowmanx.readInt();
            int _snowmanxxxx = _snowmanx.readInt();
            List<edj.a> _snowmanxxxxx = Lists.newArrayList();

            for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < _snowmanxxxx; _snowmanxxxxxx++) {
               int _snowmanxxxxxxx = _snowmanx.readInt();
               boolean _snowmanxxxxxxxx = _snowmanx.readBoolean();
               String _snowmanxxxxxxxxx = _snowmanx.e(255);
               _snowmanxxxxx.add(new edj.a(_snowmanxx, _snowmanxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxx));
            }

            this.f.i.q.a(_snowmanxxx, _snowmanxxxxx);
         } else if (pk.q.equals(_snowman)) {
            int _snowmanxx = _snowmanx.readInt();
            Collection<fx> _snowmanxxx = Lists.newArrayList();

            for (int _snowmanxxxx = 0; _snowmanxxxx < _snowmanxx; _snowmanxxxx++) {
               _snowmanxxx.add(_snowmanx.e());
            }

            this.f.i.p.a(_snowmanxxx);
         } else if (pk.l.equals(_snowman)) {
            double _snowmanxx = _snowmanx.readDouble();
            double _snowmanxxx = _snowmanx.readDouble();
            double _snowmanxxxx = _snowmanx.readDouble();
            gk _snowmanxxxxx = new gl(_snowmanxx, _snowmanxxx, _snowmanxxxx);
            UUID _snowmanxxxxxx = _snowmanx.k();
            int _snowmanxxxxxxx = _snowmanx.readInt();
            String _snowmanxxxxxxxx = _snowmanx.o();
            String _snowmanxxxxxxxxx = _snowmanx.o();
            int _snowmanxxxxxxxxxx = _snowmanx.readInt();
            float _snowmanxxxxxxxxxxx = _snowmanx.readFloat();
            float _snowmanxxxxxxxxxxxx = _snowmanx.readFloat();
            String _snowmanxxxxxxxxxxxxx = _snowmanx.o();
            boolean _snowmanxxxxxxxxxxxxxx = _snowmanx.readBoolean();
            cxd _snowmanxxxxxxxxxxxxxxx;
            if (_snowmanxxxxxxxxxxxxxx) {
               _snowmanxxxxxxxxxxxxxxx = cxd.b(_snowmanx);
            } else {
               _snowmanxxxxxxxxxxxxxxx = null;
            }

            boolean _snowmanxxxxxxxxxxxxxxxx = _snowmanx.readBoolean();
            edc.a _snowmanxxxxxxxxxxxxxxxxx = new edc.a(
               _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxx, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx
            );
            int _snowmanxxxxxxxxxxxxxxxxxx = _snowmanx.readInt();

            for (int _snowmanxxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxxxx++) {
               String _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanx.o();
               _snowmanxxxxxxxxxxxxxxxxx.l.add(_snowmanxxxxxxxxxxxxxxxxxxxx);
            }

            int _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanx.readInt();

            for (int _snowmanxxxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxxxxx++) {
               String _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowmanx.o();
               _snowmanxxxxxxxxxxxxxxxxx.m.add(_snowmanxxxxxxxxxxxxxxxxxxxxx);
            }

            int _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanx.readInt();

            for (int _snowmanxxxxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxxxxxx++) {
               String _snowmanxxxxxxxxxxxxxxxxxxxxxx = _snowmanx.o();
               _snowmanxxxxxxxxxxxxxxxxx.n.add(_snowmanxxxxxxxxxxxxxxxxxxxxxx);
            }

            int _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowmanx.readInt();

            for (int _snowmanxxxxxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxxxxxxx++) {
               fx _snowmanxxxxxxxxxxxxxxxxxxxxxxx = _snowmanx.e();
               _snowmanxxxxxxxxxxxxxxxxx.p.add(_snowmanxxxxxxxxxxxxxxxxxxxxxxx);
            }

            int _snowmanxxxxxxxxxxxxxxxxxxxxxx = _snowmanx.readInt();

            for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxxxxxxxx++) {
               fx _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanx.e();
               _snowmanxxxxxxxxxxxxxxxxx.q.add(_snowmanxxxxxxxxxxxxxxxxxxxxxxxx);
            }

            int _snowmanxxxxxxxxxxxxxxxxxxxxxxx = _snowmanx.readInt();

            for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxxxxxxxxx++) {
               String _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanx.o();
               _snowmanxxxxxxxxxxxxxxxxx.o.add(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx);
            }

            this.f.i.m.a(_snowmanxxxxxxxxxxxxxxxxx);
         } else if (pk.m.equals(_snowman)) {
            double _snowmanxx = _snowmanx.readDouble();
            double _snowmanxxx = _snowmanx.readDouble();
            double _snowmanxxxx = _snowmanx.readDouble();
            gk _snowmanxxxxx = new gl(_snowmanxx, _snowmanxxx, _snowmanxxxx);
            UUID _snowmanxxxxxx = _snowmanx.k();
            int _snowmanxxxxxxx = _snowmanx.readInt();
            boolean _snowmanxxxxxxxx = _snowmanx.readBoolean();
            fx _snowmanxxxxxxxxx = null;
            if (_snowmanxxxxxxxx) {
               _snowmanxxxxxxxxx = _snowmanx.e();
            }

            boolean _snowmanxxxxxxxxxx = _snowmanx.readBoolean();
            fx _snowmanxxxxxxxxxxx = null;
            if (_snowmanxxxxxxxxxx) {
               _snowmanxxxxxxxxxxx = _snowmanx.e();
            }

            int _snowmanxxxxxxxxxxxx = _snowmanx.readInt();
            boolean _snowmanxxxxxxxxxxxxx = _snowmanx.readBoolean();
            cxd _snowmanxxxxxxxxxxxxxx = null;
            if (_snowmanxxxxxxxxxxxxx) {
               _snowmanxxxxxxxxxxxxxx = cxd.b(_snowmanx);
            }

            edb.a _snowmanxxxxxxxxxxxxxxx = new edb.a(_snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx);
            int _snowmanxxxxxxxxxxxxxxxx = _snowmanx.readInt();

            for (int _snowmanxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxx++) {
               String _snowmanxxxxxxxxxxxxxxxxxx = _snowmanx.o();
               _snowmanxxxxxxxxxxxxxxx.h.add(_snowmanxxxxxxxxxxxxxxxxxx);
            }

            int _snowmanxxxxxxxxxxxxxxxxx = _snowmanx.readInt();

            for (int _snowmanxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxxx++) {
               fx _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanx.e();
               _snowmanxxxxxxxxxxxxxxx.i.add(_snowmanxxxxxxxxxxxxxxxxxxx);
            }

            this.f.i.o.a(_snowmanxxxxxxxxxxxxxxx);
         } else if (pk.n.equals(_snowman)) {
            fx _snowmanxxxxxxxxxxxx = _snowmanx.e();
            String _snowmanxxxxxxxxxxxxx = _snowmanx.o();
            int _snowmanxxxxxxxxxxxxxx = _snowmanx.readInt();
            int _snowmanxxxxxxxxxxxxxxx = _snowmanx.readInt();
            boolean _snowmanxxxxxxxxxxxxxxxx = _snowmanx.readBoolean();
            edb.b _snowmanxxxxxxxxxxxxxxxxx = new edb.b(_snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx, this.g.T());
            this.f.i.o.a(_snowmanxxxxxxxxxxxxxxxxx);
         } else if (pk.p.equals(_snowman)) {
            this.f.i.r.a();
         } else if (pk.o.equals(_snowman)) {
            fx _snowmanxxxxxxxxxxxx = _snowmanx.e();
            int _snowmanxxxxxxxxxxxxx = _snowmanx.readInt();
            String _snowmanxxxxxxxxxxxxxx = _snowmanx.o();
            int _snowmanxxxxxxxxxxxxxxx = _snowmanx.readInt();
            this.f.i.r.a(_snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx);
         } else {
            a.warn("Unknown custom packed identifier: {}", _snowman);
         }
      } finally {
         if (_snowmanx != null) {
            _snowmanx.release();
         }
      }
   }

   @Override
   public void a(rg var1) {
      ol.a(_snowman, this, this.f);
      ddn _snowman = this.g.G();
      String _snowmanx = _snowman.b();
      if (_snowman.d() == 0) {
         _snowman.a(_snowmanx, ddq.b, _snowman.c(), _snowman.e());
      } else if (_snowman.b(_snowmanx)) {
         ddk _snowmanxx = _snowman.d(_snowmanx);
         if (_snowman.d() == 1) {
            _snowman.j(_snowmanxx);
         } else if (_snowman.d() == 2) {
            _snowmanxx.a(_snowman.e());
            _snowmanxx.a(_snowman.c());
         }
      }
   }

   @Override
   public void a(rj var1) {
      ol.a(_snowman, this, this.f);
      ddn _snowman = this.g.G();
      String _snowmanx = _snowman.c();
      switch (_snowman.e()) {
         case a:
            ddk _snowmanxx = _snowman.c(_snowmanx);
            ddm _snowmanxxx = _snowman.c(_snowman.b(), _snowmanxx);
            _snowmanxxx.c(_snowman.d());
            break;
         case b:
            _snowman.d(_snowman.b(), _snowman.d(_snowmanx));
      }
   }

   @Override
   public void a(qz var1) {
      ol.a(_snowman, this, this.f);
      ddn _snowman = this.g.G();
      String _snowmanx = _snowman.c();
      ddk _snowmanxx = _snowmanx == null ? null : _snowman.c(_snowmanx);
      _snowman.a(_snowman.b(), _snowmanxx);
   }

   @Override
   public void a(ri var1) {
      ol.a(_snowman, this, this.f);
      ddn _snowman = this.g.G();
      ddl _snowmanx;
      if (_snowman.e() == 0) {
         _snowmanx = _snowman.g(_snowman.b());
      } else {
         _snowmanx = _snowman.f(_snowman.b());
      }

      if (_snowman.e() == 0 || _snowman.e() == 2) {
         _snowmanx.a(_snowman.c());
         _snowmanx.a(_snowman.g());
         _snowmanx.a(_snowman.f());
         ddp.b _snowmanxx = ddp.b.a(_snowman.h());
         if (_snowmanxx != null) {
            _snowmanx.a(_snowmanxx);
         }

         ddp.a _snowmanxxx = ddp.a.a(_snowman.i());
         if (_snowmanxxx != null) {
            _snowmanx.a(_snowmanxxx);
         }

         _snowmanx.b(_snowman.j());
         _snowmanx.c(_snowman.k());
      }

      if (_snowman.e() == 0 || _snowman.e() == 3) {
         for (String _snowmanxxx : _snowman.d()) {
            _snowman.a(_snowmanxxx, _snowmanx);
         }
      }

      if (_snowman.e() == 4) {
         for (String _snowmanxxx : _snowman.d()) {
            _snowman.b(_snowmanxxx, _snowmanx);
         }
      }

      if (_snowman.e() == 1) {
         _snowman.d(_snowmanx);
      }
   }

   @Override
   public void a(pv var1) {
      ol.a(_snowman, this, this.f);
      if (_snowman.j() == 0) {
         double _snowman = (double)(_snowman.i() * _snowman.f());
         double _snowmanx = (double)(_snowman.i() * _snowman.g());
         double _snowmanxx = (double)(_snowman.i() * _snowman.h());

         try {
            this.g.a(_snowman.k(), _snowman.b(), _snowman.c(), _snowman.d(), _snowman.e(), _snowman, _snowmanx, _snowmanxx);
         } catch (Throwable var17) {
            a.warn("Could not spawn particle effect {}", _snowman.k());
         }
      } else {
         for (int _snowman = 0; _snowman < _snowman.j(); _snowman++) {
            double _snowmanx = this.p.nextGaussian() * (double)_snowman.f();
            double _snowmanxx = this.p.nextGaussian() * (double)_snowman.g();
            double _snowmanxxx = this.p.nextGaussian() * (double)_snowman.h();
            double _snowmanxxxx = this.p.nextGaussian() * (double)_snowman.i();
            double _snowmanxxxxx = this.p.nextGaussian() * (double)_snowman.i();
            double _snowmanxxxxxx = this.p.nextGaussian() * (double)_snowman.i();

            try {
               this.g.a(_snowman.k(), _snowman.b(), _snowman.c() + _snowmanx, _snowman.d() + _snowmanxx, _snowman.e() + _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx);
            } catch (Throwable var16) {
               a.warn("Could not spawn particle effect {}", _snowman.k());
               return;
            }
         }
      }
   }

   @Override
   public void a(ru var1) {
      ol.a(_snowman, this, this.f);
      aqa _snowman = this.g.a(_snowman.b());
      if (_snowman != null) {
         if (!(_snowman instanceof aqm)) {
            throw new IllegalStateException("Server tried to update attributes of a non-living entity (actually: " + _snowman + ")");
         } else {
            ari _snowmanx = ((aqm)_snowman).dB();

            for (ru.a _snowmanxx : _snowman.c()) {
               arh _snowmanxxx = _snowmanx.a(_snowmanxx.a());
               if (_snowmanxxx == null) {
                  a.warn("Entity {} does not have attribute {}", _snowman, gm.af.b(_snowmanxx.a()));
               } else {
                  _snowmanxxx.a(_snowmanxx.b());
                  _snowmanxxx.e();

                  for (arj _snowmanxxxx : _snowmanxx.c()) {
                     _snowmanxxx.b(_snowmanxxxx);
                  }
               }
            }
         }
      }
   }

   @Override
   public void a(qf var1) {
      ol.a(_snowman, this, this.f);
      bic _snowman = this.f.s.bp;
      if (_snowman.b == _snowman.c() && _snowman.c(this.f.s)) {
         this.r.a(_snowman.b()).ifPresent(var2x -> {
            if (this.f.y instanceof drv) {
               drp _snowmanx = ((drv)this.f.y).k();
               _snowmanx.a((boq<?>)var2x, _snowman.a);
            }
         });
      }
   }

   @Override
   public void a(pw var1) {
      ol.a(_snowman, this, this.f);
      int _snowman = _snowman.b();
      int _snowmanx = _snowman.c();
      cuo _snowmanxx = this.g.n().l();
      int _snowmanxxx = _snowman.d();
      int _snowmanxxxx = _snowman.e();
      Iterator<byte[]> _snowmanxxxxx = _snowman.f().iterator();
      this.a(_snowman, _snowmanx, _snowmanxx, bsf.a, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, _snowman.j());
      int _snowmanxxxxxx = _snowman.g();
      int _snowmanxxxxxxx = _snowman.h();
      Iterator<byte[]> _snowmanxxxxxxxx = _snowman.i().iterator();
      this.a(_snowman, _snowmanx, _snowmanxx, bsf.b, _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx, _snowman.j());
   }

   @Override
   public void a(pz var1) {
      ol.a(_snowman, this, this.f);
      bic _snowman = this.f.s.bp;
      if (_snowman.b() == _snowman.b && _snowman instanceof bjg) {
         ((bjg)_snowman).a(new bqw(_snowman.c().a()));
         ((bjg)_snowman).e(_snowman.e());
         ((bjg)_snowman).f(_snowman.d());
         ((bjg)_snowman).a(_snowman.f());
         ((bjg)_snowman).b(_snowman.g());
      }
   }

   @Override
   public void a(qx var1) {
      ol.a(_snowman, this, this.f);
      this.o = _snowman.b();
      this.g.n().a(_snowman.b());
   }

   @Override
   public void a(qw var1) {
      ol.a(_snowman, this, this.f);
      this.g.n().e(_snowman.b(), _snowman.c());
   }

   @Override
   public void a(ou var1) {
      ol.a(_snowman, this, this.f);
      this.f.q.a(this.g, _snowman.c(), _snowman.b(), _snowman.e(), _snowman.d());
   }

   private void a(int var1, int var2, cuo var3, bsf var4, int var5, int var6, Iterator<byte[]> var7, boolean var8) {
      for (int _snowman = 0; _snowman < 18; _snowman++) {
         int _snowmanx = -1 + _snowman;
         boolean _snowmanxx = (_snowman & 1 << _snowman) != 0;
         boolean _snowmanxxx = (_snowman & 1 << _snowman) != 0;
         if (_snowmanxx || _snowmanxxx) {
            _snowman.a(_snowman, gp.a(_snowman, _snowmanx, _snowman), _snowmanxx ? new cgb((byte[])_snowman.next().clone()) : new cgb(), _snowman);
            this.g.d(_snowman, _snowmanx, _snowman);
         }
      }
   }

   @Override
   public nd a() {
      return this.c;
   }

   public Collection<dwx> e() {
      return this.j.values();
   }

   public Collection<UUID> f() {
      return this.j.keySet();
   }

   @Nullable
   public dwx a(UUID var1) {
      return this.j.get(_snowman);
   }

   @Nullable
   public dwx a(String var1) {
      for (dwx _snowman : this.j.values()) {
         if (_snowman.a().getName().equals(_snowman)) {
            return _snowman;
         }
      }

      return null;
   }

   public GameProfile g() {
      return this.d;
   }

   public dwq h() {
      return this.k;
   }

   public CommandDispatcher<dd> i() {
      return this.q;
   }

   public dwt j() {
      return this.g;
   }

   public aen k() {
      return this.m;
   }

   public djq l() {
      return this.n;
   }

   public UUID m() {
      return this.s;
   }

   public Set<vj<brx>> n() {
      return this.t;
   }

   public gn o() {
      return this.u;
   }
}
