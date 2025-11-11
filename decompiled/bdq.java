import java.util.Random;
import java.util.function.Predicate;

public abstract class bdq extends aqu implements bdi {
   protected bdq(aqe<? extends bdq> var1, brx var2) {
      super(_snowman, _snowman);
      this.f = 5;
   }

   @Override
   public adr cu() {
      return adr.f;
   }

   @Override
   public void k() {
      this.dA();
      this.eQ();
      super.k();
   }

   protected void eQ() {
      float _snowman = this.aR();
      if (_snowman > 0.5F) {
         this.aI += 2;
      }
   }

   @Override
   protected boolean L() {
      return true;
   }

   @Override
   protected adp av() {
      return adq.gi;
   }

   @Override
   protected adp aw() {
      return adq.gh;
   }

   @Override
   public boolean a(apk var1, float var2) {
      return this.b(_snowman) ? false : super.a(_snowman, _snowman);
   }

   @Override
   protected adp e(apk var1) {
      return adq.gf;
   }

   @Override
   protected adp dq() {
      return adq.ge;
   }

   @Override
   protected adp o(int var1) {
      return _snowman > 4 ? adq.gd : adq.gg;
   }

   @Override
   public float a(fx var1, brz var2) {
      return 0.5F - _snowman.y(_snowman);
   }

   public static boolean a(bsk var0, fx var1, Random var2) {
      if (_snowman.a(bsf.a, _snowman) > _snowman.nextInt(32)) {
         return false;
      } else {
         int _snowman = _snowman.E().W() ? _snowman.c(_snowman, 10) : _snowman.B(_snowman);
         return _snowman <= _snowman.nextInt(8);
      }
   }

   public static boolean b(aqe<? extends bdq> var0, bsk var1, aqp var2, fx var3, Random var4) {
      return _snowman.ad() != aor.a && a(_snowman, _snowman, _snowman) && a(_snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public static boolean c(aqe<? extends bdq> var0, bry var1, aqp var2, fx var3, Random var4) {
      return _snowman.ad() != aor.a && a(_snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public static ark.a eR() {
      return aqn.p().a(arl.f);
   }

   @Override
   protected boolean cV() {
      return true;
   }

   @Override
   protected boolean cW() {
      return true;
   }

   public boolean f(bfw var1) {
      return true;
   }

   @Override
   public bmb f(bmb var1) {
      if (_snowman.b() instanceof bmo) {
         Predicate<bmb> _snowman = ((bmo)_snowman.b()).e();
         bmb _snowmanx = bmo.a(this, _snowman);
         return _snowmanx.a() ? new bmb(bmd.kd) : _snowmanx;
      } else {
         return bmb.b;
      }
   }
}
