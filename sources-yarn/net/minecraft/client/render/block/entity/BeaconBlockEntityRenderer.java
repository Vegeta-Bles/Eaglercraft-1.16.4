package net.minecraft.client.render.block.entity;

import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;

@Environment(EnvType.CLIENT)
public class BeaconBlockEntityRenderer extends BlockEntityRenderer<BeaconBlockEntity> {
   public static final Identifier BEAM_TEXTURE = new Identifier("textures/entity/beacon_beam.png");

   public BeaconBlockEntityRenderer(BlockEntityRenderDispatcher arg) {
      super(arg);
   }

   public void render(BeaconBlockEntity arg, float f, MatrixStack arg2, VertexConsumerProvider arg3, int i, int j) {
      long l = arg.getWorld().getTime();
      List<BeaconBlockEntity.BeamSegment> list = arg.getBeamSegments();
      int k = 0;

      for (int m = 0; m < list.size(); m++) {
         BeaconBlockEntity.BeamSegment lv = list.get(m);
         render(arg2, arg3, f, l, k, m == list.size() - 1 ? 1024 : lv.getHeight(), lv.getColor());
         k += lv.getHeight();
      }
   }

   private static void render(MatrixStack arg, VertexConsumerProvider arg2, float f, long l, int i, int j, float[] fs) {
      renderLightBeam(arg, arg2, BEAM_TEXTURE, f, 1.0F, l, i, j, fs, 0.2F, 0.25F);
   }

   public static void renderLightBeam(
      MatrixStack arg, VertexConsumerProvider arg2, Identifier arg3, float f, float g, long l, int i, int j, float[] fs, float h, float k
   ) {
      int m = i + j;
      arg.push();
      arg.translate(0.5, 0.0, 0.5);
      float n = (float)Math.floorMod(l, 40L) + f;
      float o = j < 0 ? n : -n;
      float p = MathHelper.fractionalPart(o * 0.2F - (float)MathHelper.floor(o * 0.1F));
      float q = fs[0];
      float r = fs[1];
      float s = fs[2];
      arg.push();
      arg.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(n * 2.25F - 45.0F));
      float t = 0.0F;
      float w = 0.0F;
      float x = -h;
      float y = 0.0F;
      float z = 0.0F;
      float aa = -h;
      float ab = 0.0F;
      float ac = 1.0F;
      float ad = -1.0F + p;
      float ae = (float)j * g * (0.5F / h) + ad;
      method_22741(arg, arg2.getBuffer(RenderLayer.getBeaconBeam(arg3, false)), q, r, s, 1.0F, i, m, 0.0F, h, h, 0.0F, x, 0.0F, 0.0F, aa, 0.0F, 1.0F, ae, ad);
      arg.pop();
      t = -k;
      float ag = -k;
      w = -k;
      x = -k;
      ab = 0.0F;
      ac = 1.0F;
      ad = -1.0F + p;
      ae = (float)j * g + ad;
      method_22741(arg, arg2.getBuffer(RenderLayer.getBeaconBeam(arg3, true)), q, r, s, 0.125F, i, m, t, ag, k, w, x, k, k, k, 0.0F, 1.0F, ae, ad);
      arg.pop();
   }

   private static void method_22741(
      MatrixStack arg,
      VertexConsumer arg2,
      float f,
      float g,
      float h,
      float i,
      int j,
      int k,
      float l,
      float m,
      float n,
      float o,
      float p,
      float q,
      float r,
      float s,
      float t,
      float u,
      float v,
      float w
   ) {
      MatrixStack.Entry lv = arg.peek();
      Matrix4f lv2 = lv.getModel();
      Matrix3f lv3 = lv.getNormal();
      method_22740(lv2, lv3, arg2, f, g, h, i, j, k, l, m, n, o, t, u, v, w);
      method_22740(lv2, lv3, arg2, f, g, h, i, j, k, r, s, p, q, t, u, v, w);
      method_22740(lv2, lv3, arg2, f, g, h, i, j, k, n, o, r, s, t, u, v, w);
      method_22740(lv2, lv3, arg2, f, g, h, i, j, k, p, q, l, m, t, u, v, w);
   }

   private static void method_22740(
      Matrix4f arg,
      Matrix3f arg2,
      VertexConsumer arg3,
      float f,
      float g,
      float h,
      float i,
      int j,
      int k,
      float l,
      float m,
      float n,
      float o,
      float p,
      float q,
      float r,
      float s
   ) {
      method_23076(arg, arg2, arg3, f, g, h, i, k, l, m, q, r);
      method_23076(arg, arg2, arg3, f, g, h, i, j, l, m, q, s);
      method_23076(arg, arg2, arg3, f, g, h, i, j, n, o, p, s);
      method_23076(arg, arg2, arg3, f, g, h, i, k, n, o, p, r);
   }

   private static void method_23076(
      Matrix4f arg, Matrix3f arg2, VertexConsumer arg3, float f, float g, float h, float i, int j, float k, float l, float m, float n
   ) {
      arg3.vertex(arg, k, (float)j, l).color(f, g, h, i).texture(m, n).overlay(OverlayTexture.DEFAULT_UV).light(15728880).normal(arg2, 0.0F, 1.0F, 0.0F).next();
   }

   public boolean rendersOutsideBoundingBox(BeaconBlockEntity arg) {
      return true;
   }
}
