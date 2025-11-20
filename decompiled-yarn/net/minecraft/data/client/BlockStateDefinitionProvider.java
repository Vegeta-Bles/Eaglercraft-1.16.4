package net.minecraft.data.client;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.block.Block;
import net.minecraft.data.DataCache;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.client.model.BlockStateModelGenerator;
import net.minecraft.data.client.model.BlockStateSupplier;
import net.minecraft.data.client.model.ModelIds;
import net.minecraft.data.client.model.SimpleModelSupplier;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BlockStateDefinitionProvider implements DataProvider {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
   private final DataGenerator generator;

   public BlockStateDefinitionProvider(DataGenerator generator) {
      this.generator = generator;
   }

   @Override
   public void run(DataCache cache) {
      Path _snowman = this.generator.getOutput();
      Map<Block, BlockStateSupplier> _snowmanx = Maps.newHashMap();
      Consumer<BlockStateSupplier> _snowmanxx = _snowmanxxx -> {
         Block _snowmanxxxx = _snowmanxxx.getBlock();
         BlockStateSupplier _snowmanxx = _snowman.put(_snowmanxxxx, _snowmanxxx);
         if (_snowmanxx != null) {
            throw new IllegalStateException("Duplicate blockstate definition for " + _snowmanxxxx);
         }
      };
      Map<Identifier, Supplier<JsonElement>> _snowmanxxx = Maps.newHashMap();
      Set<Item> _snowmanxxxx = Sets.newHashSet();
      BiConsumer<Identifier, Supplier<JsonElement>> _snowmanxxxxx = (_snowmanxxxxxx, _snowmanxxxxxxx) -> {
         Supplier<JsonElement> _snowmanxxxxxxxx = _snowman.put(_snowmanxxxxxx, _snowmanxxxxxxx);
         if (_snowmanxxxxxxxx != null) {
            throw new IllegalStateException("Duplicate model definition for " + _snowmanxxxxxx);
         }
      };
      Consumer<Item> _snowmanxxxxxx = _snowmanxxxx::add;
      new BlockStateModelGenerator(_snowmanxx, _snowmanxxxxx, _snowmanxxxxxx).register();
      new ItemModelGenerator(_snowmanxxxxx).register();
      List<Block> _snowmanxxxxxxx = Registry.BLOCK.stream().filter(_snowmanxxxxxxxx -> !_snowman.containsKey(_snowmanxxxxxxxx)).collect(Collectors.toList());
      if (!_snowmanxxxxxxx.isEmpty()) {
         throw new IllegalStateException("Missing blockstate definitions for: " + _snowmanxxxxxxx);
      } else {
         Registry.BLOCK.forEach(_snowmanxxxxxxxx -> {
            Item _snowmanxxxxxxxxx = Item.BLOCK_ITEMS.get(_snowmanxxxxxxxx);
            if (_snowmanxxxxxxxxx != null) {
               if (_snowman.contains(_snowmanxxxxxxxxx)) {
                  return;
               }

               Identifier _snowmanx = ModelIds.getItemModelId(_snowmanxxxxxxxxx);
               if (!_snowman.containsKey(_snowmanx)) {
                  _snowman.put(_snowmanx, new SimpleModelSupplier(ModelIds.getBlockModelId(_snowmanxxxxxxxx)));
               }
            }
         });
         this.writeJsons(cache, _snowman, _snowmanx, BlockStateDefinitionProvider::getBlockStateJsonPath);
         this.writeJsons(cache, _snowman, _snowmanxxx, BlockStateDefinitionProvider::getModelJsonPath);
      }
   }

   private <T> void writeJsons(DataCache cache, Path root, Map<T, ? extends Supplier<JsonElement>> jsons, BiFunction<Path, T, Path> locator) {
      jsons.forEach((_snowmanxxx, _snowmanxxxx) -> {
         Path _snowmanxxxxx = locator.apply(root, (T)_snowmanxxx);

         try {
            DataProvider.writeToPath(GSON, cache, _snowmanxxxx.get(), _snowmanxxxxx);
         } catch (Exception var7) {
            LOGGER.error("Couldn't save {}", _snowmanxxxxx, var7);
         }
      });
   }

   private static Path getBlockStateJsonPath(Path root, Block block) {
      Identifier _snowman = Registry.BLOCK.getId(block);
      return root.resolve("assets/" + _snowman.getNamespace() + "/blockstates/" + _snowman.getPath() + ".json");
   }

   private static Path getModelJsonPath(Path root, Identifier id) {
      return root.resolve("assets/" + id.getNamespace() + "/models/" + id.getPath() + ".json");
   }

   @Override
   public String getName() {
      return "Block State Definitions";
   }
}
