package net.minecraft.advancement.criterion;

import com.google.gson.JsonObject;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class ThrownItemPickedUpByEntityCriterion extends AbstractCriterion<ThrownItemPickedUpByEntityCriterion.Conditions> {
   private static final Identifier ID = new Identifier("thrown_item_picked_up_by_entity");

   public ThrownItemPickedUpByEntityCriterion() {
   }

   @Override
   public Identifier getId() {
      return ID;
   }

   protected ThrownItemPickedUpByEntityCriterion.Conditions conditionsFromJson(
      JsonObject _snowman, EntityPredicate.Extended _snowman, AdvancementEntityPredicateDeserializer _snowman
   ) {
      ItemPredicate _snowmanxxx = ItemPredicate.fromJson(_snowman.get("item"));
      EntityPredicate.Extended _snowmanxxxx = EntityPredicate.Extended.getInJson(_snowman, "entity", _snowman);
      return new ThrownItemPickedUpByEntityCriterion.Conditions(_snowman, _snowmanxxx, _snowmanxxxx);
   }

   public void trigger(ServerPlayerEntity player, ItemStack stack, Entity entity) {
      LootContext _snowman = EntityPredicate.createAdvancementEntityLootContext(player, entity);
      this.test(player, _snowmanxxx -> _snowmanxxx.test(player, stack, _snowman));
   }

   public static class Conditions extends AbstractCriterionConditions {
      private final ItemPredicate item;
      private final EntityPredicate.Extended entity;

      public Conditions(EntityPredicate.Extended _snowman, ItemPredicate item, EntityPredicate.Extended entity) {
         super(ThrownItemPickedUpByEntityCriterion.ID, _snowman);
         this.item = item;
         this.entity = entity;
      }

      public static ThrownItemPickedUpByEntityCriterion.Conditions create(EntityPredicate.Extended _snowman, ItemPredicate.Builder _snowman, EntityPredicate.Extended _snowman) {
         return new ThrownItemPickedUpByEntityCriterion.Conditions(_snowman, _snowman.build(), _snowman);
      }

      public boolean test(ServerPlayerEntity player, ItemStack stack, LootContext _snowman) {
         return !this.item.test(stack) ? false : this.entity.test(_snowman);
      }

      @Override
      public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
         JsonObject _snowman = super.toJson(predicateSerializer);
         _snowman.add("item", this.item.toJson());
         _snowman.add("entity", this.entity.toJson(predicateSerializer));
         return _snowman;
      }
   }
}
