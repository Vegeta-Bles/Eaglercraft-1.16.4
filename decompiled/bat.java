public abstract class bat extends are {
   private int bq;

   protected bat(aqe<? extends bat> var1, brx var2) {
      super(_snowman, _snowman);
   }

   public boolean d(aah var1) {
      md _snowman = new md();
      _snowman.a("id", this.aW());
      this.e(_snowman);
      if (_snowman.g(_snowman)) {
         this.ad();
         return true;
      } else {
         return false;
      }
   }

   @Override
   public void j() {
      this.bq++;
      super.j();
   }

   public boolean eY() {
      return this.bq > 100;
   }
}
