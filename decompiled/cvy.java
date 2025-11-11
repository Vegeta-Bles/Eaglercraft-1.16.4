public enum cvy implements cwl {
   a;

   private cvy() {
   }

   @Override
   public int a(cvk var1, int var2, int var3) {
      ctz _snowman = _snowman.b();
      double _snowmanx = _snowman.a((double)_snowman / 8.0, (double)_snowman / 8.0, 0.0, 0.0, 0.0);
      if (_snowmanx > 0.4) {
         return 44;
      } else if (_snowmanx > 0.2) {
         return 45;
      } else if (_snowmanx < -0.4) {
         return 10;
      } else {
         return _snowmanx < -0.2 ? 46 : 0;
      }
   }
}
