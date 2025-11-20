package net.minecraft.advancement.criterion;

import com.google.gson.JsonObject;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.loot.context.LootContext;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class CuredZombieVillagerCriterion extends AbstractCriterion<CuredZombieVillagerCriterion.Conditions> {
   private static final Identifier ID = new Identifier("cured_zombie_villager");

   public CuredZombieVillagerCriterion() {
   }

   @Override
   public Identifier getId() {
      return ID;
   }

   public CuredZombieVillagerCriterion.Conditions conditionsFromJson(JsonObject _snowman, EntityPredicate.Extended _snowman, AdvancementEntityPredicateDeserializer _snowman) {
      EntityPredicate.Extended _snowmanxxx = EntityPredicate.Extended.getInJson(_snowman, "zombie", _snowman);
      EntityPredicate.Extended _snowmanxxxx = EntityPredicate.Extended.getInJson(_snowman, "villager", _snowman);
      return new CuredZombieVillagerCriterion.Conditions(_snowman, _snowmanxxx, _snowmanxxxx);
   }

   public void trigger(ServerPlayerEntity player, ZombieEntity zombie, VillagerEntity villager) {
      LootContext _snowman = EntityPredicate.createAdvancementEntityLootContext(player, zombie);
      LootContext _snowmanx = EntityPredicate.createAdvancementEntityLootContext(player, villager);
      this.test(player, _snowmanxx -> _snowmanxx.matches(_snowman, _snowman));
   }

   public static class Conditions extends AbstractCriterionConditions {
      private final EntityPredicate.Extended zombie;
      private final EntityPredicate.Extended villager;

      public Conditions(EntityPredicate.Extended player, EntityPredicate.Extended zombie, EntityPredicate.Extended villager) {
         super(CuredZombieVillagerCriterion.ID, player);
         this.zombie = zombie;
         this.villager = villager;
      }

      public static CuredZombieVillagerCriterion.Conditions any() {
         return new CuredZombieVillagerCriterion.Conditions(EntityPredicate.Extended.EMPTY, EntityPredicate.Extended.EMPTY, EntityPredicate.Extended.EMPTY);
      }

      public boolean matches(LootContext zombieContext, LootContext villagerContext) {
         return !this.zombie.test(zombieContext) ? false : this.villager.test(villagerContext);
      }

      @Override
      public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
         JsonObject _snowman = super.toJson(predicateSerializer);
         _snowman.add("zombie", this.zombie.toJson(predicateSerializer));
         _snowman.add("villager", this.villager.toJson(predicateSerializer));
         return _snowman;
      }
   }
}
