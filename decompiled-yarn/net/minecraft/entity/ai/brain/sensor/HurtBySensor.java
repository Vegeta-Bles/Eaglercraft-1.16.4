package net.minecraft.entity.ai.brain.sensor;

import com.google.common.collect.ImmutableSet;
import java.util.Set;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.world.ServerWorld;

public class HurtBySensor extends Sensor<LivingEntity> {
   public HurtBySensor() {
   }

   @Override
   public Set<MemoryModuleType<?>> getOutputMemoryModules() {
      return ImmutableSet.of(MemoryModuleType.HURT_BY, MemoryModuleType.HURT_BY_ENTITY);
   }

   @Override
   protected void sense(ServerWorld world, LivingEntity entity) {
      Brain<?> _snowman = entity.getBrain();
      DamageSource _snowmanx = entity.getRecentDamageSource();
      if (_snowmanx != null) {
         _snowman.remember(MemoryModuleType.HURT_BY, entity.getRecentDamageSource());
         Entity _snowmanxx = _snowmanx.getAttacker();
         if (_snowmanxx instanceof LivingEntity) {
            _snowman.remember(MemoryModuleType.HURT_BY_ENTITY, (LivingEntity)_snowmanxx);
         }
      } else {
         _snowman.forget(MemoryModuleType.HURT_BY);
      }

      _snowman.getOptionalMemory(MemoryModuleType.HURT_BY_ENTITY).ifPresent(_snowmanxx -> {
         if (!_snowmanxx.isAlive() || _snowmanxx.world != world) {
            _snowman.forget(MemoryModuleType.HURT_BY_ENTITY);
         }
      });
   }
}
