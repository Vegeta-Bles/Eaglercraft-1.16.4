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

   public ChanneledLightningCriterion.Conditions conditionsFromJson(JsonObject _snowman, EntityPredicate.Extended _snowman, AdvancementEntityPredicateDeserializer _snowman) {
      EntityPredicate.Extended[] _snowmanxxx = EntityPredicate.Extended.requireInJson(_snowman, "victims", _snowman);
      return new ChanneledLightningCriterion.Conditions(_snowman, _snowmanxxx);
   }

   public void trigger(ServerPlayerEntity player, Collection<? extends Entity> victims) {
      List<LootContext> _snowman = victims.stream().map(_snowmanx -> EntityPredicate.createAdvancementEntityLootContext(player, _snowmanx)).collect(Collectors.toList());
      this.test(player, _snowmanx -> _snowmanx.matches(_snowman));
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
         for (EntityPredicate.Extended _snowman : this.victims) {
            boolean _snowmanx = false;

            for (LootContext _snowmanxx : victims) {
               if (_snowman.test(_snowmanxx)) {
                  _snowmanx = true;
                  break;
               }
            }

            if (!_snowmanx) {
               return false;
            }
         }

         return true;
      }

      @Override
      public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
         JsonObject _snowman = super.toJson(predicateSerializer);
         _snowman.add("victims", EntityPredicate.Extended.toPredicatesJsonArray(this.victims, predicateSerializer));
         return _snowman;
      }
   }
}
