package net.minecraft.client.render.entity;

import net.minecraft.block.Blocks;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.TntEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class TntEntityRenderer extends EntityRenderer<TntEntity> {
   public TntEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman);
      this.shadowRadius = 0.5F;
   }

   public void render(TntEntity _snowman, float _snowman, float _snowman, MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman) {
      _snowman.push();
      _snowman.translate(0.0, 0.5, 0.0);
      if ((float)_snowman.getFuseTimer() - _snowman + 1.0F < 10.0F) {
         float _snowmanxxxxxx = 1.0F - ((float)_snowman.getFuseTimer() - _snowman + 1.0F) / 10.0F;
         _snowmanxxxxxx = MathHelper.clamp(_snowmanxxxxxx, 0.0F, 1.0F);
         _snowmanxxxxxx *= _snowmanxxxxxx;
         _snowmanxxxxxx *= _snowmanxxxxxx;
         float _snowmanxxxxxxx = 1.0F + _snowmanxxxxxx * 0.3F;
         _snowman.scale(_snowmanxxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxx);
      }

      _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-90.0F));
      _snowman.translate(-0.5, -0.5, 0.5);
      _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(90.0F));
      TntMinecartEntityRenderer.renderFlashingBlock(Blocks.TNT.getDefaultState(), _snowman, _snowman, _snowman, _snowman.getFuseTimer() / 5 % 2 == 0);
      _snowman.pop();
      super.render(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public Identifier getTexture(TntEntity _snowman) {
      return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
   }
}
