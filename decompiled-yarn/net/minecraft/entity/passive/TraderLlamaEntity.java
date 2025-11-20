package net.minecraft.entity.passive;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;

public class TraderLlamaEntity extends LlamaEntity {
   private int despawnDelay = 47999;

   public TraderLlamaEntity(EntityType<? extends TraderLlamaEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
   }

   @Override
   public boolean isTrader() {
      return true;
   }

   @Override
   protected LlamaEntity createChild() {
      return EntityType.TRADER_LLAMA.create(this.world);
   }

   @Override
   public void writeCustomDataToTag(CompoundTag tag) {
      super.writeCustomDataToTag(tag);
      tag.putInt("DespawnDelay", this.despawnDelay);
   }

   @Override
   public void readCustomDataFromTag(CompoundTag tag) {
      super.readCustomDataFromTag(tag);
      if (tag.contains("DespawnDelay", 99)) {
         this.despawnDelay = tag.getInt("DespawnDelay");
      }
   }

   @Override
   protected void initGoals() {
      super.initGoals();
      this.goalSelector.add(1, new EscapeDangerGoal(this, 2.0));
      this.targetSelector.add(1, new TraderLlamaEntity.DefendTraderGoal(this));
   }

   @Override
   protected void putPlayerOnBack(PlayerEntity player) {
      Entity _snowman = this.getHoldingEntity();
      if (!(_snowman instanceof WanderingTraderEntity)) {
         super.putPlayerOnBack(player);
      }
   }

   @Override
   public void tickMovement() {
      super.tickMovement();
      if (!this.world.isClient) {
         this.tryDespawn();
      }
   }

   private void tryDespawn() {
      if (this.canDespawn()) {
         this.despawnDelay = this.heldByTrader() ? ((WanderingTraderEntity)this.getHoldingEntity()).getDespawnDelay() - 1 : this.despawnDelay - 1;
         if (this.despawnDelay <= 0) {
            this.detachLeash(true, false);
            this.remove();
         }
      }
   }

   private boolean canDespawn() {
      return !this.isTame() && !this.leashedByPlayer() && !this.hasPlayerRider();
   }

   private boolean heldByTrader() {
      return this.getHoldingEntity() instanceof WanderingTraderEntity;
   }

   private boolean leashedByPlayer() {
      return this.isLeashed() && !this.heldByTrader();
   }

   @Nullable
   @Override
   public EntityData initialize(
      ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable CompoundTag entityTag
   ) {
      if (spawnReason == SpawnReason.EVENT) {
         this.setBreedingAge(0);
      }

      if (entityData == null) {
         entityData = new PassiveEntity.PassiveData(false);
      }

      return super.initialize(world, difficulty, spawnReason, entityData, entityTag);
   }

   public class DefendTraderGoal extends TrackTargetGoal {
      private final LlamaEntity llama;
      private LivingEntity offender;
      private int traderLastAttackedTime;

      public DefendTraderGoal(LlamaEntity llama) {
         super(llama, false);
         this.llama = llama;
         this.setControls(EnumSet.of(Goal.Control.TARGET));
      }

      @Override
      public boolean canStart() {
         if (!this.llama.isLeashed()) {
            return false;
         } else {
            Entity _snowman = this.llama.getHoldingEntity();
            if (!(_snowman instanceof WanderingTraderEntity)) {
               return false;
            } else {
               WanderingTraderEntity _snowmanx = (WanderingTraderEntity)_snowman;
               this.offender = _snowmanx.getAttacker();
               int _snowmanxx = _snowmanx.getLastAttackedTime();
               return _snowmanxx != this.traderLastAttackedTime && this.canTrack(this.offender, TargetPredicate.DEFAULT);
            }
         }
      }

      @Override
      public void start() {
         this.mob.setTarget(this.offender);
         Entity _snowman = this.llama.getHoldingEntity();
         if (_snowman instanceof WanderingTraderEntity) {
            this.traderLastAttackedTime = ((WanderingTraderEntity)_snowman).getLastAttackedTime();
         }

         super.start();
      }
   }
}
