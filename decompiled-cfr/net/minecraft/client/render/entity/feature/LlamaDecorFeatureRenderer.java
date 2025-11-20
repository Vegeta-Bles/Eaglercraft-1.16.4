/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity.feature;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.LlamaEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.LlamaEntity;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

public class LlamaDecorFeatureRenderer
extends FeatureRenderer<LlamaEntity, LlamaEntityModel<LlamaEntity>> {
    private static final Identifier[] LLAMA_DECOR = new Identifier[]{new Identifier("textures/entity/llama/decor/white.png"), new Identifier("textures/entity/llama/decor/orange.png"), new Identifier("textures/entity/llama/decor/magenta.png"), new Identifier("textures/entity/llama/decor/light_blue.png"), new Identifier("textures/entity/llama/decor/yellow.png"), new Identifier("textures/entity/llama/decor/lime.png"), new Identifier("textures/entity/llama/decor/pink.png"), new Identifier("textures/entity/llama/decor/gray.png"), new Identifier("textures/entity/llama/decor/light_gray.png"), new Identifier("textures/entity/llama/decor/cyan.png"), new Identifier("textures/entity/llama/decor/purple.png"), new Identifier("textures/entity/llama/decor/blue.png"), new Identifier("textures/entity/llama/decor/brown.png"), new Identifier("textures/entity/llama/decor/green.png"), new Identifier("textures/entity/llama/decor/red.png"), new Identifier("textures/entity/llama/decor/black.png")};
    private static final Identifier TRADER_LLAMA_DECOR = new Identifier("textures/entity/llama/decor/trader_llama.png");
    private final LlamaEntityModel<LlamaEntity> model = new LlamaEntityModel(0.5f);

    public LlamaDecorFeatureRenderer(FeatureRendererContext<LlamaEntity, LlamaEntityModel<LlamaEntity>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n, LlamaEntity llamaEntity2, float f, float f2, float f3, float f4, float f5, float f6) {
        LlamaEntity llamaEntity2;
        Identifier identifier;
        DyeColor dyeColor = llamaEntity2.getCarpetColor();
        if (dyeColor != null) {
            identifier = LLAMA_DECOR[dyeColor.getId()];
        } else if (llamaEntity2.isTrader()) {
            identifier = TRADER_LLAMA_DECOR;
        } else {
            return;
        }
        ((LlamaEntityModel)this.getContextModel()).copyStateTo(this.model);
        this.model.setAngles(llamaEntity2, f, f2, f4, f5, f6);
        VertexConsumer _snowman2 = vertexConsumerProvider.getBuffer(RenderLayer.getEntityCutoutNoCull(identifier));
        this.model.render(matrixStack, _snowman2, n, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
    }
}

