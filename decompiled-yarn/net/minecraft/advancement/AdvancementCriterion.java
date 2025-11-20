package net.minecraft.advancement;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.Map;
import java.util.Map.Entry;
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
      Identifier _snowman = new Identifier(JsonHelper.getString(obj, "trigger"));
      Criterion<?> _snowmanx = Criteria.getById(_snowman);
      if (_snowmanx == null) {
         throw new JsonSyntaxException("Invalid criterion trigger: " + _snowman);
      } else {
         CriterionConditions _snowmanxx = _snowmanx.conditionsFromJson(JsonHelper.getObject(obj, "conditions", new JsonObject()), predicateDeserializer);
         return new AdvancementCriterion(_snowmanxx);
      }
   }

   public static AdvancementCriterion fromPacket(PacketByteBuf buf) {
      return new AdvancementCriterion();
   }

   public static Map<String, AdvancementCriterion> criteriaFromJson(JsonObject obj, AdvancementEntityPredicateDeserializer predicateDeserializer) {
      Map<String, AdvancementCriterion> _snowman = Maps.newHashMap();

      for (Entry<String, JsonElement> _snowmanx : obj.entrySet()) {
         _snowman.put(_snowmanx.getKey(), fromJson(JsonHelper.asObject(_snowmanx.getValue(), "criterion"), predicateDeserializer));
      }

      return _snowman;
   }

   public static Map<String, AdvancementCriterion> criteriaFromPacket(PacketByteBuf buf) {
      Map<String, AdvancementCriterion> _snowman = Maps.newHashMap();
      int _snowmanx = buf.readVarInt();

      for (int _snowmanxx = 0; _snowmanxx < _snowmanx; _snowmanxx++) {
         _snowman.put(buf.readString(32767), fromPacket(buf));
      }

      return _snowman;
   }

   public static void criteriaToPacket(Map<String, AdvancementCriterion> criteria, PacketByteBuf buf) {
      buf.writeVarInt(criteria.size());

      for (Entry<String, AdvancementCriterion> _snowman : criteria.entrySet()) {
         buf.writeString(_snowman.getKey());
         _snowman.getValue().toPacket(buf);
      }
   }

   @Nullable
   public CriterionConditions getConditions() {
      return this.conditions;
   }

   public JsonElement toJson() {
      JsonObject _snowman = new JsonObject();
      _snowman.addProperty("trigger", this.conditions.getId().toString());
      JsonObject _snowmanx = this.conditions.toJson(AdvancementEntityPredicateSerializer.INSTANCE);
      if (_snowmanx.size() != 0) {
         _snowman.add("conditions", _snowmanx);
      }

      return _snowman;
   }
}
