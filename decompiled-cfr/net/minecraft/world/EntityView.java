/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.world;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.Box;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

public interface EntityView {
    public List<Entity> getOtherEntities(@Nullable Entity var1, Box var2, @Nullable Predicate<? super Entity> var3);

    public <T extends Entity> List<T> getEntitiesByClass(Class<? extends T> var1, Box var2, @Nullable Predicate<? super T> var3);

    default public <T extends Entity> List<T> getEntitiesIncludingUngeneratedChunks(Class<? extends T> entityClass, Box box, @Nullable Predicate<? super T> predicate) {
        return this.getEntitiesByClass(entityClass, box, predicate);
    }

    public List<? extends PlayerEntity> getPlayers();

    default public List<Entity> getOtherEntities(@Nullable Entity except, Box box) {
        return this.getOtherEntities(except, box, EntityPredicates.EXCEPT_SPECTATOR);
    }

    default public boolean intersectsEntities(@Nullable Entity entity, VoxelShape shape) {
        if (shape.isEmpty()) {
            return true;
        }
        for (Entity entity2 : this.getOtherEntities(entity, shape.getBoundingBox())) {
            if (entity2.removed || !entity2.inanimate || entity != null && entity2.isConnectedThroughVehicle(entity) || !VoxelShapes.matchesAnywhere(shape, VoxelShapes.cuboid(entity2.getBoundingBox()), BooleanBiFunction.AND)) continue;
            return false;
        }
        return true;
    }

    default public <T extends Entity> List<T> getNonSpectatingEntities(Class<? extends T> entityClass, Box box) {
        return this.getEntitiesByClass(entityClass, box, EntityPredicates.EXCEPT_SPECTATOR);
    }

    default public <T extends Entity> List<T> getEntitiesIncludingUngeneratedChunks(Class<? extends T> entityClass, Box box) {
        return this.getEntitiesIncludingUngeneratedChunks(entityClass, box, EntityPredicates.EXCEPT_SPECTATOR);
    }

    default public Stream<VoxelShape> getEntityCollisions(@Nullable Entity entity2, Box box, Predicate<Entity> predicate) {
        if (box.getAverageSideLength() < 1.0E-7) {
            return Stream.empty();
        }
        Box box2 = box.expand(1.0E-7);
        return this.getOtherEntities(entity2, box2, predicate.and(entity -> entity.getBoundingBox().intersects(box2) && (entity2 == null ? entity.isCollidable() : entity2.collidesWith((Entity)entity)))).stream().map(Entity::getBoundingBox).map(VoxelShapes::cuboid);
    }

    @Nullable
    default public PlayerEntity getClosestPlayer(double x, double y, double z, double maxDistance, @Nullable Predicate<Entity> targetPredicate) {
        double d = -1.0;
        PlayerEntity _snowman2 = null;
        for (PlayerEntity playerEntity : this.getPlayers()) {
            if (targetPredicate != null && !targetPredicate.test(playerEntity)) continue;
            double d2 = playerEntity.squaredDistanceTo(x, y, z);
            if (!(maxDistance < 0.0) && !(d2 < maxDistance * maxDistance) || d != -1.0 && !(d2 < d)) continue;
            d = d2;
            _snowman2 = playerEntity;
        }
        return _snowman2;
    }

    @Nullable
    default public PlayerEntity getClosestPlayer(Entity entity, double maxDistance) {
        return this.getClosestPlayer(entity.getX(), entity.getY(), entity.getZ(), maxDistance, false);
    }

    @Nullable
    default public PlayerEntity getClosestPlayer(double x, double y, double z, double maxDistance, boolean ignoreCreative) {
        Predicate<Entity> predicate = ignoreCreative ? EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR : EntityPredicates.EXCEPT_SPECTATOR;
        return this.getClosestPlayer(x, y, z, maxDistance, predicate);
    }

