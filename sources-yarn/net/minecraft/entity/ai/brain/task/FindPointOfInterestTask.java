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

   public FindPointOfInterestTask(
      PointOfInterestType poiType, MemoryModuleType<GlobalPos> arg2, MemoryModuleType<GlobalPos> arg3, boolean bl, Optional<Byte> optional
   ) {
      super(method_29245(arg2, arg3));
      this.poiType = poiType;
      this.targetMemoryModuleType = arg3;
      this.onlyRunIfChild = bl;
      this.field_25812 = optional;
   }

   public FindPointOfInterestTask(PointOfInterestType arg, MemoryModuleType<GlobalPos> arg2, boolean bl, Optional<Byte> optional) {
      this(arg, arg2, arg2, bl, optional);
   }

   private static ImmutableMap<MemoryModuleType<?>, MemoryModuleState> method_29245(MemoryModuleType<GlobalPos> arg, MemoryModuleType<GlobalPos> arg2) {
      Builder<MemoryModuleType<?>, MemoryModuleState> builder = ImmutableMap.builder();
      builder.put(arg, MemoryModuleState.VALUE_ABSENT);
      if (arg2 != arg) {
         builder.put(arg2, MemoryModuleState.VALUE_ABSENT);
      }

      return builder.build();
   }

   protected boolean shouldRun(ServerWorld arg, PathAwareEntity arg2) {
      if (this.onlyRunIfChild && arg2.isBaby()) {
         return false;
      } else if (this.positionExpireTimeLimit == 0L) {
         this.positionExpireTimeLimit = arg2.world.getTime() + (long)arg.random.nextInt(20);
         return false;
      } else {
         return arg.getTime() >= this.positionExpireTimeLimit;
      }
   }

   protected void run(ServerWorld arg, PathAwareEntity arg2, long l) {
      this.positionExpireTimeLimit = l + 20L + (long)arg.getRandom().nextInt(20);
      PointOfInterestStorage lv = arg.getPointOfInterestStorage();
      this.foundPositionsToExpiry.long2ObjectEntrySet().removeIf(entry -> !((FindPointOfInterestTask.RetryMarker)entry.getValue()).method_29927(l));
      Predicate<BlockPos> predicate = argx -> {
         FindPointOfInterestTask.RetryMarker lvx = (FindPointOfInterestTask.RetryMarker)this.foundPositionsToExpiry.get(argx.asLong());
         if (lvx == null) {
            return true;
         } else if (!lvx.method_29928(l)) {
            return false;
         } else {
            lvx.method_29926(l);
            return true;
         }
      };
      Set<BlockPos> set = lv.method_30957(
            this.poiType.getCompletionCondition(), predicate, arg2.getBlockPos(), 48, PointOfInterestStorage.OccupationStatus.HAS_SPACE
         )
         .limit(5L)
         .collect(Collectors.toSet());
      Path lv2 = arg2.getNavigation().method_29934(set, this.poiType.getSearchDistance());
      if (lv2 != null && lv2.reachesTarget()) {
         BlockPos lv3 = lv2.getTarget();
         lv.getType(lv3).ifPresent(arg5 -> {
            lv.getPosition(this.poiType.getCompletionCondition(), arg2xx -> arg2xx.equals(lv3), lv3, 1);
            arg2.getBrain().remember(this.targetMemoryModuleType, GlobalPos.create(arg.getRegistryKey(), lv3));
            this.field_25812.ifPresent(byte_ -> arg.sendEntityStatus(arg2, byte_));
            this.foundPositionsToExpiry.clear();
            DebugInfoSender.sendPointOfInterest(arg, lv3);
         });
      } else {
         for (BlockPos lv4 : set) {
            this.foundPositionsToExpiry.computeIfAbsent(lv4.asLong(), m -> new FindPointOfInterestTask.RetryMarker(arg2.world.random, l));
         }
      }
   }

   static class RetryMarker {
      private final Random random;
      private long previousAttemptAt;
      private long nextScheduledAttemptAt;
      private int currentDelay;

      RetryMarker(Random random, long time) {
         this.random = random;
         this.method_29926(time);
      }

      public void method_29926(long time) {
         this.previousAttemptAt = time;
         int i = this.currentDelay + this.random.nextInt(40) + 40;
         this.currentDelay = Math.min(i, 400);
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
