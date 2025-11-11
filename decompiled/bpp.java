public class bpp extends bps {
   private static final String[] d = new String[]{"all", "undead", "arthropods"};
   private static final int[] e = new int[]{1, 5, 5};
   private static final int[] f = new int[]{11, 8, 8};
   private static final int[] g = new int[]{20, 20, 20};
   public final int a;

   public bpp(bps.a var1, int var2, aqf... var3) {
      super(_snowman, bpt.f, _snowman);
      this.a = _snowman;
   }

   @Override
   public int a(int var1) {
      return e[this.a] + (_snowman - 1) * f[this.a];
   }

   @Override
   public int b(int var1) {
      return this.a(_snowman) + g[this.a];
   }

   @Override
   public int a() {
      return 5;
   }

   @Override
   public float a(int var1, aqq var2) {
      if (this.a == 0) {
         return 1.0F + (float)Math.max(0, _snowman - 1) * 0.5F;
      } else if (this.a == 1 && _snowman == aqq.b) {
         return (float)_snowman * 2.5F;
      } else {
         return this.a == 2 && _snowman == aqq.c ? (float)_snowman * 2.5F : 0.0F;
      }
   }

   @Override
   public boolean a(bps var1) {
      return !(_snowman instanceof bpp);
   }

   @Override
   public boolean a(bmb var1) {
      return _snowman.b() instanceof bkd ? true : super.a(_snowman);
   }

   @Override
   public void a(aqm var1, aqa var2, int var3) {
      if (_snowman instanceof aqm) {
         aqm _snowman = (aqm)_snowman;
         if (this.a == 2 && _snowman.dC() == aqq.c) {
            int _snowmanx = 20 + _snowman.cY().nextInt(10 * _snowman);
            _snowman.c(new apu(apw.b, _snowmanx, 3));
         }
      }
   }
}
