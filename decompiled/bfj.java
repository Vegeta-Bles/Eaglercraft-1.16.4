import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;

public class bfj extends bfe implements aqz, bfl {
   private static final us<bfk> br = uv.a(bfj.class, uu.q);
   public static final Map<blx, Integer> bp = ImmutableMap.of(bmd.kX, 4, bmd.oZ, 1, bmd.oY, 1, bmd.qf, 1);
   private static final Set<blx> bs = ImmutableSet.of(bmd.kX, bmd.oZ, bmd.oY, bmd.kW, bmd.kV, bmd.qf, new blx[]{bmd.qg});
   private int bt;
   private boolean bu;
   @Nullable
   private bfw bv;
   private byte bx;
   private final axz by = new axz();
   private long bz;
   private long bA;
   private int bB;
   private long bC;
   private int bD;
   private long bE;
   private boolean bF;
   private static final ImmutableList<ayd<?>> bG = ImmutableList.of(
      ayd.b,
      ayd.c,
      ayd.d,
      ayd.e,
      ayd.g,
      ayd.h,
      ayd.i,
      ayd.j,
      ayd.k,
      ayd.l,
      ayd.J,
      ayd.m,
      new ayd[]{ayd.n, ayd.q, ayd.r, ayd.t, ayd.v, ayd.w, ayd.x, ayd.y, ayd.A, ayd.f, ayd.B, ayd.C, ayd.D, ayd.F, ayd.G, ayd.H, ayd.E}
   );
   private static final ImmutableList<azc<? extends azb<? super bfj>>> bH = ImmutableList.of(azc.c, azc.d, azc.b, azc.e, azc.f, azc.g, azc.h, azc.i, azc.j);
   public static final Map<ayd<gf>, BiPredicate<bfj, azr>> bq = ImmutableMap.of(
      ayd.b,
      (BiPredicate<bfj, azr>)(var0, var1) -> var1 == azr.r,
      ayd.c,
      (BiPredicate<bfj, azr>)(var0, var1) -> var0.eX().b().b() == var1,
      ayd.d,
      (BiPredicate<bfj, azr>)(var0, var1) -> azr.a.test(var1),
      ayd.e,
      (BiPredicate<bfj, azr>)(var0, var1) -> var1 == azr.s
   );

   public bfj(aqe<? extends bfj> var1, brx var2) {
      this(_snowman, _snowman, bfo.c);
   }

   public bfj(aqe<? extends bfj> var1, brx var2, bfo var3) {
      super(_snowman, _snowman);
      ((ayi)this.x()).a(true);
      this.x().d(true);
      this.p(true);
      this.a(this.eX().a(_snowman).a(bfm.a));
   }

   @Override
   public arf<bfj> cJ() {
      return (arf<bfj>)super.cJ();
   }

   @Override
   protected arf.b<bfj> cK() {
      return arf.a(bG, bH);
   }

   @Override
   protected arf<?> a(Dynamic<?> var1) {
      arf<bfj> _snowman = this.cK().a(_snowman);
      this.a(_snowman);
      return _snowman;
   }

   public void c(aag var1) {
      arf<bfj> _snowman = this.cJ();
      _snowman.b(_snowman, this);
      this.bg = _snowman.h();
      this.a(this.cJ());
   }

   private void a(arf<bfj> var1) {
      bfm _snowman = this.eX().b();
      if (this.w_()) {
         _snowman.a(bhh.c);
         _snowman.a(bhf.d, aul.a(0.5F));
      } else {
         _snowman.a(bhh.d);
         _snowman.a(bhf.c, aul.b(_snowman, 0.5F), ImmutableSet.of(Pair.of(ayd.c, aye.a)));
      }

      _snowman.a(bhf.a, aul.a(_snowman, 0.5F));
      _snowman.a(bhf.f, aul.d(_snowman, 0.5F), ImmutableSet.of(Pair.of(ayd.e, aye.a)));
      _snowman.a(bhf.e, aul.c(_snowman, 0.5F));
      _snowman.a(bhf.b, aul.e(_snowman, 0.5F));
      _snowman.a(bhf.g, aul.f(_snowman, 0.5F));
      _snowman.a(bhf.i, aul.g(_snowman, 0.5F));
      _snowman.a(bhf.h, aul.h(_snowman, 0.5F));
      _snowman.a(bhf.j, aul.i(_snowman, 0.5F));
      _snowman.a(ImmutableSet.of(bhf.a));
      _snowman.b(bhf.b);
      _snowman.a(bhf.b);
      _snowman.a(this.l.U(), this.l.T());
   }

