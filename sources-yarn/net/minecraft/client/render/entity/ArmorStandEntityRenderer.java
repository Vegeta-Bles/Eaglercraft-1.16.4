package net.minecraft.client.render.entity;

import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

@Environment(EnvType.CLIENT)
public class ArmorStandEntityRenderer extends LivingEntityRenderer<ArmorStandEntity, ArmorStandArmorEntityModel> {
   public static final Identifier TEXTURE = new Identifier("textures/entity/armorstand/wood.png");

   public ArmorStandEntityRenderer(EntityRenderDispatcher arg) {
      super(arg, new ArmorStandEntityModel(), 0.0F);
      this.addFeature(new ArmorFeatureRenderer<>(this, new ArmorStandArmorEntityModel(0.5F), new ArmorStandArmorEntityModel(1.0F)));
      this.addFeature(new HeldItemFeatureRenderer<>(this));
      this.addFeature(new ElytraFeatureRenderer<>(this));
      this.addFeature(new HeadFeatureRenderer<>(this));
   }

   public Identifier getTexture(ArmorStandEntity arg) {
      return TEXTURE;
   }

   protected void setupTransforms(ArmorStandEntity arg, MatrixStack arg2, float f, float g, float h) {
      arg2.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0F - g));
      float i = (float)(arg.world.getTime() - arg.lastHitTime) + h;
      if (i < 5.0F) {
         arg2.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(MathHelper.sin(i / 1.5F * (float) Math.PI) * 3.0F));
      }
   }

   protected boolean hasLabel(ArmorStandEntity arg) {
      double d = this.dispatcher.getSquaredDistanceToCamera(arg);
      float f = arg.isInSneakingPose() ? 32.0F : 64.0F;
      return d >= (double)(f * f) ? false : arg.isCustomNameVisible();
   }

   @Nullable
   protected RenderLayer getRenderLayer(ArmorStandEntity arg, boolean bl, boolean bl2, boolean bl3) {
      if (!arg.isMarker()) {
         return super.getRenderLayer(arg, bl, bl2, bl3);
      } else {
         Identifier lv = this.getTexture(arg);
         if (bl2) {
            return RenderLayer.getEntityTranslucent(lv, false);
         } else {
            return bl ? RenderLayer.getEntityCutoutNoCull(lv, false) : null;
         }
      }
   }
}
