public class boe extends boj {
   public boe(vk var1) {
      super(_snowman);
   }

   public boolean a(bio var1, brx var2) {
      bkx _snowman = null;
      bmb _snowmanx = null;
      bmb _snowmanxx = null;

      for (int _snowmanxxx = 0; _snowmanxxx < _snowman.Z_(); _snowmanxxx++) {
         bmb _snowmanxxxx = _snowman.a(_snowmanxxx);
         blx _snowmanxxxxx = _snowmanxxxx.b();
         if (_snowmanxxxxx instanceof bke) {
            bke _snowmanxxxxxx = (bke)_snowmanxxxxx;
            if (_snowman == null) {
               _snowman = _snowmanxxxxxx.b();
            } else if (_snowman != _snowmanxxxxxx.b()) {
               return false;
            }

            int _snowmanxxxxxxx = cca.b(_snowmanxxxx);
            if (_snowmanxxxxxxx > 6) {
               return false;
            }

            if (_snowmanxxxxxxx > 0) {
               if (_snowmanx != null) {
                  return false;
               }

               _snowmanx = _snowmanxxxx;
            } else {
               if (_snowmanxx != null) {
                  return false;
               }

               _snowmanxx = _snowmanxxxx;
            }
         }
      }

      return _snowmanx != null && _snowmanxx != null;
   }

   public bmb a(bio var1) {
      for (int _snowman = 0; _snowman < _snowman.Z_(); _snowman++) {
         bmb _snowmanx = _snowman.a(_snowman);
         if (!_snowmanx.a()) {
            int _snowmanxx = cca.b(_snowmanx);
            if (_snowmanxx > 0 && _snowmanxx <= 6) {
               bmb _snowmanxxx = _snowmanx.i();
               _snowmanxxx.e(1);
               return _snowmanxxx;
            }
         }
      }

      return bmb.b;
   }

   public gj<bmb> b(bio var1) {
      gj<bmb> _snowman = gj.a(_snowman.Z_(), bmb.b);

      for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
         bmb _snowmanxx = _snowman.a(_snowmanx);
         if (!_snowmanxx.a()) {
            if (_snowmanxx.b().p()) {
               _snowman.set(_snowmanx, new bmb(_snowmanxx.b().o()));
            } else if (_snowmanxx.n() && cca.b(_snowmanxx) > 0) {
               bmb _snowmanxxx = _snowmanxx.i();
               _snowmanxxx.e(1);
               _snowman.set(_snowmanx, _snowmanxxx);
            }
         }
      }

      return _snowman;
   }

   @Override
   public bos<?> ag_() {
      return bos.k;
   }

   @Override
   public boolean a(int var1, int var2) {
      return _snowman * _snowman >= 2;
   }
}
