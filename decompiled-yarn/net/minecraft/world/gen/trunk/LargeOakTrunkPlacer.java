package net.minecraft.world.gen.trunk;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.PillarBlock;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;

public class LargeOakTrunkPlacer extends TrunkPlacer {
   public static final Codec<LargeOakTrunkPlacer> CODEC = RecordCodecBuilder.create(_snowman -> method_28904(_snowman).apply(_snowman, LargeOakTrunkPlacer::new));

   public LargeOakTrunkPlacer(int _snowman, int _snowman, int _snowman) {
      super(_snowman, _snowman, _snowman);
   }

   @Override
   protected TrunkPlacerType<?> getType() {
      return TrunkPlacerType.FANCY_TRUNK_PLACER;
   }

   @Override
   public List<FoliagePlacer.TreeNode> generate(
      ModifiableTestableWorld world, Random random, int trunkHeight, BlockPos pos, Set<BlockPos> placedStates, BlockBox box, TreeFeatureConfig config
   ) {
      int _snowman = 5;
      int _snowmanx = trunkHeight + 2;
      int _snowmanxx = MathHelper.floor((double)_snowmanx * 0.618);
      if (!config.skipFluidCheck) {
         setToDirt(world, pos.down());
      }

      double _snowmanxxx = 1.0;
      int _snowmanxxxx = Math.min(1, MathHelper.floor(1.382 + Math.pow(1.0 * (double)_snowmanx / 13.0, 2.0)));
      int _snowmanxxxxx = pos.getY() + _snowmanxx;
      int _snowmanxxxxxx = _snowmanx - 5;
      List<LargeOakTrunkPlacer.BranchPosition> _snowmanxxxxxxx = Lists.newArrayList();
      _snowmanxxxxxxx.add(new LargeOakTrunkPlacer.BranchPosition(pos.up(_snowmanxxxxxx), _snowmanxxxxx));

      for (; _snowmanxxxxxx >= 0; _snowmanxxxxxx--) {
         float _snowmanxxxxxxxx = this.shouldGenerateBranch(_snowmanx, _snowmanxxxxxx);
         if (!(_snowmanxxxxxxxx < 0.0F)) {
            for (int _snowmanxxxxxxxxx = 0; _snowmanxxxxxxxxx < _snowmanxxxx; _snowmanxxxxxxxxx++) {
               double _snowmanxxxxxxxxxx = 1.0;
               double _snowmanxxxxxxxxxxx = 1.0 * (double)_snowmanxxxxxxxx * ((double)random.nextFloat() + 0.328);
               double _snowmanxxxxxxxxxxxx = (double)(random.nextFloat() * 2.0F) * Math.PI;
               double _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx * Math.sin(_snowmanxxxxxxxxxxxx) + 0.5;
               double _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx * Math.cos(_snowmanxxxxxxxxxxxx) + 0.5;
               BlockPos _snowmanxxxxxxxxxxxxxxx = pos.add(_snowmanxxxxxxxxxxxxx, (double)(_snowmanxxxxxx - 1), _snowmanxxxxxxxxxxxxxx);
               BlockPos _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx.up(5);
               if (this.makeOrCheckBranch(world, random, _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx, false, placedStates, box, config)) {
                  int _snowmanxxxxxxxxxxxxxxxxx = pos.getX() - _snowmanxxxxxxxxxxxxxxx.getX();
                  int _snowmanxxxxxxxxxxxxxxxxxx = pos.getZ() - _snowmanxxxxxxxxxxxxxxx.getZ();
                  double _snowmanxxxxxxxxxxxxxxxxxxx = (double)_snowmanxxxxxxxxxxxxxxx.getY()
                     - Math.sqrt((double)(_snowmanxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxx)) * 0.381;
                  int _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxx > (double)_snowmanxxxxx ? _snowmanxxxxx : (int)_snowmanxxxxxxxxxxxxxxxxxxx;
                  BlockPos _snowmanxxxxxxxxxxxxxxxxxxxxx = new BlockPos(pos.getX(), _snowmanxxxxxxxxxxxxxxxxxxxx, pos.getZ());
                  if (this.makeOrCheckBranch(world, random, _snowmanxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, false, placedStates, box, config)) {
                     _snowmanxxxxxxx.add(new LargeOakTrunkPlacer.BranchPosition(_snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxx.getY()));
                  }
               }
            }
         }
      }

      this.makeOrCheckBranch(world, random, pos, pos.up(_snowmanxx), true, placedStates, box, config);
      this.makeBranches(world, random, _snowmanx, pos, _snowmanxxxxxxx, placedStates, box, config);
      List<FoliagePlacer.TreeNode> _snowmanxxxxxxxx = Lists.newArrayList();

      for (LargeOakTrunkPlacer.BranchPosition _snowmanxxxxxxxxxx : _snowmanxxxxxxx) {
         if (this.isHighEnough(_snowmanx, _snowmanxxxxxxxxxx.getEndY() - pos.getY())) {
            _snowmanxxxxxxxx.add(_snowmanxxxxxxxxxx.node);
         }
      }

      return _snowmanxxxxxxxx;
   }

