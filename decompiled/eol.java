import java.net.InetAddress;
import java.net.UnknownHostException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class eol {
   private static final Logger a = LogManager.getLogger();
   private final dot b;
   private volatile boolean c;
   private nd d;

   public eol(dot var1) {
      this.b = _snowman;
   }

   public void a(final dgq var1, final String var2, final int var3) {
      final djz _snowman = djz.C();
      _snowman.d(true);
      eoj.a(ekx.a("mco.connect.success"));
      (new Thread("Realms-connect-task") {
         @Override
         public void run() {
            InetAddress _snowman = null;

            try {
               _snowman = InetAddress.getByName(_snowman);
               if (eol.this.c) {
                  return;
               }

               eol.this.d = nd.a(_snowman, _snowman, _snowman.k.f());
               if (eol.this.c) {
                  return;
               }

               eol.this.d.a(new dws(eol.this.d, _snowman, eol.this.b, var0 -> {
               }));
               if (eol.this.c) {
                  return;
               }

               eol.this.d.a(new tv(_snowman, _snowman, ne.d));
               if (eol.this.c) {
                  return;
               }

               eol.this.d.a(new ug(_snowman.J().e()));
               _snowman.a(_snowman.d(_snowman));
            } catch (UnknownHostException var5) {
               _snowman.P().b();
               if (eol.this.c) {
                  return;
               }

               eol.a.error("Couldn't connect to world", var5);
               eoi _snowmanx = new eoi(eol.this.b, nq.i, new of("disconnect.genericReason", "Unknown host '" + _snowman + "'"));
               _snowman.execute(() -> _snowman.a(_snowman));
            } catch (Exception var6) {
               _snowman.P().b();
               if (eol.this.c) {
                  return;
               }

               eol.a.error("Couldn't connect to world", var6);
               String _snowmanx = var6.toString();
               if (_snowman != null) {
                  String _snowmanxx = _snowman + ":" + _snowman;
                  _snowmanx = _snowmanx.replaceAll(_snowmanxx, "");
               }

               eoi _snowmanxx = new eoi(eol.this.b, nq.i, new of("disconnect.genericReason", _snowmanx));
               _snowman.execute(() -> _snowman.a(_snowman));
            }
         }
      }).start();
   }

   public void a() {
      this.c = true;
      if (this.d != null && this.d.h()) {
         this.d.a(new of("disconnect.genericReason"));
         this.d.m();
      }
   }

   public void b() {
      if (this.d != null) {
         if (this.d.h()) {
            this.d.a();
         } else {
            this.d.m();
         }
      }
   }
}
