package net.minecraft.client.render.entity.feature;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class TridentRiptideFeatureRenderer<T extends LivingEntity> extends FeatureRenderer<T, PlayerEntityModel<T>> {
   public static final Identifier TEXTURE = new Identifier("textures/entity/trident_riptide.png");
   private final ModelPart aura = new ModelPart(64, 64, 0, 0);

   public TridentRiptideFeatureRenderer(FeatureRendererContext<T, PlayerEntityModel<T>> _snowman) {
      super(_snowman);
      this.aura.addCuboid(-8.0F, -16.0F, -8.0F, 16.0F, 32.0F, 16.0F);
   }

   public void render(MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman, T _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      if (_snowman.isUsingRiptide()) {
         VertexConsumer _snowmanxxxxxxxxxx = _snowman.getBuffer(RenderLayer.getEntityCutoutNoCull(TEXTURE));

         for (int _snowmanxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxx < 3; _snowmanxxxxxxxxxxx++) {
            _snowman.push();
            float _snowmanxxxxxxxxxxxx = _snowman * (float)(-(45 + _snowmanxxxxxxxxxxx * 5));
            _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(_snowmanxxxxxxxxxxxx));
            float _snowmanxxxxxxxxxxxxx = 0.75F * (float)_snowmanxxxxxxxxxxx;
            _snowman.scale(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx);
            _snowman.translate(0.0, (double)(-0.2F + 0.6F * (float)_snowmanxxxxxxxxxxx), 0.0);
            this.aura.render(_snowman, _snowmanxxxxxxxxxx, _snowman, OverlayTexture.DEFAULT_UV);
            _snowman.pop();
         }
      }
   }
}
