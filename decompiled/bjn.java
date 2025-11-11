public class bjn extends bjr {
   private final bio a;
   private final bfw b;
   private int g;

   public bjn(bfw var1, bio var2, aon var3, int var4, int var5, int var6) {
      super(_snowman, _snowman, _snowman, _snowman);
      this.b = _snowman;
      this.a = _snowman;
   }

   @Override
   public boolean a(bmb var1) {
      return false;
   }

   @Override
   public bmb a(int var1) {
      if (this.f()) {
         this.g = this.g + Math.min(_snowman, this.e().E());
      }

      return super.a(_snowman);
   }

   @Override
   protected void a(bmb var1, int var2) {
      this.g += _snowman;
      this.c(_snowman);
   }

   @Override
   protected void b(int var1) {
      this.g += _snowman;
   }

   @Override
   protected void c(bmb var1) {
      if (this.g > 0) {
         _snowman.a(this.b.l, this.b, this.g);
      }

      if (this.c instanceof bjl) {
         ((bjl)this.c).b(this.b);
      }

      this.g = 0;
   }

   @Override
   public bmb a(bfw var1, bmb var2) {
      this.c(_snowman);
      gj<bmb> _snowman = _snowman.l.o().c(bot.a, this.a, _snowman.l);

      for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
         bmb _snowmanxx = this.a.a(_snowmanx);
         bmb _snowmanxxx = _snowman.get(_snowmanx);
         if (!_snowmanxx.a()) {
            this.a.a(_snowmanx, 1);
            _snowmanxx = this.a.a(_snowmanx);
         }

         if (!_snowmanxxx.a()) {
            if (_snowmanxx.a()) {
               this.a.a(_snowmanx, _snowmanxxx);
            } else if (bmb.c(_snowmanxx, _snowmanxxx) && bmb.a(_snowmanxx, _snowmanxxx)) {
               _snowmanxxx.f(_snowmanxx.E());
               this.a.a(_snowmanx, _snowmanxxx);
            } else if (!this.b.bm.e(_snowmanxxx)) {
               this.b.a(_snowmanxxx, false);
            }
         }
      }

      return _snowman;
   }
}
