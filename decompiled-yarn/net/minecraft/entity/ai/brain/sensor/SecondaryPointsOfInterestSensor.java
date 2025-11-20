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

   protected void sense(ServerWorld _snowman, VillagerEntity _snowman) {
      RegistryKey<World> _snowmanxx = _snowman.getRegistryKey();
      BlockPos _snowmanxxx = _snowman.getBlockPos();
      List<GlobalPos> _snowmanxxxx = Lists.newArrayList();
      int _snowmanxxxxx = 4;

      for (int _snowmanxxxxxx = -4; _snowmanxxxxxx <= 4; _snowmanxxxxxx++) {
         for (int _snowmanxxxxxxx = -2; _snowmanxxxxxxx <= 2; _snowmanxxxxxxx++) {
            for (int _snowmanxxxxxxxx = -4; _snowmanxxxxxxxx <= 4; _snowmanxxxxxxxx++) {
               BlockPos _snowmanxxxxxxxxx = _snowmanxxx.add(_snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx);
               if (_snowman.getVillagerData().getProfession().getSecondaryJobSites().contains(_snowman.getBlockState(_snowmanxxxxxxxxx).getBlock())) {
                  _snowmanxxxx.add(GlobalPos.create(_snowmanxx, _snowmanxxxxxxxxx));
               }
            }
         }
      }

      Brain<?> _snowmanxxxxxx = _snowman.getBrain();
      if (!_snowmanxxxx.isEmpty()) {
         _snowmanxxxxxx.remember(MemoryModuleType.SECONDARY_JOB_SITE, _snowmanxxxx);
      } else {
         _snowmanxxxxxx.forget(MemoryModuleType.SECONDARY_JOB_SITE);
      }
   }

   @Override
   public Set<MemoryModuleType<?>> getOutputMemoryModules() {
      return ImmutableSet.of(MemoryModuleType.SECONDARY_JOB_SITE);
   }
}
