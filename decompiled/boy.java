public class boy extends boj {
   public boy(vk var1) {
      super(_snowman);
   }

   public boolean a(bio var1, brx var2) {
      int _snowman = 0;
      int _snowmanx = 0;

      for (int _snowmanxx = 0; _snowmanxx < _snowman.Z_(); _snowmanxx++) {
         bmb _snowmanxxx = _snowman.a(_snowmanxx);
         if (!_snowmanxxx.a()) {
            if (buo.a(_snowmanxxx.b()) instanceof bzs) {
               _snowman++;
            } else {
               if (!(_snowmanxxx.b() instanceof bky)) {
                  return false;
               }

               _snowmanx++;
            }

            if (_snowmanx > 1 || _snowman > 1) {
               return false;
            }
         }
      }

      return _snowman == 1 && _snowmanx == 1;
   }

   public bmb a(bio var1) {
      bmb _snowman = bmb.b;
      bky _snowmanx = (bky)bmd.mu;

      for (int _snowmanxx = 0; _snowmanxx < _snowman.Z_(); _snowmanxx++) {
         bmb _snowmanxxx = _snowman.a(_snowmanxx);
         if (!_snowmanxxx.a()) {
            blx _snowmanxxxx = _snowmanxxx.b();
            if (buo.a(_snowmanxxxx) instanceof bzs) {
               _snowman = _snowmanxxx;
            } else if (_snowmanxxxx instanceof bky) {
               _snowmanx = (bky)_snowmanxxxx;
            }
         }
      }

      bmb _snowmanxxx = bzs.b(_snowmanx.d());
      if (_snowman.n()) {
         _snowmanxxx.c(_snowman.o().g());
      }

      return _snowmanxxx;
   }

   @Override
   public boolean a(int var1, int var2) {
      return _snowman * _snowman >= 2;
   }

   @Override
   public bos<?> ag_() {
      return bos.m;
   }
}
