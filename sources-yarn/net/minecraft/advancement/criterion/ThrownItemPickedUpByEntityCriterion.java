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
      JsonObject jsonObject, EntityPredicate.Extended arg, AdvancementEntityPredicateDeserializer arg2
   ) {
      ItemPredicate lv = ItemPredicate.fromJson(jsonObject.get("item"));
      EntityPredicate.Extended lv2 = EntityPredicate.Extended.getInJson(jsonObject, "entity", arg2);
      return new ThrownItemPickedUpByEntityCriterion.Conditions(arg, lv, lv2);
   }

   public void trigger(ServerPlayerEntity player, ItemStack stack, Entity entity) {
      LootContext lv = EntityPredicate.createAdvancementEntityLootContext(player, entity);
      this.test(player, arg4 -> arg4.test(player, stack, lv));
   }

   public static class Conditions extends AbstractCriterionConditions {
      private final ItemPredicate item;
      private final EntityPredicate.Extended entity;

      public Conditions(EntityPredicate.Extended arg, ItemPredicate item, EntityPredicate.Extended entity) {
         super(ThrownItemPickedUpByEntityCriterion.ID, arg);
         this.item = item;
         this.entity = entity;
      }

      public static ThrownItemPickedUpByEntityCriterion.Conditions create(
         EntityPredicate.Extended arg, ItemPredicate.Builder arg2, EntityPredicate.Extended arg3
      ) {
         return new ThrownItemPickedUpByEntityCriterion.Conditions(arg, arg2.build(), arg3);
      }

      public boolean test(ServerPlayerEntity player, ItemStack stack, LootContext arg3) {
         return !this.item.test(stack) ? false : this.entity.test(arg3);
      }

      @Override
      public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
         JsonObject jsonObject = super.toJson(predicateSerializer);
         jsonObject.add("item", this.item.toJson());
         jsonObject.add("entity", this.entity.toJson(predicateSerializer));
         return jsonObject;
      }
   }
}
