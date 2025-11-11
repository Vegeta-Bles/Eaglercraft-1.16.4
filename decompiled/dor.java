import javax.annotation.Nullable;

public class dor extends dot implements afn {
   @Nullable
   private nr a;
   @Nullable
   private nr b;
   private int c;
   private boolean p;

   public dor() {
      super(dkz.a);
   }

   @Override
   public boolean as_() {
      return false;
   }

   @Override
   public void a(nr var1) {
      this.b(_snowman);
   }

   @Override
   public void b(nr var1) {
      this.a = _snowman;
      this.c(new of("progress.working"));
   }

   @Override
   public void c(nr var1) {
      this.b = _snowman;
      this.a(0);
   }

   @Override
   public void a(int var1) {
      this.c = _snowman;
   }

   @Override
   public void a() {
      this.p = true;
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      if (this.p) {
         if (!this.i.ah()) {
            this.i.a(null);
         }
      } else {
         this.a(_snowman);
         if (this.a != null) {
            a(_snowman, this.o, this.a, this.k / 2, 70, 16777215);
         }

         if (this.b != null && this.c != 0) {
            a(_snowman, this.o, new oe("").a(this.b).c(" " + this.c + "%"), this.k / 2, 90, 16777215);
         }

         super.a(_snowman, _snowman, _snowman, _snowman);
      }
   }
}
