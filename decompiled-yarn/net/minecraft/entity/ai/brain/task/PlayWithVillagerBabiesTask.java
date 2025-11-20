package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetFinder;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.EntityLookTarget;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class PlayWithVillagerBabiesTask extends Task<PathAwareEntity> {
   public PlayWithVillagerBabiesTask() {
      super(
         ImmutableMap.of(
            MemoryModuleType.VISIBLE_VILLAGER_BABIES,
            MemoryModuleState.VALUE_PRESENT,
            MemoryModuleType.WALK_TARGET,
            MemoryModuleState.VALUE_ABSENT,
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleState.REGISTERED,
            MemoryModuleType.INTERACTION_TARGET,
            MemoryModuleState.REGISTERED
         )
      );
   }

   protected boolean shouldRun(ServerWorld _snowman, PathAwareEntity _snowman) {
      return _snowman.getRandom().nextInt(10) == 0 && this.hasVisibleVillagerBabies(_snowman);
   }

   protected void run(ServerWorld _snowman, PathAwareEntity _snowman, long _snowman) {
      LivingEntity _snowmanxxx = this.findVisibleVillagerBaby(_snowman);
      if (_snowmanxxx != null) {
         this.setGroundTarget(_snowman, _snowman, _snowmanxxx);
      } else {
         Optional<LivingEntity> _snowmanxxxx = this.getLeastPopularBabyInteractionTarget(_snowman);
         if (_snowmanxxxx.isPresent()) {
            setPlayTarget(_snowman, _snowmanxxxx.get());
         } else {
            this.getVisibleMob(_snowman).ifPresent(_snowmanxxxxx -> setPlayTarget(_snowman, _snowmanxxxxx));
         }
      }
   }

   private void setGroundTarget(ServerWorld world, PathAwareEntity entity, LivingEntity unusedBaby) {
      for (int _snowman = 0; _snowman < 10; _snowman++) {
         Vec3d _snowmanx = TargetFinder.findGroundTarget(entity, 20, 8);
         if (_snowmanx != null && world.isNearOccupiedPointOfInterest(new BlockPos(_snowmanx))) {
            entity.getBrain().remember(MemoryModuleType.WALK_TARGET, new WalkTarget(_snowmanx, 0.6F, 0));
            return;
         }
      }
   }

   private static void setPlayTarget(PathAwareEntity entity, LivingEntity target) {
      Brain<?> _snowman = entity.getBrain();
      _snowman.remember(MemoryModuleType.INTERACTION_TARGET, target);
      _snowman.remember(MemoryModuleType.LOOK_TARGET, new EntityLookTarget(target, true));
      _snowman.remember(MemoryModuleType.WALK_TARGET, new WalkTarget(new EntityLookTarget(target, false), 0.6F, 1));
   }

   private Optional<LivingEntity> getVisibleMob(PathAwareEntity entity) {
      return this.getVisibleVillagerBabies(entity).stream().findAny();
   }

   private Optional<LivingEntity> getLeastPopularBabyInteractionTarget(PathAwareEntity entity) {
      Map<LivingEntity, Integer> _snowman = this.getBabyInteractionTargetCounts(entity);
      return _snowman.entrySet()
         .stream()
         .sorted(Comparator.comparingInt(Entry::getValue))
         .filter(_snowmanx -> _snowmanx.getValue() > 0 && _snowmanx.getValue() <= 5)
         .map(Entry::getKey)
         .findFirst();
   }

   private Map<LivingEntity, Integer> getBabyInteractionTargetCounts(PathAwareEntity entity) {
      Map<LivingEntity, Integer> _snowman = Maps.newHashMap();
      this.getVisibleVillagerBabies(entity).stream().filter(this::hasInteractionTarget).forEach(_snowmanx -> {
         Integer var10000 = _snowman.compute(this.getInteractionTarget(_snowmanx), (_snowmanxx, _snowmanxxx) -> _snowmanxxx == null ? 1 : _snowmanxxx + 1);
      });
      return _snowman;
   }

   private List<LivingEntity> getVisibleVillagerBabies(PathAwareEntity entity) {
      return entity.getBrain().getOptionalMemory(MemoryModuleType.VISIBLE_VILLAGER_BABIES).get();
   }

   private LivingEntity getInteractionTarget(LivingEntity entity) {
      return entity.getBrain().getOptionalMemory(MemoryModuleType.INTERACTION_TARGET).get();
   }

   @Nullable
   private LivingEntity findVisibleVillagerBaby(LivingEntity entity) {
      return entity.getBrain()
         .getOptionalMemory(MemoryModuleType.VISIBLE_VILLAGER_BABIES)
         .get()
         .stream()
         .filter(_snowmanx -> this.isInteractionTargetOf(entity, _snowmanx))
         .findAny()
         .orElse(null);
   }

   private boolean hasInteractionTarget(LivingEntity entity) {
      return entity.getBrain().getOptionalMemory(MemoryModuleType.INTERACTION_TARGET).isPresent();
   }

   private boolean isInteractionTargetOf(LivingEntity entity, LivingEntity other) {
      return other.getBrain().getOptionalMemory(MemoryModuleType.INTERACTION_TARGET).filter(_snowmanx -> _snowmanx == entity).isPresent();
   }

   private boolean hasVisibleVillagerBabies(PathAwareEntity entity) {
      return entity.getBrain().hasMemoryModule(MemoryModuleType.VISIBLE_VILLAGER_BABIES);
   }
}