   private boolean makeOrCheckBranch(
      ModifiableTestableWorld world, Random _snowman, BlockPos start, BlockPos end, boolean make, Set<BlockPos> placedStates, BlockBox box, TreeFeatureConfig config
   ) {
      if (!make && Objects.equals(start, end)) {
         return true;
      } else {
         BlockPos _snowmanx = end.add(-start.getX(), -start.getY(), -start.getZ());
         int _snowmanxx = this.getLongestSide(_snowmanx);
         float _snowmanxxx = (float)_snowmanx.getX() / (float)_snowmanxx;
         float _snowmanxxxx = (float)_snowmanx.getY() / (float)_snowmanxx;
         float _snowmanxxxxx = (float)_snowmanx.getZ() / (float)_snowmanxx;

         for (int _snowmanxxxxxx = 0; _snowmanxxxxxx <= _snowmanxx; _snowmanxxxxxx++) {
            BlockPos _snowmanxxxxxxx = start.add(
               (double)(0.5F + (float)_snowmanxxxxxx * _snowmanxxx), (double)(0.5F + (float)_snowmanxxxxxx * _snowmanxxxx), (double)(0.5F + (float)_snowmanxxxxxx * _snowmanxxxxx)
            );
            if (make) {
               setBlockState(world, _snowmanxxxxxxx, config.trunkProvider.getBlockState(_snowman, _snowmanxxxxxxx).with(PillarBlock.AXIS, this.getLogAxis(start, _snowmanxxxxxxx)), box);
               placedStates.add(_snowmanxxxxxxx.toImmutable());
            } else if (!TreeFeature.canTreeReplace(world, _snowmanxxxxxxx)) {
               return false;
            }
         }

         return true;
      }
   }

   private int getLongestSide(BlockPos offset) {
      int _snowman = MathHelper.abs(offset.getX());
      int _snowmanx = MathHelper.abs(offset.getY());
      int _snowmanxx = MathHelper.abs(offset.getZ());
      return Math.max(_snowman, Math.max(_snowmanx, _snowmanxx));
   }

   private Direction.Axis getLogAxis(BlockPos branchStart, BlockPos branchEnd) {
      Direction.Axis _snowman = Direction.Axis.Y;
      int _snowmanx = Math.abs(branchEnd.getX() - branchStart.getX());
      int _snowmanxx = Math.abs(branchEnd.getZ() - branchStart.getZ());
      int _snowmanxxx = Math.max(_snowmanx, _snowmanxx);
      if (_snowmanxxx > 0) {
         if (_snowmanx == _snowmanxxx) {
            _snowman = Direction.Axis.X;
         } else {
            _snowman = Direction.Axis.Z;
         }
      }

      return _snowman;
   }

   private boolean isHighEnough(int treeHeight, int height) {
      return (double)height >= (double)treeHeight * 0.2;
   }

   private void makeBranches(
      ModifiableTestableWorld world,
      Random random,
      int treeHeight,
      BlockPos treePos,
      List<LargeOakTrunkPlacer.BranchPosition> branches,
      Set<BlockPos> placedStates,
      BlockBox box,
      TreeFeatureConfig config
   ) {
      for (LargeOakTrunkPlacer.BranchPosition _snowman : branches) {
         int _snowmanx = _snowman.getEndY();
         BlockPos _snowmanxx = new BlockPos(treePos.getX(), _snowmanx, treePos.getZ());
         if (!_snowmanxx.equals(_snowman.node.getCenter()) && this.isHighEnough(treeHeight, _snowmanx - treePos.getY())) {
            this.makeOrCheckBranch(world, random, _snowmanxx, _snowman.node.getCenter(), true, placedStates, box, config);
         }
      }
   }

   private float shouldGenerateBranch(int trunkHeight, int y) {
      if ((float)y < (float)trunkHeight * 0.3F) {
         return -1.0F;
      } else {
         float _snowman = (float)trunkHeight / 2.0F;
         float _snowmanx = _snowman - (float)y;
         float _snowmanxx = MathHelper.sqrt(_snowman * _snowman - _snowmanx * _snowmanx);
         if (_snowmanx == 0.0F) {
            _snowmanxx = _snowman;
         } else if (Math.abs(_snowmanx) >= _snowman) {
            return 0.0F;
         }

         return _snowmanxx * 0.5F;
      }
   }

   static class BranchPosition {
      private final FoliagePlacer.TreeNode node;
      private final int endY;

      public BranchPosition(BlockPos pos, int width) {
         this.node = new FoliagePlacer.TreeNode(pos, 0, false);
         this.endY = width;
      }

      public int getEndY() {
         return this.endY;
      }
   }
}
