public class bpf extends boj {
   public bpf(vk var1) {
      super(_snowman);
   }

   public boolean a(bio var1, brx var2) {
      boolean _snowman = false;
      boolean _snowmanx = false;
      boolean _snowmanxx = false;
      boolean _snowmanxxx = false;

      for (int _snowmanxxxx = 0; _snowmanxxxx < _snowman.Z_(); _snowmanxxxx++) {
         bmb _snowmanxxxxx = _snowman.a(_snowmanxxxx);
         if (!_snowmanxxxxx.a()) {
            if (_snowmanxxxxx.b() == bup.bC.h() && !_snowmanxx) {
               _snowmanxx = true;
            } else if (_snowmanxxxxx.b() == bup.bD.h() && !_snowmanx) {
               _snowmanx = true;
            } else if (_snowmanxxxxx.b().a(aeg.I) && !_snowman) {
               _snowman = true;
            } else {
               if (_snowmanxxxxx.b() != bmd.kQ || _snowmanxxx) {
                  return false;
               }

               _snowmanxxx = true;
            }
         }
      }

      return _snowman && _snowmanxx && _snowmanx && _snowmanxxx;
   }

   public bmb a(bio var1) {
      bmb _snowman = bmb.b;

      for (int _snowmanx = 0; _snowmanx < _snowman.Z_(); _snowmanx++) {
         bmb _snowmanxx = _snowman.a(_snowmanx);
         if (!_snowmanxx.a() && _snowmanxx.b().a(aeg.I)) {
            _snowman = _snowmanxx;
            break;
         }
      }

      bmb _snowmanxx = new bmb(bmd.qR, 1);
      if (_snowman.b() instanceof bkh && ((bkh)_snowman.b()).e() instanceof bwu) {
         bwu _snowmanxxx = (bwu)((bkh)_snowman.b()).e();
         aps _snowmanxxxx = _snowmanxxx.c();
         bne.a(_snowmanxx, _snowmanxxxx, _snowmanxxx.d());
      }

      return _snowmanxx;
   }

   @Override
   public boolean a(int var1, int var2) {
      return _snowman >= 2 && _snowman >= 2;
   }

   @Override
   public bos<?> ag_() {
      return bos.n;
   }
}
