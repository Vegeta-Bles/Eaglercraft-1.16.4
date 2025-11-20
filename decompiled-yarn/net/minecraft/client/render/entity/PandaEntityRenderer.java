package net.minecraft.client.render.entity;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.client.render.entity.feature.PandaHeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.PandaEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.passive.PandaEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;

public class PandaEntityRenderer extends MobEntityRenderer<PandaEntity, PandaEntityModel<PandaEntity>> {
   private static final Map<PandaEntity.Gene, Identifier> TEXTURES = Util.make(Maps.newEnumMap(PandaEntity.Gene.class), _snowman -> {
      _snowman.put(PandaEntity.Gene.NORMAL, new Identifier("textures/entity/panda/panda.png"));
      _snowman.put(PandaEntity.Gene.LAZY, new Identifier("textures/entity/panda/lazy_panda.png"));
      _snowman.put(PandaEntity.Gene.WORRIED, new Identifier("textures/entity/panda/worried_panda.png"));
      _snowman.put(PandaEntity.Gene.PLAYFUL, new Identifier("textures/entity/panda/playful_panda.png"));
      _snowman.put(PandaEntity.Gene.BROWN, new Identifier("textures/entity/panda/brown_panda.png"));
      _snowman.put(PandaEntity.Gene.WEAK, new Identifier("textures/entity/panda/weak_panda.png"));
      _snowman.put(PandaEntity.Gene.AGGRESSIVE, new Identifier("textures/entity/panda/aggressive_panda.png"));
   });

   public PandaEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman, new PandaEntityModel<>(9, 0.0F), 0.9F);
      this.addFeature(new PandaHeldItemFeatureRenderer(this));
   }

   public Identifier getTexture(PandaEntity _snowman) {
      return TEXTURES.getOrDefault(_snowman.getProductGene(), TEXTURES.get(PandaEntity.Gene.NORMAL));
   }

   protected void setupTransforms(PandaEntity _snowman, MatrixStack _snowman, float _snowman, float _snowman, float _snowman) {
      super.setupTransforms(_snowman, _snowman, _snowman, _snowman, _snowman);
      if (_snowman.playingTicks > 0) {
         int _snowmanxxxxx = _snowman.playingTicks;
         int _snowmanxxxxxx = _snowmanxxxxx + 1;
         float _snowmanxxxxxxx = 7.0F;
         float _snowmanxxxxxxxx = _snowman.isBaby() ? 0.3F : 0.8F;
         if (_snowmanxxxxx < 8) {
            float _snowmanxxxxxxxxx = (float)(90 * _snowmanxxxxx) / 7.0F;
            float _snowmanxxxxxxxxxx = (float)(90 * _snowmanxxxxxx) / 7.0F;
            float _snowmanxxxxxxxxxxx = this.method_4086(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxx, _snowman, 8.0F);
            _snowman.translate(0.0, (double)((_snowmanxxxxxxxx + 0.2F) * (_snowmanxxxxxxxxxxx / 90.0F)), 0.0);
            _snowman.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(-_snowmanxxxxxxxxxxx));
         } else if (_snowmanxxxxx < 16) {
            float _snowmanxxxxxxxxx = ((float)_snowmanxxxxx - 8.0F) / 7.0F;
            float _snowmanxxxxxxxxxx = 90.0F + 90.0F * _snowmanxxxxxxxxx;
            float _snowmanxxxxxxxxxxx = 90.0F + 90.0F * ((float)_snowmanxxxxxx - 8.0F) / 7.0F;
            float _snowmanxxxxxxxxxxxx = this.method_4086(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxx, _snowman, 16.0F);
            _snowman.translate(0.0, (double)(_snowmanxxxxxxxx + 0.2F + (_snowmanxxxxxxxx - 0.2F) * (_snowmanxxxxxxxxxxxx - 90.0F) / 90.0F), 0.0);
            _snowman.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(-_snowmanxxxxxxxxxxxx));
         } else if ((float)_snowmanxxxxx < 24.0F) {
            float _snowmanxxxxxxxxx = ((float)_snowmanxxxxx - 16.0F) / 7.0F;
            float _snowmanxxxxxxxxxx = 180.0F + 90.0F * _snowmanxxxxxxxxx;
            float _snowmanxxxxxxxxxxx = 180.0F + 90.0F * ((float)_snowmanxxxxxx - 16.0F) / 7.0F;
            float _snowmanxxxxxxxxxxxx = this.method_4086(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxx, _snowman, 24.0F);
            _snowman.translate(0.0, (double)(_snowmanxxxxxxxx + _snowmanxxxxxxxx * (270.0F - _snowmanxxxxxxxxxxxx) / 90.0F), 0.0);
            _snowman.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(-_snowmanxxxxxxxxxxxx));
         } else if (_snowmanxxxxx < 32) {
            float _snowmanxxxxxxxxx = ((float)_snowmanxxxxx - 24.0F) / 7.0F;
            float _snowmanxxxxxxxxxx = 270.0F + 90.0F * _snowmanxxxxxxxxx;
            float _snowmanxxxxxxxxxxx = 270.0F + 90.0F * ((float)_snowmanxxxxxx - 24.0F) / 7.0F;
            float _snowmanxxxxxxxxxxxx = this.method_4086(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxx, _snowman, 32.0F);
            _snowman.translate(0.0, (double)(_snowmanxxxxxxxx * ((360.0F - _snowmanxxxxxxxxxxxx) / 90.0F)), 0.0);
            _snowman.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(-_snowmanxxxxxxxxxxxx));
         }
      }

      float _snowmanxxxxx = _snowman.getScaredAnimationProgress(_snowman);
      if (_snowmanxxxxx > 0.0F) {
         _snowman.translate(0.0, (double)(0.8F * _snowmanxxxxx), 0.0);
         _snowman.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(MathHelper.lerp(_snowmanxxxxx, _snowman.pitch, _snowman.pitch + 90.0F)));
         _snowman.translate(0.0, (double)(-1.0F * _snowmanxxxxx), 0.0);
         if (_snowman.isScaredByThunderstorm()) {
            float _snowmanxxxxxx = (float)(Math.cos((double)_snowman.age * 1.25) * Math.PI * 0.05F);
            _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(_snowmanxxxxxx));
            if (_snowman.isBaby()) {
               _snowman.translate(0.0, 0.8F, 0.55F);
            }
         }
      }

      float _snowmanxxxxxx = _snowman.getLieOnBackAnimationProgress(_snowman);
      if (_snowmanxxxxxx > 0.0F) {
         float _snowmanxxxxxxx = _snowman.isBaby() ? 0.5F : 1.3F;
         _snowman.translate(0.0, (double)(_snowmanxxxxxxx * _snowmanxxxxxx), 0.0);
         _snowman.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(MathHelper.lerp(_snowmanxxxxxx, _snowman.pitch, _snowman.pitch + 180.0F)));
      }
   }

   private float method_4086(float _snowman, float _snowman, int _snowman, float _snowman, float _snowman) {
      return (float)_snowman < _snowman ? MathHelper.lerp(_snowman, _snowman, _snowman) : _snowman;
   }
}
