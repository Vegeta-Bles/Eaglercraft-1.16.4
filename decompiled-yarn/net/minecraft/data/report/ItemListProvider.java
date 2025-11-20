package net.minecraft.data.report;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.nio.file.Path;
import net.minecraft.data.DataCache;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.DefaultedRegistry;
import net.minecraft.util.registry.Registry;

public class ItemListProvider implements DataProvider {
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
   private final DataGenerator root;

   public ItemListProvider(DataGenerator _snowman) {
      this.root = _snowman;
   }

   @Override
   public void run(DataCache cache) throws IOException {
      JsonObject _snowman = new JsonObject();
      Registry.REGISTRIES.getIds().forEach(_snowmanx -> _snowman.add(_snowmanx.toString(), toJson((Registry<?>)Registry.REGISTRIES.get(_snowmanx))));
      Path _snowmanx = this.root.getOutput().resolve("reports/registries.json");
      DataProvider.writeToPath(GSON, cache, _snowman, _snowmanx);
   }

   private static <T> JsonElement toJson(Registry<T> _snowman) {
      JsonObject _snowmanx = new JsonObject();
      if (_snowman instanceof DefaultedRegistry) {
         Identifier _snowmanxx = ((DefaultedRegistry)_snowman).getDefaultId();
         _snowmanx.addProperty("default", _snowmanxx.toString());
      }

      int _snowmanxx = Registry.REGISTRIES.getRawId(_snowman);
      _snowmanx.addProperty("protocol_id", _snowmanxx);
      JsonObject _snowmanxxx = new JsonObject();

      for (Identifier _snowmanxxxx : _snowman.getIds()) {
         T _snowmanxxxxx = _snowman.get(_snowmanxxxx);
         int _snowmanxxxxxx = _snowman.getRawId(_snowmanxxxxx);
         JsonObject _snowmanxxxxxxx = new JsonObject();
         _snowmanxxxxxxx.addProperty("protocol_id", _snowmanxxxxxx);
         _snowmanxxx.add(_snowmanxxxx.toString(), _snowmanxxxxxxx);
      }

      _snowmanx.add("entries", _snowmanxxx);
      return _snowmanx;
   }

   @Override
   public String getName() {
      return "Registry Dump";
   }
}
