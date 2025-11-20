package net.minecraft.client.render.entity;

import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.GuardianEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.GuardianEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;

public class GuardianEntityRenderer extends MobEntityRenderer<GuardianEntity, GuardianEntityModel> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/guardian.png");
   private static final Identifier EXPLOSION_BEAM_TEXTURE = new Identifier("textures/entity/guardian_beam.png");
   private static final RenderLayer LAYER = RenderLayer.getEntityCutoutNoCull(EXPLOSION_BEAM_TEXTURE);

   public GuardianEntityRenderer(EntityRenderDispatcher _snowman) {
      this(_snowman, 0.5F);
   }

   protected GuardianEntityRenderer(EntityRenderDispatcher dispatcher, float _snowman) {
      super(dispatcher, new GuardianEntityModel(), _snowman);
   }

   public boolean shouldRender(GuardianEntity _snowman, Frustum _snowman, double _snowman, double _snowman, double _snowman) {
      if (super.shouldRender(_snowman, _snowman, _snowman, _snowman, _snowman)) {
         return true;
      } else {
         if (_snowman.hasBeamTarget()) {
            LivingEntity _snowmanxxxxx = _snowman.getBeamTarget();
            if (_snowmanxxxxx != null) {
               Vec3d _snowmanxxxxxx = this.fromLerpedPosition(_snowmanxxxxx, (double)_snowmanxxxxx.getHeight() * 0.5, 1.0F);
               Vec3d _snowmanxxxxxxx = this.fromLerpedPosition(_snowman, (double)_snowman.getStandingEyeHeight(), 1.0F);
               return _snowman.isVisible(new Box(_snowmanxxxxxxx.x, _snowmanxxxxxxx.y, _snowmanxxxxxxx.z, _snowmanxxxxxx.x, _snowmanxxxxxx.y, _snowmanxxxxxx.z));
            }
         }

         return false;
      }
   }

   private Vec3d fromLerpedPosition(LivingEntity entity, double yOffset, float delta) {
      double _snowman = MathHelper.lerp((double)delta, entity.lastRenderX, entity.getX());
      double _snowmanx = MathHelper.lerp((double)delta, entity.lastRenderY, entity.getY()) + yOffset;
      double _snowmanxx = MathHelper.lerp((double)delta, entity.lastRenderZ, entity.getZ());
      return new Vec3d(_snowman, _snowmanx, _snowmanxx);
   }

   public void render(GuardianEntity _snowman, float _snowman, float _snowman, MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman) {
      super.render(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      LivingEntity _snowmanxxxxxx = _snowman.getBeamTarget();
      if (_snowmanxxxxxx != null) {
         float _snowmanxxxxxxx = _snowman.getBeamProgress(_snowman);
         float _snowmanxxxxxxxx = (float)_snowman.world.getTime() + _snowman;
         float _snowmanxxxxxxxxx = _snowmanxxxxxxxx * 0.5F % 1.0F;
         float _snowmanxxxxxxxxxx = _snowman.getStandingEyeHeight();
         _snowman.push();
         _snowman.translate(0.0, (double)_snowmanxxxxxxxxxx, 0.0);
         Vec3d _snowmanxxxxxxxxxxx = this.fromLerpedPosition(_snowmanxxxxxx, (double)_snowmanxxxxxx.getHeight() * 0.5, _snowman);
         Vec3d _snowmanxxxxxxxxxxxx = this.fromLerpedPosition(_snowman, (double)_snowmanxxxxxxxxxx, _snowman);
         Vec3d _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.subtract(_snowmanxxxxxxxxxxxx);
         float _snowmanxxxxxxxxxxxxxx = (float)(_snowmanxxxxxxxxxxxxx.length() + 1.0);
         _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx.normalize();
         float _snowmanxxxxxxxxxxxxxxx = (float)Math.acos(_snowmanxxxxxxxxxxxxx.y);
         float _snowmanxxxxxxxxxxxxxxxx = (float)Math.atan2(_snowmanxxxxxxxxxxxxx.z, _snowmanxxxxxxxxxxxxx.x);
         _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(((float) (Math.PI / 2) - _snowmanxxxxxxxxxxxxxxxx) * (180.0F / (float)Math.PI)));
         _snowman.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(_snowmanxxxxxxxxxxxxxxx * (180.0F / (float)Math.PI)));
         int _snowmanxxxxxxxxxxxxxxxxx = 1;
         float _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxx * 0.05F * -1.5F;
         float _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxx * _snowmanxxxxxxx;
         int _snowmanxxxxxxxxxxxxxxxxxxxx = 64 + (int)(_snowmanxxxxxxxxxxxxxxxxxxx * 191.0F);
         int _snowmanxxxxxxxxxxxxxxxxxxxxx = 32 + (int)(_snowmanxxxxxxxxxxxxxxxxxxx * 191.0F);
         int _snowmanxxxxxxxxxxxxxxxxxxxxxx = 128 - (int)(_snowmanxxxxxxxxxxxxxxxxxxx * 64.0F);
         float _snowmanxxxxxxxxxxxxxxxxxxxxxxx = 0.2F;
         float _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = 0.282F;
         float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = MathHelper.cos(_snowmanxxxxxxxxxxxxxxxxxx + (float) (Math.PI * 3.0 / 4.0)) * 0.282F;
         float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = MathHelper.sin(_snowmanxxxxxxxxxxxxxxxxxx + (float) (Math.PI * 3.0 / 4.0)) * 0.282F;
         float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = MathHelper.cos(_snowmanxxxxxxxxxxxxxxxxxx + (float) (Math.PI / 4)) * 0.282F;
         float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = MathHelper.sin(_snowmanxxxxxxxxxxxxxxxxxx + (float) (Math.PI / 4)) * 0.282F;
         float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = MathHelper.cos(_snowmanxxxxxxxxxxxxxxxxxx + ((float) Math.PI * 5.0F / 4.0F)) * 0.282F;
         float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = MathHelper.sin(_snowmanxxxxxxxxxxxxxxxxxx + ((float) Math.PI * 5.0F / 4.0F)) * 0.282F;
         float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = MathHelper.cos(_snowmanxxxxxxxxxxxxxxxxxx + ((float) Math.PI * 7.0F / 4.0F)) * 0.282F;
         float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = MathHelper.sin(_snowmanxxxxxxxxxxxxxxxxxx + ((float) Math.PI * 7.0F / 4.0F)) * 0.282F;
         float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = MathHelper.cos(_snowmanxxxxxxxxxxxxxxxxxx + (float) Math.PI) * 0.2F;
         float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = MathHelper.sin(_snowmanxxxxxxxxxxxxxxxxxx + (float) Math.PI) * 0.2F;
         float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = MathHelper.cos(_snowmanxxxxxxxxxxxxxxxxxx + 0.0F) * 0.2F;
         float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = MathHelper.sin(_snowmanxxxxxxxxxxxxxxxxxx + 0.0F) * 0.2F;
         float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = MathHelper.cos(_snowmanxxxxxxxxxxxxxxxxxx + (float) (Math.PI / 2)) * 0.2F;
         float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = MathHelper.sin(_snowmanxxxxxxxxxxxxxxxxxx + (float) (Math.PI / 2)) * 0.2F;
         float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = MathHelper.cos(_snowmanxxxxxxxxxxxxxxxxxx + (float) (Math.PI * 3.0 / 2.0)) * 0.2F;
         float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = MathHelper.sin(_snowmanxxxxxxxxxxxxxxxxxx + (float) (Math.PI * 3.0 / 2.0)) * 0.2F;
         float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0.0F;
         float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0.4999F;
         float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = -1.0F + _snowmanxxxxxxxxx;
         float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx * 2.5F + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
         VertexConsumer _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.getBuffer(LAYER);
         MatrixStack.Entry _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.peek();
         Matrix4f _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getModel();
         Matrix3f _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getNormal();
         method_23173(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxx,
            0.4999F,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
         );
         method_23173(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            0.0F,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxx,
            0.4999F,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
         );
         method_23173(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            0.0F,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxx,
            0.0F,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
         );
         method_23173(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxx,
            0.0F,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
         );
         method_23173(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxx,
            0.4999F,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
         );
         method_23173(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            0.0F,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxx,
            0.4999F,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
         );
         method_23173(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            0.0F,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxx,
            0.0F,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
         );
         method_23173(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxx,
            0.0F,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
         );
         float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0.0F;
         if (_snowman.age % 2 == 0) {
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0.5F;
         }

         method_23173(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxx,
            0.5F,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx + 0.5F
         );
         method_23173(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxx,
            1.0F,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx + 0.5F
         );
         method_23173(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxx,
            1.0F,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
         );
         method_23173(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxx,
            0.5F,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
         );
         _snowman.pop();
      }
   }

   private static void method_23173(VertexConsumer _snowman, Matrix4f _snowman, Matrix3f _snowman, float _snowman, float _snowman, float _snowman, int _snowman, int _snowman, int _snowman, float _snowman, float _snowman) {
      _snowman.vertex(_snowman, _snowman, _snowman, _snowman).color(_snowman, _snowman, _snowman, 255).texture(_snowman, _snowman).overlay(OverlayTexture.DEFAULT_UV).light(15728880).normal(_snowman, 0.0F, 1.0F, 0.0F).next();
   }

   public Identifier getTexture(GuardianEntity _snowman) {
      return TEXTURE;
   }
}
