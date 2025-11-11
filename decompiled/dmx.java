import javax.annotation.Nullable;

public class dmx extends ejq {
   private final vk d;
   private final eao e;
   private final eao f;
   private final boolean g;
   private final dmx.a h;

   public dmx(vk var1, boolean var2) {
      this.d = _snowman;
      this.g = _snowman;
      this.h = new dmx.a(0, 0, 256, 256);
      dex.a(_snowman ? det.b.a : det.b.e, this.b(), 256, 256);
      this.e = eao.p(_snowman);
      this.f = eao.q(_snowman);
   }

   @Override
   public void a(ach var1) {
   }

   @Override
   public void close() {
      this.c();
   }

   @Nullable
   public dmz a(dec var1) {
      if (_snowman.f() != this.g) {
         return null;
      } else {
         dmx.a _snowman = this.h.a(_snowman);
         if (_snowman != null) {
            this.d();
            _snowman.a(_snowman.a, _snowman.b);
            float _snowmanx = 256.0F;
            float _snowmanxx = 256.0F;
            float _snowmanxxx = 0.01F;
            return new dmz(
               this.e,
               this.f,
               ((float)_snowman.a + 0.01F) / 256.0F,
               ((float)_snowman.a - 0.01F + (float)_snowman.d()) / 256.0F,
               ((float)_snowman.b + 0.01F) / 256.0F,
               ((float)_snowman.b - 0.01F + (float)_snowman.e()) / 256.0F,
               _snowman.h(),
               _snowman.i(),
               _snowman.j(),
               _snowman.k()
            );
         } else {
            return null;
         }
      }
   }

   public vk a() {
      return this.d;
   }

   static class a {
      private final int a;
      private final int b;
      private final int c;
      private final int d;
      private dmx.a e;
      private dmx.a f;
      private boolean g;

      private a(int var1, int var2, int var3, int var4) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
      }

      @Nullable
      dmx.a a(dec var1) {
         if (this.e != null && this.f != null) {
            dmx.a _snowman = this.e.a(_snowman);
            if (_snowman == null) {
               _snowman = this.f.a(_snowman);
            }

            return _snowman;
         } else if (this.g) {
            return null;
         } else {
            int _snowman = _snowman.d();
            int _snowmanx = _snowman.e();
            if (_snowman > this.c || _snowmanx > this.d) {
               return null;
            } else if (_snowman == this.c && _snowmanx == this.d) {
               this.g = true;
               return this;
            } else {
               int _snowmanxx = this.c - _snowman;
               int _snowmanxxx = this.d - _snowmanx;
               if (_snowmanxx > _snowmanxxx) {
                  this.e = new dmx.a(this.a, this.b, _snowman, this.d);
                  this.f = new dmx.a(this.a + _snowman + 1, this.b, this.c - _snowman - 1, this.d);
               } else {
                  this.e = new dmx.a(this.a, this.b, this.c, _snowmanx);
                  this.f = new dmx.a(this.a, this.b + _snowmanx + 1, this.c, this.d - _snowmanx - 1);
               }

               return this.e.a(_snowman);
            }
         }
      }
   }
}
