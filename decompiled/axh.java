public class axh extends avv {
   private final aqu a;

   public axh(aqu var1) {
      this.a = _snowman;
   }

   @Override
   public boolean a() {
      return this.a.ao() && !this.a.l.b(this.a.cB()).a(aef.b);
   }

   @Override
   public void c() {
      fx _snowman = null;

      for (fx _snowmanx : fx.b(
         afm.c(this.a.cD() - 2.0), afm.c(this.a.cE() - 2.0), afm.c(this.a.cH() - 2.0), afm.c(this.a.cD() + 2.0), afm.c(this.a.cE()), afm.c(this.a.cH() + 2.0)
      )) {
         if (this.a.l.b(_snowmanx).a(aef.b)) {
            _snowman = _snowmanx;
            break;
         }
      }

      if (_snowman != null) {
         this.a.u().a((double)_snowman.u(), (double)_snowman.v(), (double)_snowman.w(), 1.0);
      }
   }
}
