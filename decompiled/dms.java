import com.mojang.blaze3d.systems.RenderSystem;
import javax.annotation.Nullable;

public class dms implements dmq {
   private final dms.a c;
   private final nr d;
   private final nr e;
   private dmq.a f = dmq.a.a;
   private long g;
   private float h;
   private float i;
   private final boolean j;

   public dms(dms.a var1, nr var2, @Nullable nr var3, boolean var4) {
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.j = _snowman;
   }

   @Override
   public dmq.a a(dfm var1, dmr var2, long var3) {
      _snowman.b().M().a(a);
      RenderSystem.color3f(1.0F, 1.0F, 1.0F);
      _snowman.b(_snowman, 0, 0, 0, 96, this.a(), this.d());
      this.c.a(_snowman, _snowman, 6, 6);
      if (this.e == null) {
         _snowman.b().g.b(_snowman, this.d, 30.0F, 12.0F, -11534256);
      } else {
         _snowman.b().g.b(_snowman, this.d, 30.0F, 7.0F, -11534256);
         _snowman.b().g.b(_snowman, this.e, 30.0F, 18.0F, -16777216);
      }

      if (this.j) {
         dkw.a(_snowman, 3, 28, 157, 29, -1);
         float _snowman = (float)afm.b((double)this.h, (double)this.i, (double)((float)(_snowman - this.g) / 100.0F));
         int _snowmanx;
         if (this.i >= this.h) {
            _snowmanx = -16755456;
         } else {
            _snowmanx = -11206656;
         }

         dkw.a(_snowman, 3, 28, (int)(3.0F + 154.0F * _snowman), 29, _snowmanx);
         this.h = _snowman;
         this.g = _snowman;
      }

      return this.f;
   }

   public void b() {
      this.f = dmq.a.b;
   }

   public void a(float var1) {
      this.i = _snowman;
   }

   public static enum a {
      a(0, 0),
      b(1, 0),
      c(2, 0),
      d(0, 1),
      e(1, 1),
      f(2, 1);

      private final int g;
      private final int h;

      private a(int var3, int var4) {
         this.g = _snowman;
         this.h = _snowman;
      }

      public void a(dfm var1, dkw var2, int var3, int var4) {
         RenderSystem.enableBlend();
         _snowman.b(_snowman, _snowman, _snowman, 176 + this.g * 20, this.h * 20, 20, 20);
         RenderSystem.enableBlend();
      }
   }
}
