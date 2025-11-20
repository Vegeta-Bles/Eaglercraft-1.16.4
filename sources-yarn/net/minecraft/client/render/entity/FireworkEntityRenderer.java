package net.minecraft.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class FireworkEntityRenderer extends EntityRenderer<FireworkRocketEntity> {
   private final ItemRenderer itemRenderer;

   public FireworkEntityRenderer(EntityRenderDispatcher dispatcher, ItemRenderer itemRenderer) {
      super(dispatcher);
      this.itemRenderer = itemRenderer;
   }

   public void render(FireworkRocketEntity arg, float f, float g, MatrixStack arg2, VertexConsumerProvider arg3, int i) {
      arg2.push();
      arg2.multiply(this.dispatcher.getRotation());
      arg2.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0F));
      if (arg.wasShotAtAngle()) {
         arg2.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(180.0F));
         arg2.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0F));
         arg2.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(90.0F));
      }

      this.itemRenderer.renderItem(arg.getStack(), ModelTransformation.Mode.GROUND, i, OverlayTexture.DEFAULT_UV, arg2, arg3);
      arg2.pop();
      super.render(arg, f, g, arg2, arg3, i);
   }

   public Identifier getTexture(FireworkRocketEntity arg) {
      return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
   }
}
