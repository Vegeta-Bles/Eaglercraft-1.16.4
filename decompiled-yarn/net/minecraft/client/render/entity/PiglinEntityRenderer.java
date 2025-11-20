package net.minecraft.client.render.entity;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PiglinEntityModel;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.AbstractPiglinEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;

public class PiglinEntityRenderer extends BipedEntityRenderer<MobEntity, PiglinEntityModel<MobEntity>> {
   private static final Map<EntityType<?>, Identifier> field_25793 = ImmutableMap.of(
      EntityType.PIGLIN,
      new Identifier("textures/entity/piglin/piglin.png"),
      EntityType.ZOMBIFIED_PIGLIN,
      new Identifier("textures/entity/piglin/zombified_piglin.png"),
      EntityType.PIGLIN_BRUTE,
      new Identifier("textures/entity/piglin/piglin_brute.png")
   );

   public PiglinEntityRenderer(EntityRenderDispatcher dispatcher, boolean zombified) {
      super(dispatcher, getPiglinModel(zombified), 0.5F, 1.0019531F, 1.0F, 1.0019531F);
      this.addFeature(new ArmorFeatureRenderer<>(this, new BipedEntityModel(0.5F), new BipedEntityModel(1.02F)));
   }

   private static PiglinEntityModel<MobEntity> getPiglinModel(boolean zombified) {
      PiglinEntityModel<MobEntity> _snowman = new PiglinEntityModel<>(0.0F, 64, 64);
      if (zombified) {
         _snowman.leftEar.visible = false;
      }

      return _snowman;
   }

   @Override
   public Identifier getTexture(MobEntity _snowman) {
      Identifier _snowmanx = field_25793.get(_snowman.getType());
      if (_snowmanx == null) {
         throw new IllegalArgumentException("I don't know what texture to use for " + _snowman.getType());
      } else {
         return _snowmanx;
      }
   }

   protected boolean isShaking(MobEntity _snowman) {
      return _snowman instanceof AbstractPiglinEntity && ((AbstractPiglinEntity)_snowman).shouldZombify();
   }
}
