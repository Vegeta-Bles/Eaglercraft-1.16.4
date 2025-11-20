/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.render.entity;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.Model;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class LivingEntityRenderer<T extends LivingEntity, M extends EntityModel<T>>
extends EntityRenderer<T>
implements FeatureRendererContext<T, M> {
    private static final Logger LOGGER = LogManager.getLogger();
    protected M model;
    protected final List<FeatureRenderer<T, M>> features = Lists.newArrayList();

    public LivingEntityRenderer(EntityRenderDispatcher dispatcher, M model, float shadowRadius) {
        super(dispatcher);
        this.model = model;
        this.shadowRadius = shadowRadius;
    }

    protected final boolean addFeature(FeatureRenderer<T, M> feature) {
        return this.features.add(feature);
    }

    @Override
    public M getModel() {
        return this.model;
    }

    @Override
    public void render(T t, float f, float f2, MatrixStack matrixStack2, VertexConsumerProvider vertexConsumerProvider, int n) {
        MatrixStack matrixStack2;
        float _snowman3;
        matrixStack2.push();
        ((EntityModel)this.model).handSwingProgress = this.getHandSwingProgress(t, f2);
        ((EntityModel)this.model).riding = ((Entity)t).hasVehicle();
        ((EntityModel)this.model).child = ((LivingEntity)t).isBaby();
        float _snowman2 = MathHelper.lerpAngleDegrees(f2, ((LivingEntity)t).prevBodyYaw, ((LivingEntity)t).bodyYaw);
        _snowman = MathHelper.lerpAngleDegrees(f2, ((LivingEntity)t).prevHeadYaw, ((LivingEntity)t).headYaw);
        _snowman3 = _snowman - _snowman2;
        if (((Entity)t).hasVehicle() && ((Entity)t).getVehicle() instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity)((Entity)t).getVehicle();
            _snowman2 = MathHelper.lerpAngleDegrees(f2, livingEntity.prevBodyYaw, livingEntity.bodyYaw);
            _snowman3 = _snowman - _snowman2;
            float _snowman4 = MathHelper.wrapDegrees(_snowman3);
            if (_snowman4 < -85.0f) {
                _snowman4 = -85.0f;
            }
            if (_snowman4 >= 85.0f) {
                _snowman4 = 85.0f;
            }
            _snowman2 = _snowman - _snowman4;
            if (_snowman4 * _snowman4 > 2500.0f) {
                _snowman2 += _snowman4 * 0.2f;
            }
            _snowman3 = _snowman - _snowman2;
        }
        float f3 = MathHelper.lerp(f2, ((LivingEntity)t).prevPitch, ((LivingEntity)t).pitch);
        if (((Entity)t).getPose() == EntityPose.SLEEPING && (_snowman = ((LivingEntity)t).getSleepingDirection()) != null) {
            _snowman = ((Entity)t).getEyeHeight(EntityPose.STANDING) - 0.1f;
            matrixStack2.translate((float)(-_snowman.getOffsetX()) * _snowman, 0.0, (float)(-_snowman.getOffsetZ()) * _snowman);
        }
        _snowman = this.getAnimationProgress(t, f2);
        this.setupTransforms(t, matrixStack2, _snowman, _snowman2, f2);
        matrixStack2.scale(-1.0f, -1.0f, 1.0f);
        this.scale(t, matrixStack2, f2);
        matrixStack2.translate(0.0, -1.501f, 0.0);
        _snowman = 0.0f;
        _snowman = 0.0f;
        if (!((Entity)t).hasVehicle() && ((LivingEntity)t).isAlive()) {
            _snowman = MathHelper.lerp(f2, ((LivingEntity)t).lastLimbDistance, ((LivingEntity)t).limbDistance);
            _snowman = ((LivingEntity)t).limbAngle - ((LivingEntity)t).limbDistance * (1.0f - f2);
            if (((LivingEntity)t).isBaby()) {
                _snowman *= 3.0f;
            }
            if (_snowman > 1.0f) {
                _snowman = 1.0f;
            }
        }
        ((EntityModel)this.model).animateModel(t, _snowman, _snowman, f2);
        ((EntityModel)this.model).setAngles(t, _snowman, _snowman, _snowman, _snowman3, f3);
        MinecraftClient _snowman5 = MinecraftClient.getInstance();
        boolean _snowman6 = this.isVisible(t);
        boolean _snowman7 = !_snowman6 && !((Entity)t).isInvisibleTo(_snowman5.player);
        boolean _snowman8 = _snowman5.hasOutline((Entity)t);
        RenderLayer _snowman9 = this.getRenderLayer(t, _snowman6, _snowman7, _snowman8);
        if (_snowman9 != null) {
            VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(_snowman9);
            int _snowman10 = LivingEntityRenderer.getOverlay(t, this.getAnimationCounter(t, f2));
            ((Model)this.model).render(matrixStack2, vertexConsumer, n, _snowman10, 1.0f, 1.0f, 1.0f, _snowman7 ? 0.15f : 1.0f);
        }
        if (!((Entity)t).isSpectator()) {
            for (FeatureRenderer<T, M> featureRenderer : this.features) {
                featureRenderer.render(matrixStack2, vertexConsumerProvider, n, t, _snowman, _snowman, f2, _snowman, _snowman3, f3);
            }
        }
        matrixStack2.pop();
        super.render(t, f, f2, matrixStack2, vertexConsumerProvider, n);
    }

    @Nullable
    protected RenderLayer getRenderLayer(T entity, boolean showBody, boolean translucent, boolean showOutline) {
        Identifier identifier = this.getTexture(entity);
        if (translucent) {
            return RenderLayer.getItemEntityTranslucentCull(identifier);
        }
        if (showBody) {
            return ((Model)this.model).getLayer(identifier);
        }
        if (showOutline) {
            return RenderLayer.getOutline(identifier);
        }
        return null;
    }

    public static int getOverlay(LivingEntity entity, float whiteOverlayProgress) {
        return OverlayTexture.packUv(OverlayTexture.getU(whiteOverlayProgress), OverlayTexture.getV(entity.hurtTime > 0 || entity.deathTime > 0));
    }

    protected boolean isVisible(T entity) {
        return !((Entity)entity).isInvisible();
    }

    private static float getYaw(Direction direction) {
        switch (direction) {
            case SOUTH: {
                return 90.0f;
            }
            case WEST: {
                return 0.0f;
            }
            case NORTH: {
                return 270.0f;
            }
            case EAST: {
                return 180.0f;
            }
        }
        return 0.0f;
    }

    protected boolean isShaking(T entity) {
        return false;
    }

    protected void setupTransforms(T entity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta) {
        EntityPose entityPose;
        if (this.isShaking(entity)) {
            bodyYaw += (float)(Math.cos((double)((LivingEntity)entity).age * 3.25) * Math.PI * (double)0.4f);
        }
        if ((entityPose = ((Entity)entity).getPose()) != EntityPose.SLEEPING) {
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0f - bodyYaw));
        }
        if (((LivingEntity)entity).deathTime > 0) {
            float f = ((float)((LivingEntity)entity).deathTime + tickDelta - 1.0f) / 20.0f * 1.6f;
            if ((f = MathHelper.sqrt(f)) > 1.0f) {
                f = 1.0f;
            }
            matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(f * this.getLyingAngle(entity)));
        } else if (((LivingEntity)entity).isUsingRiptide()) {
            matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(-90.0f - ((LivingEntity)entity).pitch));
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(((float)((LivingEntity)entity).age + tickDelta) * -75.0f));
        } else if (entityPose == EntityPose.SLEEPING) {
            Direction direction = ((LivingEntity)entity).getSleepingDirection();
            float _snowman2 = direction != null ? LivingEntityRenderer.getYaw(direction) : bodyYaw;
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(_snowman2));
            matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(this.getLyingAngle(entity)));
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(270.0f));
        } else if ((((Entity)entity).hasCustomName() || entity instanceof PlayerEntity) && ("Dinnerbone".equals(_snowman = Formatting.strip(((Entity)entity).getName().getString())) || "Grumm".equals(_snowman)) && (!(entity instanceof PlayerEntity) || ((PlayerEntity)entity).isPartVisible(PlayerModelPart.CAPE))) {
            matrices.translate(0.0, ((Entity)entity).getHeight() + 0.1f, 0.0);
            matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(180.0f));
        }
    }

    protected float getHandSwingProgress(T entity, float tickDelta) {
        return ((LivingEntity)entity).getHandSwingProgress(tickDelta);
    }

    protected float getAnimationProgress(T entity, float tickDelta) {
        return (float)((LivingEntity)entity).age + tickDelta;
    }

    protected float getLyingAngle(T entity) {
        return 90.0f;
    }

    protected float getAnimationCounter(T entity, float tickDelta) {
        return 0.0f;
    }

    protected void scale(T entity, MatrixStack matrices, float amount) {
    }

    @Override
    protected boolean hasLabel(T t2) {
        T t2;
        double d = this.dispatcher.getSquaredDistanceToCamera((Entity)t2);
        float f = _snowman = ((Entity)t2).isSneaky() ? 32.0f : 64.0f;
        if (d >= (double)(_snowman * _snowman)) {
            return false;
        }
        MinecraftClient _snowman2 = MinecraftClient.getInstance();
        ClientPlayerEntity _snowman3 = _snowman2.player;
        boolean bl = _snowman = !((Entity)t2).isInvisibleTo(_snowman3);
        if (t2 != _snowman3) {
            AbstractTeam abstractTeam = ((Entity)t2).getScoreboardTeam();
            _snowman = _snowman3.getScoreboardTeam();
            if (abstractTeam != null) {
                AbstractTeam.VisibilityRule visibilityRule = abstractTeam.getNameTagVisibilityRule();
                switch (visibilityRule) {
                    case ALWAYS: {
                        return _snowman;
                    }
                    case NEVER: {
                        return false;
                    }
                    case HIDE_FOR_OTHER_TEAMS: {
                        return _snowman == null ? _snowman : abstractTeam.isEqual(_snowman) && (abstractTeam.shouldShowFriendlyInvisibles() || _snowman);
                    }
                    case HIDE_FOR_OWN_TEAM: {
                        return _snowman == null ? _snowman : !abstractTeam.isEqual(_snowman) && _snowman;
                    }
                }
                return true;
            }
        }
        return MinecraftClient.isHudEnabled() && t2 != _snowman2.getCameraEntity() && _snowman && !((Entity)t2).hasPassengers();
    }
}

