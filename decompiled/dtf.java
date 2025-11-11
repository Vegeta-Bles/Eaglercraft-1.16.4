import java.util.function.Function;

public abstract class dtf<E extends aqa> extends duc<E> {
   private final boolean a;
   private final float b;
   private final float f;
   private final float g;
   private final float h;
   private final float i;

   protected dtf(boolean var1, float var2, float var3) {
      this(_snowman, _snowman, _snowman, 2.0F, 2.0F, 24.0F);
   }

   protected dtf(boolean var1, float var2, float var3, float var4, float var5, float var6) {
      this(eao::d, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   protected dtf(Function<vk, eao> var1, boolean var2, float var3, float var4, float var5, float var6, float var7) {
      super(_snowman);
      this.a = _snowman;
      this.b = _snowman;
      this.f = _snowman;
      this.g = _snowman;
      this.h = _snowman;
      this.i = _snowman;
   }

   protected dtf() {
      this(false, 5.0F, 2.0F);
   }

   @Override
   public void a(dfm var1, dfq var2, int var3, int var4, float var5, float var6, float var7, float var8) {
      if (this.e) {
         _snowman.a();
         if (this.a) {
            float _snowman = 1.5F / this.g;
            _snowman.a(_snowman, _snowman, _snowman);
         }

         _snowman.a(0.0, (double)(this.b / 16.0F), (double)(this.f / 16.0F));
         this.a().forEach(var8x -> var8x.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman));
         _snowman.b();
         _snowman.a();
         float _snowman = 1.0F / this.h;
         _snowman.a(_snowman, _snowman, _snowman);
         _snowman.a(0.0, (double)(this.i / 16.0F), 0.0);
         this.b().forEach(var8x -> var8x.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman));
         _snowman.b();
      } else {
         this.a().forEach(var8x -> var8x.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman));
         this.b().forEach(var8x -> var8x.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman));
      }
   }

   protected abstract Iterable<dwn> a();

   protected abstract Iterable<dwn> b();
}
