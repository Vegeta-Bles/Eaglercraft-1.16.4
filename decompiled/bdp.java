import java.util.Random;

public class bdp extends bdz {
   public bdp(aqe<? extends bdp> var1, brx var2) {
      super(_snowman, _snowman);
   }

   public static ark.a m() {
      return bdq.eR().a(arl.d, 0.2F);
   }

   public static boolean b(aqe<bdp> var0, bry var1, aqp var2, fx var3, Random var4) {
      return _snowman.ad() != aor.a;
   }

   @Override
   public boolean a(brz var1) {
      return _snowman.j(this) && !_snowman.d(this.cc());
   }

   @Override
   protected void a(int var1, boolean var2) {
      super.a(_snowman, _snowman);
      this.a(arl.i).a((double)(_snowman * 3));
   }

   @Override
   public float aR() {
      return 1.0F;
   }

   @Override
   protected hf eI() {
      return hh.A;
   }

   @Override
   protected vk J() {
      return this.eQ() ? cyq.a : this.X().i();
   }

   @Override
   public boolean bq() {
      return false;
   }

   @Override
   protected int eJ() {
      return super.eJ() * 4;
   }

   @Override
   protected void eK() {
      this.b *= 0.9F;
   }

   @Override
   protected void dK() {
      dcn _snowman = this.cC();
      this.n(_snowman.b, (double)(this.dJ() + (float)this.eP() * 0.1F), _snowman.d);
      this.Z = true;
   }

   @Override
   protected void c(ael<cuw> var1) {
      if (_snowman == aef.c) {
         dcn _snowman = this.cC();
         this.n(_snowman.b, (double)(0.22F + (float)this.eP() * 0.05F), _snowman.d);
         this.Z = true;
      } else {
         super.c(_snowman);
      }
   }

   @Override
   public boolean b(float var1, float var2) {
      return false;
   }

   @Override
   protected boolean eL() {
      return this.dS();
   }

   @Override
   protected float eM() {
      return super.eM() + 2.0F;
   }

   @Override
   protected adp e(apk var1) {
      return this.eQ() ? adq.hx : adq.hw;
   }

   @Override
   protected adp dq() {
      return this.eQ() ? adq.ho : adq.hv;
   }

   @Override
   protected adp eN() {
      return this.eQ() ? adq.hA : adq.hz;
   }

   @Override
   protected adp eO() {
      return adq.hy;
   }
}
