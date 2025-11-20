package net.minecraft.client.render.item;

import com.google.common.base.MoreObjects;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.map.MapState;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;

public class HeldItemRenderer {
   private static final RenderLayer MAP_BACKGROUND = RenderLayer.getText(new Identifier("textures/map/map_background.png"));
   private static final RenderLayer MAP_BACKGROUND_CHECKERBOARD = RenderLayer.getText(new Identifier("textures/map/map_background_checkerboard.png"));
   private final MinecraftClient client;
   private ItemStack mainHand = ItemStack.EMPTY;
   private ItemStack offHand = ItemStack.EMPTY;
   private float equipProgressMainHand;
   private float prevEquipProgressMainHand;
   private float equipProgressOffHand;
   private float prevEquipProgressOffHand;
   private final EntityRenderDispatcher renderManager;
   private final ItemRenderer itemRenderer;

   public HeldItemRenderer(MinecraftClient client) {
      this.client = client;
      this.renderManager = client.getEntityRenderDispatcher();
      this.itemRenderer = client.getItemRenderer();
   }

   public void renderItem(
      LivingEntity entity,
      ItemStack stack,
      ModelTransformation.Mode renderMode,
      boolean leftHanded,
      MatrixStack matrices,
      VertexConsumerProvider vertexConsumers,
      int light
   ) {
      if (!stack.isEmpty()) {
         this.itemRenderer.renderItem(entity, stack, renderMode, leftHanded, matrices, vertexConsumers, entity.world, light, OverlayTexture.DEFAULT_UV);
      }
   }

   private float getMapAngle(float tickDelta) {
      float _snowman = 1.0F - tickDelta / 45.0F + 0.1F;
      _snowman = MathHelper.clamp(_snowman, 0.0F, 1.0F);
      return -MathHelper.cos(_snowman * (float) Math.PI) * 0.5F + 0.5F;
   }

