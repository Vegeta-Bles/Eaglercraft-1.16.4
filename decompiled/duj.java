import com.google.common.collect.ImmutableList;

public class duj<T extends aqn & beo> extends dtf<T> {
   private final dwn a;
   private final dwn b;
   private final dwn f;
   private final dwn g;
   private final dwn h;
   private final dwn i;
   private final dwn j;
   private final dwn k;
   private final dwn l;

   public duj() {
      super(true, 8.0F, 6.0F, 1.9F, 2.0F, 24.0F);
      this.r = 128;
      this.s = 64;
      this.g = new dwn(this);
      this.g.a(0.0F, 7.0F, 0.0F);
      this.g.a(1, 1).a(-8.0F, -7.0F, -13.0F, 16.0F, 14.0F, 26.0F);
      this.l = new dwn(this);
      this.l.a(0.0F, -14.0F, -5.0F);
      this.l.a(90, 33).a(0.0F, 0.0F, -9.0F, 0.0F, 10.0F, 19.0F, 0.001F);
      this.g.b(this.l);
      this.a = new dwn(this);
      this.a.a(0.0F, 2.0F, -12.0F);
      this.a.a(61, 1).a(-7.0F, -3.0F, -19.0F, 14.0F, 6.0F, 19.0F);
      this.b = new dwn(this);
      this.b.a(-6.0F, -2.0F, -3.0F);
      this.b.a(1, 1).a(-6.0F, -1.0F, -2.0F, 6.0F, 1.0F, 4.0F);
      this.b.f = (float) (-Math.PI * 2.0 / 9.0);
      this.a.b(this.b);
      this.f = new dwn(this);
      this.f.a(6.0F, -2.0F, -3.0F);
      this.f.a(1, 6).a(0.0F, -1.0F, -2.0F, 6.0F, 1.0F, 4.0F);
      this.f.f = (float) (Math.PI * 2.0 / 9.0);
      this.a.b(this.f);
      dwn _snowman = new dwn(this);
      _snowman.a(-7.0F, 2.0F, -12.0F);
      _snowman.a(10, 13).a(-1.0F, -11.0F, -1.0F, 2.0F, 11.0F, 2.0F);
      this.a.b(_snowman);
      dwn _snowmanx = new dwn(this);
      _snowmanx.a(7.0F, 2.0F, -12.0F);
      _snowmanx.a(1, 13).a(-1.0F, -11.0F, -1.0F, 2.0F, 11.0F, 2.0F);
      this.a.b(_snowmanx);
      this.a.d = 0.87266463F;
      int _snowmanxx = 14;
      int _snowmanxxx = 11;
      this.h = new dwn(this);
      this.h.a(-4.0F, 10.0F, -8.5F);
      this.h.a(66, 42).a(-3.0F, 0.0F, -3.0F, 6.0F, 14.0F, 6.0F);
      this.i = new dwn(this);
      this.i.a(4.0F, 10.0F, -8.5F);
      this.i.a(41, 42).a(-3.0F, 0.0F, -3.0F, 6.0F, 14.0F, 6.0F);
      this.j = new dwn(this);
      this.j.a(-5.0F, 13.0F, 10.0F);
      this.j.a(21, 45).a(-2.5F, 0.0F, -2.5F, 5.0F, 11.0F, 5.0F);
      this.k = new dwn(this);
      this.k.a(5.0F, 13.0F, 10.0F);
      this.k.a(0, 45).a(-2.5F, 0.0F, -2.5F, 5.0F, 11.0F, 5.0F);
   }

   @Override
   protected Iterable<dwn> a() {
      return ImmutableList.of(this.a);
   }

   @Override
   protected Iterable<dwn> b() {
      return ImmutableList.of(this.g, this.h, this.i, this.j, this.k);
   }

   public void a(T var1, float var2, float var3, float var4, float var5, float var6) {
      this.b.f = (float) (-Math.PI * 2.0 / 9.0) - _snowman * afm.a(_snowman);
      this.f.f = (float) (Math.PI * 2.0 / 9.0) + _snowman * afm.a(_snowman);
      this.a.e = _snowman * (float) (Math.PI / 180.0);
      int _snowman = _snowman.eM();
      float _snowmanx = 1.0F - (float)afm.a(10 - 2 * _snowman) / 10.0F;
      this.a.d = afm.g(_snowmanx, 0.87266463F, (float) (-Math.PI / 9));
      if (_snowman.w_()) {
         this.a.b = afm.g(_snowmanx, 2.0F, 5.0F);
         this.l.c = -3.0F;
      } else {
         this.a.b = 2.0F;
         this.l.c = -7.0F;
      }

      float _snowmanxx = 1.2F;
      this.h.d = afm.b(_snowman) * 1.2F * _snowman;
      this.i.d = afm.b(_snowman + (float) Math.PI) * 1.2F * _snowman;
      this.j.d = this.i.d;
      this.k.d = this.h.d;
   }
}
