package net.minecraft.world;

import java.util.Objects;
import java.util.Spliterators.AbstractSpliterator;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.util.CuboidBlockIterator;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.border.WorldBorder;

public class BlockCollisionSpliterator extends AbstractSpliterator<VoxelShape> {
   @Nullable
   private final Entity entity;
   private final Box box;
   private final ShapeContext context;
   private final CuboidBlockIterator blockIterator;
   private final BlockPos.Mutable pos;
   private final VoxelShape boxShape;
   private final CollisionView world;
   private boolean checkEntity;
   private final BiPredicate<BlockState, BlockPos> blockPredicate;

   public BlockCollisionSpliterator(CollisionView world, @Nullable Entity entity, Box box) {
      this(world, entity, box, (_snowman, _snowmanx) -> true);
   }

   public BlockCollisionSpliterator(CollisionView _snowman, @Nullable Entity _snowman, Box _snowman, BiPredicate<BlockState, BlockPos> blockPredicate) {
      super(Long.MAX_VALUE, 1280);
      this.context = _snowman == null ? ShapeContext.absent() : ShapeContext.of(_snowman);
      this.pos = new BlockPos.Mutable();
      this.boxShape = VoxelShapes.cuboid(_snowman);
      this.world = _snowman;
      this.checkEntity = _snowman != null;
      this.entity = _snowman;
      this.box = _snowman;
      this.blockPredicate = blockPredicate;
      int _snowmanxxx = MathHelper.floor(_snowman.minX - 1.0E-7) - 1;
      int _snowmanxxxx = MathHelper.floor(_snowman.maxX + 1.0E-7) + 1;
      int _snowmanxxxxx = MathHelper.floor(_snowman.minY - 1.0E-7) - 1;
      int _snowmanxxxxxx = MathHelper.floor(_snowman.maxY + 1.0E-7) + 1;
      int _snowmanxxxxxxx = MathHelper.floor(_snowman.minZ - 1.0E-7) - 1;
      int _snowmanxxxxxxxx = MathHelper.floor(_snowman.maxZ + 1.0E-7) + 1;
      this.blockIterator = new CuboidBlockIterator(_snowmanxxx, _snowmanxxxxx, _snowmanxxxxxxx, _snowmanxxxx, _snowmanxxxxxx, _snowmanxxxxxxxx);
   }

   @Override
   public boolean tryAdvance(Consumer<? super VoxelShape> _snowman) {
      return this.checkEntity && this.offerEntityShape(_snowman) || this.offerBlockShape(_snowman);
   }

   boolean offerBlockShape(Consumer<? super VoxelShape> _snowman) {
      while (this.blockIterator.step()) {
         int _snowmanx = this.blockIterator.getX();
         int _snowmanxx = this.blockIterator.getY();
         int _snowmanxxx = this.blockIterator.getZ();
         int _snowmanxxxx = this.blockIterator.getEdgeCoordinatesCount();
         if (_snowmanxxxx != 3) {
            BlockView _snowmanxxxxx = this.getChunk(_snowmanx, _snowmanxxx);
            if (_snowmanxxxxx != null) {
               this.pos.set(_snowmanx, _snowmanxx, _snowmanxxx);
               BlockState _snowmanxxxxxx = _snowmanxxxxx.getBlockState(this.pos);
               if (this.blockPredicate.test(_snowmanxxxxxx, this.pos) && (_snowmanxxxx != 1 || _snowmanxxxxxx.exceedsCube()) && (_snowmanxxxx != 2 || _snowmanxxxxxx.isOf(Blocks.MOVING_PISTON))) {
                  VoxelShape _snowmanxxxxxxx = _snowmanxxxxxx.getCollisionShape(this.world, this.pos, this.context);
                  if (_snowmanxxxxxxx == VoxelShapes.fullCube()) {
                     if (this.box.intersects((double)_snowmanx, (double)_snowmanxx, (double)_snowmanxxx, (double)_snowmanx + 1.0, (double)_snowmanxx + 1.0, (double)_snowmanxxx + 1.0)) {
                        _snowman.accept(_snowmanxxxxxxx.offset((double)_snowmanx, (double)_snowmanxx, (double)_snowmanxxx));
                        return true;
                     }
                  } else {
                     VoxelShape _snowmanxxxxxxxx = _snowmanxxxxxxx.offset((double)_snowmanx, (double)_snowmanxx, (double)_snowmanxxx);
                     if (VoxelShapes.matchesAnywhere(_snowmanxxxxxxxx, this.boxShape, BooleanBiFunction.AND)) {
                        _snowman.accept(_snowmanxxxxxxxx);
                        return true;
                     }
                  }
               }
            }
         }
      }

      return false;
   }

   @Nullable
   private BlockView getChunk(int x, int z) {
      int _snowman = x >> 4;
      int _snowmanx = z >> 4;
      return this.world.getExistingChunk(_snowman, _snowmanx);
   }

   boolean offerEntityShape(Consumer<? super VoxelShape> _snowman) {
      Objects.requireNonNull(this.entity);
      this.checkEntity = false;
      WorldBorder _snowmanx = this.world.getWorldBorder();
      Box _snowmanxx = this.entity.getBoundingBox();
      if (!isInWorldBorder(_snowmanx, _snowmanxx)) {
         VoxelShape _snowmanxxx = _snowmanx.asVoxelShape();
         if (!method_30131(_snowmanxxx, _snowmanxx) && method_30130(_snowmanxxx, _snowmanxx)) {
            _snowman.accept(_snowmanxxx);
            return true;
         }
      }

      return false;
   }

   private static boolean method_30130(VoxelShape _snowman, Box _snowman) {
      return VoxelShapes.matchesAnywhere(_snowman, VoxelShapes.cuboid(_snowman.expand(1.0E-7)), BooleanBiFunction.AND);
   }

   private static boolean method_30131(VoxelShape _snowman, Box _snowman) {
      return VoxelShapes.matchesAnywhere(_snowman, VoxelShapes.cuboid(_snowman.contract(1.0E-7)), BooleanBiFunction.AND);
   }

   public static boolean isInWorldBorder(WorldBorder border, Box box) {
      double _snowman = (double)MathHelper.floor(border.getBoundWest());
      double _snowmanx = (double)MathHelper.floor(border.getBoundNorth());
      double _snowmanxx = (double)MathHelper.ceil(border.getBoundEast());
      double _snowmanxxx = (double)MathHelper.ceil(border.getBoundSouth());
      return box.minX > _snowman && box.minX < _snowmanxx && box.minZ > _snowmanx && box.minZ < _snowmanxxx && box.maxX > _snowman && box.maxX < _snowmanxx && box.maxZ > _snowmanx && box.maxZ < _snowmanxxx;
   }
}
