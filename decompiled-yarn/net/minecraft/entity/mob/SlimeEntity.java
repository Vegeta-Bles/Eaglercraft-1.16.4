package net.minecraft.entity.mob;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.ChunkRandom;

public class SlimeEntity extends MobEntity implements Monster {
   private static final TrackedData<Integer> SLIME_SIZE = DataTracker.registerData(SlimeEntity.class, TrackedDataHandlerRegistry.INTEGER);
   public float targetStretch;
   public float stretch;
   public float lastStretch;
   private boolean onGroundLastTick;

   public SlimeEntity(EntityType<? extends SlimeEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
      this.moveControl = new SlimeEntity.SlimeMoveControl(this);
   }

   @Override
   protected void initGoals() {
      this.goalSelector.add(1, new SlimeEntity.SwimmingGoal(this));
      this.goalSelector.add(2, new SlimeEntity.FaceTowardTargetGoal(this));
      this.goalSelector.add(3, new SlimeEntity.RandomLookGoal(this));
      this.goalSelector.add(5, new SlimeEntity.MoveGoal(this));
      this.targetSelector.add(1, new FollowTargetGoal<>(this, PlayerEntity.class, 10, true, false, _snowman -> Math.abs(_snowman.getY() - this.getY()) <= 4.0));
      this.targetSelector.add(3, new FollowTargetGoal<>(this, IronGolemEntity.class, true));
   }

   @Override
   protected void initDataTracker() {
      super.initDataTracker();
      this.dataTracker.startTracking(SLIME_SIZE, 1);
   }

   protected void setSize(int size, boolean heal) {
      this.dataTracker.set(SLIME_SIZE, size);
      this.refreshPosition();
      this.calculateDimensions();
      this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue((double)(size * size));
      this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue((double)(0.2F + 0.1F * (float)size));
      this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue((double)size);
      if (heal) {
         this.setHealth(this.getMaxHealth());
      }

      this.experiencePoints = size;
   }

   public int getSize() {
      return this.dataTracker.get(SLIME_SIZE);
   }

   @Override
   public void writeCustomDataToTag(CompoundTag tag) {
      super.writeCustomDataToTag(tag);
      tag.putInt("Size", this.getSize() - 1);
      tag.putBoolean("wasOnGround", this.onGroundLastTick);
   }

   @Override
   public void readCustomDataFromTag(CompoundTag tag) {
      int _snowman = tag.getInt("Size");
      if (_snowman < 0) {
         _snowman = 0;
      }

      this.setSize(_snowman + 1, false);
      super.readCustomDataFromTag(tag);
      this.onGroundLastTick = tag.getBoolean("wasOnGround");
   }

   public boolean isSmall() {
      return this.getSize() <= 1;
   }

   protected ParticleEffect getParticles() {
      return ParticleTypes.ITEM_SLIME;
   }

   @Override
   protected boolean isDisallowedInPeaceful() {
      return this.getSize() > 0;
   }

   @Override
   public void tick() {
      this.stretch = this.stretch + (this.targetStretch - this.stretch) * 0.5F;
      this.lastStretch = this.stretch;
      super.tick();
      if (this.onGround && !this.onGroundLastTick) {
         int _snowman = this.getSize();

         for (int _snowmanx = 0; _snowmanx < _snowman * 8; _snowmanx++) {
            float _snowmanxx = this.random.nextFloat() * (float) (Math.PI * 2);
            float _snowmanxxx = this.random.nextFloat() * 0.5F + 0.5F;
            float _snowmanxxxx = MathHelper.sin(_snowmanxx) * (float)_snowman * 0.5F * _snowmanxxx;
            float _snowmanxxxxx = MathHelper.cos(_snowmanxx) * (float)_snowman * 0.5F * _snowmanxxx;
            this.world.addParticle(this.getParticles(), this.getX() + (double)_snowmanxxxx, this.getY(), this.getZ() + (double)_snowmanxxxxx, 0.0, 0.0, 0.0);
         }

         this.playSound(this.getSquishSound(), this.getSoundVolume(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) / 0.8F);
         this.targetStretch = -0.5F;
      } else if (!this.onGround && this.onGroundLastTick) {
         this.targetStretch = 1.0F;
      }

      this.onGroundLastTick = this.onGround;
      this.updateStretch();
   }

