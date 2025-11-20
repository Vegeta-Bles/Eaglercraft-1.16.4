package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.dynamic.GlobalPos;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.poi.PointOfInterestType;

public class VillagerBreedTask extends Task<VillagerEntity> {
   private long breedEndTime;

   public VillagerBreedTask() {
      super(
         ImmutableMap.of(MemoryModuleType.BREED_TARGET, MemoryModuleState.VALUE_PRESENT, MemoryModuleType.VISIBLE_MOBS, MemoryModuleState.VALUE_PRESENT),
         350,
         350
      );
   }

   protected boolean shouldRun(ServerWorld _snowman, VillagerEntity _snowman) {
      return this.isReadyToBreed(_snowman);
   }

   protected boolean shouldKeepRunning(ServerWorld _snowman, VillagerEntity _snowman, long _snowman) {
      return _snowman <= this.breedEndTime && this.isReadyToBreed(_snowman);
   }

   protected void run(ServerWorld _snowman, VillagerEntity _snowman, long _snowman) {
      PassiveEntity _snowmanxxx = _snowman.getBrain().getOptionalMemory(MemoryModuleType.BREED_TARGET).get();
      LookTargetUtil.lookAtAndWalkTowardsEachOther(_snowman, _snowmanxxx, 0.5F);
      _snowman.sendEntityStatus(_snowmanxxx, (byte)18);
      _snowman.sendEntityStatus(_snowman, (byte)18);
      int _snowmanxxxx = 275 + _snowman.getRandom().nextInt(50);
      this.breedEndTime = _snowman + (long)_snowmanxxxx;
   }

   protected void keepRunning(ServerWorld _snowman, VillagerEntity _snowman, long _snowman) {
      VillagerEntity _snowmanxxx = (VillagerEntity)_snowman.getBrain().getOptionalMemory(MemoryModuleType.BREED_TARGET).get();
      if (!(_snowman.squaredDistanceTo(_snowmanxxx) > 5.0)) {
         LookTargetUtil.lookAtAndWalkTowardsEachOther(_snowman, _snowmanxxx, 0.5F);
         if (_snowman >= this.breedEndTime) {
            _snowman.eatForBreeding();
            _snowmanxxx.eatForBreeding();
            this.goHome(_snowman, _snowman, _snowmanxxx);
         } else if (_snowman.getRandom().nextInt(35) == 0) {
            _snowman.sendEntityStatus(_snowmanxxx, (byte)12);
            _snowman.sendEntityStatus(_snowman, (byte)12);
         }
      }
   }

   private void goHome(ServerWorld world, VillagerEntity first, VillagerEntity second) {
      Optional<BlockPos> _snowman = this.getReachableHome(world, first);
      if (!_snowman.isPresent()) {
         world.sendEntityStatus(second, (byte)13);
         world.sendEntityStatus(first, (byte)13);
      } else {
         Optional<VillagerEntity> _snowmanx = this.createChild(world, first, second);
         if (_snowmanx.isPresent()) {
            this.setChildHome(world, _snowmanx.get(), _snowman.get());
         } else {
            world.getPointOfInterestStorage().releaseTicket(_snowman.get());
            DebugInfoSender.sendPointOfInterest(world, _snowman.get());
         }
      }
   }

   protected void finishRunning(ServerWorld _snowman, VillagerEntity _snowman, long _snowman) {
      _snowman.getBrain().forget(MemoryModuleType.BREED_TARGET);
   }

   private boolean isReadyToBreed(VillagerEntity villager) {
      Brain<VillagerEntity> _snowman = villager.getBrain();
      Optional<PassiveEntity> _snowmanx = _snowman.getOptionalMemory(MemoryModuleType.BREED_TARGET).filter(_snowmanxx -> _snowmanxx.getType() == EntityType.VILLAGER);
      return !_snowmanx.isPresent()
         ? false
         : LookTargetUtil.canSee(_snowman, MemoryModuleType.BREED_TARGET, EntityType.VILLAGER) && villager.isReadyToBreed() && _snowmanx.get().isReadyToBreed();
   }

   private Optional<BlockPos> getReachableHome(ServerWorld world, VillagerEntity villager) {
      return world.getPointOfInterestStorage()
         .getPosition(PointOfInterestType.HOME.getCompletionCondition(), _snowmanx -> this.canReachHome(villager, _snowmanx), villager.getBlockPos(), 48);
   }

   private boolean canReachHome(VillagerEntity villager, BlockPos pos) {
      Path _snowman = villager.getNavigation().findPathTo(pos, PointOfInterestType.HOME.getSearchDistance());
      return _snowman != null && _snowman.reachesTarget();
   }

   private Optional<VillagerEntity> createChild(ServerWorld _snowman, VillagerEntity _snowman, VillagerEntity _snowman) {
      VillagerEntity _snowmanxxx = _snowman.createChild(_snowman, _snowman);
      if (_snowmanxxx == null) {
         return Optional.empty();
      } else {
         _snowman.setBreedingAge(6000);
         _snowman.setBreedingAge(6000);
         _snowmanxxx.setBreedingAge(-24000);
         _snowmanxxx.refreshPositionAndAngles(_snowman.getX(), _snowman.getY(), _snowman.getZ(), 0.0F, 0.0F);
         _snowman.spawnEntityAndPassengers(_snowmanxxx);
         _snowman.sendEntityStatus(_snowmanxxx, (byte)12);
         return Optional.of(_snowmanxxx);
      }
   }

   private void setChildHome(ServerWorld world, VillagerEntity child, BlockPos pos) {
      GlobalPos _snowman = GlobalPos.create(world.getRegistryKey(), pos);
      child.getBrain().remember(MemoryModuleType.HOME, _snowman);
   }
}
