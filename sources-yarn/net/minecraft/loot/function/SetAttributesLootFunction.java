package net.minecraft.loot.function;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.UniformLootTableRange;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;

public class SetAttributesLootFunction extends ConditionalLootFunction {
   private final List<SetAttributesLootFunction.Attribute> attributes;

   private SetAttributesLootFunction(LootCondition[] conditions, List<SetAttributesLootFunction.Attribute> attributes) {
      super(conditions);
      this.attributes = ImmutableList.copyOf(attributes);
   }

   @Override
   public LootFunctionType getType() {
      return LootFunctionTypes.SET_ATTRIBUTES;
   }

   @Override
   public ItemStack process(ItemStack stack, LootContext context) {
      Random random = context.getRandom();

      for (SetAttributesLootFunction.Attribute lv : this.attributes) {
         UUID uUID = lv.id;
         if (uUID == null) {
            uUID = UUID.randomUUID();
         }

         EquipmentSlot lv2 = Util.getRandom(lv.slots, random);
         stack.addAttributeModifier(lv.attribute, new EntityAttributeModifier(uUID, lv.name, (double)lv.amountRange.nextFloat(random), lv.operation), lv2);
      }

      return stack;
   }

   static class Attribute {
      private final String name;
      private final EntityAttribute attribute;
      private final EntityAttributeModifier.Operation operation;
      private final UniformLootTableRange amountRange;
      @Nullable
      private final UUID id;
      private final EquipmentSlot[] slots;

      private Attribute(
         String name,
         EntityAttribute arg,
         EntityAttributeModifier.Operation operation,
         UniformLootTableRange amountRange,
         EquipmentSlot[] slots,
         @Nullable UUID id
      ) {
         this.name = name;
         this.attribute = arg;
         this.operation = operation;
         this.amountRange = amountRange;
         this.id = id;
         this.slots = slots;
      }

      public JsonObject serialize(JsonSerializationContext context) {
         JsonObject jsonObject = new JsonObject();
         jsonObject.addProperty("name", this.name);
         jsonObject.addProperty("attribute", Registry.ATTRIBUTE.getId(this.attribute).toString());
         jsonObject.addProperty("operation", getName(this.operation));
         jsonObject.add("amount", context.serialize(this.amountRange));
         if (this.id != null) {
            jsonObject.addProperty("id", this.id.toString());
         }

         if (this.slots.length == 1) {
            jsonObject.addProperty("slot", this.slots[0].getName());
         } else {
            JsonArray jsonArray = new JsonArray();

            for (EquipmentSlot lv : this.slots) {
               jsonArray.add(new JsonPrimitive(lv.getName()));
            }

            jsonObject.add("slot", jsonArray);
         }

         return jsonObject;
      }

      public static SetAttributesLootFunction.Attribute deserialize(JsonObject json, JsonDeserializationContext context) {
         String string = JsonHelper.getString(json, "name");
         Identifier lv = new Identifier(JsonHelper.getString(json, "attribute"));
         EntityAttribute lv2 = Registry.ATTRIBUTE.get(lv);
         if (lv2 == null) {
            throw new JsonSyntaxException("Unknown attribute: " + lv);
         } else {
            EntityAttributeModifier.Operation lv3 = fromName(JsonHelper.getString(json, "operation"));
            UniformLootTableRange lv4 = JsonHelper.deserialize(json, "amount", context, UniformLootTableRange.class);
            UUID uUID = null;
            EquipmentSlot[] lvs;
            if (JsonHelper.hasString(json, "slot")) {
               lvs = new EquipmentSlot[]{EquipmentSlot.byName(JsonHelper.getString(json, "slot"))};
            } else {
               if (!JsonHelper.hasArray(json, "slot")) {
                  throw new JsonSyntaxException("Invalid or missing attribute modifier slot; must be either string or array of strings.");
               }

               JsonArray jsonArray = JsonHelper.getArray(json, "slot");
               lvs = new EquipmentSlot[jsonArray.size()];
               int i = 0;

               for (JsonElement jsonElement : jsonArray) {
                  lvs[i++] = EquipmentSlot.byName(JsonHelper.asString(jsonElement, "slot"));
               }

               if (lvs.length == 0) {
                  throw new JsonSyntaxException("Invalid attribute modifier slot; must contain at least one entry.");
               }
            }

            if (json.has("id")) {
               String string2 = JsonHelper.getString(json, "id");

               try {
                  uUID = UUID.fromString(string2);
               } catch (IllegalArgumentException var13) {
                  throw new JsonSyntaxException("Invalid attribute modifier id '" + string2 + "' (must be UUID format, with dashes)");
               }
            }

            return new SetAttributesLootFunction.Attribute(string, lv2, lv3, lv4, lvs, uUID);
         }
      }

      private static String getName(EntityAttributeModifier.Operation operation) {
         switch (operation) {
            case ADDITION:
               return "addition";
            case MULTIPLY_BASE:
               return "multiply_base";
            case MULTIPLY_TOTAL:
               return "multiply_total";
            default:
               throw new IllegalArgumentException("Unknown operation " + operation);
         }
      }

      private static EntityAttributeModifier.Operation fromName(String name) {
         switch (name) {
            case "addition":
               return EntityAttributeModifier.Operation.ADDITION;
            case "multiply_base":
               return EntityAttributeModifier.Operation.MULTIPLY_BASE;
            case "multiply_total":
               return EntityAttributeModifier.Operation.MULTIPLY_TOTAL;
            default:
               throw new JsonSyntaxException("Unknown attribute modifier operation " + name);
         }
      }
   }

   public static class Serializer extends ConditionalLootFunction.Serializer<SetAttributesLootFunction> {
      public Serializer() {
      }

      public void toJson(JsonObject jsonObject, SetAttributesLootFunction arg, JsonSerializationContext jsonSerializationContext) {
         super.toJson(jsonObject, arg, jsonSerializationContext);
         JsonArray jsonArray = new JsonArray();

         for (SetAttributesLootFunction.Attribute lv : arg.attributes) {
            jsonArray.add(lv.serialize(jsonSerializationContext));
         }

         jsonObject.add("modifiers", jsonArray);
      }

      public SetAttributesLootFunction fromJson(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, LootCondition[] args) {
         JsonArray jsonArray = JsonHelper.getArray(jsonObject, "modifiers");
         List<SetAttributesLootFunction.Attribute> list = Lists.newArrayListWithExpectedSize(jsonArray.size());

         for (JsonElement jsonElement : jsonArray) {
            list.add(SetAttributesLootFunction.Attribute.deserialize(JsonHelper.asObject(jsonElement, "modifier"), jsonDeserializationContext));
         }

         if (list.isEmpty()) {
            throw new JsonSyntaxException("Invalid attribute modifiers array; cannot be empty");
         } else {
            return new SetAttributesLootFunction(args, list);
         }
      }
   }
}
