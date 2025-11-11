import com.google.common.collect.ImmutableList;

public class dvq<T extends bdw> extends dur<T> {
   private final dwn a;
   private final dwn b = new dwn(64, 64, 0, 0);
   private final dwn f;

   public dvq() {
      super(eao::e);
      this.a = new dwn(64, 64, 0, 28);
      this.f = new dwn(64, 64, 0, 52);
      this.b.a(-8.0F, -16.0F, -8.0F, 16.0F, 12.0F, 16.0F);
      this.b.a(0.0F, 24.0F, 0.0F);
      this.a.a(-8.0F, -8.0F, -8.0F, 16.0F, 8.0F, 16.0F);
      this.a.a(0.0F, 24.0F, 0.0F);
      this.f.a(-3.0F, 0.0F, -3.0F, 6.0F, 6.0F, 6.0F);
      this.f.a(0.0F, 12.0F, 0.0F);
   }

   public void a(T var1, float var2, float var3, float var4, float var5, float var6) {
      float _snowman = _snowman - (float)_snowman.K;
      float _snowmanx = (0.5F + _snowman.y(_snowman)) * (float) Math.PI;
      float _snowmanxx = -1.0F + afm.a(_snowmanx);
      float _snowmanxxx = 0.0F;
      if (_snowmanx > (float) Math.PI) {
         _snowmanxxx = afm.a(_snowman * 0.1F) * 0.7F;
      }

      this.b.a(0.0F, 16.0F + afm.a(_snowmanx) * 8.0F + _snowmanxxx, 0.0F);
      if (_snowman.y(_snowman) > 0.3F) {
         this.b.e = _snowmanxx * _snowmanxx * _snowmanxx * _snowmanxx * (float) Math.PI * 0.125F;
      } else {
         this.b.e = 0.0F;
      }

      this.f.d = _snowman * (float) (Math.PI / 180.0);
      this.f.e = (_snowman.aC - 180.0F - _snowman.aA) * (float) (Math.PI / 180.0);
   }

   @Override
   public Iterable<dwn> a() {
      return ImmutableList.of(this.a, this.b);
   }

   public dwn b() {
      return this.a;
   }

   public dwn c() {
      return this.b;
   }

   public dwn d() {
      return this.f;
   }
}
