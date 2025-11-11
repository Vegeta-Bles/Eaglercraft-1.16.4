import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;

public class bel extends bej implements aqs {
   private static final UUID b = UUID.fromString("49455A49-7EC5-45BA-B886-3B90B23A1718");
   private static final arj c = new arj(b, "Attacking speed boost", 0.05, arj.a.a);
   private static final afh d = afu.a(0, 1);
   private int bo;
   private static final afh bp = afu.a(20, 39);
   private int bq;
   private UUID br;
   private static final afh bs = afu.a(4, 6);
   private int bt;

   public bel(aqe<? extends bel> var1, brx var2) {
      super(_snowman, _snowman);
      this.a(cwz.g, 8.0F);
   }

   @Override
   public void a(@Nullable UUID var1) {
      this.br = _snowman;
   }

   @Override
   public double bb() {
      return this.w_() ? -0.05 : -0.45;
   }

   @Override
   protected void m() {
      this.bk.a(2, new axm(this, 1.0, false));
      this.bk.a(7, new axk(this, 1.0));
      this.bl.a(1, new axp(this).a());
      this.bl.a(2, new axq<>(this, bfw.class, 10, true, false, this::a_));
      this.bl.a(3, new axw<>(this, true));
   }

   public static ark.a eW() {
      return bej.eS().a(arl.l, 0.0).a(arl.d, 0.23F).a(arl.f, 5.0);
   }

   @Override
   protected boolean eN() {
      return false;
   }

   @Override
   protected void N() {
      arh _snowman = this.a(arl.d);
      if (this.H_()) {
         if (!this.w_() && !_snowman.a(c)) {
            _snowman.b(c);
         }

         this.eX();
      } else if (_snowman.a(c)) {
         _snowman.d(c);
      }

      this.a((aag)this.l, true);
      if (this.A() != null) {
         this.eY();
      }

      if (this.H_()) {
         this.aG = this.K;
      }

      super.N();
   }

   private void eX() {
      if (this.bo > 0) {
         this.bo--;
         if (this.bo == 0) {
            this.fa();
         }
      }
   }

   private void eY() {
      if (this.bt > 0) {
         this.bt--;
      } else {
         if (this.z().a(this.A())) {
            this.eZ();
         }

         this.bt = bs.a(this.J);
      }
   }

   private void eZ() {
      double _snowman = this.b(arl.b);
      dci _snowmanx = dci.a(this.cA()).c(_snowman, 10.0, _snowman);
      this.l
         .b(bel.class, _snowmanx)
         .stream()
         .filter(var1x -> var1x != this)
         .filter(var0 -> var0.A() == null)
         .filter(var1x -> !var1x.r(this.A()))
         .forEach(var1x -> var1x.h(this.A()));
   }

   private void fa() {
      this.a(adq.rU, this.dG() * 2.0F, this.dH() * 1.8F);
   }

   @Override
   public void h(@Nullable aqm var1) {
      if (this.A() == null && _snowman != null) {
         this.bo = d.a(this.J);
         this.bt = bs.a(this.J);
      }

      if (_snowman instanceof bfw) {
         this.e((bfw)_snowman);
      }

      super.h(_snowman);
   }

   @Override
   public void G_() {
      this.a_(bp.a(this.J));
   }

   public static boolean b(aqe<bel> var0, bry var1, aqp var2, fx var3, Random var4) {
      return _snowman.ad() != aor.a && _snowman.d_(_snowman.c()).b() != bup.iK;
   }

   @Override
   public boolean a(brz var1) {
      return _snowman.j(this) && !_snowman.d(this.cc());
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      this.c(_snowman);
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      this.a((aag)this.l, _snowman);
   }

   @Override
   public void a_(int var1) {
      this.bq = _snowman;
   }

   @Override
   public int E_() {
      return this.bq;
   }

   @Override
   public boolean a(apk var1, float var2) {
      return this.b(_snowman) ? false : super.a(_snowman, _snowman);
   }

   @Override
   protected adp I() {
      return this.H_() ? adq.rU : adq.rT;
   }

   @Override
   protected adp e(apk var1) {
      return adq.rW;
   }

   @Override
   protected adp dq() {
      return adq.rV;
   }

   @Override
   protected void a(aos var1) {
      this.a(aqf.a, new bmb(bmd.kv));
   }

   @Override
   protected bmb eM() {
      return bmb.b;
   }

   @Override
   protected void eV() {
      this.a(arl.l).a(0.0);
   }

   @Override
   public UUID F_() {
      return this.br;
   }

   @Override
   public boolean f(bfw var1) {
      return this.a_((aqm)_snowman);
   }
}
