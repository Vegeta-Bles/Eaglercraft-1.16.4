package net.minecraft.client.render.entity;

import net.minecraft.client.render.entity.model.MagmaCubeEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.MagmaCubeEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class MagmaCubeEntityRenderer extends MobEntityRenderer<MagmaCubeEntity, MagmaCubeEntityModel<MagmaCubeEntity>> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/slime/magmacube.png");

   public MagmaCubeEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman, new MagmaCubeEntityModel<>(), 0.25F);
   }

   protected int getBlockLight(MagmaCubeEntity _snowman, BlockPos _snowman) {
      return 15;
   }

   public Identifier getTexture(MagmaCubeEntity _snowman) {
      return TEXTURE;
   }

   protected void scale(MagmaCubeEntity _snowman, MatrixStack _snowman, float _snowman) {
      int _snowmanxxx = _snowman.getSize();
      float _snowmanxxxx = MathHelper.lerp(_snowman, _snowman.lastStretch, _snowman.stretch) / ((float)_snowmanxxx * 0.5F + 1.0F);
      float _snowmanxxxxx = 1.0F / (_snowmanxxxx + 1.0F);
      _snowman.scale(_snowmanxxxxx * (float)_snowmanxxx, 1.0F / _snowmanxxxxx * (float)_snowmanxxx, _snowmanxxxxx * (float)_snowmanxxx);
   }
}
