package net.minecraft.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.LeashKnotEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.decoration.LeashKnotEntity;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class LeashKnotEntityRenderer extends EntityRenderer<LeashKnotEntity> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/lead_knot.png");
   private final LeashKnotEntityModel<LeashKnotEntity> model = new LeashKnotEntityModel<>();

   public LeashKnotEntityRenderer(EntityRenderDispatcher arg) {
      super(arg);
   }

   public void render(LeashKnotEntity arg, float f, float g, MatrixStack arg2, VertexConsumerProvider arg3, int i) {
      arg2.push();
      arg2.scale(-1.0F, -1.0F, 1.0F);
      this.model.setAngles(arg, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
      VertexConsumer lv = arg3.getBuffer(this.model.getLayer(TEXTURE));
      this.model.render(arg2, lv, i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
      arg2.pop();
      super.render(arg, f, g, arg2, arg3, i);
   }

   public Identifier getTexture(LeashKnotEntity arg) {
      return TEXTURE;
   }
}
