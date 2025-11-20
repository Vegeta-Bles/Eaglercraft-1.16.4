package net.minecraft.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.TntEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class TntEntityRenderer extends EntityRenderer<TntEntity> {
   public TntEntityRenderer(EntityRenderDispatcher arg) {
      super(arg);
      this.shadowRadius = 0.5F;
   }

   public void render(TntEntity arg, float f, float g, MatrixStack arg2, VertexConsumerProvider arg3, int i) {
      arg2.push();
      arg2.translate(0.0, 0.5, 0.0);
      if ((float)arg.getFuseTimer() - g + 1.0F < 10.0F) {
         float h = 1.0F - ((float)arg.getFuseTimer() - g + 1.0F) / 10.0F;
         h = MathHelper.clamp(h, 0.0F, 1.0F);
         h *= h;
         h *= h;
         float j = 1.0F + h * 0.3F;
         arg2.scale(j, j, j);
      }

      arg2.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-90.0F));
      arg2.translate(-0.5, -0.5, 0.5);
      arg2.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(90.0F));
      TntMinecartEntityRenderer.renderFlashingBlock(Blocks.TNT.getDefaultState(), arg2, arg3, i, arg.getFuseTimer() / 5 % 2 == 0);
      arg2.pop();
      super.render(arg, f, g, arg2, arg3, i);
   }

   public Identifier getTexture(TntEntity arg) {
      return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
   }
}
