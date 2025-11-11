public interface dea {
   float getAdvance();

   default float a(boolean var1) {
      return this.getAdvance() + (_snowman ? this.b() : 0.0F);
   }

   default float a() {
      return 0.0F;
   }

   default float b() {
      return 1.0F;
   }

   default float c() {
      return 1.0F;
   }
}
