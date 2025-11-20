package net.minecraft.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.TropicalFishColorFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.LargeTropicalFishEntityModel;
import net.minecraft.client.render.entity.model.SmallTropicalFishEntityModel;
import net.minecraft.client.render.entity.model.TintableCompositeModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.passive.TropicalFishEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class TropicalFishEntityRenderer extends MobEntityRenderer<TropicalFishEntity, EntityModel<TropicalFishEntity>> {
   private final SmallTropicalFishEntityModel<TropicalFishEntity> smallModel = new SmallTropicalFishEntityModel<>(0.0F);
   private final LargeTropicalFishEntityModel<TropicalFishEntity> largeModel = new LargeTropicalFishEntityModel<>(0.0F);

   public TropicalFishEntityRenderer(EntityRenderDispatcher arg) {
      super(arg, new SmallTropicalFishEntityModel<>(0.0F), 0.15F);
      this.addFeature(new TropicalFishColorFeatureRenderer(this));
   }

   public Identifier getTexture(TropicalFishEntity arg) {
      return arg.getShapeId();
   }

   public void render(TropicalFishEntity arg, float f, float g, MatrixStack arg2, VertexConsumerProvider arg3, int i) {
      TintableCompositeModel<TropicalFishEntity> lv = (TintableCompositeModel<TropicalFishEntity>)(arg.getShape() == 0 ? this.smallModel : this.largeModel);
      this.model = lv;
      float[] fs = arg.getBaseColorComponents();
      lv.setColorMultiplier(fs[0], fs[1], fs[2]);
      super.render(arg, f, g, arg2, arg3, i);
      lv.setColorMultiplier(1.0F, 1.0F, 1.0F);
   }

   protected void setupTransforms(TropicalFishEntity arg, MatrixStack arg2, float f, float g, float h) {
      super.setupTransforms(arg, arg2, f, g, h);
      float i = 4.3F * MathHelper.sin(0.6F * f);
      arg2.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(i));
      if (!arg.isTouchingWater()) {
         arg2.translate(0.2F, 0.1F, 0.0);
         arg2.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(90.0F));
      }
   }
}
