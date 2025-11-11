public class doe extends dnq {
   public doe() {
      super("");
   }

   @Override
   protected void b() {
      super.b();
      this.a(new dlj(this.k / 2 - 100, this.l - 40, 200, 20, new of("multiplayer.stopSleeping"), var1 -> this.h()));
   }

   @Override
   public void at_() {
      this.h();
   }

   @Override
   public boolean a(int var1, int var2, int var3) {
      if (_snowman == 256) {
         this.h();
      } else if (_snowman == 257 || _snowman == 335) {
         String _snowman = this.a.b().trim();
         if (!_snowman.isEmpty()) {
            this.b_(_snowman);
         }

         this.a.a("");
         this.i.j.c().c();
         return true;
      }

      return super.a(_snowman, _snowman, _snowman);
   }

   private void h() {
      dwu _snowman = this.i.s.e;
      _snowman.a(new ta(this.i.s, ta.a.c));
   }
}
