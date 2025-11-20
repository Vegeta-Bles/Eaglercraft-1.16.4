package net.minecraft.client.particle;

import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;

public abstract class BillboardParticle extends Particle {
   protected float scale = 0.1F * (this.random.nextFloat() * 0.5F + 0.5F) * 2.0F;

   protected BillboardParticle(ClientWorld _snowman, double _snowman, double _snowman, double _snowman) {
      super(_snowman, _snowman, _snowman, _snowman);
   }

   protected BillboardParticle(ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
      super(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
      Vec3d _snowman = camera.getPos();
      float _snowmanx = (float)(MathHelper.lerp((double)tickDelta, this.prevPosX, this.x) - _snowman.getX());
      float _snowmanxx = (float)(MathHelper.lerp((double)tickDelta, this.prevPosY, this.y) - _snowman.getY());
      float _snowmanxxx = (float)(MathHelper.lerp((double)tickDelta, this.prevPosZ, this.z) - _snowman.getZ());
      Quaternion _snowmanxxxx;
      if (this.angle == 0.0F) {
         _snowmanxxxx = camera.getRotation();
      } else {
         _snowmanxxxx = new Quaternion(camera.getRotation());
         float _snowmanxxxxx = MathHelper.lerp(tickDelta, this.prevAngle, this.angle);
         _snowmanxxxx.hamiltonProduct(Vector3f.POSITIVE_Z.getRadialQuaternion(_snowmanxxxxx));
      }

      Vector3f _snowmanxxxxx = new Vector3f(-1.0F, -1.0F, 0.0F);
      _snowmanxxxxx.rotate(_snowmanxxxx);
      Vector3f[] _snowmanxxxxxx = new Vector3f[]{
         new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)
      };
      float _snowmanxxxxxxx = this.getSize(tickDelta);

      for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < 4; _snowmanxxxxxxxx++) {
         Vector3f _snowmanxxxxxxxxx = _snowmanxxxxxx[_snowmanxxxxxxxx];
         _snowmanxxxxxxxxx.rotate(_snowmanxxxx);
         _snowmanxxxxxxxxx.scale(_snowmanxxxxxxx);
         _snowmanxxxxxxxxx.add(_snowmanx, _snowmanxx, _snowmanxxx);
      }

      float _snowmanxxxxxxxx = this.getMinU();
      float _snowmanxxxxxxxxx = this.getMaxU();
      float _snowmanxxxxxxxxxx = this.getMinV();
      float _snowmanxxxxxxxxxxx = this.getMaxV();
      int _snowmanxxxxxxxxxxxx = this.getColorMultiplier(tickDelta);
      vertexConsumer.vertex((double)_snowmanxxxxxx[0].getX(), (double)_snowmanxxxxxx[0].getY(), (double)_snowmanxxxxxx[0].getZ())
         .texture(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxxx)
         .color(this.colorRed, this.colorGreen, this.colorBlue, this.colorAlpha)
         .light(_snowmanxxxxxxxxxxxx)
         .next();
      vertexConsumer.vertex((double)_snowmanxxxxxx[1].getX(), (double)_snowmanxxxxxx[1].getY(), (double)_snowmanxxxxxx[1].getZ())
         .texture(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxx)
         .color(this.colorRed, this.colorGreen, this.colorBlue, this.colorAlpha)
         .light(_snowmanxxxxxxxxxxxx)
         .next();
      vertexConsumer.vertex((double)_snowmanxxxxxx[2].getX(), (double)_snowmanxxxxxx[2].getY(), (double)_snowmanxxxxxx[2].getZ())
         .texture(_snowmanxxxxxxxx, _snowmanxxxxxxxxxx)
         .color(this.colorRed, this.colorGreen, this.colorBlue, this.colorAlpha)
         .light(_snowmanxxxxxxxxxxxx)
         .next();
      vertexConsumer.vertex((double)_snowmanxxxxxx[3].getX(), (double)_snowmanxxxxxx[3].getY(), (double)_snowmanxxxxxx[3].getZ())
         .texture(_snowmanxxxxxxxx, _snowmanxxxxxxxxxxx)
         .color(this.colorRed, this.colorGreen, this.colorBlue, this.colorAlpha)
         .light(_snowmanxxxxxxxxxxxx)
         .next();
   }

   public float getSize(float tickDelta) {
      return this.scale;
   }

   @Override
   public Particle scale(float scale) {
      this.scale *= scale;
      return super.scale(scale);
   }

   protected abstract float getMinU();

   protected abstract float getMaxU();

   protected abstract float getMinV();

   protected abstract float getMaxV();
}
