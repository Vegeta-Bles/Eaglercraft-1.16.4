package net.minecraft.client.render.entity;

import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.ZombieEntityModel;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.Identifier;

public abstract class ZombieBaseEntityRenderer<T extends ZombieEntity, M extends ZombieEntityModel<T>> extends BipedEntityRenderer<T, M> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/zombie/zombie.png");

   protected ZombieBaseEntityRenderer(EntityRenderDispatcher dispatcher, M _snowman, M _snowman, M _snowman) {
      super(dispatcher, _snowman, 0.5F);
      this.addFeature(new ArmorFeatureRenderer<>(this, _snowman, _snowman));
   }

   public Identifier getTexture(ZombieEntity _snowman) {
      return TEXTURE;
   }

   protected boolean isShaking(T _snowman) {
      return _snowman.isConvertingInWater();
   }
}
