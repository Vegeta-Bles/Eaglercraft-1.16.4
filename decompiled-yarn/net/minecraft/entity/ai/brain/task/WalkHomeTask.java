package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.longs.Long2LongMap;
import it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.poi.PointOfInterestStorage;
import net.minecraft.world.poi.PointOfInterestType;

public class WalkHomeTask extends Task<LivingEntity> {
   private final float speed;
   private final Long2LongMap positionToExpiry = new Long2LongOpenHashMap();
   private int tries;
   private long expiryTimeLimit;

   public WalkHomeTask(float speed) {
      super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT, MemoryModuleType.HOME, MemoryModuleState.VALUE_ABSENT));
      this.speed = speed;
   }

   @Override
   protected boolean shouldRun(ServerWorld world, LivingEntity entity) {
      if (world.getTime() - this.expiryTimeLimit < 20L) {
         return false;
      } else {
         PathAwareEntity _snowman = (PathAwareEntity)entity;
         PointOfInterestStorage _snowmanx = world.getPointOfInterestStorage();
         Optional<BlockPos> _snowmanxx = _snowmanx.getNearestPosition(
            PointOfInterestType.HOME.getCompletionCondition(), entity.getBlockPos(), 48, PointOfInterestStorage.OccupationStatus.ANY
         );
         return _snowmanxx.isPresent() && !(_snowmanxx.get().getSquaredDistance(_snowman.getBlockPos()) <= 4.0);
      }
   }

   @Override
   protected void run(ServerWorld world, LivingEntity entity, long time) {
      this.tries = 0;
      this.expiryTimeLimit = world.getTime() + (long)world.getRandom().nextInt(20);
      PathAwareEntity _snowman = (PathAwareEntity)entity;
      PointOfInterestStorage _snowmanx = world.getPointOfInterestStorage();
      Predicate<BlockPos> _snowmanxx = _snowmanxxx -> {
         long _snowmanx = _snowmanxxx.asLong();
         if (this.positionToExpiry.containsKey(_snowmanx)) {
            return false;
         } else if (++this.tries >= 5) {
            return false;
         } else {
            this.positionToExpiry.put(_snowmanx, this.expiryTimeLimit + 40L);
            return true;
         }
      };
      Stream<BlockPos> _snowmanxxx = _snowmanx.getPositions(
         PointOfInterestType.HOME.getCompletionCondition(), _snowmanxx, entity.getBlockPos(), 48, PointOfInterestStorage.OccupationStatus.ANY
      );
      Path _snowmanxxxx = _snowman.getNavigation().findPathToAny(_snowmanxxx, PointOfInterestType.HOME.getSearchDistance());
      if (_snowmanxxxx != null && _snowmanxxxx.reachesTarget()) {
         BlockPos _snowmanxxxxx = _snowmanxxxx.getTarget();
         Optional<PointOfInterestType> _snowmanxxxxxx = _snowmanx.getType(_snowmanxxxxx);
         if (_snowmanxxxxxx.isPresent()) {
            entity.getBrain().remember(MemoryModuleType.WALK_TARGET, new WalkTarget(_snowmanxxxxx, this.speed, 1));
            DebugInfoSender.sendPointOfInterest(world, _snowmanxxxxx);
         }
      } else if (this.tries < 5) {
         this.positionToExpiry.long2LongEntrySet().removeIf(_snowmanxxxxx -> _snowmanxxxxx.getLongValue() < this.expiryTimeLimit);
      }
   }
}
