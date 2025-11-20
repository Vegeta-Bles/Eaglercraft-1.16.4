package net.minecraft.advancement.criterion;

import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.loot.context.LootContext;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class BredAnimalsCriterion extends AbstractCriterion<BredAnimalsCriterion.Conditions> {
   private static final Identifier ID = new Identifier("bred_animals");

   public BredAnimalsCriterion() {
   }

   @Override
   public Identifier getId() {
      return ID;
   }

   public BredAnimalsCriterion.Conditions conditionsFromJson(JsonObject _snowman, EntityPredicate.Extended _snowman, AdvancementEntityPredicateDeserializer _snowman) {
      EntityPredicate.Extended _snowmanxxx = EntityPredicate.Extended.getInJson(_snowman, "parent", _snowman);
      EntityPredicate.Extended _snowmanxxxx = EntityPredicate.Extended.getInJson(_snowman, "partner", _snowman);
      EntityPredicate.Extended _snowmanxxxxx = EntityPredicate.Extended.getInJson(_snowman, "child", _snowman);
      return new BredAnimalsCriterion.Conditions(_snowman, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx);
   }

   public void trigger(ServerPlayerEntity _snowman, AnimalEntity _snowman, AnimalEntity _snowman, @Nullable PassiveEntity _snowman) {
      LootContext _snowmanxxxx = EntityPredicate.createAdvancementEntityLootContext(_snowman, _snowman);
      LootContext _snowmanxxxxx = EntityPredicate.createAdvancementEntityLootContext(_snowman, _snowman);
      LootContext _snowmanxxxxxx = _snowman != null ? EntityPredicate.createAdvancementEntityLootContext(_snowman, _snowman) : null;
      this.test(_snowman, _snowmanxxxxxxx -> _snowmanxxxxxxx.matches(_snowman, _snowman, _snowman));
   }

   public static class Conditions extends AbstractCriterionConditions {
      private final EntityPredicate.Extended parent;
      private final EntityPredicate.Extended partner;
      private final EntityPredicate.Extended child;

      public Conditions(EntityPredicate.Extended _snowman, EntityPredicate.Extended _snowman, EntityPredicate.Extended _snowman, EntityPredicate.Extended _snowman) {
         super(BredAnimalsCriterion.ID, _snowman);
         this.parent = _snowman;
         this.partner = _snowman;
         this.child = _snowman;
      }

      public static BredAnimalsCriterion.Conditions any() {
         return new BredAnimalsCriterion.Conditions(
            EntityPredicate.Extended.EMPTY, EntityPredicate.Extended.EMPTY, EntityPredicate.Extended.EMPTY, EntityPredicate.Extended.EMPTY
         );
      }

      public static BredAnimalsCriterion.Conditions create(EntityPredicate.Builder _snowman) {
         return new BredAnimalsCriterion.Conditions(
            EntityPredicate.Extended.EMPTY, EntityPredicate.Extended.EMPTY, EntityPredicate.Extended.EMPTY, EntityPredicate.Extended.ofLegacy(_snowman.build())
         );
      }

      public static BredAnimalsCriterion.Conditions method_29918(EntityPredicate _snowman, EntityPredicate _snowman, EntityPredicate _snowman) {
         return new BredAnimalsCriterion.Conditions(
            EntityPredicate.Extended.EMPTY, EntityPredicate.Extended.ofLegacy(_snowman), EntityPredicate.Extended.ofLegacy(_snowman), EntityPredicate.Extended.ofLegacy(_snowman)
         );
      }

      public boolean matches(LootContext _snowman, LootContext _snowman, @Nullable LootContext _snowman) {
         return this.child == EntityPredicate.Extended.EMPTY || _snowman != null && this.child.test(_snowman)
            ? this.parent.test(_snowman) && this.partner.test(_snowman) || this.parent.test(_snowman) && this.partner.test(_snowman)
            : false;
      }

      @Override
      public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
         JsonObject _snowman = super.toJson(predicateSerializer);
         _snowman.add("parent", this.parent.toJson(predicateSerializer));
         _snowman.add("partner", this.partner.toJson(predicateSerializer));
         _snowman.add("child", this.child.toJson(predicateSerializer));
         return _snowman;
      }
   }
}