   @Override
   protected void m() {
      super.m();
      if (this.l instanceof aag) {
         this.c((aag)this.l);
      }
   }

   public static ark.a eY() {
      return aqn.p().a(arl.d, 0.5).a(arl.b, 48.0);
   }

   public boolean eZ() {
      return this.bF;
   }

   @Override
   protected void N() {
      this.l.Z().a("villagerBrain");
      this.cJ().a((aag)this.l, this);
      this.l.Z().c();
      if (this.bF) {
         this.bF = false;
      }

      if (!this.eN() && this.bt > 0) {
         this.bt--;
         if (this.bt <= 0) {
            if (this.bu) {
               this.fu();
               this.bu = false;
            }

            this.c(new apu(apw.j, 200, 0));
         }
      }

      if (this.bv != null && this.l instanceof aag) {
         ((aag)this.l).a(azl.e, this.bv, this);
         this.l.a(this, (byte)14);
         this.bv = null;
      }

      if (!this.eD() && this.J.nextInt(100) == 0) {
         bhb _snowman = ((aag)this.l).b_(this.cB());
         if (_snowman != null && _snowman.v() && !_snowman.a()) {
            this.l.a(this, (byte)42);
         }
      }

      if (this.eX().b() == bfm.a && this.eN()) {
         this.eT();
      }

      super.N();
   }

   @Override
   public void j() {
      super.j();
      if (this.eK() > 0) {
         this.s(this.eK() - 1);
      }

      this.fw();
   }

   @Override
   public aou b(bfw var1, aot var2) {
      bmb _snowman = _snowman.b(_snowman);
      if (_snowman.b() == bmd.oG || !this.aX() || this.eN() || this.em()) {
         return super.b(_snowman, _snowman);
      } else if (this.w_()) {
         this.fk();
         return aou.a(this.l.v);
      } else {
         boolean _snowmanx = this.eO().isEmpty();
         if (_snowman == aot.a) {
            if (_snowmanx && !this.l.v) {
               this.fk();
            }

            _snowman.a(aea.R);
         }

         if (_snowmanx) {
            return aou.a(this.l.v);
         } else {
            if (!this.l.v && !this.bo.isEmpty()) {
               this.h(_snowman);
            }

            return aou.a(this.l.v);
         }
      }
   }

   private void fk() {
      this.s(40);
      if (!this.l.s_()) {
         this.a(adq.pW, this.dG(), this.dH());
      }
   }

   private void h(bfw var1) {
      this.i(_snowman);
      this.f(_snowman);
      this.a(_snowman, this.d(), this.eX().c());
   }

   @Override
   public void f(@Nullable bfw var1) {
      boolean _snowman = this.eM() != null && _snowman == null;
      super.f(_snowman);
      if (_snowman) {
         this.eT();
      }
   }

   @Override
   protected void eT() {
      super.eT();
      this.fl();
   }

   private void fl() {
      for (bqv _snowman : this.eO()) {
         _snowman.l();
      }
   }

   @Override
   public boolean fa() {
      return true;
   }

   public void fb() {
      this.fp();

      for (bqv _snowman : this.eO()) {
         _snowman.h();
      }

      this.bC = this.l.T();
      this.bD++;
   }

   private boolean fm() {
      for (bqv _snowman : this.eO()) {
         if (_snowman.r()) {
            return true;
         }
      }

      return false;
   }

   private boolean fn() {
      return this.bD == 0 || this.bD < 2 && this.l.T() > this.bC + 2400L;
   }

   public boolean fc() {
      long _snowman = this.bC + 12000L;
      long _snowmanx = this.l.T();
      boolean _snowmanxx = _snowmanx > _snowman;
      long _snowmanxxx = this.l.U();
      if (this.bE > 0L) {
         long _snowmanxxxx = this.bE / 24000L;
         long _snowmanxxxxx = _snowmanxxx / 24000L;
         _snowmanxx |= _snowmanxxxxx > _snowmanxxxx;
      }

      this.bE = _snowmanxxx;
      if (_snowmanxx) {
         this.bC = _snowmanx;
         this.fx();
      }

      return this.fn() && this.fm();
   }

