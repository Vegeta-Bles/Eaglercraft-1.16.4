package net.minecraft.advancement.criterion;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;

public class SlideDownBlockCriterion extends AbstractCriterion<SlideDownBlockCriterion.Conditions> {
   private static final Identifier ID = new Identifier("slide_down_block");

   public SlideDownBlockCriterion() {
   }

   @Override
   public Identifier getId() {
      return ID;
   }

   public SlideDownBlockCriterion.Conditions conditionsFromJson(JsonObject _snowman, EntityPredicate.Extended _snowman, AdvancementEntityPredicateDeserializer _snowman) {
      Block _snowmanxxx = getBlock(_snowman);
      StatePredicate _snowmanxxxx = StatePredicate.fromJson(_snowman.get("state"));
      if (_snowmanxxx != null) {
         _snowmanxxxx.check(_snowmanxxx.getStateManager(), key -> {
            throw new JsonSyntaxException("Block " + _snowman + " has no property " + key);
         });
      }

      return new SlideDownBlockCriterion.Conditions(_snowman, _snowmanxxx, _snowmanxxxx);
   }

   @Nullable
   private static Block getBlock(JsonObject root) {
      if (root.has("block")) {
         Identifier _snowman = new Identifier(JsonHelper.getString(root, "block"));
         return Registry.BLOCK.getOrEmpty(_snowman).orElseThrow(() -> new JsonSyntaxException("Unknown block type '" + _snowman + "'"));
      } else {
         return null;
      }
   }

   public void test(ServerPlayerEntity player, BlockState state) {
      this.test(player, conditions -> conditions.test(state));
   }

   public static class Conditions extends AbstractCriterionConditions {
      private final Block block;
      private final StatePredicate state;

      public Conditions(EntityPredicate.Extended player, @Nullable Block block, StatePredicate state) {
         super(SlideDownBlockCriterion.ID, player);
         this.block = block;
         this.state = state;
      }

      public static SlideDownBlockCriterion.Conditions create(Block block) {
         return new SlideDownBlockCriterion.Conditions(EntityPredicate.Extended.EMPTY, block, StatePredicate.ANY);
      }

      @Override
      public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
         JsonObject _snowman = super.toJson(predicateSerializer);
         if (this.block != null) {
            _snowman.addProperty("block", Registry.BLOCK.getId(this.block).toString());
         }

         _snowman.add("state", this.state.toJson());
         return _snowman;
      }

      public boolean test(BlockState state) {
         return this.block != null && !state.isOf(this.block) ? false : this.state.test(state);
      }
   }
}
