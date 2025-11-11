public class dvs<T extends aqn & bdu> extends dum<T> {
   public dvs() {
      this(0.0F, false);
   }

   public dvs(float var1, boolean var2) {
      super(_snowman);
      if (!_snowman) {
         this.i = new dwn(this, 40, 16);
         this.i.a(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, _snowman);
         this.i.a(-5.0F, 2.0F, 0.0F);
         this.j = new dwn(this, 40, 16);
         this.j.g = true;
         this.j.a(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, _snowman);
         this.j.a(5.0F, 2.0F, 0.0F);
         this.k = new dwn(this, 0, 16);
         this.k.a(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, _snowman);
         this.k.a(-2.0F, 12.0F, 0.0F);
         this.l = new dwn(this, 0, 16);
         this.l.g = true;
         this.l.a(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, _snowman);
         this.l.a(2.0F, 12.0F, 0.0F);
      }
   }

   public void a(T var1, float var2, float var3, float var4) {
      this.n = dum.a.a;
      this.m = dum.a.a;
      bmb _snowman = _snowman.b(aot.a);
      if (_snowman.b() == bmd.kc && _snowman.eF()) {
         if (_snowman.dV() == aqi.b) {
            this.n = dum.a.d;
         } else {
            this.m = dum.a.d;
         }
      }

      super.a(_snowman, _snowman, _snowman, _snowman);
   }

   public void a(T var1, float var2, float var3, float var4, float var5, float var6) {
      super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      bmb _snowman = _snowman.dD();
      if (_snowman.eF() && (_snowman.a() || _snowman.b() != bmd.kc)) {
         float _snowmanx = afm.a(this.c * (float) Math.PI);
         float _snowmanxx = afm.a((1.0F - (1.0F - this.c) * (1.0F - this.c)) * (float) Math.PI);
         this.i.f = 0.0F;
         this.j.f = 0.0F;
         this.i.e = -(0.1F - _snowmanx * 0.6F);
         this.j.e = 0.1F - _snowmanx * 0.6F;
         this.i.d = (float) (-Math.PI / 2);
         this.j.d = (float) (-Math.PI / 2);
         this.i.d -= _snowmanx * 1.2F - _snowmanxx * 0.4F;
         this.j.d -= _snowmanx * 1.2F - _snowmanxx * 0.4F;
         dtg.a(this.i, this.j, _snowman);
      }
   }

   @Override
   public void a(aqi var1, dfm var2) {
      float _snowman = _snowman == aqi.b ? 1.0F : -1.0F;
      dwn _snowmanx = this.a(_snowman);
      _snowmanx.a += _snowman;
      _snowmanx.a(_snowman);
      _snowmanx.a -= _snowman;
   }
}
