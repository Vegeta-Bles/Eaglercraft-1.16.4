package net.minecraft.client.render.entity;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.TurtleEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.util.Identifier;

public class TurtleEntityRenderer extends MobEntityRenderer<TurtleEntity, TurtleEntityModel<TurtleEntity>> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/turtle/big_sea_turtle.png");

   public TurtleEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman, new TurtleEntityModel<>(0.0F), 0.7F);
   }

   public void render(TurtleEntity _snowman, float _snowman, float _snowman, MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman) {
      if (_snowman.isBaby()) {
         this.shadowRadius *= 0.5F;
      }

      super.render(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public Identifier getTexture(TurtleEntity _snowman) {
      return TEXTURE;
   }
}
