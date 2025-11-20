package net.minecraft.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.SpectralArrowEntity;
import net.minecraft.world.World;

public class SpectralArrowItem extends ArrowItem {
   public SpectralArrowItem(Item.Settings _snowman) {
      super(_snowman);
   }

   @Override
   public PersistentProjectileEntity createArrow(World world, ItemStack stack, LivingEntity shooter) {
      return new SpectralArrowEntity(world, shooter);
   }
}
