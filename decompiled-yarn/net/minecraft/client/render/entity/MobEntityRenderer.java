package net.minecraft.client.render.entity;

import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LightType;

public abstract class MobEntityRenderer<T extends MobEntity, M extends EntityModel<T>> extends LivingEntityRenderer<T, M> {
   public MobEntityRenderer(EntityRenderDispatcher _snowman, M _snowman, float _snowman) {
      super(_snowman, _snowman, _snowman);
   }

   protected boolean hasLabel(T _snowman) {
      return super.hasLabel(_snowman) && (_snowman.shouldRenderName() || _snowman.hasCustomName() && _snowman == this.dispatcher.targetedEntity);
   }

   public boolean shouldRender(T _snowman, Frustum _snowman, double _snowman, double _snowman, double _snowman) {
      if (super.shouldRender(_snowman, _snowman, _snowman, _snowman, _snowman)) {
         return true;
      } else {
         Entity _snowmanxxxxx = _snowman.getHoldingEntity();
         return _snowmanxxxxx != null ? _snowman.isVisible(_snowmanxxxxx.getVisibilityBoundingBox()) : false;
      }
   }

   public void render(T _snowman, float _snowman, float _snowman, MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman) {
      super.render(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      Entity _snowmanxxxxxx = _snowman.getHoldingEntity();
      if (_snowmanxxxxxx != null) {
         this.method_4073(_snowman, _snowman, _snowman, _snowman, _snowmanxxxxxx);
      }
   }

   private <E extends Entity> void method_4073(T _snowman, float _snowman, MatrixStack _snowman, VertexConsumerProvider _snowman, E _snowman) {
      _snowman.push();
      Vec3d _snowmanxxxxx = _snowman.method_30951(_snowman);
      double _snowmanxxxxxx = (double)(MathHelper.lerp(_snowman, _snowman.bodyYaw, _snowman.prevBodyYaw) * (float) (Math.PI / 180.0)) + (Math.PI / 2);
      Vec3d _snowmanxxxxxxx = _snowman.method_29919();
      double _snowmanxxxxxxxx = Math.cos(_snowmanxxxxxx) * _snowmanxxxxxxx.z + Math.sin(_snowmanxxxxxx) * _snowmanxxxxxxx.x;
      double _snowmanxxxxxxxxx = Math.sin(_snowmanxxxxxx) * _snowmanxxxxxxx.z - Math.cos(_snowmanxxxxxx) * _snowmanxxxxxxx.x;
      double _snowmanxxxxxxxxxx = MathHelper.lerp((double)_snowman, _snowman.prevX, _snowman.getX()) + _snowmanxxxxxxxx;
      double _snowmanxxxxxxxxxxx = MathHelper.lerp((double)_snowman, _snowman.prevY, _snowman.getY()) + _snowmanxxxxxxx.y;
      double _snowmanxxxxxxxxxxxx = MathHelper.lerp((double)_snowman, _snowman.prevZ, _snowman.getZ()) + _snowmanxxxxxxxxx;
      _snowman.translate(_snowmanxxxxxxxx, _snowmanxxxxxxx.y, _snowmanxxxxxxxxx);
      float _snowmanxxxxxxxxxxxxx = (float)(_snowmanxxxxx.x - _snowmanxxxxxxxxxx);
      float _snowmanxxxxxxxxxxxxxx = (float)(_snowmanxxxxx.y - _snowmanxxxxxxxxxxx);
      float _snowmanxxxxxxxxxxxxxxx = (float)(_snowmanxxxxx.z - _snowmanxxxxxxxxxxxx);
      float _snowmanxxxxxxxxxxxxxxxx = 0.025F;
      VertexConsumer _snowmanxxxxxxxxxxxxxxxxx = _snowman.getBuffer(RenderLayer.getLeash());
      Matrix4f _snowmanxxxxxxxxxxxxxxxxxx = _snowman.peek().getModel();
      float _snowmanxxxxxxxxxxxxxxxxxxx = MathHelper.fastInverseSqrt(_snowmanxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxx) * 0.025F / 2.0F;
      float _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxx;
      float _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxx;
      BlockPos _snowmanxxxxxxxxxxxxxxxxxxxxxx = new BlockPos(_snowman.getCameraPosVec(_snowman));
      BlockPos _snowmanxxxxxxxxxxxxxxxxxxxxxxx = new BlockPos(_snowman.getCameraPosVec(_snowman));
      int _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = this.getBlockLight(_snowman, _snowmanxxxxxxxxxxxxxxxxxxxxxx);
      int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = this.dispatcher.getRenderer(_snowman).getBlockLight(_snowman, _snowmanxxxxxxxxxxxxxxxxxxxxxxx);
      int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.world.getLightLevel(LightType.SKY, _snowmanxxxxxxxxxxxxxxxxxxxxxx);
      int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.world.getLightLevel(LightType.SKY, _snowmanxxxxxxxxxxxxxxxxxxxxxxx);
      method_23186(
         _snowmanxxxxxxxxxxxxxxxxx,
         _snowmanxxxxxxxxxxxxxxxxxx,
         _snowmanxxxxxxxxxxxxx,
         _snowmanxxxxxxxxxxxxxx,
         _snowmanxxxxxxxxxxxxxxx,
         _snowmanxxxxxxxxxxxxxxxxxxxxxxxx,
         _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx,
         _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx,
         _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx,
         0.025F,
         0.025F,
         _snowmanxxxxxxxxxxxxxxxxxxxx,
         _snowmanxxxxxxxxxxxxxxxxxxxxx
      );
      method_23186(
         _snowmanxxxxxxxxxxxxxxxxx,
         _snowmanxxxxxxxxxxxxxxxxxx,
         _snowmanxxxxxxxxxxxxx,
         _snowmanxxxxxxxxxxxxxx,
         _snowmanxxxxxxxxxxxxxxx,
         _snowmanxxxxxxxxxxxxxxxxxxxxxxxx,
         _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx,
         _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx,
         _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx,
         0.025F,
         0.0F,
         _snowmanxxxxxxxxxxxxxxxxxxxx,
         _snowmanxxxxxxxxxxxxxxxxxxxxx
      );
      _snowman.pop();
   }

   public static void method_23186(VertexConsumer _snowman, Matrix4f _snowman, float _snowman, float _snowman, float _snowman, int _snowman, int _snowman, int _snowman, int _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      int _snowmanxxxxxxxxxxxxx = 24;

      for (int _snowmanxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxx < 24; _snowmanxxxxxxxxxxxxxx++) {
         float _snowmanxxxxxxxxxxxxxxx = (float)_snowmanxxxxxxxxxxxxxx / 23.0F;
         int _snowmanxxxxxxxxxxxxxxxx = (int)MathHelper.lerp(_snowmanxxxxxxxxxxxxxxx, (float)_snowman, (float)_snowman);
         int _snowmanxxxxxxxxxxxxxxxxx = (int)MathHelper.lerp(_snowmanxxxxxxxxxxxxxxx, (float)_snowman, (float)_snowman);
         int _snowmanxxxxxxxxxxxxxxxxxx = LightmapTextureManager.pack(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx);
         method_23187(_snowman, _snowman, _snowmanxxxxxxxxxxxxxxxxxx, _snowman, _snowman, _snowman, _snowman, _snowman, 24, _snowmanxxxxxxxxxxxxxx, false, _snowman, _snowman);
         method_23187(_snowman, _snowman, _snowmanxxxxxxxxxxxxxxxxxx, _snowman, _snowman, _snowman, _snowman, _snowman, 24, _snowmanxxxxxxxxxxxxxx + 1, true, _snowman, _snowman);
      }
   }

   public static void method_23187(VertexConsumer _snowman, Matrix4f _snowman, int _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman, int _snowman, int _snowman, boolean _snowman, float _snowman, float _snowman) {
      float _snowmanxxxxxxxxxxxxx = 0.5F;
      float _snowmanxxxxxxxxxxxxxx = 0.4F;
      float _snowmanxxxxxxxxxxxxxxx = 0.3F;
      if (_snowman % 2 == 0) {
         _snowmanxxxxxxxxxxxxx *= 0.7F;
         _snowmanxxxxxxxxxxxxxx *= 0.7F;
         _snowmanxxxxxxxxxxxxxxx *= 0.7F;
      }

      float _snowmanxxxxxxxxxxxxxxxx = (float)_snowman / (float)_snowman;
      float _snowmanxxxxxxxxxxxxxxxxx = _snowman * _snowmanxxxxxxxxxxxxxxxx;
      float _snowmanxxxxxxxxxxxxxxxxxx = _snowman > 0.0F ? _snowman * _snowmanxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxx : _snowman - _snowman * (1.0F - _snowmanxxxxxxxxxxxxxxxx) * (1.0F - _snowmanxxxxxxxxxxxxxxxx);
      float _snowmanxxxxxxxxxxxxxxxxxxx = _snowman * _snowmanxxxxxxxxxxxxxxxx;
      if (!_snowman) {
         _snowman.vertex(_snowman, _snowmanxxxxxxxxxxxxxxxxx + _snowman, _snowmanxxxxxxxxxxxxxxxxxx + _snowman - _snowman, _snowmanxxxxxxxxxxxxxxxxxxx - _snowman)
            .color(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, 1.0F)
            .light(_snowman)
            .next();
      }

      _snowman.vertex(_snowman, _snowmanxxxxxxxxxxxxxxxxx - _snowman, _snowmanxxxxxxxxxxxxxxxxxx + _snowman, _snowmanxxxxxxxxxxxxxxxxxxx + _snowman)
         .color(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, 1.0F)
         .light(_snowman)
         .next();
      if (_snowman) {
         _snowman.vertex(_snowman, _snowmanxxxxxxxxxxxxxxxxx + _snowman, _snowmanxxxxxxxxxxxxxxxxxx + _snowman - _snowman, _snowmanxxxxxxxxxxxxxxxxxxx - _snowman)
            .color(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, 1.0F)
            .light(_snowman)
            .next();
      }
   }
}
