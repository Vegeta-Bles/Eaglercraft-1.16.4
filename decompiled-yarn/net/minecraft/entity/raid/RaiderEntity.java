package net.minecraft.entity.raid;

import com.google.common.collect.Lists;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.TargetFinder;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.MoveToRaidCenterGoal;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.mob.PatrolEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.village.raid.Raid;
import net.minecraft.village.raid.RaidManager;
import net.minecraft.world.GameRules;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.poi.PointOfInterestStorage;
import net.minecraft.world.poi.PointOfInterestType;

public abstract class RaiderEntity extends PatrolEntity {
   protected static final TrackedData<Boolean> CELEBRATING = DataTracker.registerData(RaiderEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
   private static final Predicate<ItemEntity> OBTAINABLE_OMINOUS_BANNER_PREDICATE = _snowman -> !_snowman.cannotPickup()
         && _snowman.isAlive()
         && ItemStack.areEqual(_snowman.getStack(), Raid.getOminousBanner());
   @Nullable
   protected Raid raid;
   private int wave;
   private boolean ableToJoinRaid;
   private int outOfRaidCounter;

   protected RaiderEntity(EntityType<? extends RaiderEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
   }

   @Override
   protected void initGoals() {
      super.initGoals();
      this.goalSelector.add(1, new RaiderEntity.PickupBannerAsLeaderGoal<>(this));
      this.goalSelector.add(3, new MoveToRaidCenterGoal<>(this));
      this.goalSelector.add(4, new RaiderEntity.AttackHomeGoal(this, 1.05F, 1));
      this.goalSelector.add(5, new RaiderEntity.CelebrateGoal(this));
   }

   @Override
   protected void initDataTracker() {
      super.initDataTracker();
      this.dataTracker.startTracking(CELEBRATING, false);
   }

   public abstract void addBonusForWave(int wave, boolean unused);

   public boolean canJoinRaid() {
      return this.ableToJoinRaid;
   }

   public void setAbleToJoinRaid(boolean ableToJoinRaid) {
      this.ableToJoinRaid = ableToJoinRaid;
   }

   @Override
   public void tickMovement() {
      if (this.world instanceof ServerWorld && this.isAlive()) {
         Raid _snowman = this.getRaid();
         if (this.canJoinRaid()) {
            if (_snowman == null) {
               if (this.world.getTime() % 20L == 0L) {
                  Raid _snowmanx = ((ServerWorld)this.world).getRaidAt(this.getBlockPos());
                  if (_snowmanx != null && RaidManager.isValidRaiderFor(this, _snowmanx)) {
                     _snowmanx.addRaider(_snowmanx.getGroupsSpawned(), this, null, true);
                  }
               }
            } else {
               LivingEntity _snowmanx = this.getTarget();
               if (_snowmanx != null && (_snowmanx.getType() == EntityType.PLAYER || _snowmanx.getType() == EntityType.IRON_GOLEM)) {
                  this.despawnCounter = 0;
               }
            }
         }
      }

      super.tickMovement();
   }

   @Override
   protected void updateDespawnCounter() {
      this.despawnCounter += 2;
   }

   @Override
   public void onDeath(DamageSource source) {
      if (this.world instanceof ServerWorld) {
         Entity _snowman = source.getAttacker();
         Raid _snowmanx = this.getRaid();
         if (_snowmanx != null) {
            if (this.isPatrolLeader()) {
               _snowmanx.removeLeader(this.getWave());
            }

            if (_snowman != null && _snowman.getType() == EntityType.PLAYER) {
               _snowmanx.addHero(_snowman);
            }

            _snowmanx.removeFromWave(this, false);
         }

         if (this.isPatrolLeader() && _snowmanx == null && ((ServerWorld)this.world).getRaidAt(this.getBlockPos()) == null) {
            ItemStack _snowmanxx = this.getEquippedStack(EquipmentSlot.HEAD);
            PlayerEntity _snowmanxxx = null;
            if (_snowman instanceof PlayerEntity) {
               _snowmanxxx = (PlayerEntity)_snowman;
            } else if (_snowman instanceof WolfEntity) {
               WolfEntity _snowmanxxxx = (WolfEntity)_snowman;
               LivingEntity _snowmanxxxxx = _snowmanxxxx.getOwner();
               if (_snowmanxxxx.isTamed() && _snowmanxxxxx instanceof PlayerEntity) {
                  _snowmanxxx = (PlayerEntity)_snowmanxxxxx;
               }
            }

            if (!_snowmanxx.isEmpty() && ItemStack.areEqual(_snowmanxx, Raid.getOminousBanner()) && _snowmanxxx != null) {
               StatusEffectInstance _snowmanxxxx = _snowmanxxx.getStatusEffect(StatusEffects.BAD_OMEN);
               int _snowmanxxxxx = 1;
               if (_snowmanxxxx != null) {
                  _snowmanxxxxx += _snowmanxxxx.getAmplifier();
                  _snowmanxxx.removeStatusEffectInternal(StatusEffects.BAD_OMEN);
               } else {
                  _snowmanxxxxx--;
               }

               _snowmanxxxxx = MathHelper.clamp(_snowmanxxxxx, 0, 4);
               StatusEffectInstance _snowmanxxxxxx = new StatusEffectInstance(StatusEffects.BAD_OMEN, 120000, _snowmanxxxxx, false, false, true);
               if (!this.world.getGameRules().getBoolean(GameRules.DISABLE_RAIDS)) {
                  _snowmanxxx.addStatusEffect(_snowmanxxxxxx);
               }
            }
         }
      }

      super.onDeath(source);
   }

   @Override
   public boolean hasNoRaid() {
      return !this.hasActiveRaid();
   }

   public void setRaid(@Nullable Raid raid) {
      this.raid = raid;
   }

   @Nullable
   public Raid getRaid() {
      return this.raid;
   }

   public boolean hasActiveRaid() {
      return this.getRaid() != null && this.getRaid().isActive();
   }

   public void setWave(int wave) {
      this.wave = wave;
   }

   public int getWave() {
      return this.wave;
   }

   public boolean isCelebrating() {
      return this.dataTracker.get(CELEBRATING);
   }

   public void setCelebrating(boolean celebrating) {
      this.dataTracker.set(CELEBRATING, celebrating);
   }

   @Override
   public void writeCustomDataToTag(CompoundTag tag) {
      super.writeCustomDataToTag(tag);
      tag.putInt("Wave", this.wave);
      tag.putBoolean("CanJoinRaid", this.ableToJoinRaid);
      if (this.raid != null) {
         tag.putInt("RaidId", this.raid.getRaidId());
      }
   }

   @Override
   public void readCustomDataFromTag(CompoundTag tag) {
      super.readCustomDataFromTag(tag);
      this.wave = tag.getInt("Wave");
      this.ableToJoinRaid = tag.getBoolean("CanJoinRaid");
      if (tag.contains("RaidId", 3)) {
         if (this.world instanceof ServerWorld) {
            this.raid = ((ServerWorld)this.world).getRaidManager().getRaid(tag.getInt("RaidId"));
         }

         if (this.raid != null) {
            this.raid.addToWave(this.wave, this, false);
            if (this.isPatrolLeader()) {
               this.raid.setWaveCaptain(this.wave, this);
            }
         }
      }
   }

   @Override
   protected void loot(ItemEntity item) {
      ItemStack _snowman = item.getStack();
      boolean _snowmanx = this.hasActiveRaid() && this.getRaid().getCaptain(this.getWave()) != null;
      if (this.hasActiveRaid() && !_snowmanx && ItemStack.areEqual(_snowman, Raid.getOminousBanner())) {
         EquipmentSlot _snowmanxx = EquipmentSlot.HEAD;
         ItemStack _snowmanxxx = this.getEquippedStack(_snowmanxx);
         double _snowmanxxxx = (double)this.getDropChance(_snowmanxx);
         if (!_snowmanxxx.isEmpty() && (double)Math.max(this.random.nextFloat() - 0.1F, 0.0F) < _snowmanxxxx) {
            this.dropStack(_snowmanxxx);
         }

         this.method_29499(item);
         this.equipStack(_snowmanxx, _snowman);
         this.sendPickup(item, _snowman.getCount());
         item.remove();
         this.getRaid().setWaveCaptain(this.getWave(), this);
         this.setPatrolLeader(true);
      } else {
         super.loot(item);
      }
   }

   @Override
   public boolean canImmediatelyDespawn(double distanceSquared) {
      return this.getRaid() == null ? super.canImmediatelyDespawn(distanceSquared) : false;
   }

   @Override
   public boolean cannotDespawn() {
      return super.cannotDespawn() || this.getRaid() != null;
   }

   public int getOutOfRaidCounter() {
      return this.outOfRaidCounter;
   }

   public void setOutOfRaidCounter(int counter) {
      this.outOfRaidCounter = counter;
   }

   @Override
   public boolean damage(DamageSource source, float amount) {
      if (this.hasActiveRaid()) {
         this.getRaid().updateBar();
      }

      return super.damage(source, amount);
   }

   @Nullable
   @Override
   public EntityData initialize(
      ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable CompoundTag entityTag
   ) {
      this.setAbleToJoinRaid(this.getType() != EntityType.WITCH || spawnReason != SpawnReason.NATURAL);
      return super.initialize(world, difficulty, spawnReason, entityData, entityTag);
   }

   public abstract SoundEvent getCelebratingSound();

   static class AttackHomeGoal extends Goal {
      private final RaiderEntity raider;
      private final double speed;
      private BlockPos home;
      private final List<BlockPos> lastHomes = Lists.newArrayList();
      private final int distance;
      private boolean finished;

      public AttackHomeGoal(RaiderEntity raider, double speed, int distance) {
         this.raider = raider;
         this.speed = speed;
         this.distance = distance;
         this.setControls(EnumSet.of(Goal.Control.MOVE));
      }

      @Override
      public boolean canStart() {
         this.purgeMemory();
         return this.isRaiding() && this.tryFindHome() && this.raider.getTarget() == null;
      }

      private boolean isRaiding() {
         return this.raider.hasActiveRaid() && !this.raider.getRaid().isFinished();
      }

      private boolean tryFindHome() {
         ServerWorld _snowman = (ServerWorld)this.raider.world;
         BlockPos _snowmanx = this.raider.getBlockPos();
         Optional<BlockPos> _snowmanxx = _snowman.getPointOfInterestStorage()
            .getPosition(_snowmanxxx -> _snowmanxxx == PointOfInterestType.HOME, this::canLootHome, PointOfInterestStorage.OccupationStatus.ANY, _snowmanx, 48, this.raider.random);
         if (!_snowmanxx.isPresent()) {
            return false;
         } else {
            this.home = _snowmanxx.get().toImmutable();
            return true;
         }
      }

      @Override
      public boolean shouldContinue() {
         return this.raider.getNavigation().isIdle()
            ? false
            : this.raider.getTarget() == null
               && !this.home.isWithinDistance(this.raider.getPos(), (double)(this.raider.getWidth() + (float)this.distance))
               && !this.finished;
      }

      @Override
      public void stop() {
         if (this.home.isWithinDistance(this.raider.getPos(), (double)this.distance)) {
            this.lastHomes.add(this.home);
         }
      }

      @Override
      public void start() {
         super.start();
         this.raider.setDespawnCounter(0);
         this.raider.getNavigation().startMovingTo((double)this.home.getX(), (double)this.home.getY(), (double)this.home.getZ(), this.speed);
         this.finished = false;
      }

      @Override
      public void tick() {
         if (this.raider.getNavigation().isIdle()) {
            Vec3d _snowman = Vec3d.ofBottomCenter(this.home);
            Vec3d _snowmanx = TargetFinder.findTargetTowards(this.raider, 16, 7, _snowman, (float) (Math.PI / 10));
            if (_snowmanx == null) {
               _snowmanx = TargetFinder.findTargetTowards(this.raider, 8, 7, _snowman);
            }

            if (_snowmanx == null) {
               this.finished = true;
               return;
            }

            this.raider.getNavigation().startMovingTo(_snowmanx.x, _snowmanx.y, _snowmanx.z, this.speed);
         }
      }

      private boolean canLootHome(BlockPos pos) {
         for (BlockPos _snowman : this.lastHomes) {
            if (Objects.equals(pos, _snowman)) {
               return false;
            }
         }

         return true;
      }

      private void purgeMemory() {
         if (this.lastHomes.size() > 2) {
            this.lastHomes.remove(0);
         }
      }
   }

   public class CelebrateGoal extends Goal {
      private final RaiderEntity raider;

      CelebrateGoal(RaiderEntity raider) {
         this.raider = raider;
         this.setControls(EnumSet.of(Goal.Control.MOVE));
      }

      @Override
      public boolean canStart() {
         Raid _snowman = this.raider.getRaid();
         return this.raider.isAlive() && this.raider.getTarget() == null && _snowman != null && _snowman.hasLost();
      }

      @Override
      public void start() {
         this.raider.setCelebrating(true);
         super.start();
      }

      @Override
      public void stop() {
         this.raider.setCelebrating(false);
         super.stop();
      }

      @Override
      public void tick() {
         if (!this.raider.isSilent() && this.raider.random.nextInt(100) == 0) {
            RaiderEntity.this.playSound(RaiderEntity.this.getCelebratingSound(), RaiderEntity.this.getSoundVolume(), RaiderEntity.this.getSoundPitch());
         }

         if (!this.raider.hasVehicle() && this.raider.random.nextInt(50) == 0) {
            this.raider.getJumpControl().setActive();
         }

         super.tick();
      }
   }

   public class PatrolApproachGoal extends Goal {
      private final RaiderEntity raider;
      private final float squaredDistance;
      public final TargetPredicate closeRaiderPredicate = new TargetPredicate()
         .setBaseMaxDistance(8.0)
         .ignoreEntityTargetRules()
         .includeInvulnerable()
         .includeTeammates()
         .includeHidden()
         .ignoreDistanceScalingFactor();

      public PatrolApproachGoal(IllagerEntity illager, float distance) {
         this.raider = illager;
         this.squaredDistance = distance * distance;
         this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
      }

      @Override
      public boolean canStart() {
         LivingEntity _snowman = this.raider.getAttacker();
         return this.raider.getRaid() == null
            && this.raider.isRaidCenterSet()
            && this.raider.getTarget() != null
            && !this.raider.isAttacking()
            && (_snowman == null || _snowman.getType() != EntityType.PLAYER);
      }

      @Override
      public void start() {
         super.start();
         this.raider.getNavigation().stop();

         for (RaiderEntity _snowman : this.raider
            .world
            .getTargets(RaiderEntity.class, this.closeRaiderPredicate, this.raider, this.raider.getBoundingBox().expand(8.0, 8.0, 8.0))) {
            _snowman.setTarget(this.raider.getTarget());
         }
      }

      @Override
      public void stop() {
         super.stop();
         LivingEntity _snowman = this.raider.getTarget();
         if (_snowman != null) {
            for (RaiderEntity _snowmanx : this.raider
               .world
               .getTargets(RaiderEntity.class, this.closeRaiderPredicate, this.raider, this.raider.getBoundingBox().expand(8.0, 8.0, 8.0))) {
               _snowmanx.setTarget(_snowman);
               _snowmanx.setAttacking(true);
            }

            this.raider.setAttacking(true);
         }
      }

      @Override
      public void tick() {
         LivingEntity _snowman = this.raider.getTarget();
         if (_snowman != null) {
            if (this.raider.squaredDistanceTo(_snowman) > (double)this.squaredDistance) {
               this.raider.getLookControl().lookAt(_snowman, 30.0F, 30.0F);
               if (this.raider.random.nextInt(50) == 0) {
                  this.raider.playAmbientSound();
               }
            } else {
               this.raider.setAttacking(true);
            }

            super.tick();
         }
      }
   }

   public class PickupBannerAsLeaderGoal<T extends RaiderEntity> extends Goal {
      private final T actor;

      public PickupBannerAsLeaderGoal(T actor) {
         this.actor = actor;
         this.setControls(EnumSet.of(Goal.Control.MOVE));
      }

      @Override
      public boolean canStart() {
         Raid _snowman = this.actor.getRaid();
         if (this.actor.hasActiveRaid()
            && !this.actor.getRaid().isFinished()
            && this.actor.canLead()
            && !ItemStack.areEqual(this.actor.getEquippedStack(EquipmentSlot.HEAD), Raid.getOminousBanner())) {
            RaiderEntity _snowmanx = _snowman.getCaptain(this.actor.getWave());
            if (_snowmanx == null || !_snowmanx.isAlive()) {
               List<ItemEntity> _snowmanxx = this.actor
                  .world
                  .getEntitiesByClass(ItemEntity.class, this.actor.getBoundingBox().expand(16.0, 8.0, 16.0), RaiderEntity.OBTAINABLE_OMINOUS_BANNER_PREDICATE);
               if (!_snowmanxx.isEmpty()) {
                  return this.actor.getNavigation().startMovingTo(_snowmanxx.get(0), 1.15F);
               }
            }

            return false;
         } else {
            return false;
         }
      }

      @Override
      public void tick() {
         if (this.actor.getNavigation().getTargetPos().isWithinDistance(this.actor.getPos(), 1.414)) {
            List<ItemEntity> _snowman = this.actor
               .world
               .getEntitiesByClass(ItemEntity.class, this.actor.getBoundingBox().expand(4.0, 4.0, 4.0), RaiderEntity.OBTAINABLE_OMINOUS_BANNER_PREDICATE);
            if (!_snowman.isEmpty()) {
               this.actor.loot(_snowman.get(0));
            }
         }
      }
   }
}
