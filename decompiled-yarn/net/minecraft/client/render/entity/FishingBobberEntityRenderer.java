package net.minecraft.client.render.entity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;

public class FishingBobberEntityRenderer extends EntityRenderer<FishingBobberEntity> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/fishing_hook.png");
   private static final RenderLayer LAYER = RenderLayer.getEntityCutout(TEXTURE);

   public FishingBobberEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman);
   }

   public void render(FishingBobberEntity _snowman, float _snowman, float _snowman, MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman) {
      PlayerEntity _snowmanxxxxxx = _snowman.getPlayerOwner();
      if (_snowmanxxxxxx != null) {
         _snowman.push();
         _snowman.push();
         _snowman.scale(0.5F, 0.5F, 0.5F);
         _snowman.multiply(this.dispatcher.getRotation());
         _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0F));
         MatrixStack.Entry _snowmanxxxxxxx = _snowman.peek();
         Matrix4f _snowmanxxxxxxxx = _snowmanxxxxxxx.getModel();
         Matrix3f _snowmanxxxxxxxxx = _snowmanxxxxxxx.getNormal();
         VertexConsumer _snowmanxxxxxxxxxx = _snowman.getBuffer(LAYER);
         method_23840(_snowmanxxxxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowman, 0.0F, 0, 0, 1);
         method_23840(_snowmanxxxxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowman, 1.0F, 0, 1, 1);
         method_23840(_snowmanxxxxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowman, 1.0F, 1, 1, 0);
         method_23840(_snowmanxxxxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowman, 0.0F, 1, 0, 0);
         _snowman.pop();
         int _snowmanxxxxxxxxxxx = _snowmanxxxxxx.getMainArm() == Arm.RIGHT ? 1 : -1;
         ItemStack _snowmanxxxxxxxxxxxx = _snowmanxxxxxx.getMainHandStack();
         if (_snowmanxxxxxxxxxxxx.getItem() != Items.FISHING_ROD) {
            _snowmanxxxxxxxxxxx = -_snowmanxxxxxxxxxxx;
         }

         float _snowmanxxxxxxxxxxxxx = _snowmanxxxxxx.getHandSwingProgress(_snowman);
         float _snowmanxxxxxxxxxxxxxx = MathHelper.sin(MathHelper.sqrt(_snowmanxxxxxxxxxxxxx) * (float) Math.PI);
         float _snowmanxxxxxxxxxxxxxxx = MathHelper.lerp(_snowman, _snowmanxxxxxx.prevBodyYaw, _snowmanxxxxxx.bodyYaw) * (float) (Math.PI / 180.0);
         double _snowmanxxxxxxxxxxxxxxxx = (double)MathHelper.sin(_snowmanxxxxxxxxxxxxxxx);
         double _snowmanxxxxxxxxxxxxxxxxx = (double)MathHelper.cos(_snowmanxxxxxxxxxxxxxxx);
         double _snowmanxxxxxxxxxxxxxxxxxx = (double)_snowmanxxxxxxxxxxx * 0.35;
         double _snowmanxxxxxxxxxxxxxxxxxxx = 0.8;
         double _snowmanxxxxxxxxxxxxxxxxxxxx;
         double _snowmanxxxxxxxxxxxxxxxxxxxxx;
         double _snowmanxxxxxxxxxxxxxxxxxxxxxx;
         float _snowmanxxxxxxxxxxxxxxxxxxxxxxx;
         if ((this.dispatcher.gameOptions == null || this.dispatcher.gameOptions.getPerspective().isFirstPerson())
            && _snowmanxxxxxx == MinecraftClient.getInstance().player) {
            double _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = this.dispatcher.gameOptions.fov;
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxx /= 100.0;
            Vec3d _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = new Vec3d((double)_snowmanxxxxxxxxxxx * -0.36 * _snowmanxxxxxxxxxxxxxxxxxxxxxxxx, -0.045 * _snowmanxxxxxxxxxxxxxxxxxxxxxxxx, 0.4);
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx.rotateX(-MathHelper.lerp(_snowman, _snowmanxxxxxx.prevPitch, _snowmanxxxxxx.pitch) * (float) (Math.PI / 180.0));
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx.rotateY(-MathHelper.lerp(_snowman, _snowmanxxxxxx.prevYaw, _snowmanxxxxxx.yaw) * (float) (Math.PI / 180.0));
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx.rotateY(_snowmanxxxxxxxxxxxxxx * 0.5F);
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx.rotateX(-_snowmanxxxxxxxxxxxxxx * 0.7F);
            _snowmanxxxxxxxxxxxxxxxxxxxx = MathHelper.lerp((double)_snowman, _snowmanxxxxxx.prevX, _snowmanxxxxxx.getX()) + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx.x;
            _snowmanxxxxxxxxxxxxxxxxxxxxx = MathHelper.lerp((double)_snowman, _snowmanxxxxxx.prevY, _snowmanxxxxxx.getY()) + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx.y;
            _snowmanxxxxxxxxxxxxxxxxxxxxxx = MathHelper.lerp((double)_snowman, _snowmanxxxxxx.prevZ, _snowmanxxxxxx.getZ()) + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx.z;
            _snowmanxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxx.getStandingEyeHeight();
         } else {
            _snowmanxxxxxxxxxxxxxxxxxxxx = MathHelper.lerp((double)_snowman, _snowmanxxxxxx.prevX, _snowmanxxxxxx.getX())
               - _snowmanxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxx
               - _snowmanxxxxxxxxxxxxxxxx * 0.8;
            _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxx.prevY + (double)_snowmanxxxxxx.getStandingEyeHeight() + (_snowmanxxxxxx.getY() - _snowmanxxxxxx.prevY) * (double)_snowman - 0.45;
            _snowmanxxxxxxxxxxxxxxxxxxxxxx = MathHelper.lerp((double)_snowman, _snowmanxxxxxx.prevZ, _snowmanxxxxxx.getZ())
               - _snowmanxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxx
               + _snowmanxxxxxxxxxxxxxxxxx * 0.8;
            _snowmanxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxx.isInSneakingPose() ? -0.1875F : 0.0F;
         }

         double _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = MathHelper.lerp((double)_snowman, _snowman.prevX, _snowman.getX());
         double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = MathHelper.lerp((double)_snowman, _snowman.prevY, _snowman.getY()) + 0.25;
         double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = MathHelper.lerp((double)_snowman, _snowman.prevZ, _snowman.getZ());
         float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = (float)(_snowmanxxxxxxxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxxxxxxxxxxxxx);
         float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (float)(_snowmanxxxxxxxxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx) + _snowmanxxxxxxxxxxxxxxxxxxxxxxx;
         float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (float)(_snowmanxxxxxxxxxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx);
         VertexConsumer _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.getBuffer(RenderLayer.getLines());
         Matrix4f _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.peek().getModel();
         int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 16;

         for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < 16; _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx++) {
            method_23172(
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               method_23954(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, 16)
            );
            method_23172(
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               method_23954(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx + 1, 16)
            );
         }

         _snowman.pop();
         super.render(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }

   private static float method_23954(int _snowman, int _snowman) {
      return (float)_snowman / (float)_snowman;
   }

   private static void method_23840(VertexConsumer _snowman, Matrix4f _snowman, Matrix3f _snowman, int _snowman, float _snowman, int _snowman, int _snowman, int _snowman) {
      _snowman.vertex(_snowman, _snowman - 0.5F, (float)_snowman - 0.5F, 0.0F)
         .color(255, 255, 255, 255)
         .texture((float)_snowman, (float)_snowman)
         .overlay(OverlayTexture.DEFAULT_UV)
         .light(_snowman)
         .normal(_snowman, 0.0F, 1.0F, 0.0F)
         .next();
   }

   private static void method_23172(float _snowman, float _snowman, float _snowman, VertexConsumer _snowman, Matrix4f _snowman, float _snowman) {
      _snowman.vertex(_snowman, _snowman * _snowman, _snowman * (_snowman * _snowman + _snowman) * 0.5F + 0.25F, _snowman * _snowman).color(0, 0, 0, 255).next();
   }

   public Identifier getTexture(FishingBobberEntity _snowman) {
      return TEXTURE;
   }
}
