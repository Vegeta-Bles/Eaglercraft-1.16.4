import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import java.util.Arrays;

public class dvr<T extends aqa> extends dur<T> {
   private final dwn[] a;
   private final dwn[] b;
   private final ImmutableList<dwn> f;
   private final float[] g = new float[7];
   private static final int[][] h = new int[][]{{3, 2, 2}, {4, 3, 2}, {6, 4, 3}, {3, 3, 3}, {2, 2, 3}, {2, 1, 2}, {1, 1, 2}};
   private static final int[][] i = new int[][]{{0, 0}, {0, 4}, {0, 9}, {0, 16}, {0, 22}, {11, 0}, {13, 4}};

   public dvr() {
      this.a = new dwn[7];
      float _snowman = -3.5F;

      for (int _snowmanx = 0; _snowmanx < this.a.length; _snowmanx++) {
         this.a[_snowmanx] = new dwn(this, i[_snowmanx][0], i[_snowmanx][1]);
         this.a[_snowmanx].a((float)h[_snowmanx][0] * -0.5F, 0.0F, (float)h[_snowmanx][2] * -0.5F, (float)h[_snowmanx][0], (float)h[_snowmanx][1], (float)h[_snowmanx][2]);
         this.a[_snowmanx].a(0.0F, (float)(24 - h[_snowmanx][1]), _snowman);
         this.g[_snowmanx] = _snowman;
         if (_snowmanx < this.a.length - 1) {
            _snowman += (float)(h[_snowmanx][2] + h[_snowmanx + 1][2]) * 0.5F;
         }
      }

      this.b = new dwn[3];
      this.b[0] = new dwn(this, 20, 0);
      this.b[0].a(-5.0F, 0.0F, (float)h[2][2] * -0.5F, 10.0F, 8.0F, (float)h[2][2]);
      this.b[0].a(0.0F, 16.0F, this.g[2]);
      this.b[1] = new dwn(this, 20, 11);
      this.b[1].a(-3.0F, 0.0F, (float)h[4][2] * -0.5F, 6.0F, 4.0F, (float)h[4][2]);
      this.b[1].a(0.0F, 20.0F, this.g[4]);
      this.b[2] = new dwn(this, 20, 18);
      this.b[2].a(-3.0F, 0.0F, (float)h[4][2] * -0.5F, 6.0F, 5.0F, (float)h[1][2]);
      this.b[2].a(0.0F, 19.0F, this.g[1]);
      Builder<dwn> _snowmanxx = ImmutableList.builder();
      _snowmanxx.addAll(Arrays.asList(this.a));
      _snowmanxx.addAll(Arrays.asList(this.b));
      this.f = _snowmanxx.build();
   }

   public ImmutableList<dwn> b() {
      return this.f;
   }

   @Override
   public void a(T var1, float var2, float var3, float var4, float var5, float var6) {
      for (int _snowman = 0; _snowman < this.a.length; _snowman++) {
         this.a[_snowman].e = afm.b(_snowman * 0.9F + (float)_snowman * 0.15F * (float) Math.PI) * (float) Math.PI * 0.05F * (float)(1 + Math.abs(_snowman - 2));
         this.a[_snowman].a = afm.a(_snowman * 0.9F + (float)_snowman * 0.15F * (float) Math.PI) * (float) Math.PI * 0.2F * (float)Math.abs(_snowman - 2);
      }

      this.b[0].e = this.a[2].e;
      this.b[1].e = this.a[4].e;
      this.b[1].a = this.a[4].a;
      this.b[2].e = this.a[1].e;
      this.b[2].a = this.a[1].a;
   }
}