   private void renderArm(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, Arm arm) {
      this.client.getTextureManager().bindTexture(this.client.player.getSkinTexture());
      PlayerEntityRenderer _snowman = (PlayerEntityRenderer)this.renderManager.<AbstractClientPlayerEntity>getRenderer(this.client.player);
      matrices.push();
      float _snowmanx = arm == Arm.RIGHT ? 1.0F : -1.0F;
      matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(92.0F));
      matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(45.0F));
      matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(_snowmanx * -41.0F));
      matrices.translate((double)(_snowmanx * 0.3F), -1.1F, 0.45F);
      if (arm == Arm.RIGHT) {
         _snowman.renderRightArm(matrices, vertexConsumers, light, this.client.player);
      } else {
         _snowman.renderLeftArm(matrices, vertexConsumers, light, this.client.player);
      }

      matrices.pop();
   }

   private void renderMapInOneHand(
      MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, float equipProgress, Arm arm, float swingProgress, ItemStack stack
   ) {
      float _snowman = arm == Arm.RIGHT ? 1.0F : -1.0F;
      matrices.translate((double)(_snowman * 0.125F), -0.125, 0.0);
      if (!this.client.player.isInvisible()) {
         matrices.push();
         matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(_snowman * 10.0F));
         this.renderArmHoldingItem(matrices, vertexConsumers, light, equipProgress, swingProgress, arm);
         matrices.pop();
      }

      matrices.push();
      matrices.translate((double)(_snowman * 0.51F), (double)(-0.08F + equipProgress * -1.2F), -0.75);
      float _snowmanx = MathHelper.sqrt(swingProgress);
      float _snowmanxx = MathHelper.sin(_snowmanx * (float) Math.PI);
      float _snowmanxxx = -0.5F * _snowmanxx;
      float _snowmanxxxx = 0.4F * MathHelper.sin(_snowmanx * (float) (Math.PI * 2));
      float _snowmanxxxxx = -0.3F * MathHelper.sin(swingProgress * (float) Math.PI);
      matrices.translate((double)(_snowman * _snowmanxxx), (double)(_snowmanxxxx - 0.3F * _snowmanxx), (double)_snowmanxxxxx);
      matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(_snowmanxx * -45.0F));
      matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(_snowman * _snowmanxx * -30.0F));
      this.renderFirstPersonMap(matrices, vertexConsumers, light, stack);
      matrices.pop();
   }

   private void renderMapInBothHands(
      MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, float pitch, float equipProgress, float swingProgress
   ) {
      float _snowman = MathHelper.sqrt(swingProgress);
      float _snowmanx = -0.2F * MathHelper.sin(swingProgress * (float) Math.PI);
      float _snowmanxx = -0.4F * MathHelper.sin(_snowman * (float) Math.PI);
      matrices.translate(0.0, (double)(-_snowmanx / 2.0F), (double)_snowmanxx);
      float _snowmanxxx = this.getMapAngle(pitch);
      matrices.translate(0.0, (double)(0.04F + equipProgress * -1.2F + _snowmanxxx * -0.5F), -0.72F);
      matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(_snowmanxxx * -85.0F));
      if (!this.client.player.isInvisible()) {
         matrices.push();
         matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(90.0F));
         this.renderArm(matrices, vertexConsumers, light, Arm.RIGHT);
         this.renderArm(matrices, vertexConsumers, light, Arm.LEFT);
         matrices.pop();
      }

      float _snowmanxxxx = MathHelper.sin(_snowman * (float) Math.PI);
      matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(_snowmanxxxx * 20.0F));
      matrices.scale(2.0F, 2.0F, 2.0F);
      this.renderFirstPersonMap(matrices, vertexConsumers, light, this.mainHand);
   }

   private void renderFirstPersonMap(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int swingProgress, ItemStack stack) {
      matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0F));
      matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(180.0F));
      matrices.scale(0.38F, 0.38F, 0.38F);
      matrices.translate(-0.5, -0.5, 0.0);
      matrices.scale(0.0078125F, 0.0078125F, 0.0078125F);
      MapState _snowman = FilledMapItem.getOrCreateMapState(stack, this.client.world);
      VertexConsumer _snowmanx = vertexConsumers.getBuffer(_snowman == null ? MAP_BACKGROUND : MAP_BACKGROUND_CHECKERBOARD);
      Matrix4f _snowmanxx = matrices.peek().getModel();
      _snowmanx.vertex(_snowmanxx, -7.0F, 135.0F, 0.0F).color(255, 255, 255, 255).texture(0.0F, 1.0F).light(swingProgress).next();
      _snowmanx.vertex(_snowmanxx, 135.0F, 135.0F, 0.0F).color(255, 255, 255, 255).texture(1.0F, 1.0F).light(swingProgress).next();
      _snowmanx.vertex(_snowmanxx, 135.0F, -7.0F, 0.0F).color(255, 255, 255, 255).texture(1.0F, 0.0F).light(swingProgress).next();
      _snowmanx.vertex(_snowmanxx, -7.0F, -7.0F, 0.0F).color(255, 255, 255, 255).texture(0.0F, 0.0F).light(swingProgress).next();
      if (_snowman != null) {
         this.client.gameRenderer.getMapRenderer().draw(matrices, vertexConsumers, _snowman, false, swingProgress);
      }
   }

   private void renderArmHoldingItem(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, float equipProgress, float swingProgress, Arm arm) {
      boolean _snowman = arm != Arm.LEFT;
      float _snowmanx = _snowman ? 1.0F : -1.0F;
      float _snowmanxx = MathHelper.sqrt(swingProgress);
      float _snowmanxxx = -0.3F * MathHelper.sin(_snowmanxx * (float) Math.PI);
      float _snowmanxxxx = 0.4F * MathHelper.sin(_snowmanxx * (float) (Math.PI * 2));
      float _snowmanxxxxx = -0.4F * MathHelper.sin(swingProgress * (float) Math.PI);
      matrices.translate((double)(_snowmanx * (_snowmanxxx + 0.64000005F)), (double)(_snowmanxxxx + -0.6F + equipProgress * -0.6F), (double)(_snowmanxxxxx + -0.71999997F));
      matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(_snowmanx * 45.0F));
      float _snowmanxxxxxx = MathHelper.sin(swingProgress * swingProgress * (float) Math.PI);
      float _snowmanxxxxxxx = MathHelper.sin(_snowmanxx * (float) Math.PI);
      matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(_snowmanx * _snowmanxxxxxxx * 70.0F));
      matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(_snowmanx * _snowmanxxxxxx * -20.0F));
      AbstractClientPlayerEntity _snowmanxxxxxxxx = this.client.player;
      this.client.getTextureManager().bindTexture(_snowmanxxxxxxxx.getSkinTexture());
      matrices.translate((double)(_snowmanx * -1.0F), 3.6F, 3.5);
      matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(_snowmanx * 120.0F));
      matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(200.0F));
      matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(_snowmanx * -135.0F));
      matrices.translate((double)(_snowmanx * 5.6F), 0.0, 0.0);
      PlayerEntityRenderer _snowmanxxxxxxxxx = (PlayerEntityRenderer)this.renderManager.<AbstractClientPlayerEntity>getRenderer(_snowmanxxxxxxxx);
      if (_snowman) {
         _snowmanxxxxxxxxx.renderRightArm(matrices, vertexConsumers, light, _snowmanxxxxxxxx);
      } else {
         _snowmanxxxxxxxxx.renderLeftArm(matrices, vertexConsumers, light, _snowmanxxxxxxxx);
      }
   }

   private void applyEatOrDrinkTransformation(MatrixStack matrices, float tickDelta, Arm arm, ItemStack stack) {
      float _snowman = (float)this.client.player.getItemUseTimeLeft() - tickDelta + 1.0F;
      float _snowmanx = _snowman / (float)stack.getMaxUseTime();
      if (_snowmanx < 0.8F) {
         float _snowmanxx = MathHelper.abs(MathHelper.cos(_snowman / 4.0F * (float) Math.PI) * 0.1F);
         matrices.translate(0.0, (double)_snowmanxx, 0.0);
      }

      float _snowmanxx = 1.0F - (float)Math.pow((double)_snowmanx, 27.0);
      int _snowmanxxx = arm == Arm.RIGHT ? 1 : -1;
      matrices.translate((double)(_snowmanxx * 0.6F * (float)_snowmanxxx), (double)(_snowmanxx * -0.5F), (double)(_snowmanxx * 0.0F));
      matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion((float)_snowmanxxx * _snowmanxx * 90.0F));
      matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(_snowmanxx * 10.0F));
      matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion((float)_snowmanxxx * _snowmanxx * 30.0F));
   }

   private void applySwingOffset(MatrixStack matrices, Arm arm, float swingProgress) {
      int _snowman = arm == Arm.RIGHT ? 1 : -1;
      float _snowmanx = MathHelper.sin(swingProgress * swingProgress * (float) Math.PI);
      matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion((float)_snowman * (45.0F + _snowmanx * -20.0F)));
      float _snowmanxx = MathHelper.sin(MathHelper.sqrt(swingProgress) * (float) Math.PI);
      matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion((float)_snowman * _snowmanxx * -20.0F));
      matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(_snowmanxx * -80.0F));
      matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion((float)_snowman * -45.0F));
   }

   private void applyEquipOffset(MatrixStack matrices, Arm arm, float equipProgress) {
      int _snowman = arm == Arm.RIGHT ? 1 : -1;
      matrices.translate((double)((float)_snowman * 0.56F), (double)(-0.52F + equipProgress * -0.6F), -0.72F);
   }

   public void renderItem(float tickDelta, MatrixStack matrices, VertexConsumerProvider.Immediate vertexConsumers, ClientPlayerEntity player, int light) {
      float _snowman = player.getHandSwingProgress(tickDelta);
      Hand _snowmanx = (Hand)MoreObjects.firstNonNull(player.preferredHand, Hand.MAIN_HAND);
      float _snowmanxx = MathHelper.lerp(tickDelta, player.prevPitch, player.pitch);
      boolean _snowmanxxx = true;
      boolean _snowmanxxxx = true;
      if (player.isUsingItem()) {
         ItemStack _snowmanxxxxx = player.getActiveItem();
         if (_snowmanxxxxx.getItem() == Items.BOW || _snowmanxxxxx.getItem() == Items.CROSSBOW) {
            _snowmanxxx = player.getActiveHand() == Hand.MAIN_HAND;
            _snowmanxxxx = !_snowmanxxx;
         }

         Hand _snowmanxxxxxx = player.getActiveHand();
         if (_snowmanxxxxxx == Hand.MAIN_HAND) {
            ItemStack _snowmanxxxxxxx = player.getOffHandStack();
            if (_snowmanxxxxxxx.getItem() == Items.CROSSBOW && CrossbowItem.isCharged(_snowmanxxxxxxx)) {
               _snowmanxxxx = false;
            }
         }
      } else {
         ItemStack _snowmanxxxxxx = player.getMainHandStack();
         ItemStack _snowmanxxxxxxx = player.getOffHandStack();
         if (_snowmanxxxxxx.getItem() == Items.CROSSBOW && CrossbowItem.isCharged(_snowmanxxxxxx)) {
            _snowmanxxxx = !_snowmanxxx;
         }

         if (_snowmanxxxxxxx.getItem() == Items.CROSSBOW && CrossbowItem.isCharged(_snowmanxxxxxxx)) {
            _snowmanxxx = !_snowmanxxxxxx.isEmpty();
            _snowmanxxxx = !_snowmanxxx;
         }
      }

      float _snowmanxxxxxxxx = MathHelper.lerp(tickDelta, player.lastRenderPitch, player.renderPitch);
      float _snowmanxxxxxxxxx = MathHelper.lerp(tickDelta, player.lastRenderYaw, player.renderYaw);
      matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion((player.getPitch(tickDelta) - _snowmanxxxxxxxx) * 0.1F));
      matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion((player.getYaw(tickDelta) - _snowmanxxxxxxxxx) * 0.1F));
      if (_snowmanxxx) {
         float _snowmanxxxxxxxxxx = _snowmanx == Hand.MAIN_HAND ? _snowman : 0.0F;
         float _snowmanxxxxxxxxxxx = 1.0F - MathHelper.lerp(tickDelta, this.prevEquipProgressMainHand, this.equipProgressMainHand);
         this.renderFirstPersonItem(player, tickDelta, _snowmanxx, Hand.MAIN_HAND, _snowmanxxxxxxxxxx, this.mainHand, _snowmanxxxxxxxxxxx, matrices, vertexConsumers, light);
      }

      if (_snowmanxxxx) {
         float _snowmanxxxxxxxxxx = _snowmanx == Hand.OFF_HAND ? _snowman : 0.0F;
         float _snowmanxxxxxxxxxxx = 1.0F - MathHelper.lerp(tickDelta, this.prevEquipProgressOffHand, this.equipProgressOffHand);
         this.renderFirstPersonItem(player, tickDelta, _snowmanxx, Hand.OFF_HAND, _snowmanxxxxxxxxxx, this.offHand, _snowmanxxxxxxxxxxx, matrices, vertexConsumers, light);
      }

      vertexConsumers.draw();
   }

   private void renderFirstPersonItem(
      AbstractClientPlayerEntity player,
      float tickDelta,
      float pitch,
      Hand hand,
      float swingProgress,
      ItemStack item,
      float equipProgress,
      MatrixStack matrices,
      VertexConsumerProvider vertexConsumers,
      int light
   ) {
      boolean _snowman = hand == Hand.MAIN_HAND;
      Arm _snowmanx = _snowman ? player.getMainArm() : player.getMainArm().getOpposite();
      matrices.push();
      if (item.isEmpty()) {
         if (_snowman && !player.isInvisible()) {
            this.renderArmHoldingItem(matrices, vertexConsumers, light, equipProgress, swingProgress, _snowmanx);
         }
      } else if (item.getItem() == Items.FILLED_MAP) {
         if (_snowman && this.offHand.isEmpty()) {
            this.renderMapInBothHands(matrices, vertexConsumers, light, pitch, equipProgress, swingProgress);
         } else {
            this.renderMapInOneHand(matrices, vertexConsumers, light, equipProgress, _snowmanx, swingProgress, item);
         }
      } else if (item.getItem() == Items.CROSSBOW) {
         boolean _snowmanxx = CrossbowItem.isCharged(item);
         boolean _snowmanxxx = _snowmanx == Arm.RIGHT;
         int _snowmanxxxx = _snowmanxxx ? 1 : -1;
         if (player.isUsingItem() && player.getItemUseTimeLeft() > 0 && player.getActiveHand() == hand) {
            this.applyEquipOffset(matrices, _snowmanx, equipProgress);
            matrices.translate((double)((float)_snowmanxxxx * -0.4785682F), -0.094387F, 0.05731531F);
            matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(-11.935F));
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion((float)_snowmanxxxx * 65.3F));
            matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion((float)_snowmanxxxx * -9.785F));
            float _snowmanxxxxx = (float)item.getMaxUseTime() - ((float)this.client.player.getItemUseTimeLeft() - tickDelta + 1.0F);
            float _snowmanxxxxxx = _snowmanxxxxx / (float)CrossbowItem.getPullTime(item);
            if (_snowmanxxxxxx > 1.0F) {
               _snowmanxxxxxx = 1.0F;
            }

            if (_snowmanxxxxxx > 0.1F) {
               float _snowmanxxxxxxx = MathHelper.sin((_snowmanxxxxx - 0.1F) * 1.3F);
               float _snowmanxxxxxxxx = _snowmanxxxxxx - 0.1F;
               float _snowmanxxxxxxxxx = _snowmanxxxxxxx * _snowmanxxxxxxxx;
               matrices.translate((double)(_snowmanxxxxxxxxx * 0.0F), (double)(_snowmanxxxxxxxxx * 0.004F), (double)(_snowmanxxxxxxxxx * 0.0F));
            }

            matrices.translate((double)(_snowmanxxxxxx * 0.0F), (double)(_snowmanxxxxxx * 0.0F), (double)(_snowmanxxxxxx * 0.04F));
            matrices.scale(1.0F, 1.0F, 1.0F + _snowmanxxxxxx * 0.2F);
            matrices.multiply(Vector3f.NEGATIVE_Y.getDegreesQuaternion((float)_snowmanxxxx * 45.0F));
         } else {
            float _snowmanxxxxxxx = -0.4F * MathHelper.sin(MathHelper.sqrt(swingProgress) * (float) Math.PI);
            float _snowmanxxxxxxxx = 0.2F * MathHelper.sin(MathHelper.sqrt(swingProgress) * (float) (Math.PI * 2));
            float _snowmanxxxxxxxxx = -0.2F * MathHelper.sin(swingProgress * (float) Math.PI);
            matrices.translate((double)((float)_snowmanxxxx * _snowmanxxxxxxx), (double)_snowmanxxxxxxxx, (double)_snowmanxxxxxxxxx);
            this.applyEquipOffset(matrices, _snowmanx, equipProgress);
            this.applySwingOffset(matrices, _snowmanx, swingProgress);
            if (_snowmanxx && swingProgress < 0.001F) {
               matrices.translate((double)((float)_snowmanxxxx * -0.641864F), 0.0, 0.0);
               matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion((float)_snowmanxxxx * 10.0F));
            }
         }

         this.renderItem(
            player,
            item,
            _snowmanxxx ? ModelTransformation.Mode.FIRST_PERSON_RIGHT_HAND : ModelTransformation.Mode.FIRST_PERSON_LEFT_HAND,
            !_snowmanxxx,
            matrices,
            vertexConsumers,
            light
         );
      } else {
         boolean _snowmanxx = _snowmanx == Arm.RIGHT;
         if (player.isUsingItem() && player.getItemUseTimeLeft() > 0 && player.getActiveHand() == hand) {
            int _snowmanxxx = _snowmanxx ? 1 : -1;
            switch (item.getUseAction()) {
               case NONE:
                  this.applyEquipOffset(matrices, _snowmanx, equipProgress);
                  break;
               case EAT:
               case DRINK:
                  this.applyEatOrDrinkTransformation(matrices, tickDelta, _snowmanx, item);
                  this.applyEquipOffset(matrices, _snowmanx, equipProgress);
                  break;
               case BLOCK:
                  this.applyEquipOffset(matrices, _snowmanx, equipProgress);
                  break;
               case BOW:
                  this.applyEquipOffset(matrices, _snowmanx, equipProgress);
                  matrices.translate((double)((float)_snowmanxxx * -0.2785682F), 0.18344387F, 0.15731531F);
                  matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(-13.935F));
                  matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion((float)_snowmanxxx * 35.3F));
                  matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion((float)_snowmanxxx * -9.785F));
                  float _snowmanxxxxxxxx = (float)item.getMaxUseTime() - ((float)this.client.player.getItemUseTimeLeft() - tickDelta + 1.0F);
                  float _snowmanxxxxxxxxx = _snowmanxxxxxxxx / 20.0F;
                  _snowmanxxxxxxxxx = (_snowmanxxxxxxxxx * _snowmanxxxxxxxxx + _snowmanxxxxxxxxx * 2.0F) / 3.0F;
                  if (_snowmanxxxxxxxxx > 1.0F) {
                     _snowmanxxxxxxxxx = 1.0F;
                  }

                  if (_snowmanxxxxxxxxx > 0.1F) {
                     float _snowmanxxxxxxxxxx = MathHelper.sin((_snowmanxxxxxxxx - 0.1F) * 1.3F);
                     float _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxx - 0.1F;
                     float _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxx * _snowmanxxxxxxxxxxx;
                     matrices.translate((double)(_snowmanxxxxxxxxxxxx * 0.0F), (double)(_snowmanxxxxxxxxxxxx * 0.004F), (double)(_snowmanxxxxxxxxxxxx * 0.0F));
                  }

                  matrices.translate((double)(_snowmanxxxxxxxxx * 0.0F), (double)(_snowmanxxxxxxxxx * 0.0F), (double)(_snowmanxxxxxxxxx * 0.04F));
                  matrices.scale(1.0F, 1.0F, 1.0F + _snowmanxxxxxxxxx * 0.2F);
                  matrices.multiply(Vector3f.NEGATIVE_Y.getDegreesQuaternion((float)_snowmanxxx * 45.0F));
                  break;
               case SPEAR:
                  this.applyEquipOffset(matrices, _snowmanx, equipProgress);
                  matrices.translate((double)((float)_snowmanxxx * -0.5F), 0.7F, 0.1F);
                  matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(-55.0F));
                  matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion((float)_snowmanxxx * 35.3F));
                  matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion((float)_snowmanxxx * -9.785F));
                  float _snowmanxxxx = (float)item.getMaxUseTime() - ((float)this.client.player.getItemUseTimeLeft() - tickDelta + 1.0F);
                  float _snowmanxxxxxxx = _snowmanxxxx / 10.0F;
                  if (_snowmanxxxxxxx > 1.0F) {
                     _snowmanxxxxxxx = 1.0F;
                  }

                  if (_snowmanxxxxxxx > 0.1F) {
                     float _snowmanxxxxxxxx = MathHelper.sin((_snowmanxxxx - 0.1F) * 1.3F);
                     float _snowmanxxxxxxxxx = _snowmanxxxxxxx - 0.1F;
                     float _snowmanxxxxxxxxxx = _snowmanxxxxxxxx * _snowmanxxxxxxxxx;
                     matrices.translate((double)(_snowmanxxxxxxxxxx * 0.0F), (double)(_snowmanxxxxxxxxxx * 0.004F), (double)(_snowmanxxxxxxxxxx * 0.0F));
                  }

                  matrices.translate(0.0, 0.0, (double)(_snowmanxxxxxxx * 0.2F));
                  matrices.scale(1.0F, 1.0F, 1.0F + _snowmanxxxxxxx * 0.2F);
                  matrices.multiply(Vector3f.NEGATIVE_Y.getDegreesQuaternion((float)_snowmanxxx * 45.0F));
            }
         } else if (player.isUsingRiptide()) {
            this.applyEquipOffset(matrices, _snowmanx, equipProgress);
            int _snowmanxxx = _snowmanxx ? 1 : -1;
            matrices.translate((double)((float)_snowmanxxx * -0.4F), 0.8F, 0.3F);
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion((float)_snowmanxxx * 65.0F));
            matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion((float)_snowmanxxx * -85.0F));
         } else {
            float _snowmanxxx = -0.4F * MathHelper.sin(MathHelper.sqrt(swingProgress) * (float) Math.PI);
            float _snowmanxxxxxxxxxx = 0.2F * MathHelper.sin(MathHelper.sqrt(swingProgress) * (float) (Math.PI * 2));
            float _snowmanxxxxxxxxxxx = -0.2F * MathHelper.sin(swingProgress * (float) Math.PI);
            int _snowmanxxxxxxxxxxxx = _snowmanxx ? 1 : -1;
            matrices.translate((double)((float)_snowmanxxxxxxxxxxxx * _snowmanxxx), (double)_snowmanxxxxxxxxxx, (double)_snowmanxxxxxxxxxxx);
            this.applyEquipOffset(matrices, _snowmanx, equipProgress);
            this.applySwingOffset(matrices, _snowmanx, swingProgress);
         }

         this.renderItem(
            player,
            item,
            _snowmanxx ? ModelTransformation.Mode.FIRST_PERSON_RIGHT_HAND : ModelTransformation.Mode.FIRST_PERSON_LEFT_HAND,
            !_snowmanxx,
            matrices,
            vertexConsumers,
            light
         );
      }

      matrices.pop();
   }

   public void updateHeldItems() {
      this.prevEquipProgressMainHand = this.equipProgressMainHand;
      this.prevEquipProgressOffHand = this.equipProgressOffHand;
      ClientPlayerEntity _snowman = this.client.player;
      ItemStack _snowmanx = _snowman.getMainHandStack();
      ItemStack _snowmanxx = _snowman.getOffHandStack();
      if (ItemStack.areEqual(this.mainHand, _snowmanx)) {
         this.mainHand = _snowmanx;
      }

      if (ItemStack.areEqual(this.offHand, _snowmanxx)) {
         this.offHand = _snowmanxx;
      }

      if (_snowman.isRiding()) {
         this.equipProgressMainHand = MathHelper.clamp(this.equipProgressMainHand - 0.4F, 0.0F, 1.0F);
         this.equipProgressOffHand = MathHelper.clamp(this.equipProgressOffHand - 0.4F, 0.0F, 1.0F);
      } else {
         float _snowmanxxx = _snowman.getAttackCooldownProgress(1.0F);
         this.equipProgressMainHand = this.equipProgressMainHand
            + MathHelper.clamp((this.mainHand == _snowmanx ? _snowmanxxx * _snowmanxxx * _snowmanxxx : 0.0F) - this.equipProgressMainHand, -0.4F, 0.4F);
         this.equipProgressOffHand = this.equipProgressOffHand
            + MathHelper.clamp((float)(this.offHand == _snowmanxx ? 1 : 0) - this.equipProgressOffHand, -0.4F, 0.4F);
      }

      if (this.equipProgressMainHand < 0.1F) {
         this.mainHand = _snowmanx;
      }

      if (this.equipProgressOffHand < 0.1F) {
         this.offHand = _snowmanxx;
      }
   }

   public void resetEquipProgress(Hand hand) {
      if (hand == Hand.MAIN_HAND) {
         this.equipProgressMainHand = 0.0F;
      } else {
         this.equipProgressOffHand = 0.0F;
      }
   }
}
