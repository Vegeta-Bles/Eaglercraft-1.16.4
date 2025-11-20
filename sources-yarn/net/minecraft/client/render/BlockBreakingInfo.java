package net.minecraft.client.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.math.BlockPos;

@Environment(EnvType.CLIENT)
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
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else if (object != null && this.getClass() == object.getClass()) {
         BlockBreakingInfo lv = (BlockBreakingInfo)object;
         return this.actorNetworkId == lv.actorNetworkId;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Integer.hashCode(this.actorNetworkId);
   }

   public int compareTo(BlockBreakingInfo arg) {
      return this.stage != arg.stage ? Integer.compare(this.stage, arg.stage) : Integer.compare(this.actorNetworkId, arg.actorNetworkId);
   }
}
