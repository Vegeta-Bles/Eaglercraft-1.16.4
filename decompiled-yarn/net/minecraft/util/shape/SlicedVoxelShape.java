package net.minecraft.util.shape;

import it.unimi.dsi.fastutil.doubles.DoubleList;
import net.minecraft.util.math.Direction;

public class SlicedVoxelShape extends VoxelShape {
   private final VoxelShape shape;
   private final Direction.Axis axis;
   private static final DoubleList POINTS = new FractionalDoubleList(1);

   public SlicedVoxelShape(VoxelShape shape, Direction.Axis axis, int sliceWidth) {
      super(createVoxelSet(shape.voxels, axis, sliceWidth));
      this.shape = shape;
      this.axis = axis;
   }

   private static VoxelSet createVoxelSet(VoxelSet voxelSet, Direction.Axis _snowman, int sliceWidth) {
      return new CroppedVoxelSet(
         voxelSet,
         _snowman.choose(sliceWidth, 0, 0),
         _snowman.choose(0, sliceWidth, 0),
         _snowman.choose(0, 0, sliceWidth),
         _snowman.choose(sliceWidth + 1, voxelSet.xSize, voxelSet.xSize),
         _snowman.choose(voxelSet.ySize, sliceWidth + 1, voxelSet.ySize),
         _snowman.choose(voxelSet.zSize, voxelSet.zSize, sliceWidth + 1)
      );
   }

   @Override
   protected DoubleList getPointPositions(Direction.Axis axis) {
      return axis == this.axis ? POINTS : this.shape.getPointPositions(axis);
   }
}
