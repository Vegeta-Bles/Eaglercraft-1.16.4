package net.minecraft.client.sound;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BubbleColumnBlock;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.ClientPlayerTickable;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public class BubbleColumnSoundPlayer implements ClientPlayerTickable {
   private final ClientPlayerEntity player;
   private boolean hasPlayedForCurrentColumn;
   private boolean firstTick = true;

   public BubbleColumnSoundPlayer(ClientPlayerEntity player) {
      this.player = player;
   }

   @Override
   public void tick() {
      World _snowman = this.player.world;
      BlockState _snowmanx = _snowman.method_29556(this.player.getBoundingBox().expand(0.0, -0.4F, 0.0).contract(0.001))
         .filter(_snowmanxx -> _snowmanxx.isOf(Blocks.BUBBLE_COLUMN))
         .findFirst()
         .orElse(null);
      if (_snowmanx != null) {
         if (!this.hasPlayedForCurrentColumn && !this.firstTick && _snowmanx.isOf(Blocks.BUBBLE_COLUMN) && !this.player.isSpectator()) {
            boolean _snowmanxx = _snowmanx.get(BubbleColumnBlock.DRAG);
            if (_snowmanxx) {
               this.player.playSound(SoundEvents.BLOCK_BUBBLE_COLUMN_WHIRLPOOL_INSIDE, 1.0F, 1.0F);
            } else {
               this.player.playSound(SoundEvents.BLOCK_BUBBLE_COLUMN_UPWARDS_INSIDE, 1.0F, 1.0F);
            }
         }

         this.hasPlayedForCurrentColumn = true;
      } else {
         this.hasPlayedForCurrentColumn = false;
      }

      this.firstTick = false;
   }
}
