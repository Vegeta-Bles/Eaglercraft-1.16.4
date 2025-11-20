package net.minecraft.client.render.entity;

import net.minecraft.client.render.entity.model.BatEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class BatEntityRenderer extends MobEntityRenderer<BatEntity, BatEntityModel> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/bat.png");

   public BatEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman, new BatEntityModel(), 0.25F);
   }

   public Identifier getTexture(BatEntity _snowman) {
      return TEXTURE;
   }

   protected void scale(BatEntity _snowman, MatrixStack _snowman, float _snowman) {
      _snowman.scale(0.35F, 0.35F, 0.35F);
   }

   protected void setupTransforms(BatEntity _snowman, MatrixStack _snowman, float _snowman, float _snowman, float _snowman) {
      if (_snowman.isRoosting()) {
         _snowman.translate(0.0, -0.1F, 0.0);
      } else {
         _snowman.translate(0.0, (double)(MathHelper.cos(_snowman * 0.3F) * 0.1F), 0.0);
      }

      super.setupTransforms(_snowman, _snowman, _snowman, _snowman, _snowman);
   }
}
