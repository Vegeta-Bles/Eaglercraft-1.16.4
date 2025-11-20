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
      Brain<?> _snowman = villager.getBrain();
      villager.releaseTicketFor(this.destination);
      _snowman.forget(this.destination);
      _snowman.remember(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, time);
   }

   protected void run(ServerWorld _snowman, VillagerEntity _snowman, long _snowman) {
      Brain<?> _snowmanxxx = _snowman.getBrain();
      _snowmanxxx.getOptionalMemory(this.destination)
         .ifPresent(
            _snowmanxxxxxx -> {
               if (this.method_30952(_snowman, _snowmanxxxxxx) || this.shouldGiveUp(_snowman, _snowman)) {
                  this.giveUp(_snowman, _snowman);
               } else if (this.exceedsMaxRange(_snowman, _snowmanxxxxxx)) {
                  Vec3d _snowmanxxxxx = null;
                  int _snowmanxxxxxx = 0;

                  for (int _snowmanxxxxxx = 1000;
                     _snowmanxxxxxx < 1000 && (_snowmanxxxxx == null || this.exceedsMaxRange(_snowman, GlobalPos.create(_snowman.getRegistryKey(), new BlockPos(_snowmanxxxxx))));
                     _snowmanxxxxxx++
                  ) {
                     _snowmanxxxxx = TargetFinder.findTargetTowards(_snowman, 15, 7, Vec3d.ofBottomCenter(_snowmanxxxxxx.getPos()));
                  }

                  if (_snowmanxxxxxx == 1000) {
                     this.giveUp(_snowman, _snowman);
                     return;
                  }

                  _snowman.remember(MemoryModuleType.WALK_TARGET, new WalkTarget(_snowmanxxxxx, this.speed, this.completionRange));
               } else if (!this.reachedDestination(_snowman, _snowman, _snowmanxxxxxx)) {
                  _snowman.remember(MemoryModuleType.WALK_TARGET, new WalkTarget(_snowmanxxxxxx.getPos(), this.speed, this.completionRange));
               }
            }
         );
   }

   private boolean shouldGiveUp(ServerWorld world, VillagerEntity villager) {
      Optional<Long> _snowman = villager.getBrain().getOptionalMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
      return _snowman.isPresent() ? world.getTime() - _snowman.get() > (long)this.maxRunTime : false;
   }

   private boolean exceedsMaxRange(VillagerEntity _snowman, GlobalPos _snowman) {
      return _snowman.getPos().getManhattanDistance(_snowman.getBlockPos()) > this.maxRange;
   }

   private boolean method_30952(ServerWorld _snowman, GlobalPos _snowman) {
      return _snowman.getDimension() != _snowman.getRegistryKey();
   }

   private boolean reachedDestination(ServerWorld world, VillagerEntity villager, GlobalPos pos) {
      return pos.getDimension() == world.getRegistryKey() && pos.getPos().getManhattanDistance(villager.getBlockPos()) <= this.completionRange;
   }
}
