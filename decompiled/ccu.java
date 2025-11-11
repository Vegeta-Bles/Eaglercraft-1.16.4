import java.util.Random;
import javax.annotation.Nullable;

public class ccu extends ccj implements aoy, cdm {
   public int a;
   public float b;
   public float c;
   public float g;
   public float h;
   public float i;
   public float j;
   public float k;
   public float l;
   public float m;
   private static final Random n = new Random();
   private nr o;

   public ccu() {
      super(cck.l);
   }

   @Override
   public md a(md var1) {
      super.a(_snowman);
      if (this.S()) {
         _snowman.a("CustomName", nr.a.a(this.o));
      }

      return _snowman;
   }

   @Override
   public void a(ceh var1, md var2) {
      super.a(_snowman, _snowman);
      if (_snowman.c("CustomName", 8)) {
         this.o = nr.a.a(_snowman.l("CustomName"));
      }
   }

   @Override
   public void aj_() {
      this.j = this.i;
      this.l = this.k;
      bfw _snowman = this.d.a((double)this.e.u() + 0.5, (double)this.e.v() + 0.5, (double)this.e.w() + 0.5, 3.0, false);
      if (_snowman != null) {
         double _snowmanx = _snowman.cD() - ((double)this.e.u() + 0.5);
         double _snowmanxx = _snowman.cH() - ((double)this.e.w() + 0.5);
         this.m = (float)afm.d(_snowmanxx, _snowmanx);
         this.i += 0.1F;
         if (this.i < 0.5F || n.nextInt(40) == 0) {
            float _snowmanxxx = this.g;

            do {
               this.g = this.g + (float)(n.nextInt(4) - n.nextInt(4));
            } while (_snowmanxxx == this.g);
         }
      } else {
         this.m += 0.02F;
         this.i -= 0.1F;
      }

      while (this.k >= (float) Math.PI) {
         this.k -= (float) (Math.PI * 2);
      }

      while (this.k < (float) -Math.PI) {
         this.k += (float) (Math.PI * 2);
      }

      while (this.m >= (float) Math.PI) {
         this.m -= (float) (Math.PI * 2);
      }

      while (this.m < (float) -Math.PI) {
         this.m += (float) (Math.PI * 2);
      }

      float _snowmanx = this.m - this.k;

      while (_snowmanx >= (float) Math.PI) {
         _snowmanx -= (float) (Math.PI * 2);
      }

      while (_snowmanx < (float) -Math.PI) {
         _snowmanx += (float) (Math.PI * 2);
      }

      this.k += _snowmanx * 0.4F;
      this.i = afm.a(this.i, 0.0F, 1.0F);
      this.a++;
      this.c = this.b;
      float _snowmanxx = (this.g - this.b) * 0.4F;
      float _snowmanxxx = 0.2F;
      _snowmanxx = afm.a(_snowmanxx, -0.2F, 0.2F);
      this.h = this.h + (_snowmanxx - this.h) * 0.9F;
      this.b = this.b + this.h;
   }

   @Override
   public nr R() {
      return (nr)(this.o != null ? this.o : new of("container.enchant"));
   }

   public void a(@Nullable nr var1) {
      this.o = _snowman;
   }

   @Nullable
   @Override
   public nr T() {
      return this.o;
   }
}
