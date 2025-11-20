package net.minecraft.client.render.entity;

import net.minecraft.client.render.entity.feature.PhantomEyesFeatureRenderer;
import net.minecraft.client.render.entity.model.PhantomEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.util.Identifier;

public class PhantomEntityRenderer extends MobEntityRenderer<PhantomEntity, PhantomEntityModel<PhantomEntity>> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/phantom.png");

   public PhantomEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman, new PhantomEntityModel<>(), 0.75F);
      this.addFeature(new PhantomEyesFeatureRenderer<>(this));
   }

   public Identifier getTexture(PhantomEntity _snowman) {
      return TEXTURE;
   }

   protected void scale(PhantomEntity _snowman, MatrixStack _snowman, float _snowman) {
      int _snowmanxxx = _snowman.getPhantomSize();
      float _snowmanxxxx = 1.0F + 0.15F * (float)_snowmanxxx;
      _snowman.scale(_snowmanxxxx, _snowmanxxxx, _snowmanxxxx);
      _snowman.translate(0.0, 1.3125, 0.1875);
   }

   protected void setupTransforms(PhantomEntity _snowman, MatrixStack _snowman, float _snowman, float _snowman, float _snowman) {
      super.setupTransforms(_snowman, _snowman, _snowman, _snowman, _snowman);
      _snowman.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(_snowman.pitch));
   }
}
