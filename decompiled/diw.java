public class diw extends dja {
   private final dgq c;
   private final dhs d;

   public diw(dgq var1, dhs var2) {
      this.c = _snowman;
      this.d = _snowman;
   }

   @Override
   public void run() {
      this.b(new of("mco.configure.world.closing"));
      dgb _snowman = dgb.a();

      for (int _snowmanx = 0; _snowmanx < 25; _snowmanx++) {
         if (this.c()) {
            return;
         }

         try {
            boolean _snowmanxx = _snowman.f(this.c.a);
            if (_snowmanxx) {
               this.d.a();
               this.c.e = dgq.b.a;
               a(this.d);
               break;
            }
         } catch (dhj var4) {
            if (this.c()) {
               return;
            }

            a(var4.e);
         } catch (Exception var5) {
            if (this.c()) {
               return;
            }

            a.error("Failed to close server", var5);
            this.a("Failed to close the server");
         }
      }
   }
}
