import javax.annotation.Nullable;

public class djc extends dja {
   private final String c;
   private final dhe d;
   private final int e;
   private final boolean f;
   private final long g;
   private nr h = new of("mco.reset.world.resetting.screen.title");
   private final Runnable i;

   public djc(@Nullable String var1, @Nullable dhe var2, int var3, boolean var4, long var5, @Nullable nr var7, Runnable var8) {
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
      this.g = _snowman;
      if (_snowman != null) {
         this.h = _snowman;
      }

      this.i = _snowman;
   }

   @Override
   public void run() {
      dgb _snowman = dgb.a();
      this.b(this.h);
      int _snowmanx = 0;

      while (_snowmanx < 25) {
         try {
            if (this.c()) {
               return;
            }

            if (this.d != null) {
               _snowman.g(this.g, this.d.a);
            } else {
               _snowman.a(this.g, this.c, this.e, this.f);
            }

            if (this.c()) {
               return;
            }

            this.i.run();
            return;
         } catch (dhj var4) {
            if (this.c()) {
               return;
            }

            a(var4.e);
            _snowmanx++;
         } catch (Exception var5) {
            if (this.c()) {
               return;
            }

            a.error("Couldn't reset world");
            this.a(var5.toString());
            return;
         }
      }
   }
}
