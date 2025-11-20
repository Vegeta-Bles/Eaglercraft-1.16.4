/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Multimap
 *  com.google.common.collect.Sets
 *  com.google.common.collect.Sets$SetView
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.mojang.datafixers.util.Pair
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
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
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.data.DataCache;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.server.BarterLootTableGenerator;
import net.minecraft.data.server.BlockLootTableGenerator;
import net.minecraft.data.server.ChestLootTableGenerator;
import net.minecraft.data.server.EntityLootTableGenerator;
import net.minecraft.data.server.FishingLootTableGenerator;
import net.minecraft.data.server.GiftLootTableGenerator;
import net.minecraft.loot.LootManager;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTableReporter;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.context.LootContextType;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LootTablesProvider
implements DataProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private final DataGenerator root;
    private final List<Pair<Supplier<Consumer<BiConsumer<Identifier, LootTable.Builder>>>, LootContextType>> lootTypeGenerators = ImmutableList.of((Object)Pair.of(FishingLootTableGenerator::new, (Object)LootContextTypes.FISHING), (Object)Pair.of(ChestLootTableGenerator::new, (Object)LootContextTypes.CHEST), (Object)Pair.of(EntityLootTableGenerator::new, (Object)LootContextTypes.ENTITY), (Object)Pair.of(BlockLootTableGenerator::new, (Object)LootContextTypes.BLOCK), (Object)Pair.of(BarterLootTableGenerator::new, (Object)LootContextTypes.BARTER), (Object)Pair.of(GiftLootTableGenerator::new, (Object)LootContextTypes.GIFT));

    public LootTablesProvider(DataGenerator dataGenerator) {
        this.root = dataGenerator;
    }

    @Override
    public void run(DataCache cache) {
        Path path = this.root.getOutput();
        HashMap _snowman2 = Maps.newHashMap();
        this.lootTypeGenerators.forEach(pair -> ((Consumer)((Supplier)pair.getFirst()).get()).accept((identifier, builder) -> {
            if (_snowman2.put(identifier, builder.type((LootContextType)pair.getSecond()).build()) != null) {
                throw new IllegalStateException("Duplicate loot table " + identifier);
            }
        }));
        LootTableReporter _snowman3 = new LootTableReporter(LootContextTypes.GENERIC, identifier -> null, _snowman2::get);
        Sets.SetView _snowman4 = Sets.difference(LootTables.getAll(), _snowman2.keySet());
        for (Identifier identifier2 : _snowman4) {
            _snowman3.report("Missing built-in table: " + identifier2);
        }
        _snowman2.forEach((identifier, lootTable) -> LootManager.validate(_snowman3, identifier, lootTable));
        Multimap<String, String> multimap = _snowman3.getMessages();
        if (!multimap.isEmpty()) {
            multimap.forEach((string, string2) -> LOGGER.warn("Found validation problem in " + string + ": " + string2));
            throw new IllegalStateException("Failed to validate loot tables, see logs");
        }
        _snowman2.forEach((identifier, lootTable) -> {
            Path path2 = LootTablesProvider.getOutput(path, identifier);
            try {
                DataProvider.writeToPath(GSON, cache, LootManager.toJson(lootTable), path2);
            }
            catch (IOException _snowman2) {
                LOGGER.error("Couldn't save loot table {}", (Object)path2, (Object)_snowman2);
            }
        });
    }

    private static Path getOutput(Path rootOutput, Identifier lootTableId) {
        return rootOutput.resolve("data/" + lootTableId.getNamespace() + "/loot_tables/" + lootTableId.getPath() + ".json");
    }

    @Override
    public String getName() {
        return "LootTables";
    }
}

