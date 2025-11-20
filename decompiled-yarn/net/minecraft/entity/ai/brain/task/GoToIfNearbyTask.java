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

   public GoToIfNearbyTask(MemoryModuleType<GlobalPos> target, float _snowman, int _snowman) {
      super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.REGISTERED, target, MemoryModuleState.VALUE_PRESENT));
      this.target = target;
      this.field_25752 = _snowman;
      this.maxDistance = _snowman;
   }

   protected boolean shouldRun(ServerWorld _snowman, PathAwareEntity _snowman) {
      Optional<GlobalPos> _snowmanxx = _snowman.getBrain().getOptionalMemory(this.target);
      return _snowmanxx.isPresent() && _snowman.getRegistryKey() == _snowmanxx.get().getDimension() && _snowmanxx.get().getPos().isWithinDistance(_snowman.getPos(), (double)this.maxDistance);
   }

   protected void run(ServerWorld _snowman, PathAwareEntity _snowman, long _snowman) {
      if (_snowman > this.nextUpdateTime) {
         Optional<Vec3d> _snowmanxxx = Optional.ofNullable(TargetFinder.findGroundTarget(_snowman, 8, 6));
         _snowman.getBrain().remember(MemoryModuleType.WALK_TARGET, _snowmanxxx.map(_snowmanxxxx -> new WalkTarget(_snowmanxxxx, this.field_25752, 1)));
         this.nextUpdateTime = _snowman + 180L;
      }
   }
}
