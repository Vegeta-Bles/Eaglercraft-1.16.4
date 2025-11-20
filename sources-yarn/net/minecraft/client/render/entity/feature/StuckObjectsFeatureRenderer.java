package net.minecraft.client.render.entity.feature;

import java.util.Random;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public abstract class StuckObjectsFeatureRenderer<T extends LivingEntity, M extends PlayerEntityModel<T>> extends FeatureRenderer<T, M> {
   public StuckObjectsFeatureRenderer(LivingEntityRenderer<T, M> entityRenderer) {
      super(entityRenderer);
   }

   protected abstract int getObjectCount(T entity);

   protected abstract void renderObject(
      MatrixStack matrices,
      VertexConsumerProvider vertexConsumers,
      int light,
      Entity entity,
      float directionX,
      float directionY,
      float directionZ,
      float tickDelta
   );

   public void render(MatrixStack arg, VertexConsumerProvider arg2, int i, T arg3, float f, float g, float h, float j, float k, float l) {
      int m = this.getObjectCount(arg3);
      Random random = new Random((long)arg3.getEntityId());
      if (m > 0) {
         for (int n = 0; n < m; n++) {
            arg.push();
            ModelPart lv = this.getContextModel().getRandomPart(random);
            ModelPart.Cuboid lv2 = lv.getRandomCuboid(random);
            lv.rotate(arg);
            float o = random.nextFloat();
            float p = random.nextFloat();
            float q = random.nextFloat();
            float r = MathHelper.lerp(o, lv2.minX, lv2.maxX) / 16.0F;
            float s = MathHelper.lerp(p, lv2.minY, lv2.maxY) / 16.0F;
            float t = MathHelper.lerp(q, lv2.minZ, lv2.maxZ) / 16.0F;
            arg.translate((double)r, (double)s, (double)t);
            o = -1.0F * (o * 2.0F - 1.0F);
            p = -1.0F * (p * 2.0F - 1.0F);
            q = -1.0F * (q * 2.0F - 1.0F);
            this.renderObject(arg, arg2, i, arg3, o, p, q, h);
            arg.pop();
         }
      }
   }
}
