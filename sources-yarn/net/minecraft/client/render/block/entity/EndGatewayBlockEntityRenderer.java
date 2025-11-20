package net.minecraft.client.render.block.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.EndGatewayBlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class EndGatewayBlockEntityRenderer extends EndPortalBlockEntityRenderer<EndGatewayBlockEntity> {
   private static final Identifier BEAM_TEXTURE = new Identifier("textures/entity/end_gateway_beam.png");

   public EndGatewayBlockEntityRenderer(BlockEntityRenderDispatcher arg) {
      super(arg);
   }

   public void render(EndGatewayBlockEntity arg, float f, MatrixStack arg2, VertexConsumerProvider arg3, int i, int j) {
      if (arg.isRecentlyGenerated() || arg.needsCooldownBeforeTeleporting()) {
         float g = arg.isRecentlyGenerated() ? arg.getRecentlyGeneratedBeamHeight(f) : arg.getCooldownBeamHeight(f);
         double d = arg.isRecentlyGenerated() ? 256.0 : 50.0;
         g = MathHelper.sin(g * (float) Math.PI);
         int k = MathHelper.floor((double)g * d);
         float[] fs = arg.isRecentlyGenerated() ? DyeColor.MAGENTA.getColorComponents() : DyeColor.PURPLE.getColorComponents();
         long l = arg.getWorld().getTime();
         BeaconBlockEntityRenderer.renderLightBeam(arg2, arg3, BEAM_TEXTURE, f, g, l, 0, k, fs, 0.15F, 0.175F);
         BeaconBlockEntityRenderer.renderLightBeam(arg2, arg3, BEAM_TEXTURE, f, g, l, 0, -k, fs, 0.15F, 0.175F);
      }

      super.render(arg, f, arg2, arg3, i, j);
   }

   @Override
   protected int method_3592(double d) {
      return super.method_3592(d) + 1;
   }

   @Override
   protected float method_3594() {
      return 1.0F;
   }
}
