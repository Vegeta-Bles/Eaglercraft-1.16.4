package net.minecraft.entity.mob;

import java.util.EnumSet;
import java.util.Random;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class GhastEntity extends FlyingEntity implements Monster {
   private static final TrackedData<Boolean> SHOOTING = DataTracker.registerData(GhastEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
   private int fireballStrength = 1;

   public GhastEntity(EntityType<? extends GhastEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
      this.experiencePoints = 5;
      this.moveControl = new GhastEntity.GhastMoveControl(this);
   }

   @Override
   protected void initGoals() {
      this.goalSelector.add(5, new GhastEntity.FlyRandomlyGoal(this));
      this.goalSelector.add(7, new GhastEntity.LookAtTargetGoal(this));
      this.goalSelector.add(7, new GhastEntity.ShootFireballGoal(this));
      this.targetSelector.add(1, new FollowTargetGoal<>(this, PlayerEntity.class, 10, true, false, _snowman -> Math.abs(_snowman.getY() - this.getY()) <= 4.0));
   }

   public boolean isShooting() {
      return this.dataTracker.get(SHOOTING);
   }

   public void setShooting(boolean shooting) {
      this.dataTracker.set(SHOOTING, shooting);
   }

   public int getFireballStrength() {
      return this.fireballStrength;
   }

   @Override
   protected boolean isDisallowedInPeaceful() {
      return true;
   }

   @Override
   public boolean damage(DamageSource source, float amount) {
      if (this.isInvulnerableTo(source)) {
         return false;
      } else if (source.getSource() instanceof FireballEntity && source.getAttacker() instanceof PlayerEntity) {
         super.damage(source, 1000.0F);
         return true;
      } else {
         return super.damage(source, amount);
      }
   }

   @Override
   protected void initDataTracker() {
      super.initDataTracker();
      this.dataTracker.startTracking(SHOOTING, false);
   }

   public static DefaultAttributeContainer.Builder createGhastAttributes() {
      return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0);
   }

   @Override
   public SoundCategory getSoundCategory() {
      return SoundCategory.HOSTILE;
   }

   @Override
   protected SoundEvent getAmbientSound() {
      return SoundEvents.ENTITY_GHAST_AMBIENT;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource source) {
      return SoundEvents.ENTITY_GHAST_HURT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.ENTITY_GHAST_DEATH;
   }

   @Override
   protected float getSoundVolume() {
      return 5.0F;
   }

   public static boolean canSpawn(EntityType<GhastEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
      return world.getDifficulty() != Difficulty.PEACEFUL && random.nextInt(20) == 0 && canMobSpawn(type, world, spawnReason, pos, random);
   }

   @Override
   public int getLimitPerChunk() {
      return 1;
   }

   @Override
   public void writeCustomDataToTag(CompoundTag tag) {
      super.writeCustomDataToTag(tag);
      tag.putInt("ExplosionPower", this.fireballStrength);
   }

   @Override
   public void readCustomDataFromTag(CompoundTag tag) {
      super.readCustomDataFromTag(tag);
      if (tag.contains("ExplosionPower", 99)) {
         this.fireballStrength = tag.getInt("ExplosionPower");
      }
   }

   @Override
   protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
      return 2.6F;
   }

   static class FlyRandomlyGoal extends Goal {
      private final GhastEntity ghast;

      public FlyRandomlyGoal(GhastEntity ghast) {
         this.ghast = ghast;
         this.setControls(EnumSet.of(Goal.Control.MOVE));
      }

      @Override
      public boolean canStart() {
         MoveControl _snowman = this.ghast.getMoveControl();
         if (!_snowman.isMoving()) {
            return true;
         } else {
            double _snowmanx = _snowman.getTargetX() - this.ghast.getX();
            double _snowmanxx = _snowman.getTargetY() - this.ghast.getY();
            double _snowmanxxx = _snowman.getTargetZ() - this.ghast.getZ();
            double _snowmanxxxx = _snowmanx * _snowmanx + _snowmanxx * _snowmanxx + _snowmanxxx * _snowmanxxx;
            return _snowmanxxxx < 1.0 || _snowmanxxxx > 3600.0;
         }
      }

      @Override
      public boolean shouldContinue() {
         return false;
      }

      @Override
      public void start() {
         Random _snowman = this.ghast.getRandom();
         double _snowmanx = this.ghast.getX() + (double)((_snowman.nextFloat() * 2.0F - 1.0F) * 16.0F);
         double _snowmanxx = this.ghast.getY() + (double)((_snowman.nextFloat() * 2.0F - 1.0F) * 16.0F);
         double _snowmanxxx = this.ghast.getZ() + (double)((_snowman.nextFloat() * 2.0F - 1.0F) * 16.0F);
         this.ghast.getMoveControl().moveTo(_snowmanx, _snowmanxx, _snowmanxxx, 1.0);
      }
   }

   static class GhastMoveControl extends MoveControl {
      private final GhastEntity ghast;
      private int collisionCheckCooldown;

      public GhastMoveControl(GhastEntity ghast) {
         super(ghast);
         this.ghast = ghast;
      }

      @Override
      public void tick() {
         if (this.state == MoveControl.State.MOVE_TO) {
            if (this.collisionCheckCooldown-- <= 0) {
               this.collisionCheckCooldown = this.collisionCheckCooldown + this.ghast.getRandom().nextInt(5) + 2;
               Vec3d _snowman = new Vec3d(this.targetX - this.ghast.getX(), this.targetY - this.ghast.getY(), this.targetZ - this.ghast.getZ());
               double _snowmanx = _snowman.length();
               _snowman = _snowman.normalize();
               if (this.willCollide(_snowman, MathHelper.ceil(_snowmanx))) {
                  this.ghast.setVelocity(this.ghast.getVelocity().add(_snowman.multiply(0.1)));
               } else {
                  this.state = MoveControl.State.WAIT;
               }
            }
         }
      }

      private boolean willCollide(Vec3d direction, int steps) {
         Box _snowman = this.ghast.getBoundingBox();

         for (int _snowmanx = 1; _snowmanx < steps; _snowmanx++) {
            _snowman = _snowman.offset(direction);
            if (!this.ghast.world.isSpaceEmpty(this.ghast, _snowman)) {
               return false;
            }
         }

         return true;
      }
   }

   static class LookAtTargetGoal extends Goal {
      private final GhastEntity ghast;

      public LookAtTargetGoal(GhastEntity ghast) {
         this.ghast = ghast;
         this.setControls(EnumSet.of(Goal.Control.LOOK));
      }

      @Override
      public boolean canStart() {
         return true;
      }

      @Override
      public void tick() {
         if (this.ghast.getTarget() == null) {
            Vec3d _snowman = this.ghast.getVelocity();
            this.ghast.yaw = -((float)MathHelper.atan2(_snowman.x, _snowman.z)) * (180.0F / (float)Math.PI);
            this.ghast.bodyYaw = this.ghast.yaw;
         } else {
            LivingEntity _snowman = this.ghast.getTarget();
            double _snowmanx = 64.0;
            if (_snowman.squaredDistanceTo(this.ghast) < 4096.0) {
               double _snowmanxx = _snowman.getX() - this.ghast.getX();
               double _snowmanxxx = _snowman.getZ() - this.ghast.getZ();
               this.ghast.yaw = -((float)MathHelper.atan2(_snowmanxx, _snowmanxxx)) * (180.0F / (float)Math.PI);
               this.ghast.bodyYaw = this.ghast.yaw;
            }
         }
      }
   }

   static class ShootFireballGoal extends Goal {
      private final GhastEntity ghast;
      public int cooldown;

      public ShootFireballGoal(GhastEntity ghast) {
         this.ghast = ghast;
      }

      @Override
      public boolean canStart() {
         return this.ghast.getTarget() != null;
      }

      @Override
      public void start() {
         this.cooldown = 0;
      }

      @Override
      public void stop() {
         this.ghast.setShooting(false);
      }

      @Override
      public void tick() {
         LivingEntity _snowman = this.ghast.getTarget();
         double _snowmanx = 64.0;
         if (_snowman.squaredDistanceTo(this.ghast) < 4096.0 && this.ghast.canSee(_snowman)) {
            World _snowmanxx = this.ghast.world;
            this.cooldown++;
            if (this.cooldown == 10 && !this.ghast.isSilent()) {
               _snowmanxx.syncWorldEvent(null, 1015, this.ghast.getBlockPos(), 0);
            }

            if (this.cooldown == 20) {
               double _snowmanxxx = 4.0;
               Vec3d _snowmanxxxx = this.ghast.getRotationVec(1.0F);
               double _snowmanxxxxx = _snowman.getX() - (this.ghast.getX() + _snowmanxxxx.x * 4.0);
               double _snowmanxxxxxx = _snowman.getBodyY(0.5) - (0.5 + this.ghast.getBodyY(0.5));
               double _snowmanxxxxxxx = _snowman.getZ() - (this.ghast.getZ() + _snowmanxxxx.z * 4.0);
               if (!this.ghast.isSilent()) {
                  _snowmanxx.syncWorldEvent(null, 1016, this.ghast.getBlockPos(), 0);
               }

               FireballEntity _snowmanxxxxxxxx = new FireballEntity(_snowmanxx, this.ghast, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx);
               _snowmanxxxxxxxx.explosionPower = this.ghast.getFireballStrength();
               _snowmanxxxxxxxx.updatePosition(this.ghast.getX() + _snowmanxxxx.x * 4.0, this.ghast.getBodyY(0.5) + 0.5, _snowmanxxxxxxxx.getZ() + _snowmanxxxx.z * 4.0);
               _snowmanxx.spawnEntity(_snowmanxxxxxxxx);
               this.cooldown = -40;
            }
         } else if (this.cooldown > 0) {
            this.cooldown--;
         }

         this.ghast.setShooting(this.cooldown > 10);
      }
   }
}
