import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import java.util.Arrays;

public class dwh<T extends bcl> extends dur<T> {
   private final dwn[] a;
   private final dwn[] b;
   private final ImmutableList<dwn> f;

   public dwh(float var1) {
      this.r = 64;
      this.s = 64;
      this.a = new dwn[3];
      this.a[0] = new dwn(this, 0, 16);
      this.a[0].a(-10.0F, 3.9F, -0.5F, 20.0F, 3.0F, 3.0F, _snowman);
      this.a[1] = new dwn(this).b(this.r, this.s);
      this.a[1].a(-2.0F, 6.9F, -0.5F);
      this.a[1].a(0, 22).a(0.0F, 0.0F, 0.0F, 3.0F, 10.0F, 3.0F, _snowman);
      this.a[1].a(24, 22).a(-4.0F, 1.5F, 0.5F, 11.0F, 2.0F, 2.0F, _snowman);
      this.a[1].a(24, 22).a(-4.0F, 4.0F, 0.5F, 11.0F, 2.0F, 2.0F, _snowman);
      this.a[1].a(24, 22).a(-4.0F, 6.5F, 0.5F, 11.0F, 2.0F, 2.0F, _snowman);
      this.a[2] = new dwn(this, 12, 22);
      this.a[2].a(0.0F, 0.0F, 0.0F, 3.0F, 6.0F, 3.0F, _snowman);
      this.b = new dwn[3];
      this.b[0] = new dwn(this, 0, 0);
      this.b[0].a(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, _snowman);
      this.b[1] = new dwn(this, 32, 0);
      this.b[1].a(-4.0F, -4.0F, -4.0F, 6.0F, 6.0F, 6.0F, _snowman);
      this.b[1].a = -8.0F;
      this.b[1].b = 4.0F;
      this.b[2] = new dwn(this, 32, 0);
      this.b[2].a(-4.0F, -4.0F, -4.0F, 6.0F, 6.0F, 6.0F, _snowman);
      this.b[2].a = 10.0F;
      this.b[2].b = 4.0F;
      Builder<dwn> _snowman = ImmutableList.builder();
      _snowman.addAll(Arrays.asList(this.b));
      _snowman.addAll(Arrays.asList(this.a));
      this.f = _snowman.build();
   }

   public ImmutableList<dwn> b() {
      return this.f;
   }

   public void a(T var1, float var2, float var3, float var4, float var5, float var6) {
      float _snowman = afm.b(_snowman * 0.1F);
      this.a[1].d = (0.065F + 0.05F * _snowman) * (float) Math.PI;
      this.a[2].a(-2.0F, 6.9F + afm.b(this.a[1].d) * 10.0F, -0.5F + afm.a(this.a[1].d) * 10.0F);
      this.a[2].d = (0.265F + 0.1F * _snowman) * (float) Math.PI;
      this.b[0].e = _snowman * (float) (Math.PI / 180.0);
      this.b[0].d = _snowman * (float) (Math.PI / 180.0);
   }

   public void a(T var1, float var2, float var3, float var4) {
      for (int _snowman = 1; _snowman < 3; _snowman++) {
         this.b[_snowman].e = (_snowman.a(_snowman - 1) - _snowman.aA) * (float) (Math.PI / 180.0);
         this.b[_snowman].d = _snowman.b(_snowman - 1) * (float) (Math.PI / 180.0);
      }
   }
}
