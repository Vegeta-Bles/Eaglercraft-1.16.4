package net.minecraft.item;

import java.util.Objects;
import java.util.Optional;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
   public BoneMealItem(Item.Settings arg) {
      super(arg);
   }

   @Override
   public ActionResult useOnBlock(ItemUsageContext context) {
      World lv = context.getWorld();
      BlockPos lv2 = context.getBlockPos();
      BlockPos lv3 = lv2.offset(context.getSide());
      if (useOnFertilizable(context.getStack(), lv, lv2)) {
         if (!lv.isClient) {
            lv.syncWorldEvent(2005, lv2, 0);
         }

         return ActionResult.success(lv.isClient);
      } else {
         BlockState lv4 = lv.getBlockState(lv2);
         boolean bl = lv4.isSideSolidFullSquare(lv, lv2, context.getSide());
         if (bl && useOnGround(context.getStack(), lv, lv3, context.getSide())) {
            if (!lv.isClient) {
               lv.syncWorldEvent(2005, lv3, 0);
            }

            return ActionResult.success(lv.isClient);
         } else {
            return ActionResult.PASS;
         }
      }
   }

   public static boolean useOnFertilizable(ItemStack stack, World world, BlockPos pos) {
      BlockState lv = world.getBlockState(pos);
      if (lv.getBlock() instanceof Fertilizable) {
         Fertilizable lv2 = (Fertilizable)lv.getBlock();
         if (lv2.isFertilizable(world, pos, lv, world.isClient)) {
            if (world instanceof ServerWorld) {
               if (lv2.canGrow(world, world.random, pos, lv)) {
                  lv2.grow((ServerWorld)world, world.random, pos, lv);
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
            for (int i = 0; i < 128; i++) {
               BlockPos lv = blockPos;
               BlockState lv2 = Blocks.SEAGRASS.getDefaultState();

               for (int j = 0; j < i / 16; j++) {
                  lv = lv.add(RANDOM.nextInt(3) - 1, (RANDOM.nextInt(3) - 1) * RANDOM.nextInt(3) / 2, RANDOM.nextInt(3) - 1);
                  if (world.getBlockState(lv).isFullCube(world, lv)) {
                     continue label80;
                  }
               }

               Optional<RegistryKey<Biome>> optional = world.method_31081(lv);
               if (Objects.equals(optional, Optional.of(BiomeKeys.WARM_OCEAN)) || Objects.equals(optional, Optional.of(BiomeKeys.DEEP_WARM_OCEAN))) {
                  if (i == 0 && facing != null && facing.getAxis().isHorizontal()) {
                     lv2 = BlockTags.WALL_CORALS.getRandom(world.random).getDefaultState().with(DeadCoralWallFanBlock.FACING, facing);
                  } else if (RANDOM.nextInt(4) == 0) {
                     lv2 = BlockTags.UNDERWATER_BONEMEALS.getRandom(RANDOM).getDefaultState();
                  }
               }

               if (lv2.getBlock().isIn(BlockTags.WALL_CORALS)) {
                  for (int k = 0; !lv2.canPlaceAt(world, lv) && k < 4; k++) {
                     lv2 = lv2.with(DeadCoralWallFanBlock.FACING, Direction.Type.HORIZONTAL.random(RANDOM));
                  }
               }

               if (lv2.canPlaceAt(world, lv)) {
                  BlockState lv3 = world.getBlockState(lv);
                  if (lv3.isOf(Blocks.WATER) && world.getFluidState(lv).getLevel() == 8) {
                     world.setBlockState(lv, lv2, 3);
                  } else if (lv3.isOf(Blocks.SEAGRASS) && RANDOM.nextInt(10) == 0) {
                     ((Fertilizable)Blocks.SEAGRASS).grow((ServerWorld)world, RANDOM, lv, lv3);
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

   @Environment(EnvType.CLIENT)
   public static void createParticles(WorldAccess world, BlockPos pos, int count) {
      if (count == 0) {
         count = 15;
      }

      BlockState lv = world.getBlockState(pos);
      if (!lv.isAir()) {
         double d = 0.5;
         double e;
         if (lv.isOf(Blocks.WATER)) {
            count *= 3;
            e = 1.0;
            d = 3.0;
         } else if (lv.isOpaqueFullCube(world, pos)) {
            pos = pos.up();
            count *= 3;
            d = 3.0;
            e = 1.0;
         } else {
            e = lv.getOutlineShape(world, pos).getMax(Direction.Axis.Y);
         }

         world.addParticle(ParticleTypes.HAPPY_VILLAGER, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, 0.0, 0.0, 0.0);

         for (int j = 0; j < count; j++) {
            double h = RANDOM.nextGaussian() * 0.02;
            double k = RANDOM.nextGaussian() * 0.02;
            double l = RANDOM.nextGaussian() * 0.02;
            double m = 0.5 - d;
            double n = (double)pos.getX() + m + RANDOM.nextDouble() * d * 2.0;
            double o = (double)pos.getY() + RANDOM.nextDouble() * e;
            double p = (double)pos.getZ() + m + RANDOM.nextDouble() * d * 2.0;
            if (!world.getBlockState(new BlockPos(n, o, p).down()).isAir()) {
               world.addParticle(ParticleTypes.HAPPY_VILLAGER, n, o, p, h, k, l);
            }
         }
      }
   }
}