   private void fo() {
      int _snowman = 2 - this.bD;
      if (_snowman > 0) {
         for (bqv _snowmanx : this.eO()) {
            _snowmanx.h();
         }
      }

      for (int _snowmanx = 0; _snowmanx < _snowman; _snowmanx++) {
         this.fp();
      }
   }

   private void fp() {
      for (bqv _snowman : this.eO()) {
         _snowman.e();
      }
   }

   private void i(bfw var1) {
      int _snowman = this.g(_snowman);
      if (_snowman != 0) {
         for (bqv _snowmanx : this.eO()) {
            _snowmanx.a(-afm.d((float)_snowman * _snowmanx.n()));
         }
      }

      if (_snowman.a(apw.F)) {
         apu _snowmanx = _snowman.b(apw.F);
         int _snowmanxx = _snowmanx.c();

         for (bqv _snowmanxxx : this.eO()) {
            double _snowmanxxxx = 0.3 + 0.0625 * (double)_snowmanxx;
            int _snowmanxxxxx = (int)Math.floor(_snowmanxxxx * (double)_snowmanxxx.a().E());
            _snowmanxxx.a(-Math.max(_snowmanxxxxx, 1));
         }
      }
   }

   @Override
   protected void e() {
      super.e();
      this.R.a(br, new bfk(bfo.c, bfm.a, 1));
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      bfk.a.encodeStart(mo.a, this.eX()).resultOrPartial(h::error).ifPresent(var1x -> _snowman.a("VillagerData", var1x));
      _snowman.a("FoodLevel", this.bx);
      _snowman.a("Gossips", (mt)this.by.a(mo.a).getValue());
      _snowman.b("Xp", this.bB);
      _snowman.a("LastRestock", this.bC);
      _snowman.a("LastGossipDecay", this.bA);
      _snowman.b("RestocksToday", this.bD);
      if (this.bF) {
         _snowman.a("AssignProfessionWhenSpawned", true);
      }
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      if (_snowman.c("VillagerData", 10)) {
         DataResult<bfk> _snowman = bfk.a.parse(new Dynamic(mo.a, _snowman.c("VillagerData")));
         _snowman.resultOrPartial(h::error).ifPresent(this::a);
      }

      if (_snowman.c("Offers", 10)) {
         this.bo = new bqw(_snowman.p("Offers"));
      }

      if (_snowman.c("FoodLevel", 1)) {
         this.bx = _snowman.f("FoodLevel");
      }

      mj _snowman = _snowman.d("Gossips", 10);
      this.by.a(new Dynamic(mo.a, _snowman));
      if (_snowman.c("Xp", 3)) {
         this.bB = _snowman.h("Xp");
      }

      this.bC = _snowman.i("LastRestock");
      this.bA = _snowman.i("LastGossipDecay");
      this.p(true);
      if (this.l instanceof aag) {
         this.c((aag)this.l);
      }

      this.bD = _snowman.h("RestocksToday");
      if (_snowman.e("AssignProfessionWhenSpawned")) {
         this.bF = _snowman.q("AssignProfessionWhenSpawned");
      }
   }

   @Override
   public boolean h(double var1) {
      return false;
   }

   @Nullable
   @Override
   protected adp I() {
      if (this.em()) {
         return null;
      } else {
         return this.eN() ? adq.pX : adq.pS;
      }
   }

   @Override
   protected adp e(apk var1) {
      return adq.pV;
   }

   @Override
   protected adp dq() {
      return adq.pU;
   }

   public void fd() {
      adp _snowman = this.eX().b().e();
      if (_snowman != null) {
         this.a(_snowman, this.dG(), this.dH());
      }
   }

   public void a(bfk var1) {
      bfk _snowman = this.eX();
      if (_snowman.b() != _snowman.b()) {
         this.bo = null;
      }

      this.R.b(br, _snowman);
   }

   @Override
   public bfk eX() {
      return this.R.a(br);
   }

