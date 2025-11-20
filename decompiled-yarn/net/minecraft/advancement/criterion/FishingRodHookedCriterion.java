package net.minecraft.advancement.criterion;

import com.google.gson.JsonObject;
import java.util.Collection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class FishingRodHookedCriterion extends AbstractCriterion<FishingRodHookedCriterion.Conditions> {
   private static final Identifier ID = new Identifier("fishing_rod_hooked");

   public FishingRodHookedCriterion() {
   }

   @Override
   public Identifier getId() {
      return ID;
   }

   public FishingRodHookedCriterion.Conditions conditionsFromJson(JsonObject _snowman, EntityPredicate.Extended _snowman, AdvancementEntityPredicateDeserializer _snowman) {
      ItemPredicate _snowmanxxx = ItemPredicate.fromJson(_snowman.get("rod"));
      EntityPredicate.Extended _snowmanxxxx = EntityPredicate.Extended.getInJson(_snowman, "entity", _snowman);
      ItemPredicate _snowmanxxxxx = ItemPredicate.fromJson(_snowman.get("item"));
      return new FishingRodHookedCriterion.Conditions(_snowman, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx);
   }

   public void trigger(ServerPlayerEntity player, ItemStack rod, FishingBobberEntity bobber, Collection<ItemStack> fishingLoots) {
      LootContext _snowman = EntityPredicate.createAdvancementEntityLootContext(player, (Entity)(bobber.getHookedEntity() != null ? bobber.getHookedEntity() : bobber));
      this.test(player, _snowmanxxx -> _snowmanxxx.test(rod, _snowman, fishingLoots));
   }

   public static class Conditions extends AbstractCriterionConditions {
      private final ItemPredicate rod;
      private final EntityPredicate.Extended hookedEntity;
      private final ItemPredicate caughtItem;

      public Conditions(EntityPredicate.Extended player, ItemPredicate rod, EntityPredicate.Extended hookedEntity, ItemPredicate caughtItem) {
         super(FishingRodHookedCriterion.ID, player);
         this.rod = rod;
         this.hookedEntity = hookedEntity;
         this.caughtItem = caughtItem;
      }

      public static FishingRodHookedCriterion.Conditions create(ItemPredicate rod, EntityPredicate bobber, ItemPredicate item) {
         return new FishingRodHookedCriterion.Conditions(EntityPredicate.Extended.EMPTY, rod, EntityPredicate.Extended.ofLegacy(bobber), item);
      }

      public boolean test(ItemStack rod, LootContext hookedEntityContext, Collection<ItemStack> fishingLoots) {
         if (!this.rod.test(rod)) {
            return false;
         } else if (!this.hookedEntity.test(hookedEntityContext)) {
            return false;
         } else {
            if (this.caughtItem != ItemPredicate.ANY) {
               boolean _snowman = false;
               Entity _snowmanx = hookedEntityContext.get(LootContextParameters.THIS_ENTITY);
               if (_snowmanx instanceof ItemEntity) {
                  ItemEntity _snowmanxx = (ItemEntity)_snowmanx;
                  if (this.caughtItem.test(_snowmanxx.getStack())) {
                     _snowman = true;
                  }
               }

               for (ItemStack _snowmanxx : fishingLoots) {
                  if (this.caughtItem.test(_snowmanxx)) {
                     _snowman = true;
                     break;
                  }
               }

               if (!_snowman) {
                  return false;
               }
            }

            return true;
         }
      }

      @Override
      public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
         JsonObject _snowman = super.toJson(predicateSerializer);
         _snowman.add("rod", this.rod.toJson());
         _snowman.add("entity", this.hookedEntity.toJson(predicateSerializer));
         _snowman.add("item", this.caughtItem.toJson());
         return _snowman;
      }
   }
}
