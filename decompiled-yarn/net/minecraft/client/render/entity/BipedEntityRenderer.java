package net.minecraft.client.render.entity;

import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;

public class BipedEntityRenderer<T extends MobEntity, M extends BipedEntityModel<T>> extends MobEntityRenderer<T, M> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/steve.png");

   public BipedEntityRenderer(EntityRenderDispatcher dispatcher, M model, float _snowman) {
      this(dispatcher, model, _snowman, 1.0F, 1.0F, 1.0F);
   }

   public BipedEntityRenderer(EntityRenderDispatcher _snowman, M _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      super(_snowman, _snowman, _snowman);
      this.addFeature(new HeadFeatureRenderer<>(this, _snowman, _snowman, _snowman));
      this.addFeature(new ElytraFeatureRenderer<>(this));
      this.addFeature(new HeldItemFeatureRenderer<>(this));
   }

   public Identifier getTexture(T _snowman) {
      return TEXTURE;
   }
}
