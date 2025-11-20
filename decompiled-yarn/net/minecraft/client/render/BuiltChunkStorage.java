package net.minecraft.client.render;

import javax.annotation.Nullable;
import net.minecraft.client.render.chunk.ChunkBuilder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class BuiltChunkStorage {
   protected final WorldRenderer worldRenderer;
   protected final World world;
   protected int sizeY;
   protected int sizeX;
   protected int sizeZ;
   public ChunkBuilder.BuiltChunk[] chunks;

   public BuiltChunkStorage(ChunkBuilder _snowman, World world, int viewDistance, WorldRenderer worldRenderer) {
      this.worldRenderer = worldRenderer;
      this.world = world;
      this.setViewDistance(viewDistance);
      this.createChunks(_snowman);
   }

   protected void createChunks(ChunkBuilder _snowman) {
      int _snowmanx = this.sizeX * this.sizeY * this.sizeZ;
      this.chunks = new ChunkBuilder.BuiltChunk[_snowmanx];

      for (int _snowmanxx = 0; _snowmanxx < this.sizeX; _snowmanxx++) {
         for (int _snowmanxxx = 0; _snowmanxxx < this.sizeY; _snowmanxxx++) {
            for (int _snowmanxxxx = 0; _snowmanxxxx < this.sizeZ; _snowmanxxxx++) {
               int _snowmanxxxxx = this.getChunkIndex(_snowmanxx, _snowmanxxx, _snowmanxxxx);
               this.chunks[_snowmanxxxxx] = _snowman.new BuiltChunk();
               this.chunks[_snowmanxxxxx].setOrigin(_snowmanxx * 16, _snowmanxxx * 16, _snowmanxxxx * 16);
            }
         }
      }
   }

   public void clear() {
      for (ChunkBuilder.BuiltChunk _snowman : this.chunks) {
         _snowman.delete();
      }
   }

   private int getChunkIndex(int x, int y, int z) {
      return (z * this.sizeY + y) * this.sizeX + x;
   }

   protected void setViewDistance(int viewDistance) {
      int _snowman = viewDistance * 2 + 1;
      this.sizeX = _snowman;
      this.sizeY = 16;
      this.sizeZ = _snowman;
   }

   public void updateCameraPosition(double x, double z) {
      int _snowman = MathHelper.floor(x);
      int _snowmanx = MathHelper.floor(z);

      for (int _snowmanxx = 0; _snowmanxx < this.sizeX; _snowmanxx++) {
         int _snowmanxxx = this.sizeX * 16;
         int _snowmanxxxx = _snowman - 8 - _snowmanxxx / 2;
         int _snowmanxxxxx = _snowmanxxxx + Math.floorMod(_snowmanxx * 16 - _snowmanxxxx, _snowmanxxx);

         for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < this.sizeZ; _snowmanxxxxxx++) {
            int _snowmanxxxxxxx = this.sizeZ * 16;
            int _snowmanxxxxxxxx = _snowmanx - 8 - _snowmanxxxxxxx / 2;
            int _snowmanxxxxxxxxx = _snowmanxxxxxxxx + Math.floorMod(_snowmanxxxxxx * 16 - _snowmanxxxxxxxx, _snowmanxxxxxxx);

            for (int _snowmanxxxxxxxxxx = 0; _snowmanxxxxxxxxxx < this.sizeY; _snowmanxxxxxxxxxx++) {
               int _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxx * 16;
               ChunkBuilder.BuiltChunk _snowmanxxxxxxxxxxxx = this.chunks[this.getChunkIndex(_snowmanxx, _snowmanxxxxxxxxxx, _snowmanxxxxxx)];
               _snowmanxxxxxxxxxxxx.setOrigin(_snowmanxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxx);
            }
         }
      }
   }

   public void scheduleRebuild(int x, int y, int z, boolean important) {
      int _snowman = Math.floorMod(x, this.sizeX);
      int _snowmanx = Math.floorMod(y, this.sizeY);
      int _snowmanxx = Math.floorMod(z, this.sizeZ);
      ChunkBuilder.BuiltChunk _snowmanxxx = this.chunks[this.getChunkIndex(_snowman, _snowmanx, _snowmanxx)];
      _snowmanxxx.scheduleRebuild(important);
   }

   @Nullable
   protected ChunkBuilder.BuiltChunk getRenderedChunk(BlockPos pos) {
      int _snowman = MathHelper.floorDiv(pos.getX(), 16);
      int _snowmanx = MathHelper.floorDiv(pos.getY(), 16);
      int _snowmanxx = MathHelper.floorDiv(pos.getZ(), 16);
      if (_snowmanx >= 0 && _snowmanx < this.sizeY) {
         _snowman = MathHelper.floorMod(_snowman, this.sizeX);
         _snowmanxx = MathHelper.floorMod(_snowmanxx, this.sizeZ);
         return this.chunks[this.getChunkIndex(_snowman, _snowmanx, _snowmanxx)];
      } else {
         return null;
      }
   }
}
