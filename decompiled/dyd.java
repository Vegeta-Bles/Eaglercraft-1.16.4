public class dyd extends dyg {
   private final duv a = new duh();
   private final eao b = eao.h(eeo.a);

   private dyd(dwt var1, double var2, double var4, double var6) {
      super(_snowman, _snowman, _snowman, _snowman);
      this.u = 0.0F;
      this.t = 30;
   }

   @Override
   public dyk b() {
      return dyk.e;
   }

   @Override
   public void a(dfq var1, djk var2, float var3) {
      float _snowman = ((float)this.s + _snowman) / (float)this.t;
      float _snowmanx = 0.05F + 0.5F * afm.a(_snowman * (float) Math.PI);
      dfm _snowmanxx = new dfm();
      _snowmanxx.a(_snowman.f());
      _snowmanxx.a(g.b.a(150.0F * _snowman - 60.0F));
      _snowmanxx.a(-1.0F, -1.0F, 1.0F);
      _snowmanxx.a(0.0, -1.101F, 1.5);
      eag.a _snowmanxxx = djz.C().aE().b();
      dfq _snowmanxxxx = _snowmanxxx.getBuffer(this.b);
      this.a.a(_snowmanxx, _snowmanxxxx, 15728880, ejw.a, 1.0F, 1.0F, 1.0F, _snowmanx);
      _snowmanxxx.a();
   }

   public static class a implements dyj<hi> {
      public a() {
      }

      public dyg a(hi var1, dwt var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         return new dyd(_snowman, _snowman, _snowman, _snowman);
      }
   }
}
