package net.minecraft.advancement.criterion;

import com.google.gson.JsonObject;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class ItemUsedOnBlockCriterion extends AbstractCriterion<ItemUsedOnBlockCriterion.Conditions> {
   private static final Identifier ID = new Identifier("item_used_on_block");

   public ItemUsedOnBlockCriterion() {
   }

   @Override
   public Identifier getId() {
      return ID;
   }

   public ItemUsedOnBlockCriterion.Conditions conditionsFromJson(JsonObject _snowman, EntityPredicate.Extended _snowman, AdvancementEntityPredicateDeserializer _snowman) {
      LocationPredicate _snowmanxxx = LocationPredicate.fromJson(_snowman.get("location"));
      ItemPredicate _snowmanxxxx = ItemPredicate.fromJson(_snowman.get("item"));
      return new ItemUsedOnBlockCriterion.Conditions(_snowman, _snowmanxxx, _snowmanxxxx);
   }

   public void test(ServerPlayerEntity player, BlockPos pos, ItemStack stack) {
      BlockState _snowman = player.getServerWorld().getBlockState(pos);
      this.test(player, conditions -> conditions.test(_snowman, player.getServerWorld(), pos, stack));
   }

   public static class Conditions extends AbstractCriterionConditions {
      private final LocationPredicate location;
      private final ItemPredicate item;

      public Conditions(EntityPredicate.Extended _snowman, LocationPredicate location, ItemPredicate item) {
         super(ItemUsedOnBlockCriterion.ID, _snowman);
         this.location = location;
         this.item = item;
      }

      public static ItemUsedOnBlockCriterion.Conditions create(LocationPredicate.Builder _snowman, ItemPredicate.Builder _snowman) {
         return new ItemUsedOnBlockCriterion.Conditions(EntityPredicate.Extended.EMPTY, _snowman.build(), _snowman.build());
      }

      public boolean test(BlockState state, ServerWorld world, BlockPos pos, ItemStack stack) {
         return !this.location.test(world, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5) ? false : this.item.test(stack);
      }

      @Override
      public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
         JsonObject _snowman = super.toJson(predicateSerializer);
         _snowman.add("location", this.location.toJson());
         _snowman.add("item", this.item.toJson());
         return _snowman;
      }
   }
}
