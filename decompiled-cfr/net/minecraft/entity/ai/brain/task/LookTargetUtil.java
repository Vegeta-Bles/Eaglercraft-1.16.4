/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.entity.ai.brain.task;

import java.util.Comparator;
import java.util.List;
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
        LookTargetUtil.lookAtEachOther(first, second);
        LookTargetUtil.walkTowardsEachOther(first, second, speed);
    }

    public static boolean canSee(Brain<?> brain, LivingEntity target) {
        return brain.getOptionalMemory(MemoryModuleType.VISIBLE_MOBS).filter(list -> list.contains(target)).isPresent();
    }

    public static boolean canSee(Brain<?> brain, MemoryModuleType<? extends LivingEntity> memoryModuleType, EntityType<?> entityType) {
        return LookTargetUtil.canSee(brain, memoryModuleType, (LivingEntity livingEntity) -> livingEntity.getType() == entityType);
    }

    private static boolean canSee(Brain<?> brain, MemoryModuleType<? extends LivingEntity> memoryType, Predicate<LivingEntity> filter) {
        return brain.getOptionalMemory(memoryType).filter(filter).filter(LivingEntity::isAlive).filter(livingEntity -> LookTargetUtil.canSee(brain, livingEntity)).isPresent();
    }

    private static void lookAtEachOther(LivingEntity first, LivingEntity second) {
        LookTargetUtil.lookAt(first, second);
        LookTargetUtil.lookAt(second, first);
    }

    public static void lookAt(LivingEntity entity, LivingEntity target) {
        entity.getBrain().remember(MemoryModuleType.LOOK_TARGET, new EntityLookTarget(target, true));
    }

    private static void walkTowardsEachOther(LivingEntity first, LivingEntity second, float speed) {
        int n = 2;
        LookTargetUtil.walkTowards(first, second, speed, 2);
        LookTargetUtil.walkTowards(second, first, speed, 2);
    }

    public static void walkTowards(LivingEntity entity, Entity target, float speed, int completionRange) {
        WalkTarget walkTarget = new WalkTarget(new EntityLookTarget(target, false), speed, completionRange);
        entity.getBrain().remember(MemoryModuleType.LOOK_TARGET, new EntityLookTarget(target, true));
        entity.getBrain().remember(MemoryModuleType.WALK_TARGET, walkTarget);
    }

    public static void walkTowards(LivingEntity entity, BlockPos target, float speed, int completionRange) {
        WalkTarget walkTarget = new WalkTarget(new BlockPosLookTarget(target), speed, completionRange);
        entity.getBrain().remember(MemoryModuleType.LOOK_TARGET, new BlockPosLookTarget(target));
        entity.getBrain().remember(MemoryModuleType.WALK_TARGET, walkTarget);
    }

    public static void give(LivingEntity entity, ItemStack stack, Vec3d targetLocation) {
        double d = entity.getEyeY() - (double)0.3f;
        ItemEntity _snowman2 = new ItemEntity(entity.world, entity.getX(), d, entity.getZ(), stack);
        float _snowman3 = 0.3f;
        Vec3d _snowman4 = targetLocation.subtract(entity.getPos());
        _snowman4 = _snowman4.normalize().multiply(0.3f);
        _snowman2.setVelocity(_snowman4);
        _snowman2.setToDefaultPickupDelay();
        entity.world.spawnEntity(_snowman2);
    }

    public static ChunkSectionPos getPosClosestToOccupiedPointOfInterest(ServerWorld world, ChunkSectionPos center, int radius) {
        int n = world.getOccupiedPointOfInterestDistance(center);
        return ChunkSectionPos.stream(center, radius).filter(chunkSectionPos -> world.getOccupiedPointOfInterestDistance((ChunkSectionPos)chunkSectionPos) < n).min(Comparator.comparingInt(world::getOccupiedPointOfInterestDistance)).orElse(center);
    }

    public static boolean method_25940(MobEntity mobEntity2, LivingEntity livingEntity, int n) {
        MobEntity mobEntity2;
        Item item = mobEntity2.getMainHandStack().getItem();
        if (item instanceof RangedWeaponItem && mobEntity2.canUseRangedWeapon((RangedWeaponItem)item)) {
            int n2 = ((RangedWeaponItem)item).getRange() - n;
            return mobEntity2.isInRange(livingEntity, n2);
        }
        return LookTargetUtil.method_25941(mobEntity2, livingEntity);
    }

    public static boolean method_25941(LivingEntity livingEntity, LivingEntity livingEntity2) {
        double d = livingEntity.squaredDistanceTo(livingEntity2.getX(), livingEntity2.getY(), livingEntity2.getZ());
        return d <= (_snowman = (double)(livingEntity.getWidth() * 2.0f * (livingEntity.getWidth() * 2.0f) + livingEntity2.getWidth()));
    }

    public static boolean isNewTargetTooFar(LivingEntity source, LivingEntity target, double extraDistance) {
        Optional<LivingEntity> optional = source.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET);
        if (!optional.isPresent()) {
            return false;
        }
        double _snowman2 = source.squaredDistanceTo(optional.get().getPos());
        double _snowman3 = source.squaredDistanceTo(target.getPos());
        return _snowman3 > _snowman2 + extraDistance * extraDistance;
    }

    public static boolean isVisibleInMemory(LivingEntity source, LivingEntity target) {
        Brain<List<LivingEntity>> brain = source.getBrain();
        if (!brain.hasMemoryModule(MemoryModuleType.VISIBLE_MOBS)) {
            return false;
        }
        return brain.getOptionalMemory(MemoryModuleType.VISIBLE_MOBS).get().contains(target);
    }

    public static LivingEntity getCloserEntity(LivingEntity source, Optional<LivingEntity> first, LivingEntity second) {
        if (!first.isPresent()) {
            return second;
        }
        return LookTargetUtil.getCloserEntity(source, first.get(), second);
    }

    public static LivingEntity getCloserEntity(LivingEntity source, LivingEntity first, LivingEntity second) {
        Vec3d vec3d = first.getPos();
        _snowman = second.getPos();
        return source.squaredDistanceTo(vec3d) < source.squaredDistanceTo(_snowman) ? first : second;
    }

    public static Optional<LivingEntity> getEntity(LivingEntity entity, MemoryModuleType<UUID> uuidMemoryModule) {
        Optional<UUID> optional = entity.getBrain().getOptionalMemory(uuidMemoryModule);
        return optional.map(uUID -> (LivingEntity)((ServerWorld)livingEntity.world).getEntity((UUID)uUID));
    }

    public static Stream<VillagerEntity> streamSeenVillagers(VillagerEntity villager, Predicate<VillagerEntity> filter) {
        return villager.getBrain().getOptionalMemory(MemoryModuleType.MOBS).map(list -> list.stream().filter(livingEntity -> livingEntity instanceof VillagerEntity && livingEntity != villager).map(livingEntity -> (VillagerEntity)livingEntity).filter(LivingEntity::isAlive).filter(filter)).orElseGet(Stream::empty);
    }
}

