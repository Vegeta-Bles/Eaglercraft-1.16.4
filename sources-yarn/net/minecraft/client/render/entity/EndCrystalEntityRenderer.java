package net.minecraft.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;

@Environment(EnvType.CLIENT)
public class EndCrystalEntityRenderer extends EntityRenderer<EndCrystalEntity> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/end_crystal/end_crystal.png");
   private static final RenderLayer END_CRYSTAL = RenderLayer.getEntityCutoutNoCull(TEXTURE);
   private static final float SINE_45_DEGREES = (float)Math.sin(Math.PI / 4);
   private final ModelPart core;
   private final ModelPart frame;
   private final ModelPart bottom;

   public EndCrystalEntityRenderer(EntityRenderDispatcher arg) {
      super(arg);
      this.shadowRadius = 0.5F;
      this.frame = new ModelPart(64, 32, 0, 0);
      this.frame.addCuboid(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F);
      this.core = new ModelPart(64, 32, 32, 0);
      this.core.addCuboid(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F);
      this.bottom = new ModelPart(64, 32, 0, 16);
      this.bottom.addCuboid(-6.0F, 0.0F, -6.0F, 12.0F, 4.0F, 12.0F);
   }

   public void render(EndCrystalEntity arg, float f, float g, MatrixStack arg2, VertexConsumerProvider arg3, int i) {
      arg2.push();
      float h = getYOffset(arg, g);
      float j = ((float)arg.endCrystalAge + g) * 3.0F;
      VertexConsumer lv = arg3.getBuffer(END_CRYSTAL);
      arg2.push();
      arg2.scale(2.0F, 2.0F, 2.0F);
      arg2.translate(0.0, -0.5, 0.0);
      int k = OverlayTexture.DEFAULT_UV;
      if (arg.getShowBottom()) {
         this.bottom.render(arg2, lv, i, k);
      }

      arg2.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(j));
      arg2.translate(0.0, (double)(1.5F + h / 2.0F), 0.0);
      arg2.multiply(new Quaternion(new Vector3f(SINE_45_DEGREES, 0.0F, SINE_45_DEGREES), 60.0F, true));
      this.frame.render(arg2, lv, i, k);
      float l = 0.875F;
      arg2.scale(0.875F, 0.875F, 0.875F);
      arg2.multiply(new Quaternion(new Vector3f(SINE_45_DEGREES, 0.0F, SINE_45_DEGREES), 60.0F, true));
      arg2.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(j));
      this.frame.render(arg2, lv, i, k);
      arg2.scale(0.875F, 0.875F, 0.875F);
      arg2.multiply(new Quaternion(new Vector3f(SINE_45_DEGREES, 0.0F, SINE_45_DEGREES), 60.0F, true));
      arg2.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(j));
      this.core.render(arg2, lv, i, k);
      arg2.pop();
      arg2.pop();
      BlockPos lv2 = arg.getBeamTarget();
      if (lv2 != null) {
         float m = (float)lv2.getX() + 0.5F;
         float n = (float)lv2.getY() + 0.5F;
         float o = (float)lv2.getZ() + 0.5F;
         float p = (float)((double)m - arg.getX());
         float q = (float)((double)n - arg.getY());
         float r = (float)((double)o - arg.getZ());
         arg2.translate((double)p, (double)q, (double)r);
         EnderDragonEntityRenderer.renderCrystalBeam(-p, -q + h, -r, g, arg.endCrystalAge, arg2, arg3, i);
      }

      super.render(arg, f, g, arg2, arg3, i);
   }

   public static float getYOffset(EndCrystalEntity crystal, float tickDelta) {
      float g = (float)crystal.endCrystalAge + tickDelta;
      float h = MathHelper.sin(g * 0.2F) / 2.0F + 0.5F;
      h = (h * h + h) * 0.4F;
      return h - 1.4F;
   }

   public Identifier getTexture(EndCrystalEntity arg) {
      return TEXTURE;
   }

   public boolean shouldRender(EndCrystalEntity arg, Frustum arg2, double d, double e, double f) {
      return super.shouldRender(arg, arg2, d, e, f) || arg.getBeamTarget() != null;
   }
}
