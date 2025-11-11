import java.util.Random;

public class crb {
   public static class a extends cru {
      public a(fx var1) {
         super(clb.aa, 0);
         this.n = new cra(_snowman.u(), _snowman.v(), _snowman.w(), _snowman.u(), _snowman.v(), _snowman.w());
      }

      public a(csw var1, md var2) {
         super(clb.aa, _snowman);
      }

      @Override
      protected void a(md var1) {
      }

      @Override
      public boolean a(bsr var1, bsn var2, cfy var3, Random var4, cra var5, brd var6, fx var7) {
         int _snowman = _snowman.a(chn.a.c, this.n.a, this.n.c);
         fx.a _snowmanx = new fx.a(this.n.a, _snowman, this.n.c);

         while (_snowmanx.v() > 0) {
            ceh _snowmanxx = _snowman.d_(_snowmanx);
            ceh _snowmanxxx = _snowman.d_(_snowmanx.c());
            if (_snowmanxxx == bup.at.n() || _snowmanxxx == bup.b.n() || _snowmanxxx == bup.g.n() || _snowmanxxx == bup.c.n() || _snowmanxxx == bup.e.n()) {
               ceh _snowmanxxxx = !_snowmanxx.g() && !this.a(_snowmanxx) ? _snowmanxx : bup.C.n();

               for (gc _snowmanxxxxx : gc.values()) {
                  fx _snowmanxxxxxx = _snowmanx.a(_snowmanxxxxx);
                  ceh _snowmanxxxxxxx = _snowman.d_(_snowmanxxxxxx);
                  if (_snowmanxxxxxxx.g() || this.a(_snowmanxxxxxxx)) {
                     fx _snowmanxxxxxxxx = _snowmanxxxxxx.c();
                     ceh _snowmanxxxxxxxxx = _snowman.d_(_snowmanxxxxxxxx);
                     if ((_snowmanxxxxxxxxx.g() || this.a(_snowmanxxxxxxxxx)) && _snowmanxxxxx != gc.b) {
                        _snowman.a(_snowmanxxxxxx, _snowmanxxx, 3);
                     } else {
                        _snowman.a(_snowmanxxxxxx, _snowmanxxxx, 3);
                     }
                  }
               }

               this.n = new cra(_snowmanx.u(), _snowmanx.v(), _snowmanx.w(), _snowmanx.u(), _snowmanx.v(), _snowmanx.w());
               return this.a(_snowman, _snowman, _snowman, _snowmanx, cyq.G, null);
            }

            _snowmanx.e(0, -1, 0);
         }

         return false;
      }

      private boolean a(ceh var1) {
         return _snowman == bup.A.n() || _snowman == bup.B.n();
      }
   }
}
