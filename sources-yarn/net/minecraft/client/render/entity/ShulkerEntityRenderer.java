package net.minecraft.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

@Environment(EnvType.CLIENT)
public class ShulkerEntityRenderer extends MobEntityRenderer<ShulkerEntity, ShulkerEntityModel<ShulkerEntity>> {
   public static final Identifier TEXTURE = new Identifier("textures/" + TexturedRenderLayers.SHULKER_TEXTURE_ID.getTextureId().getPath() + ".png");
   public static final Identifier[] COLORED_TEXTURES = TexturedRenderLayers.COLORED_SHULKER_BOXES_TEXTURES
      .stream()
      .map(arg -> new Identifier("textures/" + arg.getTextureId().getPath() + ".png"))
      .toArray(Identifier[]::new);

   public ShulkerEntityRenderer(EntityRenderDispatcher arg) {
      super(arg, new ShulkerEntityModel<>(), 0.0F);
      this.addFeature(new ShulkerHeadFeatureRenderer(this));
   }

   public Vec3d getPositionOffset(ShulkerEntity arg, float f) {
      int i = arg.getTeleportLerpTimer();
      if (i > 0 && arg.hasAttachedBlock()) {
         BlockPos lv = arg.getAttachedBlock();
         BlockPos lv2 = arg.getPrevAttachedBlock();
         double d = (double)((float)i - f) / 6.0;
         d *= d;
         double e = (double)(lv.getX() - lv2.getX()) * d;
         double g = (double)(lv.getY() - lv2.getY()) * d;
         double h = (double)(lv.getZ() - lv2.getZ()) * d;
         return new Vec3d(-e, -g, -h);
      } else {
         return super.getPositionOffset(arg, f);
      }
   }

   public boolean shouldRender(ShulkerEntity arg, Frustum arg2, double d, double e, double f) {
      if (super.shouldRender(arg, arg2, d, e, f)) {
         return true;
      } else {
         if (arg.getTeleportLerpTimer() > 0 && arg.hasAttachedBlock()) {
            Vec3d lv = Vec3d.of(arg.getAttachedBlock());
            Vec3d lv2 = Vec3d.of(arg.getPrevAttachedBlock());
            if (arg2.isVisible(new Box(lv2.x, lv2.y, lv2.z, lv.x, lv.y, lv.z))) {
               return true;
            }
         }

         return false;
      }
   }

   public Identifier getTexture(ShulkerEntity arg) {
      return arg.getColor() == null ? TEXTURE : COLORED_TEXTURES[arg.getColor().getId()];
   }

   protected void setupTransforms(ShulkerEntity arg, MatrixStack arg2, float f, float g, float h) {
      super.setupTransforms(arg, arg2, f, g + 180.0F, h);
      arg2.translate(0.0, 0.5, 0.0);
      arg2.multiply(arg.getAttachedFace().getOpposite().getRotationQuaternion());
      arg2.translate(0.0, -0.5, 0.0);
   }
}
