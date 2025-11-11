public class bqf extends bps {
   public final bqf.a a;

   public bqf(bps.a var1, bqf.a var2, aqf... var3) {
      super(_snowman, _snowman == bqf.a.c ? bpt.b : bpt.a, _snowman);
      this.a = _snowman;
   }

   @Override
   public int a(int var1) {
      return this.a.b() + (_snowman - 1) * this.a.c();
   }

   @Override
   public int b(int var1) {
      return this.a(_snowman) + this.a.c();
   }

   @Override
   public int a() {
      return 4;
   }

   @Override
   public int a(int var1, apk var2) {
      if (_snowman.h()) {
         return 0;
      } else if (this.a == bqf.a.a) {
         return _snowman;
      } else if (this.a == bqf.a.b && _snowman.p()) {
         return _snowman * 2;
      } else if (this.a == bqf.a.c && _snowman == apk.k) {
         return _snowman * 3;
      } else if (this.a == bqf.a.d && _snowman.d()) {
         return _snowman * 2;
      } else {
         return this.a == bqf.a.e && _snowman.b() ? _snowman * 2 : 0;
      }
   }

   @Override
   public boolean a(bps var1) {
      if (_snowman instanceof bqf) {
         bqf _snowman = (bqf)_snowman;
         return this.a == _snowman.a ? false : this.a == bqf.a.c || _snowman.a == bqf.a.c;
      } else {
         return super.a(_snowman);
      }
   }

   public static int a(aqm var0, int var1) {
      int _snowman = bpu.a(bpw.b, _snowman);
      if (_snowman > 0) {
         _snowman -= afm.d((float)_snowman * (float)_snowman * 0.15F);
      }

      return _snowman;
   }

   public static double a(aqm var0, double var1) {
      int _snowman = bpu.a(bpw.d, _snowman);
      if (_snowman > 0) {
         _snowman -= (double)afm.c(_snowman * (double)((float)_snowman * 0.15F));
      }

      return _snowman;
   }

   public static enum a {
      a("all", 1, 11),
      b("fire", 10, 8),
      c("fall", 5, 6),
      d("explosion", 5, 8),
      e("projectile", 3, 6);

      private final String f;
      private final int g;
      private final int h;

      private a(String var3, int var4, int var5) {
         this.f = _snowman;
         this.g = _snowman;
         this.h = _snowman;
      }

      public int b() {
         return this.g;
      }

      public int c() {
         return this.h;
      }
   }
}
