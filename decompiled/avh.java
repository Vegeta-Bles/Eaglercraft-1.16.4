import java.util.EnumSet;

public class avh extends avv {
   private final aqu a;

   public avh(aqu var1) {
      this.a = _snowman;
      this.a(EnumSet.of(avv.a.a, avv.a.b));
   }

   @Override
   public boolean a() {
      return this.a.bI() < 140;
   }

   @Override
   public boolean b() {
      return this.a();
   }

   @Override
   public boolean C_() {
      return false;
   }

   @Override
   public void c() {
      this.g();
   }

   private void g() {
      Iterable<fx> _snowman = fx.b(
         afm.c(this.a.cD() - 1.0), afm.c(this.a.cE()), afm.c(this.a.cH() - 1.0), afm.c(this.a.cD() + 1.0), afm.c(this.a.cE() + 8.0), afm.c(this.a.cH() + 1.0)
      );
      fx _snowmanx = null;

      for (fx _snowmanxx : _snowman) {
         if (this.a(this.a.l, _snowmanxx)) {
            _snowmanx = _snowmanxx;
            break;
         }
      }

      if (_snowmanx == null) {
         _snowmanx = new fx(this.a.cD(), this.a.cE() + 8.0, this.a.cH());
      }

      this.a.x().a((double)_snowmanx.u(), (double)(_snowmanx.v() + 1), (double)_snowmanx.w(), 1.0);
   }

   @Override
   public void e() {
      this.g();
      this.a.a(0.02F, new dcn((double)this.a.aR, (double)this.a.aS, (double)this.a.aT));
      this.a.a(aqr.a, this.a.cC());
   }

   private boolean a(brz var1, fx var2) {
      ceh _snowman = _snowman.d_(_snowman);
      return (_snowman.b(_snowman).c() || _snowman.a(bup.lc)) && _snowman.a(_snowman, _snowman, cxe.a);
   }
}
