import java.util.Random;

public abstract class ejc<T extends aqm, M extends dvd<T>> extends eit<T, M> {
   public ejc(efr<T, M> var1) {
      super(_snowman);
   }

   protected abstract int a(T var1);

   protected abstract void a(dfm var1, eag var2, int var3, aqa var4, float var5, float var6, float var7, float var8);

   public void a(dfm var1, eag var2, int var3, T var4, float var5, float var6, float var7, float var8, float var9, float var10) {
      int _snowman = this.a(_snowman);
      Random _snowmanx = new Random((long)_snowman.Y());
      if (_snowman > 0) {
         for (int _snowmanxx = 0; _snowmanxx < _snowman; _snowmanxx++) {
            _snowman.a();
            dwn _snowmanxxx = this.aC_().a(_snowmanx);
            dwn.a _snowmanxxxx = _snowmanxxx.a(_snowmanx);
            _snowmanxxx.a(_snowman);
            float _snowmanxxxxx = _snowmanx.nextFloat();
            float _snowmanxxxxxx = _snowmanx.nextFloat();
            float _snowmanxxxxxxx = _snowmanx.nextFloat();
            float _snowmanxxxxxxxx = afm.g(_snowmanxxxxx, _snowmanxxxx.a, _snowmanxxxx.d) / 16.0F;
            float _snowmanxxxxxxxxx = afm.g(_snowmanxxxxxx, _snowmanxxxx.b, _snowmanxxxx.e) / 16.0F;
            float _snowmanxxxxxxxxxx = afm.g(_snowmanxxxxxxx, _snowmanxxxx.c, _snowmanxxxx.f) / 16.0F;
            _snowman.a((double)_snowmanxxxxxxxx, (double)_snowmanxxxxxxxxx, (double)_snowmanxxxxxxxxxx);
            _snowmanxxxxx = -1.0F * (_snowmanxxxxx * 2.0F - 1.0F);
            _snowmanxxxxxx = -1.0F * (_snowmanxxxxxx * 2.0F - 1.0F);
            _snowmanxxxxxxx = -1.0F * (_snowmanxxxxxxx * 2.0F - 1.0F);
            this.a(_snowman, _snowman, _snowman, _snowman, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx, _snowman);
            _snowman.b();
         }
      }
   }
}
