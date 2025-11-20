/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerModelPart;
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
import net.minecraft.scoreboard.ScoreboardPlayerScore;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class PlayerEntityRenderer
extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    public PlayerEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        this(entityRenderDispatcher, false);
    }

    public PlayerEntityRenderer(EntityRenderDispatcher dispatcher, boolean bl) {
        super(dispatcher, new PlayerEntityModel(0.0f, bl), 0.5f);
        this.addFeature(new ArmorFeatureRenderer(this, new BipedEntityModel(0.5f), new BipedEntityModel(1.0f)));
        this.addFeature(new HeldItemFeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>>(this));
        this.addFeature(new StuckArrowsFeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>>(this));
        this.addFeature(new Deadmau5FeatureRenderer(this));
        this.addFeature(new CapeFeatureRenderer(this));
        this.addFeature(new HeadFeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>>(this));
        this.addFeature(new ElytraFeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>>(this));
        this.addFeature(new ShoulderParrotFeatureRenderer<AbstractClientPlayerEntity>(this));
        this.addFeature(new TridentRiptideFeatureRenderer<AbstractClientPlayerEntity>(this));
        this.addFeature(new StuckStingersFeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>>(this));
    }

    @Override
    public void render(AbstractClientPlayerEntity abstractClientPlayerEntity, float f, float f2, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n) {
        this.setModelPose(abstractClientPlayerEntity);
        super.render(abstractClientPlayerEntity, f, f2, matrixStack, vertexConsumerProvider, n);
    }

    @Override
    public Vec3d getPositionOffset(AbstractClientPlayerEntity abstractClientPlayerEntity, float f) {
        if (abstractClientPlayerEntity.isInSneakingPose()) {
            return new Vec3d(0.0, -0.125, 0.0);
        }
        return super.getPositionOffset(abstractClientPlayerEntity, f);
    }

    private void setModelPose(AbstractClientPlayerEntity abstractClientPlayerEntity) {
        PlayerEntityModel playerEntityModel = (PlayerEntityModel)this.getModel();
        if (abstractClientPlayerEntity.isSpectator()) {
            playerEntityModel.setVisible(false);
            playerEntityModel.head.visible = true;
            playerEntityModel.helmet.visible = true;
        } else {
            playerEntityModel.setVisible(true);
            playerEntityModel.helmet.visible = abstractClientPlayerEntity.isPartVisible(PlayerModelPart.HAT);
            playerEntityModel.jacket.visible = abstractClientPlayerEntity.isPartVisible(PlayerModelPart.JACKET);
            playerEntityModel.leftPantLeg.visible = abstractClientPlayerEntity.isPartVisible(PlayerModelPart.LEFT_PANTS_LEG);
            playerEntityModel.rightPantLeg.visible = abstractClientPlayerEntity.isPartVisible(PlayerModelPart.RIGHT_PANTS_LEG);
            playerEntityModel.leftSleeve.visible = abstractClientPlayerEntity.isPartVisible(PlayerModelPart.LEFT_SLEEVE);
            playerEntityModel.rightSleeve.visible = abstractClientPlayerEntity.isPartVisible(PlayerModelPart.RIGHT_SLEEVE);
            playerEntityModel.sneaking = abstractClientPlayerEntity.isInSneakingPose();
            BipedEntityModel.ArmPose armPose = PlayerEntityRenderer.getArmPose(abstractClientPlayerEntity, Hand.MAIN_HAND);
            _snowman = PlayerEntityRenderer.getArmPose(abstractClientPlayerEntity, Hand.OFF_HAND);
            if (armPose.method_30156()) {
                BipedEntityModel.ArmPose armPose2 = _snowman = abstractClientPlayerEntity.getOffHandStack().isEmpty() ? BipedEntityModel.ArmPose.EMPTY : BipedEntityModel.ArmPose.ITEM;
            }
            if (abstractClientPlayerEntity.getMainArm() == Arm.RIGHT) {
                playerEntityModel.rightArmPose = armPose;
                playerEntityModel.leftArmPose = _snowman;
            } else {
                playerEntityModel.rightArmPose = _snowman;
                playerEntityModel.leftArmPose = armPose;
            }
        }
    }

    private static BipedEntityModel.ArmPose getArmPose(AbstractClientPlayerEntity abstractClientPlayerEntity2, Hand hand) {
        AbstractClientPlayerEntity abstractClientPlayerEntity2;
        ItemStack itemStack = abstractClientPlayerEntity2.getStackInHand(hand);
        if (itemStack.isEmpty()) {
            return BipedEntityModel.ArmPose.EMPTY;
        }
        if (abstractClientPlayerEntity2.getActiveHand() == hand && abstractClientPlayerEntity2.getItemUseTimeLeft() > 0) {
            UseAction useAction = itemStack.getUseAction();
            if (useAction == UseAction.BLOCK) {
                return BipedEntityModel.ArmPose.BLOCK;
            }
            if (useAction == UseAction.BOW) {
                return BipedEntityModel.ArmPose.BOW_AND_ARROW;
            }
            if (useAction == UseAction.SPEAR) {
                return BipedEntityModel.ArmPose.THROW_SPEAR;
            }
            if (useAction == UseAction.CROSSBOW && hand == abstractClientPlayerEntity2.getActiveHand()) {
                return BipedEntityModel.ArmPose.CROSSBOW_CHARGE;
            }
        } else if (!abstractClientPlayerEntity2.handSwinging && itemStack.getItem() == Items.CROSSBOW && CrossbowItem.isCharged(itemStack)) {
            return BipedEntityModel.ArmPose.CROSSBOW_HOLD;
        }
        return BipedEntityModel.ArmPose.ITEM;
    }

    @Override
    public Identifier getTexture(AbstractClientPlayerEntity abstractClientPlayerEntity) {
        return abstractClientPlayerEntity.getSkinTexture();
    }

    @Override
    protected void scale(AbstractClientPlayerEntity abstractClientPlayerEntity, MatrixStack matrixStack, float f) {
        _snowman = 0.9375f;
        matrixStack.scale(0.9375f, 0.9375f, 0.9375f);
    }

    @Override
    protected void renderLabelIfPresent(AbstractClientPlayerEntity abstractClientPlayerEntity2, Text text, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n) {
        AbstractClientPlayerEntity abstractClientPlayerEntity2;
        double d = this.dispatcher.getSquaredDistanceToCamera(abstractClientPlayerEntity2);
        matrixStack.push();
        if (d < 100.0 && (_snowman = (_snowman = abstractClientPlayerEntity2.getScoreboard()).getObjectiveForSlot(2)) != null) {
            ScoreboardPlayerScore scoreboardPlayerScore = _snowman.getPlayerScore(abstractClientPlayerEntity2.getEntityName(), _snowman);
            super.renderLabelIfPresent(abstractClientPlayerEntity2, new LiteralText(Integer.toString(scoreboardPlayerScore.getScore())).append(" ").append(_snowman.getDisplayName()), matrixStack, vertexConsumerProvider, n);
            this.getFontRenderer().getClass();
            matrixStack.translate(0.0, 9.0f * 1.15f * 0.025f, 0.0);
        }
        super.renderLabelIfPresent(abstractClientPlayerEntity2, text, matrixStack, vertexConsumerProvider, n);
        matrixStack.pop();
    }

    public void renderRightArm(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity player) {
        this.renderArm(matrices, vertexConsumers, light, player, ((PlayerEntityModel)this.model).rightArm, ((PlayerEntityModel)this.model).rightSleeve);
    }

    public void renderLeftArm(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity player) {
        this.renderArm(matrices, vertexConsumers, light, player, ((PlayerEntityModel)this.model).leftArm, ((PlayerEntityModel)this.model).leftSleeve);
    }

    private void renderArm(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity player, ModelPart arm, ModelPart sleeve) {
        PlayerEntityModel playerEntityModel = (PlayerEntityModel)this.getModel();
        this.setModelPose(player);
        playerEntityModel.handSwingProgress = 0.0f;
        playerEntityModel.sneaking = false;
        playerEntityModel.leaningPitch = 0.0f;
        playerEntityModel.setAngles(player, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
        arm.pitch = 0.0f;
        arm.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntitySolid(player.getSkinTexture())), light, OverlayTexture.DEFAULT_UV);
        sleeve.pitch = 0.0f;
        sleeve.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(player.getSkinTexture())), light, OverlayTexture.DEFAULT_UV);
    }

    @Override
    protected void setupTransforms(AbstractClientPlayerEntity abstractClientPlayerEntity, MatrixStack matrixStack, float f, float f2, float f3) {
        float f4;
        f4 = abstractClientPlayerEntity.getLeaningPitch(f3);
        if (abstractClientPlayerEntity.isFallFlying()) {
            super.setupTransforms(abstractClientPlayerEntity, matrixStack, f, f2, f3);
            _snowman = (float)abstractClientPlayerEntity.getRoll() + f3;
            _snowman = MathHelper.clamp(_snowman * _snowman / 100.0f, 0.0f, 1.0f);
            if (!abstractClientPlayerEntity.isUsingRiptide()) {
                matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(_snowman * (-90.0f - abstractClientPlayerEntity.pitch)));
            }
            Vec3d vec3d = abstractClientPlayerEntity.getRotationVec(f3);
            _snowman = abstractClientPlayerEntity.getVelocity();
            double _snowman2 = Entity.squaredHorizontalLength(_snowman);
            double _snowman3 = Entity.squaredHorizontalLength(vec3d);
            if (_snowman2 > 0.0 && _snowman3 > 0.0) {
                double d = (_snowman.x * vec3d.x + _snowman.z * vec3d.z) / Math.sqrt(_snowman2 * _snowman3);
                _snowman = _snowman.x * vec3d.z - _snowman.z * vec3d.x;
                matrixStack.multiply(Vector3f.POSITIVE_Y.getRadialQuaternion((float)(Math.signum(_snowman) * Math.acos(d))));
            }
        } else if (f4 > 0.0f) {
            super.setupTransforms(abstractClientPlayerEntity, matrixStack, f, f2, f3);
            _snowman = abstractClientPlayerEntity.isTouchingWater() ? -90.0f - abstractClientPlayerEntity.pitch : -90.0f;
            _snowman = MathHelper.lerp(f4, 0.0f, _snowman);
            matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(_snowman));
            if (abstractClientPlayerEntity.isInSwimmingPose()) {
                matrixStack.translate(0.0, -1.0, 0.3f);
            }
        } else {
            super.setupTransforms(abstractClientPlayerEntity, matrixStack, f, f2, f3);
        }
    }
}

