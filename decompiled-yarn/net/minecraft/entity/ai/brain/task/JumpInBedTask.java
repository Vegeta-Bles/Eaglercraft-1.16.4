package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;

public class JumpInBedTask extends Task<MobEntity> {
   private final float walkSpeed;
   @Nullable
   private BlockPos bedPos;
   private int ticksOutOfBedUntilStopped;
   private int jumpsRemaining;
   private int ticksToNextJump;

   public JumpInBedTask(float walkSpeed) {
      super(ImmutableMap.of(MemoryModuleType.NEAREST_BED, MemoryModuleState.VALUE_PRESENT, MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT));
      this.walkSpeed = walkSpeed;
   }

   protected boolean shouldRun(ServerWorld _snowman, MobEntity _snowman) {
      return _snowman.isBaby() && this.shouldStartJumping(_snowman, _snowman);
   }

   protected void run(ServerWorld _snowman, MobEntity _snowman, long _snowman) {
      super.run(_snowman, _snowman, _snowman);
      this.getNearestBed(_snowman).ifPresent(_snowmanxxx -> {
         this.bedPos = _snowmanxxx;
         this.ticksOutOfBedUntilStopped = 100;
         this.jumpsRemaining = 3 + _snowman.random.nextInt(4);
         this.ticksToNextJump = 0;
         this.setWalkTarget(_snowman, _snowmanxxx);
      });
   }

   protected void finishRunning(ServerWorld _snowman, MobEntity _snowman, long _snowman) {
      super.finishRunning(_snowman, _snowman, _snowman);
      this.bedPos = null;
      this.ticksOutOfBedUntilStopped = 0;
      this.jumpsRemaining = 0;
      this.ticksToNextJump = 0;
   }

   protected boolean shouldKeepRunning(ServerWorld _snowman, MobEntity _snowman, long _snowman) {
      return _snowman.isBaby() && this.bedPos != null && this.isBedAt(_snowman, this.bedPos) && !this.isBedGoneTooLong(_snowman, _snowman) && !this.isDoneJumping(_snowman, _snowman);
   }

   @Override
   protected boolean isTimeLimitExceeded(long time) {
      return false;
   }

   protected void keepRunning(ServerWorld _snowman, MobEntity _snowman, long _snowman) {
      if (!this.isAboveBed(_snowman, _snowman)) {
         this.ticksOutOfBedUntilStopped--;
      } else if (this.ticksToNextJump > 0) {
         this.ticksToNextJump--;
      } else {
         if (this.isOnBed(_snowman, _snowman)) {
            _snowman.getJumpControl().setActive();
            this.jumpsRemaining--;
            this.ticksToNextJump = 5;
         }
      }
   }

   private void setWalkTarget(MobEntity mob, BlockPos pos) {
      mob.getBrain().remember(MemoryModuleType.WALK_TARGET, new WalkTarget(pos, this.walkSpeed, 0));
   }

   private boolean shouldStartJumping(ServerWorld world, MobEntity mob) {
      return this.isAboveBed(world, mob) || this.getNearestBed(mob).isPresent();
   }

   private boolean isAboveBed(ServerWorld world, MobEntity mob) {
      BlockPos _snowman = mob.getBlockPos();
      BlockPos _snowmanx = _snowman.down();
      return this.isBedAt(world, _snowman) || this.isBedAt(world, _snowmanx);
   }

   private boolean isOnBed(ServerWorld world, MobEntity mob) {
      return this.isBedAt(world, mob.getBlockPos());
   }

   private boolean isBedAt(ServerWorld world, BlockPos pos) {
      return world.getBlockState(pos).isIn(BlockTags.BEDS);
   }

   private Optional<BlockPos> getNearestBed(MobEntity mob) {
      return mob.getBrain().getOptionalMemory(MemoryModuleType.NEAREST_BED);
   }

   private boolean isBedGoneTooLong(ServerWorld world, MobEntity mob) {
      return !this.isAboveBed(world, mob) && this.ticksOutOfBedUntilStopped <= 0;
   }

   private boolean isDoneJumping(ServerWorld world, MobEntity mob) {
      return this.isAboveBed(world, mob) && this.jumpsRemaining <= 0;
   }
}
