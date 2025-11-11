import java.util.Random;

public class dzh extends dxh {
   protected dzh(dwt var1, double var2, double var4, double var6, double var8, double var10, double var12, float var14, dyw var15) {
      super(_snowman, _snowman, _snowman, _snowman, 0.1F, -0.1F, 0.1F, _snowman, _snowman, _snowman, _snowman, _snowman, 0.0F, 20, -5.0E-4, false);
      this.v = 0.7294118F;
      this.w = 0.69411767F;
      this.x = 0.7607843F;
   }

   public static class a implements dyj<hi> {
      private final dyw a;

      public a(dyw var1) {
         this.a = _snowman;
      }

      public dyg a(hi var1, dwt var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         Random _snowman = _snowman.t;
         double _snowmanx = (double)_snowman.nextFloat() * -1.9 * (double)_snowman.nextFloat() * 0.1;
         double _snowmanxx = (double)_snowman.nextFloat() * -0.5 * (double)_snowman.nextFloat() * 0.1 * 5.0;
         double _snowmanxxx = (double)_snowman.nextFloat() * -1.9 * (double)_snowman.nextFloat() * 0.1;
         return new dzh(_snowman, _snowman, _snowman, _snowman, _snowmanx, _snowmanxx, _snowmanxxx, 1.0F, this.a);
      }
   }
}
