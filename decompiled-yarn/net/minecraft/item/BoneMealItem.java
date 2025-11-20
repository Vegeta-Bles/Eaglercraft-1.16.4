package net.minecraft.item;

import java.util.Objects;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DeadCoralWallFanBlock;
import net.minecraft.block.Fertilizable;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;

public class BoneMealItem extends Item {
   public BoneMealItem(Item.Settings _snowman) {
      super(_snowman);
   }

   @Override
   public ActionResult useOnBlock(ItemUsageContext context) {
      World _snowman = context.getWorld();
      BlockPos _snowmanx = context.getBlockPos();
      BlockPos _snowmanxx = _snowmanx.offset(context.getSide());
      if (useOnFertilizable(context.getStack(), _snowman, _snowmanx)) {
         if (!_snowman.isClient) {
            _snowman.syncWorldEvent(2005, _snowmanx, 0);
         }

         return ActionResult.success(_snowman.isClient);
      } else {
         BlockState _snowmanxxx = _snowman.getBlockState(_snowmanx);
         boolean _snowmanxxxx = _snowmanxxx.isSideSolidFullSquare(_snowman, _snowmanx, context.getSide());
         if (_snowmanxxxx && useOnGround(context.getStack(), _snowman, _snowmanxx, context.getSide())) {
            if (!_snowman.isClient) {
               _snowman.syncWorldEvent(2005, _snowmanxx, 0);
            }

            return ActionResult.success(_snowman.isClient);
         } else {
            return ActionResult.PASS;
         }
      }
   }

   public static boolean useOnFertilizable(ItemStack stack, World world, BlockPos pos) {
      BlockState _snowman = world.getBlockState(pos);
      if (_snowman.getBlock() instanceof Fertilizable) {
         Fertilizable _snowmanx = (Fertilizable)_snowman.getBlock();
         if (_snowmanx.isFertilizable(world, pos, _snowman, world.isClient)) {
            if (world instanceof ServerWorld) {
               if (_snowmanx.canGrow(world, world.random, pos, _snowman)) {
                  _snowmanx.grow((ServerWorld)world, world.random, pos, _snowman);
               }

               stack.decrement(1);
            }

            return true;
         }
      }

      return false;
   }

   public static boolean useOnGround(ItemStack stack, World world, BlockPos blockPos, @Nullable Direction facing) {
      if (world.getBlockState(blockPos).isOf(Blocks.WATER) && world.getFluidState(blockPos).getLevel() == 8) {
         if (!(world instanceof ServerWorld)) {
            return true;
         } else {
            label80:
            for (int _snowman = 0; _snowman < 128; _snowman++) {
               BlockPos _snowmanx = blockPos;
               BlockState _snowmanxx = Blocks.SEAGRASS.getDefaultState();

               for (int _snowmanxxx = 0; _snowmanxxx < _snowman / 16; _snowmanxxx++) {
                  _snowmanx = _snowmanx.add(RANDOM.nextInt(3) - 1, (RANDOM.nextInt(3) - 1) * RANDOM.nextInt(3) / 2, RANDOM.nextInt(3) - 1);
                  if (world.getBlockState(_snowmanx).isFullCube(world, _snowmanx)) {
                     continue label80;
                  }
               }

               Optional<RegistryKey<Biome>> _snowmanxxxx = world.method_31081(_snowmanx);
               if (Objects.equals(_snowmanxxxx, Optional.of(BiomeKeys.WARM_OCEAN)) || Objects.equals(_snowmanxxxx, Optional.of(BiomeKeys.DEEP_WARM_OCEAN))) {
                  if (_snowman == 0 && facing != null && facing.getAxis().isHorizontal()) {
                     _snowmanxx = BlockTags.WALL_CORALS.getRandom(world.random).getDefaultState().with(DeadCoralWallFanBlock.FACING, facing);
                  } else if (RANDOM.nextInt(4) == 0) {
                     _snowmanxx = BlockTags.UNDERWATER_BONEMEALS.getRandom(RANDOM).getDefaultState();
                  }
               }

               if (_snowmanxx.getBlock().isIn(BlockTags.WALL_CORALS)) {
                  for (int _snowmanxxxxx = 0; !_snowmanxx.canPlaceAt(world, _snowmanx) && _snowmanxxxxx < 4; _snowmanxxxxx++) {
                     _snowmanxx = _snowmanxx.with(DeadCoralWallFanBlock.FACING, Direction.Type.HORIZONTAL.random(RANDOM));
                  }
               }

               if (_snowmanxx.canPlaceAt(world, _snowmanx)) {
                  BlockState _snowmanxxxxx = world.getBlockState(_snowmanx);
                  if (_snowmanxxxxx.isOf(Blocks.WATER) && world.getFluidState(_snowmanx).getLevel() == 8) {
                     world.setBlockState(_snowmanx, _snowmanxx, 3);
                  } else if (_snowmanxxxxx.isOf(Blocks.SEAGRASS) && RANDOM.nextInt(10) == 0) {
                     ((Fertilizable)Blocks.SEAGRASS).grow((ServerWorld)world, RANDOM, _snowmanx, _snowmanxxxxx);
                  }
               }
            }

            stack.decrement(1);
            return true;
         }
      } else {
         return false;
      }
   }

   public static void createParticles(WorldAccess world, BlockPos pos, int count) {
      if (count == 0) {
         count = 15;
      }

      BlockState _snowman = world.getBlockState(pos);
      if (!_snowman.isAir()) {
         double _snowmanx = 0.5;
         double _snowmanxx;
         if (_snowman.isOf(Blocks.WATER)) {
            count *= 3;
            _snowmanxx = 1.0;
            _snowmanx = 3.0;
         } else if (_snowman.isOpaqueFullCube(world, pos)) {
            pos = pos.up();
            count *= 3;
            _snowmanx = 3.0;
            _snowmanxx = 1.0;
         } else {
            _snowmanxx = _snowman.getOutlineShape(world, pos).getMax(Direction.Axis.Y);
         }

         world.addParticle(ParticleTypes.HAPPY_VILLAGER, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, 0.0, 0.0, 0.0);

         for (int _snowmanxxx = 0; _snowmanxxx < count; _snowmanxxx++) {
            double _snowmanxxxx = RANDOM.nextGaussian() * 0.02;
            double _snowmanxxxxx = RANDOM.nextGaussian() * 0.02;
            double _snowmanxxxxxx = RANDOM.nextGaussian() * 0.02;
            double _snowmanxxxxxxx = 0.5 - _snowmanx;
            double _snowmanxxxxxxxx = (double)pos.getX() + _snowmanxxxxxxx + RANDOM.nextDouble() * _snowmanx * 2.0;
            double _snowmanxxxxxxxxx = (double)pos.getY() + RANDOM.nextDouble() * _snowmanxx;
            double _snowmanxxxxxxxxxx = (double)pos.getZ() + _snowmanxxxxxxx + RANDOM.nextDouble() * _snowmanx * 2.0;
            if (!world.getBlockState(new BlockPos(_snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx).down()).isAir()) {
               world.addParticle(ParticleTypes.HAPPY_VILLAGER, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx);
            }
         }
      }
   }
}
