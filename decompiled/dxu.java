import javax.annotation.Nullable;

public class dxu extends dzb {
   private final float a;
   private final dyw b;

   private dxu(dwt var1, double var2, double var4, double var6, float var8, float var9, float var10, dyw var11) {
      super(_snowman, _snowman, _snowman, _snowman);
      this.b = _snowman;
      this.v = _snowman;
      this.w = _snowman;
      this.x = _snowman;
      float _snowman = 0.9F;
      this.B *= 0.67499995F;
      int _snowmanx = (int)(32.0 / (Math.random() * 0.8 + 0.2));
      this.t = (int)Math.max((float)_snowmanx * 0.9F, 1.0F);
      this.b(_snowman);
      this.a = ((float)Math.random() - 0.5F) * 0.1F;
      this.z = (float)Math.random() * (float) (Math.PI * 2);
   }

   @Override
   public dyk b() {
      return dyk.b;
   }

   @Override
   public float b(float var1) {
      return this.B * afm.a(((float)this.s + _snowman) / (float)this.t * 32.0F, 0.0F, 1.0F);
   }

   @Override
   public void a() {
      this.d = this.g;
      this.e = this.h;
      this.f = this.i;
      if (this.s++ >= this.t) {
         this.j();
      } else {
         this.b(this.b);
         this.A = this.z;
         this.z = this.z + (float) Math.PI * this.a * 2.0F;
         if (this.m) {
            this.A = this.z = 0.0F;
         }

         this.a(this.j, this.k, this.l);
         this.k -= 0.003F;
         this.k = Math.max(this.k, -0.14F);
      }
   }

   public static class a implements dyj<hc> {
      private final dyw a;

      public a(dyw var1) {
         this.a = _snowman;
      }

      @Nullable
      public dyg a(hc var1, dwt var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         ceh _snowman = _snowman.c();
         if (!_snowman.g() && _snowman.h() == bzh.a) {
            return null;
         } else {
            fx _snowmanx = new fx(_snowman, _snowman, _snowman);
            int _snowmanxx = djz.C().al().a(_snowman, _snowman, _snowmanx);
            if (_snowman.b() instanceof bwo) {
               _snowmanxx = ((bwo)_snowman.b()).c(_snowman, _snowman, _snowmanx);
            }

            float _snowmanxxx = (float)(_snowmanxx >> 16 & 0xFF) / 255.0F;
            float _snowmanxxxx = (float)(_snowmanxx >> 8 & 0xFF) / 255.0F;
            float _snowmanxxxxx = (float)(_snowmanxx & 0xFF) / 255.0F;
            return new dxu(_snowman, _snowman, _snowman, _snowman, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, this.a);
         }
      }
   }
}
