package net.minecraft.client.render.entity;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;

public class ExperienceOrbEntityRenderer extends EntityRenderer<ExperienceOrbEntity> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/experience_orb.png");
   private static final RenderLayer LAYER = RenderLayer.getItemEntityTranslucentCull(TEXTURE);

   public ExperienceOrbEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman);
      this.shadowRadius = 0.15F;
      this.shadowOpacity = 0.75F;
   }

   protected int getBlockLight(ExperienceOrbEntity _snowman, BlockPos _snowman) {
      return MathHelper.clamp(super.getBlockLight(_snowman, _snowman) + 7, 0, 15);
   }

   public void render(ExperienceOrbEntity _snowman, float _snowman, float _snowman, MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman) {
      _snowman.push();
      int _snowmanxxxxxx = _snowman.getOrbSize();
      float _snowmanxxxxxxx = (float)(_snowmanxxxxxx % 4 * 16 + 0) / 64.0F;
      float _snowmanxxxxxxxx = (float)(_snowmanxxxxxx % 4 * 16 + 16) / 64.0F;
      float _snowmanxxxxxxxxx = (float)(_snowmanxxxxxx / 4 * 16 + 0) / 64.0F;
      float _snowmanxxxxxxxxxx = (float)(_snowmanxxxxxx / 4 * 16 + 16) / 64.0F;
      float _snowmanxxxxxxxxxxx = 1.0F;
      float _snowmanxxxxxxxxxxxx = 0.5F;
      float _snowmanxxxxxxxxxxxxx = 0.25F;
      float _snowmanxxxxxxxxxxxxxx = 255.0F;
      float _snowmanxxxxxxxxxxxxxxx = ((float)_snowman.renderTicks + _snowman) / 2.0F;
      int _snowmanxxxxxxxxxxxxxxxx = (int)((MathHelper.sin(_snowmanxxxxxxxxxxxxxxx + 0.0F) + 1.0F) * 0.5F * 255.0F);
      int _snowmanxxxxxxxxxxxxxxxxx = 255;
      int _snowmanxxxxxxxxxxxxxxxxxx = (int)((MathHelper.sin(_snowmanxxxxxxxxxxxxxxx + (float) (Math.PI * 4.0 / 3.0)) + 1.0F) * 0.1F * 255.0F);
      _snowman.translate(0.0, 0.1F, 0.0);
      _snowman.multiply(this.dispatcher.getRotation());
      _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0F));
      float _snowmanxxxxxxxxxxxxxxxxxxx = 0.3F;
      _snowman.scale(0.3F, 0.3F, 0.3F);
      VertexConsumer _snowmanxxxxxxxxxxxxxxxxxxxx = _snowman.getBuffer(LAYER);
      MatrixStack.Entry _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowman.peek();
      Matrix4f _snowmanxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxx.getModel();
      Matrix3f _snowmanxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxx.getNormal();
      method_23171(
         _snowmanxxxxxxxxxxxxxxxxxxxx,
         _snowmanxxxxxxxxxxxxxxxxxxxxxx,
         _snowmanxxxxxxxxxxxxxxxxxxxxxxx,
         -0.5F,
         -0.25F,
         _snowmanxxxxxxxxxxxxxxxx,
         255,
         _snowmanxxxxxxxxxxxxxxxxxx,
         _snowmanxxxxxxx,
         _snowmanxxxxxxxxxx,
         _snowman
      );
      method_23171(
         _snowmanxxxxxxxxxxxxxxxxxxxx,
         _snowmanxxxxxxxxxxxxxxxxxxxxxx,
         _snowmanxxxxxxxxxxxxxxxxxxxxxxx,
         0.5F,
         -0.25F,
         _snowmanxxxxxxxxxxxxxxxx,
         255,
         _snowmanxxxxxxxxxxxxxxxxxx,
         _snowmanxxxxxxxx,
         _snowmanxxxxxxxxxx,
         _snowman
      );
      method_23171(
         _snowmanxxxxxxxxxxxxxxxxxxxx,
         _snowmanxxxxxxxxxxxxxxxxxxxxxx,
         _snowmanxxxxxxxxxxxxxxxxxxxxxxx,
         0.5F,
         0.75F,
         _snowmanxxxxxxxxxxxxxxxx,
         255,
         _snowmanxxxxxxxxxxxxxxxxxx,
         _snowmanxxxxxxxx,
         _snowmanxxxxxxxxx,
         _snowman
      );
      method_23171(
         _snowmanxxxxxxxxxxxxxxxxxxxx,
         _snowmanxxxxxxxxxxxxxxxxxxxxxx,
         _snowmanxxxxxxxxxxxxxxxxxxxxxxx,
         -0.5F,
         0.75F,
         _snowmanxxxxxxxxxxxxxxxx,
         255,
         _snowmanxxxxxxxxxxxxxxxxxx,
         _snowmanxxxxxxx,
         _snowmanxxxxxxxxx,
         _snowman
      );
      _snowman.pop();
      super.render(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   private static void method_23171(VertexConsumer _snowman, Matrix4f _snowman, Matrix3f _snowman, float _snowman, float _snowman, int _snowman, int _snowman, int _snowman, float _snowman, float _snowman, int _snowman) {
      _snowman.vertex(_snowman, _snowman, _snowman, 0.0F).color(_snowman, _snowman, _snowman, 128).texture(_snowman, _snowman).overlay(OverlayTexture.DEFAULT_UV).light(_snowman).normal(_snowman, 0.0F, 1.0F, 0.0F).next();
   }

   public Identifier getTexture(ExperienceOrbEntity _snowman) {
      return TEXTURE;
   }
}
