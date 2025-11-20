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

   private CopyStateFunction(LootCondition[] _snowman, Block _snowman, Set<Property<?>> properties) {
      super(_snowman);
      this.block = _snowman;
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
      BlockState _snowman = context.get(LootContextParameters.BLOCK_STATE);
      if (_snowman != null) {
         CompoundTag _snowmanx = stack.getOrCreateTag();
         CompoundTag _snowmanxx;
         if (_snowmanx.contains("BlockStateTag", 10)) {
            _snowmanxx = _snowmanx.getCompound("BlockStateTag");
         } else {
            _snowmanxx = new CompoundTag();
            _snowmanx.put("BlockStateTag", _snowmanxx);
         }

         this.properties.stream().filter(_snowman::contains).forEach(_snowmanxxx -> _snowman.putString(_snowmanxxx.getName(), method_21893(_snowman, (Property<?>)_snowmanxxx)));
      }

      return stack;
   }

   public static CopyStateFunction.Builder getBuilder(Block _snowman) {
      return new CopyStateFunction.Builder(_snowman);
   }

   private static <T extends Comparable<T>> String method_21893(BlockState _snowman, Property<T> _snowman) {
      T _snowmanxx = _snowman.get(_snowman);
      return _snowman.name(_snowmanxx);
   }

   public static class Builder extends ConditionalLootFunction.Builder<CopyStateFunction.Builder> {
      private final Block block;
      private final Set<Property<?>> properties = Sets.newHashSet();

      private Builder(Block _snowman) {
         this.block = _snowman;
      }

      public CopyStateFunction.Builder method_21898(Property<?> _snowman) {
         if (!this.block.getStateManager().getProperties().contains(_snowman)) {
            throw new IllegalStateException("Property " + _snowman + " is not present on block " + this.block);
         } else {
            this.properties.add(_snowman);
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

      public void toJson(JsonObject _snowman, CopyStateFunction _snowman, JsonSerializationContext _snowman) {
         super.toJson(_snowman, _snowman, _snowman);
         _snowman.addProperty("block", Registry.BLOCK.getId(_snowman.block).toString());
         JsonArray _snowmanxxx = new JsonArray();
         _snowman.properties.forEach(_snowmanxxxx -> _snowman.add(_snowmanxxxx.getName()));
         _snowman.add("properties", _snowmanxxx);
      }

      public CopyStateFunction fromJson(JsonObject _snowman, JsonDeserializationContext _snowman, LootCondition[] _snowman) {
         Identifier _snowmanxxx = new Identifier(JsonHelper.getString(_snowman, "block"));
         Block _snowmanxxxx = Registry.BLOCK.getOrEmpty(_snowmanxxx).orElseThrow(() -> new IllegalArgumentException("Can't find block " + _snowman));
         StateManager<Block, BlockState> _snowmanxxxxx = _snowmanxxxx.getStateManager();
         Set<Property<?>> _snowmanxxxxxx = Sets.newHashSet();
         JsonArray _snowmanxxxxxxx = JsonHelper.getArray(_snowman, "properties", null);
         if (_snowmanxxxxxxx != null) {
            _snowmanxxxxxxx.forEach(_snowmanxxxxxxxx -> _snowman.add(_snowman.getProperty(JsonHelper.asString(_snowmanxxxxxxxx, "property"))));
         }

         return new CopyStateFunction(_snowman, _snowmanxxxx, _snowmanxxxxxx);
      }
   }
}
