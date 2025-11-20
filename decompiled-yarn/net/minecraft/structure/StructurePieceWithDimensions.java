package net.minecraft.structure;

import java.util.Random;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.Heightmap;
import net.minecraft.world.WorldAccess;

public abstract class StructurePieceWithDimensions extends StructurePiece {
   protected final int width;
   protected final int height;
   protected final int depth;
   protected int hPos = -1;

   protected StructurePieceWithDimensions(StructurePieceType type, Random random, int x, int y, int z, int width, int height, int depth) {
      super(type, 0);
      this.width = width;
      this.height = height;
      this.depth = depth;
      this.setOrientation(Direction.Type.HORIZONTAL.random(random));
      if (this.getFacing().getAxis() == Direction.Axis.Z) {
         this.boundingBox = new BlockBox(x, y, z, x + width - 1, y + height - 1, z + depth - 1);
      } else {
         this.boundingBox = new BlockBox(x, y, z, x + depth - 1, y + height - 1, z + width - 1);
      }
   }

   protected StructurePieceWithDimensions(StructurePieceType _snowman, CompoundTag _snowman) {
      super(_snowman, _snowman);
      this.width = _snowman.getInt("Width");
      this.height = _snowman.getInt("Height");
      this.depth = _snowman.getInt("Depth");
      this.hPos = _snowman.getInt("HPos");
   }

   @Override
   protected void toNbt(CompoundTag tag) {
      tag.putInt("Width", this.width);
      tag.putInt("Height", this.height);
      tag.putInt("Depth", this.depth);
      tag.putInt("HPos", this.hPos);
   }

   protected boolean method_14839(WorldAccess world, BlockBox boundingBox, int _snowman) {
      if (this.hPos >= 0) {
         return true;
      } else {
         int _snowmanx = 0;
         int _snowmanxx = 0;
         BlockPos.Mutable _snowmanxxx = new BlockPos.Mutable();

         for (int _snowmanxxxx = this.boundingBox.minZ; _snowmanxxxx <= this.boundingBox.maxZ; _snowmanxxxx++) {
            for (int _snowmanxxxxx = this.boundingBox.minX; _snowmanxxxxx <= this.boundingBox.maxX; _snowmanxxxxx++) {
               _snowmanxxx.set(_snowmanxxxxx, 64, _snowmanxxxx);
               if (boundingBox.contains(_snowmanxxx)) {
                  _snowmanx += world.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, _snowmanxxx).getY();
                  _snowmanxx++;
               }
            }
         }

         if (_snowmanxx == 0) {
            return false;
         } else {
            this.hPos = _snowmanx / _snowmanxx;
            this.boundingBox.move(0, this.hPos - this.boundingBox.minY + _snowman, 0);
            return true;
         }
      }
   }
}
