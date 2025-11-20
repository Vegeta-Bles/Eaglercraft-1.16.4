package net.minecraft.entity.ai.brain.sensor;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.mob.HoglinEntity;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;

public class HoglinSpecificSensor extends Sensor<HoglinEntity> {
   public HoglinSpecificSensor() {
   }

   @Override
   public Set<MemoryModuleType<?>> getOutputMemoryModules() {
      return ImmutableSet.of(
         MemoryModuleType.VISIBLE_MOBS,
         MemoryModuleType.NEAREST_REPELLENT,
         MemoryModuleType.NEAREST_VISIBLE_ADULT_PIGLIN,
         MemoryModuleType.NEAREST_VISIBLE_ADULT_HOGLINS,
         MemoryModuleType.VISIBLE_ADULT_PIGLIN_COUNT,
         MemoryModuleType.VISIBLE_ADULT_HOGLIN_COUNT,
         new MemoryModuleType[0]
      );
   }

   protected void sense(ServerWorld _snowman, HoglinEntity _snowman) {
      Brain<?> _snowmanxx = _snowman.getBrain();
      _snowmanxx.remember(MemoryModuleType.NEAREST_REPELLENT, this.findNearestWarpedFungus(_snowman, _snowman));
      Optional<PiglinEntity> _snowmanxxx = Optional.empty();
      int _snowmanxxxx = 0;
      List<HoglinEntity> _snowmanxxxxx = Lists.newArrayList();

      for (LivingEntity _snowmanxxxxxx : _snowmanxx.getOptionalMemory(MemoryModuleType.VISIBLE_MOBS).orElse(Lists.newArrayList())) {
         if (_snowmanxxxxxx instanceof PiglinEntity && !_snowmanxxxxxx.isBaby()) {
            _snowmanxxxx++;
            if (!_snowmanxxx.isPresent()) {
               _snowmanxxx = Optional.of((PiglinEntity)_snowmanxxxxxx);
            }
         }

         if (_snowmanxxxxxx instanceof HoglinEntity && !_snowmanxxxxxx.isBaby()) {
            _snowmanxxxxx.add((HoglinEntity)_snowmanxxxxxx);
         }
      }

      _snowmanxx.remember(MemoryModuleType.NEAREST_VISIBLE_ADULT_PIGLIN, _snowmanxxx);
      _snowmanxx.remember(MemoryModuleType.NEAREST_VISIBLE_ADULT_HOGLINS, _snowmanxxxxx);
      _snowmanxx.remember(MemoryModuleType.VISIBLE_ADULT_PIGLIN_COUNT, _snowmanxxxx);
      _snowmanxx.remember(MemoryModuleType.VISIBLE_ADULT_HOGLIN_COUNT, _snowmanxxxxx.size());
   }

   private Optional<BlockPos> findNearestWarpedFungus(ServerWorld world, HoglinEntity hoglin) {
      return BlockPos.findClosest(hoglin.getBlockPos(), 8, 4, _snowmanx -> world.getBlockState(_snowmanx).isIn(BlockTags.HOGLIN_REPELLENTS));
   }
}
