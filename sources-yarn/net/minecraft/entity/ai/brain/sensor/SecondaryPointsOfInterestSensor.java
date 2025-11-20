package net.minecraft.entity.ai.brain.sensor;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Set;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.dynamic.GlobalPos;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

public class SecondaryPointsOfInterestSensor extends Sensor<VillagerEntity> {
   public SecondaryPointsOfInterestSensor() {
      super(40);
   }

   protected void sense(ServerWorld arg, VillagerEntity arg2) {
      RegistryKey<World> lv = arg.getRegistryKey();
      BlockPos lv2 = arg2.getBlockPos();
      List<GlobalPos> list = Lists.newArrayList();
      int i = 4;

      for (int j = -4; j <= 4; j++) {
         for (int k = -2; k <= 2; k++) {
            for (int l = -4; l <= 4; l++) {
               BlockPos lv3 = lv2.add(j, k, l);
               if (arg2.getVillagerData().getProfession().getSecondaryJobSites().contains(arg.getBlockState(lv3).getBlock())) {
                  list.add(GlobalPos.create(lv, lv3));
               }
            }
         }
      }

      Brain<?> lv4 = arg2.getBrain();
      if (!list.isEmpty()) {
         lv4.remember(MemoryModuleType.SECONDARY_JOB_SITE, list);
      } else {
         lv4.forget(MemoryModuleType.SECONDARY_JOB_SITE);
      }
   }

   @Override
   public Set<MemoryModuleType<?>> getOutputMemoryModules() {
      return ImmutableSet.of(MemoryModuleType.SECONDARY_JOB_SITE);
   }
}
