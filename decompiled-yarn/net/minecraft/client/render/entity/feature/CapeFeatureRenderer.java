package net.minecraft.client.render.entity.feature;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.MathHelper;

public class CapeFeatureRenderer extends FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
   public CapeFeatureRenderer(FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> _snowman) {
      super(_snowman);
   }

   public void render(MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman, AbstractClientPlayerEntity _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      if (_snowman.canRenderCapeTexture() && !_snowman.isInvisible() && _snowman.isPartVisible(PlayerModelPart.CAPE) && _snowman.getCapeTexture() != null) {
         ItemStack _snowmanxxxxxxxxxx = _snowman.getEquippedStack(EquipmentSlot.CHEST);
         if (_snowmanxxxxxxxxxx.getItem() != Items.ELYTRA) {
            _snowman.push();
            _snowman.translate(0.0, 0.0, 0.125);
            double _snowmanxxxxxxxxxxx = MathHelper.lerp((double)_snowman, _snowman.prevCapeX, _snowman.capeX) - MathHelper.lerp((double)_snowman, _snowman.prevX, _snowman.getX());
            double _snowmanxxxxxxxxxxxx = MathHelper.lerp((double)_snowman, _snowman.prevCapeY, _snowman.capeY) - MathHelper.lerp((double)_snowman, _snowman.prevY, _snowman.getY());
            double _snowmanxxxxxxxxxxxxx = MathHelper.lerp((double)_snowman, _snowman.prevCapeZ, _snowman.capeZ) - MathHelper.lerp((double)_snowman, _snowman.prevZ, _snowman.getZ());
            float _snowmanxxxxxxxxxxxxxx = _snowman.prevBodyYaw + (_snowman.bodyYaw - _snowman.prevBodyYaw);
            double _snowmanxxxxxxxxxxxxxxx = (double)MathHelper.sin(_snowmanxxxxxxxxxxxxxx * (float) (Math.PI / 180.0));
            double _snowmanxxxxxxxxxxxxxxxx = (double)(-MathHelper.cos(_snowmanxxxxxxxxxxxxxx * (float) (Math.PI / 180.0)));
            float _snowmanxxxxxxxxxxxxxxxxx = (float)_snowmanxxxxxxxxxxxx * 10.0F;
            _snowmanxxxxxxxxxxxxxxxxx = MathHelper.clamp(_snowmanxxxxxxxxxxxxxxxxx, -6.0F, 32.0F);
            float _snowmanxxxxxxxxxxxxxxxxxx = (float)(_snowmanxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxx) * 100.0F;
            _snowmanxxxxxxxxxxxxxxxxxx = MathHelper.clamp(_snowmanxxxxxxxxxxxxxxxxxx, 0.0F, 150.0F);
            float _snowmanxxxxxxxxxxxxxxxxxxx = (float)(_snowmanxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxx) * 100.0F;
            _snowmanxxxxxxxxxxxxxxxxxxx = MathHelper.clamp(_snowmanxxxxxxxxxxxxxxxxxxx, -20.0F, 20.0F);
            if (_snowmanxxxxxxxxxxxxxxxxxx < 0.0F) {
               _snowmanxxxxxxxxxxxxxxxxxx = 0.0F;
            }

            float _snowmanxxxxxxxxxxxxxxxxxxxx = MathHelper.lerp(_snowman, _snowman.prevStrideDistance, _snowman.strideDistance);
            _snowmanxxxxxxxxxxxxxxxxx += MathHelper.sin(MathHelper.lerp(_snowman, _snowman.prevHorizontalSpeed, _snowman.horizontalSpeed) * 6.0F) * 32.0F * _snowmanxxxxxxxxxxxxxxxxxxxx;
            if (_snowman.isInSneakingPose()) {
               _snowmanxxxxxxxxxxxxxxxxx += 25.0F;
            }

            _snowman.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(6.0F + _snowmanxxxxxxxxxxxxxxxxxx / 2.0F + _snowmanxxxxxxxxxxxxxxxxx));
            _snowman.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(_snowmanxxxxxxxxxxxxxxxxxxx / 2.0F));
            _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0F - _snowmanxxxxxxxxxxxxxxxxxxx / 2.0F));
            VertexConsumer _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowman.getBuffer(RenderLayer.getEntitySolid(_snowman.getCapeTexture()));
            this.getContextModel().renderCape(_snowman, _snowmanxxxxxxxxxxxxxxxxxxxxx, _snowman, OverlayTexture.DEFAULT_UV);
            _snowman.pop();
         }
      }
   }
}
