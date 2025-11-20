package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.BellBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class RingBellTask extends Task<LivingEntity> {
   public RingBellTask() {
      super(ImmutableMap.of(MemoryModuleType.MEETING_POINT, MemoryModuleState.VALUE_PRESENT));
   }

   @Override
   protected boolean shouldRun(ServerWorld world, LivingEntity entity) {
      return world.random.nextFloat() > 0.95F;
   }

   @Override
   protected void run(ServerWorld world, LivingEntity entity, long time) {
      Brain<?> _snowman = entity.getBrain();
      BlockPos _snowmanx = _snowman.getOptionalMemory(MemoryModuleType.MEETING_POINT).get().getPos();
      if (_snowmanx.isWithinDistance(entity.getBlockPos(), 3.0)) {
         BlockState _snowmanxx = world.getBlockState(_snowmanx);
         if (_snowmanxx.isOf(Blocks.BELL)) {
            BellBlock _snowmanxxx = (BellBlock)_snowmanxx.getBlock();
            _snowmanxxx.ring(world, _snowmanx, null);
         }
      }
   }
}
