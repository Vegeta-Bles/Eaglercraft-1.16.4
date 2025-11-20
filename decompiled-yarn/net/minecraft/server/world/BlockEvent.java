package net.minecraft.server.world;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

public class BlockEvent {
   private final BlockPos pos;
   private final Block block;
   private final int type;
   private final int data;

   public BlockEvent(BlockPos pos, Block block, int type, int data) {
      this.pos = pos;
      this.block = block;
      this.type = type;
      this.data = data;
   }

   public BlockPos getPos() {
      return this.pos;
   }

   public Block getBlock() {
      return this.block;
   }

   public int getType() {
      return this.type;
   }

   public int getData() {
      return this.data;
   }

   @Override
   public boolean equals(Object o) {
      if (!(o instanceof BlockEvent)) {
         return false;
      } else {
         BlockEvent _snowman = (BlockEvent)o;
         return this.pos.equals(_snowman.pos) && this.type == _snowman.type && this.data == _snowman.data && this.block == _snowman.block;
      }
   }

   @Override
   public int hashCode() {
      int _snowman = this.pos.hashCode();
      _snowman = 31 * _snowman + this.block.hashCode();
      _snowman = 31 * _snowman + this.type;
      return 31 * _snowman + this.data;
   }

   @Override
   public String toString() {
      return "TE(" + this.pos + ")," + this.type + "," + this.data + "," + this.block;
   }
}
