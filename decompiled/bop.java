public class bop extends bov {
   public bop(vk var1) {
      super(
         _snowman,
         "",
         3,
         3,
         gj.a(bon.a, bon.a(bmd.mb), bon.a(bmd.mb), bon.a(bmd.mb), bon.a(bmd.mb), bon.a(bmd.nf), bon.a(bmd.mb), bon.a(bmd.mb), bon.a(bmd.mb), bon.a(bmd.mb)),
         new bmb(bmd.pc)
      );
   }

   @Override
   public boolean a(bio var1, brx var2) {
      if (!super.a(_snowman, _snowman)) {
         return false;
      } else {
         bmb _snowman = bmb.b;

         for (int _snowmanx = 0; _snowmanx < _snowman.Z_() && _snowman.a(); _snowmanx++) {
            bmb _snowmanxx = _snowman.a(_snowmanx);
            if (_snowmanxx.b() == bmd.nf) {
               _snowman = _snowmanxx;
            }
         }

         if (_snowman.a()) {
            return false;
         } else {
            cxx _snowmanxx = bmh.b(_snowman, _snowman);
            if (_snowmanxx == null) {
               return false;
            } else {
               return this.a(_snowmanxx) ? false : _snowmanxx.f < 4;
            }
         }
      }
   }

   private boolean a(cxx var1) {
      if (_snowman.j != null) {
         for (cxu _snowman : _snowman.j.values()) {
            if (_snowman.b() == cxu.a.i || _snowman.b() == cxu.a.j) {
               return true;
            }
         }
      }

      return false;
   }

   @Override
   public bmb a(bio var1) {
      bmb _snowman = bmb.b;

      for (int _snowmanx = 0; _snowmanx < _snowman.Z_() && _snowman.a(); _snowmanx++) {
         bmb _snowmanxx = _snowman.a(_snowmanx);
         if (_snowmanxx.b() == bmd.nf) {
            _snowman = _snowmanxx;
         }
      }

      _snowman = _snowman.i();
      _snowman.e(1);
      _snowman.p().b("map_scale_direction", 1);
      return _snowman;
   }

   @Override
   public boolean af_() {
      return true;
   }

   @Override
   public bos<?> ag_() {
      return bos.f;
   }
}
