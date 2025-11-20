package net.minecraft.client.render.block.entity;

import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.MobSpawnerLogic;

public class MobSpawnerBlockEntityRenderer extends BlockEntityRenderer<MobSpawnerBlockEntity> {
   public MobSpawnerBlockEntityRenderer(BlockEntityRenderDispatcher _snowman) {
      super(_snowman);
   }

   public void render(MobSpawnerBlockEntity _snowman, float _snowman, MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman, int _snowman) {
      _snowman.push();
      _snowman.translate(0.5, 0.0, 0.5);
      MobSpawnerLogic _snowmanxxxxxx = _snowman.getLogic();
      Entity _snowmanxxxxxxx = _snowmanxxxxxx.getRenderedEntity();
      if (_snowmanxxxxxxx != null) {
         float _snowmanxxxxxxxx = 0.53125F;
         float _snowmanxxxxxxxxx = Math.max(_snowmanxxxxxxx.getWidth(), _snowmanxxxxxxx.getHeight());
         if ((double)_snowmanxxxxxxxxx > 1.0) {
            _snowmanxxxxxxxx /= _snowmanxxxxxxxxx;
         }

         _snowman.translate(0.0, 0.4F, 0.0);
         _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion((float)MathHelper.lerp((double)_snowman, _snowmanxxxxxx.method_8279(), _snowmanxxxxxx.method_8278()) * 10.0F));
         _snowman.translate(0.0, -0.2F, 0.0);
         _snowman.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(-30.0F));
         _snowman.scale(_snowmanxxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxx);
         MinecraftClient.getInstance().getEntityRenderDispatcher().render(_snowmanxxxxxxx, 0.0, 0.0, 0.0, 0.0F, _snowman, _snowman, _snowman, _snowman);
      }

      _snowman.pop();
   }
}
