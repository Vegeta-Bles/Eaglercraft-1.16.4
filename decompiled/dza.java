import javax.annotation.Nullable;

public class dza extends dzb {
   private final ceh a;
   private fx b;
   private final float D;
   private final float E;

   public dza(dwt var1, double var2, double var4, double var6, double var8, double var10, double var12, ceh var14) {
      super(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      this.a = _snowman;
      this.a(djz.C().ab().a().a(_snowman));
      this.u = 1.0F;
      this.v = 0.6F;
      this.w = 0.6F;
      this.x = 0.6F;
      this.B /= 2.0F;
      this.D = this.r.nextFloat() * 3.0F;
      this.E = this.r.nextFloat() * 3.0F;
   }

   @Override
   public dyk b() {
      return dyk.a;
   }

   public dza a(fx var1) {
      this.b = _snowman;
      if (this.a.a(bup.i)) {
         return this;
      } else {
         this.b(_snowman);
         return this;
      }
   }

   public dza g() {
      this.b = new fx(this.g, this.h, this.i);
      if (this.a.a(bup.i)) {
         return this;
      } else {
         this.b(this.b);
         return this;
      }
   }

   protected void b(@Nullable fx var1) {
      int _snowman = djz.C().al().a(this.a, this.c, _snowman, 0);
      this.v *= (float)(_snowman >> 16 & 0xFF) / 255.0F;
      this.w *= (float)(_snowman >> 8 & 0xFF) / 255.0F;
      this.x *= (float)(_snowman & 0xFF) / 255.0F;
   }

   @Override
   protected float c() {
      return this.C.a((double)((this.D + 1.0F) / 4.0F * 16.0F));
   }

   @Override
   protected float d() {
      return this.C.a((double)(this.D / 4.0F * 16.0F));
   }

   @Override
   protected float e() {
      return this.C.b((double)(this.E / 4.0F * 16.0F));
   }

   @Override
   protected float f() {
      return this.C.b((double)((this.E + 1.0F) / 4.0F * 16.0F));
   }

   @Override
   public int a(float var1) {
      int _snowman = super.a(_snowman);
      int _snowmanx = 0;
      if (this.c.C(this.b)) {
         _snowmanx = eae.a(this.c, this.b);
      }

      return _snowman == 0 ? _snowmanx : _snowman;
   }

   public static class a implements dyj<hc> {
      public a() {
      }

      public dyg a(hc var1, dwt var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         ceh _snowman = _snowman.c();
         return !_snowman.g() && !_snowman.a(bup.bo) ? new dza(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman).g() : null;
      }
   }
}
