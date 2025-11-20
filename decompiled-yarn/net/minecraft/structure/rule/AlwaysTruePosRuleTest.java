package net.minecraft.structure.rule;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.util.math.BlockPos;

public class AlwaysTruePosRuleTest extends PosRuleTest {
   public static final Codec<AlwaysTruePosRuleTest> CODEC = Codec.unit(() -> AlwaysTruePosRuleTest.INSTANCE);
   public static final AlwaysTruePosRuleTest INSTANCE = new AlwaysTruePosRuleTest();

   private AlwaysTruePosRuleTest() {
   }

   @Override
   public boolean test(BlockPos _snowman, BlockPos _snowman, BlockPos _snowman, Random _snowman) {
      return true;
   }

   @Override
   protected PosRuleTestType<?> getType() {
      return PosRuleTestType.ALWAYS_TRUE;
   }
}
