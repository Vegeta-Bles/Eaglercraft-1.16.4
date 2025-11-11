import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.util.Either;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class aah extends bfw implements bin {
   private static final Logger bJ = LogManager.getLogger();
   public aay b;
   public final MinecraftServer c;
   public final aai d;
   private final List<Integer> bK = Lists.newLinkedList();
   private final vt bL;
   private final adw bM;
   private float bN = Float.MIN_VALUE;
   private int bO = Integer.MIN_VALUE;
   private int bP = Integer.MIN_VALUE;
   private int bQ = Integer.MIN_VALUE;
   private int bR = Integer.MIN_VALUE;
   private int bS = Integer.MIN_VALUE;
   private float bT = -1.0E8F;
   private int bU = -99999999;
   private boolean bV = true;
   private int bW = -99999999;
   private int bX = 60;
   private bfu bY;
   private boolean bZ = true;
   private long ca = x.b();
   private aqa cb;
   private boolean cc;
   private boolean cd;
   private final adv ce = new adv();
   private dcn cf;
   private int cg;
   private boolean ch;
   @Nullable
   private dcn ci;
   private gp cj = gp.a(0, 0, 0);
   private vj<brx> ck = brx.g;
   @Nullable
   private fx cl;
   private boolean cm;
   private float cn;
   @Nullable
   private final abc co;
   private int cp;
   public boolean e;
   public int f;
   public boolean g;

   public aah(MinecraftServer var1, aag var2, GameProfile var3, aai var4) {
      super(_snowman, _snowman.u(), _snowman.v(), _snowman);
      _snowman.b = this;
      this.d = _snowman;
      this.c = _snowman;
      this.bM = _snowman.ae().a((bfw)this);
      this.bL = _snowman.ae().f(this);
      this.G = 1.0F;
      this.c(_snowman);
      this.co = _snowman.a(this);
   }

   private void c(aag var1) {
      fx _snowman = _snowman.u();
      if (_snowman.k().b() && _snowman.l().aX().m() != bru.d) {
         int _snowmanx = Math.max(0, this.c.a(_snowman));
         int _snowmanxx = afm.c(_snowman.f().b((double)_snowman.u(), (double)_snowman.w()));
         if (_snowmanxx < _snowmanx) {
            _snowmanx = _snowmanxx;
         }

         if (_snowmanxx <= 1) {
            _snowmanx = 1;
         }

         long _snowmanxxx = (long)(_snowmanx * 2 + 1);
         long _snowmanxxxx = _snowmanxxx * _snowmanxxx;
         int _snowmanxxxxx = _snowmanxxxx > 2147483647L ? Integer.MAX_VALUE : (int)_snowmanxxxx;
         int _snowmanxxxxxx = this.u(_snowmanxxxxx);
         int _snowmanxxxxxxx = new Random().nextInt(_snowmanxxxxx);

         for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < _snowmanxxxxx; _snowmanxxxxxxxx++) {
            int _snowmanxxxxxxxxx = (_snowmanxxxxxxx + _snowmanxxxxxx * _snowmanxxxxxxxx) % _snowmanxxxxx;
            int _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx % (_snowmanx * 2 + 1);
            int _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxx / (_snowmanx * 2 + 1);
            fx _snowmanxxxxxxxxxxxx = aab.a(_snowman, _snowman.u() + _snowmanxxxxxxxxxx - _snowmanx, _snowman.w() + _snowmanxxxxxxxxxxx - _snowmanx, false);
            if (_snowmanxxxxxxxxxxxx != null) {
               this.a(_snowmanxxxxxxxxxxxx, 0.0F, 0.0F);
               if (_snowman.k(this)) {
                  break;
               }
            }
         }
      } else {
         this.a(_snowman, 0.0F, 0.0F);

         while (!_snowman.k(this) && this.cE() < 255.0) {
            this.d(this.cD(), this.cE() + 1.0, this.cH());
         }
      }
   }

   private int u(int var1) {
      return _snowman <= 16 ? _snowman - 1 : 17;
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      if (_snowman.c("playerGameType", 99)) {
         if (this.ch().al()) {
            this.d.a(this.ch().s(), bru.a);
         } else {
            this.d.a(bru.a(_snowman.h("playerGameType")), _snowman.c("previousPlayerGameType", 3) ? bru.a(_snowman.h("previousPlayerGameType")) : bru.a);
         }
      }

      if (_snowman.c("enteredNetherPosition", 10)) {
         md _snowman = _snowman.p("enteredNetherPosition");
         this.ci = new dcn(_snowman.k("x"), _snowman.k("y"), _snowman.k("z"));
      }

      this.cd = _snowman.q("seenCredits");
      if (_snowman.c("recipeBook", 10)) {
         this.ce.a(_snowman.p("recipeBook"), this.c.aF());
      }

      if (this.em()) {
         this.en();
      }

      if (_snowman.c("SpawnX", 99) && _snowman.c("SpawnY", 99) && _snowman.c("SpawnZ", 99)) {
         this.cl = new fx(_snowman.h("SpawnX"), _snowman.h("SpawnY"), _snowman.h("SpawnZ"));
         this.cm = _snowman.q("SpawnForced");
         this.cn = _snowman.j("SpawnAngle");
         if (_snowman.e("SpawnDimension")) {
            this.ck = brx.f.parse(mo.a, _snowman.c("SpawnDimension")).resultOrPartial(bJ::error).orElse(brx.g);
         }
      }
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      _snowman.b("playerGameType", this.d.b().a());
      _snowman.b("previousPlayerGameType", this.d.c().a());
      _snowman.a("seenCredits", this.cd);
      if (this.ci != null) {
         md _snowman = new md();
         _snowman.a("x", this.ci.b);
         _snowman.a("y", this.ci.c);
         _snowman.a("z", this.ci.d);
         _snowman.a("enteredNetherPosition", _snowman);
      }

      aqa _snowman = this.cr();
      aqa _snowmanx = this.ct();
      if (_snowmanx != null && _snowman != this && _snowman.cq()) {
         md _snowmanxx = new md();
         md _snowmanxxx = new md();
         _snowman.d(_snowmanxxx);
         _snowmanxx.a("Attach", _snowmanx.bS());
         _snowmanxx.a("Entity", _snowmanxxx);
         _snowman.a("RootVehicle", _snowmanxx);
      }

      _snowman.a("recipeBook", this.ce.b());
      _snowman.a("Dimension", this.l.Y().a().toString());
      if (this.cl != null) {
         _snowman.b("SpawnX", this.cl.u());
         _snowman.b("SpawnY", this.cl.v());
         _snowman.b("SpawnZ", this.cl.w());
         _snowman.a("SpawnForced", this.cm);
         _snowman.a("SpawnAngle", this.cn);
         vk.a.encodeStart(mo.a, this.ck.a()).resultOrPartial(bJ::error).ifPresent(var1x -> _snowman.a("SpawnDimension", var1x));
      }
   }

   public void a(int var1) {
      float _snowman = (float)this.eH();
      float _snowmanx = (_snowman - 1.0F) / _snowman;
      this.bF = afm.a((float)_snowman / _snowman, 0.0F, _snowmanx);
      this.bW = -1;
   }

   public void b(int var1) {
      this.bD = _snowman;
      this.bW = -1;
   }

   @Override
   public void c(int var1) {
      super.c(_snowman);
      this.bW = -1;
   }

   @Override
   public void a(bmb var1, int var2) {
      super.a(_snowman, _snowman);
      this.bW = -1;
   }

   public void f() {
      this.bp.a((bin)this);
   }

   @Override
   public void g() {
      super.g();
      this.b.a(new qh(this.dv(), qh.a.a));
   }

   @Override
   public void h() {
      super.h();
      this.b.a(new qh(this.dv(), qh.a.b));
   }

   @Override
   protected void a(ceh var1) {
      ac.d.a(this, _snowman);
   }

   @Override
   protected bly i() {
      return new bmt(this);
   }

   @Override
   public void j() {
      this.d.a();
      this.bX--;
      if (this.P > 0) {
         this.P--;
      }

      this.bp.c();
      if (!this.l.v && !this.bp.a((bfw)this)) {
         this.m();
         this.bp = this.bo;
      }

      while (!this.bK.isEmpty()) {
         int _snowman = Math.min(this.bK.size(), Integer.MAX_VALUE);
         int[] _snowmanx = new int[_snowman];
         Iterator<Integer> _snowmanxx = this.bK.iterator();
         int _snowmanxxx = 0;

         while (_snowmanxx.hasNext() && _snowmanxxx < _snowman) {
            _snowmanx[_snowmanxxx++] = _snowmanxx.next();
            _snowmanxx.remove();
         }

         this.b.a(new qm(_snowmanx));
      }

      aqa _snowman = this.D();
      if (_snowman != this) {
         if (_snowman.aX()) {
            this.a(_snowman.cD(), _snowman.cE(), _snowman.cH(), _snowman.p, _snowman.q);
            this.u().i().a(this);
            if (this.er()) {
               this.e(this);
            }
         } else {
            this.e(this);
         }
      }

      ac.w.a(this);
      if (this.cf != null) {
         ac.u.a(this, this.cf, this.K - this.cg);
      }

      this.bL.b(this);
   }

   public void v_() {
      try {
         if (!this.a_() || this.l.C(this.cB())) {
            super.j();
         }

         for (int _snowman = 0; _snowman < this.bm.Z_(); _snowman++) {
            bmb _snowmanx = this.bm.a(_snowman);
            if (_snowmanx.b().ac_()) {
               oj<?> _snowmanxx = ((bkr)_snowmanx.b()).a(_snowmanx, this.l, this);
               if (_snowmanxx != null) {
                  this.b.a(_snowmanxx);
               }
            }
         }

         if (this.dk() != this.bT || this.bU != this.bq.a() || this.bq.e() == 0.0F != this.bV) {
            this.b.a(new rf(this.dk(), this.bq.a(), this.bq.e()));
            this.bT = this.dk();
            this.bU = this.bq.a();
            this.bV = this.bq.e() == 0.0F;
         }

         if (this.dk() + this.dT() != this.bN) {
            this.bN = this.dk() + this.dT();
            this.a(ddq.g, afm.f(this.bN));
         }

         if (this.bq.a() != this.bO) {
            this.bO = this.bq.a();
            this.a(ddq.h, afm.f((float)this.bO));
         }

         if (this.bI() != this.bP) {
            this.bP = this.bI();
            this.a(ddq.i, afm.f((float)this.bP));
         }

         if (this.du() != this.bQ) {
            this.bQ = this.du();
            this.a(ddq.j, afm.f((float)this.bQ));
         }

         if (this.bE != this.bS) {
            this.bS = this.bE;
            this.a(ddq.k, afm.f((float)this.bS));
         }

         if (this.bD != this.bR) {
            this.bR = this.bD;
            this.a(ddq.l, afm.f((float)this.bR));
         }

         if (this.bE != this.bW) {
            this.bW = this.bE;
            this.b.a(new re(this.bF, this.bE, this.bD));
         }

         if (this.K % 20 == 0) {
            ac.p.a(this);
         }
      } catch (Throwable var4) {
         l _snowmanx = l.a(var4, "Ticking player");
         m _snowmanxx = _snowmanx.a("Player being ticked");
         this.a(_snowmanxx);
         throw new u(_snowmanx);
      }
   }

   private void a(ddq var1, int var2) {
      this.eN().a(_snowman, this.bU(), var1x -> var1x.c(_snowman));
   }

   @Override
   public void a(apk var1) {
      boolean _snowman = this.l.V().b(brt.l);
      if (_snowman) {
         nr _snowmanx = this.dv().b();
         this.b.a(new qh(this.dv(), qh.a.c, _snowmanx), var2x -> {
            if (!var2x.isSuccess()) {
               int _snowmanxx = 256;
               String _snowmanx = _snowman.a(256);
               nr _snowmanxx = new of("death.attack.message_too_long", new oe(_snowmanx).a(k.o));
               nr _snowmanxxx = new of("death.attack.even_more_magic", this.d()).a(var1x -> var1x.a(new nv(nv.a.a, _snowman)));
               this.b.a(new qh(this.dv(), qh.a.c, _snowmanxxx));
            }
         });
         ddp _snowmanxx = this.bG();
         if (_snowmanxx == null || _snowmanxx.k() == ddp.b.a) {
            this.c.ae().a(_snowmanx, no.b, x.b);
         } else if (_snowmanxx.k() == ddp.b.c) {
            this.c.ae().a(this, _snowmanx);
         } else if (_snowmanxx.k() == ddp.b.d) {
            this.c.ae().b(this, _snowmanx);
         }
      } else {
         this.b.a(new qh(this.dv(), qh.a.c));
      }

      this.eM();
      if (this.l.V().b(brt.F)) {
         this.eW();
      }

      if (!this.a_()) {
         this.d(_snowman);
      }

      this.eN().a(ddq.d, this.bU(), ddm::a);
      aqm _snowmanx = this.dw();
      if (_snowmanx != null) {
         this.b(aea.h.b(_snowmanx.X()));
         _snowmanx.a(this, this.aO, _snowman);
         this.f(_snowmanx);
      }

      this.l.a(this, (byte)3);
      this.a(aea.M);
      this.a(aea.i.b(aea.l));
      this.a(aea.i.b(aea.m));
      this.am();
      this.b(0, false);
      this.dv().g();
   }

   private void eW() {
      dci _snowman = new dci(this.cB()).c(32.0, 10.0, 32.0);
      this.l.b(aqn.class, _snowman).stream().filter(var0 -> var0 instanceof aqs).forEach(var1x -> ((aqs)var1x).b(this));
   }

   @Override
   public void a(aqa var1, int var2, apk var3) {
      if (_snowman != this) {
         super.a(_snowman, _snowman, _snowman);
         this.t(_snowman);
         String _snowman = this.bU();
         String _snowmanx = _snowman.bU();
         this.eN().a(ddq.f, _snowman, ddm::a);
         if (_snowman instanceof bfw) {
            this.a(aea.P);
            this.eN().a(ddq.e, _snowman, ddm::a);
         } else {
            this.a(aea.N);
         }

         this.a(_snowman, _snowmanx, ddq.m);
         this.a(_snowmanx, _snowman, ddq.n);
         ac.b.a(this, _snowman, _snowman);
      }
   }

   private void a(String var1, String var2, ddq[] var3) {
      ddl _snowman = this.eN().i(_snowman);
      if (_snowman != null) {
         int _snowmanx = _snowman.n().b();
         if (_snowmanx >= 0 && _snowmanx < _snowman.length) {
            this.eN().a(_snowman[_snowmanx], _snowman, ddm::a);
         }
      }
   }

   @Override
   public boolean a(apk var1, float var2) {
      if (this.b(_snowman)) {
         return false;
      } else {
         boolean _snowman = this.c.j() && this.eX() && "fall".equals(_snowman.v);
         if (!_snowman && this.bX > 0 && _snowman != apk.m) {
            return false;
         } else {
            if (_snowman instanceof apl) {
               aqa _snowmanx = _snowman.k();
               if (_snowmanx instanceof bfw && !this.a((bfw)_snowmanx)) {
                  return false;
               }

               if (_snowmanx instanceof bga) {
                  bga _snowmanxx = (bga)_snowmanx;
                  aqa _snowmanxxx = _snowmanxx.v();
                  if (_snowmanxxx instanceof bfw && !this.a((bfw)_snowmanxxx)) {
                     return false;
                  }
               }
            }

            return super.a(_snowman, _snowman);
         }
      }
   }

   @Override
   public boolean a(bfw var1) {
      return !this.eX() ? false : super.a(_snowman);
   }

   private boolean eX() {
      return this.c.Z();
   }

   @Nullable
   @Override
   protected cxm a(aag var1) {
      cxm _snowman = super.a(_snowman);
      if (_snowman != null && this.l.Y() == brx.g && _snowman.Y() == brx.i) {
         dcn _snowmanx = _snowman.a.b(0.0, -1.0, 0.0);
         return new cxm(_snowmanx, dcn.a, 90.0F, 0.0F);
      } else {
         return _snowman;
      }
   }

   @Nullable
   @Override
   public aqa b(aag var1) {
      this.cc = true;
      aag _snowman = this.u();
      vj<brx> _snowmanx = _snowman.Y();
      if (_snowmanx == brx.i && _snowman.Y() == brx.g) {
         this.V();
         this.u().e(this);
         if (!this.g) {
            this.g = true;
            this.b.a(new pq(pq.e, this.cd ? 0.0F : 1.0F));
            this.cd = true;
         }

         return this;
      } else {
         cyd _snowmanxx = _snowman.h();
         this.b.a(new qp(_snowman.k(), _snowman.Y(), bsx.a(_snowman.C()), this.d.b(), this.d.c(), _snowman.ab(), _snowman.B(), true));
         this.b.a(new pa(_snowmanxx.s(), _snowmanxx.t()));
         acu _snowmanxxx = this.c.ae();
         _snowmanxxx.d(this);
         _snowman.e(this);
         this.y = false;
         cxm _snowmanxxxx = this.a(_snowman);
         if (_snowmanxxxx != null) {
            _snowman.Z().a("moving");
            if (_snowmanx == brx.g && _snowman.Y() == brx.h) {
               this.ci = this.cA();
            } else if (_snowman.Y() == brx.i) {
               this.a(_snowman, new fx(_snowmanxxxx.a));
            }

            _snowman.Z().c();
            _snowman.Z().a("placing");
            this.a_(_snowman);
            _snowman.b(this);
            this.a(_snowmanxxxx.c, _snowmanxxxx.d);
            this.b(_snowmanxxxx.a.b, _snowmanxxxx.a.c, _snowmanxxxx.a.d);
            _snowman.Z().c();
            this.d(_snowman);
            this.d.a(_snowman);
            this.b.a(new qg(this.bC));
            _snowmanxxx.a(this, _snowman);
            _snowmanxxx.e(this);

            for (apu _snowmanxxxxx : this.dh()) {
               this.b.a(new rv(this.Y(), _snowmanxxxxx));
            }

            this.b.a(new pu(1032, fx.b, 0, false));
            this.bW = -1;
            this.bT = -1.0F;
            this.bU = -1;
         }

         return this;
      }
   }

   private void a(aag var1, fx var2) {
      fx.a _snowman = _snowman.i();

      for (int _snowmanx = -2; _snowmanx <= 2; _snowmanx++) {
         for (int _snowmanxx = -2; _snowmanxx <= 2; _snowmanxx++) {
            for (int _snowmanxxx = -1; _snowmanxxx < 3; _snowmanxxx++) {
               ceh _snowmanxxxx = _snowmanxxx == -1 ? bup.bK.n() : bup.a.n();
               _snowman.a(_snowman.g(_snowman).e(_snowmanxx, _snowmanxxx, _snowmanx), _snowmanxxxx);
            }
         }
      }
   }

   @Override
   protected Optional<i.a> a(aag var1, fx var2, boolean var3) {
      Optional<i.a> _snowman = super.a(_snowman, _snowman, _snowman);
      if (_snowman.isPresent()) {
         return _snowman;
      } else {
         gc.a _snowmanx = this.l.d_(this.ac).d(byj.a).orElse(gc.a.a);
         Optional<i.a> _snowmanxx = _snowman.m().a(_snowman, _snowmanx);
         if (!_snowmanxx.isPresent()) {
            bJ.error("Unable to create a portal, likely target out of worldborder");
         }

         return _snowmanxx;
      }
   }

   private void d(aag var1) {
      vj<brx> _snowman = _snowman.Y();
      vj<brx> _snowmanx = this.l.Y();
      ac.v.a(this, _snowman, _snowmanx);
      if (_snowman == brx.h && _snowmanx == brx.g && this.ci != null) {
         ac.C.a(this, this.ci);
      }

      if (_snowmanx != brx.h) {
         this.ci = null;
      }
   }

   @Override
   public boolean a(aah var1) {
      if (_snowman.a_()) {
         return this.D() == this;
      } else {
         return this.a_() ? false : super.a(_snowman);
      }
   }

   private void a(ccj var1) {
      if (_snowman != null) {
         ow _snowman = _snowman.a();
         if (_snowman != null) {
            this.b.a(_snowman);
         }
      }
   }

   @Override
   public void a(aqa var1, int var2) {
      super.a(_snowman, _snowman);
      this.bp.c();
   }

   @Override
   public Either<bfw.a, afx> a(fx var1) {
      gc _snowman = this.l.d_(_snowman).c(bxm.aq);
      if (this.em() || !this.aX()) {
         return Either.left(bfw.a.e);
      } else if (!this.l.k().e()) {
         return Either.left(bfw.a.a);
      } else if (!this.a(_snowman, _snowman)) {
         return Either.left(bfw.a.c);
      } else if (this.b(_snowman, _snowman)) {
         return Either.left(bfw.a.d);
      } else {
         this.a(this.l.Y(), _snowman, this.p, false, true);
         if (this.l.M()) {
            return Either.left(bfw.a.b);
         } else {
            if (!this.b_()) {
               double _snowmanx = 8.0;
               double _snowmanxx = 5.0;
               dcn _snowmanxxx = dcn.c(_snowman);
               List<bdq> _snowmanxxxx = this.l
                  .a(bdq.class, new dci(_snowmanxxx.a() - 8.0, _snowmanxxx.b() - 5.0, _snowmanxxx.c() - 8.0, _snowmanxxx.a() + 8.0, _snowmanxxx.b() + 5.0, _snowmanxxx.c() + 8.0), var1x -> var1x.f(this));
               if (!_snowmanxxxx.isEmpty()) {
                  return Either.left(bfw.a.f);
               }
            }

            Either<bfw.a, afx> _snowmanx = super.a(_snowman).ifRight(var1x -> {
               this.a(aea.ao);
               ac.q.a(this);
            });
            ((aag)this.l).n_();
            return _snowmanx;
         }
      }
   }

   @Override
   public void b(fx var1) {
      this.a(aea.i.b(aea.m));
      super.b(_snowman);
   }

   private boolean a(fx var1, gc var2) {
      return this.g(_snowman) || this.g(_snowman.a(_snowman.f()));
   }

   private boolean g(fx var1) {
      dcn _snowman = dcn.c(_snowman);
      return Math.abs(this.cD() - _snowman.a()) <= 3.0 && Math.abs(this.cE() - _snowman.b()) <= 2.0 && Math.abs(this.cH() - _snowman.c()) <= 3.0;
   }

   private boolean b(fx var1, gc var2) {
      fx _snowman = _snowman.b();
      return !this.f(_snowman) || !this.f(_snowman.a(_snowman.f()));
   }

   @Override
   public void a(boolean var1, boolean var2) {
      if (this.em()) {
         this.u().i().a(this, new os(this, 2));
      }

      super.a(_snowman, _snowman);
      if (this.b != null) {
         this.b.a(this.cD(), this.cE(), this.cH(), this.p, this.q);
      }
   }

   @Override
   public boolean a(aqa var1, boolean var2) {
      aqa _snowman = this.ct();
      if (!super.a(_snowman, _snowman)) {
         return false;
      } else {
         aqa _snowmanx = this.ct();
         if (_snowmanx != _snowman && this.b != null) {
            this.b.a(this.cD(), this.cE(), this.cH(), this.p, this.q);
         }

         return true;
      }
   }

   @Override
   public void l() {
      aqa _snowman = this.ct();
      super.l();
      aqa _snowmanx = this.ct();
      if (_snowmanx != _snowman && this.b != null) {
         this.b.a(this.cD(), this.cE(), this.cH(), this.p, this.q);
      }
   }

   @Override
   public boolean b(apk var1) {
      return super.b(_snowman) || this.H() || this.bC.a && _snowman == apk.p;
   }

   @Override
   protected void a(double var1, boolean var3, ceh var4, fx var5) {
   }

   @Override
   protected void c(fx var1) {
      if (!this.a_()) {
         super.c(_snowman);
      }
   }

   public void a(double var1, boolean var3) {
      fx _snowman = this.ap();
      if (this.l.C(_snowman)) {
         super.a(_snowman, _snowman, this.l.d_(_snowman), _snowman);
      }
   }

   @Override
   public void a(cdf var1) {
      _snowman.a((bfw)this);
      this.b.a(new qe(_snowman.o()));
   }

   private void eY() {
      this.cp = this.cp % 100 + 1;
   }

   @Override
   public OptionalInt a(@Nullable aox var1) {
      if (_snowman == null) {
         return OptionalInt.empty();
      } else {
         if (this.bp != this.bo) {
            this.m();
         }

         this.eY();
         bic _snowman = _snowman.createMenu(this.cp, this.bm, this);
         if (_snowman == null) {
            if (this.a_()) {
               this.a(new of("container.spectatorCantOpen").a(k.m), true);
            }

            return OptionalInt.empty();
         } else {
            this.b.a(new qd(_snowman.b, _snowman.a(), _snowman.d()));
            _snowman.a((bin)this);
            this.bp = _snowman;
            return OptionalInt.of(this.cp);
         }
      }
   }

   @Override
   public void a(int var1, bqw var2, int var3, int var4, boolean var5, boolean var6) {
      this.b.a(new pz(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman));
   }

   @Override
   public void a(bbb var1, aon var2) {
      if (this.bp != this.bo) {
         this.m();
      }

      this.eY();
      this.b.a(new pr(this.cp, _snowman.Z_(), _snowman.Y()));
      this.bp = new biy(this.cp, this.bm, _snowman, _snowman);
      this.bp.a((bin)this);
   }

   @Override
   public void a(bmb var1, aot var2) {
      blx _snowman = _snowman.b();
      if (_snowman == bmd.oU) {
         if (bns.a(_snowman, this.cw(), this)) {
            this.bp.c();
         }

         this.b.a(new qc(_snowman));
      }
   }

   @Override
   public void a(cco var1) {
      _snowman.c(true);
      this.a((ccj)_snowman);
   }

   @Override
   public void a(bic var1, int var2, bmb var3) {
      if (!(_snowman.a(_snowman) instanceof bjn)) {
         if (_snowman == this.bo) {
            ac.e.a(this, this.bm, _snowman);
         }

         if (!this.e) {
            this.b.a(new pi(_snowman.b, _snowman, _snowman));
         }
      }
   }

   public void a(bic var1) {
      this.a(_snowman, _snowman.b());
   }

   @Override
   public void a(bic var1, gj<bmb> var2) {
      this.b.a(new pg(_snowman.b, _snowman));
      this.b.a(new pi(-1, -1, this.bm.m()));
   }

   @Override
   public void a(bic var1, int var2, int var3) {
      this.b.a(new ph(_snowman.b, _snowman, _snowman));
   }

   @Override
   public void m() {
      this.b.a(new pf(this.bp.b));
      this.o();
   }

   public void n() {
      if (!this.e) {
         this.b.a(new pi(-1, -1, this.bm.m()));
      }
   }

   @Override
   public void o() {
      this.bp.b((bfw)this);
      this.bp = this.bo;
   }

   public void a(float var1, float var2, boolean var3, boolean var4) {
      if (this.br()) {
         if (_snowman >= -1.0F && _snowman <= 1.0F) {
            this.aR = _snowman;
         }

         if (_snowman >= -1.0F && _snowman <= 1.0F) {
            this.aT = _snowman;
         }

         this.aQ = _snowman;
         this.f(_snowman);
      }
   }

   @Override
   public void a(adx<?> var1, int var2) {
      this.bM.b(this, _snowman, _snowman);
      this.eN().a(_snowman, this.bU(), var1x -> var1x.a(_snowman));
   }

   @Override
   public void a(adx<?> var1) {
      this.bM.a(this, _snowman, 0);
      this.eN().a(_snowman, this.bU(), ddm::c);
   }

   @Override
   public int a(Collection<boq<?>> var1) {
      return this.ce.a(_snowman, this);
   }

   @Override
   public void a(vk[] var1) {
      List<boq<?>> _snowman = Lists.newArrayList();

      for (vk _snowmanx : _snowman) {
         this.c.aF().a(_snowmanx).ifPresent(_snowman::add);
      }

      this.a(_snowman);
   }

   @Override
   public int b(Collection<boq<?>> var1) {
      return this.ce.b(_snowman, this);
   }

   @Override
   public void d(int var1) {
      super.d(_snowman);
      this.bW = -1;
   }

   @Override
   public void p() {
      this.ch = true;
      this.be();
      if (this.em()) {
         this.a(true, false);
      }
   }

   @Override
   public boolean q() {
      return this.ch;
   }

   @Override
   public void r() {
      this.bT = -1.0E8F;
   }

   @Override
   public void a(nr var1, boolean var2) {
      this.b.a(new pb(_snowman, _snowman ? no.c : no.a, x.b));
   }

   @Override
   protected void s() {
      if (!this.bc.a() && this.dW()) {
         this.b.a(new pn(this, (byte)9));
         super.s();
      }
   }

   @Override
   public void a(dj.a var1, dcn var2) {
      super.a(_snowman, _snowman);
      this.b.a(new qj(_snowman, _snowman.b, _snowman.c, _snowman.d));
   }

   public void a(dj.a var1, aqa var2, dj.a var3) {
      dcn _snowman = _snowman.a(_snowman);
      super.a(_snowman, _snowman);
      this.b.a(new qj(_snowman, _snowman, _snowman));
   }

   public void a(aah var1, boolean var2) {
      if (_snowman) {
         this.bm.a(_snowman.bm);
         this.c(_snowman.dk());
         this.bq = _snowman.bq;
         this.bD = _snowman.bD;
         this.bE = _snowman.bE;
         this.bF = _snowman.bF;
         this.s(_snowman.ev());
         this.ac = _snowman.ac;
      } else if (this.l.V().b(brt.c) || _snowman.a_()) {
         this.bm.a(_snowman.bm);
         this.bD = _snowman.bD;
         this.bE = _snowman.bE;
         this.bF = _snowman.bF;
         this.s(_snowman.ev());
      }

      this.bG = _snowman.bG;
      this.bn = _snowman.bn;
      this.ab().b(bi, _snowman.ab().a(bi));
      this.bW = -1;
      this.bT = -1.0F;
      this.bU = -1;
      this.ce.a(_snowman.ce);
      this.bK.addAll(_snowman.bK);
      this.cd = _snowman.cd;
      this.ci = _snowman.ci;
      this.h(_snowman.eP());
      this.i(_snowman.eQ());
   }

   @Override
   protected void a(apu var1) {
      super.a(_snowman);
      this.b.a(new rv(this.Y(), _snowman));
      if (_snowman.a() == apw.y) {
         this.cg = this.K;
         this.cf = this.cA();
      }

      ac.A.a(this);
   }

   @Override
   protected void a(apu var1, boolean var2) {
      super.a(_snowman, _snowman);
      this.b.a(new rv(this.Y(), _snowman));
      ac.A.a(this);
   }

   @Override
   protected void b(apu var1) {
      super.b(_snowman);
      this.b.a(new qn(this.Y(), _snowman.a()));
      if (_snowman.a() == apw.y) {
         this.cf = null;
      }

      ac.A.a(this);
   }

   @Override
   public void a(double var1, double var3, double var5) {
      this.b.a(_snowman, _snowman, _snowman, this.p, this.q);
   }

   @Override
   public void b(double var1, double var3, double var5) {
      this.a(_snowman, _snowman, _snowman);
      this.b.c();
   }

   @Override
   public void a(aqa var1) {
      this.u().i().a(this, new os(_snowman, 4));
   }

   @Override
   public void b(aqa var1) {
      this.u().i().a(this, new os(_snowman, 5));
   }

   @Override
   public void t() {
      if (this.b != null) {
         this.b.a(new qg(this.bC));
         this.C();
      }
   }

   public aag u() {
      return (aag)this.l;
   }

   @Override
   public void a(bru var1) {
      this.d.a(_snowman);
      this.b.a(new pq(pq.d, (float)_snowman.a()));
      if (_snowman == bru.e) {
         this.eM();
         this.l();
      } else {
         this.e(this);
      }

      this.t();
      this.dU();
   }

   @Override
   public boolean a_() {
      return this.d.b() == bru.e;
   }

   @Override
   public boolean b_() {
      return this.d.b() == bru.c;
   }

   @Override
   public void a(nr var1, UUID var2) {
      this.a(_snowman, no.b, _snowman);
   }

   public void a(nr var1, no var2, UUID var3) {
      this.b.a(new pb(_snowman, _snowman, _snowman), var4 -> {
         if (!var4.isSuccess() && (_snowman == no.c || _snowman == no.b)) {
            int _snowman = 256;
            String _snowmanx = _snowman.a(256);
            nr _snowmanxx = new oe(_snowmanx).a(k.o);
            this.b.a(new pb(new of("multiplayer.message_not_delivered", _snowmanxx).a(k.m), no.b, _snowman));
         }
      });
   }

   public String v() {
      String _snowman = this.b.a.c().toString();
      _snowman = _snowman.substring(_snowman.indexOf("/") + 1);
      return _snowman.substring(0, _snowman.indexOf(":"));
   }

   public void a(sg var1) {
      this.bY = _snowman.d();
      this.bZ = _snowman.e();
      this.ab().b(bi, (byte)_snowman.f());
      this.ab().b(bj, (byte)(_snowman.g() == aqi.a ? 0 : 1));
   }

   public bfu x() {
      return this.bY;
   }

   public void a(String var1, String var2) {
      this.b.a(new qo(_snowman, _snowman));
   }

   @Override
   protected int y() {
      return this.c.b(this.eA());
   }

   public void z() {
      this.ca = x.b();
   }

   public adw A() {
      return this.bM;
   }

   public adv B() {
      return this.ce;
   }

   @Override
   public void c(aqa var1) {
      if (_snowman instanceof bfw) {
         this.b.a(new qm(_snowman.Y()));
      } else {
         this.bK.add(_snowman.Y());
      }
   }

   @Override
   public void d(aqa var1) {
      this.bK.remove(Integer.valueOf(_snowman.Y()));
   }

   @Override
   protected void C() {
      if (this.a_()) {
         this.df();
         this.j(true);
      } else {
         super.C();
      }
   }

   public aqa D() {
      return (aqa)(this.cb == null ? this : this.cb);
   }

   public void e(aqa var1) {
      aqa _snowman = this.D();
      this.cb = (aqa)(_snowman == null ? this : _snowman);
      if (_snowman != this.cb) {
         this.b.a(new qu(this.cb));
         this.a(this.cb.cD(), this.cb.cE(), this.cb.cH());
      }
   }

   @Override
   protected void E() {
      if (!this.cc) {
         super.E();
      }
   }

   @Override
   public void f(aqa var1) {
      if (this.d.b() == bru.e) {
         this.e(_snowman);
      } else {
         super.f(_snowman);
      }
   }

   public long F() {
      return this.ca;
   }

   @Nullable
   public nr G() {
      return null;
   }

   @Override
   public void a(aot var1) {
      super.a(_snowman);
      this.eS();
   }

   public boolean H() {
      return this.cc;
   }

   public void I() {
      this.cc = false;
   }

   public vt J() {
      return this.bL;
   }

   public void a(aag var1, double var2, double var4, double var6, float var8, float var9) {
      this.e(this);
      this.l();
      if (_snowman == this.l) {
         this.b.a(_snowman, _snowman, _snowman, _snowman, _snowman);
      } else {
         aag _snowman = this.u();
         cyd _snowmanx = _snowman.h();
         this.b.a(new qp(_snowman.k(), _snowman.Y(), bsx.a(_snowman.C()), this.d.b(), this.d.c(), _snowman.ab(), _snowman.B(), true));
         this.b.a(new pa(_snowmanx.s(), _snowmanx.t()));
         this.c.ae().d(this);
         _snowman.e(this);
         this.y = false;
         this.b(_snowman, _snowman, _snowman, _snowman, _snowman);
         this.a_(_snowman);
         _snowman.a(this);
         this.d(_snowman);
         this.b.a(_snowman, _snowman, _snowman, _snowman, _snowman);
         this.d.a(_snowman);
         this.c.ae().a(this, _snowman);
         this.c.ae().e(this);
      }
   }

   @Nullable
   public fx K() {
      return this.cl;
   }

   public float L() {
      return this.cn;
   }

   public vj<brx> M() {
      return this.ck;
   }

   public boolean N() {
      return this.cm;
   }

   public void a(vj<brx> var1, @Nullable fx var2, float var3, boolean var4, boolean var5) {
      if (_snowman != null) {
         boolean _snowman = _snowman.equals(this.cl) && _snowman.equals(this.ck);
         if (_snowman && !_snowman) {
            this.a(new of("block.minecraft.set_spawn"), x.b);
         }

         this.cl = _snowman;
         this.ck = _snowman;
         this.cn = _snowman;
         this.cm = _snowman;
      } else {
         this.cl = null;
         this.ck = brx.g;
         this.cn = 0.0F;
         this.cm = false;
      }
   }

   public void a(brd var1, oj<?> var2, oj<?> var3) {
      this.b.a(_snowman);
      this.b.a(_snowman);
   }

   public void a(brd var1) {
      if (this.aX()) {
         this.b.a(new pp(_snowman.b, _snowman.c));
      }
   }

   public gp O() {
      return this.cj;
   }

   public void a(gp var1) {
      this.cj = _snowman;
   }

   @Override
   public void a(adp var1, adr var2, float var3, float var4) {
      this.b.a(new rn(_snowman, _snowman, this.cD(), this.cE(), this.cH(), _snowman, _snowman));
   }

   @Override
   public oj<?> P() {
      return new or(this);
   }

   @Override
   public bcv a(bmb var1, boolean var2, boolean var3) {
      bcv _snowman = super.a(_snowman, _snowman, _snowman);
      if (_snowman == null) {
         return null;
      } else {
         this.l.c(_snowman);
         bmb _snowmanx = _snowman.g();
         if (_snowman) {
            if (!_snowmanx.a()) {
               this.a(aea.f.b(_snowmanx.b()), _snowman.E());
            }

            this.a(aea.E);
         }

         return _snowman;
      }
   }

   @Nullable
   public abc Q() {
      return this.co;
   }
}
