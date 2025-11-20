/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonSyntaxException
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.ArrayUtils
 */
package net.minecraft.advancement;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.AdvancementDisplay;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.CriterionMerger;
import net.minecraft.advancement.criterion.CriterionConditions;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import org.apache.commons.lang3.ArrayUtils;

public class Advancement {
    private final Advancement parent;
    private final AdvancementDisplay display;
    private final AdvancementRewards rewards;
    private final Identifier id;
    private final Map<String, AdvancementCriterion> criteria;
    private final String[][] requirements;
    private final Set<Advancement> children = Sets.newLinkedHashSet();
    private final Text text;

    public Advancement(Identifier id, @Nullable Advancement parent, @Nullable AdvancementDisplay display, AdvancementRewards rewards, Map<String, AdvancementCriterion> criteria, String[][] requirements) {
        this.id = id;
        this.display = display;
        this.criteria = ImmutableMap.copyOf(criteria);
        this.parent = parent;
        this.rewards = rewards;
        this.requirements = requirements;
        if (parent != null) {
            parent.addChild(this);
        }
        if (display == null) {
            this.text = new LiteralText(id.toString());
        } else {
            Text text = display.getTitle();
            Formatting _snowman2 = display.getFrame().getTitleFormat();
            MutableText _snowman3 = Texts.setStyleIfAbsent(text.shallowCopy(), Style.EMPTY.withColor(_snowman2)).append("\n").append(display.getDescription());
            MutableText _snowman4 = text.shallowCopy().styled(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, _snowman3)));
            this.text = Texts.bracketed(_snowman4).formatted(_snowman2);
        }
    }

    public Task createTask() {
        return new Task(this.parent == null ? null : this.parent.getId(), this.display, this.rewards, this.criteria, this.requirements);
    }

    @Nullable
    public Advancement getParent() {
        return this.parent;
    }

    @Nullable
    public AdvancementDisplay getDisplay() {
        return this.display;
    }

    public AdvancementRewards getRewards() {
        return this.rewards;
    }

    public String toString() {
        return "SimpleAdvancement{id=" + this.getId() + ", parent=" + (this.parent == null ? "null" : this.parent.getId()) + ", display=" + this.display + ", rewards=" + this.rewards + ", criteria=" + this.criteria + ", requirements=" + Arrays.deepToString((Object[])this.requirements) + '}';
    }

    public Iterable<Advancement> getChildren() {
        return this.children;
    }

    public Map<String, AdvancementCriterion> getCriteria() {
        return this.criteria;
    }

    public int getRequirementCount() {
        return this.requirements.length;
    }

    public void addChild(Advancement child) {
        this.children.add(child);
    }

    public Identifier getId() {
        return this.id;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Advancement)) {
            return false;
        }
        Advancement advancement = (Advancement)o;
        return this.id.equals(advancement.id);
    }

    public int hashCode() {
        return this.id.hashCode();
    }

    public String[][] getRequirements() {
        return this.requirements;
    }

    public Text toHoverableText() {
        return this.text;
    }

    public static class Task {
        private Identifier parentId;
        private Advancement parentObj;
        private AdvancementDisplay display;
        private AdvancementRewards rewards = AdvancementRewards.NONE;
        private Map<String, AdvancementCriterion> criteria = Maps.newLinkedHashMap();
        private String[][] requirements;
        private CriterionMerger merger = CriterionMerger.AND;

        private Task(@Nullable Identifier parentId, @Nullable AdvancementDisplay display, AdvancementRewards rewards, Map<String, AdvancementCriterion> criteria, String[][] requirements) {
            this.parentId = parentId;
            this.display = display;
            this.rewards = rewards;
            this.criteria = criteria;
            this.requirements = requirements;
        }

        private Task() {
        }

        public static Task create() {
            return new Task();
        }

        public Task parent(Advancement parent) {
            this.parentObj = parent;
            return this;
        }

        public Task parent(Identifier parentId) {
            this.parentId = parentId;
            return this;
        }

        public Task display(ItemStack icon, Text title, Text description, @Nullable Identifier background, AdvancementFrame frame, boolean showToast, boolean announceToChat, boolean hidden) {
            return this.display(new AdvancementDisplay(icon, title, description, background, frame, showToast, announceToChat, hidden));
        }

        public Task display(ItemConvertible icon, Text title, Text description, @Nullable Identifier background, AdvancementFrame frame, boolean showToast, boolean announceToChat, boolean hidden) {
            return this.display(new AdvancementDisplay(new ItemStack(icon.asItem()), title, description, background, frame, showToast, announceToChat, hidden));
        }

        public Task display(AdvancementDisplay display) {
            this.display = display;
            return this;
        }

        public Task rewards(AdvancementRewards.Builder builder) {
            return this.rewards(builder.build());
        }

        public Task rewards(AdvancementRewards rewards) {
            this.rewards = rewards;
            return this;
        }

        public Task criterion(String name, CriterionConditions criterionConditions) {
            return this.criterion(name, new AdvancementCriterion(criterionConditions));
        }

        public Task criterion(String name, AdvancementCriterion advancementCriterion) {
            if (this.criteria.containsKey(name)) {
                throw new IllegalArgumentException("Duplicate criterion " + name);
            }
            this.criteria.put(name, advancementCriterion);
            return this;
        }

        public Task criteriaMerger(CriterionMerger merger) {
            this.merger = merger;
            return this;
        }

        public boolean findParent(Function<Identifier, Advancement> parentProvider) {
            if (this.parentId == null) {
                return true;
            }
            if (this.parentObj == null) {
                this.parentObj = parentProvider.apply(this.parentId);
            }
            return this.parentObj != null;
        }

        public Advancement build(Identifier id) {
            if (!this.findParent(identifier -> null)) {
                throw new IllegalStateException("Tried to build incomplete advancement!");
            }
            if (this.requirements == null) {
                this.requirements = this.merger.createRequirements(this.criteria.keySet());
            }
            return new Advancement(id, this.parentObj, this.display, this.rewards, this.criteria, this.requirements);
        }

        public Advancement build(Consumer<Advancement> consumer, String string) {
            Advancement advancement = this.build(new Identifier(string));
            consumer.accept(advancement);
            return advancement;
        }

        public JsonObject toJson() {
            if (this.requirements == null) {
                this.requirements = this.merger.createRequirements(this.criteria.keySet());
            }
            JsonObject jsonObject = new JsonObject();
            if (this.parentObj != null) {
                jsonObject.addProperty("parent", this.parentObj.getId().toString());
            } else if (this.parentId != null) {
                jsonObject.addProperty("parent", this.parentId.toString());
            }
            if (this.display != null) {
                jsonObject.add("display", this.display.toJson());
            }
            jsonObject.add("rewards", this.rewards.toJson());
            _snowman = new JsonObject();
            for (Map.Entry<String, AdvancementCriterion> entry : this.criteria.entrySet()) {
                _snowman.add(entry.getKey(), entry.getValue().toJson());
            }
            jsonObject.add("criteria", (JsonElement)_snowman);
            JsonArray _snowman2 = new JsonArray();
            for (String[] stringArray : this.requirements) {
                JsonArray jsonArray = new JsonArray();
                for (String string : stringArray) {
                    jsonArray.add(string);
                }
                _snowman2.add((JsonElement)jsonArray);
            }
            jsonObject.add("requirements", (JsonElement)_snowman2);
            return jsonObject;
        }

        public void toPacket(PacketByteBuf buf) {
            if (this.parentId == null) {
                buf.writeBoolean(false);
            } else {
                buf.writeBoolean(true);
                buf.writeIdentifier(this.parentId);
            }
            if (this.display == null) {
                buf.writeBoolean(false);
            } else {
                buf.writeBoolean(true);
                this.display.toPacket(buf);
            }
            AdvancementCriterion.criteriaToPacket(this.criteria, buf);
            buf.writeVarInt(this.requirements.length);
            for (String[] stringArray : this.requirements) {
                buf.writeVarInt(stringArray.length);
                for (String string : stringArray) {
                    buf.writeString(string);
                }
            }
        }

        public String toString() {
            return "Task Advancement{parentId=" + this.parentId + ", display=" + this.display + ", rewards=" + this.rewards + ", criteria=" + this.criteria + ", requirements=" + Arrays.deepToString((Object[])this.requirements) + '}';
        }

        public static Task fromJson(JsonObject obj, AdvancementEntityPredicateDeserializer predicateDeserializer) {
            Identifier identifier = obj.has("parent") ? new Identifier(JsonHelper.getString(obj, "parent")) : null;
            AdvancementDisplay _snowman2 = obj.has("display") ? AdvancementDisplay.fromJson(JsonHelper.getObject(obj, "display")) : null;
            AdvancementRewards _snowman3 = obj.has("rewards") ? AdvancementRewards.fromJson(JsonHelper.getObject(obj, "rewards")) : AdvancementRewards.NONE;
            Map<String, AdvancementCriterion> _snowman4 = AdvancementCriterion.criteriaFromJson(JsonHelper.getObject(obj, "criteria"), predicateDeserializer);
            if (_snowman4.isEmpty()) {
                throw new JsonSyntaxException("Advancement criteria cannot be empty");
            }
            JsonArray _snowman5 = JsonHelper.getArray(obj, "requirements", new JsonArray());
            String[][] _snowman6 = new String[_snowman5.size()][];
            for (int n = 0; n < _snowman5.size(); ++n) {
                JsonArray jsonArray = JsonHelper.asArray(_snowman5.get(n), "requirements[" + n + "]");
                _snowman6[n] = new String[jsonArray.size()];
                for (int i = 0; i < jsonArray.size(); ++i) {
                    _snowman6[n][i] = JsonHelper.asString(jsonArray.get(i), "requirements[" + n + "][" + i + "]");
                }
            }
            if (_snowman6.length == 0) {
                _snowman6 = new String[_snowman4.size()][];
                int n = 0;
                for (String string2 : _snowman4.keySet()) {
                    _snowman6[n++] = new String[]{string2};
                }
            }
            for (String[] stringArray : _snowman6) {
                if (stringArray.length == 0 && _snowman4.isEmpty()) {
                    throw new JsonSyntaxException("Requirement entry cannot be empty");
                }
                String[] stringArray2 = stringArray;
                int n2 = stringArray2.length;
                for (int i = 0; i < n2; ++i) {
                    String string = stringArray2[i];
                    if (_snowman4.containsKey(string)) continue;
                    throw new JsonSyntaxException("Unknown required criterion '" + string + "'");
                }
            }
            for (String string : _snowman4.keySet()) {
                boolean bl = false;
                for (Object[] objectArray : _snowman6) {
                    if (!ArrayUtils.contains((Object[])objectArray, (Object)string)) continue;
                    bl = true;
                    break;
                }
                if (bl) continue;
                throw new JsonSyntaxException("Criterion '" + string + "' isn't a requirement for completion. This isn't supported behaviour, all criteria must be required.");
            }
            return new Task(identifier, _snowman2, _snowman3, _snowman4, _snowman6);
        }

        public static Task fromPacket(PacketByteBuf buf) {
            Identifier identifier = buf.readBoolean() ? buf.readIdentifier() : null;
            AdvancementDisplay _snowman2 = buf.readBoolean() ? AdvancementDisplay.fromPacket(buf) : null;
            Map<String, AdvancementCriterion> _snowman3 = AdvancementCriterion.criteriaFromPacket(buf);
            String[][] _snowman4 = new String[buf.readVarInt()][];
            for (int i = 0; i < _snowman4.length; ++i) {
                _snowman4[i] = new String[buf.readVarInt()];
                for (_snowman = 0; _snowman < _snowman4[i].length; ++_snowman) {
                    _snowman4[i][_snowman] = buf.readString(Short.MAX_VALUE);
                }
            }
            return new Task(identifier, _snowman2, AdvancementRewards.NONE, _snowman3, _snowman4);
        }

        public Map<String, AdvancementCriterion> getCriteria() {
            return this.criteria;
        }
    }
}

