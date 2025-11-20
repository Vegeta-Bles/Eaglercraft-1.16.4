package net.minecraft.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;

@Environment(EnvType.CLIENT)
public abstract class BillboardParticle extends Particle {
   protected float scale = 0.1F * (this.random.nextFloat() * 0.5F + 0.5F) * 2.0F;

   protected BillboardParticle(ClientWorld arg, double d, double e, double f) {
      super(arg, d, e, f);
   }

   protected BillboardParticle(ClientWorld arg, double d, double e, double f, double g, double h, double i) {
      super(arg, d, e, f, g, h, i);
   }

   @Override
   public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
      Vec3d lv = camera.getPos();
      float g = (float)(MathHelper.lerp((double)tickDelta, this.prevPosX, this.x) - lv.getX());
      float h = (float)(MathHelper.lerp((double)tickDelta, this.prevPosY, this.y) - lv.getY());
      float i = (float)(MathHelper.lerp((double)tickDelta, this.prevPosZ, this.z) - lv.getZ());
      Quaternion lv2;
      if (this.angle == 0.0F) {
         lv2 = camera.getRotation();
      } else {
         lv2 = new Quaternion(camera.getRotation());
         float j = MathHelper.lerp(tickDelta, this.prevAngle, this.angle);
         lv2.hamiltonProduct(Vector3f.POSITIVE_Z.getRadialQuaternion(j));
      }

      Vector3f lv4 = new Vector3f(-1.0F, -1.0F, 0.0F);
      lv4.rotate(lv2);
      Vector3f[] lvs = new Vector3f[]{
         new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)
      };
      float k = this.getSize(tickDelta);

      for (int l = 0; l < 4; l++) {
         Vector3f lv5 = lvs[l];
         lv5.rotate(lv2);
         lv5.scale(k);
         lv5.add(g, h, i);
      }

      float m = this.getMinU();
      float n = this.getMaxU();
      float o = this.getMinV();
      float p = this.getMaxV();
      int q = this.getColorMultiplier(tickDelta);
      vertexConsumer.vertex((double)lvs[0].getX(), (double)lvs[0].getY(), (double)lvs[0].getZ())
         .texture(n, p)
         .color(this.colorRed, this.colorGreen, this.colorBlue, this.colorAlpha)
         .light(q)
         .next();
      vertexConsumer.vertex((double)lvs[1].getX(), (double)lvs[1].getY(), (double)lvs[1].getZ())
         .texture(n, o)
         .color(this.colorRed, this.colorGreen, this.colorBlue, this.colorAlpha)
         .light(q)
         .next();
      vertexConsumer.vertex((double)lvs[2].getX(), (double)lvs[2].getY(), (double)lvs[2].getZ())
         .texture(m, o)
         .color(this.colorRed, this.colorGreen, this.colorBlue, this.colorAlpha)
         .light(q)
         .next();
      vertexConsumer.vertex((double)lvs[3].getX(), (double)lvs[3].getY(), (double)lvs[3].getZ())
         .texture(m, p)
         .color(this.colorRed, this.colorGreen, this.colorBlue, this.colorAlpha)
         .light(q)
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
