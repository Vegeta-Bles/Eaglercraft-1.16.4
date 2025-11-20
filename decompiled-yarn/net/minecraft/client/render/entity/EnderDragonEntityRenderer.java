package net.minecraft.client.render.entity;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;

public class EnderDragonEntityRenderer extends EntityRenderer<EnderDragonEntity> {
   public static final Identifier CRYSTAL_BEAM_TEXTURE = new Identifier("textures/entity/end_crystal/end_crystal_beam.png");
   private static final Identifier EXPLOSION_TEXTURE = new Identifier("textures/entity/enderdragon/dragon_exploding.png");
   private static final Identifier TEXTURE = new Identifier("textures/entity/enderdragon/dragon.png");
   private static final Identifier EYE_TEXTURE = new Identifier("textures/entity/enderdragon/dragon_eyes.png");
   private static final RenderLayer DRAGON_CUTOUT = RenderLayer.getEntityCutoutNoCull(TEXTURE);
   private static final RenderLayer DRAGON_DECAL = RenderLayer.getEntityDecal(TEXTURE);
   private static final RenderLayer DRAGON_EYES = RenderLayer.getEyes(EYE_TEXTURE);
   private static final RenderLayer CRYSTAL_BEAM_LAYER = RenderLayer.getEntitySmoothCutout(CRYSTAL_BEAM_TEXTURE);
   private static final float HALF_SQRT_3 = (float)(Math.sqrt(3.0) / 2.0);
   private final EnderDragonEntityRenderer.DragonEntityModel model = new EnderDragonEntityRenderer.DragonEntityModel();

