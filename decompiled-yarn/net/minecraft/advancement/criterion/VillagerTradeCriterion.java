package net.minecraft.advancement.criterion;

import com.google.gson.JsonObject;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class VillagerTradeCriterion extends AbstractCriterion<VillagerTradeCriterion.Conditions> {
   private static final Identifier ID = new Identifier("villager_trade");

   public VillagerTradeCriterion() {
   }

   @Override
   public Identifier getId() {
      return ID;
   }

   public VillagerTradeCriterion.Conditions conditionsFromJson(JsonObject _snowman, EntityPredicate.Extended _snowman, AdvancementEntityPredicateDeserializer _snowman) {
      EntityPredicate.Extended _snowmanxxx = EntityPredicate.Extended.getInJson(_snowman, "villager", _snowman);
      ItemPredicate _snowmanxxxx = ItemPredicate.fromJson(_snowman.get("item"));
      return new VillagerTradeCriterion.Conditions(_snowman, _snowmanxxx, _snowmanxxxx);
   }

   public void handle(ServerPlayerEntity player, MerchantEntity merchant, ItemStack stack) {
      LootContext _snowman = EntityPredicate.createAdvancementEntityLootContext(player, merchant);
      this.test(player, _snowmanxx -> _snowmanxx.matches(_snowman, stack));
   }

   public static class Conditions extends AbstractCriterionConditions {
      private final EntityPredicate.Extended villager;
      private final ItemPredicate item;

      public Conditions(EntityPredicate.Extended player, EntityPredicate.Extended villager, ItemPredicate item) {
         super(VillagerTradeCriterion.ID, player);
         this.villager = villager;
         this.item = item;
      }

      public static VillagerTradeCriterion.Conditions any() {
         return new VillagerTradeCriterion.Conditions(EntityPredicate.Extended.EMPTY, EntityPredicate.Extended.EMPTY, ItemPredicate.ANY);
      }

      public boolean matches(LootContext merchantContext, ItemStack stack) {
         return !this.villager.test(merchantContext) ? false : this.item.test(stack);
      }

      @Override
      public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
         JsonObject _snowman = super.toJson(predicateSerializer);
         _snowman.add("item", this.item.toJson());
         _snowman.add("villager", this.villager.toJson(predicateSerializer));
         return _snowman;
      }
   }
}
