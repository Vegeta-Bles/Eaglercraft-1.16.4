public class boo extends boj {
   public boo(vk var1) {
      super(_snowman);
   }

   public boolean a(bio var1, brx var2) {
      int _snowman = 0;
      bmb _snowmanx = bmb.b;

      for (int _snowmanxx = 0; _snowmanxx < _snowman.Z_(); _snowmanxx++) {
         bmb _snowmanxxx = _snowman.a(_snowmanxx);
         if (!_snowmanxxx.a()) {
            if (_snowmanxxx.b() == bmd.nf) {
               if (!_snowmanx.a()) {
                  return false;
               }

               _snowmanx = _snowmanxxx;
            } else {
               if (_snowmanxxx.b() != bmd.pc) {
                  return false;
               }

               _snowman++;
            }
         }
      }

      return !_snowmanx.a() && _snowman > 0;
   }

   public bmb a(bio var1) {
      int _snowman = 0;
      bmb _snowmanx = bmb.b;

      for (int _snowmanxx = 0; _snowmanxx < _snowman.Z_(); _snowmanxx++) {
         bmb _snowmanxxx = _snowman.a(_snowmanxx);
         if (!_snowmanxxx.a()) {
            if (_snowmanxxx.b() == bmd.nf) {
               if (!_snowmanx.a()) {
                  return bmb.b;
               }

               _snowmanx = _snowmanxxx;
            } else {
               if (_snowmanxxx.b() != bmd.pc) {
                  return bmb.b;
               }

               _snowman++;
            }
         }
      }

      if (!_snowmanx.a() && _snowman >= 1) {
         bmb _snowmanxxx = _snowmanx.i();
         _snowmanxxx.e(_snowman + 1);
         return _snowmanxxx;
      } else {
         return bmb.b;
      }
   }

   @Override
   public boolean a(int var1, int var2) {
      return _snowman >= 3 && _snowman >= 3;
   }

   @Override
   public bos<?> ag_() {
      return bos.e;
   }
}
