package net.minecraft.advancement.criterion;

import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class ItemDurabilityChangedCriterion extends AbstractCriterion<ItemDurabilityChangedCriterion.Conditions> {
   private static final Identifier ID = new Identifier("item_durability_changed");

   public ItemDurabilityChangedCriterion() {
   }

   @Override
   public Identifier getId() {
      return ID;
   }

   public ItemDurabilityChangedCriterion.Conditions conditionsFromJson(JsonObject _snowman, EntityPredicate.Extended _snowman, AdvancementEntityPredicateDeserializer _snowman) {
      ItemPredicate _snowmanxxx = ItemPredicate.fromJson(_snowman.get("item"));
      NumberRange.IntRange _snowmanxxxx = NumberRange.IntRange.fromJson(_snowman.get("durability"));
      NumberRange.IntRange _snowmanxxxxx = NumberRange.IntRange.fromJson(_snowman.get("delta"));
      return new ItemDurabilityChangedCriterion.Conditions(_snowman, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx);
   }

   public void trigger(ServerPlayerEntity player, ItemStack stack, int damage) {
      this.test(player, _snowmanxx -> _snowmanxx.matches(stack, damage));
   }

   public static class Conditions extends AbstractCriterionConditions {
      private final ItemPredicate item;
      private final NumberRange.IntRange durability;
      private final NumberRange.IntRange delta;

      public Conditions(EntityPredicate.Extended player, ItemPredicate item, NumberRange.IntRange durability, NumberRange.IntRange delta) {
         super(ItemDurabilityChangedCriterion.ID, player);
         this.item = item;
         this.durability = durability;
         this.delta = delta;
      }

      public static ItemDurabilityChangedCriterion.Conditions create(EntityPredicate.Extended _snowman, ItemPredicate _snowman, NumberRange.IntRange _snowman) {
         return new ItemDurabilityChangedCriterion.Conditions(_snowman, _snowman, _snowman, NumberRange.IntRange.ANY);
      }

      public boolean matches(ItemStack stack, int damage) {
         if (!this.item.test(stack)) {
            return false;
         } else {
            return !this.durability.test(stack.getMaxDamage() - damage) ? false : this.delta.test(stack.getDamage() - damage);
         }
      }

      @Override
      public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
         JsonObject _snowman = super.toJson(predicateSerializer);
         _snowman.add("item", this.item.toJson());
         _snowman.add("durability", this.durability.toJson());
         _snowman.add("delta", this.delta.toJson());
         return _snowman;
      }
   }
}
