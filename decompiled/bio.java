public class bio implements aon, bju {
   private final gj<bmb> a;
   private final int b;
   private final int c;
   private final bic d;

   public bio(bic var1, int var2, int var3) {
      this.a = gj.a(_snowman * _snowman, bmb.b);
      this.d = _snowman;
      this.b = _snowman;
      this.c = _snowman;
   }

   @Override
   public int Z_() {
      return this.a.size();
   }

   @Override
   public boolean c() {
      for (bmb _snowman : this.a) {
         if (!_snowman.a()) {
            return false;
         }
      }

      return true;
   }

   @Override
   public bmb a(int var1) {
      return _snowman >= this.Z_() ? bmb.b : this.a.get(_snowman);
   }

   @Override
   public bmb b(int var1) {
      return aoo.a(this.a, _snowman);
   }

   @Override
   public bmb a(int var1, int var2) {
      bmb _snowman = aoo.a(this.a, _snowman, _snowman);
      if (!_snowman.a()) {
         this.d.a(this);
      }

      return _snowman;
   }

   @Override
   public void a(int var1, bmb var2) {
      this.a.set(_snowman, _snowman);
      this.d.a(this);
   }

   @Override
   public void X_() {
   }

   @Override
   public boolean a(bfw var1) {
      return true;
   }

   @Override
   public void Y_() {
      this.a.clear();
   }

   public int f() {
      return this.c;
   }

   public int g() {
      return this.b;
   }

   @Override
   public void a(bfy var1) {
      for (bmb _snowman : this.a) {
         _snowman.a(_snowman);
      }
   }
}
