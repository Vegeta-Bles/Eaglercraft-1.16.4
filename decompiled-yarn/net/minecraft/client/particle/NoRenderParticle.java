package net.minecraft.client.particle;

import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.world.ClientWorld;

public class NoRenderParticle extends Particle {
   protected NoRenderParticle(ClientWorld _snowman, double _snowman, double _snowman, double _snowman) {
      super(_snowman, _snowman, _snowman, _snowman);
   }

   protected NoRenderParticle(ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
      super(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public final void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
   }

   @Override
   public ParticleTextureSheet getType() {
      return ParticleTextureSheet.NO_RENDER;
   }
}
