import java.io.File;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class cxs {
   private static final Logger a = LogManager.getLogger();
   private final String b;
   private boolean c;

   public cxs(String var1) {
      this.b = _snowman;
   }

   public abstract void a(md var1);

   public abstract md b(md var1);

   public void b() {
      this.a(true);
   }

   public void a(boolean var1) {
      this.c = _snowman;
   }

   public boolean c() {
      return this.c;
   }

   public String d() {
      return this.b;
   }

   public void a(File var1) {
      if (this.c()) {
         md _snowman = new md();
         _snowman.a("data", this.b(new md()));
         _snowman.b("DataVersion", w.a().getWorldVersion());

         try {
            mn.a(_snowman, _snowman);
         } catch (IOException var4) {
            a.error("Could not save data {}", this, var4);
         }

         this.a(false);
      }
   }
}
