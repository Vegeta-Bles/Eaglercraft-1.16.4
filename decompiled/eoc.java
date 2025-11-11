public class eoc implements eof {
   private static final nr a = new of("tutorial.open_inventory.title");
   private static final nr b = new of("tutorial.open_inventory.description", eoe.a("inventory"));
   private final eoe c;
   private dms d;
   private int e;

   public eoc(eoe var1) {
      this.c = _snowman;
   }

   @Override
   public void a() {
      this.e++;
      if (this.c.f() != bru.b) {
         this.c.a(eog.f);
      } else {
         if (this.e >= 600 && this.d == null) {
            this.d = new dms(dms.a.d, a, b, false);
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
   public void c() {
      this.c.a(eog.e);
   }
}
