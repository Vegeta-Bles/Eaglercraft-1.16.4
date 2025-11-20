/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  com.google.gson.JsonSerializationContext
 *  com.google.gson.JsonSerializer
 *  javax.annotation.Nullable
 */
package net.minecraft.advancement;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.criterion.CriterionProgress;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.JsonHelper;

public class AdvancementProgress
implements Comparable<AdvancementProgress> {
    private final Map<String, CriterionProgress> criteriaProgresses = Maps.newHashMap();
    private String[][] requirements = new String[0][];

    public void init(Map<String, AdvancementCriterion> criteria, String[][] requirements) {
        Set<String> set = criteria.keySet();
        this.criteriaProgresses.entrySet().removeIf(entry -> !set.contains(entry.getKey()));
        for (String string : set) {
            if (this.criteriaProgresses.containsKey(string)) continue;
            this.criteriaProgresses.put(string, new CriterionProgress());
        }
        this.requirements = requirements;
    }

    public boolean isDone() {
        if (this.requirements.length == 0) {
            return false;
        }
        for (String[] stringArray : this.requirements) {
            boolean _snowman2 = false;
            for (String string : stringArray) {
                CriterionProgress criterionProgress = this.getCriterionProgress(string);
                if (criterionProgress == null || !criterionProgress.isObtained()) continue;
                _snowman2 = true;
                break;
            }
            if (_snowman2) continue;
            return false;
        }
        return true;
    }

    public boolean isAnyObtained() {
        for (CriterionProgress criterionProgress : this.criteriaProgresses.values()) {
            if (!criterionProgress.isObtained()) continue;
            return true;
        }
        return false;
    }

    public boolean obtain(String name) {
        CriterionProgress criterionProgress = this.criteriaProgresses.get(name);
        if (criterionProgress != null && !criterionProgress.isObtained()) {
            criterionProgress.obtain();
            return true;
        }
        return false;
    }

    public boolean reset(String name) {
        CriterionProgress criterionProgress = this.criteriaProgresses.get(name);
        if (criterionProgress != null && criterionProgress.isObtained()) {
            criterionProgress.reset();
            return true;
        }
        return false;
    }

    public String toString() {
        return "AdvancementProgress{criteria=" + this.criteriaProgresses + ", requirements=" + Arrays.deepToString((Object[])this.requirements) + '}';
    }

    public void toPacket(PacketByteBuf buf) {
        buf.writeVarInt(this.criteriaProgresses.size());
        for (Map.Entry<String, CriterionProgress> entry : this.criteriaProgresses.entrySet()) {
            buf.writeString(entry.getKey());
            entry.getValue().toPacket(buf);
        }
    }

    public static AdvancementProgress fromPacket(PacketByteBuf buf) {
        AdvancementProgress advancementProgress = new AdvancementProgress();
        int _snowman2 = buf.readVarInt();
        for (int i = 0; i < _snowman2; ++i) {
            advancementProgress.criteriaProgresses.put(buf.readString(Short.MAX_VALUE), CriterionProgress.fromPacket(buf));
        }
        return advancementProgress;
    }

    @Nullable
    public CriterionProgress getCriterionProgress(String name) {
        return this.criteriaProgresses.get(name);
    }

    public float getProgressBarPercentage() {
        if (this.criteriaProgresses.isEmpty()) {
            return 0.0f;
        }
        float f = this.requirements.length;
        _snowman = this.countObtainedRequirements();
        return _snowman / f;
    }

    @Nullable
    public String getProgressBarFraction() {
        if (this.criteriaProgresses.isEmpty()) {
            return null;
        }
        int n = this.requirements.length;
        if (n <= 1) {
            return null;
        }
        _snowman = this.countObtainedRequirements();
        return _snowman + "/" + n;
    }

    private int countObtainedRequirements() {
        int n = 0;
        for (String[] stringArray : this.requirements) {
            boolean _snowman2 = false;
            for (String string : stringArray) {
                CriterionProgress criterionProgress = this.getCriterionProgress(string);
                if (criterionProgress == null || !criterionProgress.isObtained()) continue;
                _snowman2 = true;
                break;
            }
            if (!_snowman2) continue;
            ++n;
        }
        return n;
    }

    public Iterable<String> getUnobtainedCriteria() {
        ArrayList arrayList = Lists.newArrayList();
        for (Map.Entry<String, CriterionProgress> entry : this.criteriaProgresses.entrySet()) {
            if (entry.getValue().isObtained()) continue;
            arrayList.add(entry.getKey());
        }
        return arrayList;
    }

    public Iterable<String> getObtainedCriteria() {
        ArrayList arrayList = Lists.newArrayList();
        for (Map.Entry<String, CriterionProgress> entry : this.criteriaProgresses.entrySet()) {
            if (!entry.getValue().isObtained()) continue;
            arrayList.add(entry.getKey());
        }
        return arrayList;
    }

    @Nullable
    public Date getEarliestProgressObtainDate() {
        Date date = null;
        for (CriterionProgress criterionProgress : this.criteriaProgresses.values()) {
            if (!criterionProgress.isObtained() || date != null && !criterionProgress.getObtainedDate().before(date)) continue;
            date = criterionProgress.getObtainedDate();
        }
        return date;
    }

    @Override
    public int compareTo(AdvancementProgress advancementProgress) {
        Date date = this.getEarliestProgressObtainDate();
        _snowman = advancementProgress.getEarliestProgressObtainDate();
        if (date == null && _snowman != null) {
            return 1;
        }
        if (date != null && _snowman == null) {
            return -1;
        }
        if (date == null && _snowman == null) {
            return 0;
        }
        return date.compareTo(_snowman);
    }

    @Override
    public /* synthetic */ int compareTo(Object object) {
        return this.compareTo((AdvancementProgress)object);
    }

    public static class Serializer
    implements JsonDeserializer<AdvancementProgress>,
    JsonSerializer<AdvancementProgress> {
        public JsonElement serialize(AdvancementProgress advancementProgress, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject jsonObject;
            JsonObject jsonObject2 = new JsonObject();
            jsonObject = new JsonObject();
            for (Map.Entry entry : advancementProgress.criteriaProgresses.entrySet()) {
                CriterionProgress criterionProgress = (CriterionProgress)entry.getValue();
                if (!criterionProgress.isObtained()) continue;
                jsonObject.add((String)entry.getKey(), criterionProgress.toJson());
            }
            if (!jsonObject.entrySet().isEmpty()) {
                jsonObject2.add("criteria", (JsonElement)jsonObject);
            }
            jsonObject2.addProperty("done", Boolean.valueOf(advancementProgress.isDone()));
            return jsonObject2;
        }

        public AdvancementProgress deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject jsonObject = JsonHelper.asObject(jsonElement, "advancement");
            _snowman = JsonHelper.getObject(jsonObject, "criteria", new JsonObject());
            AdvancementProgress _snowman2 = new AdvancementProgress();
            for (Map.Entry entry : _snowman.entrySet()) {
                String string = (String)entry.getKey();
                _snowman2.criteriaProgresses.put(string, CriterionProgress.obtainedAt(JsonHelper.asString((JsonElement)entry.getValue(), string)));
            }
            return _snowman2;
        }

        public /* synthetic */ Object deserialize(JsonElement functionJson, Type unused, JsonDeserializationContext context) throws JsonParseException {
            return this.deserialize(functionJson, unused, context);
        }

        public /* synthetic */ JsonElement serialize(Object entry, Type unused, JsonSerializationContext context) {
            return this.serialize((AdvancementProgress)entry, unused, context);
        }
    }
}

