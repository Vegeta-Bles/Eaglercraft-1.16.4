public class djf extends dja {
   private final long c;
   private final int d;
   private final Runnable e;

   public djf(long var1, int var3, Runnable var4) {
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
   }

   @Override
   public void run() {
      dgb _snowman = dgb.a();
      this.b(new of("mco.minigame.world.slot.screen.title"));

      for (int _snowmanx = 0; _snowmanx < 25; _snowmanx++) {
         try {
            if (this.c()) {
               return;
            }

            if (_snowman.a(this.c, this.d)) {
               this.e.run();
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

            a.error("Couldn't switch world!");
            this.a(var5.toString());
         }
      }
   }
}
