/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity.feature;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class TridentRiptideFeatureRenderer<T extends LivingEntity>
extends FeatureRenderer<T, PlayerEntityModel<T>> {
    public static final Identifier TEXTURE = new Identifier("textures/entity/trident_riptide.png");
    private final ModelPart aura = new ModelPart(64, 64, 0, 0);

    public TridentRiptideFeatureRenderer(FeatureRendererContext<T, PlayerEntityModel<T>> featureRendererContext) {
        super(featureRendererContext);
        this.aura.addCuboid(-8.0f, -16.0f, -8.0f, 16.0f, 32.0f, 16.0f);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
        if (!((LivingEntity)t).isUsingRiptide()) {
            return;
        }
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityCutoutNoCull(TEXTURE));
        for (int i = 0; i < 3; ++i) {
            matrixStack.push();
            float f7 = f4 * (float)(-(45 + i * 5));
            matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(f7));
            _snowman = 0.75f * (float)i;
            matrixStack.scale(_snowman, _snowman, _snowman);
            matrixStack.translate(0.0, -0.2f + 0.6f * (float)i, 0.0);
            this.aura.render(matrixStack, vertexConsumer, n, OverlayTexture.DEFAULT_UV);
            matrixStack.pop();
        }
    }
}

