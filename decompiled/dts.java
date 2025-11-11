import com.google.common.collect.ImmutableList;

public class dts<T extends aqa> extends dur<T> {
   private final dwn a;
   private final dwn b;
   private final dwn f;
   private final dwn g;
   private final dwn h;
   private final dwn i;
   private final dwn j;

   public dts() {
      this.r = 32;
      this.s = 32;
      int _snowman = 22;
      this.a = new dwn(this, 0, 0);
      this.a.a(-1.0F, -2.0F, 0.0F, 2.0F, 4.0F, 7.0F);
      this.a.a(0.0F, 22.0F, 0.0F);
      this.f = new dwn(this, 11, 0);
      this.f.a(-1.0F, -2.0F, -3.0F, 2.0F, 4.0F, 3.0F);
      this.f.a(0.0F, 22.0F, 0.0F);
      this.g = new dwn(this, 0, 0);
      this.g.a(-1.0F, -2.0F, -1.0F, 2.0F, 3.0F, 1.0F);
      this.g.a(0.0F, 22.0F, -3.0F);
      this.h = new dwn(this, 22, 1);
      this.h.a(-2.0F, 0.0F, -1.0F, 2.0F, 0.0F, 2.0F);
      this.h.a(-1.0F, 23.0F, 0.0F);
      this.h.f = (float) (-Math.PI / 4);
      this.i = new dwn(this, 22, 4);
      this.i.a(0.0F, 0.0F, -1.0F, 2.0F, 0.0F, 2.0F);
      this.i.a(1.0F, 23.0F, 0.0F);
      this.i.f = (float) (Math.PI / 4);
      this.j = new dwn(this, 22, 3);
      this.j.a(0.0F, -2.0F, 0.0F, 0.0F, 4.0F, 4.0F);
      this.j.a(0.0F, 22.0F, 7.0F);
      this.b = new dwn(this, 20, -6);
      this.b.a(0.0F, -1.0F, -1.0F, 0.0F, 1.0F, 6.0F);
      this.b.a(0.0F, 20.0F, 0.0F);
   }

   @Override
   public Iterable<dwn> a() {
      return ImmutableList.of(this.a, this.f, this.g, this.h, this.i, this.j, this.b);
   }

   @Override
   public void a(T var1, float var2, float var3, float var4, float var5, float var6) {
      float _snowman = 1.0F;
      if (!_snowman.aE()) {
         _snowman = 1.5F;
      }

      this.j.e = -_snowman * 0.45F * afm.a(0.6F * _snowman);
   }
}
