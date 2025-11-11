import com.google.common.collect.ImmutableList;

public class dwf<T extends aqa> extends dur<T> implements dui, dwe {
   protected dwn a;
   protected dwn b;
   protected final dwn f;
   protected final dwn g;
   protected final dwn h;
   protected final dwn i;
   protected final dwn j;
   protected final dwn k;
   protected final dwn l;

   public dwf(float var1) {
      this(_snowman, 64, 64);
   }

   public dwf(float var1, int var2, int var3) {
      float _snowman = 0.5F;
      this.a = new dwn(this).b(_snowman, _snowman);
      this.a.a(0.0F, 0.0F, 0.0F);
      this.a.a(0, 0).a(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, _snowman);
      this.b = new dwn(this).b(_snowman, _snowman);
      this.b.a(0.0F, 0.0F, 0.0F);
      this.b.a(32, 0).a(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, _snowman + 0.5F);
      this.a.b(this.b);
      this.f = new dwn(this).b(_snowman, _snowman);
      this.f.a(0.0F, 0.0F, 0.0F);
      this.f.a(30, 47).a(-8.0F, -8.0F, -6.0F, 16.0F, 16.0F, 1.0F, _snowman);
      this.f.d = (float) (-Math.PI / 2);
      this.b.b(this.f);
      this.l = new dwn(this).b(_snowman, _snowman);
      this.l.a(0.0F, -2.0F, 0.0F);
      this.l.a(24, 0).a(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F, _snowman);
      this.a.b(this.l);
      this.g = new dwn(this).b(_snowman, _snowman);
      this.g.a(0.0F, 0.0F, 0.0F);
      this.g.a(16, 20).a(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F, _snowman);
      this.h = new dwn(this).b(_snowman, _snowman);
      this.h.a(0.0F, 0.0F, 0.0F);
      this.h.a(0, 38).a(-4.0F, 0.0F, -3.0F, 8.0F, 18.0F, 6.0F, _snowman + 0.5F);
      this.g.b(this.h);
      this.i = new dwn(this).b(_snowman, _snowman);
      this.i.a(0.0F, 2.0F, 0.0F);
      this.i.a(44, 22).a(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, _snowman);
      this.i.a(44, 22).a(4.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, _snowman, true);
      this.i.a(40, 38).a(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 4.0F, _snowman);
      this.j = new dwn(this, 0, 22).b(_snowman, _snowman);
      this.j.a(-2.0F, 12.0F, 0.0F);
      this.j.a(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, _snowman);
      this.k = new dwn(this, 0, 22).b(_snowman, _snowman);
      this.k.g = true;
      this.k.a(2.0F, 12.0F, 0.0F);
      this.k.a(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, _snowman);
   }

   @Override
   public Iterable<dwn> a() {
      return ImmutableList.of(this.a, this.g, this.j, this.k, this.i);
   }

   @Override
   public void a(T var1, float var2, float var3, float var4, float var5, float var6) {
      boolean _snowman = false;
      if (_snowman instanceof bfe) {
         _snowman = ((bfe)_snowman).eK() > 0;
      }

      this.a.e = _snowman * (float) (Math.PI / 180.0);
      this.a.d = _snowman * (float) (Math.PI / 180.0);
      if (_snowman) {
         this.a.f = 0.3F * afm.a(0.45F * _snowman);
         this.a.d = 0.4F;
      } else {
         this.a.f = 0.0F;
      }

      this.i.b = 3.0F;
      this.i.c = -1.0F;
      this.i.d = -0.75F;
      this.j.d = afm.b(_snowman * 0.6662F) * 1.4F * _snowman * 0.5F;
      this.k.d = afm.b(_snowman * 0.6662F + (float) Math.PI) * 1.4F * _snowman * 0.5F;
      this.j.e = 0.0F;
      this.k.e = 0.0F;
   }

   @Override
   public dwn c() {
      return this.a;
   }

   @Override
   public void a(boolean var1) {
      this.a.h = _snowman;
      this.b.h = _snowman;
      this.f.h = _snowman;
   }
}
