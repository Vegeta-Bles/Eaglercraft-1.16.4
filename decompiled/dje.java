public class dje extends dja {
   private final long c;
   private final dhe d;
   private final dhs e;

   public dje(long var1, dhe var3, dhs var4) {
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
   }

   @Override
   public void run() {
      dgb _snowman = dgb.a();
      this.b(new of("mco.minigame.world.starting.screen.title"));

      for (int _snowmanx = 0; _snowmanx < 25; _snowmanx++) {
         try {
            if (this.c()) {
               return;
            }

            if (_snowman.d(this.c, this.d.a)) {
               a(this.e);
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

            a.error("Couldn't start mini game!");
            this.a(var5.toString());
         }
      }
   }
}
