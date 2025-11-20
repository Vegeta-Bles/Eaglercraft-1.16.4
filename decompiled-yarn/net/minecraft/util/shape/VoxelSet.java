package net.minecraft.util.shape;

import net.minecraft.util.math.AxisCycleDirection;
import net.minecraft.util.math.Direction;

public abstract class VoxelSet {
   private static final Direction.Axis[] AXES = Direction.Axis.values();
   protected final int xSize;
   protected final int ySize;
   protected final int zSize;

   protected VoxelSet(int xSize, int ySize, int zSize) {
      this.xSize = xSize;
      this.ySize = ySize;
      this.zSize = zSize;
   }

   public boolean inBoundsAndContains(AxisCycleDirection cycle, int x, int y, int z) {
      return this.inBoundsAndContains(cycle.choose(x, y, z, Direction.Axis.X), cycle.choose(x, y, z, Direction.Axis.Y), cycle.choose(x, y, z, Direction.Axis.Z));
   }

   public boolean inBoundsAndContains(int x, int y, int z) {
      if (x < 0 || y < 0 || z < 0) {
         return false;
      } else {
         return x < this.xSize && y < this.ySize && z < this.zSize ? this.contains(x, y, z) : false;
      }
   }

   public boolean contains(AxisCycleDirection cycle, int x, int y, int z) {
      return this.contains(cycle.choose(x, y, z, Direction.Axis.X), cycle.choose(x, y, z, Direction.Axis.Y), cycle.choose(x, y, z, Direction.Axis.Z));
   }

   public abstract boolean contains(int x, int y, int z);

   public abstract void set(int x, int y, int z, boolean resize, boolean included);

   public boolean isEmpty() {
      for (Direction.Axis _snowman : AXES) {
         if (this.getMin(_snowman) >= this.getMax(_snowman)) {
            return true;
         }
      }

      return false;
   }

   public abstract int getMin(Direction.Axis axis);

   public abstract int getMax(Direction.Axis axis);

   public int getEndingAxisCoord(Direction.Axis _snowman, int from, int to) {
      if (from >= 0 && to >= 0) {
         Direction.Axis _snowmanx = AxisCycleDirection.FORWARD.cycle(_snowman);
         Direction.Axis _snowmanxx = AxisCycleDirection.BACKWARD.cycle(_snowman);
         if (from < this.getSize(_snowmanx) && to < this.getSize(_snowmanxx)) {
            int _snowmanxxx = this.getSize(_snowman);
            AxisCycleDirection _snowmanxxxx = AxisCycleDirection.between(Direction.Axis.X, _snowman);

            for (int _snowmanxxxxx = _snowmanxxx - 1; _snowmanxxxxx >= 0; _snowmanxxxxx--) {
               if (this.contains(_snowmanxxxx, _snowmanxxxxx, from, to)) {
                  return _snowmanxxxxx + 1;
               }
            }

            return 0;
         } else {
            return 0;
         }
      } else {
         return 0;
      }
   }

   public int getSize(Direction.Axis axis) {
      return axis.choose(this.xSize, this.ySize, this.zSize);
   }

   public int getXSize() {
      return this.getSize(Direction.Axis.X);
   }

   public int getYSize() {
      return this.getSize(Direction.Axis.Y);
   }

   public int getZSize() {
      return this.getSize(Direction.Axis.Z);
   }

   public void forEachEdge(VoxelSet.PositionBiConsumer _snowman, boolean _snowman) {
      this.forEachEdge(_snowman, AxisCycleDirection.NONE, _snowman);
      this.forEachEdge(_snowman, AxisCycleDirection.FORWARD, _snowman);
      this.forEachEdge(_snowman, AxisCycleDirection.BACKWARD, _snowman);
   }

