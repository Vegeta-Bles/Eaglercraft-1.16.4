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
      BlockPos _snowman = pos.up();
      if (world.isAir(_snowman) && _snowman.getY() < 256) {
         int _snowmanx = state.get(AGE);
         if (_snowmanx < 5) {
            boolean _snowmanxx = false;
            boolean _snowmanxxx = false;
            BlockState _snowmanxxxx = world.getBlockState(pos.down());
            Block _snowmanxxxxx = _snowmanxxxx.getBlock();
            if (_snowmanxxxxx == Blocks.END_STONE) {
               _snowmanxx = true;
            } else if (_snowmanxxxxx == this.plantBlock) {
               int _snowmanxxxxxx = 1;

               for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < 4; _snowmanxxxxxxx++) {
                  Block _snowmanxxxxxxxx = world.getBlockState(pos.down(_snowmanxxxxxx + 1)).getBlock();
                  if (_snowmanxxxxxxxx != this.plantBlock) {
                     if (_snowmanxxxxxxxx == Blocks.END_STONE) {
                        _snowmanxxx = true;
                     }
                     break;
                  }

                  _snowmanxxxxxx++;
               }

               if (_snowmanxxxxxx < 2 || _snowmanxxxxxx <= random.nextInt(_snowmanxxx ? 5 : 4)) {
                  _snowmanxx = true;
               }
            } else if (_snowmanxxxx.isAir()) {
               _snowmanxx = true;
            }

            if (_snowmanxx && isSurroundedByAir(world, _snowman, null) && world.isAir(pos.up(2))) {
               world.setBlockState(pos, this.plantBlock.withConnectionProperties(world, pos), 2);
               this.grow(world, _snowman, _snowmanx);
            } else if (_snowmanx < 4) {
               int _snowmanxxxxxx = random.nextInt(4);
               if (_snowmanxxx) {
                  _snowmanxxxxxx++;
               }

               boolean _snowmanxxxxxxx = false;

               for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < _snowmanxxxxxx; _snowmanxxxxxxxx++) {
                  Direction _snowmanxxxxxxxxx = Direction.Type.HORIZONTAL.random(random);
                  BlockPos _snowmanxxxxxxxxxx = pos.offset(_snowmanxxxxxxxxx);
                  if (world.isAir(_snowmanxxxxxxxxxx) && world.isAir(_snowmanxxxxxxxxxx.down()) && isSurroundedByAir(world, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxx.getOpposite())) {
                     this.grow(world, _snowmanxxxxxxxxxx, _snowmanx + 1);
                     _snowmanxxxxxxx = true;
                  }
               }

               if (_snowmanxxxxxxx) {
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
      for (Direction _snowman : Direction.Type.HORIZONTAL) {
         if (_snowman != exceptDirection && !world.isAir(pos.offset(_snowman))) {
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
      BlockState _snowman = world.getBlockState(pos.down());
      if (_snowman.getBlock() != this.plantBlock && !_snowman.isOf(Blocks.END_STONE)) {
         if (!_snowman.isAir()) {
            return false;
         } else {
            boolean _snowmanx = false;

            for (Direction _snowmanxx : Direction.Type.HORIZONTAL) {
               BlockState _snowmanxxx = world.getBlockState(pos.offset(_snowmanxx));
               if (_snowmanxxx.isOf(this.plantBlock)) {
                  if (_snowmanx) {
                     return false;
                  }

                  _snowmanx = true;
               } else if (!_snowmanxxx.isAir()) {
                  return false;
               }
            }

            return _snowmanx;
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
      ChorusPlantBlock _snowman = (ChorusPlantBlock)Blocks.CHORUS_PLANT;
      int _snowmanx = random.nextInt(4) + 1;
      if (layer == 0) {
         _snowmanx++;
      }

      for (int _snowmanxx = 0; _snowmanxx < _snowmanx; _snowmanxx++) {
         BlockPos _snowmanxxx = pos.up(_snowmanxx + 1);
         if (!isSurroundedByAir(world, _snowmanxxx, null)) {
            return;
         }

         world.setBlockState(_snowmanxxx, _snowman.withConnectionProperties(world, _snowmanxxx), 2);
         world.setBlockState(_snowmanxxx.down(), _snowman.withConnectionProperties(world, _snowmanxxx.down()), 2);
      }

      boolean _snowmanxx = false;
      if (layer < 4) {
         int _snowmanxxx = random.nextInt(4);
         if (layer == 0) {
            _snowmanxxx++;
         }

         for (int _snowmanxxxx = 0; _snowmanxxxx < _snowmanxxx; _snowmanxxxx++) {
            Direction _snowmanxxxxx = Direction.Type.HORIZONTAL.random(random);
            BlockPos _snowmanxxxxxx = pos.up(_snowmanx).offset(_snowmanxxxxx);
            if (Math.abs(_snowmanxxxxxx.getX() - rootPos.getX()) < size
               && Math.abs(_snowmanxxxxxx.getZ() - rootPos.getZ()) < size
               && world.isAir(_snowmanxxxxxx)
               && world.isAir(_snowmanxxxxxx.down())
               && isSurroundedByAir(world, _snowmanxxxxxx, _snowmanxxxxx.getOpposite())) {
               _snowmanxx = true;
               world.setBlockState(_snowmanxxxxxx, _snowman.withConnectionProperties(world, _snowmanxxxxxx), 2);
               world.setBlockState(_snowmanxxxxxx.offset(_snowmanxxxxx.getOpposite()), _snowman.withConnectionProperties(world, _snowmanxxxxxx.offset(_snowmanxxxxx.getOpposite())), 2);
               generate(world, _snowmanxxxxxx, random, rootPos, size, layer + 1);
            }
         }
      }

      if (!_snowmanxx) {
         world.setBlockState(pos.up(_snowmanx), Blocks.CHORUS_FLOWER.getDefaultState().with(AGE, Integer.valueOf(5)), 2);
      }
   }

   @Override
   public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
      if (projectile.getType().isIn(EntityTypeTags.IMPACT_PROJECTILES)) {
         BlockPos _snowman = hit.getBlockPos();
         world.breakBlock(_snowman, true, projectile);
      }
   }
}
