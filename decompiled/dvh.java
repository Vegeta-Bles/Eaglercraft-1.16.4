import com.google.common.collect.ImmutableList;

public class dvh<T extends aqa> extends dur<T> {
   private final dwn a;
   private final dwn b;
   private final dwn f;
   private final dwn g;
   private final dwn h;
   private final dwn i;

   public dvh() {
      this.r = 32;
      this.s = 32;
      int _snowman = 23;
      this.a = new dwn(this, 0, 27);
      this.a.a(-1.5F, -2.0F, -1.5F, 3.0F, 2.0F, 3.0F);
      this.a.a(0.0F, 23.0F, 0.0F);
      this.b = new dwn(this, 24, 6);
      this.b.a(-1.5F, 0.0F, -1.5F, 1.0F, 1.0F, 1.0F);
      this.b.a(0.0F, 20.0F, 0.0F);
      this.f = new dwn(this, 28, 6);
      this.f.a(0.5F, 0.0F, -1.5F, 1.0F, 1.0F, 1.0F);
      this.f.a(0.0F, 20.0F, 0.0F);
      this.i = new dwn(this, -3, 0);
      this.i.a(-1.5F, 0.0F, 0.0F, 3.0F, 0.0F, 3.0F);
      this.i.a(0.0F, 22.0F, 1.5F);
      this.g = new dwn(this, 25, 0);
      this.g.a(-1.0F, 0.0F, 0.0F, 1.0F, 0.0F, 2.0F);
      this.g.a(-1.5F, 22.0F, -1.5F);
      this.h = new dwn(this, 25, 0);
      this.h.a(0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 2.0F);
      this.h.a(1.5F, 22.0F, -1.5F);
   }

   @Override
   public Iterable<dwn> a() {
      return ImmutableList.of(this.a, this.b, this.f, this.i, this.g, this.h);
   }

   @Override
   public void a(T var1, float var2, float var3, float var4, float var5, float var6) {
      this.g.f = -0.2F + 0.4F * afm.a(_snowman * 0.2F);
      this.h.f = 0.2F - 0.4F * afm.a(_snowman * 0.2F);
   }
}
