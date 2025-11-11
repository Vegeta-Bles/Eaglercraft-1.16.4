import com.google.common.collect.UnmodifiableIterator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.nio.file.Path;

public class hv implements hm {
   private static final Gson b = new GsonBuilder().setPrettyPrinting().create();
   private final hl c;

   public hv(hl var1) {
      this.c = _snowman;
   }

   @Override
   public void a(hn var1) throws IOException {
      JsonObject _snowman = new JsonObject();

      for (buo _snowmanx : gm.Q) {
         vk _snowmanxx = gm.Q.b(_snowmanx);
         JsonObject _snowmanxxx = new JsonObject();
         cei<buo, ceh> _snowmanxxxx = _snowmanx.m();
         if (!_snowmanxxxx.d().isEmpty()) {
            JsonObject _snowmanxxxxx = new JsonObject();

            for (cfj<?> _snowmanxxxxxx : _snowmanxxxx.d()) {
               JsonArray _snowmanxxxxxxx = new JsonArray();

               for (Comparable<?> _snowmanxxxxxxxx : _snowmanxxxxxx.a()) {
                  _snowmanxxxxxxx.add(x.a(_snowmanxxxxxx, _snowmanxxxxxxxx));
               }

               _snowmanxxxxx.add(_snowmanxxxxxx.f(), _snowmanxxxxxxx);
            }

            _snowmanxxx.add("properties", _snowmanxxxxx);
         }

         JsonArray _snowmanxxxxx = new JsonArray();
         UnmodifiableIterator var17 = _snowmanxxxx.a().iterator();

         while (var17.hasNext()) {
            ceh _snowmanxxxxxx = (ceh)var17.next();
            JsonObject _snowmanxxxxxxx = new JsonObject();
            JsonObject _snowmanxxxxxxxx = new JsonObject();

            for (cfj<?> _snowmanxxxxxxxxx : _snowmanxxxx.d()) {
               _snowmanxxxxxxxx.addProperty(_snowmanxxxxxxxxx.f(), x.a(_snowmanxxxxxxxxx, _snowmanxxxxxx.c(_snowmanxxxxxxxxx)));
            }

            if (_snowmanxxxxxxxx.size() > 0) {
               _snowmanxxxxxxx.add("properties", _snowmanxxxxxxxx);
            }

            _snowmanxxxxxxx.addProperty("id", buo.i(_snowmanxxxxxx));
            if (_snowmanxxxxxx == _snowmanx.n()) {
               _snowmanxxxxxxx.addProperty("default", true);
            }

            _snowmanxxxxx.add(_snowmanxxxxxxx);
         }

         _snowmanxxx.add("states", _snowmanxxxxx);
         _snowman.add(_snowmanxx.toString(), _snowmanxxx);
      }

      Path _snowmanx = this.c.b().resolve("reports/blocks.json");
      hm.a(b, _snowman, _snowman, _snowmanx);
   }

   @Override
   public String a() {
      return "Block List";
   }
}
