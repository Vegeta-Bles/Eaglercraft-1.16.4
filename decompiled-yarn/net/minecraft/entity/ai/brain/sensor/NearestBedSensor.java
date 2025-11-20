package net.minecraft.entity.ai.brain.sensor;

import com.google.common.collect.ImmutableSet;
import it.unimi.dsi.fastutil.longs.Long2LongMap;
import it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.poi.PointOfInterestStorage;
import net.minecraft.world.poi.PointOfInterestType;

public class NearestBedSensor extends Sensor<MobEntity> {
   private final Long2LongMap positionToExpiryTime = new Long2LongOpenHashMap();
   private int tries;
   private long expiryTime;

   public NearestBedSensor() {
      super(20);
   }

   @Override
   public Set<MemoryModuleType<?>> getOutputMemoryModules() {
      return ImmutableSet.of(MemoryModuleType.NEAREST_BED);
   }

   protected void sense(ServerWorld _snowman, MobEntity _snowman) {
      if (_snowman.isBaby()) {
         this.tries = 0;
         this.expiryTime = _snowman.getTime() + (long)_snowman.getRandom().nextInt(20);
         PointOfInterestStorage _snowmanxx = _snowman.getPointOfInterestStorage();
         Predicate<BlockPos> _snowmanxxx = _snowmanxxxx -> {
            long _snowmanxxxxx = _snowmanxxxx.asLong();
            if (this.positionToExpiryTime.containsKey(_snowmanxxxxx)) {
               return false;
            } else if (++this.tries >= 5) {
               return false;
            } else {
               this.positionToExpiryTime.put(_snowmanxxxxx, this.expiryTime + 40L);
               return true;
            }
         };
         Stream<BlockPos> _snowmanxxxx = _snowmanxx.getPositions(
            PointOfInterestType.HOME.getCompletionCondition(), _snowmanxxx, _snowman.getBlockPos(), 48, PointOfInterestStorage.OccupationStatus.ANY
         );
         Path _snowmanxxxxx = _snowman.getNavigation().findPathToAny(_snowmanxxxx, PointOfInterestType.HOME.getSearchDistance());
         if (_snowmanxxxxx != null && _snowmanxxxxx.reachesTarget()) {
            BlockPos _snowmanxxxxxx = _snowmanxxxxx.getTarget();
            Optional<PointOfInterestType> _snowmanxxxxxxx = _snowmanxx.getType(_snowmanxxxxxx);
            if (_snowmanxxxxxxx.isPresent()) {
               _snowman.getBrain().remember(MemoryModuleType.NEAREST_BED, _snowmanxxxxxx);
            }
         } else if (this.tries < 5) {
            this.positionToExpiryTime.long2LongEntrySet().removeIf(_snowmanxxxxxx -> _snowmanxxxxxx.getLongValue() < this.expiryTime);
         }
      }
   }
}
