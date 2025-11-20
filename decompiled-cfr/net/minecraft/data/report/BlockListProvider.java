/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 */
package net.minecraft.data.report;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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

public class BlockListProvider
implements DataProvider {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final DataGenerator root;

    public BlockListProvider(DataGenerator dataGenerator) {
        this.root = dataGenerator;
    }

    @Override
    public void run(DataCache cache) throws IOException {
        JsonObject jsonObject = new JsonObject();
        for (Block block : Registry.BLOCK) {
            JsonArray jsonArray;
            JsonObject jsonObject2;
            Identifier identifier = Registry.BLOCK.getId(block);
            JsonObject _snowman2 = new JsonObject();
            StateManager<Block, BlockState> _snowman3 = block.getStateManager();
            if (!_snowman3.getProperties().isEmpty()) {
                jsonObject2 = new JsonObject();
                for (Property property : _snowman3.getProperties()) {
                    jsonArray = new JsonArray();
                    for (Comparable comparable : property.getValues()) {
                        jsonArray.add(Util.getValueAsString(property, comparable));
                    }
                    jsonObject2.add(property.getName(), (JsonElement)jsonArray);
                }
                _snowman2.add("properties", (JsonElement)jsonObject2);
            }
            jsonObject2 = new JsonArray();
            for (BlockState blockState : _snowman3.getStates()) {
                jsonArray = new JsonObject();
                JsonObject _snowman4 = new JsonObject();
                for (Property<?> property : _snowman3.getProperties()) {
                    _snowman4.addProperty(property.getName(), Util.getValueAsString(property, blockState.get(property)));
                }
                if (_snowman4.size() > 0) {
                    jsonArray.add("properties", (JsonElement)_snowman4);
                }
                jsonArray.addProperty("id", (Number)Block.getRawIdFromState(blockState));
                if (blockState == block.getDefaultState()) {
                    jsonArray.addProperty("default", Boolean.valueOf(true));
                }
                jsonObject2.add((JsonElement)jsonArray);
            }
            _snowman2.add("states", (JsonElement)jsonObject2);
            jsonObject.add(identifier.toString(), (JsonElement)_snowman2);
        }
        Path path = this.root.getOutput().resolve("reports/blocks.json");
        DataProvider.writeToPath(GSON, cache, (JsonElement)jsonObject, path);
    }

    @Override
    public String getName() {
        return "Block List";
    }
}

