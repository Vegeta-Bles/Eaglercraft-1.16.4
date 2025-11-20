package net.minecraft.client.particle;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.Vec3d;

public class EmitterParticle extends NoRenderParticle {
   private final Entity entity;
   private int emitterAge;
   private final int maxEmitterAge;
   private final ParticleEffect parameters;

   public EmitterParticle(ClientWorld world, Entity entity, ParticleEffect parameters) {
      this(world, entity, parameters, 3);
   }

   public EmitterParticle(ClientWorld world, Entity entity, ParticleEffect parameters, int maxEmitterAge) {
      this(world, entity, parameters, maxEmitterAge, entity.getVelocity());
   }

   private EmitterParticle(ClientWorld world, Entity entity, ParticleEffect parameters, int maxEmitterAge, Vec3d velocity) {
      super(world, entity.getX(), entity.getBodyY(0.5), entity.getZ(), velocity.x, velocity.y, velocity.z);
      this.entity = entity;
      this.maxEmitterAge = maxEmitterAge;
      this.parameters = parameters;
      this.tick();
   }

   @Override
   public void tick() {
      for (int _snowman = 0; _snowman < 16; _snowman++) {
         double _snowmanx = (double)(this.random.nextFloat() * 2.0F - 1.0F);
         double _snowmanxx = (double)(this.random.nextFloat() * 2.0F - 1.0F);
         double _snowmanxxx = (double)(this.random.nextFloat() * 2.0F - 1.0F);
         if (!(_snowmanx * _snowmanx + _snowmanxx * _snowmanxx + _snowmanxxx * _snowmanxxx > 1.0)) {
            double _snowmanxxxx = this.entity.offsetX(_snowmanx / 4.0);
            double _snowmanxxxxx = this.entity.getBodyY(0.5 + _snowmanxx / 4.0);
            double _snowmanxxxxxx = this.entity.offsetZ(_snowmanxxx / 4.0);
            this.world.addParticle(this.parameters, false, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanx, _snowmanxx + 0.2, _snowmanxxx);
         }
      }

      this.emitterAge++;
      if (this.emitterAge >= this.maxEmitterAge) {
         this.markDead();
      }
   }
}
