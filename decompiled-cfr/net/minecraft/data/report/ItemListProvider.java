/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 */
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

public class ItemListProvider
implements DataProvider {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final DataGenerator root;

    public ItemListProvider(DataGenerator dataGenerator) {
        this.root = dataGenerator;
    }

    @Override
    public void run(DataCache cache) throws IOException {
        JsonObject jsonObject = new JsonObject();
        Registry.REGISTRIES.getIds().forEach(identifier -> jsonObject.add(identifier.toString(), ItemListProvider.toJson(Registry.REGISTRIES.get((Identifier)identifier))));
        Path _snowman2 = this.root.getOutput().resolve("reports/registries.json");
        DataProvider.writeToPath(GSON, cache, (JsonElement)jsonObject, _snowman2);
    }

    private static <T> JsonElement toJson(Registry<T> registry2) {
        Registry<T> registry2;
        JsonObject jsonObject = new JsonObject();
        if (registry2 instanceof DefaultedRegistry) {
            Identifier identifier = ((DefaultedRegistry)registry2).getDefaultId();
            jsonObject.addProperty("default", identifier.toString());
        }
        int _snowman2 = Registry.REGISTRIES.getRawId(registry2);
        jsonObject.addProperty("protocol_id", (Number)_snowman2);
        JsonObject _snowman3 = new JsonObject();
        for (Identifier identifier : registry2.getIds()) {
            T t = registry2.get(identifier);
            int _snowman4 = registry2.getRawId(t);
            JsonObject _snowman5 = new JsonObject();
            _snowman5.addProperty("protocol_id", (Number)_snowman4);
            _snowman3.add(identifier.toString(), (JsonElement)_snowman5);
        }
        jsonObject.add("entries", (JsonElement)_snowman3);
        return jsonObject;
    }

    @Override
    public String getName() {
        return "Registry Dump";
    }
}

