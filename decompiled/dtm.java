import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import java.util.Arrays;

public class dtm<T extends aqa> extends dur<T> {
   private final dwn[] a;
   private final dwn b = new dwn(this, 0, 0);
   private final ImmutableList<dwn> f;

   public dtm() {
      this.b.a(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F);
      this.a = new dwn[12];

      for (int _snowman = 0; _snowman < this.a.length; _snowman++) {
         this.a[_snowman] = new dwn(this, 0, 16);
         this.a[_snowman].a(0.0F, 0.0F, 0.0F, 2.0F, 8.0F, 2.0F);
      }

      Builder<dwn> _snowman = ImmutableList.builder();
      _snowman.add(this.b);
      _snowman.addAll(Arrays.asList(this.a));
      this.f = _snowman.build();
   }

   @Override
   public Iterable<dwn> a() {
      return this.f;
   }

   @Override
   public void a(T var1, float var2, float var3, float var4, float var5, float var6) {
      float _snowman = _snowman * (float) Math.PI * -0.1F;

      for (int _snowmanx = 0; _snowmanx < 4; _snowmanx++) {
         this.a[_snowmanx].b = -2.0F + afm.b(((float)(_snowmanx * 2) + _snowman) * 0.25F);
         this.a[_snowmanx].a = afm.b(_snowman) * 9.0F;
         this.a[_snowmanx].c = afm.a(_snowman) * 9.0F;
         _snowman++;
      }

      _snowman = (float) (Math.PI / 4) + _snowman * (float) Math.PI * 0.03F;

      for (int _snowmanx = 4; _snowmanx < 8; _snowmanx++) {
         this.a[_snowmanx].b = 2.0F + afm.b(((float)(_snowmanx * 2) + _snowman) * 0.25F);
         this.a[_snowmanx].a = afm.b(_snowman) * 7.0F;
         this.a[_snowmanx].c = afm.a(_snowman) * 7.0F;
         _snowman++;
      }

      _snowman = 0.47123894F + _snowman * (float) Math.PI * -0.05F;

      for (int _snowmanx = 8; _snowmanx < 12; _snowmanx++) {
         this.a[_snowmanx].b = 11.0F + afm.b(((float)_snowmanx * 1.5F + _snowman) * 0.5F);
         this.a[_snowmanx].a = afm.b(_snowman) * 5.0F;
         this.a[_snowmanx].c = afm.a(_snowman) * 5.0F;
         _snowman++;
      }

      this.b.e = _snowman * (float) (Math.PI / 180.0);
      this.b.d = _snowman * (float) (Math.PI / 180.0);
   }
}
