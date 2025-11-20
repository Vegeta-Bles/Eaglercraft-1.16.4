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

   public BlockListProvider(DataGenerator _snowman) {
      this.root = _snowman;
   }

   @Override
   public void run(DataCache cache) throws IOException {
      JsonObject _snowman = new JsonObject();

      for (Block _snowmanx : Registry.BLOCK) {
         Identifier _snowmanxx = Registry.BLOCK.getId(_snowmanx);
         JsonObject _snowmanxxx = new JsonObject();
         StateManager<Block, BlockState> _snowmanxxxx = _snowmanx.getStateManager();
         if (!_snowmanxxxx.getProperties().isEmpty()) {
            JsonObject _snowmanxxxxx = new JsonObject();

            for (Property<?> _snowmanxxxxxx : _snowmanxxxx.getProperties()) {
               JsonArray _snowmanxxxxxxx = new JsonArray();

               for (Comparable<?> _snowmanxxxxxxxx : _snowmanxxxxxx.getValues()) {
                  _snowmanxxxxxxx.add(Util.getValueAsString(_snowmanxxxxxx, _snowmanxxxxxxxx));
               }

               _snowmanxxxxx.add(_snowmanxxxxxx.getName(), _snowmanxxxxxxx);
            }

            _snowmanxxx.add("properties", _snowmanxxxxx);
         }

         JsonArray _snowmanxxxxx = new JsonArray();
         UnmodifiableIterator var17 = _snowmanxxxx.getStates().iterator();

         while (var17.hasNext()) {
            BlockState _snowmanxxxxxx = (BlockState)var17.next();
            JsonObject _snowmanxxxxxxx = new JsonObject();
            JsonObject _snowmanxxxxxxxx = new JsonObject();

            for (Property<?> _snowmanxxxxxxxxx : _snowmanxxxx.getProperties()) {
               _snowmanxxxxxxxx.addProperty(_snowmanxxxxxxxxx.getName(), Util.getValueAsString(_snowmanxxxxxxxxx, _snowmanxxxxxx.get(_snowmanxxxxxxxxx)));
            }

            if (_snowmanxxxxxxxx.size() > 0) {
               _snowmanxxxxxxx.add("properties", _snowmanxxxxxxxx);
            }

            _snowmanxxxxxxx.addProperty("id", Block.getRawIdFromState(_snowmanxxxxxx));
            if (_snowmanxxxxxx == _snowmanx.getDefaultState()) {
               _snowmanxxxxxxx.addProperty("default", true);
            }

            _snowmanxxxxx.add(_snowmanxxxxxxx);
         }

         _snowmanxxx.add("states", _snowmanxxxxx);
         _snowman.add(_snowmanxx.toString(), _snowmanxxx);
      }

      Path _snowmanx = this.root.getOutput().resolve("reports/blocks.json");
      DataProvider.writeToPath(GSON, cache, _snowman, _snowmanx);
   }

   @Override
   public String getName() {
      return "Block List";
   }
}
