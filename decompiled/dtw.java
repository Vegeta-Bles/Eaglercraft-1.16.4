import com.google.common.collect.ImmutableList;

public class dtw<T extends aqa> extends dur<T> {
   private final dwn a;
   private final dwn b;
   private final dwn f;
   private final dwn g;
   private final dwn h;
   private final dwn i;
   private final dwn j;

   public dtw() {
      this(0.0F);
   }

   public dtw(float var1) {
      int _snowman = 6;
      this.a = new dwn(this, 0, 0);
      this.a.a(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, _snowman);
      this.a.a(0.0F, 6.0F, 0.0F);
      this.b = new dwn(this, 32, 0);
      this.b.a(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, _snowman + 0.5F);
      this.b.a(0.0F, 6.0F, 0.0F);
      this.f = new dwn(this, 16, 16);
      this.f.a(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, _snowman);
      this.f.a(0.0F, 6.0F, 0.0F);
      this.g = new dwn(this, 0, 16);
      this.g.a(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, _snowman);
      this.g.a(-2.0F, 18.0F, 4.0F);
      this.h = new dwn(this, 0, 16);
      this.h.a(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, _snowman);
      this.h.a(2.0F, 18.0F, 4.0F);
      this.i = new dwn(this, 0, 16);
      this.i.a(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, _snowman);
      this.i.a(-2.0F, 18.0F, -4.0F);
      this.j = new dwn(this, 0, 16);
      this.j.a(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, _snowman);
      this.j.a(2.0F, 18.0F, -4.0F);
   }

   @Override
   public Iterable<dwn> a() {
      return ImmutableList.of(this.a, this.f, this.g, this.h, this.i, this.j);
   }

   @Override
   public void a(T var1, float var2, float var3, float var4, float var5, float var6) {
      this.a.e = _snowman * (float) (Math.PI / 180.0);
      this.a.d = _snowman * (float) (Math.PI / 180.0);
      this.g.d = afm.b(_snowman * 0.6662F) * 1.4F * _snowman;
      this.h.d = afm.b(_snowman * 0.6662F + (float) Math.PI) * 1.4F * _snowman;
      this.i.d = afm.b(_snowman * 0.6662F + (float) Math.PI) * 1.4F * _snowman;
      this.j.d = afm.b(_snowman * 0.6662F) * 1.4F * _snowman;
   }
}
