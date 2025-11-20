package net.minecraft.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.CaveSpiderEntity;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class CaveSpiderEntityRenderer extends SpiderEntityRenderer<CaveSpiderEntity> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/spider/cave_spider.png");

   public CaveSpiderEntityRenderer(EntityRenderDispatcher arg) {
      super(arg);
      this.shadowRadius *= 0.7F;
   }

   protected void scale(CaveSpiderEntity arg, MatrixStack arg2, float f) {
      arg2.scale(0.7F, 0.7F, 0.7F);
   }

   public Identifier getTexture(CaveSpiderEntity arg) {
      return TEXTURE;
   }
}
