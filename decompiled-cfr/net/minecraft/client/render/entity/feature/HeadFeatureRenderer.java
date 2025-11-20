/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  org.apache.commons.lang3.StringUtils
 */
package net.minecraft.client.render.entity.feature;

import com.mojang.authlib.GameProfile;
import net.minecraft.block.AbstractSkullBlock;
import net.minecraft.block.entity.SkullBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.SkullBlockEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import org.apache.commons.lang3.StringUtils;

public class HeadFeatureRenderer<T extends LivingEntity, M extends EntityModel<T>>
extends FeatureRenderer<T, M> {
    private final float field_24474;
    private final float field_24475;
    private final float field_24476;

    public HeadFeatureRenderer(FeatureRendererContext<T, M> featureRendererContext) {
        this(featureRendererContext, 1.0f, 1.0f, 1.0f);
    }

    public HeadFeatureRenderer(FeatureRendererContext<T, M> featureRendererContext, float f, float f2, float f3) {
        super(featureRendererContext);
        this.field_24474 = f;
        this.field_24475 = f2;
        this.field_24476 = f3;
    }

    @Override
    public void render(MatrixStack matrixStack2, VertexConsumerProvider vertexConsumerProvider, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
        MatrixStack matrixStack2;
        float f7;
        ItemStack itemStack = ((LivingEntity)t).getEquippedStack(EquipmentSlot.HEAD);
        if (itemStack.isEmpty()) {
            return;
        }
        Item _snowman2 = itemStack.getItem();
        matrixStack2.push();
        matrixStack2.scale(this.field_24474, this.field_24475, this.field_24476);
        boolean bl = _snowman = t instanceof VillagerEntity || t instanceof ZombieVillagerEntity;
        if (((LivingEntity)t).isBaby() && !(t instanceof VillagerEntity)) {
            f7 = 2.0f;
            _snowman = 1.4f;
            matrixStack2.translate(0.0, 0.03125, 0.0);
            matrixStack2.scale(0.7f, 0.7f, 0.7f);
            matrixStack2.translate(0.0, 1.0, 0.0);
        }
        ((ModelWithHead)this.getContextModel()).getHead().rotate(matrixStack2);
        if (_snowman2 instanceof BlockItem && ((BlockItem)_snowman2).getBlock() instanceof AbstractSkullBlock) {
            f7 = 1.1875f;
            matrixStack2.scale(1.1875f, -1.1875f, -1.1875f);
            if (_snowman) {
                matrixStack2.translate(0.0, 0.0625, 0.0);
            }
            GameProfile _snowman3 = null;
            if (itemStack.hasTag()) {
                CompoundTag compoundTag = itemStack.getTag();
                if (compoundTag.contains("SkullOwner", 10)) {
                    _snowman3 = NbtHelper.toGameProfile(compoundTag.getCompound("SkullOwner"));
                } else if (compoundTag.contains("SkullOwner", 8) && !StringUtils.isBlank((CharSequence)(_snowman = compoundTag.getString("SkullOwner")))) {
                    _snowman3 = SkullBlockEntity.loadProperties(new GameProfile(null, _snowman));
                    compoundTag.put("SkullOwner", NbtHelper.fromGameProfile(new CompoundTag(), _snowman3));
                }
            }
            matrixStack2.translate(-0.5, 0.0, -0.5);
            SkullBlockEntityRenderer.render(null, 180.0f, ((AbstractSkullBlock)((BlockItem)_snowman2).getBlock()).getSkullType(), _snowman3, f, matrixStack2, vertexConsumerProvider, n);
        } else if (!(_snowman2 instanceof ArmorItem) || ((ArmorItem)_snowman2).getSlotType() != EquipmentSlot.HEAD) {
            f7 = 0.625f;
            matrixStack2.translate(0.0, -0.25, 0.0);
            matrixStack2.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0f));
            matrixStack2.scale(0.625f, -0.625f, -0.625f);
            if (_snowman) {
                matrixStack2.translate(0.0, 0.1875, 0.0);
            }
            MinecraftClient.getInstance().getHeldItemRenderer().renderItem((LivingEntity)t, itemStack, ModelTransformation.Mode.HEAD, false, matrixStack2, vertexConsumerProvider, n);
        }
        matrixStack2.pop();
    }
}