    default public boolean isPlayerInRange(double x, double y, double z, double range) {
        for (PlayerEntity playerEntity : this.getPlayers()) {
            if (!EntityPredicates.EXCEPT_SPECTATOR.test(playerEntity) || !EntityPredicates.VALID_LIVING_ENTITY.test(playerEntity)) continue;
            double d = playerEntity.squaredDistanceTo(x, y, z);
            if (!(range < 0.0) && !(d < range * range)) continue;
            return true;
        }
        return false;
    }

    @Nullable
    default public PlayerEntity getClosestPlayer(TargetPredicate targetPredicate, LivingEntity entity) {
        return this.getClosestEntity(this.getPlayers(), targetPredicate, entity, entity.getX(), entity.getY(), entity.getZ());
    }

    @Nullable
    default public PlayerEntity getClosestPlayer(TargetPredicate targetPredicate, LivingEntity entity, double x, double y, double z) {
        return this.getClosestEntity(this.getPlayers(), targetPredicate, entity, x, y, z);
    }

    @Nullable
    default public PlayerEntity getClosestPlayer(TargetPredicate targetPredicate, double x, double y, double z) {
        return this.getClosestEntity(this.getPlayers(), targetPredicate, null, x, y, z);
    }

    @Nullable
    default public <T extends LivingEntity> T getClosestEntity(Class<? extends T> entityClass, TargetPredicate targetPredicate, @Nullable LivingEntity entity, double x, double y, double z, Box box) {
        return this.getClosestEntity(this.getEntitiesByClass(entityClass, box, null), targetPredicate, entity, x, y, z);
    }

    @Nullable
    default public <T extends LivingEntity> T getClosestEntityIncludingUngeneratedChunks(Class<? extends T> entityClass, TargetPredicate targetPredicate, @Nullable LivingEntity entity, double x, double y, double z, Box box) {
        return this.getClosestEntity(this.getEntitiesIncludingUngeneratedChunks(entityClass, box, null), targetPredicate, entity, x, y, z);
    }

    @Nullable
    default public <T extends LivingEntity> T getClosestEntity(List<? extends T> entityList, TargetPredicate targetPredicate, @Nullable LivingEntity entity, double x, double y, double z) {
        double d = -1.0;
        LivingEntity _snowman2 = null;
        for (LivingEntity livingEntity : entityList) {
            if (!targetPredicate.test(entity, livingEntity)) continue;
            double d2 = livingEntity.squaredDistanceTo(x, y, z);
            if (d != -1.0 && !(d2 < d)) continue;
            d = d2;
            _snowman2 = livingEntity;
        }
        return (T)_snowman2;
    }

    default public List<PlayerEntity> getPlayers(TargetPredicate targetPredicate, LivingEntity entity, Box box) {
        ArrayList arrayList = Lists.newArrayList();
        for (PlayerEntity playerEntity : this.getPlayers()) {
            if (!box.contains(playerEntity.getX(), playerEntity.getY(), playerEntity.getZ()) || !targetPredicate.test(entity, playerEntity)) continue;
            arrayList.add(playerEntity);
        }
        return arrayList;
    }

    default public <T extends LivingEntity> List<T> getTargets(Class<? extends T> entityClass, TargetPredicate targetPredicate, LivingEntity targetingEntity, Box box) {
        List<T> list = this.getEntitiesByClass(entityClass, box, null);
        ArrayList _snowman2 = Lists.newArrayList();
        for (LivingEntity livingEntity : list) {
            if (!targetPredicate.test(targetingEntity, livingEntity)) continue;
            _snowman2.add(livingEntity);
        }
        return _snowman2;
    }

    @Nullable
    default public PlayerEntity getPlayerByUuid(UUID uuid) {
        for (int i = 0; i < this.getPlayers().size(); ++i) {
            PlayerEntity playerEntity = this.getPlayers().get(i);
            if (!uuid.equals(playerEntity.getUuid())) continue;
            return playerEntity;
        }
        return null;
    }
}

