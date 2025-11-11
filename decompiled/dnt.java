import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dnt extends dot {
   private static final AtomicInteger a = new AtomicInteger(0);
   private static final Logger b = LogManager.getLogger();
   private nd c;
   private boolean p;
   private final dot q;
   private nr r = new of("connect.connecting");
   private long s = -1L;

   public dnt(dot var1, djz var2, dwz var3) {
      super(dkz.a);
      this.i = _snowman;
      this.q = _snowman;
      dwy _snowman = dwy.a(_snowman.b);
      _snowman.r();
      _snowman.a(_snowman);
      this.a(_snowman.a(), _snowman.b());
   }

   public dnt(dot var1, djz var2, String var3, int var4) {
      super(dkz.a);
      this.i = _snowman;
      this.q = _snowman;
      _snowman.r();
      this.a(_snowman, _snowman);
   }

   private void a(final String var1, final int var2) {
      b.info("Connecting to {}, {}", _snowman, _snowman);
      Thread _snowman = new Thread("Server Connector #" + a.incrementAndGet()) {
         @Override
         public void run() {
            InetAddress _snowman = null;

            try {
               if (dnt.this.p) {
                  return;
               }

               _snowman = InetAddress.getByName(_snowman);
               dnt.this.c = nd.a(_snowman, _snowman, dnt.this.i.k.f());
               dnt.this.c.a(new dws(dnt.this.c, dnt.this.i, dnt.this.q, var1xx -> dnt.this.a(var1xx)));
               dnt.this.c.a(new tv(_snowman, _snowman, ne.d));
               dnt.this.c.a(new ug(dnt.this.i.J().e()));
            } catch (UnknownHostException var4) {
               if (dnt.this.p) {
                  return;
               }

               dnt.b.error("Couldn't connect to server", var4);
               dnt.this.i.execute(() -> dnt.this.i.a(new doa(dnt.this.q, nq.i, new of("disconnect.genericReason", "Unknown host"))));
            } catch (Exception var5) {
               if (dnt.this.p) {
                  return;
               }

               dnt.b.error("Couldn't connect to server", var5);
               String _snowmanx = _snowman == null ? var5.toString() : var5.toString().replaceAll(_snowman + ":" + _snowman, "");
               dnt.this.i.execute(() -> dnt.this.i.a(new doa(dnt.this.q, nq.i, new of("disconnect.genericReason", _snowman))));
            }
         }
      };
      _snowman.setUncaughtExceptionHandler(new o(b));
      _snowman.start();
   }

   private void a(nr var1) {
      this.r = _snowman;
   }

   @Override
   public void d() {
      if (this.c != null) {
         if (this.c.h()) {
            this.c.a();
         } else {
            this.c.m();
         }
      }
   }

   @Override
   public boolean as_() {
      return false;
   }

   @Override
   protected void b() {
      this.a((dlj)(new dlj(this.k / 2 - 100, this.l / 4 + 120 + 12, 200, 20, nq.d, var1 -> {
         this.p = true;
         if (this.c != null) {
            this.c.a(new of("connect.aborted"));
         }

         this.i.a(this.q);
      })));
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
      long _snowman = x.b();
      if (_snowman - this.s > 2000L) {
         this.s = _snowman;
         dkz.b.a(new of("narrator.joining").getString());
      }

      a(_snowman, this.o, this.r, this.k / 2, this.l / 2 - 50, 16777215);
      super.a(_snowman, _snowman, _snowman, _snowman);
   }
}
