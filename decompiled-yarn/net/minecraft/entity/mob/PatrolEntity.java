package net.minecraft.entity.mob;

import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.village.raid.Raid;
import net.minecraft.world.Heightmap;
import net.minecraft.world.LightType;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public abstract class PatrolEntity extends HostileEntity {
   private BlockPos patrolTarget;
   private boolean patrolLeader;
   private boolean patrolling;

   protected PatrolEntity(EntityType<? extends PatrolEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
   }

   @Override
   protected void initGoals() {
      super.initGoals();
      this.goalSelector.add(4, new PatrolEntity.PatrolGoal<>(this, 0.7, 0.595));
   }

   @Override
   public void writeCustomDataToTag(CompoundTag tag) {
      super.writeCustomDataToTag(tag);
      if (this.patrolTarget != null) {
         tag.put("PatrolTarget", NbtHelper.fromBlockPos(this.patrolTarget));
      }

      tag.putBoolean("PatrolLeader", this.patrolLeader);
      tag.putBoolean("Patrolling", this.patrolling);
   }

   @Override
   public void readCustomDataFromTag(CompoundTag tag) {
      super.readCustomDataFromTag(tag);
      if (tag.contains("PatrolTarget")) {
         this.patrolTarget = NbtHelper.toBlockPos(tag.getCompound("PatrolTarget"));
      }

      this.patrolLeader = tag.getBoolean("PatrolLeader");
      this.patrolling = tag.getBoolean("Patrolling");
   }

   @Override
   public double getHeightOffset() {
      return -0.45;
   }

   public boolean canLead() {
      return true;
   }

   @Nullable
   @Override
   public EntityData initialize(
      ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable CompoundTag entityTag
   ) {
      if (spawnReason != SpawnReason.PATROL
         && spawnReason != SpawnReason.EVENT
         && spawnReason != SpawnReason.STRUCTURE
         && this.random.nextFloat() < 0.06F
         && this.canLead()) {
         this.patrolLeader = true;
      }

      if (this.isPatrolLeader()) {
         this.equipStack(EquipmentSlot.HEAD, Raid.getOminousBanner());
         this.setEquipmentDropChance(EquipmentSlot.HEAD, 2.0F);
      }

      if (spawnReason == SpawnReason.PATROL) {
         this.patrolling = true;
      }

      return super.initialize(world, difficulty, spawnReason, entityData, entityTag);
   }

   public static boolean canSpawn(EntityType<? extends PatrolEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
      return world.getLightLevel(LightType.BLOCK, pos) > 8 ? false : canSpawnIgnoreLightLevel(type, world, spawnReason, pos, random);
   }

   @Override
   public boolean canImmediatelyDespawn(double distanceSquared) {
      return !this.patrolling || distanceSquared > 16384.0;
   }

   public void setPatrolTarget(BlockPos targetPos) {
      this.patrolTarget = targetPos;
      this.patrolling = true;
   }

   public BlockPos getPatrolTarget() {
      return this.patrolTarget;
   }

   public boolean hasPatrolTarget() {
      return this.patrolTarget != null;
   }

   public void setPatrolLeader(boolean patrolLeader) {
      this.patrolLeader = patrolLeader;
      this.patrolling = true;
   }

   public boolean isPatrolLeader() {
      return this.patrolLeader;
   }

   public boolean hasNoRaid() {
      return true;
   }

   public void setRandomPatrolTarget() {
      this.patrolTarget = this.getBlockPos().add(-500 + this.random.nextInt(1000), 0, -500 + this.random.nextInt(1000));
      this.patrolling = true;
   }

   protected boolean isRaidCenterSet() {
      return this.patrolling;
   }

   protected void setPatrolling(boolean patrolling) {
      this.patrolling = patrolling;
   }

   public static class PatrolGoal<T extends PatrolEntity> extends Goal {
      private final T entity;
      private final double leaderSpeed;
      private final double followSpeed;
      private long nextPatrolSearchTime;

      public PatrolGoal(T entity, double leaderSpeed, double followSpeed) {
         this.entity = entity;
         this.leaderSpeed = leaderSpeed;
         this.followSpeed = followSpeed;
         this.nextPatrolSearchTime = -1L;
         this.setControls(EnumSet.of(Goal.Control.MOVE));
      }

      @Override
      public boolean canStart() {
         boolean _snowman = this.entity.world.getTime() < this.nextPatrolSearchTime;
         return this.entity.isRaidCenterSet() && this.entity.getTarget() == null && !this.entity.hasPassengers() && this.entity.hasPatrolTarget() && !_snowman;
      }

      @Override
      public void start() {
      }

      @Override
      public void stop() {
      }

      @Override
      public void tick() {
         boolean _snowman = this.entity.isPatrolLeader();
         EntityNavigation _snowmanx = this.entity.getNavigation();
         if (_snowmanx.isIdle()) {
            List<PatrolEntity> _snowmanxx = this.findPatrolTargets();
            if (this.entity.isRaidCenterSet() && _snowmanxx.isEmpty()) {
               this.entity.setPatrolling(false);
            } else if (_snowman && this.entity.getPatrolTarget().isWithinDistance(this.entity.getPos(), 10.0)) {
               this.entity.setRandomPatrolTarget();
            } else {
               Vec3d _snowmanxxx = Vec3d.ofBottomCenter(this.entity.getPatrolTarget());
               Vec3d _snowmanxxxx = this.entity.getPos();
               Vec3d _snowmanxxxxx = _snowmanxxxx.subtract(_snowmanxxx);
               _snowmanxxx = _snowmanxxxxx.rotateY(90.0F).multiply(0.4).add(_snowmanxxx);
               Vec3d _snowmanxxxxxx = _snowmanxxx.subtract(_snowmanxxxx).normalize().multiply(10.0).add(_snowmanxxxx);
               BlockPos _snowmanxxxxxxx = new BlockPos(_snowmanxxxxxx);
               _snowmanxxxxxxx = this.entity.world.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, _snowmanxxxxxxx);
               if (!_snowmanx.startMovingTo((double)_snowmanxxxxxxx.getX(), (double)_snowmanxxxxxxx.getY(), (double)_snowmanxxxxxxx.getZ(), _snowman ? this.followSpeed : this.leaderSpeed)) {
                  this.wander();
                  this.nextPatrolSearchTime = this.entity.world.getTime() + 200L;
               } else if (_snowman) {
                  for (PatrolEntity _snowmanxxxxxxxx : _snowmanxx) {
                     _snowmanxxxxxxxx.setPatrolTarget(_snowmanxxxxxxx);
                  }
               }
            }
         }
      }

      private List<PatrolEntity> findPatrolTargets() {
         return this.entity
            .world
            .getEntitiesByClass(PatrolEntity.class, this.entity.getBoundingBox().expand(16.0), _snowman -> _snowman.hasNoRaid() && !_snowman.isPartOf(this.entity));
      }

      private boolean wander() {
         Random _snowman = this.entity.getRandom();
         BlockPos _snowmanx = this.entity
            .world
            .getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, this.entity.getBlockPos().add(-8 + _snowman.nextInt(16), 0, -8 + _snowman.nextInt(16)));
         return this.entity.getNavigation().startMovingTo((double)_snowmanx.getX(), (double)_snowmanx.getY(), (double)_snowmanx.getZ(), this.leaderSpeed);
      }
   }
}
