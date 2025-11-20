package net.minecraft.client.render.block.entity;

import net.minecraft.block.entity.ConduitBlockEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;

public class ConduitBlockEntityRenderer extends BlockEntityRenderer<ConduitBlockEntity> {
   public static final SpriteIdentifier BASE_TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("entity/conduit/base"));
   public static final SpriteIdentifier CAGE_TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("entity/conduit/cage"));
   public static final SpriteIdentifier WIND_TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("entity/conduit/wind"));
   public static final SpriteIdentifier WIND_VERTICAL_TEXTURE = new SpriteIdentifier(
      SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("entity/conduit/wind_vertical")
   );
   public static final SpriteIdentifier OPEN_EYE_TEXTURE = new SpriteIdentifier(
      SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("entity/conduit/open_eye")
   );
   public static final SpriteIdentifier CLOSED_EYE_TEXTURE = new SpriteIdentifier(
      SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("entity/conduit/closed_eye")
   );
   private final ModelPart field_20823 = new ModelPart(16, 16, 0, 0);
   private final ModelPart field_20824;
   private final ModelPart field_20825;
   private final ModelPart field_20826;

   public ConduitBlockEntityRenderer(BlockEntityRenderDispatcher _snowman) {
      super(_snowman);
      this.field_20823.addCuboid(-4.0F, -4.0F, 0.0F, 8.0F, 8.0F, 0.0F, 0.01F);
      this.field_20824 = new ModelPart(64, 32, 0, 0);
      this.field_20824.addCuboid(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F);
      this.field_20825 = new ModelPart(32, 16, 0, 0);
      this.field_20825.addCuboid(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F);
      this.field_20826 = new ModelPart(32, 16, 0, 0);
      this.field_20826.addCuboid(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F);
   }

   public void render(ConduitBlockEntity _snowman, float _snowman, MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman, int _snowman) {
      float _snowmanxxxxxx = (float)_snowman.ticks + _snowman;
      if (!_snowman.isActive()) {
         float _snowmanxxxxxxx = _snowman.getRotation(0.0F);
         VertexConsumer _snowmanxxxxxxxx = BASE_TEXTURE.getVertexConsumer(_snowman, RenderLayer::getEntitySolid);
         _snowman.push();
         _snowman.translate(0.5, 0.5, 0.5);
         _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(_snowmanxxxxxxx));
         this.field_20825.render(_snowman, _snowmanxxxxxxxx, _snowman, _snowman);
         _snowman.pop();
      } else {
         float _snowmanxxxxxxx = _snowman.getRotation(_snowman) * (180.0F / (float)Math.PI);
         float _snowmanxxxxxxxx = MathHelper.sin(_snowmanxxxxxx * 0.1F) / 2.0F + 0.5F;
         _snowmanxxxxxxxx = _snowmanxxxxxxxx * _snowmanxxxxxxxx + _snowmanxxxxxxxx;
         _snowman.push();
         _snowman.translate(0.5, (double)(0.3F + _snowmanxxxxxxxx * 0.2F), 0.5);
         Vector3f _snowmanxxxxxxxxx = new Vector3f(0.5F, 1.0F, 0.5F);
         _snowmanxxxxxxxxx.normalize();
         _snowman.multiply(new Quaternion(_snowmanxxxxxxxxx, _snowmanxxxxxxx, true));
         this.field_20826.render(_snowman, CAGE_TEXTURE.getVertexConsumer(_snowman, RenderLayer::getEntityCutoutNoCull), _snowman, _snowman);
         _snowman.pop();
         int _snowmanxxxxxxxxxx = _snowman.ticks / 66 % 3;
         _snowman.push();
         _snowman.translate(0.5, 0.5, 0.5);
         if (_snowmanxxxxxxxxxx == 1) {
            _snowman.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(90.0F));
         } else if (_snowmanxxxxxxxxxx == 2) {
            _snowman.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(90.0F));
         }

         VertexConsumer _snowmanxxxxxxxxxxx = (_snowmanxxxxxxxxxx == 1 ? WIND_VERTICAL_TEXTURE : WIND_TEXTURE).getVertexConsumer(_snowman, RenderLayer::getEntityCutoutNoCull);
         this.field_20824.render(_snowman, _snowmanxxxxxxxxxxx, _snowman, _snowman);
         _snowman.pop();
         _snowman.push();
         _snowman.translate(0.5, 0.5, 0.5);
         _snowman.scale(0.875F, 0.875F, 0.875F);
         _snowman.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(180.0F));
         _snowman.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(180.0F));
         this.field_20824.render(_snowman, _snowmanxxxxxxxxxxx, _snowman, _snowman);
         _snowman.pop();
         Camera _snowmanxxxxxxxxxxxx = this.dispatcher.camera;
         _snowman.push();
         _snowman.translate(0.5, (double)(0.3F + _snowmanxxxxxxxx * 0.2F), 0.5);
         _snowman.scale(0.5F, 0.5F, 0.5F);
         float _snowmanxxxxxxxxxxxxx = -_snowmanxxxxxxxxxxxx.getYaw();
         _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(_snowmanxxxxxxxxxxxxx));
         _snowman.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(_snowmanxxxxxxxxxxxx.getPitch()));
         _snowman.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(180.0F));
         float _snowmanxxxxxxxxxxxxxx = 1.3333334F;
         _snowman.scale(1.3333334F, 1.3333334F, 1.3333334F);
         this.field_20823.render(_snowman, (_snowman.isEyeOpen() ? OPEN_EYE_TEXTURE : CLOSED_EYE_TEXTURE).getVertexConsumer(_snowman, RenderLayer::getEntityCutoutNoCull), _snowman, _snowman);
         _snowman.pop();
      }
   }
}
