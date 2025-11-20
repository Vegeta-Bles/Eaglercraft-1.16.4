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
   public final void beginTrackingCondition(PlayerAdvancementTracker manager, Criterion.ConditionsContainer<T> arg2) {
      this.progressions.computeIfAbsent(manager, arg -> Sets.newHashSet()).add(arg2);
   }

   @Override
   public final void endTrackingCondition(PlayerAdvancementTracker manager, Criterion.ConditionsContainer<T> arg2) {
      Set<Criterion.ConditionsContainer<T>> set = this.progressions.get(manager);
      if (set != null) {
         set.remove(arg2);
         if (set.isEmpty()) {
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

   public final T conditionsFromJson(JsonObject jsonObject, AdvancementEntityPredicateDeserializer arg) {
      EntityPredicate.Extended lv = EntityPredicate.Extended.getInJson(jsonObject, "player", arg);
      return this.conditionsFromJson(jsonObject, lv, arg);
   }

   protected void test(ServerPlayerEntity player, Predicate<T> tester) {
      PlayerAdvancementTracker lv = player.getAdvancementTracker();
      Set<Criterion.ConditionsContainer<T>> set = this.progressions.get(lv);
      if (set != null && !set.isEmpty()) {
         LootContext lv2 = EntityPredicate.createAdvancementEntityLootContext(player, player);
         List<Criterion.ConditionsContainer<T>> list = null;

         for (Criterion.ConditionsContainer<T> lv3 : set) {
            T lv4 = lv3.getConditions();
            if (lv4.getPlayerPredicate().test(lv2) && tester.test(lv4)) {
               if (list == null) {
                  list = Lists.newArrayList();
               }

               list.add(lv3);
            }
         }

         if (list != null) {
            for (Criterion.ConditionsContainer<T> lv5 : list) {
               lv5.grant(lv);
            }
         }
      }
   }
}
