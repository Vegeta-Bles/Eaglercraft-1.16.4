package net.minecraft.block.piston;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PistonBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class PistonHandler {
   private final World world;
   private final BlockPos posFrom;
   private final boolean retracted;
   private final BlockPos posTo;
   private final Direction motionDirection;
   private final List<BlockPos> movedBlocks = Lists.newArrayList();
   private final List<BlockPos> brokenBlocks = Lists.newArrayList();
   private final Direction pistonDirection;

   public PistonHandler(World world, BlockPos pos, Direction dir, boolean retracted) {
      this.world = world;
      this.posFrom = pos;
      this.pistonDirection = dir;
      this.retracted = retracted;
      if (retracted) {
         this.motionDirection = dir;
         this.posTo = pos.offset(dir);
      } else {
         this.motionDirection = dir.getOpposite();
         this.posTo = pos.offset(dir, 2);
      }
   }

   public boolean calculatePush() {
      this.movedBlocks.clear();
      this.brokenBlocks.clear();
      BlockState _snowman = this.world.getBlockState(this.posTo);
      if (!PistonBlock.isMovable(_snowman, this.world, this.posTo, this.motionDirection, false, this.pistonDirection)) {
         if (this.retracted && _snowman.getPistonBehavior() == PistonBehavior.DESTROY) {
            this.brokenBlocks.add(this.posTo);
            return true;
         } else {
            return false;
         }
      } else if (!this.tryMove(this.posTo, this.motionDirection)) {
         return false;
      } else {
         for (int _snowmanx = 0; _snowmanx < this.movedBlocks.size(); _snowmanx++) {
            BlockPos _snowmanxx = this.movedBlocks.get(_snowmanx);
            if (isBlockSticky(this.world.getBlockState(_snowmanxx).getBlock()) && !this.canMoveAdjacentBlock(_snowmanxx)) {
               return false;
            }
         }

         return true;
      }
   }

   private static boolean isBlockSticky(Block block) {
      return block == Blocks.SLIME_BLOCK || block == Blocks.HONEY_BLOCK;
   }

   private static boolean isAdjacentBlockStuck(Block _snowman, Block _snowman) {
      if (_snowman == Blocks.HONEY_BLOCK && _snowman == Blocks.SLIME_BLOCK) {
         return false;
      } else {
         return _snowman == Blocks.SLIME_BLOCK && _snowman == Blocks.HONEY_BLOCK ? false : isBlockSticky(_snowman) || isBlockSticky(_snowman);
      }
   }

   private boolean tryMove(BlockPos pos, Direction dir) {
      BlockState _snowman = this.world.getBlockState(pos);
      Block _snowmanx = _snowman.getBlock();
      if (_snowman.isAir()) {
         return true;
      } else if (!PistonBlock.isMovable(_snowman, this.world, pos, this.motionDirection, false, dir)) {
         return true;
      } else if (pos.equals(this.posFrom)) {
         return true;
      } else if (this.movedBlocks.contains(pos)) {
         return true;
      } else {
         int _snowmanxx = 1;
         if (_snowmanxx + this.movedBlocks.size() > 12) {
            return false;
         } else {
            while (isBlockSticky(_snowmanx)) {
               BlockPos _snowmanxxx = pos.offset(this.motionDirection.getOpposite(), _snowmanxx);
               Block _snowmanxxxx = _snowmanx;
               _snowman = this.world.getBlockState(_snowmanxxx);
               _snowmanx = _snowman.getBlock();
               if (_snowman.isAir()
                  || !isAdjacentBlockStuck(_snowmanxxxx, _snowmanx)
                  || !PistonBlock.isMovable(_snowman, this.world, _snowmanxxx, this.motionDirection, false, this.motionDirection.getOpposite())
                  || _snowmanxxx.equals(this.posFrom)) {
                  break;
               }

               if (++_snowmanxx + this.movedBlocks.size() > 12) {
                  return false;
               }
            }

            int _snowmanxxxxx = 0;

            for (int _snowmanxxxxxx = _snowmanxx - 1; _snowmanxxxxxx >= 0; _snowmanxxxxxx--) {
               this.movedBlocks.add(pos.offset(this.motionDirection.getOpposite(), _snowmanxxxxxx));
               _snowmanxxxxx++;
            }

            int _snowmanxxxxxx = 1;

            while (true) {
               BlockPos _snowmanxxxxxxx = pos.offset(this.motionDirection, _snowmanxxxxxx);
               int _snowmanxxxxxxxx = this.movedBlocks.indexOf(_snowmanxxxxxxx);
               if (_snowmanxxxxxxxx > -1) {
                  this.setMovedBlocks(_snowmanxxxxx, _snowmanxxxxxxxx);

                  for (int _snowmanxxxxxxxxx = 0; _snowmanxxxxxxxxx <= _snowmanxxxxxxxx + _snowmanxxxxx; _snowmanxxxxxxxxx++) {
                     BlockPos _snowmanxxxxxxxxxx = this.movedBlocks.get(_snowmanxxxxxxxxx);
                     if (isBlockSticky(this.world.getBlockState(_snowmanxxxxxxxxxx).getBlock()) && !this.canMoveAdjacentBlock(_snowmanxxxxxxxxxx)) {
                        return false;
                     }
                  }

                  return true;
               }

               _snowman = this.world.getBlockState(_snowmanxxxxxxx);
               if (_snowman.isAir()) {
                  return true;
               }

               if (!PistonBlock.isMovable(_snowman, this.world, _snowmanxxxxxxx, this.motionDirection, true, this.motionDirection) || _snowmanxxxxxxx.equals(this.posFrom)) {
                  return false;
               }

               if (_snowman.getPistonBehavior() == PistonBehavior.DESTROY) {
                  this.brokenBlocks.add(_snowmanxxxxxxx);
                  return true;
               }

               if (this.movedBlocks.size() >= 12) {
                  return false;
               }

               this.movedBlocks.add(_snowmanxxxxxxx);
               _snowmanxxxxx++;
               _snowmanxxxxxx++;
            }
         }
      }
   }

   private void setMovedBlocks(int from, int to) {
      List<BlockPos> _snowman = Lists.newArrayList();
      List<BlockPos> _snowmanx = Lists.newArrayList();
      List<BlockPos> _snowmanxx = Lists.newArrayList();
      _snowman.addAll(this.movedBlocks.subList(0, to));
      _snowmanx.addAll(this.movedBlocks.subList(this.movedBlocks.size() - from, this.movedBlocks.size()));
      _snowmanxx.addAll(this.movedBlocks.subList(to, this.movedBlocks.size() - from));
      this.movedBlocks.clear();
      this.movedBlocks.addAll(_snowman);
      this.movedBlocks.addAll(_snowmanx);
      this.movedBlocks.addAll(_snowmanxx);
   }

   private boolean canMoveAdjacentBlock(BlockPos pos) {
      BlockState _snowman = this.world.getBlockState(pos);

      for (Direction _snowmanx : Direction.values()) {
         if (_snowmanx.getAxis() != this.motionDirection.getAxis()) {
            BlockPos _snowmanxx = pos.offset(_snowmanx);
            BlockState _snowmanxxx = this.world.getBlockState(_snowmanxx);
            if (isAdjacentBlockStuck(_snowmanxxx.getBlock(), _snowman.getBlock()) && !this.tryMove(_snowmanxx, _snowmanx)) {
               return false;
            }
         }
      }

      return true;
   }

   public List<BlockPos> getMovedBlocks() {
      return this.movedBlocks;
   }

   public List<BlockPos> getBrokenBlocks() {
      return this.brokenBlocks;
   }
}
