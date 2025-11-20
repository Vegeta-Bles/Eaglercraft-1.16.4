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
      BlockState lv = this.world.getBlockState(this.posTo);
      if (!PistonBlock.isMovable(lv, this.world, this.posTo, this.motionDirection, false, this.pistonDirection)) {
         if (this.retracted && lv.getPistonBehavior() == PistonBehavior.DESTROY) {
            this.brokenBlocks.add(this.posTo);
            return true;
         } else {
            return false;
         }
      } else if (!this.tryMove(this.posTo, this.motionDirection)) {
         return false;
      } else {
         for (int i = 0; i < this.movedBlocks.size(); i++) {
            BlockPos lv2 = this.movedBlocks.get(i);
            if (isBlockSticky(this.world.getBlockState(lv2).getBlock()) && !this.canMoveAdjacentBlock(lv2)) {
               return false;
            }
         }

         return true;
      }
   }

   private static boolean isBlockSticky(Block block) {
      return block == Blocks.SLIME_BLOCK || block == Blocks.HONEY_BLOCK;
   }

   private static boolean isAdjacentBlockStuck(Block arg, Block arg2) {
      if (arg == Blocks.HONEY_BLOCK && arg2 == Blocks.SLIME_BLOCK) {
         return false;
      } else {
         return arg == Blocks.SLIME_BLOCK && arg2 == Blocks.HONEY_BLOCK ? false : isBlockSticky(arg) || isBlockSticky(arg2);
      }
   }

   private boolean tryMove(BlockPos pos, Direction dir) {
      BlockState lv = this.world.getBlockState(pos);
      Block lv2 = lv.getBlock();
      if (lv.isAir()) {
         return true;
      } else if (!PistonBlock.isMovable(lv, this.world, pos, this.motionDirection, false, dir)) {
         return true;
      } else if (pos.equals(this.posFrom)) {
         return true;
      } else if (this.movedBlocks.contains(pos)) {
         return true;
      } else {
         int i = 1;
         if (i + this.movedBlocks.size() > 12) {
            return false;
         } else {
            while (isBlockSticky(lv2)) {
               BlockPos lv3 = pos.offset(this.motionDirection.getOpposite(), i);
               Block lv4 = lv2;
               lv = this.world.getBlockState(lv3);
               lv2 = lv.getBlock();
               if (lv.isAir()
                  || !isAdjacentBlockStuck(lv4, lv2)
                  || !PistonBlock.isMovable(lv, this.world, lv3, this.motionDirection, false, this.motionDirection.getOpposite())
                  || lv3.equals(this.posFrom)) {
                  break;
               }

               if (++i + this.movedBlocks.size() > 12) {
                  return false;
               }
            }

            int j = 0;

            for (int k = i - 1; k >= 0; k--) {
               this.movedBlocks.add(pos.offset(this.motionDirection.getOpposite(), k));
               j++;
            }

            int l = 1;

            while (true) {
               BlockPos lv5 = pos.offset(this.motionDirection, l);
               int m = this.movedBlocks.indexOf(lv5);
               if (m > -1) {
                  this.setMovedBlocks(j, m);

                  for (int n = 0; n <= m + j; n++) {
                     BlockPos lv6 = this.movedBlocks.get(n);
                     if (isBlockSticky(this.world.getBlockState(lv6).getBlock()) && !this.canMoveAdjacentBlock(lv6)) {
                        return false;
                     }
                  }

                  return true;
               }

               lv = this.world.getBlockState(lv5);
               if (lv.isAir()) {
                  return true;
               }

               if (!PistonBlock.isMovable(lv, this.world, lv5, this.motionDirection, true, this.motionDirection) || lv5.equals(this.posFrom)) {
                  return false;
               }

               if (lv.getPistonBehavior() == PistonBehavior.DESTROY) {
                  this.brokenBlocks.add(lv5);
                  return true;
               }

               if (this.movedBlocks.size() >= 12) {
                  return false;
               }

               this.movedBlocks.add(lv5);
               j++;
               l++;
            }
         }
      }
   }

   private void setMovedBlocks(int from, int to) {
      List<BlockPos> list = Lists.newArrayList();
      List<BlockPos> list2 = Lists.newArrayList();
      List<BlockPos> list3 = Lists.newArrayList();
      list.addAll(this.movedBlocks.subList(0, to));
      list2.addAll(this.movedBlocks.subList(this.movedBlocks.size() - from, this.movedBlocks.size()));
      list3.addAll(this.movedBlocks.subList(to, this.movedBlocks.size() - from));
      this.movedBlocks.clear();
      this.movedBlocks.addAll(list);
      this.movedBlocks.addAll(list2);
      this.movedBlocks.addAll(list3);
   }

   private boolean canMoveAdjacentBlock(BlockPos pos) {
      BlockState lv = this.world.getBlockState(pos);

      for (Direction lv2 : Direction.values()) {
         if (lv2.getAxis() != this.motionDirection.getAxis()) {
            BlockPos lv3 = pos.offset(lv2);
            BlockState lv4 = this.world.getBlockState(lv3);
            if (isAdjacentBlockStuck(lv4.getBlock(), lv.getBlock()) && !this.tryMove(lv3, lv2)) {
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
