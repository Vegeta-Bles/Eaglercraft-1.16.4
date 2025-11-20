package net.minecraft.client.render.entity;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.client.render.entity.feature.HorseArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.HorseMarkingFeatureRenderer;
import net.minecraft.client.render.entity.model.HorseEntityModel;
import net.minecraft.entity.passive.HorseColor;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

public final class HorseEntityRenderer extends HorseBaseEntityRenderer<HorseEntity, HorseEntityModel<HorseEntity>> {
   private static final Map<HorseColor, Identifier> TEXTURES = Util.make(Maps.newEnumMap(HorseColor.class), _snowman -> {
      _snowman.put(HorseColor.WHITE, new Identifier("textures/entity/horse/horse_white.png"));
      _snowman.put(HorseColor.CREAMY, new Identifier("textures/entity/horse/horse_creamy.png"));
      _snowman.put(HorseColor.CHESTNUT, new Identifier("textures/entity/horse/horse_chestnut.png"));
      _snowman.put(HorseColor.BROWN, new Identifier("textures/entity/horse/horse_brown.png"));
      _snowman.put(HorseColor.BLACK, new Identifier("textures/entity/horse/horse_black.png"));
      _snowman.put(HorseColor.GRAY, new Identifier("textures/entity/horse/horse_gray.png"));
      _snowman.put(HorseColor.DARKBROWN, new Identifier("textures/entity/horse/horse_darkbrown.png"));
   });

   public HorseEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman, new HorseEntityModel<>(0.0F), 1.1F);
      this.addFeature(new HorseMarkingFeatureRenderer(this));
      this.addFeature(new HorseArmorFeatureRenderer(this));
   }

   public Identifier getTexture(HorseEntity _snowman) {
      return TEXTURES.get(_snowman.getColor());
   }
}
