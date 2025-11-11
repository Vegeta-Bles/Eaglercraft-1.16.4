import com.google.common.collect.ImmutableList;

public class dtz<T extends aqm> extends dtf<T> {
   private final dwn a;
   private final dwn b = new dwn(this, 22, 0);

   public dtz() {
      this.b.a(-10.0F, 0.0F, 0.0F, 10.0F, 20.0F, 2.0F, 1.0F);
      this.a = new dwn(this, 22, 0);
      this.a.g = true;
      this.a.a(0.0F, 0.0F, 0.0F, 10.0F, 20.0F, 2.0F, 1.0F);
   }

   @Override
   protected Iterable<dwn> a() {
      return ImmutableList.of();
   }

   @Override
   protected Iterable<dwn> b() {
      return ImmutableList.of(this.b, this.a);
   }

   public void a(T var1, float var2, float var3, float var4, float var5, float var6) {
      float _snowman = (float) (Math.PI / 12);
      float _snowmanx = (float) (-Math.PI / 12);
      float _snowmanxx = 0.0F;
      float _snowmanxxx = 0.0F;
      if (_snowman.ef()) {
         float _snowmanxxxx = 1.0F;
         dcn _snowmanxxxxx = _snowman.cC();
         if (_snowmanxxxxx.c < 0.0) {
            dcn _snowmanxxxxxx = _snowmanxxxxx.d();
            _snowmanxxxx = 1.0F - (float)Math.pow(-_snowmanxxxxxx.c, 1.5);
         }

         _snowman = _snowmanxxxx * (float) (Math.PI / 9) + (1.0F - _snowmanxxxx) * _snowman;
         _snowmanx = _snowmanxxxx * (float) (-Math.PI / 2) + (1.0F - _snowmanxxxx) * _snowmanx;
      } else if (_snowman.bz()) {
         _snowman = (float) (Math.PI * 2.0 / 9.0);
         _snowmanx = (float) (-Math.PI / 4);
         _snowmanxx = 3.0F;
         _snowmanxxx = 0.08726646F;
      }

      this.b.a = 5.0F;
      this.b.b = _snowmanxx;
      if (_snowman instanceof dzj) {
         dzj _snowmanxxxx = (dzj)_snowman;
         _snowmanxxxx.a = (float)((double)_snowmanxxxx.a + (double)(_snowman - _snowmanxxxx.a) * 0.1);
         _snowmanxxxx.b = (float)((double)_snowmanxxxx.b + (double)(_snowmanxxx - _snowmanxxxx.b) * 0.1);
         _snowmanxxxx.c = (float)((double)_snowmanxxxx.c + (double)(_snowmanx - _snowmanxxxx.c) * 0.1);
         this.b.d = _snowmanxxxx.a;
         this.b.e = _snowmanxxxx.b;
         this.b.f = _snowmanxxxx.c;
      } else {
         this.b.d = _snowman;
         this.b.f = _snowmanx;
         this.b.e = _snowmanxxx;
      }

      this.a.a = -this.b.a;
      this.a.e = -this.b.e;
      this.a.b = this.b.b;
      this.a.d = this.b.d;
      this.a.f = -this.b.f;
   }
}
