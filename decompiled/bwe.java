public class bwe extends bwo {
   protected static final ddh a = buo.a(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

   public bwe(ceg.c var1) {
      super(_snowman);
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      return a;
   }

   @Override
   public aou a(ceh var1, brx var2, fx var3, bfw var4, aot var5, dcj var6) {
      this.d(_snowman, _snowman, _snowman);
      return aou.a(_snowman.v);
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, bfw var4) {
      this.d(_snowman, _snowman, _snowman);
   }

   private void d(ceh var1, brx var2, fx var3) {
      for (int _snowman = 0; _snowman < 1000; _snowman++) {
         fx _snowmanx = _snowman.b(_snowman.t.nextInt(16) - _snowman.t.nextInt(16), _snowman.t.nextInt(8) - _snowman.t.nextInt(8), _snowman.t.nextInt(16) - _snowman.t.nextInt(16));
         if (_snowman.d_(_snowmanx).g()) {
            if (_snowman.v) {
               for (int _snowmanxx = 0; _snowmanxx < 128; _snowmanxx++) {
                  double _snowmanxxx = _snowman.t.nextDouble();
                  float _snowmanxxxx = (_snowman.t.nextFloat() - 0.5F) * 0.2F;
                  float _snowmanxxxxx = (_snowman.t.nextFloat() - 0.5F) * 0.2F;
                  float _snowmanxxxxxx = (_snowman.t.nextFloat() - 0.5F) * 0.2F;
                  double _snowmanxxxxxxx = afm.d(_snowmanxxx, (double)_snowmanx.u(), (double)_snowman.u()) + (_snowman.t.nextDouble() - 0.5) + 0.5;
                  double _snowmanxxxxxxxx = afm.d(_snowmanxxx, (double)_snowmanx.v(), (double)_snowman.v()) + _snowman.t.nextDouble() - 0.5;
                  double _snowmanxxxxxxxxx = afm.d(_snowmanxxx, (double)_snowmanx.w(), (double)_snowman.w()) + (_snowman.t.nextDouble() - 0.5) + 0.5;
                  _snowman.a(hh.Q, _snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, (double)_snowmanxxxx, (double)_snowmanxxxxx, (double)_snowmanxxxxxx);
               }
            } else {
               _snowman.a(_snowmanx, _snowman, 2);
               _snowman.a(_snowman, false);
            }

            return;
         }
      }
   }

   @Override
   protected int c() {
      return 5;
   }

   @Override
   public boolean a(ceh var1, brc var2, fx var3, cxe var4) {
      return false;
   }
}
