import com.google.common.collect.ImmutableList;

public class dvi<T extends aqa> extends dtf<T> {
   protected dwn a = new dwn(this, 0, 0);
   protected dwn b;
   protected dwn f;
   protected dwn g;
   protected dwn h;
   protected dwn i;

   public dvi(int var1, float var2, boolean var3, float var4, float var5, float var6, float var7, int var8) {
      super(_snowman, _snowman, _snowman, _snowman, _snowman, (float)_snowman);
      this.a.a(-4.0F, -4.0F, -8.0F, 8.0F, 8.0F, 8.0F, _snowman);
      this.a.a(0.0F, (float)(18 - _snowman), -6.0F);
      this.b = new dwn(this, 28, 8);
      this.b.a(-5.0F, -10.0F, -7.0F, 10.0F, 16.0F, 8.0F, _snowman);
      this.b.a(0.0F, (float)(17 - _snowman), 2.0F);
      this.f = new dwn(this, 0, 16);
      this.f.a(-2.0F, 0.0F, -2.0F, 4.0F, (float)_snowman, 4.0F, _snowman);
      this.f.a(-3.0F, (float)(24 - _snowman), 7.0F);
      this.g = new dwn(this, 0, 16);
      this.g.a(-2.0F, 0.0F, -2.0F, 4.0F, (float)_snowman, 4.0F, _snowman);
      this.g.a(3.0F, (float)(24 - _snowman), 7.0F);
      this.h = new dwn(this, 0, 16);
      this.h.a(-2.0F, 0.0F, -2.0F, 4.0F, (float)_snowman, 4.0F, _snowman);
      this.h.a(-3.0F, (float)(24 - _snowman), -5.0F);
      this.i = new dwn(this, 0, 16);
      this.i.a(-2.0F, 0.0F, -2.0F, 4.0F, (float)_snowman, 4.0F, _snowman);
      this.i.a(3.0F, (float)(24 - _snowman), -5.0F);
   }

   @Override
   protected Iterable<dwn> a() {
      return ImmutableList.of(this.a);
   }

   @Override
   protected Iterable<dwn> b() {
      return ImmutableList.of(this.b, this.f, this.g, this.h, this.i);
   }

   @Override
   public void a(T var1, float var2, float var3, float var4, float var5, float var6) {
      this.a.d = _snowman * (float) (Math.PI / 180.0);
      this.a.e = _snowman * (float) (Math.PI / 180.0);
      this.b.d = (float) (Math.PI / 2);
      this.f.d = afm.b(_snowman * 0.6662F) * 1.4F * _snowman;
      this.g.d = afm.b(_snowman * 0.6662F + (float) Math.PI) * 1.4F * _snowman;
      this.h.d = afm.b(_snowman * 0.6662F + (float) Math.PI) * 1.4F * _snowman;
      this.i.d = afm.b(_snowman * 0.6662F) * 1.4F * _snowman;
   }
}
