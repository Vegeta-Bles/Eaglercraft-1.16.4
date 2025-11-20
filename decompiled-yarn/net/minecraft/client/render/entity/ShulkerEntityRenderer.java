package net.minecraft.client.render.entity;

import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.entity.feature.ShulkerHeadFeatureRenderer;
import net.minecraft.client.render.entity.model.ShulkerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class ShulkerEntityRenderer extends MobEntityRenderer<ShulkerEntity, ShulkerEntityModel<ShulkerEntity>> {
   public static final Identifier TEXTURE = new Identifier("textures/" + TexturedRenderLayers.SHULKER_TEXTURE_ID.getTextureId().getPath() + ".png");
   public static final Identifier[] COLORED_TEXTURES = TexturedRenderLayers.COLORED_SHULKER_BOXES_TEXTURES
      .stream()
      .map(_snowman -> new Identifier("textures/" + _snowman.getTextureId().getPath() + ".png"))
      .toArray(Identifier[]::new);

   public ShulkerEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman, new ShulkerEntityModel<>(), 0.0F);
      this.addFeature(new ShulkerHeadFeatureRenderer(this));
   }

   public Vec3d getPositionOffset(ShulkerEntity _snowman, float _snowman) {
      int _snowmanxx = _snowman.getTeleportLerpTimer();
      if (_snowmanxx > 0 && _snowman.hasAttachedBlock()) {
         BlockPos _snowmanxxx = _snowman.getAttachedBlock();
         BlockPos _snowmanxxxx = _snowman.getPrevAttachedBlock();
         double _snowmanxxxxx = (double)((float)_snowmanxx - _snowman) / 6.0;
         _snowmanxxxxx *= _snowmanxxxxx;
         double _snowmanxxxxxx = (double)(_snowmanxxx.getX() - _snowmanxxxx.getX()) * _snowmanxxxxx;
         double _snowmanxxxxxxx = (double)(_snowmanxxx.getY() - _snowmanxxxx.getY()) * _snowmanxxxxx;
         double _snowmanxxxxxxxx = (double)(_snowmanxxx.getZ() - _snowmanxxxx.getZ()) * _snowmanxxxxx;
         return new Vec3d(-_snowmanxxxxxx, -_snowmanxxxxxxx, -_snowmanxxxxxxxx);
      } else {
         return super.getPositionOffset(_snowman, _snowman);
      }
   }

   public boolean shouldRender(ShulkerEntity _snowman, Frustum _snowman, double _snowman, double _snowman, double _snowman) {
      if (super.shouldRender(_snowman, _snowman, _snowman, _snowman, _snowman)) {
         return true;
      } else {
         if (_snowman.getTeleportLerpTimer() > 0 && _snowman.hasAttachedBlock()) {
            Vec3d _snowmanxxxxx = Vec3d.of(_snowman.getAttachedBlock());
            Vec3d _snowmanxxxxxx = Vec3d.of(_snowman.getPrevAttachedBlock());
            if (_snowman.isVisible(new Box(_snowmanxxxxxx.x, _snowmanxxxxxx.y, _snowmanxxxxxx.z, _snowmanxxxxx.x, _snowmanxxxxx.y, _snowmanxxxxx.z))) {
               return true;
            }
         }

         return false;
      }
   }

   public Identifier getTexture(ShulkerEntity _snowman) {
      return _snowman.getColor() == null ? TEXTURE : COLORED_TEXTURES[_snowman.getColor().getId()];
   }

   protected void setupTransforms(ShulkerEntity _snowman, MatrixStack _snowman, float _snowman, float _snowman, float _snowman) {
      super.setupTransforms(_snowman, _snowman, _snowman, _snowman + 180.0F, _snowman);
      _snowman.translate(0.0, 0.5, 0.0);
      _snowman.multiply(_snowman.getAttachedFace().getOpposite().getRotationQuaternion());
      _snowman.translate(0.0, -0.5, 0.0);
   }
}
