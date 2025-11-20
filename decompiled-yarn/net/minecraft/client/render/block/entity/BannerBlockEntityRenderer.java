package net.minecraft.client.render.block.entity;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import net.minecraft.block.BannerBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallBannerBlock;
import net.minecraft.block.entity.BannerBlockEntity;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class BannerBlockEntityRenderer extends BlockEntityRenderer<BannerBlockEntity> {
   private final ModelPart banner = createBanner();
   private final ModelPart pillar = new ModelPart(64, 64, 44, 0);
   private final ModelPart crossbar;

   public BannerBlockEntityRenderer(BlockEntityRenderDispatcher _snowman) {
      super(_snowman);
      this.pillar.addCuboid(-1.0F, -30.0F, -1.0F, 2.0F, 42.0F, 2.0F, 0.0F);
      this.crossbar = new ModelPart(64, 64, 0, 42);
      this.crossbar.addCuboid(-10.0F, -32.0F, -1.0F, 20.0F, 2.0F, 2.0F, 0.0F);
   }

   public static ModelPart createBanner() {
      ModelPart _snowman = new ModelPart(64, 64, 0, 0);
      _snowman.addCuboid(-10.0F, 0.0F, -2.0F, 20.0F, 40.0F, 1.0F, 0.0F);
      return _snowman;
   }

   public void render(BannerBlockEntity _snowman, float _snowman, MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman, int _snowman) {
      List<Pair<BannerPattern, DyeColor>> _snowmanxxxxxx = _snowman.getPatterns();
      if (_snowmanxxxxxx != null) {
         float _snowmanxxxxxxx = 0.6666667F;
         boolean _snowmanxxxxxxxx = _snowman.getWorld() == null;
         _snowman.push();
         long _snowmanxxxxxxxxx;
         if (_snowmanxxxxxxxx) {
            _snowmanxxxxxxxxx = 0L;
            _snowman.translate(0.5, 0.5, 0.5);
            this.pillar.visible = true;
         } else {
            _snowmanxxxxxxxxx = _snowman.getWorld().getTime();
            BlockState _snowmanxxxxxxxxxx = _snowman.getCachedState();
            if (_snowmanxxxxxxxxxx.getBlock() instanceof BannerBlock) {
               _snowman.translate(0.5, 0.5, 0.5);
               float _snowmanxxxxxxxxxxx = (float)(-_snowmanxxxxxxxxxx.get(BannerBlock.ROTATION) * 360) / 16.0F;
               _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(_snowmanxxxxxxxxxxx));
               this.pillar.visible = true;
            } else {
               _snowman.translate(0.5, -0.16666667F, 0.5);
               float _snowmanxxxxxxxxxxx = -_snowmanxxxxxxxxxx.get(WallBannerBlock.FACING).asRotation();
               _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(_snowmanxxxxxxxxxxx));
               _snowman.translate(0.0, -0.3125, -0.4375);
               this.pillar.visible = false;
            }
         }

         _snowman.push();
         _snowman.scale(0.6666667F, -0.6666667F, -0.6666667F);
         VertexConsumer _snowmanxxxxxxxxxxx = ModelLoader.BANNER_BASE.getVertexConsumer(_snowman, RenderLayer::getEntitySolid);
         this.pillar.render(_snowman, _snowmanxxxxxxxxxxx, _snowman, _snowman);
         this.crossbar.render(_snowman, _snowmanxxxxxxxxxxx, _snowman, _snowman);
         BlockPos _snowmanxxxxxxxxxxxx = _snowman.getPos();
         float _snowmanxxxxxxxxxxxxx = (
               (float)Math.floorMod((long)(_snowmanxxxxxxxxxxxx.getX() * 7 + _snowmanxxxxxxxxxxxx.getY() * 9 + _snowmanxxxxxxxxxxxx.getZ() * 13) + _snowmanxxxxxxxxx, 100L) + _snowman
            )
            / 100.0F;
         this.banner.pitch = (-0.0125F + 0.01F * MathHelper.cos((float) (Math.PI * 2) * _snowmanxxxxxxxxxxxxx)) * (float) Math.PI;
         this.banner.pivotY = -32.0F;
         method_29999(_snowman, _snowman, _snowman, _snowman, this.banner, ModelLoader.BANNER_BASE, true, _snowmanxxxxxx);
         _snowman.pop();
         _snowman.pop();
      }
   }

   public static void method_29999(
      MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman, int _snowman, ModelPart _snowman, SpriteIdentifier _snowman, boolean _snowman, List<Pair<BannerPattern, DyeColor>> _snowman
   ) {
      renderCanvas(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, false);
   }

   public static void renderCanvas(
      MatrixStack matrices,
      VertexConsumerProvider vertexConsumers,
      int light,
      int overlay,
      ModelPart canvas,
      SpriteIdentifier baseSprite,
      boolean isBanner,
      List<Pair<BannerPattern, DyeColor>> patterns,
      boolean _snowman
   ) {
      canvas.render(matrices, baseSprite.method_30001(vertexConsumers, RenderLayer::getEntitySolid, _snowman), light, overlay);

      for (int _snowmanx = 0; _snowmanx < 17 && _snowmanx < patterns.size(); _snowmanx++) {
         Pair<BannerPattern, DyeColor> _snowmanxx = patterns.get(_snowmanx);
         float[] _snowmanxxx = ((DyeColor)_snowmanxx.getSecond()).getColorComponents();
         SpriteIdentifier _snowmanxxxx = new SpriteIdentifier(
            isBanner ? TexturedRenderLayers.BANNER_PATTERNS_ATLAS_TEXTURE : TexturedRenderLayers.SHIELD_PATTERNS_ATLAS_TEXTURE,
            ((BannerPattern)_snowmanxx.getFirst()).getSpriteId(isBanner)
         );
         canvas.render(matrices, _snowmanxxxx.getVertexConsumer(vertexConsumers, RenderLayer::getEntityNoOutline), light, overlay, _snowmanxxx[0], _snowmanxxx[1], _snowmanxxx[2], 1.0F);
      }
   }
}
