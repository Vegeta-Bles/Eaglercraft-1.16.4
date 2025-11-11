import java.util.Random;

public class bdn extends bej {
   public bdn(aqe<? extends bdn> var1, brx var2) {
      super(_snowman, _snowman);
   }

   public static boolean a(aqe<bdn> var0, bsk var1, aqp var2, fx var3, Random var4) {
      return b(_snowman, _snowman, _snowman, _snowman, _snowman) && (_snowman == aqp.c || _snowman.e(_snowman));
   }

   @Override
   protected boolean T_() {
      return false;
   }

   @Override
   protected adp I() {
      return adq.gj;
   }

   @Override
   protected adp e(apk var1) {
      return adq.gm;
   }

   @Override
   protected adp dq() {
      return adq.gl;
   }

   @Override
   protected adp eL() {
      return adq.gn;
   }

   @Override
   public boolean B(aqa var1) {
      boolean _snowman = super.B(_snowman);
      if (_snowman && this.dD().a() && _snowman instanceof aqm) {
         float _snowmanx = this.l.d(this.cB()).b();
         ((aqm)_snowman).c(new apu(apw.q, 140 * (int)_snowmanx));
      }

      return _snowman;
   }

   @Override
   protected boolean eN() {
      return true;
   }

   @Override
   protected void eP() {
      this.b(aqe.aY);
      if (!this.aA()) {
         this.l.a(null, 1041, this.cB(), 0);
      }
   }

   @Override
   protected bmb eM() {
      return bmb.b;
   }
}
