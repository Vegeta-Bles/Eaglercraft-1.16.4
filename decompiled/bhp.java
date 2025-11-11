public class bhp extends bhl {
   public bhp(aqe<?> var1, brx var2) {
      super(_snowman, _snowman);
   }

   public bhp(brx var1, double var2, double var4, double var6) {
      super(aqe.T, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public aou a(bfw var1, aot var2) {
      if (_snowman.eq()) {
         return aou.c;
      } else if (this.bs()) {
         return aou.c;
      } else if (!this.l.v) {
         return _snowman.m(this) ? aou.b : aou.c;
      } else {
         return aou.a;
      }
   }

   @Override
   public void a(int var1, int var2, int var3, boolean var4) {
      if (_snowman) {
         if (this.bs()) {
            this.be();
         }

         if (this.m() == 0) {
            this.d(-this.n());
            this.c(10);
            this.a(50.0F);
            this.aS();
         }
      }
   }

   @Override
   public bhl.a o() {
      return bhl.a.a;
   }
}
