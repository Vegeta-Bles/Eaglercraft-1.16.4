/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity;

import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
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

public class GuardianEntityRenderer
extends MobEntityRenderer<GuardianEntity, GuardianEntityModel> {
    private static final Identifier TEXTURE = new Identifier("textures/entity/guardian.png");
    private static final Identifier EXPLOSION_BEAM_TEXTURE = new Identifier("textures/entity/guardian_beam.png");
    private static final RenderLayer LAYER = RenderLayer.getEntityCutoutNoCull(EXPLOSION_BEAM_TEXTURE);

    public GuardianEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        this(entityRenderDispatcher, 0.5f);
    }

    protected GuardianEntityRenderer(EntityRenderDispatcher dispatcher, float f) {
        super(dispatcher, new GuardianEntityModel(), f);
    }

    @Override
    public boolean shouldRender(GuardianEntity guardianEntity, Frustum frustum, double d, double d2, double d3) {
        if (super.shouldRender(guardianEntity, frustum, d, d2, d3)) {
            return true;
        }
        if (guardianEntity.hasBeamTarget() && (_snowman = guardianEntity.getBeamTarget()) != null) {
            Vec3d vec3d = this.fromLerpedPosition(_snowman, (double)_snowman.getHeight() * 0.5, 1.0f);
            _snowman = this.fromLerpedPosition(guardianEntity, guardianEntity.getStandingEyeHeight(), 1.0f);
            return frustum.isVisible(new Box(_snowman.x, _snowman.y, _snowman.z, vec3d.x, vec3d.y, vec3d.z));
        }
        return false;
    }

    private Vec3d fromLerpedPosition(LivingEntity entity, double yOffset, float delta) {
        double d = MathHelper.lerp((double)delta, entity.lastRenderX, entity.getX());
        _snowman = MathHelper.lerp((double)delta, entity.lastRenderY, entity.getY()) + yOffset;
        _snowman = MathHelper.lerp((double)delta, entity.lastRenderZ, entity.getZ());
        return new Vec3d(d, _snowman, _snowman);
    }

    @Override
    public void render(GuardianEntity guardianEntity, float f, float f2, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n) {
        super.render(guardianEntity, f, f2, matrixStack, vertexConsumerProvider, n);
        LivingEntity livingEntity = guardianEntity.getBeamTarget();
        if (livingEntity != null) {
            float f3 = guardianEntity.getBeamProgress(f2);
            _snowman = (float)guardianEntity.world.getTime() + f2;
            _snowman = _snowman * 0.5f % 1.0f;
            _snowman = guardianEntity.getStandingEyeHeight();
            matrixStack.push();
            matrixStack.translate(0.0, _snowman, 0.0);
            Vec3d _snowman2 = this.fromLerpedPosition(livingEntity, (double)livingEntity.getHeight() * 0.5, f2);
            Vec3d _snowman3 = this.fromLerpedPosition(guardianEntity, _snowman, f2);
            Vec3d _snowman4 = _snowman2.subtract(_snowman3);
            _snowman = (float)(_snowman4.length() + 1.0);
            _snowman4 = _snowman4.normalize();
            _snowman = (float)Math.acos(_snowman4.y);
            _snowman = (float)Math.atan2(_snowman4.z, _snowman4.x);
            matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion((1.5707964f - _snowman) * 57.295776f));
            matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(_snowman * 57.295776f));
            boolean _snowman5 = true;
            _snowman = _snowman * 0.05f * -1.5f;
            _snowman = f3 * f3;
            int _snowman6 = 64 + (int)(_snowman * 191.0f);
            int _snowman7 = 32 + (int)(_snowman * 191.0f);
            int _snowman8 = 128 - (int)(_snowman * 64.0f);
            _snowman = 0.2f;
            _snowman = 0.282f;
            _snowman = MathHelper.cos(_snowman + 2.3561945f) * 0.282f;
            _snowman = MathHelper.sin(_snowman + 2.3561945f) * 0.282f;
            _snowman = MathHelper.cos(_snowman + 0.7853982f) * 0.282f;
            _snowman = MathHelper.sin(_snowman + 0.7853982f) * 0.282f;
            _snowman = MathHelper.cos(_snowman + 3.926991f) * 0.282f;
            _snowman = MathHelper.sin(_snowman + 3.926991f) * 0.282f;
            _snowman = MathHelper.cos(_snowman + 5.4977875f) * 0.282f;
            _snowman = MathHelper.sin(_snowman + 5.4977875f) * 0.282f;
            _snowman = MathHelper.cos(_snowman + (float)Math.PI) * 0.2f;
            _snowman = MathHelper.sin(_snowman + (float)Math.PI) * 0.2f;
            _snowman = MathHelper.cos(_snowman + 0.0f) * 0.2f;
            _snowman = MathHelper.sin(_snowman + 0.0f) * 0.2f;
            _snowman = MathHelper.cos(_snowman + 1.5707964f) * 0.2f;
            _snowman = MathHelper.sin(_snowman + 1.5707964f) * 0.2f;
            _snowman = MathHelper.cos(_snowman + 4.712389f) * 0.2f;
            _snowman = MathHelper.sin(_snowman + 4.712389f) * 0.2f;
            _snowman = _snowman;
            _snowman = 0.0f;
            _snowman = 0.4999f;
            _snowman = -1.0f + _snowman;
            _snowman = _snowman * 2.5f + _snowman;
            VertexConsumer _snowman9 = vertexConsumerProvider.getBuffer(LAYER);
            MatrixStack.Entry _snowman10 = matrixStack.peek();
            Matrix4f _snowman11 = _snowman10.getModel();
            Matrix3f _snowman12 = _snowman10.getNormal();
            GuardianEntityRenderer.method_23173(_snowman9, _snowman11, _snowman12, _snowman, _snowman, _snowman, _snowman6, _snowman7, _snowman8, 0.4999f, _snowman);
            GuardianEntityRenderer.method_23173(_snowman9, _snowman11, _snowman12, _snowman, 0.0f, _snowman, _snowman6, _snowman7, _snowman8, 0.4999f, _snowman);
            GuardianEntityRenderer.method_23173(_snowman9, _snowman11, _snowman12, _snowman, 0.0f, _snowman, _snowman6, _snowman7, _snowman8, 0.0f, _snowman);
            GuardianEntityRenderer.method_23173(_snowman9, _snowman11, _snowman12, _snowman, _snowman, _snowman, _snowman6, _snowman7, _snowman8, 0.0f, _snowman);
            GuardianEntityRenderer.method_23173(_snowman9, _snowman11, _snowman12, _snowman, _snowman, _snowman, _snowman6, _snowman7, _snowman8, 0.4999f, _snowman);
            GuardianEntityRenderer.method_23173(_snowman9, _snowman11, _snowman12, _snowman, 0.0f, _snowman, _snowman6, _snowman7, _snowman8, 0.4999f, _snowman);
            GuardianEntityRenderer.method_23173(_snowman9, _snowman11, _snowman12, _snowman, 0.0f, _snowman, _snowman6, _snowman7, _snowman8, 0.0f, _snowman);
            GuardianEntityRenderer.method_23173(_snowman9, _snowman11, _snowman12, _snowman, _snowman, _snowman, _snowman6, _snowman7, _snowman8, 0.0f, _snowman);
            _snowman = 0.0f;
            if (guardianEntity.age % 2 == 0) {
                _snowman = 0.5f;
            }
            GuardianEntityRenderer.method_23173(_snowman9, _snowman11, _snowman12, _snowman, _snowman, _snowman, _snowman6, _snowman7, _snowman8, 0.5f, _snowman + 0.5f);
            GuardianEntityRenderer.method_23173(_snowman9, _snowman11, _snowman12, _snowman, _snowman, _snowman, _snowman6, _snowman7, _snowman8, 1.0f, _snowman + 0.5f);
            GuardianEntityRenderer.method_23173(_snowman9, _snowman11, _snowman12, _snowman, _snowman, _snowman, _snowman6, _snowman7, _snowman8, 1.0f, _snowman);
            GuardianEntityRenderer.method_23173(_snowman9, _snowman11, _snowman12, _snowman, _snowman, _snowman, _snowman6, _snowman7, _snowman8, 0.5f, _snowman);
            matrixStack.pop();
        }
    }

    private static void method_23173(VertexConsumer vertexConsumer, Matrix4f matrix4f, Matrix3f matrix3f, float f, float f2, float f3, int n, int n2, int n3, float f4, float f5) {
        vertexConsumer.vertex(matrix4f, f, f2, f3).color(n, n2, n3, 255).texture(f4, f5).overlay(OverlayTexture.DEFAULT_UV).light(0xF000F0).normal(matrix3f, 0.0f, 1.0f, 0.0f).next();
    }

    @Override
    public Identifier getTexture(GuardianEntity guardianEntity) {
        return TEXTURE;
    }
}

