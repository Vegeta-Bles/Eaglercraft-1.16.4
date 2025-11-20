package net.minecraft.data.report;

import com.google.common.collect.UnmodifiableIterator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.nio.file.Path;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.data.DataCache;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;

public class BlockListProvider implements DataProvider {
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
   private final DataGenerator root;

   public BlockListProvider(DataGenerator arg) {
      this.root = arg;
   }

   @Override
   public void run(DataCache cache) throws IOException {
      JsonObject jsonObject = new JsonObject();

      for (Block lv : Registry.BLOCK) {
         Identifier lv2 = Registry.BLOCK.getId(lv);
         JsonObject jsonObject2 = new JsonObject();
         StateManager<Block, BlockState> lv3 = lv.getStateManager();
         if (!lv3.getProperties().isEmpty()) {
            JsonObject jsonObject3 = new JsonObject();

            for (Property<?> lv4 : lv3.getProperties()) {
               JsonArray jsonArray = new JsonArray();

               for (Comparable<?> comparable : lv4.getValues()) {
                  jsonArray.add(Util.getValueAsString(lv4, comparable));
               }

               jsonObject3.add(lv4.getName(), jsonArray);
            }

            jsonObject2.add("properties", jsonObject3);
         }

         JsonArray jsonArray2 = new JsonArray();
         UnmodifiableIterator var17 = lv3.getStates().iterator();

         while (var17.hasNext()) {
            BlockState lv5 = (BlockState)var17.next();
            JsonObject jsonObject4 = new JsonObject();
            JsonObject jsonObject5 = new JsonObject();

            for (Property<?> lv6 : lv3.getProperties()) {
               jsonObject5.addProperty(lv6.getName(), Util.getValueAsString(lv6, lv5.get(lv6)));
            }

            if (jsonObject5.size() > 0) {
               jsonObject4.add("properties", jsonObject5);
            }

            jsonObject4.addProperty("id", Block.getRawIdFromState(lv5));
            if (lv5 == lv.getDefaultState()) {
               jsonObject4.addProperty("default", true);
            }

            jsonArray2.add(jsonObject4);
         }

         jsonObject2.add("states", jsonArray2);
         jsonObject.add(lv2.toString(), jsonObject2);
      }

      Path path = this.root.getOutput().resolve("reports/blocks.json");
      DataProvider.writeToPath(GSON, cache, jsonObject, path);
   }

   @Override
   public String getName() {
      return "Block List";
   }
}
