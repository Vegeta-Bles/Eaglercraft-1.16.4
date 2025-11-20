package net.minecraft.entity.mob;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.Durations;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.UniversalAngerGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.ProjectileDamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.IntRange;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class EndermanEntity extends HostileEntity implements Angerable {
   private static final UUID ATTACKING_SPEED_BOOST_ID = UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0");
   private static final EntityAttributeModifier ATTACKING_SPEED_BOOST = new EntityAttributeModifier(
      ATTACKING_SPEED_BOOST_ID, "Attacking speed boost", 0.15F, EntityAttributeModifier.Operation.ADDITION
   );
   private static final TrackedData<Optional<BlockState>> CARRIED_BLOCK = DataTracker.registerData(
      EndermanEntity.class, TrackedDataHandlerRegistry.OPTIONAL_BLOCK_STATE
   );
   private static final TrackedData<Boolean> ANGRY = DataTracker.registerData(EndermanEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
   private static final TrackedData<Boolean> PROVOKED = DataTracker.registerData(EndermanEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
   private static final Predicate<LivingEntity> PLAYER_ENDERMITE_PREDICATE = _snowman -> _snowman instanceof EndermiteEntity && ((EndermiteEntity)_snowman).isPlayerSpawned();
   private int lastAngrySoundAge = Integer.MIN_VALUE;
   private int ageWhenTargetSet;
   private static final IntRange ANGER_TIME_RANGE = Durations.betweenSeconds(20, 39);
   private int angerTime;
   private UUID targetUuid;

   public EndermanEntity(EntityType<? extends EndermanEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
      this.stepHeight = 1.0F;
      this.setPathfindingPenalty(PathNodeType.WATER, -1.0F);
   }

   @Override
   protected void initGoals() {
      this.goalSelector.add(0, new SwimGoal(this));
      this.goalSelector.add(1, new EndermanEntity.ChasePlayerGoal(this));
      this.goalSelector.add(2, new MeleeAttackGoal(this, 1.0, false));
      this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.0, 0.0F));
      this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
      this.goalSelector.add(8, new LookAroundGoal(this));
      this.goalSelector.add(10, new EndermanEntity.PlaceBlockGoal(this));
      this.goalSelector.add(11, new EndermanEntity.PickUpBlockGoal(this));
      this.targetSelector.add(1, new EndermanEntity.TeleportTowardsPlayerGoal(this, this::shouldAngerAt));
      this.targetSelector.add(2, new RevengeGoal(this));
      this.targetSelector.add(3, new FollowTargetGoal<>(this, EndermiteEntity.class, 10, true, false, PLAYER_ENDERMITE_PREDICATE));
      this.targetSelector.add(4, new UniversalAngerGoal<>(this, false));
   }

   public static DefaultAttributeContainer.Builder createEndermanAttributes() {
      return HostileEntity.createHostileAttributes()
         .add(EntityAttributes.GENERIC_MAX_HEALTH, 40.0)
         .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3F)
         .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 7.0)
         .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 64.0);
   }

   @Override
   public void setTarget(@Nullable LivingEntity target) {
      super.setTarget(target);
      EntityAttributeInstance _snowman = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
      if (target == null) {
         this.ageWhenTargetSet = 0;
         this.dataTracker.set(ANGRY, false);
         this.dataTracker.set(PROVOKED, false);
         _snowman.removeModifier(ATTACKING_SPEED_BOOST);
      } else {
         this.ageWhenTargetSet = this.age;
         this.dataTracker.set(ANGRY, true);
         if (!_snowman.hasModifier(ATTACKING_SPEED_BOOST)) {
            _snowman.addTemporaryModifier(ATTACKING_SPEED_BOOST);
         }
      }
   }

   @Override
   protected void initDataTracker() {
      super.initDataTracker();
      this.dataTracker.startTracking(CARRIED_BLOCK, Optional.empty());
      this.dataTracker.startTracking(ANGRY, false);
      this.dataTracker.startTracking(PROVOKED, false);
   }

   @Override
   public void chooseRandomAngerTime() {
      this.setAngerTime(ANGER_TIME_RANGE.choose(this.random));
   }

   @Override
   public void setAngerTime(int ticks) {
      this.angerTime = ticks;
   }

   @Override
   public int getAngerTime() {
      return this.angerTime;
   }

   @Override
   public void setAngryAt(@Nullable UUID uuid) {
      this.targetUuid = uuid;
   }

   @Override
   public UUID getAngryAt() {
      return this.targetUuid;
   }

   public void playAngrySound() {
      if (this.age >= this.lastAngrySoundAge + 400) {
         this.lastAngrySoundAge = this.age;
         if (!this.isSilent()) {
            this.world.playSound(this.getX(), this.getEyeY(), this.getZ(), SoundEvents.ENTITY_ENDERMAN_STARE, this.getSoundCategory(), 2.5F, 1.0F, false);
         }
      }
   }

   @Override
   public void onTrackedDataSet(TrackedData<?> data) {
      if (ANGRY.equals(data) && this.isProvoked() && this.world.isClient) {
         this.playAngrySound();
      }

      super.onTrackedDataSet(data);
   }

   @Override
   public void writeCustomDataToTag(CompoundTag tag) {
      super.writeCustomDataToTag(tag);
      BlockState _snowman = this.getCarriedBlock();
      if (_snowman != null) {
         tag.put("carriedBlockState", NbtHelper.fromBlockState(_snowman));
      }

      this.angerToTag(tag);
   }

   @Override
   public void readCustomDataFromTag(CompoundTag tag) {
      super.readCustomDataFromTag(tag);
      BlockState _snowman = null;
      if (tag.contains("carriedBlockState", 10)) {
         _snowman = NbtHelper.toBlockState(tag.getCompound("carriedBlockState"));
         if (_snowman.isAir()) {
            _snowman = null;
         }
      }

      this.setCarriedBlock(_snowman);
      this.angerFromTag((ServerWorld)this.world, tag);
   }

   private boolean isPlayerStaring(PlayerEntity player) {
      ItemStack _snowman = player.inventory.armor.get(3);
      if (_snowman.getItem() == Blocks.CARVED_PUMPKIN.asItem()) {
         return false;
      } else {
         Vec3d _snowmanx = player.getRotationVec(1.0F).normalize();
         Vec3d _snowmanxx = new Vec3d(this.getX() - player.getX(), this.getEyeY() - player.getEyeY(), this.getZ() - player.getZ());
         double _snowmanxxx = _snowmanxx.length();
         _snowmanxx = _snowmanxx.normalize();
         double _snowmanxxxx = _snowmanx.dotProduct(_snowmanxx);
         return _snowmanxxxx > 1.0 - 0.025 / _snowmanxxx ? player.canSee(this) : false;
      }
   }

   @Override
   protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
      return 2.55F;
   }

   @Override
   public void tickMovement() {
      if (this.world.isClient) {
         for (int _snowman = 0; _snowman < 2; _snowman++) {
            this.world
               .addParticle(
                  ParticleTypes.PORTAL,
                  this.getParticleX(0.5),
                  this.getRandomBodyY() - 0.25,
                  this.getParticleZ(0.5),
                  (this.random.nextDouble() - 0.5) * 2.0,
                  -this.random.nextDouble(),
                  (this.random.nextDouble() - 0.5) * 2.0
               );
         }
      }

      this.jumping = false;
      if (!this.world.isClient) {
         this.tickAngerLogic((ServerWorld)this.world, true);
      }

      super.tickMovement();
   }

   @Override
   public boolean hurtByWater() {
      return true;
   }

   @Override
   protected void mobTick() {
      if (this.world.isDay() && this.age >= this.ageWhenTargetSet + 600) {
         float _snowman = this.getBrightnessAtEyes();
         if (_snowman > 0.5F && this.world.isSkyVisible(this.getBlockPos()) && this.random.nextFloat() * 30.0F < (_snowman - 0.4F) * 2.0F) {
            this.setTarget(null);
            this.teleportRandomly();
         }
      }

      super.mobTick();
   }

   protected boolean teleportRandomly() {
      if (!this.world.isClient() && this.isAlive()) {
         double _snowman = this.getX() + (this.random.nextDouble() - 0.5) * 64.0;
         double _snowmanx = this.getY() + (double)(this.random.nextInt(64) - 32);
         double _snowmanxx = this.getZ() + (this.random.nextDouble() - 0.5) * 64.0;
         return this.teleportTo(_snowman, _snowmanx, _snowmanxx);
      } else {
         return false;
      }
   }

   private boolean teleportTo(Entity entity) {
      Vec3d _snowman = new Vec3d(this.getX() - entity.getX(), this.getBodyY(0.5) - entity.getEyeY(), this.getZ() - entity.getZ());
      _snowman = _snowman.normalize();
      double _snowmanx = 16.0;
      double _snowmanxx = this.getX() + (this.random.nextDouble() - 0.5) * 8.0 - _snowman.x * 16.0;
      double _snowmanxxx = this.getY() + (double)(this.random.nextInt(16) - 8) - _snowman.y * 16.0;
      double _snowmanxxxx = this.getZ() + (this.random.nextDouble() - 0.5) * 8.0 - _snowman.z * 16.0;
      return this.teleportTo(_snowmanxx, _snowmanxxx, _snowmanxxxx);
   }

   private boolean teleportTo(double x, double y, double z) {
      BlockPos.Mutable _snowman = new BlockPos.Mutable(x, y, z);

      while (_snowman.getY() > 0 && !this.world.getBlockState(_snowman).getMaterial().blocksMovement()) {
         _snowman.move(Direction.DOWN);
      }

      BlockState _snowmanx = this.world.getBlockState(_snowman);
      boolean _snowmanxx = _snowmanx.getMaterial().blocksMovement();
      boolean _snowmanxxx = _snowmanx.getFluidState().isIn(FluidTags.WATER);
      if (_snowmanxx && !_snowmanxxx) {
         boolean _snowmanxxxx = this.teleport(x, y, z, true);
         if (_snowmanxxxx && !this.isSilent()) {
            this.world.playSound(null, this.prevX, this.prevY, this.prevZ, SoundEvents.ENTITY_ENDERMAN_TELEPORT, this.getSoundCategory(), 1.0F, 1.0F);
            this.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F);
         }

         return _snowmanxxxx;
      } else {
         return false;
      }
   }

   @Override
   protected SoundEvent getAmbientSound() {
      return this.isAngry() ? SoundEvents.ENTITY_ENDERMAN_SCREAM : SoundEvents.ENTITY_ENDERMAN_AMBIENT;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource source) {
      return SoundEvents.ENTITY_ENDERMAN_HURT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.ENTITY_ENDERMAN_DEATH;
   }

   @Override
   protected void dropEquipment(DamageSource source, int lootingMultiplier, boolean allowDrops) {
      super.dropEquipment(source, lootingMultiplier, allowDrops);
      BlockState _snowman = this.getCarriedBlock();
      if (_snowman != null) {
         this.dropItem(_snowman.getBlock());
      }
   }

   public void setCarriedBlock(@Nullable BlockState state) {
      this.dataTracker.set(CARRIED_BLOCK, Optional.ofNullable(state));
   }

   @Nullable
   public BlockState getCarriedBlock() {
      return this.dataTracker.get(CARRIED_BLOCK).orElse(null);
   }

   @Override
   public boolean damage(DamageSource source, float amount) {
      if (this.isInvulnerableTo(source)) {
         return false;
      } else if (source instanceof ProjectileDamageSource) {
         for (int _snowman = 0; _snowman < 64; _snowman++) {
            if (this.teleportRandomly()) {
               return true;
            }
         }

         return false;
      } else {
         boolean _snowmanx = super.damage(source, amount);
         if (!this.world.isClient() && !(source.getAttacker() instanceof LivingEntity) && this.random.nextInt(10) != 0) {
            this.teleportRandomly();
         }

         return _snowmanx;
      }
   }

   public boolean isAngry() {
      return this.dataTracker.get(ANGRY);
   }

   public boolean isProvoked() {
      return this.dataTracker.get(PROVOKED);
   }

   public void setProvoked() {
      this.dataTracker.set(PROVOKED, true);
   }

   @Override
   public boolean cannotDespawn() {
      return super.cannotDespawn() || this.getCarriedBlock() != null;
   }

   static class ChasePlayerGoal extends Goal {
      private final EndermanEntity enderman;
      private LivingEntity target;

      public ChasePlayerGoal(EndermanEntity enderman) {
         this.enderman = enderman;
         this.setControls(EnumSet.of(Goal.Control.JUMP, Goal.Control.MOVE));
      }

      @Override
      public boolean canStart() {
         this.target = this.enderman.getTarget();
         if (!(this.target instanceof PlayerEntity)) {
            return false;
         } else {
            double _snowman = this.target.squaredDistanceTo(this.enderman);
            return _snowman > 256.0 ? false : this.enderman.isPlayerStaring((PlayerEntity)this.target);
         }
      }

      @Override
      public void start() {
         this.enderman.getNavigation().stop();
      }

      @Override
      public void tick() {
         this.enderman.getLookControl().lookAt(this.target.getX(), this.target.getEyeY(), this.target.getZ());
      }
   }

   static class PickUpBlockGoal extends Goal {
      private final EndermanEntity enderman;

      public PickUpBlockGoal(EndermanEntity enderman) {
         this.enderman = enderman;
      }

      @Override
      public boolean canStart() {
         if (this.enderman.getCarriedBlock() != null) {
            return false;
         } else {
            return !this.enderman.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING) ? false : this.enderman.getRandom().nextInt(20) == 0;
         }
      }

      @Override
      public void tick() {
         Random _snowman = this.enderman.getRandom();
         World _snowmanx = this.enderman.world;
         int _snowmanxx = MathHelper.floor(this.enderman.getX() - 2.0 + _snowman.nextDouble() * 4.0);
         int _snowmanxxx = MathHelper.floor(this.enderman.getY() + _snowman.nextDouble() * 3.0);
         int _snowmanxxxx = MathHelper.floor(this.enderman.getZ() - 2.0 + _snowman.nextDouble() * 4.0);
         BlockPos _snowmanxxxxx = new BlockPos(_snowmanxx, _snowmanxxx, _snowmanxxxx);
         BlockState _snowmanxxxxxx = _snowmanx.getBlockState(_snowmanxxxxx);
         Block _snowmanxxxxxxx = _snowmanxxxxxx.getBlock();
         Vec3d _snowmanxxxxxxxx = new Vec3d(
            (double)MathHelper.floor(this.enderman.getX()) + 0.5, (double)_snowmanxxx + 0.5, (double)MathHelper.floor(this.enderman.getZ()) + 0.5
         );
         Vec3d _snowmanxxxxxxxxx = new Vec3d((double)_snowmanxx + 0.5, (double)_snowmanxxx + 0.5, (double)_snowmanxxxx + 0.5);
         BlockHitResult _snowmanxxxxxxxxxx = _snowmanx.raycast(
            new RaycastContext(_snowmanxxxxxxxx, _snowmanxxxxxxxxx, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, this.enderman)
         );
         boolean _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxx.getBlockPos().equals(_snowmanxxxxx);
         if (_snowmanxxxxxxx.isIn(BlockTags.ENDERMAN_HOLDABLE) && _snowmanxxxxxxxxxxx) {
            _snowmanx.removeBlock(_snowmanxxxxx, false);
            this.enderman.setCarriedBlock(_snowmanxxxxxx.getBlock().getDefaultState());
         }
      }
   }

   static class PlaceBlockGoal extends Goal {
      private final EndermanEntity enderman;

      public PlaceBlockGoal(EndermanEntity enderman) {
         this.enderman = enderman;
      }

      @Override
      public boolean canStart() {
         if (this.enderman.getCarriedBlock() == null) {
            return false;
         } else {
            return !this.enderman.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING) ? false : this.enderman.getRandom().nextInt(2000) == 0;
         }
      }

      @Override
      public void tick() {
         Random _snowman = this.enderman.getRandom();
         World _snowmanx = this.enderman.world;
         int _snowmanxx = MathHelper.floor(this.enderman.getX() - 1.0 + _snowman.nextDouble() * 2.0);
         int _snowmanxxx = MathHelper.floor(this.enderman.getY() + _snowman.nextDouble() * 2.0);
         int _snowmanxxxx = MathHelper.floor(this.enderman.getZ() - 1.0 + _snowman.nextDouble() * 2.0);
         BlockPos _snowmanxxxxx = new BlockPos(_snowmanxx, _snowmanxxx, _snowmanxxxx);
         BlockState _snowmanxxxxxx = _snowmanx.getBlockState(_snowmanxxxxx);
         BlockPos _snowmanxxxxxxx = _snowmanxxxxx.down();
         BlockState _snowmanxxxxxxxx = _snowmanx.getBlockState(_snowmanxxxxxxx);
         BlockState _snowmanxxxxxxxxx = this.enderman.getCarriedBlock();
         if (_snowmanxxxxxxxxx != null) {
            _snowmanxxxxxxxxx = Block.postProcessState(_snowmanxxxxxxxxx, this.enderman.world, _snowmanxxxxx);
            if (this.canPlaceOn(_snowmanx, _snowmanxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxx)) {
               _snowmanx.setBlockState(_snowmanxxxxx, _snowmanxxxxxxxxx, 3);
               this.enderman.setCarriedBlock(null);
            }
         }
      }

      private boolean canPlaceOn(World _snowman, BlockPos posAbove, BlockState carriedState, BlockState stateAbove, BlockState state, BlockPos pos) {
         return stateAbove.isAir()
            && !state.isAir()
            && !state.isOf(Blocks.BEDROCK)
            && state.isFullCube(_snowman, pos)
            && carriedState.canPlaceAt(_snowman, posAbove)
            && _snowman.getOtherEntities(this.enderman, Box.method_29968(Vec3d.of(posAbove))).isEmpty();
      }
   }

   static class TeleportTowardsPlayerGoal extends FollowTargetGoal<PlayerEntity> {
      private final EndermanEntity enderman;
      private PlayerEntity targetPlayer;
      private int lookAtPlayerWarmup;
      private int ticksSinceUnseenTeleport;
      private final TargetPredicate staringPlayerPredicate;
      private final TargetPredicate validTargetPredicate = new TargetPredicate().includeHidden();

      public TeleportTowardsPlayerGoal(EndermanEntity enderman, @Nullable Predicate<LivingEntity> _snowman) {
         super(enderman, PlayerEntity.class, 10, false, false, _snowman);
         this.enderman = enderman;
         this.staringPlayerPredicate = new TargetPredicate()
            .setBaseMaxDistance(this.getFollowRange())
            .setPredicate(playerEntity -> enderman.isPlayerStaring((PlayerEntity)playerEntity));
      }

      @Override
      public boolean canStart() {
         this.targetPlayer = this.enderman.world.getClosestPlayer(this.staringPlayerPredicate, this.enderman);
         return this.targetPlayer != null;
      }

      @Override
      public void start() {
         this.lookAtPlayerWarmup = 5;
         this.ticksSinceUnseenTeleport = 0;
         this.enderman.setProvoked();
      }

      @Override
      public void stop() {
         this.targetPlayer = null;
         super.stop();
      }

      @Override
      public boolean shouldContinue() {
         if (this.targetPlayer != null) {
            if (!this.enderman.isPlayerStaring(this.targetPlayer)) {
               return false;
            } else {
               this.enderman.lookAtEntity(this.targetPlayer, 10.0F, 10.0F);
               return true;
            }
         } else {
            return this.targetEntity != null && this.validTargetPredicate.test(this.enderman, this.targetEntity) ? true : super.shouldContinue();
         }
      }

      @Override
      public void tick() {
         if (this.enderman.getTarget() == null) {
            super.setTargetEntity(null);
         }

         if (this.targetPlayer != null) {
            if (--this.lookAtPlayerWarmup <= 0) {
               this.targetEntity = this.targetPlayer;
               this.targetPlayer = null;
               super.start();
            }
         } else {
            if (this.targetEntity != null && !this.enderman.hasVehicle()) {
               if (this.enderman.isPlayerStaring((PlayerEntity)this.targetEntity)) {
                  if (this.targetEntity.squaredDistanceTo(this.enderman) < 16.0) {
                     this.enderman.teleportRandomly();
                  }

                  this.ticksSinceUnseenTeleport = 0;
               } else if (this.targetEntity.squaredDistanceTo(this.enderman) > 256.0
                  && this.ticksSinceUnseenTeleport++ >= 30
                  && this.enderman.teleportTo(this.targetEntity)) {
                  this.ticksSinceUnseenTeleport = 0;
               }
            }

            super.tick();
         }
      }
   }
}
