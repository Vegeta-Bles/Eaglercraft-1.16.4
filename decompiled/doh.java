import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.function.Consumer;

public class doh extends don {
   private static final vk a = new vk("textures/gui/title/mojangstudios.png");
   private static final int b = aez.a.a(255, 239, 50, 61);
   private static final int c = b & 16777215;
   private final djz d;
   private final ace e;
   private final Consumer<Optional<Throwable>> i;
   private final boolean j;
   private float k;
   private long l = -1L;
   private long m = -1L;

   public doh(djz var1, ace var2, Consumer<Optional<Throwable>> var3, boolean var4) {
      this.d = _snowman;
      this.e = _snowman;
      this.i = _snowman;
      this.j = _snowman;
   }

   public static void a(djz var0) {
      _snowman.M().a(a, new doh.a());
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      int _snowman = this.d.aD().o();
      int _snowmanx = this.d.aD().p();
      long _snowmanxx = x.b();
      if (this.j && (this.e.c() || this.d.y != null) && this.m == -1L) {
         this.m = _snowmanxx;
      }

      float _snowmanxxx = this.l > -1L ? (float)(_snowmanxx - this.l) / 1000.0F : -1.0F;
      float _snowmanxxxx = this.m > -1L ? (float)(_snowmanxx - this.m) / 500.0F : -1.0F;
      float _snowmanxxxxx;
      if (_snowmanxxx >= 1.0F) {
         if (this.d.y != null) {
            this.d.y.a(_snowman, 0, 0, _snowman);
         }

         int _snowmanxxxxxx = afm.f((1.0F - afm.a(_snowmanxxx - 1.0F, 0.0F, 1.0F)) * 255.0F);
         a(_snowman, 0, 0, _snowman, _snowmanx, c | _snowmanxxxxxx << 24);
         _snowmanxxxxx = 1.0F - afm.a(_snowmanxxx - 1.0F, 0.0F, 1.0F);
      } else if (this.j) {
         if (this.d.y != null && _snowmanxxxx < 1.0F) {
            this.d.y.a(_snowman, _snowman, _snowman, _snowman);
         }

         int _snowmanxxxxxx = afm.f(afm.a((double)_snowmanxxxx, 0.15, 1.0) * 255.0);
         a(_snowman, 0, 0, _snowman, _snowmanx, c | _snowmanxxxxxx << 24);
         _snowmanxxxxx = afm.a(_snowmanxxxx, 0.0F, 1.0F);
      } else {
         a(_snowman, 0, 0, _snowman, _snowmanx, b);
         _snowmanxxxxx = 1.0F;
      }

      int _snowmanxxxxxx = (int)((double)this.d.aD().o() * 0.5);
      int _snowmanxxxxxxx = (int)((double)this.d.aD().p() * 0.5);
      double _snowmanxxxxxxxx = Math.min((double)this.d.aD().o() * 0.75, (double)this.d.aD().p()) * 0.25;
      int _snowmanxxxxxxxxx = (int)(_snowmanxxxxxxxx * 0.5);
      double _snowmanxxxxxxxxxx = _snowmanxxxxxxxx * 4.0;
      int _snowmanxxxxxxxxxxx = (int)(_snowmanxxxxxxxxxx * 0.5);
      this.d.M().a(a);
      RenderSystem.enableBlend();
      RenderSystem.blendEquation(32774);
      RenderSystem.blendFunc(770, 1);
      RenderSystem.alphaFunc(516, 0.0F);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, _snowmanxxxxx);
      a(_snowman, _snowmanxxxxxx - _snowmanxxxxxxxxxxx, _snowmanxxxxxxx - _snowmanxxxxxxxxx, _snowmanxxxxxxxxxxx, (int)_snowmanxxxxxxxx, -0.0625F, 0.0F, 120, 60, 120, 120);
      a(_snowman, _snowmanxxxxxx, _snowmanxxxxxxx - _snowmanxxxxxxxxx, _snowmanxxxxxxxxxxx, (int)_snowmanxxxxxxxx, 0.0625F, 60.0F, 120, 60, 120, 120);
      RenderSystem.defaultBlendFunc();
      RenderSystem.defaultAlphaFunc();
      RenderSystem.disableBlend();
      int _snowmanxxxxxxxxxxxx = (int)((double)this.d.aD().p() * 0.8325);
      float _snowmanxxxxxxxxxxxxx = this.e.b();
      this.k = afm.a(this.k * 0.95F + _snowmanxxxxxxxxxxxxx * 0.050000012F, 0.0F, 1.0F);
      if (_snowmanxxx < 1.0F) {
         this.a(_snowman, _snowman / 2 - _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx - 5, _snowman / 2 + _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx + 5, 1.0F - afm.a(_snowmanxxx, 0.0F, 1.0F));
      }

      if (_snowmanxxx >= 2.0F) {
         this.d.a(null);
      }

      if (this.l == -1L && this.e.d() && (!this.j || _snowmanxxxx >= 2.0F)) {
         try {
            this.e.e();
            this.i.accept(Optional.empty());
         } catch (Throwable var23) {
            this.i.accept(Optional.of(var23));
         }

         this.l = x.b();
         if (this.d.y != null) {
            this.d.y.b(this.d, this.d.aD().o(), this.d.aD().p());
         }
      }
   }

   private void a(dfm var1, int var2, int var3, int var4, int var5, float var6) {
      int _snowman = afm.f((float)(_snowman - _snowman - 2) * this.k);
      int _snowmanx = Math.round(_snowman * 255.0F);
      int _snowmanxx = aez.a.a(_snowmanx, 255, 255, 255);
      a(_snowman, _snowman + 1, _snowman, _snowman - 1, _snowman + 1, _snowmanxx);
      a(_snowman, _snowman + 1, _snowman, _snowman - 1, _snowman - 1, _snowmanxx);
      a(_snowman, _snowman, _snowman, _snowman + 1, _snowman, _snowmanxx);
      a(_snowman, _snowman, _snowman, _snowman - 1, _snowman, _snowmanxx);
      a(_snowman, _snowman + 2, _snowman + 2, _snowman + _snowman, _snowman - 2, _snowmanxx);
   }

   @Override
   public boolean a() {
      return true;
   }

   static class a extends ejy {
      public a() {
         super(doh.a);
      }

      @Override
      protected ejy.a b(ach var1) {
         djz _snowman = djz.C();
         abm _snowmanx = _snowman.P().a();

         try (InputStream _snowmanxx = _snowmanx.a(abk.a, doh.a)) {
            return new ejy.a(new ell(true, true), det.a(_snowmanxx));
         } catch (IOException var18) {
            return new ejy.a(var18);
         }
      }
   }
}
