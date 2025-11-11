public interface cfs {
   void a(cfu var1, double var2);

   void a(cfu var1, double var2, double var4, long var6);

   void a(cfu var1, double var2, double var4);

   void a(cfu var1, int var2);

   void b(cfu var1, int var2);

   void b(cfu var1, double var2);

   void c(cfu var1, double var2);

   public static class a implements cfs {
      private final cfu a;

      public a(cfu var1) {
         this.a = _snowman;
      }

      @Override
      public void a(cfu var1, double var2) {
         this.a.a(_snowman);
      }

      @Override
      public void a(cfu var1, double var2, double var4, long var6) {
         this.a.a(_snowman, _snowman, _snowman);
      }

      @Override
      public void a(cfu var1, double var2, double var4) {
         this.a.c(_snowman, _snowman);
      }

      @Override
      public void a(cfu var1, int var2) {
         this.a.b(_snowman);
      }

      @Override
      public void b(cfu var1, int var2) {
         this.a.c(_snowman);
      }

      @Override
      public void b(cfu var1, double var2) {
         this.a.c(_snowman);
      }

      @Override
      public void c(cfu var1, double var2) {
         this.a.b(_snowman);
      }
   }
}
