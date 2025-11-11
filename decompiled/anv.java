import java.io.File;
import java.util.List;

public interface anv {
   List<any> a(String var1);

   boolean a(File var1);

   long a();

   int b();

   long c();

   int d();

   default long g() {
      return this.c() - this.a();
   }

   default int f() {
      return this.d() - this.b();
   }

   static String b(String var0) {
      return _snowman.replace('\u001e', '.');
   }
}
