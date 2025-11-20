package net.minecraft.advancement;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.advancement.criterion.CriterionConditions;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.LiteralText;
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

   public Advancement(
      Identifier id,
      @Nullable Advancement parent,
      @Nullable AdvancementDisplay display,
      AdvancementRewards rewards,
      Map<String, AdvancementCriterion> criteria,
      String[][] requirements
   ) {
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
         Text _snowman = display.getTitle();
         Formatting _snowmanx = display.getFrame().getTitleFormat();
         Text _snowmanxx = Texts.setStyleIfAbsent(_snowman.shallowCopy(), Style.EMPTY.withColor(_snowmanx)).append("\n").append(display.getDescription());
         Text _snowmanxxx = _snowman.shallowCopy().styled(_snowmanxxxx -> _snowmanxxxx.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, _snowman)));
         this.text = Texts.bracketed(_snowmanxxx).formatted(_snowmanx);
      }
   }

   public Advancement.Task createTask() {
      return new Advancement.Task(this.parent == null ? null : this.parent.getId(), this.display, this.rewards, this.criteria, this.requirements);
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

   @Override
   public String toString() {
      return "SimpleAdvancement{id="
         + this.getId()
         + ", parent="
         + (this.parent == null ? "null" : this.parent.getId())
         + ", display="
         + this.display
         + ", rewards="
         + this.rewards
         + ", criteria="
         + this.criteria
         + ", requirements="
         + Arrays.deepToString(this.requirements)
         + '}';
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

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof Advancement)) {
         return false;
      } else {
         Advancement _snowman = (Advancement)o;
         return this.id.equals(_snowman.id);
      }
   }

   @Override
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

      private Task(
         @Nullable Identifier parentId,
         @Nullable AdvancementDisplay display,
         AdvancementRewards rewards,
         Map<String, AdvancementCriterion> criteria,
         String[][] requirements
      ) {
         this.parentId = parentId;
         this.display = display;
         this.rewards = rewards;
         this.criteria = criteria;
         this.requirements = requirements;
      }

      private Task() {
      }

      public static Advancement.Task create() {
         return new Advancement.Task();
      }

      public Advancement.Task parent(Advancement parent) {
         this.parentObj = parent;
         return this;
      }

      public Advancement.Task parent(Identifier parentId) {
         this.parentId = parentId;
         return this;
      }

      public Advancement.Task display(
         ItemStack icon,
         Text title,
         Text description,
         @Nullable Identifier background,
         AdvancementFrame frame,
         boolean showToast,
         boolean announceToChat,
         boolean hidden
      ) {
         return this.display(new AdvancementDisplay(icon, title, description, background, frame, showToast, announceToChat, hidden));
      }

      public Advancement.Task display(
         ItemConvertible icon,
         Text title,
         Text description,
         @Nullable Identifier background,
         AdvancementFrame frame,
         boolean showToast,
         boolean announceToChat,
         boolean hidden
      ) {
         return this.display(new AdvancementDisplay(new ItemStack(icon.asItem()), title, description, background, frame, showToast, announceToChat, hidden));
      }

      public Advancement.Task display(AdvancementDisplay display) {
         this.display = display;
         return this;
      }

      public Advancement.Task rewards(AdvancementRewards.Builder _snowman) {
         return this.rewards(_snowman.build());
      }

      public Advancement.Task rewards(AdvancementRewards rewards) {
         this.rewards = rewards;
         return this;
      }

      public Advancement.Task criterion(String name, CriterionConditions _snowman) {
         return this.criterion(name, new AdvancementCriterion(_snowman));
      }

      public Advancement.Task criterion(String name, AdvancementCriterion _snowman) {
         if (this.criteria.containsKey(name)) {
            throw new IllegalArgumentException("Duplicate criterion " + name);
         } else {
            this.criteria.put(name, _snowman);
            return this;
         }
      }

      public Advancement.Task criteriaMerger(CriterionMerger merger) {
         this.merger = merger;
         return this;
      }

      public boolean findParent(Function<Identifier, Advancement> parentProvider) {
         if (this.parentId == null) {
            return true;
         } else {
            if (this.parentObj == null) {
               this.parentObj = parentProvider.apply(this.parentId);
            }

            return this.parentObj != null;
         }
      }

      public Advancement build(Identifier id) {
         if (!this.findParent(_snowman -> null)) {
            throw new IllegalStateException("Tried to build incomplete advancement!");
         } else {
            if (this.requirements == null) {
               this.requirements = this.merger.createRequirements(this.criteria.keySet());
            }

            return new Advancement(id, this.parentObj, this.display, this.rewards, this.criteria, this.requirements);
         }
      }

      public Advancement build(Consumer<Advancement> _snowman, String _snowman) {
         Advancement _snowmanxx = this.build(new Identifier(_snowman));
         _snowman.accept(_snowmanxx);
         return _snowmanxx;
      }

      public JsonObject toJson() {
         if (this.requirements == null) {
            this.requirements = this.merger.createRequirements(this.criteria.keySet());
         }

         JsonObject _snowman = new JsonObject();
         if (this.parentObj != null) {
            _snowman.addProperty("parent", this.parentObj.getId().toString());
         } else if (this.parentId != null) {
            _snowman.addProperty("parent", this.parentId.toString());
         }

         if (this.display != null) {
            _snowman.add("display", this.display.toJson());
         }

         _snowman.add("rewards", this.rewards.toJson());
         JsonObject _snowmanx = new JsonObject();

         for (Entry<String, AdvancementCriterion> _snowmanxx : this.criteria.entrySet()) {
            _snowmanx.add(_snowmanxx.getKey(), _snowmanxx.getValue().toJson());
         }

         _snowman.add("criteria", _snowmanx);
         JsonArray _snowmanxx = new JsonArray();

         for (String[] _snowmanxxx : this.requirements) {
            JsonArray _snowmanxxxx = new JsonArray();

            for (String _snowmanxxxxx : _snowmanxxx) {
               _snowmanxxxx.add(_snowmanxxxxx);
            }

            _snowmanxx.add(_snowmanxxxx);
         }

         _snowman.add("requirements", _snowmanxx);
         return _snowman;
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

         for (String[] _snowman : this.requirements) {
            buf.writeVarInt(_snowman.length);

            for (String _snowmanx : _snowman) {
               buf.writeString(_snowmanx);
            }
         }
      }

      @Override
      public String toString() {
         return "Task Advancement{parentId="
            + this.parentId
            + ", display="
            + this.display
            + ", rewards="
            + this.rewards
            + ", criteria="
            + this.criteria
            + ", requirements="
            + Arrays.deepToString(this.requirements)
            + '}';
      }

      public static Advancement.Task fromJson(JsonObject obj, AdvancementEntityPredicateDeserializer predicateDeserializer) {
         Identifier _snowman = obj.has("parent") ? new Identifier(JsonHelper.getString(obj, "parent")) : null;
         AdvancementDisplay _snowmanx = obj.has("display") ? AdvancementDisplay.fromJson(JsonHelper.getObject(obj, "display")) : null;
         AdvancementRewards _snowmanxx = obj.has("rewards") ? AdvancementRewards.fromJson(JsonHelper.getObject(obj, "rewards")) : AdvancementRewards.NONE;
         Map<String, AdvancementCriterion> _snowmanxxx = AdvancementCriterion.criteriaFromJson(JsonHelper.getObject(obj, "criteria"), predicateDeserializer);
         if (_snowmanxxx.isEmpty()) {
            throw new JsonSyntaxException("Advancement criteria cannot be empty");
         } else {
            JsonArray _snowmanxxxx = JsonHelper.getArray(obj, "requirements", new JsonArray());
            String[][] _snowmanxxxxx = new String[_snowmanxxxx.size()][];

            for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < _snowmanxxxx.size(); _snowmanxxxxxx++) {
               JsonArray _snowmanxxxxxxx = JsonHelper.asArray(_snowmanxxxx.get(_snowmanxxxxxx), "requirements[" + _snowmanxxxxxx + "]");
               _snowmanxxxxx[_snowmanxxxxxx] = new String[_snowmanxxxxxxx.size()];

               for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < _snowmanxxxxxxx.size(); _snowmanxxxxxxxx++) {
                  _snowmanxxxxx[_snowmanxxxxxx][_snowmanxxxxxxxx] = JsonHelper.asString(_snowmanxxxxxxx.get(_snowmanxxxxxxxx), "requirements[" + _snowmanxxxxxx + "][" + _snowmanxxxxxxxx + "]");
               }
            }

            if (_snowmanxxxxx.length == 0) {
               _snowmanxxxxx = new String[_snowmanxxx.size()][];
               int _snowmanxxxxxx = 0;

               for (String _snowmanxxxxxxx : _snowmanxxx.keySet()) {
                  _snowmanxxxxx[_snowmanxxxxxx++] = new String[]{_snowmanxxxxxxx};
               }
            }

            for (String[] _snowmanxxxxxx : _snowmanxxxxx) {
               if (_snowmanxxxxxx.length == 0 && _snowmanxxx.isEmpty()) {
                  throw new JsonSyntaxException("Requirement entry cannot be empty");
               }

               for (String _snowmanxxxxxxx : _snowmanxxxxxx) {
                  if (!_snowmanxxx.containsKey(_snowmanxxxxxxx)) {
                     throw new JsonSyntaxException("Unknown required criterion '" + _snowmanxxxxxxx + "'");
                  }
               }
            }

            for (String _snowmanxxxxxx : _snowmanxxx.keySet()) {
               boolean _snowmanxxxxxxxx = false;

               for (String[] _snowmanxxxxxxxxx : _snowmanxxxxx) {
                  if (ArrayUtils.contains(_snowmanxxxxxxxxx, _snowmanxxxxxx)) {
                     _snowmanxxxxxxxx = true;
                     break;
                  }
               }

               if (!_snowmanxxxxxxxx) {
                  throw new JsonSyntaxException(
                     "Criterion '" + _snowmanxxxxxx + "' isn't a requirement for completion. This isn't supported behaviour, all criteria must be required."
                  );
               }
            }

            return new Advancement.Task(_snowman, _snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxxx);
         }
      }

      public static Advancement.Task fromPacket(PacketByteBuf buf) {
         Identifier _snowman = buf.readBoolean() ? buf.readIdentifier() : null;
         AdvancementDisplay _snowmanx = buf.readBoolean() ? AdvancementDisplay.fromPacket(buf) : null;
         Map<String, AdvancementCriterion> _snowmanxx = AdvancementCriterion.criteriaFromPacket(buf);
         String[][] _snowmanxxx = new String[buf.readVarInt()][];

         for (int _snowmanxxxx = 0; _snowmanxxxx < _snowmanxxx.length; _snowmanxxxx++) {
            _snowmanxxx[_snowmanxxxx] = new String[buf.readVarInt()];

            for (int _snowmanxxxxx = 0; _snowmanxxxxx < _snowmanxxx[_snowmanxxxx].length; _snowmanxxxxx++) {
               _snowmanxxx[_snowmanxxxx][_snowmanxxxxx] = buf.readString(32767);
            }
         }

         return new Advancement.Task(_snowman, _snowmanx, AdvancementRewards.NONE, _snowmanxx, _snowmanxxx);
      }

      public Map<String, AdvancementCriterion> getCriteria() {
         return this.criteria;
      }
   }
}
