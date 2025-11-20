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
         BlockState lv = Blocks.FROSTED_ICE.getDefaultState();
         float f = (float)Math.min(16, 2 + level);
         BlockPos.Mutable lv2 = new BlockPos.Mutable();

         for (BlockPos lv3 : BlockPos.iterate(blockPos.add((double)(-f), -1.0, (double)(-f)), blockPos.add((double)f, -1.0, (double)f))) {
            if (lv3.isWithinDistance(entity.getPos(), (double)f)) {
               lv2.set(lv3.getX(), lv3.getY() + 1, lv3.getZ());
               BlockState lv4 = world.getBlockState(lv2);
               if (lv4.isAir()) {
                  BlockState lv5 = world.getBlockState(lv3);
                  if (lv5.getMaterial() == Material.WATER
                     && lv5.get(FluidBlock.LEVEL) == 0
                     && lv.canPlaceAt(world, lv3)
                     && world.canPlace(lv, lv3, ShapeContext.absent())) {
                     world.setBlockState(lv3, lv);
                     world.getBlockTickScheduler().schedule(lv3, Blocks.FROSTED_ICE, MathHelper.nextInt(entity.getRandom(), 60, 120));
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
