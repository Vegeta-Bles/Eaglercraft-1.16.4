package net.minecraft.advancement.criterion;

import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class FilledBucketCriterion extends AbstractCriterion<FilledBucketCriterion.Conditions> {
   private static final Identifier ID = new Identifier("filled_bucket");

   public FilledBucketCriterion() {
   }

   @Override
   public Identifier getId() {
      return ID;
   }

   public FilledBucketCriterion.Conditions conditionsFromJson(JsonObject _snowman, EntityPredicate.Extended _snowman, AdvancementEntityPredicateDeserializer _snowman) {
      ItemPredicate _snowmanxxx = ItemPredicate.fromJson(_snowman.get("item"));
      return new FilledBucketCriterion.Conditions(_snowman, _snowmanxxx);
   }

   public void trigger(ServerPlayerEntity player, ItemStack stack) {
      this.test(player, _snowmanx -> _snowmanx.matches(stack));
   }

   public static class Conditions extends AbstractCriterionConditions {
      private final ItemPredicate item;

      public Conditions(EntityPredicate.Extended player, ItemPredicate item) {
         super(FilledBucketCriterion.ID, player);
         this.item = item;
      }

      public static FilledBucketCriterion.Conditions create(ItemPredicate item) {
         return new FilledBucketCriterion.Conditions(EntityPredicate.Extended.EMPTY, item);
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
