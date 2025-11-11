public class biv extends bjr {
   private final bfw a;
   private int b;

   public biv(bfw var1, aon var2, int var3, int var4, int var5) {
      super(_snowman, _snowman, _snowman, _snowman);
      this.a = _snowman;
   }

   @Override
   public boolean a(bmb var1) {
      return false;
   }

   @Override
   public bmb a(int var1) {
      if (this.f()) {
         this.b = this.b + Math.min(_snowman, this.e().E());
      }

      return super.a(_snowman);
   }

   @Override
   public bmb a(bfw var1, bmb var2) {
      this.c(_snowman);
      super.a(_snowman, _snowman);
      return _snowman;
   }

   @Override
   protected void a(bmb var1, int var2) {
      this.b += _snowman;
      this.c(_snowman);
   }

   @Override
   protected void c(bmb var1) {
      _snowman.a(this.a.l, this.a, this.b);
      if (!this.a.l.v && this.c instanceof cbz) {
         ((cbz)this.c).d(this.a);
      }

      this.b = 0;
   }
}
