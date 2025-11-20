package net.minecraft.loot.condition;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameter;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.JsonSerializer;
import net.minecraft.util.registry.Registry;

public class BlockStatePropertyLootCondition implements LootCondition {
   private final Block block;
   private final StatePredicate properties;

   private BlockStatePropertyLootCondition(Block block, StatePredicate properties) {
      this.block = block;
      this.properties = properties;
   }

   @Override
   public LootConditionType getType() {
      return LootConditionTypes.BLOCK_STATE_PROPERTY;
   }

   @Override
   public Set<LootContextParameter<?>> getRequiredParameters() {
      return ImmutableSet.of(LootContextParameters.BLOCK_STATE);
   }

   public boolean test(LootContext _snowman) {
      BlockState _snowmanx = _snowman.get(LootContextParameters.BLOCK_STATE);
      return _snowmanx != null && this.block == _snowmanx.getBlock() && this.properties.test(_snowmanx);
   }

   public static BlockStatePropertyLootCondition.Builder builder(Block block) {
      return new BlockStatePropertyLootCondition.Builder(block);
   }

   public static class Builder implements LootCondition.Builder {
      private final Block block;
      private StatePredicate propertyValues = StatePredicate.ANY;

      public Builder(Block block) {
         this.block = block;
      }

      public BlockStatePropertyLootCondition.Builder properties(StatePredicate.Builder _snowman) {
         this.propertyValues = _snowman.build();
         return this;
      }

      @Override
      public LootCondition build() {
         return new BlockStatePropertyLootCondition(this.block, this.propertyValues);
      }
   }

   public static class Serializer implements JsonSerializer<BlockStatePropertyLootCondition> {
      public Serializer() {
      }

      public void toJson(JsonObject _snowman, BlockStatePropertyLootCondition _snowman, JsonSerializationContext _snowman) {
         _snowman.addProperty("block", Registry.BLOCK.getId(_snowman.block).toString());
         _snowman.add("properties", _snowman.properties.toJson());
      }

      public BlockStatePropertyLootCondition fromJson(JsonObject _snowman, JsonDeserializationContext _snowman) {
         Identifier _snowmanxx = new Identifier(JsonHelper.getString(_snowman, "block"));
         Block _snowmanxxx = Registry.BLOCK.getOrEmpty(_snowmanxx).orElseThrow(() -> new IllegalArgumentException("Can't find block " + _snowman));
         StatePredicate _snowmanxxxx = StatePredicate.fromJson(_snowman.get("properties"));
         _snowmanxxxx.check(_snowmanxxx.getStateManager(), _snowmanxxxxx -> {
            throw new JsonSyntaxException("Block " + _snowman + " has no property " + _snowmanxxxxx);
         });
         return new BlockStatePropertyLootCondition(_snowmanxxx, _snowmanxxxx);
      }
   }
}
