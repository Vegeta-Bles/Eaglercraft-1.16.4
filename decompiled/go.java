public class go {
   protected final float a;
   protected final float b;
   protected final float c;

   public go(float var1, float var2, float var3) {
      this.a = !Float.isInfinite(_snowman) && !Float.isNaN(_snowman) ? _snowman % 360.0F : 0.0F;
      this.b = !Float.isInfinite(_snowman) && !Float.isNaN(_snowman) ? _snowman % 360.0F : 0.0F;
      this.c = !Float.isInfinite(_snowman) && !Float.isNaN(_snowman) ? _snowman % 360.0F : 0.0F;
   }

   public go(mj var1) {
      this(_snowman.i(0), _snowman.i(1), _snowman.i(2));
   }

   public mj a() {
      mj _snowman = new mj();
      _snowman.add(mg.a(this.a));
      _snowman.add(mg.a(this.b));
      _snowman.add(mg.a(this.c));
      return _snowman;
   }

   @Override
   public boolean equals(Object var1) {
      if (!(_snowman instanceof go)) {
         return false;
      } else {
         go _snowman = (go)_snowman;
         return this.a == _snowman.a && this.b == _snowman.b && this.c == _snowman.c;
      }
   }

   public float b() {
      return this.a;
   }

   public float c() {
      return this.b;
   }

   public float d() {
      return this.c;
   }
}
