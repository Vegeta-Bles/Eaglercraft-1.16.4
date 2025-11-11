import com.google.common.collect.ImmutableList;

public class dwa<T extends aqa> extends dtu<T> {
   private final dwn a;
   private final dwn b;
   private final dwn f;
   private final dwn g;
   private final dwn h;

   public dwa(float var1) {
      this.r = 32;
      this.s = 32;
      int _snowman = 22;
      this.a = new dwn(this, 0, 0);
      this.a.a(-1.0F, -1.5F, -3.0F, 2.0F, 3.0F, 6.0F, _snowman);
      this.a.a(0.0F, 22.0F, 0.0F);
      this.b = new dwn(this, 22, -6);
      this.b.a(0.0F, -1.5F, 0.0F, 0.0F, 3.0F, 6.0F, _snowman);
      this.b.a(0.0F, 22.0F, 3.0F);
      this.f = new dwn(this, 2, 16);
      this.f.a(-2.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, _snowman);
      this.f.a(-1.0F, 22.5F, 0.0F);
      this.f.e = (float) (Math.PI / 4);
      this.g = new dwn(this, 2, 12);
      this.g.a(0.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, _snowman);
      this.g.a(1.0F, 22.5F, 0.0F);
      this.g.e = (float) (-Math.PI / 4);
      this.h = new dwn(this, 10, -5);
      this.h.a(0.0F, -3.0F, 0.0F, 0.0F, 3.0F, 6.0F, _snowman);
      this.h.a(0.0F, 20.5F, -3.0F);
   }

   @Override
   public Iterable<dwn> a() {
      return ImmutableList.of(this.a, this.b, this.f, this.g, this.h);
   }

   @Override
   public void a(T var1, float var2, float var3, float var4, float var5, float var6) {
      float _snowman = 1.0F;
      if (!_snowman.aE()) {
         _snowman = 1.5F;
      }

      this.b.e = -_snowman * 0.45F * afm.a(0.6F * _snowman);
   }
}
