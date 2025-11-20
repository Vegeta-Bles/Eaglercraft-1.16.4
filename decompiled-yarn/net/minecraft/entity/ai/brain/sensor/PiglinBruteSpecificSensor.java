package net.minecraft.entity.ai.brain.sensor;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.mob.AbstractPiglinEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.server.world.ServerWorld;

public class PiglinBruteSpecificSensor extends Sensor<LivingEntity> {
   public PiglinBruteSpecificSensor() {
   }

   @Override
   public Set<MemoryModuleType<?>> getOutputMemoryModules() {
      return ImmutableSet.of(MemoryModuleType.VISIBLE_MOBS, MemoryModuleType.NEAREST_VISIBLE_NEMESIS, MemoryModuleType.NEARBY_ADULT_PIGLINS);
   }

   @Override
   protected void sense(ServerWorld world, LivingEntity entity) {
      Brain<?> _snowman = entity.getBrain();
      Optional<MobEntity> _snowmanx = Optional.empty();
      List<AbstractPiglinEntity> _snowmanxx = Lists.newArrayList();

      for (LivingEntity _snowmanxxx : _snowman.getOptionalMemory(MemoryModuleType.VISIBLE_MOBS).orElse(ImmutableList.of())) {
         if (_snowmanxxx instanceof WitherSkeletonEntity || _snowmanxxx instanceof WitherEntity) {
            _snowmanx = Optional.of((MobEntity)_snowmanxxx);
            break;
         }
      }

      for (LivingEntity _snowmanxxxx : _snowman.getOptionalMemory(MemoryModuleType.MOBS).orElse(ImmutableList.of())) {
         if (_snowmanxxxx instanceof AbstractPiglinEntity && ((AbstractPiglinEntity)_snowmanxxxx).isAdult()) {
            _snowmanxx.add((AbstractPiglinEntity)_snowmanxxxx);
         }
      }

      _snowman.remember(MemoryModuleType.NEAREST_VISIBLE_NEMESIS, _snowmanx);
      _snowman.remember(MemoryModuleType.NEARBY_ADULT_PIGLINS, _snowmanxx);
   }
}
