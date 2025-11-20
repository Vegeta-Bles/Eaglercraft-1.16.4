/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.LargePufferfishEntityModel;
import net.minecraft.client.render.entity.model.MediumPufferfishEntityModel;
import net.minecraft.client.render.entity.model.SmallPufferfishEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.PufferfishEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class PufferfishEntityRenderer
extends MobEntityRenderer<PufferfishEntity, EntityModel<PufferfishEntity>> {
    private static final Identifier TEXTURE = new Identifier("textures/entity/fish/pufferfish.png");
    private int modelSize = 3;
    private final SmallPufferfishEntityModel<PufferfishEntity> smallModel = new SmallPufferfishEntityModel();
    private final MediumPufferfishEntityModel<PufferfishEntity> mediumModel = new MediumPufferfishEntityModel();
    private final LargePufferfishEntityModel<PufferfishEntity> largeModel = new LargePufferfishEntityModel();

    public PufferfishEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new LargePufferfishEntityModel(), 0.2f);
    }

    @Override
    public Identifier getTexture(PufferfishEntity pufferfishEntity) {
        return TEXTURE;
    }

    @Override
    public void render(PufferfishEntity pufferfishEntity, float f, float f2, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n) {
        _snowman = pufferfishEntity.getPuffState();
        if (_snowman != this.modelSize) {
            this.model = _snowman == 0 ? this.smallModel : (_snowman == 1 ? this.mediumModel : this.largeModel);
        }
        this.modelSize = _snowman;
        this.shadowRadius = 0.1f + 0.1f * (float)_snowman;
        super.render(pufferfishEntity, f, f2, matrixStack, vertexConsumerProvider, n);
    }

    @Override
    protected void setupTransforms(PufferfishEntity pufferfishEntity, MatrixStack matrixStack, float f, float f2, float f3) {
        matrixStack.translate(0.0, MathHelper.cos(f * 0.05f) * 0.08f, 0.0);
        super.setupTransforms(pufferfishEntity, matrixStack, f, f2, f3);
    }
}

