package net.minecraft.world.gen.tree;

import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.VineBlock;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;

public class LeaveVineTreeDecorator extends TreeDecorator {
   public static final Codec<LeaveVineTreeDecorator> CODEC = Codec.unit(() -> LeaveVineTreeDecorator.INSTANCE);
   public static final LeaveVineTreeDecorator INSTANCE = new LeaveVineTreeDecorator();

   public LeaveVineTreeDecorator() {
   }

   @Override
   protected TreeDecoratorType<?> getType() {
      return TreeDecoratorType.LEAVE_VINE;
   }

   @Override
   public void generate(
      StructureWorldAccess world, Random random, List<BlockPos> logPositions, List<BlockPos> leavesPositions, Set<BlockPos> placedStates, BlockBox box
   ) {
      leavesPositions.forEach(pos -> {
         if (random.nextInt(4) == 0) {
            BlockPos _snowmanxxxx = pos.west();
            if (Feature.isAir(world, _snowmanxxxx)) {
               this.placeVines(world, _snowmanxxxx, VineBlock.EAST, placedStates, box);
            }
         }

         if (random.nextInt(4) == 0) {
            BlockPos _snowmanxxxx = pos.east();
            if (Feature.isAir(world, _snowmanxxxx)) {
               this.placeVines(world, _snowmanxxxx, VineBlock.WEST, placedStates, box);
            }
         }

         if (random.nextInt(4) == 0) {
            BlockPos _snowmanxxxx = pos.north();
            if (Feature.isAir(world, _snowmanxxxx)) {
               this.placeVines(world, _snowmanxxxx, VineBlock.SOUTH, placedStates, box);
            }
         }

         if (random.nextInt(4) == 0) {
            BlockPos _snowmanxxxx = pos.south();
            if (Feature.isAir(world, _snowmanxxxx)) {
               this.placeVines(world, _snowmanxxxx, VineBlock.NORTH, placedStates, box);
            }
         }
      });
   }

   private void placeVines(ModifiableTestableWorld world, BlockPos pos, BooleanProperty side, Set<BlockPos> placedStates, BlockBox box) {
      this.placeVine(world, pos, side, placedStates, box);
      int _snowman = 4;

      for (BlockPos var7 = pos.down(); Feature.isAir(world, var7) && _snowman > 0; _snowman--) {
         this.placeVine(world, var7, side, placedStates, box);
         var7 = var7.down();
      }
   }
}
