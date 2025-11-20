package net.minecraft.advancement.criterion;

import com.google.gson.JsonObject;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.util.Identifier;

public interface Criterion<T extends CriterionConditions> {
   Identifier getId();

   void beginTrackingCondition(PlayerAdvancementTracker manager, Criterion.ConditionsContainer<T> var2);

   void endTrackingCondition(PlayerAdvancementTracker manager, Criterion.ConditionsContainer<T> var2);

   void endTracking(PlayerAdvancementTracker tracker);

   T conditionsFromJson(JsonObject obj, AdvancementEntityPredicateDeserializer predicateDeserializer);

   public static class ConditionsContainer<T extends CriterionConditions> {
      private final T conditions;
      private final Advancement advancement;
      private final String id;

      public ConditionsContainer(T conditions, Advancement advancement, String id) {
         this.conditions = conditions;
         this.advancement = advancement;
         this.id = id;
      }

      public T getConditions() {
         return this.conditions;
      }

      public void grant(PlayerAdvancementTracker tracker) {
         tracker.grantCriterion(this.advancement, this.id);
      }

      @Override
      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (o != null && this.getClass() == o.getClass()) {
            Criterion.ConditionsContainer<?> _snowman = (Criterion.ConditionsContainer<?>)o;
            if (!this.conditions.equals(_snowman.conditions)) {
               return false;
            } else {
               return !this.advancement.equals(_snowman.advancement) ? false : this.id.equals(_snowman.id);
            }
         } else {
            return false;
         }
      }

      @Override
      public int hashCode() {
         int _snowman = this.conditions.hashCode();
         _snowman = 31 * _snowman + this.advancement.hashCode();
         return 31 * _snowman + this.id.hashCode();
      }
   }
}
