package net.minecraft.entity.mob;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public class SkeletonEntity extends AbstractSkeletonEntity {
   public SkeletonEntity(EntityType<? extends SkeletonEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
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
      Entity _snowman = source.getAttacker();
      if (_snowman instanceof CreeperEntity) {
         CreeperEntity _snowmanx = (CreeperEntity)_snowman;
         if (_snowmanx.shouldDropHead()) {
            _snowmanx.onHeadDropped();
            this.dropItem(Items.SKELETON_SKULL);
         }
      }
   }
}