   public EnderDragonEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman);
      this.shadowRadius = 0.5F;
   }

   public void render(EnderDragonEntity _snowman, float _snowman, float _snowman, MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman) {
      _snowman.push();
      float _snowmanxxxxxx = (float)_snowman.getSegmentProperties(7, _snowman)[0];
      float _snowmanxxxxxxx = (float)(_snowman.getSegmentProperties(5, _snowman)[1] - _snowman.getSegmentProperties(10, _snowman)[1]);
      _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-_snowmanxxxxxx));
      _snowman.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(_snowmanxxxxxxx * 10.0F));
      _snowman.translate(0.0, 0.0, 1.0);
      _snowman.scale(-1.0F, -1.0F, 1.0F);
      _snowman.translate(0.0, -1.501F, 0.0);
      boolean _snowmanxxxxxxxx = _snowman.hurtTime > 0;
      this.model.animateModel(_snowman, 0.0F, 0.0F, _snowman);
      if (_snowman.ticksSinceDeath > 0) {
         float _snowmanxxxxxxxxx = (float)_snowman.ticksSinceDeath / 200.0F;
         VertexConsumer _snowmanxxxxxxxxxx = _snowman.getBuffer(RenderLayer.getEntityAlpha(EXPLOSION_TEXTURE, _snowmanxxxxxxxxx));
         this.model.render(_snowman, _snowmanxxxxxxxxxx, _snowman, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
         VertexConsumer _snowmanxxxxxxxxxxx = _snowman.getBuffer(DRAGON_DECAL);
         this.model.render(_snowman, _snowmanxxxxxxxxxxx, _snowman, OverlayTexture.getUv(0.0F, _snowmanxxxxxxxx), 1.0F, 1.0F, 1.0F, 1.0F);
      } else {
         VertexConsumer _snowmanxxxxxxxxx = _snowman.getBuffer(DRAGON_CUTOUT);
         this.model.render(_snowman, _snowmanxxxxxxxxx, _snowman, OverlayTexture.getUv(0.0F, _snowmanxxxxxxxx), 1.0F, 1.0F, 1.0F, 1.0F);
      }

      VertexConsumer _snowmanxxxxxxxxx = _snowman.getBuffer(DRAGON_EYES);
      this.model.render(_snowman, _snowmanxxxxxxxxx, _snowman, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
      if (_snowman.ticksSinceDeath > 0) {
         float _snowmanxxxxxxxxxx = ((float)_snowman.ticksSinceDeath + _snowman) / 200.0F;
         float _snowmanxxxxxxxxxxx = Math.min(_snowmanxxxxxxxxxx > 0.8F ? (_snowmanxxxxxxxxxx - 0.8F) / 0.2F : 0.0F, 1.0F);
         Random _snowmanxxxxxxxxxxxx = new Random(432L);
         VertexConsumer _snowmanxxxxxxxxxxxxx = _snowman.getBuffer(RenderLayer.getLightning());
         _snowman.push();
         _snowman.translate(0.0, -1.0, -2.0);

         for (int _snowmanxxxxxxxxxxxxxx = 0; (float)_snowmanxxxxxxxxxxxxxx < (_snowmanxxxxxxxxxx + _snowmanxxxxxxxxxx * _snowmanxxxxxxxxxx) / 2.0F * 60.0F; _snowmanxxxxxxxxxxxxxx++) {
            _snowman.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(_snowmanxxxxxxxxxxxx.nextFloat() * 360.0F));
            _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(_snowmanxxxxxxxxxxxx.nextFloat() * 360.0F));
            _snowman.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(_snowmanxxxxxxxxxxxx.nextFloat() * 360.0F));
            _snowman.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(_snowmanxxxxxxxxxxxx.nextFloat() * 360.0F));
            _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(_snowmanxxxxxxxxxxxx.nextFloat() * 360.0F));
            _snowman.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(_snowmanxxxxxxxxxxxx.nextFloat() * 360.0F + _snowmanxxxxxxxxxx * 90.0F));
            float _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx.nextFloat() * 20.0F + 5.0F + _snowmanxxxxxxxxxxx * 10.0F;
            float _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx.nextFloat() * 2.0F + 1.0F + _snowmanxxxxxxxxxxx * 2.0F;
            Matrix4f _snowmanxxxxxxxxxxxxxxxxx = _snowman.peek().getModel();
            int _snowmanxxxxxxxxxxxxxxxxxx = (int)(255.0F * (1.0F - _snowmanxxxxxxxxxxx));
            method_23157(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx);
            method_23156(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx);
            method_23158(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx);
            method_23157(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx);
            method_23158(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx);
            method_23159(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx);
            method_23157(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx);
            method_23159(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx);
            method_23156(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx);
         }

         _snowman.pop();
      }

      _snowman.pop();
      if (_snowman.connectedCrystal != null) {
         _snowman.push();
         float _snowmanxxxxxxxxxx = (float)(_snowman.connectedCrystal.getX() - MathHelper.lerp((double)_snowman, _snowman.prevX, _snowman.getX()));
         float _snowmanxxxxxxxxxxx = (float)(_snowman.connectedCrystal.getY() - MathHelper.lerp((double)_snowman, _snowman.prevY, _snowman.getY()));
         float _snowmanxxxxxxxxxxxx = (float)(_snowman.connectedCrystal.getZ() - MathHelper.lerp((double)_snowman, _snowman.prevZ, _snowman.getZ()));
         renderCrystalBeam(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx + EndCrystalEntityRenderer.getYOffset(_snowman.connectedCrystal, _snowman), _snowmanxxxxxxxxxxxx, _snowman, _snowman.age, _snowman, _snowman, _snowman);
         _snowman.pop();
      }

      super.render(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   private static void method_23157(VertexConsumer vertices, Matrix4f matrix, int alpha) {
      vertices.vertex(matrix, 0.0F, 0.0F, 0.0F).color(255, 255, 255, alpha).next();
      vertices.vertex(matrix, 0.0F, 0.0F, 0.0F).color(255, 255, 255, alpha).next();
   }

   private static void method_23156(VertexConsumer vertices, Matrix4f matrix, float y, float x) {
      vertices.vertex(matrix, -HALF_SQRT_3 * x, y, -0.5F * x).color(255, 0, 255, 0).next();
   }

   private static void method_23158(VertexConsumer vertices, Matrix4f matrix, float y, float x) {
      vertices.vertex(matrix, HALF_SQRT_3 * x, y, -0.5F * x).color(255, 0, 255, 0).next();
   }

   private static void method_23159(VertexConsumer vertices, Matrix4f matrix, float y, float z) {
      vertices.vertex(matrix, 0.0F, y, 1.0F * z).color(255, 0, 255, 0).next();
   }

   public static void renderCrystalBeam(
      float dx, float dy, float dz, float tickDelta, int age, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light
   ) {
      float _snowman = MathHelper.sqrt(dx * dx + dz * dz);
      float _snowmanx = MathHelper.sqrt(dx * dx + dy * dy + dz * dz);
      matrices.push();
      matrices.translate(0.0, 2.0, 0.0);
      matrices.multiply(Vector3f.POSITIVE_Y.getRadialQuaternion((float)(-Math.atan2((double)dz, (double)dx)) - (float) (Math.PI / 2)));
      matrices.multiply(Vector3f.POSITIVE_X.getRadialQuaternion((float)(-Math.atan2((double)_snowman, (double)dy)) - (float) (Math.PI / 2)));
      VertexConsumer _snowmanxx = vertexConsumers.getBuffer(CRYSTAL_BEAM_LAYER);
      float _snowmanxxx = 0.0F - ((float)age + tickDelta) * 0.01F;
      float _snowmanxxxx = MathHelper.sqrt(dx * dx + dy * dy + dz * dz) / 32.0F - ((float)age + tickDelta) * 0.01F;
      int _snowmanxxxxx = 8;
      float _snowmanxxxxxx = 0.0F;
      float _snowmanxxxxxxx = 0.75F;
      float _snowmanxxxxxxxx = 0.0F;
      MatrixStack.Entry _snowmanxxxxxxxxx = matrices.peek();
      Matrix4f _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx.getModel();
      Matrix3f _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxx.getNormal();

      for (int _snowmanxxxxxxxxxxxx = 1; _snowmanxxxxxxxxxxxx <= 8; _snowmanxxxxxxxxxxxx++) {
         float _snowmanxxxxxxxxxxxxx = MathHelper.sin((float)_snowmanxxxxxxxxxxxx * (float) (Math.PI * 2) / 8.0F) * 0.75F;
         float _snowmanxxxxxxxxxxxxxx = MathHelper.cos((float)_snowmanxxxxxxxxxxxx * (float) (Math.PI * 2) / 8.0F) * 0.75F;
         float _snowmanxxxxxxxxxxxxxxx = (float)_snowmanxxxxxxxxxxxx / 8.0F;
         _snowmanxx.vertex(_snowmanxxxxxxxxxx, _snowmanxxxxxx * 0.2F, _snowmanxxxxxxx * 0.2F, 0.0F)
            .color(0, 0, 0, 255)
            .texture(_snowmanxxxxxxxx, _snowmanxxx)
            .overlay(OverlayTexture.DEFAULT_UV)
            .light(light)
            .normal(_snowmanxxxxxxxxxxx, 0.0F, -1.0F, 0.0F)
            .next();
         _snowmanxx.vertex(_snowmanxxxxxxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanx)
            .color(255, 255, 255, 255)
            .texture(_snowmanxxxxxxxx, _snowmanxxxx)
            .overlay(OverlayTexture.DEFAULT_UV)
            .light(light)
            .normal(_snowmanxxxxxxxxxxx, 0.0F, -1.0F, 0.0F)
            .next();
         _snowmanxx.vertex(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanx)
            .color(255, 255, 255, 255)
            .texture(_snowmanxxxxxxxxxxxxxxx, _snowmanxxxx)
            .overlay(OverlayTexture.DEFAULT_UV)
            .light(light)
            .normal(_snowmanxxxxxxxxxxx, 0.0F, -1.0F, 0.0F)
            .next();
         _snowmanxx.vertex(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxxxx * 0.2F, _snowmanxxxxxxxxxxxxxx * 0.2F, 0.0F)
            .color(0, 0, 0, 255)
            .texture(_snowmanxxxxxxxxxxxxxxx, _snowmanxxx)
            .overlay(OverlayTexture.DEFAULT_UV)
            .light(light)
            .normal(_snowmanxxxxxxxxxxx, 0.0F, -1.0F, 0.0F)
            .next();
         _snowmanxxxxxx = _snowmanxxxxxxxxxxxxx;
         _snowmanxxxxxxx = _snowmanxxxxxxxxxxxxxx;
         _snowmanxxxxxxxx = _snowmanxxxxxxxxxxxxxxx;
      }

      matrices.pop();
   }

   public Identifier getTexture(EnderDragonEntity _snowman) {
      return TEXTURE;
   }

   public static class DragonEntityModel extends EntityModel<EnderDragonEntity> {
      private final ModelPart head;
      private final ModelPart neck;
      private final ModelPart jaw;
      private final ModelPart body;
      private ModelPart wing;
      private ModelPart field_21548;
      private ModelPart field_21549;
      private ModelPart field_21550;
      private ModelPart field_21551;
      private ModelPart field_21552;
      private ModelPart field_21553;
      private ModelPart field_21554;
      private ModelPart field_21555;
      private ModelPart wingTip;
      private ModelPart frontLeg;
      private ModelPart frontLegTip;
      private ModelPart frontFoot;
      private ModelPart rearLeg;
      private ModelPart rearLegTip;
      private ModelPart rearFoot;
      @Nullable
      private EnderDragonEntity dragon;
      private float tickDelta;

      public DragonEntityModel() {
         this.textureWidth = 256;
         this.textureHeight = 256;
         float _snowman = -16.0F;
         this.head = new ModelPart(this);
         this.head.addCuboid("upperlip", -6.0F, -1.0F, -24.0F, 12, 5, 16, 0.0F, 176, 44);
         this.head.addCuboid("upperhead", -8.0F, -8.0F, -10.0F, 16, 16, 16, 0.0F, 112, 30);
         this.head.mirror = true;
         this.head.addCuboid("scale", -5.0F, -12.0F, -4.0F, 2, 4, 6, 0.0F, 0, 0);
         this.head.addCuboid("nostril", -5.0F, -3.0F, -22.0F, 2, 2, 4, 0.0F, 112, 0);
         this.head.mirror = false;
         this.head.addCuboid("scale", 3.0F, -12.0F, -4.0F, 2, 4, 6, 0.0F, 0, 0);
         this.head.addCuboid("nostril", 3.0F, -3.0F, -22.0F, 2, 2, 4, 0.0F, 112, 0);
         this.jaw = new ModelPart(this);
         this.jaw.setPivot(0.0F, 4.0F, -8.0F);
         this.jaw.addCuboid("jaw", -6.0F, 0.0F, -16.0F, 12, 4, 16, 0.0F, 176, 65);
         this.head.addChild(this.jaw);
         this.neck = new ModelPart(this);
         this.neck.addCuboid("box", -5.0F, -5.0F, -5.0F, 10, 10, 10, 0.0F, 192, 104);
         this.neck.addCuboid("scale", -1.0F, -9.0F, -3.0F, 2, 4, 6, 0.0F, 48, 0);
         this.body = new ModelPart(this);
         this.body.setPivot(0.0F, 4.0F, 8.0F);
         this.body.addCuboid("body", -12.0F, 0.0F, -16.0F, 24, 24, 64, 0.0F, 0, 0);
         this.body.addCuboid("scale", -1.0F, -6.0F, -10.0F, 2, 6, 12, 0.0F, 220, 53);
         this.body.addCuboid("scale", -1.0F, -6.0F, 10.0F, 2, 6, 12, 0.0F, 220, 53);
         this.body.addCuboid("scale", -1.0F, -6.0F, 30.0F, 2, 6, 12, 0.0F, 220, 53);
         this.wing = new ModelPart(this);
         this.wing.mirror = true;
         this.wing.setPivot(12.0F, 5.0F, 2.0F);
         this.wing.addCuboid("bone", 0.0F, -4.0F, -4.0F, 56, 8, 8, 0.0F, 112, 88);
         this.wing.addCuboid("skin", 0.0F, 0.0F, 2.0F, 56, 0, 56, 0.0F, -56, 88);
         this.field_21548 = new ModelPart(this);
         this.field_21548.mirror = true;
         this.field_21548.setPivot(56.0F, 0.0F, 0.0F);
         this.field_21548.addCuboid("bone", 0.0F, -2.0F, -2.0F, 56, 4, 4, 0.0F, 112, 136);
         this.field_21548.addCuboid("skin", 0.0F, 0.0F, 2.0F, 56, 0, 56, 0.0F, -56, 144);
         this.wing.addChild(this.field_21548);
         this.field_21549 = new ModelPart(this);
         this.field_21549.setPivot(12.0F, 20.0F, 2.0F);
         this.field_21549.addCuboid("main", -4.0F, -4.0F, -4.0F, 8, 24, 8, 0.0F, 112, 104);
         this.field_21550 = new ModelPart(this);
         this.field_21550.setPivot(0.0F, 20.0F, -1.0F);
         this.field_21550.addCuboid("main", -3.0F, -1.0F, -3.0F, 6, 24, 6, 0.0F, 226, 138);
         this.field_21549.addChild(this.field_21550);
         this.field_21551 = new ModelPart(this);
         this.field_21551.setPivot(0.0F, 23.0F, 0.0F);
         this.field_21551.addCuboid("main", -4.0F, 0.0F, -12.0F, 8, 4, 16, 0.0F, 144, 104);
         this.field_21550.addChild(this.field_21551);
         this.field_21552 = new ModelPart(this);
         this.field_21552.setPivot(16.0F, 16.0F, 42.0F);
         this.field_21552.addCuboid("main", -8.0F, -4.0F, -8.0F, 16, 32, 16, 0.0F, 0, 0);
         this.field_21553 = new ModelPart(this);
         this.field_21553.setPivot(0.0F, 32.0F, -4.0F);
         this.field_21553.addCuboid("main", -6.0F, -2.0F, 0.0F, 12, 32, 12, 0.0F, 196, 0);
         this.field_21552.addChild(this.field_21553);
         this.field_21554 = new ModelPart(this);
         this.field_21554.setPivot(0.0F, 31.0F, 4.0F);
         this.field_21554.addCuboid("main", -9.0F, 0.0F, -20.0F, 18, 6, 24, 0.0F, 112, 0);
         this.field_21553.addChild(this.field_21554);
         this.field_21555 = new ModelPart(this);
         this.field_21555.setPivot(-12.0F, 5.0F, 2.0F);
         this.field_21555.addCuboid("bone", -56.0F, -4.0F, -4.0F, 56, 8, 8, 0.0F, 112, 88);
         this.field_21555.addCuboid("skin", -56.0F, 0.0F, 2.0F, 56, 0, 56, 0.0F, -56, 88);
         this.wingTip = new ModelPart(this);
         this.wingTip.setPivot(-56.0F, 0.0F, 0.0F);
         this.wingTip.addCuboid("bone", -56.0F, -2.0F, -2.0F, 56, 4, 4, 0.0F, 112, 136);
         this.wingTip.addCuboid("skin", -56.0F, 0.0F, 2.0F, 56, 0, 56, 0.0F, -56, 144);
         this.field_21555.addChild(this.wingTip);
         this.frontLeg = new ModelPart(this);
         this.frontLeg.setPivot(-12.0F, 20.0F, 2.0F);
         this.frontLeg.addCuboid("main", -4.0F, -4.0F, -4.0F, 8, 24, 8, 0.0F, 112, 104);
         this.frontLegTip = new ModelPart(this);
         this.frontLegTip.setPivot(0.0F, 20.0F, -1.0F);
         this.frontLegTip.addCuboid("main", -3.0F, -1.0F, -3.0F, 6, 24, 6, 0.0F, 226, 138);
         this.frontLeg.addChild(this.frontLegTip);
         this.frontFoot = new ModelPart(this);
         this.frontFoot.setPivot(0.0F, 23.0F, 0.0F);
         this.frontFoot.addCuboid("main", -4.0F, 0.0F, -12.0F, 8, 4, 16, 0.0F, 144, 104);
         this.frontLegTip.addChild(this.frontFoot);
         this.rearLeg = new ModelPart(this);
         this.rearLeg.setPivot(-16.0F, 16.0F, 42.0F);
         this.rearLeg.addCuboid("main", -8.0F, -4.0F, -8.0F, 16, 32, 16, 0.0F, 0, 0);
         this.rearLegTip = new ModelPart(this);
         this.rearLegTip.setPivot(0.0F, 32.0F, -4.0F);
         this.rearLegTip.addCuboid("main", -6.0F, -2.0F, 0.0F, 12, 32, 12, 0.0F, 196, 0);
         this.rearLeg.addChild(this.rearLegTip);
         this.rearFoot = new ModelPart(this);
         this.rearFoot.setPivot(0.0F, 31.0F, 4.0F);
         this.rearFoot.addCuboid("main", -9.0F, 0.0F, -20.0F, 18, 6, 24, 0.0F, 112, 0);
         this.rearLegTip.addChild(this.rearFoot);
      }

      public void animateModel(EnderDragonEntity _snowman, float _snowman, float _snowman, float _snowman) {
         this.dragon = _snowman;
         this.tickDelta = _snowman;
      }

      public void setAngles(EnderDragonEntity _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      }

      @Override
      public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
         matrices.push();
         float _snowman = MathHelper.lerp(this.tickDelta, this.dragon.prevWingPosition, this.dragon.wingPosition);
         this.jaw.pitch = (float)(Math.sin((double)(_snowman * (float) (Math.PI * 2))) + 1.0) * 0.2F;
         float _snowmanx = (float)(Math.sin((double)(_snowman * (float) (Math.PI * 2) - 1.0F)) + 1.0);
         _snowmanx = (_snowmanx * _snowmanx + _snowmanx * 2.0F) * 0.05F;
         matrices.translate(0.0, (double)(_snowmanx - 2.0F), -3.0);
         matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(_snowmanx * 2.0F));
         float _snowmanxx = 0.0F;
         float _snowmanxxx = 20.0F;
         float _snowmanxxxx = -12.0F;
         float _snowmanxxxxx = 1.5F;
         double[] _snowmanxxxxxx = this.dragon.getSegmentProperties(6, this.tickDelta);
         float _snowmanxxxxxxx = MathHelper.fwrapDegrees(
            this.dragon.getSegmentProperties(5, this.tickDelta)[0] - this.dragon.getSegmentProperties(10, this.tickDelta)[0]
         );
         float _snowmanxxxxxxxx = MathHelper.fwrapDegrees(this.dragon.getSegmentProperties(5, this.tickDelta)[0] + (double)(_snowmanxxxxxxx / 2.0F));
         float _snowmanxxxxxxxxx = _snowman * (float) (Math.PI * 2);

         for (int _snowmanxxxxxxxxxx = 0; _snowmanxxxxxxxxxx < 5; _snowmanxxxxxxxxxx++) {
            double[] _snowmanxxxxxxxxxxx = this.dragon.getSegmentProperties(5 - _snowmanxxxxxxxxxx, this.tickDelta);
            float _snowmanxxxxxxxxxxxx = (float)Math.cos((double)((float)_snowmanxxxxxxxxxx * 0.45F + _snowmanxxxxxxxxx)) * 0.15F;
            this.neck.yaw = MathHelper.fwrapDegrees(_snowmanxxxxxxxxxxx[0] - _snowmanxxxxxx[0]) * (float) (Math.PI / 180.0) * 1.5F;
            this.neck.pitch = _snowmanxxxxxxxxxxxx + this.dragon.method_6823(_snowmanxxxxxxxxxx, _snowmanxxxxxx, _snowmanxxxxxxxxxxx) * (float) (Math.PI / 180.0) * 1.5F * 5.0F;
            this.neck.roll = -MathHelper.fwrapDegrees(_snowmanxxxxxxxxxxx[0] - (double)_snowmanxxxxxxxx) * (float) (Math.PI / 180.0) * 1.5F;
            this.neck.pivotY = _snowmanxxx;
            this.neck.pivotZ = _snowmanxxxx;
            this.neck.pivotX = _snowmanxx;
            _snowmanxxx = (float)((double)_snowmanxxx + Math.sin((double)this.neck.pitch) * 10.0);
            _snowmanxxxx = (float)((double)_snowmanxxxx - Math.cos((double)this.neck.yaw) * Math.cos((double)this.neck.pitch) * 10.0);
            _snowmanxx = (float)((double)_snowmanxx - Math.sin((double)this.neck.yaw) * Math.cos((double)this.neck.pitch) * 10.0);
            this.neck.render(matrices, vertices, light, overlay);
         }

         this.head.pivotY = _snowmanxxx;
         this.head.pivotZ = _snowmanxxxx;
         this.head.pivotX = _snowmanxx;
         double[] _snowmanxxxxxxxxxx = this.dragon.getSegmentProperties(0, this.tickDelta);
         this.head.yaw = MathHelper.fwrapDegrees(_snowmanxxxxxxxxxx[0] - _snowmanxxxxxx[0]) * (float) (Math.PI / 180.0);
         this.head.pitch = MathHelper.fwrapDegrees((double)this.dragon.method_6823(6, _snowmanxxxxxx, _snowmanxxxxxxxxxx)) * (float) (Math.PI / 180.0) * 1.5F * 5.0F;
         this.head.roll = -MathHelper.fwrapDegrees(_snowmanxxxxxxxxxx[0] - (double)_snowmanxxxxxxxx) * (float) (Math.PI / 180.0);
         this.head.render(matrices, vertices, light, overlay);
         matrices.push();
         matrices.translate(0.0, 1.0, 0.0);
         matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(-_snowmanxxxxxxx * 1.5F));
         matrices.translate(0.0, -1.0, 0.0);
         this.body.roll = 0.0F;
         this.body.render(matrices, vertices, light, overlay);
         float _snowmanxxxxxxxxxxx = _snowman * (float) (Math.PI * 2);
         this.wing.pitch = 0.125F - (float)Math.cos((double)_snowmanxxxxxxxxxxx) * 0.2F;
         this.wing.yaw = -0.25F;
         this.wing.roll = -((float)(Math.sin((double)_snowmanxxxxxxxxxxx) + 0.125)) * 0.8F;
         this.field_21548.roll = (float)(Math.sin((double)(_snowmanxxxxxxxxxxx + 2.0F)) + 0.5) * 0.75F;
         this.field_21555.pitch = this.wing.pitch;
         this.field_21555.yaw = -this.wing.yaw;
         this.field_21555.roll = -this.wing.roll;
         this.wingTip.roll = -this.field_21548.roll;
         this.method_23838(
            matrices,
            vertices,
            light,
            overlay,
            _snowmanx,
            this.wing,
            this.field_21549,
            this.field_21550,
            this.field_21551,
            this.field_21552,
            this.field_21553,
            this.field_21554
         );
         this.method_23838(
            matrices,
            vertices,
            light,
            overlay,
            _snowmanx,
            this.field_21555,
            this.frontLeg,
            this.frontLegTip,
            this.frontFoot,
            this.rearLeg,
            this.rearLegTip,
            this.rearFoot
         );
         matrices.pop();
         float _snowmanxxxxxxxxxxxx = -((float)Math.sin((double)(_snowman * (float) (Math.PI * 2)))) * 0.0F;
         _snowmanxxxxxxxxx = _snowman * (float) (Math.PI * 2);
         _snowmanxxx = 10.0F;
         _snowmanxxxx = 60.0F;
         _snowmanxx = 0.0F;
         _snowmanxxxxxx = this.dragon.getSegmentProperties(11, this.tickDelta);

         for (int _snowmanxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxx < 12; _snowmanxxxxxxxxxxxxx++) {
            _snowmanxxxxxxxxxx = this.dragon.getSegmentProperties(12 + _snowmanxxxxxxxxxxxxx, this.tickDelta);
            _snowmanxxxxxxxxxxxx = (float)((double)_snowmanxxxxxxxxxxxx + Math.sin((double)((float)_snowmanxxxxxxxxxxxxx * 0.45F + _snowmanxxxxxxxxx)) * 0.05F);
            this.neck.yaw = (MathHelper.fwrapDegrees(_snowmanxxxxxxxxxx[0] - _snowmanxxxxxx[0]) * 1.5F + 180.0F) * (float) (Math.PI / 180.0);
            this.neck.pitch = _snowmanxxxxxxxxxxxx + (float)(_snowmanxxxxxxxxxx[1] - _snowmanxxxxxx[1]) * (float) (Math.PI / 180.0) * 1.5F * 5.0F;
            this.neck.roll = MathHelper.fwrapDegrees(_snowmanxxxxxxxxxx[0] - (double)_snowmanxxxxxxxx) * (float) (Math.PI / 180.0) * 1.5F;
            this.neck.pivotY = _snowmanxxx;
            this.neck.pivotZ = _snowmanxxxx;
            this.neck.pivotX = _snowmanxx;
            _snowmanxxx = (float)((double)_snowmanxxx + Math.sin((double)this.neck.pitch) * 10.0);
            _snowmanxxxx = (float)((double)_snowmanxxxx - Math.cos((double)this.neck.yaw) * Math.cos((double)this.neck.pitch) * 10.0);
            _snowmanxx = (float)((double)_snowmanxx - Math.sin((double)this.neck.yaw) * Math.cos((double)this.neck.pitch) * 10.0);
            this.neck.render(matrices, vertices, light, overlay);
         }

         matrices.pop();
      }

      private void method_23838(
         MatrixStack matrices,
         VertexConsumer vertices,
         int light,
         int overlay,
         float offset,
         ModelPart _snowman,
         ModelPart _snowman,
         ModelPart _snowman,
         ModelPart _snowman,
         ModelPart _snowman,
         ModelPart _snowman,
         ModelPart _snowman
      ) {
         _snowman.pitch = 1.0F + offset * 0.1F;
         _snowman.pitch = 0.5F + offset * 0.1F;
         _snowman.pitch = 0.75F + offset * 0.1F;
         _snowman.pitch = 1.3F + offset * 0.1F;
         _snowman.pitch = -0.5F - offset * 0.1F;
         _snowman.pitch = 0.75F + offset * 0.1F;
         _snowman.render(matrices, vertices, light, overlay);
         _snowman.render(matrices, vertices, light, overlay);
         _snowman.render(matrices, vertices, light, overlay);
      }
   }
}
