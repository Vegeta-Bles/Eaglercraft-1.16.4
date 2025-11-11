public class bpg extends boj {
   public bpg(vk var1) {
      super(_snowman);
   }

   public boolean a(bio var1, brx var2) {
      if (_snowman.g() == 3 && _snowman.f() == 3) {
         for (int _snowman = 0; _snowman < _snowman.g(); _snowman++) {
            for (int _snowmanx = 0; _snowmanx < _snowman.f(); _snowmanx++) {
               bmb _snowmanxx = _snowman.a(_snowman + _snowmanx * _snowman.g());
               if (_snowmanxx.a()) {
                  return false;
               }

               blx _snowmanxxx = _snowmanxx.b();
               if (_snowman == 1 && _snowmanx == 1) {
                  if (_snowmanxxx != bmd.qm) {
                     return false;
                  }
               } else if (_snowmanxxx != bmd.kd) {
                  return false;
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public bmb a(bio var1) {
      bmb _snowman = _snowman.a(1 + _snowman.g());
      if (_snowman.b() != bmd.qm) {
         return bmb.b;
      } else {
         bmb _snowmanx = new bmb(bmd.ql, 8);
         bnv.a(_snowmanx, bnv.d(_snowman));
         bnv.a(_snowmanx, bnv.b(_snowman));
         return _snowmanx;
      }
   }

   @Override
   public boolean a(int var1, int var2) {
      return _snowman >= 2 && _snowman >= 2;
   }

   @Override
   public bos<?> ag_() {
      return bos.j;
   }
}
