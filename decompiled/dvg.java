import com.google.common.collect.ImmutableList;

public class dvg<T extends aqa> extends dur<T> {
   private final dwn a;
   private final dwn b;
   private final dwn f;
   private final dwn g;
   private final dwn h;
   private final dwn i;
   private final dwn j;
   private final dwn k;
   private final dwn l;
   private final dwn m;
   private final dwn n;

   public dvg() {
      this.r = 32;
      this.s = 32;
      int _snowman = 22;
      this.a = new dwn(this, 12, 22);
      this.a.a(-2.5F, -5.0F, -2.5F, 5.0F, 5.0F, 5.0F);
      this.a.a(0.0F, 22.0F, 0.0F);
      this.b = new dwn(this, 24, 0);
      this.b.a(-2.0F, 0.0F, 0.0F, 2.0F, 0.0F, 2.0F);
      this.b.a(-2.5F, 17.0F, -1.5F);
      this.f = new dwn(this, 24, 3);
      this.f.a(0.0F, 0.0F, 0.0F, 2.0F, 0.0F, 2.0F);
      this.f.a(2.5F, 17.0F, -1.5F);
      this.g = new dwn(this, 15, 16);
      this.g.a(-2.5F, -1.0F, 0.0F, 5.0F, 1.0F, 1.0F);
      this.g.a(0.0F, 17.0F, -2.5F);
      this.g.d = (float) (Math.PI / 4);
      this.h = new dwn(this, 10, 16);
      this.h.a(-2.5F, -1.0F, -1.0F, 5.0F, 1.0F, 1.0F);
      this.h.a(0.0F, 17.0F, 2.5F);
      this.h.d = (float) (-Math.PI / 4);
      this.i = new dwn(this, 8, 16);
      this.i.a(-1.0F, -5.0F, 0.0F, 1.0F, 5.0F, 1.0F);
      this.i.a(-2.5F, 22.0F, -2.5F);
      this.i.e = (float) (-Math.PI / 4);
      this.j = new dwn(this, 8, 16);
      this.j.a(-1.0F, -5.0F, 0.0F, 1.0F, 5.0F, 1.0F);
      this.j.a(-2.5F, 22.0F, 2.5F);
      this.j.e = (float) (Math.PI / 4);
      this.k = new dwn(this, 4, 16);
      this.k.a(0.0F, -5.0F, 0.0F, 1.0F, 5.0F, 1.0F);
      this.k.a(2.5F, 22.0F, 2.5F);
      this.k.e = (float) (-Math.PI / 4);
      this.l = new dwn(this, 0, 16);
      this.l.a(0.0F, -5.0F, 0.0F, 1.0F, 5.0F, 1.0F);
      this.l.a(2.5F, 22.0F, -2.5F);
      this.l.e = (float) (Math.PI / 4);
      this.m = new dwn(this, 8, 22);
      this.m.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      this.m.a(0.5F, 22.0F, 2.5F);
      this.m.d = (float) (Math.PI / 4);
      this.n = new dwn(this, 17, 21);
      this.n.a(-2.5F, 0.0F, 0.0F, 5.0F, 1.0F, 1.0F);
      this.n.a(0.0F, 22.0F, -2.5F);
      this.n.d = (float) (-Math.PI / 4);
   }

   @Override
   public Iterable<dwn> a() {
      return ImmutableList.of(this.a, this.b, this.f, this.g, this.h, this.i, this.j, this.k, this.l, this.m, this.n);
   }

   @Override
   public void a(T var1, float var2, float var3, float var4, float var5, float var6) {
      this.b.f = -0.2F + 0.4F * afm.a(_snowman * 0.2F);
      this.f.f = 0.2F - 0.4F * afm.a(_snowman * 0.2F);
   }
}