   @Override
   protected void b(bqv var1) {
      int _snowman = 3 + this.J.nextInt(4);
      this.bB = this.bB + _snowman.o();
      this.bv = this.eM();
      if (this.ft()) {
         this.bt = 40;
         this.bu = true;
         _snowman += 5;
      }

      if (_snowman.s()) {
         this.l.c(new aqg(this.l, this.cD(), this.cE() + 0.5, this.cH(), _snowman));
      }
   }

   @Override
   public void a(@Nullable aqm var1) {
      if (_snowman != null && this.l instanceof aag) {
         ((aag)this.l).a(azl.c, _snowman, this);
         if (this.aX() && _snowman instanceof bfw) {
            this.l.a(this, (byte)13);
         }
      }

      super.a(_snowman);
   }

   @Override
   public void a(apk var1) {
      h.info("Villager {} died, message: '{}'", this, _snowman.a((aqm)this).getString());
      aqa _snowman = _snowman.k();
      if (_snowman != null) {
         this.a(_snowman);
      }

      this.fq();
      super.a(_snowman);
   }

   private void fq() {
      this.a(ayd.b);
      this.a(ayd.c);
      this.a(ayd.d);
      this.a(ayd.e);
   }

   private void a(aqa var1) {
      if (this.l instanceof aag) {
         Optional<List<aqm>> _snowman = this.bg.c(ayd.h);
         if (_snowman.isPresent()) {
            aag _snowmanx = (aag)this.l;
            _snowman.get().stream().filter(var0 -> var0 instanceof aqz).forEach(var2x -> _snowman.a(azl.d, _snowman, (aqz)var2x));
         }
      }
   }

   public void a(ayd<gf> var1) {
      if (this.l instanceof aag) {
         MinecraftServer _snowman = ((aag)this.l).l();
         this.bg.c(_snowman).ifPresent(var3 -> {
            aag _snowmanx = _snowman.a(var3.a());
            if (_snowmanx != null) {
               azo _snowmanx = _snowmanx.y();
               Optional<azr> _snowmanxx = _snowmanx.c(var3.b());
               BiPredicate<bfj, azr> _snowmanxxx = bq.get(_snowman);
               if (_snowmanxx.isPresent() && _snowmanxxx.test(this, _snowmanxx.get())) {
                  _snowmanx.b(var3.b());
                  rz.c(_snowmanx, var3.b());
               }
            }
         });
      }
   }

   @Override
   public boolean f() {
      return this.bx + this.fv() >= 12 && this.i() == 0;
   }

   private boolean fr() {
      return this.bx < 12;
   }

   private void fs() {
      if (this.fr() && this.fv() != 0) {
         for (int _snowman = 0; _snowman < this.eU().Z_(); _snowman++) {
            bmb _snowmanx = this.eU().a(_snowman);
            if (!_snowmanx.a()) {
               Integer _snowmanxx = bp.get(_snowmanx.b());
               if (_snowmanxx != null) {
                  int _snowmanxxx = _snowmanx.E();

                  for (int _snowmanxxxx = _snowmanxxx; _snowmanxxxx > 0; _snowmanxxxx--) {
                     this.bx = (byte)(this.bx + _snowmanxx);
                     this.eU().a(_snowman, 1);
                     if (!this.fr()) {
                        return;
                     }
                  }
               }
            }
         }
      }
   }

   public int g(bfw var1) {
      return this.by.a(_snowman.bS(), var0 -> true);
   }

   private void v(int var1) {
      this.bx = (byte)(this.bx - _snowman);
   }

   public void ff() {
      this.fs();
      this.v(12);
   }

   public void b(bqw var1) {
      this.bo = _snowman;
   }

   private boolean ft() {
      int _snowman = this.eX().c();
      return bfk.d(_snowman) && this.bB >= bfk.c(_snowman);
   }

   private void fu() {
      this.a(this.eX().a(this.eX().c() + 1));
      this.eW();
   }

   @Override
   protected nr bJ() {
      return new of(this.X().f() + '.' + gm.ai.b(this.eX().b()).a());
   }

   @Override
   public void a(byte var1) {
      if (_snowman == 12) {
         this.a(hh.G);
      } else if (_snowman == 13) {
         this.a(hh.b);
      } else if (_snowman == 14) {
         this.a(hh.E);
      } else if (_snowman == 42) {
         this.a(hh.Z);
      } else {
         super.a(_snowman);
      }
   }

