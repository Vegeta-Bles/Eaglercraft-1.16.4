import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;

public class dlx extends dlo<dlx.a> {
   public dlx(djz var1, int var2, int var3, int var4, int var5, int var6) {
      super(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      this.m = false;
   }

   public int a(dkc var1) {
      return this.b(dlx.a.a(this.b.k, this.d, _snowman));
   }

   public void a(dkc var1, @Nullable dkc var2) {
      this.b(dlx.a.a(this.b.k, this.d, _snowman, _snowman));
   }

   public void a(dkc[] var1) {
      for (int _snowman = 0; _snowman < _snowman.length; _snowman += 2) {
         this.a(_snowman[_snowman], _snowman < _snowman.length - 1 ? _snowman[_snowman + 1] : null);
      }
   }

   @Override
   public int d() {
      return 400;
   }

   @Override
   protected int e() {
      return super.e() + 32;
   }

   @Nullable
   public dlh b(dkc var1) {
      for (dlx.a _snowman : this.au_()) {
         for (dlh _snowmanx : _snowman.a) {
            if (_snowmanx instanceof dlw && ((dlw)_snowmanx).a() == _snowman) {
               return _snowmanx;
            }
         }
      }

      return null;
   }

   public Optional<dlh> c(double var1, double var3) {
      for (dlx.a _snowman : this.au_()) {
         for (dlh _snowmanx : _snowman.a) {
            if (_snowmanx.b(_snowman, _snowman)) {
               return Optional.of(_snowmanx);
            }
         }
      }

      return Optional.empty();
   }

   public static class a extends dlo.a<dlx.a> {
      private final List<dlh> a;

      private a(List<dlh> var1) {
         this.a = _snowman;
      }

      public static dlx.a a(dkd var0, int var1, dkc var2) {
         return new dlx.a(ImmutableList.of(_snowman.a(_snowman, _snowman / 2 - 155, 0, 310)));
      }

      public static dlx.a a(dkd var0, int var1, dkc var2, @Nullable dkc var3) {
         dlh _snowman = _snowman.a(_snowman, _snowman / 2 - 155, 0, 150);
         return _snowman == null ? new dlx.a(ImmutableList.of(_snowman)) : new dlx.a(ImmutableList.of(_snowman, _snowman.a(_snowman, _snowman / 2 - 155 + 160, 0, 150)));
      }

      @Override
      public void a(dfm var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10) {
         this.a.forEach(var5x -> {
            var5x.m = _snowman;
            var5x.a(_snowman, _snowman, _snowman, _snowman);
         });
      }

      @Override
      public List<? extends dmi> au_() {
         return this.a;
      }
   }
}
