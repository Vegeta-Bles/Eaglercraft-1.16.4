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
      .xmap(CocoaBeansTreeDecorator::new, _snowman -> _snowman.probability)
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
         int _snowman = logPositions.get(0).getY();
         logPositions.stream().filter(pos -> pos.getY() - _snowman <= 2).forEach(pos -> {
            for (Direction _snowmanx : Direction.Type.HORIZONTAL) {
               if (random.nextFloat() <= 0.25F) {
                  Direction _snowmanx = _snowmanx.getOpposite();
                  BlockPos _snowmanxx = pos.add(_snowmanx.getOffsetX(), 0, _snowmanx.getOffsetZ());
                  if (Feature.isAir(world, _snowmanxx)) {
                     BlockState _snowmanxxx = Blocks.COCOA.getDefaultState().with(CocoaBlock.AGE, Integer.valueOf(random.nextInt(3))).with(CocoaBlock.FACING, _snowmanx);
                     this.setBlockStateAndEncompassPosition(world, _snowmanxx, _snowmanxxx, placedStates, box);
                  }
               }
            }
         });
      }
   }
}
