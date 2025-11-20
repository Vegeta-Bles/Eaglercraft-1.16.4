/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 */
package net.minecraft.client.render.entity.feature;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.IronGolemEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.util.Identifier;

public class IronGolemCrackFeatureRenderer
extends FeatureRenderer<IronGolemEntity, IronGolemEntityModel<IronGolemEntity>> {
    private static final Map<IronGolemEntity.Crack, Identifier> DAMAGE_TO_TEXTURE = ImmutableMap.of((Object)((Object)IronGolemEntity.Crack.LOW), (Object)new Identifier("textures/entity/iron_golem/iron_golem_crackiness_low.png"), (Object)((Object)IronGolemEntity.Crack.MEDIUM), (Object)new Identifier("textures/entity/iron_golem/iron_golem_crackiness_medium.png"), (Object)((Object)IronGolemEntity.Crack.HIGH), (Object)new Identifier("textures/entity/iron_golem/iron_golem_crackiness_high.png"));

    public IronGolemCrackFeatureRenderer(FeatureRendererContext<IronGolemEntity, IronGolemEntityModel<IronGolemEntity>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n, IronGolemEntity ironGolemEntity, float f, float f2, float f3, float f4, float f5, float f6) {
        if (ironGolemEntity.isInvisible()) {
            return;
        }
        IronGolemEntity.Crack crack = ironGolemEntity.getCrack();
        if (crack == IronGolemEntity.Crack.NONE) {
            return;
        }
        Identifier _snowman2 = DAMAGE_TO_TEXTURE.get((Object)crack);
        IronGolemCrackFeatureRenderer.renderModel(this.getContextModel(), _snowman2, matrixStack, vertexConsumerProvider, n, ironGolemEntity, 1.0f, 1.0f, 1.0f);
    }
}

