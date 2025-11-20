package net.minecraft.world.gen.tree;

import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public class AlterGroundTreeDecorator extends TreeDecorator {
   public static final Codec<AlterGroundTreeDecorator> CODEC = BlockStateProvider.TYPE_CODEC
      .fieldOf("provider")
      .xmap(AlterGroundTreeDecorator::new, _snowman -> _snowman.provider)
      .codec();
   private final BlockStateProvider provider;

   public AlterGroundTreeDecorator(BlockStateProvider provider) {
      this.provider = provider;
   }

   @Override
   protected TreeDecoratorType<?> getType() {
      return TreeDecoratorType.ALTER_GROUND;
   }

   @Override
   public void generate(
      StructureWorldAccess world, Random random, List<BlockPos> logPositions, List<BlockPos> leavesPositions, Set<BlockPos> placedStates, BlockBox box
   ) {
      int _snowman = logPositions.get(0).getY();
      logPositions.stream().filter(_snowmanx -> _snowmanx.getY() == _snowman).forEach(_snowmanxx -> {
         this.method_23462(world, random, _snowmanxx.west().north());
         this.method_23462(world, random, _snowmanxx.east(2).north());
         this.method_23462(world, random, _snowmanxx.west().south(2));
         this.method_23462(world, random, _snowmanxx.east(2).south(2));

         for (int _snowmanx = 0; _snowmanx < 5; _snowmanx++) {
            int _snowmanx = random.nextInt(64);
            int _snowmanxxx = _snowmanx % 8;
            int _snowmanxxxx = _snowmanx / 8;
            if (_snowmanxxx == 0 || _snowmanxxx == 7 || _snowmanxxxx == 0 || _snowmanxxxx == 7) {
               this.method_23462(world, random, _snowmanxx.add(-3 + _snowmanxxx, 0, -3 + _snowmanxxxx));
            }
         }
      });
   }

   private void method_23462(ModifiableTestableWorld _snowman, Random _snowman, BlockPos _snowman) {
      for (int _snowmanxxx = -2; _snowmanxxx <= 2; _snowmanxxx++) {
         for (int _snowmanxxxx = -2; _snowmanxxxx <= 2; _snowmanxxxx++) {
            if (Math.abs(_snowmanxxx) != 2 || Math.abs(_snowmanxxxx) != 2) {
               this.method_23463(_snowman, _snowman, _snowman.add(_snowmanxxx, 0, _snowmanxxxx));
            }
         }
      }
   }

   private void method_23463(ModifiableTestableWorld _snowman, Random _snowman, BlockPos _snowman) {
      for (int _snowmanxxx = 2; _snowmanxxx >= -3; _snowmanxxx--) {
         BlockPos _snowmanxxxx = _snowman.up(_snowmanxxx);
         if (Feature.isSoil(_snowman, _snowmanxxxx)) {
            _snowman.setBlockState(_snowmanxxxx, this.provider.getBlockState(_snowman, _snowman), 19);
            break;
         }

         if (!Feature.isAir(_snowman, _snowmanxxxx) && _snowmanxxx < 0) {
            break;
         }
      }
   }
}
