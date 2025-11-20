package net.minecraft.advancement.criterion;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.loot.context.LootContext;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;

public abstract class AbstractCriterion<T extends AbstractCriterionConditions> implements Criterion<T> {
   private final Map<PlayerAdvancementTracker, Set<Criterion.ConditionsContainer<T>>> progressions = Maps.newIdentityHashMap();

   public AbstractCriterion() {
   }

   @Override
   public final void beginTrackingCondition(PlayerAdvancementTracker manager, Criterion.ConditionsContainer<T> _snowman) {
      this.progressions.computeIfAbsent(manager, _snowmanx -> Sets.newHashSet()).add(_snowman);
   }

   @Override
   public final void endTrackingCondition(PlayerAdvancementTracker manager, Criterion.ConditionsContainer<T> _snowman) {
      Set<Criterion.ConditionsContainer<T>> _snowmanx = this.progressions.get(manager);
      if (_snowmanx != null) {
         _snowmanx.remove(_snowman);
         if (_snowmanx.isEmpty()) {
            this.progressions.remove(manager);
         }
      }
   }

   @Override
   public final void endTracking(PlayerAdvancementTracker tracker) {
      this.progressions.remove(tracker);
   }

   protected abstract T conditionsFromJson(
      JsonObject obj, EntityPredicate.Extended playerPredicate, AdvancementEntityPredicateDeserializer predicateDeserializer
   );

   public final T conditionsFromJson(JsonObject _snowman, AdvancementEntityPredicateDeserializer _snowman) {
      EntityPredicate.Extended _snowmanxx = EntityPredicate.Extended.getInJson(_snowman, "player", _snowman);
      return this.conditionsFromJson(_snowman, _snowmanxx, _snowman);
   }

   protected void test(ServerPlayerEntity player, Predicate<T> tester) {
      PlayerAdvancementTracker _snowman = player.getAdvancementTracker();
      Set<Criterion.ConditionsContainer<T>> _snowmanx = this.progressions.get(_snowman);
      if (_snowmanx != null && !_snowmanx.isEmpty()) {
         LootContext _snowmanxx = EntityPredicate.createAdvancementEntityLootContext(player, player);
         List<Criterion.ConditionsContainer<T>> _snowmanxxx = null;

         for (Criterion.ConditionsContainer<T> _snowmanxxxx : _snowmanx) {
            T _snowmanxxxxx = _snowmanxxxx.getConditions();
            if (_snowmanxxxxx.getPlayerPredicate().test(_snowmanxx) && tester.test(_snowmanxxxxx)) {
               if (_snowmanxxx == null) {
                  _snowmanxxx = Lists.newArrayList();
               }

               _snowmanxxx.add(_snowmanxxxx);
            }
         }

         if (_snowmanxxx != null) {
            for (Criterion.ConditionsContainer<T> _snowmanxxxxx : _snowmanxxx) {
               _snowmanxxxxx.grant(_snowman);
            }
         }
      }
   }
}
