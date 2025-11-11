public class bok extends boj {
   private static final bon a = bon.a(bmd.mb);
   private static final bon b = bon.a(bmd.kU);
   private static final bon c = bon.a(bmd.pp);

   public bok(vk var1) {
      super(_snowman);
   }

   public boolean a(bio var1, brx var2) {
      boolean _snowman = false;
      int _snowmanx = 0;

      for (int _snowmanxx = 0; _snowmanxx < _snowman.Z_(); _snowmanxx++) {
         bmb _snowmanxxx = _snowman.a(_snowmanxx);
         if (!_snowmanxxx.a()) {
            if (a.a(_snowmanxxx)) {
               if (_snowman) {
                  return false;
               }

               _snowman = true;
            } else if (b.a(_snowmanxxx)) {
               if (++_snowmanx > 3) {
                  return false;
               }
            } else if (!c.a(_snowmanxxx)) {
               return false;
            }
         }
      }

      return _snowman && _snowmanx >= 1;
   }

   public bmb a(bio var1) {
      bmb _snowman = new bmb(bmd.po, 3);
      md _snowmanx = _snowman.a("Fireworks");
      mj _snowmanxx = new mj();
      int _snowmanxxx = 0;

      for (int _snowmanxxxx = 0; _snowmanxxxx < _snowman.Z_(); _snowmanxxxx++) {
         bmb _snowmanxxxxx = _snowman.a(_snowmanxxxx);
         if (!_snowmanxxxxx.a()) {
            if (b.a(_snowmanxxxxx)) {
               _snowmanxxx++;
            } else if (c.a(_snowmanxxxxx)) {
               md _snowmanxxxxxx = _snowmanxxxxx.b("Explosion");
               if (_snowmanxxxxxx != null) {
                  _snowmanxx.add(_snowmanxxxxxx);
               }
            }
         }
      }

      _snowmanx.a("Flight", (byte)_snowmanxxx);
      if (!_snowmanxx.isEmpty()) {
         _snowmanx.a("Explosions", _snowmanxx);
      }

      return _snowman;
   }

   @Override
   public boolean a(int var1, int var2) {
      return _snowman * _snowman >= 2;
   }

   @Override
   public bmb c() {
      return new bmb(bmd.po);
   }

   @Override
   public bos<?> ag_() {
      return bos.g;
   }
}
