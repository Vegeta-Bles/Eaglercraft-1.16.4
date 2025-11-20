package net.minecraft.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.ParrotEntityModel;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class ParrotEntityRenderer extends MobEntityRenderer<ParrotEntity, ParrotEntityModel> {
   public static final Identifier[] TEXTURES = new Identifier[]{
      new Identifier("textures/entity/parrot/parrot_red_blue.png"),
      new Identifier("textures/entity/parrot/parrot_blue.png"),
      new Identifier("textures/entity/parrot/parrot_green.png"),
      new Identifier("textures/entity/parrot/parrot_yellow_blue.png"),
      new Identifier("textures/entity/parrot/parrot_grey.png")
   };

   public ParrotEntityRenderer(EntityRenderDispatcher arg) {
      super(arg, new ParrotEntityModel(), 0.3F);
   }

   public Identifier getTexture(ParrotEntity arg) {
      return TEXTURES[arg.getVariant()];
   }

   public float getAnimationProgress(ParrotEntity arg, float f) {
      float g = MathHelper.lerp(f, arg.prevFlapProgress, arg.flapProgress);
      float h = MathHelper.lerp(f, arg.prevMaxWingDeviation, arg.maxWingDeviation);
      return (MathHelper.sin(g) + 1.0F) * h;
   }
}
