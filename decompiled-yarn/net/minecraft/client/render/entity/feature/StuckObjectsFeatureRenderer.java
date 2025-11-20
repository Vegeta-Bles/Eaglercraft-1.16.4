package net.minecraft.client.render.entity.feature;

import java.util.Random;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

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

   public void render(MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman, T _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      int _snowmanxxxxxxxxxx = this.getObjectCount(_snowman);
      Random _snowmanxxxxxxxxxxx = new Random((long)_snowman.getEntityId());
      if (_snowmanxxxxxxxxxx > 0) {
         for (int _snowmanxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxx < _snowmanxxxxxxxxxx; _snowmanxxxxxxxxxxxx++) {
            _snowman.push();
            ModelPart _snowmanxxxxxxxxxxxxx = this.getContextModel().getRandomPart(_snowmanxxxxxxxxxxx);
            ModelPart.Cuboid _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx.getRandomCuboid(_snowmanxxxxxxxxxxx);
            _snowmanxxxxxxxxxxxxx.rotate(_snowman);
            float _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.nextFloat();
            float _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.nextFloat();
            float _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.nextFloat();
            float _snowmanxxxxxxxxxxxxxxxxxx = MathHelper.lerp(_snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx.minX, _snowmanxxxxxxxxxxxxxx.maxX) / 16.0F;
            float _snowmanxxxxxxxxxxxxxxxxxxx = MathHelper.lerp(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx.minY, _snowmanxxxxxxxxxxxxxx.maxY) / 16.0F;
            float _snowmanxxxxxxxxxxxxxxxxxxxx = MathHelper.lerp(_snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx.minZ, _snowmanxxxxxxxxxxxxxx.maxZ) / 16.0F;
            _snowman.translate((double)_snowmanxxxxxxxxxxxxxxxxxx, (double)_snowmanxxxxxxxxxxxxxxxxxxx, (double)_snowmanxxxxxxxxxxxxxxxxxxxx);
            _snowmanxxxxxxxxxxxxxxx = -1.0F * (_snowmanxxxxxxxxxxxxxxx * 2.0F - 1.0F);
            _snowmanxxxxxxxxxxxxxxxx = -1.0F * (_snowmanxxxxxxxxxxxxxxxx * 2.0F - 1.0F);
            _snowmanxxxxxxxxxxxxxxxxx = -1.0F * (_snowmanxxxxxxxxxxxxxxxxx * 2.0F - 1.0F);
            this.renderObject(_snowman, _snowman, _snowman, _snowman, _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, _snowman);
            _snowman.pop();
         }
      }
   }
}
