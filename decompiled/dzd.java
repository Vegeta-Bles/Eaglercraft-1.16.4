public class dzd extends dye {
   private final aqa a;
   private int b;
   private final int B;
   private final hf C;

   public dzd(dwt var1, aqa var2, hf var3) {
      this(_snowman, _snowman, _snowman, 3);
   }

   public dzd(dwt var1, aqa var2, hf var3, int var4) {
      this(_snowman, _snowman, _snowman, _snowman, _snowman.cC());
   }

   private dzd(dwt var1, aqa var2, hf var3, int var4, dcn var5) {
      super(_snowman, _snowman.cD(), _snowman.e(0.5), _snowman.cH(), _snowman.b, _snowman.c, _snowman.d);
      this.a = _snowman;
      this.B = _snowman;
      this.C = _snowman;
      this.a();
   }

   @Override
   public void a() {
      for (int _snowman = 0; _snowman < 16; _snowman++) {
         double _snowmanx = (double)(this.r.nextFloat() * 2.0F - 1.0F);
         double _snowmanxx = (double)(this.r.nextFloat() * 2.0F - 1.0F);
         double _snowmanxxx = (double)(this.r.nextFloat() * 2.0F - 1.0F);
         if (!(_snowmanx * _snowmanx + _snowmanxx * _snowmanxx + _snowmanxxx * _snowmanxxx > 1.0)) {
            double _snowmanxxxx = this.a.c(_snowmanx / 4.0);
            double _snowmanxxxxx = this.a.e(0.5 + _snowmanxx / 4.0);
            double _snowmanxxxxxx = this.a.f(_snowmanxxx / 4.0);
            this.c.a(this.C, false, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanx, _snowmanxx + 0.2, _snowmanxxx);
         }
      }

      this.b++;
      if (this.b >= this.B) {
         this.j();
      }
   }
}
