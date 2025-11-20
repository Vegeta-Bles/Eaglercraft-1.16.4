package net.minecraft.client.render.entity;

import net.minecraft.client.render.entity.model.BlazeEntityModel;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class BlazeEntityRenderer extends MobEntityRenderer<BlazeEntity, BlazeEntityModel<BlazeEntity>> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/blaze.png");

   public BlazeEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman, new BlazeEntityModel<>(), 0.5F);
   }

   protected int getBlockLight(BlazeEntity _snowman, BlockPos _snowman) {
      return 15;
   }

   public Identifier getTexture(BlazeEntity _snowman) {
      return TEXTURE;
   }
}
