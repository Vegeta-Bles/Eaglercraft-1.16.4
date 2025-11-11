import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import java.util.Arrays;

public class dup<T extends bdz> extends dur<T> {
   private final dwn[] a = new dwn[8];
   private final dwn b;
   private final ImmutableList<dwn> f;

   public dup() {
      for (int _snowman = 0; _snowman < this.a.length; _snowman++) {
         int _snowmanx = 0;
         int _snowmanxx = _snowman;
         if (_snowman == 2) {
            _snowmanx = 24;
            _snowmanxx = 10;
         } else if (_snowman == 3) {
            _snowmanx = 24;
            _snowmanxx = 19;
         }

         this.a[_snowman] = new dwn(this, _snowmanx, _snowmanxx);
         this.a[_snowman].a(-4.0F, (float)(16 + _snowman), -4.0F, 8.0F, 1.0F, 8.0F);
      }

      this.b = new dwn(this, 0, 16);
      this.b.a(-2.0F, 18.0F, -2.0F, 4.0F, 4.0F, 4.0F);
      Builder<dwn> _snowman = ImmutableList.builder();
      _snowman.add(this.b);
      _snowman.addAll(Arrays.asList(this.a));
      this.f = _snowman.build();
   }

   public void a(T var1, float var2, float var3, float var4, float var5, float var6) {
   }

   public void a(T var1, float var2, float var3, float var4) {
      float _snowman = afm.g(_snowman, _snowman.d, _snowman.c);
      if (_snowman < 0.0F) {
         _snowman = 0.0F;
      }

      for (int _snowmanx = 0; _snowmanx < this.a.length; _snowmanx++) {
         this.a[_snowmanx].b = (float)(-(4 - _snowmanx)) * _snowman * 1.7F;
      }
   }

   public ImmutableList<dwn> b() {
      return this.f;
   }
}
