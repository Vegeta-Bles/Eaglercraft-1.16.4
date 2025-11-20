package net.minecraft.structure.rule;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import net.minecraft.block.BlockState;

public class RandomBlockStateMatchRuleTest extends RuleTest {
   public static final Codec<RandomBlockStateMatchRuleTest> CODEC = RecordCodecBuilder.create(
      _snowman -> _snowman.group(BlockState.CODEC.fieldOf("block_state").forGetter(_snowmanx -> _snowmanx.blockState), Codec.FLOAT.fieldOf("probability").forGetter(_snowmanx -> _snowmanx.probability))
            .apply(_snowman, RandomBlockStateMatchRuleTest::new)
   );
   private final BlockState blockState;
   private final float probability;

   public RandomBlockStateMatchRuleTest(BlockState blockState, float probability) {
      this.blockState = blockState;
      this.probability = probability;
   }

   @Override
   public boolean test(BlockState state, Random random) {
      return state == this.blockState && random.nextFloat() < this.probability;
   }

   @Override
   protected RuleTestType<?> getType() {
      return RuleTestType.RANDOM_BLOCKSTATE_MATCH;
   }
}
