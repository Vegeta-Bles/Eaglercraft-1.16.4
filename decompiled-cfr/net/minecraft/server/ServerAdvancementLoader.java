/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.server;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementManager;
import net.minecraft.advancement.AdvancementPositioner;
import net.minecraft.loot.condition.LootConditionManager;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.profiler.Profiler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerAdvancementLoader
extends JsonDataLoader {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new GsonBuilder().create();
    private AdvancementManager manager = new AdvancementManager();
    private final LootConditionManager conditionManager;

    public ServerAdvancementLoader(LootConditionManager conditionManager) {
        super(GSON, "advancements");
        this.conditionManager = conditionManager;
    }

    @Override
    protected void apply(Map<Identifier, JsonElement> map, ResourceManager resourceManager, Profiler profiler) {
        HashMap hashMap = Maps.newHashMap();
        map.forEach((identifier, jsonElement) -> {
            try {
                JsonObject jsonObject = JsonHelper.asObject(jsonElement, "advancement");
                Advancement.Task _snowman2 = Advancement.Task.fromJson(jsonObject, new AdvancementEntityPredicateDeserializer((Identifier)identifier, this.conditionManager));
                hashMap.put(identifier, _snowman2);
            }
            catch (JsonParseException | IllegalArgumentException throwable) {
                LOGGER.error("Parsing error loading custom advancement {}: {}", identifier, (Object)throwable.getMessage());
            }
        });
        AdvancementManager _snowman2 = new AdvancementManager();
        _snowman2.load(hashMap);
        for (Advancement advancement : _snowman2.getRoots()) {
            if (advancement.getDisplay() == null) continue;
            AdvancementPositioner.arrangeForTree(advancement);
        }
        this.manager = _snowman2;
    }

    @Nullable
    public Advancement get(Identifier id) {
        return this.manager.get(id);
    }

    public Collection<Advancement> getAdvancements() {
        return this.manager.getAdvancements();
    }
}

