package net.minecraft.client.render.entity;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.CaveSpiderEntity;
import net.minecraft.util.Identifier;

public class CaveSpiderEntityRenderer extends SpiderEntityRenderer<CaveSpiderEntity> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/spider/cave_spider.png");

   public CaveSpiderEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman);
      this.shadowRadius *= 0.7F;
   }

   protected void scale(CaveSpiderEntity _snowman, MatrixStack _snowman, float _snowman) {
      _snowman.scale(0.7F, 0.7F, 0.7F);
   }

   public Identifier getTexture(CaveSpiderEntity _snowman) {
      return TEXTURE;
   }
}
