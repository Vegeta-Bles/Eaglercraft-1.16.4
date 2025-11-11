import javax.annotation.Nullable;

public class bbw extends bbt {
   private dcn b;
   private int c;

   public bbw(bbr var1) {
      super(_snowman);
   }

   @Override
   public void b() {
      if (this.c++ % 10 == 0) {
         float _snowman = (this.a.cY().nextFloat() - 0.5F) * 8.0F;
         float _snowmanx = (this.a.cY().nextFloat() - 0.5F) * 4.0F;
         float _snowmanxx = (this.a.cY().nextFloat() - 0.5F) * 8.0F;
         this.a.l.a(hh.v, this.a.cD() + (double)_snowman, this.a.cE() + 2.0 + (double)_snowmanx, this.a.cH() + (double)_snowmanxx, 0.0, 0.0, 0.0);
      }
   }

   @Override
   public void c() {
      this.c++;
      if (this.b == null) {
         fx _snowman = this.a.l.a(chn.a.e, cjk.a);
         this.b = dcn.c(_snowman);
      }

      double _snowman = this.b.c(this.a.cD(), this.a.cE(), this.a.cH());
      if (!(_snowman < 100.0) && !(_snowman > 22500.0) && !this.a.u && !this.a.v) {
         this.a.c(1.0F);
      } else {
         this.a.c(0.0F);
      }
   }

   @Override
   public void d() {
      this.b = null;
      this.c = 0;
   }

   @Override
   public float f() {
      return 3.0F;
   }

   @Nullable
   @Override
   public dcn g() {
      return this.b;
   }

   @Override
   public bch<bbw> i() {
      return bch.j;
   }
}