   protected void updateStretch() {
      this.targetStretch *= 0.6F;
   }

   protected int getTicksUntilNextJump() {
      return this.random.nextInt(20) + 10;
   }

   @Override
   public void calculateDimensions() {
      double _snowman = this.getX();
      double _snowmanx = this.getY();
      double _snowmanxx = this.getZ();
      super.calculateDimensions();
      this.updatePosition(_snowman, _snowmanx, _snowmanxx);
   }

   @Override
   public void onTrackedDataSet(TrackedData<?> data) {
      if (SLIME_SIZE.equals(data)) {
         this.calculateDimensions();
         this.yaw = this.headYaw;
         this.bodyYaw = this.headYaw;
         if (this.isTouchingWater() && this.random.nextInt(20) == 0) {
            this.onSwimmingStart();
         }
      }

      super.onTrackedDataSet(data);
   }

   @Override
   public EntityType<? extends SlimeEntity> getType() {
      return (EntityType<? extends SlimeEntity>)super.getType();
   }

   @Override
   public void remove() {
      int _snowman = this.getSize();
      if (!this.world.isClient && _snowman > 1 && this.isDead()) {
         Text _snowmanx = this.getCustomName();
         boolean _snowmanxx = this.isAiDisabled();
         float _snowmanxxx = (float)_snowman / 4.0F;
         int _snowmanxxxx = _snowman / 2;
         int _snowmanxxxxx = 2 + this.random.nextInt(3);

         for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < _snowmanxxxxx; _snowmanxxxxxx++) {
            float _snowmanxxxxxxx = ((float)(_snowmanxxxxxx % 2) - 0.5F) * _snowmanxxx;
            float _snowmanxxxxxxxx = ((float)(_snowmanxxxxxx / 2) - 0.5F) * _snowmanxxx;
            SlimeEntity _snowmanxxxxxxxxx = this.getType().create(this.world);
            if (this.isPersistent()) {
               _snowmanxxxxxxxxx.setPersistent();
            }

            _snowmanxxxxxxxxx.setCustomName(_snowmanx);
            _snowmanxxxxxxxxx.setAiDisabled(_snowmanxx);
            _snowmanxxxxxxxxx.setInvulnerable(this.isInvulnerable());
            _snowmanxxxxxxxxx.setSize(_snowmanxxxx, true);
            _snowmanxxxxxxxxx.refreshPositionAndAngles(
               this.getX() + (double)_snowmanxxxxxxx, this.getY() + 0.5, this.getZ() + (double)_snowmanxxxxxxxx, this.random.nextFloat() * 360.0F, 0.0F
            );
            this.world.spawnEntity(_snowmanxxxxxxxxx);
         }
      }

