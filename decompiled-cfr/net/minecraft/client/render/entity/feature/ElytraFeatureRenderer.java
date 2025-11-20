/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity.feature;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.ElytraEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class ElytraFeatureRenderer<T extends LivingEntity, M extends EntityModel<T>>
extends FeatureRenderer<T, M> {
    private static final Identifier SKIN = new Identifier("textures/entity/elytra.png");
    private final ElytraEntityModel<T> elytra = new ElytraEntityModel();

    public ElytraFeatureRenderer(FeatureRendererContext<T, M> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
        ItemStack itemStack = ((LivingEntity)t).getEquippedStack(EquipmentSlot.CHEST);
        if (itemStack.getItem() != Items.ELYTRA) {
            return;
        }
        Identifier _snowman2 = t instanceof AbstractClientPlayerEntity ? (((AbstractClientPlayerEntity)(_snowman3 = (AbstractClientPlayerEntity)t)).canRenderElytraTexture() && ((AbstractClientPlayerEntity)_snowman3).getElytraTexture() != null ? ((AbstractClientPlayerEntity)_snowman3).getElytraTexture() : (((AbstractClientPlayerEntity)_snowman3).canRenderCapeTexture() && ((AbstractClientPlayerEntity)_snowman3).getCapeTexture() != null && ((PlayerEntity)_snowman3).isPartVisible(PlayerModelPart.CAPE) ? ((AbstractClientPlayerEntity)_snowman3).getCapeTexture() : SKIN)) : SKIN;
        matrixStack.push();
        matrixStack.translate(0.0, 0.0, 0.125);
        ((EntityModel)this.getContextModel()).copyStateTo(this.elytra);
        this.elytra.setAngles(t, f, f2, f4, f5, f6);
        Object _snowman3 = ItemRenderer.getArmorGlintConsumer(vertexConsumerProvider, RenderLayer.getArmorCutoutNoCull(_snowman2), false, itemStack.hasGlint());
        this.elytra.render(matrixStack, (VertexConsumer)_snowman3, n, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
        matrixStack.pop();
    }
}

