import java.util.Random;

public class dyy extends dzb {
   private dyy(dwt var1, double var2, double var4, double var6) {
      super(_snowman, _snowman, _snowman - 0.125, _snowman);
      this.v = 0.4F;
      this.w = 0.4F;
      this.x = 0.7F;
      this.a(0.01F, 0.01F);
      this.B = this.B * (this.r.nextFloat() * 0.6F + 0.2F);
      this.t = (int)(16.0 / (Math.random() * 0.8 + 0.2));
      this.n = false;
   }

   private dyy(dwt var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      super(_snowman, _snowman, _snowman - 0.125, _snowman, _snowman, _snowman, _snowman);
      this.a(0.01F, 0.01F);
      this.B = this.B * (this.r.nextFloat() * 0.6F + 0.6F);
      this.t = (int)(16.0 / (Math.random() * 0.8 + 0.2));
      this.n = false;
   }

   @Override
   public dyk b() {
      return dyk.b;
   }

   @Override
   public void a() {
      this.d = this.g;
      this.e = this.h;
      this.f = this.i;
      if (this.t-- <= 0) {
         this.j();
      } else {
         this.a(this.j, this.k, this.l);
      }
   }

   public static class a implements dyj<hi> {
      private final dyw a;

      public a(dyw var1) {
         this.a = _snowman;
      }

      public dyg a(hi var1, dwt var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         Random _snowman = _snowman.t;
         double _snowmanx = _snowman.nextGaussian() * 1.0E-6F;
         double _snowmanxx = _snowman.nextGaussian() * 1.0E-4F;
         double _snowmanxxx = _snowman.nextGaussian() * 1.0E-6F;
         dyy _snowmanxxxx = new dyy(_snowman, _snowman, _snowman, _snowman, _snowmanx, _snowmanxx, _snowmanxxx);
         _snowmanxxxx.a(this.a);
         _snowmanxxxx.a(0.9F, 0.4F, 0.5F);
         return _snowmanxxxx;
      }
   }

   public static class b implements dyj<hi> {
      private final dyw a;

      public b(dyw var1) {
         this.a = _snowman;
      }

      public dyg a(hi var1, dwt var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         dyy _snowman = new dyy(_snowman, _snowman, _snowman, _snowman);
         _snowman.a(this.a);
         return _snowman;
      }
   }

   public static class c implements dyj<hi> {
      private final dyw a;

      public c(dyw var1) {
         this.a = _snowman;
      }

      public dyg a(hi var1, dwt var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         double _snowman = (double)_snowman.t.nextFloat() * -1.9 * (double)_snowman.t.nextFloat() * 0.1;
         dyy _snowmanx = new dyy(_snowman, _snowman, _snowman, _snowman, 0.0, _snowman, 0.0);
         _snowmanx.a(this.a);
         _snowmanx.a(0.1F, 0.1F, 0.3F);
         _snowmanx.a(0.001F, 0.001F);
         return _snowmanx;
      }
   }
}