      super.remove();
   }

   @Override
   public void pushAwayFrom(Entity entity) {
      super.pushAwayFrom(entity);
      if (entity instanceof IronGolemEntity && this.canAttack()) {
         this.damage((LivingEntity)entity);
      }
   }

   @Override
   public void onPlayerCollision(PlayerEntity player) {
      if (this.canAttack()) {
         this.damage(player);
      }
   }

   protected void damage(LivingEntity target) {
      if (this.isAlive()) {
         int _snowman = this.getSize();
         if (this.squaredDistanceTo(target) < 0.6 * (double)_snowman * 0.6 * (double)_snowman
            && this.canSee(target)
            && target.damage(DamageSource.mob(this), this.getDamageAmount())) {
            this.playSound(SoundEvents.ENTITY_SLIME_ATTACK, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            this.dealDamage(this, target);
         }
      }
   }

   @Override
   protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
      return 0.625F * dimensions.height;
   }

   protected boolean canAttack() {
      return !this.isSmall() && this.canMoveVoluntarily();
   }

   protected float getDamageAmount() {
      return (float)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource source) {
      return this.isSmall() ? SoundEvents.ENTITY_SLIME_HURT_SMALL : SoundEvents.ENTITY_SLIME_HURT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return this.isSmall() ? SoundEvents.ENTITY_SLIME_DEATH_SMALL : SoundEvents.ENTITY_SLIME_DEATH;
   }

   protected SoundEvent getSquishSound() {
      return this.isSmall() ? SoundEvents.ENTITY_SLIME_SQUISH_SMALL : SoundEvents.ENTITY_SLIME_SQUISH;
   }

   @Override
   protected Identifier getLootTableId() {
      return this.getSize() == 1 ? this.getType().getLootTableId() : LootTables.EMPTY;
   }

   public static boolean canSpawn(EntityType<SlimeEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
      if (world.getDifficulty() != Difficulty.PEACEFUL) {
         if (Objects.equals(world.method_31081(pos), Optional.of(BiomeKeys.SWAMP))
            && pos.getY() > 50
            && pos.getY() < 70
            && random.nextFloat() < 0.5F
            && random.nextFloat() < world.getMoonSize()
            && world.getLightLevel(pos) <= random.nextInt(8)) {
            return canMobSpawn(type, world, spawnReason, pos, random);
         }

         if (!(world instanceof StructureWorldAccess)) {
            return false;
         }

         ChunkPos _snowman = new ChunkPos(pos);
         boolean _snowmanx = ChunkRandom.getSlimeRandom(_snowman.x, _snowman.z, ((StructureWorldAccess)world).getSeed(), 987234911L).nextInt(10) == 0;
         if (random.nextInt(10) == 0 && _snowmanx && pos.getY() < 40) {
            return canMobSpawn(type, world, spawnReason, pos, random);
         }
      }

      return false;
   }

   @Override
   protected float getSoundVolume() {
      return 0.4F * (float)this.getSize();
   }

   @Override
   public int getLookPitchSpeed() {
      return 0;
   }

   protected boolean makesJumpSound() {
      return this.getSize() > 0;
   }

   @Override
   protected void jump() {
      Vec3d _snowman = this.getVelocity();
      this.setVelocity(_snowman.x, (double)this.getJumpVelocity(), _snowman.z);
      this.velocityDirty = true;
   }

   @Nullable
   @Override
   public EntityData initialize(
      ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable CompoundTag entityTag
   ) {
      int _snowman = this.random.nextInt(3);
      if (_snowman < 2 && this.random.nextFloat() < 0.5F * difficulty.getClampedLocalDifficulty()) {
         _snowman++;
      }

      int _snowmanx = 1 << _snowman;
      this.setSize(_snowmanx, true);
      return super.initialize(world, difficulty, spawnReason, entityData, entityTag);
   }

   private float getJumpSoundPitch() {
      float _snowman = this.isSmall() ? 1.4F : 0.8F;
      return ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) * _snowman;
   }

   protected SoundEvent getJumpSound() {
      return this.isSmall() ? SoundEvents.ENTITY_SLIME_JUMP_SMALL : SoundEvents.ENTITY_SLIME_JUMP;
   }

   @Override
   public EntityDimensions getDimensions(EntityPose pose) {
      return super.getDimensions(pose).scaled(0.255F * (float)this.getSize());
   }

   static class FaceTowardTargetGoal extends Goal {
      private final SlimeEntity slime;
      private int ticksLeft;

      public FaceTowardTargetGoal(SlimeEntity slime) {
         this.slime = slime;
         this.setControls(EnumSet.of(Goal.Control.LOOK));
      }

      @Override
      public boolean canStart() {
         LivingEntity _snowman = this.slime.getTarget();
         if (_snowman == null) {
            return false;
         } else if (!_snowman.isAlive()) {
            return false;
         } else {
            return _snowman instanceof PlayerEntity && ((PlayerEntity)_snowman).abilities.invulnerable
               ? false
               : this.slime.getMoveControl() instanceof SlimeEntity.SlimeMoveControl;
         }
      }

      @Override
      public void start() {
         this.ticksLeft = 300;
         super.start();
      }

      @Override
      public boolean shouldContinue() {
         LivingEntity _snowman = this.slime.getTarget();
         if (_snowman == null) {
            return false;
         } else if (!_snowman.isAlive()) {
            return false;
         } else {
            return _snowman instanceof PlayerEntity && ((PlayerEntity)_snowman).abilities.invulnerable ? false : --this.ticksLeft > 0;
         }
      }

      @Override
      public void tick() {
         this.slime.lookAtEntity(this.slime.getTarget(), 10.0F, 10.0F);
         ((SlimeEntity.SlimeMoveControl)this.slime.getMoveControl()).look(this.slime.yaw, this.slime.canAttack());
      }
   }

   static class MoveGoal extends Goal {
      private final SlimeEntity slime;

      public MoveGoal(SlimeEntity slime) {
         this.slime = slime;
         this.setControls(EnumSet.of(Goal.Control.JUMP, Goal.Control.MOVE));
      }

      @Override
      public boolean canStart() {
         return !this.slime.hasVehicle();
      }

      @Override
      public void tick() {
         ((SlimeEntity.SlimeMoveControl)this.slime.getMoveControl()).move(1.0);
      }
   }

   static class RandomLookGoal extends Goal {
      private final SlimeEntity slime;
      private float targetYaw;
      private int timer;

      public RandomLookGoal(SlimeEntity slime) {
         this.slime = slime;
         this.setControls(EnumSet.of(Goal.Control.LOOK));
      }

      @Override
      public boolean canStart() {
         return this.slime.getTarget() == null
            && (this.slime.onGround || this.slime.isTouchingWater() || this.slime.isInLava() || this.slime.hasStatusEffect(StatusEffects.LEVITATION))
            && this.slime.getMoveControl() instanceof SlimeEntity.SlimeMoveControl;
      }

      @Override
      public void tick() {
         if (--this.timer <= 0) {
            this.timer = 40 + this.slime.getRandom().nextInt(60);
            this.targetYaw = (float)this.slime.getRandom().nextInt(360);
         }

         ((SlimeEntity.SlimeMoveControl)this.slime.getMoveControl()).look(this.targetYaw, false);
      }
   }

   static class SlimeMoveControl extends MoveControl {
      private float targetYaw;
      private int ticksUntilJump;
      private final SlimeEntity slime;
      private boolean jumpOften;

      public SlimeMoveControl(SlimeEntity slime) {
         super(slime);
         this.slime = slime;
         this.targetYaw = 180.0F * slime.yaw / (float) Math.PI;
      }

      public void look(float targetYaw, boolean jumpOften) {
         this.targetYaw = targetYaw;
         this.jumpOften = jumpOften;
      }

      public void move(double speed) {
         this.speed = speed;
         this.state = MoveControl.State.MOVE_TO;
      }

      @Override
      public void tick() {
         this.entity.yaw = this.changeAngle(this.entity.yaw, this.targetYaw, 90.0F);
         this.entity.headYaw = this.entity.yaw;
         this.entity.bodyYaw = this.entity.yaw;
         if (this.state != MoveControl.State.MOVE_TO) {
            this.entity.setForwardSpeed(0.0F);
         } else {
            this.state = MoveControl.State.WAIT;
            if (this.entity.isOnGround()) {
               this.entity.setMovementSpeed((float)(this.speed * this.entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)));
               if (this.ticksUntilJump-- <= 0) {
                  this.ticksUntilJump = this.slime.getTicksUntilNextJump();
                  if (this.jumpOften) {
                     this.ticksUntilJump /= 3;
                  }

                  this.slime.getJumpControl().setActive();
                  if (this.slime.makesJumpSound()) {
                     this.slime.playSound(this.slime.getJumpSound(), this.slime.getSoundVolume(), this.slime.getJumpSoundPitch());
                  }
               } else {
                  this.slime.sidewaysSpeed = 0.0F;
                  this.slime.forwardSpeed = 0.0F;
                  this.entity.setMovementSpeed(0.0F);
               }
            } else {
               this.entity.setMovementSpeed((float)(this.speed * this.entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)));
            }
         }
      }
   }

   static class SwimmingGoal extends Goal {
      private final SlimeEntity slime;

      public SwimmingGoal(SlimeEntity slime) {
         this.slime = slime;
         this.setControls(EnumSet.of(Goal.Control.JUMP, Goal.Control.MOVE));
         slime.getNavigation().setCanSwim(true);
      }

      @Override
      public boolean canStart() {
         return (this.slime.isTouchingWater() || this.slime.isInLava()) && this.slime.getMoveControl() instanceof SlimeEntity.SlimeMoveControl;
      }

      @Override
      public void tick() {
         if (this.slime.getRandom().nextFloat() < 0.8F) {
            this.slime.getJumpControl().setActive();
         }

         ((SlimeEntity.SlimeMoveControl)this.slime.getMoveControl()).move(1.2);
      }
   }
}
