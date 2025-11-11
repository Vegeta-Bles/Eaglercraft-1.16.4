import com.google.common.collect.ImmutableList;

public class dvw<T extends aqa> extends dur<T> {
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

   public dvw() {
      float _snowman = 0.0F;
      int _snowmanx = 15;
      this.a = new dwn(this, 32, 4);
      this.a.a(-4.0F, -4.0F, -8.0F, 8.0F, 8.0F, 8.0F, 0.0F);
      this.a.a(0.0F, 15.0F, -3.0F);
      this.b = new dwn(this, 0, 0);
      this.b.a(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, 0.0F);
      this.b.a(0.0F, 15.0F, 0.0F);
      this.f = new dwn(this, 0, 12);
      this.f.a(-5.0F, -4.0F, -6.0F, 10.0F, 8.0F, 12.0F, 0.0F);
      this.f.a(0.0F, 15.0F, 9.0F);
      this.g = new dwn(this, 18, 0);
      this.g.a(-15.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, 0.0F);
      this.g.a(-4.0F, 15.0F, 2.0F);
      this.h = new dwn(this, 18, 0);
      this.h.a(-1.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, 0.0F);
      this.h.a(4.0F, 15.0F, 2.0F);
      this.i = new dwn(this, 18, 0);
      this.i.a(-15.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, 0.0F);
      this.i.a(-4.0F, 15.0F, 1.0F);
      this.j = new dwn(this, 18, 0);
      this.j.a(-1.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, 0.0F);
      this.j.a(4.0F, 15.0F, 1.0F);
      this.k = new dwn(this, 18, 0);
      this.k.a(-15.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, 0.0F);
      this.k.a(-4.0F, 15.0F, 0.0F);
      this.l = new dwn(this, 18, 0);
      this.l.a(-1.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, 0.0F);
      this.l.a(4.0F, 15.0F, 0.0F);
      this.m = new dwn(this, 18, 0);
      this.m.a(-15.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, 0.0F);
      this.m.a(-4.0F, 15.0F, -1.0F);
      this.n = new dwn(this, 18, 0);
      this.n.a(-1.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, 0.0F);
      this.n.a(4.0F, 15.0F, -1.0F);
   }

   @Override
   public Iterable<dwn> a() {
      return ImmutableList.of(this.a, this.b, this.f, this.g, this.h, this.i, this.j, this.k, this.l, this.m, this.n);
   }

   @Override
   public void a(T var1, float var2, float var3, float var4, float var5, float var6) {
      this.a.e = _snowman * (float) (Math.PI / 180.0);
      this.a.d = _snowman * (float) (Math.PI / 180.0);
      float _snowman = (float) (Math.PI / 4);
      this.g.f = (float) (-Math.PI / 4);
      this.h.f = (float) (Math.PI / 4);
      this.i.f = -0.58119464F;
      this.j.f = 0.58119464F;
      this.k.f = -0.58119464F;
      this.l.f = 0.58119464F;
      this.m.f = (float) (-Math.PI / 4);
      this.n.f = (float) (Math.PI / 4);
      float _snowmanx = -0.0F;
      float _snowmanxx = (float) (Math.PI / 8);
      this.g.e = (float) (Math.PI / 4);
      this.h.e = (float) (-Math.PI / 4);
      this.i.e = (float) (Math.PI / 8);
      this.j.e = (float) (-Math.PI / 8);
      this.k.e = (float) (-Math.PI / 8);
      this.l.e = (float) (Math.PI / 8);
      this.m.e = (float) (-Math.PI / 4);
      this.n.e = (float) (Math.PI / 4);
      float _snowmanxxx = -(afm.b(_snowman * 0.6662F * 2.0F + 0.0F) * 0.4F) * _snowman;
      float _snowmanxxxx = -(afm.b(_snowman * 0.6662F * 2.0F + (float) Math.PI) * 0.4F) * _snowman;
      float _snowmanxxxxx = -(afm.b(_snowman * 0.6662F * 2.0F + (float) (Math.PI / 2)) * 0.4F) * _snowman;
      float _snowmanxxxxxx = -(afm.b(_snowman * 0.6662F * 2.0F + (float) (Math.PI * 3.0 / 2.0)) * 0.4F) * _snowman;
      float _snowmanxxxxxxx = Math.abs(afm.a(_snowman * 0.6662F + 0.0F) * 0.4F) * _snowman;
      float _snowmanxxxxxxxx = Math.abs(afm.a(_snowman * 0.6662F + (float) Math.PI) * 0.4F) * _snowman;
      float _snowmanxxxxxxxxx = Math.abs(afm.a(_snowman * 0.6662F + (float) (Math.PI / 2)) * 0.4F) * _snowman;
      float _snowmanxxxxxxxxxx = Math.abs(afm.a(_snowman * 0.6662F + (float) (Math.PI * 3.0 / 2.0)) * 0.4F) * _snowman;
      this.g.e += _snowmanxxx;
      this.h.e += -_snowmanxxx;
      this.i.e += _snowmanxxxx;
      this.j.e += -_snowmanxxxx;
      this.k.e += _snowmanxxxxx;
      this.l.e += -_snowmanxxxxx;
      this.m.e += _snowmanxxxxxx;
      this.n.e += -_snowmanxxxxxx;
      this.g.f += _snowmanxxxxxxx;
      this.h.f += -_snowmanxxxxxxx;
      this.i.f += _snowmanxxxxxxxx;
      this.j.f += -_snowmanxxxxxxxx;
      this.k.f += _snowmanxxxxxxxxx;
      this.l.f += -_snowmanxxxxxxxxx;
      this.m.f += _snowmanxxxxxxxxxx;
      this.n.f += -_snowmanxxxxxxxxxx;
   }
}
