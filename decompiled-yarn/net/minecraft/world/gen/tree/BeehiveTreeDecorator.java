package net.minecraft.world.gen.tree;

import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;

public class BeehiveTreeDecorator extends TreeDecorator {
   public static final Codec<BeehiveTreeDecorator> CODEC = Codec.floatRange(0.0F, 1.0F)
      .fieldOf("probability")
      .xmap(BeehiveTreeDecorator::new, decorator -> decorator.probability)
      .codec();
   private final float probability;

   public BeehiveTreeDecorator(float probability) {
      this.probability = probability;
   }

   @Override
   protected TreeDecoratorType<?> getType() {
      return TreeDecoratorType.BEEHIVE;
   }

   @Override
   public void generate(
      StructureWorldAccess world, Random random, List<BlockPos> logPositions, List<BlockPos> leavesPositions, Set<BlockPos> placedStates, BlockBox box
   ) {
      if (!(random.nextFloat() >= this.probability)) {
         Direction _snowman = BeehiveBlock.getRandomGenerationDirection(random);
         int _snowmanx = !leavesPositions.isEmpty()
            ? Math.max(leavesPositions.get(0).getY() - 1, logPositions.get(0).getY())
            : Math.min(logPositions.get(0).getY() + 1 + random.nextInt(3), logPositions.get(logPositions.size() - 1).getY());
         List<BlockPos> _snowmanxx = logPositions.stream().filter(pos -> pos.getY() == _snowman).collect(Collectors.toList());
         if (!_snowmanxx.isEmpty()) {
            BlockPos _snowmanxxx = _snowmanxx.get(random.nextInt(_snowmanxx.size()));
            BlockPos _snowmanxxxx = _snowmanxxx.offset(_snowman);
            if (Feature.isAir(world, _snowmanxxxx) && Feature.isAir(world, _snowmanxxxx.offset(Direction.SOUTH))) {
               BlockState _snowmanxxxxx = Blocks.BEE_NEST.getDefaultState().with(BeehiveBlock.FACING, Direction.SOUTH);
               this.setBlockStateAndEncompassPosition(world, _snowmanxxxx, _snowmanxxxxx, placedStates, box);
               BlockEntity _snowmanxxxxxx = world.getBlockEntity(_snowmanxxxx);
               if (_snowmanxxxxxx instanceof BeehiveBlockEntity) {
                  BeehiveBlockEntity _snowmanxxxxxxx = (BeehiveBlockEntity)_snowmanxxxxxx;
                  int _snowmanxxxxxxxx = 2 + random.nextInt(2);

                  for (int _snowmanxxxxxxxxx = 0; _snowmanxxxxxxxxx < _snowmanxxxxxxxx; _snowmanxxxxxxxxx++) {
                     BeeEntity _snowmanxxxxxxxxxx = new BeeEntity(EntityType.BEE, world.toServerWorld());
                     _snowmanxxxxxxx.tryEnterHive(_snowmanxxxxxxxxxx, false, random.nextInt(599));
                  }
               }
            }
         }
      }
   }
}
