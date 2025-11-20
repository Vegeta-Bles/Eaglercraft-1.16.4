package net.minecraft.world;

import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
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
      VoxelShape _snowman = state.getCollisionShape(this, pos, context);
      return _snowman.isEmpty() || this.intersectsEntities(null, _snowman.offset((double)pos.getX(), (double)pos.getY(), (double)pos.getZ()));
   }

   default boolean intersectsEntities(Entity entity) {
      return this.intersectsEntities(entity, VoxelShapes.cuboid(entity.getBoundingBox()));
   }

   default boolean isSpaceEmpty(Box box) {
      return this.isSpaceEmpty(null, box, _snowman -> true);
   }

   default boolean isSpaceEmpty(Entity entity) {
      return this.isSpaceEmpty(entity, entity.getBoundingBox(), _snowman -> true);
   }

   default boolean isSpaceEmpty(Entity entity, Box box) {
      return this.isSpaceEmpty(entity, box, _snowman -> true);
   }

   default boolean isSpaceEmpty(@Nullable Entity _snowman, Box _snowman, Predicate<Entity> _snowman) {
      return this.getCollisions(_snowman, _snowman, _snowman).allMatch(VoxelShape::isEmpty);
   }

   Stream<VoxelShape> getEntityCollisions(@Nullable Entity var1, Box var2, Predicate<Entity> var3);

   default Stream<VoxelShape> getCollisions(@Nullable Entity _snowman, Box _snowman, Predicate<Entity> _snowman) {
      return Stream.concat(this.getBlockCollisions(_snowman, _snowman), this.getEntityCollisions(_snowman, _snowman, _snowman));
   }

   default Stream<VoxelShape> getBlockCollisions(@Nullable Entity entity, Box box) {
      return StreamSupport.stream(new BlockCollisionSpliterator(this, entity, box), false);
   }

   default boolean isBlockSpaceEmpty(@Nullable Entity _snowman, Box _snowman, BiPredicate<BlockState, BlockPos> _snowman) {
      return this.getBlockCollisions(_snowman, _snowman, _snowman).allMatch(VoxelShape::isEmpty);
   }

   default Stream<VoxelShape> getBlockCollisions(@Nullable Entity _snowman, Box _snowman, BiPredicate<BlockState, BlockPos> _snowman) {
      return StreamSupport.stream(new BlockCollisionSpliterator(this, _snowman, _snowman, _snowman), false);
   }
}
