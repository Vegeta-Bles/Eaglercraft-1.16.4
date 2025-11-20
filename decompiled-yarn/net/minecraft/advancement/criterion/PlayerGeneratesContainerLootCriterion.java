package net.minecraft.advancement.criterion;

import com.google.gson.JsonObject;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

public class PlayerGeneratesContainerLootCriterion extends AbstractCriterion<PlayerGeneratesContainerLootCriterion.Conditions> {
   private static final Identifier ID = new Identifier("player_generates_container_loot");

   public PlayerGeneratesContainerLootCriterion() {
   }

   @Override
   public Identifier getId() {
      return ID;
   }

   protected PlayerGeneratesContainerLootCriterion.Conditions conditionsFromJson(
      JsonObject _snowman, EntityPredicate.Extended _snowman, AdvancementEntityPredicateDeserializer _snowman
   ) {
      Identifier _snowmanxxx = new Identifier(JsonHelper.getString(_snowman, "loot_table"));
      return new PlayerGeneratesContainerLootCriterion.Conditions(_snowman, _snowmanxxx);
   }

   public void test(ServerPlayerEntity player, Identifier id) {
      this.test(player, _snowmanx -> _snowmanx.test(id));
   }

   public static class Conditions extends AbstractCriterionConditions {
      private final Identifier lootTable;

      public Conditions(EntityPredicate.Extended entity, Identifier lootTable) {
         super(PlayerGeneratesContainerLootCriterion.ID, entity);
         this.lootTable = lootTable;
      }

      public static PlayerGeneratesContainerLootCriterion.Conditions create(Identifier lootTable) {
         return new PlayerGeneratesContainerLootCriterion.Conditions(EntityPredicate.Extended.EMPTY, lootTable);
      }

      public boolean test(Identifier lootTable) {
         return this.lootTable.equals(lootTable);
      }

      @Override
      public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
         JsonObject _snowman = super.toJson(predicateSerializer);
         _snowman.addProperty("loot_table", this.lootTable.toString());
         return _snowman;
      }
   }
}