   @Nullable
   @Override
   public arc a(bsk var1, aos var2, aqp var3, @Nullable arc var4, @Nullable md var5) {
      if (_snowman == aqp.e) {
         this.a(this.eX().a(bfm.a));
      }

      if (_snowman == aqp.n || _snowman == aqp.m || _snowman == aqp.c || _snowman == aqp.o) {
         this.a(this.eX().a(bfo.a(_snowman.i(this.cB()))));
      }

      if (_snowman == aqp.d) {
         this.bF = true;
      }

      return super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public bfj b(aag var1, apy var2) {
      double _snowman = this.J.nextDouble();
      bfo _snowmanx;
      if (_snowman < 0.5) {
         _snowmanx = bfo.a(_snowman.i(this.cB()));
      } else if (_snowman < 0.75) {
         _snowmanx = this.eX().a();
      } else {
         _snowmanx = ((bfj)_snowman).eX().a();
      }

      bfj _snowmanxx = new bfj(aqe.aP, _snowman, _snowmanx);
      _snowmanxx.a(_snowman, _snowman.d(_snowmanxx.cB()), aqp.e, null, null);
      return _snowmanxx;
   }

   @Override
   public void a(aag var1, aql var2) {
      if (_snowman.ad() != aor.a) {
         h.info("Villager {} was struck by lightning {}.", this, _snowman);
         beg _snowman = aqe.aS.a(_snowman);
         _snowman.b(this.cD(), this.cE(), this.cH(), this.p, this.q);
         _snowman.a(_snowman, _snowman.d(_snowman.cB()), aqp.i, null, null);
         _snowman.q(this.eD());
         if (this.S()) {
            _snowman.a(this.T());
            _snowman.n(this.bX());
         }

         _snowman.es();
         _snowman.l(_snowman);
         this.fq();
         this.ad();
      } else {
         super.a(_snowman, _snowman);
      }
   }

   @Override
   protected void b(bcv var1) {
      bmb _snowman = _snowman.g();
      if (this.i(_snowman)) {
         apa _snowmanx = this.eU();
         boolean _snowmanxx = _snowmanx.b(_snowman);
         if (!_snowmanxx) {
            return;
         }

         this.a(_snowman);
         this.a(_snowman, _snowman.E());
         bmb _snowmanxxx = _snowmanx.a(_snowman);
         if (_snowmanxxx.a()) {
            _snowman.ad();
         } else {
            _snowman.e(_snowmanxxx.E());
         }
      }
   }

   @Override
   public boolean i(bmb var1) {
      blx _snowman = _snowman.b();
      return (bs.contains(_snowman) || this.eX().b().c().contains(_snowman)) && this.eU().b(_snowman);
   }

   public boolean fg() {
      return this.fv() >= 24;
   }

   public boolean fh() {
      return this.fv() < 12;
   }

   private int fv() {
      apa _snowman = this.eU();
      return bp.entrySet().stream().mapToInt(var1x -> _snowman.a(var1x.getKey()) * var1x.getValue()).sum();
   }

   public boolean fi() {
      return this.eU().a(ImmutableSet.of(bmd.kV, bmd.oZ, bmd.oY, bmd.qg));
   }

   @Override
   protected void eW() {
      bfk _snowman = this.eX();
      Int2ObjectMap<bfn.f[]> _snowmanx = bfn.a.get(_snowman.b());
      if (_snowmanx != null && !_snowmanx.isEmpty()) {
         bfn.f[] _snowmanxx = (bfn.f[])_snowmanx.get(_snowman.c());
         if (_snowmanxx != null) {
            bqw _snowmanxxx = this.eO();
            this.a(_snowmanxxx, _snowmanxx, 2);
         }
      }
   }

   public void a(aag var1, bfj var2, long var3) {
      if ((_snowman < this.bz || _snowman >= this.bz + 1200L) && (_snowman < _snowman.bz || _snowman >= _snowman.bz + 1200L)) {
         this.by.a(_snowman.by, this.J, 10);
         this.bz = _snowman;
         _snowman.bz = _snowman;
         this.a(_snowman, _snowman, 5);
      }
   }

   private void fw() {
      long _snowman = this.l.T();
      if (this.bA == 0L) {
         this.bA = _snowman;
      } else if (_snowman >= this.bA + 24000L) {
         this.by.b();
         this.bA = _snowman;
      }
   }

   public void a(aag var1, long var2, int var4) {
      if (this.a(_snowman)) {
         dci _snowman = this.cc().c(10.0, 10.0, 10.0);
         List<bfj> _snowmanx = _snowman.a(bfj.class, _snowman);
         List<bfj> _snowmanxx = _snowmanx.stream().filter(var2x -> var2x.a(_snowman)).limit(5L).collect(Collectors.toList());
         if (_snowmanxx.size() >= _snowman) {
            bai _snowmanxxx = this.d(_snowman);
            if (_snowmanxxx != null) {
               _snowmanx.forEach(ayq::b);
            }
         }
      }
   }

   public boolean a(long var1) {
      return !this.b(this.l.T()) ? false : !this.bg.a(ayd.E);
   }

   @Nullable
   private bai d(aag var1) {
      fx _snowman = this.cB();

      for (int _snowmanx = 0; _snowmanx < 10; _snowmanx++) {
         double _snowmanxx = (double)(_snowman.t.nextInt(16) - 8);
         double _snowmanxxx = (double)(_snowman.t.nextInt(16) - 8);
         fx _snowmanxxxx = this.a(_snowman, _snowmanxx, _snowmanxxx);
         if (_snowmanxxxx != null) {
            bai _snowmanxxxxx = aqe.K.b(_snowman, null, null, null, _snowmanxxxx, aqp.f, false, false);
            if (_snowmanxxxxx != null) {
               if (_snowmanxxxxx.a(_snowman, aqp.f) && _snowmanxxxxx.a(_snowman)) {
                  _snowman.l(_snowmanxxxxx);
                  return _snowmanxxxxx;
               }

               _snowmanxxxxx.ad();
            }
         }
      }

      return null;
   }

   @Nullable
   private fx a(fx var1, double var2, double var4) {
      int _snowman = 6;
      fx _snowmanx = _snowman.a(_snowman, 6.0, _snowman);
      ceh _snowmanxx = this.l.d_(_snowmanx);

      for (int _snowmanxxx = 6; _snowmanxxx >= -6; _snowmanxxx--) {
         fx _snowmanxxxx = _snowmanx;
         ceh _snowmanxxxxx = _snowmanxx;
         _snowmanx = _snowmanx.c();
         _snowmanxx = this.l.d_(_snowmanx);
         if ((_snowmanxxxxx.g() || _snowmanxxxxx.c().a()) && _snowmanxx.c().f()) {
            return _snowmanxxxx;
         }
      }

      return null;
   }

   @Override
   public void a(azl var1, aqa var2) {
      if (_snowman == azl.a) {
         this.by.a(_snowman.bS(), aya.d, 20);
         this.by.a(_snowman.bS(), aya.c, 25);
      } else if (_snowman == azl.e) {
         this.by.a(_snowman.bS(), aya.e, 2);
      } else if (_snowman == azl.c) {
         this.by.a(_snowman.bS(), aya.b, 25);
      } else if (_snowman == azl.d) {
         this.by.a(_snowman.bS(), aya.a, 25);
      }
   }

   @Override
   public int eL() {
      return this.bB;
   }

   public void u(int var1) {
      this.bB = _snowman;
   }

   private void fx() {
      this.fo();
      this.bD = 0;
   }

   public axz fj() {
      return this.by;
   }

   public void a(mt var1) {
      this.by.a(new Dynamic(mo.a, _snowman));
   }

   @Override
   protected void M() {
      super.M();
      rz.a(this);
   }

   @Override
   public void b(fx var1) {
      super.b(_snowman);
      this.bg.a(ayd.F, this.l.T());
      this.bg.b(ayd.m);
      this.bg.b(ayd.D);
   }

   @Override
   public void en() {
      super.en();
      this.bg.a(ayd.G, this.l.T());
   }

   private boolean b(long var1) {
      Optional<Long> _snowman = this.bg.c(ayd.F);
      return _snowman.isPresent() ? _snowman - _snowman.get() < 24000L : false;
   }
}
