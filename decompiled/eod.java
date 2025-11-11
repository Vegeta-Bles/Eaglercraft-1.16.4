public class eod implements eof {
   private static final nr a = new of("tutorial.punch_tree.title");
   private static final nr b = new of("tutorial.punch_tree.description", eoe.a("attack"));
   private final eoe c;
   private dms d;
   private int e;
   private int f;

   public eod(eoe var1) {
      this.c = _snowman;
   }

   @Override
   public void a() {
      this.e++;
      if (this.c.f() != bru.b) {
         this.c.a(eog.f);
      } else {
         if (this.e == 1) {
            dzm _snowman = this.c.e().s;
            if (_snowman != null) {
               if (_snowman.bm.a(aeg.q)) {
                  this.c.a(eog.e);
                  return;
               }

               if (eoa.a(_snowman)) {
                  this.c.a(eog.e);
                  return;
               }
            }
         }

         if ((this.e >= 600 || this.f > 3) && this.d == null) {
            this.d = new dms(dms.a.c, a, b, true);
            this.c.e().an().a(this.d);
         }
      }
   }

   @Override
   public void b() {
      if (this.d != null) {
         this.d.b();
         this.d = null;
      }
   }

   @Override
   public void a(dwt var1, fx var2, ceh var3, float var4) {
      boolean _snowman = _snowman.a(aed.s);
      if (_snowman && _snowman > 0.0F) {
         if (this.d != null) {
            this.d.a(_snowman);
         }

         if (_snowman >= 1.0F) {
            this.c.a(eog.d);
         }
      } else if (this.d != null) {
         this.d.a(0.0F);
      } else if (_snowman) {
         this.f++;
      }
   }

   @Override
   public void a(bmb var1) {
      if (aeg.q.a(_snowman.b())) {
         this.c.a(eog.e);
      }
   }
}
