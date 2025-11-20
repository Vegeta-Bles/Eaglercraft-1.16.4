package net.minecraft.client.render.block.entity;

import net.minecraft.block.entity.EnchantingTableBlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BookModel;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class EnchantingTableBlockEntityRenderer extends BlockEntityRenderer<EnchantingTableBlockEntity> {
   public static final SpriteIdentifier BOOK_TEXTURE = new SpriteIdentifier(
      SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("entity/enchanting_table_book")
   );
   private final BookModel book = new BookModel();

   public EnchantingTableBlockEntityRenderer(BlockEntityRenderDispatcher _snowman) {
      super(_snowman);
   }

   public void render(EnchantingTableBlockEntity _snowman, float _snowman, MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman, int _snowman) {
      _snowman.push();
      _snowman.translate(0.5, 0.75, 0.5);
      float _snowmanxxxxxx = (float)_snowman.ticks + _snowman;
      _snowman.translate(0.0, (double)(0.1F + MathHelper.sin(_snowmanxxxxxx * 0.1F) * 0.01F), 0.0);
      float _snowmanxxxxxxx = _snowman.field_11964 - _snowman.field_11963;

      while (_snowmanxxxxxxx >= (float) Math.PI) {
         _snowmanxxxxxxx -= (float) (Math.PI * 2);
      }

      while (_snowmanxxxxxxx < (float) -Math.PI) {
         _snowmanxxxxxxx += (float) (Math.PI * 2);
      }

      float _snowmanxxxxxxxx = _snowman.field_11963 + _snowmanxxxxxxx * _snowman;
      _snowman.multiply(Vector3f.POSITIVE_Y.getRadialQuaternion(-_snowmanxxxxxxxx));
      _snowman.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(80.0F));
      float _snowmanxxxxxxxxx = MathHelper.lerp(_snowman, _snowman.pageAngle, _snowman.nextPageAngle);
      float _snowmanxxxxxxxxxx = MathHelper.fractionalPart(_snowmanxxxxxxxxx + 0.25F) * 1.6F - 0.3F;
      float _snowmanxxxxxxxxxxx = MathHelper.fractionalPart(_snowmanxxxxxxxxx + 0.75F) * 1.6F - 0.3F;
      float _snowmanxxxxxxxxxxxx = MathHelper.lerp(_snowman, _snowman.pageTurningSpeed, _snowman.nextPageTurningSpeed);
      this.book.setPageAngles(_snowmanxxxxxx, MathHelper.clamp(_snowmanxxxxxxxxxx, 0.0F, 1.0F), MathHelper.clamp(_snowmanxxxxxxxxxxx, 0.0F, 1.0F), _snowmanxxxxxxxxxxxx);
      VertexConsumer _snowmanxxxxxxxxxxxxx = BOOK_TEXTURE.getVertexConsumer(_snowman, RenderLayer::getEntitySolid);
      this.book.method_24184(_snowman, _snowmanxxxxxxxxxxxxx, _snowman, _snowman, 1.0F, 1.0F, 1.0F, 1.0F);
      _snowman.pop();
   }
}
