import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.nio.file.Path;

public class hx implements hm {
   private static final Gson b = new GsonBuilder().setPrettyPrinting().create();
   private final hl c;

   public hx(hl var1) {
      this.c = _snowman;
   }

   @Override
   public void a(hn var1) throws IOException {
      JsonObject _snowman = new JsonObject();
      gm.f.c().forEach(var1x -> _snowman.add(var1x.toString(), a((gm<?>)gm.f.a(var1x))));
      Path _snowmanx = this.c.b().resolve("reports/registries.json");
      hm.a(b, _snowman, _snowman, _snowmanx);
   }

   private static <T> JsonElement a(gm<T> var0) {
      JsonObject _snowman = new JsonObject();
      if (_snowman instanceof gb) {
         vk _snowmanx = ((gb)_snowman).a();
         _snowman.addProperty("default", _snowmanx.toString());
      }

      int _snowmanx = gm.f.a(_snowman);
      _snowman.addProperty("protocol_id", _snowmanx);
      JsonObject _snowmanxx = new JsonObject();

      for (vk _snowmanxxx : _snowman.c()) {
         T _snowmanxxxx = _snowman.a(_snowmanxxx);
         int _snowmanxxxxx = _snowman.a(_snowmanxxxx);
         JsonObject _snowmanxxxxxx = new JsonObject();
         _snowmanxxxxxx.addProperty("protocol_id", _snowmanxxxxx);
         _snowmanxx.add(_snowmanxxx.toString(), _snowmanxxxxxx);
      }

      _snowman.add("entries", _snowmanxx);
      return _snowman;
   }

   @Override
   public String a() {
      return "Registry Dump";
   }
}
