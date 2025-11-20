/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity.feature;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;

public class HeldItemFeatureRenderer<T extends LivingEntity, M extends EntityModel<T>>
extends FeatureRenderer<T, M> {
    public HeldItemFeatureRenderer(FeatureRendererContext<T, M> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n, T t2, float f, float f2, float f3, float f4, float f5, float f6) {
        T t2;
        boolean bl = ((LivingEntity)t2).getMainArm() == Arm.RIGHT;
        ItemStack _snowman2 = bl ? ((LivingEntity)t2).getOffHandStack() : ((LivingEntity)t2).getMainHandStack();
        ItemStack itemStack = _snowman = bl ? ((LivingEntity)t2).getMainHandStack() : ((LivingEntity)t2).getOffHandStack();
        if (_snowman2.isEmpty() && _snowman.isEmpty()) {
            return;
        }
        matrixStack.push();
        if (((EntityModel)this.getContextModel()).child) {
            float f7 = 0.5f;
            matrixStack.translate(0.0, 0.75, 0.0);
            matrixStack.scale(0.5f, 0.5f, 0.5f);
        }
        this.renderItem((LivingEntity)t2, _snowman, ModelTransformation.Mode.THIRD_PERSON_RIGHT_HAND, Arm.RIGHT, matrixStack, vertexConsumerProvider, n);
        this.renderItem((LivingEntity)t2, _snowman2, ModelTransformation.Mode.THIRD_PERSON_LEFT_HAND, Arm.LEFT, matrixStack, vertexConsumerProvider, n);
        matrixStack.pop();
    }

    private void renderItem(LivingEntity entity, ItemStack stack, ModelTransformation.Mode transformationMode, Arm arm, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        if (stack.isEmpty()) {
            return;
        }
        matrices.push();
        ((ModelWithArms)this.getContextModel()).setArmAngle(arm, matrices);
        matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(-90.0f));
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0f));
        boolean bl = arm == Arm.LEFT;
        matrices.translate((float)(bl ? -1 : 1) / 16.0f, 0.125, -0.625);
        MinecraftClient.getInstance().getHeldItemRenderer().renderItem(entity, stack, transformationMode, bl, matrices, vertexConsumers, light);
        matrices.pop();
    }
}

