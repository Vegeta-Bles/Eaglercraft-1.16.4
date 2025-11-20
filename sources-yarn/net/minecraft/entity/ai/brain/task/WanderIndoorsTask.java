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

   protected boolean shouldRun(ServerWorld arg, PathAwareEntity arg2) {
      return !arg.isSkyVisible(arg2.getBlockPos());
   }

   protected void run(ServerWorld arg, PathAwareEntity arg2, long l) {
      BlockPos lv = arg2.getBlockPos();
      List<BlockPos> list = BlockPos.stream(lv.add(-1, -1, -1), lv.add(1, 1, 1)).map(BlockPos::toImmutable).collect(Collectors.toList());
      Collections.shuffle(list);
      Optional<BlockPos> optional = list.stream()
         .filter(arg2x -> !arg.isSkyVisible(arg2x))
         .filter(arg3 -> arg.isTopSolid(arg3, arg2))
         .filter(arg3 -> arg.isSpaceEmpty(arg2))
         .findFirst();
      optional.ifPresent(arg2x -> arg2.getBrain().remember(MemoryModuleType.WALK_TARGET, new WalkTarget(arg2x, this.speed, 0)));
   }
}
