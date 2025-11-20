package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.dynamic.GlobalPos;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.poi.PointOfInterestStorage;
import net.minecraft.world.poi.PointOfInterestType;

public class HideInHomeTask extends Task<LivingEntity> {
   private final float walkSpeed;
   private final int maxDistance;
   private final int preferredDistance;
   private Optional<BlockPos> homePosition = Optional.empty();

   public HideInHomeTask(int maxDistance, float walkSpeed, int preferredDistance) {
      super(
         ImmutableMap.of(
            MemoryModuleType.WALK_TARGET,
            MemoryModuleState.VALUE_ABSENT,
            MemoryModuleType.HOME,
            MemoryModuleState.REGISTERED,
            MemoryModuleType.HIDING_PLACE,
            MemoryModuleState.REGISTERED
         )
      );
      this.maxDistance = maxDistance;
      this.walkSpeed = walkSpeed;
      this.preferredDistance = preferredDistance;
   }

   @Override
   protected boolean shouldRun(ServerWorld world, LivingEntity entity) {
      Optional<BlockPos> _snowman = world.getPointOfInterestStorage()
         .getPosition(
            _snowmanx -> _snowmanx == PointOfInterestType.HOME, _snowmanx -> true, entity.getBlockPos(), this.preferredDistance + 1, PointOfInterestStorage.OccupationStatus.ANY
         );
      if (_snowman.isPresent() && _snowman.get().isWithinDistance(entity.getPos(), (double)this.preferredDistance)) {
         this.homePosition = _snowman;
      } else {
         this.homePosition = Optional.empty();
      }

      return true;
   }

   @Override
   protected void run(ServerWorld world, LivingEntity entity, long time) {
      Brain<?> _snowman = entity.getBrain();
      Optional<BlockPos> _snowmanx = this.homePosition;
      if (!_snowmanx.isPresent()) {
         _snowmanx = world.getPointOfInterestStorage()
            .getPosition(
               _snowmanxx -> _snowmanxx == PointOfInterestType.HOME,
               _snowmanxx -> true,
               PointOfInterestStorage.OccupationStatus.ANY,
               entity.getBlockPos(),
               this.maxDistance,
               entity.getRandom()
            );
         if (!_snowmanx.isPresent()) {
            Optional<GlobalPos> _snowmanxx = _snowman.getOptionalMemory(MemoryModuleType.HOME);
            if (_snowmanxx.isPresent()) {
               _snowmanx = Optional.of(_snowmanxx.get().getPos());
            }
         }
      }

      if (_snowmanx.isPresent()) {
         _snowman.forget(MemoryModuleType.PATH);
         _snowman.forget(MemoryModuleType.LOOK_TARGET);
         _snowman.forget(MemoryModuleType.BREED_TARGET);
         _snowman.forget(MemoryModuleType.INTERACTION_TARGET);
         _snowman.remember(MemoryModuleType.HIDING_PLACE, GlobalPos.create(world.getRegistryKey(), _snowmanx.get()));
         if (!_snowmanx.get().isWithinDistance(entity.getPos(), (double)this.preferredDistance)) {
            _snowman.remember(MemoryModuleType.WALK_TARGET, new WalkTarget(_snowmanx.get(), this.walkSpeed, this.preferredDistance));
         }
      }
   }
}
