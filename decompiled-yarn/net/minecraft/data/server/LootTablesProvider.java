package net.minecraft.data.server;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.datafixers.util.Pair;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.data.DataCache;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.loot.LootManager;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTableReporter;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.context.LootContextType;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LootTablesProvider implements DataProvider {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
   private final DataGenerator root;
   private final List<Pair<Supplier<Consumer<BiConsumer<Identifier, LootTable.Builder>>>, LootContextType>> lootTypeGenerators = ImmutableList.of(
      Pair.of(FishingLootTableGenerator::new, LootContextTypes.FISHING),
      Pair.of(ChestLootTableGenerator::new, LootContextTypes.CHEST),
      Pair.of(EntityLootTableGenerator::new, LootContextTypes.ENTITY),
      Pair.of(BlockLootTableGenerator::new, LootContextTypes.BLOCK),
      Pair.of(BarterLootTableGenerator::new, LootContextTypes.BARTER),
      Pair.of(GiftLootTableGenerator::new, LootContextTypes.GIFT)
   );

   public LootTablesProvider(DataGenerator _snowman) {
      this.root = _snowman;
   }

   @Override
   public void run(DataCache cache) {
      Path _snowman = this.root.getOutput();
      Map<Identifier, LootTable> _snowmanx = Maps.newHashMap();
      this.lootTypeGenerators.forEach(_snowmanxx -> ((Consumer)((Supplier)_snowmanxx.getFirst()).get()).accept((_snowmanxxxx, _snowmanxxx) -> {
            if (_snowman.put(_snowmanxxxx, _snowmanxxx.type((LootContextType)_snowmanx.getSecond()).build()) != null) {
               throw new IllegalStateException("Duplicate loot table " + _snowmanxxxx);
            }
         }));
      LootTableReporter _snowmanxx = new LootTableReporter(LootContextTypes.GENERIC, _snowmanxxx -> null, _snowmanx::get);

      for (Identifier _snowmanxxx : Sets.difference(LootTables.getAll(), _snowmanx.keySet())) {
         _snowmanxx.report("Missing built-in table: " + _snowmanxxx);
      }

      _snowmanx.forEach((_snowmanxxx, _snowmanxxxx) -> LootManager.validate(_snowman, _snowmanxxx, _snowmanxxxx));
      Multimap<String, String> _snowmanxxx = _snowmanxx.getMessages();
      if (!_snowmanxxx.isEmpty()) {
         _snowmanxxx.forEach((_snowmanxxxx, _snowmanxxxxx) -> LOGGER.warn("Found validation problem in " + _snowmanxxxx + ": " + _snowmanxxxxx));
         throw new IllegalStateException("Failed to validate loot tables, see logs");
      } else {
         _snowmanx.forEach((_snowmanxxxx, _snowmanxxxxx) -> {
            Path _snowmanxxxxxx = getOutput(_snowman, _snowmanxxxx);

            try {
               DataProvider.writeToPath(GSON, cache, LootManager.toJson(_snowmanxxxxx), _snowmanxxxxxx);
            } catch (IOException var6) {
               LOGGER.error("Couldn't save loot table {}", _snowmanxxxxxx, var6);
            }
         });
      }
   }

   private static Path getOutput(Path rootOutput, Identifier lootTableId) {
      return rootOutput.resolve("data/" + lootTableId.getNamespace() + "/loot_tables/" + lootTableId.getPath() + ".json");
   }

   @Override
   public String getName() {
      return "LootTables";
   }
}
