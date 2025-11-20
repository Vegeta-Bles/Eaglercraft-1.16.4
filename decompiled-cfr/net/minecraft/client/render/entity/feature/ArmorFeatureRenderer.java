/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  javax.annotation.Nullable
 */
package net.minecraft.client.render.entity.feature;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ArmorFeatureRenderer<T extends LivingEntity, M extends BipedEntityModel<T>, A extends BipedEntityModel<T>>
extends FeatureRenderer<T, M> {
    private static final Map<String, Identifier> ARMOR_TEXTURE_CACHE = Maps.newHashMap();
    private final A leggingsModel;
    private final A bodyModel;

    public ArmorFeatureRenderer(FeatureRendererContext<T, M> context, A leggingsModel, A bodyModel) {
        super(context);
        this.leggingsModel = leggingsModel;
        this.bodyModel = bodyModel;
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
        this.renderArmor(matrixStack, vertexConsumerProvider, t, EquipmentSlot.CHEST, n, this.getArmor(EquipmentSlot.CHEST));
        this.renderArmor(matrixStack, vertexConsumerProvider, t, EquipmentSlot.LEGS, n, this.getArmor(EquipmentSlot.LEGS));
        this.renderArmor(matrixStack, vertexConsumerProvider, t, EquipmentSlot.FEET, n, this.getArmor(EquipmentSlot.FEET));
        this.renderArmor(matrixStack, vertexConsumerProvider, t, EquipmentSlot.HEAD, n, this.getArmor(EquipmentSlot.HEAD));
    }

    private void renderArmor(MatrixStack matrices, VertexConsumerProvider vertexConsumers, T t, EquipmentSlot equipmentSlot, int n, A a) {
        ItemStack itemStack = ((LivingEntity)t).getEquippedStack(equipmentSlot);
        if (!(itemStack.getItem() instanceof ArmorItem)) {
            return;
        }
        ArmorItem _snowman2 = (ArmorItem)itemStack.getItem();
        if (_snowman2.getSlotType() != equipmentSlot) {
            return;
        }
        ((BipedEntityModel)this.getContextModel()).setAttributes(a);
        this.setVisible(a, equipmentSlot);
        boolean _snowman3 = this.usesSecondLayer(equipmentSlot);
        boolean _snowman4 = itemStack.hasGlint();
        if (_snowman2 instanceof DyeableArmorItem) {
            int n2 = ((DyeableArmorItem)_snowman2).getColor(itemStack);
            float _snowman5 = (float)(n2 >> 16 & 0xFF) / 255.0f;
            float _snowman6 = (float)(n2 >> 8 & 0xFF) / 255.0f;
            float _snowman7 = (float)(n2 & 0xFF) / 255.0f;
            this.renderArmorParts(matrices, vertexConsumers, n, _snowman2, _snowman4, a, _snowman3, _snowman5, _snowman6, _snowman7, null);
            this.renderArmorParts(matrices, vertexConsumers, n, _snowman2, _snowman4, a, _snowman3, 1.0f, 1.0f, 1.0f, "overlay");
        } else {
            this.renderArmorParts(matrices, vertexConsumers, n, _snowman2, _snowman4, a, _snowman3, 1.0f, 1.0f, 1.0f, null);
        }
    }

    protected void setVisible(A bipedModel, EquipmentSlot slot) {
        ((BipedEntityModel)bipedModel).setVisible(false);
        switch (slot) {
            case HEAD: {
                ((BipedEntityModel)bipedModel).head.visible = true;
                ((BipedEntityModel)bipedModel).helmet.visible = true;
                break;
            }
            case CHEST: {
                ((BipedEntityModel)bipedModel).torso.visible = true;
                ((BipedEntityModel)bipedModel).rightArm.visible = true;
                ((BipedEntityModel)bipedModel).leftArm.visible = true;
                break;
            }
            case LEGS: {
                ((BipedEntityModel)bipedModel).torso.visible = true;
                ((BipedEntityModel)bipedModel).rightLeg.visible = true;
                ((BipedEntityModel)bipedModel).leftLeg.visible = true;
                break;
            }
            case FEET: {
                ((BipedEntityModel)bipedModel).rightLeg.visible = true;
                ((BipedEntityModel)bipedModel).leftLeg.visible = true;
            }
        }
    }

    private void renderArmorParts(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n, ArmorItem armorItem, boolean bl, A a, boolean bl2, float f, float f2, float f3, @Nullable String string) {
        VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumerProvider, RenderLayer.getArmorCutoutNoCull(this.getArmorTexture(armorItem, bl2, string)), false, bl);
        ((AnimalModel)a).render(matrixStack, vertexConsumer, n, OverlayTexture.DEFAULT_UV, f, f2, f3, 1.0f);
    }

    private A getArmor(EquipmentSlot slot) {
        return this.usesSecondLayer(slot) ? this.leggingsModel : this.bodyModel;
    }

    private boolean usesSecondLayer(EquipmentSlot slot) {
        return slot == EquipmentSlot.LEGS;
    }

    private Identifier getArmorTexture(ArmorItem armorItem, boolean bl, @Nullable String string) {
        _snowman = "textures/models/armor/" + armorItem.getMaterial().getName() + "_layer_" + (bl ? 2 : 1) + (string == null ? "" : "_" + string) + ".png";
        return ARMOR_TEXTURE_CACHE.computeIfAbsent(_snowman, Identifier::new);
    }
}

