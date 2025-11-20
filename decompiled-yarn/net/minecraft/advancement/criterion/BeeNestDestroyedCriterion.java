package net.minecraft.advancement.criterion;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;

public class BeeNestDestroyedCriterion extends AbstractCriterion<BeeNestDestroyedCriterion.Conditions> {
   private static final Identifier ID = new Identifier("bee_nest_destroyed");

   public BeeNestDestroyedCriterion() {
   }

   @Override
   public Identifier getId() {
      return ID;
   }

   public BeeNestDestroyedCriterion.Conditions conditionsFromJson(JsonObject _snowman, EntityPredicate.Extended _snowman, AdvancementEntityPredicateDeserializer _snowman) {
      Block _snowmanxxx = getBlock(_snowman);
      ItemPredicate _snowmanxxxx = ItemPredicate.fromJson(_snowman.get("item"));
      NumberRange.IntRange _snowmanxxxxx = NumberRange.IntRange.fromJson(_snowman.get("num_bees_inside"));
      return new BeeNestDestroyedCriterion.Conditions(_snowman, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx);
   }

   @Nullable
   private static Block getBlock(JsonObject root) {
      if (root.has("block")) {
         Identifier _snowman = new Identifier(JsonHelper.getString(root, "block"));
         return Registry.BLOCK.getOrEmpty(_snowman).orElseThrow(() -> new JsonSyntaxException("Unknown block type '" + _snowman + "'"));
      } else {
         return null;
      }
   }

   public void test(ServerPlayerEntity player, Block block, ItemStack stack, int beeCount) {
      this.test(player, conditions -> conditions.test(block, stack, beeCount));
   }

   public static class Conditions extends AbstractCriterionConditions {
      @Nullable
      private final Block block;
      private final ItemPredicate item;
      private final NumberRange.IntRange beeCount;

      public Conditions(EntityPredicate.Extended player, @Nullable Block block, ItemPredicate item, NumberRange.IntRange beeCount) {
         super(BeeNestDestroyedCriterion.ID, player);
         this.block = block;
         this.item = item;
         this.beeCount = beeCount;
      }

      public static BeeNestDestroyedCriterion.Conditions create(Block block, ItemPredicate.Builder itemPredicateBuilder, NumberRange.IntRange beeCountRange) {
         return new BeeNestDestroyedCriterion.Conditions(EntityPredicate.Extended.EMPTY, block, itemPredicateBuilder.build(), beeCountRange);
      }

      public boolean test(Block block, ItemStack stack, int count) {
         if (this.block != null && block != this.block) {
            return false;
         } else {
            return !this.item.test(stack) ? false : this.beeCount.test(count);
         }
      }

      @Override
      public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
         JsonObject _snowman = super.toJson(predicateSerializer);
         if (this.block != null) {
            _snowman.addProperty("block", Registry.BLOCK.getId(this.block).toString());
         }

         _snowman.add("item", this.item.toJson());
         _snowman.add("num_bees_inside", this.beeCount.toJson());
         return _snowman;
      }
   }
}
