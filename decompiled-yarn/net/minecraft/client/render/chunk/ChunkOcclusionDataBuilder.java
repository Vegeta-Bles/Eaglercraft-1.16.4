package net.minecraft.client.render.chunk;

import it.unimi.dsi.fastutil.ints.IntArrayFIFOQueue;
import it.unimi.dsi.fastutil.ints.IntPriorityQueue;
import java.util.BitSet;
import java.util.EnumSet;
import java.util.Set;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class ChunkOcclusionDataBuilder {
   private static final int STEP_X = (int)Math.pow(16.0, 0.0);
   private static final int STEP_Z = (int)Math.pow(16.0, 1.0);
   private static final int STEP_Y = (int)Math.pow(16.0, 2.0);
   private static final Direction[] DIRECTIONS = Direction.values();
   private final BitSet closed = new BitSet(4096);
   private static final int[] EDGE_POINTS = Util.make(new int[1352], _snowman -> {
      int _snowmanx = 0;
      int _snowmanxx = 15;
      int _snowmanxxx = 0;

      for (int _snowmanxxxx = 0; _snowmanxxxx < 16; _snowmanxxxx++) {
         for (int _snowmanxxxxx = 0; _snowmanxxxxx < 16; _snowmanxxxxx++) {
            for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < 16; _snowmanxxxxxx++) {
               if (_snowmanxxxx == 0 || _snowmanxxxx == 15 || _snowmanxxxxx == 0 || _snowmanxxxxx == 15 || _snowmanxxxxxx == 0 || _snowmanxxxxxx == 15) {
                  _snowman[_snowmanxxx++] = pack(_snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx);
               }
            }
         }
      }
   });
   private int openCount = 4096;

   public ChunkOcclusionDataBuilder() {
   }

   public void markClosed(BlockPos pos) {
      this.closed.set(pack(pos), true);
      this.openCount--;
   }

   private static int pack(BlockPos pos) {
      return pack(pos.getX() & 15, pos.getY() & 15, pos.getZ() & 15);
   }

   private static int pack(int x, int y, int z) {
      return x << 0 | y << 8 | z << 4;
   }

   public ChunkOcclusionData build() {
      ChunkOcclusionData _snowman = new ChunkOcclusionData();
      if (4096 - this.openCount < 256) {
         _snowman.fill(true);
      } else if (this.openCount == 0) {
         _snowman.fill(false);
      } else {
         for (int _snowmanx : EDGE_POINTS) {
            if (!this.closed.get(_snowmanx)) {
               _snowman.addOpenEdgeFaces(this.getOpenFaces(_snowmanx));
            }
         }
      }

      return _snowman;
   }

   private Set<Direction> getOpenFaces(int pos) {
      Set<Direction> _snowman = EnumSet.noneOf(Direction.class);
      IntPriorityQueue _snowmanx = new IntArrayFIFOQueue();
      _snowmanx.enqueue(pos);
      this.closed.set(pos, true);

      while (!_snowmanx.isEmpty()) {
         int _snowmanxx = _snowmanx.dequeueInt();
         this.addEdgeFaces(_snowmanxx, _snowman);

         for (Direction _snowmanxxx : DIRECTIONS) {
            int _snowmanxxxx = this.offset(_snowmanxx, _snowmanxxx);
            if (_snowmanxxxx >= 0 && !this.closed.get(_snowmanxxxx)) {
               this.closed.set(_snowmanxxxx, true);
               _snowmanx.enqueue(_snowmanxxxx);
            }
         }
      }

      return _snowman;
   }

   private void addEdgeFaces(int pos, Set<Direction> openFaces) {
      int _snowman = pos >> 0 & 15;
      if (_snowman == 0) {
         openFaces.add(Direction.WEST);
      } else if (_snowman == 15) {
         openFaces.add(Direction.EAST);
      }

      int _snowmanx = pos >> 8 & 15;
      if (_snowmanx == 0) {
         openFaces.add(Direction.DOWN);
      } else if (_snowmanx == 15) {
         openFaces.add(Direction.UP);
      }

      int _snowmanxx = pos >> 4 & 15;
      if (_snowmanxx == 0) {
         openFaces.add(Direction.NORTH);
      } else if (_snowmanxx == 15) {
         openFaces.add(Direction.SOUTH);
      }
   }

   private int offset(int pos, Direction _snowman) {
      switch (_snowman) {
         case DOWN:
            if ((pos >> 8 & 15) == 0) {
               return -1;
            }

            return pos - STEP_Y;
         case UP:
            if ((pos >> 8 & 15) == 15) {
               return -1;
            }

            return pos + STEP_Y;
         case NORTH:
            if ((pos >> 4 & 15) == 0) {
               return -1;
            }

            return pos - STEP_Z;
         case SOUTH:
            if ((pos >> 4 & 15) == 15) {
               return -1;
            }

            return pos + STEP_Z;
         case WEST:
            if ((pos >> 0 & 15) == 0) {
               return -1;
            }

            return pos - STEP_X;
         case EAST:
            if ((pos >> 0 & 15) == 15) {
               return -1;
            }

            return pos + STEP_X;
         default:
            return -1;
      }
   }
}
