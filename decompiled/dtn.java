import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import java.util.Arrays;

public class dtn extends dur<bhn> {
   private final dwn[] a = new dwn[2];
   private final dwn b;
   private final ImmutableList<dwn> f;

   public dtn() {
      dwn[] _snowman = new dwn[]{
         new dwn(this, 0, 0).b(128, 64),
         new dwn(this, 0, 19).b(128, 64),
         new dwn(this, 0, 27).b(128, 64),
         new dwn(this, 0, 35).b(128, 64),
         new dwn(this, 0, 43).b(128, 64)
      };
      int _snowmanx = 32;
      int _snowmanxx = 6;
      int _snowmanxxx = 20;
      int _snowmanxxxx = 4;
      int _snowmanxxxxx = 28;
      _snowman[0].a(-14.0F, -9.0F, -3.0F, 28.0F, 16.0F, 3.0F, 0.0F);
      _snowman[0].a(0.0F, 3.0F, 1.0F);
      _snowman[1].a(-13.0F, -7.0F, -1.0F, 18.0F, 6.0F, 2.0F, 0.0F);
      _snowman[1].a(-15.0F, 4.0F, 4.0F);
      _snowman[2].a(-8.0F, -7.0F, -1.0F, 16.0F, 6.0F, 2.0F, 0.0F);
      _snowman[2].a(15.0F, 4.0F, 0.0F);
      _snowman[3].a(-14.0F, -7.0F, -1.0F, 28.0F, 6.0F, 2.0F, 0.0F);
      _snowman[3].a(0.0F, 4.0F, -9.0F);
      _snowman[4].a(-14.0F, -7.0F, -1.0F, 28.0F, 6.0F, 2.0F, 0.0F);
      _snowman[4].a(0.0F, 4.0F, 9.0F);
      _snowman[0].d = (float) (Math.PI / 2);
      _snowman[1].e = (float) (Math.PI * 3.0 / 2.0);
      _snowman[2].e = (float) (Math.PI / 2);
      _snowman[3].e = (float) Math.PI;
      this.a[0] = this.a(true);
      this.a[0].a(3.0F, -5.0F, 9.0F);
      this.a[1] = this.a(false);
      this.a[1].a(3.0F, -5.0F, -9.0F);
      this.a[1].e = (float) Math.PI;
      this.a[0].f = (float) (Math.PI / 16);
      this.a[1].f = (float) (Math.PI / 16);
      this.b = new dwn(this, 0, 0).b(128, 64);
      this.b.a(-14.0F, -9.0F, -3.0F, 28.0F, 16.0F, 3.0F, 0.0F);
      this.b.a(0.0F, -3.0F, 1.0F);
      this.b.d = (float) (Math.PI / 2);
      Builder<dwn> _snowmanxxxxxx = ImmutableList.builder();
      _snowmanxxxxxx.addAll(Arrays.asList(_snowman));
      _snowmanxxxxxx.addAll(Arrays.asList(this.a));
      this.f = _snowmanxxxxxx.build();
   }

   public void a(bhn var1, float var2, float var3, float var4, float var5, float var6) {
      this.a(_snowman, 0, _snowman);
      this.a(_snowman, 1, _snowman);
   }

   public ImmutableList<dwn> b() {
      return this.f;
   }

   public dwn c() {
      return this.b;
   }

   protected dwn a(boolean var1) {
      dwn _snowman = new dwn(this, 62, _snowman ? 0 : 20).b(128, 64);
      int _snowmanx = 20;
      int _snowmanxx = 7;
      int _snowmanxxx = 6;
      float _snowmanxxxx = -5.0F;
      _snowman.a(-1.0F, 0.0F, -5.0F, 2.0F, 2.0F, 18.0F);
      _snowman.a(_snowman ? -1.001F : 0.001F, -3.0F, 8.0F, 1.0F, 6.0F, 7.0F);
      return _snowman;
   }

   protected void a(bhn var1, int var2, float var3) {
      float _snowman = _snowman.a(_snowman, _snowman);
      dwn _snowmanx = this.a[_snowman];
      _snowmanx.d = (float)afm.b((float) (-Math.PI / 3), (float) (-Math.PI / 12), (double)((afm.a(-_snowman) + 1.0F) / 2.0F));
      _snowmanx.e = (float)afm.b((float) (-Math.PI / 4), (float) (Math.PI / 4), (double)((afm.a(-_snowman + 1.0F) + 1.0F) / 2.0F));
      if (_snowman == 1) {
         _snowmanx.e = (float) Math.PI - _snowmanx.e;
      }
   }
}
