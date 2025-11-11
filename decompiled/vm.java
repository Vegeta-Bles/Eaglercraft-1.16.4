import java.io.PrintStream;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class vm {
   public static final PrintStream a = System.out;
   private static boolean b;
   private static final Logger c = LogManager.getLogger();

   public static void a() {
      if (!b) {
         b = true;
         if (gm.f.c().isEmpty()) {
            throw new IllegalStateException("Unable to load registries");
         } else {
            bws.c();
            bvk.c();
            if (aqe.a(aqe.bc) == null) {
               throw new IllegalStateException("Failed loading EntityTypes");
            } else {
               bnu.a();
               fe.a();
               gw.c();
               fk.a();
               aek.b();
               d();
            }
         }
      }
   }

   private static <T> void a(Iterable<T> var0, Function<T, String> var1, Set<String> var2) {
      ly _snowman = ly.a();
      _snowman.forEach(var3x -> {
         String _snowmanx = _snowman.apply((T)var3x);
         if (!_snowman.b(_snowmanx)) {
            _snowman.add(_snowmanx);
         }
      });
   }

   private static void a(final Set<String> var0) {
      final ly _snowman = ly.a();
      brt.a(new brt.c() {
         @Override
         public <T extends brt.g<T>> void a(brt.e<T> var1x, brt.f<T> var2) {
            if (!_snowman.b(_snowman.b())) {
               _snowman.add(_snowman.a());
            }
         }
      });
   }

   public static Set<String> b() {
      Set<String> _snowman = new TreeSet<>();
      a(gm.af, arg::c, _snowman);
      a(gm.S, aqe::f, _snowman);
      a(gm.P, aps::c, _snowman);
      a(gm.T, blx::a, _snowman);
      a(gm.R, bps::g, _snowman);
      a(gm.Q, buo::i, _snowman);
      a(gm.Y, var0x -> "stat." + var0x.toString().replace(':', '.'), _snowman);
      a(_snowman);
      return _snowman;
   }

   public static void c() {
      if (!b) {
         throw new IllegalArgumentException("Not bootstrapped");
      } else {
         if (w.d) {
            b().forEach(var0 -> c.error("Missing translations: " + var0));
            dc.b();
         }

         arm.a();
      }
   }

   private static void d() {
      if (c.isDebugEnabled()) {
         System.setErr(new vq("STDERR", System.err));
         System.setOut(new vq("STDOUT", a));
      } else {
         System.setErr(new vs("STDERR", System.err));
         System.setOut(new vs("STDOUT", a));
      }
   }

   public static void a(String var0) {
      a.println(_snowman);
   }
}
