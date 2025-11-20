package net.minecraft.client.render.entity;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.CapeFeatureRenderer;
import net.minecraft.client.render.entity.feature.Deadmau5FeatureRenderer;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.feature.ShoulderParrotFeatureRenderer;
import net.minecraft.client.render.entity.feature.StuckArrowsFeatureRenderer;
import net.minecraft.client.render.entity.feature.StuckStingersFeatureRenderer;
import net.minecraft.client.render.entity.feature.TridentRiptideFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.Entity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.ScoreboardPlayerScore;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class PlayerEntityRenderer extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
   public PlayerEntityRenderer(EntityRenderDispatcher _snowman) {
      this(_snowman, false);
   }

   public PlayerEntityRenderer(EntityRenderDispatcher dispatcher, boolean _snowman) {
      super(dispatcher, new PlayerEntityModel<>(0.0F, _snowman), 0.5F);
      this.addFeature(new ArmorFeatureRenderer<>(this, new BipedEntityModel(0.5F), new BipedEntityModel(1.0F)));
      this.addFeature(new HeldItemFeatureRenderer<>(this));
      this.addFeature(new StuckArrowsFeatureRenderer<>(this));
      this.addFeature(new Deadmau5FeatureRenderer(this));
      this.addFeature(new CapeFeatureRenderer(this));
      this.addFeature(new HeadFeatureRenderer<>(this));
      this.addFeature(new ElytraFeatureRenderer<>(this));
      this.addFeature(new ShoulderParrotFeatureRenderer<>(this));
      this.addFeature(new TridentRiptideFeatureRenderer<>(this));
      this.addFeature(new StuckStingersFeatureRenderer<>(this));
   }

   public void render(AbstractClientPlayerEntity _snowman, float _snowman, float _snowman, MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman) {
      this.setModelPose(_snowman);
      super.render(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public Vec3d getPositionOffset(AbstractClientPlayerEntity _snowman, float _snowman) {
      return _snowman.isInSneakingPose() ? new Vec3d(0.0, -0.125, 0.0) : super.getPositionOffset(_snowman, _snowman);
   }

   private void setModelPose(AbstractClientPlayerEntity _snowman) {
      PlayerEntityModel<AbstractClientPlayerEntity> _snowmanx = this.getModel();
      if (_snowman.isSpectator()) {
         _snowmanx.setVisible(false);
         _snowmanx.head.visible = true;
         _snowmanx.helmet.visible = true;
      } else {
         _snowmanx.setVisible(true);
         _snowmanx.helmet.visible = _snowman.isPartVisible(PlayerModelPart.HAT);
         _snowmanx.jacket.visible = _snowman.isPartVisible(PlayerModelPart.JACKET);
         _snowmanx.leftPantLeg.visible = _snowman.isPartVisible(PlayerModelPart.LEFT_PANTS_LEG);
         _snowmanx.rightPantLeg.visible = _snowman.isPartVisible(PlayerModelPart.RIGHT_PANTS_LEG);
         _snowmanx.leftSleeve.visible = _snowman.isPartVisible(PlayerModelPart.LEFT_SLEEVE);
         _snowmanx.rightSleeve.visible = _snowman.isPartVisible(PlayerModelPart.RIGHT_SLEEVE);
         _snowmanx.sneaking = _snowman.isInSneakingPose();
         BipedEntityModel.ArmPose _snowmanxx = getArmPose(_snowman, Hand.MAIN_HAND);
         BipedEntityModel.ArmPose _snowmanxxx = getArmPose(_snowman, Hand.OFF_HAND);
         if (_snowmanxx.method_30156()) {
            _snowmanxxx = _snowman.getOffHandStack().isEmpty() ? BipedEntityModel.ArmPose.EMPTY : BipedEntityModel.ArmPose.ITEM;
         }

         if (_snowman.getMainArm() == Arm.RIGHT) {
            _snowmanx.rightArmPose = _snowmanxx;
            _snowmanx.leftArmPose = _snowmanxxx;
         } else {
            _snowmanx.rightArmPose = _snowmanxxx;
            _snowmanx.leftArmPose = _snowmanxx;
         }
      }
   }

   private static BipedEntityModel.ArmPose getArmPose(AbstractClientPlayerEntity _snowman, Hand _snowman) {
      ItemStack _snowmanxx = _snowman.getStackInHand(_snowman);
      if (_snowmanxx.isEmpty()) {
         return BipedEntityModel.ArmPose.EMPTY;
      } else {
         if (_snowman.getActiveHand() == _snowman && _snowman.getItemUseTimeLeft() > 0) {
            UseAction _snowmanxxx = _snowmanxx.getUseAction();
            if (_snowmanxxx == UseAction.BLOCK) {
               return BipedEntityModel.ArmPose.BLOCK;
            }

            if (_snowmanxxx == UseAction.BOW) {
               return BipedEntityModel.ArmPose.BOW_AND_ARROW;
            }

            if (_snowmanxxx == UseAction.SPEAR) {
               return BipedEntityModel.ArmPose.THROW_SPEAR;
            }

            if (_snowmanxxx == UseAction.CROSSBOW && _snowman == _snowman.getActiveHand()) {
               return BipedEntityModel.ArmPose.CROSSBOW_CHARGE;
            }
         } else if (!_snowman.handSwinging && _snowmanxx.getItem() == Items.CROSSBOW && CrossbowItem.isCharged(_snowmanxx)) {
            return BipedEntityModel.ArmPose.CROSSBOW_HOLD;
         }

         return BipedEntityModel.ArmPose.ITEM;
      }
   }

   public Identifier getTexture(AbstractClientPlayerEntity _snowman) {
      return _snowman.getSkinTexture();
   }

   protected void scale(AbstractClientPlayerEntity _snowman, MatrixStack _snowman, float _snowman) {
      float _snowmanxxx = 0.9375F;
      _snowman.scale(0.9375F, 0.9375F, 0.9375F);
   }

   protected void renderLabelIfPresent(AbstractClientPlayerEntity _snowman, Text _snowman, MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman) {
      double _snowmanxxxxx = this.dispatcher.getSquaredDistanceToCamera(_snowman);
      _snowman.push();
      if (_snowmanxxxxx < 100.0) {
         Scoreboard _snowmanxxxxxx = _snowman.getScoreboard();
         ScoreboardObjective _snowmanxxxxxxx = _snowmanxxxxxx.getObjectiveForSlot(2);
         if (_snowmanxxxxxxx != null) {
            ScoreboardPlayerScore _snowmanxxxxxxxx = _snowmanxxxxxx.getPlayerScore(_snowman.getEntityName(), _snowmanxxxxxxx);
            super.renderLabelIfPresent(_snowman, new LiteralText(Integer.toString(_snowmanxxxxxxxx.getScore())).append(" ").append(_snowmanxxxxxxx.getDisplayName()), _snowman, _snowman, _snowman);
            _snowman.translate(0.0, (double)(9.0F * 1.15F * 0.025F), 0.0);
         }
      }

      super.renderLabelIfPresent(_snowman, _snowman, _snowman, _snowman, _snowman);
      _snowman.pop();
   }

   public void renderRightArm(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity player) {
      this.renderArm(matrices, vertexConsumers, light, player, this.model.rightArm, this.model.rightSleeve);
   }

   public void renderLeftArm(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity player) {
      this.renderArm(matrices, vertexConsumers, light, player, this.model.leftArm, this.model.leftSleeve);
   }

   private void renderArm(
      MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity player, ModelPart arm, ModelPart sleeve
   ) {
      PlayerEntityModel<AbstractClientPlayerEntity> _snowman = this.getModel();
      this.setModelPose(player);
      _snowman.handSwingProgress = 0.0F;
      _snowman.sneaking = false;
      _snowman.leaningPitch = 0.0F;
      _snowman.setAngles(player, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
      arm.pitch = 0.0F;
      arm.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntitySolid(player.getSkinTexture())), light, OverlayTexture.DEFAULT_UV);
      sleeve.pitch = 0.0F;
      sleeve.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(player.getSkinTexture())), light, OverlayTexture.DEFAULT_UV);
   }

   protected void setupTransforms(AbstractClientPlayerEntity _snowman, MatrixStack _snowman, float _snowman, float _snowman, float _snowman) {
      float _snowmanxxxxx = _snowman.getLeaningPitch(_snowman);
      if (_snowman.isFallFlying()) {
         super.setupTransforms(_snowman, _snowman, _snowman, _snowman, _snowman);
         float _snowmanxxxxxx = (float)_snowman.getRoll() + _snowman;
         float _snowmanxxxxxxx = MathHelper.clamp(_snowmanxxxxxx * _snowmanxxxxxx / 100.0F, 0.0F, 1.0F);
         if (!_snowman.isUsingRiptide()) {
            _snowman.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(_snowmanxxxxxxx * (-90.0F - _snowman.pitch)));
         }

         Vec3d _snowmanxxxxxxxx = _snowman.getRotationVec(_snowman);
         Vec3d _snowmanxxxxxxxxx = _snowman.getVelocity();
         double _snowmanxxxxxxxxxx = Entity.squaredHorizontalLength(_snowmanxxxxxxxxx);
         double _snowmanxxxxxxxxxxx = Entity.squaredHorizontalLength(_snowmanxxxxxxxx);
         if (_snowmanxxxxxxxxxx > 0.0 && _snowmanxxxxxxxxxxx > 0.0) {
            double _snowmanxxxxxxxxxxxx = (_snowmanxxxxxxxxx.x * _snowmanxxxxxxxx.x + _snowmanxxxxxxxxx.z * _snowmanxxxxxxxx.z) / Math.sqrt(_snowmanxxxxxxxxxx * _snowmanxxxxxxxxxxx);
            double _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxx.x * _snowmanxxxxxxxx.z - _snowmanxxxxxxxxx.z * _snowmanxxxxxxxx.x;
            _snowman.multiply(Vector3f.POSITIVE_Y.getRadialQuaternion((float)(Math.signum(_snowmanxxxxxxxxxxxxx) * Math.acos(_snowmanxxxxxxxxxxxx))));
         }
      } else if (_snowmanxxxxx > 0.0F) {
         super.setupTransforms(_snowman, _snowman, _snowman, _snowman, _snowman);
         float _snowmanxxxxxxxx = _snowman.isTouchingWater() ? -90.0F - _snowman.pitch : -90.0F;
         float _snowmanxxxxxxxxx = MathHelper.lerp(_snowmanxxxxx, 0.0F, _snowmanxxxxxxxx);
         _snowman.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(_snowmanxxxxxxxxx));
         if (_snowman.isInSwimmingPose()) {
            _snowman.translate(0.0, -1.0, 0.3F);
         }
      } else {
         super.setupTransforms(_snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }
}
