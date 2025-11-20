package net.minecraft.enchantment;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.Material;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class FrostWalkerEnchantment extends Enchantment {
   public FrostWalkerEnchantment(Enchantment.Rarity weight, EquipmentSlot... slotTypes) {
      super(weight, EnchantmentTarget.ARMOR_FEET, slotTypes);
   }

   @Override
   public int getMinPower(int level) {
      return level * 10;
   }

   @Override
   public int getMaxPower(int level) {
      return this.getMinPower(level) + 15;
   }

   @Override
   public boolean isTreasure() {
      return true;
   }

   @Override
   public int getMaxLevel() {
      return 2;
   }

   public static void freezeWater(LivingEntity entity, World world, BlockPos blockPos, int level) {
      if (entity.isOnGround()) {
         BlockState _snowman = Blocks.FROSTED_ICE.getDefaultState();
         float _snowmanx = (float)Math.min(16, 2 + level);
         BlockPos.Mutable _snowmanxx = new BlockPos.Mutable();

         for (BlockPos _snowmanxxx : BlockPos.iterate(blockPos.add((double)(-_snowmanx), -1.0, (double)(-_snowmanx)), blockPos.add((double)_snowmanx, -1.0, (double)_snowmanx))) {
            if (_snowmanxxx.isWithinDistance(entity.getPos(), (double)_snowmanx)) {
               _snowmanxx.set(_snowmanxxx.getX(), _snowmanxxx.getY() + 1, _snowmanxxx.getZ());
               BlockState _snowmanxxxx = world.getBlockState(_snowmanxx);
               if (_snowmanxxxx.isAir()) {
                  BlockState _snowmanxxxxx = world.getBlockState(_snowmanxxx);
                  if (_snowmanxxxxx.getMaterial() == Material.WATER
                     && _snowmanxxxxx.get(FluidBlock.LEVEL) == 0
                     && _snowman.canPlaceAt(world, _snowmanxxx)
                     && world.canPlace(_snowman, _snowmanxxx, ShapeContext.absent())) {
                     world.setBlockState(_snowmanxxx, _snowman);
                     world.getBlockTickScheduler().schedule(_snowmanxxx, Blocks.FROSTED_ICE, MathHelper.nextInt(entity.getRandom(), 60, 120));
                  }
               }
            }
         }
      }
   }

   @Override
   public boolean canAccept(Enchantment other) {
      return super.canAccept(other) && other != Enchantments.DEPTH_STRIDER;
   }
}
