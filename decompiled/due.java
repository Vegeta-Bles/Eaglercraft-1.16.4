import com.google.common.collect.ImmutableList;

public class due<T extends bah> extends dtf<T> {
   public final dwn a;
   private final dwn b;
   private final dwn f;
   private final dwn g;
   private final dwn h;
   private final dwn i;
   private final dwn j;
   private final dwn k;
   private final dwn l;
   private final dwn m;
   private float n;

   public due() {
      super(true, 8.0F, 3.35F);
      this.r = 48;
      this.s = 32;
      this.a = new dwn(this, 1, 5);
      this.a.a(-3.0F, -2.0F, -5.0F, 8.0F, 6.0F, 6.0F);
      this.a.a(-1.0F, 16.5F, -3.0F);
      this.b = new dwn(this, 8, 1);
      this.b.a(-3.0F, -4.0F, -4.0F, 2.0F, 2.0F, 1.0F);
      this.f = new dwn(this, 15, 1);
      this.f.a(3.0F, -4.0F, -4.0F, 2.0F, 2.0F, 1.0F);
      this.g = new dwn(this, 6, 18);
      this.g.a(-1.0F, 2.01F, -8.0F, 4.0F, 2.0F, 3.0F);
      this.a.b(this.b);
      this.a.b(this.f);
      this.a.b(this.g);
      this.h = new dwn(this, 24, 15);
      this.h.a(-3.0F, 3.999F, -3.5F, 6.0F, 11.0F, 6.0F);
      this.h.a(0.0F, 16.0F, -6.0F);
      float _snowman = 0.001F;
      this.i = new dwn(this, 13, 24);
      this.i.a(2.0F, 0.5F, -1.0F, 2.0F, 6.0F, 2.0F, 0.001F);
      this.i.a(-5.0F, 17.5F, 7.0F);
      this.j = new dwn(this, 4, 24);
      this.j.a(2.0F, 0.5F, -1.0F, 2.0F, 6.0F, 2.0F, 0.001F);
      this.j.a(-1.0F, 17.5F, 7.0F);
      this.k = new dwn(this, 13, 24);
      this.k.a(2.0F, 0.5F, -1.0F, 2.0F, 6.0F, 2.0F, 0.001F);
      this.k.a(-5.0F, 17.5F, 0.0F);
      this.l = new dwn(this, 4, 24);
      this.l.a(2.0F, 0.5F, -1.0F, 2.0F, 6.0F, 2.0F, 0.001F);
      this.l.a(-1.0F, 17.5F, 0.0F);
      this.m = new dwn(this, 30, 0);
      this.m.a(2.0F, 0.0F, -1.0F, 4.0F, 9.0F, 5.0F);
      this.m.a(-4.0F, 15.0F, -1.0F);
      this.h.b(this.m);
   }

   public void a(T var1, float var2, float var3, float var4) {
      this.h.d = (float) (Math.PI / 2);
      this.m.d = -0.05235988F;
      this.i.d = afm.b(_snowman * 0.6662F) * 1.4F * _snowman;
      this.j.d = afm.b(_snowman * 0.6662F + (float) Math.PI) * 1.4F * _snowman;
      this.k.d = afm.b(_snowman * 0.6662F + (float) Math.PI) * 1.4F * _snowman;
      this.l.d = afm.b(_snowman * 0.6662F) * 1.4F * _snowman;
      this.a.a(-1.0F, 16.5F, -3.0F);
      this.a.e = 0.0F;
      this.a.f = _snowman.y(_snowman);
      this.i.h = true;
      this.j.h = true;
      this.k.h = true;
      this.l.h = true;
      this.h.a(0.0F, 16.0F, -6.0F);
      this.h.f = 0.0F;
      this.i.a(-5.0F, 17.5F, 7.0F);
      this.j.a(-1.0F, 17.5F, 7.0F);
      if (_snowman.bz()) {
         this.h.d = 1.6755161F;
         float _snowman = _snowman.z(_snowman);
         this.h.a(0.0F, 16.0F + _snowman.z(_snowman), -6.0F);
         this.a.a(-1.0F, 16.5F + _snowman, -3.0F);
         this.a.e = 0.0F;
      } else if (_snowman.em()) {
         this.h.f = (float) (-Math.PI / 2);
         this.h.a(0.0F, 21.0F, -6.0F);
         this.m.d = (float) (-Math.PI * 5.0 / 6.0);
         if (this.e) {
            this.m.d = -2.1816616F;
            this.h.a(0.0F, 21.0F, -2.0F);
         }

         this.a.a(1.0F, 19.49F, -3.0F);
         this.a.d = 0.0F;
         this.a.e = (float) (-Math.PI * 2.0 / 3.0);
         this.a.f = 0.0F;
         this.i.h = false;
         this.j.h = false;
         this.k.h = false;
         this.l.h = false;
      } else if (_snowman.eM()) {
         this.h.d = (float) (Math.PI / 6);
         this.h.a(0.0F, 9.0F, -3.0F);
         this.m.d = (float) (Math.PI / 4);
         this.m.a(-4.0F, 15.0F, -2.0F);
         this.a.a(-1.0F, 10.0F, -0.25F);
         this.a.d = 0.0F;
         this.a.e = 0.0F;
         if (this.e) {
            this.a.a(-1.0F, 13.0F, -3.75F);
         }

         this.i.d = (float) (-Math.PI * 5.0 / 12.0);
         this.i.a(-5.0F, 21.5F, 6.75F);
         this.j.d = (float) (-Math.PI * 5.0 / 12.0);
         this.j.a(-1.0F, 21.5F, 6.75F);
         this.k.d = (float) (-Math.PI / 12);
         this.l.d = (float) (-Math.PI / 12);
      }
   }

   @Override
   protected Iterable<dwn> a() {
      return ImmutableList.of(this.a);
   }

   @Override
   protected Iterable<dwn> b() {
      return ImmutableList.of(this.h, this.i, this.j, this.k, this.l);
   }

   public void a(T var1, float var2, float var3, float var4, float var5, float var6) {
      if (!_snowman.em() && !_snowman.eN() && !_snowman.bz()) {
         this.a.d = _snowman * (float) (Math.PI / 180.0);
         this.a.e = _snowman * (float) (Math.PI / 180.0);
      }

      if (_snowman.em()) {
         this.a.d = 0.0F;
         this.a.e = (float) (-Math.PI * 2.0 / 3.0);
         this.a.f = afm.b(_snowman * 0.027F) / 22.0F;
      }

      if (_snowman.bz()) {
         float _snowman = afm.b(_snowman) * 0.01F;
         this.h.e = _snowman;
         this.i.f = _snowman;
         this.j.f = _snowman;
         this.k.f = _snowman / 2.0F;
         this.l.f = _snowman / 2.0F;
      }

      if (_snowman.eN()) {
         float _snowman = 0.1F;
         this.n += 0.67F;
         this.i.d = afm.b(this.n * 0.4662F) * 0.1F;
         this.j.d = afm.b(this.n * 0.4662F + (float) Math.PI) * 0.1F;
         this.k.d = afm.b(this.n * 0.4662F + (float) Math.PI) * 0.1F;
         this.l.d = afm.b(this.n * 0.4662F) * 0.1F;
      }
   }
}
