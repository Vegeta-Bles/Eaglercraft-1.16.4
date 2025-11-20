package net.minecraft.client.particle;

import javax.annotation.Nullable;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class BlockFallingDustParticle extends SpriteBillboardParticle {
   private final float field_3809;
   private final SpriteProvider spriteProvider;

   private BlockFallingDustParticle(
      ClientWorld world, double x, double y, double z, float colorRed, float colorGreen, float colorBlue, SpriteProvider spriteProvider
   ) {
      super(world, x, y, z);
      this.spriteProvider = spriteProvider;
      this.colorRed = colorRed;
      this.colorGreen = colorGreen;
      this.colorBlue = colorBlue;
      float _snowman = 0.9F;
      this.scale *= 0.67499995F;
      int _snowmanx = (int)(32.0 / (Math.random() * 0.8 + 0.2));
      this.maxAge = (int)Math.max((float)_snowmanx * 0.9F, 1.0F);
      this.setSpriteForAge(spriteProvider);
      this.field_3809 = ((float)Math.random() - 0.5F) * 0.1F;
      this.angle = (float)Math.random() * (float) (Math.PI * 2);
   }

   @Override
   public ParticleTextureSheet getType() {
      return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
   }

   @Override
   public float getSize(float tickDelta) {
      return this.scale * MathHelper.clamp(((float)this.age + tickDelta) / (float)this.maxAge * 32.0F, 0.0F, 1.0F);
   }

   @Override
   public void tick() {
      this.prevPosX = this.x;
      this.prevPosY = this.y;
      this.prevPosZ = this.z;
      if (this.age++ >= this.maxAge) {
         this.markDead();
      } else {
         this.setSpriteForAge(this.spriteProvider);
         this.prevAngle = this.angle;
         this.angle = this.angle + (float) Math.PI * this.field_3809 * 2.0F;
         if (this.onGround) {
            this.prevAngle = this.angle = 0.0F;
         }

         this.move(this.velocityX, this.velocityY, this.velocityZ);
         this.velocityY -= 0.003F;
         this.velocityY = Math.max(this.velocityY, -0.14F);
      }
   }

   public static class Factory implements ParticleFactory<BlockStateParticleEffect> {
      private final SpriteProvider spriteProvider;

      public Factory(SpriteProvider spriteProvider) {
         this.spriteProvider = spriteProvider;
      }

      @Nullable
      public Particle createParticle(BlockStateParticleEffect _snowman, ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
         BlockState _snowmanxxxxxxxx = _snowman.getBlockState();
         if (!_snowmanxxxxxxxx.isAir() && _snowmanxxxxxxxx.getRenderType() == BlockRenderType.INVISIBLE) {
            return null;
         } else {
            BlockPos _snowmanxxxxxxxxx = new BlockPos(_snowman, _snowman, _snowman);
            int _snowmanxxxxxxxxxx = MinecraftClient.getInstance().getBlockColors().getColor(_snowmanxxxxxxxx, _snowman, _snowmanxxxxxxxxx);
            if (_snowmanxxxxxxxx.getBlock() instanceof FallingBlock) {
               _snowmanxxxxxxxxxx = ((FallingBlock)_snowmanxxxxxxxx.getBlock()).getColor(_snowmanxxxxxxxx, _snowman, _snowmanxxxxxxxxx);
            }

            float _snowmanxxxxxxxxxxx = (float)(_snowmanxxxxxxxxxx >> 16 & 0xFF) / 255.0F;
            float _snowmanxxxxxxxxxxxx = (float)(_snowmanxxxxxxxxxx >> 8 & 0xFF) / 255.0F;
            float _snowmanxxxxxxxxxxxxx = (float)(_snowmanxxxxxxxxxx & 0xFF) / 255.0F;
            return new BlockFallingDustParticle(_snowman, _snowman, _snowman, _snowman, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, this.spriteProvider);
         }
      }
   }
}
