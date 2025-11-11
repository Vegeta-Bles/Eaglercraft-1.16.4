import com.google.common.collect.ImmutableList;

public class dtr<T extends aqa> extends dtf<T> {
   private final dwn a;
   private final dwn b;
   private final dwn f;
   private final dwn g;
   private final dwn h;
   private final dwn i;
   private final dwn j;
   private final dwn k;

   public dtr() {
      int _snowman = 16;
      this.a = new dwn(this, 0, 0);
      this.a.a(-2.0F, -6.0F, -2.0F, 4.0F, 6.0F, 3.0F, 0.0F);
      this.a.a(0.0F, 15.0F, -4.0F);
      this.j = new dwn(this, 14, 0);
      this.j.a(-2.0F, -4.0F, -4.0F, 4.0F, 2.0F, 2.0F, 0.0F);
      this.j.a(0.0F, 15.0F, -4.0F);
      this.k = new dwn(this, 14, 4);
      this.k.a(-1.0F, -2.0F, -3.0F, 2.0F, 2.0F, 2.0F, 0.0F);
      this.k.a(0.0F, 15.0F, -4.0F);
      this.b = new dwn(this, 0, 9);
      this.b.a(-3.0F, -4.0F, -3.0F, 6.0F, 8.0F, 6.0F, 0.0F);
      this.b.a(0.0F, 16.0F, 0.0F);
      this.f = new dwn(this, 26, 0);
      this.f.a(-1.0F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F);
      this.f.a(-2.0F, 19.0F, 1.0F);
      this.g = new dwn(this, 26, 0);
      this.g.a(-1.0F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F);
      this.g.a(1.0F, 19.0F, 1.0F);
      this.h = new dwn(this, 24, 13);
      this.h.a(0.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F);
      this.h.a(-4.0F, 13.0F, 0.0F);
      this.i = new dwn(this, 24, 13);
      this.i.a(-1.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F);
      this.i.a(4.0F, 13.0F, 0.0F);
   }

   @Override
   protected Iterable<dwn> a() {
      return ImmutableList.of(this.a, this.j, this.k);
   }

   @Override
   protected Iterable<dwn> b() {
      return ImmutableList.of(this.b, this.f, this.g, this.h, this.i);
   }

   @Override
   public void a(T var1, float var2, float var3, float var4, float var5, float var6) {
      this.a.d = _snowman * (float) (Math.PI / 180.0);
      this.a.e = _snowman * (float) (Math.PI / 180.0);
      this.j.d = this.a.d;
      this.j.e = this.a.e;
      this.k.d = this.a.d;
      this.k.e = this.a.e;
      this.b.d = (float) (Math.PI / 2);
      this.f.d = afm.b(_snowman * 0.6662F) * 1.4F * _snowman;
      this.g.d = afm.b(_snowman * 0.6662F + (float) Math.PI) * 1.4F * _snowman;
      this.h.f = _snowman;
      this.i.f = -_snowman;
   }
}
