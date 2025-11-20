package net.minecraft.world;

import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.border.WorldBorder;

public interface CollisionView extends BlockView {
   WorldBorder getWorldBorder();

   @Nullable
   BlockView getExistingChunk(int chunkX, int chunkZ);

   default boolean intersectsEntities(@Nullable Entity except, VoxelShape shape) {
      return true;
   }

   default boolean canPlace(BlockState state, BlockPos pos, ShapeContext context) {
      VoxelShape lv = state.getCollisionShape(this, pos, context);
      return lv.isEmpty() || this.intersectsEntities(null, lv.offset((double)pos.getX(), (double)pos.getY(), (double)pos.getZ()));
   }

   default boolean intersectsEntities(Entity entity) {
      return this.intersectsEntities(entity, VoxelShapes.cuboid(entity.getBoundingBox()));
   }

   default boolean isSpaceEmpty(Box box) {
      return this.isSpaceEmpty(null, box, arg -> true);
   }

   default boolean isSpaceEmpty(Entity entity) {
      return this.isSpaceEmpty(entity, entity.getBoundingBox(), arg -> true);
   }

   default boolean isSpaceEmpty(Entity entity, Box box) {
      return this.isSpaceEmpty(entity, box, arg -> true);
   }

   default boolean isSpaceEmpty(@Nullable Entity arg, Box arg2, Predicate<Entity> predicate) {
      return this.getCollisions(arg, arg2, predicate).allMatch(VoxelShape::isEmpty);
   }

   Stream<VoxelShape> getEntityCollisions(@Nullable Entity entity, Box box, Predicate<Entity> predicate);

   default Stream<VoxelShape> getCollisions(@Nullable Entity arg, Box arg2, Predicate<Entity> predicate) {
      return Stream.concat(this.getBlockCollisions(arg, arg2), this.getEntityCollisions(arg, arg2, predicate));
   }

   default Stream<VoxelShape> getBlockCollisions(@Nullable Entity entity, Box box) {
      return StreamSupport.stream(new BlockCollisionSpliterator(this, entity, box), false);
   }

   @Environment(EnvType.CLIENT)
   default boolean isBlockSpaceEmpty(@Nullable Entity arg, Box arg2, BiPredicate<BlockState, BlockPos> biPredicate) {
      return this.getBlockCollisions(arg, arg2, biPredicate).allMatch(VoxelShape::isEmpty);
   }

   default Stream<VoxelShape> getBlockCollisions(@Nullable Entity arg, Box arg2, BiPredicate<BlockState, BlockPos> biPredicate) {
      return StreamSupport.stream(new BlockCollisionSpliterator(this, arg, arg2, biPredicate), false);
   }
}
