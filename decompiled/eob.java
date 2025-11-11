public class eob implements eof {
   private static final nr a = new of("tutorial.move.title", eoe.a("forward"), eoe.a("left"), eoe.a("back"), eoe.a("right"));
   private static final nr b = new of("tutorial.move.description", eoe.a("jump"));
   private static final nr c = new of("tutorial.look.title");
   private static final nr d = new of("tutorial.look.description");
   private final eoe e;
   private dms f;
   private dms g;
   private int h;
   private int i;
   private int j;
   private boolean k;
   private boolean l;
   private int m = -1;
   private int n = -1;

   public eob(eoe var1) {
      this.e = _snowman;
   }

   @Override
   public void a() {
      this.h++;
      if (this.k) {
         this.i++;
         this.k = false;
      }

      if (this.l) {
         this.j++;
         this.l = false;
      }

      if (this.m == -1 && this.i > 40) {
         if (this.f != null) {
            this.f.b();
            this.f = null;
         }

         this.m = this.h;
      }

      if (this.n == -1 && this.j > 40) {
         if (this.g != null) {
            this.g.b();
            this.g = null;
         }

         this.n = this.h;
      }

      if (this.m != -1 && this.n != -1) {
         if (this.e.f() == bru.b) {
            this.e.a(eog.b);
         } else {
            this.e.a(eog.f);
         }
      }

      if (this.f != null) {
         this.f.a((float)this.i / 40.0F);
      }

      if (this.g != null) {
         this.g.a((float)this.j / 40.0F);
      }

      if (this.h >= 100) {
         if (this.m == -1 && this.f == null) {
            this.f = new dms(dms.a.a, a, b, true);
            this.e.e().an().a(this.f);
         } else if (this.m != -1 && this.h - this.m >= 20 && this.n == -1 && this.g == null) {
            this.g = new dms(dms.a.b, c, d, true);
            this.e.e().an().a(this.g);
         }
      }
   }

   @Override
   public void b() {
      if (this.f != null) {
         this.f.b();
         this.f = null;
      }

      if (this.g != null) {
         this.g.b();
         this.g = null;
      }
   }

   @Override
   public void a(dzk var1) {
      if (_snowman.c || _snowman.d || _snowman.e || _snowman.f || _snowman.g) {
         this.k = true;
      }
   }

   @Override
   public void a(double var1, double var3) {
      if (Math.abs(_snowman) > 0.01 || Math.abs(_snowman) > 0.01) {
         this.l = true;
      }
   }
}
