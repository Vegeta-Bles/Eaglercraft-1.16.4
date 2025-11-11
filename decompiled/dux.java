import com.google.common.collect.ImmutableList;

public class dux<T extends aqa> extends dtf<T> {
   protected final dwn a;
   protected final dwn b;
   protected final dwn f;
   protected final dwn g;
   protected final dwn h;
   protected final dwn i;
   protected final dwn j;
   protected final dwn k;
   protected int l = 1;

   public dux(float var1) {
      super(true, 10.0F, 4.0F);
      this.j = new dwn(this);
      this.j.a("main", -2.5F, -2.0F, -3.0F, 5, 4, 5, _snowman, 0, 0);
      this.j.a("nose", -1.5F, 0.0F, -4.0F, 3, 2, 2, _snowman, 0, 24);
      this.j.a("ear1", -2.0F, -3.0F, 0.0F, 1, 1, 2, _snowman, 0, 10);
      this.j.a("ear2", 1.0F, -3.0F, 0.0F, 1, 1, 2, _snowman, 6, 10);
      this.j.a(0.0F, 15.0F, -9.0F);
      this.k = new dwn(this, 20, 0);
      this.k.a(-2.0F, 3.0F, -8.0F, 4.0F, 16.0F, 6.0F, _snowman);
      this.k.a(0.0F, 12.0F, -10.0F);
      this.h = new dwn(this, 0, 15);
      this.h.a(-0.5F, 0.0F, 0.0F, 1.0F, 8.0F, 1.0F, _snowman);
      this.h.d = 0.9F;
      this.h.a(0.0F, 15.0F, 8.0F);
      this.i = new dwn(this, 4, 15);
      this.i.a(-0.5F, 0.0F, 0.0F, 1.0F, 8.0F, 1.0F, _snowman);
      this.i.a(0.0F, 20.0F, 14.0F);
      this.a = new dwn(this, 8, 13);
      this.a.a(-1.0F, 0.0F, 1.0F, 2.0F, 6.0F, 2.0F, _snowman);
      this.a.a(1.1F, 18.0F, 5.0F);
      this.b = new dwn(this, 8, 13);
      this.b.a(-1.0F, 0.0F, 1.0F, 2.0F, 6.0F, 2.0F, _snowman);
      this.b.a(-1.1F, 18.0F, 5.0F);
      this.f = new dwn(this, 40, 0);
      this.f.a(-1.0F, 0.0F, 0.0F, 2.0F, 10.0F, 2.0F, _snowman);
      this.f.a(1.2F, 14.1F, -5.0F);
      this.g = new dwn(this, 40, 0);
      this.g.a(-1.0F, 0.0F, 0.0F, 2.0F, 10.0F, 2.0F, _snowman);
      this.g.a(-1.2F, 14.1F, -5.0F);
   }

   @Override
   protected Iterable<dwn> a() {
      return ImmutableList.of(this.j);
   }

   @Override
   protected Iterable<dwn> b() {
      return ImmutableList.of(this.k, this.a, this.b, this.f, this.g, this.h, this.i);
   }

   @Override
   public void a(T var1, float var2, float var3, float var4, float var5, float var6) {
      this.j.d = _snowman * (float) (Math.PI / 180.0);
      this.j.e = _snowman * (float) (Math.PI / 180.0);
      if (this.l != 3) {
         this.k.d = (float) (Math.PI / 2);
         if (this.l == 2) {
            this.a.d = afm.b(_snowman * 0.6662F) * _snowman;
            this.b.d = afm.b(_snowman * 0.6662F + 0.3F) * _snowman;
            this.f.d = afm.b(_snowman * 0.6662F + (float) Math.PI + 0.3F) * _snowman;
            this.g.d = afm.b(_snowman * 0.6662F + (float) Math.PI) * _snowman;
            this.i.d = 1.7278761F + (float) (Math.PI / 10) * afm.b(_snowman) * _snowman;
         } else {
            this.a.d = afm.b(_snowman * 0.6662F) * _snowman;
            this.b.d = afm.b(_snowman * 0.6662F + (float) Math.PI) * _snowman;
            this.f.d = afm.b(_snowman * 0.6662F + (float) Math.PI) * _snowman;
            this.g.d = afm.b(_snowman * 0.6662F) * _snowman;
            if (this.l == 1) {
               this.i.d = 1.7278761F + (float) (Math.PI / 4) * afm.b(_snowman) * _snowman;
            } else {
               this.i.d = 1.7278761F + 0.47123894F * afm.b(_snowman) * _snowman;
            }
         }
      }
   }

   @Override
   public void a(T var1, float var2, float var3, float var4) {
      this.k.b = 12.0F;
      this.k.c = -10.0F;
      this.j.b = 15.0F;
      this.j.c = -9.0F;
      this.h.b = 15.0F;
      this.h.c = 8.0F;
      this.i.b = 20.0F;
      this.i.c = 14.0F;
      this.f.b = 14.1F;
      this.f.c = -5.0F;
      this.g.b = 14.1F;
      this.g.c = -5.0F;
      this.a.b = 18.0F;
      this.a.c = 5.0F;
      this.b.b = 18.0F;
      this.b.c = 5.0F;
      this.h.d = 0.9F;
      if (_snowman.bz()) {
         this.k.b++;
         this.j.b += 2.0F;
         this.h.b++;
         this.i.b += -4.0F;
         this.i.c += 2.0F;
         this.h.d = (float) (Math.PI / 2);
         this.i.d = (float) (Math.PI / 2);
         this.l = 0;
      } else if (_snowman.bA()) {
         this.i.b = this.h.b;
         this.i.c += 2.0F;
         this.h.d = (float) (Math.PI / 2);
         this.i.d = (float) (Math.PI / 2);
         this.l = 2;
      } else {
         this.l = 1;
      }
   }
}
