package net.minecraft.client.render.entity.feature;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.SheepEntityModel;
import net.minecraft.client.render.entity.model.SheepWoolEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class SheepWoolFeatureRenderer extends FeatureRenderer<SheepEntity, SheepEntityModel<SheepEntity>> {
   private static final Identifier SKIN = new Identifier("textures/entity/sheep/sheep_fur.png");
   private final SheepWoolEntityModel<SheepEntity> model = new SheepWoolEntityModel<>();

   public SheepWoolFeatureRenderer(FeatureRendererContext<SheepEntity, SheepEntityModel<SheepEntity>> arg) {
      super(arg);
   }

   public void render(MatrixStack arg, VertexConsumerProvider arg2, int i, SheepEntity arg3, float f, float g, float h, float j, float k, float l) {
      if (!arg3.isSheared() && !arg3.isInvisible()) {
         float s;
         float t;
         float u;
         if (arg3.hasCustomName() && "jeb_".equals(arg3.getName().asString())) {
            int m = 25;
            int n = arg3.age / 25 + arg3.getEntityId();
            int o = DyeColor.values().length;
            int p = n % o;
            int q = (n + 1) % o;
            float r = ((float)(arg3.age % 25) + h) / 25.0F;
            float[] fs = SheepEntity.getRgbColor(DyeColor.byId(p));
            float[] gs = SheepEntity.getRgbColor(DyeColor.byId(q));
            s = fs[0] * (1.0F - r) + gs[0] * r;
            t = fs[1] * (1.0F - r) + gs[1] * r;
            u = fs[2] * (1.0F - r) + gs[2] * r;
         } else {
            float[] hs = SheepEntity.getRgbColor(arg3.getColor());
            s = hs[0];
            t = hs[1];
            u = hs[2];
         }

         render(this.getContextModel(), this.model, SKIN, arg, arg2, i, arg3, f, g, j, k, l, h, s, t, u);
      }
   }
}
