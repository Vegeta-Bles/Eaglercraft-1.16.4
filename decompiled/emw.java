public class emw {
   public static class a extends emd {
      private final dzm n;

      protected a(dzm var1, adp var2) {
         super(_snowman, adr.i);
         this.n = _snowman;
         this.i = false;
         this.j = 0;
         this.d = 1.0F;
         this.l = true;
         this.m = true;
      }

      @Override
      public void r() {
         if (this.n.y || !this.n.aI()) {
            this.o();
         }
      }
   }

   public static class b extends emd {
      private final dzm n;
      private int o;

      public b(dzm var1) {
         super(adq.s, adr.i);
         this.n = _snowman;
         this.i = true;
         this.j = 0;
         this.d = 1.0F;
         this.l = true;
         this.m = true;
      }

      @Override
      public void r() {
         if (!this.n.y && this.o >= 0) {
            if (this.n.aI()) {
               this.o++;
            } else {
               this.o -= 2;
            }

            this.o = Math.min(this.o, 40);
            this.d = Math.max(0.0F, Math.min((float)this.o / 40.0F, 1.0F));
         } else {
            this.o();
         }
      }
   }
}
