import com.google.common.collect.ImmutableList;

public class duk<T extends bbb> extends dtf<T> {
   protected final dwn a;
   protected final dwn b;
   private final dwn f;
   private final dwn g;
   private final dwn h;
   private final dwn i;
   private final dwn j;
   private final dwn k;
   private final dwn l;
   private final dwn m;
   private final dwn n;
   private final dwn[] o;
   private final dwn[] p;

   public duk(float var1) {
      super(true, 16.2F, 1.36F, 2.7272F, 2.0F, 20.0F);
      this.r = 64;
      this.s = 64;
      this.a = new dwn(this, 0, 32);
      this.a.a(-5.0F, -8.0F, -17.0F, 10.0F, 10.0F, 22.0F, 0.05F);
      this.a.a(0.0F, 11.0F, 5.0F);
      this.b = new dwn(this, 0, 35);
      this.b.a(-2.05F, -6.0F, -2.0F, 4.0F, 12.0F, 7.0F);
      this.b.d = (float) (Math.PI / 6);
      dwn _snowman = new dwn(this, 0, 13);
      _snowman.a(-3.0F, -11.0F, -2.0F, 6.0F, 5.0F, 7.0F, _snowman);
      dwn _snowmanx = new dwn(this, 56, 36);
      _snowmanx.a(-1.0F, -11.0F, 5.01F, 2.0F, 16.0F, 2.0F, _snowman);
      dwn _snowmanxx = new dwn(this, 0, 25);
      _snowmanxx.a(-2.0F, -11.0F, -7.0F, 4.0F, 5.0F, 5.0F, _snowman);
      this.b.b(_snowman);
      this.b.b(_snowmanx);
      this.b.b(_snowmanxx);
      this.a(this.b);
      this.f = new dwn(this, 48, 21);
      this.f.g = true;
      this.f.a(-3.0F, -1.01F, -1.0F, 4.0F, 11.0F, 4.0F, _snowman);
      this.f.a(4.0F, 14.0F, 7.0F);
      this.g = new dwn(this, 48, 21);
      this.g.a(-1.0F, -1.01F, -1.0F, 4.0F, 11.0F, 4.0F, _snowman);
      this.g.a(-4.0F, 14.0F, 7.0F);
      this.h = new dwn(this, 48, 21);
      this.h.g = true;
      this.h.a(-3.0F, -1.01F, -1.9F, 4.0F, 11.0F, 4.0F, _snowman);
      this.h.a(4.0F, 6.0F, -12.0F);
      this.i = new dwn(this, 48, 21);
      this.i.a(-1.0F, -1.01F, -1.9F, 4.0F, 11.0F, 4.0F, _snowman);
      this.i.a(-4.0F, 6.0F, -12.0F);
      float _snowmanxxx = 5.5F;
      this.j = new dwn(this, 48, 21);
      this.j.g = true;
      this.j.a(-3.0F, -1.01F, -1.0F, 4.0F, 11.0F, 4.0F, _snowman, _snowman + 5.5F, _snowman);
      this.j.a(4.0F, 14.0F, 7.0F);
      this.k = new dwn(this, 48, 21);
      this.k.a(-1.0F, -1.01F, -1.0F, 4.0F, 11.0F, 4.0F, _snowman, _snowman + 5.5F, _snowman);
      this.k.a(-4.0F, 14.0F, 7.0F);
      this.l = new dwn(this, 48, 21);
      this.l.g = true;
      this.l.a(-3.0F, -1.01F, -1.9F, 4.0F, 11.0F, 4.0F, _snowman, _snowman + 5.5F, _snowman);
      this.l.a(4.0F, 6.0F, -12.0F);
      this.m = new dwn(this, 48, 21);
      this.m.a(-1.0F, -1.01F, -1.9F, 4.0F, 11.0F, 4.0F, _snowman, _snowman + 5.5F, _snowman);
      this.m.a(-4.0F, 6.0F, -12.0F);
      this.n = new dwn(this, 42, 36);
      this.n.a(-1.5F, 0.0F, 0.0F, 3.0F, 14.0F, 4.0F, _snowman);
      this.n.a(0.0F, -5.0F, 2.0F);
      this.n.d = (float) (Math.PI / 6);
      this.a.b(this.n);
      dwn _snowmanxxxx = new dwn(this, 26, 0);
      _snowmanxxxx.a(-5.0F, -8.0F, -9.0F, 10.0F, 9.0F, 9.0F, 0.5F);
      this.a.b(_snowmanxxxx);
      dwn _snowmanxxxxx = new dwn(this, 29, 5);
      _snowmanxxxxx.a(2.0F, -9.0F, -6.0F, 1.0F, 2.0F, 2.0F, _snowman);
      this.b.b(_snowmanxxxxx);
      dwn _snowmanxxxxxx = new dwn(this, 29, 5);
      _snowmanxxxxxx.a(-3.0F, -9.0F, -6.0F, 1.0F, 2.0F, 2.0F, _snowman);
      this.b.b(_snowmanxxxxxx);
      dwn _snowmanxxxxxxx = new dwn(this, 32, 2);
      _snowmanxxxxxxx.a(3.1F, -6.0F, -8.0F, 0.0F, 3.0F, 16.0F, _snowman);
      _snowmanxxxxxxx.d = (float) (-Math.PI / 6);
      this.b.b(_snowmanxxxxxxx);
      dwn _snowmanxxxxxxxx = new dwn(this, 32, 2);
      _snowmanxxxxxxxx.a(-3.1F, -6.0F, -8.0F, 0.0F, 3.0F, 16.0F, _snowman);
      _snowmanxxxxxxxx.d = (float) (-Math.PI / 6);
      this.b.b(_snowmanxxxxxxxx);
      dwn _snowmanxxxxxxxxx = new dwn(this, 1, 1);
      _snowmanxxxxxxxxx.a(-3.0F, -11.0F, -1.9F, 6.0F, 5.0F, 6.0F, 0.2F);
      this.b.b(_snowmanxxxxxxxxx);
      dwn _snowmanxxxxxxxxxx = new dwn(this, 19, 0);
      _snowmanxxxxxxxxxx.a(-2.0F, -11.0F, -4.0F, 4.0F, 5.0F, 2.0F, 0.2F);
      this.b.b(_snowmanxxxxxxxxxx);
      this.o = new dwn[]{_snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx};
      this.p = new dwn[]{_snowmanxxxxxxx, _snowmanxxxxxxxx};
   }

