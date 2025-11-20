package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.dynamic.GlobalPos;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.poi.PointOfInterestStorage;
import net.minecraft.world.poi.PointOfInterestType;

public class FindPointOfInterestTask extends Task<PathAwareEntity> {
   private final PointOfInterestType poiType;
   private final MemoryModuleType<GlobalPos> targetMemoryModuleType;
   private final boolean onlyRunIfChild;
   private final Optional<Byte> field_25812;
   private long positionExpireTimeLimit;
   private final Long2ObjectMap<FindPointOfInterestTask.RetryMarker> foundPositionsToExpiry = new Long2ObjectOpenHashMap();

   public FindPointOfInterestTask(PointOfInterestType poiType, MemoryModuleType<GlobalPos> _snowman, MemoryModuleType<GlobalPos> _snowman, boolean _snowman, Optional<Byte> _snowman) {
      super(method_29245(_snowman, _snowman));
      this.poiType = poiType;
      this.targetMemoryModuleType = _snowman;
      this.onlyRunIfChild = _snowman;
      this.field_25812 = _snowman;
   }

   public FindPointOfInterestTask(PointOfInterestType _snowman, MemoryModuleType<GlobalPos> _snowman, boolean _snowman, Optional<Byte> _snowman) {
      this(_snowman, _snowman, _snowman, _snowman, _snowman);
   }

   private static ImmutableMap<MemoryModuleType<?>, MemoryModuleState> method_29245(MemoryModuleType<GlobalPos> _snowman, MemoryModuleType<GlobalPos> _snowman) {
      Builder<MemoryModuleType<?>, MemoryModuleState> _snowmanxx = ImmutableMap.builder();
      _snowmanxx.put(_snowman, MemoryModuleState.VALUE_ABSENT);
      if (_snowman != _snowman) {
         _snowmanxx.put(_snowman, MemoryModuleState.VALUE_ABSENT);
      }

      return _snowmanxx.build();
   }

   protected boolean shouldRun(ServerWorld _snowman, PathAwareEntity _snowman) {
      if (this.onlyRunIfChild && _snowman.isBaby()) {
         return false;
      } else if (this.positionExpireTimeLimit == 0L) {
         this.positionExpireTimeLimit = _snowman.world.getTime() + (long)_snowman.random.nextInt(20);
         return false;
      } else {
         return _snowman.getTime() >= this.positionExpireTimeLimit;
      }
   }

   protected void run(ServerWorld _snowman, PathAwareEntity _snowman, long _snowman) {
      this.positionExpireTimeLimit = _snowman + 20L + (long)_snowman.getRandom().nextInt(20);
      PointOfInterestStorage _snowmanxxx = _snowman.getPointOfInterestStorage();
      this.foundPositionsToExpiry.long2ObjectEntrySet().removeIf(_snowmanxxxx -> !((FindPointOfInterestTask.RetryMarker)_snowmanxxxx.getValue()).method_29927(_snowman));
      Predicate<BlockPos> _snowmanxxxx = _snowmanxxxxx -> {
         FindPointOfInterestTask.RetryMarker _snowmanxxxxxx = (FindPointOfInterestTask.RetryMarker)this.foundPositionsToExpiry.get(_snowmanxxxxx.asLong());
         if (_snowmanxxxxxx == null) {
            return true;
         } else if (!_snowmanxxxxxx.method_29928(_snowman)) {
            return false;
         } else {
            _snowmanxxxxxx.method_29926(_snowman);
            return true;
         }
      };
      Set<BlockPos> _snowmanxxxxx = _snowmanxxx.method_30957(
            this.poiType.getCompletionCondition(), _snowmanxxxx, _snowman.getBlockPos(), 48, PointOfInterestStorage.OccupationStatus.HAS_SPACE
         )
         .limit(5L)
         .collect(Collectors.toSet());
      Path _snowmanxxxxxx = _snowman.getNavigation().method_29934(_snowmanxxxxx, this.poiType.getSearchDistance());
      if (_snowmanxxxxxx != null && _snowmanxxxxxx.reachesTarget()) {
         BlockPos _snowmanxxxxxxx = _snowmanxxxxxx.getTarget();
         _snowmanxxx.getType(_snowmanxxxxxxx).ifPresent(_snowmanxxxxxxxx -> {
            _snowman.getPosition(this.poiType.getCompletionCondition(), _snowmanxxxxxxxxx -> _snowmanxxxxxxxxx.equals(_snowman), _snowman, 1);
            _snowman.getBrain().remember(this.targetMemoryModuleType, GlobalPos.create(_snowman.getRegistryKey(), _snowman));
            this.field_25812.ifPresent(_snowmanxxxxxxxxx -> _snowman.sendEntityStatus(_snowman, _snowmanxxxxxxxxx));
            this.foundPositionsToExpiry.clear();
            DebugInfoSender.sendPointOfInterest(_snowman, _snowman);
         });
      } else {
         for (BlockPos _snowmanxxxxxxx : _snowmanxxxxx) {
            this.foundPositionsToExpiry.computeIfAbsent(_snowmanxxxxxxx.asLong(), _snowmanxxxxxxxx -> new FindPointOfInterestTask.RetryMarker(_snowman.world.random, _snowman));
         }
      }
   }

   static class RetryMarker {
      private final Random random;
      private long previousAttemptAt;
      private long nextScheduledAttemptAt;
      private int currentDelay;

      RetryMarker(Random _snowman, long time) {
         this.random = _snowman;
         this.method_29926(time);
      }

      public void method_29926(long time) {
         this.previousAttemptAt = time;
         int _snowman = this.currentDelay + this.random.nextInt(40) + 40;
         this.currentDelay = Math.min(_snowman, 400);
         this.nextScheduledAttemptAt = time + (long)this.currentDelay;
      }

      public boolean method_29927(long time) {
         return time - this.previousAttemptAt < 400L;
      }

      public boolean method_29928(long time) {
         return time >= this.nextScheduledAttemptAt;
      }

      @Override
      public String toString() {
         return "RetryMarker{, previousAttemptAt="
            + this.previousAttemptAt
            + ", nextScheduledAttemptAt="
            + this.nextScheduledAttemptAt
            + ", currentDelay="
            + this.currentDelay
            + '}';
      }
   }
}