   private void forEachEdge(VoxelSet.PositionBiConsumer _snowman, AxisCycleDirection direction, boolean _snowman) {
      AxisCycleDirection _snowmanxx = direction.opposite();
      int _snowmanxxx = this.getSize(_snowmanxx.cycle(Direction.Axis.X));
      int _snowmanxxxx = this.getSize(_snowmanxx.cycle(Direction.Axis.Y));
      int _snowmanxxxxx = this.getSize(_snowmanxx.cycle(Direction.Axis.Z));

      for (int _snowmanxxxxxx = 0; _snowmanxxxxxx <= _snowmanxxx; _snowmanxxxxxx++) {
         for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx <= _snowmanxxxx; _snowmanxxxxxxx++) {
            int _snowmanxxxxxxxx = -1;

            for (int _snowmanxxxxxxxxx = 0; _snowmanxxxxxxxxx <= _snowmanxxxxx; _snowmanxxxxxxxxx++) {
               int _snowmanxxxxxxxxxx = 0;
               int _snowmanxxxxxxxxxxx = 0;

               for (int _snowmanxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxx <= 1; _snowmanxxxxxxxxxxxx++) {
                  for (int _snowmanxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxx <= 1; _snowmanxxxxxxxxxxxxx++) {
                     if (this.inBoundsAndContains(_snowmanxx, _snowmanxxxxxx + _snowmanxxxxxxxxxxxx - 1, _snowmanxxxxxxx + _snowmanxxxxxxxxxxxxx - 1, _snowmanxxxxxxxxx)) {
                        _snowmanxxxxxxxxxx++;
                        _snowmanxxxxxxxxxxx ^= _snowmanxxxxxxxxxxxx ^ _snowmanxxxxxxxxxxxxx;
                     }
                  }
               }

               if (_snowmanxxxxxxxxxx == 1 || _snowmanxxxxxxxxxx == 3 || _snowmanxxxxxxxxxx == 2 && (_snowmanxxxxxxxxxxx & 1) == 0) {
                  if (_snowman) {
                     if (_snowmanxxxxxxxx == -1) {
                        _snowmanxxxxxxxx = _snowmanxxxxxxxxx;
                     }
                  } else {
                     _snowman.consume(
                        _snowmanxx.choose(_snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxxx, Direction.Axis.X),
                        _snowmanxx.choose(_snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxxx, Direction.Axis.Y),
                        _snowmanxx.choose(_snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxxx, Direction.Axis.Z),
                        _snowmanxx.choose(_snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxxx + 1, Direction.Axis.X),
                        _snowmanxx.choose(_snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxxx + 1, Direction.Axis.Y),
                        _snowmanxx.choose(_snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxxx + 1, Direction.Axis.Z)
                     );
                  }
               } else if (_snowmanxxxxxxxx != -1) {
                  _snowman.consume(
                     _snowmanxx.choose(_snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx, Direction.Axis.X),
                     _snowmanxx.choose(_snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx, Direction.Axis.Y),
                     _snowmanxx.choose(_snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx, Direction.Axis.Z),
                     _snowmanxx.choose(_snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxxx, Direction.Axis.X),
                     _snowmanxx.choose(_snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxxx, Direction.Axis.Y),
                     _snowmanxx.choose(_snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxxx, Direction.Axis.Z)
                  );
                  _snowmanxxxxxxxx = -1;
               }
            }
         }
      }
   }

   protected boolean isColumnFull(int minZ, int maxZ, int x, int y) {
      for (int _snowman = minZ; _snowman < maxZ; _snowman++) {
         if (!this.inBoundsAndContains(x, y, _snowman)) {
            return false;
         }
      }

      return true;
   }

   protected void setColumn(int minZ, int maxZ, int x, int y, boolean included) {
      for (int _snowman = minZ; _snowman < maxZ; _snowman++) {
         this.set(x, y, _snowman, false, included);
      }
   }

   protected boolean isRectangleFull(int minX, int maxX, int minZ, int maxZ, int y) {
      for (int _snowman = minX; _snowman < maxX; _snowman++) {
         if (!this.isColumnFull(minZ, maxZ, _snowman, y)) {
            return false;
         }
      }

      return true;
   }

   public void forEachBox(VoxelSet.PositionBiConsumer consumer, boolean largest) {
      VoxelSet _snowman = new BitSetVoxelSet(this);

      for (int _snowmanx = 0; _snowmanx <= this.xSize; _snowmanx++) {
         for (int _snowmanxx = 0; _snowmanxx <= this.ySize; _snowmanxx++) {
            int _snowmanxxx = -1;

            for (int _snowmanxxxx = 0; _snowmanxxxx <= this.zSize; _snowmanxxxx++) {
               if (_snowman.inBoundsAndContains(_snowmanx, _snowmanxx, _snowmanxxxx)) {
                  if (largest) {
                     if (_snowmanxxx == -1) {
                        _snowmanxxx = _snowmanxxxx;
                     }
                  } else {
                     consumer.consume(_snowmanx, _snowmanxx, _snowmanxxxx, _snowmanx + 1, _snowmanxx + 1, _snowmanxxxx + 1);
                  }
               } else if (_snowmanxxx != -1) {
                  int _snowmanxxxxx = _snowmanx;
                  int _snowmanxxxxxx = _snowmanx;
                  int _snowmanxxxxxxx = _snowmanxx;
                  int _snowmanxxxxxxxx = _snowmanxx;
                  _snowman.setColumn(_snowmanxxx, _snowmanxxxx, _snowmanx, _snowmanxx, false);

                  while (_snowman.isColumnFull(_snowmanxxx, _snowmanxxxx, _snowmanxxxxx - 1, _snowmanxxxxxxx)) {
                     _snowman.setColumn(_snowmanxxx, _snowmanxxxx, _snowmanxxxxx - 1, _snowmanxxxxxxx, false);
                     _snowmanxxxxx--;
                  }

                  while (_snowman.isColumnFull(_snowmanxxx, _snowmanxxxx, _snowmanxxxxxx + 1, _snowmanxxxxxxx)) {
                     _snowman.setColumn(_snowmanxxx, _snowmanxxxx, _snowmanxxxxxx + 1, _snowmanxxxxxxx, false);
                     _snowmanxxxxxx++;
                  }

                  while (_snowman.isRectangleFull(_snowmanxxxxx, _snowmanxxxxxx + 1, _snowmanxxx, _snowmanxxxx, _snowmanxxxxxxx - 1)) {
                     for (int _snowmanxxxxxxxxx = _snowmanxxxxx; _snowmanxxxxxxxxx <= _snowmanxxxxxx; _snowmanxxxxxxxxx++) {
                        _snowman.setColumn(_snowmanxxx, _snowmanxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxx - 1, false);
                     }

                     _snowmanxxxxxxx--;
                  }

                  while (_snowman.isRectangleFull(_snowmanxxxxx, _snowmanxxxxxx + 1, _snowmanxxx, _snowmanxxxx, _snowmanxxxxxxxx + 1)) {
                     for (int _snowmanxxxxxxxxx = _snowmanxxxxx; _snowmanxxxxxxxxx <= _snowmanxxxxxx; _snowmanxxxxxxxxx++) {
                        _snowman.setColumn(_snowmanxxx, _snowmanxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxx + 1, false);
                     }

                     _snowmanxxxxxxxx++;
                  }

                  consumer.consume(_snowmanxxxxx, _snowmanxxxxxxx, _snowmanxxx, _snowmanxxxxxx + 1, _snowmanxxxxxxxx + 1, _snowmanxxxx);
                  _snowmanxxx = -1;
               }
            }
         }
      }
   }

   public void forEachDirection(VoxelSet.PositionConsumer _snowman) {
      this.forEachDirection(_snowman, AxisCycleDirection.NONE);
      this.forEachDirection(_snowman, AxisCycleDirection.FORWARD);
      this.forEachDirection(_snowman, AxisCycleDirection.BACKWARD);
   }

   private void forEachDirection(VoxelSet.PositionConsumer _snowman, AxisCycleDirection direction) {
      AxisCycleDirection _snowmanx = direction.opposite();
      Direction.Axis _snowmanxx = _snowmanx.cycle(Direction.Axis.Z);
      int _snowmanxxx = this.getSize(_snowmanx.cycle(Direction.Axis.X));
      int _snowmanxxxx = this.getSize(_snowmanx.cycle(Direction.Axis.Y));
      int _snowmanxxxxx = this.getSize(_snowmanxx);
      Direction _snowmanxxxxxx = Direction.from(_snowmanxx, Direction.AxisDirection.NEGATIVE);
      Direction _snowmanxxxxxxx = Direction.from(_snowmanxx, Direction.AxisDirection.POSITIVE);

      for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < _snowmanxxx; _snowmanxxxxxxxx++) {
         for (int _snowmanxxxxxxxxx = 0; _snowmanxxxxxxxxx < _snowmanxxxx; _snowmanxxxxxxxxx++) {
            boolean _snowmanxxxxxxxxxx = false;

            for (int _snowmanxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxx <= _snowmanxxxxx; _snowmanxxxxxxxxxxx++) {
               boolean _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxx != _snowmanxxxxx && this.contains(_snowmanx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxxx);
               if (!_snowmanxxxxxxxxxx && _snowmanxxxxxxxxxxxx) {
                  _snowman.consume(
                     _snowmanxxxxxx,
                     _snowmanx.choose(_snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxxx, Direction.Axis.X),
                     _snowmanx.choose(_snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxxx, Direction.Axis.Y),
                     _snowmanx.choose(_snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxxx, Direction.Axis.Z)
                  );
               }

               if (_snowmanxxxxxxxxxx && !_snowmanxxxxxxxxxxxx) {
                  _snowman.consume(
                     _snowmanxxxxxxx,
                     _snowmanx.choose(_snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxxx - 1, Direction.Axis.X),
                     _snowmanx.choose(_snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxxx - 1, Direction.Axis.Y),
                     _snowmanx.choose(_snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxxx - 1, Direction.Axis.Z)
                  );
               }

               _snowmanxxxxxxxxxx = _snowmanxxxxxxxxxxxx;
            }
         }
      }
   }

   public interface PositionBiConsumer {
      void consume(int x1, int y1, int z1, int x2, int y2, int z2);
   }

   public interface PositionConsumer {
      void consume(Direction direction, int x, int y, int z);
   }
}
