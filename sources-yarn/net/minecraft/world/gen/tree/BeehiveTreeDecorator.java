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
         Direction lv = BeehiveBlock.getRandomGenerationDirection(random);
         int i = !leavesPositions.isEmpty()
            ? Math.max(leavesPositions.get(0).getY() - 1, logPositions.get(0).getY())
            : Math.min(logPositions.get(0).getY() + 1 + random.nextInt(3), logPositions.get(logPositions.size() - 1).getY());
         List<BlockPos> list3 = logPositions.stream().filter(pos -> pos.getY() == i).collect(Collectors.toList());
         if (!list3.isEmpty()) {
            BlockPos lv2 = list3.get(random.nextInt(list3.size()));
            BlockPos lv3 = lv2.offset(lv);
            if (Feature.isAir(world, lv3) && Feature.isAir(world, lv3.offset(Direction.SOUTH))) {
               BlockState lv4 = Blocks.BEE_NEST.getDefaultState().with(BeehiveBlock.FACING, Direction.SOUTH);
               this.setBlockStateAndEncompassPosition(world, lv3, lv4, placedStates, box);
               BlockEntity lv5 = world.getBlockEntity(lv3);
               if (lv5 instanceof BeehiveBlockEntity) {
                  BeehiveBlockEntity lv6 = (BeehiveBlockEntity)lv5;
                  int j = 2 + random.nextInt(2);

                  for (int k = 0; k < j; k++) {
                     BeeEntity lv7 = new BeeEntity(EntityType.BEE, world.toServerWorld());
                     lv6.tryEnterHive(lv7, false, random.nextInt(599));
                  }
               }
            }
         }
      }
   }
}
