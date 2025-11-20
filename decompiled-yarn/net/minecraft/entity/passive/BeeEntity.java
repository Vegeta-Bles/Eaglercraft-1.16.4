package net.minecraft.entity.passive;

import com.google.common.collect.Lists;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.block.StemBlock;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Flutterer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.Durations;
import net.minecraft.entity.ai.TargetFinder;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.control.LookControl;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.UniversalAngerGoal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.IntProperty;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.IntRange;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.poi.PointOfInterest;
import net.minecraft.world.poi.PointOfInterestStorage;
import net.minecraft.world.poi.PointOfInterestType;

public class BeeEntity extends AnimalEntity implements Angerable, Flutterer {
   private static final TrackedData<Byte> STATUS_TRACKER = DataTracker.registerData(BeeEntity.class, TrackedDataHandlerRegistry.BYTE);
   private static final TrackedData<Integer> ANGER = DataTracker.registerData(BeeEntity.class, TrackedDataHandlerRegistry.INTEGER);
   private static final IntRange ANGER_TIME_RANGE = Durations.betweenSeconds(20, 39);
   private UUID targetUuid;
   private float currentPitch;
   private float lastPitch;
   private int ticksSinceSting;
   private int ticksSincePollination;
   private int cannotEnterHiveTicks;
   private int cropsGrownSincePollination;
   private int ticksLeftToFindHive = 0;
   private int ticksUntilCanPollinate = 0;
   @Nullable
   private BlockPos flowerPos = null;
   @Nullable
   private BlockPos hivePos = null;
   private BeeEntity.PollinateGoal pollinateGoal;
   private BeeEntity.MoveToHiveGoal moveToHiveGoal;
   private BeeEntity.MoveToFlowerGoal moveToFlowerGoal;
   private int ticksInsideWater;

