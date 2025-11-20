package net.minecraft.structure.rule;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;

public class AxisAlignedLinearPosRuleTest extends PosRuleTest {
   public static final Codec<AxisAlignedLinearPosRuleTest> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
               Codec.FLOAT.fieldOf("min_chance").orElse(0.0F).forGetter(arg -> arg.minChance),
               Codec.FLOAT.fieldOf("max_chance").orElse(0.0F).forGetter(arg -> arg.maxChance),
               Codec.INT.fieldOf("min_dist").orElse(0).forGetter(arg -> arg.minDistance),
               Codec.INT.fieldOf("max_dist").orElse(0).forGetter(arg -> arg.maxDistance),
               Direction.Axis.CODEC.fieldOf("axis").orElse(Direction.Axis.Y).forGetter(arg -> arg.axis)
            )
            .apply(instance, AxisAlignedLinearPosRuleTest::new)
   );
   private final float minChance;
   private final float maxChance;
   private final int minDistance;
   private final int maxDistance;
   private final Direction.Axis axis;

   public AxisAlignedLinearPosRuleTest(float minChance, float maxChance, int minDistance, int maxDistance, Direction.Axis axis) {
      if (minDistance >= maxDistance) {
         throw new IllegalArgumentException("Invalid range: [" + minDistance + "," + maxDistance + "]");
      } else {
         this.minChance = minChance;
         this.maxChance = maxChance;
         this.minDistance = minDistance;
         this.maxDistance = maxDistance;
         this.axis = axis;
      }
   }

   @Override
   public boolean test(BlockPos arg, BlockPos arg2, BlockPos arg3, Random random) {
      Direction lv = Direction.get(Direction.AxisDirection.POSITIVE, this.axis);
      float f = (float)Math.abs((arg2.getX() - arg3.getX()) * lv.getOffsetX());
      float g = (float)Math.abs((arg2.getY() - arg3.getY()) * lv.getOffsetY());
      float h = (float)Math.abs((arg2.getZ() - arg3.getZ()) * lv.getOffsetZ());
      int i = (int)(f + g + h);
      float j = random.nextFloat();
      return (double)j
         <= MathHelper.clampedLerp(
            (double)this.minChance, (double)this.maxChance, MathHelper.getLerpProgress((double)i, (double)this.minDistance, (double)this.maxDistance)
         );
   }

   @Override
   protected PosRuleTestType<?> getType() {
      return PosRuleTestType.AXIS_ALIGNED_LINEAR_POS;
   }
}
