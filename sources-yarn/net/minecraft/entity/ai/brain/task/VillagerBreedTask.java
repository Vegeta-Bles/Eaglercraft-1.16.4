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

   protected boolean shouldRun(ServerWorld arg, VillagerEntity arg2) {
      return this.isReadyToBreed(arg2);
   }

   protected boolean shouldKeepRunning(ServerWorld arg, VillagerEntity arg2, long l) {
      return l <= this.breedEndTime && this.isReadyToBreed(arg2);
   }

   protected void run(ServerWorld arg, VillagerEntity arg2, long l) {
      PassiveEntity lv = arg2.getBrain().getOptionalMemory(MemoryModuleType.BREED_TARGET).get();
      LookTargetUtil.lookAtAndWalkTowardsEachOther(arg2, lv, 0.5F);
      arg.sendEntityStatus(lv, (byte)18);
      arg.sendEntityStatus(arg2, (byte)18);
      int i = 275 + arg2.getRandom().nextInt(50);
      this.breedEndTime = l + (long)i;
   }

   protected void keepRunning(ServerWorld arg, VillagerEntity arg2, long l) {
      VillagerEntity lv = (VillagerEntity)arg2.getBrain().getOptionalMemory(MemoryModuleType.BREED_TARGET).get();
      if (!(arg2.squaredDistanceTo(lv) > 5.0)) {
         LookTargetUtil.lookAtAndWalkTowardsEachOther(arg2, lv, 0.5F);
         if (l >= this.breedEndTime) {
            arg2.eatForBreeding();
            lv.eatForBreeding();
            this.goHome(arg, arg2, lv);
         } else if (arg2.getRandom().nextInt(35) == 0) {
            arg.sendEntityStatus(lv, (byte)12);
            arg.sendEntityStatus(arg2, (byte)12);
         }
      }
   }

   private void goHome(ServerWorld world, VillagerEntity first, VillagerEntity second) {
      Optional<BlockPos> optional = this.getReachableHome(world, first);
      if (!optional.isPresent()) {
         world.sendEntityStatus(second, (byte)13);
         world.sendEntityStatus(first, (byte)13);
      } else {
         Optional<VillagerEntity> optional2 = this.createChild(world, first, second);
         if (optional2.isPresent()) {
            this.setChildHome(world, optional2.get(), optional.get());
         } else {
            world.getPointOfInterestStorage().releaseTicket(optional.get());
            DebugInfoSender.sendPointOfInterest(world, optional.get());
         }
      }
   }

   protected void finishRunning(ServerWorld arg, VillagerEntity arg2, long l) {
      arg2.getBrain().forget(MemoryModuleType.BREED_TARGET);
   }

   private boolean isReadyToBreed(VillagerEntity villager) {
      Brain<VillagerEntity> lv = villager.getBrain();
      Optional<PassiveEntity> optional = lv.getOptionalMemory(MemoryModuleType.BREED_TARGET).filter(arg -> arg.getType() == EntityType.VILLAGER);
      return !optional.isPresent()
         ? false
         : LookTargetUtil.canSee(lv, MemoryModuleType.BREED_TARGET, EntityType.VILLAGER) && villager.isReadyToBreed() && optional.get().isReadyToBreed();
   }

   private Optional<BlockPos> getReachableHome(ServerWorld world, VillagerEntity villager) {
      return world.getPointOfInterestStorage()
         .getPosition(PointOfInterestType.HOME.getCompletionCondition(), arg2 -> this.canReachHome(villager, arg2), villager.getBlockPos(), 48);
   }

   private boolean canReachHome(VillagerEntity villager, BlockPos pos) {
      Path lv = villager.getNavigation().findPathTo(pos, PointOfInterestType.HOME.getSearchDistance());
      return lv != null && lv.reachesTarget();
   }

   private Optional<VillagerEntity> createChild(ServerWorld arg, VillagerEntity arg2, VillagerEntity arg3) {
      VillagerEntity lv = arg2.createChild(arg, arg3);
      if (lv == null) {
         return Optional.empty();
      } else {
         arg2.setBreedingAge(6000);
         arg3.setBreedingAge(6000);
         lv.setBreedingAge(-24000);
         lv.refreshPositionAndAngles(arg2.getX(), arg2.getY(), arg2.getZ(), 0.0F, 0.0F);
         arg.spawnEntityAndPassengers(lv);
         arg.sendEntityStatus(lv, (byte)12);
         return Optional.of(lv);
      }
   }

   private void setChildHome(ServerWorld world, VillagerEntity child, BlockPos pos) {
      GlobalPos lv = GlobalPos.create(world.getRegistryKey(), pos);
      child.getBrain().remember(MemoryModuleType.HOME, lv);
   }
}
