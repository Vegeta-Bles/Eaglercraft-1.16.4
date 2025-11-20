package net.minecraft.entity.passive;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.AmbientEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class BatEntity extends AmbientEntity {
   private static final TrackedData<Byte> BAT_FLAGS = DataTracker.registerData(BatEntity.class, TrackedDataHandlerRegistry.BYTE);
   private static final TargetPredicate CLOSE_PLAYER_PREDICATE = new TargetPredicate().setBaseMaxDistance(4.0).includeTeammates();
   private BlockPos hangingPosition;

   public BatEntity(EntityType<? extends BatEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
      this.setRoosting(true);
   }

   @Override
   protected void initDataTracker() {
      super.initDataTracker();
      this.dataTracker.startTracking(BAT_FLAGS, (byte)0);
   }

   @Override
   protected float getSoundVolume() {
      return 0.1F;
   }

   @Override
   protected float getSoundPitch() {
      return super.getSoundPitch() * 0.95F;
   }

   @Nullable
   @Override
   public SoundEvent getAmbientSound() {
      return this.isRoosting() && this.random.nextInt(4) != 0 ? null : SoundEvents.ENTITY_BAT_AMBIENT;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource source) {
      return SoundEvents.ENTITY_BAT_HURT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.ENTITY_BAT_DEATH;
   }

   @Override
   public boolean isPushable() {
      return false;
   }

   @Override
   protected void pushAway(Entity entity) {
   }

   @Override
   protected void tickCramming() {
   }

   public static DefaultAttributeContainer.Builder createBatAttributes() {
      return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 6.0);
   }

   public boolean isRoosting() {
      return (this.dataTracker.get(BAT_FLAGS) & 1) != 0;
   }

   public void setRoosting(boolean roosting) {
      byte _snowman = this.dataTracker.get(BAT_FLAGS);
      if (roosting) {
         this.dataTracker.set(BAT_FLAGS, (byte)(_snowman | 1));
      } else {
         this.dataTracker.set(BAT_FLAGS, (byte)(_snowman & -2));
      }
   }

   @Override
   public void tick() {
      super.tick();
      if (this.isRoosting()) {
         this.setVelocity(Vec3d.ZERO);
         this.setPos(this.getX(), (double)MathHelper.floor(this.getY()) + 1.0 - (double)this.getHeight(), this.getZ());
      } else {
         this.setVelocity(this.getVelocity().multiply(1.0, 0.6, 1.0));
      }
   }

   @Override
   protected void mobTick() {
      super.mobTick();
      BlockPos _snowman = this.getBlockPos();
      BlockPos _snowmanx = _snowman.up();
      if (this.isRoosting()) {
         boolean _snowmanxx = this.isSilent();
         if (this.world.getBlockState(_snowmanx).isSolidBlock(this.world, _snowman)) {
            if (this.random.nextInt(200) == 0) {
               this.headYaw = (float)this.random.nextInt(360);
            }

            if (this.world.getClosestPlayer(CLOSE_PLAYER_PREDICATE, this) != null) {
               this.setRoosting(false);
               if (!_snowmanxx) {
                  this.world.syncWorldEvent(null, 1025, _snowman, 0);
               }
            }
         } else {
            this.setRoosting(false);
            if (!_snowmanxx) {
               this.world.syncWorldEvent(null, 1025, _snowman, 0);
            }
         }
      } else {
         if (this.hangingPosition != null && (!this.world.isAir(this.hangingPosition) || this.hangingPosition.getY() < 1)) {
            this.hangingPosition = null;
         }

         if (this.hangingPosition == null || this.random.nextInt(30) == 0 || this.hangingPosition.isWithinDistance(this.getPos(), 2.0)) {
            this.hangingPosition = new BlockPos(
               this.getX() + (double)this.random.nextInt(7) - (double)this.random.nextInt(7),
               this.getY() + (double)this.random.nextInt(6) - 2.0,
               this.getZ() + (double)this.random.nextInt(7) - (double)this.random.nextInt(7)
            );
         }

         double _snowmanxx = (double)this.hangingPosition.getX() + 0.5 - this.getX();
         double _snowmanxxx = (double)this.hangingPosition.getY() + 0.1 - this.getY();
         double _snowmanxxxx = (double)this.hangingPosition.getZ() + 0.5 - this.getZ();
         Vec3d _snowmanxxxxx = this.getVelocity();
         Vec3d _snowmanxxxxxx = _snowmanxxxxx.add(
            (Math.signum(_snowmanxx) * 0.5 - _snowmanxxxxx.x) * 0.1F, (Math.signum(_snowmanxxx) * 0.7F - _snowmanxxxxx.y) * 0.1F, (Math.signum(_snowmanxxxx) * 0.5 - _snowmanxxxxx.z) * 0.1F
         );
         this.setVelocity(_snowmanxxxxxx);
         float _snowmanxxxxxxx = (float)(MathHelper.atan2(_snowmanxxxxxx.z, _snowmanxxxxxx.x) * 180.0F / (float)Math.PI) - 90.0F;
         float _snowmanxxxxxxxx = MathHelper.wrapDegrees(_snowmanxxxxxxx - this.yaw);
         this.forwardSpeed = 0.5F;
         this.yaw += _snowmanxxxxxxxx;
         if (this.random.nextInt(100) == 0 && this.world.getBlockState(_snowmanx).isSolidBlock(this.world, _snowmanx)) {
            this.setRoosting(true);
         }
      }
   }

   @Override
   protected boolean canClimb() {
      return false;
   }

   @Override
   public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
      return false;
   }

   @Override
   protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
   }

   @Override
   public boolean canAvoidTraps() {
      return true;
   }

   @Override
   public boolean damage(DamageSource source, float amount) {
      if (this.isInvulnerableTo(source)) {
         return false;
      } else {
         if (!this.world.isClient && this.isRoosting()) {
            this.setRoosting(false);
         }

         return super.damage(source, amount);
      }
   }

   @Override
   public void readCustomDataFromTag(CompoundTag tag) {
      super.readCustomDataFromTag(tag);
      this.dataTracker.set(BAT_FLAGS, tag.getByte("BatFlags"));
   }

   @Override
   public void writeCustomDataToTag(CompoundTag tag) {
      super.writeCustomDataToTag(tag);
      tag.putByte("BatFlags", this.dataTracker.get(BAT_FLAGS));
   }

   public static boolean canSpawn(EntityType<BatEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
      if (pos.getY() >= world.getSeaLevel()) {
         return false;
      } else {
         int _snowman = world.getLightLevel(pos);
         int _snowmanx = 4;
         if (isTodayAroundHalloween()) {
            _snowmanx = 7;
         } else if (random.nextBoolean()) {
            return false;
         }

         return _snowman > random.nextInt(_snowmanx) ? false : canMobSpawn(type, world, spawnReason, pos, random);
      }
   }

   private static boolean isTodayAroundHalloween() {
      LocalDate _snowman = LocalDate.now();
      int _snowmanx = _snowman.get(ChronoField.DAY_OF_MONTH);
      int _snowmanxx = _snowman.get(ChronoField.MONTH_OF_YEAR);
      return _snowmanxx == 10 && _snowmanx >= 20 || _snowmanxx == 11 && _snowmanx <= 3;
   }

   @Override
   protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
      return dimensions.height / 2.0F;
   }
}
