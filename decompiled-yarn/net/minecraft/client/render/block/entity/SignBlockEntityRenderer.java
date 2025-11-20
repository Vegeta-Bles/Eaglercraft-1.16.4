package net.minecraft.client.render.block.entity;

import java.util.List;
import net.minecraft.block.AbstractSignBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SignBlock;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.text.OrderedText;
import net.minecraft.util.SignType;

public class SignBlockEntityRenderer extends BlockEntityRenderer<SignBlockEntity> {
   private final SignBlockEntityRenderer.SignModel model = new SignBlockEntityRenderer.SignModel();

   public SignBlockEntityRenderer(BlockEntityRenderDispatcher _snowman) {
      super(_snowman);
   }

   public void render(SignBlockEntity _snowman, float _snowman, MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman, int _snowman) {
      BlockState _snowmanxxxxxx = _snowman.getCachedState();
      _snowman.push();
      float _snowmanxxxxxxx = 0.6666667F;
      if (_snowmanxxxxxx.getBlock() instanceof SignBlock) {
         _snowman.translate(0.5, 0.5, 0.5);
         float _snowmanxxxxxxxx = -((float)(_snowmanxxxxxx.get(SignBlock.ROTATION) * 360) / 16.0F);
         _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(_snowmanxxxxxxxx));
         this.model.foot.visible = true;
      } else {
         _snowman.translate(0.5, 0.5, 0.5);
         float _snowmanxxxxxxxx = -_snowmanxxxxxx.get(WallSignBlock.FACING).asRotation();
         _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(_snowmanxxxxxxxx));
         _snowman.translate(0.0, -0.3125, -0.4375);
         this.model.foot.visible = false;
      }

      _snowman.push();
      _snowman.scale(0.6666667F, -0.6666667F, -0.6666667F);
      SpriteIdentifier _snowmanxxxxxxxx = getModelTexture(_snowmanxxxxxx.getBlock());
      VertexConsumer _snowmanxxxxxxxxx = _snowmanxxxxxxxx.getVertexConsumer(_snowman, this.model::getLayer);
      this.model.field.render(_snowman, _snowmanxxxxxxxxx, _snowman, _snowman);
      this.model.foot.render(_snowman, _snowmanxxxxxxxxx, _snowman, _snowman);
      _snowman.pop();
      TextRenderer _snowmanxxxxxxxxxx = this.dispatcher.getTextRenderer();
      float _snowmanxxxxxxxxxxx = 0.010416667F;
      _snowman.translate(0.0, 0.33333334F, 0.046666667F);
      _snowman.scale(0.010416667F, -0.010416667F, 0.010416667F);
      int _snowmanxxxxxxxxxxxx = _snowman.getTextColor().getSignColor();
      double _snowmanxxxxxxxxxxxxx = 0.4;
      int _snowmanxxxxxxxxxxxxxx = (int)((double)NativeImage.getRed(_snowmanxxxxxxxxxxxx) * 0.4);
      int _snowmanxxxxxxxxxxxxxxx = (int)((double)NativeImage.getGreen(_snowmanxxxxxxxxxxxx) * 0.4);
      int _snowmanxxxxxxxxxxxxxxxx = (int)((double)NativeImage.getBlue(_snowmanxxxxxxxxxxxx) * 0.4);
      int _snowmanxxxxxxxxxxxxxxxxx = NativeImage.getAbgrColor(0, _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx);
      int _snowmanxxxxxxxxxxxxxxxxxx = 20;

      for (int _snowmanxxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxxx < 4; _snowmanxxxxxxxxxxxxxxxxxxx++) {
         OrderedText _snowmanxxxxxxxxxxxxxxxxxxxx = _snowman.getTextBeingEditedOnRow(_snowmanxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxx -> {
            List<OrderedText> _snowmanxxxxxxxxxxxxxxxxxxxxxx = _snowman.wrapLines(_snowmanxxxxxxxxxxxxxxxxxxxxx, 90);
            return _snowmanxxxxxxxxxxxxxxxxxxxxxx.isEmpty() ? OrderedText.EMPTY : _snowmanxxxxxxxxxxxxxxxxxxxxxx.get(0);
         });
         if (_snowmanxxxxxxxxxxxxxxxxxxxx != null) {
            float _snowmanxxxxxxxxxxxxxxxxxxxxx = (float)(-_snowmanxxxxxxxxxx.getWidth(_snowmanxxxxxxxxxxxxxxxxxxxx) / 2);
            _snowmanxxxxxxxxxx.draw(
               _snowmanxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxx,
               (float)(_snowmanxxxxxxxxxxxxxxxxxxx * 10 - 20),
               _snowmanxxxxxxxxxxxxxxxxx,
               false,
               _snowman.peek().getModel(),
               _snowman,
               false,
               0,
               _snowman
            );
         }
      }

      _snowman.pop();
   }

   public static SpriteIdentifier getModelTexture(Block _snowman) {
      SignType _snowmanx;
      if (_snowman instanceof AbstractSignBlock) {
         _snowmanx = ((AbstractSignBlock)_snowman).getSignType();
      } else {
         _snowmanx = SignType.OAK;
      }

      return TexturedRenderLayers.getSignTextureId(_snowmanx);
   }

   public static final class SignModel extends Model {
      public final ModelPart field = new ModelPart(64, 32, 0, 0);
      public final ModelPart foot;

      public SignModel() {
         super(RenderLayer::getEntityCutoutNoCull);
         this.field.addCuboid(-12.0F, -14.0F, -1.0F, 24.0F, 12.0F, 2.0F, 0.0F);
         this.foot = new ModelPart(64, 32, 0, 14);
         this.foot.addCuboid(-1.0F, -2.0F, -1.0F, 2.0F, 14.0F, 2.0F, 0.0F);
      }

      @Override
      public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
         this.field.render(matrices, vertices, light, overlay, red, green, blue, alpha);
         this.foot.render(matrices, vertices, light, overlay, red, green, blue, alpha);
      }
   }
}
