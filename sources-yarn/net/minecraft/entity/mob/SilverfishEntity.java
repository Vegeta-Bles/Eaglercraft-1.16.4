package net.minecraft.entity.mob;

import java.util.EnumSet;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.InfestedBlock;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class SilverfishEntity extends HostileEntity {
   private SilverfishEntity.CallForHelpGoal callForHelpGoal;

   public SilverfishEntity(EntityType<? extends SilverfishEntity> arg, World arg2) {
      super(arg, arg2);
   }

   @Override
   protected void initGoals() {
      this.callForHelpGoal = new SilverfishEntity.CallForHelpGoal(this);
      this.goalSelector.add(1, new SwimGoal(this));
      this.goalSelector.add(3, this.callForHelpGoal);
      this.goalSelector.add(4, new MeleeAttackGoal(this, 1.0, false));
      this.goalSelector.add(5, new SilverfishEntity.WanderAndInfestGoal(this));
      this.targetSelector.add(1, new RevengeGoal(this).setGroupRevenge());
      this.targetSelector.add(2, new FollowTargetGoal<>(this, PlayerEntity.class, true));
   }

   @Override
   public double getHeightOffset() {
      return 0.1;
   }

   @Override
   protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
      return 0.13F;
   }

   public static DefaultAttributeContainer.Builder createSilverfishAttributes() {
      return HostileEntity.createHostileAttributes()
         .add(EntityAttributes.GENERIC_MAX_HEALTH, 8.0)
         .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25)
         .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1.0);
   }

   @Override
   protected boolean canClimb() {
      return false;
   }

   @Override
   protected SoundEvent getAmbientSound() {
      return SoundEvents.ENTITY_SILVERFISH_AMBIENT;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource source) {
      return SoundEvents.ENTITY_SILVERFISH_HURT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.ENTITY_SILVERFISH_DEATH;
   }

   @Override
   protected void playStepSound(BlockPos pos, BlockState state) {
      this.playSound(SoundEvents.ENTITY_SILVERFISH_STEP, 0.15F, 1.0F);
   }

   @Override
   public boolean damage(DamageSource source, float amount) {
      if (this.isInvulnerableTo(source)) {
         return false;
      } else {
         if ((source instanceof EntityDamageSource || source == DamageSource.MAGIC) && this.callForHelpGoal != null) {
            this.callForHelpGoal.onHurt();
         }

         return super.damage(source, amount);
      }
   }

   @Override
   public void tick() {
      this.bodyYaw = this.yaw;
      super.tick();
   }

   @Override
   public void setYaw(float yaw) {
      this.yaw = yaw;
      super.setYaw(yaw);
   }

   @Override
   public float getPathfindingFavor(BlockPos pos, WorldView world) {
      return InfestedBlock.isInfestable(world.getBlockState(pos.down())) ? 10.0F : super.getPathfindingFavor(pos, world);
   }

   public static boolean canSpawn(EntityType<SilverfishEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
      if (canSpawnIgnoreLightLevel(type, world, spawnReason, pos, random)) {
         PlayerEntity lv = world.getClosestPlayer((double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, 5.0, true);
         return lv == null;
      } else {
         return false;
      }
   }

   @Override
   public EntityGroup getGroup() {
      return EntityGroup.ARTHROPOD;
   }

   static class CallForHelpGoal extends Goal {
      private final SilverfishEntity silverfish;
      private int delay;

      public CallForHelpGoal(SilverfishEntity silverfish) {
         this.silverfish = silverfish;
      }

      public void onHurt() {
         if (this.delay == 0) {
            this.delay = 20;
         }
      }

      @Override
      public boolean canStart() {
         return this.delay > 0;
      }

      @Override
      public void tick() {
         this.delay--;
         if (this.delay <= 0) {
            World lv = this.silverfish.world;
            Random random = this.silverfish.getRandom();
            BlockPos lv2 = this.silverfish.getBlockPos();

            for (int i = 0; i <= 5 && i >= -5; i = (i <= 0 ? 1 : 0) - i) {
               for (int j = 0; j <= 10 && j >= -10; j = (j <= 0 ? 1 : 0) - j) {
                  for (int k = 0; k <= 10 && k >= -10; k = (k <= 0 ? 1 : 0) - k) {
                     BlockPos lv3 = lv2.add(j, i, k);
                     BlockState lv4 = lv.getBlockState(lv3);
                     Block lv5 = lv4.getBlock();
                     if (lv5 instanceof InfestedBlock) {
                        if (lv.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
                           lv.breakBlock(lv3, true, this.silverfish);
                        } else {
                           lv.setBlockState(lv3, ((InfestedBlock)lv5).getRegularBlock().getDefaultState(), 3);
                        }

                        if (random.nextBoolean()) {
                           return;
                        }
                     }
                  }
               }
            }
         }
      }
   }

   static class WanderAndInfestGoal extends WanderAroundGoal {
      private Direction direction;
      private boolean canInfest;

      public WanderAndInfestGoal(SilverfishEntity silverfish) {
         super(silverfish, 1.0, 10);
         this.setControls(EnumSet.of(Goal.Control.MOVE));
      }

      @Override
      public boolean canStart() {
         if (this.mob.getTarget() != null) {
            return false;
         } else if (!this.mob.getNavigation().isIdle()) {
            return false;
         } else {
            Random random = this.mob.getRandom();
            if (this.mob.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING) && random.nextInt(10) == 0) {
               this.direction = Direction.random(random);
               BlockPos lv = new BlockPos(this.mob.getX(), this.mob.getY() + 0.5, this.mob.getZ()).offset(this.direction);
               BlockState lv2 = this.mob.world.getBlockState(lv);
               if (InfestedBlock.isInfestable(lv2)) {
                  this.canInfest = true;
                  return true;
               }
            }

            this.canInfest = false;
            return super.canStart();
         }
      }

      @Override
      public boolean shouldContinue() {
         return this.canInfest ? false : super.shouldContinue();
      }

      @Override
      public void start() {
         if (!this.canInfest) {
            super.start();
         } else {
            WorldAccess lv = this.mob.world;
            BlockPos lv2 = new BlockPos(this.mob.getX(), this.mob.getY() + 0.5, this.mob.getZ()).offset(this.direction);
            BlockState lv3 = lv.getBlockState(lv2);
            if (InfestedBlock.isInfestable(lv3)) {
               lv.setBlockState(lv2, InfestedBlock.fromRegularBlock(lv3.getBlock()), 3);
               this.mob.playSpawnEffects();
               this.mob.remove();
            }
         }
      }
   }
}
