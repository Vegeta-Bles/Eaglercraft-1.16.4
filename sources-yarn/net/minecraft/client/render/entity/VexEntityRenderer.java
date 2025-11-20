package net.minecraft.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.VexEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.VexEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

@Environment(EnvType.CLIENT)
public class VexEntityRenderer extends BipedEntityRenderer<VexEntity, VexEntityModel> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/illager/vex.png");
   private static final Identifier CHARGING_TEXTURE = new Identifier("textures/entity/illager/vex_charging.png");

   public VexEntityRenderer(EntityRenderDispatcher arg) {
      super(arg, new VexEntityModel(), 0.3F);
   }

   protected int getBlockLight(VexEntity arg, BlockPos arg2) {
      return 15;
   }

   public Identifier getTexture(VexEntity arg) {
      return arg.isCharging() ? CHARGING_TEXTURE : TEXTURE;
   }

   protected void scale(VexEntity arg, MatrixStack arg2, float f) {
      arg2.scale(0.4F, 0.4F, 0.4F);
   }
}
