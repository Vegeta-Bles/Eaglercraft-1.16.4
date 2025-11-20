package net.minecraft.client.render.block.entity;

import net.minecraft.block.entity.EndGatewayBlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class EndGatewayBlockEntityRenderer extends EndPortalBlockEntityRenderer<EndGatewayBlockEntity> {
   private static final Identifier BEAM_TEXTURE = new Identifier("textures/entity/end_gateway_beam.png");

   public EndGatewayBlockEntityRenderer(BlockEntityRenderDispatcher _snowman) {
      super(_snowman);
   }

   public void render(EndGatewayBlockEntity _snowman, float _snowman, MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman, int _snowman) {
      if (_snowman.isRecentlyGenerated() || _snowman.needsCooldownBeforeTeleporting()) {
         float _snowmanxxxxxx = _snowman.isRecentlyGenerated() ? _snowman.getRecentlyGeneratedBeamHeight(_snowman) : _snowman.getCooldownBeamHeight(_snowman);
         double _snowmanxxxxxxx = _snowman.isRecentlyGenerated() ? 256.0 : 50.0;
         _snowmanxxxxxx = MathHelper.sin(_snowmanxxxxxx * (float) Math.PI);
         int _snowmanxxxxxxxx = MathHelper.floor((double)_snowmanxxxxxx * _snowmanxxxxxxx);
         float[] _snowmanxxxxxxxxx = _snowman.isRecentlyGenerated() ? DyeColor.MAGENTA.getColorComponents() : DyeColor.PURPLE.getColorComponents();
         long _snowmanxxxxxxxxxx = _snowman.getWorld().getTime();
         BeaconBlockEntityRenderer.renderLightBeam(_snowman, _snowman, BEAM_TEXTURE, _snowman, _snowmanxxxxxx, _snowmanxxxxxxxxxx, 0, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, 0.15F, 0.175F);
         BeaconBlockEntityRenderer.renderLightBeam(_snowman, _snowman, BEAM_TEXTURE, _snowman, _snowmanxxxxxx, _snowmanxxxxxxxxxx, 0, -_snowmanxxxxxxxx, _snowmanxxxxxxxxx, 0.15F, 0.175F);
      }

      super.render(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   protected int method_3592(double _snowman) {
      return super.method_3592(_snowman) + 1;
   }

   @Override
   protected float method_3594() {
      return 1.0F;
   }
}
