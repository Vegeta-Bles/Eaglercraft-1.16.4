import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import javax.annotation.Nullable;

public class bev extends ber {
   protected static final ImmutableList<azc<? extends azb<? super bev>>> d = ImmutableList.of(azc.c, azc.d, azc.b, azc.f, azc.l);
   protected static final ImmutableList<ayd<?>> bo = ImmutableList.of(
      ayd.n, ayd.v, ayd.g, ayd.h, ayd.k, ayd.l, ayd.Y, ayd.X, ayd.x, ayd.y, ayd.m, ayd.D, new ayd[]{ayd.o, ayd.p, ayd.q, ayd.t, ayd.L, ayd.K, ayd.b}
   );

   public bev(aqe<? extends bev> var1, brx var2) {
      super(_snowman, _snowman);
      this.f = 20;
   }

   public static ark.a eS() {
      return bdq.eR().a(arl.a, 50.0).a(arl.d, 0.35F).a(arl.f, 7.0);
   }

   @Nullable
   @Override
   public arc a(bsk var1, aos var2, aqp var3, @Nullable arc var4, @Nullable md var5) {
      bew.a(this);
      this.a(_snowman);
      return super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   protected void a(aos var1) {
      this.a(aqf.a, new bmb(bmd.ky));
   }

   @Override
   protected arf.b<bev> cK() {
      return arf.a(bo, d);
   }

   @Override
   protected arf<?> a(Dynamic<?> var1) {
      return bew.a(this, this.cK().a(_snowman));
   }

   @Override
   public arf<bev> cJ() {
      return (arf<bev>)super.cJ();
   }

   @Override
   public boolean m() {
      return false;
   }

   @Override
   public boolean i(bmb var1) {
      return _snowman.b() == bmd.ky ? super.i(_snowman) : false;
   }

   @Override
   protected void N() {
      this.l.Z().a("piglinBruteBrain");
      this.cJ().a((aag)this.l, this);
      this.l.Z().c();
      bew.b(this);
      bew.c(this);
      super.N();
   }

   @Override
   public beu eN() {
      return this.eF() && this.eO() ? beu.a : beu.f;
   }

   @Override
   public boolean a(apk var1, float var2) {
      boolean _snowman = super.a(_snowman, _snowman);
      if (this.l.v) {
         return false;
      } else {
         if (_snowman && _snowman.k() instanceof aqm) {
            bew.a(this, (aqm)_snowman.k());
         }

         return _snowman;
      }
   }

   @Override
   protected adp I() {
      return adq.lc;
   }

   @Override
   protected adp e(apk var1) {
      return adq.lf;
   }

   @Override
   protected adp dq() {
      return adq.le;
   }

   @Override
   protected void b(fx var1, ceh var2) {
      this.a(adq.lg, 0.15F, 1.0F);
   }

   protected void eT() {
      this.a(adq.ld, 1.0F, this.dH());
   }

   @Override
   protected void eP() {
      this.a(adq.lh, 1.0F, this.dH());
   }
}
