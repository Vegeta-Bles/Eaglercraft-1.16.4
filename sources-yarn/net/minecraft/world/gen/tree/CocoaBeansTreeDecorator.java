package net.minecraft.world.gen.tree;

import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CocoaBlock;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;

public class CocoaBeansTreeDecorator extends TreeDecorator {
   public static final Codec<CocoaBeansTreeDecorator> CODEC = Codec.floatRange(0.0F, 1.0F)
      .fieldOf("probability")
      .xmap(CocoaBeansTreeDecorator::new, arg -> arg.probability)
      .codec();
   private final float probability;

   public CocoaBeansTreeDecorator(float probability) {
      this.probability = probability;
   }

   @Override
   protected TreeDecoratorType<?> getType() {
      return TreeDecoratorType.COCOA;
   }

   @Override
   public void generate(
      StructureWorldAccess world, Random random, List<BlockPos> logPositions, List<BlockPos> leavesPositions, Set<BlockPos> placedStates, BlockBox box
   ) {
      if (!(random.nextFloat() >= this.probability)) {
         int i = logPositions.get(0).getY();
         logPositions.stream().filter(pos -> pos.getY() - i <= 2).forEach(pos -> {
            for (Direction lv : Direction.Type.HORIZONTAL) {
               if (random.nextFloat() <= 0.25F) {
                  Direction lv2 = lv.getOpposite();
                  BlockPos lv3 = pos.add(lv2.getOffsetX(), 0, lv2.getOffsetZ());
                  if (Feature.isAir(world, lv3)) {
                     BlockState lv4 = Blocks.COCOA.getDefaultState().with(CocoaBlock.AGE, Integer.valueOf(random.nextInt(3))).with(CocoaBlock.FACING, lv);
                     this.setBlockStateAndEncompassPosition(world, lv3, lv4, placedStates, box);
                  }
               }
            }
         });
      }
   }
}
