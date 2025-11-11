import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;

public class diz extends dja {
   private final dgq c;
   private final dot d;
   private final dfw e;
   private final ReentrantLock f;

   public diz(dfw var1, dot var2, dgq var3, ReentrantLock var4) {
      this.d = _snowman;
      this.e = _snowman;
      this.c = _snowman;
      this.f = _snowman;
   }

   @Override
   public void run() {
      this.b(new of("mco.connect.connecting"));
      dgb _snowman = dgb.a();
      boolean _snowmanx = false;
      boolean _snowmanxx = false;
      int _snowmanxxx = 5;
      dgr _snowmanxxxx = null;
      boolean _snowmanxxxxx = false;
      boolean _snowmanxxxxxx = false;

      for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < 40 && !this.c(); _snowmanxxxxxxx++) {
         try {
            _snowmanxxxx = _snowman.b(this.c.a);
            _snowmanx = true;
         } catch (dhj var11) {
            _snowmanxxx = var11.e;
         } catch (dhi var12) {
            if (var12.c == 6002) {
               _snowmanxxxxx = true;
            } else if (var12.c == 6006) {
               _snowmanxxxxxx = true;
            } else {
               _snowmanxx = true;
               this.a(var12.toString());
               a.error("Couldn't connect to world", var12);
            }
            break;
         } catch (Exception var13) {
            _snowmanxx = true;
            a.error("Couldn't connect to world", var13);
            this.a(var13.getLocalizedMessage());
            break;
         }

         if (_snowmanx) {
            break;
         }

         this.b(_snowmanxxx);
      }

      if (_snowmanxxxxx) {
         a(new dim(this.d, this.e, this.c));
      } else if (_snowmanxxxxxx) {
         if (this.c.g.equals(djz.C().J().b())) {
            a(new dhq(this.d, this.e, this.c.a, this.c.m == dgq.c.b));
         } else {
            a(new dhw(new of("mco.brokenworld.nonowner.title"), new of("mco.brokenworld.nonowner.error"), this.d));
         }
      } else if (!this.c() && !_snowmanxx) {
         if (_snowmanx) {
            dgr _snowmanxxxxxxx = _snowmanxxxx;
            if (_snowmanxxxxxxx.b != null && _snowmanxxxxxxx.c != null) {
               nr _snowmanxxxxxxxx = new of("mco.configure.world.resourcepack.question.line1");
               nr _snowmanxxxxxxxxx = new of("mco.configure.world.resourcepack.question.line2");
               a(new dhy(var2x -> {
                  try {
                     if (var2x) {
                        Function<Throwable, Void> _snowmanxxxxxxxxxx = var1x -> {
                           djz.C().P().b();
                           a.error(var1x);
                           a(new dhw(new oe("Failed to download resource pack!"), this.d));
                           return null;
                        };

                        try {
                           djz.C().P().a(_snowman.b, _snowman.c).thenRun(() -> this.a(new dhz(this.d, new dix(this.d, this.c, _snowman)))).exceptionally(_snowmanxxxxxxxxxx);
                        } catch (Exception var8x) {
                           _snowmanxxxxxxxxxx.apply(var8x);
                        }
                     } else {
                        a(this.d);
                     }
                  } finally {
                     if (this.f != null && this.f.isHeldByCurrentThread()) {
                        this.f.unlock();
                     }
                  }
               }, dhy.a.b, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, true));
            } else {
               this.a(new dhz(this.d, new dix(this.d, this.c, _snowmanxxxxxxx)));
            }
         } else {
            this.a(new of("mco.errorMessage.connectionFailure"));
         }
      }
   }

   private void b(int var1) {
      try {
         Thread.sleep((long)(_snowman * 1000));
      } catch (InterruptedException var3) {
         a.warn(var3.getLocalizedMessage());
      }
   }
}
