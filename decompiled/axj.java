import javax.annotation.Nullable;

public class axj extends axk {
   public axj(aqu var1, double var2) {
      super(_snowman, _snowman);
   }

   @Nullable
   @Override
   protected dcn g() {
      dcn _snowman = null;
      if (this.a.aE()) {
         _snowman = azj.b(this.a, 15, 15);
      }

      if (this.a.cY().nextFloat() >= this.h) {
         _snowman = this.j();
      }

      return _snowman == null ? super.g() : _snowman;
   }

   @Nullable
   private dcn j() {
      fx _snowman = this.a.cB();
      fx.a _snowmanx = new fx.a();
      fx.a _snowmanxx = new fx.a();

      for (fx _snowmanxxx : fx.b(
         afm.c(this.a.cD() - 3.0),
         afm.c(this.a.cE() - 6.0),
         afm.c(this.a.cH() - 3.0),
         afm.c(this.a.cD() + 3.0),
         afm.c(this.a.cE() + 6.0),
         afm.c(this.a.cH() + 3.0)
      )) {
         if (!_snowman.equals(_snowmanxxx)) {
            buo _snowmanxxxx = this.a.l.d_(_snowmanxx.a(_snowmanxxx, gc.a)).b();
            boolean _snowmanxxxxx = _snowmanxxxx instanceof bxx || _snowmanxxxx.a(aed.s);
            if (_snowmanxxxxx && this.a.l.w(_snowmanxxx) && this.a.l.w(_snowmanx.a(_snowmanxxx, gc.b))) {
               return dcn.c(_snowmanxxx);
            }
         }
      }

      return null;
   }
}
