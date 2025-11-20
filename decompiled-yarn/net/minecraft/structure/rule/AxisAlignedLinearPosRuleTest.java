package net.minecraft.structure.rule;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;

public class AxisAlignedLinearPosRuleTest extends PosRuleTest {
   public static final Codec<AxisAlignedLinearPosRuleTest> CODEC = RecordCodecBuilder.create(
      _snowman -> _snowman.group(
               Codec.FLOAT.fieldOf("min_chance").orElse(0.0F).forGetter(_snowmanx -> _snowmanx.minChance),
               Codec.FLOAT.fieldOf("max_chance").orElse(0.0F).forGetter(_snowmanx -> _snowmanx.maxChance),
               Codec.INT.fieldOf("min_dist").orElse(0).forGetter(_snowmanx -> _snowmanx.minDistance),
               Codec.INT.fieldOf("max_dist").orElse(0).forGetter(_snowmanx -> _snowmanx.maxDistance),
               Direction.Axis.CODEC.fieldOf("axis").orElse(Direction.Axis.Y).forGetter(_snowmanx -> _snowmanx.axis)
            )
            .apply(_snowman, AxisAlignedLinearPosRuleTest::new)
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
   public boolean test(BlockPos _snowman, BlockPos _snowman, BlockPos _snowman, Random _snowman) {
      Direction _snowmanxxxx = Direction.get(Direction.AxisDirection.POSITIVE, this.axis);
      float _snowmanxxxxx = (float)Math.abs((_snowman.getX() - _snowman.getX()) * _snowmanxxxx.getOffsetX());
      float _snowmanxxxxxx = (float)Math.abs((_snowman.getY() - _snowman.getY()) * _snowmanxxxx.getOffsetY());
      float _snowmanxxxxxxx = (float)Math.abs((_snowman.getZ() - _snowman.getZ()) * _snowmanxxxx.getOffsetZ());
      int _snowmanxxxxxxxx = (int)(_snowmanxxxxx + _snowmanxxxxxx + _snowmanxxxxxxx);
      float _snowmanxxxxxxxxx = _snowman.nextFloat();
      return (double)_snowmanxxxxxxxxx
         <= MathHelper.clampedLerp(
            (double)this.minChance, (double)this.maxChance, MathHelper.getLerpProgress((double)_snowmanxxxxxxxx, (double)this.minDistance, (double)this.maxDistance)
         );
   }

   @Override
   protected PosRuleTestType<?> getType() {
      return PosRuleTestType.AXIS_ALIGNED_LINEAR_POS;
   }
}
