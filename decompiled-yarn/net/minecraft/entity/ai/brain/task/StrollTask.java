package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.entity.ai.TargetFinder;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

public class StrollTask extends Task<PathAwareEntity> {
   private final float speed;
   private final int horizontalRadius;
   private final int verticalRadius;

   public StrollTask(float speed) {
      this(speed, 10, 7);
   }

   public StrollTask(float speed, int horizontalRadius, int verticalRadius) {
      super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT));
      this.speed = speed;
      this.horizontalRadius = horizontalRadius;
      this.verticalRadius = verticalRadius;
   }

   protected void run(ServerWorld _snowman, PathAwareEntity _snowman, long _snowman) {
      Optional<Vec3d> _snowmanxxx = Optional.ofNullable(TargetFinder.findGroundTarget(_snowman, this.horizontalRadius, this.verticalRadius));
      _snowman.getBrain().remember(MemoryModuleType.WALK_TARGET, _snowmanxxx.map(_snowmanxxxx -> new WalkTarget(_snowmanxxxx, this.speed, 0)));
   }
}
