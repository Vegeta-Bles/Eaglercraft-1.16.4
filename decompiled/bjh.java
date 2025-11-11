public class bjh extends bjr {
   private final bjf a;
   private final bfw b;
   private int g;
   private final bqu h;

   public bjh(bfw var1, bqu var2, bjf var3, int var4, int var5, int var6) {
      super(_snowman, _snowman, _snowman, _snowman);
      this.b = _snowman;
      this.h = _snowman;
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
   protected void c(bmb var1) {
      _snowman.a(this.b.l, this.b, this.g);
      this.g = 0;
   }

   @Override
   public bmb a(bfw var1, bmb var2) {
      this.c(_snowman);
      bqv _snowman = this.a.g();
      if (_snowman != null) {
         bmb _snowmanx = this.a.a(0);
         bmb _snowmanxx = this.a.a(1);
         if (_snowman.b(_snowmanx, _snowmanxx) || _snowman.b(_snowmanxx, _snowmanx)) {
            this.h.a(_snowman);
            _snowman.a(aea.S);
            this.a.a(0, _snowmanx);
            this.a.a(1, _snowmanxx);
         }

         this.h.t(this.h.eL() + _snowman.o());
      }

      return _snowman;
   }
}
