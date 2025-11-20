package net.minecraft.entity.mob;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.TargetFinder;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.ai.goal.ProjectileAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.ai.goal.ZombieAttackGoal;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.ai.pathing.SwimNavigation;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;

public class DrownedEntity extends ZombieEntity implements RangedAttackMob {
   private boolean targetingUnderwater;
   protected final SwimNavigation waterNavigation;
   protected final MobNavigation landNavigation;

   public DrownedEntity(EntityType<? extends DrownedEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
      this.stepHeight = 1.0F;
      this.moveControl = new DrownedEntity.DrownedMoveControl(this);
      this.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
      this.waterNavigation = new SwimNavigation(this, _snowman);
      this.landNavigation = new MobNavigation(this, _snowman);
   }

   @Override
   protected void initCustomGoals() {
      this.goalSelector.add(1, new DrownedEntity.WanderAroundOnSurfaceGoal(this, 1.0));
      this.goalSelector.add(2, new DrownedEntity.TridentAttackGoal(this, 1.0, 40, 10.0F));
      this.goalSelector.add(2, new DrownedEntity.DrownedAttackGoal(this, 1.0, false));
      this.goalSelector.add(5, new DrownedEntity.LeaveWaterGoal(this, 1.0));
      this.goalSelector.add(6, new DrownedEntity.TargetAboveWaterGoal(this, 1.0, this.world.getSeaLevel()));
      this.goalSelector.add(7, new WanderAroundGoal(this, 1.0));
      this.targetSelector.add(1, new RevengeGoal(this, DrownedEntity.class).setGroupRevenge(ZombifiedPiglinEntity.class));
      this.targetSelector.add(2, new FollowTargetGoal<>(this, PlayerEntity.class, 10, true, false, this::canDrownedAttackTarget));
      this.targetSelector.add(3, new FollowTargetGoal<>(this, MerchantEntity.class, false));
      this.targetSelector.add(3, new FollowTargetGoal<>(this, IronGolemEntity.class, true));
      this.targetSelector.add(5, new FollowTargetGoal<>(this, TurtleEntity.class, 10, true, false, TurtleEntity.BABY_TURTLE_ON_LAND_FILTER));
   }

   @Override
   public EntityData initialize(
      ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable CompoundTag entityTag
   ) {
      entityData = super.initialize(world, difficulty, spawnReason, entityData, entityTag);
      if (this.getEquippedStack(EquipmentSlot.OFFHAND).isEmpty() && this.random.nextFloat() < 0.03F) {
         this.equipStack(EquipmentSlot.OFFHAND, new ItemStack(Items.NAUTILUS_SHELL));
         this.handDropChances[EquipmentSlot.OFFHAND.getEntitySlotId()] = 2.0F;
      }

      return entityData;
   }

   public static boolean canSpawn(EntityType<DrownedEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
      Optional<RegistryKey<Biome>> _snowman = world.method_31081(pos);
      boolean _snowmanx = world.getDifficulty() != Difficulty.PEACEFUL
         && isSpawnDark(world, pos, random)
         && (spawnReason == SpawnReason.SPAWNER || world.getFluidState(pos).isIn(FluidTags.WATER));
      return !Objects.equals(_snowman, Optional.of(BiomeKeys.RIVER)) && !Objects.equals(_snowman, Optional.of(BiomeKeys.FROZEN_RIVER))
         ? random.nextInt(40) == 0 && isValidSpawnDepth(world, pos) && _snowmanx
         : random.nextInt(15) == 0 && _snowmanx;
   }

   private static boolean isValidSpawnDepth(WorldAccess world, BlockPos pos) {
      return pos.getY() < world.getSeaLevel() - 5;
   }

   @Override
   protected boolean shouldBreakDoors() {
      return false;
   }

   @Override
   protected SoundEvent getAmbientSound() {
      return this.isTouchingWater() ? SoundEvents.ENTITY_DROWNED_AMBIENT_WATER : SoundEvents.ENTITY_DROWNED_AMBIENT;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource source) {
      return this.isTouchingWater() ? SoundEvents.ENTITY_DROWNED_HURT_WATER : SoundEvents.ENTITY_DROWNED_HURT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return this.isTouchingWater() ? SoundEvents.ENTITY_DROWNED_DEATH_WATER : SoundEvents.ENTITY_DROWNED_DEATH;
   }

