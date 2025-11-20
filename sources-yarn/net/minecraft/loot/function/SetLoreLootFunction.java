package net.minecraft.loot.function;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Streams;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.List;
import java.util.Set;
import java.util.function.UnaryOperator;
import javax.annotation.Nullable;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.text.Text;
import net.minecraft.util.JsonHelper;

public class SetLoreLootFunction extends ConditionalLootFunction {
   private final boolean replace;
   private final List<Text> lore;
   @Nullable
   private final LootContext.EntityTarget entity;

   public SetLoreLootFunction(LootCondition[] conditions, boolean replace, List<Text> lore, @Nullable LootContext.EntityTarget entity) {
      super(conditions);
      this.replace = replace;
      this.lore = ImmutableList.copyOf(lore);
      this.entity = entity;
   }

   @Override
   public LootFunctionType getType() {
      return LootFunctionTypes.SET_LORE;
   }

   @Override
   public Set<LootContextParameter<?>> getRequiredParameters() {
      return this.entity != null ? ImmutableSet.of(this.entity.getParameter()) : ImmutableSet.of();
   }

   @Override
   public ItemStack process(ItemStack stack, LootContext context) {
      ListTag lv = this.getLoreForMerge(stack, !this.lore.isEmpty());
      if (lv != null) {
         if (this.replace) {
            lv.clear();
         }

         UnaryOperator<Text> unaryOperator = SetNameLootFunction.applySourceEntity(context, this.entity);
         this.lore.stream().map(unaryOperator).map(Text.Serializer::toJson).map(StringTag::of).forEach(lv::add);
      }

      return stack;
   }

   @Nullable
   private ListTag getLoreForMerge(ItemStack stack, boolean otherLoreExists) {
      CompoundTag lv;
      if (stack.hasTag()) {
         lv = stack.getTag();
      } else {
         if (!otherLoreExists) {
            return null;
         }

         lv = new CompoundTag();
         stack.setTag(lv);
      }

      CompoundTag lv4;
      if (lv.contains("display", 10)) {
         lv4 = lv.getCompound("display");
      } else {
         if (!otherLoreExists) {
            return null;
         }

         lv4 = new CompoundTag();
         lv.put("display", lv4);
      }

      if (lv4.contains("Lore", 9)) {
         return lv4.getList("Lore", 8);
      } else if (otherLoreExists) {
         ListTag lv7 = new ListTag();
         lv4.put("Lore", lv7);
         return lv7;
      } else {
         return null;
      }
   }

   public static class Serializer extends ConditionalLootFunction.Serializer<SetLoreLootFunction> {
      public Serializer() {
      }

      public void toJson(JsonObject jsonObject, SetLoreLootFunction arg, JsonSerializationContext jsonSerializationContext) {
         super.toJson(jsonObject, arg, jsonSerializationContext);
         jsonObject.addProperty("replace", arg.replace);
         JsonArray jsonArray = new JsonArray();

         for (Text lv : arg.lore) {
            jsonArray.add(Text.Serializer.toJsonTree(lv));
         }

         jsonObject.add("lore", jsonArray);
         if (arg.entity != null) {
            jsonObject.add("entity", jsonSerializationContext.serialize(arg.entity));
         }
      }

      public SetLoreLootFunction fromJson(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, LootCondition[] args) {
         boolean bl = JsonHelper.getBoolean(jsonObject, "replace", false);
         List<Text> list = Streams.stream(JsonHelper.getArray(jsonObject, "lore")).map(Text.Serializer::fromJson).collect(ImmutableList.toImmutableList());
         LootContext.EntityTarget lv = JsonHelper.deserialize(jsonObject, "entity", null, jsonDeserializationContext, LootContext.EntityTarget.class);
         return new SetLoreLootFunction(args, bl, list, lv);
      }
   }
}
