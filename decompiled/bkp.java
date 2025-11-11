public class bkp extends blx {
   public bkp(blx.a var1) {
      super(_snowman);
   }

   @Override
   public bmb a(bmb var1, brx var2, aqm var3) {
      bmb _snowman = super.a(_snowman, _snowman, _snowman);
      if (!_snowman.v) {
         double _snowmanx = _snowman.cD();
         double _snowmanxx = _snowman.cE();
         double _snowmanxxx = _snowman.cH();

         for (int _snowmanxxxx = 0; _snowmanxxxx < 16; _snowmanxxxx++) {
            double _snowmanxxxxx = _snowman.cD() + (_snowman.cY().nextDouble() - 0.5) * 16.0;
            double _snowmanxxxxxx = afm.a(_snowman.cE() + (double)(_snowman.cY().nextInt(16) - 8), 0.0, (double)(_snowman.ae() - 1));
            double _snowmanxxxxxxx = _snowman.cH() + (_snowman.cY().nextDouble() - 0.5) * 16.0;
            if (_snowman.br()) {
               _snowman.l();
            }

            if (_snowman.a(_snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx, true)) {
               adp _snowmanxxxxxxxx = _snowman instanceof bah ? adq.ez : adq.bO;
               _snowman.a(null, _snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxxxxxx, adr.h, 1.0F, 1.0F);
               _snowman.a(_snowmanxxxxxxxx, 1.0F, 1.0F);
               break;
            }
         }

         if (_snowman instanceof bfw) {
            ((bfw)_snowman).eT().a(this, 20);
         }
      }

      return _snowman;
   }
}
