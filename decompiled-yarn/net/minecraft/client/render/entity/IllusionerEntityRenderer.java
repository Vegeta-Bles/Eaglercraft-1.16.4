package net.minecraft.client.render.entity;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.IllusionerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class IllusionerEntityRenderer extends IllagerEntityRenderer<IllusionerEntity> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/illager/illusioner.png");

   public IllusionerEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman, new IllagerEntityModel<>(0.0F, 0.0F, 64, 64), 0.5F);
      this.addFeature(new HeldItemFeatureRenderer<IllusionerEntity, IllagerEntityModel<IllusionerEntity>>(this) {
         public void render(MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman, IllusionerEntity _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
            if (_snowman.isSpellcasting() || _snowman.isAttacking()) {
               super.render(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
            }
         }
      });
      this.model.getHat().visible = true;
   }

   public Identifier getTexture(IllusionerEntity _snowman) {
      return TEXTURE;
   }

   public void render(IllusionerEntity _snowman, float _snowman, float _snowman, MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman) {
      if (_snowman.isInvisible()) {
         Vec3d[] _snowmanxxxxxx = _snowman.method_7065(_snowman);
         float _snowmanxxxxxxx = this.getAnimationProgress(_snowman, _snowman);

         for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < _snowmanxxxxxx.length; _snowmanxxxxxxxx++) {
            _snowman.push();
            _snowman.translate(
               _snowmanxxxxxx[_snowmanxxxxxxxx].x + (double)MathHelper.cos((float)_snowmanxxxxxxxx + _snowmanxxxxxxx * 0.5F) * 0.025,
               _snowmanxxxxxx[_snowmanxxxxxxxx].y + (double)MathHelper.cos((float)_snowmanxxxxxxxx + _snowmanxxxxxxx * 0.75F) * 0.0125,
               _snowmanxxxxxx[_snowmanxxxxxxxx].z + (double)MathHelper.cos((float)_snowmanxxxxxxxx + _snowmanxxxxxxx * 0.7F) * 0.025
            );
            super.render(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
            _snowman.pop();
         }
      } else {
         super.render(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }

   protected boolean isVisible(IllusionerEntity _snowman) {
      return true;
   }
}
