package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.entity.ai.TargetFinder;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.dynamic.GlobalPos;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class VillagerWalkTowardsTask extends Task<VillagerEntity> {
   private final MemoryModuleType<GlobalPos> destination;
   private final float speed;
   private final int completionRange;
   private final int maxRange;
   private final int maxRunTime;

   public VillagerWalkTowardsTask(MemoryModuleType<GlobalPos> destination, float speed, int completionRange, int maxRange, int maxRunTime) {
      super(
         ImmutableMap.of(
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryModuleState.REGISTERED,
            MemoryModuleType.WALK_TARGET,
            MemoryModuleState.VALUE_ABSENT,
            destination,
            MemoryModuleState.VALUE_PRESENT
         )
      );
      this.destination = destination;
      this.speed = speed;
      this.completionRange = completionRange;
      this.maxRange = maxRange;
      this.maxRunTime = maxRunTime;
   }

   private void giveUp(VillagerEntity villager, long time) {
      Brain<?> lv = villager.getBrain();
      villager.releaseTicketFor(this.destination);
      lv.forget(this.destination);
      lv.remember(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, time);
   }

   protected void run(ServerWorld arg, VillagerEntity arg2, long l) {
      Brain<?> lv = arg2.getBrain();
      lv.getOptionalMemory(this.destination).ifPresent(arg4 -> {
         if (this.method_30952(arg, arg4) || this.shouldGiveUp(arg, arg2)) {
            this.giveUp(arg2, l);
         } else if (this.exceedsMaxRange(arg2, arg4)) {
            Vec3d lvx = null;
            int i = 0;

            for (int j = 1000; i < 1000 && (lvx == null || this.exceedsMaxRange(arg2, GlobalPos.create(arg.getRegistryKey(), new BlockPos(lvx)))); i++) {
               lvx = TargetFinder.findTargetTowards(arg2, 15, 7, Vec3d.ofBottomCenter(arg4.getPos()));
            }

            if (i == 1000) {
               this.giveUp(arg2, l);
               return;
            }

            lv.remember(MemoryModuleType.WALK_TARGET, new WalkTarget(lvx, this.speed, this.completionRange));
         } else if (!this.reachedDestination(arg, arg2, arg4)) {
            lv.remember(MemoryModuleType.WALK_TARGET, new WalkTarget(arg4.getPos(), this.speed, this.completionRange));
         }
      });
   }

   private boolean shouldGiveUp(ServerWorld world, VillagerEntity villager) {
      Optional<Long> optional = villager.getBrain().getOptionalMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
      return optional.isPresent() ? world.getTime() - optional.get() > (long)this.maxRunTime : false;
   }

   private boolean exceedsMaxRange(VillagerEntity arg, GlobalPos arg2) {
      return arg2.getPos().getManhattanDistance(arg.getBlockPos()) > this.maxRange;
   }

   private boolean method_30952(ServerWorld arg, GlobalPos arg2) {
      return arg2.getDimension() != arg.getRegistryKey();
   }

   private boolean reachedDestination(ServerWorld world, VillagerEntity villager, GlobalPos pos) {
      return pos.getDimension() == world.getRegistryKey() && pos.getPos().getManhattanDistance(villager.getBlockPos()) <= this.completionRange;
   }
}
