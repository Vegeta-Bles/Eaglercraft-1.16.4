import com.google.common.collect.ImmutableList;

public class dvk extends dur<bdv> {
   private final dwn a;
   private final dwn b;
   private final dwn f;
   private final dwn g;
   private final dwn h;
   private final dwn i;
   private final dwn j;
   private final dwn k;

   public dvk() {
      this.r = 128;
      this.s = 128;
      int _snowman = 16;
      float _snowmanx = 0.0F;
      this.k = new dwn(this);
      this.k.a(0.0F, -7.0F, -1.5F);
      this.k.a(68, 73).a(-5.0F, -1.0F, -18.0F, 10.0F, 10.0F, 18.0F, 0.0F);
      this.a = new dwn(this);
      this.a.a(0.0F, 16.0F, -17.0F);
      this.a.a(0, 0).a(-8.0F, -20.0F, -14.0F, 16.0F, 20.0F, 16.0F, 0.0F);
      this.a.a(0, 0).a(-2.0F, -6.0F, -18.0F, 4.0F, 8.0F, 4.0F, 0.0F);
      dwn _snowmanxx = new dwn(this);
      _snowmanxx.a(-10.0F, -14.0F, -8.0F);
      _snowmanxx.a(74, 55).a(0.0F, -14.0F, -2.0F, 2.0F, 14.0F, 4.0F, 0.0F);
      _snowmanxx.d = 1.0995574F;
      this.a.b(_snowmanxx);
      dwn _snowmanxxx = new dwn(this);
      _snowmanxxx.g = true;
      _snowmanxxx.a(8.0F, -14.0F, -8.0F);
      _snowmanxxx.a(74, 55).a(0.0F, -14.0F, -2.0F, 2.0F, 14.0F, 4.0F, 0.0F);
      _snowmanxxx.d = 1.0995574F;
      this.a.b(_snowmanxxx);
      this.b = new dwn(this);
      this.b.a(0.0F, -2.0F, 2.0F);
      this.b.a(0, 36).a(-8.0F, 0.0F, -16.0F, 16.0F, 3.0F, 16.0F, 0.0F);
      this.a.b(this.b);
      this.k.b(this.a);
      this.f = new dwn(this);
      this.f.a(0, 55).a(-7.0F, -10.0F, -7.0F, 14.0F, 16.0F, 20.0F, 0.0F);
      this.f.a(0, 91).a(-6.0F, 6.0F, -7.0F, 12.0F, 13.0F, 18.0F, 0.0F);
      this.f.a(0.0F, 1.0F, 2.0F);
      this.g = new dwn(this, 96, 0);
      this.g.a(-4.0F, 0.0F, -4.0F, 8.0F, 37.0F, 8.0F, 0.0F);
      this.g.a(-8.0F, -13.0F, 18.0F);
      this.h = new dwn(this, 96, 0);
      this.h.g = true;
      this.h.a(-4.0F, 0.0F, -4.0F, 8.0F, 37.0F, 8.0F, 0.0F);
      this.h.a(8.0F, -13.0F, 18.0F);
      this.i = new dwn(this, 64, 0);
      this.i.a(-4.0F, 0.0F, -4.0F, 8.0F, 37.0F, 8.0F, 0.0F);
      this.i.a(-8.0F, -13.0F, -5.0F);
      this.j = new dwn(this, 64, 0);
      this.j.g = true;
      this.j.a(-4.0F, 0.0F, -4.0F, 8.0F, 37.0F, 8.0F, 0.0F);
      this.j.a(8.0F, -13.0F, -5.0F);
   }

   @Override
   public Iterable<dwn> a() {
      return ImmutableList.of(this.k, this.f, this.g, this.h, this.i, this.j);
   }

   public void a(bdv var1, float var2, float var3, float var4, float var5, float var6) {
      this.a.d = _snowman * (float) (Math.PI / 180.0);
      this.a.e = _snowman * (float) (Math.PI / 180.0);
      this.f.d = (float) (Math.PI / 2);
      float _snowman = 0.4F * _snowman;
      this.g.d = afm.b(_snowman * 0.6662F) * _snowman;
      this.h.d = afm.b(_snowman * 0.6662F + (float) Math.PI) * _snowman;
      this.i.d = afm.b(_snowman * 0.6662F + (float) Math.PI) * _snowman;
      this.j.d = afm.b(_snowman * 0.6662F) * _snowman;
   }

   public void a(bdv var1, float var2, float var3, float var4) {
      super.a(_snowman, _snowman, _snowman, _snowman);
      int _snowman = _snowman.eM();
      int _snowmanx = _snowman.eW();
      int _snowmanxx = 20;
      int _snowmanxxx = _snowman.eK();
      int _snowmanxxxx = 10;
      if (_snowmanxxx > 0) {
         float _snowmanxxxxx = afm.e((float)_snowmanxxx - _snowman, 10.0F);
         float _snowmanxxxxxx = (1.0F + _snowmanxxxxx) * 0.5F;
         float _snowmanxxxxxxx = _snowmanxxxxxx * _snowmanxxxxxx * _snowmanxxxxxx * 12.0F;
         float _snowmanxxxxxxxx = _snowmanxxxxxxx * afm.a(this.k.d);
         this.k.c = -6.5F + _snowmanxxxxxxx;
         this.k.b = -7.0F - _snowmanxxxxxxxx;
         float _snowmanxxxxxxxxx = afm.a(((float)_snowmanxxx - _snowman) / 10.0F * (float) Math.PI * 0.25F);
         this.b.d = (float) (Math.PI / 2) * _snowmanxxxxxxxxx;
         if (_snowmanxxx > 5) {
            this.b.d = afm.a(((float)(-4 + _snowmanxxx) - _snowman) / 4.0F) * (float) Math.PI * 0.4F;
         } else {
            this.b.d = (float) (Math.PI / 20) * afm.a((float) Math.PI * ((float)_snowmanxxx - _snowman) / 10.0F);
         }
      } else {
         float _snowmanxxxxx = -1.0F;
         float _snowmanxxxxxx = -1.0F * afm.a(this.k.d);
         this.k.a = 0.0F;
         this.k.b = -7.0F - _snowmanxxxxxx;
         this.k.c = 5.5F;
         boolean _snowmanxxxxxxx = _snowman > 0;
         this.k.d = _snowmanxxxxxxx ? 0.21991149F : 0.0F;
         this.b.d = (float) Math.PI * (_snowmanxxxxxxx ? 0.05F : 0.01F);
         if (_snowmanxxxxxxx) {
            double _snowmanxxxxxxxx = (double)_snowman / 40.0;
            this.k.a = (float)Math.sin(_snowmanxxxxxxxx * 10.0) * 3.0F;
         } else if (_snowmanx > 0) {
            float _snowmanxxxxxxxx = afm.a(((float)(20 - _snowmanx) - _snowman) / 20.0F * (float) Math.PI * 0.25F);
            this.b.d = (float) (Math.PI / 2) * _snowmanxxxxxxxx;
         }
      }
   }
}
