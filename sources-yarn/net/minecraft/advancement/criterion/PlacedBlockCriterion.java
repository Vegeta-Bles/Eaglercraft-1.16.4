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

   public PlacedBlockCriterion.Conditions conditionsFromJson(JsonObject jsonObject, EntityPredicate.Extended arg, AdvancementEntityPredicateDeserializer arg2) {
      Block lv = getBlock(jsonObject);
      StatePredicate lv2 = StatePredicate.fromJson(jsonObject.get("state"));
      if (lv != null) {
         lv2.check(lv.getStateManager(), name -> {
            throw new JsonSyntaxException("Block " + lv + " has no property " + name + ":");
         });
      }

      LocationPredicate lv3 = LocationPredicate.fromJson(jsonObject.get("location"));
      ItemPredicate lv4 = ItemPredicate.fromJson(jsonObject.get("item"));
      return new PlacedBlockCriterion.Conditions(arg, lv, lv2, lv3, lv4);
   }

   @Nullable
   private static Block getBlock(JsonObject obj) {
      if (obj.has("block")) {
         Identifier lv = new Identifier(JsonHelper.getString(obj, "block"));
         return Registry.BLOCK.getOrEmpty(lv).orElseThrow(() -> new JsonSyntaxException("Unknown block type '" + lv + "'"));
      } else {
         return null;
      }
   }

   public void trigger(ServerPlayerEntity player, BlockPos blockPos, ItemStack stack) {
      BlockState lv = player.getServerWorld().getBlockState(blockPos);
      this.test(player, arg5 -> arg5.matches(lv, blockPos, player.getServerWorld(), stack));
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
         JsonObject jsonObject = super.toJson(predicateSerializer);
         if (this.block != null) {
            jsonObject.addProperty("block", Registry.BLOCK.getId(this.block).toString());
         }

         jsonObject.add("state", this.state.toJson());
         jsonObject.add("location", this.location.toJson());
         jsonObject.add("item", this.item.toJson());
         return jsonObject;
      }
   }
}
