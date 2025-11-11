import java.util.Arrays;

public class dub<T extends aqa> extends dur<T> {
   private static final int[][] a = new int[][]{{4, 3, 2}, {6, 4, 5}, {3, 3, 1}, {1, 2, 1}};
   private static final int[][] b = new int[][]{{0, 0}, {0, 5}, {0, 14}, {0, 18}};
   private static final int f = a.length;
   private final dwn[] g = new dwn[f];

   public dub() {
      float _snowman = -3.5F;

      for (int _snowmanx = 0; _snowmanx < this.g.length; _snowmanx++) {
         this.g[_snowmanx] = new dwn(this, b[_snowmanx][0], b[_snowmanx][1]);
         this.g[_snowmanx].a((float)a[_snowmanx][0] * -0.5F, 0.0F, (float)a[_snowmanx][2] * -0.5F, (float)a[_snowmanx][0], (float)a[_snowmanx][1], (float)a[_snowmanx][2]);
         this.g[_snowmanx].a(0.0F, (float)(24 - a[_snowmanx][1]), _snowman);
         if (_snowmanx < this.g.length - 1) {
            _snowman += (float)(a[_snowmanx][2] + a[_snowmanx + 1][2]) * 0.5F;
         }
      }
   }

   @Override
   public Iterable<dwn> a() {
      return Arrays.asList(this.g);
   }

   @Override
   public void a(T var1, float var2, float var3, float var4, float var5, float var6) {
      for (int _snowman = 0; _snowman < this.g.length; _snowman++) {
         this.g[_snowman].e = afm.b(_snowman * 0.9F + (float)_snowman * 0.15F * (float) Math.PI) * (float) Math.PI * 0.01F * (float)(1 + Math.abs(_snowman - 2));
         this.g[_snowman].a = afm.a(_snowman * 0.9F + (float)_snowman * 0.15F * (float) Math.PI) * (float) Math.PI * 0.1F * (float)Math.abs(_snowman - 2);
      }
   }
}
