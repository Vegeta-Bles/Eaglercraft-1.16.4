/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonElement
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.data.client;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.block.Block;
import net.minecraft.data.DataCache;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.model.BlockStateModelGenerator;
import net.minecraft.data.client.model.BlockStateSupplier;
import net.minecraft.data.client.model.ModelIds;
import net.minecraft.data.client.model.SimpleModelSupplier;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BlockStateDefinitionProvider
implements DataProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private final DataGenerator generator;

    public BlockStateDefinitionProvider(DataGenerator generator) {
        this.generator = generator;
    }

    @Override
    public void run(DataCache cache) {
        Path path = this.generator.getOutput();
        HashMap _snowman2 = Maps.newHashMap();
        Consumer<BlockStateSupplier> _snowman3 = blockStateSupplier -> {
            Block block = blockStateSupplier.getBlock();
            BlockStateSupplier _snowman2 = _snowman2.put(block, blockStateSupplier);
            if (_snowman2 != null) {
                throw new IllegalStateException("Duplicate blockstate definition for " + block);
            }
        };
        HashMap _snowman4 = Maps.newHashMap();
        HashSet _snowman5 = Sets.newHashSet();
        BiConsumer<Identifier, Supplier<JsonElement>> _snowman6 = (identifier, supplier) -> {
            _snowman = _snowman4.put(identifier, supplier);
            if (_snowman != null) {
                throw new IllegalStateException("Duplicate model definition for " + identifier);
            }
        };
        Consumer<Item> _snowman7 = _snowman5::add;
        new BlockStateModelGenerator(_snowman3, _snowman6, _snowman7).register();
        new ItemModelGenerator(_snowman6).register();
        List _snowman8 = Registry.BLOCK.stream().filter(block -> !_snowman2.containsKey(block)).collect(Collectors.toList());
        if (!_snowman8.isEmpty()) {
            throw new IllegalStateException("Missing blockstate definitions for: " + _snowman8);
        }
        Registry.BLOCK.forEach(block -> {
            Item item = Item.BLOCK_ITEMS.get(block);
            if (item != null) {
                if (_snowman5.contains(item)) {
                    return;
                }
                Identifier identifier = ModelIds.getItemModelId(item);
                if (!_snowman4.containsKey(identifier)) {
                    _snowman4.put(identifier, new SimpleModelSupplier(ModelIds.getBlockModelId(block)));
                }
            }
        });
        this.writeJsons(cache, path, _snowman2, BlockStateDefinitionProvider::getBlockStateJsonPath);
        this.writeJsons(cache, path, _snowman4, BlockStateDefinitionProvider::getModelJsonPath);
    }

    private <T> void writeJsons(DataCache cache, Path root, Map<T, ? extends Supplier<JsonElement>> jsons, BiFunction<Path, T, Path> locator) {
        jsons.forEach((object, supplier) -> {
            Path path2 = (Path)locator.apply(root, object);
            try {
                DataProvider.writeToPath(GSON, cache, (JsonElement)supplier.get(), path2);
            }
            catch (Exception _snowman2) {
                LOGGER.error("Couldn't save {}", (Object)path2, (Object)_snowman2);
            }
        });
    }

    private static Path getBlockStateJsonPath(Path root, Block block) {
        Identifier identifier = Registry.BLOCK.getId(block);
        return root.resolve("assets/" + identifier.getNamespace() + "/blockstates/" + identifier.getPath() + ".json");
    }

    private static Path getModelJsonPath(Path root, Identifier id) {
        return root.resolve("assets/" + id.getNamespace() + "/models/" + id.getPath() + ".json");
    }

    @Override
    public String getName() {
        return "Block State Definitions";
    }
}

