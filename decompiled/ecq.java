public class ecq extends ece<cdj> {
   public ecq(ecd var1) {
      super(_snowman);
   }

   public void a(cdj var1, float var2, dfm var3, eag var4, int var5, int var6) {
      if (djz.C().s.eV() || djz.C().s.a_()) {
         fx _snowman = _snowman.h();
         fx _snowmanx = _snowman.j();
         if (_snowmanx.u() >= 1 && _snowmanx.v() >= 1 && _snowmanx.w() >= 1) {
            if (_snowman.x() == cfo.a || _snowman.x() == cfo.b) {
               double _snowmanxx = (double)_snowman.u();
               double _snowmanxxx = (double)_snowman.w();
               double _snowmanxxxx = (double)_snowman.v();
               double _snowmanxxxxx = _snowmanxxxx + (double)_snowmanx.v();
               double _snowmanxxxxxx;
               double _snowmanxxxxxxx;
               switch (_snowman.k()) {
                  case b:
                     _snowmanxxxxxx = (double)_snowmanx.u();
                     _snowmanxxxxxxx = (double)(-_snowmanx.w());
                     break;
                  case c:
                     _snowmanxxxxxx = (double)(-_snowmanx.u());
                     _snowmanxxxxxxx = (double)_snowmanx.w();
                     break;
                  default:
                     _snowmanxxxxxx = (double)_snowmanx.u();
                     _snowmanxxxxxxx = (double)_snowmanx.w();
               }

               double _snowmanxx;
               double _snowmanxxx;
               double _snowmanxxxx;
               double _snowmanxxxxx;
               switch (_snowman.l()) {
                  case b:
                     _snowmanxx = _snowmanxxxxxxx < 0.0 ? _snowmanxx : _snowmanxx + 1.0;
                     _snowmanxxx = _snowmanxxxxxx < 0.0 ? _snowmanxxx + 1.0 : _snowmanxxx;
                     _snowmanxxxx = _snowmanxx - _snowmanxxxxxxx;
                     _snowmanxxxxx = _snowmanxxx + _snowmanxxxxxx;
                     break;
                  case c:
                     _snowmanxx = _snowmanxxxxxx < 0.0 ? _snowmanxx : _snowmanxx + 1.0;
                     _snowmanxxx = _snowmanxxxxxxx < 0.0 ? _snowmanxxx : _snowmanxxx + 1.0;
                     _snowmanxxxx = _snowmanxx - _snowmanxxxxxx;
                     _snowmanxxxxx = _snowmanxxx - _snowmanxxxxxxx;
                     break;
                  case d:
                     _snowmanxx = _snowmanxxxxxxx < 0.0 ? _snowmanxx + 1.0 : _snowmanxx;
                     _snowmanxxx = _snowmanxxxxxx < 0.0 ? _snowmanxxx : _snowmanxxx + 1.0;
                     _snowmanxxxx = _snowmanxx + _snowmanxxxxxxx;
                     _snowmanxxxxx = _snowmanxxx - _snowmanxxxxxx;
                     break;
                  default:
                     _snowmanxx = _snowmanxxxxxx < 0.0 ? _snowmanxx + 1.0 : _snowmanxx;
                     _snowmanxxx = _snowmanxxxxxxx < 0.0 ? _snowmanxxx + 1.0 : _snowmanxxx;
                     _snowmanxxxx = _snowmanxx + _snowmanxxxxxx;
                     _snowmanxxxxx = _snowmanxxx + _snowmanxxxxxxx;
               }

               float _snowmanxx = 1.0F;
               float _snowmanxxx = 0.9F;
               float _snowmanxxxx = 0.5F;
               dfq _snowmanxxxxx = _snowman.getBuffer(eao.t());
               if (_snowman.x() == cfo.a || _snowman.I()) {
                  eae.a(_snowman, _snowmanxxxxx, _snowmanxx, _snowmanxxxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxx, 0.9F, 0.9F, 0.9F, 1.0F, 0.5F, 0.5F, 0.5F);
               }

               if (_snowman.x() == cfo.a && _snowman.H()) {
                  this.a(_snowman, _snowmanxxxxx, _snowman, true, _snowman);
                  this.a(_snowman, _snowmanxxxxx, _snowman, false, _snowman);
               }
            }
         }
      }
   }

   private void a(cdj var1, dfq var2, fx var3, boolean var4, dfm var5) {
      brc _snowman = _snowman.v();
      fx _snowmanx = _snowman.o();
      fx _snowmanxx = _snowmanx.a(_snowman);

      for (fx _snowmanxxx : fx.a(_snowmanxx, _snowmanxx.a(_snowman.j()).b(-1, -1, -1))) {
         ceh _snowmanxxxx = _snowman.d_(_snowmanxxx);
         boolean _snowmanxxxxx = _snowmanxxxx.g();
         boolean _snowmanxxxxxx = _snowmanxxxx.a(bup.iN);
         if (_snowmanxxxxx || _snowmanxxxxxx) {
            float _snowmanxxxxxxx = _snowmanxxxxx ? 0.05F : 0.0F;
            double _snowmanxxxxxxxx = (double)((float)(_snowmanxxx.u() - _snowmanx.u()) + 0.45F - _snowmanxxxxxxx);
            double _snowmanxxxxxxxxx = (double)((float)(_snowmanxxx.v() - _snowmanx.v()) + 0.45F - _snowmanxxxxxxx);
            double _snowmanxxxxxxxxxx = (double)((float)(_snowmanxxx.w() - _snowmanx.w()) + 0.45F - _snowmanxxxxxxx);
            double _snowmanxxxxxxxxxxx = (double)((float)(_snowmanxxx.u() - _snowmanx.u()) + 0.55F + _snowmanxxxxxxx);
            double _snowmanxxxxxxxxxxxx = (double)((float)(_snowmanxxx.v() - _snowmanx.v()) + 0.55F + _snowmanxxxxxxx);
            double _snowmanxxxxxxxxxxxxx = (double)((float)(_snowmanxxx.w() - _snowmanx.w()) + 0.55F + _snowmanxxxxxxx);
            if (_snowman) {
               eae.a(_snowman, _snowman, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F);
            } else if (_snowmanxxxxx) {
               eae.a(_snowman, _snowman, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, 0.5F, 0.5F, 1.0F, 1.0F, 0.5F, 0.5F, 1.0F);
            } else {
               eae.a(_snowman, _snowman, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, 1.0F, 0.25F, 0.25F, 1.0F, 1.0F, 0.25F, 0.25F);
            }
         }
      }
   }

   public boolean a(cdj var1) {
      return true;
   }
}
