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

   protected boolean shouldRun(ServerWorld arg, E arg2) {
      LivingEntity lv = getAttackTarget(arg2);
      return arg2.isHolding(Items.CROSSBOW) && LookTargetUtil.isVisibleInMemory(arg2, lv) && LookTargetUtil.method_25940(arg2, lv, 0);
   }

   protected boolean shouldKeepRunning(ServerWorld arg, E arg2, long l) {
      return arg2.getBrain().hasMemoryModule(MemoryModuleType.ATTACK_TARGET) && this.shouldRun(arg, arg2);
   }

   protected void keepRunning(ServerWorld arg, E arg2, long l) {
      LivingEntity lv = getAttackTarget(arg2);
      this.setLookTarget(arg2, lv);
      this.tickState(arg2, lv);
   }

   protected void finishRunning(ServerWorld arg, E arg2, long l) {
      if (arg2.isUsingItem()) {
         arg2.clearActiveItem();
      }

      if (arg2.isHolding(Items.CROSSBOW)) {
         arg2.setCharging(false);
         CrossbowItem.setCharged(arg2.getActiveItem(), false);
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

         int i = entity.getItemUseTime();
         ItemStack lv = entity.getActiveItem();
         if (i >= CrossbowItem.getPullTime(lv)) {
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
         ItemStack lv2 = entity.getStackInHand(ProjectileUtil.getHandPossiblyHolding(entity, Items.CROSSBOW));
         CrossbowItem.setCharged(lv2, false);
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
