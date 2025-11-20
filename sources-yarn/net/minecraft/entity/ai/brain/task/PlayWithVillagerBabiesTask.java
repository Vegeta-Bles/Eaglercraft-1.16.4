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

   protected boolean shouldRun(ServerWorld arg, PathAwareEntity arg2) {
      return arg.getRandom().nextInt(10) == 0 && this.hasVisibleVillagerBabies(arg2);
   }

   protected void run(ServerWorld arg, PathAwareEntity arg2, long l) {
      LivingEntity lv = this.findVisibleVillagerBaby(arg2);
      if (lv != null) {
         this.setGroundTarget(arg, arg2, lv);
      } else {
         Optional<LivingEntity> optional = this.getLeastPopularBabyInteractionTarget(arg2);
         if (optional.isPresent()) {
            setPlayTarget(arg2, optional.get());
         } else {
            this.getVisibleMob(arg2).ifPresent(arg2x -> setPlayTarget(arg2, arg2x));
         }
      }
   }

   private void setGroundTarget(ServerWorld world, PathAwareEntity entity, LivingEntity unusedBaby) {
      for (int i = 0; i < 10; i++) {
         Vec3d lv = TargetFinder.findGroundTarget(entity, 20, 8);
         if (lv != null && world.isNearOccupiedPointOfInterest(new BlockPos(lv))) {
            entity.getBrain().remember(MemoryModuleType.WALK_TARGET, new WalkTarget(lv, 0.6F, 0));
            return;
         }
      }
   }

   private static void setPlayTarget(PathAwareEntity entity, LivingEntity target) {
      Brain<?> lv = entity.getBrain();
      lv.remember(MemoryModuleType.INTERACTION_TARGET, target);
      lv.remember(MemoryModuleType.LOOK_TARGET, new EntityLookTarget(target, true));
      lv.remember(MemoryModuleType.WALK_TARGET, new WalkTarget(new EntityLookTarget(target, false), 0.6F, 1));
   }

   private Optional<LivingEntity> getVisibleMob(PathAwareEntity entity) {
      return this.getVisibleVillagerBabies(entity).stream().findAny();
   }

   private Optional<LivingEntity> getLeastPopularBabyInteractionTarget(PathAwareEntity entity) {
      Map<LivingEntity, Integer> map = this.getBabyInteractionTargetCounts(entity);
      return map.entrySet()
         .stream()
         .sorted(Comparator.comparingInt(Entry::getValue))
         .filter(entry -> entry.getValue() > 0 && entry.getValue() <= 5)
         .map(Entry::getKey)
         .findFirst();
   }

   private Map<LivingEntity, Integer> getBabyInteractionTargetCounts(PathAwareEntity entity) {
      Map<LivingEntity, Integer> map = Maps.newHashMap();
      this.getVisibleVillagerBabies(entity).stream().filter(this::hasInteractionTarget).forEach(arg -> {
         Integer var10000 = map.compute(this.getInteractionTarget(arg), (argx, integer) -> integer == null ? 1 : integer + 1);
      });
      return map;
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
         .filter(arg2 -> this.isInteractionTargetOf(entity, arg2))
         .findAny()
         .orElse(null);
   }

   private boolean hasInteractionTarget(LivingEntity entity) {
      return entity.getBrain().getOptionalMemory(MemoryModuleType.INTERACTION_TARGET).isPresent();
   }

   private boolean isInteractionTargetOf(LivingEntity entity, LivingEntity other) {
      return other.getBrain().getOptionalMemory(MemoryModuleType.INTERACTION_TARGET).filter(arg2 -> arg2 == entity).isPresent();
   }

   private boolean hasVisibleVillagerBabies(PathAwareEntity entity) {
      return entity.getBrain().hasMemoryModule(MemoryModuleType.VISIBLE_VILLAGER_BABIES);
   }
}
