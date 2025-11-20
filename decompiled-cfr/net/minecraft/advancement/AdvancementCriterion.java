/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonSyntaxException
 *  javax.annotation.Nullable
 */
package net.minecraft.advancement;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.advancement.criterion.Criterion;
import net.minecraft.advancement.criterion.CriterionConditions;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

public class AdvancementCriterion {
    private final CriterionConditions conditions;

    public AdvancementCriterion(CriterionConditions conditions) {
        this.conditions = conditions;
    }

    public AdvancementCriterion() {
        this.conditions = null;
    }

    public void toPacket(PacketByteBuf buf) {
    }

    public static AdvancementCriterion fromJson(JsonObject obj, AdvancementEntityPredicateDeserializer predicateDeserializer) {
        Identifier identifier = new Identifier(JsonHelper.getString(obj, "trigger"));
        Criterion _snowman2 = Criteria.getById(identifier);
        if (_snowman2 == null) {
            throw new JsonSyntaxException("Invalid criterion trigger: " + identifier);
        }
        Object _snowman3 = _snowman2.conditionsFromJson(JsonHelper.getObject(obj, "conditions", new JsonObject()), predicateDeserializer);
        return new AdvancementCriterion((CriterionConditions)_snowman3);
    }

    public static AdvancementCriterion fromPacket(PacketByteBuf buf) {
        return new AdvancementCriterion();
    }

    public static Map<String, AdvancementCriterion> criteriaFromJson(JsonObject obj, AdvancementEntityPredicateDeserializer predicateDeserializer) {
        HashMap hashMap = Maps.newHashMap();
        for (Map.Entry entry : obj.entrySet()) {
            hashMap.put(entry.getKey(), AdvancementCriterion.fromJson(JsonHelper.asObject((JsonElement)entry.getValue(), "criterion"), predicateDeserializer));
        }
        return hashMap;
    }

    public static Map<String, AdvancementCriterion> criteriaFromPacket(PacketByteBuf buf) {
        HashMap hashMap = Maps.newHashMap();
        int _snowman2 = buf.readVarInt();
        for (int i = 0; i < _snowman2; ++i) {
            hashMap.put(buf.readString(Short.MAX_VALUE), AdvancementCriterion.fromPacket(buf));
        }
        return hashMap;
    }

    public static void criteriaToPacket(Map<String, AdvancementCriterion> criteria, PacketByteBuf buf) {
        buf.writeVarInt(criteria.size());
        for (Map.Entry<String, AdvancementCriterion> entry : criteria.entrySet()) {
            buf.writeString(entry.getKey());
            entry.getValue().toPacket(buf);
        }
    }

    @Nullable
    public CriterionConditions getConditions() {
        return this.conditions;
    }

    public JsonElement toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("trigger", this.conditions.getId().toString());
        _snowman = this.conditions.toJson(AdvancementEntityPredicateSerializer.INSTANCE);
        if (_snowman.size() != 0) {
            jsonObject.add("conditions", (JsonElement)_snowman);
        }
        return jsonObject;
    }
}

