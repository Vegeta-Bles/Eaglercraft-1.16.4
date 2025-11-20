package net.minecraft.structure.rule;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.registry.Registry;

public class RandomBlockMatchRuleTest extends RuleTest {
   public static final Codec<RandomBlockMatchRuleTest> CODEC = RecordCodecBuilder.create(
      _snowman -> _snowman.group(Registry.BLOCK.fieldOf("block").forGetter(_snowmanx -> _snowmanx.block), Codec.FLOAT.fieldOf("probability").forGetter(_snowmanx -> _snowmanx.probability))
            .apply(_snowman, RandomBlockMatchRuleTest::new)
   );
   private final Block block;
   private final float probability;

   public RandomBlockMatchRuleTest(Block block, float probability) {
      this.block = block;
      this.probability = probability;
   }

   @Override
   public boolean test(BlockState state, Random random) {
      return state.isOf(this.block) && random.nextFloat() < this.probability;
   }

   @Override
   protected RuleTestType<?> getType() {
      return RuleTestType.RANDOM_BLOCK_MATCH;
   }
}
