package net.minecraft.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;

@Environment(EnvType.CLIENT)
public class ExperienceOrbEntityRenderer extends EntityRenderer<ExperienceOrbEntity> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/experience_orb.png");
   private static final RenderLayer LAYER = RenderLayer.getItemEntityTranslucentCull(TEXTURE);

   public ExperienceOrbEntityRenderer(EntityRenderDispatcher arg) {
      super(arg);
      this.shadowRadius = 0.15F;
      this.shadowOpacity = 0.75F;
   }

   protected int getBlockLight(ExperienceOrbEntity arg, BlockPos arg2) {
      return MathHelper.clamp(super.getBlockLight(arg, arg2) + 7, 0, 15);
   }

   public void render(ExperienceOrbEntity arg, float f, float g, MatrixStack arg2, VertexConsumerProvider arg3, int i) {
      arg2.push();
      int j = arg.getOrbSize();
      float h = (float)(j % 4 * 16 + 0) / 64.0F;
      float k = (float)(j % 4 * 16 + 16) / 64.0F;
      float l = (float)(j / 4 * 16 + 0) / 64.0F;
      float m = (float)(j / 4 * 16 + 16) / 64.0F;
      float n = 1.0F;
      float o = 0.5F;
      float p = 0.25F;
      float q = 255.0F;
      float r = ((float)arg.renderTicks + g) / 2.0F;
      int s = (int)((MathHelper.sin(r + 0.0F) + 1.0F) * 0.5F * 255.0F);
      int t = 255;
      int u = (int)((MathHelper.sin(r + (float) (Math.PI * 4.0 / 3.0)) + 1.0F) * 0.1F * 255.0F);
      arg2.translate(0.0, 0.1F, 0.0);
      arg2.multiply(this.dispatcher.getRotation());
      arg2.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0F));
      float v = 0.3F;
      arg2.scale(0.3F, 0.3F, 0.3F);
      VertexConsumer lv = arg3.getBuffer(LAYER);
      MatrixStack.Entry lv2 = arg2.peek();
      Matrix4f lv3 = lv2.getModel();
      Matrix3f lv4 = lv2.getNormal();
      method_23171(lv, lv3, lv4, -0.5F, -0.25F, s, 255, u, h, m, i);
      method_23171(lv, lv3, lv4, 0.5F, -0.25F, s, 255, u, k, m, i);
      method_23171(lv, lv3, lv4, 0.5F, 0.75F, s, 255, u, k, l, i);
      method_23171(lv, lv3, lv4, -0.5F, 0.75F, s, 255, u, h, l, i);
      arg2.pop();
      super.render(arg, f, g, arg2, arg3, i);
   }

   private static void method_23171(VertexConsumer arg, Matrix4f arg2, Matrix3f arg3, float f, float g, int i, int j, int k, float h, float l, int m) {
      arg.vertex(arg2, f, g, 0.0F).color(i, j, k, 128).texture(h, l).overlay(OverlayTexture.DEFAULT_UV).light(m).normal(arg3, 0.0F, 1.0F, 0.0F).next();
   }

   public Identifier getTexture(ExperienceOrbEntity arg) {
      return TEXTURE;
   }
}
