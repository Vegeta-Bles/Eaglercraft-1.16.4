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

   public BeeNestDestroyedCriterion.Conditions conditionsFromJson(
      JsonObject jsonObject, EntityPredicate.Extended arg, AdvancementEntityPredicateDeserializer arg2
   ) {
      Block lv = getBlock(jsonObject);
      ItemPredicate lv2 = ItemPredicate.fromJson(jsonObject.get("item"));
      NumberRange.IntRange lv3 = NumberRange.IntRange.fromJson(jsonObject.get("num_bees_inside"));
      return new BeeNestDestroyedCriterion.Conditions(arg, lv, lv2, lv3);
   }

   @Nullable
   private static Block getBlock(JsonObject root) {
      if (root.has("block")) {
         Identifier lv = new Identifier(JsonHelper.getString(root, "block"));
         return Registry.BLOCK.getOrEmpty(lv).orElseThrow(() -> new JsonSyntaxException("Unknown block type '" + lv + "'"));
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
         JsonObject jsonObject = super.toJson(predicateSerializer);
         if (this.block != null) {
            jsonObject.addProperty("block", Registry.BLOCK.getId(this.block).toString());
         }

         jsonObject.add("item", this.item.toJson());
         jsonObject.add("num_bees_inside", this.beeCount.toJson());
         return jsonObject;
      }
   }
}
