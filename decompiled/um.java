import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;

public class um implements oj<uk> {
   private static final Gson a = new GsonBuilder()
      .registerTypeAdapter(un.c.class, new un.c.a())
      .registerTypeAdapter(un.a.class, new un.a.a())
      .registerTypeAdapter(un.class, new un.b())
      .registerTypeHierarchyAdapter(nr.class, new nr.a())
      .registerTypeHierarchyAdapter(ob.class, new ob.a())
      .registerTypeAdapterFactory(new afl())
      .create();
   private un b;

   public um() {
   }

   public um(un var1) {
      this.b = _snowman;
   }

   @Override
   public void a(nf var1) throws IOException {
      this.b = afd.a(a, _snowman.e(32767), un.class);
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.a(a.toJson(this.b));
   }

   public void a(uk var1) {
      _snowman.a(this);
   }

   public un b() {
      return this.b;
   }
}
