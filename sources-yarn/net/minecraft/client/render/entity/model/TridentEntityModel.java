package net.minecraft.client.render.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class TridentEntityModel extends Model {
   public static final Identifier TEXTURE = new Identifier("textures/entity/trident.png");
   private final ModelPart trident = new ModelPart(32, 32, 0, 6);

   public TridentEntityModel() {
      super(RenderLayer::getEntitySolid);
      this.trident.addCuboid(-0.5F, 2.0F, -0.5F, 1.0F, 25.0F, 1.0F, 0.0F);
      ModelPart lv = new ModelPart(32, 32, 4, 0);
      lv.addCuboid(-1.5F, 0.0F, -0.5F, 3.0F, 2.0F, 1.0F);
      this.trident.addChild(lv);
      ModelPart lv2 = new ModelPart(32, 32, 4, 3);
      lv2.addCuboid(-2.5F, -3.0F, -0.5F, 1.0F, 4.0F, 1.0F);
      this.trident.addChild(lv2);
      ModelPart lv3 = new ModelPart(32, 32, 0, 0);
      lv3.addCuboid(-0.5F, -4.0F, -0.5F, 1.0F, 4.0F, 1.0F, 0.0F);
      this.trident.addChild(lv3);
      ModelPart lv4 = new ModelPart(32, 32, 4, 3);
      lv4.mirror = true;
      lv4.addCuboid(1.5F, -3.0F, -0.5F, 1.0F, 4.0F, 1.0F);
      this.trident.addChild(lv4);
   }

   @Override
   public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
      this.trident.render(matrices, vertices, light, overlay, red, green, blue, alpha);
   }
}
