package net.minecraft.entity.mob;

import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;

public class CaveSpiderEntity extends SpiderEntity {
   public CaveSpiderEntity(EntityType<? extends CaveSpiderEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
   }

   public static DefaultAttributeContainer.Builder createCaveSpiderAttributes() {
      return SpiderEntity.createSpiderAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 12.0);
   }

   @Override
   public boolean tryAttack(Entity target) {
      if (super.tryAttack(target)) {
         if (target instanceof LivingEntity) {
            int _snowman = 0;
            if (this.world.getDifficulty() == Difficulty.NORMAL) {
               _snowman = 7;
            } else if (this.world.getDifficulty() == Difficulty.HARD) {
               _snowman = 15;
            }

            if (_snowman > 0) {
               ((LivingEntity)target).addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, _snowman * 20, 0));
            }
         }

         return true;
      } else {
         return false;
      }
   }

   @Nullable
   @Override
   public EntityData initialize(
      ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable CompoundTag entityTag
   ) {
      return entityData;
   }

   @Override
   protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
      return 0.45F;
   }
}
