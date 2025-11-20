/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.render.entity;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EndCrystalEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;

public class EnderDragonEntityRenderer
extends EntityRenderer<EnderDragonEntity> {
    public static final Identifier CRYSTAL_BEAM_TEXTURE = new Identifier("textures/entity/end_crystal/end_crystal_beam.png");
    private static final Identifier EXPLOSION_TEXTURE = new Identifier("textures/entity/enderdragon/dragon_exploding.png");
    private static final Identifier TEXTURE = new Identifier("textures/entity/enderdragon/dragon.png");
    private static final Identifier EYE_TEXTURE = new Identifier("textures/entity/enderdragon/dragon_eyes.png");
    private static final RenderLayer DRAGON_CUTOUT = RenderLayer.getEntityCutoutNoCull(TEXTURE);
    private static final RenderLayer DRAGON_DECAL = RenderLayer.getEntityDecal(TEXTURE);
    private static final RenderLayer DRAGON_EYES = RenderLayer.getEyes(EYE_TEXTURE);
    private static final RenderLayer CRYSTAL_BEAM_LAYER = RenderLayer.getEntitySmoothCutout(CRYSTAL_BEAM_TEXTURE);
    private static final float HALF_SQRT_3 = (float)(Math.sqrt(3.0) / 2.0);
    private final DragonEntityModel model = new DragonEntityModel();

    public EnderDragonEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher);
        this.shadowRadius = 0.5f;
    }

    @Override
    public void render(EnderDragonEntity enderDragonEntity2, float f, float f2, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider2, int n) {
        EnderDragonEntity enderDragonEntity2;
        matrixStack.push();
        float f3 = (float)enderDragonEntity2.getSegmentProperties(7, f2)[0];
        _snowman = (float)(enderDragonEntity2.getSegmentProperties(5, f2)[1] - enderDragonEntity2.getSegmentProperties(10, f2)[1]);
        matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-f3));
        matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(_snowman * 10.0f));
        matrixStack.translate(0.0, 0.0, 1.0);
        matrixStack.scale(-1.0f, -1.0f, 1.0f);
        matrixStack.translate(0.0, -1.501f, 0.0);
        boolean _snowman2 = enderDragonEntity2.hurtTime > 0;
        this.model.animateModel(enderDragonEntity2, 0.0f, 0.0f, f2);
        if (enderDragonEntity2.ticksSinceDeath > 0) {
            _snowman = (float)enderDragonEntity2.ticksSinceDeath / 200.0f;
            VertexConsumer vertexConsumer = vertexConsumerProvider2.getBuffer(RenderLayer.getEntityAlpha(EXPLOSION_TEXTURE, _snowman));
            this.model.render(matrixStack, vertexConsumer, n, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
            _snowman = vertexConsumerProvider2.getBuffer(DRAGON_DECAL);
            this.model.render(matrixStack, _snowman, n, OverlayTexture.getUv(0.0f, _snowman2), 1.0f, 1.0f, 1.0f, 1.0f);
        } else {
            VertexConsumerProvider vertexConsumerProvider2;
            VertexConsumer _snowman3 = vertexConsumerProvider2.getBuffer(DRAGON_CUTOUT);
            this.model.render(matrixStack, _snowman3, n, OverlayTexture.getUv(0.0f, _snowman2), 1.0f, 1.0f, 1.0f, 1.0f);
        }
        VertexConsumer vertexConsumer = vertexConsumerProvider2.getBuffer(DRAGON_EYES);
        this.model.render(matrixStack, vertexConsumer, n, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
        if (enderDragonEntity2.ticksSinceDeath > 0) {
            float f4 = ((float)enderDragonEntity2.ticksSinceDeath + f2) / 200.0f;
            _snowman = Math.min(f4 > 0.8f ? (f4 - 0.8f) / 0.2f : 0.0f, 1.0f);
            Random _snowman4 = new Random(432L);
            VertexConsumer _snowman5 = vertexConsumerProvider2.getBuffer(RenderLayer.getLightning());
            matrixStack.push();
            matrixStack.translate(0.0, -1.0, -2.0);
            int _snowman6 = 0;
            while ((float)_snowman6 < (f4 + f4 * f4) / 2.0f * 60.0f) {
                matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(_snowman4.nextFloat() * 360.0f));
                matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(_snowman4.nextFloat() * 360.0f));
                matrixStack.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(_snowman4.nextFloat() * 360.0f));
                matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(_snowman4.nextFloat() * 360.0f));
                matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(_snowman4.nextFloat() * 360.0f));
                matrixStack.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(_snowman4.nextFloat() * 360.0f + f4 * 90.0f));
                _snowman = _snowman4.nextFloat() * 20.0f + 5.0f + _snowman * 10.0f;
                _snowman = _snowman4.nextFloat() * 2.0f + 1.0f + _snowman * 2.0f;
                Matrix4f matrix4f = matrixStack.peek().getModel();
                int _snowman7 = (int)(255.0f * (1.0f - _snowman));
                EnderDragonEntityRenderer.method_23157(_snowman5, matrix4f, _snowman7);
                EnderDragonEntityRenderer.method_23156(_snowman5, matrix4f, _snowman, _snowman);
                EnderDragonEntityRenderer.method_23158(_snowman5, matrix4f, _snowman, _snowman);
                EnderDragonEntityRenderer.method_23157(_snowman5, matrix4f, _snowman7);
                EnderDragonEntityRenderer.method_23158(_snowman5, matrix4f, _snowman, _snowman);
                EnderDragonEntityRenderer.method_23159(_snowman5, matrix4f, _snowman, _snowman);
                EnderDragonEntityRenderer.method_23157(_snowman5, matrix4f, _snowman7);
                EnderDragonEntityRenderer.method_23159(_snowman5, matrix4f, _snowman, _snowman);
                EnderDragonEntityRenderer.method_23156(_snowman5, matrix4f, _snowman, _snowman);
                ++_snowman6;
            }
            matrixStack.pop();
        }
        matrixStack.pop();
        if (enderDragonEntity2.connectedCrystal != null) {
            matrixStack.push();
            float f5 = (float)(enderDragonEntity2.connectedCrystal.getX() - MathHelper.lerp((double)f2, enderDragonEntity2.prevX, enderDragonEntity2.getX()));
            _snowman = (float)(enderDragonEntity2.connectedCrystal.getY() - MathHelper.lerp((double)f2, enderDragonEntity2.prevY, enderDragonEntity2.getY()));
            _snowman = (float)(enderDragonEntity2.connectedCrystal.getZ() - MathHelper.lerp((double)f2, enderDragonEntity2.prevZ, enderDragonEntity2.getZ()));
            EnderDragonEntityRenderer.renderCrystalBeam(f5, _snowman + EndCrystalEntityRenderer.getYOffset(enderDragonEntity2.connectedCrystal, f2), _snowman, f2, enderDragonEntity2.age, matrixStack, vertexConsumerProvider2, n);
            matrixStack.pop();
        }
        super.render(enderDragonEntity2, f, f2, matrixStack, vertexConsumerProvider2, n);
    }

    private static void method_23157(VertexConsumer vertices, Matrix4f matrix, int alpha) {
        vertices.vertex(matrix, 0.0f, 0.0f, 0.0f).color(255, 255, 255, alpha).next();
        vertices.vertex(matrix, 0.0f, 0.0f, 0.0f).color(255, 255, 255, alpha).next();
    }

    private static void method_23156(VertexConsumer vertices, Matrix4f matrix, float y, float x) {
        vertices.vertex(matrix, -HALF_SQRT_3 * x, y, -0.5f * x).color(255, 0, 255, 0).next();
    }

    private static void method_23158(VertexConsumer vertices, Matrix4f matrix, float y, float x) {
        vertices.vertex(matrix, HALF_SQRT_3 * x, y, -0.5f * x).color(255, 0, 255, 0).next();
    }

    private static void method_23159(VertexConsumer vertices, Matrix4f matrix, float y, float z) {
        vertices.vertex(matrix, 0.0f, y, 1.0f * z).color(255, 0, 255, 0).next();
    }

    public static void renderCrystalBeam(float dx, float dy, float dz, float tickDelta, int age, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        float f = MathHelper.sqrt(dx * dx + dz * dz);
        _snowman = MathHelper.sqrt(dx * dx + dy * dy + dz * dz);
        matrices.push();
        matrices.translate(0.0, 2.0, 0.0);
        matrices.multiply(Vector3f.POSITIVE_Y.getRadialQuaternion((float)(-Math.atan2(dz, dx)) - 1.5707964f));
        matrices.multiply(Vector3f.POSITIVE_X.getRadialQuaternion((float)(-Math.atan2(f, dy)) - 1.5707964f));
        VertexConsumer _snowman2 = vertexConsumers.getBuffer(CRYSTAL_BEAM_LAYER);
        _snowman = 0.0f - ((float)age + tickDelta) * 0.01f;
        _snowman = MathHelper.sqrt(dx * dx + dy * dy + dz * dz) / 32.0f - ((float)age + tickDelta) * 0.01f;
        int _snowman3 = 8;
        _snowman = 0.0f;
        _snowman = 0.75f;
        _snowman = 0.0f;
        MatrixStack.Entry _snowman4 = matrices.peek();
        Matrix4f _snowman5 = _snowman4.getModel();
        Matrix3f _snowman6 = _snowman4.getNormal();
        for (int i = 1; i <= 8; ++i) {
            float f2 = MathHelper.sin((float)i * ((float)Math.PI * 2) / 8.0f) * 0.75f;
            _snowman = MathHelper.cos((float)i * ((float)Math.PI * 2) / 8.0f) * 0.75f;
            _snowman = (float)i / 8.0f;
            _snowman2.vertex(_snowman5, _snowman * 0.2f, _snowman * 0.2f, 0.0f).color(0, 0, 0, 255).texture(_snowman, _snowman).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(_snowman6, 0.0f, -1.0f, 0.0f).next();
            _snowman2.vertex(_snowman5, _snowman, _snowman, _snowman).color(255, 255, 255, 255).texture(_snowman, _snowman).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(_snowman6, 0.0f, -1.0f, 0.0f).next();
            _snowman2.vertex(_snowman5, f2, _snowman, _snowman).color(255, 255, 255, 255).texture(_snowman, _snowman).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(_snowman6, 0.0f, -1.0f, 0.0f).next();
            _snowman2.vertex(_snowman5, f2 * 0.2f, _snowman * 0.2f, 0.0f).color(0, 0, 0, 255).texture(_snowman, _snowman).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(_snowman6, 0.0f, -1.0f, 0.0f).next();
            _snowman = f2;
            _snowman = _snowman;
            _snowman = _snowman;
        }
        matrices.pop();
    }

    @Override
    public Identifier getTexture(EnderDragonEntity enderDragonEntity) {
        return TEXTURE;
    }

    public static class DragonEntityModel
    extends EntityModel<EnderDragonEntity> {
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
            float f = -16.0f;
            this.head = new ModelPart(this);
            this.head.addCuboid("upperlip", -6.0f, -1.0f, -24.0f, 12, 5, 16, 0.0f, 176, 44);
            this.head.addCuboid("upperhead", -8.0f, -8.0f, -10.0f, 16, 16, 16, 0.0f, 112, 30);
            this.head.mirror = true;
            this.head.addCuboid("scale", -5.0f, -12.0f, -4.0f, 2, 4, 6, 0.0f, 0, 0);
            this.head.addCuboid("nostril", -5.0f, -3.0f, -22.0f, 2, 2, 4, 0.0f, 112, 0);
            this.head.mirror = false;
            this.head.addCuboid("scale", 3.0f, -12.0f, -4.0f, 2, 4, 6, 0.0f, 0, 0);
            this.head.addCuboid("nostril", 3.0f, -3.0f, -22.0f, 2, 2, 4, 0.0f, 112, 0);
            this.jaw = new ModelPart(this);
            this.jaw.setPivot(0.0f, 4.0f, -8.0f);
            this.jaw.addCuboid("jaw", -6.0f, 0.0f, -16.0f, 12, 4, 16, 0.0f, 176, 65);
            this.head.addChild(this.jaw);
            this.neck = new ModelPart(this);
            this.neck.addCuboid("box", -5.0f, -5.0f, -5.0f, 10, 10, 10, 0.0f, 192, 104);
            this.neck.addCuboid("scale", -1.0f, -9.0f, -3.0f, 2, 4, 6, 0.0f, 48, 0);
            this.body = new ModelPart(this);
            this.body.setPivot(0.0f, 4.0f, 8.0f);
            this.body.addCuboid("body", -12.0f, 0.0f, -16.0f, 24, 24, 64, 0.0f, 0, 0);
            this.body.addCuboid("scale", -1.0f, -6.0f, -10.0f, 2, 6, 12, 0.0f, 220, 53);
            this.body.addCuboid("scale", -1.0f, -6.0f, 10.0f, 2, 6, 12, 0.0f, 220, 53);
            this.body.addCuboid("scale", -1.0f, -6.0f, 30.0f, 2, 6, 12, 0.0f, 220, 53);
            this.wing = new ModelPart(this);
            this.wing.mirror = true;
            this.wing.setPivot(12.0f, 5.0f, 2.0f);
            this.wing.addCuboid("bone", 0.0f, -4.0f, -4.0f, 56, 8, 8, 0.0f, 112, 88);
            this.wing.addCuboid("skin", 0.0f, 0.0f, 2.0f, 56, 0, 56, 0.0f, -56, 88);
            this.field_21548 = new ModelPart(this);
            this.field_21548.mirror = true;
            this.field_21548.setPivot(56.0f, 0.0f, 0.0f);
            this.field_21548.addCuboid("bone", 0.0f, -2.0f, -2.0f, 56, 4, 4, 0.0f, 112, 136);
            this.field_21548.addCuboid("skin", 0.0f, 0.0f, 2.0f, 56, 0, 56, 0.0f, -56, 144);
            this.wing.addChild(this.field_21548);
            this.field_21549 = new ModelPart(this);
            this.field_21549.setPivot(12.0f, 20.0f, 2.0f);
            this.field_21549.addCuboid("main", -4.0f, -4.0f, -4.0f, 8, 24, 8, 0.0f, 112, 104);
            this.field_21550 = new ModelPart(this);
            this.field_21550.setPivot(0.0f, 20.0f, -1.0f);
            this.field_21550.addCuboid("main", -3.0f, -1.0f, -3.0f, 6, 24, 6, 0.0f, 226, 138);
            this.field_21549.addChild(this.field_21550);
            this.field_21551 = new ModelPart(this);
            this.field_21551.setPivot(0.0f, 23.0f, 0.0f);
            this.field_21551.addCuboid("main", -4.0f, 0.0f, -12.0f, 8, 4, 16, 0.0f, 144, 104);
            this.field_21550.addChild(this.field_21551);
            this.field_21552 = new ModelPart(this);
            this.field_21552.setPivot(16.0f, 16.0f, 42.0f);
            this.field_21552.addCuboid("main", -8.0f, -4.0f, -8.0f, 16, 32, 16, 0.0f, 0, 0);
            this.field_21553 = new ModelPart(this);
            this.field_21553.setPivot(0.0f, 32.0f, -4.0f);
            this.field_21553.addCuboid("main", -6.0f, -2.0f, 0.0f, 12, 32, 12, 0.0f, 196, 0);
            this.field_21552.addChild(this.field_21553);
            this.field_21554 = new ModelPart(this);
            this.field_21554.setPivot(0.0f, 31.0f, 4.0f);
            this.field_21554.addCuboid("main", -9.0f, 0.0f, -20.0f, 18, 6, 24, 0.0f, 112, 0);
            this.field_21553.addChild(this.field_21554);
            this.field_21555 = new ModelPart(this);
            this.field_21555.setPivot(-12.0f, 5.0f, 2.0f);
            this.field_21555.addCuboid("bone", -56.0f, -4.0f, -4.0f, 56, 8, 8, 0.0f, 112, 88);
            this.field_21555.addCuboid("skin", -56.0f, 0.0f, 2.0f, 56, 0, 56, 0.0f, -56, 88);
            this.wingTip = new ModelPart(this);
            this.wingTip.setPivot(-56.0f, 0.0f, 0.0f);
            this.wingTip.addCuboid("bone", -56.0f, -2.0f, -2.0f, 56, 4, 4, 0.0f, 112, 136);
            this.wingTip.addCuboid("skin", -56.0f, 0.0f, 2.0f, 56, 0, 56, 0.0f, -56, 144);
            this.field_21555.addChild(this.wingTip);
            this.frontLeg = new ModelPart(this);
            this.frontLeg.setPivot(-12.0f, 20.0f, 2.0f);
            this.frontLeg.addCuboid("main", -4.0f, -4.0f, -4.0f, 8, 24, 8, 0.0f, 112, 104);
            this.frontLegTip = new ModelPart(this);
            this.frontLegTip.setPivot(0.0f, 20.0f, -1.0f);
            this.frontLegTip.addCuboid("main", -3.0f, -1.0f, -3.0f, 6, 24, 6, 0.0f, 226, 138);
            this.frontLeg.addChild(this.frontLegTip);
            this.frontFoot = new ModelPart(this);
            this.frontFoot.setPivot(0.0f, 23.0f, 0.0f);
            this.frontFoot.addCuboid("main", -4.0f, 0.0f, -12.0f, 8, 4, 16, 0.0f, 144, 104);
            this.frontLegTip.addChild(this.frontFoot);
            this.rearLeg = new ModelPart(this);
            this.rearLeg.setPivot(-16.0f, 16.0f, 42.0f);
            this.rearLeg.addCuboid("main", -8.0f, -4.0f, -8.0f, 16, 32, 16, 0.0f, 0, 0);
            this.rearLegTip = new ModelPart(this);
            this.rearLegTip.setPivot(0.0f, 32.0f, -4.0f);
            this.rearLegTip.addCuboid("main", -6.0f, -2.0f, 0.0f, 12, 32, 12, 0.0f, 196, 0);
            this.rearLeg.addChild(this.rearLegTip);
            this.rearFoot = new ModelPart(this);
            this.rearFoot.setPivot(0.0f, 31.0f, 4.0f);
            this.rearFoot.addCuboid("main", -9.0f, 0.0f, -20.0f, 18, 6, 24, 0.0f, 112, 0);
            this.rearLegTip.addChild(this.rearFoot);
        }

        @Override
        public void animateModel(EnderDragonEntity enderDragonEntity, float f, float f2, float f3) {
            this.dragon = enderDragonEntity;
            this.tickDelta = f3;
        }

        @Override
        public void setAngles(EnderDragonEntity enderDragonEntity, float f, float f2, float f3, float f4, float f5) {
        }

        @Override
        public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
            float _snowman6;
            float _snowman5;
            float _snowman4;
            float _snowman3;
            matrices.push();
            float f = MathHelper.lerp(this.tickDelta, this.dragon.prevWingPosition, this.dragon.wingPosition);
            this.jaw.pitch = (float)(Math.sin(f * ((float)Math.PI * 2)) + 1.0) * 0.2f;
            _snowman = (float)(Math.sin(f * ((float)Math.PI * 2) - 1.0f) + 1.0);
            _snowman = (_snowman * _snowman + _snowman * 2.0f) * 0.05f;
            matrices.translate(0.0, _snowman - 2.0f, -3.0);
            matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(_snowman * 2.0f));
            _snowman6 = 0.0f;
            _snowman4 = 20.0f;
            _snowman5 = -12.0f;
            _snowman = 1.5f;
            double[] _snowman2 = this.dragon.getSegmentProperties(6, this.tickDelta);
            _snowman = MathHelper.fwrapDegrees(this.dragon.getSegmentProperties(5, this.tickDelta)[0] - this.dragon.getSegmentProperties(10, this.tickDelta)[0]);
            _snowman = MathHelper.fwrapDegrees(this.dragon.getSegmentProperties(5, this.tickDelta)[0] + (double)(_snowman / 2.0f));
            _snowman = f * ((float)Math.PI * 2);
            for (int i = 0; i < 5; ++i) {
                double[] dArray = this.dragon.getSegmentProperties(5 - i, this.tickDelta);
                _snowman3 = (float)Math.cos((float)i * 0.45f + _snowman) * 0.15f;
                this.neck.yaw = MathHelper.fwrapDegrees(dArray[0] - _snowman2[0]) * ((float)Math.PI / 180) * 1.5f;
                this.neck.pitch = _snowman3 + this.dragon.method_6823(i, _snowman2, dArray) * ((float)Math.PI / 180) * 1.5f * 5.0f;
                this.neck.roll = -MathHelper.fwrapDegrees(dArray[0] - (double)_snowman) * ((float)Math.PI / 180) * 1.5f;
                this.neck.pivotY = _snowman4;
                this.neck.pivotZ = _snowman5;
                this.neck.pivotX = _snowman6;
                _snowman4 = (float)((double)_snowman4 + Math.sin(this.neck.pitch) * 10.0);
                _snowman5 = (float)((double)_snowman5 - Math.cos(this.neck.yaw) * Math.cos(this.neck.pitch) * 10.0);
                _snowman6 = (float)((double)_snowman6 - Math.sin(this.neck.yaw) * Math.cos(this.neck.pitch) * 10.0);
                this.neck.render(matrices, vertices, light, overlay);
            }
            this.head.pivotY = _snowman4;
            this.head.pivotZ = _snowman5;
            this.head.pivotX = _snowman6;
            dArray = this.dragon.getSegmentProperties(0, this.tickDelta);
            this.head.yaw = MathHelper.fwrapDegrees(dArray[0] - _snowman2[0]) * ((float)Math.PI / 180);
            this.head.pitch = MathHelper.fwrapDegrees(this.dragon.method_6823(6, _snowman2, dArray)) * ((float)Math.PI / 180) * 1.5f * 5.0f;
            this.head.roll = -MathHelper.fwrapDegrees(dArray[0] - (double)_snowman) * ((float)Math.PI / 180);
            this.head.render(matrices, vertices, light, overlay);
            matrices.push();
            matrices.translate(0.0, 1.0, 0.0);
            matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(-_snowman * 1.5f));
            matrices.translate(0.0, -1.0, 0.0);
            this.body.roll = 0.0f;
            this.body.render(matrices, vertices, light, overlay);
            float f2 = f * ((float)Math.PI * 2);
            this.wing.pitch = 0.125f - (float)Math.cos(f2) * 0.2f;
            this.wing.yaw = -0.25f;
            this.wing.roll = -((float)(Math.sin(f2) + 0.125)) * 0.8f;
            this.field_21548.roll = (float)(Math.sin(f2 + 2.0f) + 0.5) * 0.75f;
            this.field_21555.pitch = this.wing.pitch;
            this.field_21555.yaw = -this.wing.yaw;
            this.field_21555.roll = -this.wing.roll;
            this.wingTip.roll = -this.field_21548.roll;
            this.method_23838(matrices, vertices, light, overlay, _snowman, this.wing, this.field_21549, this.field_21550, this.field_21551, this.field_21552, this.field_21553, this.field_21554);
            this.method_23838(matrices, vertices, light, overlay, _snowman, this.field_21555, this.frontLeg, this.frontLegTip, this.frontFoot, this.rearLeg, this.rearLegTip, this.rearFoot);
            matrices.pop();
            _snowman3 = -((float)Math.sin(f * ((float)Math.PI * 2))) * 0.0f;
            _snowman = f * ((float)Math.PI * 2);
            _snowman4 = 10.0f;
            _snowman5 = 60.0f;
            _snowman6 = 0.0f;
            _snowman2 = this.dragon.getSegmentProperties(11, this.tickDelta);
            for (int i = 0; i < 12; ++i) {
                double[] dArray = this.dragon.getSegmentProperties(12 + i, this.tickDelta);
                _snowman3 = (float)((double)_snowman3 + Math.sin((float)i * 0.45f + _snowman) * (double)0.05f);
                this.neck.yaw = (MathHelper.fwrapDegrees(dArray[0] - _snowman2[0]) * 1.5f + 180.0f) * ((float)Math.PI / 180);
                this.neck.pitch = _snowman3 + (float)(dArray[1] - _snowman2[1]) * ((float)Math.PI / 180) * 1.5f * 5.0f;
                this.neck.roll = MathHelper.fwrapDegrees(dArray[0] - (double)_snowman) * ((float)Math.PI / 180) * 1.5f;
                this.neck.pivotY = _snowman4;
                this.neck.pivotZ = _snowman5;
                this.neck.pivotX = _snowman6;
                _snowman4 = (float)((double)_snowman4 + Math.sin(this.neck.pitch) * 10.0);
                _snowman5 = (float)((double)_snowman5 - Math.cos(this.neck.yaw) * Math.cos(this.neck.pitch) * 10.0);
                _snowman6 = (float)((double)_snowman6 - Math.sin(this.neck.yaw) * Math.cos(this.neck.pitch) * 10.0);
                this.neck.render(matrices, vertices, light, overlay);
            }
            matrices.pop();
        }

        private void method_23838(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float offset, ModelPart modelPart, ModelPart modelPart2, ModelPart modelPart3, ModelPart modelPart4, ModelPart modelPart5, ModelPart modelPart6, ModelPart modelPart7) {
            modelPart5.pitch = 1.0f + offset * 0.1f;
            modelPart6.pitch = 0.5f + offset * 0.1f;
            modelPart7.pitch = 0.75f + offset * 0.1f;
            modelPart2.pitch = 1.3f + offset * 0.1f;
            modelPart3.pitch = -0.5f - offset * 0.1f;
            modelPart4.pitch = 0.75f + offset * 0.1f;
            modelPart.render(matrices, vertices, light, overlay);
            modelPart2.render(matrices, vertices, light, overlay);
            modelPart5.render(matrices, vertices, light, overlay);
        }
    }
}

