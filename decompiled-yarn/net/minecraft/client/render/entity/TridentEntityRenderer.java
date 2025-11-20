package net.minecraft.client.render.entity;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.TridentEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class TridentEntityRenderer extends EntityRenderer<TridentEntity> {
   public static final Identifier TEXTURE = new Identifier("textures/entity/trident.png");
   private final TridentEntityModel model = new TridentEntityModel();

   public TridentEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman);
   }

   public void render(TridentEntity _snowman, float _snowman, float _snowman, MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman) {
      _snowman.push();
      _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(MathHelper.lerp(_snowman, _snowman.prevYaw, _snowman.yaw) - 90.0F));
      _snowman.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(MathHelper.lerp(_snowman, _snowman.prevPitch, _snowman.pitch) + 90.0F));
      VertexConsumer _snowmanxxxxxx = ItemRenderer.getDirectItemGlintConsumer(_snowman, this.model.getLayer(this.getTexture(_snowman)), false, _snowman.isEnchanted());
      this.model.render(_snowman, _snowmanxxxxxx, _snowman, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
      _snowman.pop();
      super.render(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public Identifier getTexture(TridentEntity _snowman) {
      return TEXTURE;
   }
}
