package net.minecraft.advancement.criterion;

import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

public class ChangedDimensionCriterion extends AbstractCriterion<ChangedDimensionCriterion.Conditions> {
   private static final Identifier ID = new Identifier("changed_dimension");

   public ChangedDimensionCriterion() {
   }

   @Override
   public Identifier getId() {
      return ID;
   }

   public ChangedDimensionCriterion.Conditions conditionsFromJson(JsonObject _snowman, EntityPredicate.Extended _snowman, AdvancementEntityPredicateDeserializer _snowman) {
      RegistryKey<World> _snowmanxxx = _snowman.has("from") ? RegistryKey.of(Registry.DIMENSION, new Identifier(JsonHelper.getString(_snowman, "from"))) : null;
      RegistryKey<World> _snowmanxxxx = _snowman.has("to") ? RegistryKey.of(Registry.DIMENSION, new Identifier(JsonHelper.getString(_snowman, "to"))) : null;
      return new ChangedDimensionCriterion.Conditions(_snowman, _snowmanxxx, _snowmanxxxx);
   }

   public void trigger(ServerPlayerEntity player, RegistryKey<World> from, RegistryKey<World> to) {
      this.test(player, _snowmanxx -> _snowmanxx.matches(from, to));
   }

   public static class Conditions extends AbstractCriterionConditions {
      @Nullable
      private final RegistryKey<World> from;
      @Nullable
      private final RegistryKey<World> to;

      public Conditions(EntityPredicate.Extended player, @Nullable RegistryKey<World> from, @Nullable RegistryKey<World> to) {
         super(ChangedDimensionCriterion.ID, player);
         this.from = from;
         this.to = to;
      }

      public static ChangedDimensionCriterion.Conditions to(RegistryKey<World> to) {
         return new ChangedDimensionCriterion.Conditions(EntityPredicate.Extended.EMPTY, null, to);
      }

      public boolean matches(RegistryKey<World> from, RegistryKey<World> to) {
         return this.from != null && this.from != from ? false : this.to == null || this.to == to;
      }

      @Override
      public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
         JsonObject _snowman = super.toJson(predicateSerializer);
         if (this.from != null) {
            _snowman.addProperty("from", this.from.getValue().toString());
         }

         if (this.to != null) {
            _snowman.addProperty("to", this.to.getValue().toString());
         }

         return _snowman;
      }
   }
}
