package net.minecraft.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class BipedEntityRenderer<T extends MobEntity, M extends BipedEntityModel<T>> extends MobEntityRenderer<T, M> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/steve.png");

   public BipedEntityRenderer(EntityRenderDispatcher dispatcher, M model, float f) {
      this(dispatcher, model, f, 1.0F, 1.0F, 1.0F);
   }

   public BipedEntityRenderer(EntityRenderDispatcher arg, M arg2, float f, float g, float h, float i) {
      super(arg, arg2, f);
      this.addFeature(new HeadFeatureRenderer<>(this, g, h, i));
      this.addFeature(new ElytraFeatureRenderer<>(this));
      this.addFeature(new HeldItemFeatureRenderer<>(this));
   }

   public Identifier getTexture(T arg) {
      return TEXTURE;
   }
}
