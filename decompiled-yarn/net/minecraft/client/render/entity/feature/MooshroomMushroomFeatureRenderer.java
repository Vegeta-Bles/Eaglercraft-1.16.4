package net.minecraft.client.render.entity.feature;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.CowEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.passive.MooshroomEntity;

public class MooshroomMushroomFeatureRenderer<T extends MooshroomEntity> extends FeatureRenderer<T, CowEntityModel<T>> {
   public MooshroomMushroomFeatureRenderer(FeatureRendererContext<T, CowEntityModel<T>> _snowman) {
      super(_snowman);
   }

   public void render(MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman, T _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      if (!_snowman.isBaby() && !_snowman.isInvisible()) {
         BlockRenderManager _snowmanxxxxxxxxxx = MinecraftClient.getInstance().getBlockRenderManager();
         BlockState _snowmanxxxxxxxxxxx = _snowman.getMooshroomType().getMushroomState();
         int _snowmanxxxxxxxxxxxx = LivingEntityRenderer.getOverlay(_snowman, 0.0F);
         _snowman.push();
         _snowman.translate(0.2F, -0.35F, 0.5);
         _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-48.0F));
         _snowman.scale(-1.0F, -1.0F, 1.0F);
         _snowman.translate(-0.5, -0.5, -0.5);
         _snowmanxxxxxxxxxx.renderBlockAsEntity(_snowmanxxxxxxxxxxx, _snowman, _snowman, _snowman, _snowmanxxxxxxxxxxxx);
         _snowman.pop();
         _snowman.push();
         _snowman.translate(0.2F, -0.35F, 0.5);
         _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(42.0F));
         _snowman.translate(0.1F, 0.0, -0.6F);
         _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-48.0F));
         _snowman.scale(-1.0F, -1.0F, 1.0F);
         _snowman.translate(-0.5, -0.5, -0.5);
         _snowmanxxxxxxxxxx.renderBlockAsEntity(_snowmanxxxxxxxxxxx, _snowman, _snowman, _snowman, _snowmanxxxxxxxxxxxx);
         _snowman.pop();
         _snowman.push();
         this.getContextModel().getHead().rotate(_snowman);
         _snowman.translate(0.0, -0.7F, -0.2F);
         _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-78.0F));
         _snowman.scale(-1.0F, -1.0F, 1.0F);
         _snowman.translate(-0.5, -0.5, -0.5);
         _snowmanxxxxxxxxxx.renderBlockAsEntity(_snowmanxxxxxxxxxxx, _snowman, _snowman, _snowman, _snowmanxxxxxxxxxxxx);
         _snowman.pop();
      }
   }
}
