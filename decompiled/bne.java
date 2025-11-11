public class bne extends blx {
   public bne(blx.a var1) {
      super(_snowman);
   }

   public static void a(bmb var0, aps var1, int var2) {
      md _snowman = _snowman.p();
      mj _snowmanx = _snowman.d("Effects", 9);
      md _snowmanxx = new md();
      _snowmanxx.a("EffectId", (byte)aps.a(_snowman));
      _snowmanxx.b("EffectDuration", _snowman);
      _snowmanx.add(_snowmanxx);
      _snowman.a("Effects", _snowmanx);
   }

   @Override
   public bmb a(bmb var1, brx var2, aqm var3) {
      bmb _snowman = super.a(_snowman, _snowman, _snowman);
      md _snowmanx = _snowman.o();
      if (_snowmanx != null && _snowmanx.c("Effects", 9)) {
         mj _snowmanxx = _snowmanx.d("Effects", 10);

         for (int _snowmanxxx = 0; _snowmanxxx < _snowmanxx.size(); _snowmanxxx++) {
            int _snowmanxxxx = 160;
            md _snowmanxxxxx = _snowmanxx.a(_snowmanxxx);
            if (_snowmanxxxxx.c("EffectDuration", 3)) {
               _snowmanxxxx = _snowmanxxxxx.h("EffectDuration");
            }

            aps _snowmanxxxxxx = aps.a(_snowmanxxxxx.f("EffectId"));
            if (_snowmanxxxxxx != null) {
               _snowman.c(new apu(_snowmanxxxxxx, _snowmanxxxx));
            }
         }
      }

      return _snowman instanceof bfw && ((bfw)_snowman).bC.d ? _snowman : new bmb(bmd.kQ);
   }
}
