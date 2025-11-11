public class djd extends dja {
   private final dgg c;
   private final long d;
   private final dhs e;

   public djd(dgg var1, long var2, dhs var4) {
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
   }

   @Override
   public void run() {
      this.b(new of("mco.backup.restoring"));
      dgb _snowman = dgb.a();
      int _snowmanx = 0;

      while (_snowmanx < 25) {
         try {
            if (this.c()) {
               return;
            }

            _snowman.c(this.d, this.c.a);
            a(1);
            if (this.c()) {
               return;
            }

            a(this.e.c());
            return;
         } catch (dhj var4) {
            if (this.c()) {
               return;
            }

            a(var4.e);
            _snowmanx++;
         } catch (dhi var5) {
            if (this.c()) {
               return;
            }

            a.error("Couldn't restore backup", var5);
            a(new dhw(var5, this.e));
            return;
         } catch (Exception var6) {
            if (this.c()) {
               return;
            }

            a.error("Couldn't restore backup", var6);
            this.a(var6.getLocalizedMessage());
            return;
         }
      }
   }
}
