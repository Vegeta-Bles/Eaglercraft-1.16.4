/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.IllagerEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.IllusionerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class IllusionerEntityRenderer
extends IllagerEntityRenderer<IllusionerEntity> {
    private static final Identifier TEXTURE = new Identifier("textures/entity/illager/illusioner.png");

    public IllusionerEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new IllagerEntityModel(0.0f, 0.0f, 64, 64), 0.5f);
        this.addFeature(new HeldItemFeatureRenderer<IllusionerEntity, IllagerEntityModel<IllusionerEntity>>((FeatureRendererContext)this){

            @Override
            public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n, IllusionerEntity illusionerEntity, float f, float f2, float f3, float f4, float f5, float f6) {
                if (illusionerEntity.isSpellcasting() || illusionerEntity.isAttacking()) {
                    super.render(matrixStack, vertexConsumerProvider, n, illusionerEntity, f, f2, f3, f4, f5, f6);
                }
            }
        });
        ((IllagerEntityModel)this.model).getHat().visible = true;
    }

    @Override
    public Identifier getTexture(IllusionerEntity illusionerEntity) {
        return TEXTURE;
    }

    @Override
    public void render(IllusionerEntity illusionerEntity2, float f, float f2, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n) {
        if (illusionerEntity2.isInvisible()) {
            Vec3d[] vec3dArray = illusionerEntity2.method_7065(f2);
            float _snowman2 = this.getAnimationProgress(illusionerEntity2, f2);
            for (int i = 0; i < vec3dArray.length; ++i) {
                matrixStack.push();
                matrixStack.translate(vec3dArray[i].x + (double)MathHelper.cos((float)i + _snowman2 * 0.5f) * 0.025, vec3dArray[i].y + (double)MathHelper.cos((float)i + _snowman2 * 0.75f) * 0.0125, vec3dArray[i].z + (double)MathHelper.cos((float)i + _snowman2 * 0.7f) * 0.025);
                super.render(illusionerEntity2, f, f2, matrixStack, vertexConsumerProvider, n);
                matrixStack.pop();
            }
        } else {
            IllusionerEntity illusionerEntity2;
            super.render(illusionerEntity2, f, f2, matrixStack, vertexConsumerProvider, n);
        }
    }

    @Override
    protected boolean isVisible(IllusionerEntity illusionerEntity) {
        return true;
    }

    @Override
    protected /* synthetic */ boolean isVisible(LivingEntity entity) {
        return this.isVisible((IllusionerEntity)entity);
    }
}

