package net.minecraft.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.CodEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.passive.CodEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class CodEntityRenderer extends MobEntityRenderer<CodEntity, CodEntityModel<CodEntity>> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/fish/cod.png");

   public CodEntityRenderer(EntityRenderDispatcher arg) {
      super(arg, new CodEntityModel<>(), 0.3F);
   }

   public Identifier getTexture(CodEntity arg) {
      return TEXTURE;
   }

   protected void setupTransforms(CodEntity arg, MatrixStack arg2, float f, float g, float h) {
      super.setupTransforms(arg, arg2, f, g, h);
      float i = 4.3F * MathHelper.sin(0.6F * f);
      arg2.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(i));
      if (!arg.isTouchingWater()) {
         arg2.translate(0.1F, 0.1F, -0.1F);
         arg2.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(90.0F));
      }
   }
}
