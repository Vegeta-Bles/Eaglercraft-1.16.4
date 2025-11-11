public class dow extends dol {
   public dow(dot var1, dkd var2) {
      super(_snowman, _snowman, new of("options.skinCustomisation.title"));
   }

   @Override
   protected void b() {
      int _snowman = 0;

      for (bfx _snowmanx : bfx.values()) {
         this.a((dlj)(new dlj(this.k / 2 - 155 + _snowman % 2 * 160, this.l / 6 + 24 * (_snowman >> 1), 150, 20, this.a(_snowmanx), var2 -> {
            this.b.a(_snowman);
            var2.a(this.a(_snowman));
         })));
         _snowman++;
      }

      this.a(new dlw(this.k / 2 - 155 + _snowman % 2 * 160, this.l / 6 + 24 * (_snowman >> 1), 150, 20, dkc.z, dkc.z.c(this.b), var1x -> {
         dkc.z.a(this.b, 1);
         this.b.b();
         var1x.a(dkc.z.c(this.b));
         this.b.c();
      }));
      if (++_snowman % 2 == 1) {
         _snowman++;
      }

      this.a((dlj)(new dlj(this.k / 2 - 100, this.l / 6 + 24 * (_snowman >> 1), 200, 20, nq.c, var1x -> this.i.a(this.a))));
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
      a(_snowman, this.o, this.d, this.k / 2, 20, 16777215);
      super.a(_snowman, _snowman, _snowman, _snowman);
   }

   private nr a(bfx var1) {
      return nq.a(_snowman.d(), this.b.d().contains(_snowman));
   }
}
