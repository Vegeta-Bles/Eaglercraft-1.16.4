public class bog extends boj {
   public bog(vk var1) {
      super(_snowman);
   }

   public boolean a(bio var1, brx var2) {
      int _snowman = 0;
      bmb _snowmanx = bmb.b;

      for (int _snowmanxx = 0; _snowmanxx < _snowman.Z_(); _snowmanxx++) {
         bmb _snowmanxxx = _snowman.a(_snowmanxx);
         if (!_snowmanxxx.a()) {
            if (_snowmanxxx.b() == bmd.oU) {
               if (!_snowmanx.a()) {
                  return false;
               }

               _snowmanx = _snowmanxxx;
            } else {
               if (_snowmanxxx.b() != bmd.oT) {
                  return false;
               }

               _snowman++;
            }
         }
      }

      return !_snowmanx.a() && _snowmanx.n() && _snowman > 0;
   }

   public bmb a(bio var1) {
      int _snowman = 0;
      bmb _snowmanx = bmb.b;

      for (int _snowmanxx = 0; _snowmanxx < _snowman.Z_(); _snowmanxx++) {
         bmb _snowmanxxx = _snowman.a(_snowmanxx);
         if (!_snowmanxxx.a()) {
            if (_snowmanxxx.b() == bmd.oU) {
               if (!_snowmanx.a()) {
                  return bmb.b;
               }

               _snowmanx = _snowmanxxx;
            } else {
               if (_snowmanxxx.b() != bmd.oT) {
                  return bmb.b;
               }

               _snowman++;
            }
         }
      }

      if (!_snowmanx.a() && _snowmanx.n() && _snowman >= 1 && bns.d(_snowmanx) < 2) {
         bmb _snowmanxxx = new bmb(bmd.oU, _snowman);
         md _snowmanxxxx = _snowmanx.o().g();
         _snowmanxxxx.b("generation", bns.d(_snowmanx) + 1);
         _snowmanxxx.c(_snowmanxxxx);
         return _snowmanxxx;
      } else {
         return bmb.b;
      }
   }

   public gj<bmb> b(bio var1) {
      gj<bmb> _snowman = gj.a(_snowman.Z_(), bmb.b);

      for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
         bmb _snowmanxx = _snowman.a(_snowmanx);
         if (_snowmanxx.b().p()) {
            _snowman.set(_snowmanx, new bmb(_snowmanxx.b().o()));
         } else if (_snowmanxx.b() instanceof bns) {
            bmb _snowmanxxx = _snowmanxx.i();
            _snowmanxxx.e(1);
            _snowman.set(_snowmanx, _snowmanxxx);
            break;
         }
      }

      return _snowman;
   }

   @Override
   public bos<?> ag_() {
      return bos.d;
   }

   @Override
   public boolean a(int var1, int var2) {
      return _snowman >= 3 && _snowman >= 3;
   }
}
