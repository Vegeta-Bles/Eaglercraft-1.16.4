package net.minecraft.advancement.criterion;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.context.LootContext;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class KilledByCrossbowCriterion extends AbstractCriterion<KilledByCrossbowCriterion.Conditions> {
   private static final Identifier ID = new Identifier("killed_by_crossbow");

   public KilledByCrossbowCriterion() {
   }

   @Override
   public Identifier getId() {
      return ID;
   }

   public KilledByCrossbowCriterion.Conditions conditionsFromJson(JsonObject _snowman, EntityPredicate.Extended _snowman, AdvancementEntityPredicateDeserializer _snowman) {
      EntityPredicate.Extended[] _snowmanxxx = EntityPredicate.Extended.requireInJson(_snowman, "victims", _snowman);
      NumberRange.IntRange _snowmanxxxx = NumberRange.IntRange.fromJson(_snowman.get("unique_entity_types"));
      return new KilledByCrossbowCriterion.Conditions(_snowman, _snowmanxxx, _snowmanxxxx);
   }

   public void trigger(ServerPlayerEntity player, Collection<Entity> piercingKilledEntities) {
      List<LootContext> _snowman = Lists.newArrayList();
      Set<EntityType<?>> _snowmanx = Sets.newHashSet();

      for (Entity _snowmanxx : piercingKilledEntities) {
         _snowmanx.add(_snowmanxx.getType());
         _snowman.add(EntityPredicate.createAdvancementEntityLootContext(player, _snowmanxx));
      }

      this.test(player, _snowmanxx -> _snowmanxx.matches(_snowman, _snowman.size()));
   }

   public static class Conditions extends AbstractCriterionConditions {
      private final EntityPredicate.Extended[] victims;
      private final NumberRange.IntRange uniqueEntityTypes;

      public Conditions(EntityPredicate.Extended player, EntityPredicate.Extended[] victims, NumberRange.IntRange uniqueEntityTypes) {
         super(KilledByCrossbowCriterion.ID, player);
         this.victims = victims;
         this.uniqueEntityTypes = uniqueEntityTypes;
      }

      public static KilledByCrossbowCriterion.Conditions create(EntityPredicate.Builder... victimPredicates) {
         EntityPredicate.Extended[] _snowman = new EntityPredicate.Extended[victimPredicates.length];

         for (int _snowmanx = 0; _snowmanx < victimPredicates.length; _snowmanx++) {
            EntityPredicate.Builder _snowmanxx = victimPredicates[_snowmanx];
            _snowman[_snowmanx] = EntityPredicate.Extended.ofLegacy(_snowmanxx.build());
         }

         return new KilledByCrossbowCriterion.Conditions(EntityPredicate.Extended.EMPTY, _snowman, NumberRange.IntRange.ANY);
      }

      public static KilledByCrossbowCriterion.Conditions create(NumberRange.IntRange uniqueEntityTypes) {
         EntityPredicate.Extended[] _snowman = new EntityPredicate.Extended[0];
         return new KilledByCrossbowCriterion.Conditions(EntityPredicate.Extended.EMPTY, _snowman, uniqueEntityTypes);
      }

      public boolean matches(Collection<LootContext> victimContexts, int uniqueEntityTypeCount) {
         if (this.victims.length > 0) {
            List<LootContext> _snowman = Lists.newArrayList(victimContexts);

            for (EntityPredicate.Extended _snowmanx : this.victims) {
               boolean _snowmanxx = false;
               Iterator<LootContext> _snowmanxxx = _snowman.iterator();

               while (_snowmanxxx.hasNext()) {
                  LootContext _snowmanxxxx = _snowmanxxx.next();
                  if (_snowmanx.test(_snowmanxxxx)) {
                     _snowmanxxx.remove();
                     _snowmanxx = true;
                     break;
                  }
               }

               if (!_snowmanxx) {
                  return false;
               }
            }
         }

         return this.uniqueEntityTypes.test(uniqueEntityTypeCount);
      }

      @Override
      public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
         JsonObject _snowman = super.toJson(predicateSerializer);
         _snowman.add("victims", EntityPredicate.Extended.toPredicatesJsonArray(this.victims, predicateSerializer));
         _snowman.add("unique_entity_types", this.uniqueEntityTypes.toJson());
         return _snowman;
      }
   }
}
