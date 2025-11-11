public class dxi extends dzb {
   private final float a;
   private final float b;

   private dxi(dwt var1, double var2, double var4, double var6, double var8, double var10, double var12, bmb var14) {
      this(_snowman, _snowman, _snowman, _snowman, _snowman);
      this.j *= 0.1F;
      this.k *= 0.1F;
      this.l *= 0.1F;
      this.j += _snowman;
      this.k += _snowman;
      this.l += _snowman;
   }

   @Override
   public dyk b() {
      return dyk.a;
   }

   protected dxi(dwt var1, double var2, double var4, double var6, bmb var8) {
      super(_snowman, _snowman, _snowman, _snowman, 0.0, 0.0, 0.0);
      this.a(djz.C().ad().a(_snowman, _snowman, null).e());
      this.u = 1.0F;
      this.B /= 2.0F;
      this.a = this.r.nextFloat() * 3.0F;
      this.b = this.r.nextFloat() * 3.0F;
   }

   @Override
   protected float c() {
      return this.C.a((double)((this.a + 1.0F) / 4.0F * 16.0F));
   }

   @Override
   protected float d() {
      return this.C.a((double)(this.a / 4.0F * 16.0F));
   }

   @Override
   protected float e() {
      return this.C.b((double)(this.b / 4.0F * 16.0F));
   }

   @Override
   protected float f() {
      return this.C.b((double)((this.b + 1.0F) / 4.0F * 16.0F));
   }

   public static class a implements dyj<he> {
      public a() {
      }

      public dyg a(he var1, dwt var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         return new dxi(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman.c());
      }
   }

   public static class b implements dyj<hi> {
      public b() {
      }

      public dyg a(hi var1, dwt var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         return new dxi(_snowman, _snowman, _snowman, _snowman, new bmb(bmd.md));
      }
   }

   public static class c implements dyj<hi> {
      public c() {
      }

      public dyg a(hi var1, dwt var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         return new dxi(_snowman, _snowman, _snowman, _snowman, new bmb(bmd.lQ));
      }
   }
}
