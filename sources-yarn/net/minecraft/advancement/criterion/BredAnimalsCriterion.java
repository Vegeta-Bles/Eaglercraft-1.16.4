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

   public BredAnimalsCriterion.Conditions conditionsFromJson(JsonObject jsonObject, EntityPredicate.Extended arg, AdvancementEntityPredicateDeserializer arg2) {
      EntityPredicate.Extended lv = EntityPredicate.Extended.getInJson(jsonObject, "parent", arg2);
      EntityPredicate.Extended lv2 = EntityPredicate.Extended.getInJson(jsonObject, "partner", arg2);
      EntityPredicate.Extended lv3 = EntityPredicate.Extended.getInJson(jsonObject, "child", arg2);
      return new BredAnimalsCriterion.Conditions(arg, lv, lv2, lv3);
   }

   public void trigger(ServerPlayerEntity arg, AnimalEntity arg2, AnimalEntity arg3, @Nullable PassiveEntity arg4) {
      LootContext lv = EntityPredicate.createAdvancementEntityLootContext(arg, arg2);
      LootContext lv2 = EntityPredicate.createAdvancementEntityLootContext(arg, arg3);
      LootContext lv3 = arg4 != null ? EntityPredicate.createAdvancementEntityLootContext(arg, arg4) : null;
      this.test(arg, arg4x -> arg4x.matches(lv, lv2, lv3));
   }

   public static class Conditions extends AbstractCriterionConditions {
      private final EntityPredicate.Extended parent;
      private final EntityPredicate.Extended partner;
      private final EntityPredicate.Extended child;

      public Conditions(EntityPredicate.Extended arg, EntityPredicate.Extended arg2, EntityPredicate.Extended arg3, EntityPredicate.Extended arg4) {
         super(BredAnimalsCriterion.ID, arg);
         this.parent = arg2;
         this.partner = arg3;
         this.child = arg4;
      }

      public static BredAnimalsCriterion.Conditions any() {
         return new BredAnimalsCriterion.Conditions(
            EntityPredicate.Extended.EMPTY, EntityPredicate.Extended.EMPTY, EntityPredicate.Extended.EMPTY, EntityPredicate.Extended.EMPTY
         );
      }

      public static BredAnimalsCriterion.Conditions create(EntityPredicate.Builder arg) {
         return new BredAnimalsCriterion.Conditions(
            EntityPredicate.Extended.EMPTY, EntityPredicate.Extended.EMPTY, EntityPredicate.Extended.EMPTY, EntityPredicate.Extended.ofLegacy(arg.build())
         );
      }

      public static BredAnimalsCriterion.Conditions method_29918(EntityPredicate arg, EntityPredicate arg2, EntityPredicate arg3) {
         return new BredAnimalsCriterion.Conditions(
            EntityPredicate.Extended.EMPTY,
            EntityPredicate.Extended.ofLegacy(arg),
            EntityPredicate.Extended.ofLegacy(arg2),
            EntityPredicate.Extended.ofLegacy(arg3)
         );
      }

      public boolean matches(LootContext arg, LootContext arg2, @Nullable LootContext arg3) {
         return this.child == EntityPredicate.Extended.EMPTY || arg3 != null && this.child.test(arg3)
            ? this.parent.test(arg) && this.partner.test(arg2) || this.parent.test(arg2) && this.partner.test(arg)
            : false;
      }

      @Override
      public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
         JsonObject jsonObject = super.toJson(predicateSerializer);
         jsonObject.add("parent", this.parent.toJson(predicateSerializer));
         jsonObject.add("partner", this.partner.toJson(predicateSerializer));
         jsonObject.add("child", this.child.toJson(predicateSerializer));
         return jsonObject;
      }
   }
}
