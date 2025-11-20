package net.minecraft.data.report;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.data.DataCache;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BiomeListProvider implements DataProvider {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
   private final DataGenerator dataGenerator;

   public BiomeListProvider(DataGenerator dataGenerator) {
      this.dataGenerator = dataGenerator;
   }

   @Override
   public void run(DataCache cache) {
      Path _snowman = this.dataGenerator.getOutput();

      for (Entry<RegistryKey<Biome>, Biome> _snowmanx : BuiltinRegistries.BIOME.getEntries()) {
         Path _snowmanxx = getPath(_snowman, _snowmanx.getKey().getValue());
         Biome _snowmanxxx = _snowmanx.getValue();
         Function<Supplier<Biome>, DataResult<JsonElement>> _snowmanxxxx = JsonOps.INSTANCE.withEncoder(Biome.REGISTRY_CODEC);

         try {
            Optional<JsonElement> _snowmanxxxxx = _snowmanxxxx.apply(() -> _snowman).result();
            if (_snowmanxxxxx.isPresent()) {
               DataProvider.writeToPath(GSON, cache, _snowmanxxxxx.get(), _snowmanxx);
            } else {
               LOGGER.error("Couldn't serialize biome {}", _snowmanxx);
            }
         } catch (IOException var9) {
            LOGGER.error("Couldn't save biome {}", _snowmanxx, var9);
         }
      }
   }

   private static Path getPath(Path _snowman, Identifier _snowman) {
      return _snowman.resolve("reports/biomes/" + _snowman.getPath() + ".json");
   }

   @Override
   public String getName() {
      return "Biomes";
   }
}
