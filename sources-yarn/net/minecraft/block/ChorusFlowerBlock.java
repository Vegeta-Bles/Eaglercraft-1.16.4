package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.EntityTypeTags;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class ChorusFlowerBlock extends Block {
   public static final IntProperty AGE = Properties.AGE_5;
   private final ChorusPlantBlock plantBlock;

   protected ChorusFlowerBlock(ChorusPlantBlock plantBlock, AbstractBlock.Settings settings) {
      super(settings);
      this.plantBlock = plantBlock;
      this.setDefaultState(this.stateManager.getDefaultState().with(AGE, Integer.valueOf(0)));
   }

   @Override
   public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
      if (!state.canPlaceAt(world, pos)) {
         world.breakBlock(pos, true);
      }
   }

   @Override
   public boolean hasRandomTicks(BlockState state) {
      return state.get(AGE) < 5;
   }

   @Override
   public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
      BlockPos lv = pos.up();
      if (world.isAir(lv) && lv.getY() < 256) {
         int i = state.get(AGE);
         if (i < 5) {
            boolean bl = false;
            boolean bl2 = false;
            BlockState lv2 = world.getBlockState(pos.down());
            Block lv3 = lv2.getBlock();
            if (lv3 == Blocks.END_STONE) {
               bl = true;
            } else if (lv3 == this.plantBlock) {
               int j = 1;

               for (int k = 0; k < 4; k++) {
                  Block lv4 = world.getBlockState(pos.down(j + 1)).getBlock();
                  if (lv4 != this.plantBlock) {
                     if (lv4 == Blocks.END_STONE) {
                        bl2 = true;
                     }
                     break;
                  }

                  j++;
               }

               if (j < 2 || j <= random.nextInt(bl2 ? 5 : 4)) {
                  bl = true;
               }
            } else if (lv2.isAir()) {
               bl = true;
            }

            if (bl && isSurroundedByAir(world, lv, null) && world.isAir(pos.up(2))) {
               world.setBlockState(pos, this.plantBlock.withConnectionProperties(world, pos), 2);
               this.grow(world, lv, i);
            } else if (i < 4) {
               int l = random.nextInt(4);
               if (bl2) {
                  l++;
               }

               boolean bl3 = false;

               for (int m = 0; m < l; m++) {
                  Direction lv5 = Direction.Type.HORIZONTAL.random(random);
                  BlockPos lv6 = pos.offset(lv5);
                  if (world.isAir(lv6) && world.isAir(lv6.down()) && isSurroundedByAir(world, lv6, lv5.getOpposite())) {
                     this.grow(world, lv6, i + 1);
                     bl3 = true;
                  }
               }

               if (bl3) {
                  world.setBlockState(pos, this.plantBlock.withConnectionProperties(world, pos), 2);
               } else {
                  this.die(world, pos);
               }
            } else {
               this.die(world, pos);
            }
         }
      }
   }

   private void grow(World world, BlockPos pos, int age) {
      world.setBlockState(pos, this.getDefaultState().with(AGE, Integer.valueOf(age)), 2);
      world.syncWorldEvent(1033, pos, 0);
   }

   private void die(World world, BlockPos pos) {
      world.setBlockState(pos, this.getDefaultState().with(AGE, Integer.valueOf(5)), 2);
      world.syncWorldEvent(1034, pos, 0);
   }

   private static boolean isSurroundedByAir(WorldView world, BlockPos pos, @Nullable Direction exceptDirection) {
      for (Direction lv : Direction.Type.HORIZONTAL) {
         if (lv != exceptDirection && !world.isAir(pos.offset(lv))) {
            return false;
         }
      }

      return true;
   }

   @Override
   public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
      if (direction != Direction.UP && !state.canPlaceAt(world, pos)) {
         world.getBlockTickScheduler().schedule(pos, this, 1);
      }

      return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
   }

   @Override
   public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
      BlockState lv = world.getBlockState(pos.down());
      if (lv.getBlock() != this.plantBlock && !lv.isOf(Blocks.END_STONE)) {
         if (!lv.isAir()) {
            return false;
         } else {
            boolean bl = false;

            for (Direction lv2 : Direction.Type.HORIZONTAL) {
               BlockState lv3 = world.getBlockState(pos.offset(lv2));
               if (lv3.isOf(this.plantBlock)) {
                  if (bl) {
                     return false;
                  }

                  bl = true;
               } else if (!lv3.isAir()) {
                  return false;
               }
            }

            return bl;
         }
      } else {
         return true;
      }
   }

   @Override
   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(AGE);
   }

   public static void generate(WorldAccess world, BlockPos pos, Random random, int size) {
      world.setBlockState(pos, ((ChorusPlantBlock)Blocks.CHORUS_PLANT).withConnectionProperties(world, pos), 2);
      generate(world, pos, random, pos, size, 0);
   }

   private static void generate(WorldAccess world, BlockPos pos, Random random, BlockPos rootPos, int size, int layer) {
      ChorusPlantBlock lv = (ChorusPlantBlock)Blocks.CHORUS_PLANT;
      int k = random.nextInt(4) + 1;
      if (layer == 0) {
         k++;
      }

      for (int l = 0; l < k; l++) {
         BlockPos lv2 = pos.up(l + 1);
         if (!isSurroundedByAir(world, lv2, null)) {
            return;
         }

         world.setBlockState(lv2, lv.withConnectionProperties(world, lv2), 2);
         world.setBlockState(lv2.down(), lv.withConnectionProperties(world, lv2.down()), 2);
      }

      boolean bl = false;
      if (layer < 4) {
         int m = random.nextInt(4);
         if (layer == 0) {
            m++;
         }

         for (int n = 0; n < m; n++) {
            Direction lv3 = Direction.Type.HORIZONTAL.random(random);
            BlockPos lv4 = pos.up(k).offset(lv3);
            if (Math.abs(lv4.getX() - rootPos.getX()) < size
               && Math.abs(lv4.getZ() - rootPos.getZ()) < size
               && world.isAir(lv4)
               && world.isAir(lv4.down())
               && isSurroundedByAir(world, lv4, lv3.getOpposite())) {
               bl = true;
               world.setBlockState(lv4, lv.withConnectionProperties(world, lv4), 2);
               world.setBlockState(lv4.offset(lv3.getOpposite()), lv.withConnectionProperties(world, lv4.offset(lv3.getOpposite())), 2);
               generate(world, lv4, random, rootPos, size, layer + 1);
            }
         }
      }

      if (!bl) {
         world.setBlockState(pos.up(k), Blocks.CHORUS_FLOWER.getDefaultState().with(AGE, Integer.valueOf(5)), 2);
      }
   }

   @Override
   public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
      if (projectile.getType().isIn(EntityTypeTags.IMPACT_PROJECTILES)) {
         BlockPos lv = hit.getBlockPos();
         world.breakBlock(lv, true, projectile);
      }
   }
}
