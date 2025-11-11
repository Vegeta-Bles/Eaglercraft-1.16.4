import javax.annotation.Nullable;

public abstract class ccd extends ccj implements aon, aox, aoy {
   private aow a;
   private nr b;

   protected ccd(cck<?> var1) {
      super(_snowman);
      this.a = aow.a;
   }

   @Override
   public void a(ceh var1, md var2) {
      super.a(_snowman, _snowman);
      this.a = aow.b(_snowman);
      if (_snowman.c("CustomName", 8)) {
         this.b = nr.a.a(_snowman.l("CustomName"));
      }
   }

   @Override
   public md a(md var1) {
      super.a(_snowman);
      this.a.a(_snowman);
      if (this.b != null) {
         _snowman.a("CustomName", nr.a.a(this.b));
      }

      return _snowman;
   }

   public void a(nr var1) {
      this.b = _snowman;
   }

   @Override
   public nr R() {
      return this.b != null ? this.b : this.g();
   }

   @Override
   public nr d() {
      return this.R();
   }

   @Nullable
   @Override
   public nr T() {
      return this.b;
   }

   protected abstract nr g();

   public boolean e(bfw var1) {
      return a(_snowman, this.a, this.d());
   }

   public static boolean a(bfw var0, aow var1, nr var2) {
      if (!_snowman.a_() && !_snowman.a(_snowman.dD())) {
         _snowman.a(new of("container.isLocked", _snowman), true);
         _snowman.a(adq.bF, adr.e, 1.0F, 1.0F);
         return false;
      } else {
         return true;
      }
   }

   @Nullable
   @Override
   public bic createMenu(int var1, bfv var2, bfw var3) {
      return this.e(_snowman) ? this.a(_snowman, _snowman) : null;
   }

   protected abstract bic a(int var1, bfv var2);
}
