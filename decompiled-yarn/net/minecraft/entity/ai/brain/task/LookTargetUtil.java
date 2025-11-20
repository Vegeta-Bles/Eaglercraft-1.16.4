package net.minecraft.entity.ai.brain.task;

import java.util.Comparator;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.BlockPosLookTarget;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.EntityLookTarget;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.Vec3d;

public class LookTargetUtil {
   public static void lookAtAndWalkTowardsEachOther(LivingEntity first, LivingEntity second, float speed) {
      lookAtEachOther(first, second);
      walkTowardsEachOther(first, second, speed);
   }

   public static boolean canSee(Brain<?> brain, LivingEntity target) {
      return brain.getOptionalMemory(MemoryModuleType.VISIBLE_MOBS).filter(_snowmanx -> _snowmanx.contains(target)).isPresent();
   }

   public static boolean canSee(Brain<?> brain, MemoryModuleType<? extends LivingEntity> memoryModuleType, EntityType<?> entityType) {
      return canSee(brain, memoryModuleType, _snowmanx -> _snowmanx.getType() == entityType);
   }

   private static boolean canSee(Brain<?> brain, MemoryModuleType<? extends LivingEntity> memoryType, Predicate<LivingEntity> filter) {
      return brain.getOptionalMemory(memoryType).filter(filter).filter(LivingEntity::isAlive).filter(_snowmanx -> canSee(brain, _snowmanx)).isPresent();
   }

   private static void lookAtEachOther(LivingEntity first, LivingEntity second) {
      lookAt(first, second);
      lookAt(second, first);
   }

   public static void lookAt(LivingEntity entity, LivingEntity target) {
      entity.getBrain().remember(MemoryModuleType.LOOK_TARGET, new EntityLookTarget(target, true));
   }

   private static void walkTowardsEachOther(LivingEntity first, LivingEntity second, float speed) {
      int _snowman = 2;
      walkTowards(first, second, speed, 2);
      walkTowards(second, first, speed, 2);
   }

   public static void walkTowards(LivingEntity entity, Entity target, float speed, int completionRange) {
      WalkTarget _snowman = new WalkTarget(new EntityLookTarget(target, false), speed, completionRange);
      entity.getBrain().remember(MemoryModuleType.LOOK_TARGET, new EntityLookTarget(target, true));
      entity.getBrain().remember(MemoryModuleType.WALK_TARGET, _snowman);
   }

   public static void walkTowards(LivingEntity entity, BlockPos target, float speed, int completionRange) {
      WalkTarget _snowman = new WalkTarget(new BlockPosLookTarget(target), speed, completionRange);
      entity.getBrain().remember(MemoryModuleType.LOOK_TARGET, new BlockPosLookTarget(target));
      entity.getBrain().remember(MemoryModuleType.WALK_TARGET, _snowman);
   }

   public static void give(LivingEntity entity, ItemStack stack, Vec3d targetLocation) {
      double _snowman = entity.getEyeY() - 0.3F;
      ItemEntity _snowmanx = new ItemEntity(entity.world, entity.getX(), _snowman, entity.getZ(), stack);
      float _snowmanxx = 0.3F;
      Vec3d _snowmanxxx = targetLocation.subtract(entity.getPos());
      _snowmanxxx = _snowmanxxx.normalize().multiply(0.3F);
      _snowmanx.setVelocity(_snowmanxxx);
      _snowmanx.setToDefaultPickupDelay();
      entity.world.spawnEntity(_snowmanx);
   }

   public static ChunkSectionPos getPosClosestToOccupiedPointOfInterest(ServerWorld world, ChunkSectionPos center, int radius) {
      int _snowman = world.getOccupiedPointOfInterestDistance(center);
      return ChunkSectionPos.stream(center, radius)
         .filter(_snowmanxx -> world.getOccupiedPointOfInterestDistance(_snowmanxx) < _snowman)
         .min(Comparator.comparingInt(world::getOccupiedPointOfInterestDistance))
         .orElse(center);
   }

   public static boolean method_25940(MobEntity _snowman, LivingEntity _snowman, int _snowman) {
      Item _snowmanxxx = _snowman.getMainHandStack().getItem();
      if (_snowmanxxx instanceof RangedWeaponItem && _snowman.canUseRangedWeapon((RangedWeaponItem)_snowmanxxx)) {
         int _snowmanxxxx = ((RangedWeaponItem)_snowmanxxx).getRange() - _snowman;
         return _snowman.isInRange(_snowman, (double)_snowmanxxxx);
      } else {
         return method_25941(_snowman, _snowman);
      }
   }

   public static boolean method_25941(LivingEntity _snowman, LivingEntity _snowman) {
      double _snowmanxx = _snowman.squaredDistanceTo(_snowman.getX(), _snowman.getY(), _snowman.getZ());
      double _snowmanxxx = (double)(_snowman.getWidth() * 2.0F * _snowman.getWidth() * 2.0F + _snowman.getWidth());
      return _snowmanxx <= _snowmanxxx;
   }

   public static boolean isNewTargetTooFar(LivingEntity source, LivingEntity target, double extraDistance) {
      Optional<LivingEntity> _snowman = source.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET);
      if (!_snowman.isPresent()) {
         return false;
      } else {
         double _snowmanx = source.squaredDistanceTo(_snowman.get().getPos());
         double _snowmanxx = source.squaredDistanceTo(target.getPos());
         return _snowmanxx > _snowmanx + extraDistance * extraDistance;
      }
   }

   public static boolean isVisibleInMemory(LivingEntity source, LivingEntity target) {
      Brain<?> _snowman = source.getBrain();
      return !_snowman.hasMemoryModule(MemoryModuleType.VISIBLE_MOBS) ? false : _snowman.getOptionalMemory(MemoryModuleType.VISIBLE_MOBS).get().contains(target);
   }

   public static LivingEntity getCloserEntity(LivingEntity source, Optional<LivingEntity> first, LivingEntity second) {
      return !first.isPresent() ? second : getCloserEntity(source, first.get(), second);
   }

   public static LivingEntity getCloserEntity(LivingEntity source, LivingEntity first, LivingEntity second) {
      Vec3d _snowman = first.getPos();
      Vec3d _snowmanx = second.getPos();
      return source.squaredDistanceTo(_snowman) < source.squaredDistanceTo(_snowmanx) ? first : second;
   }

   public static Optional<LivingEntity> getEntity(LivingEntity entity, MemoryModuleType<UUID> uuidMemoryModule) {
      Optional<UUID> _snowman = entity.getBrain().getOptionalMemory(uuidMemoryModule);
      return _snowman.map(_snowmanx -> (LivingEntity)((ServerWorld)entity.world).getEntity(_snowmanx));
   }

   public static Stream<VillagerEntity> streamSeenVillagers(VillagerEntity villager, Predicate<VillagerEntity> filter) {
      return villager.getBrain()
         .getOptionalMemory(MemoryModuleType.MOBS)
         .map(
            _snowmanxx -> _snowmanxx.stream()
                  .filter(_snowmanxxxx -> _snowmanxxxx instanceof VillagerEntity && _snowmanxxxx != villager)
                  .map(_snowmanxxx -> (VillagerEntity)_snowmanxxx)
                  .filter(LivingEntity::isAlive)
                  .filter(filter)
         )
         .orElseGet(Stream::empty);
   }
}
