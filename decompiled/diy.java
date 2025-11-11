public class diy extends dja {
   private final long c;
   private final int d;
   private final dot e;
   private final String f;

   public diy(long var1, int var3, String var4, dot var5) {
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
   }

   @Override
   public void run() {
      this.b(new of("mco.download.preparing"));
      dgb _snowman = dgb.a();
      int _snowmanx = 0;

      while (_snowmanx < 25) {
         try {
            if (this.c()) {
               return;
            }

            dhd _snowmanxx = _snowman.b(this.c, this.d);
            a(1);
            if (this.c()) {
               return;
            }

            a(new dhv(this.e, _snowmanxx, this.f, var0 -> {
            }));
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

            a.error("Couldn't download world data");
            a(new dhw(var5, this.e));
            return;
         } catch (Exception var6) {
            if (this.c()) {
               return;
            }

            a.error("Couldn't download world data", var6);
            this.a(var6.getLocalizedMessage());
            return;
         }
      }
   }
}
