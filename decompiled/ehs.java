public class ehs<T extends aqm, M extends dvd<T>> extends ejc<T, M> {
   private final eet a;
   private bgc b;

   public ehs(efr<T, M> var1) {
      super(_snowman);
      this.a = _snowman.b();
   }

   @Override
   protected int a(T var1) {
      return _snowman.dy();
   }

   @Override
   protected void a(dfm var1, eag var2, int var3, aqa var4, float var5, float var6, float var7, float var8) {
      float _snowman = afm.c(_snowman * _snowman + _snowman * _snowman);
      this.b = new bgc(_snowman.l, _snowman.cD(), _snowman.cE(), _snowman.cH());
      this.b.p = (float)(Math.atan2((double)_snowman, (double)_snowman) * 180.0F / (float)Math.PI);
      this.b.q = (float)(Math.atan2((double)_snowman, (double)_snowman) * 180.0F / (float)Math.PI);
      this.b.r = this.b.p;
      this.b.s = this.b.q;
      this.a.a(this.b, 0.0, 0.0, 0.0, 0.0F, _snowman, _snowman, _snowman, _snowman);
   }
}
