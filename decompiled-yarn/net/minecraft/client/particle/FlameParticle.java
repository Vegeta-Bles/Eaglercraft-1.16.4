package net.minecraft.client.particle;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.MathHelper;

public class FlameParticle extends AbstractSlowingParticle {
   private FlameParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
      super(world, x, y, z, velocityX, velocityY, velocityZ);
   }

   @Override
   public ParticleTextureSheet getType() {
      return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
   }

   @Override
   public void move(double dx, double dy, double dz) {
      this.setBoundingBox(this.getBoundingBox().offset(dx, dy, dz));
      this.repositionFromBoundingBox();
   }

   @Override
   public float getSize(float tickDelta) {
      float _snowman = ((float)this.age + tickDelta) / (float)this.maxAge;
      return this.scale * (1.0F - _snowman * _snowman * 0.5F);
   }

   @Override
   public int getColorMultiplier(float tint) {
      float _snowman = ((float)this.age + tint) / (float)this.maxAge;
      _snowman = MathHelper.clamp(_snowman, 0.0F, 1.0F);
      int _snowmanx = super.getColorMultiplier(tint);
      int _snowmanxx = _snowmanx & 0xFF;
      int _snowmanxxx = _snowmanx >> 16 & 0xFF;
      _snowmanxx += (int)(_snowman * 15.0F * 16.0F);
      if (_snowmanxx > 240) {
         _snowmanxx = 240;
      }

      return _snowmanxx | _snowmanxxx << 16;
   }

   public static class Factory implements ParticleFactory<DefaultParticleType> {
      private final SpriteProvider spriteProvider;

      public Factory(SpriteProvider spriteProvider) {
         this.spriteProvider = spriteProvider;
      }

      public Particle createParticle(DefaultParticleType _snowman, ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
         FlameParticle _snowmanxxxxxxxx = new FlameParticle(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
         _snowmanxxxxxxxx.setSprite(this.spriteProvider);
         return _snowmanxxxxxxxx;
      }
   }
}
