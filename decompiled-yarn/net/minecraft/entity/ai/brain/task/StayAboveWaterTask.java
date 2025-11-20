package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.FluidTags;

public class StayAboveWaterTask extends Task<MobEntity> {
   private final float chance;

   public StayAboveWaterTask(float minWaterHeight) {
      super(ImmutableMap.of());
      this.chance = minWaterHeight;
   }

   protected boolean shouldRun(ServerWorld _snowman, MobEntity _snowman) {
      return _snowman.isTouchingWater() && _snowman.getFluidHeight(FluidTags.WATER) > _snowman.method_29241() || _snowman.isInLava();
   }

   protected boolean shouldKeepRunning(ServerWorld _snowman, MobEntity _snowman, long _snowman) {
      return this.shouldRun(_snowman, _snowman);
   }

   protected void keepRunning(ServerWorld _snowman, MobEntity _snowman, long _snowman) {
      if (_snowman.getRandom().nextFloat() < this.chance) {
         _snowman.getJumpControl().setActive();
      }
   }
}
