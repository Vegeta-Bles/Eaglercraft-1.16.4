import com.google.common.collect.ImmutableList;

public class duh extends dur<bdm> {
   private static final float[] a = new float[]{1.75F, 0.25F, 0.0F, 0.0F, 0.5F, 0.5F, 0.5F, 0.5F, 1.25F, 0.75F, 0.0F, 0.0F};
   private static final float[] b = new float[]{0.0F, 0.0F, 0.0F, 0.0F, 0.25F, 1.75F, 1.25F, 0.75F, 0.0F, 0.0F, 0.0F, 0.0F};
   private static final float[] f = new float[]{0.0F, 0.0F, 0.25F, 1.75F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.75F, 1.25F};
   private static final float[] g = new float[]{0.0F, 0.0F, 8.0F, -8.0F, -8.0F, 8.0F, 8.0F, -8.0F, 0.0F, 0.0F, 8.0F, -8.0F};
   private static final float[] h = new float[]{-8.0F, -8.0F, -8.0F, -8.0F, 0.0F, 0.0F, 0.0F, 0.0F, 8.0F, 8.0F, 8.0F, 8.0F};
   private static final float[] i = new float[]{8.0F, -8.0F, 0.0F, 0.0F, -8.0F, -8.0F, 8.0F, 8.0F, 8.0F, -8.0F, 0.0F, 0.0F};
   private final dwn j;
   private final dwn k;
   private final dwn[] l;
   private final dwn[] m;

   public duh() {
      this.r = 64;
      this.s = 64;
      this.l = new dwn[12];
      this.j = new dwn(this);
      this.j.a(0, 0).a(-6.0F, 10.0F, -8.0F, 12.0F, 12.0F, 16.0F);
      this.j.a(0, 28).a(-8.0F, 10.0F, -6.0F, 2.0F, 12.0F, 12.0F);
      this.j.a(0, 28).a(6.0F, 10.0F, -6.0F, 2.0F, 12.0F, 12.0F, true);
      this.j.a(16, 40).a(-6.0F, 8.0F, -6.0F, 12.0F, 2.0F, 12.0F);
      this.j.a(16, 40).a(-6.0F, 22.0F, -6.0F, 12.0F, 2.0F, 12.0F);

      for (int _snowman = 0; _snowman < this.l.length; _snowman++) {
         this.l[_snowman] = new dwn(this, 0, 0);
         this.l[_snowman].a(-1.0F, -4.5F, -1.0F, 2.0F, 9.0F, 2.0F);
         this.j.b(this.l[_snowman]);
      }

      this.k = new dwn(this, 8, 0);
      this.k.a(-1.0F, 15.0F, 0.0F, 2.0F, 2.0F, 1.0F);
      this.j.b(this.k);
      this.m = new dwn[3];
      this.m[0] = new dwn(this, 40, 0);
      this.m[0].a(-2.0F, 14.0F, 7.0F, 4.0F, 4.0F, 8.0F);
      this.m[1] = new dwn(this, 0, 54);
      this.m[1].a(0.0F, 14.0F, 0.0F, 3.0F, 3.0F, 7.0F);
      this.m[2] = new dwn(this);
      this.m[2].a(41, 32).a(0.0F, 14.0F, 0.0F, 2.0F, 2.0F, 6.0F);
      this.m[2].a(25, 19).a(1.0F, 10.5F, 3.0F, 1.0F, 9.0F, 9.0F);
      this.j.b(this.m[0]);
      this.m[0].b(this.m[1]);
      this.m[1].b(this.m[2]);
      this.a(0.0F, 0.0F);
   }

   @Override
   public Iterable<dwn> a() {
      return ImmutableList.of(this.j);
   }

   public void a(bdm var1, float var2, float var3, float var4, float var5, float var6) {
      float _snowman = _snowman - (float)_snowman.K;
      this.j.e = _snowman * (float) (Math.PI / 180.0);
      this.j.d = _snowman * (float) (Math.PI / 180.0);
      float _snowmanx = (1.0F - _snowman.z(_snowman)) * 0.55F;
      this.a(_snowman, _snowmanx);
      this.k.c = -8.25F;
      aqa _snowmanxx = djz.C().aa();
      if (_snowman.eO()) {
         _snowmanxx = _snowman.eP();
      }

      if (_snowmanxx != null) {
         dcn _snowmanxxx = _snowmanxx.j(0.0F);
         dcn _snowmanxxxx = _snowman.j(0.0F);
         double _snowmanxxxxx = _snowmanxxx.c - _snowmanxxxx.c;
         if (_snowmanxxxxx > 0.0) {
            this.k.b = 0.0F;
         } else {
            this.k.b = 1.0F;
         }

         dcn _snowmanxxxxxx = _snowman.f(0.0F);
         _snowmanxxxxxx = new dcn(_snowmanxxxxxx.b, 0.0, _snowmanxxxxxx.d);
         dcn _snowmanxxxxxxx = new dcn(_snowmanxxxx.b - _snowmanxxx.b, 0.0, _snowmanxxxx.d - _snowmanxxx.d).d().b((float) (Math.PI / 2));
         double _snowmanxxxxxxxx = _snowmanxxxxxx.b(_snowmanxxxxxxx);
         this.k.a = afm.c((float)Math.abs(_snowmanxxxxxxxx)) * 2.0F * (float)Math.signum(_snowmanxxxxxxxx);
      }

      this.k.h = true;
      float _snowmanxxx = _snowman.y(_snowman);
      this.m[0].e = afm.a(_snowmanxxx) * (float) Math.PI * 0.05F;
      this.m[1].e = afm.a(_snowmanxxx) * (float) Math.PI * 0.1F;
      this.m[1].a = -1.5F;
      this.m[1].b = 0.5F;
      this.m[1].c = 14.0F;
      this.m[2].e = afm.a(_snowmanxxx) * (float) Math.PI * 0.15F;
      this.m[2].a = 0.5F;
      this.m[2].b = 0.5F;
      this.m[2].c = 6.0F;
   }

   private void a(float var1, float var2) {
      for (int _snowman = 0; _snowman < 12; _snowman++) {
         this.l[_snowman].d = (float) Math.PI * a[_snowman];
         this.l[_snowman].e = (float) Math.PI * b[_snowman];
         this.l[_snowman].f = (float) Math.PI * f[_snowman];
         this.l[_snowman].a = g[_snowman] * (1.0F + afm.b(_snowman * 1.5F + (float)_snowman) * 0.01F - _snowman);
         this.l[_snowman].b = 16.0F + h[_snowman] * (1.0F + afm.b(_snowman * 1.5F + (float)_snowman) * 0.01F - _snowman);
         this.l[_snowman].c = i[_snowman] * (1.0F + afm.b(_snowman * 1.5F + (float)_snowman) * 0.01F - _snowman);
      }
   }
}
