public class emv implements eme {
   private final dzm a;
   private final enu b;
   private int c = 0;

   public emv(dzm var1, enu var2) {
      this.a = _snowman;
      this.b = _snowman;
   }

   @Override
   public void a() {
      this.c--;
      if (this.c <= 0 && this.a.aI()) {
         float _snowman = this.a.l.t.nextFloat();
         if (_snowman < 1.0E-4F) {
            this.c = 0;
            this.b.a((emt)(new emw.a(this.a, adq.v)));
         } else if (_snowman < 0.001F) {
            this.c = 0;
            this.b.a((emt)(new emw.a(this.a, adq.u)));
         } else if (_snowman < 0.01F) {
            this.c = 0;
            this.b.a((emt)(new emw.a(this.a, adq.t)));
         }
      }
   }
}
