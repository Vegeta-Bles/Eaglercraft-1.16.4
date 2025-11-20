package net.minecraft.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.world.World;

public class ArrowItem extends Item {
   public ArrowItem(Item.Settings _snowman) {
      super(_snowman);
   }

   public PersistentProjectileEntity createArrow(World world, ItemStack stack, LivingEntity shooter) {
      ArrowEntity _snowman = new ArrowEntity(world, shooter);
      _snowman.initFromStack(stack);
      return _snowman;
   }
}
