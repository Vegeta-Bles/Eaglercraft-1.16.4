import com.google.common.collect.ImmutableList;

public class dud<T extends aqa> extends dur<T> {
   private final dwn a = new dwn(this, 0, 0);
   private final dwn b;
   private final dwn f;

   public dud() {
      this.a.a(-5.0F, 22.0F, -5.0F);
      this.a.a(0.0F, 0.0F, 0.0F, 10.0F, 12.0F, 10.0F);
      this.b = new dwn(this, 40, 0);
      this.b.a(1.5F, 22.0F, -4.0F);
      this.b.a(0.0F, 0.0F, 0.0F, 4.0F, 14.0F, 8.0F);
      this.f = new dwn(this, 40, 0);
      this.f.a(-1.5F, 22.0F, 4.0F);
      this.f.a(0.0F, 0.0F, 0.0F, 4.0F, 14.0F, 8.0F);
   }

   @Override
   public void a(T var1, float var2, float var3, float var4, float var5, float var6) {
      float _snowman = _snowman * 2.0F;
      if (_snowman > 1.0F) {
         _snowman = 1.0F;
      }

      _snowman = 1.0F - _snowman * _snowman * _snowman;
      this.b.f = (float) Math.PI - _snowman * 0.35F * (float) Math.PI;
      this.f.f = (float) Math.PI + _snowman * 0.35F * (float) Math.PI;
      this.f.e = (float) Math.PI;
      float _snowmanx = (_snowman + afm.a(_snowman * 2.7F)) * 0.6F * 12.0F;
      this.b.b = 24.0F - _snowmanx;
      this.f.b = this.b.b;
      this.a.b = this.b.b;
   }

   @Override
   public Iterable<dwn> a() {
      return ImmutableList.of(this.a, this.b, this.f);
   }
}
