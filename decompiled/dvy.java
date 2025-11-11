import com.google.common.collect.ImmutableList;

public class dvy<T extends bed> extends dur<T> {
   private final dwn a;
   private final dwn b;
   private final dwn f;
   private final dwn g;
   private final dwn h;
   private final dwn i;
   private final dwn j;
   private final dwn k;
   private final dwn l;

   public dvy() {
      this.r = 64;
      this.s = 128;
      this.a = new dwn(this, 0, 32);
      this.a.a(-4.0F, 8.0F, 0.0F);
      this.a.a(-2.0F, 0.0F, -2.0F, 4.0F, 16.0F, 4.0F, 0.0F);
      this.b = new dwn(this, 0, 55);
      this.b.a(4.0F, 8.0F, 0.0F);
      this.b.a(-2.0F, 0.0F, -2.0F, 4.0F, 16.0F, 4.0F, 0.0F);
      this.f = new dwn(this, 0, 0);
      this.f.a(0.0F, 1.0F, 0.0F);
      this.f.a(-8.0F, -6.0F, -8.0F, 16.0F, 14.0F, 16.0F, 0.0F);
      this.g = new dwn(this, 16, 65);
      this.g.a(-8.0F, 4.0F, -8.0F);
      this.g.a(-12.0F, 0.0F, 0.0F, 12.0F, 0.0F, 16.0F, 0.0F, true);
      this.a(this.g, 0.0F, 0.0F, -1.2217305F);
      this.h = new dwn(this, 16, 49);
      this.h.a(-8.0F, -1.0F, -8.0F);
      this.h.a(-12.0F, 0.0F, 0.0F, 12.0F, 0.0F, 16.0F, 0.0F, true);
      this.a(this.h, 0.0F, 0.0F, -1.134464F);
      this.i = new dwn(this, 16, 33);
      this.i.a(-8.0F, -5.0F, -8.0F);
      this.i.a(-12.0F, 0.0F, 0.0F, 12.0F, 0.0F, 16.0F, 0.0F, true);
      this.a(this.i, 0.0F, 0.0F, -0.87266463F);
      this.j = new dwn(this, 16, 33);
      this.j.a(8.0F, -6.0F, -8.0F);
      this.j.a(0.0F, 0.0F, 0.0F, 12.0F, 0.0F, 16.0F, 0.0F);
      this.a(this.j, 0.0F, 0.0F, 0.87266463F);
      this.k = new dwn(this, 16, 49);
      this.k.a(8.0F, -2.0F, -8.0F);
      this.k.a(0.0F, 0.0F, 0.0F, 12.0F, 0.0F, 16.0F, 0.0F);
      this.a(this.k, 0.0F, 0.0F, 1.134464F);
      this.l = new dwn(this, 16, 65);
      this.l.a(8.0F, 3.0F, -8.0F);
      this.l.a(0.0F, 0.0F, 0.0F, 12.0F, 0.0F, 16.0F, 0.0F);
      this.a(this.l, 0.0F, 0.0F, 1.2217305F);
      this.f.b(this.g);
      this.f.b(this.h);
      this.f.b(this.i);
      this.f.b(this.j);
      this.f.b(this.k);
      this.f.b(this.l);
   }

   public void a(bed var1, float var2, float var3, float var4, float var5, float var6) {
      _snowman = Math.min(0.25F, _snowman);
      if (_snowman.cn().size() <= 0) {
         this.f.d = _snowman * (float) (Math.PI / 180.0);
         this.f.e = _snowman * (float) (Math.PI / 180.0);
      } else {
         this.f.d = 0.0F;
         this.f.e = 0.0F;
      }

      float _snowman = 1.5F;
      this.f.f = 0.1F * afm.a(_snowman * 1.5F) * 4.0F * _snowman;
      this.f.b = 2.0F;
      this.f.b = this.f.b - 2.0F * afm.b(_snowman * 1.5F) * 2.0F * _snowman;
      this.b.d = afm.a(_snowman * 1.5F * 0.5F) * 2.0F * _snowman;
      this.a.d = afm.a(_snowman * 1.5F * 0.5F + (float) Math.PI) * 2.0F * _snowman;
      this.b.f = (float) (Math.PI / 18) * afm.b(_snowman * 1.5F * 0.5F) * _snowman;
      this.a.f = (float) (Math.PI / 18) * afm.b(_snowman * 1.5F * 0.5F + (float) Math.PI) * _snowman;
      this.b.b = 8.0F + 2.0F * afm.a(_snowman * 1.5F * 0.5F + (float) Math.PI) * 2.0F * _snowman;
      this.a.b = 8.0F + 2.0F * afm.a(_snowman * 1.5F * 0.5F) * 2.0F * _snowman;
      this.g.f = -1.2217305F;
      this.h.f = -1.134464F;
      this.i.f = -0.87266463F;
      this.j.f = 0.87266463F;
      this.k.f = 1.134464F;
      this.l.f = 1.2217305F;
      float _snowmanx = afm.b(_snowman * 1.5F + (float) Math.PI) * _snowman;
      this.g.f += _snowmanx * 1.3F;
      this.h.f += _snowmanx * 1.2F;
      this.i.f += _snowmanx * 0.6F;
      this.j.f += _snowmanx * 0.6F;
      this.k.f += _snowmanx * 1.2F;
      this.l.f += _snowmanx * 1.3F;
      float _snowmanxx = 1.0F;
      float _snowmanxxx = 1.0F;
      this.g.f = this.g.f + 0.05F * afm.a(_snowman * 1.0F * -0.4F);
      this.h.f = this.h.f + 0.1F * afm.a(_snowman * 1.0F * 0.2F);
      this.i.f = this.i.f + 0.1F * afm.a(_snowman * 1.0F * 0.4F);
      this.j.f = this.j.f + 0.1F * afm.a(_snowman * 1.0F * 0.4F);
      this.k.f = this.k.f + 0.1F * afm.a(_snowman * 1.0F * 0.2F);
      this.l.f = this.l.f + 0.05F * afm.a(_snowman * 1.0F * -0.4F);
   }

   public void a(dwn var1, float var2, float var3, float var4) {
      _snowman.d = _snowman;
      _snowman.e = _snowman;
      _snowman.f = _snowman;
   }

   @Override
   public Iterable<dwn> a() {
      return ImmutableList.of(this.f, this.b, this.a);
   }
}
