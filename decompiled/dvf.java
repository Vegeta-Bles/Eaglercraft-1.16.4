import com.google.common.collect.ImmutableList;

public class dvf<T extends aqa> extends dur<T> {
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
   private final dwn o;
   private final dwn p;

   public dvf() {
      this.r = 32;
      this.s = 32;
      int _snowman = 22;
      this.a = new dwn(this, 0, 0);
      this.a.a(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F);
      this.a.a(0.0F, 22.0F, 0.0F);
      this.b = new dwn(this, 24, 0);
      this.b.a(-2.0F, 0.0F, -1.0F, 2.0F, 1.0F, 2.0F);
      this.b.a(-4.0F, 15.0F, -2.0F);
      this.f = new dwn(this, 24, 3);
      this.f.a(0.0F, 0.0F, -1.0F, 2.0F, 1.0F, 2.0F);
      this.f.a(4.0F, 15.0F, -2.0F);
      this.g = new dwn(this, 15, 17);
      this.g.a(-4.0F, -1.0F, 0.0F, 8.0F, 1.0F, 0.0F);
      this.g.a(0.0F, 14.0F, -4.0F);
      this.g.d = (float) (Math.PI / 4);
      this.h = new dwn(this, 14, 16);
      this.h.a(-4.0F, -1.0F, 0.0F, 8.0F, 1.0F, 1.0F);
      this.h.a(0.0F, 14.0F, 0.0F);
      this.i = new dwn(this, 23, 18);
      this.i.a(-4.0F, -1.0F, 0.0F, 8.0F, 1.0F, 0.0F);
      this.i.a(0.0F, 14.0F, 4.0F);
      this.i.d = (float) (-Math.PI / 4);
      this.j = new dwn(this, 5, 17);
      this.j.a(-1.0F, -8.0F, 0.0F, 1.0F, 8.0F, 0.0F);
      this.j.a(-4.0F, 22.0F, -4.0F);
      this.j.e = (float) (-Math.PI / 4);
      this.k = new dwn(this, 1, 17);
      this.k.a(0.0F, -8.0F, 0.0F, 1.0F, 8.0F, 0.0F);
      this.k.a(4.0F, 22.0F, -4.0F);
      this.k.e = (float) (Math.PI / 4);
      this.l = new dwn(this, 15, 20);
      this.l.a(-4.0F, 0.0F, 0.0F, 8.0F, 1.0F, 0.0F);
      this.l.a(0.0F, 22.0F, -4.0F);
      this.l.d = (float) (-Math.PI / 4);
      this.n = new dwn(this, 15, 20);
      this.n.a(-4.0F, 0.0F, 0.0F, 8.0F, 1.0F, 0.0F);
      this.n.a(0.0F, 22.0F, 0.0F);
      this.m = new dwn(this, 15, 20);
      this.m.a(-4.0F, 0.0F, 0.0F, 8.0F, 1.0F, 0.0F);
      this.m.a(0.0F, 22.0F, 4.0F);
      this.m.d = (float) (Math.PI / 4);
      this.o = new dwn(this, 9, 17);
      this.o.a(-1.0F, -8.0F, 0.0F, 1.0F, 8.0F, 0.0F);
      this.o.a(-4.0F, 22.0F, 4.0F);
      this.o.e = (float) (Math.PI / 4);
      this.p = new dwn(this, 9, 17);
      this.p.a(0.0F, -8.0F, 0.0F, 1.0F, 8.0F, 0.0F);
      this.p.a(4.0F, 22.0F, 4.0F);
      this.p.e = (float) (-Math.PI / 4);
   }

   @Override
   public Iterable<dwn> a() {
      return ImmutableList.of(this.a, this.b, this.f, this.g, this.h, this.i, this.j, this.k, this.l, this.n, this.m, this.o, new dwn[]{this.p});
   }

   @Override
   public void a(T var1, float var2, float var3, float var4, float var5, float var6) {
      this.b.f = -0.2F + 0.4F * afm.a(_snowman * 0.2F);
      this.f.f = 0.2F - 0.4F * afm.a(_snowman * 0.2F);
   }
}
