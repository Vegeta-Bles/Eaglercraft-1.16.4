package net.minecraft.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.GuardianEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.GuardianEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;

@Environment(EnvType.CLIENT)
public class GuardianEntityRenderer extends MobEntityRenderer<GuardianEntity, GuardianEntityModel> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/guardian.png");
   private static final Identifier EXPLOSION_BEAM_TEXTURE = new Identifier("textures/entity/guardian_beam.png");
   private static final RenderLayer LAYER = RenderLayer.getEntityCutoutNoCull(EXPLOSION_BEAM_TEXTURE);

   public GuardianEntityRenderer(EntityRenderDispatcher arg) {
      this(arg, 0.5F);
   }

   protected GuardianEntityRenderer(EntityRenderDispatcher dispatcher, float f) {
      super(dispatcher, new GuardianEntityModel(), f);
   }

   public boolean shouldRender(GuardianEntity arg, Frustum arg2, double d, double e, double f) {
      if (super.shouldRender(arg, arg2, d, e, f)) {
         return true;
      } else {
         if (arg.hasBeamTarget()) {
            LivingEntity lv = arg.getBeamTarget();
            if (lv != null) {
               Vec3d lv2 = this.fromLerpedPosition(lv, (double)lv.getHeight() * 0.5, 1.0F);
               Vec3d lv3 = this.fromLerpedPosition(arg, (double)arg.getStandingEyeHeight(), 1.0F);
               return arg2.isVisible(new Box(lv3.x, lv3.y, lv3.z, lv2.x, lv2.y, lv2.z));
            }
         }

         return false;
      }
   }

   private Vec3d fromLerpedPosition(LivingEntity entity, double yOffset, float delta) {
      double e = MathHelper.lerp((double)delta, entity.lastRenderX, entity.getX());
      double g = MathHelper.lerp((double)delta, entity.lastRenderY, entity.getY()) + yOffset;
      double h = MathHelper.lerp((double)delta, entity.lastRenderZ, entity.getZ());
      return new Vec3d(e, g, h);
   }

   public void render(GuardianEntity arg, float f, float g, MatrixStack arg2, VertexConsumerProvider arg3, int i) {
      super.render(arg, f, g, arg2, arg3, i);
      LivingEntity lv = arg.getBeamTarget();
      if (lv != null) {
         float h = arg.getBeamProgress(g);
         float j = (float)arg.world.getTime() + g;
         float k = j * 0.5F % 1.0F;
         float l = arg.getStandingEyeHeight();
         arg2.push();
         arg2.translate(0.0, (double)l, 0.0);
         Vec3d lv2 = this.fromLerpedPosition(lv, (double)lv.getHeight() * 0.5, g);
         Vec3d lv3 = this.fromLerpedPosition(arg, (double)l, g);
         Vec3d lv4 = lv2.subtract(lv3);
         float m = (float)(lv4.length() + 1.0);
         lv4 = lv4.normalize();
         float n = (float)Math.acos(lv4.y);
         float o = (float)Math.atan2(lv4.z, lv4.x);
         arg2.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(((float) (Math.PI / 2) - o) * (180.0F / (float)Math.PI)));
         arg2.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(n * (180.0F / (float)Math.PI)));
         int p = 1;
         float q = j * 0.05F * -1.5F;
         float r = h * h;
         int s = 64 + (int)(r * 191.0F);
         int t = 32 + (int)(r * 191.0F);
         int u = 128 - (int)(r * 64.0F);
         float v = 0.2F;
         float w = 0.282F;
         float x = MathHelper.cos(q + (float) (Math.PI * 3.0 / 4.0)) * 0.282F;
         float y = MathHelper.sin(q + (float) (Math.PI * 3.0 / 4.0)) * 0.282F;
         float z = MathHelper.cos(q + (float) (Math.PI / 4)) * 0.282F;
         float aa = MathHelper.sin(q + (float) (Math.PI / 4)) * 0.282F;
         float ab = MathHelper.cos(q + ((float) Math.PI * 5.0F / 4.0F)) * 0.282F;
         float ac = MathHelper.sin(q + ((float) Math.PI * 5.0F / 4.0F)) * 0.282F;
         float ad = MathHelper.cos(q + ((float) Math.PI * 7.0F / 4.0F)) * 0.282F;
         float ae = MathHelper.sin(q + ((float) Math.PI * 7.0F / 4.0F)) * 0.282F;
         float af = MathHelper.cos(q + (float) Math.PI) * 0.2F;
         float ag = MathHelper.sin(q + (float) Math.PI) * 0.2F;
         float ah = MathHelper.cos(q + 0.0F) * 0.2F;
         float ai = MathHelper.sin(q + 0.0F) * 0.2F;
         float aj = MathHelper.cos(q + (float) (Math.PI / 2)) * 0.2F;
         float ak = MathHelper.sin(q + (float) (Math.PI / 2)) * 0.2F;
         float al = MathHelper.cos(q + (float) (Math.PI * 3.0 / 2.0)) * 0.2F;
         float am = MathHelper.sin(q + (float) (Math.PI * 3.0 / 2.0)) * 0.2F;
         float ao = 0.0F;
         float ap = 0.4999F;
         float aq = -1.0F + k;
         float ar = m * 2.5F + aq;
         VertexConsumer lv5 = arg3.getBuffer(LAYER);
         MatrixStack.Entry lv6 = arg2.peek();
         Matrix4f lv7 = lv6.getModel();
         Matrix3f lv8 = lv6.getNormal();
         method_23173(lv5, lv7, lv8, af, m, ag, s, t, u, 0.4999F, ar);
         method_23173(lv5, lv7, lv8, af, 0.0F, ag, s, t, u, 0.4999F, aq);
         method_23173(lv5, lv7, lv8, ah, 0.0F, ai, s, t, u, 0.0F, aq);
         method_23173(lv5, lv7, lv8, ah, m, ai, s, t, u, 0.0F, ar);
         method_23173(lv5, lv7, lv8, aj, m, ak, s, t, u, 0.4999F, ar);
         method_23173(lv5, lv7, lv8, aj, 0.0F, ak, s, t, u, 0.4999F, aq);
         method_23173(lv5, lv7, lv8, al, 0.0F, am, s, t, u, 0.0F, aq);
         method_23173(lv5, lv7, lv8, al, m, am, s, t, u, 0.0F, ar);
         float as = 0.0F;
         if (arg.age % 2 == 0) {
            as = 0.5F;
         }

         method_23173(lv5, lv7, lv8, x, m, y, s, t, u, 0.5F, as + 0.5F);
         method_23173(lv5, lv7, lv8, z, m, aa, s, t, u, 1.0F, as + 0.5F);
         method_23173(lv5, lv7, lv8, ad, m, ae, s, t, u, 1.0F, as);
         method_23173(lv5, lv7, lv8, ab, m, ac, s, t, u, 0.5F, as);
         arg2.pop();
      }
   }

   private static void method_23173(VertexConsumer arg, Matrix4f arg2, Matrix3f arg3, float f, float g, float h, int i, int j, int k, float l, float m) {
      arg.vertex(arg2, f, g, h).color(i, j, k, 255).texture(l, m).overlay(OverlayTexture.DEFAULT_UV).light(15728880).normal(arg3, 0.0F, 1.0F, 0.0F).next();
   }

   public Identifier getTexture(GuardianEntity arg) {
      return TEXTURE;
   }
}
