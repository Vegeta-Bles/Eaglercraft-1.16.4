public class atj<E extends aqm> extends arv<E> {
   private boolean b;
   private boolean c;
   private final afh d;
   private final arv<? super E> e;
   private int f;

   public atj(arv<? super E> var1, afh var2) {
      this(_snowman, false, _snowman);
   }

   public atj(arv<? super E> var1, boolean var2, afh var3) {
      super(_snowman.a);
      this.e = _snowman;
      this.b = !_snowman;
      this.d = _snowman;
   }

   @Override
   protected boolean a(aag var1, E var2) {
      if (!this.e.a(_snowman, _snowman)) {
         return false;
      } else {
         if (this.b) {
            this.a(_snowman);
            this.b = false;
         }

         if (this.f > 0) {
            this.f--;
         }

         return !this.c && this.f == 0;
      }
   }

   @Override
   protected void a(aag var1, E var2, long var3) {
      this.e.a(_snowman, _snowman, _snowman);
   }

   @Override
   protected boolean b(aag var1, E var2, long var3) {
      return this.e.b(_snowman, _snowman, _snowman);
   }

   @Override
   protected void d(aag var1, E var2, long var3) {
      this.e.d(_snowman, _snowman, _snowman);
      this.c = this.e.a() == arv.a.b;
   }

   @Override
   protected void c(aag var1, E var2, long var3) {
      this.a(_snowman);
      this.e.c(_snowman, _snowman, _snowman);
   }

   private void a(aag var1) {
      this.f = this.d.a(_snowman.t);
   }

   @Override
   protected boolean a(long var1) {
      return false;
   }

   @Override
   public String toString() {
      return "RunSometimes: " + this.e;
   }
}
