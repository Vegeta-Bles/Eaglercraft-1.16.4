package net.minecraft.advancement.criterion;

import com.google.gson.JsonObject;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.entity.Entity;
import net.minecraft.loot.context.LootContext;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class ChanneledLightningCriterion extends AbstractCriterion<ChanneledLightningCriterion.Conditions> {
   private static final Identifier ID = new Identifier("channeled_lightning");

   public ChanneledLightningCriterion() {
   }

   @Override
   public Identifier getId() {
      return ID;
   }

   public ChanneledLightningCriterion.Conditions conditionsFromJson(
      JsonObject jsonObject, EntityPredicate.Extended arg, AdvancementEntityPredicateDeserializer arg2
   ) {
      EntityPredicate.Extended[] lvs = EntityPredicate.Extended.requireInJson(jsonObject, "victims", arg2);
      return new ChanneledLightningCriterion.Conditions(arg, lvs);
   }

   public void trigger(ServerPlayerEntity player, Collection<? extends Entity> victims) {
      List<LootContext> list = victims.stream().map(arg2 -> EntityPredicate.createAdvancementEntityLootContext(player, arg2)).collect(Collectors.toList());
      this.test(player, arg -> arg.matches(list));
   }

   public static class Conditions extends AbstractCriterionConditions {
      private final EntityPredicate.Extended[] victims;

      public Conditions(EntityPredicate.Extended player, EntityPredicate.Extended[] victims) {
         super(ChanneledLightningCriterion.ID, player);
         this.victims = victims;
      }

      public static ChanneledLightningCriterion.Conditions create(EntityPredicate... victims) {
         return new ChanneledLightningCriterion.Conditions(
            EntityPredicate.Extended.EMPTY, Stream.of(victims).map(EntityPredicate.Extended::ofLegacy).toArray(EntityPredicate.Extended[]::new)
         );
      }

      public boolean matches(Collection<? extends LootContext> victims) {
         for (EntityPredicate.Extended lv : this.victims) {
            boolean bl = false;

            for (LootContext lv2 : victims) {
               if (lv.test(lv2)) {
                  bl = true;
                  break;
               }
            }

            if (!bl) {
               return false;
            }
         }

         return true;
      }

      @Override
      public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
         JsonObject jsonObject = super.toJson(predicateSerializer);
         jsonObject.add("victims", EntityPredicate.Extended.toPredicatesJsonArray(this.victims, predicateSerializer));
         return jsonObject;
      }
   }
}
