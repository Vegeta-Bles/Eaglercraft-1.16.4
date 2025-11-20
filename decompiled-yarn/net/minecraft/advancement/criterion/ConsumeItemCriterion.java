package net.minecraft.advancement.criterion;

import com.google.gson.JsonObject;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.NbtPredicate;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class ConsumeItemCriterion extends AbstractCriterion<ConsumeItemCriterion.Conditions> {
   private static final Identifier ID = new Identifier("consume_item");

   public ConsumeItemCriterion() {
   }

   @Override
   public Identifier getId() {
      return ID;
   }

   public ConsumeItemCriterion.Conditions conditionsFromJson(JsonObject _snowman, EntityPredicate.Extended _snowman, AdvancementEntityPredicateDeserializer _snowman) {
      return new ConsumeItemCriterion.Conditions(_snowman, ItemPredicate.fromJson(_snowman.get("item")));
   }

   public void trigger(ServerPlayerEntity player, ItemStack stack) {
      this.test(player, _snowmanx -> _snowmanx.matches(stack));
   }

   public static class Conditions extends AbstractCriterionConditions {
      private final ItemPredicate item;

      public Conditions(EntityPredicate.Extended player, ItemPredicate item) {
         super(ConsumeItemCriterion.ID, player);
         this.item = item;
      }

      public static ConsumeItemCriterion.Conditions any() {
         return new ConsumeItemCriterion.Conditions(EntityPredicate.Extended.EMPTY, ItemPredicate.ANY);
      }

      public static ConsumeItemCriterion.Conditions item(ItemConvertible item) {
         return new ConsumeItemCriterion.Conditions(
            EntityPredicate.Extended.EMPTY,
            new ItemPredicate(
               null,
               item.asItem(),
               NumberRange.IntRange.ANY,
               NumberRange.IntRange.ANY,
               EnchantmentPredicate.ARRAY_OF_ANY,
               EnchantmentPredicate.ARRAY_OF_ANY,
               null,
               NbtPredicate.ANY
            )
         );
      }

      public boolean matches(ItemStack stack) {
         return this.item.test(stack);
      }

      @Override
      public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
         JsonObject _snowman = super.toJson(predicateSerializer);
         _snowman.add("item", this.item.toJson());
         return _snowman;
      }
   }
}
