public class bji extends apa {
   private ccv a;

   public bji() {
      super(27);
   }

   public void a(ccv var1) {
      this.a = _snowman;
   }

   @Override
   public void a(mj var1) {
      for (int _snowman = 0; _snowman < this.Z_(); _snowman++) {
         this.a(_snowman, bmb.b);
      }

      for (int _snowman = 0; _snowman < _snowman.size(); _snowman++) {
         md _snowmanx = _snowman.a(_snowman);
         int _snowmanxx = _snowmanx.f("Slot") & 255;
         if (_snowmanxx >= 0 && _snowmanxx < this.Z_()) {
            this.a(_snowmanxx, bmb.a(_snowmanx));
         }
      }
   }

   @Override
   public mj g() {
      mj _snowman = new mj();

      for (int _snowmanx = 0; _snowmanx < this.Z_(); _snowmanx++) {
         bmb _snowmanxx = this.a(_snowmanx);
         if (!_snowmanxx.a()) {
            md _snowmanxxx = new md();
            _snowmanxxx.a("Slot", (byte)_snowmanx);
            _snowmanxx.b(_snowmanxxx);
            _snowman.add(_snowmanxxx);
         }
      }

      return _snowman;
   }

   @Override
   public boolean a(bfw var1) {
      return this.a != null && !this.a.a(_snowman) ? false : super.a(_snowman);
   }

   @Override
   public void c_(bfw var1) {
      if (this.a != null) {
         this.a.d();
      }

      super.c_(_snowman);
   }

   @Override
   public void b_(bfw var1) {
      if (this.a != null) {
         this.a.f();
      }

      super.b_(_snowman);
      this.a = null;
   }
}
