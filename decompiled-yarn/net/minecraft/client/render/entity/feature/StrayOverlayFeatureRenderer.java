package net.minecraft.client.render.entity.feature;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.SkeletonEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;

public class StrayOverlayFeatureRenderer<T extends MobEntity & RangedAttackMob, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
   private static final Identifier SKIN = new Identifier("textures/entity/skeleton/stray_overlay.png");
   private final SkeletonEntityModel<T> model = new SkeletonEntityModel<>(0.25F, true);

   public StrayOverlayFeatureRenderer(FeatureRendererContext<T, M> _snowman) {
      super(_snowman);
   }

   public void render(MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman, T _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      render(this.getContextModel(), this.model, SKIN, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, 1.0F, 1.0F, 1.0F);
   }
}
