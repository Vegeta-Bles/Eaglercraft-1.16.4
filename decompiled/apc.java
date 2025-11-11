import com.google.common.collect.Maps;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Timer;
import java.util.UUID;

public class apc {
   private final Map<String, Object> a = Maps.newHashMap();
   private final Map<String, Object> b = Maps.newHashMap();
   private final String c = UUID.randomUUID().toString();
   private final URL d;
   private final apd e;
   private final Timer f = new Timer("Snooper Timer", true);
   private final Object g = new Object();
   private final long h;
   private boolean i;

   public apc(String var1, apd var2, long var3) {
      try {
         this.d = new URL("http://snoop.minecraft.net/" + _snowman + "?version=" + 2);
      } catch (MalformedURLException var6) {
         throw new IllegalArgumentException();
      }

      this.e = _snowman;
      this.h = _snowman;
   }

   public void a() {
      if (!this.i) {
      }
   }

   public void b() {
      this.b("memory_total", Runtime.getRuntime().totalMemory());
      this.b("memory_max", Runtime.getRuntime().maxMemory());
      this.b("memory_free", Runtime.getRuntime().freeMemory());
      this.b("cpu_cores", Runtime.getRuntime().availableProcessors());
      this.e.a(this);
   }

   public void a(String var1, Object var2) {
      synchronized (this.g) {
         this.b.put(_snowman, _snowman);
      }
   }

   public void b(String var1, Object var2) {
      synchronized (this.g) {
         this.a.put(_snowman, _snowman);
      }
   }

   public boolean d() {
      return this.i;
   }

   public void e() {
      this.f.cancel();
   }

   public String f() {
      return this.c;
   }

   public long g() {
      return this.h;
   }
}
