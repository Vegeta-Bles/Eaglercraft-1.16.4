/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 */
package net.minecraft.client.render.entity.feature;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.HorseEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.passive.HorseMarking;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

public class HorseMarkingFeatureRenderer
extends FeatureRenderer<HorseEntity, HorseEntityModel<HorseEntity>> {
    private static final Map<HorseMarking, Identifier> TEXTURES = Util.make(Maps.newEnumMap(HorseMarking.class), enumMap -> {
        enumMap.put(HorseMarking.NONE, null);
        enumMap.put(HorseMarking.WHITE, new Identifier("textures/entity/horse/horse_markings_white.png"));
        enumMap.put(HorseMarking.WHITE_FIELD, new Identifier("textures/entity/horse/horse_markings_whitefield.png"));
        enumMap.put(HorseMarking.WHITE_DOTS, new Identifier("textures/entity/horse/horse_markings_whitedots.png"));
        enumMap.put(HorseMarking.BLACK_DOTS, new Identifier("textures/entity/horse/horse_markings_blackdots.png"));
    });

    public HorseMarkingFeatureRenderer(FeatureRendererContext<HorseEntity, HorseEntityModel<HorseEntity>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n, HorseEntity horseEntity, float f, float f2, float f3, float f4, float f5, float f6) {
        Identifier identifier = TEXTURES.get((Object)horseEntity.getMarking());
        if (identifier == null || horseEntity.isInvisible()) {
            return;
        }
        VertexConsumer _snowman2 = vertexConsumerProvider.getBuffer(RenderLayer.getEntityTranslucent(identifier));
        ((HorseEntityModel)this.getContextModel()).render(matrixStack, _snowman2, n, LivingEntityRenderer.getOverlay(horseEntity, 0.0f), 1.0f, 1.0f, 1.0f, 1.0f);
    }
}

