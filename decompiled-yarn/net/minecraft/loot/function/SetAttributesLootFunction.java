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
      Random _snowman = context.getRandom();

      for (SetAttributesLootFunction.Attribute _snowmanx : this.attributes) {
         UUID _snowmanxx = _snowmanx.id;
         if (_snowmanxx == null) {
            _snowmanxx = UUID.randomUUID();
         }

         EquipmentSlot _snowmanxxx = Util.getRandom(_snowmanx.slots, _snowman);
         stack.addAttributeModifier(_snowmanx.attribute, new EntityAttributeModifier(_snowmanxx, _snowmanx.name, (double)_snowmanx.amountRange.nextFloat(_snowman), _snowmanx.operation), _snowmanxxx);
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
         EntityAttribute _snowman,
         EntityAttributeModifier.Operation operation,
         UniformLootTableRange amountRange,
         EquipmentSlot[] slots,
         @Nullable UUID id
      ) {
         this.name = name;
         this.attribute = _snowman;
         this.operation = operation;
         this.amountRange = amountRange;
         this.id = id;
         this.slots = slots;
      }

      public JsonObject serialize(JsonSerializationContext context) {
         JsonObject _snowman = new JsonObject();
         _snowman.addProperty("name", this.name);
         _snowman.addProperty("attribute", Registry.ATTRIBUTE.getId(this.attribute).toString());
         _snowman.addProperty("operation", getName(this.operation));
         _snowman.add("amount", context.serialize(this.amountRange));
         if (this.id != null) {
            _snowman.addProperty("id", this.id.toString());
         }

         if (this.slots.length == 1) {
            _snowman.addProperty("slot", this.slots[0].getName());
         } else {
            JsonArray _snowmanx = new JsonArray();

            for (EquipmentSlot _snowmanxx : this.slots) {
               _snowmanx.add(new JsonPrimitive(_snowmanxx.getName()));
            }

            _snowman.add("slot", _snowmanx);
         }

         return _snowman;
      }

      public static SetAttributesLootFunction.Attribute deserialize(JsonObject json, JsonDeserializationContext context) {
         String _snowman = JsonHelper.getString(json, "name");
         Identifier _snowmanx = new Identifier(JsonHelper.getString(json, "attribute"));
         EntityAttribute _snowmanxx = Registry.ATTRIBUTE.get(_snowmanx);
         if (_snowmanxx == null) {
            throw new JsonSyntaxException("Unknown attribute: " + _snowmanx);
         } else {
            EntityAttributeModifier.Operation _snowmanxxx = fromName(JsonHelper.getString(json, "operation"));
            UniformLootTableRange _snowmanxxxx = JsonHelper.deserialize(json, "amount", context, UniformLootTableRange.class);
            UUID _snowmanxxxxx = null;
            EquipmentSlot[] _snowmanxxxxxx;
            if (JsonHelper.hasString(json, "slot")) {
               _snowmanxxxxxx = new EquipmentSlot[]{EquipmentSlot.byName(JsonHelper.getString(json, "slot"))};
            } else {
               if (!JsonHelper.hasArray(json, "slot")) {
                  throw new JsonSyntaxException("Invalid or missing attribute modifier slot; must be either string or array of strings.");
               }

               JsonArray _snowmanxxxxxxx = JsonHelper.getArray(json, "slot");
               _snowmanxxxxxx = new EquipmentSlot[_snowmanxxxxxxx.size()];
               int _snowmanxxxxxxxx = 0;

               for (JsonElement _snowmanxxxxxxxxx : _snowmanxxxxxxx) {
                  _snowmanxxxxxx[_snowmanxxxxxxxx++] = EquipmentSlot.byName(JsonHelper.asString(_snowmanxxxxxxxxx, "slot"));
               }

               if (_snowmanxxxxxx.length == 0) {
                  throw new JsonSyntaxException("Invalid attribute modifier slot; must contain at least one entry.");
               }
            }

            if (json.has("id")) {
               String _snowmanxxxxxxx = JsonHelper.getString(json, "id");

               try {
                  _snowmanxxxxx = UUID.fromString(_snowmanxxxxxxx);
               } catch (IllegalArgumentException var13) {
                  throw new JsonSyntaxException("Invalid attribute modifier id '" + _snowmanxxxxxxx + "' (must be UUID format, with dashes)");
               }
            }

            return new SetAttributesLootFunction.Attribute(_snowman, _snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxxx, _snowmanxxxxx);
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

      public void toJson(JsonObject _snowman, SetAttributesLootFunction _snowman, JsonSerializationContext _snowman) {
         super.toJson(_snowman, _snowman, _snowman);
         JsonArray _snowmanxxx = new JsonArray();

         for (SetAttributesLootFunction.Attribute _snowmanxxxx : _snowman.attributes) {
            _snowmanxxx.add(_snowmanxxxx.serialize(_snowman));
         }

         _snowman.add("modifiers", _snowmanxxx);
      }

      public SetAttributesLootFunction fromJson(JsonObject _snowman, JsonDeserializationContext _snowman, LootCondition[] _snowman) {
         JsonArray _snowmanxxx = JsonHelper.getArray(_snowman, "modifiers");
         List<SetAttributesLootFunction.Attribute> _snowmanxxxx = Lists.newArrayListWithExpectedSize(_snowmanxxx.size());

         for (JsonElement _snowmanxxxxx : _snowmanxxx) {
            _snowmanxxxx.add(SetAttributesLootFunction.Attribute.deserialize(JsonHelper.asObject(_snowmanxxxxx, "modifier"), _snowman));
         }

         if (_snowmanxxxx.isEmpty()) {
            throw new JsonSyntaxException("Invalid attribute modifiers array; cannot be empty");
         } else {
            return new SetAttributesLootFunction(_snowman, _snowmanxxxx);
         }
      }
   }
}
