package net.minecraft.client.render.block.entity;

import net.minecraft.block.entity.BellBlockEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;

public class BellBlockEntityRenderer extends BlockEntityRenderer<BellBlockEntity> {
   public static final SpriteIdentifier BELL_BODY_TEXTURE = new SpriteIdentifier(
      SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("entity/bell/bell_body")
   );
   private final ModelPart field_20816 = new ModelPart(32, 32, 0, 0);

   public BellBlockEntityRenderer(BlockEntityRenderDispatcher _snowman) {
      super(_snowman);
      this.field_20816.addCuboid(-3.0F, -6.0F, -3.0F, 6.0F, 7.0F, 6.0F);
      this.field_20816.setPivot(8.0F, 12.0F, 8.0F);
      ModelPart _snowmanx = new ModelPart(32, 32, 0, 13);
      _snowmanx.addCuboid(4.0F, 4.0F, 4.0F, 8.0F, 2.0F, 8.0F);
      _snowmanx.setPivot(-8.0F, -12.0F, -8.0F);
      this.field_20816.addChild(_snowmanx);
   }

   public void render(BellBlockEntity _snowman, float _snowman, MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman, int _snowman) {
      float _snowmanxxxxxx = (float)_snowman.ringTicks + _snowman;
      float _snowmanxxxxxxx = 0.0F;
      float _snowmanxxxxxxxx = 0.0F;
      if (_snowman.ringing) {
         float _snowmanxxxxxxxxx = MathHelper.sin(_snowmanxxxxxx / (float) Math.PI) / (4.0F + _snowmanxxxxxx / 3.0F);
         if (_snowman.lastSideHit == Direction.NORTH) {
            _snowmanxxxxxxx = -_snowmanxxxxxxxxx;
         } else if (_snowman.lastSideHit == Direction.SOUTH) {
            _snowmanxxxxxxx = _snowmanxxxxxxxxx;
         } else if (_snowman.lastSideHit == Direction.EAST) {
            _snowmanxxxxxxxx = -_snowmanxxxxxxxxx;
         } else if (_snowman.lastSideHit == Direction.WEST) {
            _snowmanxxxxxxxx = _snowmanxxxxxxxxx;
         }
      }

      this.field_20816.pitch = _snowmanxxxxxxx;
      this.field_20816.roll = _snowmanxxxxxxxx;
      VertexConsumer _snowmanxxxxxxxxx = BELL_BODY_TEXTURE.getVertexConsumer(_snowman, RenderLayer::getEntitySolid);
      this.field_20816.render(_snowman, _snowmanxxxxxxxxx, _snowman, _snowman);
   }
}