   @Override
   protected SoundEvent getStepSound() {
      return SoundEvents.ENTITY_DROWNED_STEP;
   }

   @Override
   protected SoundEvent getSwimSound() {
      return SoundEvents.ENTITY_DROWNED_SWIM;
   }

   @Override
   protected ItemStack getSkull() {
      return ItemStack.EMPTY;
   }

   @Override
   protected void initEquipment(LocalDifficulty difficulty) {
      if ((double)this.random.nextFloat() > 0.9) {
         int _snowman = this.random.nextInt(16);
         if (_snowman < 10) {
            this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.TRIDENT));
         } else {
            this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.FISHING_ROD));
         }
      }
   }

   @Override
   protected boolean prefersNewEquipment(ItemStack newStack, ItemStack oldStack) {
      if (oldStack.getItem() == Items.NAUTILUS_SHELL) {
         return false;
      } else if (oldStack.getItem() == Items.TRIDENT) {
         return newStack.getItem() == Items.TRIDENT ? newStack.getDamage() < oldStack.getDamage() : false;
      } else {
         return newStack.getItem() == Items.TRIDENT ? true : super.prefersNewEquipment(newStack, oldStack);
      }
   }

   @Override
   protected boolean canConvertInWater() {
      return false;
   }

   @Override
   public boolean canSpawn(WorldView world) {
      return world.intersectsEntities(this);
   }

   public boolean canDrownedAttackTarget(@Nullable LivingEntity target) {
      return target != null ? !this.world.isDay() || target.isTouchingWater() : false;
   }

   @Override
   public boolean canFly() {
      return !this.isSwimming();
   }

   private boolean isTargetingUnderwater() {
      if (this.targetingUnderwater) {
         return true;
      } else {
         LivingEntity _snowman = this.getTarget();
         return _snowman != null && _snowman.isTouchingWater();
      }
   }

   @Override
   public void travel(Vec3d movementInput) {
      if (this.canMoveVoluntarily() && this.isTouchingWater() && this.isTargetingUnderwater()) {
         this.updateVelocity(0.01F, movementInput);
         this.move(MovementType.SELF, this.getVelocity());
         this.setVelocity(this.getVelocity().multiply(0.9));
      } else {
         super.travel(movementInput);
      }
   }

   @Override
   public void updateSwimming() {
      if (!this.world.isClient) {
         if (this.canMoveVoluntarily() && this.isTouchingWater() && this.isTargetingUnderwater()) {
            this.navigation = this.waterNavigation;
            this.setSwimming(true);
         } else {
            this.navigation = this.landNavigation;
            this.setSwimming(false);
         }
      }
   }

   protected boolean hasFinishedCurrentPath() {
      Path _snowman = this.getNavigation().getCurrentPath();
      if (_snowman != null) {
         BlockPos _snowmanx = _snowman.getTarget();
         if (_snowmanx != null) {
            double _snowmanxx = this.squaredDistanceTo((double)_snowmanx.getX(), (double)_snowmanx.getY(), (double)_snowmanx.getZ());
            if (_snowmanxx < 4.0) {
               return true;
            }
         }
      }

      return false;
   }

   @Override
   public void attack(LivingEntity target, float pullProgress) {
      TridentEntity _snowman = new TridentEntity(this.world, this, new ItemStack(Items.TRIDENT));
      double _snowmanx = target.getX() - this.getX();
      double _snowmanxx = target.getBodyY(0.3333333333333333) - _snowman.getY();
      double _snowmanxxx = target.getZ() - this.getZ();
      double _snowmanxxxx = (double)MathHelper.sqrt(_snowmanx * _snowmanx + _snowmanxxx * _snowmanxxx);
      _snowman.setVelocity(_snowmanx, _snowmanxx + _snowmanxxxx * 0.2F, _snowmanxxx, 1.6F, (float)(14 - this.world.getDifficulty().getId() * 4));
      this.playSound(SoundEvents.ENTITY_DROWNED_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
      this.world.spawnEntity(_snowman);
   }

   public void setTargetingUnderwater(boolean targetingUnderwater) {
      this.targetingUnderwater = targetingUnderwater;
   }

   static class DrownedAttackGoal extends ZombieAttackGoal {
      private final DrownedEntity drowned;

      public DrownedAttackGoal(DrownedEntity drowned, double speed, boolean pauseWhenMobIdle) {
         super(drowned, speed, pauseWhenMobIdle);
         this.drowned = drowned;
      }

      @Override
      public boolean canStart() {
         return super.canStart() && this.drowned.canDrownedAttackTarget(this.drowned.getTarget());
      }

      @Override
      public boolean shouldContinue() {
         return super.shouldContinue() && this.drowned.canDrownedAttackTarget(this.drowned.getTarget());
      }
   }

   static class DrownedMoveControl extends MoveControl {
      private final DrownedEntity drowned;

      public DrownedMoveControl(DrownedEntity drowned) {
         super(drowned);
         this.drowned = drowned;
      }

      @Override
      public void tick() {
         LivingEntity _snowman = this.drowned.getTarget();
         if (this.drowned.isTargetingUnderwater() && this.drowned.isTouchingWater()) {
            if (_snowman != null && _snowman.getY() > this.drowned.getY() || this.drowned.targetingUnderwater) {
               this.drowned.setVelocity(this.drowned.getVelocity().add(0.0, 0.002, 0.0));
            }

            if (this.state != MoveControl.State.MOVE_TO || this.drowned.getNavigation().isIdle()) {
               this.drowned.setMovementSpeed(0.0F);
               return;
            }

            double _snowmanx = this.targetX - this.drowned.getX();
            double _snowmanxx = this.targetY - this.drowned.getY();
            double _snowmanxxx = this.targetZ - this.drowned.getZ();
            double _snowmanxxxx = (double)MathHelper.sqrt(_snowmanx * _snowmanx + _snowmanxx * _snowmanxx + _snowmanxxx * _snowmanxxx);
            _snowmanxx /= _snowmanxxxx;
            float _snowmanxxxxx = (float)(MathHelper.atan2(_snowmanxxx, _snowmanx) * 180.0F / (float)Math.PI) - 90.0F;
            this.drowned.yaw = this.changeAngle(this.drowned.yaw, _snowmanxxxxx, 90.0F);
            this.drowned.bodyYaw = this.drowned.yaw;
            float _snowmanxxxxxx = (float)(this.speed * this.drowned.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED));
            float _snowmanxxxxxxx = MathHelper.lerp(0.125F, this.drowned.getMovementSpeed(), _snowmanxxxxxx);
            this.drowned.setMovementSpeed(_snowmanxxxxxxx);
            this.drowned
               .setVelocity(this.drowned.getVelocity().add((double)_snowmanxxxxxxx * _snowmanx * 0.005, (double)_snowmanxxxxxxx * _snowmanxx * 0.1, (double)_snowmanxxxxxxx * _snowmanxxx * 0.005));
         } else {
            if (!this.drowned.onGround) {
               this.drowned.setVelocity(this.drowned.getVelocity().add(0.0, -0.008, 0.0));
            }

            super.tick();
         }
      }
   }

   static class LeaveWaterGoal extends MoveToTargetPosGoal {
      private final DrownedEntity drowned;

      public LeaveWaterGoal(DrownedEntity drowned, double speed) {
         super(drowned, speed, 8, 2);
         this.drowned = drowned;
      }

      @Override
      public boolean canStart() {
         return super.canStart()
            && !this.drowned.world.isDay()
            && this.drowned.isTouchingWater()
            && this.drowned.getY() >= (double)(this.drowned.world.getSeaLevel() - 3);
      }

      @Override
      public boolean shouldContinue() {
         return super.shouldContinue();
      }

      @Override
      protected boolean isTargetPos(WorldView world, BlockPos pos) {
         BlockPos _snowman = pos.up();
         return world.isAir(_snowman) && world.isAir(_snowman.up()) ? world.getBlockState(pos).hasSolidTopSurface(world, pos, this.drowned) : false;
      }

      @Override
      public void start() {
         this.drowned.setTargetingUnderwater(false);
         this.drowned.navigation = this.drowned.landNavigation;
         super.start();
      }

      @Override
      public void stop() {
         super.stop();
      }
   }

   static class TargetAboveWaterGoal extends Goal {
      private final DrownedEntity drowned;
      private final double speed;
      private final int minY;
      private boolean foundTarget;

      public TargetAboveWaterGoal(DrownedEntity drowned, double speed, int minY) {
         this.drowned = drowned;
         this.speed = speed;
         this.minY = minY;
      }

      @Override
      public boolean canStart() {
         return !this.drowned.world.isDay() && this.drowned.isTouchingWater() && this.drowned.getY() < (double)(this.minY - 2);
      }

      @Override
      public boolean shouldContinue() {
         return this.canStart() && !this.foundTarget;
      }

      @Override
      public void tick() {
         if (this.drowned.getY() < (double)(this.minY - 1) && (this.drowned.getNavigation().isIdle() || this.drowned.hasFinishedCurrentPath())) {
            Vec3d _snowman = TargetFinder.findTargetTowards(this.drowned, 4, 8, new Vec3d(this.drowned.getX(), (double)(this.minY - 1), this.drowned.getZ()));
            if (_snowman == null) {
               this.foundTarget = true;
               return;
            }

            this.drowned.getNavigation().startMovingTo(_snowman.x, _snowman.y, _snowman.z, this.speed);
         }
      }

      @Override
      public void start() {
         this.drowned.setTargetingUnderwater(true);
         this.foundTarget = false;
      }

      @Override
      public void stop() {
         this.drowned.setTargetingUnderwater(false);
      }
   }

   static class TridentAttackGoal extends ProjectileAttackGoal {
      private final DrownedEntity drowned;

      public TridentAttackGoal(RangedAttackMob _snowman, double _snowman, int _snowman, float _snowman) {
         super(_snowman, _snowman, _snowman, _snowman);
         this.drowned = (DrownedEntity)_snowman;
      }

      @Override
      public boolean canStart() {
         return super.canStart() && this.drowned.getMainHandStack().getItem() == Items.TRIDENT;
      }

      @Override
      public void start() {
         super.start();
         this.drowned.setAttacking(true);
         this.drowned.setCurrentHand(Hand.MAIN_HAND);
      }

      @Override
      public void stop() {
         super.stop();
         this.drowned.clearActiveItem();
         this.drowned.setAttacking(false);
      }
   }

   static class WanderAroundOnSurfaceGoal extends Goal {
      private final PathAwareEntity mob;
      private double x;
      private double y;
      private double z;
      private final double speed;
      private final World world;

      public WanderAroundOnSurfaceGoal(PathAwareEntity mob, double speed) {
         this.mob = mob;
         this.speed = speed;
         this.world = mob.world;
         this.setControls(EnumSet.of(Goal.Control.MOVE));
      }

      @Override
      public boolean canStart() {
         if (!this.world.isDay()) {
            return false;
         } else if (this.mob.isTouchingWater()) {
            return false;
         } else {
            Vec3d _snowman = this.getWanderTarget();
            if (_snowman == null) {
               return false;
            } else {
               this.x = _snowman.x;
               this.y = _snowman.y;
               this.z = _snowman.z;
               return true;
            }
         }
      }

      @Override
      public boolean shouldContinue() {
         return !this.mob.getNavigation().isIdle();
      }

      @Override
      public void start() {
         this.mob.getNavigation().startMovingTo(this.x, this.y, this.z, this.speed);
      }

      @Nullable
      private Vec3d getWanderTarget() {
         Random _snowman = this.mob.getRandom();
         BlockPos _snowmanx = this.mob.getBlockPos();

         for (int _snowmanxx = 0; _snowmanxx < 10; _snowmanxx++) {
            BlockPos _snowmanxxx = _snowmanx.add(_snowman.nextInt(20) - 10, 2 - _snowman.nextInt(8), _snowman.nextInt(20) - 10);
            if (this.world.getBlockState(_snowmanxxx).isOf(Blocks.WATER)) {
               return Vec3d.ofBottomCenter(_snowmanxxx);
            }
         }

         return null;
      }
   }
}
