package net.minecraft.entity.passive;

import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.TargetFinder;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.control.DolphinLookControl;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.BreatheAirGoal;
import net.minecraft.entity.ai.goal.ChaseBoatGoal;
import net.minecraft.entity.ai.goal.DolphinJumpGoal;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.MoveIntoWaterGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.SwimAroundGoal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.ai.pathing.SwimNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.GuardianEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.feature.StructureFeature;

public class DolphinEntity extends WaterCreatureEntity {
   private static final TrackedData<BlockPos> TREASURE_POS = DataTracker.registerData(DolphinEntity.class, TrackedDataHandlerRegistry.BLOCK_POS);
   private static final TrackedData<Boolean> HAS_FISH = DataTracker.registerData(DolphinEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
   private static final TrackedData<Integer> MOISTNESS = DataTracker.registerData(DolphinEntity.class, TrackedDataHandlerRegistry.INTEGER);
   private static final TargetPredicate CLOSE_PLAYER_PREDICATE = new TargetPredicate()
      .setBaseMaxDistance(10.0)
      .includeTeammates()
      .includeInvulnerable()
      .includeHidden();
   public static final Predicate<ItemEntity> CAN_TAKE = _snowman -> !_snowman.cannotPickup() && _snowman.isAlive() && _snowman.isTouchingWater();

   public DolphinEntity(EntityType<? extends DolphinEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
      this.moveControl = new DolphinEntity.DolphinMoveControl(this);
      this.lookControl = new DolphinLookControl(this, 10);
      this.setCanPickUpLoot(true);
   }

   @Nullable
   @Override
   public EntityData initialize(
      ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable CompoundTag entityTag
   ) {
      this.setAir(this.getMaxAir());
      this.pitch = 0.0F;
      return super.initialize(world, difficulty, spawnReason, entityData, entityTag);
   }

   @Override
   public boolean canBreatheInWater() {
      return false;
   }

   @Override
   protected void tickWaterBreathingAir(int air) {
   }

   public void setTreasurePos(BlockPos treasurePos) {
      this.dataTracker.set(TREASURE_POS, treasurePos);
   }

   public BlockPos getTreasurePos() {
      return this.dataTracker.get(TREASURE_POS);
   }

   public boolean hasFish() {
      return this.dataTracker.get(HAS_FISH);
   }

   public void setHasFish(boolean hasFish) {
      this.dataTracker.set(HAS_FISH, hasFish);
   }

   public int getMoistness() {
      return this.dataTracker.get(MOISTNESS);
   }

   public void setMoistness(int moistness) {
      this.dataTracker.set(MOISTNESS, moistness);
   }

   @Override
   protected void initDataTracker() {
      super.initDataTracker();
      this.dataTracker.startTracking(TREASURE_POS, BlockPos.ORIGIN);
      this.dataTracker.startTracking(HAS_FISH, false);
      this.dataTracker.startTracking(MOISTNESS, 2400);
   }

   @Override
   public void writeCustomDataToTag(CompoundTag tag) {
      super.writeCustomDataToTag(tag);
      tag.putInt("TreasurePosX", this.getTreasurePos().getX());
      tag.putInt("TreasurePosY", this.getTreasurePos().getY());
      tag.putInt("TreasurePosZ", this.getTreasurePos().getZ());
      tag.putBoolean("GotFish", this.hasFish());
      tag.putInt("Moistness", this.getMoistness());
   }

   @Override
   public void readCustomDataFromTag(CompoundTag tag) {
      int _snowman = tag.getInt("TreasurePosX");
      int _snowmanx = tag.getInt("TreasurePosY");
      int _snowmanxx = tag.getInt("TreasurePosZ");
      this.setTreasurePos(new BlockPos(_snowman, _snowmanx, _snowmanxx));
      super.readCustomDataFromTag(tag);
      this.setHasFish(tag.getBoolean("GotFish"));
      this.setMoistness(tag.getInt("Moistness"));
   }

   @Override
   protected void initGoals() {
      this.goalSelector.add(0, new BreatheAirGoal(this));
      this.goalSelector.add(0, new MoveIntoWaterGoal(this));
      this.goalSelector.add(1, new DolphinEntity.LeadToNearbyTreasureGoal(this));
      this.goalSelector.add(2, new DolphinEntity.SwimWithPlayerGoal(this, 4.0));
      this.goalSelector.add(4, new SwimAroundGoal(this, 1.0, 10));
      this.goalSelector.add(4, new LookAroundGoal(this));
      this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
      this.goalSelector.add(5, new DolphinJumpGoal(this, 10));
      this.goalSelector.add(6, new MeleeAttackGoal(this, 1.2F, true));
      this.goalSelector.add(8, new DolphinEntity.PlayWithItemsGoal());
      this.goalSelector.add(8, new ChaseBoatGoal(this));
      this.goalSelector.add(9, new FleeEntityGoal<>(this, GuardianEntity.class, 8.0F, 1.0, 1.0));
      this.targetSelector.add(1, new RevengeGoal(this, GuardianEntity.class).setGroupRevenge());
   }

   public static DefaultAttributeContainer.Builder createDolphinAttributes() {
      return MobEntity.createMobAttributes()
         .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0)
         .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 1.2F)
         .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0);
   }

   @Override
   protected EntityNavigation createNavigation(World world) {
      return new SwimNavigation(this, world);
   }

   @Override
   public boolean tryAttack(Entity target) {
      boolean _snowman = target.damage(DamageSource.mob(this), (float)((int)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE)));
      if (_snowman) {
         this.dealDamage(this, target);
         this.playSound(SoundEvents.ENTITY_DOLPHIN_ATTACK, 1.0F, 1.0F);
      }

      return _snowman;
   }

   @Override
   public int getMaxAir() {
      return 4800;
   }

   @Override
   protected int getNextAirOnLand(int air) {
      return this.getMaxAir();
   }

   @Override
   protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
      return 0.3F;
   }

   @Override
   public int getLookPitchSpeed() {
      return 1;
   }

   @Override
   public int getBodyYawSpeed() {
      return 1;
   }

   @Override
   protected boolean canStartRiding(Entity entity) {
      return true;
   }

   @Override
   public boolean canEquip(ItemStack stack) {
      EquipmentSlot _snowman = MobEntity.getPreferredEquipmentSlot(stack);
      return !this.getEquippedStack(_snowman).isEmpty() ? false : _snowman == EquipmentSlot.MAINHAND && super.canEquip(stack);
   }

   @Override
   protected void loot(ItemEntity item) {
      if (this.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty()) {
         ItemStack _snowman = item.getStack();
         if (this.canPickupItem(_snowman)) {
            this.method_29499(item);
            this.equipStack(EquipmentSlot.MAINHAND, _snowman);
            this.handDropChances[EquipmentSlot.MAINHAND.getEntitySlotId()] = 2.0F;
            this.sendPickup(item, _snowman.getCount());
            item.remove();
         }
      }
   }

   @Override
   public void tick() {
      super.tick();
      if (this.isAiDisabled()) {
         this.setAir(this.getMaxAir());
      } else {
         if (this.isWet()) {
            this.setMoistness(2400);
         } else {
            this.setMoistness(this.getMoistness() - 1);
            if (this.getMoistness() <= 0) {
               this.damage(DamageSource.DRYOUT, 1.0F);
            }

            if (this.onGround) {
               this.setVelocity(
                  this.getVelocity()
                     .add((double)((this.random.nextFloat() * 2.0F - 1.0F) * 0.2F), 0.5, (double)((this.random.nextFloat() * 2.0F - 1.0F) * 0.2F))
               );
               this.yaw = this.random.nextFloat() * 360.0F;
               this.onGround = false;
               this.velocityDirty = true;
            }
         }

         if (this.world.isClient && this.isTouchingWater() && this.getVelocity().lengthSquared() > 0.03) {
            Vec3d _snowman = this.getRotationVec(0.0F);
            float _snowmanx = MathHelper.cos(this.yaw * (float) (Math.PI / 180.0)) * 0.3F;
            float _snowmanxx = MathHelper.sin(this.yaw * (float) (Math.PI / 180.0)) * 0.3F;
            float _snowmanxxx = 1.2F - this.random.nextFloat() * 0.7F;

            for (int _snowmanxxxx = 0; _snowmanxxxx < 2; _snowmanxxxx++) {
               this.world
                  .addParticle(
                     ParticleTypes.DOLPHIN,
                     this.getX() - _snowman.x * (double)_snowmanxxx + (double)_snowmanx,
                     this.getY() - _snowman.y,
                     this.getZ() - _snowman.z * (double)_snowmanxxx + (double)_snowmanxx,
                     0.0,
                     0.0,
                     0.0
                  );
               this.world
                  .addParticle(
                     ParticleTypes.DOLPHIN,
                     this.getX() - _snowman.x * (double)_snowmanxxx - (double)_snowmanx,
                     this.getY() - _snowman.y,
                     this.getZ() - _snowman.z * (double)_snowmanxxx - (double)_snowmanxx,
                     0.0,
                     0.0,
                     0.0
                  );
            }
         }
      }
   }

   @Override
   public void handleStatus(byte status) {
      if (status == 38) {
         this.spawnParticlesAround(ParticleTypes.HAPPY_VILLAGER);
      } else {
         super.handleStatus(status);
      }
   }

   private void spawnParticlesAround(ParticleEffect parameters) {
      for (int _snowman = 0; _snowman < 7; _snowman++) {
         double _snowmanx = this.random.nextGaussian() * 0.01;
         double _snowmanxx = this.random.nextGaussian() * 0.01;
         double _snowmanxxx = this.random.nextGaussian() * 0.01;
         this.world.addParticle(parameters, this.getParticleX(1.0), this.getRandomBodyY() + 0.2, this.getParticleZ(1.0), _snowmanx, _snowmanxx, _snowmanxxx);
      }
   }

   @Override
   protected ActionResult interactMob(PlayerEntity player, Hand hand) {
      ItemStack _snowman = player.getStackInHand(hand);
      if (!_snowman.isEmpty() && _snowman.getItem().isIn(ItemTags.FISHES)) {
         if (!this.world.isClient) {
            this.playSound(SoundEvents.ENTITY_DOLPHIN_EAT, 1.0F, 1.0F);
         }

         this.setHasFish(true);
         if (!player.abilities.creativeMode) {
            _snowman.decrement(1);
         }

         return ActionResult.success(this.world.isClient);
      } else {
         return super.interactMob(player, hand);
      }
   }

   public static boolean canSpawn(EntityType<DolphinEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
      if (pos.getY() > 45 && pos.getY() < world.getSeaLevel()) {
         Optional<RegistryKey<Biome>> _snowman = world.method_31081(pos);
         return (!Objects.equals(_snowman, Optional.of(BiomeKeys.OCEAN)) || !Objects.equals(_snowman, Optional.of(BiomeKeys.DEEP_OCEAN)))
            && world.getFluidState(pos).isIn(FluidTags.WATER);
      } else {
         return false;
      }
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource source) {
      return SoundEvents.ENTITY_DOLPHIN_HURT;
   }

   @Nullable
   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.ENTITY_DOLPHIN_DEATH;
   }

   @Nullable
   @Override
   protected SoundEvent getAmbientSound() {
      return this.isTouchingWater() ? SoundEvents.ENTITY_DOLPHIN_AMBIENT_WATER : SoundEvents.ENTITY_DOLPHIN_AMBIENT;
   }

   @Override
   protected SoundEvent getSplashSound() {
      return SoundEvents.ENTITY_DOLPHIN_SPLASH;
   }

   @Override
   protected SoundEvent getSwimSound() {
      return SoundEvents.ENTITY_DOLPHIN_SWIM;
   }

   protected boolean isNearTarget() {
      BlockPos _snowman = this.getNavigation().getTargetPos();
      return _snowman != null ? _snowman.isWithinDistance(this.getPos(), 12.0) : false;
   }

   @Override
   public void travel(Vec3d movementInput) {
      if (this.canMoveVoluntarily() && this.isTouchingWater()) {
         this.updateVelocity(this.getMovementSpeed(), movementInput);
         this.move(MovementType.SELF, this.getVelocity());
         this.setVelocity(this.getVelocity().multiply(0.9));
         if (this.getTarget() == null) {
            this.setVelocity(this.getVelocity().add(0.0, -0.005, 0.0));
         }
      } else {
         super.travel(movementInput);
      }
   }

   @Override
   public boolean canBeLeashedBy(PlayerEntity player) {
      return true;
   }

   static class DolphinMoveControl extends MoveControl {
      private final DolphinEntity dolphin;

      public DolphinMoveControl(DolphinEntity dolphin) {
         super(dolphin);
         this.dolphin = dolphin;
      }

      @Override
      public void tick() {
         if (this.dolphin.isTouchingWater()) {
            this.dolphin.setVelocity(this.dolphin.getVelocity().add(0.0, 0.005, 0.0));
         }

         if (this.state == MoveControl.State.MOVE_TO && !this.dolphin.getNavigation().isIdle()) {
            double _snowman = this.targetX - this.dolphin.getX();
            double _snowmanx = this.targetY - this.dolphin.getY();
            double _snowmanxx = this.targetZ - this.dolphin.getZ();
            double _snowmanxxx = _snowman * _snowman + _snowmanx * _snowmanx + _snowmanxx * _snowmanxx;
            if (_snowmanxxx < 2.5000003E-7F) {
               this.entity.setForwardSpeed(0.0F);
            } else {
               float _snowmanxxxx = (float)(MathHelper.atan2(_snowmanxx, _snowman) * 180.0F / (float)Math.PI) - 90.0F;
               this.dolphin.yaw = this.changeAngle(this.dolphin.yaw, _snowmanxxxx, 10.0F);
               this.dolphin.bodyYaw = this.dolphin.yaw;
               this.dolphin.headYaw = this.dolphin.yaw;
               float _snowmanxxxxx = (float)(this.speed * this.dolphin.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED));
               if (this.dolphin.isTouchingWater()) {
                  this.dolphin.setMovementSpeed(_snowmanxxxxx * 0.02F);
                  float _snowmanxxxxxx = -((float)(MathHelper.atan2(_snowmanx, (double)MathHelper.sqrt(_snowman * _snowman + _snowmanxx * _snowmanxx)) * 180.0F / (float)Math.PI));
                  _snowmanxxxxxx = MathHelper.clamp(MathHelper.wrapDegrees(_snowmanxxxxxx), -85.0F, 85.0F);
                  this.dolphin.pitch = this.changeAngle(this.dolphin.pitch, _snowmanxxxxxx, 5.0F);
                  float _snowmanxxxxxxx = MathHelper.cos(this.dolphin.pitch * (float) (Math.PI / 180.0));
                  float _snowmanxxxxxxxx = MathHelper.sin(this.dolphin.pitch * (float) (Math.PI / 180.0));
                  this.dolphin.forwardSpeed = _snowmanxxxxxxx * _snowmanxxxxx;
                  this.dolphin.upwardSpeed = -_snowmanxxxxxxxx * _snowmanxxxxx;
               } else {
                  this.dolphin.setMovementSpeed(_snowmanxxxxx * 0.1F);
               }
            }
         } else {
            this.dolphin.setMovementSpeed(0.0F);
            this.dolphin.setSidewaysSpeed(0.0F);
            this.dolphin.setUpwardSpeed(0.0F);
            this.dolphin.setForwardSpeed(0.0F);
         }
      }
   }

   static class LeadToNearbyTreasureGoal extends Goal {
      private final DolphinEntity dolphin;
      private boolean noPathToStructure;

      LeadToNearbyTreasureGoal(DolphinEntity dolphin) {
         this.dolphin = dolphin;
         this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
      }

      @Override
      public boolean canStop() {
         return false;
      }

      @Override
      public boolean canStart() {
         return this.dolphin.hasFish() && this.dolphin.getAir() >= 100;
      }

      @Override
      public boolean shouldContinue() {
         BlockPos _snowman = this.dolphin.getTreasurePos();
         return !new BlockPos((double)_snowman.getX(), this.dolphin.getY(), (double)_snowman.getZ()).isWithinDistance(this.dolphin.getPos(), 4.0)
            && !this.noPathToStructure
            && this.dolphin.getAir() >= 100;
      }

      @Override
      public void start() {
         if (this.dolphin.world instanceof ServerWorld) {
            ServerWorld _snowman = (ServerWorld)this.dolphin.world;
            this.noPathToStructure = false;
            this.dolphin.getNavigation().stop();
            BlockPos _snowmanx = this.dolphin.getBlockPos();
            StructureFeature<?> _snowmanxx = (double)_snowman.random.nextFloat() >= 0.5 ? StructureFeature.OCEAN_RUIN : StructureFeature.SHIPWRECK;
            BlockPos _snowmanxxx = _snowman.locateStructure(_snowmanxx, _snowmanx, 50, false);
            if (_snowmanxxx == null) {
               StructureFeature<?> _snowmanxxxx = _snowmanxx.equals(StructureFeature.OCEAN_RUIN) ? StructureFeature.SHIPWRECK : StructureFeature.OCEAN_RUIN;
               BlockPos _snowmanxxxxx = _snowman.locateStructure(_snowmanxxxx, _snowmanx, 50, false);
               if (_snowmanxxxxx == null) {
                  this.noPathToStructure = true;
                  return;
               }

               this.dolphin.setTreasurePos(_snowmanxxxxx);
            } else {
               this.dolphin.setTreasurePos(_snowmanxxx);
            }

            _snowman.sendEntityStatus(this.dolphin, (byte)38);
         }
      }

      @Override
      public void stop() {
         BlockPos _snowman = this.dolphin.getTreasurePos();
         if (new BlockPos((double)_snowman.getX(), this.dolphin.getY(), (double)_snowman.getZ()).isWithinDistance(this.dolphin.getPos(), 4.0) || this.noPathToStructure) {
            this.dolphin.setHasFish(false);
         }
      }

      @Override
      public void tick() {
         World _snowman = this.dolphin.world;
         if (this.dolphin.isNearTarget() || this.dolphin.getNavigation().isIdle()) {
            Vec3d _snowmanx = Vec3d.ofCenter(this.dolphin.getTreasurePos());
            Vec3d _snowmanxx = TargetFinder.findTargetTowards(this.dolphin, 16, 1, _snowmanx, (float) (Math.PI / 8));
            if (_snowmanxx == null) {
               _snowmanxx = TargetFinder.findTargetTowards(this.dolphin, 8, 4, _snowmanx);
            }

            if (_snowmanxx != null) {
               BlockPos _snowmanxxx = new BlockPos(_snowmanxx);
               if (!_snowman.getFluidState(_snowmanxxx).isIn(FluidTags.WATER) || !_snowman.getBlockState(_snowmanxxx).canPathfindThrough(_snowman, _snowmanxxx, NavigationType.WATER)) {
                  _snowmanxx = TargetFinder.findTargetTowards(this.dolphin, 8, 5, _snowmanx);
               }
            }

            if (_snowmanxx == null) {
               this.noPathToStructure = true;
               return;
            }

            this.dolphin.getLookControl().lookAt(_snowmanxx.x, _snowmanxx.y, _snowmanxx.z, (float)(this.dolphin.getBodyYawSpeed() + 20), (float)this.dolphin.getLookPitchSpeed());
            this.dolphin.getNavigation().startMovingTo(_snowmanxx.x, _snowmanxx.y, _snowmanxx.z, 1.3);
            if (_snowman.random.nextInt(80) == 0) {
               _snowman.sendEntityStatus(this.dolphin, (byte)38);
            }
         }
      }
   }

   class PlayWithItemsGoal extends Goal {
      private int field_6758;

      private PlayWithItemsGoal() {
      }

      @Override
      public boolean canStart() {
         if (this.field_6758 > DolphinEntity.this.age) {
            return false;
         } else {
            List<ItemEntity> _snowman = DolphinEntity.this.world
               .getEntitiesByClass(ItemEntity.class, DolphinEntity.this.getBoundingBox().expand(8.0, 8.0, 8.0), DolphinEntity.CAN_TAKE);
            return !_snowman.isEmpty() || !DolphinEntity.this.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty();
         }
      }

      @Override
      public void start() {
         List<ItemEntity> _snowman = DolphinEntity.this.world
            .getEntitiesByClass(ItemEntity.class, DolphinEntity.this.getBoundingBox().expand(8.0, 8.0, 8.0), DolphinEntity.CAN_TAKE);
         if (!_snowman.isEmpty()) {
            DolphinEntity.this.getNavigation().startMovingTo(_snowman.get(0), 1.2F);
            DolphinEntity.this.playSound(SoundEvents.ENTITY_DOLPHIN_PLAY, 1.0F, 1.0F);
         }

         this.field_6758 = 0;
      }

      @Override
      public void stop() {
         ItemStack _snowman = DolphinEntity.this.getEquippedStack(EquipmentSlot.MAINHAND);
         if (!_snowman.isEmpty()) {
            this.spitOutItem(_snowman);
            DolphinEntity.this.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
            this.field_6758 = DolphinEntity.this.age + DolphinEntity.this.random.nextInt(100);
         }
      }

      @Override
      public void tick() {
         List<ItemEntity> _snowman = DolphinEntity.this.world
            .getEntitiesByClass(ItemEntity.class, DolphinEntity.this.getBoundingBox().expand(8.0, 8.0, 8.0), DolphinEntity.CAN_TAKE);
         ItemStack _snowmanx = DolphinEntity.this.getEquippedStack(EquipmentSlot.MAINHAND);
         if (!_snowmanx.isEmpty()) {
            this.spitOutItem(_snowmanx);
            DolphinEntity.this.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
         } else if (!_snowman.isEmpty()) {
            DolphinEntity.this.getNavigation().startMovingTo(_snowman.get(0), 1.2F);
         }
      }

      private void spitOutItem(ItemStack stack) {
         if (!stack.isEmpty()) {
            double _snowman = DolphinEntity.this.getEyeY() - 0.3F;
            ItemEntity _snowmanx = new ItemEntity(DolphinEntity.this.world, DolphinEntity.this.getX(), _snowman, DolphinEntity.this.getZ(), stack);
            _snowmanx.setPickupDelay(40);
            _snowmanx.setThrower(DolphinEntity.this.getUuid());
            float _snowmanxx = 0.3F;
            float _snowmanxxx = DolphinEntity.this.random.nextFloat() * (float) (Math.PI * 2);
            float _snowmanxxxx = 0.02F * DolphinEntity.this.random.nextFloat();
            _snowmanx.setVelocity(
               (double)(
                  0.3F
                        * -MathHelper.sin(DolphinEntity.this.yaw * (float) (Math.PI / 180.0))
                        * MathHelper.cos(DolphinEntity.this.pitch * (float) (Math.PI / 180.0))
                     + MathHelper.cos(_snowmanxxx) * _snowmanxxxx
               ),
               (double)(0.3F * MathHelper.sin(DolphinEntity.this.pitch * (float) (Math.PI / 180.0)) * 1.5F),
               (double)(
                  0.3F
                        * MathHelper.cos(DolphinEntity.this.yaw * (float) (Math.PI / 180.0))
                        * MathHelper.cos(DolphinEntity.this.pitch * (float) (Math.PI / 180.0))
                     + MathHelper.sin(_snowmanxxx) * _snowmanxxxx
               )
            );
            DolphinEntity.this.world.spawnEntity(_snowmanx);
         }
      }
   }

   static class SwimWithPlayerGoal extends Goal {
      private final DolphinEntity dolphin;
      private final double speed;
      private PlayerEntity closestPlayer;

      SwimWithPlayerGoal(DolphinEntity dolphin, double speed) {
         this.dolphin = dolphin;
         this.speed = speed;
         this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
      }

      @Override
      public boolean canStart() {
         this.closestPlayer = this.dolphin.world.getClosestPlayer(DolphinEntity.CLOSE_PLAYER_PREDICATE, this.dolphin);
         return this.closestPlayer == null ? false : this.closestPlayer.isSwimming() && this.dolphin.getTarget() != this.closestPlayer;
      }

      @Override
      public boolean shouldContinue() {
         return this.closestPlayer != null && this.closestPlayer.isSwimming() && this.dolphin.squaredDistanceTo(this.closestPlayer) < 256.0;
      }

      @Override
      public void start() {
         this.closestPlayer.addStatusEffect(new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE, 100));
      }

      @Override
      public void stop() {
         this.closestPlayer = null;
         this.dolphin.getNavigation().stop();
      }

      @Override
      public void tick() {
         this.dolphin.getLookControl().lookAt(this.closestPlayer, (float)(this.dolphin.getBodyYawSpeed() + 20), (float)this.dolphin.getLookPitchSpeed());
         if (this.dolphin.squaredDistanceTo(this.closestPlayer) < 6.25) {
            this.dolphin.getNavigation().stop();
         } else {
            this.dolphin.getNavigation().startMovingTo(this.closestPlayer, this.speed);
         }

         if (this.closestPlayer.isSwimming() && this.closestPlayer.world.random.nextInt(6) == 0) {
            this.closestPlayer.addStatusEffect(new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE, 100));
         }
      }
   }
}
