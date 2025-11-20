package net.minecraft.client.particle;

import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemConvertible;
import net.minecraft.particle.DefaultParticleType;

public class BarrierParticle extends SpriteBillboardParticle {
   private BarrierParticle(ClientWorld world, double x, double y, double z, ItemConvertible _snowman) {
      super(world, x, y, z);
      this.setSprite(MinecraftClient.getInstance().getItemRenderer().getModels().getSprite(_snowman));
      this.gravityStrength = 0.0F;
      this.maxAge = 80;
      this.collidesWithWorld = false;
   }

   @Override
   public ParticleTextureSheet getType() {
      return ParticleTextureSheet.TERRAIN_SHEET;
   }

   @Override
   public float getSize(float tickDelta) {
      return 0.5F;
   }

   public static class Factory implements ParticleFactory<DefaultParticleType> {
      public Factory() {
      }

      public Particle createParticle(DefaultParticleType _snowman, ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
         return new BarrierParticle(_snowman, _snowman, _snowman, _snowman, Blocks.BARRIER.asItem());
      }
   }
}
