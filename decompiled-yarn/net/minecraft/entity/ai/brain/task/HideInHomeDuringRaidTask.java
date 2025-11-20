package net.minecraft.entity.ai.brain.task;

import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.village.raid.Raid;

public class HideInHomeDuringRaidTask extends HideInHomeTask {
   public HideInHomeDuringRaidTask(int maxDistance, float walkSpeed) {
      super(maxDistance, walkSpeed, 1);
   }

   @Override
   protected boolean shouldRun(ServerWorld world, LivingEntity entity) {
      Raid _snowman = world.getRaidAt(entity.getBlockPos());
      return super.shouldRun(world, entity) && _snowman != null && _snowman.isActive() && !_snowman.hasWon() && !_snowman.hasLost();
   }
}
