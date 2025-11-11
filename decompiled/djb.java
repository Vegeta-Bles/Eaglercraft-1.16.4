public class djb extends dja {
   private final dgq c;
   private final dot d;
   private final boolean e;
   private final dfw f;

   public djb(dgq var1, dot var2, dfw var3, boolean var4) {
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
   }

   @Override
   public void run() {
      this.b(new of("mco.configure.world.opening"));
      dgb _snowman = dgb.a();

      for (int _snowmanx = 0; _snowmanx < 25; _snowmanx++) {
         if (this.c()) {
            return;
         }

         try {
            boolean _snowmanxx = _snowman.e(this.c.a);
            if (_snowmanxx) {
               if (this.d instanceof dhs) {
                  ((dhs)this.d).a();
               }

               this.c.e = dgq.b.b;
               if (this.e) {
                  this.f.a(this.c, this.d);
               } else {
                  a(this.d);
               }
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

            a.error("Failed to open server", var5);
            this.a("Failed to open the server");
         }
      }
   }
}
