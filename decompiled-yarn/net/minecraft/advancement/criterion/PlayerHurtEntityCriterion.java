package net.minecraft.advancement.criterion;

import com.google.gson.JsonObject;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.loot.context.LootContext;
import net.minecraft.predicate.DamagePredicate;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class PlayerHurtEntityCriterion extends AbstractCriterion<PlayerHurtEntityCriterion.Conditions> {
   private static final Identifier ID = new Identifier("player_hurt_entity");

   public PlayerHurtEntityCriterion() {
   }

   @Override
   public Identifier getId() {
      return ID;
   }

   public PlayerHurtEntityCriterion.Conditions conditionsFromJson(JsonObject _snowman, EntityPredicate.Extended _snowman, AdvancementEntityPredicateDeserializer _snowman) {
      DamagePredicate _snowmanxxx = DamagePredicate.fromJson(_snowman.get("damage"));
      EntityPredicate.Extended _snowmanxxxx = EntityPredicate.Extended.getInJson(_snowman, "entity", _snowman);
      return new PlayerHurtEntityCriterion.Conditions(_snowman, _snowmanxxx, _snowmanxxxx);
   }

   public void trigger(ServerPlayerEntity player, Entity entity, DamageSource damage, float dealt, float taken, boolean blocked) {
      LootContext _snowman = EntityPredicate.createAdvancementEntityLootContext(player, entity);
      this.test(player, _snowmanxxxxxx -> _snowmanxxxxxx.matches(player, _snowman, damage, dealt, taken, blocked));
   }

   public static class Conditions extends AbstractCriterionConditions {
      private final DamagePredicate damage;
      private final EntityPredicate.Extended entity;

      public Conditions(EntityPredicate.Extended player, DamagePredicate damage, EntityPredicate.Extended entity) {
         super(PlayerHurtEntityCriterion.ID, player);
         this.damage = damage;
         this.entity = entity;
      }

      public static PlayerHurtEntityCriterion.Conditions create(DamagePredicate.Builder hurtEntityPredicateBuilder) {
         return new PlayerHurtEntityCriterion.Conditions(EntityPredicate.Extended.EMPTY, hurtEntityPredicateBuilder.build(), EntityPredicate.Extended.EMPTY);
      }

      public boolean matches(ServerPlayerEntity player, LootContext entityContext, DamageSource source, float dealt, float taken, boolean blocked) {
         return !this.damage.test(player, source, dealt, taken, blocked) ? false : this.entity.test(entityContext);
      }

      @Override
      public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
         JsonObject _snowman = super.toJson(predicateSerializer);
         _snowman.add("damage", this.damage.toJson());
         _snowman.add("entity", this.entity.toJson(predicateSerializer));
         return _snowman;
      }
   }
}
