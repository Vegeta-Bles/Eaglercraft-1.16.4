package net.minecraft.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class HuskEntityRenderer extends ZombieEntityRenderer {
   private static final Identifier TEXTURE = new Identifier("textures/entity/zombie/husk.png");

   public HuskEntityRenderer(EntityRenderDispatcher arg) {
      super(arg);
   }

   protected void scale(ZombieEntity arg, MatrixStack arg2, float f) {
      float g = 1.0625F;
      arg2.scale(1.0625F, 1.0625F, 1.0625F);
      super.scale(arg, arg2, f);
   }

   @Override
   public Identifier getTexture(ZombieEntity arg) {
      return TEXTURE;
   }
}
