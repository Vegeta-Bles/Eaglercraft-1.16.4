public class enz implements eof {
   private static final nr a = new of("tutorial.craft_planks.title");
   private static final nr b = new of("tutorial.craft_planks.description");
   private final eoe c;
   private dms d;
   private int e;

   public enz(eoe var1) {
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
               if (_snowman.bm.a(aeg.c)) {
                  this.c.a(eog.f);
                  return;
               }

               if (a(_snowman, aeg.c)) {
                  this.c.a(eog.f);
                  return;
               }
            }
         }

         if (this.e >= 1200 && this.d == null) {
            this.d = new dms(dms.a.e, a, b, false);
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
   public void a(bmb var1) {
      blx _snowman = _snowman.b();
      if (aeg.c.a(_snowman)) {
         this.c.a(eog.f);
      }
   }

   public static boolean a(dzm var0, ael<blx> var1) {
      for (blx _snowman : _snowman.b()) {
         if (_snowman.D().a(aea.b.b(_snowman)) > 0) {
            return true;
         }
      }

      return false;
   }
}
