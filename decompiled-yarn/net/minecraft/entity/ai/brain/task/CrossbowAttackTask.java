package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.CrossbowUser;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.EntityLookTarget;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;

public class CrossbowAttackTask<E extends MobEntity & CrossbowUser, T extends LivingEntity> extends Task<E> {
   private int chargingCooldown;
   private CrossbowAttackTask.CrossbowState state = CrossbowAttackTask.CrossbowState.UNCHARGED;

   public CrossbowAttackTask() {
      super(ImmutableMap.of(MemoryModuleType.LOOK_TARGET, MemoryModuleState.REGISTERED, MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_PRESENT), 1200);
   }

   protected boolean shouldRun(ServerWorld _snowman, E _snowman) {
      LivingEntity _snowmanxx = getAttackTarget(_snowman);
      return _snowman.isHolding(Items.CROSSBOW) && LookTargetUtil.isVisibleInMemory(_snowman, _snowmanxx) && LookTargetUtil.method_25940(_snowman, _snowmanxx, 0);
   }

   protected boolean shouldKeepRunning(ServerWorld _snowman, E _snowman, long _snowman) {
      return _snowman.getBrain().hasMemoryModule(MemoryModuleType.ATTACK_TARGET) && this.shouldRun(_snowman, _snowman);
   }

   protected void keepRunning(ServerWorld _snowman, E _snowman, long _snowman) {
      LivingEntity _snowmanxxx = getAttackTarget(_snowman);
      this.setLookTarget(_snowman, _snowmanxxx);
      this.tickState(_snowman, _snowmanxxx);
   }

   protected void finishRunning(ServerWorld _snowman, E _snowman, long _snowman) {
      if (_snowman.isUsingItem()) {
         _snowman.clearActiveItem();
      }

      if (_snowman.isHolding(Items.CROSSBOW)) {
         _snowman.setCharging(false);
         CrossbowItem.setCharged(_snowman.getActiveItem(), false);
      }
   }

   private void tickState(E entity, LivingEntity target) {
      if (this.state == CrossbowAttackTask.CrossbowState.UNCHARGED) {
         entity.setCurrentHand(ProjectileUtil.getHandPossiblyHolding(entity, Items.CROSSBOW));
         this.state = CrossbowAttackTask.CrossbowState.CHARGING;
         entity.setCharging(true);
      } else if (this.state == CrossbowAttackTask.CrossbowState.CHARGING) {
         if (!entity.isUsingItem()) {
            this.state = CrossbowAttackTask.CrossbowState.UNCHARGED;
         }

         int _snowman = entity.getItemUseTime();
         ItemStack _snowmanx = entity.getActiveItem();
         if (_snowman >= CrossbowItem.getPullTime(_snowmanx)) {
            entity.stopUsingItem();
            this.state = CrossbowAttackTask.CrossbowState.CHARGED;
            this.chargingCooldown = 20 + entity.getRandom().nextInt(20);
            entity.setCharging(false);
         }
      } else if (this.state == CrossbowAttackTask.CrossbowState.CHARGED) {
         this.chargingCooldown--;
         if (this.chargingCooldown == 0) {
            this.state = CrossbowAttackTask.CrossbowState.READY_TO_ATTACK;
         }
      } else if (this.state == CrossbowAttackTask.CrossbowState.READY_TO_ATTACK) {
         entity.attack(target, 1.0F);
         ItemStack _snowman = entity.getStackInHand(ProjectileUtil.getHandPossiblyHolding(entity, Items.CROSSBOW));
         CrossbowItem.setCharged(_snowman, false);
         this.state = CrossbowAttackTask.CrossbowState.UNCHARGED;
      }
   }

   private void setLookTarget(MobEntity entity, LivingEntity target) {
      entity.getBrain().remember(MemoryModuleType.LOOK_TARGET, new EntityLookTarget(target, true));
   }

   private static LivingEntity getAttackTarget(LivingEntity entity) {
      return entity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET).get();
   }

   static enum CrossbowState {
      UNCHARGED,
      CHARGING,
      CHARGED,
      READY_TO_ATTACK;

      private CrossbowState() {
      }
   }
}
