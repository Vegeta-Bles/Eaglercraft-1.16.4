package net.minecraft.entity.raid;

import com.google.common.collect.Lists;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
   private static final Predicate<ItemEntity> OBTAINABLE_OMINOUS_BANNER_PREDICATE = arg -> !arg.cannotPickup()
         && arg.isAlive()
         && ItemStack.areEqual(arg.getStack(), Raid.getOminousBanner());
   @Nullable
   protected Raid raid;
   private int wave;
   private boolean ableToJoinRaid;
   private int outOfRaidCounter;

   protected RaiderEntity(EntityType<? extends RaiderEntity> arg, World arg2) {
      super(arg, arg2);
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
         Raid lv = this.getRaid();
         if (this.canJoinRaid()) {
            if (lv == null) {
               if (this.world.getTime() % 20L == 0L) {
                  Raid lv2 = ((ServerWorld)this.world).getRaidAt(this.getBlockPos());
                  if (lv2 != null && RaidManager.isValidRaiderFor(this, lv2)) {
                     lv2.addRaider(lv2.getGroupsSpawned(), this, null, true);
                  }
               }
            } else {
               LivingEntity lv3 = this.getTarget();
               if (lv3 != null && (lv3.getType() == EntityType.PLAYER || lv3.getType() == EntityType.IRON_GOLEM)) {
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
         Entity lv = source.getAttacker();
         Raid lv2 = this.getRaid();
         if (lv2 != null) {
            if (this.isPatrolLeader()) {
               lv2.removeLeader(this.getWave());
            }

            if (lv != null && lv.getType() == EntityType.PLAYER) {
               lv2.addHero(lv);
            }

            lv2.removeFromWave(this, false);
         }

         if (this.isPatrolLeader() && lv2 == null && ((ServerWorld)this.world).getRaidAt(this.getBlockPos()) == null) {
            ItemStack lv3 = this.getEquippedStack(EquipmentSlot.HEAD);
            PlayerEntity lv4 = null;
            if (lv instanceof PlayerEntity) {
               lv4 = (PlayerEntity)lv;
            } else if (lv instanceof WolfEntity) {
               WolfEntity lv6 = (WolfEntity)lv;
               LivingEntity lv7 = lv6.getOwner();
               if (lv6.isTamed() && lv7 instanceof PlayerEntity) {
                  lv4 = (PlayerEntity)lv7;
               }
            }

            if (!lv3.isEmpty() && ItemStack.areEqual(lv3, Raid.getOminousBanner()) && lv4 != null) {
               StatusEffectInstance lv8 = lv4.getStatusEffect(StatusEffects.BAD_OMEN);
               int i = 1;
               if (lv8 != null) {
                  i += lv8.getAmplifier();
                  lv4.removeStatusEffectInternal(StatusEffects.BAD_OMEN);
               } else {
                  i--;
               }

               i = MathHelper.clamp(i, 0, 4);
               StatusEffectInstance lv9 = new StatusEffectInstance(StatusEffects.BAD_OMEN, 120000, i, false, false, true);
               if (!this.world.getGameRules().getBoolean(GameRules.DISABLE_RAIDS)) {
                  lv4.addStatusEffect(lv9);
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

   @Environment(EnvType.CLIENT)
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
      ItemStack lv = item.getStack();
      boolean bl = this.hasActiveRaid() && this.getRaid().getCaptain(this.getWave()) != null;
      if (this.hasActiveRaid() && !bl && ItemStack.areEqual(lv, Raid.getOminousBanner())) {
         EquipmentSlot lv2 = EquipmentSlot.HEAD;
         ItemStack lv3 = this.getEquippedStack(lv2);
         double d = (double)this.getDropChance(lv2);
         if (!lv3.isEmpty() && (double)Math.max(this.random.nextFloat() - 0.1F, 0.0F) < d) {
            this.dropStack(lv3);
         }

         this.method_29499(item);
         this.equipStack(lv2, lv);
         this.sendPickup(item, lv.getCount());
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
         ServerWorld lv = (ServerWorld)this.raider.world;
         BlockPos lv2 = this.raider.getBlockPos();
         Optional<BlockPos> optional = lv.getPointOfInterestStorage()
            .getPosition(arg -> arg == PointOfInterestType.HOME, this::canLootHome, PointOfInterestStorage.OccupationStatus.ANY, lv2, 48, this.raider.random);
         if (!optional.isPresent()) {
            return false;
         } else {
            this.home = optional.get().toImmutable();
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
            Vec3d lv = Vec3d.ofBottomCenter(this.home);
            Vec3d lv2 = TargetFinder.findTargetTowards(this.raider, 16, 7, lv, (float) (Math.PI / 10));
            if (lv2 == null) {
               lv2 = TargetFinder.findTargetTowards(this.raider, 8, 7, lv);
            }

            if (lv2 == null) {
               this.finished = true;
               return;
            }

            this.raider.getNavigation().startMovingTo(lv2.x, lv2.y, lv2.z, this.speed);
         }
      }

      private boolean canLootHome(BlockPos pos) {
         for (BlockPos lv : this.lastHomes) {
            if (Objects.equals(pos, lv)) {
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
         Raid lv = this.raider.getRaid();
         return this.raider.isAlive() && this.raider.getTarget() == null && lv != null && lv.hasLost();
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
         LivingEntity lv = this.raider.getAttacker();
         return this.raider.getRaid() == null
            && this.raider.isRaidCenterSet()
            && this.raider.getTarget() != null
            && !this.raider.isAttacking()
            && (lv == null || lv.getType() != EntityType.PLAYER);
      }

      @Override
      public void start() {
         super.start();
         this.raider.getNavigation().stop();

         for (RaiderEntity lv : this.raider
            .world
            .getTargets(RaiderEntity.class, this.closeRaiderPredicate, this.raider, this.raider.getBoundingBox().expand(8.0, 8.0, 8.0))) {
            lv.setTarget(this.raider.getTarget());
         }
      }

      @Override
      public void stop() {
         super.stop();
         LivingEntity lv = this.raider.getTarget();
         if (lv != null) {
            for (RaiderEntity lv2 : this.raider
               .world
               .getTargets(RaiderEntity.class, this.closeRaiderPredicate, this.raider, this.raider.getBoundingBox().expand(8.0, 8.0, 8.0))) {
               lv2.setTarget(lv);
               lv2.setAttacking(true);
            }

            this.raider.setAttacking(true);
         }
      }

      @Override
      public void tick() {
         LivingEntity lv = this.raider.getTarget();
         if (lv != null) {
            if (this.raider.squaredDistanceTo(lv) > (double)this.squaredDistance) {
               this.raider.getLookControl().lookAt(lv, 30.0F, 30.0F);
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
         Raid lv = this.actor.getRaid();
         if (this.actor.hasActiveRaid()
            && !this.actor.getRaid().isFinished()
            && this.actor.canLead()
            && !ItemStack.areEqual(this.actor.getEquippedStack(EquipmentSlot.HEAD), Raid.getOminousBanner())) {
            RaiderEntity lv2 = lv.getCaptain(this.actor.getWave());
            if (lv2 == null || !lv2.isAlive()) {
               List<ItemEntity> list = this.actor
                  .world
                  .getEntitiesByClass(ItemEntity.class, this.actor.getBoundingBox().expand(16.0, 8.0, 16.0), RaiderEntity.OBTAINABLE_OMINOUS_BANNER_PREDICATE);
               if (!list.isEmpty()) {
                  return this.actor.getNavigation().startMovingTo(list.get(0), 1.15F);
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
            List<ItemEntity> list = this.actor
               .world
               .getEntitiesByClass(ItemEntity.class, this.actor.getBoundingBox().expand(4.0, 4.0, 4.0), RaiderEntity.OBTAINABLE_OMINOUS_BANNER_PREDICATE);
            if (!list.isEmpty()) {
               this.actor.loot(list.get(0));
            }
         }
      }
   }
}
