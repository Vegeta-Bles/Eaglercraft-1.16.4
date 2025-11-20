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

   public ItemListProvider(DataGenerator arg) {
      this.root = arg;
   }

   @Override
   public void run(DataCache cache) throws IOException {
      JsonObject jsonObject = new JsonObject();
      Registry.REGISTRIES.getIds().forEach(arg -> jsonObject.add(arg.toString(), toJson((Registry<?>)Registry.REGISTRIES.get(arg))));
      Path path = this.root.getOutput().resolve("reports/registries.json");
      DataProvider.writeToPath(GSON, cache, jsonObject, path);
   }

   private static <T> JsonElement toJson(Registry<T> arg) {
      JsonObject jsonObject = new JsonObject();
      if (arg instanceof DefaultedRegistry) {
         Identifier lv = ((DefaultedRegistry)arg).getDefaultId();
         jsonObject.addProperty("default", lv.toString());
      }

      int i = Registry.REGISTRIES.getRawId(arg);
      jsonObject.addProperty("protocol_id", i);
      JsonObject jsonObject2 = new JsonObject();

      for (Identifier lv2 : arg.getIds()) {
         T object = arg.get(lv2);
         int j = arg.getRawId(object);
         JsonObject jsonObject3 = new JsonObject();
         jsonObject3.addProperty("protocol_id", j);
         jsonObject2.add(lv2.toString(), jsonObject3);
      }

      jsonObject.add("entries", jsonObject2);
      return jsonObject;
   }

   @Override
   public String getName() {
      return "Registry Dump";
   }
}
