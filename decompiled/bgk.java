public class bgk extends bgg {
   public int e = 1;

   public bgk(aqe<? extends bgk> var1, brx var2) {
      super(_snowman, _snowman);
   }

   public bgk(brx var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      super(aqe.N, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public bgk(brx var1, aqm var2, double var3, double var5, double var7) {
      super(aqe.N, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   protected void a(dcl var1) {
      super.a(_snowman);
      if (!this.l.v) {
         boolean _snowman = this.l.V().b(brt.b);
         this.l.a(null, this.cD(), this.cE(), this.cH(), (float)this.e, _snowman, _snowman ? brp.a.c : brp.a.a);
         this.ad();
      }
   }

   @Override
   protected void a(dck var1) {
      super.a(_snowman);
      if (!this.l.v) {
         aqa _snowman = _snowman.a();
         aqa _snowmanx = this.v();
         _snowman.a(apk.a((bgg)this, _snowmanx), 6.0F);
         if (_snowmanx instanceof aqm) {
            this.a((aqm)_snowmanx, _snowman);
         }
      }
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      _snowman.b("ExplosionPower", this.e);
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      if (_snowman.c("ExplosionPower", 99)) {
         this.e = _snowman.h("ExplosionPower");
      }
   }
}
