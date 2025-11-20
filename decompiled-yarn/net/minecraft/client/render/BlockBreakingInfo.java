package net.minecraft.client.render;

import net.minecraft.util.math.BlockPos;

public class BlockBreakingInfo implements Comparable<BlockBreakingInfo> {
   private final int actorNetworkId;
   private final BlockPos pos;
   private int stage;
   private int lastUpdateTick;

   public BlockBreakingInfo(int breakingEntityId, BlockPos pos) {
      this.actorNetworkId = breakingEntityId;
      this.pos = pos;
   }

   public BlockPos getPos() {
      return this.pos;
   }

   public void setStage(int stage) {
      if (stage > 10) {
         stage = 10;
      }

      this.stage = stage;
   }

   public int getStage() {
      return this.stage;
   }

   public void setLastUpdateTick(int lastUpdateTick) {
      this.lastUpdateTick = lastUpdateTick;
   }

   public int getLastUpdateTick() {
      return this.lastUpdateTick;
   }

   @Override
   public boolean equals(Object _snowman) {
      if (this == _snowman) {
         return true;
      } else if (_snowman != null && this.getClass() == _snowman.getClass()) {
         BlockBreakingInfo _snowmanx = (BlockBreakingInfo)_snowman;
         return this.actorNetworkId == _snowmanx.actorNetworkId;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Integer.hashCode(this.actorNetworkId);
   }

   public int compareTo(BlockBreakingInfo _snowman) {
      return this.stage != _snowman.stage ? Integer.compare(this.stage, _snowman.stage) : Integer.compare(this.actorNetworkId, _snowman.actorNetworkId);
   }
}
