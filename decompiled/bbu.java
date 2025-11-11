public abstract class bbu extends bbt {
   public bbu(bbr var1) {
      super(_snowman);
   }

   @Override
   public boolean a() {
      return true;
   }

   @Override
   public float a(apk var1, float var2) {
      if (_snowman.j() instanceof bga) {
         _snowman.j().f(1);
         return 0.0F;
      } else {
         return super.a(_snowman, _snowman);
      }
   }
}
