package net.minecraft.world.gen.tree;

import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.VineBlock;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;

public class TrunkVineTreeDecorator extends TreeDecorator {
   public static final Codec<TrunkVineTreeDecorator> CODEC = Codec.unit(() -> TrunkVineTreeDecorator.INSTANCE);
   public static final TrunkVineTreeDecorator INSTANCE = new TrunkVineTreeDecorator();

   public TrunkVineTreeDecorator() {
   }

   @Override
   protected TreeDecoratorType<?> getType() {
      return TreeDecoratorType.TRUNK_VINE;
   }

   @Override
   public void generate(
      StructureWorldAccess world, Random random, List<BlockPos> logPositions, List<BlockPos> leavesPositions, Set<BlockPos> placedStates, BlockBox box
   ) {
      logPositions.forEach(pos -> {
         if (random.nextInt(3) > 0) {
            BlockPos lv = pos.west();
            if (Feature.isAir(world, lv)) {
               this.placeVine(world, lv, VineBlock.EAST, placedStates, box);
            }
         }

         if (random.nextInt(3) > 0) {
            BlockPos lv2 = pos.east();
            if (Feature.isAir(world, lv2)) {
               this.placeVine(world, lv2, VineBlock.WEST, placedStates, box);
            }
         }

         if (random.nextInt(3) > 0) {
            BlockPos lv3 = pos.north();
            if (Feature.isAir(world, lv3)) {
               this.placeVine(world, lv3, VineBlock.SOUTH, placedStates, box);
            }
         }

         if (random.nextInt(3) > 0) {
            BlockPos lv4 = pos.south();
            if (Feature.isAir(world, lv4)) {
               this.placeVine(world, lv4, VineBlock.NORTH, placedStates, box);
            }
         }
      });
   }
}
