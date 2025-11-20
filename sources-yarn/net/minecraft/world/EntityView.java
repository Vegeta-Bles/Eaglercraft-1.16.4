package net.minecraft.world;

import com.google.common.collect.Lists;
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
   List<Entity> getOtherEntities(@Nullable Entity except, Box box, @Nullable Predicate<? super Entity> predicate);

   <T extends Entity> List<T> getEntitiesByClass(Class<? extends T> entityClass, Box box, @Nullable Predicate<? super T> predicate);

   default <T extends Entity> List<T> getEntitiesIncludingUngeneratedChunks(Class<? extends T> entityClass, Box box, @Nullable Predicate<? super T> predicate) {
      return this.getEntitiesByClass(entityClass, box, predicate);
   }

   List<? extends PlayerEntity> getPlayers();

   default List<Entity> getOtherEntities(@Nullable Entity except, Box box) {
      return this.getOtherEntities(except, box, EntityPredicates.EXCEPT_SPECTATOR);
   }

   default boolean intersectsEntities(@Nullable Entity entity, VoxelShape shape) {
      if (shape.isEmpty()) {
         return true;
      } else {
         for (Entity lv : this.getOtherEntities(entity, shape.getBoundingBox())) {
            if (!lv.removed
               && lv.inanimate
               && (entity == null || !lv.isConnectedThroughVehicle(entity))
               && VoxelShapes.matchesAnywhere(shape, VoxelShapes.cuboid(lv.getBoundingBox()), BooleanBiFunction.AND)) {
               return false;
            }
         }

         return true;
      }
   }

   default <T extends Entity> List<T> getNonSpectatingEntities(Class<? extends T> entityClass, Box box) {
      return this.getEntitiesByClass(entityClass, box, EntityPredicates.EXCEPT_SPECTATOR);
   }

   default <T extends Entity> List<T> getEntitiesIncludingUngeneratedChunks(Class<? extends T> entityClass, Box box) {
      return this.getEntitiesIncludingUngeneratedChunks(entityClass, box, EntityPredicates.EXCEPT_SPECTATOR);
   }

   default Stream<VoxelShape> getEntityCollisions(@Nullable Entity entity, Box box, Predicate<Entity> predicate) {
      if (box.getAverageSideLength() < 1.0E-7) {
         return Stream.empty();
      } else {
         Box lv = box.expand(1.0E-7);
         return this.getOtherEntities(
               entity, lv, predicate.and(arg3 -> arg3.getBoundingBox().intersects(lv) && (entity == null ? arg3.isCollidable() : entity.collidesWith(arg3)))
            )
            .stream()
            .map(Entity::getBoundingBox)
            .map(VoxelShapes::cuboid);
      }
   }

   @Nullable
   default PlayerEntity getClosestPlayer(double x, double y, double z, double maxDistance, @Nullable Predicate<Entity> targetPredicate) {
      double h = -1.0;
      PlayerEntity lv = null;

      for (PlayerEntity lv2 : this.getPlayers()) {
         if (targetPredicate == null || targetPredicate.test(lv2)) {
            double i = lv2.squaredDistanceTo(x, y, z);
            if ((maxDistance < 0.0 || i < maxDistance * maxDistance) && (h == -1.0 || i < h)) {
               h = i;
               lv = lv2;
            }
         }
      }

      return lv;
   }

   @Nullable
   default PlayerEntity getClosestPlayer(Entity entity, double maxDistance) {
      return this.getClosestPlayer(entity.getX(), entity.getY(), entity.getZ(), maxDistance, false);
   }

   @Nullable
   default PlayerEntity getClosestPlayer(double x, double y, double z, double maxDistance, boolean ignoreCreative) {
      Predicate<Entity> predicate = ignoreCreative ? EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR : EntityPredicates.EXCEPT_SPECTATOR;
      return this.getClosestPlayer(x, y, z, maxDistance, predicate);
   }

   default boolean isPlayerInRange(double x, double y, double z, double range) {
      for (PlayerEntity lv : this.getPlayers()) {
         if (EntityPredicates.EXCEPT_SPECTATOR.test(lv) && EntityPredicates.VALID_LIVING_ENTITY.test(lv)) {
            double h = lv.squaredDistanceTo(x, y, z);
            if (range < 0.0 || h < range * range) {
               return true;
            }
         }
      }

      return false;
   }

   @Nullable
   default PlayerEntity getClosestPlayer(TargetPredicate targetPredicate, LivingEntity entity) {
      return this.getClosestEntity(this.getPlayers(), targetPredicate, entity, entity.getX(), entity.getY(), entity.getZ());
   }

   @Nullable
   default PlayerEntity getClosestPlayer(TargetPredicate targetPredicate, LivingEntity entity, double x, double y, double z) {
      return this.getClosestEntity(this.getPlayers(), targetPredicate, entity, x, y, z);
   }

   @Nullable
   default PlayerEntity getClosestPlayer(TargetPredicate targetPredicate, double x, double y, double z) {
      return this.getClosestEntity(this.getPlayers(), targetPredicate, null, x, y, z);
   }

   @Nullable
   default <T extends LivingEntity> T getClosestEntity(
      Class<? extends T> entityClass, TargetPredicate targetPredicate, @Nullable LivingEntity entity, double x, double y, double z, Box box
   ) {
      return this.getClosestEntity(this.getEntitiesByClass(entityClass, box, null), targetPredicate, entity, x, y, z);
   }

   @Nullable
   default <T extends LivingEntity> T getClosestEntityIncludingUngeneratedChunks(
      Class<? extends T> entityClass, TargetPredicate targetPredicate, @Nullable LivingEntity entity, double x, double y, double z, Box box
   ) {
      return this.getClosestEntity(this.getEntitiesIncludingUngeneratedChunks(entityClass, box, null), targetPredicate, entity, x, y, z);
   }

   @Nullable
   default <T extends LivingEntity> T getClosestEntity(
      List<? extends T> entityList, TargetPredicate targetPredicate, @Nullable LivingEntity entity, double x, double y, double z
   ) {
      double g = -1.0;
      T lv = null;

      for (T lv2 : entityList) {
         if (targetPredicate.test(entity, lv2)) {
            double h = lv2.squaredDistanceTo(x, y, z);
            if (g == -1.0 || h < g) {
               g = h;
               lv = lv2;
            }
         }
      }

      return lv;
   }

   default List<PlayerEntity> getPlayers(TargetPredicate targetPredicate, LivingEntity entity, Box box) {
      List<PlayerEntity> list = Lists.newArrayList();

      for (PlayerEntity lv : this.getPlayers()) {
         if (box.contains(lv.getX(), lv.getY(), lv.getZ()) && targetPredicate.test(entity, lv)) {
            list.add(lv);
         }
      }

      return list;
   }

   default <T extends LivingEntity> List<T> getTargets(Class<? extends T> entityClass, TargetPredicate targetPredicate, LivingEntity targetingEntity, Box box) {
      List<T> list = this.getEntitiesByClass(entityClass, box, null);
      List<T> list2 = Lists.newArrayList();

      for (T lv : list) {
         if (targetPredicate.test(targetingEntity, lv)) {
            list2.add(lv);
         }
      }

      return list2;
   }

   @Nullable
   default PlayerEntity getPlayerByUuid(UUID uuid) {
      for (int i = 0; i < this.getPlayers().size(); i++) {
         PlayerEntity lv = this.getPlayers().get(i);
         if (uuid.equals(lv.getUuid())) {
            return lv;
         }
      }

      return null;
   }
}
