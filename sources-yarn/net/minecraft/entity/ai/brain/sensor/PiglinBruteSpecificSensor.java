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
      Brain<?> lv = entity.getBrain();
      Optional<MobEntity> optional = Optional.empty();
      List<AbstractPiglinEntity> list = Lists.newArrayList();

      for (LivingEntity lv2 : lv.getOptionalMemory(MemoryModuleType.VISIBLE_MOBS).orElse(ImmutableList.of())) {
         if (lv2 instanceof WitherSkeletonEntity || lv2 instanceof WitherEntity) {
            optional = Optional.of((MobEntity)lv2);
            break;
         }
      }

      for (LivingEntity lv3 : lv.getOptionalMemory(MemoryModuleType.MOBS).orElse(ImmutableList.of())) {
         if (lv3 instanceof AbstractPiglinEntity && ((AbstractPiglinEntity)lv3).isAdult()) {
            list.add((AbstractPiglinEntity)lv3);
         }
      }

      lv.remember(MemoryModuleType.NEAREST_VISIBLE_NEMESIS, optional);
      lv.remember(MemoryModuleType.NEARBY_ADULT_PIGLINS, list);
   }
}
