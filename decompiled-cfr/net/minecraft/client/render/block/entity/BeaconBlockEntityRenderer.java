/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.block.entity;

import java.util.List;
import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;

public class BeaconBlockEntityRenderer
extends BlockEntityRenderer<BeaconBlockEntity> {
    public static final Identifier BEAM_TEXTURE = new Identifier("textures/entity/beacon_beam.png");

    public BeaconBlockEntityRenderer(BlockEntityRenderDispatcher blockEntityRenderDispatcher) {
        super(blockEntityRenderDispatcher);
    }

    @Override
    public void render(BeaconBlockEntity beaconBlockEntity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n, int n2) {
        long l = beaconBlockEntity.getWorld().getTime();
        List<BeaconBlockEntity.BeamSegment> _snowman2 = beaconBlockEntity.getBeamSegments();
        int _snowman3 = 0;
        for (int i = 0; i < _snowman2.size(); ++i) {
            BeaconBlockEntity.BeamSegment beamSegment = _snowman2.get(i);
            BeaconBlockEntityRenderer.render(matrixStack, vertexConsumerProvider, f, l, _snowman3, i == _snowman2.size() - 1 ? 1024 : beamSegment.getHeight(), beamSegment.getColor());
            _snowman3 += beamSegment.getHeight();
        }
    }

    private static void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, float f, long l, int n, int n2, float[] fArray) {
        BeaconBlockEntityRenderer.renderLightBeam(matrixStack, vertexConsumerProvider, BEAM_TEXTURE, f, 1.0f, l, n, n2, fArray, 0.2f, 0.25f);
    }

    public static void renderLightBeam(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, Identifier identifier, float f, float f2, long l, int n, int n2, float[] fArray, float f3, float f4) {
        int n3 = n + n2;
        matrixStack.push();
        matrixStack.translate(0.5, 0.0, 0.5);
        float _snowman2 = (float)Math.floorMod(l, 40L) + f;
        float _snowman3 = n2 < 0 ? _snowman2 : -_snowman2;
        float _snowman4 = MathHelper.fractionalPart(_snowman3 * 0.2f - (float)MathHelper.floor(_snowman3 * 0.1f));
        float _snowman5 = fArray[0];
        float _snowman6 = fArray[1];
        float _snowman7 = fArray[2];
        matrixStack.push();
        matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(_snowman2 * 2.25f - 45.0f));
        float _snowman8 = 0.0f;
        float _snowman9 = f3;
        float _snowman10 = f3;
        float _snowman11 = 0.0f;
        float _snowman12 = -f3;
        float _snowman13 = 0.0f;
        float _snowman14 = 0.0f;
        float _snowman15 = -f3;
        float _snowman16 = 0.0f;
        float _snowman17 = 1.0f;
        float _snowman18 = -1.0f + _snowman4;
        float _snowman19 = (float)n2 * f2 * (0.5f / f3) + _snowman18;
        BeaconBlockEntityRenderer.method_22741(matrixStack, vertexConsumerProvider.getBuffer(RenderLayer.getBeaconBeam(identifier, false)), _snowman5, _snowman6, _snowman7, 1.0f, n, n3, 0.0f, _snowman9, _snowman10, 0.0f, _snowman12, 0.0f, 0.0f, _snowman15, 0.0f, 1.0f, _snowman19, _snowman18);
        matrixStack.pop();
        _snowman8 = -f4;
        _snowman9 = -f4;
        _snowman10 = f4;
        _snowman11 = -f4;
        _snowman12 = -f4;
        _snowman13 = f4;
        _snowman14 = f4;
        _snowman15 = f4;
        _snowman16 = 0.0f;
        _snowman17 = 1.0f;
        _snowman18 = -1.0f + _snowman4;
        _snowman19 = (float)n2 * f2 + _snowman18;
        BeaconBlockEntityRenderer.method_22741(matrixStack, vertexConsumerProvider.getBuffer(RenderLayer.getBeaconBeam(identifier, true)), _snowman5, _snowman6, _snowman7, 0.125f, n, n3, _snowman8, _snowman9, _snowman10, _snowman11, _snowman12, _snowman13, _snowman14, _snowman15, 0.0f, 1.0f, _snowman19, _snowman18);
        matrixStack.pop();
    }

    private static void method_22741(MatrixStack matrixStack, VertexConsumer vertexConsumer, float f, float f2, float f3, float f4, int n, int n2, float f5, float f6, float f7, float f8, float f9, float f10, float f11, float f12, float f13, float f14, float f15, float f16) {
        MatrixStack.Entry entry = matrixStack.peek();
        Matrix4f _snowman2 = entry.getModel();
        Matrix3f _snowman3 = entry.getNormal();
        BeaconBlockEntityRenderer.method_22740(_snowman2, _snowman3, vertexConsumer, f, f2, f3, f4, n, n2, f5, f6, f7, f8, f13, f14, f15, f16);
        BeaconBlockEntityRenderer.method_22740(_snowman2, _snowman3, vertexConsumer, f, f2, f3, f4, n, n2, f11, f12, f9, f10, f13, f14, f15, f16);
        BeaconBlockEntityRenderer.method_22740(_snowman2, _snowman3, vertexConsumer, f, f2, f3, f4, n, n2, f7, f8, f11, f12, f13, f14, f15, f16);
        BeaconBlockEntityRenderer.method_22740(_snowman2, _snowman3, vertexConsumer, f, f2, f3, f4, n, n2, f9, f10, f5, f6, f13, f14, f15, f16);
    }

    private static void method_22740(Matrix4f matrix4f, Matrix3f matrix3f, VertexConsumer vertexConsumer, float f, float f2, float f3, float f4, int n, int n2, float f5, float f6, float f7, float f8, float f9, float f10, float f11, float f12) {
        BeaconBlockEntityRenderer.method_23076(matrix4f, matrix3f, vertexConsumer, f, f2, f3, f4, n2, f5, f6, f10, f11);
        BeaconBlockEntityRenderer.method_23076(matrix4f, matrix3f, vertexConsumer, f, f2, f3, f4, n, f5, f6, f10, f12);
        BeaconBlockEntityRenderer.method_23076(matrix4f, matrix3f, vertexConsumer, f, f2, f3, f4, n, f7, f8, f9, f12);
        BeaconBlockEntityRenderer.method_23076(matrix4f, matrix3f, vertexConsumer, f, f2, f3, f4, n2, f7, f8, f9, f11);
    }

    private static void method_23076(Matrix4f matrix4f, Matrix3f matrix3f, VertexConsumer vertexConsumer, float f, float f2, float f3, float f4, int n, float f5, float f6, float f7, float f8) {
        vertexConsumer.vertex(matrix4f, f5, n, f6).color(f, f2, f3, f4).texture(f7, f8).overlay(OverlayTexture.DEFAULT_UV).light(0xF000F0).normal(matrix3f, 0.0f, 1.0f, 0.0f).next();
    }

    @Override
    public boolean rendersOutsideBoundingBox(BeaconBlockEntity beaconBlockEntity) {
        return true;
    }
}

