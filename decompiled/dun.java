import com.google.common.collect.ImmutableList;

public class dun<T extends bcy> extends dur<T> implements dth, dui {
   private final dwn a;
   private final dwn b;
   private final dwn f;
   private final dwn g;
   private final dwn h;
   private final dwn i;
   private final dwn j;
   private final dwn k;

   public dun(float var1, float var2, int var3, int var4) {
      this.a = new dwn(this).b(_snowman, _snowman);
      this.a.a(0.0F, 0.0F + _snowman, 0.0F);
      this.a.a(0, 0).a(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, _snowman);
      this.b = new dwn(this, 32, 0).b(_snowman, _snowman);
      this.b.a(-4.0F, -10.0F, -4.0F, 8.0F, 12.0F, 8.0F, _snowman + 0.45F);
      this.a.b(this.b);
      this.b.h = false;
      dwn _snowman = new dwn(this).b(_snowman, _snowman);
      _snowman.a(0.0F, _snowman - 2.0F, 0.0F);
      _snowman.a(24, 0).a(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F, _snowman);
      this.a.b(_snowman);
      this.f = new dwn(this).b(_snowman, _snowman);
      this.f.a(0.0F, 0.0F + _snowman, 0.0F);
      this.f.a(16, 20).a(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F, _snowman);
      this.f.a(0, 38).a(-4.0F, 0.0F, -3.0F, 8.0F, 18.0F, 6.0F, _snowman + 0.5F);
      this.g = new dwn(this).b(_snowman, _snowman);
      this.g.a(0.0F, 0.0F + _snowman + 2.0F, 0.0F);
      this.g.a(44, 22).a(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, _snowman);
      dwn _snowmanx = new dwn(this, 44, 22).b(_snowman, _snowman);
      _snowmanx.g = true;
      _snowmanx.a(4.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, _snowman);
      this.g.b(_snowmanx);
      this.g.a(40, 38).a(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 4.0F, _snowman);
      this.h = new dwn(this, 0, 22).b(_snowman, _snowman);
      this.h.a(-2.0F, 12.0F + _snowman, 0.0F);
      this.h.a(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, _snowman);
      this.i = new dwn(this, 0, 22).b(_snowman, _snowman);
      this.i.g = true;
      this.i.a(2.0F, 12.0F + _snowman, 0.0F);
      this.i.a(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, _snowman);
      this.j = new dwn(this, 40, 46).b(_snowman, _snowman);
      this.j.a(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, _snowman);
      this.j.a(-5.0F, 2.0F + _snowman, 0.0F);
      this.k = new dwn(this, 40, 46).b(_snowman, _snowman);
      this.k.g = true;
      this.k.a(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, _snowman);
      this.k.a(5.0F, 2.0F + _snowman, 0.0F);
   }

   @Override
   public Iterable<dwn> a() {
      return ImmutableList.of(this.a, this.f, this.h, this.i, this.g, this.j, this.k);
   }

   public void a(T var1, float var2, float var3, float var4, float var5, float var6) {
      this.a.e = _snowman * (float) (Math.PI / 180.0);
      this.a.d = _snowman * (float) (Math.PI / 180.0);
      this.g.b = 3.0F;
      this.g.c = -1.0F;
      this.g.d = -0.75F;
      if (this.d) {
         this.j.d = (float) (-Math.PI / 5);
         this.j.e = 0.0F;
         this.j.f = 0.0F;
         this.k.d = (float) (-Math.PI / 5);
         this.k.e = 0.0F;
         this.k.f = 0.0F;
         this.h.d = -1.4137167F;
         this.h.e = (float) (Math.PI / 10);
         this.h.f = 0.07853982F;
         this.i.d = -1.4137167F;
         this.i.e = (float) (-Math.PI / 10);
         this.i.f = -0.07853982F;
      } else {
         this.j.d = afm.b(_snowman * 0.6662F + (float) Math.PI) * 2.0F * _snowman * 0.5F;
         this.j.e = 0.0F;
         this.j.f = 0.0F;
         this.k.d = afm.b(_snowman * 0.6662F) * 2.0F * _snowman * 0.5F;
         this.k.e = 0.0F;
         this.k.f = 0.0F;
         this.h.d = afm.b(_snowman * 0.6662F) * 1.4F * _snowman * 0.5F;
         this.h.e = 0.0F;
         this.h.f = 0.0F;
         this.i.d = afm.b(_snowman * 0.6662F + (float) Math.PI) * 1.4F * _snowman * 0.5F;
         this.i.e = 0.0F;
         this.i.f = 0.0F;
      }

      bcy.a _snowman = _snowman.m();
      if (_snowman == bcy.a.b) {
         if (_snowman.dD().a()) {
            dtg.a(this.k, this.j, true, this.c, _snowman);
         } else {
            dtg.a(this.j, this.k, _snowman, this.c, _snowman);
         }
      } else if (_snowman == bcy.a.c) {
         this.j.c = 0.0F;
         this.j.a = -5.0F;
         this.k.c = 0.0F;
         this.k.a = 5.0F;
         this.j.d = afm.b(_snowman * 0.6662F) * 0.25F;
         this.k.d = afm.b(_snowman * 0.6662F) * 0.25F;
         this.j.f = (float) (Math.PI * 3.0 / 4.0);
         this.k.f = (float) (-Math.PI * 3.0 / 4.0);
         this.j.e = 0.0F;
         this.k.e = 0.0F;
      } else if (_snowman == bcy.a.d) {
         this.j.e = -0.1F + this.a.e;
         this.j.d = (float) (-Math.PI / 2) + this.a.d;
         this.k.d = -0.9424779F + this.a.d;
         this.k.e = this.a.e - 0.4F;
         this.k.f = (float) (Math.PI / 2);
      } else if (_snowman == bcy.a.e) {
         dtg.a(this.j, this.k, this.a, true);
      } else if (_snowman == bcy.a.f) {
         dtg.a(this.j, this.k, _snowman, true);
      } else if (_snowman == bcy.a.g) {
         this.j.c = 0.0F;
         this.j.a = -5.0F;
         this.j.d = afm.b(_snowman * 0.6662F) * 0.05F;
         this.j.f = 2.670354F;
         this.j.e = 0.0F;
         this.k.c = 0.0F;
         this.k.a = 5.0F;
         this.k.d = afm.b(_snowman * 0.6662F) * 0.05F;
         this.k.f = (float) (-Math.PI * 3.0 / 4.0);
         this.k.e = 0.0F;
      }

      boolean _snowmanx = _snowman == bcy.a.a;
      this.g.h = _snowmanx;
      this.k.h = !_snowmanx;
      this.j.h = !_snowmanx;
   }

   private dwn a(aqi var1) {
      return _snowman == aqi.a ? this.k : this.j;
   }

   public dwn b() {
      return this.b;
   }

   @Override
   public dwn c() {
      return this.a;
   }

   @Override
   public void a(aqi var1, dfm var2) {
      this.a(_snowman).a(_snowman);
   }
}
