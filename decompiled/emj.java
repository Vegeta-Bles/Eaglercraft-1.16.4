public class emj implements eme {
   private final dzm a;
   private boolean b;
   private boolean c = true;

   public emj(dzm var1) {
      this.a = _snowman;
   }

   @Override
   public void a() {
      brx _snowman = this.a.l;
      ceh _snowmanx = _snowman.c(this.a.cc().c(0.0, -0.4F, 0.0).h(0.001)).filter(var0 -> var0.a(bup.lc)).findFirst().orElse(null);
      if (_snowmanx != null) {
         if (!this.b && !this.c && _snowmanx.a(bup.lc) && !this.a.a_()) {
            boolean _snowmanxx = _snowmanx.c(bus.a);
            if (_snowmanxx) {
               this.a.a(adq.bi, 1.0F, 1.0F);
            } else {
               this.a.a(adq.bg, 1.0F, 1.0F);
            }
         }

         this.b = true;
      } else {
         this.b = false;
      }

      this.c = false;
   }
}
