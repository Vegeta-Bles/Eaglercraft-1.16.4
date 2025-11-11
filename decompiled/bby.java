import javax.annotation.Nullable;

public class bby extends bbt {
   private dcn b;

   public bby(bbr var1) {
      super(_snowman);
   }

   @Override
   public void c() {
      if (this.b == null) {
         this.b = this.a.cA();
      }
   }

   @Override
   public boolean a() {
      return true;
   }

   @Override
   public void d() {
      this.b = null;
   }

   @Override
   public float f() {
      return 1.0F;
   }

   @Nullable
   @Override
   public dcn g() {
      return this.b;
   }

   @Override
   public bch<bby> i() {
      return bch.k;
   }
}