   protected void a(dwn var1) {
      dwn _snowman = new dwn(this, 19, 16);
      _snowman.a(0.55F, -13.0F, 4.0F, 2.0F, 3.0F, 1.0F, -0.001F);
      dwn _snowmanx = new dwn(this, 19, 16);
      _snowmanx.a(-2.55F, -13.0F, 4.0F, 2.0F, 3.0F, 1.0F, -0.001F);
      _snowman.b(_snowman);
      _snowman.b(_snowmanx);
   }

   public void a(T var1, float var2, float var3, float var4, float var5, float var6) {
      boolean _snowman = _snowman.M_();
      boolean _snowmanx = _snowman.bs();

      for (dwn _snowmanxx : this.o) {
         _snowmanxx.h = _snowman;
      }

      for (dwn _snowmanxx : this.p) {
         _snowmanxx.h = _snowmanx && _snowman;
      }

      this.a.b = 11.0F;
   }

   @Override
   public Iterable<dwn> a() {
      return ImmutableList.of(this.b);
   }

   @Override
   protected Iterable<dwn> b() {
      return ImmutableList.of(this.a, this.f, this.g, this.h, this.i, this.j, this.k, this.l, this.m);
   }

   public void a(T var1, float var2, float var3, float var4) {
      super.a(_snowman, _snowman, _snowman, _snowman);
      float _snowman = afm.j(_snowman.aB, _snowman.aA, _snowman);
      float _snowmanx = afm.j(_snowman.aD, _snowman.aC, _snowman);
      float _snowmanxx = afm.g(_snowman, _snowman.s, _snowman.q);
      float _snowmanxxx = _snowmanx - _snowman;
      float _snowmanxxxx = _snowmanxx * (float) (Math.PI / 180.0);
      if (_snowmanxxx > 20.0F) {
         _snowmanxxx = 20.0F;
      }

      if (_snowmanxxx < -20.0F) {
         _snowmanxxx = -20.0F;
      }

      if (_snowman > 0.2F) {
         _snowmanxxxx += afm.b(_snowman * 0.4F) * 0.15F * _snowman;
      }

      float _snowmanxxxxx = _snowman.y(_snowman);
      float _snowmanxxxxxx = _snowman.z(_snowman);
      float _snowmanxxxxxxx = 1.0F - _snowmanxxxxxx;
      float _snowmanxxxxxxxx = _snowman.A(_snowman);
      boolean _snowmanxxxxxxxxx = _snowman.bo != 0;
      float _snowmanxxxxxxxxxx = (float)_snowman.K + _snowman;
      this.b.b = 4.0F;
      this.b.c = -12.0F;
      this.a.d = 0.0F;
      this.b.d = (float) (Math.PI / 6) + _snowmanxxxx;
      this.b.e = _snowmanxxx * (float) (Math.PI / 180.0);
      float _snowmanxxxxxxxxxxx = _snowman.aE() ? 0.2F : 1.0F;
      float _snowmanxxxxxxxxxxxx = afm.b(_snowmanxxxxxxxxxxx * _snowman * 0.6662F + (float) Math.PI);
      float _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx * 0.8F * _snowman;
      float _snowmanxxxxxxxxxxxxxx = (1.0F - Math.max(_snowmanxxxxxx, _snowmanxxxxx)) * ((float) (Math.PI / 6) + _snowmanxxxx + _snowmanxxxxxxxx * afm.a(_snowmanxxxxxxxxxx) * 0.05F);
      this.b.d = _snowmanxxxxxx * ((float) (Math.PI / 12) + _snowmanxxxx) + _snowmanxxxxx * (2.1816616F + afm.a(_snowmanxxxxxxxxxx) * 0.05F) + _snowmanxxxxxxxxxxxxxx;
      this.b.e = _snowmanxxxxxx * _snowmanxxx * (float) (Math.PI / 180.0) + (1.0F - Math.max(_snowmanxxxxxx, _snowmanxxxxx)) * this.b.e;
      this.b.b = _snowmanxxxxxx * -4.0F + _snowmanxxxxx * 11.0F + (1.0F - Math.max(_snowmanxxxxxx, _snowmanxxxxx)) * this.b.b;
      this.b.c = _snowmanxxxxxx * -4.0F + _snowmanxxxxx * -12.0F + (1.0F - Math.max(_snowmanxxxxxx, _snowmanxxxxx)) * this.b.c;
      this.a.d = _snowmanxxxxxx * (float) (-Math.PI / 4) + _snowmanxxxxxxx * this.a.d;
      float _snowmanxxxxxxxxxxxxxxx = (float) (Math.PI / 12) * _snowmanxxxxxx;
      float _snowmanxxxxxxxxxxxxxxxx = afm.b(_snowmanxxxxxxxxxx * 0.6F + (float) Math.PI);
      this.h.b = 2.0F * _snowmanxxxxxx + 14.0F * _snowmanxxxxxxx;
      this.h.c = -6.0F * _snowmanxxxxxx - 10.0F * _snowmanxxxxxxx;
      this.i.b = this.h.b;
      this.i.c = this.h.c;
      float _snowmanxxxxxxxxxxxxxxxxx = ((float) (-Math.PI / 3) + _snowmanxxxxxxxxxxxxxxxx) * _snowmanxxxxxx + _snowmanxxxxxxxxxxxxx * _snowmanxxxxxxx;
      float _snowmanxxxxxxxxxxxxxxxxxx = ((float) (-Math.PI / 3) - _snowmanxxxxxxxxxxxxxxxx) * _snowmanxxxxxx - _snowmanxxxxxxxxxxxxx * _snowmanxxxxxxx;
      this.f.d = _snowmanxxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxx * 0.5F * _snowman * _snowmanxxxxxxx;
      this.g.d = _snowmanxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxx * 0.5F * _snowman * _snowmanxxxxxxx;
      this.h.d = _snowmanxxxxxxxxxxxxxxxxx;
      this.i.d = _snowmanxxxxxxxxxxxxxxxxxx;
      this.n.d = (float) (Math.PI / 6) + _snowman * 0.75F;
      this.n.b = -5.0F + _snowman;
      this.n.c = 2.0F + _snowman * 2.0F;
      if (_snowmanxxxxxxxxx) {
         this.n.e = afm.b(_snowmanxxxxxxxxxx * 0.7F);
      } else {
         this.n.e = 0.0F;
      }

      this.j.b = this.f.b;
      this.j.c = this.f.c;
      this.j.d = this.f.d;
      this.k.b = this.g.b;
      this.k.c = this.g.c;
      this.k.d = this.g.d;
      this.l.b = this.h.b;
      this.l.c = this.h.c;
      this.l.d = this.h.d;
      this.m.b = this.i.b;
      this.m.c = this.i.c;
      this.m.d = this.i.d;
      boolean _snowmanxxxxxxxxxxxxxxxxxxx = _snowman.w_();
      this.f.h = !_snowmanxxxxxxxxxxxxxxxxxxx;
      this.g.h = !_snowmanxxxxxxxxxxxxxxxxxxx;
      this.h.h = !_snowmanxxxxxxxxxxxxxxxxxxx;
      this.i.h = !_snowmanxxxxxxxxxxxxxxxxxxx;
      this.j.h = _snowmanxxxxxxxxxxxxxxxxxxx;
      this.k.h = _snowmanxxxxxxxxxxxxxxxxxxx;
      this.l.h = _snowmanxxxxxxxxxxxxxxxxxxx;
      this.m.h = _snowmanxxxxxxxxxxxxxxxxxxx;
      this.a.b = _snowmanxxxxxxxxxxxxxxxxxxx ? 10.8F : 0.0F;
   }
}
