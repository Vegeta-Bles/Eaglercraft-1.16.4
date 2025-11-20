package net.minecraft.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.MagmaCubeEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.MagmaCubeEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class MagmaCubeEntityRenderer extends MobEntityRenderer<MagmaCubeEntity, MagmaCubeEntityModel<MagmaCubeEntity>> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/slime/magmacube.png");

   public MagmaCubeEntityRenderer(EntityRenderDispatcher arg) {
      super(arg, new MagmaCubeEntityModel<>(), 0.25F);
   }

   protected int getBlockLight(MagmaCubeEntity arg, BlockPos arg2) {
      return 15;
   }

   public Identifier getTexture(MagmaCubeEntity arg) {
      return TEXTURE;
   }

   protected void scale(MagmaCubeEntity arg, MatrixStack arg2, float f) {
      int i = arg.getSize();
      float g = MathHelper.lerp(f, arg.lastStretch, arg.stretch) / ((float)i * 0.5F + 1.0F);
      float h = 1.0F / (g + 1.0F);
      arg2.scale(h * (float)i, 1.0F / h * (float)i, h * (float)i);
   }
}
