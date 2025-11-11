import com.google.common.collect.Lists;
import java.io.File;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dxa {
   private static final Logger a = LogManager.getLogger();
   private final djz b;
   private final List<dwz> c = Lists.newArrayList();

   public dxa(djz var1) {
      this.b = _snowman;
      this.a();
   }

   public void a() {
      try {
         this.c.clear();
         md _snowman = mn.b(new File(this.b.n, "servers.dat"));
         if (_snowman == null) {
            return;
         }

         mj _snowmanx = _snowman.d("servers", 10);

         for (int _snowmanxx = 0; _snowmanxx < _snowmanx.size(); _snowmanxx++) {
            this.c.add(dwz.a(_snowmanx.a(_snowmanxx)));
         }
      } catch (Exception var4) {
         a.error("Couldn't load server list", var4);
      }
   }

   public void b() {
      try {
         mj _snowman = new mj();

         for (dwz _snowmanx : this.c) {
            _snowman.add(_snowmanx.a());
         }

         md _snowmanx = new md();
         _snowmanx.a("servers", _snowman);
         File _snowmanxx = File.createTempFile("servers", ".dat", this.b.n);
         mn.b(_snowmanx, _snowmanxx);
         File _snowmanxxx = new File(this.b.n, "servers.dat_old");
         File _snowmanxxxx = new File(this.b.n, "servers.dat");
         x.a(_snowmanxxxx, _snowmanxx, _snowmanxxx);
      } catch (Exception var6) {
         a.error("Couldn't save server list", var6);
      }
   }

   public dwz a(int var1) {
      return this.c.get(_snowman);
   }

   public void a(dwz var1) {
      this.c.remove(_snowman);
   }

   public void b(dwz var1) {
      this.c.add(_snowman);
   }

   public int c() {
      return this.c.size();
   }

   public void a(int var1, int var2) {
      dwz _snowman = this.a(_snowman);
      this.c.set(_snowman, this.a(_snowman));
      this.c.set(_snowman, _snowman);
      this.b();
   }

   public void a(int var1, dwz var2) {
      this.c.set(_snowman, _snowman);
   }

   public static void c(dwz var0) {
      dxa _snowman = new dxa(djz.C());
      _snowman.a();

      for (int _snowmanx = 0; _snowmanx < _snowman.c(); _snowmanx++) {
         dwz _snowmanxx = _snowman.a(_snowmanx);
         if (_snowmanxx.a.equals(_snowman.a) && _snowmanxx.b.equals(_snowman.b)) {
            _snowman.a(_snowmanx, _snowman);
            break;
         }
      }

      _snowman.b();
   }
}
