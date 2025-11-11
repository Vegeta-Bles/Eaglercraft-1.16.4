import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Optional;

public class bei extends bdq implements bdi, beo {
   private static final us<Boolean> d = uv.a(bei.class, uu.i);
   private int bo;
   protected static final ImmutableList<? extends azc<? extends azb<? super bei>>> b = ImmutableList.of(azc.c, azc.d);
   protected static final ImmutableList<? extends ayd<?>> c = ImmutableList.of(ayd.g, ayd.h, ayd.k, ayd.l, ayd.n, ayd.m, ayd.D, ayd.t, ayd.o, ayd.p);

   public bei(aqe<? extends bei> var1, brx var2) {
      super(_snowman, _snowman);
      this.f = 5;
   }

   @Override
   protected arf.b<bei> cK() {
      return arf.a(c, b);
   }

   @Override
   protected arf<?> a(Dynamic<?> var1) {
      arf<bei> _snowman = this.cK().a(_snowman);
      a(_snowman);
      b(_snowman);
      c(_snowman);
      _snowman.a(ImmutableSet.of(bhf.a));
      _snowman.b(bhf.b);
      _snowman.e();
      return _snowman;
   }

   private static void a(arf<bei> var0) {
      _snowman.a(bhf.a, 0, ImmutableList.of(new asu(45, 90), new asy()));
   }

   private static void b(arf<bei> var0) {
      _snowman.a(
         bhf.b,
         10,
         ImmutableList.of(
            new atw(bei::eO),
            new atj<>(new atl(8.0F), afh.a(30, 60)),
            new ati(ImmutableList.of(Pair.of(new atc(0.4F), 2), Pair.of(new ats(0.4F, 3), 2), Pair.of(new asc(30, 60), 1)))
         )
      );
   }

   private static void c(arf<bei> var0) {
      _snowman.a(bhf.k, 10, ImmutableList.of(new atq(1.0F), new ath<>(bei::eK, new asv(40)), new ath<>(bei::w_, new asv(15)), new aty()), ayd.o);
   }

   private Optional<? extends aqm> eO() {
      return this.cJ().c(ayd.h).orElse(ImmutableList.of()).stream().filter(bei::i).findFirst();
   }

   private static boolean i(aqm var0) {
      aqe<?> _snowman = _snowman.X();
      return _snowman != aqe.aX && _snowman != aqe.m && aqd.f.test(_snowman);
   }

   @Override
   protected void e() {
      super.e();
      this.R.a(d, false);
   }

   @Override
   public void a(us<?> var1) {
      super.a(_snowman);
      if (d.equals(_snowman)) {
         this.x_();
      }
   }

   public static ark.a m() {
      return bdq.eR().a(arl.a, 40.0).a(arl.d, 0.3F).a(arl.c, 0.6F).a(arl.g, 1.0).a(arl.f, 6.0);
   }

   public boolean eK() {
      return !this.w_();
   }

   @Override
   public boolean B(aqa var1) {
      if (!(_snowman instanceof aqm)) {
         return false;
      } else {
         this.bo = 10;
         this.l.a(this, (byte)4);
         this.a(adq.rD, 1.0F, this.dH());
         return beo.a(this, (aqm)_snowman);
      }
   }

   @Override
   public boolean a(bfw var1) {
      return !this.eB();
   }

   @Override
   protected void e(aqm var1) {
      if (!this.w_()) {
         beo.b(this, _snowman);
      }
   }

   @Override
   public double bc() {
      return (double)this.cz() - (this.w_() ? 0.2 : 0.15);
   }

   @Override
   public boolean a(apk var1, float var2) {
      boolean _snowman = super.a(_snowman, _snowman);
      if (this.l.v) {
         return false;
      } else if (_snowman && _snowman.k() instanceof aqm) {
         aqm _snowmanx = (aqm)_snowman.k();
         if (aqd.f.test(_snowmanx) && !arw.a(this, _snowmanx, 4.0)) {
            this.j(_snowmanx);
         }

         return _snowman;
      } else {
         return _snowman;
      }
   }

   private void j(aqm var1) {
      this.bg.b(ayd.D);
      this.bg.a(ayd.o, _snowman, 200L);
   }

   @Override
   public arf<bei> cJ() {
      return (arf<bei>)super.cJ();
   }

   protected void eL() {
      bhf _snowman = this.bg.f().orElse(null);
      this.bg.a(ImmutableList.of(bhf.k, bhf.b));
      bhf _snowmanx = this.bg.f().orElse(null);
      if (_snowmanx == bhf.k && _snowman != bhf.k) {
         this.eN();
      }

      this.s(this.bg.a(ayd.o));
   }

   @Override
   protected void N() {
      this.l.Z().a("zoglinBrain");
      this.cJ().a((aag)this.l, this);
      this.l.Z().c();
      this.eL();
   }

   @Override
   public void a(boolean var1) {
      this.ab().b(d, _snowman);
      if (!this.l.v && _snowman) {
         this.a(arl.f).a(0.5);
      }
   }

   @Override
   public boolean w_() {
      return this.ab().a(d);
   }

   @Override
   public void k() {
      if (this.bo > 0) {
         this.bo--;
      }

      super.k();
   }

   @Override
   public void a(byte var1) {
      if (_snowman == 4) {
         this.bo = 10;
         this.a(adq.rD, 1.0F, this.dH());
      } else {
         super.a(_snowman);
      }
   }

   @Override
   public int eM() {
      return this.bo;
   }

   @Override
   protected adp I() {
      if (this.l.v) {
         return null;
      } else {
         return this.bg.a(ayd.o) ? adq.rC : adq.rB;
      }
   }

   @Override
   protected adp e(apk var1) {
      return adq.rF;
   }

   @Override
   protected adp dq() {
      return adq.rE;
   }

   @Override
   protected void b(fx var1, ceh var2) {
      this.a(adq.rG, 0.15F, 1.0F);
   }

   protected void eN() {
      this.a(adq.rC, 1.0F, this.dH());
   }

   @Override
   protected void M() {
      super.M();
      rz.a(this);
   }

   @Override
   public aqq dC() {
      return aqq.b;
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      if (this.w_()) {
         _snowman.a("IsBaby", true);
      }
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      if (_snowman.q("IsBaby")) {
         this.a(true);
      }
   }
}
