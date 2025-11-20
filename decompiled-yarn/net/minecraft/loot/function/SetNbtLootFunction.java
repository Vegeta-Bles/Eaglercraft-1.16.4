package net.minecraft.loot.function;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.util.JsonHelper;

public class SetNbtLootFunction extends ConditionalLootFunction {
   private final CompoundTag tag;

   private SetNbtLootFunction(LootCondition[] conditions, CompoundTag tag) {
      super(conditions);
      this.tag = tag;
   }

   @Override
   public LootFunctionType getType() {
      return LootFunctionTypes.SET_NBT;
   }

   @Override
   public ItemStack process(ItemStack stack, LootContext context) {
      stack.getOrCreateTag().copyFrom(this.tag);
      return stack;
   }

   public static ConditionalLootFunction.Builder<?> builder(CompoundTag tag) {
      return builder(conditions -> new SetNbtLootFunction(conditions, tag));
   }

   public static class Serializer extends ConditionalLootFunction.Serializer<SetNbtLootFunction> {
      public Serializer() {
      }

      public void toJson(JsonObject _snowman, SetNbtLootFunction _snowman, JsonSerializationContext _snowman) {
         super.toJson(_snowman, _snowman, _snowman);
         _snowman.addProperty("tag", _snowman.tag.toString());
      }

      public SetNbtLootFunction fromJson(JsonObject _snowman, JsonDeserializationContext _snowman, LootCondition[] _snowman) {
         try {
            CompoundTag _snowmanxxx = StringNbtReader.parse(JsonHelper.getString(_snowman, "tag"));
            return new SetNbtLootFunction(_snowman, _snowmanxxx);
         } catch (CommandSyntaxException var5) {
            throw new JsonSyntaxException(var5.getMessage());
         }
      }
   }
}
