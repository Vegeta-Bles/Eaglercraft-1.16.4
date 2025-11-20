package net.minecraft.client.render.block.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.ConduitBlockEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;

@Environment(EnvType.CLIENT)
public class ConduitBlockEntityRenderer extends BlockEntityRenderer<ConduitBlockEntity> {
   public static final SpriteIdentifier BASE_TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("entity/conduit/base"));
   public static final SpriteIdentifier CAGE_TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("entity/conduit/cage"));
   public static final SpriteIdentifier WIND_TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("entity/conduit/wind"));
   public static final SpriteIdentifier WIND_VERTICAL_TEXTURE = new SpriteIdentifier(
      SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("entity/conduit/wind_vertical")
   );
   public static final SpriteIdentifier OPEN_EYE_TEXTURE = new SpriteIdentifier(
      SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("entity/conduit/open_eye")
   );
   public static final SpriteIdentifier CLOSED_EYE_TEXTURE = new SpriteIdentifier(
      SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("entity/conduit/closed_eye")
   );
   private final ModelPart field_20823 = new ModelPart(16, 16, 0, 0);
   private final ModelPart field_20824;
   private final ModelPart field_20825;
   private final ModelPart field_20826;

   public ConduitBlockEntityRenderer(BlockEntityRenderDispatcher arg) {
      super(arg);
      this.field_20823.addCuboid(-4.0F, -4.0F, 0.0F, 8.0F, 8.0F, 0.0F, 0.01F);
      this.field_20824 = new ModelPart(64, 32, 0, 0);
      this.field_20824.addCuboid(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F);
      this.field_20825 = new ModelPart(32, 16, 0, 0);
      this.field_20825.addCuboid(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F);
      this.field_20826 = new ModelPart(32, 16, 0, 0);
      this.field_20826.addCuboid(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F);
   }

   public void render(ConduitBlockEntity arg, float f, MatrixStack arg2, VertexConsumerProvider arg3, int i, int j) {
      float g = (float)arg.ticks + f;
      if (!arg.isActive()) {
         float h = arg.getRotation(0.0F);
         VertexConsumer lv = BASE_TEXTURE.getVertexConsumer(arg3, RenderLayer::getEntitySolid);
         arg2.push();
         arg2.translate(0.5, 0.5, 0.5);
         arg2.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(h));
         this.field_20825.render(arg2, lv, i, j);
         arg2.pop();
      } else {
         float k = arg.getRotation(f) * (180.0F / (float)Math.PI);
         float l = MathHelper.sin(g * 0.1F) / 2.0F + 0.5F;
         l = l * l + l;
         arg2.push();
         arg2.translate(0.5, (double)(0.3F + l * 0.2F), 0.5);
         Vector3f lv2 = new Vector3f(0.5F, 1.0F, 0.5F);
         lv2.normalize();
         arg2.multiply(new Quaternion(lv2, k, true));
         this.field_20826.render(arg2, CAGE_TEXTURE.getVertexConsumer(arg3, RenderLayer::getEntityCutoutNoCull), i, j);
         arg2.pop();
         int m = arg.ticks / 66 % 3;
         arg2.push();
         arg2.translate(0.5, 0.5, 0.5);
         if (m == 1) {
            arg2.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(90.0F));
         } else if (m == 2) {
            arg2.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(90.0F));
         }

         VertexConsumer lv3 = (m == 1 ? WIND_VERTICAL_TEXTURE : WIND_TEXTURE).getVertexConsumer(arg3, RenderLayer::getEntityCutoutNoCull);
         this.field_20824.render(arg2, lv3, i, j);
         arg2.pop();
         arg2.push();
         arg2.translate(0.5, 0.5, 0.5);
         arg2.scale(0.875F, 0.875F, 0.875F);
         arg2.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(180.0F));
         arg2.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(180.0F));
         this.field_20824.render(arg2, lv3, i, j);
         arg2.pop();
         Camera lv4 = this.dispatcher.camera;
         arg2.push();
         arg2.translate(0.5, (double)(0.3F + l * 0.2F), 0.5);
         arg2.scale(0.5F, 0.5F, 0.5F);
         float n = -lv4.getYaw();
         arg2.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(n));
         arg2.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(lv4.getPitch()));
         arg2.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(180.0F));
         float o = 1.3333334F;
         arg2.scale(1.3333334F, 1.3333334F, 1.3333334F);
         this.field_20823
            .render(arg2, (arg.isEyeOpen() ? OPEN_EYE_TEXTURE : CLOSED_EYE_TEXTURE).getVertexConsumer(arg3, RenderLayer::getEntityCutoutNoCull), i, j);
         arg2.pop();
      }
   }
}
