package net.minecraft.block.entity;

import net.minecraft.block.Blocks;
import net.minecraft.client.block.ChestAnimationProgress;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.MathHelper;

public class EnderChestBlockEntity extends BlockEntity implements ChestAnimationProgress, Tickable {
   public float animationProgress;
   public float lastAnimationProgress;
   public int viewerCount;
   private int ticks;

   public EnderChestBlockEntity() {
      super(BlockEntityType.ENDER_CHEST);
   }

   @Override
   public void tick() {
      if (++this.ticks % 20 * 4 == 0) {
         this.world.addSyncedBlockEvent(this.pos, Blocks.ENDER_CHEST, 1, this.viewerCount);
      }

      this.lastAnimationProgress = this.animationProgress;
      int _snowman = this.pos.getX();
      int _snowmanx = this.pos.getY();
      int _snowmanxx = this.pos.getZ();
      float _snowmanxxx = 0.1F;
      if (this.viewerCount > 0 && this.animationProgress == 0.0F) {
         double _snowmanxxxx = (double)_snowman + 0.5;
         double _snowmanxxxxx = (double)_snowmanxx + 0.5;
         this.world
            .playSound(
               null,
               _snowmanxxxx,
               (double)_snowmanx + 0.5,
               _snowmanxxxxx,
               SoundEvents.BLOCK_ENDER_CHEST_OPEN,
               SoundCategory.BLOCKS,
               0.5F,
               this.world.random.nextFloat() * 0.1F + 0.9F
            );
      }

      if (this.viewerCount == 0 && this.animationProgress > 0.0F || this.viewerCount > 0 && this.animationProgress < 1.0F) {
         float _snowmanxxxx = this.animationProgress;
         if (this.viewerCount > 0) {
            this.animationProgress += 0.1F;
         } else {
            this.animationProgress -= 0.1F;
         }

         if (this.animationProgress > 1.0F) {
            this.animationProgress = 1.0F;
         }

         float _snowmanxxxxx = 0.5F;
         if (this.animationProgress < 0.5F && _snowmanxxxx >= 0.5F) {
            double _snowmanxxxxxx = (double)_snowman + 0.5;
            double _snowmanxxxxxxx = (double)_snowmanxx + 0.5;
            this.world
               .playSound(
                  null,
                  _snowmanxxxxxx,
                  (double)_snowmanx + 0.5,
                  _snowmanxxxxxxx,
                  SoundEvents.BLOCK_ENDER_CHEST_CLOSE,
                  SoundCategory.BLOCKS,
                  0.5F,
                  this.world.random.nextFloat() * 0.1F + 0.9F
               );
         }

         if (this.animationProgress < 0.0F) {
            this.animationProgress = 0.0F;
         }
      }
   }

   @Override
   public boolean onSyncedBlockEvent(int type, int data) {
      if (type == 1) {
         this.viewerCount = data;
         return true;
      } else {
         return super.onSyncedBlockEvent(type, data);
      }
   }

   @Override
   public void markRemoved() {
      this.resetBlock();
      super.markRemoved();
   }

   public void onOpen() {
      this.viewerCount++;
      this.world.addSyncedBlockEvent(this.pos, Blocks.ENDER_CHEST, 1, this.viewerCount);
   }

   public void onClose() {
      this.viewerCount--;
      this.world.addSyncedBlockEvent(this.pos, Blocks.ENDER_CHEST, 1, this.viewerCount);
   }

   public boolean canPlayerUse(PlayerEntity _snowman) {
      return this.world.getBlockEntity(this.pos) != this
         ? false
         : !(_snowman.squaredDistanceTo((double)this.pos.getX() + 0.5, (double)this.pos.getY() + 0.5, (double)this.pos.getZ() + 0.5) > 64.0);
   }

   @Override
   public float getAnimationProgress(float tickDelta) {
      return MathHelper.lerp(tickDelta, this.lastAnimationProgress, this.animationProgress);
   }
}
