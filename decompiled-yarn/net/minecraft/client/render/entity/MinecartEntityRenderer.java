package net.minecraft.client.render.entity;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.MinecartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class MinecartEntityRenderer<T extends AbstractMinecartEntity> extends EntityRenderer<T> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/minecart.png");
   protected final EntityModel<T> model = new MinecartEntityModel<>();

   public MinecartEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman);
      this.shadowRadius = 0.7F;
   }

   public void render(T _snowman, float _snowman, float _snowman, MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman) {
      super.render(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      _snowman.push();
      long _snowmanxxxxxx = (long)_snowman.getEntityId() * 493286711L;
      _snowmanxxxxxx = _snowmanxxxxxx * _snowmanxxxxxx * 4392167121L + _snowmanxxxxxx * 98761L;
      float _snowmanxxxxxxx = (((float)(_snowmanxxxxxx >> 16 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
      float _snowmanxxxxxxxx = (((float)(_snowmanxxxxxx >> 20 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
      float _snowmanxxxxxxxxx = (((float)(_snowmanxxxxxx >> 24 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
      _snowman.translate((double)_snowmanxxxxxxx, (double)_snowmanxxxxxxxx, (double)_snowmanxxxxxxxxx);
      double _snowmanxxxxxxxxxx = MathHelper.lerp((double)_snowman, _snowman.lastRenderX, _snowman.getX());
      double _snowmanxxxxxxxxxxx = MathHelper.lerp((double)_snowman, _snowman.lastRenderY, _snowman.getY());
      double _snowmanxxxxxxxxxxxx = MathHelper.lerp((double)_snowman, _snowman.lastRenderZ, _snowman.getZ());
      double _snowmanxxxxxxxxxxxxx = 0.3F;
      Vec3d _snowmanxxxxxxxxxxxxxx = _snowman.snapPositionToRail(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx);
      float _snowmanxxxxxxxxxxxxxxx = MathHelper.lerp(_snowman, _snowman.prevPitch, _snowman.pitch);
      if (_snowmanxxxxxxxxxxxxxx != null) {
         Vec3d _snowmanxxxxxxxxxxxxxxxx = _snowman.snapPositionToRailWithOffset(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, 0.3F);
         Vec3d _snowmanxxxxxxxxxxxxxxxxx = _snowman.snapPositionToRailWithOffset(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, -0.3F);
         if (_snowmanxxxxxxxxxxxxxxxx == null) {
            _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx;
         }

         if (_snowmanxxxxxxxxxxxxxxxxx == null) {
            _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx;
         }

         _snowman.translate(_snowmanxxxxxxxxxxxxxx.x - _snowmanxxxxxxxxxx, (_snowmanxxxxxxxxxxxxxxxx.y + _snowmanxxxxxxxxxxxxxxxxx.y) / 2.0 - _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx.z - _snowmanxxxxxxxxxxxx);
         Vec3d _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx.add(-_snowmanxxxxxxxxxxxxxxxx.x, -_snowmanxxxxxxxxxxxxxxxx.y, -_snowmanxxxxxxxxxxxxxxxx.z);
         if (_snowmanxxxxxxxxxxxxxxxxxx.length() != 0.0) {
            _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxx.normalize();
            _snowman = (float)(Math.atan2(_snowmanxxxxxxxxxxxxxxxxxx.z, _snowmanxxxxxxxxxxxxxxxxxx.x) * 180.0 / Math.PI);
            _snowmanxxxxxxxxxxxxxxx = (float)(Math.atan(_snowmanxxxxxxxxxxxxxxxxxx.y) * 73.0);
         }
      }

      _snowman.translate(0.0, 0.375, 0.0);
      _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0F - _snowman));
      _snowman.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(-_snowmanxxxxxxxxxxxxxxx));
      float _snowmanxxxxxxxxxxxxxxxxxx = (float)_snowman.getDamageWobbleTicks() - _snowman;
      float _snowmanxxxxxxxxxxxxxxxxxxx = _snowman.getDamageWobbleStrength() - _snowman;
      if (_snowmanxxxxxxxxxxxxxxxxxxx < 0.0F) {
         _snowmanxxxxxxxxxxxxxxxxxxx = 0.0F;
      }

      if (_snowmanxxxxxxxxxxxxxxxxxx > 0.0F) {
         _snowman.multiply(
            Vector3f.POSITIVE_X
               .getDegreesQuaternion(MathHelper.sin(_snowmanxxxxxxxxxxxxxxxxxx) * _snowmanxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxx / 10.0F * (float)_snowman.getDamageWobbleSide())
         );
      }

      int _snowmanxxxxxxxxxxxxxxxxxxxx = _snowman.getBlockOffset();
      BlockState _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowman.getContainedBlock();
      if (_snowmanxxxxxxxxxxxxxxxxxxxxx.getRenderType() != BlockRenderType.INVISIBLE) {
         _snowman.push();
         float _snowmanxxxxxxxxxxxxxxxxxxxxxx = 0.75F;
         _snowman.scale(0.75F, 0.75F, 0.75F);
         _snowman.translate(-0.5, (double)((float)(_snowmanxxxxxxxxxxxxxxxxxxxx - 8) / 16.0F), 0.5);
         _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(90.0F));
         this.renderBlock(_snowman, _snowman, _snowmanxxxxxxxxxxxxxxxxxxxxx, _snowman, _snowman, _snowman);
         _snowman.pop();
      }

      _snowman.scale(-1.0F, -1.0F, 1.0F);
      this.model.setAngles(_snowman, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F);
      VertexConsumer _snowmanxxxxxxxxxxxxxxxxxxxxxx = _snowman.getBuffer(this.model.getLayer(this.getTexture(_snowman)));
      this.model.render(_snowman, _snowmanxxxxxxxxxxxxxxxxxxxxxx, _snowman, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
      _snowman.pop();
   }

   public Identifier getTexture(T _snowman) {
      return TEXTURE;
   }

   protected void renderBlock(T entity, float delta, BlockState state, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
      MinecraftClient.getInstance().getBlockRenderManager().renderBlockAsEntity(state, matrices, vertexConsumers, light, OverlayTexture.DEFAULT_UV);
   }
}
