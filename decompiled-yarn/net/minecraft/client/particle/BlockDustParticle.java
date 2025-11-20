package net.minecraft.client.particle;

import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.util.math.BlockPos;

public class BlockDustParticle extends SpriteBillboardParticle {
   private final BlockState blockState;
   private BlockPos blockPos;
   private final float sampleU;
   private final float sampleV;

   public BlockDustParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, BlockState blockState) {
      super(world, x, y, z, velocityX, velocityY, velocityZ);
      this.blockState = blockState;
      this.setSprite(MinecraftClient.getInstance().getBlockRenderManager().getModels().getSprite(blockState));
      this.gravityStrength = 1.0F;
      this.colorRed = 0.6F;
      this.colorGreen = 0.6F;
      this.colorBlue = 0.6F;
      this.scale /= 2.0F;
      this.sampleU = this.random.nextFloat() * 3.0F;
      this.sampleV = this.random.nextFloat() * 3.0F;
   }

   @Override
   public ParticleTextureSheet getType() {
      return ParticleTextureSheet.TERRAIN_SHEET;
   }

   public BlockDustParticle setBlockPos(BlockPos blockPos) {
      this.blockPos = blockPos;
      if (this.blockState.isOf(Blocks.GRASS_BLOCK)) {
         return this;
      } else {
         this.updateColor(blockPos);
         return this;
      }
   }

   public BlockDustParticle setBlockPosFromPosition() {
      this.blockPos = new BlockPos(this.x, this.y, this.z);
      if (this.blockState.isOf(Blocks.GRASS_BLOCK)) {
         return this;
      } else {
         this.updateColor(this.blockPos);
         return this;
      }
   }

   protected void updateColor(@Nullable BlockPos blockPos) {
      int _snowman = MinecraftClient.getInstance().getBlockColors().getColor(this.blockState, this.world, blockPos, 0);
      this.colorRed *= (float)(_snowman >> 16 & 0xFF) / 255.0F;
      this.colorGreen *= (float)(_snowman >> 8 & 0xFF) / 255.0F;
      this.colorBlue *= (float)(_snowman & 0xFF) / 255.0F;
   }

   @Override
   protected float getMinU() {
      return this.sprite.getFrameU((double)((this.sampleU + 1.0F) / 4.0F * 16.0F));
   }

   @Override
   protected float getMaxU() {
      return this.sprite.getFrameU((double)(this.sampleU / 4.0F * 16.0F));
   }

   @Override
   protected float getMinV() {
      return this.sprite.getFrameV((double)(this.sampleV / 4.0F * 16.0F));
   }

   @Override
   protected float getMaxV() {
      return this.sprite.getFrameV((double)((this.sampleV + 1.0F) / 4.0F * 16.0F));
   }

   @Override
   public int getColorMultiplier(float tint) {
      int _snowman = super.getColorMultiplier(tint);
      int _snowmanx = 0;
      if (this.world.isChunkLoaded(this.blockPos)) {
         _snowmanx = WorldRenderer.getLightmapCoordinates(this.world, this.blockPos);
      }

      return _snowman == 0 ? _snowmanx : _snowman;
   }

   public static class Factory implements ParticleFactory<BlockStateParticleEffect> {
      public Factory() {
      }

      public Particle createParticle(BlockStateParticleEffect _snowman, ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
         BlockState _snowmanxxxxxxxx = _snowman.getBlockState();
         return !_snowmanxxxxxxxx.isAir() && !_snowmanxxxxxxxx.isOf(Blocks.MOVING_PISTON)
            ? new BlockDustParticle(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowmanxxxxxxxx).setBlockPosFromPosition()
            : null;
      }
   }
}
