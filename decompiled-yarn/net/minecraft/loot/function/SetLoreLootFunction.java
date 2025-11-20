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
      ListTag _snowman = this.getLoreForMerge(stack, !this.lore.isEmpty());
      if (_snowman != null) {
         if (this.replace) {
            _snowman.clear();
         }

         UnaryOperator<Text> _snowmanx = SetNameLootFunction.applySourceEntity(context, this.entity);
         this.lore.stream().map(_snowmanx).map(Text.Serializer::toJson).map(StringTag::of).forEach(_snowman::add);
      }

      return stack;
   }

   @Nullable
   private ListTag getLoreForMerge(ItemStack stack, boolean otherLoreExists) {
      CompoundTag _snowman;
      if (stack.hasTag()) {
         _snowman = stack.getTag();
      } else {
         if (!otherLoreExists) {
            return null;
         }

         _snowman = new CompoundTag();
         stack.setTag(_snowman);
      }

      CompoundTag _snowmanx;
      if (_snowman.contains("display", 10)) {
         _snowmanx = _snowman.getCompound("display");
      } else {
         if (!otherLoreExists) {
            return null;
         }

         _snowmanx = new CompoundTag();
         _snowman.put("display", _snowmanx);
      }

      if (_snowmanx.contains("Lore", 9)) {
         return _snowmanx.getList("Lore", 8);
      } else if (otherLoreExists) {
         ListTag _snowmanxx = new ListTag();
         _snowmanx.put("Lore", _snowmanxx);
         return _snowmanxx;
      } else {
         return null;
      }
   }

   public static class Serializer extends ConditionalLootFunction.Serializer<SetLoreLootFunction> {
      public Serializer() {
      }

      public void toJson(JsonObject _snowman, SetLoreLootFunction _snowman, JsonSerializationContext _snowman) {
         super.toJson(_snowman, _snowman, _snowman);
         _snowman.addProperty("replace", _snowman.replace);
         JsonArray _snowmanxxx = new JsonArray();

         for (Text _snowmanxxxx : _snowman.lore) {
            _snowmanxxx.add(Text.Serializer.toJsonTree(_snowmanxxxx));
         }

         _snowman.add("lore", _snowmanxxx);
         if (_snowman.entity != null) {
            _snowman.add("entity", _snowman.serialize(_snowman.entity));
         }
      }

      public SetLoreLootFunction fromJson(JsonObject _snowman, JsonDeserializationContext _snowman, LootCondition[] _snowman) {
         boolean _snowmanxxx = JsonHelper.getBoolean(_snowman, "replace", false);
         List<Text> _snowmanxxxx = Streams.stream(JsonHelper.getArray(_snowman, "lore")).map(Text.Serializer::fromJson).collect(ImmutableList.toImmutableList());
         LootContext.EntityTarget _snowmanxxxxx = JsonHelper.deserialize(_snowman, "entity", null, _snowman, LootContext.EntityTarget.class);
         return new SetLoreLootFunction(_snowman, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx);
      }
   }
}
