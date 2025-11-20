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
         for (Entity _snowman : this.getOtherEntities(entity, shape.getBoundingBox())) {
            if (!_snowman.removed
               && _snowman.inanimate
               && (entity == null || !_snowman.isConnectedThroughVehicle(entity))
               && VoxelShapes.matchesAnywhere(shape, VoxelShapes.cuboid(_snowman.getBoundingBox()), BooleanBiFunction.AND)) {
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
         Box _snowman = box.expand(1.0E-7);
         return this.getOtherEntities(
               entity, _snowman, predicate.and(_snowmanx -> _snowmanx.getBoundingBox().intersects(_snowman) && (entity == null ? _snowmanx.isCollidable() : entity.collidesWith(_snowmanx)))
            )
            .stream()
            .map(Entity::getBoundingBox)
            .map(VoxelShapes::cuboid);
      }
   }

   @Nullable
   default PlayerEntity getClosestPlayer(double x, double y, double z, double maxDistance, @Nullable Predicate<Entity> targetPredicate) {
      double _snowman = -1.0;
      PlayerEntity _snowmanx = null;

      for (PlayerEntity _snowmanxx : this.getPlayers()) {
         if (targetPredicate == null || targetPredicate.test(_snowmanxx)) {
            double _snowmanxxx = _snowmanxx.squaredDistanceTo(x, y, z);
            if ((maxDistance < 0.0 || _snowmanxxx < maxDistance * maxDistance) && (_snowman == -1.0 || _snowmanxxx < _snowman)) {
               _snowman = _snowmanxxx;
               _snowmanx = _snowmanxx;
            }
         }
      }

      return _snowmanx;
   }

   @Nullable
   default PlayerEntity getClosestPlayer(Entity entity, double maxDistance) {
      return this.getClosestPlayer(entity.getX(), entity.getY(), entity.getZ(), maxDistance, false);
   }

   @Nullable
   default PlayerEntity getClosestPlayer(double x, double y, double z, double maxDistance, boolean ignoreCreative) {
      Predicate<Entity> _snowman = ignoreCreative ? EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR : EntityPredicates.EXCEPT_SPECTATOR;
      return this.getClosestPlayer(x, y, z, maxDistance, _snowman);
   }

   default boolean isPlayerInRange(double x, double y, double z, double range) {
      for (PlayerEntity _snowman : this.getPlayers()) {
         if (EntityPredicates.EXCEPT_SPECTATOR.test(_snowman) && EntityPredicates.VALID_LIVING_ENTITY.test(_snowman)) {
            double _snowmanx = _snowman.squaredDistanceTo(x, y, z);
            if (range < 0.0 || _snowmanx < range * range) {
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
      double _snowman = -1.0;
      T _snowmanx = null;

      for (T _snowmanxx : entityList) {
         if (targetPredicate.test(entity, _snowmanxx)) {
            double _snowmanxxx = _snowmanxx.squaredDistanceTo(x, y, z);
            if (_snowman == -1.0 || _snowmanxxx < _snowman) {
               _snowman = _snowmanxxx;
               _snowmanx = _snowmanxx;
            }
         }
      }

      return _snowmanx;
   }

   default List<PlayerEntity> getPlayers(TargetPredicate targetPredicate, LivingEntity entity, Box box) {
      List<PlayerEntity> _snowman = Lists.newArrayList();

      for (PlayerEntity _snowmanx : this.getPlayers()) {
         if (box.contains(_snowmanx.getX(), _snowmanx.getY(), _snowmanx.getZ()) && targetPredicate.test(entity, _snowmanx)) {
            _snowman.add(_snowmanx);
         }
      }

      return _snowman;
   }

   default <T extends LivingEntity> List<T> getTargets(Class<? extends T> entityClass, TargetPredicate targetPredicate, LivingEntity targetingEntity, Box box) {
      List<T> _snowman = this.getEntitiesByClass(entityClass, box, null);
      List<T> _snowmanx = Lists.newArrayList();

      for (T _snowmanxx : _snowman) {
         if (targetPredicate.test(targetingEntity, _snowmanxx)) {
            _snowmanx.add(_snowmanxx);
         }
      }

      return _snowmanx;
   }

   @Nullable
   default PlayerEntity getPlayerByUuid(UUID uuid) {
      for (int _snowman = 0; _snowman < this.getPlayers().size(); _snowman++) {
         PlayerEntity _snowmanx = this.getPlayers().get(_snowman);
         if (uuid.equals(_snowmanx.getUuid())) {
            return _snowmanx;
         }
      }

      return null;
   }
}
