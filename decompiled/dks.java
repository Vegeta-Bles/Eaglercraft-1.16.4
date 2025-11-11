public class dks {
   private final gh<dkr> a = new gh<>(32);

   public dks() {
   }

   public static dks a(dko var0) {
      dks _snowman = new dks();
      _snowman.a((var0x, var1x) -> var1x > 0 ? -1 : ((blb)var0x.b()).b(var0x), bmd.kY, bmd.kZ, bmd.la, bmd.lb, bmd.pG);
      _snowman.a((var0x, var1x) -> brv.a(0.5, 1.0), bup.gY, bup.gZ);
      _snowman.a((var0x, var1x) -> {
         if (var1x != 1) {
            return -1;
         } else {
            md _snowmanx = var0x.b("Explosion");
            int[] _snowmanx = _snowmanx != null && _snowmanx.c("Colors", 11) ? _snowmanx.n("Colors") : null;
            if (_snowmanx != null && _snowmanx.length != 0) {
               if (_snowmanx.length == 1) {
                  return _snowmanx[0];
               } else {
                  int _snowmanxx = 0;
                  int _snowmanxxx = 0;
                  int _snowmanxxxx = 0;

                  for (int _snowmanxxxxx : _snowmanx) {
                     _snowmanxx += (_snowmanxxxxx & 0xFF0000) >> 16;
                     _snowmanxxx += (_snowmanxxxxx & 0xFF00) >> 8;
                     _snowmanxxxx += (_snowmanxxxxx & 0xFF) >> 0;
                  }

                  _snowmanxx /= _snowmanx.length;
                  _snowmanxxx /= _snowmanx.length;
                  _snowmanxxxx /= _snowmanx.length;
                  return _snowmanxx << 16 | _snowmanxxx << 8 | _snowmanxxxx;
               }
            } else {
               return 9079434;
            }
         }
      }, bmd.pp);
      _snowman.a((var0x, var1x) -> var1x > 0 ? -1 : bnv.c(var0x), bmd.nv, bmd.qj, bmd.qm);

      for (bna _snowmanx : bna.f()) {
         _snowman.a((var1x, var2) -> _snowman.a(var2), _snowmanx);
      }

      _snowman.a((var1x, var2) -> {
         ceh _snowmanx = ((bkh)var1x.b()).e().n();
         return _snowman.a(_snowmanx, null, null, var2);
      }, bup.i, bup.aR, bup.aS, bup.dP, bup.ah, bup.ai, bup.aj, bup.ak, bup.al, bup.am, bup.dU);
      _snowman.a((var0x, var1x) -> var1x == 0 ? bnv.c(var0x) : -1, bmd.ql);
      _snowman.a((var0x, var1x) -> var1x == 0 ? -1 : bmh.g(var0x), bmd.nf);
      return _snowman;
   }

   public int a(bmb var1, int var2) {
      dkr _snowman = this.a.a(gm.T.a(_snowman.b()));
      return _snowman == null ? -1 : _snowman.getColor(_snowman, _snowman);
   }

   public void a(dkr var1, brw... var2) {
      for (brw _snowman : _snowman) {
         this.a.a(_snowman, blx.a(_snowman.h()));
      }
   }
}
