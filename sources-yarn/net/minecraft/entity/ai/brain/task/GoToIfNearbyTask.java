package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.entity.ai.TargetFinder;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.dynamic.GlobalPos;
import net.minecraft.util.math.Vec3d;

public class GoToIfNearbyTask extends Task<PathAwareEntity> {
   private final MemoryModuleType<GlobalPos> target;
   private long nextUpdateTime;
   private final int maxDistance;
   private float field_25752;

   public GoToIfNearbyTask(MemoryModuleType<GlobalPos> target, float f, int i) {
      super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.REGISTERED, target, MemoryModuleState.VALUE_PRESENT));
      this.target = target;
      this.field_25752 = f;
      this.maxDistance = i;
   }

   protected boolean shouldRun(ServerWorld arg, PathAwareEntity arg2) {
      Optional<GlobalPos> optional = arg2.getBrain().getOptionalMemory(this.target);
      return optional.isPresent()
         && arg.getRegistryKey() == optional.get().getDimension()
         && optional.get().getPos().isWithinDistance(arg2.getPos(), (double)this.maxDistance);
   }

   protected void run(ServerWorld arg, PathAwareEntity arg2, long l) {
      if (l > this.nextUpdateTime) {
         Optional<Vec3d> optional = Optional.ofNullable(TargetFinder.findGroundTarget(arg2, 8, 6));
         arg2.getBrain().remember(MemoryModuleType.WALK_TARGET, optional.map(argx -> new WalkTarget(argx, this.field_25752, 1)));
         this.nextUpdateTime = l + 180L;
      }
   }
}
