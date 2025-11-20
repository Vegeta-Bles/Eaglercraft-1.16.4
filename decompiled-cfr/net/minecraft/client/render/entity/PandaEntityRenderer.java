/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 */
package net.minecraft.client.render.entity;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.PandaHeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.PandaEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.passive.PandaEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;

public class PandaEntityRenderer
extends MobEntityRenderer<PandaEntity, PandaEntityModel<PandaEntity>> {
    private static final Map<PandaEntity.Gene, Identifier> TEXTURES = Util.make(Maps.newEnumMap(PandaEntity.Gene.class), enumMap -> {
        enumMap.put(PandaEntity.Gene.NORMAL, new Identifier("textures/entity/panda/panda.png"));
        enumMap.put(PandaEntity.Gene.LAZY, new Identifier("textures/entity/panda/lazy_panda.png"));
        enumMap.put(PandaEntity.Gene.WORRIED, new Identifier("textures/entity/panda/worried_panda.png"));
        enumMap.put(PandaEntity.Gene.PLAYFUL, new Identifier("textures/entity/panda/playful_panda.png"));
        enumMap.put(PandaEntity.Gene.BROWN, new Identifier("textures/entity/panda/brown_panda.png"));
        enumMap.put(PandaEntity.Gene.WEAK, new Identifier("textures/entity/panda/weak_panda.png"));
        enumMap.put(PandaEntity.Gene.AGGRESSIVE, new Identifier("textures/entity/panda/aggressive_panda.png"));
    });

    public PandaEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new PandaEntityModel(9, 0.0f), 0.9f);
        this.addFeature(new PandaHeldItemFeatureRenderer(this));
    }

    @Override
    public Identifier getTexture(PandaEntity pandaEntity) {
        return TEXTURES.getOrDefault((Object)pandaEntity.getProductGene(), TEXTURES.get((Object)PandaEntity.Gene.NORMAL));
    }

    @Override
    protected void setupTransforms(PandaEntity pandaEntity, MatrixStack matrixStack, float f, float f2, float f3) {
        float f4;
        float _snowman2;
        super.setupTransforms(pandaEntity, matrixStack, f, f2, f3);
        if (pandaEntity.playingTicks > 0) {
            int n = pandaEntity.playingTicks;
            _snowman = n + 1;
            _snowman2 = 7.0f;
            float f5 = _snowman = pandaEntity.isBaby() ? 0.3f : 0.8f;
            if (n < 8) {
                float f6 = (float)(90 * n) / 7.0f;
                _snowman = (float)(90 * _snowman) / 7.0f;
                _snowman = this.method_4086(f6, _snowman, _snowman, f3, 8.0f);
                matrixStack.translate(0.0, (_snowman + 0.2f) * (_snowman / 90.0f), 0.0);
                matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(-_snowman));
            } else if (n < 16) {
                float f7 = ((float)n - 8.0f) / 7.0f;
                _snowman = 90.0f + 90.0f * f7;
                _snowman = 90.0f + 90.0f * ((float)_snowman - 8.0f) / 7.0f;
                _snowman = this.method_4086(_snowman, _snowman, _snowman, f3, 16.0f);
                matrixStack.translate(0.0, _snowman + 0.2f + (_snowman - 0.2f) * (_snowman - 90.0f) / 90.0f, 0.0);
                matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(-_snowman));
            } else if ((float)n < 24.0f) {
                float f8 = ((float)n - 16.0f) / 7.0f;
                _snowman = 180.0f + 90.0f * f8;
                _snowman = 180.0f + 90.0f * ((float)_snowman - 16.0f) / 7.0f;
                _snowman = this.method_4086(_snowman, _snowman, _snowman, f3, 24.0f);
                matrixStack.translate(0.0, _snowman + _snowman * (270.0f - _snowman) / 90.0f, 0.0);
                matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(-_snowman));
            } else if (n < 32) {
                float f9 = ((float)n - 24.0f) / 7.0f;
                _snowman = 270.0f + 90.0f * f9;
                _snowman = 270.0f + 90.0f * ((float)_snowman - 24.0f) / 7.0f;
                _snowman = this.method_4086(_snowman, _snowman, _snowman, f3, 32.0f);
                matrixStack.translate(0.0, _snowman * ((360.0f - _snowman) / 90.0f), 0.0);
                matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(-_snowman));
            }
        }
        if ((f4 = pandaEntity.getScaredAnimationProgress(f3)) > 0.0f) {
            matrixStack.translate(0.0, 0.8f * f4, 0.0);
            matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(MathHelper.lerp(f4, pandaEntity.pitch, pandaEntity.pitch + 90.0f)));
            matrixStack.translate(0.0, -1.0f * f4, 0.0);
            if (pandaEntity.isScaredByThunderstorm()) {
                _snowman = (float)(Math.cos((double)pandaEntity.age * 1.25) * Math.PI * (double)0.05f);
                matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(_snowman));
                if (pandaEntity.isBaby()) {
                    matrixStack.translate(0.0, 0.8f, 0.55f);
                }
            }
        }
        if ((_snowman = pandaEntity.getLieOnBackAnimationProgress(f3)) > 0.0f) {
            _snowman2 = pandaEntity.isBaby() ? 0.5f : 1.3f;
            matrixStack.translate(0.0, _snowman2 * _snowman, 0.0);
            matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(MathHelper.lerp(_snowman, pandaEntity.pitch, pandaEntity.pitch + 180.0f)));
        }
    }

    private float method_4086(float f, float f2, int n, float f3, float f4) {
        if ((float)n < f4) {
            return MathHelper.lerp(f3, f, f2);
        }
        return f;
    }
}

