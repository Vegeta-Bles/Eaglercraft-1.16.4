package net.minecraft.client.render.entity;

import net.minecraft.client.render.entity.model.HorseEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.HorseBaseEntity;

public abstract class HorseBaseEntityRenderer<T extends HorseBaseEntity, M extends HorseEntityModel<T>> extends MobEntityRenderer<T, M> {
   private final float scale;

   public HorseBaseEntityRenderer(EntityRenderDispatcher dispatcher, M model, float scale) {
      super(dispatcher, model, 0.75F);
      this.scale = scale;
   }

   protected void scale(T _snowman, MatrixStack _snowman, float _snowman) {
      _snowman.scale(this.scale, this.scale, this.scale);
      super.scale(_snowman, _snowman, _snowman);
   }
}
