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

   protected boolean shouldRun(ServerWorld arg, MobEntity arg2) {
      return arg2.isTouchingWater() && arg2.getFluidHeight(FluidTags.WATER) > arg2.method_29241() || arg2.isInLava();
   }

   protected boolean shouldKeepRunning(ServerWorld arg, MobEntity arg2, long l) {
      return this.shouldRun(arg, arg2);
   }

   protected void keepRunning(ServerWorld arg, MobEntity arg2, long l) {
      if (arg2.getRandom().nextFloat() < this.chance) {
         arg2.getJumpControl().setActive();
      }
   }
}
