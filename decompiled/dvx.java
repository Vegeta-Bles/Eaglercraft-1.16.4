import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import java.util.Arrays;

public class dvx<T extends aqa> extends dur<T> {
   private final dwn a;
   private final dwn[] b = new dwn[8];
   private final ImmutableList<dwn> f;

   public dvx() {
      int _snowman = -16;
      this.a = new dwn(this, 0, 0);
      this.a.a(-6.0F, -8.0F, -6.0F, 12.0F, 16.0F, 12.0F);
      this.a.b += 8.0F;

      for (int _snowmanx = 0; _snowmanx < this.b.length; _snowmanx++) {
         this.b[_snowmanx] = new dwn(this, 48, 0);
         double _snowmanxx = (double)_snowmanx * Math.PI * 2.0 / (double)this.b.length;
         float _snowmanxxx = (float)Math.cos(_snowmanxx) * 5.0F;
         float _snowmanxxxx = (float)Math.sin(_snowmanxx) * 5.0F;
         this.b[_snowmanx].a(-1.0F, 0.0F, -1.0F, 2.0F, 18.0F, 2.0F);
         this.b[_snowmanx].a = _snowmanxxx;
         this.b[_snowmanx].c = _snowmanxxxx;
         this.b[_snowmanx].b = 15.0F;
         _snowmanxx = (double)_snowmanx * Math.PI * -2.0 / (double)this.b.length + (Math.PI / 2);
         this.b[_snowmanx].e = (float)_snowmanxx;
      }

      Builder<dwn> _snowmanx = ImmutableList.builder();
      _snowmanx.add(this.a);
      _snowmanx.addAll(Arrays.asList(this.b));
      this.f = _snowmanx.build();
   }

   @Override
   public void a(T var1, float var2, float var3, float var4, float var5, float var6) {
      for (dwn _snowman : this.b) {
         _snowman.d = _snowman;
      }
   }

   @Override
   public Iterable<dwn> a() {
      return this.f;
   }
}