   public BeeEntity(EntityType<? extends BeeEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
      this.moveControl = new FlightMoveControl(this, 20, true);
      this.lookControl = new BeeEntity.BeeLookControl(this);
      this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0F);
      this.setPathfindingPenalty(PathNodeType.WATER, -1.0F);
      this.setPathfindingPenalty(PathNodeType.WATER_BORDER, 16.0F);
      this.setPathfindingPenalty(PathNodeType.COCOA, -1.0F);
      this.setPathfindingPenalty(PathNodeType.FENCE, -1.0F);
   }

   @Override
   protected void initDataTracker() {
      super.initDataTracker();
      this.dataTracker.startTracking(STATUS_TRACKER, (byte)0);
      this.dataTracker.startTracking(ANGER, 0);
   }

   @Override
   public float getPathfindingFavor(BlockPos pos, WorldView world) {
      return world.getBlockState(pos).isAir() ? 10.0F : 0.0F;
   }

   @Override
   protected void initGoals() {
      this.goalSelector.add(0, new BeeEntity.StingGoal(this, 1.4F, true));
      this.goalSelector.add(1, new BeeEntity.EnterHiveGoal());
      this.goalSelector.add(2, new AnimalMateGoal(this, 1.0));
      this.goalSelector.add(3, new TemptGoal(this, 1.25, Ingredient.fromTag(ItemTags.FLOWERS), false));
      this.pollinateGoal = new BeeEntity.PollinateGoal();
      this.goalSelector.add(4, this.pollinateGoal);
      this.goalSelector.add(5, new FollowParentGoal(this, 1.25));
      this.goalSelector.add(5, new BeeEntity.FindHiveGoal());
      this.moveToHiveGoal = new BeeEntity.MoveToHiveGoal();
      this.goalSelector.add(5, this.moveToHiveGoal);
      this.moveToFlowerGoal = new BeeEntity.MoveToFlowerGoal();
      this.goalSelector.add(6, this.moveToFlowerGoal);
      this.goalSelector.add(7, new BeeEntity.GrowCropsGoal());
      this.goalSelector.add(8, new BeeEntity.BeeWanderAroundGoal());
      this.goalSelector.add(9, new SwimGoal(this));
      this.targetSelector.add(1, new BeeEntity.BeeRevengeGoal(this).setGroupRevenge(new Class[0]));
      this.targetSelector.add(2, new BeeEntity.BeeFollowTargetGoal(this));
      this.targetSelector.add(3, new UniversalAngerGoal<>(this, true));
   }

   @Override
   public void writeCustomDataToTag(CompoundTag tag) {
      super.writeCustomDataToTag(tag);
      if (this.hasHive()) {
         tag.put("HivePos", NbtHelper.fromBlockPos(this.getHivePos()));
      }

      if (this.hasFlower()) {
         tag.put("FlowerPos", NbtHelper.fromBlockPos(this.getFlowerPos()));
      }

      tag.putBoolean("HasNectar", this.hasNectar());
      tag.putBoolean("HasStung", this.hasStung());
      tag.putInt("TicksSincePollination", this.ticksSincePollination);
      tag.putInt("CannotEnterHiveTicks", this.cannotEnterHiveTicks);
      tag.putInt("CropsGrownSincePollination", this.cropsGrownSincePollination);
      this.angerToTag(tag);
   }

   @Override
   public void readCustomDataFromTag(CompoundTag tag) {
      this.hivePos = null;
      if (tag.contains("HivePos")) {
         this.hivePos = NbtHelper.toBlockPos(tag.getCompound("HivePos"));
      }

      this.flowerPos = null;
      if (tag.contains("FlowerPos")) {
         this.flowerPos = NbtHelper.toBlockPos(tag.getCompound("FlowerPos"));
      }

      super.readCustomDataFromTag(tag);
      this.setHasNectar(tag.getBoolean("HasNectar"));
      this.setHasStung(tag.getBoolean("HasStung"));
      this.ticksSincePollination = tag.getInt("TicksSincePollination");
      this.cannotEnterHiveTicks = tag.getInt("CannotEnterHiveTicks");
      this.cropsGrownSincePollination = tag.getInt("CropsGrownSincePollination");
      this.angerFromTag((ServerWorld)this.world, tag);
   }

   @Override
   public boolean tryAttack(Entity target) {
      boolean _snowman = target.damage(DamageSource.sting(this), (float)((int)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE)));
      if (_snowman) {
         this.dealDamage(this, target);
         if (target instanceof LivingEntity) {
            ((LivingEntity)target).setStingerCount(((LivingEntity)target).getStingerCount() + 1);
            int _snowmanx = 0;
            if (this.world.getDifficulty() == Difficulty.NORMAL) {
               _snowmanx = 10;
            } else if (this.world.getDifficulty() == Difficulty.HARD) {
               _snowmanx = 18;
            }

            if (_snowmanx > 0) {
               ((LivingEntity)target).addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, _snowmanx * 20, 0));
            }
         }

         this.setHasStung(true);
         this.stopAnger();
         this.playSound(SoundEvents.ENTITY_BEE_STING, 1.0F, 1.0F);
      }

      return _snowman;
   }

   @Override
   public void tick() {
      super.tick();
      if (this.hasNectar() && this.getCropsGrownSincePollination() < 10 && this.random.nextFloat() < 0.05F) {
         for (int _snowman = 0; _snowman < this.random.nextInt(2) + 1; _snowman++) {
            this.addParticle(
               this.world, this.getX() - 0.3F, this.getX() + 0.3F, this.getZ() - 0.3F, this.getZ() + 0.3F, this.getBodyY(0.5), ParticleTypes.FALLING_NECTAR
            );
         }
      }

      this.updateBodyPitch();
   }

   private void addParticle(World world, double lastX, double x, double lastZ, double z, double y, ParticleEffect effect) {
      world.addParticle(effect, MathHelper.lerp(world.random.nextDouble(), lastX, x), y, MathHelper.lerp(world.random.nextDouble(), lastZ, z), 0.0, 0.0, 0.0);
   }

   private void startMovingTo(BlockPos pos) {
      Vec3d _snowman = Vec3d.ofBottomCenter(pos);
      int _snowmanx = 0;
      BlockPos _snowmanxx = this.getBlockPos();
      int _snowmanxxx = (int)_snowman.y - _snowmanxx.getY();
      if (_snowmanxxx > 2) {
         _snowmanx = 4;
      } else if (_snowmanxxx < -2) {
         _snowmanx = -4;
      }

      int _snowmanxxxx = 6;
      int _snowmanxxxxx = 8;
      int _snowmanxxxxxx = _snowmanxx.getManhattanDistance(pos);
      if (_snowmanxxxxxx < 15) {
         _snowmanxxxx = _snowmanxxxxxx / 2;
         _snowmanxxxxx = _snowmanxxxxxx / 2;
      }

      Vec3d _snowmanxxxxxxx = TargetFinder.findGroundTargetTowards(this, _snowmanxxxx, _snowmanxxxxx, _snowmanx, _snowman, (float) (Math.PI / 10));
      if (_snowmanxxxxxxx != null) {
         this.navigation.setRangeMultiplier(0.5F);
         this.navigation.startMovingTo(_snowmanxxxxxxx.x, _snowmanxxxxxxx.y, _snowmanxxxxxxx.z, 1.0);
      }
   }

   @Nullable
   public BlockPos getFlowerPos() {
      return this.flowerPos;
   }

   public boolean hasFlower() {
      return this.flowerPos != null;
   }

   public void setFlowerPos(BlockPos pos) {
      this.flowerPos = pos;
   }

   private boolean failedPollinatingTooLong() {
      return this.ticksSincePollination > 3600;
   }

   private boolean canEnterHive() {
      if (this.cannotEnterHiveTicks <= 0 && !this.pollinateGoal.isRunning() && !this.hasStung() && this.getTarget() == null) {
         boolean _snowman = this.failedPollinatingTooLong() || this.world.isRaining() || this.world.isNight() || this.hasNectar();
         return _snowman && !this.isHiveNearFire();
      } else {
         return false;
      }
   }

   public void setCannotEnterHiveTicks(int ticks) {
      this.cannotEnterHiveTicks = ticks;
   }

   public float getBodyPitch(float tickDelta) {
      return MathHelper.lerp(tickDelta, this.lastPitch, this.currentPitch);
   }

   private void updateBodyPitch() {
      this.lastPitch = this.currentPitch;
      if (this.isNearTarget()) {
         this.currentPitch = Math.min(1.0F, this.currentPitch + 0.2F);
      } else {
         this.currentPitch = Math.max(0.0F, this.currentPitch - 0.24F);
      }
   }

   @Override
   protected void mobTick() {
      boolean _snowman = this.hasStung();
      if (this.isInsideWaterOrBubbleColumn()) {
         this.ticksInsideWater++;
      } else {
         this.ticksInsideWater = 0;
      }

      if (this.ticksInsideWater > 20) {
         this.damage(DamageSource.DROWN, 1.0F);
      }

      if (_snowman) {
         this.ticksSinceSting++;
         if (this.ticksSinceSting % 5 == 0 && this.random.nextInt(MathHelper.clamp(1200 - this.ticksSinceSting, 1, 1200)) == 0) {
            this.damage(DamageSource.GENERIC, this.getHealth());
         }
      }

      if (!this.hasNectar()) {
         this.ticksSincePollination++;
      }

      if (!this.world.isClient) {
         this.tickAngerLogic((ServerWorld)this.world, false);
      }
   }

   public void resetPollinationTicks() {
      this.ticksSincePollination = 0;
   }

   private boolean isHiveNearFire() {
      if (this.hivePos == null) {
         return false;
      } else {
         BlockEntity _snowman = this.world.getBlockEntity(this.hivePos);
         return _snowman instanceof BeehiveBlockEntity && ((BeehiveBlockEntity)_snowman).isNearFire();
      }
   }

   @Override
   public int getAngerTime() {
      return this.dataTracker.get(ANGER);
   }

   @Override
   public void setAngerTime(int ticks) {
      this.dataTracker.set(ANGER, ticks);
   }

   @Override
   public UUID getAngryAt() {
      return this.targetUuid;
   }

   @Override
   public void setAngryAt(@Nullable UUID uuid) {
      this.targetUuid = uuid;
   }

   @Override
   public void chooseRandomAngerTime() {
      this.setAngerTime(ANGER_TIME_RANGE.choose(this.random));
   }

   private boolean doesHiveHaveSpace(BlockPos pos) {
      BlockEntity _snowman = this.world.getBlockEntity(pos);
      return _snowman instanceof BeehiveBlockEntity ? !((BeehiveBlockEntity)_snowman).isFullOfBees() : false;
   }

   public boolean hasHive() {
      return this.hivePos != null;
   }

   @Nullable
   public BlockPos getHivePos() {
      return this.hivePos;
   }

   @Override
   protected void sendAiDebugData() {
      super.sendAiDebugData();
      DebugInfoSender.sendBeeDebugData(this);
   }

   private int getCropsGrownSincePollination() {
      return this.cropsGrownSincePollination;
   }

   private void resetCropCounter() {
      this.cropsGrownSincePollination = 0;
   }

   private void addCropCounter() {
      this.cropsGrownSincePollination++;
   }

   @Override
   public void tickMovement() {
      super.tickMovement();
      if (!this.world.isClient) {
         if (this.cannotEnterHiveTicks > 0) {
            this.cannotEnterHiveTicks--;
         }

         if (this.ticksLeftToFindHive > 0) {
            this.ticksLeftToFindHive--;
         }

         if (this.ticksUntilCanPollinate > 0) {
            this.ticksUntilCanPollinate--;
         }

         boolean _snowman = this.hasAngerTime() && !this.hasStung() && this.getTarget() != null && this.getTarget().squaredDistanceTo(this) < 4.0;
         this.setNearTarget(_snowman);
         if (this.age % 20 == 0 && !this.isHiveValid()) {
            this.hivePos = null;
         }
      }
   }

   private boolean isHiveValid() {
      if (!this.hasHive()) {
         return false;
      } else {
         BlockEntity _snowman = this.world.getBlockEntity(this.hivePos);
         return _snowman != null && _snowman.getType() == BlockEntityType.BEEHIVE;
      }
   }

   public boolean hasNectar() {
      return this.getBeeFlag(8);
   }

   private void setHasNectar(boolean hasNectar) {
      if (hasNectar) {
         this.resetPollinationTicks();
      }

      this.setBeeFlag(8, hasNectar);
   }

   public boolean hasStung() {
      return this.getBeeFlag(4);
   }

   private void setHasStung(boolean hasStung) {
      this.setBeeFlag(4, hasStung);
   }

   private boolean isNearTarget() {
      return this.getBeeFlag(2);
   }

   private void setNearTarget(boolean nearTarget) {
      this.setBeeFlag(2, nearTarget);
   }

   private boolean isTooFar(BlockPos pos) {
      return !this.isWithinDistance(pos, 32);
   }

   private void setBeeFlag(int bit, boolean value) {
      if (value) {
         this.dataTracker.set(STATUS_TRACKER, (byte)(this.dataTracker.get(STATUS_TRACKER) | bit));
      } else {
         this.dataTracker.set(STATUS_TRACKER, (byte)(this.dataTracker.get(STATUS_TRACKER) & ~bit));
      }
   }

   private boolean getBeeFlag(int location) {
      return (this.dataTracker.get(STATUS_TRACKER) & location) != 0;
   }

   public static DefaultAttributeContainer.Builder createBeeAttributes() {
      return MobEntity.createMobAttributes()
         .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0)
         .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.6F)
         .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3F)
         .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0)
         .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 48.0);
   }

   @Override
   protected EntityNavigation createNavigation(World world) {
      BirdNavigation _snowman = new BirdNavigation(this, world) {
         @Override
         public boolean isValidPosition(BlockPos pos) {
            return !this.world.getBlockState(pos.down()).isAir();
         }

         @Override
         public void tick() {
            if (!BeeEntity.this.pollinateGoal.isRunning()) {
               super.tick();
            }
         }
      };
      _snowman.setCanPathThroughDoors(false);
      _snowman.setCanSwim(false);
      _snowman.setCanEnterOpenDoors(true);
      return _snowman;
   }

   @Override
   public boolean isBreedingItem(ItemStack stack) {
      return stack.getItem().isIn(ItemTags.FLOWERS);
   }

   private boolean isFlowers(BlockPos pos) {
      return this.world.canSetBlock(pos) && this.world.getBlockState(pos).getBlock().isIn(BlockTags.FLOWERS);
   }

   @Override
   protected void playStepSound(BlockPos pos, BlockState state) {
   }

   @Override
   protected SoundEvent getAmbientSound() {
      return null;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource source) {
      return SoundEvents.ENTITY_BEE_HURT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.ENTITY_BEE_DEATH;
   }

   @Override
   protected float getSoundVolume() {
      return 0.4F;
   }

   public BeeEntity createChild(ServerWorld _snowman, PassiveEntity _snowman) {
      return EntityType.BEE.create(_snowman);
   }

   @Override
   protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
      return this.isBaby() ? dimensions.height * 0.5F : dimensions.height * 0.5F;
   }

   @Override
   public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
      return false;
   }

   @Override
   protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
   }

   @Override
   protected boolean hasWings() {
      return true;
   }

   public void onHoneyDelivered() {
      this.setHasNectar(false);
      this.resetCropCounter();
   }

   @Override
   public boolean damage(DamageSource source, float amount) {
      if (this.isInvulnerableTo(source)) {
         return false;
      } else {
         Entity _snowman = source.getAttacker();
         if (!this.world.isClient) {
            this.pollinateGoal.cancel();
         }

         return super.damage(source, amount);
      }
   }

   @Override
   public EntityGroup getGroup() {
      return EntityGroup.ARTHROPOD;
   }

   @Override
   protected void swimUpward(Tag<Fluid> fluid) {
      this.setVelocity(this.getVelocity().add(0.0, 0.01, 0.0));
   }

   @Override
   public Vec3d method_29919() {
      return new Vec3d(0.0, (double)(0.5F * this.getStandingEyeHeight()), (double)(this.getWidth() * 0.2F));
   }

   private boolean isWithinDistance(BlockPos pos, int distance) {
      return pos.isWithinDistance(this.getBlockPos(), (double)distance);
   }

   static class BeeFollowTargetGoal extends FollowTargetGoal<PlayerEntity> {
      BeeFollowTargetGoal(BeeEntity bee) {
         super(bee, PlayerEntity.class, 10, true, false, bee::shouldAngerAt);
      }

      @Override
      public boolean canStart() {
         return this.canSting() && super.canStart();
      }

      @Override
      public boolean shouldContinue() {
         boolean _snowman = this.canSting();
         if (_snowman && this.mob.getTarget() != null) {
            return super.shouldContinue();
         } else {
            this.target = null;
            return false;
         }
      }

      private boolean canSting() {
         BeeEntity _snowman = (BeeEntity)this.mob;
         return _snowman.hasAngerTime() && !_snowman.hasStung();
      }
   }

   class BeeLookControl extends LookControl {
      BeeLookControl(MobEntity entity) {
         super(entity);
      }

      @Override
      public void tick() {
         if (!BeeEntity.this.hasAngerTime()) {
            super.tick();
         }
      }

      @Override
      protected boolean shouldStayHorizontal() {
         return !BeeEntity.this.pollinateGoal.isRunning();
      }
   }

   class BeeRevengeGoal extends RevengeGoal {
      BeeRevengeGoal(BeeEntity bee) {
         super(bee);
      }

      @Override
      public boolean shouldContinue() {
         return BeeEntity.this.hasAngerTime() && super.shouldContinue();
      }

      @Override
      protected void setMobEntityTarget(MobEntity mob, LivingEntity target) {
         if (mob instanceof BeeEntity && this.mob.canSee(target)) {
            mob.setTarget(target);
         }
      }
   }

   class BeeWanderAroundGoal extends Goal {
      BeeWanderAroundGoal() {
         this.setControls(EnumSet.of(Goal.Control.MOVE));
      }

      @Override
      public boolean canStart() {
         return BeeEntity.this.navigation.isIdle() && BeeEntity.this.random.nextInt(10) == 0;
      }

      @Override
      public boolean shouldContinue() {
         return BeeEntity.this.navigation.isFollowingPath();
      }

      @Override
      public void start() {
         Vec3d _snowman = this.getRandomLocation();
         if (_snowman != null) {
            BeeEntity.this.navigation.startMovingAlong(BeeEntity.this.navigation.findPathTo(new BlockPos(_snowman), 1), 1.0);
         }
      }

      @Nullable
      private Vec3d getRandomLocation() {
         Vec3d _snowman;
         if (BeeEntity.this.isHiveValid() && !BeeEntity.this.isWithinDistance(BeeEntity.this.hivePos, 22)) {
            Vec3d _snowmanx = Vec3d.ofCenter(BeeEntity.this.hivePos);
            _snowman = _snowmanx.subtract(BeeEntity.this.getPos()).normalize();
         } else {
            _snowman = BeeEntity.this.getRotationVec(0.0F);
         }

         int _snowmanx = 8;
         Vec3d _snowmanxx = TargetFinder.findAirTarget(BeeEntity.this, 8, 7, _snowman, (float) (Math.PI / 2), 2, 1);
         return _snowmanxx != null ? _snowmanxx : TargetFinder.findGroundTarget(BeeEntity.this, 8, 4, -2, _snowman, (float) (Math.PI / 2));
      }
   }

   class EnterHiveGoal extends BeeEntity.NotAngryGoal {
      private EnterHiveGoal() {
      }

      @Override
      public boolean canBeeStart() {
         if (BeeEntity.this.hasHive() && BeeEntity.this.canEnterHive() && BeeEntity.this.hivePos.isWithinDistance(BeeEntity.this.getPos(), 2.0)) {
            BlockEntity _snowman = BeeEntity.this.world.getBlockEntity(BeeEntity.this.hivePos);
            if (_snowman instanceof BeehiveBlockEntity) {
               BeehiveBlockEntity _snowmanx = (BeehiveBlockEntity)_snowman;
               if (!_snowmanx.isFullOfBees()) {
                  return true;
               }

               BeeEntity.this.hivePos = null;
            }
         }

         return false;
      }

      @Override
      public boolean canBeeContinue() {
         return false;
      }

      @Override
      public void start() {
         BlockEntity _snowman = BeeEntity.this.world.getBlockEntity(BeeEntity.this.hivePos);
         if (_snowman instanceof BeehiveBlockEntity) {
            BeehiveBlockEntity _snowmanx = (BeehiveBlockEntity)_snowman;
            _snowmanx.tryEnterHive(BeeEntity.this, BeeEntity.this.hasNectar());
         }
      }
   }

   class FindHiveGoal extends BeeEntity.NotAngryGoal {
      private FindHiveGoal() {
      }

      @Override
      public boolean canBeeStart() {
         return BeeEntity.this.ticksLeftToFindHive == 0 && !BeeEntity.this.hasHive() && BeeEntity.this.canEnterHive();
      }

      @Override
      public boolean canBeeContinue() {
         return false;
      }

      @Override
      public void start() {
         BeeEntity.this.ticksLeftToFindHive = 200;
         List<BlockPos> _snowman = this.getNearbyFreeHives();
         if (!_snowman.isEmpty()) {
            for (BlockPos _snowmanx : _snowman) {
               if (!BeeEntity.this.moveToHiveGoal.isPossibleHive(_snowmanx)) {
                  BeeEntity.this.hivePos = _snowmanx;
                  return;
               }
            }

            BeeEntity.this.moveToHiveGoal.clearPossibleHives();
            BeeEntity.this.hivePos = _snowman.get(0);
         }
      }

      private List<BlockPos> getNearbyFreeHives() {
         BlockPos _snowman = BeeEntity.this.getBlockPos();
         PointOfInterestStorage _snowmanx = ((ServerWorld)BeeEntity.this.world).getPointOfInterestStorage();
         Stream<PointOfInterest> _snowmanxx = _snowmanx.getInCircle(
            _snowmanxxx -> _snowmanxxx == PointOfInterestType.BEEHIVE || _snowmanxxx == PointOfInterestType.BEE_NEST, _snowman, 20, PointOfInterestStorage.OccupationStatus.ANY
         );
         return _snowmanxx.map(PointOfInterest::getPos)
            .filter(_snowmanxxx -> BeeEntity.this.doesHiveHaveSpace(_snowmanxxx))
            .sorted(Comparator.comparingDouble(_snowmanxxx -> _snowmanxxx.getSquaredDistance(_snowman)))
            .collect(Collectors.toList());
      }
   }

   class GrowCropsGoal extends BeeEntity.NotAngryGoal {
      private GrowCropsGoal() {
      }

      @Override
      public boolean canBeeStart() {
         if (BeeEntity.this.getCropsGrownSincePollination() >= 10) {
            return false;
         } else {
            return BeeEntity.this.random.nextFloat() < 0.3F ? false : BeeEntity.this.hasNectar() && BeeEntity.this.isHiveValid();
         }
      }

      @Override
      public boolean canBeeContinue() {
         return this.canBeeStart();
      }

      @Override
      public void tick() {
         if (BeeEntity.this.random.nextInt(30) == 0) {
            for (int _snowman = 1; _snowman <= 2; _snowman++) {
               BlockPos _snowmanx = BeeEntity.this.getBlockPos().down(_snowman);
               BlockState _snowmanxx = BeeEntity.this.world.getBlockState(_snowmanx);
               Block _snowmanxxx = _snowmanxx.getBlock();
               boolean _snowmanxxxx = false;
               IntProperty _snowmanxxxxx = null;
               if (_snowmanxxx.isIn(BlockTags.BEE_GROWABLES)) {
                  if (_snowmanxxx instanceof CropBlock) {
                     CropBlock _snowmanxxxxxx = (CropBlock)_snowmanxxx;
                     if (!_snowmanxxxxxx.isMature(_snowmanxx)) {
                        _snowmanxxxx = true;
                        _snowmanxxxxx = _snowmanxxxxxx.getAgeProperty();
                     }
                  } else if (_snowmanxxx instanceof StemBlock) {
                     int _snowmanxxxxxx = _snowmanxx.get(StemBlock.AGE);
                     if (_snowmanxxxxxx < 7) {
                        _snowmanxxxx = true;
                        _snowmanxxxxx = StemBlock.AGE;
                     }
                  } else if (_snowmanxxx == Blocks.SWEET_BERRY_BUSH) {
                     int _snowmanxxxxxx = _snowmanxx.get(SweetBerryBushBlock.AGE);
                     if (_snowmanxxxxxx < 3) {
                        _snowmanxxxx = true;
                        _snowmanxxxxx = SweetBerryBushBlock.AGE;
                     }
                  }

                  if (_snowmanxxxx) {
                     BeeEntity.this.world.syncWorldEvent(2005, _snowmanx, 0);
                     BeeEntity.this.world.setBlockState(_snowmanx, _snowmanxx.with(_snowmanxxxxx, Integer.valueOf(_snowmanxx.get(_snowmanxxxxx) + 1)));
                     BeeEntity.this.addCropCounter();
                  }
               }
            }
         }
      }
   }

   public class MoveToFlowerGoal extends BeeEntity.NotAngryGoal {
      private int ticks = BeeEntity.this.world.random.nextInt(10);

      MoveToFlowerGoal() {
         this.setControls(EnumSet.of(Goal.Control.MOVE));
      }

      @Override
      public boolean canBeeStart() {
         return BeeEntity.this.flowerPos != null
            && !BeeEntity.this.hasPositionTarget()
            && this.shouldMoveToFlower()
            && BeeEntity.this.isFlowers(BeeEntity.this.flowerPos)
            && !BeeEntity.this.isWithinDistance(BeeEntity.this.flowerPos, 2);
      }

      @Override
      public boolean canBeeContinue() {
         return this.canBeeStart();
      }

      @Override
      public void start() {
         this.ticks = 0;
         super.start();
      }

      @Override
      public void stop() {
         this.ticks = 0;
         BeeEntity.this.navigation.stop();
         BeeEntity.this.navigation.resetRangeMultiplier();
      }

      @Override
      public void tick() {
         if (BeeEntity.this.flowerPos != null) {
            this.ticks++;
            if (this.ticks > 600) {
               BeeEntity.this.flowerPos = null;
            } else if (!BeeEntity.this.navigation.isFollowingPath()) {
               if (BeeEntity.this.isTooFar(BeeEntity.this.flowerPos)) {
                  BeeEntity.this.flowerPos = null;
               } else {
                  BeeEntity.this.startMovingTo(BeeEntity.this.flowerPos);
               }
            }
         }
      }

      private boolean shouldMoveToFlower() {
         return BeeEntity.this.ticksSincePollination > 2400;
      }
   }

   public class MoveToHiveGoal extends BeeEntity.NotAngryGoal {
      private int ticks = BeeEntity.this.world.random.nextInt(10);
      private List<BlockPos> possibleHives = Lists.newArrayList();
      @Nullable
      private Path path = null;
      private int ticksUntilLost;

      MoveToHiveGoal() {
         this.setControls(EnumSet.of(Goal.Control.MOVE));
      }

      @Override
      public boolean canBeeStart() {
         return BeeEntity.this.hivePos != null
            && !BeeEntity.this.hasPositionTarget()
            && BeeEntity.this.canEnterHive()
            && !this.isCloseEnough(BeeEntity.this.hivePos)
            && BeeEntity.this.world.getBlockState(BeeEntity.this.hivePos).isIn(BlockTags.BEEHIVES);
      }

      @Override
      public boolean canBeeContinue() {
         return this.canBeeStart();
      }

      @Override
      public void start() {
         this.ticks = 0;
         this.ticksUntilLost = 0;
         super.start();
      }

      @Override
      public void stop() {
         this.ticks = 0;
         this.ticksUntilLost = 0;
         BeeEntity.this.navigation.stop();
         BeeEntity.this.navigation.resetRangeMultiplier();
      }

      @Override
      public void tick() {
         if (BeeEntity.this.hivePos != null) {
            this.ticks++;
            if (this.ticks > 600) {
               this.makeChosenHivePossibleHive();
            } else if (!BeeEntity.this.navigation.isFollowingPath()) {
               if (!BeeEntity.this.isWithinDistance(BeeEntity.this.hivePos, 16)) {
                  if (BeeEntity.this.isTooFar(BeeEntity.this.hivePos)) {
                     this.setLost();
                  } else {
                     BeeEntity.this.startMovingTo(BeeEntity.this.hivePos);
                  }
               } else {
                  boolean _snowman = this.startMovingToFar(BeeEntity.this.hivePos);
                  if (!_snowman) {
                     this.makeChosenHivePossibleHive();
                  } else if (this.path != null && BeeEntity.this.navigation.getCurrentPath().equalsPath(this.path)) {
                     this.ticksUntilLost++;
                     if (this.ticksUntilLost > 60) {
                        this.setLost();
                        this.ticksUntilLost = 0;
                     }
                  } else {
                     this.path = BeeEntity.this.navigation.getCurrentPath();
                  }
               }
            }
         }
      }

      private boolean startMovingToFar(BlockPos pos) {
         BeeEntity.this.navigation.setRangeMultiplier(10.0F);
         BeeEntity.this.navigation.startMovingTo((double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), 1.0);
         return BeeEntity.this.navigation.getCurrentPath() != null && BeeEntity.this.navigation.getCurrentPath().reachesTarget();
      }

      private boolean isPossibleHive(BlockPos pos) {
         return this.possibleHives.contains(pos);
      }

      private void addPossibleHive(BlockPos pos) {
         this.possibleHives.add(pos);

         while (this.possibleHives.size() > 3) {
            this.possibleHives.remove(0);
         }
      }

      private void clearPossibleHives() {
         this.possibleHives.clear();
      }

      private void makeChosenHivePossibleHive() {
         if (BeeEntity.this.hivePos != null) {
            this.addPossibleHive(BeeEntity.this.hivePos);
         }

         this.setLost();
      }

      private void setLost() {
         BeeEntity.this.hivePos = null;
         BeeEntity.this.ticksLeftToFindHive = 200;
      }

      private boolean isCloseEnough(BlockPos pos) {
         if (BeeEntity.this.isWithinDistance(pos, 2)) {
            return true;
         } else {
            Path _snowman = BeeEntity.this.navigation.getCurrentPath();
            return _snowman != null && _snowman.getTarget().equals(pos) && _snowman.reachesTarget() && _snowman.isFinished();
         }
      }
   }

   abstract class NotAngryGoal extends Goal {
      private NotAngryGoal() {
      }

      public abstract boolean canBeeStart();

      public abstract boolean canBeeContinue();

      @Override
      public boolean canStart() {
         return this.canBeeStart() && !BeeEntity.this.hasAngerTime();
      }

      @Override
      public boolean shouldContinue() {
         return this.canBeeContinue() && !BeeEntity.this.hasAngerTime();
      }
   }

   class PollinateGoal extends BeeEntity.NotAngryGoal {
      private final Predicate<BlockState> flowerPredicate = _snowman -> {
         if (_snowman.isIn(BlockTags.TALL_FLOWERS)) {
            return _snowman.isOf(Blocks.SUNFLOWER) ? _snowman.get(TallPlantBlock.HALF) == DoubleBlockHalf.UPPER : true;
         } else {
            return _snowman.isIn(BlockTags.SMALL_FLOWERS);
         }
      };
      private int pollinationTicks = 0;
      private int lastPollinationTick = 0;
      private boolean running;
      private Vec3d nextTarget;
      private int ticks = 0;

      PollinateGoal() {
         this.setControls(EnumSet.of(Goal.Control.MOVE));
      }

      @Override
      public boolean canBeeStart() {
         if (BeeEntity.this.ticksUntilCanPollinate > 0) {
            return false;
         } else if (BeeEntity.this.hasNectar()) {
            return false;
         } else if (BeeEntity.this.world.isRaining()) {
            return false;
         } else if (BeeEntity.this.random.nextFloat() < 0.7F) {
            return false;
         } else {
            Optional<BlockPos> _snowman = this.getFlower();
            if (_snowman.isPresent()) {
               BeeEntity.this.flowerPos = _snowman.get();
               BeeEntity.this.navigation
                  .startMovingTo(
                     (double)BeeEntity.this.flowerPos.getX() + 0.5,
                     (double)BeeEntity.this.flowerPos.getY() + 0.5,
                     (double)BeeEntity.this.flowerPos.getZ() + 0.5,
                     1.2F
                  );
               return true;
            } else {
               return false;
            }
         }
      }

      @Override
      public boolean canBeeContinue() {
         if (!this.running) {
            return false;
         } else if (!BeeEntity.this.hasFlower()) {
            return false;
         } else if (BeeEntity.this.world.isRaining()) {
            return false;
         } else if (this.completedPollination()) {
            return BeeEntity.this.random.nextFloat() < 0.2F;
         } else if (BeeEntity.this.age % 20 == 0 && !BeeEntity.this.isFlowers(BeeEntity.this.flowerPos)) {
            BeeEntity.this.flowerPos = null;
            return false;
         } else {
            return true;
         }
      }

      private boolean completedPollination() {
         return this.pollinationTicks > 400;
      }

      private boolean isRunning() {
         return this.running;
      }

      private void cancel() {
         this.running = false;
      }

      @Override
      public void start() {
         this.pollinationTicks = 0;
         this.ticks = 0;
         this.lastPollinationTick = 0;
         this.running = true;
         BeeEntity.this.resetPollinationTicks();
      }

      @Override
      public void stop() {
         if (this.completedPollination()) {
            BeeEntity.this.setHasNectar(true);
         }

         this.running = false;
         BeeEntity.this.navigation.stop();
         BeeEntity.this.ticksUntilCanPollinate = 200;
      }

      @Override
      public void tick() {
         this.ticks++;
         if (this.ticks > 600) {
            BeeEntity.this.flowerPos = null;
         } else {
            Vec3d _snowman = Vec3d.ofBottomCenter(BeeEntity.this.flowerPos).add(0.0, 0.6F, 0.0);
            if (_snowman.distanceTo(BeeEntity.this.getPos()) > 1.0) {
               this.nextTarget = _snowman;
               this.moveToNextTarget();
            } else {
               if (this.nextTarget == null) {
                  this.nextTarget = _snowman;
               }

               boolean _snowmanx = BeeEntity.this.getPos().distanceTo(this.nextTarget) <= 0.1;
               boolean _snowmanxx = true;
               if (!_snowmanx && this.ticks > 600) {
                  BeeEntity.this.flowerPos = null;
               } else {
                  if (_snowmanx) {
                     boolean _snowmanxxx = BeeEntity.this.random.nextInt(25) == 0;
                     if (_snowmanxxx) {
                        this.nextTarget = new Vec3d(_snowman.getX() + (double)this.getRandomOffset(), _snowman.getY(), _snowman.getZ() + (double)this.getRandomOffset());
                        BeeEntity.this.navigation.stop();
                     } else {
                        _snowmanxx = false;
                     }

                     BeeEntity.this.getLookControl().lookAt(_snowman.getX(), _snowman.getY(), _snowman.getZ());
                  }

                  if (_snowmanxx) {
                     this.moveToNextTarget();
                  }

                  this.pollinationTicks++;
                  if (BeeEntity.this.random.nextFloat() < 0.05F && this.pollinationTicks > this.lastPollinationTick + 60) {
                     this.lastPollinationTick = this.pollinationTicks;
                     BeeEntity.this.playSound(SoundEvents.ENTITY_BEE_POLLINATE, 1.0F, 1.0F);
                  }
               }
            }
         }
      }

      private void moveToNextTarget() {
         BeeEntity.this.getMoveControl().moveTo(this.nextTarget.getX(), this.nextTarget.getY(), this.nextTarget.getZ(), 0.35F);
      }

      private float getRandomOffset() {
         return (BeeEntity.this.random.nextFloat() * 2.0F - 1.0F) * 0.33333334F;
      }

      private Optional<BlockPos> getFlower() {
         return this.findFlower(this.flowerPredicate, 5.0);
      }

      private Optional<BlockPos> findFlower(Predicate<BlockState> predicate, double searchDistance) {
         BlockPos _snowman = BeeEntity.this.getBlockPos();
         BlockPos.Mutable _snowmanx = new BlockPos.Mutable();

         for (int _snowmanxx = 0; (double)_snowmanxx <= searchDistance; _snowmanxx = _snowmanxx > 0 ? -_snowmanxx : 1 - _snowmanxx) {
            for (int _snowmanxxx = 0; (double)_snowmanxxx < searchDistance; _snowmanxxx++) {
               for (int _snowmanxxxx = 0; _snowmanxxxx <= _snowmanxxx; _snowmanxxxx = _snowmanxxxx > 0 ? -_snowmanxxxx : 1 - _snowmanxxxx) {
                  for (int _snowmanxxxxx = _snowmanxxxx < _snowmanxxx && _snowmanxxxx > -_snowmanxxx ? _snowmanxxx : 0; _snowmanxxxxx <= _snowmanxxx; _snowmanxxxxx = _snowmanxxxxx > 0 ? -_snowmanxxxxx : 1 - _snowmanxxxxx) {
                     _snowmanx.set(_snowman, _snowmanxxxx, _snowmanxx - 1, _snowmanxxxxx);
                     if (_snowman.isWithinDistance(_snowmanx, searchDistance) && predicate.test(BeeEntity.this.world.getBlockState(_snowmanx))) {
                        return Optional.of(_snowmanx);
                     }
                  }
               }
            }
         }

         return Optional.empty();
      }
   }

   class StingGoal extends MeleeAttackGoal {
      StingGoal(PathAwareEntity mob, double speed, boolean pauseWhenMobIdle) {
         super(mob, speed, pauseWhenMobIdle);
      }

      @Override
      public boolean canStart() {
         return super.canStart() && BeeEntity.this.hasAngerTime() && !BeeEntity.this.hasStung();
      }

      @Override
      public boolean shouldContinue() {
         return super.shouldContinue() && BeeEntity.this.hasAngerTime() && !BeeEntity.this.hasStung();
      }
   }
}
