package net.minecraft.loot.function;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameter;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;

public class CopyStateFunction extends ConditionalLootFunction {
   private final Block block;
   private final Set<Property<?>> properties;

   private CopyStateFunction(LootCondition[] args, Block arg, Set<Property<?>> properties) {
      super(args);
      this.block = arg;
      this.properties = properties;
   }

   @Override
   public LootFunctionType getType() {
      return LootFunctionTypes.COPY_STATE;
   }

   @Override
   public Set<LootContextParameter<?>> getRequiredParameters() {
      return ImmutableSet.of(LootContextParameters.BLOCK_STATE);
   }

   @Override
   protected ItemStack process(ItemStack stack, LootContext context) {
      BlockState lv = context.get(LootContextParameters.BLOCK_STATE);
      if (lv != null) {
         CompoundTag lv2 = stack.getOrCreateTag();
         CompoundTag lv3;
         if (lv2.contains("BlockStateTag", 10)) {
            lv3 = lv2.getCompound("BlockStateTag");
         } else {
            lv3 = new CompoundTag();
            lv2.put("BlockStateTag", lv3);
         }

         this.properties.stream().filter(lv::contains).forEach(arg3 -> lv3.putString(arg3.getName(), method_21893(lv, (Property<?>)arg3)));
      }

      return stack;
   }

   public static CopyStateFunction.Builder getBuilder(Block arg) {
      return new CopyStateFunction.Builder(arg);
   }

   private static <T extends Comparable<T>> String method_21893(BlockState arg, Property<T> arg2) {
      T comparable = arg.get(arg2);
      return arg2.name(comparable);
   }

   public static class Builder extends ConditionalLootFunction.Builder<CopyStateFunction.Builder> {
      private final Block block;
      private final Set<Property<?>> properties = Sets.newHashSet();

      private Builder(Block arg) {
         this.block = arg;
      }

      public CopyStateFunction.Builder method_21898(Property<?> arg) {
         if (!this.block.getStateManager().getProperties().contains(arg)) {
            throw new IllegalStateException("Property " + arg + " is not present on block " + this.block);
         } else {
            this.properties.add(arg);
            return this;
         }
      }

      protected CopyStateFunction.Builder getThisBuilder() {
         return this;
      }

      @Override
      public LootFunction build() {
         return new CopyStateFunction(this.getConditions(), this.block, this.properties);
      }
   }

   public static class Serializer extends ConditionalLootFunction.Serializer<CopyStateFunction> {
      public Serializer() {
      }

      public void toJson(JsonObject jsonObject, CopyStateFunction arg, JsonSerializationContext jsonSerializationContext) {
         super.toJson(jsonObject, arg, jsonSerializationContext);
         jsonObject.addProperty("block", Registry.BLOCK.getId(arg.block).toString());
         JsonArray jsonArray = new JsonArray();
         arg.properties.forEach(argx -> jsonArray.add(argx.getName()));
         jsonObject.add("properties", jsonArray);
      }

      public CopyStateFunction fromJson(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, LootCondition[] args) {
         Identifier lv = new Identifier(JsonHelper.getString(jsonObject, "block"));
         Block lv2 = Registry.BLOCK.getOrEmpty(lv).orElseThrow(() -> new IllegalArgumentException("Can't find block " + lv));
         StateManager<Block, BlockState> lv3 = lv2.getStateManager();
         Set<Property<?>> set = Sets.newHashSet();
         JsonArray jsonArray = JsonHelper.getArray(jsonObject, "properties", null);
         if (jsonArray != null) {
            jsonArray.forEach(jsonElement -> set.add(lv3.getProperty(JsonHelper.asString(jsonElement, "property"))));
         }

         return new CopyStateFunction(args, lv2, set);
      }
   }
}
