import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import java.util.Random;

public class duf<T extends aqa> extends dur<T> {
   private final dwn[] a = new dwn[9];
   private final ImmutableList<dwn> b;

   public duf() {
      Builder<dwn> _snowman = ImmutableList.builder();
      dwn _snowmanx = new dwn(this, 0, 0);
      _snowmanx.a(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F);
      _snowmanx.b = 17.6F;
      _snowman.add(_snowmanx);
      Random _snowmanxx = new Random(1660L);

      for (int _snowmanxxx = 0; _snowmanxxx < this.a.length; _snowmanxxx++) {
         this.a[_snowmanxxx] = new dwn(this, 0, 0);
         float _snowmanxxxx = (((float)(_snowmanxxx % 3) - (float)(_snowmanxxx / 3 % 2) * 0.5F + 0.25F) / 2.0F * 2.0F - 1.0F) * 5.0F;
         float _snowmanxxxxx = ((float)(_snowmanxxx / 3) / 2.0F * 2.0F - 1.0F) * 5.0F;
         int _snowmanxxxxxx = _snowmanxx.nextInt(7) + 8;
         this.a[_snowmanxxx].a(-1.0F, 0.0F, -1.0F, 2.0F, (float)_snowmanxxxxxx, 2.0F);
         this.a[_snowmanxxx].a = _snowmanxxxx;
         this.a[_snowmanxxx].c = _snowmanxxxxx;
         this.a[_snowmanxxx].b = 24.6F;
         _snowman.add(this.a[_snowmanxxx]);
      }

      this.b = _snowman.build();
   }

   @Override
   public void a(T var1, float var2, float var3, float var4, float var5, float var6) {
      for (int _snowman = 0; _snowman < this.a.length; _snowman++) {
         this.a[_snowman].d = 0.2F * afm.a(_snowman * 0.3F + (float)_snowman) + 0.4F;
      }
   }

   @Override
   public Iterable<dwn> a() {
      return this.b;
   }
}
