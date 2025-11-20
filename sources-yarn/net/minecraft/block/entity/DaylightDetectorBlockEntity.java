package net.minecraft.block.entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DaylightDetectorBlock;
import net.minecraft.util.Tickable;

public class DaylightDetectorBlockEntity extends BlockEntity implements Tickable {
   public DaylightDetectorBlockEntity() {
      super(BlockEntityType.DAYLIGHT_DETECTOR);
   }

   @Override
   public void tick() {
      if (this.world != null && !this.world.isClient && this.world.getTime() % 20L == 0L) {
         BlockState lv = this.getCachedState();
         Block lv2 = lv.getBlock();
         if (lv2 instanceof DaylightDetectorBlock) {
            DaylightDetectorBlock.updateState(lv, this.world, this.pos);
         }
      }
   }
}
