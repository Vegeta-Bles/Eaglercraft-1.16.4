/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity.feature;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.SheepEntityModel;
import net.minecraft.client.render.entity.model.SheepWoolEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

public class SheepWoolFeatureRenderer
extends FeatureRenderer<SheepEntity, SheepEntityModel<SheepEntity>> {
    private static final Identifier SKIN = new Identifier("textures/entity/sheep/sheep_fur.png");
    private final SheepWoolEntityModel<SheepEntity> model = new SheepWoolEntityModel();

    public SheepWoolFeatureRenderer(FeatureRendererContext<SheepEntity, SheepEntityModel<SheepEntity>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n, SheepEntity sheepEntity, float f, float f2, float f3, float f4, float f5, float f6) {
        float _snowman7;
        float _snowman6;
        float _snowman5;
        if (sheepEntity.isSheared() || sheepEntity.isInvisible()) {
            return;
        }
        if (sheepEntity.hasCustomName() && "jeb_".equals(sheepEntity.getName().asString())) {
            int n2 = 25;
            _snowman = sheepEntity.age / 25 + sheepEntity.getEntityId();
            _snowman = DyeColor.values().length;
            _snowman = _snowman % _snowman;
            _snowman = (_snowman + 1) % _snowman;
            float _snowman2 = ((float)(sheepEntity.age % 25) + f3) / 25.0f;
            float[] _snowman3 = SheepEntity.getRgbColor(DyeColor.byId(_snowman));
            float[] _snowman4 = SheepEntity.getRgbColor(DyeColor.byId(_snowman));
            _snowman5 = _snowman3[0] * (1.0f - _snowman2) + _snowman4[0] * _snowman2;
            _snowman6 = _snowman3[1] * (1.0f - _snowman2) + _snowman4[1] * _snowman2;
            _snowman7 = _snowman3[2] * (1.0f - _snowman2) + _snowman4[2] * _snowman2;
        } else {
            float[] _snowman8 = SheepEntity.getRgbColor(sheepEntity.getColor());
            _snowman5 = _snowman8[0];
            _snowman6 = _snowman8[1];
            _snowman7 = _snowman8[2];
        }
        SheepWoolFeatureRenderer.render(this.getContextModel(), this.model, SKIN, matrixStack, vertexConsumerProvider, n, sheepEntity, f, f2, f4, f5, f6, f3, _snowman5, _snowman6, _snowman7);
    }
}

