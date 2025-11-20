package net.minecraft.client.render.entity.model;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class TridentEntityModel extends Model {
   public static final Identifier TEXTURE = new Identifier("textures/entity/trident.png");
   private final ModelPart trident = new ModelPart(32, 32, 0, 6);

   public TridentEntityModel() {
      super(RenderLayer::getEntitySolid);
      this.trident.addCuboid(-0.5F, 2.0F, -0.5F, 1.0F, 25.0F, 1.0F, 0.0F);
      ModelPart _snowman = new ModelPart(32, 32, 4, 0);
      _snowman.addCuboid(-1.5F, 0.0F, -0.5F, 3.0F, 2.0F, 1.0F);
      this.trident.addChild(_snowman);
      ModelPart _snowmanx = new ModelPart(32, 32, 4, 3);
      _snowmanx.addCuboid(-2.5F, -3.0F, -0.5F, 1.0F, 4.0F, 1.0F);
      this.trident.addChild(_snowmanx);
      ModelPart _snowmanxx = new ModelPart(32, 32, 0, 0);
      _snowmanxx.addCuboid(-0.5F, -4.0F, -0.5F, 1.0F, 4.0F, 1.0F, 0.0F);
      this.trident.addChild(_snowmanxx);
      ModelPart _snowmanxxx = new ModelPart(32, 32, 4, 3);
      _snowmanxxx.mirror = true;
      _snowmanxxx.addCuboid(1.5F, -3.0F, -0.5F, 1.0F, 4.0F, 1.0F);
      this.trident.addChild(_snowmanxxx);
   }

   @Override
   public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
      this.trident.render(matrices, vertices, light, overlay, red, green, blue, alpha);
   }
}
