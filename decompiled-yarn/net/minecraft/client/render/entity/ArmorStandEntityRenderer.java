package net.minecraft.client.render.entity;

import javax.annotation.Nullable;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.ArmorStandArmorEntityModel;
import net.minecraft.client.render.entity.model.ArmorStandEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class ArmorStandEntityRenderer extends LivingEntityRenderer<ArmorStandEntity, ArmorStandArmorEntityModel> {
   public static final Identifier TEXTURE = new Identifier("textures/entity/armorstand/wood.png");

   public ArmorStandEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman, new ArmorStandEntityModel(), 0.0F);
      this.addFeature(new ArmorFeatureRenderer<>(this, new ArmorStandArmorEntityModel(0.5F), new ArmorStandArmorEntityModel(1.0F)));
      this.addFeature(new HeldItemFeatureRenderer<>(this));
      this.addFeature(new ElytraFeatureRenderer<>(this));
      this.addFeature(new HeadFeatureRenderer<>(this));
   }

   public Identifier getTexture(ArmorStandEntity _snowman) {
      return TEXTURE;
   }

   protected void setupTransforms(ArmorStandEntity _snowman, MatrixStack _snowman, float _snowman, float _snowman, float _snowman) {
      _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0F - _snowman));
      float _snowmanxxxxx = (float)(_snowman.world.getTime() - _snowman.lastHitTime) + _snowman;
      if (_snowmanxxxxx < 5.0F) {
         _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(MathHelper.sin(_snowmanxxxxx / 1.5F * (float) Math.PI) * 3.0F));
      }
   }

   protected boolean hasLabel(ArmorStandEntity _snowman) {
      double _snowmanx = this.dispatcher.getSquaredDistanceToCamera(_snowman);
      float _snowmanxx = _snowman.isInSneakingPose() ? 32.0F : 64.0F;
      return _snowmanx >= (double)(_snowmanxx * _snowmanxx) ? false : _snowman.isCustomNameVisible();
   }

   @Nullable
   protected RenderLayer getRenderLayer(ArmorStandEntity _snowman, boolean _snowman, boolean _snowman, boolean _snowman) {
      if (!_snowman.isMarker()) {
         return super.getRenderLayer(_snowman, _snowman, _snowman, _snowman);
      } else {
         Identifier _snowmanxxxx = this.getTexture(_snowman);
         if (_snowman) {
            return RenderLayer.getEntityTranslucent(_snowmanxxxx, false);
         } else {
            return _snowman ? RenderLayer.getEntityCutoutNoCull(_snowmanxxxx, false) : null;
         }
      }
   }
}
