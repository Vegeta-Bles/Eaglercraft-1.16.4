package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Random;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class GoToCelebrateTask<E extends MobEntity> extends Task<E> {
   private final int completionRange;
   private final float field_23130;

   public GoToCelebrateTask(int completionRange, float _snowman) {
      super(
         ImmutableMap.of(
            MemoryModuleType.CELEBRATE_LOCATION,
            MemoryModuleState.VALUE_PRESENT,
            MemoryModuleType.ATTACK_TARGET,
            MemoryModuleState.VALUE_ABSENT,
            MemoryModuleType.WALK_TARGET,
            MemoryModuleState.VALUE_ABSENT,
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleState.REGISTERED
         )
      );
      this.completionRange = completionRange;
      this.field_23130 = _snowman;
   }

   protected void run(ServerWorld _snowman, MobEntity _snowman, long _snowman) {
      BlockPos _snowmanxxx = getCelebrateLocation(_snowman);
      boolean _snowmanxxxx = _snowmanxxx.isWithinDistance(_snowman.getBlockPos(), (double)this.completionRange);
      if (!_snowmanxxxx) {
         LookTargetUtil.walkTowards(_snowman, fuzz(_snowman, _snowmanxxx), this.field_23130, this.completionRange);
      }
   }

   private static BlockPos fuzz(MobEntity mob, BlockPos pos) {
      Random _snowman = mob.world.random;
      return pos.add(fuzz(_snowman), 0, fuzz(_snowman));
   }

   private static int fuzz(Random random) {
      return random.nextInt(3) - 1;
   }

   private static BlockPos getCelebrateLocation(MobEntity entity) {
      return entity.getBrain().getOptionalMemory(MemoryModuleType.CELEBRATE_LOCATION).get();
   }
}
