package net.minecraft.util.shape;

import it.unimi.dsi.fastutil.doubles.DoubleList;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;

public final class SimpleVoxelShape extends VoxelShape {
   protected SimpleVoxelShape(VoxelSet arg) {
      super(arg);
   }

   @Override
   protected DoubleList getPointPositions(Direction.Axis axis) {
      return new FractionalDoubleList(this.voxels.getSize(axis));
   }

   @Override
   protected int getCoordIndex(Direction.Axis arg, double coord) {
      int i = this.voxels.getSize(arg);
      return MathHelper.clamp(MathHelper.floor(coord * (double)i), -1, i);
   }
}
