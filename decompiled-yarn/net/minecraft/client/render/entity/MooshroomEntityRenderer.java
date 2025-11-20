package net.minecraft.client.render.entity;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.client.render.entity.feature.MooshroomMushroomFeatureRenderer;
import net.minecraft.client.render.entity.model.CowEntityModel;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

public class MooshroomEntityRenderer extends MobEntityRenderer<MooshroomEntity, CowEntityModel<MooshroomEntity>> {
   private static final Map<MooshroomEntity.Type, Identifier> TEXTURES = Util.make(Maps.newHashMap(), _snowman -> {
      _snowman.put(MooshroomEntity.Type.BROWN, new Identifier("textures/entity/cow/brown_mooshroom.png"));
      _snowman.put(MooshroomEntity.Type.RED, new Identifier("textures/entity/cow/red_mooshroom.png"));
   });

   public MooshroomEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman, new CowEntityModel<>(), 0.7F);
      this.addFeature(new MooshroomMushroomFeatureRenderer<>(this));
   }

   public Identifier getTexture(MooshroomEntity _snowman) {
      return TEXTURES.get(_snowman.getMooshroomType());
   }
}
