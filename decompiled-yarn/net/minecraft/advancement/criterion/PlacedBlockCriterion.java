package net.minecraft.advancement.criterion;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

public class PlacedBlockCriterion extends AbstractCriterion<PlacedBlockCriterion.Conditions> {
   private static final Identifier ID = new Identifier("placed_block");

   public PlacedBlockCriterion() {
   }

   @Override
   public Identifier getId() {
      return ID;
   }

   public PlacedBlockCriterion.Conditions conditionsFromJson(JsonObject _snowman, EntityPredicate.Extended _snowman, AdvancementEntityPredicateDeserializer _snowman) {
      Block _snowmanxxx = getBlock(_snowman);
      StatePredicate _snowmanxxxx = StatePredicate.fromJson(_snowman.get("state"));
      if (_snowmanxxx != null) {
         _snowmanxxxx.check(_snowmanxxx.getStateManager(), name -> {
            throw new JsonSyntaxException("Block " + _snowman + " has no property " + name + ":");
         });
      }

      LocationPredicate _snowmanxxxxx = LocationPredicate.fromJson(_snowman.get("location"));
      ItemPredicate _snowmanxxxxxx = ItemPredicate.fromJson(_snowman.get("item"));
      return new PlacedBlockCriterion.Conditions(_snowman, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx);
   }

   @Nullable
   private static Block getBlock(JsonObject obj) {
      if (obj.has("block")) {
         Identifier _snowman = new Identifier(JsonHelper.getString(obj, "block"));
         return Registry.BLOCK.getOrEmpty(_snowman).orElseThrow(() -> new JsonSyntaxException("Unknown block type '" + _snowman + "'"));
      } else {
         return null;
      }
   }

   public void trigger(ServerPlayerEntity player, BlockPos blockPos, ItemStack stack) {
      BlockState _snowman = player.getServerWorld().getBlockState(blockPos);
      this.test(player, _snowmanxxxx -> _snowmanxxxx.matches(_snowman, blockPos, player.getServerWorld(), stack));
   }

   public static class Conditions extends AbstractCriterionConditions {
      private final Block block;
      private final StatePredicate state;
      private final LocationPredicate location;
      private final ItemPredicate item;

      public Conditions(EntityPredicate.Extended player, @Nullable Block block, StatePredicate state, LocationPredicate location, ItemPredicate item) {
         super(PlacedBlockCriterion.ID, player);
         this.block = block;
         this.state = state;
         this.location = location;
         this.item = item;
      }

      public static PlacedBlockCriterion.Conditions block(Block block) {
         return new PlacedBlockCriterion.Conditions(EntityPredicate.Extended.EMPTY, block, StatePredicate.ANY, LocationPredicate.ANY, ItemPredicate.ANY);
      }

      public boolean matches(BlockState state, BlockPos pos, ServerWorld world, ItemStack stack) {
         if (this.block != null && !state.isOf(this.block)) {
            return false;
         } else if (!this.state.test(state)) {
            return false;
         } else {
            return !this.location.test(world, (float)pos.getX(), (float)pos.getY(), (float)pos.getZ()) ? false : this.item.test(stack);
         }
      }

      @Override
      public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
         JsonObject _snowman = super.toJson(predicateSerializer);
         if (this.block != null) {
            _snowman.addProperty("block", Registry.BLOCK.getId(this.block).toString());
         }

         _snowman.add("state", this.state.toJson());
         _snowman.add("location", this.location.toJson());
         _snowman.add("item", this.item.toJson());
         return _snowman;
      }
   }
}
