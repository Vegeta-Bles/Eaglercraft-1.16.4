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

   public ItemDurabilityChangedCriterion.Conditions conditionsFromJson(
      JsonObject jsonObject, EntityPredicate.Extended arg, AdvancementEntityPredicateDeserializer arg2
   ) {
      ItemPredicate lv = ItemPredicate.fromJson(jsonObject.get("item"));
      NumberRange.IntRange lv2 = NumberRange.IntRange.fromJson(jsonObject.get("durability"));
      NumberRange.IntRange lv3 = NumberRange.IntRange.fromJson(jsonObject.get("delta"));
      return new ItemDurabilityChangedCriterion.Conditions(arg, lv, lv2, lv3);
   }

   public void trigger(ServerPlayerEntity player, ItemStack stack, int damage) {
      this.test(player, arg2 -> arg2.matches(stack, damage));
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

      public static ItemDurabilityChangedCriterion.Conditions create(EntityPredicate.Extended arg, ItemPredicate arg2, NumberRange.IntRange arg3) {
         return new ItemDurabilityChangedCriterion.Conditions(arg, arg2, arg3, NumberRange.IntRange.ANY);
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
         JsonObject jsonObject = super.toJson(predicateSerializer);
         jsonObject.add("item", this.item.toJson());
         jsonObject.add("durability", this.durability.toJson());
         jsonObject.add("delta", this.delta.toJson());
         return jsonObject;
      }
   }
}
