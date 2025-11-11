public class auw extends ava {
   private final int h;

   public auw(aqn var1, int var2) {
      super(_snowman);
      this.h = _snowman;
   }

   @Override
   public void a() {
      if (this.d) {
         this.d = false;
         this.a.aC = this.a(this.a.aC, this.h() + 20.0F, this.b);
         this.a.q = this.a(this.a.q, this.g() + 10.0F, this.c);
      } else {
         if (this.a.x().m()) {
            this.a.q = this.a(this.a.q, 0.0F, 5.0F);
         }

         this.a.aC = this.a(this.a.aC, this.a.aA, this.b);
      }

      float _snowman = afm.g(this.a.aC - this.a.aA);
      if (_snowman < (float)(-this.h)) {
         this.a.aA -= 4.0F;
      } else if (_snowman > (float)this.h) {
         this.a.aA += 4.0F;
      }
   }
}
