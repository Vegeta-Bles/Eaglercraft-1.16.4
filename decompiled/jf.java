import com.google.gson.JsonObject;
import javax.annotation.Nullable;

public interface jf {
   void a(JsonObject var1);

   default JsonObject a() {
      JsonObject _snowman = new JsonObject();
      _snowman.addProperty("type", gm.ae.b(this.c()).toString());
      this.a(_snowman);
      return _snowman;
   }

   vk b();

   bos<?> c();

   @Nullable
   JsonObject d();

   @Nullable
   vk e();
}
