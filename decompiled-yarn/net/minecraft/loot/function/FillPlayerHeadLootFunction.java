package net.minecraft.loot.function;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.mojang.authlib.GameProfile;
import java.util.Set;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.util.JsonHelper;

public class FillPlayerHeadLootFunction extends ConditionalLootFunction {
   private final LootContext.EntityTarget entity;

   public FillPlayerHeadLootFunction(LootCondition[] conditions, LootContext.EntityTarget entity) {
      super(conditions);
      this.entity = entity;
   }

   @Override
   public LootFunctionType getType() {
      return LootFunctionTypes.FILL_PLAYER_HEAD;
   }

   @Override
   public Set<LootContextParameter<?>> getRequiredParameters() {
      return ImmutableSet.of(this.entity.getParameter());
   }

   @Override
   public ItemStack process(ItemStack stack, LootContext context) {
      if (stack.getItem() == Items.PLAYER_HEAD) {
         Entity _snowman = context.get(this.entity.getParameter());
         if (_snowman instanceof PlayerEntity) {
            GameProfile _snowmanx = ((PlayerEntity)_snowman).getGameProfile();
            stack.getOrCreateTag().put("SkullOwner", NbtHelper.fromGameProfile(new CompoundTag(), _snowmanx));
         }
      }

      return stack;
   }

   public static class Serializer extends ConditionalLootFunction.Serializer<FillPlayerHeadLootFunction> {
      public Serializer() {
      }

      public void toJson(JsonObject _snowman, FillPlayerHeadLootFunction _snowman, JsonSerializationContext _snowman) {
         super.toJson(_snowman, _snowman, _snowman);
         _snowman.add("entity", _snowman.serialize(_snowman.entity));
      }

      public FillPlayerHeadLootFunction fromJson(JsonObject _snowman, JsonDeserializationContext _snowman, LootCondition[] _snowman) {
         LootContext.EntityTarget _snowmanxxx = JsonHelper.deserialize(_snowman, "entity", _snowman, LootContext.EntityTarget.class);
         return new FillPlayerHeadLootFunction(_snowman, _snowmanxxx);
      }
   }
}
