import com.google.common.collect.ImmutableList;

public class dus<T extends bba> extends duc<T> {
   private final dwn a;
   private final dwn b;
   private final dwn f;
   private final dwn g;
   private final dwn h;
   private final dwn i;
   private final dwn j;
   private final dwn k;

   public dus(float var1) {
      this.r = 128;
      this.s = 64;
      this.a = new dwn(this, 0, 0);
      this.a.a(-2.0F, -14.0F, -10.0F, 4.0F, 4.0F, 9.0F, _snowman);
      this.a.a(0.0F, 7.0F, -6.0F);
      this.a.a(0, 14).a(-4.0F, -16.0F, -6.0F, 8.0F, 18.0F, 6.0F, _snowman);
      this.a.a(17, 0).a(-4.0F, -19.0F, -4.0F, 3.0F, 3.0F, 2.0F, _snowman);
      this.a.a(17, 0).a(1.0F, -19.0F, -4.0F, 3.0F, 3.0F, 2.0F, _snowman);
      this.b = new dwn(this, 29, 0);
      this.b.a(-6.0F, -10.0F, -7.0F, 12.0F, 18.0F, 10.0F, _snowman);
      this.b.a(0.0F, 5.0F, 2.0F);
      this.j = new dwn(this, 45, 28);
      this.j.a(-3.0F, 0.0F, 0.0F, 8.0F, 8.0F, 3.0F, _snowman);
      this.j.a(-8.5F, 3.0F, 3.0F);
      this.j.e = (float) (Math.PI / 2);
      this.k = new dwn(this, 45, 41);
      this.k.a(-3.0F, 0.0F, 0.0F, 8.0F, 8.0F, 3.0F, _snowman);
      this.k.a(5.5F, 3.0F, 3.0F);
      this.k.e = (float) (Math.PI / 2);
      int _snowman = 4;
      int _snowmanx = 14;
      this.f = new dwn(this, 29, 29);
      this.f.a(-2.0F, 0.0F, -2.0F, 4.0F, 14.0F, 4.0F, _snowman);
      this.f.a(-2.5F, 10.0F, 6.0F);
      this.g = new dwn(this, 29, 29);
      this.g.a(-2.0F, 0.0F, -2.0F, 4.0F, 14.0F, 4.0F, _snowman);
      this.g.a(2.5F, 10.0F, 6.0F);
      this.h = new dwn(this, 29, 29);
      this.h.a(-2.0F, 0.0F, -2.0F, 4.0F, 14.0F, 4.0F, _snowman);
      this.h.a(-2.5F, 10.0F, -4.0F);
      this.i = new dwn(this, 29, 29);
      this.i.a(-2.0F, 0.0F, -2.0F, 4.0F, 14.0F, 4.0F, _snowman);
      this.i.a(2.5F, 10.0F, -4.0F);
      this.f.a--;
      this.g.a++;
      this.f.c += 0.0F;
      this.g.c += 0.0F;
      this.h.a--;
      this.i.a++;
      this.h.c--;
      this.i.c--;
   }

   public void a(T var1, float var2, float var3, float var4, float var5, float var6) {
      this.a.d = _snowman * (float) (Math.PI / 180.0);
      this.a.e = _snowman * (float) (Math.PI / 180.0);
      this.b.d = (float) (Math.PI / 2);
      this.f.d = afm.b(_snowman * 0.6662F) * 1.4F * _snowman;
      this.g.d = afm.b(_snowman * 0.6662F + (float) Math.PI) * 1.4F * _snowman;
      this.h.d = afm.b(_snowman * 0.6662F + (float) Math.PI) * 1.4F * _snowman;
      this.i.d = afm.b(_snowman * 0.6662F) * 1.4F * _snowman;
      boolean _snowman = !_snowman.w_() && _snowman.eM();
      this.j.h = _snowman;
      this.k.h = _snowman;
   }

   @Override
   public void a(dfm var1, dfq var2, int var3, int var4, float var5, float var6, float var7, float var8) {
      if (this.e) {
         float _snowman = 2.0F;
         _snowman.a();
         float _snowmanx = 0.7F;
         _snowman.a(0.71428573F, 0.64935064F, 0.7936508F);
         _snowman.a(0.0, 1.3125, 0.22F);
         this.a.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
         _snowman.b();
         _snowman.a();
         float _snowmanxx = 1.1F;
         _snowman.a(0.625F, 0.45454544F, 0.45454544F);
         _snowman.a(0.0, 2.0625, 0.0);
         this.b.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
         _snowman.b();
         _snowman.a();
         _snowman.a(0.45454544F, 0.41322312F, 0.45454544F);
         _snowman.a(0.0, 2.0625, 0.0);
         ImmutableList.of(this.f, this.g, this.h, this.i, this.j, this.k).forEach(var8x -> var8x.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman));
         _snowman.b();
      } else {
         ImmutableList.of(this.a, this.b, this.f, this.g, this.h, this.i, this.j, this.k).forEach(var8x -> var8x.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman));
      }
   }
}
