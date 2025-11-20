package net.minecraft.client.render.entity;

import net.minecraft.client.render.entity.model.VexEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.VexEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class VexEntityRenderer extends BipedEntityRenderer<VexEntity, VexEntityModel> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/illager/vex.png");
   private static final Identifier CHARGING_TEXTURE = new Identifier("textures/entity/illager/vex_charging.png");

   public VexEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman, new VexEntityModel(), 0.3F);
   }

   protected int getBlockLight(VexEntity _snowman, BlockPos _snowman) {
      return 15;
   }

   public Identifier getTexture(VexEntity _snowman) {
      return _snowman.isCharging() ? CHARGING_TEXTURE : TEXTURE;
   }

   protected void scale(VexEntity _snowman, MatrixStack _snowman, float _snowman) {
      _snowman.scale(0.4F, 0.4F, 0.4F);
   }
}
