package net.minecraft.client.render.entity.feature;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.DrownedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.DrownedEntity;
import net.minecraft.util.Identifier;

public class DrownedOverlayFeatureRenderer<T extends DrownedEntity> extends FeatureRenderer<T, DrownedEntityModel<T>> {
   private static final Identifier SKIN = new Identifier("textures/entity/zombie/drowned_outer_layer.png");
   private final DrownedEntityModel<T> model = new DrownedEntityModel<>(0.25F, 0.0F, 64, 64);

   public DrownedOverlayFeatureRenderer(FeatureRendererContext<T, DrownedEntityModel<T>> _snowman) {
      super(_snowman);
   }

   public void render(MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman, T _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      render(this.getContextModel(), this.model, SKIN, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, 1.0F, 1.0F, 1.0F);
   }
}
