package net.minecraft.util.shape;

import java.util.BitSet;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.Direction;

public final class BitSetVoxelSet extends VoxelSet {
   private final BitSet storage;
   private int xMin;
   private int yMin;
   private int zMin;
   private int xMax;
   private int yMax;
   private int zMax;

   public BitSetVoxelSet(int _snowman, int _snowman, int _snowman) {
      this(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, 0, 0, 0);
   }

   public BitSetVoxelSet(int xMask, int yMask, int zMask, int xMin, int yMin, int zMin, int xMax, int yMax, int zMax) {
      super(xMask, yMask, zMask);
      this.storage = new BitSet(xMask * yMask * zMask);
      this.xMin = xMin;
      this.yMin = yMin;
      this.zMin = zMin;
      this.xMax = xMax;
      this.yMax = yMax;
      this.zMax = zMax;
   }

   public BitSetVoxelSet(VoxelSet other) {
      super(other.xSize, other.ySize, other.zSize);
      if (other instanceof BitSetVoxelSet) {
         this.storage = (BitSet)((BitSetVoxelSet)other).storage.clone();
      } else {
         this.storage = new BitSet(this.xSize * this.ySize * this.zSize);

         for (int _snowman = 0; _snowman < this.xSize; _snowman++) {
            for (int _snowmanx = 0; _snowmanx < this.ySize; _snowmanx++) {
               for (int _snowmanxx = 0; _snowmanxx < this.zSize; _snowmanxx++) {
                  if (other.contains(_snowman, _snowmanx, _snowmanxx)) {
                     this.storage.set(this.getIndex(_snowman, _snowmanx, _snowmanxx));
                  }
               }
            }
         }
      }

      this.xMin = other.getMin(Direction.Axis.X);
      this.yMin = other.getMin(Direction.Axis.Y);
      this.zMin = other.getMin(Direction.Axis.Z);
      this.xMax = other.getMax(Direction.Axis.X);
      this.yMax = other.getMax(Direction.Axis.Y);
      this.zMax = other.getMax(Direction.Axis.Z);
   }

   protected int getIndex(int x, int y, int z) {
      return (x * this.ySize + y) * this.zSize + z;
   }

   @Override
   public boolean contains(int x, int y, int z) {
      return this.storage.get(this.getIndex(x, y, z));
   }

   @Override
   public void set(int x, int y, int z, boolean resize, boolean included) {
      this.storage.set(this.getIndex(x, y, z), included);
      if (resize && included) {
         this.xMin = Math.min(this.xMin, x);
         this.yMin = Math.min(this.yMin, y);
         this.zMin = Math.min(this.zMin, z);
         this.xMax = Math.max(this.xMax, x + 1);
         this.yMax = Math.max(this.yMax, y + 1);
         this.zMax = Math.max(this.zMax, z + 1);
      }
   }

   @Override
   public boolean isEmpty() {
      return this.storage.isEmpty();
   }

   @Override
   public int getMin(Direction.Axis axis) {
      return axis.choose(this.xMin, this.yMin, this.zMin);
   }

   @Override
   public int getMax(Direction.Axis axis) {
      return axis.choose(this.xMax, this.yMax, this.zMax);
   }

   @Override
   protected boolean isColumnFull(int minZ, int maxZ, int x, int y) {
      if (x < 0 || y < 0 || minZ < 0) {
         return false;
      } else {
         return x < this.xSize && y < this.ySize && maxZ <= this.zSize
            ? this.storage.nextClearBit(this.getIndex(x, y, minZ)) >= this.getIndex(x, y, maxZ)
            : false;
      }
   }

   @Override
   protected void setColumn(int minZ, int maxZ, int x, int y, boolean included) {
      this.storage.set(this.getIndex(x, y, minZ), this.getIndex(x, y, maxZ), included);
   }

   static BitSetVoxelSet combine(VoxelSet first, VoxelSet second, PairList xPoints, PairList yPoints, PairList zPoints, BooleanBiFunction function) {
      BitSetVoxelSet _snowman = new BitSetVoxelSet(xPoints.getPairs().size() - 1, yPoints.getPairs().size() - 1, zPoints.getPairs().size() - 1);
      int[] _snowmanx = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE};
      xPoints.forEachPair(
         (_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx) -> {
            boolean[] _snowmanxx = new boolean[]{false};
            boolean _snowmanx = yPoints.forEachPair(
               (_snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx) -> {
                  boolean[] _snowmanxxxxxxxxxxxxx = new boolean[]{false};
                  boolean _snowmanxxxxxxxxxxxxxx = zPoints.forEachPair(
                     (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx) -> {
                        boolean _snowmanxxxxxxxxxxxxxxx = function.apply(
                           first.inBoundsAndContains(_snowmanxxxxxxx, _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx),
                           second.inBoundsAndContains(_snowmanxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
                        );
                        if (_snowmanxxxxxxxxxxxxxxx) {
                           _snowman.storage.set(_snowman.getIndex(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx));
                           _snowman[2] = Math.min(_snowman[2], _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx);
                           _snowman[5] = Math.max(_snowman[5], _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx);
                           _snowmanxxxxxxxxxxxx[0] = true;
                        }

                        return true;
                     }
                  );
                  if (_snowmanxxxxxxxxxxxxx[0]) {
                     _snowman[1] = Math.min(_snowman[1], _snowmanxxxxxxxxxxxxx);
                     _snowman[4] = Math.max(_snowman[4], _snowmanxxxxxxxxxxxxx);
                     _snowman[0] = true;
                  }

                  return _snowmanxxxxxxxxxxxxxx;
               }
            );
            if (_snowmanxx[0]) {
               _snowman[0] = Math.min(_snowman[0], _snowmanxxxxxxxxx);
               _snowman[3] = Math.max(_snowman[3], _snowmanxxxxxxxxx);
            }

            return _snowmanx;
         }
      );
      _snowman.xMin = _snowmanx[0];
      _snowman.yMin = _snowmanx[1];
      _snowman.zMin = _snowmanx[2];
      _snowman.xMax = _snowmanx[3] + 1;
      _snowman.yMax = _snowmanx[4] + 1;
      _snowman.zMax = _snowmanx[5] + 1;
      return _snowman;
   }
}
