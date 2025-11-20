package net.minecraft.entity.mob;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public class SkeletonEntity extends AbstractSkeletonEntity {
   public SkeletonEntity(EntityType<? extends SkeletonEntity> arg, World arg2) {
      super(arg, arg2);
   }

   @Override
   protected SoundEvent getAmbientSound() {
      return SoundEvents.ENTITY_SKELETON_AMBIENT;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource source) {
      return SoundEvents.ENTITY_SKELETON_HURT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.ENTITY_SKELETON_DEATH;
   }

   @Override
   SoundEvent getStepSound() {
      return SoundEvents.ENTITY_SKELETON_STEP;
   }

   @Override
   protected void dropEquipment(DamageSource source, int lootingMultiplier, boolean allowDrops) {
      super.dropEquipment(source, lootingMultiplier, allowDrops);
      Entity lv = source.getAttacker();
      if (lv instanceof CreeperEntity) {
         CreeperEntity lv2 = (CreeperEntity)lv;
         if (lv2.shouldDropHead()) {
            lv2.onHeadDropped();
            this.dropItem(Items.SKELETON_SKULL);
         }
      }
   }
}
