package net.minecraft.client.render.entity;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.util.Identifier;

public class FireworkEntityRenderer extends EntityRenderer<FireworkRocketEntity> {
   private final ItemRenderer itemRenderer;

   public FireworkEntityRenderer(EntityRenderDispatcher dispatcher, ItemRenderer itemRenderer) {
      super(dispatcher);
      this.itemRenderer = itemRenderer;
   }

   public void render(FireworkRocketEntity _snowman, float _snowman, float _snowman, MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman) {
      _snowman.push();
      _snowman.multiply(this.dispatcher.getRotation());
      _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0F));
      if (_snowman.wasShotAtAngle()) {
         _snowman.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(180.0F));
         _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0F));
         _snowman.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(90.0F));
      }

      this.itemRenderer.renderItem(_snowman.getStack(), ModelTransformation.Mode.GROUND, _snowman, OverlayTexture.DEFAULT_UV, _snowman, _snowman);
      _snowman.pop();
      super.render(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public Identifier getTexture(FireworkRocketEntity _snowman) {
      return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
   }
}
