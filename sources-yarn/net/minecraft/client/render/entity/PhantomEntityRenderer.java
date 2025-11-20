package net.minecraft.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.feature.PhantomEyesFeatureRenderer;
import net.minecraft.client.render.entity.model.PhantomEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class PhantomEntityRenderer extends MobEntityRenderer<PhantomEntity, PhantomEntityModel<PhantomEntity>> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/phantom.png");

   public PhantomEntityRenderer(EntityRenderDispatcher arg) {
      super(arg, new PhantomEntityModel<>(), 0.75F);
      this.addFeature(new PhantomEyesFeatureRenderer<>(this));
   }

   public Identifier getTexture(PhantomEntity arg) {
      return TEXTURE;
   }

   protected void scale(PhantomEntity arg, MatrixStack arg2, float f) {
      int i = arg.getPhantomSize();
      float g = 1.0F + 0.15F * (float)i;
      arg2.scale(g, g, g);
      arg2.translate(0.0, 1.3125, 0.1875);
   }

   protected void setupTransforms(PhantomEntity arg, MatrixStack arg2, float f, float g, float h) {
      super.setupTransforms(arg, arg2, f, g, h);
      arg2.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(arg.pitch));
   }
}
