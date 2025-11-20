package net.minecraft.util.shape;

import it.unimi.dsi.fastutil.doubles.DoubleList;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;

public final class SimpleVoxelShape extends VoxelShape {
   protected SimpleVoxelShape(VoxelSet _snowman) {
      super(_snowman);
   }

   @Override
   protected DoubleList getPointPositions(Direction.Axis axis) {
      return new FractionalDoubleList(this.voxels.getSize(axis));
   }

   @Override
   protected int getCoordIndex(Direction.Axis _snowman, double coord) {
      int _snowmanx = this.voxels.getSize(_snowman);
      return MathHelper.clamp(MathHelper.floor(coord * (double)_snowmanx), -1, _snowmanx);
   }
}
