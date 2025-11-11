import javax.annotation.Nullable;

public class cdi extends ccj implements cdm {
   private final bqz a = new bqz() {
      @Override
      public void a(int var1) {
         cdi.this.d.a(cdi.this.e, bup.bP, _snowman, 0);
      }

      @Override
      public brx a() {
         return cdi.this.d;
      }

      @Override
      public fx b() {
         return cdi.this.e;
      }

      @Override
      public void a(bsm var1) {
         super.a(_snowman);
         if (this.a() != null) {
            ceh _snowman = this.a().d_(this.b());
            this.a().a(cdi.this.e, _snowman, _snowman, 4);
         }
      }
   };

   public cdi() {
      super(cck.i);
   }

   @Override
   public void a(ceh var1, md var2) {
      super.a(_snowman, _snowman);
      this.a.a(_snowman);
   }

   @Override
   public md a(md var1) {
      super.a(_snowman);
      this.a.b(_snowman);
      return _snowman;
   }

   @Override
   public void aj_() {
      this.a.c();
   }

   @Nullable
   @Override
   public ow a() {
      return new ow(this.e, 1, this.b());
   }

   @Override
   public md b() {
      md _snowman = this.a(new md());
      _snowman.r("SpawnPotentials");
      return _snowman;
   }

   @Override
   public boolean a_(int var1, int var2) {
      return this.a.b(_snowman) ? true : super.a_(_snowman, _snowman);
   }

   @Override
   public boolean t() {
      return true;
   }

   public bqz d() {
      return this.a;
   }
}
