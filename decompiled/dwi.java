import com.google.common.collect.ImmutableList;

public class dwi<T extends baz> extends dtt<T> {
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

   public dwi() {
      float _snowman = 0.0F;
      float _snowmanx = 13.5F;
      this.a = new dwn(this, 0, 0);
      this.a.a(-1.0F, 13.5F, -7.0F);
      this.b = new dwn(this, 0, 0);
      this.b.a(-2.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F, 0.0F);
      this.a.b(this.b);
      this.f = new dwn(this, 18, 14);
      this.f.a(-3.0F, -2.0F, -3.0F, 6.0F, 9.0F, 6.0F, 0.0F);
      this.f.a(0.0F, 14.0F, 2.0F);
      this.m = new dwn(this, 21, 0);
      this.m.a(-3.0F, -3.0F, -3.0F, 8.0F, 6.0F, 7.0F, 0.0F);
      this.m.a(-1.0F, 14.0F, 2.0F);
      this.g = new dwn(this, 0, 18);
      this.g.a(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F);
      this.g.a(-2.5F, 16.0F, 7.0F);
      this.h = new dwn(this, 0, 18);
      this.h.a(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F);
      this.h.a(0.5F, 16.0F, 7.0F);
      this.i = new dwn(this, 0, 18);
      this.i.a(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F);
      this.i.a(-2.5F, 16.0F, -4.0F);
      this.j = new dwn(this, 0, 18);
      this.j.a(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F);
      this.j.a(0.5F, 16.0F, -4.0F);
      this.k = new dwn(this, 9, 18);
      this.k.a(-1.0F, 12.0F, 8.0F);
      this.l = new dwn(this, 9, 18);
      this.l.a(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F);
      this.k.b(this.l);
      this.b.a(16, 14).a(-2.0F, -5.0F, 0.0F, 2.0F, 2.0F, 1.0F, 0.0F);
      this.b.a(16, 14).a(2.0F, -5.0F, 0.0F, 2.0F, 2.0F, 1.0F, 0.0F);
      this.b.a(0, 10).a(-0.5F, 0.0F, -5.0F, 3.0F, 3.0F, 4.0F, 0.0F);
   }

   @Override
   protected Iterable<dwn> a() {
      return ImmutableList.of(this.a);
   }

   @Override
   protected Iterable<dwn> b() {
      return ImmutableList.of(this.f, this.g, this.h, this.i, this.j, this.k, this.m);
   }

   public void a(T var1, float var2, float var3, float var4) {
      if (_snowman.H_()) {
         this.k.e = 0.0F;
      } else {
         this.k.e = afm.b(_snowman * 0.6662F) * 1.4F * _snowman;
      }

      if (_snowman.eM()) {
         this.m.a(-1.0F, 16.0F, -3.0F);
         this.m.d = (float) (Math.PI * 2.0 / 5.0);
         this.m.e = 0.0F;
         this.f.a(0.0F, 18.0F, 0.0F);
         this.f.d = (float) (Math.PI / 4);
         this.k.a(-1.0F, 21.0F, 6.0F);
         this.g.a(-2.5F, 22.7F, 2.0F);
         this.g.d = (float) (Math.PI * 3.0 / 2.0);
         this.h.a(0.5F, 22.7F, 2.0F);
         this.h.d = (float) (Math.PI * 3.0 / 2.0);
         this.i.d = 5.811947F;
         this.i.a(-2.49F, 17.0F, -4.0F);
         this.j.d = 5.811947F;
         this.j.a(0.51F, 17.0F, -4.0F);
      } else {
         this.f.a(0.0F, 14.0F, 2.0F);
         this.f.d = (float) (Math.PI / 2);
         this.m.a(-1.0F, 14.0F, -3.0F);
         this.m.d = this.f.d;
         this.k.a(-1.0F, 12.0F, 8.0F);
         this.g.a(-2.5F, 16.0F, 7.0F);
         this.h.a(0.5F, 16.0F, 7.0F);
         this.i.a(-2.5F, 16.0F, -4.0F);
         this.j.a(0.5F, 16.0F, -4.0F);
         this.g.d = afm.b(_snowman * 0.6662F) * 1.4F * _snowman;
         this.h.d = afm.b(_snowman * 0.6662F + (float) Math.PI) * 1.4F * _snowman;
         this.i.d = afm.b(_snowman * 0.6662F + (float) Math.PI) * 1.4F * _snowman;
         this.j.d = afm.b(_snowman * 0.6662F) * 1.4F * _snowman;
      }

      this.b.f = _snowman.z(_snowman) + _snowman.g(_snowman, 0.0F);
      this.m.f = _snowman.g(_snowman, -0.08F);
      this.f.f = _snowman.g(_snowman, -0.16F);
      this.l.f = _snowman.g(_snowman, -0.2F);
   }

   public void a(T var1, float var2, float var3, float var4, float var5, float var6) {
      this.a.d = _snowman * (float) (Math.PI / 180.0);
      this.a.e = _snowman * (float) (Math.PI / 180.0);
      this.k.d = _snowman;
   }
}
