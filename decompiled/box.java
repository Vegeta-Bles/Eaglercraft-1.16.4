public class box extends boj {
   public box(vk var1) {
      super(_snowman);
   }

   public boolean a(bio var1, brx var2) {
      bmb _snowman = bmb.b;
      bmb _snowmanx = bmb.b;

      for (int _snowmanxx = 0; _snowmanxx < _snowman.Z_(); _snowmanxx++) {
         bmb _snowmanxxx = _snowman.a(_snowmanxx);
         if (!_snowmanxxx.a()) {
            if (_snowmanxxx.b() instanceof bke) {
               if (!_snowmanx.a()) {
                  return false;
               }

               _snowmanx = _snowmanxxx;
            } else {
               if (_snowmanxxx.b() != bmd.qn) {
                  return false;
               }

               if (!_snowman.a()) {
                  return false;
               }

               if (_snowmanxxx.b("BlockEntityTag") != null) {
                  return false;
               }

               _snowman = _snowmanxxx;
            }
         }
      }

      return !_snowman.a() && !_snowmanx.a();
   }

   public bmb a(bio var1) {
      bmb _snowman = bmb.b;
      bmb _snowmanx = bmb.b;

      for (int _snowmanxx = 0; _snowmanxx < _snowman.Z_(); _snowmanxx++) {
         bmb _snowmanxxx = _snowman.a(_snowmanxx);
         if (!_snowmanxxx.a()) {
            if (_snowmanxxx.b() instanceof bke) {
               _snowman = _snowmanxxx;
            } else if (_snowmanxxx.b() == bmd.qn) {
               _snowmanx = _snowmanxxx.i();
            }
         }
      }

      if (_snowmanx.a()) {
         return _snowmanx;
      } else {
         md _snowmanxxx = _snowman.b("BlockEntityTag");
         md _snowmanxxxx = _snowmanxxx == null ? new md() : _snowmanxxx.g();
         _snowmanxxxx.b("Base", ((bke)_snowman.b()).b().b());
         _snowmanx.a("BlockEntityTag", _snowmanxxxx);
         return _snowmanx;
      }
   }

   @Override
   public boolean a(int var1, int var2) {
      return _snowman * _snowman >= 2;
   }

   @Override
   public bos<?> ag_() {
      return bos.l;
   }
}
