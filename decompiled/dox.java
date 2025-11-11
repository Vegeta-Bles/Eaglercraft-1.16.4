public class dox extends dol {
   public dox(dot var1, dkd var2) {
      super(_snowman, _snowman, new of("options.sounds.title"));
   }

   @Override
   protected void b() {
      int _snowman = 0;
      this.a(new dme(this.i, this.k / 2 - 155 + _snowman % 2 * 160, this.l / 6 - 12 + 24 * (_snowman >> 1), adr.a, 310));
      _snowman += 2;

      for (adr _snowmanx : adr.values()) {
         if (_snowmanx != adr.a) {
            this.a(new dme(this.i, this.k / 2 - 155 + _snowman % 2 * 160, this.l / 6 - 12 + 24 * (_snowman >> 1), _snowmanx, 150));
            _snowman++;
         }
      }

      this.a(new dlw(this.k / 2 - 75, this.l / 6 - 12 + 24 * (++_snowman >> 1), 150, 20, dkc.R, dkc.R.c(this.b), var1x -> {
         dkc.R.a(this.i.k);
         var1x.a(dkc.R.c(this.i.k));
         this.i.k.b();
      }));
      this.a(new dlj(this.k / 2 - 100, this.l / 6 + 168, 200, 20, nq.c, var1x -> this.i.a(this.a)));
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
      a(_snowman, this.o, this.d, this.k / 2, 15, 16777215);
      super.a(_snowman, _snowman, _snowman, _snowman);
   }
}
