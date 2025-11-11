import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

public class dwc<T extends bax> extends dvi<T> {
   private final dwn j;

   public dwc(float var1) {
      super(12, _snowman, true, 120.0F, 0.0F, 9.0F, 6.0F, 120);
      this.r = 128;
      this.s = 64;
      this.a = new dwn(this, 3, 0);
      this.a.a(-3.0F, -1.0F, -3.0F, 6.0F, 5.0F, 6.0F, 0.0F);
      this.a.a(0.0F, 19.0F, -10.0F);
      this.b = new dwn(this);
      this.b.a(7, 37).a(-9.5F, 3.0F, -10.0F, 19.0F, 20.0F, 6.0F, 0.0F);
      this.b.a(31, 1).a(-5.5F, 3.0F, -13.0F, 11.0F, 18.0F, 3.0F, 0.0F);
      this.b.a(0.0F, 11.0F, -10.0F);
      this.j = new dwn(this);
      this.j.a(70, 33).a(-4.5F, 3.0F, -14.0F, 9.0F, 18.0F, 1.0F, 0.0F);
      this.j.a(0.0F, 11.0F, -10.0F);
      int _snowman = 1;
      this.f = new dwn(this, 1, 23);
      this.f.a(-2.0F, 0.0F, 0.0F, 4.0F, 1.0F, 10.0F, 0.0F);
      this.f.a(-3.5F, 22.0F, 11.0F);
      this.g = new dwn(this, 1, 12);
      this.g.a(-2.0F, 0.0F, 0.0F, 4.0F, 1.0F, 10.0F, 0.0F);
      this.g.a(3.5F, 22.0F, 11.0F);
      this.h = new dwn(this, 27, 30);
      this.h.a(-13.0F, 0.0F, -2.0F, 13.0F, 1.0F, 5.0F, 0.0F);
      this.h.a(-5.0F, 21.0F, -4.0F);
      this.i = new dwn(this, 27, 24);
      this.i.a(0.0F, 0.0F, -2.0F, 13.0F, 1.0F, 5.0F, 0.0F);
      this.i.a(5.0F, 21.0F, -4.0F);
   }

   @Override
   protected Iterable<dwn> b() {
      return Iterables.concat(super.b(), ImmutableList.of(this.j));
   }

   public void a(T var1, float var2, float var3, float var4, float var5, float var6) {
      super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      this.f.d = afm.b(_snowman * 0.6662F * 0.6F) * 0.5F * _snowman;
      this.g.d = afm.b(_snowman * 0.6662F * 0.6F + (float) Math.PI) * 0.5F * _snowman;
      this.h.f = afm.b(_snowman * 0.6662F * 0.6F + (float) Math.PI) * 0.5F * _snowman;
      this.i.f = afm.b(_snowman * 0.6662F * 0.6F) * 0.5F * _snowman;
      this.h.d = 0.0F;
      this.i.d = 0.0F;
      this.h.e = 0.0F;
      this.i.e = 0.0F;
      this.f.e = 0.0F;
      this.g.e = 0.0F;
      this.j.d = (float) (Math.PI / 2);
      if (!_snowman.aE() && _snowman.ao()) {
         float _snowman = _snowman.eL() ? 4.0F : 1.0F;
         float _snowmanx = _snowman.eL() ? 2.0F : 1.0F;
         float _snowmanxx = 5.0F;
         this.h.e = afm.b(_snowman * _snowman * 5.0F + (float) Math.PI) * 8.0F * _snowman * _snowmanx;
         this.h.f = 0.0F;
         this.i.e = afm.b(_snowman * _snowman * 5.0F) * 8.0F * _snowman * _snowmanx;
         this.i.f = 0.0F;
         this.f.e = afm.b(_snowman * 5.0F + (float) Math.PI) * 3.0F * _snowman;
         this.f.d = 0.0F;
         this.g.e = afm.b(_snowman * 5.0F) * 3.0F * _snowman;
         this.g.d = 0.0F;
      }

      this.j.h = !this.e && _snowman.eK();
   }

   @Override
   public void a(dfm var1, dfq var2, int var3, int var4, float var5, float var6, float var7, float var8) {
      boolean _snowman = this.j.h;
      if (_snowman) {
         _snowman.a();
         _snowman.a(0.0, -0.08F, 0.0);
      }

      super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      if (_snowman) {
         _snowman.b();
      }
   }
}
