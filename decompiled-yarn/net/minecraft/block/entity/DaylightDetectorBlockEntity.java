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
         BlockState _snowman = this.getCachedState();
         Block _snowmanx = _snowman.getBlock();
         if (_snowmanx instanceof DaylightDetectorBlock) {
            DaylightDetectorBlock.updateState(_snowman, this.world, this.pos);
         }
      }
   }
}
