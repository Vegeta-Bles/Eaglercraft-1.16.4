package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class WanderIndoorsTask extends Task<PathAwareEntity> {
   private final float speed;

   public WanderIndoorsTask(float speed) {
      super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT));
      this.speed = speed;
   }

   protected boolean shouldRun(ServerWorld _snowman, PathAwareEntity _snowman) {
      return !_snowman.isSkyVisible(_snowman.getBlockPos());
   }

   protected void run(ServerWorld _snowman, PathAwareEntity _snowman, long _snowman) {
      BlockPos _snowmanxxx = _snowman.getBlockPos();
      List<BlockPos> _snowmanxxxx = BlockPos.stream(_snowmanxxx.add(-1, -1, -1), _snowmanxxx.add(1, 1, 1)).map(BlockPos::toImmutable).collect(Collectors.toList());
      Collections.shuffle(_snowmanxxxx);
      Optional<BlockPos> _snowmanxxxxx = _snowmanxxxx.stream()
         .filter(_snowmanxxxxxx -> !_snowman.isSkyVisible(_snowmanxxxxxx))
         .filter(_snowmanxxxxxx -> _snowman.isTopSolid(_snowmanxxxxxx, _snowman))
         .filter(_snowmanxxxxxx -> _snowman.isSpaceEmpty(_snowman))
         .findFirst();
      _snowmanxxxxx.ifPresent(_snowmanxxxxxx -> _snowman.getBrain().remember(MemoryModuleType.WALK_TARGET, new WalkTarget(_snowmanxxxxxx, this.speed, 0)));
   }
}
